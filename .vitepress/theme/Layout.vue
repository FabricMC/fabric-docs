<script setup lang="ts">
import { useEventListener } from "@vueuse/core";
import mediumZoom, { type Zoom } from "medium-zoom";
import { inBrowser, onContentUpdated, useRouter } from "vitepress";
import DefaultTheme from "vitepress/theme";
import { nextTick } from "vue";
import Authors from "./layout/Authors.vue";
import Banner from "./layout/Banner.vue";
import FullscreenCode from "./layout/FullscreenCode.vue";
import NotFound from "./layout/NotFound.vue";
import References from "./layout/References.vue";

const router = useRouter();

// Replace data-gen head script, which updates head tags
router.onAfterRouteChange = () => {
  const oldScript = document.querySelector("script[data-gen]");
  if (!oldScript) {
    return;
  }

  const newScript = document.createElement("script");
  newScript.innerHTML = oldScript.innerHTML;
  newScript.setAttribute("data-gen", "");
  oldScript.parentNode!.replaceChild(newScript, oldScript);
};

let zoom: Zoom;
const attachZoom = () => {
  if (!inBrowser) {
    return;
  }

  zoom ||= mediumZoom({ background: "var(--vp-c-bg)" });
  zoom.attach(".vp-doc img");
};

onContentUpdated(attachZoom);

useEventListener("click", (event) => {
  if (!(event.target instanceof HTMLElement)) {
    return;
  }

  // TODO: reuse this for FabricMC/fabric-docs#663
  if (event.target.closest(".plugin-tabs--tab")) {
    nextTick(attachZoom);
  }
});
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
