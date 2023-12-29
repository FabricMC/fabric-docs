import { defineConfig } from 'vitepress'

// https://vitepress.dev/reference/site-config
export default defineConfig({
  title: "Fabric Documentation",
  description: "Fabric docs site",
  themeConfig: {
    // https://vitepress.dev/reference/default-theme-config
    nav: [
      { text: 'Home', link: 'https://fabricmc.net/' }
    ],

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
