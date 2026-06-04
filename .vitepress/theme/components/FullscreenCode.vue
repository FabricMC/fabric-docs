<script setup lang="ts">
import { getIcon, loadIcon } from "@iconify/vue";
import { onContentUpdated, useData } from "vitepress";
import { computed, nextTick, onMounted, onUnmounted, ref } from "vue";
import { Fabric } from "../../types";

const data = useData();
const options = computed(() => data.theme.value.code as Fabric.CodeOptions);

const fullscreenSlot = ref<HTMLDivElement>();
const isFullscreen = ref(false);
const isWrapped = ref(false);

let originalCodeBlock: HTMLElement | null = null;
let originalPlaceholder: HTMLElement | null = null;

const icons = [
  "material-symbols:close-fullscreen-rounded",
  "material-symbols:open-in-full-rounded",
  "material-symbols:wrap-text-rounded",
  "material-symbols:format-text-overflow-rounded",
] as const;

const getSvgIcon = (name: (typeof icons)[number]) => {
  const iconData = getIcon(name);
  return iconData
    ? `<svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24">${iconData.body}</svg>`
    : "";
};

const closeFullscreen = () => {
  if (!isFullscreen.value || !originalCodeBlock) return;

  isFullscreen.value = false;
  document.body.style.overflow = "";

  const codeBlock = originalCodeBlock;
  const placeholder = originalPlaceholder;
  originalCodeBlock = originalPlaceholder = null;

  const fullscreenButton = codeBlock.querySelector<HTMLButtonElement>("button.fullscreen");
  if (fullscreenButton) {
    fullscreenButton.title = options.value.enterFullscreen;
    fullscreenButton.setAttribute("aria-label", options.value.enterFullscreen);
    fullscreenButton.innerHTML = getSvgIcon("material-symbols:open-in-full-rounded");
  }

  codeBlock.querySelector<HTMLButtonElement>("button.wrap")?.remove();
  codeBlock.classList.remove("wrap");

  placeholder?.parentNode?.replaceChild(codeBlock, placeholder);
};

const openFullscreen = async (codeBlock: HTMLElement) => {
  if (originalCodeBlock) closeFullscreen();

  originalCodeBlock = codeBlock;
  isFullscreen.value = true;
  document.documentElement.style.scrollbarGutter = "stable";
  document.body.style.overflow = "hidden";
  await nextTick();

  const rect = codeBlock.getBoundingClientRect();
  originalPlaceholder = document.createElement("div");
  originalPlaceholder.className = "placeholder";
  originalPlaceholder.style.width = `${rect.width}px`;
  originalPlaceholder.style.height = `${rect.height}px`;

  codeBlock.parentNode?.replaceChild(originalPlaceholder, codeBlock);

  const fullscreenButton = codeBlock.querySelector<HTMLButtonElement>("button.fullscreen");
  if (fullscreenButton) {
    fullscreenButton.title = options.value.exitFullscreen;
    fullscreenButton.setAttribute("aria-label", options.value.exitFullscreen);
    fullscreenButton.innerHTML = getSvgIcon("material-symbols:close-fullscreen-rounded");
  }

  const wrapButton = document.createElement("button");
  wrapButton.title = options.value.wrap;
  wrapButton.setAttribute("aria-label", options.value.wrap);
  wrapButton.className = "copy wrap";
  wrapButton.innerHTML = getSvgIcon(
    isWrapped.value
      ? "material-symbols:format-text-overflow-rounded"
      : "material-symbols:wrap-text-rounded"
  );
  wrapButton.addEventListener("click", (e) => {
    e.stopImmediatePropagation();
    isWrapped.value = !isWrapped.value;
    wrapButton.innerHTML = getSvgIcon(
      isWrapped.value
        ? "material-symbols:format-text-overflow-rounded"
        : "material-symbols:wrap-text-rounded"
    );
    codeBlock.classList.toggle("wrap", isWrapped.value);
  });
  codeBlock.prepend(wrapButton);

  fullscreenSlot.value!.replaceChildren(codeBlock);
};

const setupFullscreen = async () => {
  if (isFullscreen.value) closeFullscreen();

  await Promise.all(icons.map((i) => loadIcon(i)));
  await nextTick();

  const codeBlocks = document.querySelectorAll<HTMLDivElement>(
    '.VPContent .vp-doc div[class*="language-"]'
  );

  for (const codeBlock of codeBlocks) {
    const copyButton = codeBlock.querySelector<HTMLButtonElement>(
      "button.copy:not(.fullscreen, .wrap)"
    );
    if (copyButton) {
      copyButton.title = options.value.copy;
      copyButton.setAttribute("aria-label", options.value.copy);
    }

    codeBlock.querySelector<HTMLButtonElement>("button.fullscreen")?.remove();

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
  min-height: min(95vh, 10rlh);
  min-width: min(95vw, 80ch);
  max-height: 95vh;
  max-width: 95vw;
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

  div[class*="language-"] pre::-webkit-scrollbar-corner {
    background: transparent;
  }

  div[class*="language-"].line-numbers-mode {
    padding-left: 0;

    .line-numbers-wrapper {
      display: none;
    }

    pre {
      counter-reset: line-counter;
    }

    pre code {
      display: flex;
      flex-direction: column;
      padding-left: 0;
    }

    pre .line {
      position: relative;
      padding-left: 56px;
    }

    pre .line::before {
      content: counter(line-counter);
      counter-increment: line-counter;
      position: absolute;
      left: 0;
      top: 0;
      bottom: 0;
      width: 32px;
      text-align: center;
      user-select: none;
      color: var(--vp-code-line-number-color);
      border-right: 1px solid var(--vp-code-block-divider-color);
    }

    pre .line:empty::after {
      content: "\a0";
    }
  }

  div[class*="language-"].wrap pre {
    white-space: pre-wrap;
    overflow-wrap: anywhere;
  }
}
</style>

<style>
div[class*="language-"] {
  button.copy:not(.fullscreen, .wrap) {
    right: 60px;
  }

  button.copy.fullscreen,
  button.copy.wrap {
    background-image: unset;

    svg {
      margin: auto;
      color: #808080;
    }
  }

  button.copy.wrap {
    right: 72px;

    + button.fullscreen {
      right: 24px;

      + button.copy {
        right: 120px;
      }
    }
  }
}

div.placeholder {
  background-color: var(--vp-code-block-bg);
  border-radius: 8px;
}
</style>
