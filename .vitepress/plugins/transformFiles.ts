import matter from "gray-matter";
import { Plugin } from "vitepress";

export const transformFile = (src: string) => {
  let { data, content } = matter(src);

  // Add heading from frontmatter title
  if (data.title && data.layout !== "home") {
    content = `# ${data.title} {#h1}\n\n${content}`;
  }

  if (data.filesExclude === true) {
    data.files = [];
  } else {
    // Find files referenced in the page
    const filePathRegex = /(?:^<<< *([^[{#\n]+))|(?:^@\[[^\]]*\]\(([^)]*)\))/gm;
    const matches = [...src.matchAll(filePathRegex)].map((m) => (m[1] ?? m[2]).trim());

    matches.push(...(data.files ?? []));

    data.files = [...new Set(matches)].filter((f) => !(data.filesExclude ?? []).includes(f));
  }

  return matter.stringify(content, data);
};

export const transformFilesPlugin: Plugin = {
  name: "fabric-docs:transform-files",
  enforce: "pre",

  transform: {
    filter: { id: /\.md$/ },
    handler(src, id) {
      this.addWatchFile(id);
      return { code: transformFile(src) };
    },
  },
};
