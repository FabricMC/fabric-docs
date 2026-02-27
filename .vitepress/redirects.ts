export default [
  {
    from: /^(.*)((?<=^|[/])index)?\.html$/,
    dest: "$1",
  },
  {
    from: /develop[/]items[/]custom-item-groups$/,
    dest: "develop/items/custom-creative-tabs",
  },
  {
    from: /develop[/]rendering[/]draw-context$/,
    dest: "develop/rendering/gui-graphics",
  },
] as { from: RegExp; dest: string }[];
