<script setup lang="ts">
import "@videojs/html/audio/player";
import "@videojs/html/audio/skin";
import "@videojs/html/media/youtube-video";
import "@videojs/html/video/player";
import "@videojs/html/video/skin";
import { useData } from "vitepress";
import { VPButton } from "vitepress/theme";
import { computed, ref, useId } from "vue";
import type { ThemeConfig } from "../../types.d.ts";

const props = defineProps<{
  type: "audio" | "video" | "youtube";
  src: string;
  contentTitle?: string;
  warn?: boolean;
}>();

const id = useId();
const data = useData<ThemeConfig>();

const options = computed(() => data.theme.value.video);

const showWarning = ref(props.type !== "audio" && props.warn);
</script>

<template>
  <audio-player v-if="type === 'audio'" :content-title>
    <audio-skin>
      <audio :src preload="metadata"></audio>
    </audio-skin>
  </audio-player>

  <video-player v-else :content-title>
    <video-skin :inert="showWarning">
      <youtube-video v-if="type === 'youtube'" :src playsinline></youtube-video>
      <video v-else :src preload="metadata" playsinline></video>
    </video-skin>

    <dialog
      v-if="showWarning"
      open
      role="alertdialog"
      :aria-labelledby="`${id}-title`"
      :aria-describedby="`${id}-description`"
    >
      <h3 :id="`${id}-title`">{{ options.title }}</h3>
      <p :id="`${id}-description`">{{ options.description }}</p>
      <VPButton @click="showWarning = false">{{ options.button }}</VPButton>
    </dialog>
  </video-player>
</template>

<style scoped>
audio-player,
video-player {
  display: block;
  margin-block: 1rem;
}

video-player {
  position: relative;
}

video-skin {
  display: block;
}

youtube-video {
  display: block;
  aspect-ratio: 16 / 9;
  width: 100%;
}

dialog {
  position: absolute;
  z-index: 10;
  inset: 0;

  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;

  box-sizing: border-box;
  width: 100%;
  height: 100%;
  margin: 0;
  padding: 2rem;
  border: 0;
  border-radius: var(--media-border-radius);

  color: var(--vp-c-white);
  text-align: center;

  background-color: color-mix(in srgb, var(--vp-c-black) 93%, transparent);

  h3 {
    margin: 0;
  }

  p {
    margin: 0.5rem 0 1rem;
  }
}
</style>
