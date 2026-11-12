import { isAudioSrc, isVideoSrc } from "vidstack";
import type { MarkdownRenderer } from "vitepress";

const resolveAdapterType = (src: string) => {
  if (isAudioSrc({ src, type: "" }) || src.endsWith(".ogg")) {
    return "audio";
  }

  if (isVideoSrc({ src, type: "" }) || URL.parse(src)?.hostname.includes("youtu")) {
    return "video";
  }
};

export const videoImagePlugin = (md: MarkdownRenderer) => {
  md.core.ruler.push("video_image_unwrap", (state) => {
    for (let i = 0; i < state.tokens.length - 2; i++) {
      const [open, inline, close] = [state.tokens[i], state.tokens[i + 1], state.tokens[i + 2]];
      if (
        open.type !== "paragraph_open"
        || inline.type !== "inline"
        || inline.children?.length !== 1
        || close.type !== "paragraph_close"
      ) {
        continue;
      }

      const media = inline.children[0];
      const type = resolveAdapterType(media.attrGet("src") || "") as string;
      if (media.type !== "image" || !["audio", "video"].includes(type)) {
        continue;
      }

      open.hidden = true;
      (media.meta ||= {}).type = type;
      close.hidden = true;
    }

    return true;
  });

  const image = md.renderer.rules.image!;
  md.renderer.rules.image = (tokens, idx, options, env, self) => {
    if (!env.localeIndex) {
      return image(tokens, idx, options, env, self);
    }

    const token = tokens[idx];

    const type = token.meta?.type as string;
    if (!type) {
      return image(tokens, idx, options, env, self);
    }

    const srcValue = token.attrGet("src")!;
    return `<Player type="${type}" src="${md.utils.escapeHtml(
      srcValue
    )}" title="${md.utils.escapeHtml(token.content)}"${token.attrGet("warn") !== null ? " warn" : ""} />`;
  };
};
