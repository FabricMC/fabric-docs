<script setup lang="ts">
import { useElementSize } from "@vueuse/core";
import { useData } from "vitepress";
import { VPLink } from "vitepress/theme";
import { computed, onMounted, ref, watchEffect } from "vue";
import type { ThemeConfig } from "../../types.d.ts";
import { useRem } from "../composables/rem.ts";

const data = useData<ThemeConfig>();
const banner = ref<HTMLDivElement>();
const { height } = useElementSize(banner);
const rem = useRem();

const env = computed(() => data.theme.value.env);
const options = computed(() => data.theme.value.banner);

const strings = computed(() => {
  switch (env.value) {
    case "github":
    case "netlify":
      return [];

    case "build":
      return [options.value.local.build];

    case "dev":
      return [options.value.local.dev];

    default: {
      const split = options.value.pr.text.split("%s").filter(Boolean);

      return [split[0], String(env.value), split.slice(1).join("%s")];
    }
  }
});

onMounted(() =>
  watchEffect(() => {
    document.documentElement.style.setProperty(
      "--vp-layout-top-height",
      `${strings.value.length > 0 ? height.value / rem.value + 1 : 0}rem`
    );
  })
);
</script>

<template>
  <div v-show="strings.length" ref="banner">
    {{ strings[0]
    }}<VPLink
      v-if="strings[1]"
      :href="`https://github.com/FabricMC/fabric-docs/pull/${strings[1]}`"
      >{{ options.pr.link.replace("%d", strings[1]) }}</VPLink
    >{{ strings[2] }}
  </div>
</template>

<style scoped>
div {
  position: fixed;
  z-index: var(--vp-z-index-layout-top);
  right: 0;
  left: 0;

  align-items: center;

  padding: 0.5rem;

  font-family: var(--vp-font-family-mono);
  font-weight: 600;
  color: var(--vp-c-white);
  text-align: center;

  background: rgb(207 114 21);
}

a {
  text-decoration: underline;
}
</style>
