<script setup lang="ts">
import { useData } from "vitepress";
import { computed } from "vue";

const data = useData();
const heading = computed(() => data.theme.value.authors.heading);
const labelNoGitHub = computed(() => data.theme.value.authors.noGitHub);

const combinedAuthors = computed<{ name: string; noGitHub?: true }[]>(() => {
  const authors: string[] = data.frontmatter.value["authors"] || [];
  const authorsNoGitHub: string[] =
    data.frontmatter.value["authors-nogithub"] || [];

  const withGitHub = authors.map((name) => ({ name }));
  const withoutGitHub = authorsNoGitHub.map((name) => ({
    name,
    noGitHub: true,
  }));

  return [...withGitHub, ...withoutGitHub].sort((a, b) =>
    a.name.localeCompare(b.name)
  );
});
</script>

<template>
  <div v-if="combinedAuthors.length" class="authors-section">
    <h2>{{ heading }}</h2>
    <div class="page-authors">
      <template
        v-for="author in combinedAuthors"
        :key="(author.noGitHub ? ':' : '') + author.name"
      >
        <img
          v-if="author.noGitHub"
          loading="lazy"
          class="author-avatar"
          src="/assets/avatater.png"
          :alt="author.name"
          :title="labelNoGitHub.replace('%s', author.name)"
        />
        <a
          v-else
          :href="`https://github.com/${author.name}`"
          target="_blank"
          class="author-link"
          :title="author.name"
        >
          <img
            loading="lazy"
            class="author-avatar"
            :src="`https://wsrv.nl/?url=${encodeURIComponent(
              `https://github.com/${author.name}.png?size=32`
            )}&af&maxage=7d`"
            :alt="author.name"
          />
        </a>
      </template>
    </div>
  </div>
</template>

<style scoped>
.authors-section {
  margin-top: 8px;
}

.authors-section > h2 {
  font-weight: 700;
  color: var(--vp-c-text-1);
  font-size: 14px;
}

.page-authors {
  display: flex;
  flex-direction: row;
  flex-wrap: wrap;
  align-items: center;
  margin-top: 8px;
  gap: 8px;
  padding-bottom: 8px;
}

.author-avatar {
  border-radius: 50%;
  width: 32px;
  height: 32px;

  transition: filter 0.2s ease-in-out;
}

.page-authors > a:hover > .author-avatar {
  filter: brightness(1.2);
}

@media (min-width: 1280px) {
  .content-container > .authors-section {
    display: none;
  }
}
</style>
