<script setup lang="ts">
import { useData } from "vitepress";
import VPButton from "vitepress/dist/client/theme-default/components/VPButton.vue";
import { computed, useSlots } from "vue";
import { Fabric } from "../../types.d";

defineProps<{
  downloadURL: string;
  visualURL?: string;
}>();

const data = useData();

const title = useSlots().default?.() ?? [""];

const text = computed(() =>
  (data.theme.value.download as Fabric.DownloadOptions).text.replace(
    "%s",
    title.length > 0 ? ((title[0] as any).children ?? "") : "%s"
  )
);
</script>

<template>
  <div>
    <img v-if="visualURL" :src="visualURL ?? downloadURL" />
    <VPButton size="medium" theme="brand" :text :href="downloadURL" download />
  </div>
</template>

<style scoped>
div {
  display: flex;
  flex-direction: column;
  gap: 8px;
  align-items: center;
  justify-content: center;
}

img {
  pointer-events: none;
  z-index: 0;
  max-width: 100%;
  max-height: 300px;
}

a {
  text-decoration: none;

  &:hover {
    cursor: pointer;
  }
}
</style>
