<script setup lang="ts">
import { Icon } from "@iconify/vue";
import VPLink from "vitepress/dist/client/theme-default/components/VPLink.vue";

defineProps<{
  choices: {
    name: string;
    icon?: string;
    color?: string;
    href?: string;
  }[];
}>();
</script>

<template>
  <div :style="{ '--grid-columns': Math.min(choices.length, 3) }">
    <VPLink v-for="(c, key) in choices" :href="c.href" :style="{ '--color': c.color }" :key>
      <Icon v-if="c.icon" :icon="c.icon" width="48" />
      {{ c.name }}
    </VPLink>
  </div>
</template>

<style scoped>
div {
  display: grid;
  gap: 1rem;
  grid-template-columns: repeat(var(--grid-columns), 1fr);
}

@media (max-width: 768px) {
  div {
    grid-template-columns: 1fr;
  }
}

.VPLink {
  align-items: center;
  background-color: var(--vp-c-bg-soft);
  border-radius: 10px;
  border: 1px solid var(--vp-c-border);
  color: var(--vp-c-text-1);
  display: flex;
  font-size: 1.1rem;
  font-weight: 600;
  gap: 1rem;
  overflow: hidden;
  padding: 1rem;
  text-decoration: none;
  transition:
    transform 0.25s ease,
    box-shadow 0.25s ease,
    border-color 0.25s ease,
    color 0.25s ease;

  .iconify {
    min-width: 48px;
  }
}

a.VPLink:hover,
a.VPLink:focus-visible {
  border-color: var(--color, var(--vp-brand-1));
  box-shadow: 0 6px 16px rgba(0, 0, 0, 0.12);
  color: var(--color, var(--vp-brand-1));
  transform: translateY(-4px);
}

span.VPLink {
  cursor: not-allowed;
  opacity: 0.75;
}
</style>
