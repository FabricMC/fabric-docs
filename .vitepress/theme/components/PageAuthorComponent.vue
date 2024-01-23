<template>
  <div v-if="authors.length > 0" class="authors-section">
    <h2>Page Authors</h2>
    <div class="page-authors">
      <a v-for="author in authors" :href="`https://github.com/${author}`" target="_blank" class="author-link"
        :title="author">
        <img loading="lazy" class="author-avatar"
          :src="`https://wsrv.nl/?url=${encodeURIComponent(`https://github.com/${author}.png?size=32`)}&af`"
          :alt="author" />
      </a>
    </div>
  </div>
</template>

<script setup lang="ts">
import { onContentUpdated, useData } from 'vitepress';
import { ref } from 'vue';

const authors = ref([]);
const pageData = useData();

function refreshData() {
  authors.value = [];

  if (pageData.frontmatter.value["authors"]) {
    authors.value = pageData.frontmatter.value["authors"];
  }
}

onContentUpdated(() => {
  refreshData();
});
</script>

<style scoped>
.authors-section {
  margin-top: 8px;
}

.authors-section>h2 {
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
}

.page-authors>a>img {
  border-radius: 50%;
  width: 32px;
  height: 32px;

  transition: filter 0.2s ease-in-out;
}

.page-authors>a:hover>img {
  filter: brightness(1.2);
}
</style>