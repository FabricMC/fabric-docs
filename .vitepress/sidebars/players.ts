import { ExtendedSidebarItem } from "./utils";

export default [
  {
    text: "players.title",
    link: "/players/",
    items: [
      {
        text: "players.faq",
        link: "/players/faq"
      },
      {
        text: "players.installingJava",
        collapsed: true,
        items: [
          {
            text: "players.installingJava.windows",
            link: "/players/installing-java/windows"
          },
          {
            text: "players.installingJava.macOS",
            link: "https://fabricmc.net/wiki/player:tutorials:java:mac",
            // @ts-ignore
            process: false
          },
          {
            text: "players.installingJava.linux",
            link: "/players/installing-java/linux"
          }
        ]
      },
      {
        text: "players.installingFabric",
        link: "/players/installing-fabric"
      },
      {
        text: "players.findingMods",
        link: "/players/finding-mods"
      },
      {
        text: "players.installingMods",
        link: "/players/installing-mods"
      },
      {
        text: "players.troubleshooting",
        items: [
          {
            text: "players.troubleshooting.uploadingLogs",
            link: "/players/troubleshooting/uploading-logs"
          },
          {
            text: "players.troubleshooting.crashReports",
            link: "/players/troubleshooting/crash-reports"
          }
        ]
      },
      {
        text: "players.updatingFabric",
        link: "/players/updating-fabric"
      }
    ]
  }
] as ExtendedSidebarItem[];
