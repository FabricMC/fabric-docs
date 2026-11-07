<script setup lang="ts">
import { resolveAdapterType } from "@videojs/html";
import { computed, useSlots } from "vue";
import Player from "./Player.vue";

/** @deprecated use ![title](/path/to/video.webm) instead */
interface VideoPlayerProps {
  src: string;
}

const props = defineProps<VideoPlayerProps>();

const type = computed(() => resolveAdapterType(props.src) as "audio" | "video" | "youtube");

const contentTitle = String(useSlots().default?.()?.[0]?.children || "");
</script>

<template>
  <Player :type :src :content-title />
</template>
