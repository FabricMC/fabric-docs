<script setup lang="ts">
import { useData } from "vitepress";
import VPLink from "vitepress/dist/client/theme-default/components/VPLink.vue";
import { computed } from "vue";

import { Fabric } from "../../types";

const data = useData();

const options = computed(() => data.theme.value.references as Fabric.ReferencesOptions);

const resources = computed(() =>
  Object.entries(data.frontmatter.value.resources ?? {}).map(
    ([url, title]) => [url, title ?? url] as [string, string]
  )
);

const files = computed(() => (data.frontmatter.value.files ?? []) as string[]);

const getImageSrc = (url: string) =>
  `https://www.google.com/s2/favicons?domain=${new URL(url).hostname}&sz=16`;

const getFileHref = (path: string) =>
  path.replace(/^@/, "https://github.com/FabricMC/fabric-docs/blob/-");

const getFileTitle = (path: string) =>
  path.replace(/^@[/]reference[/][^/]+[/]/, "").replace("com/example/docs", "...");
</script>

<template>
  <h2 v-if="resources.length">{{ options.resources }}</h2>
  <VPLink v-for="[url, title] in resources" :key="url" :href="url">
    <img :src="getImageSrc(url)" alt="" width="16" height="16" />
    <span>{{ title ?? url }}</span>
  </VPLink>

  <h2 v-if="files.length">{{ options.files }}</h2>
  <VPLink v-for="f in files" :key="f" :href="getFileHref(f)" :title="getFileTitle(f)">
    <code>{{ f.split("/").at(-1) }}</code>
  </VPLink>

  <div />
</template>

<style scoped>
div,
h2 {
  margin-top: 20px;
  padding-top: 16px;
  border-top: 1px solid var(--vp-c-divider);
  font-size: 12px;
  font-weight: bold;
  color: var(--vp-c-text-2);
  letter-spacing: 0.06em;
  text-transform: uppercase;
}

.VPLink {
  display: flex;
  align-items: center;
  gap: 0.3em;
  transition: color 0.15s;
  font-size: 12px;
  color: var(--vp-c-text-2);
}

.VPLink:hover {
  color: var(--vp-c-text-1);
}

@media (min-width: 1280px) {
  .content-container > h2,
  .content-container > .VPLink {
    display: none;
  }
}
</style>
