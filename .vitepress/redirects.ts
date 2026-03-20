import latestVersion from "virtual:fabric-docs:latest-version";

export default [
  {
    from: /((?<=^|[/])index)?[.]html$/,
    dest: "",
  },
  {
    from: new RegExp(`^${latestVersion}([/]|$)`),
    dest: "",
  },
  {
    from: /develop[/]items[/]custom-item-groups$/,
    dest: "develop/items/custom-creative-tabs",
  },
  {
    from: /develop[/]rendering[/]draw-context$/,
    dest: "develop/rendering/gui-graphics",
  },
  {
    from: /develop[/]migrating-mappings([/]|$)/,
    dest: "develop/porting/mappings/",
  },
  {
    from: /develop[/]porting[/]current$/,
    dest: "develop/porting/",
  },
  {
    from: /develop[/]porting[/](next|26[.]1)([/]|$)/,
    dest: "26.1/develop/porting/",
  },
] satisfies { from: RegExp; dest: string }[];
