import * as childProcess from "node:child_process";
import * as fs from "node:fs";
import * as process from "node:process";
import * as tinyglobby from "tinyglobby";
import { getSidebar } from "../config/i18n.ts";
import { AT, LOCALES, VERSION_RE } from "../constants.ts";

const git = (...args: string[]) => {
  const returned = childProcess.spawnSync("git", args, { encoding: "utf8" });

  if (returned.error) {
    throw new Error(`Failed to run 'git ${args.join(" ")}'!`, { cause: returned.error });
  }

  return returned;
};

process.chdir(AT);

if (git("status", "--porcelain").stdout.toString().trim().length > 0) {
  throw new Error("Working directory must be clean!");
}

const oldBuildGradle = fs.readFileSync("./reference/latest/build.gradle", "utf-8");
const oldVersion = oldBuildGradle.match(/def minecraftVersion = "([^"]+)"/)![1];
console.log(`Current version: 'Minecraft ${oldVersion}'`);

const launcherMetaUrl = "https://piston-meta.mojang.com/mc/game/version_manifest.json";
const launcherVersions: any = await (await fetch(launcherMetaUrl)).json();
const newVersion = process.argv[2]?.replace(/[.]0$/, "") || launcherVersions?.latest?.release;
if (!newVersion) {
  throw new Error("Couldn't obtain a valid Minecraft version!");
}
if (newVersion === oldVersion || fs.existsSync(`./reference/${newVersion}`)) {
  throw new Error(`'Minecraft ${newVersion}' already exists!`);
}
if (!VERSION_RE.test(newVersion)) {
  throw new Error(`'${newVersion}' does not look like a stable Minecraft version!`);
}
console.log(`New version: 'Minecraft ${newVersion}'`);

if (git("rev-parse", "--verify", `refs/heads/port/${newVersion}`).status === 0) {
  throw new Error(`Branch 'port/${newVersion}' already exists!`);
}

console.log(`Switching to new branch 'port/${newVersion}'...`);
if (git("switch", "-c", `port/${newVersion}`).status !== 0) {
  throw new Error(`Couldn't switch to branch 'port/${newVersion}'!`);
}

const fabricApiUrl = `https://api.modrinth.com/v2/project/fabric-api/version?loaders=["fabric"]&game_versions=["${newVersion}"]&featured=true`;
const fabricApiVersions: any[] = await (await fetch(fabricApiUrl)).json();
const fabricApiVersion = fabricApiVersions[0]?.version_number;
if (!fabricApiVersion) {
  throw new Error(`No Fabric API version found for Minecraft ${newVersion}!`);
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
  ignore: [
    "README.md",
    "contributing.md",
    "versions/**/*.md",
    "+([0-9.])/**/*.md",
    "node_modules/**/*",
  ],
  onlyFiles: true,
})) {
  fs.cpSync(`./${file}`, `./versions/${oldVersion}/${file}`);
}

if (fs.existsSync(`./${newVersion}/`)) {
  console.log(`Moving in files from '${newVersion}/'...`);
  for (const file of tinyglobby.globSync("**/*", { cwd: `./${newVersion}`, onlyFiles: true })) {
    fs.cpSync(`./${newVersion}/${file}`, `./${file}`);
  }
  fs.rmSync(`./${newVersion}`, { recursive: true });
}

console.log(`Creating sidebars at '.vitepress/sidebars/versioned/${oldVersion}.json'...`);
for (const locale of LOCALES) {
  fs.writeFileSync(
    `./.vitepress/sidebars/versioned/${oldVersion}${locale === "en_us" ? "" : `-${locale}`}.json`,
    JSON.stringify(getSidebar(locale), null, 2)
  );
}

console.log("Updating links in content...");
for (const file of tinyglobby.globSync(`./versions/${oldVersion}/**/*.md`, { onlyFiles: true })) {
  const content = fs
    .readFileSync(file, "utf-8")
    .replaceAll(/[/]reference[/]latest/g, `/reference/${oldVersion}`)
    .replaceAll("./contributing", "./../contributing");
  fs.writeFileSync(file, content);
}

console.log("Updating version redirects...");
const toSemVer = (v: string) => (v.split(".").length === 2 ? `${v}.0` : v);
const releaseVersions = (launcherVersions.versions as any[])
  .filter((v) => v.type === "release")
  .map<string>((v) => v.id);
const newIndex = releaseVersions.findIndex((v) => v === newVersion);
const oldIndex = releaseVersions.findIndex((v) => v === oldVersion);
const newHeadContent = fs
  .readFileSync("./.vitepress/config/head.ts", "utf-8")
  .replace(
    "const versionRedirects: Record<string, string> = {",
    [
      "$&",
      ...(newIndex < oldIndex ? releaseVersions.slice(newIndex, oldIndex) : [newVersion]).map(
        (v) => `    "${toSemVer(v)}": "${toSemVer(newVersion)}",`
      ),
      "",
    ].join("\n")
  );
fs.writeFileSync("./.vitepress/config/head.ts", newHeadContent);

console.log(`Committing 'chore: bump to ${newVersion}'...`);
if (
  git("add", ".").status !== 0
  || git("commit", "-m", `chore: bump to ${newVersion}`).status !== 0
) {
  throw new Error(`Couldn't commit as 'chore: bump to ${newVersion}'!`);
}

console.log("DONE! Please complete the version bump manually");
