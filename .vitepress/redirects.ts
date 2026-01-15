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
] satisfies { from: RegExp; dest: string }[];
