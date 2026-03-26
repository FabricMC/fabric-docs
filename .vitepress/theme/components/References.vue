<script setup lang="ts">
import { Icon } from "@iconify/vue";
import { useData } from "vitepress";
import VPLink from "vitepress/dist/client/theme-default/components/VPLink.vue";
import { computed } from "vue";
import { Fabric } from "../../types";

const data = useData();

const options = computed(() => data.theme.value.references as Fabric.ReferencesOptions);

const resources = computed(() =>
  Object.entries(data.frontmatter.value.resources ?? {}).map(([href, title]) => {
    const newHref = new URL(href, "https://a.com").href.replace("https://a.com", "");
    return [newHref, title ?? newHref] as [string, string];
  })
);

const files = computed(() => (data.frontmatter.value.files ?? []) as string[]);

const shortestUniquePaths = computed(() =>
  files.value.map((file, i) => {
    const parts = file.split("/");
    for (let len = 1; len <= parts.length; len++) {
      const current = parts.slice(-len).join("/");
      const isUnique = files.value.every(
        (other, j) => i === j || other.split("/").slice(-len).join("/") !== current
      );
      if (isUnique) return current;
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
  path.replace(/^.*[.]([^.]+)$/, "$1").replace(/^classtweaker$/, "document");
</script>

<template>
  <h2 v-if="resources.length">{{ options.resources }}</h2>
  <VPLink v-for="[href, title] in resources" :key="href" :href>
    <img :src="getImageSrc(href)" alt="" width="16" height="16" />
    <span>{{ title }}</span>
  </VPLink>

  <h2 v-if="files.length">{{ options.files }}</h2>
  <VPLink v-for="(f, i) in files" :key="f" :href="getFileHref(f)" :title="getFileTitle(f)" noIcon>
    <Icon :icon="`material-icon-theme:${getFileExtension(f)}`" />
    <code>
      <template v-for="(seg, j) in shortestUniquePaths[i].split('/')" :key="j">
        <template v-if="j !== 0">/<wbr /></template>{{ seg }}
      </template>
    </code>
  </VPLink>

  <div />
</template>

<style scoped>
div,
h2 {
  margin-top: 20px;
  margin-bottom: 8px;
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
  align-items: flex-start;
  line-height: 1.5;
  margin-bottom: 4px;
  gap: 0.3em;
  transition: color 0.15s;
  font-size: 12px;
  color: var(--vp-c-text-2);
}

.VPLink:hover {
  color: var(--vp-c-text-1);
}

svg {
  flex-shrink: 0;
  margin-top: 2px;
}

img {
  margin-top: 1px;
}

@media (min-width: 1280px) {
  .VPDocFooter > h2,
  .VPDocFooter > .VPLink {
    display: none;
  }
}
</style>
