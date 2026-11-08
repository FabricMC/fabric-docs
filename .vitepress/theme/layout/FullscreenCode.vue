<script setup lang="ts">
import { usePreferredReducedMotion } from "@vueuse/core";
import { onContentUpdated, useData } from "vitepress";
import { VPIcon } from "vitepress/theme";
import { computed, nextTick, onUnmounted, ref } from "vue";
import type { ThemeConfig } from "../../types.d.ts";
import { useIconSpan } from "../composables/iconSpan.ts";

const prefersReducedMotion = usePreferredReducedMotion();
const data = useData<ThemeConfig>();
const markdown = computed(() => data.site.value.locales[data.localeIndex.value].markdown!);

const options = computed(() => data.theme.value.code);
const copyOptions = computed(() => markdown.value.codeCopyButton!);

const icon = useIconSpan("lucide:maximize-2");

const dialog = ref<HTMLDialogElement>();
const originalCopyButton = ref<HTMLButtonElement>();

const originalTabs = ref<HTMLInputElement[]>([]);
const originalCodeBlocks = ref<HTMLDivElement[]>([]);

const isClosing = ref(false);
const isWrapped = ref(false);
const isCopied = ref(false);

const loadCodeBlock = async (originalCodeBlock: HTMLDivElement) => {
  if (!dialog.value) {
    return;
  }

  originalCopyButton.value =
    originalCodeBlock.querySelector<HTMLButtonElement>("button.copy") || undefined;

  const clonedCodeBlock = originalCodeBlock.cloneNode(true) as HTMLDivElement;
  clonedCodeBlock.style.viewTransitionName = "code-block-view-transition";
  clonedCodeBlock.querySelector<HTMLDivElement>("div.line-numbers-wrapper")?.remove();
  clonedCodeBlock
    .querySelectorAll<HTMLButtonElement>("button.copy, button.fullscreen")
    .forEach((b) => b.remove());

  const onViewTransition = () => {
    dialog.value?.querySelector<HTMLDivElement>("div.slot")?.replaceChildren(clonedCodeBlock);
  };

  if (prefersReducedMotion.value === "reduce" || !document.startViewTransition) {
    return onViewTransition();
  }

  await document.startViewTransition(onViewTransition).ready;
};

const handleEnterFullscreen = async (originalCodeBlock: HTMLDivElement) => {
  if (!dialog.value) {
    return;
  }

  const originalCodeGroup = originalCodeBlock.closest<HTMLDivElement>("div.vp-code-group");
  if (originalCodeGroup) {
    originalTabs.value = [
      ...originalCodeGroup.querySelectorAll<HTMLInputElement>("div.tabs > input[type='radio']"),
    ];
    originalCodeBlocks.value = [
      ...originalCodeGroup.querySelectorAll<HTMLDivElement>("div.blocks div[class*='language-']"),
    ];
  }

  await loadCodeBlock(originalCodeBlock);
  dialog.value.showModal();
  if (document.activeElement instanceof HTMLElement) {
    document.activeElement.blur();
  }
};

const handleTabChange = (index: number) => {
  if (!originalTabs.value[index] || !originalCodeBlocks.value[index]) {
    return;
  }

  originalTabs.value[index].click();
  loadCodeBlock(originalCodeBlocks.value[index]);
};

const handleCopy = () => {
  originalCopyButton.value?.click();
  if (document.activeElement instanceof HTMLElement) {
    document.activeElement.blur();
  }
  isCopied.value = true;
  setTimeout(() => (isCopied.value = false), 2000);
};

const handleWrap = () => {
  if (document.activeElement instanceof HTMLElement) {
    document.activeElement.blur();
  }
  isWrapped.value = !isWrapped.value;
};

const handleExitFullscreen = () => {
  if (!dialog.value?.open) {
    return;
  }

  if (document.activeElement instanceof HTMLElement) {
    document.activeElement.blur();
  }
  isClosing.value = true;

  const onAnimationend = () => {
    isClosing.value = false;
    originalCopyButton.value = undefined;
    originalTabs.value = [];
    originalCodeBlocks.value = [];

    dialog.value?.close();
    dialog.value?.querySelector<HTMLDivElement>("div.slot")?.replaceChildren();
  };

  if (prefersReducedMotion.value === "reduce") {
    return onAnimationend();
  }

  dialog.value.addEventListener("animationend", onAnimationend, { once: true });
};

onContentUpdated(() =>
  nextTick(async () => {
    if (!dialog.value) {
      return;
    }

    handleExitFullscreen();

    const codeBlocks = //
      document.querySelectorAll<HTMLDivElement>("div.vp-doc:not(.slot) div[class*='language-']");

    for (const codeBlock of codeBlocks) {
      const originalCopyButton = codeBlock.querySelector<HTMLButtonElement>("button.copy");
      if (!originalCopyButton) {
        continue;
      }

      const enterFullscreenButton =
        codeBlock.querySelector<HTMLButtonElement>("button.fullscreen")
        || document.createElement("button");
      enterFullscreenButton.title = options.value.enterFullscreen;
      enterFullscreenButton.setAttribute("aria-label", options.value.enterFullscreen);
      enterFullscreenButton.className = "fullscreen";
      enterFullscreenButton.innerHTML = icon;
      enterFullscreenButton.onclick = (event) => {
        if (!(event.currentTarget instanceof HTMLButtonElement)) {
          return;
        }

        event.currentTarget.blur();

        const codeBlock = event.currentTarget.closest<HTMLDivElement>("div[class*='language-']");
        if (!codeBlock) {
          return;
        }

        handleEnterFullscreen(codeBlock);
      };

      codeBlock.prepend(enterFullscreenButton);
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
    @cancel.prevent="handleExitFullscreen"
  >
    <div class="toolbar">
      <div v-if="originalTabs.length" class="tabs">
        <template v-for="(t, i) of originalTabs" :key="i">
          <input
            type="radio"
            :name="`dialog-fullscreen-${t.name}`"
            :id="`dialog-fullscreen-${t.id}`"
            :checked="t.checked"
            @change="handleTabChange(i)"
          />
          <label :for="`dialog-fullscreen-${t.id}`">{{ t.labels?.[0]?.textContent }}</label>
        </template>
      </div>
      <button
        class="copy"
        :class="{ copied: isCopied }"
        :title="copyOptions.tooltipText"
        :aria-label="copyOptions.tooltipText"
        @click="handleCopy"
      >
        <span>{{ copyOptions.copiedText }}</span>
        <VPIcon :icon="isCopied ? 'lucide:clipboard-check' : 'lucide:clipboard'" />
      </button>
      <button
        class="wrap"
        :class="{ wrapped: isWrapped }"
        :title="options.wrap"
        :aria-label="options.wrap"
        @click="handleWrap"
      >
        <VPIcon :icon="isWrapped ? 'lucide:text' : 'lucide:text-wrap'" />
      </button>
      <button
        class="fullscreen"
        :title="options.exitFullscreen"
        :aria-label="options.exitFullscreen"
        @click="handleExitFullscreen"
      >
        <VPIcon icon="lucide:minimize-2" />
      </button>
    </div>
    <div class="slot vp-doc" :class="{ wrapped: isWrapped, tabbed: originalTabs.length }" />
  </dialog>
</template>

<style scoped>
dialog#fullscreen {
  will-change: transform, opacity;

  overflow: hidden;

  max-width: none;
  max-height: none;
  padding: 1rem;
  border: none;

  background: transparent;

  animation:
    fade-in 0.3s ease-in,
    scale-in 0.3s ease;

  &::backdrop {
    background-color: var(--vp-c-bg);
    animation: fade-in 0.3s ease;
  }

  &,
  &::backdrop {
    width: 100dvw;
    height: 100dvh;
  }

  &[open] {
    display: flex;
    flex-direction: column;
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
    opacity: 0%;
  }

  to {
    opacity: 100%;
  }
}

@keyframes fade-out {
  from {
    opacity: 100%;
  }

  to {
    opacity: 0%;
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
  flex-grow: 0;
  gap: 0.5rem;
  align-items: flex-end;
  justify-content: flex-end;

  div.tabs {
    z-index: 10;

    overflow: auto hidden;
    display: flex;
    flex-grow: 1;
    align-items: flex-end;

    height: calc(100% + 0.125rem);
    margin: -1px 0;
    padding: 0 0.75rem;
    border: 1px solid var(--vp-c-divider);
    border-bottom: none;
    border-radius: 0.75rem 0.75rem 0 0;

    font-size: 0.875rem;
    font-weight: 500;
    color: var(--vp-code-tab-text-color);
    white-space: nowrap;

    background-color: var(--vp-code-tab-bg);
    box-shadow: inset 0 -1px var(--vp-code-block-divider-color);

    &::-webkit-scrollbar {
      height: 0.5rem;
    }

    &::-webkit-scrollbar-track {
      z-index: 10;
      box-shadow: 0 -1px var(--vp-code-block-divider-color);
    }

    &::-webkit-scrollbar-thumb {
      margin-bottom: 1px;
    }

    &:deep(label) {
      cursor: pointer;

      position: relative;

      height: 100%;
      padding: 0 0.75rem;

      line-height: 3rem;
      text-align: center;

      &::after {
        content: "";

        position: absolute;
        bottom: 0;
        left: 0;

        display: block;

        width: 100%;
        height: 0.125rem;
        border-radius: 0.125rem;

        background-color: transparent;

        transition: background-color 0.25s;
      }

      &:hover,
      &:focus {
        color: var(--vp-code-tab-hover-text-color);

        &::after {
          background-color: var(--vp-c-divider);
        }
      }
    }

    &:deep(input) {
      display: none;

      &:checked + label {
        color: var(--vp-code-tab-active-text-color);

        &::after {
          background-color: var(--vp-code-tab-active-bar-color);
        }
      }
    }
  }

  button {
    display: flex;
    flex-shrink: 0;
    align-items: center;
    justify-content: center;

    width: 2.5rem;
    height: 2.5rem;
    margin-bottom: 0.5rem;
    border: 1px solid var(--vp-code-copy-code-border-color);
    border-radius: 0.25rem;

    color: var(--vp-code-copy-code-active-text);

    background-color: var(--vp-code-copy-code-bg);

    transition:
      border-color 0.25s,
      background-color 0.25s;

    &:hover,
    &.copy.copied {
      border-color: var(--vp-code-copy-code-hover-border-color);
      background-color: var(--vp-code-copy-code-hover-bg);
    }

    [class^="vpi-"] {
      font-size: 1.25rem;
    }

    &.copy {
      gap: 0;
      width: auto;
      min-width: 2.5rem;

      [class^="vpi-"] {
        margin-right: 9px;
        margin-left: 8px;
      }

      span:not([class^="vpi-"]) {
        overflow: hidden;
        display: inline-flex;
        align-items: center;

        max-width: 0;
        height: 100%;
        border-right: 1px solid transparent;

        font-size: 0.75rem;
        font-weight: 500;
        color: var(--vp-code-copy-code-active-text);
        white-space: nowrap;

        transition:
          max-width 0.3s ease,
          padding-inline 0.3s ease,
          border-color 0.3s ease;
      }

      &.copied span:not([class^="vpi-"]) {
        max-width: 6.25rem;
        padding-inline: 9px;
        border-right-color: var(--vp-code-copy-code-hover-border-color);
      }
    }

    &.wrap.wrapped [class^="vpi-"] {
      font-size: 1.5rem;
    }
  }
}

:deep(div.slot) {
  overflow: hidden;
  flex-grow: 1;

  border: 1px solid var(--vp-c-divider);
  border-radius: 0.25rem;

  background-color: var(--vp-code-block-bg);

  div[class*="language-"] {
    overflow: hidden;
    display: flex;
    flex-direction: column;

    height: 100%;
    margin: 0;
    padding-left: 0;

    background: transparent;

    pre {
      overflow: auto;
      flex-grow: 1;
    }

    pre::-webkit-scrollbar-corner {
      background-color: var(--vp-code-block-bg);
    }

    &.line-numbers-mode pre {
      counter-reset: line-counter;
      overscroll-behavior: none;
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

      span.line {
        position: relative;
        padding-left: 3.5rem;

        &::before {
          content: counter(line-counter);
          counter-increment: line-counter;
          user-select: none;

          position: sticky;
          z-index: 1;
          left: 0;

          display: inline-block;

          width: 2rem;
          margin-right: 0.75rem;
          margin-left: -3.5rem;
          border-right: 1px solid var(--vp-code-block-divider-color);

          color: var(--vp-code-line-number-color);
          text-align: center;

          background-color: var(--vp-code-block-bg);
        }

        span::after {
          content: "\200B";
        }

        &:empty::after {
          content: "\a0";
        }
      }
    }
  }

  &.tabbed {
    border-top-left-radius: 0;
  }

  &.wrapped div[class*="language-"] pre {
    overflow-wrap: anywhere;
    white-space: pre-wrap;
  }
}
</style>
