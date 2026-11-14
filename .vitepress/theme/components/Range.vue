<script setup lang="ts">
import { VPLink } from "vitepress/theme";
import { computed } from "vue";

const props = defineProps<{ r: string }>();

const href = computed(() => {
  const url = new URL("https://dexman545.github.io/outlet-database/floaderValidator");
  url.searchParams.set("mode", "minecraft");
  url.searchParams.set("p", props.r);

  return url.toString();
});
</script>

<template>
  <VPLink :href>
    <code>{{ r }}</code>
  </VPLink>
</template>
