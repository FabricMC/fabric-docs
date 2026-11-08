<script setup lang="ts">
import mediumZoom from "medium-zoom";
import { inBrowser, useRouter } from "vitepress";
import DefaultTheme from "vitepress/theme";
import { nextTick, watch } from "vue";
import Authors from "./layout/Authors.vue";
import Banner from "./layout/Banner.vue";
import FullscreenCode from "./layout/FullscreenCode.vue";
import NotFound from "./layout/NotFound.vue";
import References from "./layout/References.vue";

const router = useRouter();

// Replace data-gen head script, which updates head tags
router.onAfterRouteChange = () => {
  const oldScript = document.querySelector("script[data-gen]");
  if (!oldScript) return;

  const newScript = document.createElement("script");
  newScript.innerHTML = oldScript.innerHTML;
  newScript.setAttribute("data-gen", "");
  oldScript.parentNode!.replaceChild(newScript, oldScript);
};

watch(
  () => router.route.path,
  () =>
    nextTick(() => {
      if (!inBrowser) return;
      mediumZoom(".main img", { background: "var(--vp-c-bg)" });
    }),
  { immediate: true }
);
</script>

<template>
  <DefaultTheme.Layout>
    <template #doc-footer-before>
      <References />
    </template>

    <template #doc-before>
      <Authors />
    </template>

    <template #aside-outline-after>
      <Authors />
      <References />
    </template>

    <template #not-found>
      <NotFound />
    </template>

    <template #layout-top>
      <Banner />
    </template>

    <template #layout-bottom>
      <FullscreenCode />
    </template>
  </DefaultTheme.Layout>
</template>
