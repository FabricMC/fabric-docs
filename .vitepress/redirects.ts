import latestVersion from "virtual:fabric-docs:latest-version";

export default [
  {
    from: /[/]{2,}/,
    dest: "/",
  },
  {
    from: /((?<=^|[/])index)?[.]html$/,
    dest: "",
  },
  {
    from: new RegExp(`^${latestVersion}([/]|$)`),
    dest: "",
  },
  {
    from: new RegExp(`^1.21.10([/]|$)`),
    dest: "1.21.11/",
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
  {
    from: /develop[/]blocks[/]transparency-and-tinting$/,
    dest: "develop/blocks/block-tinting",
  },
  {
    from: /develop[/]blocks[/]block-tinting$/,
    dest: "develop/blocks/transparency-and-tinting/",
  },
  {
    from: /^(?:[0-9.]+[/])?develop[/]porting[/]mappings([/].*)?$/,
    dest: "1.21.11/develop/porting/mappings$1",
  },
] satisfies { from: RegExp; dest: string }[];
