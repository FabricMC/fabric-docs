import prompts from "prompts";
import fs from "node:fs"
import * as glob from "glob"
import players from "./sidebars/players";
import develop from "./sidebars/develop";

(async () => {
  // Determine old minecraft version by reading /reference/latest/build.gradle's `def minecraftVersion = "XXXX"` line.
  const buildGradle = fs.readFileSync("./reference/latest/build.gradle", "utf-8");
  // @ts-ignore
  const oldVersion = buildGradle.match(/def minecraftVersion = "([^"]+)"/)[1];

  const newVersion = await (await prompts({
    type: 'text',
    name: 'version',
    message: 'Enter the new Minecraft version.'
  })).version;

  console.log("Fetching Yarn version for Minecraft " + newVersion + "...");
  const yarnVersions: any[] = await (await fetch(`https://meta.fabricmc.net/v2/versions/yarn/${newVersion}`)).json() as any[];
  const yarnVersion = yarnVersions.find(v => v.stable)?.version;
  if (!yarnVersion) {
    console.error("No stable Yarn version found for Minecraft " + newVersion.version);
    process.exit(1);
  }
  console.log("Found Yarn version " + yarnVersion);

  console.log("Fetching Fabric API version for Minecraft " + newVersion + "...");
  const fabricApiVersions: any[] = await (await fetch(`https://api.modrinth.com/v2/project/fabric-api/version?loaders=["fabric"]&game_versions=["${newVersion}"]&featured=true`)).json() as any[];
  const fabricApiVersion = fabricApiVersions[0]?.version_number;
  if (!fabricApiVersion) {
    console.error("No Fabric API version found for Minecraft " + newVersion);
    process.exit(1);
  }
  console.log("Found Fabric API version " + fabricApiVersion);

  console.log("Copying latest -> " + oldVersion + "...");

  // Copy ./reference/latest/** -> ./refrence/oldVersion/**
  fs.cpSync("./reference/latest", "./reference/" + oldVersion, { recursive: true });

  // Update build.gradle in latest with new versions.
  // def minecraftVersion = "XXX"
  // def yarnVersion = "XXXXX"
  // def fabricApiVersion = "XXXX"
  const newBuildGrade = buildGradle
    .replace(/def minecraftVersion = "([^"]+)"/, `def minecraftVersion = "${newVersion}"`)
    .replace(/def yarnVersion = "([^"]+)"/, `def yarnVersion = "${yarnVersion}"`)
    .replace(/def fabricApiVersion = "([^"]+)"/, `def fabricApiVersion = "${fabricApiVersion}"`);

  fs.writeFileSync("./reference/latest/build.gradle", newBuildGrade);

  // Add `include "{oldVersion}"` to the end of settings.gradle
  const settingsGradle = fs.readFileSync("./reference/settings.gradle", "utf-8");
  const newSettingsGradle = settingsGradle + `\ninclude "${oldVersion}"`;
  fs.writeFileSync("./reference/settings.gradle", newSettingsGradle);

  console.log("Refrence mod has been bumped successfully.");
  console.log("Migrating content to versioned/" + oldVersion + "...");

  // Move all markdown files except README.md to versions/oldVersion
  const markdownFiles = glob.sync("**/*.md", { ignore: [
    "README.md",
    "contributing.md",
    "versions/**/*.md",
    "node_modules/**/*",
  ]});

  // Copy into versions/oldVersion and respect the directory structure.
  for (const file of markdownFiles) {
    const oldPath = "./" + file;
    const newPath = "./versions/" + oldVersion + "/" + file;
    fs.cpSync(oldPath, newPath);
  }

  console.log("Migration complete.")
  console.log("Migration sidebars...")
  
  const versionedSidebar = {
    '/players/': players,
    '/develop/': develop
  };
  
  fs.writeFileSync("./.vitepress/sidebars/versioned/" + oldVersion + ".json", JSON.stringify(versionedSidebar, null, 2));

  console.log("Migrated sidebars.")

  // console.log("Copying translations...")
  // // Copy translated/**/*.md -> versions/oldVersion/translated/**/*.md
  // const translatedFiles = glob.sync("translated/**/*.md");
  // for (const file of translatedFiles) {
  //   const oldPath = "./" + file;
  //   const newPath = "./versions/" + oldVersion + "/" + file;
  //   fs.cpSync(oldPath, newPath);
  // }

  console.log("Updating internal links...");

  // Get all markdown files within versions/oldVersion
  const versionedMarkdownFiles = glob.sync(`versions/${oldVersion}/**/*.md`);
  // Process all content
  for (const file of versionedMarkdownFiles) {
    const content = fs.readFileSync(file, "utf-8");

    // Replace all instances of @/reference/latest/ with @/reference/oldVersion/
    const newContent = content.replace(/@\/reference\/latest\//g, `@/reference/${oldVersion}/`);
    fs.writeFileSync(file, newContent);
  }

  console.log("Updated internal links.");
  console.log("DONE! Make sure that the changes are correct before committing.");
})();