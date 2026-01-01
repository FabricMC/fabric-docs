// when `from` is a string, it must be an exact match
// when `from` is a RegExp, `dest` can use $<n> for capture groups
type RedirectRule = { from: string | RegExp; dest: string };

export default [] as RedirectRule[];
