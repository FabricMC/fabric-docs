<script setup lang="ts">
import { useData } from "vitepress";
import { VPIcon, VPLink } from "vitepress/theme";
import { computed } from "vue";
import type { ThemeConfig } from "../../types.d.ts";

// TODO(upstream): on mobile (narrow viewport) references should be placed akin to "On this page", instead of at the footer.

const data = useData<ThemeConfig>();

const options = computed(() => data.theme.value.references);

const resources = computed(() =>
  Object.entries<string>(data.frontmatter.value.resources || {}).map(([oldHref, title]) => {
    const url = new URL(oldHref, "https://docs.fabricmc.net");
    const favicon = `https://www.google.com/s2/favicons?domain=${url.hostname}&sz=16`;
    const href =
      url.origin === "https://docs.fabricmc.net"
        ? `${url.pathname}${url.search}${url.hash}`
        : oldHref;

    return { title, href, favicon };
  })
);

const suffixToIconMap: Record<string, string> = {
  ".classtweaker": "minecraft-fabric",
  ".md": "markdown",
  ".yml": "yaml",
  "fabric.mod.json": "minecraft-fabric",
};

const files = computed(() =>
  ((data.frontmatter.value.files || []) as string[]).map((filePath) => {
    const title = filePath.replace(/^@[/]reference[/][^/]+[/]/, "");
    const href = filePath.replace(/^@/, "https://github.com/FabricMC/fabric-docs/blob/-");
    const icon = `material-icon-theme:${
      Object.entries(suffixToIconMap).find(([suffix]) => filePath.endsWith(suffix))?.[1]
      || filePath.split(/[/.]/).at(-1)!
    }`;

    return { title, href, icon };
  })
);
</script>

<template>
  <template v-if="resources.length">
    <h2>{{ options.resources }}</h2>
    <ul>
      <li v-for="r of resources" :key="r.href">
        <VPLink :href="r.href">
          <img :src="r.favicon" />
          <span>{{ r.title || r.href }}</span>
        </VPLink>
      </li>
    </ul>
  </template>

  <template v-if="files.length">
    <h2>{{ options.files }}</h2>
    <ul>
      <li v-for="f of files" :key="f.href">
        <VPLink :href="f.href" :title="f.title" no-icon>
          <VPIcon :icon="f.icon" />
          <code>
            <bdi dir="ltr">{{ f.title }}</bdi>
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
  width: 1rem;
  height: 1rem;
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
