import path from "path";
import fs from "fs";
import {build} from "vitepress";
import ora from "ora";

const checkMark = '\x1b[32m✓\x1b[0m'
const failMark = '\x1b[31m✗\x1b[0m'

const configPath = path.resolve(".vitepress/config.mts");
const versionsDir = path.resolve("versions");
const distDir = path.resolve(".vitepress/dist");
const distTmpDir = path.resolve(".vitepress/dist-tmp")

function injectConfig(srcExclude: string[], outDir?: string) {
  let config = fs.readFileSync(configPath, "utf8");
  if (outDir) {
    if (config.includes("outDir")) {
      config = config.replace(/srcExclude:\s*\[.*?]/s, `srcExclude: ` + JSON.stringify(srcExclude));
      config = config.replace(/outDir:\s*['"].*?['"]/, `outDir: "${outDir}"`);
    } else {
      config = config.replace(/srcExclude:\s*\[.*?]/s, `srcExclude: ` + JSON.stringify(srcExclude) + `, outDir: "${outDir}"`);
    }
  } else {
    config = config.replace(/srcExclude:\s*\[.*?]/s, `srcExclude: ` + JSON.stringify(srcExclude));
    // Remove outDir if it exists by matching the previous comma and the entire ourDir property.
    config = config.replace(/,\s*outDir:\s*['"].*?['"]/, "");
  }
  fs.writeFileSync(configPath, config, "utf8");
}

async function configureAndBuild(message: string, srcExclude: string[], outDir?: string) {
  const spinner = ora(message).start();
  try {
    injectConfig(srcExclude, outDir);
    await build();
  } catch (e) {
    spinner.stopAndPersist({symbol: failMark});
    throw e;
  }
  spinner.stopAndPersist({symbol: checkMark});
}

// Copies all files recursively from src to dest, without overwriting existing files
function copyDir(src: string, dest: string) {
  fs.mkdirSync(dest, {recursive: true});
  for (const entry of fs.readdirSync(src, {withFileTypes: true})) {
    const s = path.join(src, entry.name);
    const d = path.join(dest, entry.name);
    if (entry.isDirectory()) copyDir(s, d);
    else if (!fs.existsSync(d)) fs.copyFileSync(s, d, fs.constants.COPYFILE_EXCL);
  }
}

async function run() {
  // Read all versions (subdirectories inside versions)
  const versions = fs.readdirSync(versionsDir, {withFileTypes: true})
    .filter(dirent => dirent.isDirectory())
    .map(dirent => dirent.name);

  for (const version of versions) {
    // Exclude the latest and all other versions except the current one
    // Build in a separate outDir for each version to prevent overwriting
    await configureAndBuild(`Building docs for version: ${version}...`, ["develop/**", "players/**", "translated/**", "index.md", "README.md", ...versions.filter(ver => ver !== version).map(ver => `versions/${ver}/**`)], `${distTmpDir}/${version}`);
  }

  // Build the latest version last, excluding all files under the versions directory
  await configureAndBuild("Building docs for latest version...", ["versions/**", "README.md"]);
  // Reset srcExclude to default
  injectConfig(["README.md"]);

  // Copy all other versions to dist
  for (const version of versions) {
    copyDir(`${distTmpDir}/${version}`, distDir);
  }

  // Delete dist-tmp
  fs.rmSync(distTmpDir, {recursive: true, force: true});
}

run().catch(err => {
  // Reset srcExclude to default
  injectConfig(["README.md"]);

  console.error(err);
  process.exit(1);
});
