import * as crypto from "node:crypto";
import * as events from "node:events";
import * as fs from "node:fs";
import * as path from "node:path";
import * as perfHooks from "node:perf_hooks";
import * as process from "node:process";
import * as util from "node:util";
import * as workerThreads from "node:worker_threads";
import * as tinyglobby from "tinyglobby";
import {
  ALL_VERSIONS,
  AT,
  ENV,
  FUTURE_VERSIONS,
  LATEST_VERSION,
  OLD_VERSIONS,
} from "../constants.ts";

if (!workerThreads.isMainThread) {
  const vitepress = await import("vitepress");
  await vitepress.build(AT, workerThreads.workerData);

  process.exit(0);
}

const start = perfHooks.performance.now();
process.chdir(AT);

const tempDir = path.join(AT, ".vitepress", ".versions");

const args = util.parseArgs({
  options: { "list": { type: "boolean" }, "skip-build": { type: "boolean" } },
  allowPositionals: true,
});

const versionsSet = new Set(
  args.positionals.map((a) => path.basename(a)).map((a) => (a === "latest" ? LATEST_VERSION : a))
);

const isList = Boolean(args.values["list"]);
const skipBuild = Boolean(args.values["skip-build"]);

if (skipBuild) {
  if (versionsSet.size) {
    throw new Error("--skip-build does not accept versions");
  }

  if (!fs.statSync(path.join(tempDir, LATEST_VERSION), { throwIfNoEntry: false })?.isDirectory()) {
    throw new Error("couldn't find .versions directory");
  }
}

for (const v of versionsSet) {
  if (!ALL_VERSIONS.includes(v)) {
    throw new Error(`unrecognized version: '${v}'`);
  }
}

if (versionsSet.size === 0) {
  const detectedVersions = skipBuild
    ? tinyglobby.globSync("*", { cwd: tempDir, onlyDirectories: true }).map((v) => path.basename(v))
    : OLD_VERSIONS;

  for (const v of detectedVersions) {
    versionsSet.add(v);
  }
}

for (const v of FUTURE_VERSIONS) {
  versionsSet.delete(v);
}

versionsSet.add(LATEST_VERSION);

if (!skipBuild && versionsSet.size === 2) {
  // this means it's one old version and latest.
  // the old version build already includes latest,
  // so a separate build for latest, and then merging, is redundant
  versionsSet.delete(LATEST_VERSION);
}

const collator = new Intl.Collator(undefined, { numeric: true });
const versions = [...versionsSet].toSorted(collator.compare).toReversed();

if (isList) {
  console.log(JSON.stringify(versions));

  process.exit(0);
}

console.warn("PLEASE DO NOT TOUCH ANY FILE DURING BUILD\n");

for (const [i, version] of versions.entries()) {
  if (skipBuild) {
    break;
  }

  console.log(
    `${ENV === "github" ? "::group::" : ""}building ${version} (${i + 1}/${versions.length})...`
  );

  const worker = new workerThreads.Worker(import.meta.filename, {
    workerData: { outDir: path.join(tempDir, version) },
    env: {
      ...process.env,
      CI: "1",
      EXCLUDED_VERSIONS: OLD_VERSIONS.filter((v) => v !== version).join(","),
    },
  });

  const [code] = await events.once(worker, "exit");

  if (ENV === "github") console.log("::endgroup::");

  if (code !== 0) throw new Error(`building ${version} failed with exit code ${code}!`);
}

const targetDir = path.join(AT, ".vitepress", "dist");
fs.rmSync(targetDir, { recursive: true, force: true });

console.log(`merging metadata...`);
const getNewHash = (content: string) => crypto.hash("sha256", content, "hex").slice(0, 8);

const hashes: string[] = [];
const hashMap: Record<string, string> = {};
let siteData: unknown;

for (const version of versions) {
  (globalThis as any).window = {};

  const metadataFile = tinyglobby.globSync("metadata.*.js", {
    cwd: path.join(tempDir, version, "assets", "chunks"),
    absolute: true,
  })[0];

  hashes.push(path.basename(metadataFile).match(/^metadata[.](.+)[.]js$/)![1]);

  const fileContent = fs.readFileSync(metadataFile, { encoding: "utf-8" });
  const split = fileContent
    .split(";")
    .map((s) => s.trim())
    .filter(Boolean);

  if (split.length !== 2) {
    // deserializeFunctions is unsupported
    throw new Error(`too many assignments in ${metadataFile}`);
  }

  await import(metadataFile);

  if (!split[0].startsWith(`window.__VP_HASH_MAP__=JSON.parse`)) {
    throw new Error(`failed to parse hash map in ${metadataFile}`);
  }
  const hashMapFromVersion = window.__VP_HASH_MAP__;

  if (!split[1].startsWith(`window.__VP_SITE_DATA__=JSON.parse`)) {
    throw new Error(`failed to parse site data in ${metadataFile}`);
  }
  const siteDataFromVersion = window.__VP_SITE_DATA__;

  siteData ||= siteDataFromVersion;
  if (!util.isDeepStrictEqual(siteData, siteDataFromVersion)) {
    throw new Error(`site data for ${version} differs from ${versions[0]}!`);
  }

  Object.assign(hashMap, hashMapFromVersion);

  (globalThis as any).window = undefined;
}

const pageToVersionsMap = new Map<string, Set<string>>();
for (const version of versions) {
  const pageVersionsFromVersion = JSON.parse(
    fs.readFileSync(path.join(tempDir, version, "page-versions.json"), "utf-8")
  ) as Record<string, string[]>;

  for (const [purePath, versions] of Object.entries(pageVersionsFromVersion)) {
    if (!pageToVersionsMap.has(purePath)) {
      pageToVersionsMap.set(purePath, new Set());
    }

    for (const v of versions) {
      pageToVersionsMap.get(purePath)!.add(v);
    }
  }
}

const pageVersions = Object.fromEntries(
  [...pageToVersionsMap].map(([purePath, versions]) => [purePath, [...versions]])
);

const newMetadataContent = `${[
  `window.__VP_HASH_MAP__=JSON.parse(${JSON.stringify(JSON.stringify(hashMap))})`,
  `window.__VP_SITE_DATA__=JSON.parse(${JSON.stringify(JSON.stringify(siteData))})`,
  `window.__FD_PAGE_VERSIONS__=JSON.parse(${JSON.stringify(JSON.stringify(pageVersions))})`,
].join(";")};`;
const newHash = getNewHash(newMetadataContent);

const newMetadataPath = path.join(targetDir, "assets", "chunks", `metadata.${newHash}.js`);
const newHashmapJsonPath = path.join(targetDir, "hashmap.json");

fs.mkdirSync(path.dirname(newMetadataPath), { recursive: true });
fs.writeFileSync(newMetadataPath, newMetadataContent, "utf-8");
fs.writeFileSync(newHashmapJsonPath, JSON.stringify(hashMap), "utf-8");

console.log(`merging pages...`);
// assume every build has identical copies of latest's pages
for (const version of versions) {
  const outDir = path.join(tempDir, version);
  const files = tinyglobby.globSync("**/*", {
    cwd: outDir,
    ignore: ["hashmap.json", "page-versions.json", "assets/chunks/metadata.*.js"],
    absolute: true,
  });

  for (const f of files) {
    if (fs.statSync(f).isDirectory()) {
      continue;
    }

    const destPath = f.replace(outDir, targetDir);
    fs.mkdirSync(path.dirname(destPath), { recursive: true });

    if (f.endsWith(".html")) {
      let content = fs.readFileSync(f, "utf-8");
      for (const h of hashes) {
        content = content.replaceAll(`metadata.${h}.js`, `metadata.${newHash}.js`);
      }

      fs.writeFileSync(destPath, content, "utf-8");
    } else {
      fs.copyFileSync(f, destPath);
    }
  }
}

const elapsed = ((perfHooks.performance.now() - start) / 1000).toFixed(2);
if (versions.length === 1) {
  console.log(`'${versions[0]}' build complete in ${elapsed}s`);
} else if (skipBuild) {
  console.log(`all ${versions.length} builds merged in ${elapsed}s`);
} else {
  console.log(`all ${versions.length} builds complete in ${elapsed}s`);
}
