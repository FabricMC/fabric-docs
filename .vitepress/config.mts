import { defineConfig } from 'vitepress'
import defineVersionedConfig from 'vitepress-versioning-plugin'

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

  markdown: {
    lineNumbers: true,
    math: true
  },

  themeConfig: {
    // https://vitepress.dev/reference/default-theme-config
    nav: [
      { text: 'Home', link: 'https://fabricmc.net/' }
    ],

    search: {
      provider: 'local'
    },

    sidebar: {
      '/': [
        {
          text: 'Examples',
          items: [
            { text: 'Develop Example', link: '/develop/example' }
          ]
        },
      ]
    },

    socialLinks: [
      { icon: 'github', link: 'https://github.com/FabricMC/fabric-docs' },
      { icon: 'discord', link: 'https://discord.gg/v6v4pMv' }
    ]
  }
})
