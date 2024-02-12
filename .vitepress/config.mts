import { PageData, TransformPageContext } from 'vitepress'

import defineVersionedConfig from 'vitepress-versioning-plugin'
import snippetPlugin from 'markdown-it-vuepress-code-snippet-enhanced'

import RootSidebar from './sidebars/root'
import PlayersSidebar from './sidebars/players'
import DevelopSidebar from "./sidebars/develop"
import SpanishRootSidebar from './sidebars/es/root'
import SpanishPlayersSidebar from './sidebars/es/players'

import { applySEO } from './seo'
import { removeVersionedItems } from "./seo"

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
    // Ensures that it's `/contributing` instead of `/CONTRIBUTING`.
    'CONTRIBUTING.md': 'contributing.md',
    'translated/:lang/(.*)': ':lang/(.*)'
  },

  title: "Fabric Documentation",
  description: "Comprehensive documentation for Fabric, the Minecraft modding toolchain.",
  cleanUrls: true,

  head: [
    ['link', { rel: 'icon', sizes: '32x32', href: '/favicon.png' }],
  ],

  locales: {
    root: {
      label: 'English',
      lang: 'en'
    },

    es: {
      label: 'Español',
      lang: 'es',
      link: '/es/'
    }
  },

  srcExclude: [
    "README.md",
    "LICENSE.md",
  ],

  transformPageData(pageData: PageData, ctx: TransformPageContext) {
    applySEO(pageData)
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
  },

  themeConfig: {
    // https://vitepress.dev/reference/default-theme-config
    nav: [
      { text: 'Home', link: 'https://fabricmc.net/' },
      { text: 'Download', link: 'https://fabricmc.net/use' },
      {
        text: 'Contribute', items: [
          // Expand on this later, with guidelines for loader+loom potentially?
          {
            text: 'Fabric Documentation',
            link: '/contributing'
          },
          {
            text: 'Fabric API',
            link: 'https://github.com/FabricMC/fabric/blob/1.20.4/CONTRIBUTING.md'
          }
        ]
      },
    ],

    search: {
      provider: 'local'
    },

    outline: "deep",

    sidebar: {
      '/': RootSidebar,
      '/players/': PlayersSidebar,
      "/develop/": DevelopSidebar,
      '/es/': SpanishRootSidebar,
      '/es/players/': SpanishPlayersSidebar,
    },

    editLink: {
      pattern: ({ filePath }) => {
        return `https://github.com/FabricMC/fabric-docs/edit/main/${filePath}`
      },
      text: 'Edit this page on GitHub'
    },

    socialLinks: [
      { icon: 'github', link: 'https://github.com/FabricMC/fabric-docs' },
      { icon: 'discord', link: 'https://discord.gg/v6v4pMv' }
    ],

    logo: "/logo.png"
  }
})
