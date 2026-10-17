<script setup lang="ts">
import { useData } from "vitepress";
import { VPIcon, VPLink } from "vitepress/theme";
import { computed } from "vue";
import type { ThemeConfig } from "../../types.d.ts";

// TODO(upstream): on mobile (narrow viewport) references should be placed akin to "On this page", instead of at the footer.

const data = useData<ThemeConfig>();

const options = computed(() => data.theme.value.references);

const resources = computed(() =>
  Object.entries(data.frontmatter.value.resources || {}).map(([href, title]) => {
    const newHref = new URL(href, "https://a.com").href.replace("https://a.com", "");

    return [newHref, title] as const;
  })
);

const files = computed(() => (data.frontmatter.value.files || []) as string[]);

const shortestUniquePaths = computed(() =>
  files.value.map((f, i) => {
    const parts = f.split("/");
    for (let len = 1; len <= parts.length; len++) {
      const current = parts.slice(-len).join("/");
      const isUnique = files.value.every(
        (other, j) => i === j || other.split("/").slice(-len).join("/") !== current
      );

      if (isUnique) {
        return current;
      }
    }

    return f;
  })
);

const getImageSrc = (href: string) =>
  `https://www.google.com/s2/favicons?domain=${new URL(href, "https://docs.fabricmc.net").hostname}&sz=16`;

const getFileHref = (filePath: string) =>
  filePath.replace(/^@/, "https://github.com/FabricMC/fabric-docs/blob/-");

const getFileTitle = (filePath: string) =>
  filePath.replace(/^@[/]reference[/][^/]+[/]/, "").replace("com/example/docs", "...");

const getFileExtension = (filePath: string) =>
  filePath
    .replace(/^.*(?<=^|[/])fabric.mod.json$/, "minecraft-fabric")
    .replace(/^.*[.]([^.]+)$/, "$1")
    .replace(/^classtweaker$/, "minecraft-fabric")
    .replace(/^md$/, "markdown");
</script>

<template>
  <template v-if="resources.length">
    <h2>{{ options.resources }}</h2>
    <ul>
      <li v-for="[href, title] of resources" :key="href">
        <VPLink :href>
          <img :src="getImageSrc(href)" alt="" width="16" height="16" />
          <span>{{ title || href }}</span>
        </VPLink>
      </li>
    </ul>
  </template>

  <template v-if="files.length">
    <h2>{{ options.files }}</h2>
    <ul>
      <li v-for="(f, i) of files" :key="f">
        <VPLink :href="getFileHref(f)" :title="getFileTitle(f)" no-icon>
          <VPIcon :icon="`material-icon-theme:${getFileExtension(f)}`" />
          <code>
            <template v-for="(seg, j) of shortestUniquePaths[i].split('/')" :key="j">
              <template v-if="j !== 0">/<wbr /></template>{{ seg }}
            </template>
          </code>
        </VPLink>
      </li>
    </ul>
  </template>
</template>

<style scoped>
h2 {
  margin-top: 1.25rem;
  margin-bottom: 0.5rem;

  font-size: 0.75rem;
  font-weight: bold;
  color: var(--vp-c-text-2);
  text-transform: uppercase;
  letter-spacing: 0.05rem;

  &:not(:first-child) {
    padding-top: 1rem;
    border-top: 1px solid var(--vp-c-divider);
  }
}

ul:last-of-type:has(+ *:not(div.spacer)) {
  margin-bottom: 1rem;
  padding-bottom: 1rem;
  border-bottom: 1px solid var(--vp-c-divider);
}

li {
  margin-bottom: 0.25rem;
}

.VPLink {
  display: flex;
  gap: 0.2rem;
  align-items: flex-start;

  font-size: 0.75rem;
  line-height: 1.5;
  color: var(--vp-c-text-2);

  transition: color 0.15s;

  &:hover {
    color: var(--vp-c-text-1);
  }
}

[class^="vpi-"] {
  flex-shrink: 0;
  margin-top: 0.125rem;
}

img {
  margin-top: 1px;
}

@media (width >= 80rem) {
  .VPDocFooter {
    * {
      display: none;
    }
  }
}
</style>
