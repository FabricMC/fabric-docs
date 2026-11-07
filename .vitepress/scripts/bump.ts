import * as childProcess from "node:child_process";
import * as fs from "node:fs";
import * as path from "node:path";
import * as process from "node:process";
import * as tinyglobby from "tinyglobby";

const git = (...args: string[]) => {
  const returned = childProcess.spawnSync("git", args, { encoding: "utf-8" });

  if (returned.error) {
    throw new Error(`Couldn't run 'git ${args.join(" ")}'!`, { cause: returned.error });
  }

  return returned;
};

process.chdir(path.join(import.meta.dirname, "..", ".."));

if (git("status", "--porcelain").stdout.trim().length > 0) {
  throw new Error("Working directory must be clean!");
}

if (git("rev-parse", "--verify", "refs/heads/main").status !== 0) {
  throw new Error("Couldn't find branch 'main'!");
}

const getMinecraftVersion = (revision: string, directory: string) =>
  git("show", `${revision}:reference/${directory}/build.gradle`).stdout.match(
    /def minecraftVersion = "([^"]+)"/
  )?.[1];

const oldVersion = getMinecraftVersion("main", "latest");
if (!oldVersion) {
  throw new Error("Couldn't obtain current version!");
}
console.log(`Current version: 'Minecraft ${oldVersion}'`);

const launcherMetaUrl = "https://piston-meta.mojang.com/mc/game/version_manifest.json";
const releaseVersions = ((await (await fetch(launcherMetaUrl)).json()).versions as any[])
  .filter((v) => v.type === "release")
  .map<string>((v) => v.id);
const newVersion = process.argv[2]?.replace(/[.]0$/, "") || releaseVersions[0];
if (!releaseVersions.includes(newVersion)) {
  throw new Error(`'${newVersion}' is not a stable Minecraft release!`);
}
if (oldVersion === newVersion || getMinecraftVersion("main", newVersion)) {
  throw new Error(`'Minecraft ${newVersion}' already exists!`);
}
console.log(`New version: 'Minecraft ${newVersion}'`);
const newBranch = `port/${newVersion}`;
const newSubject = `chore: bump to ${newVersion}`;

const fabricApiUrl = `https://api.modrinth.com/v2/project/fabric-api/version?loaders=["fabric"]&game_versions=["${newVersion}"]&featured=true`;
const fabricApiVersions: any[] = await (await fetch(fabricApiUrl)).json();
const fabricApiVersion = fabricApiVersions[0]?.version_number;
if (!fabricApiVersion) {
  throw new Error(`No Fabric API version found for Minecraft ${newVersion}!`);
}
console.log(`Found Fabric API version '${fabricApiVersion}'`);

let oldBumpHash = "";
let oldHeadHash = "";
if (git("branch", "--show-current").stdout.trim() === newBranch) {
  oldHeadHash = git("rev-parse", "--verify", "HEAD").stdout.trim();

  const oldCommitData = git("log", "--reverse", "--format=%H%x00%s", "main..HEAD")
    .stdout.trim()
    .split("\n")[0]
    .split("\0") as [string, string];

  oldBumpHash = oldCommitData[0];
  if (oldBumpHash) {
    console.log(`Preparing to refresh '${newBranch}' (${oldHeadHash})...`);

    if (oldCommitData[1] !== newSubject) {
      throw new Error(`Couldn't find commit '${newSubject}'!`);
    }

    const oldVersionBeforeBump = getMinecraftVersion(`${oldBumpHash}^`, oldVersion);
    const newVersionBeforeBump = getMinecraftVersion(`${oldBumpHash}^`, "latest");
    const oldVersionAfterBump = getMinecraftVersion(oldBumpHash, oldVersion);
    const newVersionAfterBump = getMinecraftVersion(oldBumpHash, "latest");

    if (
      oldVersionBeforeBump !== undefined
      || newVersionBeforeBump !== oldVersion
      || oldVersionAfterBump !== oldVersion
      || newVersionAfterBump !== newVersion
    ) {
      throw new Error(`Malformed commit '${newSubject}' (${oldBumpHash})!`);
    }
  }
} else if (git("rev-parse", "--verify", `refs/heads/${newBranch}`).status === 0) {
  throw new Error(`Branch '${newBranch}' already exists! Switch to it and bump to refresh.`);
}

console.log(`Creating branch '${newBranch}' from 'main'...`);
if (git("switch", "--force-create", newBranch, "main").status !== 0) {
  throw new Error(`Couldn't create branch '${newBranch}'!`);
}

// Must be imported after the branch was created
const { LOCALES } = await import("../constants.ts");
const { getSidebar } = await import("../config/i18n.ts");

console.log(`Migrating ExampleMod to 'reference/${oldVersion}/'...`);
fs.cpSync("./reference/latest", `./reference/${oldVersion}`, { recursive: true });

const oldBuildGradle = fs.readFileSync("./reference/latest/build.gradle", "utf-8");
const newBuildGradle = oldBuildGradle
  .replace(/def minecraftVersion = "[^"]+"/, `def minecraftVersion = "${newVersion}"`)
  .replace(/def fabricApiVersion = "[^"]+"/, `def fabricApiVersion = "${fabricApiVersion}"`);
fs.writeFileSync("./reference/latest/build.gradle", newBuildGradle);

const oldFMJ = fs.readFileSync("./reference/latest/src/main/resources/fabric.mod.json", "utf-8");
const newFMJ = oldFMJ
  .replace(/"minecraft": "[^"]+"/, `"minecraft": "${newVersion}"`)
  .replace(/"fabric-api": "[^"]+"/, `"fabric-api": "${fabricApiVersion}"`);
fs.writeFileSync("./reference/latest/src/main/resources/fabric.mod.json", newFMJ);

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
for (const l of LOCALES) {
  fs.writeFileSync(
    `./.vitepress/sidebars/versioned/${oldVersion}${l === "en_us" ? "" : `-${l}`}.json`,
    JSON.stringify(getSidebar(l), null, 2)
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
const newIndex = releaseVersions.findIndex((v) => v === newVersion);
const oldIndex = releaseVersions.findIndex((v) => v === oldVersion);
const intermediateVersions = releaseVersions.slice(newIndex, Math.max(oldIndex, newIndex + 1));
const newHeadContent = fs
  .readFileSync("./.vitepress/config/head.ts", "utf-8")
  .replace(
    "const versionRedirects: Record<string, string> = {",
    [
      "$&",
      ...intermediateVersions.map((v) => `    "${toSemVer(v)}": "${toSemVer(newVersion)}",`),
      "",
    ].join("\n")
  );
fs.writeFileSync("./.vitepress/config/head.ts", newHeadContent);

console.log(`Committing '${newSubject}'...`);
if (git("add", ".").status !== 0 || git("commit", `--message=${newSubject}`).status !== 0) {
  throw new Error(`Couldn't commit '${newSubject}'!`);
}

console.log("DONE!");
if (oldBumpHash && oldBumpHash !== oldHeadHash) {
  console.log("Please run the following command:");
  console.log(`$ git cherry-pick ${oldBumpHash}..${oldHeadHash}`);
}
