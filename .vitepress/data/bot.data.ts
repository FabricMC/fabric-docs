import { Octokit } from "octokit";
import { DefaultTheme } from "vitepress";

const LOGIN = "FabricMCBot";
const GITHUB_TOKEN = process.env.GITHUB_TOKEN || "";
const octokit = new Octokit({ auth: GITHUB_TOKEN });

export default {
  async load() {
    const { data } = await (async () => {
      try {
        return await octokit.rest.users.getByUsername({
          username: LOGIN,
        });
      } catch (error) {
        console.error("Error: ", error);
        return { data: { avatar_url: `https://github.com/${LOGIN}.png` } };
      }
    })();

    return {
      avatar: data.avatar_url,
      name: LOGIN,
    } as DefaultTheme.TeamMember;
  },
};
