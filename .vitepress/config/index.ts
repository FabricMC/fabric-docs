import snippetPlugin from "markdown-it-vuepress-code-snippet-enhanced";
import * as path from "node:path";
import * as process from "node:process";
import bytecode from "syntax-java-bytecode/java-bytecode.tmLanguage.json" with { type: "json" };
import mcfunction from "syntax-mcfunction/mcfunction.tmLanguage.json" with { type: "json" };
import type { SiteConfig } from "vitepress";
import { tabsMarkdownPlugin } from "vitepress-plugin-tabs";
import defineVersionedConfig from "vitepress-versioning-plugin";
import { ENV, LATEST_VERSION } from "../constants.ts";
import { transformFile, transformFilesPlugin } from "../plugins/transformFiles.ts";
import type { Fabric } from "../types.d.ts";
import { getBuildTransformHead, getClientTransformHead } from "./head.ts";
import { getLocaleConfig } from "./i18n.ts";

const hostname =
  ENV === "github"
    ? "https://docs.fabricmc.net/"
    : ENV === "build"
      ? "http://fabric-docs.localhost:4173/"
      : ENV === "dev"
        ? "http://fabric-docs.localhost:5173/"
        : `${process.env.DEPLOY_PRIME_URL!}/`;

// https://vitepress.dev/reference/site-config
// https://www.npmjs.com/package/vitepress-versioning-plugin
export default defineVersionedConfig(
  {
    // Removes .html from the end of URLs.
    cleanUrls: true,

    // Set head tags on the client side
    head: [["script", { "data-gen": "" }, getClientTransformHead()]],

    // Ignore dead links under translated/. Allows builds with incomplete translations
    ignoreDeadLinks: [
      (_, filePath) => {
        const split = filePath.split("/");
        if (split[0] === "versions") {
          split.splice(0, 2);
        }
        return split[0] !== "translated";
      },
    ],

    // Adds a "Last Updated" block to the footer of pages, uses git to determine the last time a page's file was modified.
    lastUpdated: true,

    locales: getLocaleConfig(),

    markdown: {
      config: (md) => {
        // Use the snippet plugin for transclusions
        md.use(snippetPlugin);
        // Use the tabs plugin for... having tabs?
        md.use(tabsMarkdownPlugin);
      },
      gfmAlerts: false,
      image: { lazyLoad: true },
      languageAlias: { classtweaker: "text", gradle: "groovy" },
      languages: [
        { ...(mcfunction as any), name: "mcfunction" },
        { ...(bytecode as any), name: "bytecode" },
      ],
      lineNumbers: true,
      shikiSetup: async (shiki) => {
        await shiki.loadTheme("github-light", "github-dark");
      },
      snippet: { stripRegionMarkers: "all" },
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
      "versions/1.21.10",
      "versions/1.21.8",
      "versions/1.21.4",
      ...(typeof ENV === "number" ? ["versions"] : []),
    ],

    themeConfig: {
      env: ENV,
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
              : md.render(transformFile(src, env.path).replace(/<Badge .*> (?={#h1})/, ""), env),
        },
        provider: "local",
      },
    },

    // Set head tags at build time
    transformHead: getBuildTransformHead(),

    // Versioning plugin configuration.
    versioning: {
      latestVersion: LATEST_VERSION,
      rewrites: { localePrefix: "translated" },
      sidebars: {
        sidebarContentProcessor: (s) =>
          Object.fromEntries(
            Object.entries(s).map(([k, v]) => [
              (() => {
                const split = k.split("/").filter(Boolean);
                if (split[0] === split[2]) {
                  split.splice(2, 1);
                }
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
      plugins: [transformFilesPlugin()],
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
