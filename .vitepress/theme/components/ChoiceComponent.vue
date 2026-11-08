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
  gap: 1rem;
  grid-template-columns: repeat(var(--grid-columns), 1fr);
  padding: 0;
  list-style: none;
}

@media (max-width: 48rem) {
  ul {
    grid-template-columns: 1fr;
  }
}

li + li {
  margin-top: revert;
}

.VPLink {
  width: 100%;
  height: 100%;
  align-items: center;
  background-color: var(--vp-c-bg-soft);
  border-radius: 0.625rem;
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

  [class^="vpi-"] {
    font-size: 3rem;
  }
}

a.VPLink:hover,
a.VPLink:focus-visible {
  border-color: var(--color, var(--vp-brand-1));
  box-shadow: 0 6px 16px rgba(0, 0, 0, 0.12);
  color: var(--color, var(--vp-brand-1));
  transform: translateY(-0.25rem);
}

span.VPLink {
  cursor: not-allowed;
  opacity: 0.75;
}
</style>
