<template>
  <div v-if="authors.length > 0" class="authors-section">
    <h2>Page Authors</h2>
    <div class="page-authors">
      <div v-for="author in authors" :key="author">
        <a :href="`https://github.com/${author}`" target="_blank" class="author-link">
          <img class="author-avatar" :src="`https://github.com/${author}.png`" :alt="author" />
          <small>{{ author }}</small>
        </a>
      </div>
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

// router.onAfterRouteChanged = () => {
//     refreshData();
//     return;
//   };
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

.author-avatar {
  width: 32px;
  height: 32px;
  border-radius: 50%;
  transition: filter 0.5s;
}

.author-link {
  display: flex;
  flex-direction: row;
  align-items: center;
  gap: 8px;
}

.author-link:hover>.author-avatar {
  filter: brightness(1.2);
}

.author-link>small {
  transition: color 0.25s;
}

.author-link:hover>small {
  color: var(--vp-c-brand-1);
}

.author-avatar:hover {
  opacity: 0.8;
}

.page-authors {
  display: flex;
  flex-direction: column;
  gap: 1rem;
  margin-top: 8px;
  align-items: center;
}

.page-authors>div {
  margin-right: auto;
  display: block;
}
</style>