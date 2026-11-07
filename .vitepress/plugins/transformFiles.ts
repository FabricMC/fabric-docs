import matter from "gray-matter";
import * as path from "node:path";
import type { Plugin } from "vitepress";
import { getWebsiteResolver } from "../config/i18n.ts";
import { AT, LATEST_VERSION, OLD_VERSIONS, VERSION_RE } from "../constants.ts";

const FILE_PATH_RE = /(?:^<<< *([^[{#\n]+))|(?:^@\[[^\]]*\]\(([^)]*)\))/gm;

export const transformFile = (src: string, id: string) => {
  const { data, content } = matter(src, {});
  const relativePath = path.relative(AT, id);
  const split = relativePath.split("/");

  if (split[0] === "versions") {
    // versions/version/[translated/locale/]path/to/file-name.md
    data.version = split[1];
    data.editLink = false;
    data.search = false;

    split.splice(0, 2);
  }

  // [translated/locale/][version/]path/to/file-name.md
  const locale = split[0] === "translated" ? split[1] : "en_us";
  if (locale !== "en_us") {
    split.splice(0, 2);
  }

  if (VERSION_RE.test(split[0])) {
    data.version = split[0];
    split.splice(0, 1);
  }

  data.version ||= LATEST_VERSION;

  data.purePath = split.join("/").replace(/((?<=^|[/])index)?[.]md$/, "");
  if (/[^/a-z-0-9.]/.test(data.purePath)) {
    throw new Error(`invalid file path: '${relativePath}'`);
  }

  const versionType =
    data.version === LATEST_VERSION
      ? "latest"
      : OLD_VERSIONS.includes(data.version)
        ? "old"
        : "future";
  const resolver = getWebsiteResolver(locale);
  const newContent: string[] = [];

  if (data.layout === "home") {
    if (versionType === "old") {
      newContent.push(
        "::: warning",
        resolver("version.reminder.old_version_home").replace("%s", data.version),
        ":::"
      );
    }
  } else {
    if (data.title) {
      newContent.push("<hgroup>");

      const type = versionType === "latest" ? "tip" : "warning";
      newContent.push(`# ${data.title} <Badge type="${type}">${data.version}</Badge> {#h1}`);

      if (data.description) {
        newContent.push(`${data.description} {role="doc-subtitle"} `);
      }

      newContent.push("</hgroup>");
    }

    if (versionType === "old") {
      newContent.push(
        "::: warning",
        resolver("version.reminder.old_version").replace("%s", data.version),
        ":::"
      );
    }

    if (versionType === "future") {
      newContent.push(
        "::: warning",
        resolver("version.reminder.future_version").replace("%s", data.version),
        ":::"
      );
    }
  }

  newContent.push(content);

  if (data.filesExclude === true) {
    data.files = [];
  } else {
    // Find files referenced in the page
    const matches = [...content.matchAll(FILE_PATH_RE)].map((m) => (m[1] || m[2]).trim());

    matches.push(...(data.files || []));

    data.files = [...new Set(matches)].filter((f) => !(data.filesExclude || []).includes(f));
  }

  return matter.stringify(newContent.join("\n\n"), data);
};

export const transformFilesPlugin = (): Plugin => ({
  name: "fabric-docs:transform-files",
  enforce: "pre",

  transform: {
    filter: { id: /[.]md$/ },
    handler(src, id) {
      this.addWatchFile(id);

      return { code: transformFile(src, id) };
    },
  },
});
