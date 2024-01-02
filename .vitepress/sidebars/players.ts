import { DefaultTheme } from "vitepress";

export default [
  {
    text: "Player Guides",
    link: "/players/",
    items: [
      {
        text: "Frequently Asked Questions",
        link: "/players/faq"
      },
      {
        text: "Installing Java",
        collapsed: true,
        items: [
          {
            text: "Windows",
            link: "/players/installing-java/windows"
          },
          {
            text: "MacOS",
            link: "https://fabricmc.net/wiki/player:tutorials:java:mac",
            // @ts-ignore
            process: false
          },
          {
            text: "Linux",
            link: "/players/installing-java/linux"
          }
        ]
      },
      {
        text: "Installing Fabric",
        link: "/players/installing-fabric"
      },
      {
        text: "Finding Trustworthy Mods",
        link: "/players/finding-mods"
      },
      {
        text: "Installing Mods",
        link: "/players/installing-mods"
      },
      {
        text: "Troubleshooting",
        items: [
          {
            text: "Uploading Your Logs",
            link: "/players/troubleshooting/uploading-logs"
          },
          {
            text: "Crash Reports",
            link: "/players/troubleshooting/crash-reports"
          }
        ]
      },
      {
        text: "Updating Fabric",
        link: "/players/updating-fabric"
      }
    ]
  }
] as DefaultTheme.SidebarItem[];
