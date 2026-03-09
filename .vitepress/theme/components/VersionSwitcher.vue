<script setup lang="ts">
import { useData } from "vitepress";
import VPFlyout from "vitepress/dist/client/theme-default/components/VPFlyout.vue";
import VPLink from "vitepress/dist/client/theme-default/components/VPLink.vue";
import { computed, ref } from "vue";

import { Fabric } from "../../types";

const props = defineProps<{
  versioningPlugin: { versions: string[]; latestVersion: string };
  screenMenu?: boolean;
}>();

const data = useData();
const collator = new Intl.Collator(undefined, { numeric: true });

const env = computed(() => data.theme.value.env as Fabric.EnvOptions);
const options = computed(() => (data.theme.value.version as Fabric.VersionOptions).switcher);

const currentV = computed(() => {
  const split = data.page.value.filePath.split("/");
  if (split[0] === "versions") return split[1];
  return props.versioningPlugin.latestVersion;
});

const versions = computed(() =>
  [
    props.versioningPlugin.latestVersion,
    ...(typeof env.value === "number"
      ? []
      : props.versioningPlugin.versions.toSorted(collator.compare).reverse()),
  ].filter((v) => v !== currentV.value)
);

const open = ref(false);

/*
file format:   [versions/version/] [translated/locale/] path/to/index.md
route format:  [locale/] [version/] path/to/

- notice that version and locale are flipped between file and route
- locale/ is not added for pages in English
- version/ is not added for the latest version
*/
const getRoute = (v: string) => {
  const split = data.page.value.filePath.split("/");
  const noVersions = split.slice(split[0] === "versions" ? 2 : 0);
  const neither = noVersions.slice(noVersions[0] === "translated" ? 2 : 0);

  const segments = [
    "",
    data.localeIndex.value !== "root" ? data.localeIndex.value : undefined,
    v !== props.versioningPlugin.latestVersion ? v : undefined,
    ...neither,
  ]
    .filter((s) => s !== undefined)
    .reverse();

  segments[0] = segments[0] === "index.md" ? "" : segments[0].replace(/[.]md$/, "");

  return segments.reverse().join("/");
};
</script>

<template>
  <component
    :is="screenMenu ? 'div' : VPFlyout"
    icon="vpi-versioning"
    :class="{ open }"
    :button="currentV"
    :label="options.label.replace('%s', currentV)"
  >
    <button v-if="screenMenu" :aria-expanded="open" @click="open = !open">
      <span><span class="vpi-versioning" />{{ options.label.replace("%s", currentV) }}</span>
      <span class="vpi-plus" />
    </button>

    <VPLink v-if="versions.length" v-for="v in versions" :key="v" :href="getRoute(v)">{{
      options.label.replace("%s", v)
    }}</VPLink>
    <VPLink v-else>{{ options.none }}</VPLink>
  </component>
</template>

<style scoped>
div:not(.VPFlyout) {
  border-bottom: 1px solid var(--vp-c-divider);
  height: 48px;
  overflow: hidden;
  transition: border-color 0.5s;

  button {
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 12px 4px 11px 0;
    width: 100%;
    line-height: 24px;
    font-size: 14px;
    font-weight: 500;
    color: var(--vp-c-text-1);
    transition: color 0.25s;

    .vpi-versioning {
      padding: 8px;
      margin-right: 4px;
    }

    .vpi-plus {
      transition: transform 0.25s;
    }
  }

  button:hover {
    color: var(--vp-c-brand-1);
  }
}

.open:not(.VPFlyout) {
  padding-bottom: 10px;
  height: auto;

  button {
    color: var(--vp-c-brand-1);
  }

  .vpi-plus {
    transform: rotate(45deg);
  }
}

.VPLink {
  display: block;
  border-radius: 6px;
  padding: 0 12px;
  line-height: 32px;
  font-size: 14px;
  font-weight: 500;
  color: var(--vp-c-text-1);
  white-space: nowrap;
  transition:
    background-color 0.25s,
    color 0.25s;
}

span.VPLink {
  font-style: italic;
}

a.VPLink:hover {
  color: var(--vp-c-brand-1);
  background-color: var(--vp-c-default-soft);
}
</style>
