<script setup lang="ts">
import { getIcon, Icon, loadIcon } from "@iconify/vue";
import latestVersion from "virtual:fabric-docs:latest-version";
import { useData } from "vitepress";
import VPFlyout from "vitepress/dist/client/theme-default/components/VPFlyout.vue";
import VPLink from "vitepress/dist/client/theme-default/components/VPLink.vue";
import { computed, onMounted, ref } from "vue";
import { Fabric } from "../../types.d";

const props = defineProps<{
  versioningPlugin: { versions: string[] };
  screenMenu?: boolean;
}>();

const data = useData();
const collator = new Intl.Collator(undefined, { numeric: true });

const env = computed(() => data.theme.value.env as Fabric.EnvOptions);
const options = computed(() => (data.theme.value.version as Fabric.VersionOptions).switcher);

const currentV = computed(() => {
  const split = data.page.value.filePath.split("/");
  if (split[0] === "versions") return split[1];
  if (/^[0-9.]+$/.test(split[0])) return split[0];
  return latestVersion;
});

const button = computed(() => {
  const iconData = getIcon("mdi:source-branch");

  const icon = iconData
    ? `<svg xmlns="http://www.w3.org/2000/svg" width="1em" height="1em" viewBox="0 0 24 24">${iconData.body}</svg>`
    : "";

  return `<span style='display:flex;align-items:center;gap:4px'>${icon} ${currentV.value}</span>`;
});

// TODO: add future versions to the supported pages
const versions = computed(() =>
  [
    latestVersion,
    ...(typeof env.value === "number"
      ? []
      : props.versioningPlugin.versions.toSorted(collator.compare).reverse()),
  ].filter((v) => v !== currentV.value && v !== "1.21.10")
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
  const noVersions = split.slice(split[0] === "versions" ? 2 : /^[0-9.]+$/.test(split[0]) ? 1 : 0);
  const neither = noVersions.slice(noVersions[0] === "translated" ? 2 : 0);

  const segments = [
    "",
    data.localeIndex.value !== "root" ? data.localeIndex.value : undefined,
    v !== latestVersion ? v : undefined,
    ...neither,
  ]
    .filter((s) => s !== undefined)
    .reverse();

  segments[0] = segments[0] === "index.md" ? "" : segments[0].replace(/[.]md$/, "");

  return segments.reverse().join("/");
};

onMounted(async () => {
  await loadIcon("mdi:source-branch");
});
</script>

<template>
  <component
    :is="screenMenu ? 'div' : VPFlyout"
    :class="{ open }"
    :button
    :label="options.label.replace('%s', currentV)"
  >
    <button v-if="screenMenu" :aria-expanded="open" @click="open = !open">
      <span>
        <Icon icon="mdi:source-branch" width="16" height="16" />
        {{ options.label.replace("%s", currentV) }}
      </span>
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

    span {
      display: flex;
      align-items: center;
      gap: 4px;
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
