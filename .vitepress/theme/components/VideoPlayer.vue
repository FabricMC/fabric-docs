<script setup lang="ts">
import "vidstack/player";
import "vidstack/player/layouts/default";
import "vidstack/player/styles/default/layouts/video.css";
import "vidstack/player/styles/default/theme.css";
import "vidstack/player/ui";
import { useSlots } from "vue";

const props = defineProps<{
  src: string;
}>();

const title = (useSlots().default?.() ?? [""])[0].children ?? "";
</script>

<!-- A video player component that can be used within documentation pages instead of relying on .GIFs or .webp animated videos. -->

<template>
  <media-player
    load="visible"
    view-type="video"
    streamType="on-demand"
    :title="title"
    :src="props.src"
  >
    <media-provider> </media-provider>
    <media-video-layout />
  </media-player>
</template>
