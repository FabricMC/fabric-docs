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
      },
      {
        text: "More...",
        link: "/players/"
      }
    ]
  },
  {
    text: "Developer Guides",
    link: "/develop/",
    items: [
      {
        text: "Creating A Project",
      },
      {
        text: "Adding an Item"
      },
      {
        text: "Mixins"
      },
      {
        text: "More...",
        link: "/develop/"
      }
    ]
  }
] as DefaultTheme.SidebarItem[];