import { Fabric } from "../types";

type NewHeadContext = {
  latestVersion: string;
  pathname: string;
  search: string;
  hash: string;
  origin: string;
  isNotFound: boolean | undefined;
  isVersioned: boolean;
  title: string;
  description: string;
  lastUpdated: number | undefined;
  siteName: string;
};

const _getNewHead = (context: NewHeadContext): string | [string, Record<string, string>][] => {
  const versionMap: Record<string, string> = {
    "26.1.2": "26.1.2",
    "26.1.1": "26.1.2",
    "26.1.0": "26.1.2",

    "1.21.11": "1.21.11",

    // TODO: bring back 1.21.10
    "1.21.10": "1.21.10",
    "1.21.9": "1.21.10",

    // TODO: bring back 1.21.8
    "1.21.8": "1.21.8",
    "1.21.7": "1.21.8",
    "1.21.6": "1.21.8",

    // not on the Docs
    "1.21.5": "1.21.5",

    // TODO: bring back 1.21.4
    "1.21.4": "1.21.4",

    // not on the Docs
    "1.21.3": "1.21.3",
    "1.21.2": "1.21.3",

    "1.21.1": "1.21.1",
    "1.21.0": "1.21.1",

    // not on the Docs
    "1.20.6": "1.20.6",
    "1.20.5": "1.20.6",

    "1.20.4": "1.20.4",
    "1.20.3": "1.20.4",
  };

  const redirects: { from: RegExp; dest: string }[] = [
    {
      from: /develop[/]items[/]custom-item-groups(?=[/]|$)/,
      dest: "develop/items/custom-creative-tabs",
    },
    {
      from: /develop[/]rendering[/]draw-context(?=[/]|$)/,
      dest: "develop/rendering/gui-graphics",
    },
    {
      from: /develop[/]migrating-mappings(?=[/]|$)/,
      dest: "develop/porting/mappings",
    },
    {
      from: /develop[/]porting[/]current(?=[/]|$)/,
      dest: "develop/porting/",
    },
    {
      from: /^(?:[0-9.]+[/])?develop[/]porting[/](next|26[.]1)(?=[/]|$)/,
      dest: "26.1.2/develop/porting",
    },
    {
      from: /develop[/]blocks[/]transparency-and-tinting(?=[/]|$)/,
      dest: "develop/blocks/block-tinting",
    },
    {
      from: /develop[/]blocks[/]block-tinting(?=[/]|$)/,
      dest: "develop/blocks/transparency-and-tinting",
    },
    {
      from: /develop[/](codecs|data-attachments|saved-data)(?=[/]|$)/,
      dest: "develop/serialization/$1",
    },
    {
      from: /^(?:[0-9.]+[/])?develop[/]porting[/]mappings(?=[/]|$)/,
      dest: "1.21.11/develop/porting/mappings",
    },
  ];

  // these replacements don't need to trigger a redirect
  const oldPath = decodeURIComponent(context.pathname)
    .replace(/^[/]/, "")
    .replace(/((?<=^|[/])index)?[.](html|md)$/, "");

  const split = oldPath.toLowerCase().replaceAll(/[/]+/g, "/").split("/");
  const localeIndex = /^..[_-]..$/.test(split[0])
    ? `/${split.shift()}/`.replace("/en_us/", "/")
    : "/";

  split[0] =
    versionMap[split[0]]
    ?? versionMap[`${split[0]}.0`]
    ?? (/^(?!404$)[0-9.]+$/.test(split[0]) ? "" : split[0]);
  if (!split[0] || split[0] === context.latestVersion) split.shift();

  const seenPaths = new Set([split.join("/")]);
  const newPath = redirects.reduce((currentPath, rule) => {
    const nextPath = currentPath.replace(rule.from, rule.dest);

    // skip the redirection rule if it causes a loop
    if (seenPaths.has(nextPath)) return currentPath;

    seenPaths.add(nextPath);
    return nextPath;
  }, split.join("/"));

  if (localeIndex.includes("-") || `/${oldPath}` !== `${localeIndex}${newPath}`) {
    if (context.isNotFound) {
      return `${localeIndex.replace("-", "_")}${newPath}${context.search}${context.hash}`;
    } else {
      console.warn(`${oldPath}: unexpected redirection to '${localeIndex.slice(1)}${newPath}'`);
    }
  }

  const href = `${context.origin}${localeIndex}${newPath}`;
  const ogLocale =
    localeIndex.replaceAll("/", "").replace(/..$/, (m) => m.toUpperCase()) || "en_US";

  const returned: [string, Record<string, string>][] = [
    ["meta", { name: "theme-color", content: "#2275da" }],
    ["meta", { name: "twitter:card", content: "summary" }], // haha still twitter
    ["link", { rel: "icon", sizes: "32x32", href: "/favicon.png" }],
    ["link", { rel: "license", href: "https://github.com/FabricMC/fabric-docs/blob/-/LICENSE" }],
    ["link", { rel: "canonical", href }],
    // https://ogp.me/
    ["meta", { property: "og:url", content: href }],
    ["meta", { property: "og:site_name", content: context.siteName }],
    ["meta", { property: "og:title", content: context.title }],
    ["meta", { property: "og:description", content: context.description }],
    ["meta", { property: "og:image", content: `${context.origin}/logo.png` }],
    ["meta", { property: "og:type", content: "article" }],
    ["meta", { property: "og:locale", content: ogLocale }],
  ];

  if ((context.lastUpdated ?? 0) > 0) {
    returned.push([
      "meta",
      { property: "article:modified_time", content: new Date(context.lastUpdated!).toISOString() },
    ]);
  }

  if (context.isNotFound || context.isVersioned) {
    returned.push(["meta", { name: "robots", content: "none" }]);
  }

  return returned;
};

export const getClientTransformHead = (latestVersion: string) => {
  const script = (getNewHead: typeof _getNewHead, latestVersion: string) => {
    const headData = getNewHead({
      ...window.location,
      latestVersion,
      title: document.title,
      description: document.querySelector("head meta[name=description]")!.getAttribute("content")!,
      siteName: document.title.split("|").at(-1)!.trim(),
      isNotFound: document.title.startsWith("404 | "),

      // only used for the OpenGraph tag, does not matter on the client side
      lastUpdated: undefined,

      // only used for the robots tag, does not matter on the client side
      isVersioned: false,
    });

    if (typeof headData === "string") {
      window.location.replace(headData);
      return;
    }

    // tags with the 'data-gen' attribute are managed by this script
    document.querySelectorAll("head [data-gen]:not(script)").forEach((el) => el.remove());
    for (const [tag, attributes] of headData) {
      const el = document.createElement(tag);

      attributes["data-gen"] = "";
      for (const [k, v] of Object.entries(attributes)) el.setAttribute(k, v);

      document.head.appendChild(el);
    }
  };

  return `(${script.toString()})(${_getNewHead.toString()}, ${JSON.stringify(latestVersion)})`;
};

export const getBuildTransformHead =
  (latestVersion: string): Fabric.Config["transformHead"] =>
  (context) => {
    const returned = _getNewHead({
      latestVersion,
      pathname: context.pageData.relativePath,
      origin: context.siteConfig.sitemap!.hostname,
      hash: "",
      search: "",
      description: context.pageData.description,
      title: context.pageData.title,
      isNotFound: context.pageData.isNotFound,
      isVersioned: context.pageData.filePath.startsWith("versions/"),
      siteName: context.siteData.locales[context.siteData.localeIndex!].title!,
      lastUpdated: context.pageData.lastUpdated,
    });

    if (typeof returned !== "string") return returned;
  };
