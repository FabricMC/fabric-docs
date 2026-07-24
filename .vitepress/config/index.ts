import cjkFriendlyPlugin from "markdown-it-cjk-friendly";
import snippetPlugin from "markdown-it-vuepress-code-snippet-enhanced";
import * as fs from "node:fs";
import * as path from "node:path";
import * as process from "node:process";
import bytecode from "syntax-java-bytecode/java-bytecode.tmLanguage.json";
import mcfunction from "syntax-mcfunction/mcfunction.tmLanguage.json";
import { SiteConfig } from "vitepress";
import { tabsMarkdownPlugin } from "vitepress-plugin-tabs";
import defineVersionedConfig from "vitepress-versioning-plugin";
import { transformFile, transformFilesPlugin } from "../plugins/transformFiles";
import { Fabric } from "../types.d";
import { getBuildTransformHead, getClientTransformHead } from "./head";
import { getLocales } from "./i18n";

const latestVersion = fs
  .readFileSync(
    path.resolve(import.meta.dirname, "..", "..", "reference", "latest", "build.gradle"),
    "utf-8"
  )
  .match(/def minecraftVersion = "([^"]+)"/)![1];

const builtVersions = [
  "1.20.4",
  "1.21.1",
  "1.21.10",
  "1.21.11",
  "1.21.4",
  "1.21.8",
  "26.1.2",
];
const excludedVersions = fs
  .readdirSync(path.resolve(import.meta.dirname, "..", "..", "versions"), {
    withFileTypes: true,
  })
  .filter((entry) => entry.isDirectory() && !builtVersions.includes(entry.name))
  .map((entry) => `versions/${entry.name}`)
  .sort();

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
        : `${process.env.DEPLOY_PRIME_URL!}/`;

// https://vitepress.dev/reference/site-config
// https://www.npmjs.com/package/vitepress-versioning-plugin
export default defineVersionedConfig(
  {
    // Removes .html from the end of URLs.
    cleanUrls: true,

    // Set head tags on the client side
    head: [["script", { "data-gen": "" }, getClientTransformHead(latestVersion)]],

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

    // Keep one canonical client build, but isolate bounded server/render batches
    // in disposable Node processes so their module graphs cannot accumulate.
    ssrBuildBatchSize: 512,

    locales: getLocales(),

    markdown: {
      config: (md) => {
        // Use the CJK-friendly plugin to fix emphasis
        md.use(cjkFriendlyPlugin);
        // Use the snippet plugin for transclusions
        md.use(snippetPlugin);
        // Use the tabs plugin for... having tabs?
        md.use(tabsMarkdownPlugin);
      },
      gfmAlerts: false,
      image: { lazyLoading: true },
      languageAlias: { classtweaker: "text", gradle: "groovy" },
      languages: [
        { ...(mcfunction as any), name: "mcfunction" },
        { ...(bytecode as any), name: "bytecode" },
      ],
      lineNumbers: true,
      shikiSetup: async (shiki) => {
        await shiki.loadTheme("github-light", "github-dark");
      },
    },

    rewrites: { "translated/:locale/(.*)": ":locale/(.*)" },

    sitemap: {
      hostname,
      transformItems: (items) => {
        const config = (globalThis as any).VITEPRESS_CONFIG as SiteConfig;
        return items.filter((i) => {
          const relativePath = i.url.replace(hostname, "");
          return !config.rewrites.inv[relativePath]?.startsWith("versions/");
        });
      },
    },

    srcExclude: [
      "README.md",
      ...excludedVersions,
      ...(typeof env === "number" ? ["versions"] : []),
    ],

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
              : md.render(
                transformFile(src, env.path, latestVersion).replace(/<Badge .*> (?={#h1})/, ""),
                env
              ),
        },
        provider: "local",
      },
    },

    // Set head tags at build time
    transformHead: getBuildTransformHead(latestVersion),

    // Versioning plugin configuration.
    versioning: {
      latestVersion,
      rewrites: { localePrefix: "translated" },
      versions: typeof env === "number" ? [] : builtVersions,
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
          url.startsWith("/") ? `/${version}${/^[/].._..[/]/.test(url) ? url.slice(6) : url}` : url,
      },
    },

    vite: {
      plugins: [transformFilesPlugin(latestVersion)],
    },

    vue: {
      template: {
        compilerOptions: {
          isCustomElement: (tag) => tag.startsWith("media-"),
        },
      },
    },
  } as Fabric.Config,
  path.resolve(import.meta.dirname, "..")
);
