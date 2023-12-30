import { PageData, TransformPageContext } from 'vitepress'

import defineVersionedConfig from 'vitepress-versioning-plugin'

import RootSidebar from './sidebars/root'
import PlayersSidebar from './sidebars/players'

import { applySEO } from './seo'

// https://vitepress.dev/reference/site-config
// https://www.npmjs.com/package/vitepress-versioning-plugin
export default defineVersionedConfig(__dirname, {
  versioning: {
    latestVersion: '1.20.4'
  },

  title: "Fabric Documentation",
  description: "Comprehensive documentation for Fabric, the Minecraft modding toolchain.",
  cleanUrls: true,

  srcExclude: [
    "README.md",
    "LICENSE.md",
  ],

  transformPageData(pageData: PageData, ctx: TransformPageContext) {
    applySEO(pageData)
  },

  markdown: {
    lineNumbers: true,
    math: true
  },

  themeConfig: {
    // https://vitepress.dev/reference/default-theme-config
    nav: [
      { text: 'Home', link: 'https://fabricmc.net/' },
      { text: 'Download', link: 'https://fabricmc.net/use' }
    ],

    search: {
      provider: 'local'
    },

    outline: "deep",

    sidebar: {
      '/': RootSidebar,
      '/players/': PlayersSidebar
    },

    socialLinks: [
      { icon: 'github', link: 'https://github.com/FabricMC/fabric-docs' },
      { icon: 'discord', link: 'https://discord.gg/v6v4pMv' }
    ],

    logo: "/assets/logo.png"
  }
})
