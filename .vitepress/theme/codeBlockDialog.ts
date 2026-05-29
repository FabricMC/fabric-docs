import { inBrowser } from "vitepress";

export const fullScreenButtonClick = (e: PointerEvent) => {
  const button = (e.target as HTMLElement).closest(".full-screen-button");
  if (!button) return;
  const codeBlock = button.parentElement;
  if (!codeBlock) return;
  (button as HTMLButtonElement).blur();
  openCodeDialog(codeBlock as HTMLElement);
};

export function createCodeDialog() {
  if (document.getElementById("code-preview-dialog")) return;
  const dialog = document.createElement("dialog");
  dialog.id = "code-preview-dialog";
  dialog.innerHTML = `<div class="dialog-header"></div><div class="dialog-content"></div>`;
  document.querySelector(".vp-doc")?.appendChild(dialog);
}

function openCodeDialog(codeBlock: HTMLElement) {
  const dialog = document.getElementById("code-preview-dialog") as HTMLDialogElement;
  const content = dialog.querySelector(".dialog-content") as HTMLElement;
  const header = dialog.querySelector(".dialog-header") as HTMLElement;
  const originalCopyButton = codeBlock.querySelector(".copy") as HTMLElement;

  let title = document.createElement("span");
  title.classList.add("dialog-title");

  const codeGroup = codeBlock.closest(".vp-code-group") as HTMLElement;
  if (codeGroup) {
    title.classList.add("dialog-title-bg");
    title.innerHTML =
      (codeGroup.querySelector(".tabs>input[checked]") as HTMLInputElement).labels?.[0]?.textContent
      || "";
  }

  const clone = codeBlock.cloneNode(true) as HTMLElement;
  clone.querySelectorAll(".copy,.full-screen-button").forEach((el) => el.remove());

  const closeButton = document.createElement("button");
  closeButton.title = "Close Full Screen";
  closeButton.classList.add("full-screen-button-exit");
  closeButton.addEventListener("click", () => {
    dialog.close();
  });

  const copyButton = originalCopyButton.cloneNode(true) as HTMLElement;
  copyButton.classList.remove("copied");
  copyButton.addEventListener("click", () => {
    originalCopyButton.click();
    copyButton.classList.add("copied");
    setTimeout(() => copyButton.classList.remove("copied"), 2000);
  });

  const downloadButton = document.createElement("button");
  downloadButton.title = "Download Code as File";
  downloadButton.classList.add("download-button");
  downloadButton.addEventListener("click", async () => {
    try {
      const code = getCodeFromElement(clone);
      if (!code) throw new Error("No code to download");

      const blob = new Blob([code.content], { type: "text/plain" });
      const url = URL.createObjectURL(blob);
      const a = document.createElement("a");
      a.href = url;
      a.download = `snippet.${code.lang}`;

      document.body.appendChild(a);
      a.click();
      a.remove();

      URL.revokeObjectURL(url);
    } catch (err) {
      console.error("Failed to download code:", err);
    }
    downloadButton.blur();
  });

  header.innerHTML = "";
  header.appendChild(title);
  header.appendChild(copyButton);
  header.appendChild(downloadButton);
  header.appendChild(closeButton);

  content.innerHTML = "";
  content.appendChild(clone);
  dialog.showModal();
  dialog.focus();
}

export function getCodeFromElement(content: HTMLElement) {
  if (inBrowser) {
    const ignoredNodes = [".vp-copy-ignore", ".diff.remove"].join(", ");
    const pre = content.querySelector("pre") as HTMLElement;
    const clone = pre.cloneNode(true) as HTMLElement;
    clone.querySelectorAll(ignoredNodes).forEach((node) => node.remove());
    clone.innerHTML = clone.innerHTML.replace(/\n+/g, "\n");

    let text = clone.textContent || "";
    const lang = /language-(\w+)/.exec(content.className)?.[1] || "";
    const shelLangs = ["shellscript", "shell", "bash", "sh", "zsh"];
    if (shelLangs.includes(lang)) {
      text = text.replace(/^ *([$>]) /gm, "").trim();
    }

    return {
      content: text,
      lang: lang,
    };
  }
}
