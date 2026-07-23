<script setup lang="ts">
const { color } = defineProps<{ color: string }>();
</script>

<template>
  <div :style="`background-color: ${color}`" />
</template>

<style scoped>
div {
  width: 100%;
  margin: auto;
  padding: 50%;
  border-color: #888;
  border-width: 1px;
  border-radius: 0.25rem;
}
</style>
