<script setup lang="ts">
import "vidstack/player";
import "vidstack/player/layouts/default";
import "vidstack/player/styles/default/layouts/video.css";
import "vidstack/player/styles/default/theme.css";
import "vidstack/player/ui";
import { useData } from "vitepress";
import { VPButton } from "vitepress/theme";
import { computed, ref, useId } from "vue";
import type { ThemeConfig } from "../../types.d.ts";

const props = defineProps<{
  type: "audio" | "video";
  src: string;
  title?: string;
  warn?: boolean;
}>();

const id = useId();
const data = useData<ThemeConfig>();

const options = computed(() => data.theme.value.video);

const showWarning = ref(props.type !== "audio" && props.warn);
</script>

<template>
  <div class="media">
    <audio v-if="type === 'audio'" controls preload="metadata" :src :title></audio>

    <media-player v-else load="visible" stream-type="on-demand" :title :src :inert="showWarning">
      <media-provider />
      <media-video-layout />
    </media-player>

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
  </div>
</template>

<style scoped>
.media {
  position: relative;
  margin-block: 1rem;
}

audio {
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
  border-radius: 0.5rem;

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
