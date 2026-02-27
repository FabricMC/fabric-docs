export default [
  {
    from: /^(.*)((?<=^|[/])index)?\.html$/,
    dest: "$1",
  },
] as { from: RegExp; dest: string }[];
