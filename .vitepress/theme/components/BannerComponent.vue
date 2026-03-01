<script setup lang="ts">
import { useElementSize } from "@vueuse/core";
import { useData } from "vitepress";
import VPLink from "vitepress/dist/client/theme-default/components/VPLink.vue";
import { computed, ref, watch } from "vue";

import { Fabric } from "../../types";

const data = useData();
const banner = ref<HTMLElement>();
const { height } = useElementSize(banner);

const env = computed(() => data.theme.value.env as Fabric.EnvOptions);
const options = computed(() => data.theme.value.banner as Fabric.BannerOptions);

const strings = computed(() => {
  switch (env.value) {
    case "github":
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

watch(height, () =>
  document.documentElement.style.setProperty("--vp-layout-top-height", `${height.value + 16}px`)
);
</script>

<template>
  <div v-if="strings.length" ref="banner">
    {{ strings[0]
    }}<VPLink
      v-if="strings[1]"
      :href="`https://github.com/FabricMC/fabric-docs/pull/${strings[1]}`"
      >{{ options.pr.link.replace("%d", strings[1]) }}</VPLink
    >{{ strings[2] }}
  </div>
</template>

<style>
html {
  --vp-layout-top-height: 88px;
}

@media (min-width: 375px) {
  html {
    --vp-layout-top-height: 64px;
  }
}

@media (min-width: 768px) {
  html {
    --vp-layout-top-height: 40px;
  }
}
</style>

<style scoped>
div {
  align-items: center;
  background: rgb(207, 114, 21);
  color: #ffffff;
  font-family: monospace;
  font-weight: 600;
  left: 0;
  padding: 8px;
  position: fixed;
  right: 0;
  text-align: center;
  z-index: var(--vp-z-index-layout-top);
}

a {
  text-decoration: underline;
}
</style>
