<script setup lang="ts">
import { useData } from "vitepress";
import VPLink from "vitepress/dist/client/theme-default/components/VPLink.vue";
import { computed } from "vue";

import { Fabric } from "../../types";

const data = useData();
const options = computed(() => data.theme.value.notFound as Fabric.NotFoundOptions);
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
</script>

<template>
  <div class="not-found">
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
  max-width: 256px;
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
