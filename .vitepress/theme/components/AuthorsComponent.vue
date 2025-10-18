<script setup lang="ts">
import { useData } from "vitepress";
import { computed, ref } from "vue";

const data = useData();

const options = computed(() => data.theme.value.authors);

const authors = computed<{ name: string; noGitHub?: true }[]>(() =>
  [
    ...((data.frontmatter.value["authors"] || []) as string[]).map(
      // @error below will take care of deleted GitHub accounts
      (name) => ({ name })
    ),
    ...((data.frontmatter.value["authors-nogithub"] || []) as string[]).map(
      (name: string) => ({ name, noGitHub: true })
    ),
  ].sort((a, b) => a.name.localeCompare(b.name))
);

const deletedAuthors = ref(new Set<string>());
</script>

<template>
  <div class="authors-component">
    <h2 v-if="authors.length">{{ options.heading }}</h2>
    <div class="authors">
      <component
        v-for="author in authors"
        :key="
          author.noGitHub
            ? `${author.name}!`
            : deletedAuthors.has(author.name)
            ? `${author.name}?`
            : author.name
        "
        :is="author.noGitHub || deletedAuthors.has(author.name) ? 'div' : 'a'"
        :href="`https://github.com/${author.name}`"
        target="_blank"
      >
        <img
          :title="
            author.noGitHub
              ? options.noGitHub.replace('%s', author.name)
              : deletedAuthors.has(author.name)
              ? options.deletedGitHub.replace('%s', author.name)
              : author.name
          "
          :src="
            author.noGitHub || deletedAuthors.has(author.name)
              ? '/assets/avatater.png'
              : `https://wsrv.nl/?url=${encodeURIComponent(
                  `https://github.com/${author.name}.png?size=32`
                )}&af&maxage=7d`
          "
          :alt="author.name"
          @error="deletedAuthors.add(author.name)"
        />
      </component>
    </div>
  </div>
</template>

<style scoped>
@media (min-width: 1280px) {
  .content-container > .authors-component {
    display: none;
  }
}

.authors {
  display: flex;
  flex-direction: row;
  flex-wrap: wrap;
  align-items: center;
  margin-top: 8px;
  gap: 8px;
  padding-bottom: 8px;
}

h2 {
  font-weight: 700;
  color: var(--vp-c-text-1);
  font-size: 14px;
}

a:hover {
  filter: brightness(1.2);
}

img {
  border-radius: 50%;
  width: 32px;
  height: 32px;
  transition: filter 0.2s ease-in-out;
}
</style>
