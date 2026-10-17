import * as fs from "node:fs";
import * as path from "node:path";
import type { MarkdownRenderer } from "vitepress";
import { getWebsiteResolver } from "../config/i18n.ts";
import { AT, ENV } from "../constants.ts";

export const downloadImagePlugin = (md: MarkdownRenderer) => {
  const image = md.renderer.rules.image!;
  md.renderer.rules.image = (tokens, idx, options, env, self) => {
    if (!env.localeIndex) {
      return image(tokens, idx, options, env, self);
    }
    const locale = env.localeIndex === "root" ? "en_us" : env.localeIndex;
    const resolver = getWebsiteResolver(locale);

    const token = tokens[idx];
    const srcValue = token.attrGet("src");
    if (!srcValue) {
      return image(tokens, idx, options, env, self);
    }

    const src = URL.canParse(srcValue) ? srcValue : path.posix.normalize(srcValue!).toLowerCase();
    if (srcValue !== src) {
      console.warn(`${env.relativePath}: expected normalized '${src}', got '${srcValue}'`);
    }

    const expectedParent = `/assets/${env.frontmatter.purePath}/`;
    const relativeSrc = path.posix.relative(expectedParent, src);
    if (/^[.][.]([/]|$)/.test(relativeSrc) || path.isAbsolute(relativeSrc)) {
      // TODO(debug): console.warn(`${env.relativePath}: expected assets under ${expectedParent}, got ${src}`);
    }

    let downloadPath = token.attrGet("download");
    if (downloadPath === null) {
      return image(tokens, idx, options, env, self);
    }

    token.attrs!.splice(token.attrIndex("download"), 1);
    const renderedImage = image(tokens, idx, options, env, self);
    if (downloadPath) {
      if (downloadPath !== downloadPath.toLowerCase()) {
        console.warn(`${env.relativePath}: unexpected uppercase in {download="${downloadPath}"}`);
      }
    } else if (!src.startsWith("/assets/")) {
      console.error(`${env.relativePath}: cannot determine {download} path for ${src}.`);

      return renderedImage;
    }

    downloadPath ||= src.replace("/assets/", "/download/");
    downloadPath = downloadPath.replace(/[/]+$/, "");

    const absoluteDownloadPath = path.join(AT, "public", downloadPath);
    if (!absoluteDownloadPath.startsWith(`${AT}${path.sep}`)) {
      console.error(`${env.relativePath}: path out of bounds in {download="${downloadPath}"}`);

      return renderedImage;
    }

    const stat = fs.statSync(absoluteDownloadPath, { throwIfNoEntry: false });
    if (!stat) {
      console.error(`${env.relativePath}: no {download} asset found at /${downloadPath}`);

      return renderedImage;
    }

    if (stat.isDirectory()) {
      console.error(`${env.relativePath}: unexpected directory as asset`);

      return renderedImage;
    }

    return `${renderedImage} <a download title="${md.utils.escapeHtml(
      resolver("download.button").replace("%s", token.content || path.basename(downloadPath))
    )}" href="${md.utils.escapeHtml(downloadPath)}"><span class="vpi-lucide-download" ${ENV === "dev" ? `style="--icon: url('/_vpi/lucide/download.svg')"` : ""}></span></a>`;
  };
};
