import snippetPlugin from "markdown-it-vuepress-code-snippet-enhanced";
import defineVersionedConfig from "vitepress-versioning-plugin";

import { loadLocales, processExistingEntries } from "./i18n";
import { transformItems, transformPageData } from "./transform";

// https://vitepress.dev/reference/site-config
// https://www.npmjs.com/package/vitepress-versioning-plugin
export default defineVersionedConfig(
  {
    // Removes .html from the end of URLs.
    cleanUrls: true,

    // Mostly just for the favicon.
    head: [["link", { rel: "icon", sizes: "32x32", href: "/favicon.png" }]],

    // Prevent dead links from being reported as errors - allows partially translated pages to be built.
    ignoreDeadLinks: true,

    // Adds a "Last Updated" block to the footer of pages, uses git to determine the last time a page's file was modified.
    lastUpdated: true,

    // Reduce the size of the dist by using a separate js file for the metadata.
    metaChunk: true,

    locales: loadLocales(__dirname),

    markdown: {
      config(md) {
        // Use the snippet plugin (transclusion, etc.)
        md.use(snippetPlugin);
      },
      image: {
        lazyLoading: true,
      },
      languages: [
        async () =>
          // Adds support for mcfunction language to shiki.
          await import("syntax-mcfunction/mcfunction.tmLanguage.json", {
            with: { type: "json" },
          }).then((lang) => ({ ...(lang.default as any), name: "mcfunction" })),
      ],
      lineNumbers: true,
      math: true,
      async shikiSetup(shiki) {
        await shiki.loadTheme("github-light", "github-dark");
      },
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
          // Removes versioned and translated pages from search.
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

    // Versioning plugin configuration.
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
