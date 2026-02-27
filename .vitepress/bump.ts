import * as crossSpawn from "cross-spawn";
import * as fs from "node:fs";
import * as path from "node:path";
import * as process from "node:process";
import * as tinyglobby from "tinyglobby";

import { getLocaleNames, getResolver, getSidebar } from "./config/i18n";

const git = (...args: string[]) => {
  const res = crossSpawn.sync("git", args, { encoding: "utf8" });
  if (res.error) {
    console.error(`Failed to run 'git ${args.join(" ")}'!\n  ${res.error}`);
    process.exit(1);
  }
  return res;
};

process.chdir(path.resolve(import.meta.dirname, ".."));

if (git("status", "--porcelain").stdout.toString().trim().length > 0) {
  console.error("Working directory must be clean!");
  process.exit(1);
}

const oldBuildGradle = fs.readFileSync("./reference/latest/build.gradle", "utf-8");
const oldVersion = oldBuildGradle.match(/def minecraftVersion = "([^"]+)"/)![1];
console.log(`Current version: 'Minecraft ${oldVersion}'`);

const launcherMetaUrl = "https://piston-meta.mojang.com/mc/game/version_manifest.json";
const launcherVersions: any = await (await fetch(launcherMetaUrl)).json();
const newVersion = process.argv[2] || launcherVersions?.latest?.release;
if (!newVersion) {
  console.error("Couldn't obtain a valid Minecraft version!");
  process.exit(1);
} else if (newVersion === oldVersion || fs.existsSync(`./reference/${newVersion}`)) {
  console.error(`'Minecraft ${newVersion}' already exists!`);
  process.exit(1);
} else if (!/^[0-9]+\.[0-9]+(\.[0-9]+)?(-(snapshot|pre|rc)-[0-9]+)?$/.test(newVersion)) {
  console.error(`'${newVersion}' does not look like a Minecraft version!`);
  process.exit(1);
}
console.log(`New version: 'Minecraft ${newVersion}'`);

if (git("rev-parse", "--verify", `refs/heads/port/${newVersion}`).status === 0) {
  console.error(`Branch 'port/${newVersion}' already exists!`);
  process.exit(1);
}

console.log(`Switching to new branch 'port/${newVersion}'...`);
if (git("switch", "-c", `port/${newVersion}`).status !== 0) {
  console.error(`Couldn't switch to branch 'port/${newVersion}'!`);
  process.exit(1);
}

const fabricApiUrl = `https://api.modrinth.com/v2/project/fabric-api/version?loaders=["fabric"]&game_versions=["${newVersion}"]&featured=true`;
const fabricApiVersions: any[] = await (await fetch(fabricApiUrl)).json();
const fabricApiVersion = fabricApiVersions[0]?.version_number;
if (!fabricApiVersion) {
  console.error(`No Fabric API version found for Minecraft ${newVersion}!`);
  process.exit(1);
}
console.log(`Found Fabric API version '${fabricApiVersion}'`);

console.log(`Migrating ExampleMod to 'reference/${oldVersion}/'...`);
fs.cpSync("./reference/latest", `./reference/${oldVersion}`, { recursive: true });

const newBuildGradle = oldBuildGradle
  .replace(/def minecraftVersion = "([^"]+)"/, `def minecraftVersion = "${newVersion}"`)
  .replace(/def fabricApiVersion = "([^"]+)"/, `def fabricApiVersion = "${fabricApiVersion}"`);
fs.writeFileSync("./reference/latest/build.gradle", newBuildGradle);

console.log(`Migrating content to 'versions/${oldVersion}/'...`);
for (const file of tinyglobby.globSync("**/*.md", {
  ignore: ["README.md", "contributing.md", "versions/**/*.md", "node_modules/**/*"],
  onlyFiles: true,
})) {
  fs.cpSync(`./${file}`, `./versions/${oldVersion}/${file}`);
}
const locales = getLocaleNames(`./versions/${oldVersion}/translated`);

console.log(`Creating sidebars at '.vitepress/sidebars/versioned/${oldVersion}.json'...`);
for (const locale of locales) {
  fs.writeFileSync(
    `./.vitepress/sidebars/versioned/${oldVersion}${locale === "en_us" ? "" : `-${locale}`}.json`,
    JSON.stringify(getSidebar(locale), null, 2)
  );
}

console.log("Updating links in content...");
for (const file of tinyglobby.globSync(`./versions/${oldVersion}/**/*.md`, { onlyFiles: true })) {
  const content = fs
    .readFileSync(file, "utf-8")
    .replace(/\/reference\/latest/g, `/reference/${oldVersion}`);
  fs.writeFileSync(file, content);
}

console.log("Adding warning box to home pages...");
for (const locale of locales) {
  const resolver = getResolver("./website_translations.json", locale);
  const linksRegex = new RegExp(String.raw`link: ${locale === "en_us" ? "" : `/${locale}`}/`, "g");

  const file = `./versions/${oldVersion}/translated/${locale === "en_us" ? ".." : locale}/index.md`;
  const content = fs
    .readFileSync(file, "utf-8")
    .replace(
      /^---\n\n/m,
      [
        "---",
        "",
        "::: warning",
        resolver("version.warning").replace("%s", oldVersion),
        ":::",
        "",
        "",
      ].join("\n")
    )
    .replace(linksRegex, (m) => `${m}${oldVersion}/`);
  fs.writeFileSync(file, content);
}

console.log(`Committing 'chore: bump to ${newVersion}'...`);
if (
  git("add", ".").status !== 0
  || git("commit", "-m", `chore: bump to ${newVersion}`).status !== 0
) {
  console.error(`Couldn't commit as 'chore: bump to ${newVersion}'!`);
  process.exit(1);
}

console.log("DONE! Please complete the version bump manually");
