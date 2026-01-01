import { HeadConfig, SiteConfig, UserConfig } from "vitepress";

import redirects from "./redirects";

export const transformHref = (href: string): URL => {
  const url = URL.parse(href)!;
  const split = url.pathname.toLowerCase().split("/");

  const initial = split[0] === "" ? split.shift() : undefined;
  const locale = /^..[-_]..$/.test(split[0]) ? split.shift()!.replace("-", "_") : undefined;
  let theRest = split.join("/");

  for (const { from, dest } of [
    { from: /^(.*)((?<=^|\/)index)?\.(md|html)$/, dest: "$1" },
    ...redirects,
  ]) {
    if (from === theRest) theRest = dest;
    if (from instanceof RegExp) theRest = theRest.replace(from, dest);
  }

  url.pathname = [initial, locale, theRest].filter((s) => s !== undefined).join("/");
  return url;
};

export const transformHead: SiteConfig["transformHead"] = (context) => {
  const returned: HeadConfig[] = [];

  const split = context.pageData.filePath.split("/");
  if (split[0] === "versions") split.splice(0, 2);
  const locale = split[0] === "translated" ? split[1] : "en_us";

  const siteName =
    context.siteConfig.userConfig.locales![locale]?.title
    ?? context.siteConfig.userConfig.locales!.root.title!;
  const hostName = context.siteConfig.sitemap?.hostname ?? "";

  const currentHref = `${hostName}${context.pageData.relativePath}`;
  const canonicalHref = transformHref(currentHref).href;

  returned.push(["link", { rel: "canonical", href: canonicalHref }]);

  // https://ogp.me/
  const og: [string, string][] = [
    ["og:site_name", siteName],
    ["og:title", context.pageData.title],
    ["og:description", context.pageData.description],
    ["og:url", canonicalHref],
    ["og:image", `${hostName}/logo.png`],
    ["og:type", "article"],
    ["og:locale", locale.replace(/..$/, (m) => m.toUpperCase())],
  ];
  if ((context.pageData.lastUpdated ?? 0) > 0) {
    og.push(["article:modified_time", String(context.pageData.lastUpdated)]);
  }

  returned.push(...og.map(([property, content]) => ["meta", { property, content }] as HeadConfig));

  // Meta Refresh redirect to the canonical URL
  if (currentHref !== canonicalHref) {
    returned.push(["meta", { "http-equiv": "refresh", "content": `0; URL=${canonicalHref}` }]);
  }

  // Don't index the page if it's a versioned page.
  if (context.pageData.filePath.startsWith("versions/")) {
    returned.push(["meta", { name: "robots", content: "none" }]);
  }

  return returned;
};

export const transformItems: NonNullable<UserConfig["sitemap"]>["transformItems"] = (items) => {
  const config = (globalThis as any).VITEPRESS_CONFIG as SiteConfig;
  return items.filter((i) => {
    const relativePath = i.url.replace(config.sitemap!.hostname, "");
    return !config.rewrites.inv[relativePath]?.startsWith("versions/");
  });
};
