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

const getImageSrc = (href: string) =>
  `https://www.google.com/s2/favicons?domain=${new URL(href, "https://docs.fabricmc.net").hostname}&sz=16`;

const getFileHref = (filePath: string) =>
  filePath.replace(/^@/, "https://github.com/FabricMC/fabric-docs/blob/-");

const getFileTitle = (filePath: string) => filePath.replace(/^@[/]reference[/][^/]+[/]/, "");

const getFileIcon = (filePath: string) =>
  `material-icon-theme:${filePath
    .replace(/^.*(?<=^|[/])fabric.mod.json$/, "minecraft-fabric")
    .replace(/^.*[.]([^.]+)$/, "$1")
    .replace(/^classtweaker$/, "minecraft-fabric")
    .replace(/^md$/, "markdown")}`;
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
      <li v-for="f of files" :key="f">
        <VPLink :href="getFileHref(f)" :title="getFileTitle(f)" no-icon>
          <VPIcon :icon="getFileIcon(f)" />
          <code>
            <bdi dir="ltr">{{ getFileTitle(f) }}</bdi>
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

code {
  scrollbar-width: none;

  direction: rtl;
  overflow-x: auto;

  text-align: left;
  white-space: nowrap;

  mask-image: linear-gradient(
    to right,
    transparent 0,
    var(--vp-c-divider) var(--fade-width-left),
    var(--vp-c-divider) calc(100% - var(--fade-width-right)),
    transparent 100%
  );

  animation: change-fade linear;
  animation-timeline: scroll(self inline);
}

@keyframes change-fade {
  0% {
    --fade-width-left: 2rem;
    --fade-width-right: 0rem;
  }

  1%,
  99% {
    --fade-width-left: 2rem;
    --fade-width-right: 2rem;
  }

  100% {
    --fade-width-left: 0rem;
    --fade-width-right: 2rem;
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
