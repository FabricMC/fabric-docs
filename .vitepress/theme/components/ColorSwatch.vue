<script setup lang="ts">
const { color } = defineProps<{ color: string }>();
</script>

<template>
  <div class="color-swatch" :style="`background-color: ${color}`" />
</template>

<style scoped>
.color-swatch {
  width: 100%;
  padding: 50%;
  margin: auto;
  border-radius: 0.25rem;
  border-color: gray;
  border-width: 1px;
}
</style>
