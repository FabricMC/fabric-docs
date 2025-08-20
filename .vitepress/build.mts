import path from "path";
import fs from "fs";
import {build} from "vitepress";
import ora from "ora";

const checkMark = '\x1b[32m✓\x1b[0m'
const failMark = '\x1b[31m✗\x1b[0m'

const configPath = path.resolve(".vitepress/config.mts");
const versionsDir = path.resolve("versions");

function injectSrcExclude(srcExclude: string[]) {
  let updatedConfig = fs.readFileSync(configPath, "utf8").replace(/srcExclude:\s*\[.*?]/s, `srcExclude: ` + JSON.stringify(srcExclude));
  fs.writeFileSync(configPath, updatedConfig, "utf8");
}

async function excludeAndBuild(message: string, srcExclude: string[]) {
  const spinner = ora(message).start();
  try {
    injectSrcExclude(srcExclude);
    await build();
  } catch (e) {
    spinner.stopAndPersist({symbol: failMark});
    throw e;
  }
  spinner.stopAndPersist({symbol: checkMark});
}

async function run() {
  // Build the latest version first, excluding all files under the versions directory
  await excludeAndBuild("Building docs for latest version...", ["versions/**", "README.md"]);

  // Read all versions (subdirectories inside versions)
  const versions = fs.readdirSync(versionsDir, {withFileTypes: true})
    .filter(dirent => dirent.isDirectory())
    .map(dirent => dirent.name);

  for (const version of versions) {
    // Exclude the latest and all other versions except the current one
    await excludeAndBuild(`Building docs for version: ${version}...`, ["develop/**", "players/**", "translated/**", "index.md", "README.md", ...versions.filter(ver => ver !== version).map(ver => `versions/${ver}/**`)]);
  }
}

run().catch(err => {
  console.error(err);
  process.exit(1);
}).finally(() => {
  // Reset srcExclude to default
  injectSrcExclude(["README.md"]);
});
