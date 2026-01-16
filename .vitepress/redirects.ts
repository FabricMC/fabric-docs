import { Fabric } from "./types";

export default [
  {
    from: /^(.*)((?<=^|\/)index)?\.html$/,
    dest: "$1",
  },
] satisfies Fabric.RedirectRules;
