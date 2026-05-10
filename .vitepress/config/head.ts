import { HeadConfig, SiteConfig } from "vitepress";

export const getRedirects = (latestVersion: string) =>
  [
    {
      from: /[/]{2,}/,
      dest: "/",
    },
    {
      from: /((?<=^|[/])index)?[.]html$/,
      dest: "",
    },
    {
      from: new RegExp(`^${latestVersion}([/]|$)`),
      dest: "",
    },
    {
      from: new RegExp(`^1.21.10([/]|$)`),
      dest: "1.21.11/",
    },
    {
      from: /develop[/]items[/]custom-item-groups$/,
      dest: "develop/items/custom-creative-tabs",
    },
    {
      from: /develop[/]rendering[/]draw-context$/,
      dest: "develop/rendering/gui-graphics",
    },
    {
      from: /develop[/]migrating-mappings([/]|$)/,
      dest: "develop/porting/mappings/",
    },
    {
      from: /develop[/]porting[/]current$/,
      dest: "develop/porting/",
    },
    {
      from: /develop[/]porting[/](next|26[.]1)([/]|$)/,
      dest: "26.1/develop/porting/",
    },
    {
      from: /develop[/]blocks[/]transparency-and-tinting$/,
      dest: "develop/blocks/block-tinting",
    },
    {
      from: /develop[/]blocks[/]block-tinting$/,
      dest: "develop/blocks/transparency-and-tinting/",
    },
    {
      from: /^(?:[0-9.]+[/])?develop[/]porting[/]mappings([/].*)?$/,
      dest: "1.21.11/develop/porting/mappings$1",
    },
  ] satisfies { from: RegExp; dest: string }[];

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
    context.siteConfig.userConfig.locales![locale]?.title
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
