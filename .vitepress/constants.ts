import * as fs from "node:fs";
import * as path from "node:path";
import * as process from "node:process";

export const AT = path.join(import.meta.dirname, "..");

// https://docs.github.com/en/actions/reference/workflows-and-actions/variables#default-environment-variables
// https://docs.netlify.com/build/configure-builds/environment-variables/#read-only-variables
export const ENV = process.env.GITHUB_ACTIONS
  ? "github"
  : process.env.NETLIFY
    ? Number(process.env.REVIEW_ID)
    : process.env.NODE_ENV === "production"
      ? "build"
      : "dev";

export const VERSION_RE = /^[0-9]+[.][0-9]+([.][0-9]+)?$/;

export const LATEST_VERSION =
  fs
    .readFileSync(path.join(AT, "reference", "latest", "build.gradle"), "utf-8")
    .match(/def minecraftVersion = "([^"]+)"/)?.[1] || "";
