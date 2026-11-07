<script setup lang="ts">
import { useData } from "vitepress";
import { VPLink } from "vitepress/theme";
import { computed } from "vue";
import type { ThemeConfig } from "../../types.d.ts";

type Author = { name: string; noGitHub?: true };

const data = useData<ThemeConfig>();

const options = computed(() => data.theme.value.authors);

const authors = computed<Author[]>(() =>
  [
    ...((data.frontmatter.value["authors"] || []) as string[]).map((name) => ({ name })),
    ...((data.frontmatter.value["authors-nogithub"] || []) as string[]).map((name) => ({
      name,
      noGitHub: true,
    })),
  ].sort((a, b) => a.name.localeCompare(b.name))
);

const getImageSrc = (author: Author) => {
  if (author.noGitHub) {
    return "/assets/avatater.png";
  }

  const url = new URL("https://wsrv.nl/");
  url.searchParams.set("af", "");
  url.searchParams.set("w", "32");
  url.searchParams.set("h", "32");
  url.searchParams.set("maxage", "7d");
  url.searchParams.set("url", `https://github.com/${author.name}.png?size=32`);
  url.searchParams.set("default", "https://docs.fabricmc.net/assets/avatater.png");

  return url.toString();
};
</script>

<template v-if="authors.length">
  <h2>{{ options.heading }}</h2>
  <ul>
    <li v-for="a of authors" :key="a.noGitHub ? `${a.name}!` : a.name">
      <VPLink :href="a.noGitHub ? undefined : `https://github.com/${a.name}`" no-icon>
        <img
          :title="a.noGitHub ? options.noGitHub.replace('%s', a.name) : a.name"
          :src="getImageSrc(a)"
          :alt="a.name"
          loading="lazy"
        />
      </VPLink>
    </li>
  </ul>
</template>

<style scoped>
h2 {
  margin-top: 1.25rem;
  padding-top: 1rem;
  border-top: 1px solid var(--vp-c-divider);

  font-size: 0.75rem;
  font-weight: bold;
  color: var(--vp-c-text-2);
  text-transform: uppercase;
  letter-spacing: 0.05rem;
}

ul {
  display: flex;
  flex-flow: row wrap;
  gap: 0.5rem;
  align-items: center;

  margin-top: 0.5rem;

  a {
    transition: filter 0.2s ease-in-out;

    &::after {
      content: none;
    }

    &:hover {
      filter: brightness(120%);
    }
  }

  img {
    width: 2rem;
    height: 2rem;
    border-radius: 50%;
  }
}

.content-container {
  h2 {
    display: none;
  }

  ul {
    margin-bottom: 1rem;
  }
}

@media (width >= 80rem) {
  .content-container > ul {
    display: none;
  }
}
</style>
