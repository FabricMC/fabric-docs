import { DefaultTheme } from "vitepress";

export default [
  {
    text: "Player Guides",
    link: "/players/",
    items: [
      {
        text: "Installing Fabric",
        link: "/players/installing-fabric"
      },
      {
        text: "Finding Mods",
        link: "/players/finding-mods"
      },
      {
        text: "Uploading Your Logs",
        link: "/players/troubleshooting/uploading-logs"
      }
    ]
  }
] as DefaultTheme.SidebarItem[];