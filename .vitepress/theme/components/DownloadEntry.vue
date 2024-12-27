<script setup lang="ts">
import { onContentUpdated, useData } from "vitepress";
import { VPButton } from "vitepress/theme";
import { ref, useSlots } from "vue";

const data = useData();
const props = defineProps<{
  downloadURL: string;
  visualURL?: string;
}>();
const slotContent = useSlots().default?.() ?? [""];

const text = ref<string>("");

function refreshText() {
  text.value = data.theme.value.download.text as string;

  if (slotContent.length > 0) {
    // @ts-expect-error
    text.value = text.value.replace("%s", slotContent[0].children ?? "");
  }
}

onContentUpdated(() => {
  refreshText();
});
</script>

<template>
  <div class="container">
    <img
      v-if="$props.visualURL"
      :src="$props.visualURL ?? $props.downloadURL"
      style="max-width: 100%; max-height: 300px"
    />
    <VPButton
      tag="a"
      size="medium"
      theme="brand"
      :text="text"
      :href="$props.downloadURL"
      download
    />
  </div>
</template>

<style scoped>
div.container {
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
  gap: 8px;
}

a {
  text-decoration: none;
}

a:hover {
  cursor: pointer;
}

img {
  /* Disable interactions, prevent right click save. */
  pointer-events: none;

  z-index: 0;
}
</style>
