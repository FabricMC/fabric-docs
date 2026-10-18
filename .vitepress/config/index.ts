// @ts-expect-error
import transclusionsPlugin from "markdown-it-vuepress-code-snippet-enhanced";
import * as fs from "node:fs";
import * as path from "node:path";
import * as process from "node:process";
import bytecode from "syntax-java-bytecode/java-bytecode.tmLanguage.json" with { type: "json" };
import mcfunction from "syntax-mcfunction/mcfunction.tmLanguage.json" with { type: "json" };
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
import { createDownloadZips, downloadImagePlugin } from "../plugins/downloadImage.ts";
import { transformFile, transformFilesPlugin } from "../plugins/transformFiles.ts";
import { videoImagePlugin } from "../plugins/videoImage.ts";
import type { Config } from "../types.d.ts";
import { getBuildTransformHead, getClientTransformHead, pageToVersionsMap } from "./head.ts";
import { getLocaleConfig } from "./i18n.ts";

// https://vitepress.dev/reference/site-config
// https://npmx.dev/package/vitepress-versioning-plugin
export default defineVersionedConfig(
  {
    buildEnd: (siteConfig) => {
      createDownloadZips(siteConfig);

      fs.writeFileSync(
        path.join(siteConfig.outDir, "page-versions.json"),
        JSON.stringify(
          Object.fromEntries(
            [...pageToVersionsMap].map(([purePath, versions]) => [purePath, [...versions]])
          )
        )
      );
    },

    cleanUrls: true,

    // Set head tags on the client side
    head: [["script", { "data-gen": "" }, getClientTransformHead()]],

    icons: { include: ["lucide:download"] },

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

    lastUpdated: true,

    locales: getLocaleConfig(),

    markdown: {
      config: (md) => {
        md.use(transclusionsPlugin);
        md.use(tabsMarkdownPlugin);
        md.use(downloadImagePlugin);
        md.use(videoImagePlugin);
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
      footnote: false,
      gfmAlerts: false,
      image: { lazyLoad: true },
      languageAlias: { classtweaker: "text", gradle: "groovy" },
      languages: [
        { ...(bytecode as any), name: "bytecode" },
        { ...(mcfunction as any), name: "mcfunction" },
      ],
      lineNumbers: true,
      shikiSetup: async (shiki) => {
        await shiki.loadTheme("github-light", "github-dark");
      },
      snippet: { stripRegionMarkers: "all" },
      toc: false,
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
        const getFilePath = (url: string) => `${url.replace(/[/]$/, "/index")}.md`;

        return items.filter(
          (i) => !VITEPRESS_CONFIG.rewrites.inv[getFilePath(i.url)]?.startsWith("versions/")
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
            src = transformFile(src, env.path);
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
          isCustomElement: (tag) => /^(((audio|video)-(player|skin))|(youtube-video))$/.test(tag),
        },
      },
    },
  } as Config,
  path.join(AT, ".vitepress")
);
