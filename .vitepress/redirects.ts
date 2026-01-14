export default [
  {
    from: /^(.*)((?<=^|[/])index)?\.html$/,
    dest: "$1",
  },
  {
    from: /develop[/]items[/]custom-item-groups$/,
    dest: "develop/items/custom-creative-tabs",
  },
] as { from: RegExp; dest: string }[];
