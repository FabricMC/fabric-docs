import { SiteConfig } from "vitepress";
import { PageData } from "vitepress";

function addTag(pageData: PageData, name: string, content: string) {
  pageData.frontmatter.head ??= [];
  pageData.frontmatter.head.push([
    "meta",
    {
      name,
      content,
    },
  ]);
}

export function applySEO(pageData: PageData) {
  addTag(
    pageData,
    "og:title",
    pageData.title === "Fabric Documentation"
      ? `Fabric Documentation`
      : `${pageData.title} | Fabric Documentation`
  );

  addTag(pageData, "og:type", "website");
  addTag(pageData, "og:url", `https://docs.fabricmc.net/${pageData.relativePath}`);
  addTag(pageData, "og:description", pageData.description);
  addTag(pageData, "og:image", "/logo.png");

  addTag(pageData, "twitter:card", "summary");

  addTag(pageData, "theme-color", "#2275da");
}

export function removeVersionedItems(items: any[]): any[] {
  const config = globalThis.VITEPRESS_CONFIG as SiteConfig;
  const inverseRewrites = config.rewrites.inv;

  const itemsCopy = [...items];
  for (const item of items) {
    const path = item.url.replace("https://docs.fabricmc.net/", "") + ".md";

    // Remove the item if it's a versioned item
    if (inverseRewrites[path]?.includes("versions")) {
      itemsCopy.splice(itemsCopy.indexOf(item), 1);
    }
  }



  return itemsCopy;
}
