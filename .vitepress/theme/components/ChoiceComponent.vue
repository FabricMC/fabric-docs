<script setup lang="ts">
import { computed } from "vue";

const props = defineProps<{
  choices: {
    name: string;
    text?: string;
    href?: string;
    image?: string;
    rel?: string;
    target?: string;
  }[];
  columns?: number;
}>();

const columns = computed(
  () => props.columns ?? Math.min(props.choices.length, 3)
);
</script>

<template>
  <div class="choices" :style="{ '--grid-columns': columns }">
    <component
      :is="choice.href ? 'a' : 'div'"
      v-for="(choice, key) in choices"
      :key
      class="choice"
      :href="choice.href"
      :target="choice.target"
      :rel="choice.rel"
    >
      <img
        v-if="choice.image"
        :src="choice.image"
        :alt="choice.name"
        class="choice-image"
      />
      <div class="choice-content">
        <h3 class="choice-name">{{ choice.name }}</h3>
        <p v-if="choice.text" class="choice-text">{{ choice.text }}</p>
      </div>
    </component>
  </div>
</template>

<style scoped>
.choices {
  display: grid;
  grid-template-columns: repeat(var(--grid-columns), 1fr);
  gap: 1.25rem;
}

.choice {
  display: flex;
  flex-direction: row;
  align-items: stretch;
  background-color: var(--vp-c-bg-soft);
  border: 1px solid var(--vp-c-border);
  border-radius: 10px;
  overflow: hidden;
  text-decoration: none;
  transition: transform 0.25s ease, box-shadow 0.25s ease,
    border-color 0.25s ease, background-color 0.25s ease;
}

a.choice:hover,
a.choice:focus-visible {
  transform: translateY(-4px);
  box-shadow: 0 6px 16px rgba(0, 0, 0, 0.12);
  border-color: var(--vp-brand-1);
}

div.choice {
  cursor: not-allowed;
  opacity: 0.75;
}

.choice-image {
  aspect-ratio: 1 / 1;
  flex: 0 0 100px;
  max-width: 100px;
  object-fit: contain;
  padding: 1rem;
  pointer-events: none;
}

.choice-content {
  flex: 1;
  padding: 1rem 1rem 1rem 0;
  display: flex;
  flex-direction: column;
  justify-content: center;
}

.choice-name {
  font-size: 1.1rem;
  font-weight: 600;
  margin: 0;
  color: var(--vp-c-text-1);
}

.choice-text {
  font-size: 0.95rem;
  color: var(--vp-c-text-2);
  line-height: 1.5;
  margin: 0;
}

@media (max-width: 768px) {
  .choices {
    grid-template-columns: 1fr;
  }
}
</style>
