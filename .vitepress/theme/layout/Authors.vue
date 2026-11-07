<script setup lang="ts">
import { useData } from "vitepress";
import { VPLink } from "vitepress/theme";
import { computed } from "vue";
import type { Fabric } from "../../types.d.ts";

type Author = { name: string; noGitHub?: true };

const data = useData<Fabric.ThemeConfig>();

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

const getImageSrc = (author: Author) =>
  author.noGitHub
    ? "/assets/avatater.png"
    : "https://wsrv.nl/?"
      + new URLSearchParams({
        af: "",
        maxage: "7d",
        url: `https://github.com/${author.name}.png?size=32`,
        default: `https://docs.fabricmc.net/assets/avatater.png`,
      });
</script>

<template>
  <template v-if="authors.length">
    <h2>{{ options.heading }}</h2>
    <ul>
      <li v-for="author in authors" :key="author.noGitHub ? `${author.name}!` : author.name">
        <VPLink :href="author.noGitHub ? undefined : `https://github.com/${author.name}`">
          <img
            :title="author.noGitHub ? options.noGitHub.replace('%s', author.name) : author.name"
            :src="getImageSrc(author)"
            :alt="author.name"
            loading="lazy"
          />
        </VPLink>
      </li>
    </ul>
  </template>
</template>

<style scoped>
h2 {
  margin-top: 1.25rem;
  padding-top: 1rem;
  border-top: 1px solid var(--vp-c-divider);
  font-size: 0.75rem;
  font-weight: bold;
  color: var(--vp-c-text-2);
  letter-spacing: 0.06em;
  text-transform: uppercase;
}

ul {
  display: flex;
  flex-direction: row;
  flex-wrap: wrap;
  align-items: center;
  margin-top: 0.5rem;
  gap: 0.5rem;

  a {
    transition: filter 0.2s ease-in-out;
  }

  a:hover {
    filter: brightness(120%);
  }

  img {
    border-radius: 50%;
    width: 2rem;
    height: 2rem;
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

@media (min-width: 80rem) {
  .content-container > ul {
    display: none;
  }
}
</style>
