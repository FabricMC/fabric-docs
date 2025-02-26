import {
  default as Client,
  default as crowdin,
} from "@crowdin/crowdin-api-client";
import { DefaultTheme } from "vitepress";

const CROWDIN_TOKEN = process.env.CROWDIN_TOKEN || "";
const PROJECT_ID = 647524;
const { sourceFilesApi, reportsApi } = (() => {
  try {
    //@ts-expect-error https://github.com/crowdin/crowdin-api-client-js/issues/98
    return new crowdin.default({ token: CROWDIN_TOKEN }) as Client;
  } catch (error) {
    return { sourceFilesApi: {}, reportsApi: {} } as Client;
  }
})();

export default {
  async load() {
    if (!CROWDIN_TOKEN) return [];
    const { data: directories } = await sourceFilesApi.listProjectDirectories(
      PROJECT_ID,
      {
        filter: "fabric-docs",
      }
    );

    const { data: files } = await sourceFilesApi
      .withFetchAll()
      .listProjectFiles(PROJECT_ID, {
        directoryId: directories[0].data.directoryId,
        filter: "**",
      });

    let report: any[] = [];
    try {
      const identifier = (
        await reportsApi.generateReport(PROJECT_ID, {
          name: "contribution-raw-data",
          schema: {
            unit: "words",
            mode: "translations",
            fileIds: files.map((f) => f.data.id),
          },
        })
      ).data.identifier;

      while (
        (await reportsApi.checkReportStatus(PROJECT_ID, identifier)).data
          .status !== "finished"
      ) {
        setTimeout(() => {}, 2000);
      }

      report = (
        await (
          await fetch(
            (
              await reportsApi.downloadReport(PROJECT_ID, identifier)
            ).data.url
          )
        ).json()
      ).data;
    } catch (error) {
      console.error(error);
    }

    const translators = new Map<
      string,
      {
        words: number;
        languages: Set<string>;
        avatar: string;
        username: string;
      }
    >();

    report.forEach((entry) => {
      if (entry.user.username === "REMOVED_USER") return;

      const user = translators.get(entry.user.username) || {
        words: 0,
        languages: new Set<string>(),
        avatar: entry.user.avatarUrl,
        username: entry.user.username,
      };
      user.words += entry.translated;
      user.languages.add(entry.language.name);
      translators.set(user.username, user);
    });

    return Array.from(translators.values())
      .map(
        (contributor) =>
          ({
            avatar: contributor.avatar,
            links: [
              {
                icon: "crowdin",
                link: `https://crowdin.com/profile/${contributor.username}`,
              },
            ],
            name: contributor.username,
            number: contributor.words,
          } as DefaultTheme.TeamMember & { number: number })
      )
      .sort((a, b) => a.name.localeCompare(b.name))
      .sort((a, b) => b.number - a.number);
  },
};
