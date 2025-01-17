import snippetPlugin from "markdown-it-vuepress-code-snippet-enhanced";
import defineVersionedConfig from "vitepress-versioning-plugin";

import { loadLocales, processExistingEntries } from "./i18n";
import { transformItems, transformPageData } from "./transform";

// https://vitepress.dev/reference/site-config
// https://www.npmjs.com/package/vitepress-versioning-plugin
export default defineVersionedConfig(
  {
    cleanUrls: true,

    head: [["link", { rel: "icon", sizes: "32x32", href: "/favicon.png" }]],

    // Prevent dead links from being reported as errors - allows partially translated pages to be built.
    ignoreDeadLinks: true,

    lastUpdated: false, // Git is Corrupted so it is Disabled.


    // Reduce the size of the dist by using a separate js file for the metadata.
    metaChunk: true,

    locales: loadLocales(__dirname),

    markdown: {
      config(md) {
        md.use(snippetPlugin);
      },
      languages: [
        async () =>
          await import("syntax-mcfunction/mcfunction.tmLanguage.json", {
            with: { type: "json" },
          }).then((lang) => ({ ...(lang.default as any), name: "mcfunction" })),
      ],
      async shikiSetup(shiki) {
        await shiki.loadTheme("github-light", "github-dark");
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
        options: {
          _render(src, env, md) {
            if (env.frontmatter?.search === false) return "";
            if (env.relativePath.startsWith("translated/")) return "";
            if (env.relativePath.startsWith("versions/")) return "";
            return md.render(src, env);
          },
        },
        provider: "local",
      },
    },

    transformPageData,

    versioning: {
      latestVersion: "1.21.4",
      rewrites: {
        localePrefix: "translated",
      },
      sidebars: {
        sidebarContentProcessor: processExistingEntries,
        sidebarUrlProcessor: (url: string, version: string) =>
          url.startsWith("/") ? `/${version}${url}` : url,
      },
    },
  },
  __dirname
);
