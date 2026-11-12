<script setup lang="ts">
import { useSlots } from "vue";
import Player from "./Player.vue";

/** @deprecated use ![title](/path/to/video.webm) instead */
interface VideoPlayerProps {
  src: string;
}

defineProps<VideoPlayerProps>();

const type = "video";

const title = String(useSlots().default?.()?.[0]?.children || "");
</script>

<template>
  <Player :type :src :title />
</template>
