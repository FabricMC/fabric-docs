import { DefaultTheme } from 'vitepress'
import fs from 'node:fs'
import defineVersionedConfig from 'vitepress-versioning-plugin'
import path from 'node:path'

function loadSidebar(): DefaultTheme.SidebarMulti {
  const sidebarContent = fs.readFileSync(path.resolve(__dirname, "..", "sidebars.json"), "utf-8");
  return JSON.parse(sidebarContent);
}

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
      { text: 'Home', link: 'https://fabricmc.net/' },
      { text: 'Download', link: 'https://fabricmc.net/use' }
    ],

    search: {
      provider: 'local'
    },

    outline: "deep",

    sidebar: loadSidebar(),

    socialLinks: [
      { icon: 'github', link: 'https://github.com/FabricMC/fabric-docs' },
      { icon: 'discord', link: 'https://discord.gg/v6v4pMv' }
    ],

    logo: "https://fabricmc.net/assets/logo.png"
  }
})
