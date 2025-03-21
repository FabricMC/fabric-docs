<script setup lang="ts">
import { useData, useRoute } from "vitepress";
import { computed } from "vue";

import { Fabric } from "../../types";

const data = useData();
const route = useRoute();

const options = computed(
  () => data.theme.value.notFound as Fabric.NotFoundOptions
);

const urls = computed(() => {
  const locale = data.localeIndex.value;
  const urls: { [key: string]: string } = {};
  if (locale === "root") {
    urls["home"] = "/";
    urls["english"] = "";
    urls["crowdin"] = "";
  } else {
    urls["home"] = `/${locale}/`;
    // TODO: hide if English=404
    urls["english"] = (route.path.split("//")[1] ?? route.path).replace(
      urls["home"],
      "/"
    );
    // TODO: link to file: https://developer.crowdin.com/api/v2/#operation/api.projects.files.getMany
    urls["crowdin"] =
      "https://crowdin.com/project/fabricmc/" + options.value.crowdinCode;
  }
  return urls;
});
</script>

<template>
  <div class="not-found">
    <p class="code">{{ options!.code }}</p>
    <h1 class="title">{{ options!.title.toUpperCase() }}</h1>
    <div class="divider" />
    <blockquote class="quote">{{ options!.quote }}</blockquote>

    <div class="action">
      <a class="link" :href="urls['home']" :aria-label="options!.linkLabel">
        {{ options!.linkText }}
      </a>
      <br />
      <a
        v-if="urls['english'] !== ''"
        class="link"
        :href="urls['english']"
        :aria-label="options!.englishLinkLabel"
      >
        {{ options!.englishLinkText }}
      </a>
      <br />
      <a
        v-if="urls['crowdin'] !== ''"
        class="link"
        :href="urls['crowdin']"
        :aria-label="options!.crowdinLinkLabel"
      >
        {{ options!.crowdinLinkText }}
      </a>
    </div>
  </div>
</template>

<style scoped>
.not-found {
  padding: 64px 24px 96px;
  text-align: center;
}

@media (min-width: 768px) {
  .not-found {
    padding: 96px 32px 168px;
  }
}

.code {
  line-height: 64px;
  font-size: 64px;
  font-weight: 600;
}

.title {
  padding-top: 12px;
  letter-spacing: 2px;
  line-height: 20px;
  font-size: 20px;
  font-weight: 700;
}

.divider {
  margin: 24px auto 18px;
  width: 64px;
  height: 1px;
  background-color: var(--vp-c-divider);
}

.quote {
  margin: 0 auto;
  max-width: 256px;
  font-size: 14px;
  font-weight: 500;
  color: var(--vp-c-text-2);
}

.action {
  padding-top: 20px;
}

.link {
  margin: 8px;
  display: inline-block;
  border: 1px solid var(--vp-c-brand-1);
  border-radius: 16px;
  padding: 3px 16px;
  font-size: 14px;
  font-weight: 500;
  color: var(--vp-c-brand-1);
  transition: border-color 0.25s, color 0.25s;
}

.link:hover {
  border-color: var(--vp-c-brand-2);
  color: var(--vp-c-brand-2);
}
</style>
