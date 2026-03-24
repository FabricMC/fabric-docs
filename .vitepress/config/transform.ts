import { HeadConfig, SiteConfig, UserConfig } from "vitepress";

export const transformHead: SiteConfig["transformHead"] = (context) => {
  const returned: HeadConfig[] = [];

  // Don't index the page if it's a versioned page.
  if (context.pageData.filePath.startsWith("versions/")) {
    returned.push(["meta", { name: "robots", content: "none" }]);
  }

  if (context.pageData.isNotFound) return returned;

  const split = context.pageData.filePath.split("/");
  if (split[0] === "versions") split.splice(0, 2);
  const locale = split[0] === "translated" ? split[1] : "en_us";

  const hostName = context.siteConfig.sitemap!.hostname;
  const siteName =
    context.siteConfig.userConfig.locales![locale]?.title!
    || context.siteConfig.userConfig.locales!.root!.title!;
  const href = `${hostName}${context.pageData.relativePath.replace(/((?<=^|[/])index)?[.]md$/, "")}`;

  // https://ogp.me/
  const og: [string, string][] = [
    ["og:site_name", siteName],
    ["og:title", context.pageData.title],
    ["og:description", context.pageData.description],
    ["og:url", href],
    ["og:image", `${hostName}logo.png`],
    ["og:type", "article"],
    ["og:locale", locale.replace(/_..$/, (m) => m.toUpperCase().replace("_", "-"))],
  ];
  if ((context.pageData.lastUpdated ?? 0) > 0) {
    og.push(["article:modified_time", String(context.pageData.lastUpdated)]);
  }

  returned.push(...og.map(([property, content]) => ["meta", { property, content }] as HeadConfig));
  returned.push(["link", { rel: "canonical", href }]);

  return returned;
};

export const transformItems: NonNullable<UserConfig["sitemap"]>["transformItems"] = (items) => {
  const config = (globalThis as any).VITEPRESS_CONFIG as SiteConfig;
  return items.filter((i) => {
    const relativePath = i.url.replace(config.sitemap!.hostname, "");
    return !config.rewrites.inv[relativePath]?.startsWith("versions/");
  });
};
