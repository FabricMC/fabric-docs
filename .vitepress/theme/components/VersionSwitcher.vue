<script setup lang="ts">
import { Icon, loadIcon } from "@iconify/vue";
import { computedAsync } from "@vueuse/core";
import { useData } from "vitepress";
import VPFlyout from "vitepress/dist/client/theme-default/components/VPFlyout.vue";
import VPLink from "vitepress/dist/client/theme-default/components/VPLink.vue";
import { computed, ref } from "vue";
import { Fabric } from "../../types.d";

const props = defineProps<{
  versioningPlugin: { versions: string[]; latestVersion: string };
  screenMenu?: boolean;
}>();

const data = useData();
const collator = new Intl.Collator(undefined, { numeric: true });

const env = computed(() => data.theme.value.env as Fabric.EnvOptions);
const options = computed(() => (data.theme.value.version as Fabric.VersionOptions).switcher);
const currentV = computed(() => {
  if (data.frontmatter.value.version) return data.frontmatter.value.version as string;

  const split = data.page.value.relativePath.split("/");
  if (/^[0-9.]+$/.test(split[0])) return split[0];
  if (/^.._..$/.test(split[0]) && /^[0-9.]+$/.test(split[1])) return split[1];
  return props.versioningPlugin.latestVersion;
});

const body = computedAsync(async () => (await loadIcon("lucide:git-graph")).body);

const button = computed(() => {
  if (!body.value) return;

  const icon = `<svg xmlns="http://www.w3.org/2000/svg" width="1em" height="1em" viewBox="0 0 24 24">${body.value}</svg>`;
  return `<span style="display:flex;align-items:center;gap:4px">${icon} ${currentV.value}</span>`;
});

// TODO: add future versions to the supported pages
const versions = computed(() =>
  [
    props.versioningPlugin.latestVersion,
    ...(typeof env.value === "number"
      ? []
      : props.versioningPlugin.versions.toSorted(collator.compare).reverse()),
  ].filter((v) => v !== "1.21.10")
);

const open = ref(false);

/*
file format:   [[versions/]version/] [translated/locale/] path/to/index.md
route format:  [locale/] [version/] path/to/

- notice that version and locale are flipped between file and route
- in the file path, [versions/] isn't added if the version is unreleased
- [locale/] is not added for pages in English
- [version/] is not added for the latest version
*/
const getRoute = (newVersion: string) => {
  if (newVersion === data.frontmatter.value.version) return;

  const split = data.page.value.filePath.split("/");
  // path segments for each type of version
  const versionSlices = { latest: 0, future: 1, old: 2 };

  const noVersion = split.slice(versionSlices[data.frontmatter.value.versionType as never]);
  const neitherVersionNorLocale = noVersion.slice(noVersion[0] === "translated" ? 2 : 0);

  const segments = [
    "",
    data.localeIndex.value !== "root" ? data.localeIndex.value : undefined,
    newVersion !== props.versioningPlugin.latestVersion ? newVersion : undefined,
    ...neitherVersionNorLocale,
  ].filter((s) => s !== undefined);

  return segments.join("/").replace(/((?<=^|[/])index)?[.](html|md)$/, "");
};
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
        <Icon icon="lucide:git-graph" width="16" height="16" />
        {{ options.label.replace("%s", currentV) }}
      </span>
      <span class="vpi-plus" />
    </button>

    <VPLink v-for="v in versions" :key="v" :href="getRoute(v)">{{
      options.label.replace("%s", v)
    }}</VPLink>
    <VPLink v-if="versions.length <= 1">{{ options.none }}</VPLink>
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
