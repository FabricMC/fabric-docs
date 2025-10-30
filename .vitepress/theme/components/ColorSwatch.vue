<script setup lang="ts">
const { color } = defineProps<{ color: string }>();
</script>

<template>
  <div :style="`background-color: ${color}`" />
</template>

<style scoped>
div {
  width: 100%;
  padding: 50%;
  margin: auto;
  border-radius: 0.25rem;
  border-color: gray;
  border-width: 1px;
}
</style>
