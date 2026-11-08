import type { Fabric } from "../types.d.ts";

export const PLAYERS_SIDEBAR: Fabric.SidebarItem = {
  base: "/players",
  link: "/",
  items: [
    //
    "/installing-java/",
    "/installing-fabric/",
    "/updating-fabric/",
    "/finding-mods",
    "/installing-mods",
    {
      base: "/players/troubleshooting",
      items: [
        //
        "/uploading-logs",
        "/crash-reports",
        "/dependency-overrides",
      ],
    },
    "/faq",
  ],
};
