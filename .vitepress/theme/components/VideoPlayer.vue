<script setup lang="ts">
import "vidstack/player";
import "vidstack/player/layouts/default";
import "vidstack/player/styles/default/layouts/video.css";
import "vidstack/player/styles/default/theme.css";
import "vidstack/player/ui";
import { useSlots } from "vue";

defineProps<{ src: string }>();

const title = ((useSlots().default?.() ?? [""])[0] as any).children ?? "";
</script>

<template>
  <media-player load="visible" view-type="video" stream-type="on-demand" :title :src>
    <media-provider />
    <media-video-layout />
  </media-player>
</template>
