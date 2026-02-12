import snippetPlugin from "markdown-it-vuepress-code-snippet-enhanced";
import * as fs from "node:fs";
import * as path from "node:path/posix";
import * as process from "node:process";
import { tabsMarkdownPlugin } from "vitepress-plugin-tabs";
import defineVersionedConfig from "vitepress-versioning-plugin";

import { getLocales } from "./i18n";
import { transformHead, transformItems } from "./transform";
import { Fabric } from "./types";

// https://docs.github.com/en/actions/reference/workflows-and-actions/variables#default-environment-variables
// https://docs.netlify.com/build/configure-builds/environment-variables/#read-only-variables
const env = process.env.GITHUB_ACTIONS
  ? "github"
  : process.env.NETLIFY
    ? Number(process.env.REVIEW_ID)
    : process.env.NODE_ENV === "production"
      ? "build"
      : "dev";

const hostname =
  env === "github"
    ? "https://docs.fabricmc.net/"
    : env === "build"
      ? "http://fabric-docs.localhost:4173/"
      : env === "dev"
        ? "http://fabric-docs.localhost:5173/"
        : process.env.DEPLOY_PRIME_URL!;

const latestVersion = fs
  .readFileSync(
    path.resolve(import.meta.dirname, "..", "reference", "latest", "build.gradle"),
    "utf-8"
  )
  .match(/def minecraftVersion = "([^"]+)"/)![1];

// https://vitepress.dev/reference/site-config
// https://www.npmjs.com/package/vitepress-versioning-plugin
export default defineVersionedConfig(
  {
    // Removes .html from the end of URLs.
    cleanUrls: true,

    // Static head tags
    head: [
      ["link", { rel: "icon", sizes: "32x32", href: "/favicon.png" }],
      ["link", { rel: "license", href: "https://github.com/FabricMC/fabric-docs/blob/-/LICENSE" }],
      ["meta", { name: "theme-color", content: "#2275da" }],
      ["meta", { name: "twitter:card", content: "summary" }], // haha still twitter
    ],

    // Ignore dead links under translated/. Allows builds with incomplete translations
    ignoreDeadLinks: [
      (_, filePath) => {
        const split = filePath.split("/");
        if (split[0] === "versions") split.splice(0, 2);
        return split[0] !== "translated";
      },
    ],

    // Adds a "Last Updated" block to the footer of pages, uses git to determine the last time a page's file was modified.
    lastUpdated: true,

    // Reduce the size of the dist by using a separate js file for the metadata.
    metaChunk: true,

    locales: getLocales(),

    markdown: {
      config: (md) => {
        // Use the snippet plugin for transclusions
        md.use(snippetPlugin);
        // Use the tabs plugin for... having tabs?
        md.use(tabsMarkdownPlugin);
      },
      gfmAlerts: false,
      image: { lazyLoading: true },
      languageAlias: { gradle: "groovy" },
      languages: [
        async () =>
          await import("syntax-mcfunction/mcfunction.tmLanguage.json", {
            with: { type: "json" },
          }).then((lang) => ({ ...(lang.default as any), name: "mcfunction" })),
        async () =>
          await import("syntax-java-bytecode/java-bytecode.tmLanguage.json", {
            with: { type: "json" },
          }).then((lang) => ({ ...(lang.default as any), name: "bytecode" })),
      ],
      lineNumbers: true,
      shikiSetup: async (shiki) => {
        await shiki.loadTheme("github-light", "github-dark");
      },
    },

    rewrites: { "translated/:lang/(.*)": ":lang/(.*)" },

    sitemap: {
      hostname,
      transformItems,
    },

    srcExclude: ["README.md", ...(typeof env === "number" ? ["versions"] : [])],

    themeConfig: {
      env,
      externalLinkIcon: true,
      logo: "/logo.png",
      outline: { level: "deep" },
      search: {
        options: {
          _render: (src, env, md) =>
            env.frontmatter?.search === false
            || env.relativePath.startsWith("translated/")
            || env.relativePath.startsWith("versions/")
              ? ""
              : md.render(src, env),
        },
        provider: "local",
      },
    } as Fabric.ThemeConfig,

    // Dynamic head tags
    transformHead,

    // Versioning plugin configuration.
    versioning: {
      latestVersion,
      rewrites: { localePrefix: "translated" },
      sidebars: {
        sidebarContentProcessor: (s) =>
          Object.fromEntries(
            Object.entries(s).map(([k, v]) => [
              (() => {
                const split = k.split("/").filter(Boolean);
                if (split[0] === split[2]) split.splice(2, 1);
                return `/${split.join("/")}/`;
              })(),
              v,
            ])
          ),
        sidebarUrlProcessor: (url, version) =>
          url.startsWith("/") ? `/${version}${/^\/.._..\//.test(url) ? url.slice(6) : url}` : url,
      },
    },
  },
  import.meta.dirname
);
