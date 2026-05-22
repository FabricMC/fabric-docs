import matter from "gray-matter";
import * as path from "node:path";
import { Plugin, SiteConfig } from "vitepress";
import { Fabric } from "../types";

export const transformFile = (src: string, id: string, latestVersion: string) => {
  let { data, content } = matter(src);
  const newContent: string[] = [];

  // Version information
  const split = path.relative(path.resolve(import.meta.dirname, "..", ".."), id).split("/");
  if (split[0] === "versions") {
    data.versionType = "old";
    data.version = split[1];
  } else if (/^[0-9.]+$/.test(split[0])) {
    data.versionType = "future";
    data.version = split[0];
  } else if (split[0] === "translated" && /^[0-9.]+$/.test(split[2])) {
    data.versionType = "future";
    data.version = split[2];
  } else {
    data.versionType = "latest";
    data.version = latestVersion;
  }

  if (split[0] === "translated") {
    data.localeIndex = split[1];
  } else if (data.versionType === "old" && split[2] === "translated") {
    data.localeIndex = split[3];
  } else {
    data.localeIndex = "root";
  }

  const config = (globalThis as any).VITEPRESS_CONFIG as SiteConfig;
  const themeConfig = (
    config.userConfig.locales![data.localeIndex] ?? config.userConfig.locales!.root
  ).themeConfig as Fabric.ThemeConfig;

  if (data.layout === "home") {
    if (data.versionType === "old") {
      newContent.push(
        "::: warning",
        themeConfig.version.reminder.oldVersionHome.replace("%s", data.version),
        ":::"
      );
    }
  } else {
    if (data.title) {
      newContent.push("<hgroup>");

      const type = data.versionType === "latest" ? "tip" : "warning";
      newContent.push(`# ${data.title} <Badge type="${type}">${data.version}</Badge> {#h1}`);

      if (data.description) {
        newContent.push(`${data.description} {role="doc-subtitle"} `);
      }

      newContent.push("</hgroup>");
    }

    if (data.versionType === "old") {
      newContent.push(
        "::: warning",
        themeConfig.version.reminder.oldVersion.replace("%s", data.version),
        ":::"
      );
    }

    if (data.versionType === "future") {
      newContent.push(
        "::: warning",
        themeConfig.version.reminder.futureVersion.replace("%s", data.version),
        ":::"
      );
    }
  }

  newContent.push(content);
  content = newContent.join("\n\n");

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

export const transformFilesPlugin = (latestVersion: string): Plugin => ({
  name: "fabric-docs:transform-files",
  enforce: "pre",

  transform: {
    filter: { id: /[.]md$/ },
    handler(src, id) {
      this.addWatchFile(id);
      return { code: transformFile(src, id, latestVersion) };
    },
  },
});
