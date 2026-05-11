<script setup lang="ts">
import { useData } from "vitepress";
import VPLink from "vitepress/dist/client/theme-default/components/VPLink.vue";
import { computed } from "vue";

const props = defineProps<{ r: string }>();

const data = useData();

const href = computed(() => {
  // unlike Node.js, Fabric interprets "26.1" as "26.1.0"
  const normalizedRange = props.r.replaceAll(/(?<![0-9.])[0-9]+[.][0-9]+(?![.])/, "$&.0");
  return `https://jubianchi.github.io/semver-check/#/${encodeURIComponent(normalizedRange)}/${data.frontmatter.value.version}`;
});
</script>

<template>
  <VPLink :href
    ><code>{{ r }}</code></VPLink
  >
</template>
