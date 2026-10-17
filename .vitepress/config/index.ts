import snippetPlugin from "markdown-it-vuepress-code-snippet-enhanced";
import * as fs from "node:fs";
import * as path from "node:path";
import * as process from "node:process";
import bytecode from "syntax-java-bytecode/java-bytecode.tmLanguage.json" with { type: "json" };
import mcfunction from "syntax-mcfunction/mcfunction.tmLanguage.json" with { type: "json" };
import type { SiteConfig } from "vitepress";
import { tabsMarkdownPlugin } from "vitepress-plugin-tabs";
import defineVersionedConfig from "vitepress-versioning-plugin";
import {
  ALL_VERSIONS,
  AT,
  ENV,
  EXCLUDED_LOCALES,
  EXCLUDED_OLD_VERSIONS,
  LATEST_VERSION,
} from "../constants.ts";
import { transformFile, transformFilesPlugin } from "../plugins/transformFiles.ts";
import type { Fabric } from "../types.d.ts";
import { getBuildTransformHead, getClientTransformHead, pageToVersionsMap } from "./head.ts";
import { getLocaleConfig } from "./i18n.ts";

// https://vitepress.dev/reference/site-config
// https://www.npmjs.com/package/vitepress-versioning-plugin
export default defineVersionedConfig(
  {
    buildEnd: (siteConfig) => {
      fs.writeFileSync(
        path.join(siteConfig.outDir, "page-versions.json"),
        JSON.stringify(
          Object.fromEntries(
            [...pageToVersionsMap].map(([purePath, versions]) => [purePath, [...versions]])
          )
        )
      );
    },

    // Removes .html from the end of URLs.
    cleanUrls: true,

    // Set head tags on the client side
    head: [["script", { "data-gen": "" }, getClientTransformHead()]],

    // Allow builds with incomplete translations
    ignoreDeadLinks: [
      (link, filePath) => {
        if (link.startsWith("/assets/")) return true;

        const split = path.relative(AT, filePath).split("/");
        if (split[0] === "versions") {
          split.splice(0, 2);
        }

        return split[0] === "translated";
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
      container: {
        // TODO: acknowledge ::: caution and ::: note
        // TODO: review all ::: containers with unnecessary title, except ::: details
        // TODO: migrate ::: info PREREQUISITES
        // TODO: migrate ::: warning IMPORTANT
        customContainers: {
          prerequisites: "PREREQUISITES",
        },
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
      hostname:
        {
          dev: "http://fabric-docs.localhost:5173/",
          build: "http://fabric-docs.localhost:4173/",
          github: "https://docs.fabricmc.net/",
          netlify: "https://fabric-docs.netlify.app/",
        }[ENV] || process.env.DEPLOY_PRIME_URL!,
      transformItems: (items) => {
        const config = (globalThis as any).VITEPRESS_CONFIG as SiteConfig;
        const getFilePath = (url: string) => `${url.replace(/[/]$/, "/index")}.md`;

        return items.filter(
          (i) => !config.rewrites.inv[getFilePath(i.url)]?.startsWith("versions/")
        );
      },
    },

    srcExclude: [
      "README.md",
      ...EXCLUDED_OLD_VERSIONS.map((v) => `versions/${v}`),
      ...EXCLUDED_LOCALES.map((l) => `**/translated/${l}`),
    ],

    themeConfig: {
      env: ENV,
      externalLinkIcon: true,
      logo: "/logo.png",
      outline: { level: "deep" },
      search: {
        options: {
          _render: async (src, env, md) => {
            src = transformFile(src, env.path).replace(/<Badge .*> (?={#h1})/, "");
            const html = await md.renderAsync(src, env);

            return env.frontmatter?.search === false ? "" : html;
          },
        },
        provider: "local",
      },
      version: { latestVersion: LATEST_VERSION, allVersions: ALL_VERSIONS },
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
