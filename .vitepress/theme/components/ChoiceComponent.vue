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
  grid-template-columns: repeat(var(--grid-columns, 1), 1fr);
  gap: 1rem;

  @media (width <= 768px) {
    grid-template-columns: 1fr;
  }
}

.VPLink {
  overflow: hidden;
  display: flex;
  gap: 1rem;
  align-items: center;

  padding: 1rem;
  border: 1px solid var(--vp-c-border);
  border-radius: 10px;

  font-size: 1.1rem;
  font-weight: 600;
  color: var(--vp-c-text-1);
  text-decoration: none;

  background-color: var(--vp-c-bg-soft);

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
  transform: translateY(-4px);
  border-color: var(--color, var(--vp-c-brand-1));
  color: var(--color, var(--vp-c-brand-1));
  box-shadow: 0 6px 16px rgb(0 0 0 / 12%);
}

span.VPLink {
  cursor: not-allowed;
  opacity: 75%;
}
</style>
