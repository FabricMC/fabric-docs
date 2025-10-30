<script setup lang="ts">
import { useElementSize } from "@vueuse/core";
import { useData } from "vitepress";
import VPLink from "vitepress/dist/client/theme-default/components/VPLink.vue";
import { computed, ref, watch } from "vue";

import { Fabric } from "../../types";

const data = useData();
const banner = ref<HTMLElement>();
const { height } = useElementSize(banner);

const options = computed(() => data.theme.value.banner as Fabric.BannerOptions);

const strings = computed(() => {
  switch (options.value.env) {
    case "github":
      return [];

    case "build":
      return [options.value.local.build];

    case "dev":
      return [options.value.local.dev];

    default: {
      const split = options.value.pr.text.split("%s").filter(Boolean);
      return [split[0], String(options.value.env), split.slice(1).join("%s")];
    }
  }
});

watch([height, strings], () =>
  document.documentElement.style.setProperty(
    "--vp-layout-top-height",
    `${strings.value.length ? height.value + 16 : 0}px`
  )
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
