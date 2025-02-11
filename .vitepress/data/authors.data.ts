import { Octokit } from "octokit";
import { createContentLoader, DefaultTheme } from "vitepress";

const GITHUB_TOKEN = process.env.GITHUB_TOKEN || "";
const octokit = new Octokit({ auth: GITHUB_TOKEN });

export default {
  async load() {
    const authors = new Map<string, { files: number; login: string }>();
    const authorsNoGitHub = new Map<string, { files: number; login: string }>();

    const markdownData = await createContentLoader(["**/*.md"], {
      globOptions: {
        ignore: ["node_modules/**/*", "translated/**/*", "versions/**/*"],
      },
    }).load();

    markdownData.forEach(async (file) => {
      file.frontmatter["authors"]?.forEach((login: string) => {
        const author = authors.get(login) || { login, files: 0 };
        author.files++;
        authors.set(login, author);
      });

      file.frontmatter["authors-nogithub"]?.forEach((login: string) => {
        const author = authorsNoGitHub.get(login) || { login, files: 0 };
        author.files++;
        authorsNoGitHub.set(login, author);
      });
    });

    const authorsArray = await Promise.all(
      Array.from(authors.values()).map(async (author) => {
        try {
          const { data } = await octokit.rest.users.getByUsername({
            username: author.login,
          });

          return {
            avatar: data.avatar_url,
            links: [{ icon: "github", link: data.html_url }],
            name: data.name || data.login,
            number: author.files,
          } as DefaultTheme.TeamMember & { number: number };
        } catch (error) {
          return null;
        }
      })
    );

    const { data } = await octokit.rest.users.getByUsername({
      username: "FabricMCBot",
    });
    const authorsNoGitHubArray = Array.from(authorsNoGitHub.values()).map(
      (author) =>
        ({
          avatar: data.avatar_url,
          name: author.login,
          number: author.files,
        } as DefaultTheme.TeamMember & { number: number })
    );

    return [
      ...authorsArray.filter((author) => !!author),
      ...authorsNoGitHubArray,
    ]
      .sort((a, b) => a.name.localeCompare(b.name))
      .sort((a, b) => b.number - a.number);
  },
};
