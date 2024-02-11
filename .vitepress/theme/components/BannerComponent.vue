<script setup lang="ts">
import { useElementSize, useStorage } from '@vueuse/core';
import { onBeforeMount, onMounted, ref, watchEffect } from 'vue';

const el = ref<HTMLElement>();
const { height } = useElementSize(el);
const storage = useStorage("banner-state", -1);

watchEffect(() => {
  if (height.value) {
    document.documentElement.style.setProperty(
      '--vp-layout-top-height',
      `${height.value + 16}px`
    );
  }
});

const dismiss = () => {
  // Add 1 day to the current time
  storage.value = Date.now() + 1000 * 60 * 60 * 24;
  document.documentElement.classList.add('banner-dismissed');
};

onBeforeMount(() => {
  var date = storage.value;
  if (date > Date.now()) {
    document.documentElement.classList.add('banner-dismissed');
  }
});
</script>

<template>
  <div ref="el" class="banner">
    <div class="text">
      Fabric Documentation is a work in progress. If you find any issues, please report them on the <a
        href="https://github.com/fabricmc/fabric-docs/issues" target="_blank" rel="noopener noreferrer">GitHub
        repository</a>.
    </div>

    <button type="button" @click="dismiss">
      <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 20 20" fill="currentColor">
        <path
          d="M6.28 5.22a.75.75 0 00-1.06 1.06L8.94 10l-3.72 3.72a.75.75 0 101.06 1.06L10 11.06l3.72 3.72a.75.75 0 101.06-1.06L11.06 10l3.72-3.72a.75.75 0 00-1.06-1.06L10 8.94 6.28 5.22z" />
      </svg>
    </button>
  </div>
</template>

<style>
.banner-dismissed {
  --vp-layout-top-height: 0px !important;
}

html {
  --vp-layout-top-height: 88px;
}

@media (min-width: 375px) {
  html {
    --vp-layout-top-height: 64px;
  }
}

@media (min-width: 768px) {
  html {
    --vp-layout-top-height: 40px;
  }
}
</style>

<style scoped>
.banner-dismissed .banner {
  transform: translateY(-100%);
}

.banner {
  position: fixed;
  top: 0;
  right: 0;
  left: 0;
  z-index: var(--vp-z-index-layout-top);

  font-family: monospace;

  padding: 8px;
  text-align: center;

  background: rgb(207, 114, 21);
  color: #fff;

  display: flex;
  justify-content: space-between;
}

.text {
  flex: 1;
}

a {
  text-decoration: underline;
}

svg {
  width: 20px;
  height: 20px;
  margin-left: 8px;
}
</style>