import matter from "gray-matter";
import { Plugin } from "vitepress";

export default (): Plugin => {
  const name = "fabric-docs:transform-files";

  return {
    name,
    enforce: "pre",

    transform: {
      filter: { id: /\.md$/ },
      handler(src, id) {
        this.addWatchFile(id);

        let { data, content } = matter(src);

        // Add heading from frontmatter title
        if (data.title && data.layout !== "home") {
          content = `# ${data.title} {#h1}\n\n${content}`;
        }

        // Find files referenced in the page
        const filePathRegex = /(?:^<<< *([^[{#\n]+))|(?:^@\[[^\]]*\]\(([^)]*)\))/gm;
        const matches = [...src.matchAll(filePathRegex)].map((m) => (m[1] ?? m[2]).trim());

        matches.push(...(data.files ?? []));

        data.files = [...new Set(matches)];

        return {
          code: matter.stringify(content, data),
        };
      },
    },
  };
};
