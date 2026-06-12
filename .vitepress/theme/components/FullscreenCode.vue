<script setup lang="ts">
import { Icon, loadIcon } from "@iconify/vue";
import { usePreferredReducedMotion } from "@vueuse/core";
import { onContentUpdated, useData } from "vitepress";
import { computed, nextTick, onUnmounted, ref } from "vue";
import { Fabric } from "../../types";

const prefersReducedMotion = usePreferredReducedMotion();
const data = useData();
const options = computed(() => data.theme.value.code as Fabric.CodeOptions);

const dialog = ref<HTMLDialogElement>();
const originalCopyButton = ref<HTMLButtonElement>();

const originalTabs = ref<HTMLInputElement[]>([]);
const originalCodeBlocks = ref<HTMLDivElement[]>([]);

const isClosing = ref(false);
const isWrapped = ref(false);
const isCopied = ref(false);

const loadCodeBlock = (originalCodeBlock: HTMLDivElement) => {
  if (!dialog.value) return;

  originalCopyButton.value = //
    originalCodeBlock.querySelector<HTMLButtonElement>("button.copy:not(.fullscreen)") ?? undefined;

  const clonedCodeBlock = originalCodeBlock.cloneNode(true) as HTMLDivElement;
  clonedCodeBlock.style.viewTransitionName = "code-block-view-transition";
  clonedCodeBlock.querySelector<HTMLDivElement>("div.line-numbers-wrapper")?.remove();
  clonedCodeBlock.querySelectorAll<HTMLButtonElement>("button.copy").forEach((b) => b.remove());

  const onViewTransition = () => {
    dialog.value?.querySelector<HTMLDivElement>("div.slot")?.replaceChildren(clonedCodeBlock);
  };

  if (prefersReducedMotion.value === "reduce") return onViewTransition();

  document.startViewTransition(onViewTransition);
};

const handleEnterFullscreen = (originalCodeBlock: HTMLDivElement) => {
  if (!dialog.value) return;

  const originalCodeGroup = originalCodeBlock.closest<HTMLDivElement>("div.vp-code-group");
  if (originalCodeGroup) {
    originalTabs.value = [
      ...originalCodeGroup.querySelectorAll<HTMLInputElement>("div.tabs > input[type='radio']"),
    ];
    originalCodeBlocks.value = [
      ...originalCodeGroup.querySelectorAll<HTMLDivElement>("div.blocks div[class*='language-']"),
    ];
  }

  loadCodeBlock(originalCodeBlock);
  dialog.value.showModal();
};

const handleTabChange = (index: number) => {
  if (!originalTabs.value[index] || !originalCodeBlocks.value[index]) return;

  originalTabs.value[index].click();
  loadCodeBlock(originalCodeBlocks.value[index]);
};

const handleCopy = (event: Event) => {
  if (event.currentTarget instanceof HTMLButtonElement) event.currentTarget.blur();
  originalCopyButton.value?.click();
  isCopied.value = true;
  setTimeout(() => (isCopied.value = false), 2000);
};

const handleWrap = (event: Event) => {
  if (event.currentTarget instanceof HTMLButtonElement) event.currentTarget.blur();
  isWrapped.value = !isWrapped.value;
};

const handleExitFullscreen = (event?: Event) => {
  if (event?.currentTarget instanceof HTMLButtonElement) event.currentTarget.blur();
  if (!dialog.value?.open) return;

  dialog.value.focus();
  dialog.value.blur();
  isClosing.value = true;

  const onAnimationend = () => {
    isClosing.value = false;
    originalCopyButton.value = undefined;
    originalTabs.value = [];
    originalCodeBlocks.value = [];

    dialog.value?.close();
    dialog.value?.querySelector<HTMLDivElement>("div.slot")?.replaceChildren();
  };

  if (prefersReducedMotion.value === "reduce") return onAnimationend();

  dialog.value.addEventListener("animationend", onAnimationend, { once: true });
};

onContentUpdated(() =>
  nextTick(async () => {
    if (!dialog.value) return;

    handleExitFullscreen();

    document.documentElement.style.setProperty(
      "--vp-code-copy-copied-text-content",
      JSON.stringify(options.value.copied)
    );

    const enterFullscreenIconData = await loadIcon("lucide:maximize-2");
    const enterFullscreenIcon = `<svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24">${enterFullscreenIconData.body}</svg>`;

    const codeBlocks = //
      document.querySelectorAll<HTMLDivElement>("div.vp-doc:not(.slot) div[class*='language-']");

    for (const codeBlock of codeBlocks) {
      const originalCopyButton = //
        codeBlock.querySelector<HTMLButtonElement>("button.copy:not(.fullscreen)");
      if (!originalCopyButton) continue;

      originalCopyButton.title = options.value.copy;
      originalCopyButton.setAttribute("aria-label", options.value.copy);

      const enterFullscreenButton = document.createElement("button");
      enterFullscreenButton.title = options.value.enterFullscreen;
      enterFullscreenButton.setAttribute("aria-label", options.value.enterFullscreen);
      enterFullscreenButton.className = "copy fullscreen";
      enterFullscreenButton.innerHTML = enterFullscreenIcon;
      enterFullscreenButton.addEventListener("click", (event) => {
        if (!(event.currentTarget instanceof HTMLButtonElement)) return;

        event.stopImmediatePropagation();
        event.currentTarget.blur();

        const codeBlock = event.currentTarget.closest<HTMLDivElement>("div[class*='language-']");
        if (!codeBlock) return;

        handleEnterFullscreen(codeBlock);
      });

      codeBlock.querySelector<HTMLButtonElement>("button.copy.fullscreen")?.remove();
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
        <template v-for="(tab, i) in originalTabs" :key="i">
          <input
            type="radio"
            :name="`dialog-fullscreen-${tab.name}`"
            :id="`dialog-fullscreen-${tab.id}`"
            :checked="tab.checked"
            @change="handleTabChange(i)"
          />
          <label :for="`dialog-fullscreen-${tab.id}`">{{ tab.labels?.[0]?.textContent }}</label>
        </template>
      </div>
      <button
        class="copy"
        :class="{ copied: isCopied }"
        :title="options.copy"
        :aria-label="options.copy"
        @click="handleCopy"
      >
        <span>{{ options.copied }}</span>
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
    <div class="slot vp-doc" :class="{ wrapped: isWrapped, tabbed: originalTabs.length }" />
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
    fade-in 0.3s ease-in,
    scale-in 0.3s ease;

  &,
  &::backdrop {
    height: 100dvh;
    width: 100dvw;
  }

  &::backdrop {
    background-color: var(--vp-c-bg);
    animation: fade-in 0.3s ease;
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
  align-items: flex-end;
  flex-grow: 0;
  gap: 8px;

  div.tabs {
    margin: -1px 0 -1px 0;
    height: calc(100% + 2px);
    display: flex;
    align-items: flex-end;
    padding: 0 12px;
    flex-grow: 1;
    font-size: 14px;
    font-weight: 500;
    color: var(--vp-code-tab-text-color);
    background-color: var(--vp-code-tab-bg);
    border: 1px solid var(--vp-c-divider);
    box-shadow: inset 0 -1px var(--vp-code-block-divider-color);
    border-bottom: none;
    border-radius: 12px 12px 0 0;
    white-space: nowrap;
    overflow-x: auto;
    overflow-y: hidden;
    z-index: 10;

    &::-webkit-scrollbar {
      scrollbar-width: thin;
    }

    &::-webkit-scrollbar-track {
      box-shadow: 0 -1px var(--vp-code-block-divider-color);
      z-index: 10;
    }

    &::-webkit-scrollbar-thumb {
      margin-bottom: 1px;
    }

    &:deep(label) {
      position: relative;
      height: 100%;
      line-height: 48px;
      padding: 0 12px;
      cursor: pointer;
      text-align: center;

      &:hover,
      &:focus {
        color: var(--vp-code-tab-hover-text-color);
      }

      &::after {
        content: "";
        position: absolute;
        bottom: 0;
        left: 0;
        display: block;
        width: 100%;
        height: 2px;
        border-radius: 2px;
        background-color: transparent;
        transition: background-color 0.25s;
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
    align-items: center;
    justify-content: center;
    border: 1px solid var(--vp-code-copy-code-border-color);
    border-radius: 4px;
    width: 40px;
    height: 40px;
    flex-shrink: 0;
    margin-bottom: 8px;
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

    svg.iconify {
      height: 20px;
      width: 20px;
    }

    &.copy {
      width: auto;
      min-width: 40px;
      gap: 0;

      svg.iconify {
        margin-left: 8px;
        margin-right: 9px;
      }

      & span {
        display: inline-flex;
        align-items: center;
        overflow: hidden;
        white-space: nowrap;
        height: 100%;
        max-width: 0;
        font-size: 12px;
        font-weight: 500;
        color: var(--vp-code-copy-code-active-text);
        border-right: 1px solid transparent;
        transition:
          max-width 0.3s ease,
          padding-inline 0.3s ease,
          border-color 0.3s ease;
      }

      &.copied span {
        max-width: 100px;
        padding-inline: 9px;
        border-right-color: var(--vp-code-copy-code-hover-border-color);
      }
    }
  }
}

:deep(div.slot) {
  background-color: var(--vp-code-block-bg);
  border-radius: 12px;
  border: 1px solid var(--vp-c-divider);
  flex-grow: 1;
  overflow: hidden;

  div[class*="language-"] {
    background: transparent;
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
      overscroll-behavior: none;

      code {
        display: flex;
        flex-direction: column;
        padding-left: 0;
      }

      span.line {
        position: relative;
        padding-left: 56px;

        & span::after {
          content: "\200B";
        }
      }

      span.line::before {
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

      span.line:empty::after {
        content: "\a0";
      }
    }
  }

  &.tabbed {
    border-top-left-radius: 0;
  }

  &.wrapped div[class*="language-"] pre {
    white-space: pre-wrap;
    overflow-wrap: anywhere;
  }
}
</style>

<style>
::view-transition-group(code-block-view-transition) {
  border-radius: 12px;
  overflow: hidden;
}

::view-transition-group(root) {
  animation: none;
}

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
