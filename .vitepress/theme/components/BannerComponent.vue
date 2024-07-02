<script setup lang="ts">
import { useElementSize } from "@vueuse/core";
import { useData, useRoute } from "vitepress";
import { ref, watchEffect } from "vue";

import { Fabric } from "../../types";

const data = useData();
const route = useRoute();

const el = ref<HTMLElement>();
const options = ref<Fabric.BannerOptions>();
const path = ref<string>("");
const text = ref<string[]>([]);

function refreshOptions() {
  if (route.path !== path.value) {
    path.value = route.path;

    options.value = data.theme.value.banner as Fabric.BannerOptions;
    text.value = options.value.text.split("%s", 3);
    while (text.value.length < 3) {
      text.value.push("");
    }
  }
}

function refreshHeight() {
  if (useElementSize(el).height.value) {
    document.documentElement.style.setProperty(
      "--vp-layout-top-height",
      `${useElementSize(el).height.value}px`
    );
  }
}

watchEffect(() => {
  refreshOptions();
  refreshHeight();
});
</script>

<template>
  <div ref="el" class="banner">
    <div class="text">
      {{ text[0] }}
      <a
        href="https://github.com/fabricmc/fabric-docs/issues"
        target="_blank"
        rel="noopener noreferrer"
        >{{ options!.github }}</a
      >
      {{ text[1] }}
      <a
        href="https://discord.gg/v6v4pMv"
        target="_blank"
        rel="noopener noreferrer"
        >{{ options!.discord }}</a
      >{{ text[2] }}
    </div>
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

@media (min-width: 769px) {
  html {
    --vp-layout-top-height: 40px;
  }
}
</style>

<style scoped>
.banner {
  position: fixed;
  top: 0;
  right: 0;
  left: 0;
  z-index: var(--vp-z-index-layout-top);

  font-family: monospace;

  padding: 8px;
  text-align: center;

  background: rgb(207, 114, 21);
  color: #fff;

  display: flex;
  justify-content: space-between;
}

.text {
  flex: 1;
}

a {
  text-decoration: underline;
}
</style>
