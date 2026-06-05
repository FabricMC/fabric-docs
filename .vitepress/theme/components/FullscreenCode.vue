<script setup lang="ts">
import { getIcon, loadIcon } from "@iconify/vue";
import { onContentUpdated, useData } from "vitepress";
import { computed, nextTick, onMounted, onUnmounted, ref } from "vue";
import { Fabric } from "../../types";

const data = useData();
const options = computed(() => data.theme.value.code as Fabric.CodeOptions);

const fullscreenDialog = ref<HTMLDialogElement>();
const fullscreenSlot = ref<HTMLDivElement>();
const isFullscreen = ref(false);
const isWrapped = ref(false);

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
  if (!isFullscreen.value) return;

  const dialogEl = fullscreenDialog.value;
  if (!dialogEl) return;
  dialogEl.classList.add("is-closing");
  dialogEl.addEventListener(
    "animationend",
    () => {
      isFullscreen.value = false;
      dialogEl.close();
      dialogEl.classList.remove("is-closing");
    },
    { once: true }
  );
};

const openFullscreen = async (codeBlock: HTMLElement) => {
  if (!fullscreenDialog.value) return;

  fullscreenDialog.value!.showModal();
  isFullscreen.value = true;
  isWrapped.value = false;
  await nextTick();

  const clonedBlock = codeBlock.cloneNode(true) as HTMLElement;
  const originalCopyButton = clonedBlock.querySelector<HTMLButtonElement>(
    "button.copy:not(.fullscreen)"
  );
  originalCopyButton!.style.opacity = "0";

  clonedBlock.querySelector<HTMLDivElement>("div.line-numbers-wrapper")?.remove();
  clonedBlock.querySelector<HTMLButtonElement>("button.fullscreen")?.remove();

  const fullscreenButton = fullscreenDialog.value.querySelector(
    "button.fullscreen"
  ) as HTMLButtonElement;
  fullscreenButton.title = options.value.exitFullscreen;
  fullscreenButton.setAttribute("aria-label", options.value.exitFullscreen);
  fullscreenButton.innerHTML = getSvgIcon("material-symbols:close-fullscreen-rounded");
  fullscreenButton.addEventListener("click", () => {
    fullscreenButton.blur();
    closeFullscreen();
  });

  const wrapButton = fullscreenDialog.value.querySelector("button.wrap") as HTMLButtonElement;
  wrapButton.title = options.value.wrap;
  wrapButton.setAttribute("aria-label", options.value.wrap);
  wrapButton.innerHTML = getSvgIcon(
    isWrapped.value
      ? "material-symbols:format-text-overflow-rounded"
      : "material-symbols:wrap-text-rounded"
  );
  wrapButton.addEventListener("click", () => {
    wrapButton.blur();
    isWrapped.value = !isWrapped.value;
    wrapButton.innerHTML = getSvgIcon(
      isWrapped.value
        ? "material-symbols:format-text-overflow-rounded"
        : "material-symbols:wrap-text-rounded"
    );
    clonedBlock.classList.toggle("wrap", isWrapped.value);
  });

  const copyButton = fullscreenDialog.value.querySelector("button.copy") as HTMLButtonElement;
  copyButton.title = options.value.copy;
  copyButton.setAttribute("aria-label", options.value.copy);
  copyButton.addEventListener("click", (e) => {
    e.stopImmediatePropagation();
    copyButton.blur();
    copyButton.classList.add("copied");
    setTimeout(() => copyButton.classList.remove("copied"), 1000);
    originalCopyButton?.click();
  });

  const title = fullscreenDialog.value.querySelector("span.title") as HTMLSpanElement;
  const codeGroup = codeBlock.closest(".vp-code-group");
  if (codeGroup) {
    const tab = codeGroup.querySelector<HTMLInputElement>("div.tabs input:checked");
    if (tab && tab.labels) {
      title.style.opacity = "1";
      title.textContent = tab.labels[0].textContent ?? "";
    }
  }

  const langInPre = clonedBlock.querySelector<HTMLSpanElement>("span.lang");
  if (langInPre) langInPre.style.opacity = "0";
  const lang = fullscreenDialog.value.querySelector("span.lang") as HTMLSpanElement;
  lang.textContent = langInPre && langInPre.textContent != "" ? langInPre.textContent : "txt";

  fullscreenSlot.value?.replaceChildren(clonedBlock);
};

const setupFullscreen = async () => {
  if (isFullscreen.value) closeFullscreen();

  await Promise.all(icons.map((i) => loadIcon(i)));
  await nextTick();

  const codeBlocks = document.querySelectorAll<HTMLDivElement>(
    '.VPContent .vp-doc div[class*="language-"]'
  );

  for (const codeBlock of codeBlocks) {
    const copyButton = codeBlock.querySelector<HTMLButtonElement>("button.copy:not(.fullscreen)");
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
      fullscreenButton.blur();
      openFullscreen(codeBlock);
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
  <dialog
    id="fullscreenDialogID"
    ref="fullscreenDialog"
    :class="{ 'is-open': isFullscreen }"
    @click.self="closeFullscreen"
  >
    <div class="container">
      <div class="toolbar">
        <span class="title" />
        <span class="lang" />
        <button class="copy" />
        <button class="wrap" />
        <button class="fullscreen" />
      </div>
      <div ref="fullscreenSlot" class="vp-doc" />
    </div>
  </dialog>
</template>

<style scoped>
div.container {
  display: flex;
  flex-direction: column;
  gap: 8px;
  min-height: 0;
  max-width: stretch;

  & div.toolbar {
    display: flex;
    gap: 8px;
    justify-content: flex-end;

    & .title,
    .lang {
      opacity: 0;
      display: flex;
      align-items: center;
      flex-grow: 1;
      font-size: 1.25rem;
      font-weight: 600;
      color: var(--vp-code-tab-text-color);
      background-color: var(--vp-code-tab-bg);
      padding: 8px 12px;
      border-radius: 8px;
      border: 1px solid var(--vp-code-copy-code-border-color);
      white-space: nowrap;
      overflow: hidden;
      text-overflow: ellipsis;
    }

    & .lang {
      opacity: 1;
      flex-grow: 0;
    }

    &:deep(button) {
      display: flex;
      align-items: center;
      justify-content: center;
      direction: ltr;
      border: 1px solid var(--vp-code-copy-code-border-color);
      border-radius: 4px;
      width: 40px;
      height: 40px;
      background-color: var(--vp-code-copy-code-bg);
      color: #808080;
      cursor: pointer;
      transition:
        border-color 0.25s,
        background-color 0.25s;

      &.copy {
        &::after {
          content: "";
          position: absolute;
          width: 20px;
          height: 20px;
          mask-image: var(--vp-icon-copy);
          mask-position: 50%;
          mask-repeat: no-repeat;
          background-color: #808080;
          transition: background-color 0.25s;
        }

        &.copied::after {
          mask-image: var(--vp-icon-copied);
          background-color: var(--vp-c-brand-1);
        }
      }
    }

    &:deep(button):hover {
      border-color: var(--vp-code-copy-code-hover-border-color);
      background-color: var(--vp-code-copy-code-hover-bg);
    }
  }
}

dialog:has(.vp-doc) {
  --speed: 0.3s;

  max-height: none;
  max-width: none;
  width: 100vw;
  height: 100vh;
  pointer-events: auto;
  border: none;
  margin: 0;
  justify-content: center;
  align-items: center;
  background: transparent;
  opacity: 0;
  animation: fadeIn var(--speed) forwards;
  overflow: hidden;

  &:open {
    display: flex;
    flex-direction: column;
  }

  &::backdrop {
    opacity: 0;
    background: var(--vp-c-bg);
    animation: fadeIn var(--speed) forwards;
  }
}

dialog:has(.vp-doc).is-closing {
  pointer-events: none;
  animation: fadeOut var(--speed) forwards;

  &::backdrop {
    animation: fadeOut var(--speed) forwards;
  }
}

@keyframes fadeIn {
  from {
    opacity: 0;
  }
  to {
    opacity: 1;
  }
}

@keyframes fadeOut {
  from {
    opacity: 1;
  }
  to {
    opacity: 0;
  }
}

:deep(.vp-doc:has(*)) {
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

html:has(dialog#fullscreenDialogID[open]) {
  overflow: hidden;
  scrollbar-gutter: stable;
}
</style>
