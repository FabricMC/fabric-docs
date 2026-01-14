import { Fabric } from "./types";

export default [
  {
    from: /^(.*)((?<=^|\/)index)?\.html$/,
    dest: "$1",
  },
  {
    from: "develop/items/custom-item-groups",
    dest: "develop/items/custom-creative-tabs",
    appliesTo: ">=1.21.10",
  },
  {
    from: "develop/items/custom-creative-tabs",
    dest: "develop/items/custom-item-groups",
    appliesTo: "<1.21.10",
  },
] satisfies Fabric.RedirectRules;
