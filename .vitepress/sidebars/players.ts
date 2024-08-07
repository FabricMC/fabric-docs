import { Fabric } from "../types";

export default [
  {
    text: "players.title",
    link: "/players/",
    items: [
      {
        text: "players.faq",
        link: "/players/faq",
      },
      {
        text: "players.installingJava",
        collapsed: true,
        items: [
          {
            text: "players.installingJava.windows",
            link: "/players/installing-java/windows",
          },
          {
            text: "players.installingJava.macOS",
            link: "https://fabricmc.net/wiki/player:tutorials:java:mac",
            process: false,
          },
          {
            text: "players.installingJava.linux",
            link: "/players/installing-java/linux",
          },
        ],
      },
      {
        text: "players.installingFabric",
        link: "/players/installing-fabric",
      },
      {
        text: "players.findingMods",
        link: "/players/finding-mods",
      },
      {
        text: "players.installingMods",
        link: "/players/installing-mods",
      },
      {
        text: "players.troubleshooting",
        items: [
          {
            text: "players.troubleshooting.uploadingLogs",
            link: "/players/troubleshooting/uploading-logs",
          },
          {
            text: "players.troubleshooting.crashReports",
            link: "/players/troubleshooting/crash-reports",
          },
        ],
      },
      {
        text: "players.updatingFabric",
        link: "/players/updating-fabric",
      },
    ],
  },
  {
    text: "players.thirdParty",
    link: "/players/third-party",
    /* 
    "Using Fabric with Modrinth App"
    "Using Fabric with CurseForge App"
    "Using Fabric with Prism Launcher"
    "Using Fabric with ATLauncher"
    "Using Fabric with GDLauncher Carbon"
    "Using Fabric with FTB App"
    */
    items: [
      {
        text: "players.thirdParty.modrinth",
        link: "/players/third-party/modrinth",
      },
      {
        text: "players.thirdParty.curseforge",
        link: "/players/third-party/curseforge",
      },
      {
        text: "players.thirdParty.prism",
        link: "/players/third-party/prism",
      },
      {
        text: "players.thirdParty.atlauncher",
        link: "/players/third-party/atlauncher",
      },
      {
        text: "players.thirdParty.gdlauncher",
        link: "/players/third-party/gdlauncher",
      },
      {
        text: "players.thirdParty.ftb",
        link: "/players/third-party/ftb",
      },
    ]
  }
] as Fabric.SidebarItem[];
