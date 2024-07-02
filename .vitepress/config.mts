import snippetPlugin from "markdown-it-vuepress-code-snippet-enhanced";
import defineVersionedConfig from "vitepress-versioning-plugin";

import { loadLocales } from "./i18n";
import { transformItems, transformPageData } from "./transform";

// https://vitepress.dev/reference/site-config
// https://www.npmjs.com/package/vitepress-versioning-plugin
export default defineVersionedConfig(
  {
    cleanUrls: true,

    head: [["link", { rel: "icon", sizes: "32x32", href: "/favicon.png" }]],

    // Prevent dead links from being reported as errors - allows partially translated pages to be built.
    ignoreDeadLinks: true,

    lastUpdated: true,

    locales: loadLocales(__dirname),

    markdown: {
      config(md) {
        md.use(snippetPlugin);
      },
      // TODO: load `mcfunction`
      // - https://vitepress.dev/guide/markdown#syntax-highlighting-in-code-blocks
      // - https://shiki.style/guide/load-lang
      // - https://github.com/MinecraftCommands/syntax-mcfunction/blob/main/mcfunction.tmLanguage.json
      lineNumbers: true,
      math: true,
    },

    rewrites: {
      "translated/:lang/(.*)": ":lang/(.*)",
    },

    sitemap: {
      hostname: "https://docs.fabricmc.net/",
      transformItems,
    },

    srcExclude: ["README.md"],

    themeConfig: {
      search: {
        provider: "local",
      },
    },

    transformPageData,

    versioning: {
      latestVersion: "1.20.4",
      rewrites: {
        localePrefix: "translated",
      },
    },
  },
  __dirname
);
