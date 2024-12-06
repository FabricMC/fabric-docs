<script setup>
const { color } = defineProps({
  color: String,
});
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
  border-color: grey;
  border-width: 1px;
}
</style>
