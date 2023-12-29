import { defineConfig } from 'vitepress'

// https://vitepress.dev/reference/site-config
export default defineConfig({
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

    outline: "deep",

    sidebar: [
      {
        text: 'Examples',
        items: [
          { text: 'Develop Example', link: '/develop/example' }
        ]
      }
    ],

    socialLinks: [
      { icon: 'github', link: 'https://github.com/vuejs/vitepress' }
    ]
  }
})
