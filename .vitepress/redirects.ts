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
] satisfies { from: RegExp; dest: string }[];
