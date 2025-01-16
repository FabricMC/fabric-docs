<script setup lang="ts">
import "vidstack/player";
import "vidstack/player/layouts/default";
import "vidstack/player/styles/default/layouts/video.css";
import "vidstack/player/styles/default/theme.css";
import "vidstack/player/ui";

const props = defineProps<{
  title: string;
  src: string;
}>();
</script>

<!-- A video player component that can be used within documentation pages instead of relying on .GIFs or .webp animated videos. -->

<template>
  <media-player
    load="visible"
    view-type="video"
    streamType="on-demand"
    :title="props.title"
    :src="props.src"
  >
    <media-provider> </media-provider>
    <media-video-layout />
  </media-player>
</template>
