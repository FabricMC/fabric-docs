import * as fs from "node:fs";
import * as path from "node:path/posix";
import * as process from "node:process";
import prompts from "prompts";
import * as tinyglobby from "tinyglobby";

import { getLocaleNames, getResolver, getSidebar } from "./i18n";

process.chdir(path.resolve(__dirname, ".."));
const oldBuildGradle = fs.readFileSync("./reference/latest/build.gradle", "utf-8");
const oldVersion = oldBuildGradle.match(/def minecraftVersion = "([^"]+)"/)![1];
console.log(`Current version: 'Minecraft ${oldVersion}'`);

const newVersion: string = (
  await prompts({
    type: "text",
    name: "version",
    message: "Enter the new Minecraft version",
    validate: (newVersion) => {
      if (newVersion === oldVersion) return `Already on Minecraft ${newVersion}!`;
      if (!/^[0-9.]+$/.test(newVersion)) return "Version must match /^[0-9.]+$/";
      return true;
    },
  })
).version;
if (!newVersion) process.exit(1);
console.log(`New version: 'Minecraft ${newVersion}'`);

const yarnUrl = `https://meta.fabricmc.net/v2/versions/yarn/${newVersion}`;
const yarnVersions: any[] = await (await fetch(yarnUrl)).json();
const yarnVersion: string = (yarnVersions.find((v) => v.stable) ?? yarnVersions[0])?.version;
if (!yarnVersion) {
  console.error(`No stable Yarn version found for Minecraft ${newVersion}`);
  process.exit(1);
}
console.log(`Found Yarn version '${yarnVersion}'`);

const fabricApiUrl = `https://api.modrinth.com/v2/project/fabric-api/version?loaders=["fabric"]&game_versions=["${newVersion}"]&featured=true`;
const fabricApiVersions: any[] = await (await fetch(fabricApiUrl)).json();
const fabricApiVersion = fabricApiVersions[0]?.version_number;
if (!fabricApiVersion) {
  console.error(`No Fabric API version found for Minecraft ${newVersion}`);
  process.exit(1);
}
console.log(`Found Fabric API version '${fabricApiVersion}'`);

console.log(`Migrating ExampleMod to 'reference/${oldVersion}/'...`);
fs.cpSync("./reference/latest", `./reference/${oldVersion}`, { recursive: true });

const newBuildGradle = oldBuildGradle
  .replace(/def minecraftVersion = "([^"]+)"/, `def minecraftVersion = "${newVersion}"`)
  .replace(/def yarnVersion = "([^"]+)"/, `def yarnVersion = "${yarnVersion}"`)
  .replace(/def fabricApiVersion = "([^"]+)"/, `def fabricApiVersion = "${fabricApiVersion}"`);
fs.writeFileSync("./reference/latest/build.gradle", newBuildGradle);

console.log(`Migrating content to 'versions/${oldVersion}/'...`);
for (const file of tinyglobby.globSync("**/*.md", {
  ignore: ["README.md", "contributing.md", "versions/**/*.md", "node_modules/**/*"],
  onlyFiles: true,
})) {
  fs.cpSync(`./${file}`, `./versions/${oldVersion}/${file}`);
}
const locales = getLocaleNames(`versions/${oldVersion}/translated`);

console.log(`Creating sidebars at '.vitepress/sidebars/versioned/${oldVersion}.json'...`);
for (const locale of locales) {
  fs.writeFileSync(
    `./.vitepress/sidebars/versioned/${oldVersion}${locale === "en_us" ? "" : `-${locale}`}.json`,
    JSON.stringify(getSidebar(locale), null, 2)
  );
}

console.log("Updating links in content...");
for (const file of tinyglobby.globSync(`versions/${oldVersion}/**/*.md`, { onlyFiles: true })) {
  const content = fs
    .readFileSync(file, "utf-8")
    .replace(/\/reference\/latest/g, `/reference/${oldVersion}`);
  fs.writeFileSync(file, content);
}

console.log("Adding warning box to home pages...");
for (const locale of locales) {
  const resolver = getResolver("website_translations.json", locale);
  const linksRegex = new RegExp(String.raw`link: ${locale === "en_us" ? "" : `/${locale}`}/`, "g");

  const file = `versions/${oldVersion}/translated/${locale === "en_us" ? ".." : locale}/index.md`;
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

console.log("DONE! Make sure everything's good before committing.");
