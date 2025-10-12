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
        text: "players.installing_java",
        collapsed: true,
        items: [
          {
            text: "players.installing_java.windows",
            link: "/players/installing-java/windows",
          },
          {
            text: "players.installing_java.macos",
            link: "https://fabricmc.net/wiki/player:tutorials:java:mac",
            process: false,
          },
          {
            text: "players.installing_java.linux",
            link: "/players/installing-java/linux",
          },
        ],
      },
      {
        text: "players.installing_fabric",
        link: "/players/installing-fabric",
      },
      {
        text: "players.finding_mods",
        link: "/players/finding-mods",
      },
      {
        text: "players.installing_mods",
        link: "/players/installing-mods",
      },
      {
        text: "players.troubleshooting",
        items: [
          {
            text: "players.troubleshooting.uploading_logs",
            link: "/players/troubleshooting/uploading-logs",
          },
          {
            text: "players.troubleshooting.crash_reports",
            link: "/players/troubleshooting/crash-reports",
          },
        ],
      },
      {
        text: "players.updating_fabric",
        link: "/players/updating-fabric",
      },
    ],
  },
] as Fabric.SidebarItem[];
