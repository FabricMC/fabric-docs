<script setup lang="ts">
import VPLink from "vitepress/dist/client/theme-default/components/VPLink.vue";
import { VPIcon } from "vitepress/theme";

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
  <ul :style="{ '--grid-columns': Math.min(choices.length, 3) }">
    <li v-for="(c, key) of choices" :key>
      <VPLink :href="c.href" :style="{ '--color': c.color }">
        <VPIcon v-if="c.icon" :icon="c.icon" />
        {{ c.name }}
      </VPLink>
    </li>
  </ul>
</template>

<style scoped>
ul {
  display: grid;
  grid-template-columns: repeat(var(--grid-columns, 1), 1fr);
  gap: 1rem;

  padding: 0;

  list-style: none;

  @media (width <= 48rem) {
    grid-template-columns: 1fr;
  }
}

li + li {
  margin-top: revert;
}

.VPLink {
  overflow: hidden;
  display: flex;
  gap: 1rem;
  align-items: center;

  width: 100%;
  height: 100%;
  padding: 1rem;
  border: 1px solid var(--vp-c-border);
  border-radius: 0.625rem;

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

  [class^="vpi-"] {
    font-size: 3rem;
  }
}

a.VPLink:hover,
a.VPLink:focus-visible {
  transform: translateY(-0.25rem);
  border-color: var(--color, var(--vp-c-brand-1));
  color: var(--color, var(--vp-c-brand-1));
  box-shadow: 0 6px 16px rgb(0 0 0 / 12%);
}

span.VPLink {
  cursor: not-allowed;
  opacity: 75%;
}
</style>
