import { Octokit } from "octokit";
import { DefaultTheme } from "vitepress";

const GITHUB_TOKEN = process.env.GITHUB_TOKEN || "";
const octokit = new Octokit({ auth: GITHUB_TOKEN });

export default {
  async load() {
    const members = await (async () => {
      try {
        return await octokit.paginate(octokit.rest.teams.listMembersInOrg, {
          org: "FabricMC",
          team_slug: "documentation",
        });
      } catch (e) {
        // Allows build without a GITHUB_TOKEN
        return [];
      }
    })();

    return members
      .filter((contributor) => contributor.login !== "FabricMCBot")
      .filter(async (contributor) =>
        (
          await octokit.paginate(octokit.rest.orgs.listForUser, {
            username: contributor.login,
          })
        )
          .map((org) => org.login)
          .includes("FabricMC")
      )
      .map(
        (member) =>
          ({
            avatar: member.avatar_url,
            links: [{ icon: "github", link: member.html_url }],
            name: member.name || member.login,
          } as DefaultTheme.TeamMember)
      )
      .sort((a, b) => a.name.localeCompare(b.name));
  },
};
