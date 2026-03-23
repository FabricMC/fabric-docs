import matter from "gray-matter";
import { Plugin } from "vitepress";

export default (): Plugin => {
  const name = "fabric-docs:transform-files";

  return {
    name,
    enforce: "pre",

    transform: {
      filter: { id: /\.md$/ },
      handler(src) {
        let { data, content } = matter(src);

        // Add heading from frontmatter title
        if (data.title && data.layout !== "home") {
          content = `# ${data.title} {#h1}\n\n${content}`;
        }

        // Find files referenced in the page
        const matches = [
          ...src.matchAll(/^<<< *([^{#]+)/gm),
          ...src.matchAll(/^@\[[^\]]*\]\(([^)]*)\)/gm),
        ].map((m) => m[1]);

        data.files = [...new Set(matches)];

        return {
          code: matter.stringify(content, data),
        };
      },
    },
  };
};
