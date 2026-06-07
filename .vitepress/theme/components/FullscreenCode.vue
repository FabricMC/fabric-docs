<script setup lang="ts">
import { getIcon, Icon, loadIcon } from "@iconify/vue";
import { usePreferredReducedMotion } from "@vueuse/core";
import { onContentUpdated, useData } from "vitepress";
import { computed, nextTick, onUnmounted, ref } from "vue";
import { Fabric } from "../../types";

const prefersReducedMotion = usePreferredReducedMotion();
const data = useData();
const options = computed(() => data.theme.value.code as Fabric.CodeOptions);

const dialog = ref<HTMLDialogElement>();
const title = ref("");
const isClosing = ref(false);
const isWrapped = ref(false);
const isCopied = ref(false);

const processedCodeBlocks = new WeakSet<HTMLDivElement>();
let originalCopyButton: HTMLButtonElement | null = null;

const handleEnterFullscreen = async (originalCodeBlock: HTMLDivElement) => {
  await nextTick();
  if (!dialog.value) return;

  originalCopyButton = //
    originalCodeBlock.querySelector<HTMLButtonElement>("button.copy:not(.fullscreen)");

  const originalCodeGroupTab = originalCodeBlock
    .closest(".vp-code-group")
    ?.querySelector<HTMLInputElement>("div.tabs input:checked");
  title.value = originalCodeGroupTab?.labels?.[0].textContent ?? "";

  const clonedCodeBlock = originalCodeBlock.cloneNode(true) as HTMLDivElement;
  clonedCodeBlock.querySelector<HTMLDivElement>("div.line-numbers-wrapper")?.remove();
  clonedCodeBlock.querySelectorAll<HTMLButtonElement>("button.copy").forEach((b) => b.remove());

  const slot = dialog.value.querySelector<HTMLDivElement>("div.slot");
  slot?.replaceChildren(clonedCodeBlock);
  dialog.value.showModal();
};

const handleCopy = (event: Event) => {
  (event.currentTarget as HTMLButtonElement).blur();
  originalCopyButton?.click();
  isCopied.value = true;
  setTimeout(() => (isCopied.value = false), 2000);
};

const handleWrap = (event: Event) => {
  (event.currentTarget as HTMLButtonElement).blur();
  isWrapped.value = !isWrapped.value;
};

const handleExitFullscreen = (event: Event) => {
  (event.currentTarget as HTMLButtonElement)?.blur();
  isClosing.value = true;

  const onAnimationend = () => {
    isClosing.value = false;
    dialog.value?.close();
  };

  if (prefersReducedMotion.value === "reduce") return onAnimationend();

  dialog.value?.addEventListener("animationend", onAnimationend, { once: true });
};

onContentUpdated(() =>
  nextTick(async () => {
    if (!dialog.value) return;

    await loadIcon("lucide:maximize-2");
    dialog.value.close();

    document.documentElement.style.setProperty(
      "--vp-code-copy-copied-text-content",
      JSON.stringify(options.value.copied)
    );

    const enterFullscreenIconData = getIcon("lucide:maximize-2");
    const enterFullscreenIcon = enterFullscreenIconData
      ? `<svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24">${enterFullscreenIconData.body}</svg>`
      : "";

    const codeBlocks = //
      document.querySelectorAll<HTMLDivElement>(".vp-doc:not(.slot) div[class*='language-']");

    for (const codeBlock of codeBlocks) {
      if (processedCodeBlocks.has(codeBlock)) continue;

      const copyButton = codeBlock.querySelector<HTMLButtonElement>("button.copy:not(.fullscreen)");
      if (copyButton) {
        copyButton.title = options.value.copy;
        copyButton.setAttribute("aria-label", options.value.copy);
      }

      const enterFullscreenButton = document.createElement("button");
      enterFullscreenButton.title = options.value.enterFullscreen;
      enterFullscreenButton.setAttribute("aria-label", options.value.enterFullscreen);
      enterFullscreenButton.className = "copy fullscreen";
      enterFullscreenButton.innerHTML = enterFullscreenIcon;
      enterFullscreenButton.addEventListener("click", (e) => {
        e.stopImmediatePropagation();
        enterFullscreenButton.blur();
        handleEnterFullscreen(codeBlock);
      });

      codeBlock.prepend(enterFullscreenButton);
      processedCodeBlocks.add(codeBlock);
    }
  })
);

onUnmounted(() => dialog.value?.close());
</script>

<template>
  <dialog
    ref="dialog"
    id="fullscreen"
    aria-modal="true"
    :class="{ closing: isClosing }"
    @click.self="handleExitFullscreen"
    @cancel.prevent="handleExitFullscreen"
  >
    <div class="toolbar">
      <h2 v-if="title">{{ title }}</h2>
      <button
        class="copy"
        :class="{ copied: isCopied }"
        :title="options.copy"
        :aria-label="options.copy"
        @click="handleCopy"
      >
        <Icon v-if="isCopied" icon="lucide:clipboard-check" />
        <Icon v-else icon="lucide:clipboard" />
      </button>
      <button class="wrap" :title="options.wrap" :aria-label="options.wrap" @click="handleWrap">
        <Icon v-if="isWrapped" style="width: 24px; height: 24px" icon="lucide:text" />
        <Icon v-else icon="lucide:text-wrap" />
      </button>
      <button
        class="fullscreen"
        :title="options.exitFullscreen"
        :aria-label="options.exitFullscreen"
        @click="handleExitFullscreen"
      >
        <Icon icon="lucide:minimize-2" />
      </button>
    </div>
    <div class="slot vp-doc" :class="{ wrapped: isWrapped }" />
  </dialog>
</template>

<style scoped>
dialog#fullscreen {
  max-height: none;
  max-width: none;
  border: none;
  overflow: hidden;
  background: transparent;
  animation:
    fadeIn 0.3s ease-in,
    scale-in 0.3s ease;

  &::backdrop {
    background-color: var(--vp-c-bg);
    animation: fadeIn 0.3s ease;
  }

  &[open] {
    display: flex;
    flex-direction: column;
    gap: 12px;
  }

  &,
  &::backdrop {
    height: 100vh;
    width: 100vw;
    will-change: transform, opacity;
  }

  &[open].closing {
    pointer-events: none;
    animation:
      fade-out 0.3s ease,
      scale-out 0.3s ease;

    &::backdrop {
      animation: fade-out 0.3s ease;
    }
  }
}

@keyframes fade-in {
  from {
    opacity: 0;
  }
  to {
    opacity: 1;
  }
}

@keyframes fade-out {
  from {
    opacity: 1;
  }
  to {
    opacity: 0;
  }
}

@keyframes scale-in {
  from {
    transform: scale(0.9);
  }
  to {
    transform: scale(1);
  }
}

@keyframes scale-out {
  from {
    transform: scale(1);
  }
  to {
    transform: scale(0.9);
  }
}

div.toolbar {
  display: flex;
  justify-content: flex-end;
  flex-grow: 0;
  gap: 12px;

  h2 {
    display: flex;
    flex-grow: 1;
    align-items: center;
    font-size: 1.25rem;
    font-weight: 600;
    color: var(--vp-code-tab-text-color);
    padding: 8px;
    white-space: nowrap;
    overflow: hidden;
  }

  button {
    display: flex;
    align-items: center;
    justify-content: center;
    border: 1px solid var(--vp-code-copy-code-border-color);
    border-radius: 4px;
    width: 40px;
    height: 40px;
    background-color: var(--vp-code-copy-code-bg);
    color: #808080;
    transition:
      border-color 0.25s,
      background-color 0.25s;

    &:hover,
    &.copy.copied {
      border-color: var(--vp-code-copy-code-hover-border-color);
      background-color: var(--vp-code-copy-code-hover-bg);
    }

    svg {
      height: 20px;
      width: 20px;
    }
  }

  button.copy.copied {
    width: auto;
    padding-right: 9px;
    gap: 9px;
    border-radius: 4px;

    &::before {
      display: inline-flex;
      align-items: center;
      height: 100%;
      padding: 0 10px;
      font-size: 12px;
      font-weight: 500;
      white-space: nowrap;
      color: var(--vp-code-copy-code-active-text);
      border-right: 1px solid var(--vp-code-copy-code-hover-border-color);
      content: var(--vp-code-copy-copied-text-content);
    }
  }
}

/* :deep is needed because clonedCodeBlock does not have the data-v-****** attribute */
:deep(div.slot) {
  border-radius: 12px;
  border: 1px solid var(--vp-c-divider);
  flex-grow: 1;
  overflow: hidden;

  div[class*="language-"] {
    margin: 0;
    padding-left: 0;
    height: 100%;
    display: flex;
    flex-direction: column;
    overflow: hidden;

    span.lang {
      right: 20px;

      :hover & {
        opacity: unset;
      }
    }

    pre {
      flex-grow: 1;
      overflow: auto;
    }

    pre::-webkit-scrollbar-corner {
      background-color: var(--vp-code-block-bg);
    }

    &.line-numbers-mode pre {
      counter-reset: line-counter;
      background-image: linear-gradient(
        to right,
        transparent 31px,
        var(--vp-code-block-divider-color) 31px,
        var(--vp-code-block-divider-color) 32px,
        transparent 32px
      );

      code {
        display: flex;
        flex-direction: column;
        padding-left: 0;
      }

      .line {
        position: relative;
        padding-left: 56px;

        & span::after {
          content: "\200B";
        }
      }

      .line::before {
        content: counter(line-counter);
        counter-increment: line-counter;
        display: inline-block;
        position: sticky;
        z-index: 1;
        left: 0;
        margin-left: -56px;
        margin-right: 12px;
        width: 32px;
        text-align: center;
        user-select: none;
        color: var(--vp-code-line-number-color);
        background-color: var(--vp-code-block-bg);
        border-right: 1px solid var(--vp-code-block-divider-color);
      }

      .line:empty::after {
        content: "\a0";
      }
    }
  }

  &.wrapped div[class*="language-"] pre {
    white-space: pre-wrap;
    overflow-wrap: anywhere;
  }
}
</style>

<style>
div[class*="language-"] {
  button.copy:not(.fullscreen) {
    right: 64px;
  }

  button.copy.fullscreen {
    background-image: unset;

    svg {
      margin: auto;
      color: #808080;
    }
  }
}

html:has(dialog#fullscreen[open]) {
  overflow: hidden;
  scrollbar-gutter: stable;
}
</style>
