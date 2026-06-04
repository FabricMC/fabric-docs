<script setup lang="ts">
import { getIcon, loadIcon } from "@iconify/vue";
import { onContentUpdated, useData } from "vitepress";
import { computed, nextTick, onMounted, onUnmounted, ref } from "vue";
import { Fabric } from "../../types";

const data = useData();

const options = computed(() => data.theme.value.code as Fabric.CodeOptions);

const fullscreenSlot = ref<HTMLDivElement>();
const isFullscreen = ref(false);
let originalCodeBlock: HTMLElement | null = null;
let originalPlaceholder: HTMLElement | null = null;

const icons = [
  "material-symbols:close-fullscreen-rounded",
  "material-symbols:open-in-full-rounded",
] as const;

const getSvgIcon = (name: (typeof icons)[number]) => {
  const iconData = getIcon(name);
  return iconData
    ? `<svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24">${iconData.body}</svg>`
    : "";
};

const closeFullscreen = () => {
  isFullscreen.value = false;
  document.body.style.overflow = "";

  const codeBlock = originalCodeBlock;
  const placeholder = originalPlaceholder;
  originalCodeBlock = originalPlaceholder = null;

  if (!codeBlock) return;

  const fullscreenButton = codeBlock.querySelector<HTMLElement>("button.fullscreen");
  if (fullscreenButton) {
    fullscreenButton.title = options.value.enterFullscreen;
    fullscreenButton.setAttribute("aria-label", options.value.enterFullscreen);
    fullscreenButton.innerHTML = getSvgIcon("material-symbols:open-in-full-rounded");
  }

  codeBlock.querySelector<HTMLElement>("button.wrap")?.remove();

  placeholder?.parentNode?.replaceChild(codeBlock, placeholder);
};

const openFullscreen = async (codeBlock: HTMLElement) => {
  originalCodeBlock = codeBlock;
  isFullscreen.value = true;
  document.body.style.overflow = "hidden";
  await nextTick();

  const rect = codeBlock.getBoundingClientRect();
  originalPlaceholder = document.createElement("div");
  originalPlaceholder.className = "placeholder";
  originalPlaceholder.style.width = `${rect.width}px`;
  originalPlaceholder.style.height = `${rect.height}px`;

  codeBlock.parentNode?.replaceChild(originalPlaceholder, codeBlock);

  const fullscreenButton = codeBlock.querySelector<HTMLElement>("button.fullscreen");
  if (fullscreenButton) {
    fullscreenButton.title = options.value.exitFullscreen;
    fullscreenButton.setAttribute("aria-label", options.value.exitFullscreen);
    fullscreenButton.innerHTML = getSvgIcon("material-symbols:close-fullscreen-rounded");
  }

  fullscreenSlot.value!.replaceChildren(codeBlock);
};

const setupFullscreen = async () => {
  if (isFullscreen.value) closeFullscreen();

  await Promise.all(icons.map((i) => loadIcon(i)));
  await nextTick();

  const codeBlocks = document.querySelectorAll<HTMLElement>(
    '.VPContent .vp-doc div[class*="language-"]'
  );

  for (const codeBlock of codeBlocks) {
    const copyButton = codeBlock.querySelector<HTMLElement>("button.copy:not(.fullscreen)");
    if (copyButton) {
      copyButton.title = options.value.copy;
      copyButton.setAttribute("aria-label", options.value.copy);
    }

    codeBlock.querySelector<HTMLElement>("button.fullscreen")?.remove();

    const fullscreenButton = document.createElement("button");
    fullscreenButton.title = options.value.enterFullscreen;
    fullscreenButton.setAttribute("aria-label", options.value.enterFullscreen);
    fullscreenButton.className = "copy fullscreen";
    fullscreenButton.innerHTML = getSvgIcon("material-symbols:open-in-full-rounded");
    fullscreenButton.addEventListener("click", (e) => {
      e.stopImmediatePropagation();
      if (originalCodeBlock === codeBlock) {
        closeFullscreen();
      } else {
        openFullscreen(codeBlock);
      }
    });

    codeBlock.prepend(fullscreenButton);
  }
};

const handleKeydown = (e: KeyboardEvent) => {
  if (e.key === "Escape") closeFullscreen();
};

onMounted(() => {
  window.addEventListener("keydown", handleKeydown);
});

onUnmounted(() => {
  window.removeEventListener("keydown", handleKeydown);
  closeFullscreen();
});

onContentUpdated(() => setupFullscreen());
</script>

<template>
  <div :class="{ 'is-open': isFullscreen }" @click.self="closeFullscreen">
    <div ref="fullscreenSlot" class="vp-doc" />
  </div>
</template>

<style scoped>
div:has(.vp-doc) {
  position: fixed;
  inset: 0;
  opacity: 0;
  pointer-events: none;
  transition: opacity 0.3s ease;
  will-change: opacity;
  background-color: var(--vp-c-bg);
  z-index: 10000;
  display: flex;
  justify-content: center;
  align-items: center;
}

div:has(.vp-doc).is-open {
  opacity: 1;
  pointer-events: auto;
}

:deep(.vp-doc:has(*)) {
  min-height: min(90vh, 10rlh);
  min-width: min(90vw, 80ch);
  max-height: 90vh;
  max-width: 90vw;
  border-radius: 12px;
  border: 1px solid var(--vp-c-divider);
  display: flex;
  overflow: hidden;

  div[class*="language-"] {
    width: 100%;
    margin: 0;
    display: flex;
    overflow: hidden;

    pre {
      flex-grow: 1;
    }

    span.lang {
      right: 20px;
    }
  }
}
</style>

<style>
div[class*="language-"] {
  button.copy.fullscreen {
    background-image: unset;

    svg {
      margin: auto;
      color: #808080;
    }
  }

  button.copy:not(.fullscreen) {
    right: 56px;
  }
}

div.placeholder {
  background-color: var(--vp-code-block-bg);
  border-radius: 8px;
}
</style>
