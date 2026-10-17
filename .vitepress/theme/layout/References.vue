<script setup lang="ts">
import { useData } from "vitepress";
import { VPIcon, VPLink } from "vitepress/theme";
import { computed } from "vue";
import type { Fabric } from "../../types.d.ts";

const data = useData<Fabric.ThemeConfig>();

const options = computed(() => data.theme.value.references);

const resources = computed(() =>
  Object.entries(data.frontmatter.value.resources || {}).map(([href, title]) => {
    const newHref = new URL(href, "https://a.com").href.replace("https://a.com", "");

    return [newHref, title || newHref] as [string, string];
  })
);

const files = computed(() => (data.frontmatter.value.files || []) as string[]);

const shortestUniquePaths = computed(() =>
  files.value.map((file, i) => {
    const parts = file.split("/");
    for (let len = 1; len <= parts.length; len++) {
      const current = parts.slice(-len).join("/");
      const isUnique = files.value.every(
        (other, j) => i === j || other.split("/").slice(-len).join("/") !== current
      );
      if (isUnique) {
        return current;
      }
    }

    return file;
  })
);

const getImageSrc = (href: string) =>
  `https://www.google.com/s2/favicons?domain=${new URL(href, "https://docs.fabricmc.net").hostname}&sz=16`;

const getFileHref = (path: string) =>
  path.replace(/^@/, "https://github.com/FabricMC/fabric-docs/blob/-");

const getFileTitle = (path: string) =>
  path.replace(/^@[/]reference[/][^/]+[/]/, "").replace("com/example/docs", "...");

const getFileExtension = (path: string) =>
  path
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
          <span>{{ title }}</span>
        </VPLink>
      </li>
    </ul>
  </template>

  <template v-if="files.length">
    <h2>{{ options.files }}</h2>
    <ul>
      <li v-for="(f, i) of files" :key="f">
        <VPLink :href="getFileHref(f)" :title="getFileTitle(f)" noIcon>
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

  <div />
</template>

<style scoped>
div,
h2 {
  margin-top: 1.25rem;
  margin-bottom: 0.5rem;
  padding-top: 1rem;
  border-top: 1px solid var(--vp-c-divider);
  font-size: 0.75rem;
  font-weight: bold;
  color: var(--vp-c-text-2);
  letter-spacing: 0.06em;
  text-transform: uppercase;
}

li {
  margin-bottom: 0.25rem;
}

.VPLink {
  display: flex;
  align-items: flex-start;
  line-height: 1.5;
  gap: 0.3em;
  transition: color 0.15s;
  font-size: 0.75rem;
  color: var(--vp-c-text-2);
}

.VPLink:hover {
  color: var(--vp-c-text-1);
}

[class^="vpi-"] {
  flex-shrink: 0;
  margin-top: 0.125rem;
}

img {
  margin-top: 1px;
}

@media (min-width: 80rem) {
  .VPDocFooter > h2,
  .VPDocFooter > ul {
    display: none;
  }
}
</style>
