import { Octokit } from "octokit";
import { DefaultTheme } from "vitepress";

const GITHUB_TOKEN = process.env.GITHUB_TOKEN || "";
const octokit = new Octokit({ auth: GITHUB_TOKEN });

export default {
  async load() {
    const contributors = await (async () => {
      try {
        if (!GITHUB_TOKEN) throw new Error("GITHUB_TOKEN is unset");
        return await octokit.paginate(octokit.rest.repos.listContributors, {
          owner: "FabricMC",
          repo: "fabric-docs",
        });
      } catch (error) {
        console.error(error);
        return [];
      }
    })();

    return contributors
      .filter(
        (contributor) =>
          contributor.login && contributor.login !== "FabricMCBot"
      )
      .map(
        (contributor) =>
          ({
            avatar: contributor.avatar_url,
            links: [{ icon: "github", link: contributor.html_url }],
            name: contributor.login,
            number: contributor.contributions,
          } as DefaultTheme.TeamMember & { number: number })
      )
      .sort((a, b) => a.name.localeCompare(b.name))
      .sort((a, b) => b.number - a.number);
  },
};
