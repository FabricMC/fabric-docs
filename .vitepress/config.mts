import snippetPlugin from "markdown-it-vuepress-code-snippet-enhanced";
import defineVersionedConfig from "vitepress-versioning-plugin";

import { getLocales } from "./i18n";
import { transformHead, transformItems } from "./transform";

const latestVersion = "1.21.10";

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
      },
      gfmAlerts: false,
      image: { lazyLoading: true },
      languages: [
        async () =>
          // Adds support for mcfunction language to shiki.
          await import("syntax-mcfunction/mcfunction.tmLanguage.json", {
            with: { type: "json" },
          }).then((lang) => ({ ...(lang.default as any), name: "mcfunction" })),
      ],
      lineNumbers: true,
      shikiSetup: async (shiki) => {
        await shiki.loadTheme("github-light", "github-dark");
      },
    },

    rewrites: { "translated/:lang/(.*)": ":lang/(.*)" },

    sitemap: {
      hostname: "https://docs.fabricmc.net/",
      transformItems,
    },

    srcExclude: ["README.md"],

    themeConfig: {
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
    },

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
  __dirname
);
