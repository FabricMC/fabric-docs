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
  },
  {
    text: "Technical Info and Specs",
    link: "/technical",
    items: [
      {
        text: "fabric.mod.json Specification",
        link: "/technical/specifications/fabric-mod-json"
      },
      {
        text: "Fabric Loader",
        link: "/technical/fabric-loader"
      },
      {
        text: "Fabric Loom",
        link: "/technical/fabric-loom"
      }
    ]
  }
] as DefaultTheme.SidebarItem[];