<script setup lang="ts">
import { useData } from "vitepress";
import VPLink from "vitepress/dist/client/theme-default/components/VPLink.vue";
import { computed } from "vue";

const props = defineProps<{ r: string }>();

const data = useData();

const href = computed(() => `https://dexman545.github.io/outlet-database/floaderValidator?mode=minecraft&p=${encodeURIComponent(props.r)}`);
</script>

<template>
  <VPLink :href
    ><code>{{ r }}</code></VPLink
  >
</template>
