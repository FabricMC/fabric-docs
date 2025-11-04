import { Fabric } from "../types";

export default [
  {
    text: "players.title",
    link: "/players/",
    items: [
      {
        text: "players.installing_java",
        link: "/players/installing-java/",
      },
      {
        text: "players.installing_fabric",
        link: "/players/installing-fabric/",
      },
      {
        text: "players.updating_fabric",
        link: "/players/updating-fabric/",
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
        text: "players.faq",
        link: "/players/faq",
      },
    ],
  },
] as Fabric.SidebarItem[];
