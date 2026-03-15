<script setup lang="ts">
import { useData } from "vitepress";
import { computed } from "vue";

import { useBrowserLocation } from "@vueuse/core";
import { Fabric } from "../../types";

type Author = { name: string; noGitHub?: true };

const data = useData();

const options = computed(() => data.theme.value.authors as Fabric.AuthorsOptions);

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
        default: `${useBrowserLocation().value.origin}/assets/avatater.png`,
      });
</script>

<template>
  <h2 v-if="authors.length">{{ options.heading }}</h2>
  <div>
    <component
      v-for="author in authors"
      :key="author.noGitHub ? `${author.name}!` : author.name"
      :is="author.noGitHub ? 'span' : 'a'"
      :href="`https://github.com/${author.name}`"
      target="_blank"
    >
      <img
        :title="author.noGitHub ? options.noGitHub.replace('%s', author.name) : author.name"
        :src="getImageSrc(author)"
        :alt="author.name"
        loading="lazy"
      />
    </component>
  </div>
</template>

<style scoped>
h2 {
  font-weight: bold;
  font-size: large;
  margin-top: 16px;
}

.content-container > h2 {
  display: none;
}

div {
  display: flex;
  flex-direction: row;
  flex-wrap: wrap;
  align-items: center;
  margin-top: 8px;
  gap: 8px;

  a {
    transition: filter 0.2s ease-in-out;
  }

  a:hover {
    filter: brightness(120%);
  }

  img {
    border-radius: 50%;
    width: 32px;
    height: 32px;
  }
}

@media (min-width: 1280px) {
  .content-container > div {
    display: none;
  }
}
</style>
