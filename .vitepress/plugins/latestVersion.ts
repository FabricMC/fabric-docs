import * as fs from "node:fs";
import * as path from "node:path";
import { Plugin } from "vitepress";

export default (): Plugin => {
  const name = "fabric-docs:latest-version";
  const id = `virtual:${name}`;
  const resolvedId = `\0${id}`;

  const file = path.resolve(import.meta.dirname, "..", "..", "reference", "latest", "build.gradle");

  return {
    name,
    enforce: "pre",

    resolveId: {
      filter: { id: new RegExp(`^${id}$`) },
      handler() {
        return resolvedId;
      },
    },

    load: {
      filter: { id: new RegExp(`^${resolvedId}$`) },
      handler() {
        this.addWatchFile(file);

        const latestVersion = fs
          .readFileSync(file, "utf-8")
          .match(/def minecraftVersion = "([^"]+)"/)![1];

        return `export default ${JSON.stringify(latestVersion)};`;
      },
    },
  };
};
