import snippetPlugin from "markdown-it-vuepress-code-snippet-enhanced";
import defineVersionedConfig from "vitepress-versioning-plugin";

import { loadLocales, processExistingEntries } from "./i18n";
import { transformItems, transformPageData } from "./transform";
import { DefaultTheme } from "vitepress";

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
      languages: [
        (async () => {
          const mcfunctionLanguage = (await import("syntax-mcfunction/mcfunction.tmLanguage.json", {
            with: {
              type: "json"
            }
          }) as any).default

          mcfunctionLanguage.name = 'mcfunction';
          return mcfunctionLanguage;
        }),
      ],
      async shikiSetup(shiki) {
        await shiki.loadTheme('github-light', 'github-dark');
      },
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
        provider: "local"
      },
    },

    transformPageData,

    versioning: {
      latestVersion: "1.21",
      rewrites: {
        localePrefix: "translated",
      },
      sidebars: {
        sidebarContentProcessor(sidebar: DefaultTheme.SidebarMulti) {
          return processExistingEntries(sidebar);
        },
      }
    },

    build: {
      sourcemap: true,
    }
  },
  __dirname
);
