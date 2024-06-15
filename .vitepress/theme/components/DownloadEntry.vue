<template>
  <div class="container">
    <img v-if="!$props.noVisualURL" :src="$props.visualURL ?? $props.downloadURL" style="max-width: 100%; max-height: 300px;">
    <VPButton 
      tag="a" 
      size="medium" 
      theme="brand" 
      :text="`Download ${$props.type}`" 
      :href="$props.downloadURL"
      download
    />
  </div>
</template>

<script setup lang="ts">
import { VPButton } from "vitepress/theme"

// downloadURL prop, visualURL prop (optional), if no visualURL, use downloadURL
const props = defineProps<{
  downloadURL: string;
  visualURL?: string;
  noVisualURL?: boolean;
  type: string;
}>();
</script>

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