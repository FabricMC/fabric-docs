import { PageData, TransformPageContext } from 'vitepress'

import snippetPlugin from 'markdown-it-vuepress-code-snippet-enhanced'
import defineVersionedConfig, { VersionedConfig } from 'vitepress-versioning-plugin'

import { loadLocales } from './i18n'
import { applySEO, removeVersionedItems } from './seo'

// https://vitepress.dev/reference/site-config
// https://www.npmjs.com/package/vitepress-versioning-plugin
export default defineVersionedConfig(__dirname, {
  versioning: {
    latestVersion: '1.20.4',
    rewrites: {
      localePrefix: 'translated'
    }
  },

  rewrites: {
    'translated/:lang/(.*)': ':lang/(.*)'
  },

  title: "Fabric Documentation",
  description: "Comprehensive documentation for Fabric, the Minecraft modding toolchain.",
  cleanUrls: true,

  head: [[
    'link',
    { rel: 'icon', sizes: '32x32', href: '/favicon.png' }
  ]],

  locales: loadLocales(__dirname),

  // Prevent dead links from being reported as errors - allows partially translated pages to be built.
  ignoreDeadLinks: true,

  srcExclude: [
    "README.md",
    "LICENSE.md",
  ],

  transformPageData(pageData: PageData, _ctx: TransformPageContext) {
    applySEO(pageData);
  },

  sitemap: {
    hostname: "https://docs.fabricmc.net/",
    transformItems: items => removeVersionedItems(items)
  },

  lastUpdated: true,

  markdown: {
    lineNumbers: true,
    math: true,
    config(md) {
      md.use(snippetPlugin);
    }
  }
} as VersionedConfig)
