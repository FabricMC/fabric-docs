<script setup lang="ts">
import { useData } from "vitepress";
import VPLink from "vitepress/dist/client/theme-default/components/VPLink.vue";
import { computed, nextTick, onBeforeUnmount, onMounted, ref } from "vue";

import { Fabric } from "../../types";

let i: number = -1;
const data = useData();

const options = computed(() => {
  const { quotes, pooh, ...options } = data.theme.value.notFound as Fabric.NotFoundOptions;
  if (i === -1) i = Math.floor(Math.random() * quotes.length);
  if (i === quotes.length - 1) options.title = pooh;
  options.quote = quotes[i];
  return options;
});

const removeForEnglishRegex = new RegExp(String.raw`^${data.localeIndex.value}/|\.md$`, "g");
const urls = computed(() =>
  data.localeIndex.value === "root"
    ? {
        home: "/",
        english: undefined,
        crowdin: undefined,
      }
    : {
        home: `/${data.localeIndex.value}/`,
        // TODO: hide if English=404
        english: data.page.value.relativePath.replace(removeForEnglishRegex, ""),
        // TODO: link to file: https://developer.crowdin.com/api/v2/#operation/api.projects.files.getMany
        crowdin: `https://crowdin.com/project/fabricmc/${options.value.crowdinLocale}`,
      }
);

const root = ref<HTMLElement>();
const ball = ref<HTMLCanvasElement>();
const thread = ref<HTMLElement>();
const content = ref<HTMLElement>();

let animationFrame = 0;
let isBallRolling: boolean = true;
let values: ReturnType<typeof getValues>;
let tPattern: string;

const getValues = () => {
  const rRect = root.value!.getBoundingClientRect();
  const cRect = content.value!.getBoundingClientRect();
  const px = Math.floor((cRect.height * 1.5) / TEXTURE.length);

  const cMiddleX = cRect.width / 2;

  const bDiameter = TEXTURE.length * px;
  const bStartX = -bDiameter - 32;
  const bTotalX = rRect.width + 32 - bStartX;
  const bTopY = (rRect.height - bDiameter) / 2;

  const tTopY = bTopY + 12 * px;

  return {
    px,

    bDiameter,
    bStartX,
    bTotalX,
    bTopY,
    cMiddleX,
    tTopY,
  };
};

const drawBall = (b: HTMLCanvasElement) => {
  b.width = b.height = TEXTURE.length;
  b.style.width = b.style.height = `${values.bDiameter}px`;
  b.style.zIndex = "1";
  b.style.imageRendering = "pixelated";
  b.style.position = "absolute";
  b.style.top = `${values.bTopY}px`;
  b.style.left = "0px";
  b.style.transform = `translateX(${values.bStartX}px) rotate(0deg)`;
  b.style.display = "block";

  const context = b.getContext("2d", { alpha: true })!;
  context.imageSmoothingEnabled = false;
  context.clearRect(0, 0, b.width, b.height);

  for (let x = 0; x < TEXTURE.length; x++) {
    for (let y = 0; y < TEXTURE[x].length; y++) {
      const color = COLORS[TEXTURE[y][x]];
      if (!color) continue;
      context.fillStyle = color;
      context.fillRect(x, y, 1, 1);
    }
  }
};

const createThreadPattern = () => {
  if (tPattern) return;

  const pattern = document.createElement("canvas");
  pattern.width = TEXTURE.length;
  pattern.height = 1;

  const context = pattern.getContext("2d", { alpha: true })!;
  context.imageSmoothingEnabled = false;

  for (let x = 0; x < TEXTURE.length; x++) {
    context.fillStyle = COLORS[Math.floor(Math.random() * (COLORS.length - 1) + 1)]!;
    context.fillRect(x, 0, 1, 1);
  }

  tPattern = pattern.toDataURL();
};

const drawThread = (t: HTMLElement) => {
  createThreadPattern();
  t.style.backgroundImage = `url(${tPattern})`;
  t.style.backgroundRepeat = "repeat-x";
  t.style.backgroundSize = `${values.bDiameter}px ${values.px}px`;
  t.style.top = `${values.tTopY}px`;
  t.style.left = `0px`;
  t.style.height = `${values.px}px`;
  t.style.width = `${isBallRolling ? 0 : values.bTotalX}px`;
  t.style.imageRendering = "pixelated";
  t.style.position = "absolute";
};

const startAnimation = () => {
  if (window.matchMedia("(prefers-reduced-motion: reduce)").matches) return;
  values = getValues();

  drawBall(ball.value!);
  drawThread(thread.value!);

  // hide content initially
  content.value!.style.opacity = "0";
  content.value!.style.pointerEvents = "none";
  content.value!.setAttribute("aria-hidden", "true");

  // start rolling the ball
  const totalTime = Math.max(600, 3 * values.bTotalX);
  const startTime = performance.now();
  isBallRolling = true;

  const step = (now: number) => {
    if (!isBallRolling) return;

    const time = Math.min(1, Math.max(0, now - startTime) / totalTime);
    const bStartXNow = values.bStartX + values.bTotalX * (1 - Math.pow(1 - time, 3));
    const bMiddleXNow = bStartXNow + values.bDiameter / 2;

    thread.value!.style.width = `${Math.min(values.bTotalX, bMiddleXNow)}px`;

    // show content when the ball crosses the midpoint
    if (bMiddleXNow >= values.cMiddleX && content.value!.style.opacity === "0") {
      content.value!.style.opacity = "1";
      content.value!.style.pointerEvents = "auto";
      content.value!.setAttribute("aria-hidden", "false");
    }

    const bCircumference = Math.PI * values.bDiameter;
    const bRotationDeg = ((bStartXNow - values.bStartX) * 360) / bCircumference;
    ball.value!.style.transform = `translateX(${bStartXNow}px) translateZ(0) rotate(${bRotationDeg}deg)`;

    if (time < 1) {
      animationFrame = requestAnimationFrame(step);
    } else {
      isBallRolling = false;
      ball.value!.style.display = "none";
    }
  };

  animationFrame = requestAnimationFrame(step);
};

let handleResizeTimeout: number | null = null;
const handleResize = () => {
  values = getValues();
  if (handleResizeTimeout) clearTimeout(handleResizeTimeout);
  // even after the animation, thread must fill the width
  handleResizeTimeout = window.setTimeout(() => !isBallRolling && drawThread(thread.value!), 100);
};

onMounted(async () => {
  await nextTick();
  window.addEventListener("resize", handleResize);

  if (document.readyState === "complete") {
    startAnimation();
  } else {
    const onLoad = () => {
      startAnimation();
      window.removeEventListener("load", onLoad);
    };
    window.addEventListener("load", onLoad);
  }
});

onBeforeUnmount(() => {
  if (animationFrame) cancelAnimationFrame(animationFrame);
  if (handleResizeTimeout) clearTimeout(handleResizeTimeout);
  window.removeEventListener("resize", handleResize);
  animationFrame = 0;
  isBallRolling = false;
});

// extracted from https://github.com/FabricMC/community/blob/57106dcfe85da0f9209b327d19f4e206abd10d76/media/unascribed/png/yarn.png
const COLORS = [
  null,
  "#051842",
  "#2A6CD9",
  "#388BF6",
  "#337FEC",
  "#235DC0",
  "#2666CA",
  "#2764CF",
  "#1A49A6",
  "#041439",
  "#2059BB",
  "#1847A9",
  "#1D51B2",
  "#15409E",
  "#235CC1",
  "#123789",
  "#2A6CD3",
  "#2E76DD",
  "#1C4EAE",
  "#04153C",
  "#3D95FF",
  "#1844A0",
  "#1C4FB1",
  "#1947A7",
  "#143C94",
  "#031133",
] as const;

const TEXTURE = [
  [0, 0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0, 0],
  [0, 0, 0, 1, 1, 2, 3, 4, 2, 1, 1, 0, 0, 0],
  [0, 0, 1, 4, 3, 2, 3, 4, 2, 5, 6, 1, 0, 0],
  [0, 1, 4, 4, 4, 3, 2, 4, 3, 2, 5, 6, 1, 0],
  [0, 1, 3, 3, 3, 4, 2, 4, 3, 2, 5, 6, 1, 0],
  [1, 7, 7, 7, 4, 3, 2, 4, 3, 2, 5, 8, 8, 9],
  [1, 3, 3, 3, 7, 7, 2, 3, 4, 2, 8, 10, 11, 9],
  [1, 4, 4, 4, 4, 4, 2, 3, 4, 2, 12, 12, 13, 9],
  [1, 7, 7, 7, 3, 3, 2, 3, 4, 14, 10, 15, 15, 9],
  [0, 1, 3, 4, 7, 7, 2, 16, 17, 18, 15, 13, 19, 0],
  [0, 1, 20, 3, 4, 2, 17, 16, 18, 10, 13, 11, 19, 0],
  [0, 0, 1, 3, 16, 14, 6, 5, 21, 13, 11, 19, 0, 0],
  [0, 0, 0, 19, 25, 17, 22, 23, 24, 25, 19, 0, 0, 0],
  [0, 0, 0, 0, 0, 19, 19, 19, 19, 0, 0, 0, 0, 0],
] as const;
</script>

<template>
  <div class="not-found" ref="root" aria-live="polite">
    <div class="yarn" aria-hidden="true">
      <canvas ref="ball" />
      <div ref="thread" />
    </div>

    <div ref="content" aria-hidden="false">
      <code>{{ options.code }}</code>
      <h1>{{ options.title.toLocaleUpperCase(data.lang.value) }}</h1>
      <blockquote>{{ options.quote }}</blockquote>

      <VPLink :href="urls.home" :aria-label="options.linkLabel">
        {{ options.linkText }}
      </VPLink>
      <br />
      <VPLink v-if="urls.english" :href="urls.english" :aria-label="options.englishLinkLabel">
        {{ options.englishLinkText }}
      </VPLink>
      <br />
      <VPLink v-if="urls.crowdin" :href="urls.crowdin" :aria-label="options.crowdinLinkLabel">
        {{ options.crowdinLinkText }}
      </VPLink>
    </div>
  </div>
</template>

<style scoped>
.not-found {
  padding: 64px 24px 96px;
  text-align: center;
  position: relative;
  overflow: hidden;
}

@media (min-width: 768px) {
  .not-found {
    padding: 96px 32px 168px;
  }
}

.yarn {
  position: absolute;
  left: 0;
  top: 0;
  width: 100%;
  height: 100%;
  pointer-events: none;
  overflow: visible;
}

code {
  line-height: 64px;
  font-size: 64px;
  font-weight: 600;
}

h1 {
  padding: 12px 0px;
  letter-spacing: 2px;
  line-height: 20px;
  font-size: 20px;
  font-weight: 700;
}

blockquote {
  margin: 0 auto;
  max-width: 512px;
  font-size: 14px;
  font-weight: 500;
  color: var(--vp-c-text-2);
  padding-bottom: 20px;
}

.VPLink {
  margin: 8px;
  display: inline-block;
  border: 1px solid var(--vp-c-brand-1);
  border-radius: 16px;
  padding: 3px 16px;
  font-size: 14px;
  font-weight: 500;
}

.VPLink,
.VPLink::after {
  color: var(--vp-c-brand-1) !important;
  transition:
    border-color 0.25s,
    color 0.25s;
}

.VPLink:hover,
.VPLink:hover::after {
  border-color: var(--vp-c-brand-2);
  color: var(--vp-c-brand-2) !important;
}
</style>
