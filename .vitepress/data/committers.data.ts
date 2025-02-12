import { Octokit } from "octokit";
import { DefaultTheme } from "vitepress";

const GITHUB_TOKEN = process.env.GITHUB_TOKEN || "";
const octokit = new Octokit({ auth: GITHUB_TOKEN });

export default {
  async load() {
    const contributors = await (async () => {
      try {
        return await octokit.paginate(octokit.rest.repos.listContributors, {
          owner: "FabricMC",
          repo: "fabric-docs",
        });
      } catch (e) {
        // Allows build without internet
        return [];
      }
    })();
    const { data } = await (async () => {
      try {
        return await octokit.rest.users.getByUsername({
          username: "FabricMCBot",
        });
      } catch (e) {
        // Allows build without internet
        return { data: { avatar_url: "" } };
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
            avatar: contributor.avatar_url || data.avatar_url,
            links: [{ icon: "github", link: contributor.html_url }],
            name: contributor.name || contributor.login,
            number: contributor.contributions,
          } as DefaultTheme.TeamMember & { number: number })
      )
      .sort((a, b) => a.name.localeCompare(b.name))
      .sort((a, b) => b.number - a.number);
  },
};
