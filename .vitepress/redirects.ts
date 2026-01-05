// when `from` is a string, it must be an exact match
// when `from` is a RegExp, `dest` can use $<n> for capture groups
type RedirectRule = { from: string | RegExp; dest: string };

export default [
  {
    from: "develop/items/custom-item-groups",
    dest: "develop/items/custom-creative-tabs",
  },
  {
    from: "versions/1.21.10/develop/items/custom-item-groups",
    dest: "versions/1.21.10/develop/items/custom-creative-tabs",
  },
] as RedirectRule[];
