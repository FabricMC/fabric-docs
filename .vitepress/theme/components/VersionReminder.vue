<script setup lang="ts">
import { Icon } from "@iconify/vue";
import latestVersion from "virtual:fabric-docs:latest-version";
import { useData } from "vitepress";
import { computed } from "vue";
import { Fabric } from "../../types.d";

const data = useData();

const options = computed(() => (data.theme.value.version as Fabric.VersionOptions).reminder);

const version = computed(() => {
  const split = data.page.value.filePath.split("/");
  if (split[0] === "versions") return split[1];
  if (/^[0-9.]+$/.test(split[0])) return split[0];
  return latestVersion;
});

const replaceVersion = (s: string) => {
  const split = s.split("%s");
  return [split[0], version.value, split.slice(1).join("%s")] as const;
};

const oldV = computed(() => replaceVersion(options.value.oldVersion));
const latestV = computed(() => replaceVersion(options.value.latestVersion));
const newV = computed(() => replaceVersion(options.value.newVersion));
</script>

<template>
  <div v-if="/^(versions|[0-9.]+)([/]|$)/.test(data.page.value.filePath)" class="other">
    <span>
      <Icon icon="mdi:alert-circle-outline" />
    </span>
    <p v-if="data.page.value.filePath.startsWith('versions/')">
      {{ oldV[0] }}<b>{{ oldV[1] }}</b
      >{{ oldV[2] }}
    </p>
    <p v-else>
      {{ newV[0] }}<b>{{ newV[1] }}</b
      >{{ newV[2] }}
    </p>
  </div>
  <div v-else class="latest">
    <span>
      <Icon icon="mdi:check" />
    </span>
    <p>
      {{ latestV[0] }}<b>{{ latestV[1] }}</b
      >{{ latestV[2] }}
    </p>
  </div>
</template>

<style scoped>
div {
  align-items: center;
  border-radius: 8px;
  display: flex;
  flex-direction: row;
  gap: 16px;
  padding: 16px;
  white-space: pre-wrap;
}

span {
  width: 48px;

  .iconify {
    display: block;
    height: 100%;
    width: 100%;
  }
}

.latest {
  background-color: var(--vp-custom-block-tip-bg);
  color: var(--vp-custom-block-tip-text);

  .iconify {
    color: var(--vp-c-tip-1);
  }
}

.other {
  background-color: var(--vp-custom-block-warning-bg);
  color: var(--vp-custom-block-warning-text);

  .iconify {
    color: var(--vp-c-warning-1);
  }
}

.content-container > div {
  margin-top: -24px;
  margin-bottom: 16px;
}

@media (min-width: 1280px) {
  .content-container > div {
    display: none;
  }

  div {
    margin-top: 16px;
  }

  span {
    height: 20%;
    width: 20%;
  }
}
</style>
