<script setup lang="ts">
import { useData } from "vitepress";
import VPButton from "vitepress/dist/client/theme-default/components/VPButton.vue";
import { computed, useSlots } from "vue";

import { Fabric } from "../../types";

const data = useData();
const props = defineProps<{
  downloadURL: string;
  visualURL?: string;
}>();
const slotContent = useSlots().default?.() ?? [""];

const text = computed(() =>
  (data.theme.value.download as Fabric.DownloadOptions).text.replace(
    "%s",
    slotContent.length > 0 ? (slotContent[0] as any).children ?? "" : "%s"
  )
);
</script>

<template>
  <div>
    <img v-if="props.visualURL" :src="props.visualURL ?? props.downloadURL" />
    <VPButton size="medium" theme="brand" :text="text" :href="props.downloadURL" download />
  </div>
</template>

<style scoped>
div {
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
  gap: 8px;
}

img {
  /* Disable interactions, prevent right click save. */
  pointer-events: none;
  max-width: 100%;
  max-height: 300px;
  z-index: 0;
}

a {
  text-decoration: none;
}

a:hover {
  cursor: pointer;
}
</style>
