export default [
  {
    from: /((?<=^|[/])index)?[.]html$/,
    dest: "",
  },
] satisfies { from: RegExp; dest: string }[];
