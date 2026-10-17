<script setup lang="ts">
import { useMounted } from "@vueuse/core";
import { type DefaultTheme, useData, useRoute } from "vitepress";
import VPNavMenuGroup from "vitepress/dist/client/theme-default/components/VPNavMenuGroup.vue";
import { computed } from "vue";
import type { Fabric } from "../../types.d.ts";
import { useIconSpan } from "../composables/iconSpan.ts";

const props = defineProps<{
  h1?: boolean;
  screenMenu?: boolean;
}>();

const data = useData<Fabric.ThemeConfig>();
const route = useRoute();
const mounted = useMounted();
const icon = useIconSpan("material-icon-theme:minecraft");
const collator = new Intl.Collator(undefined, { numeric: true });

const options = computed(() => data.theme.value.version);

const segments = computed(() => {
  if (data.frontmatter.value.version) {
    return data.frontmatter.value as { version: string; purePath: string };
  }

  const split = route.path.split("/").filter(Boolean);
  if (split[0] === data.localeIndex.value) {
    split.splice(0, 1);
  }

  const version = /^[0-9]+[.][0-9]+([.][0-9]+)?$/.test(split[0])
    ? split.shift()!
    : options.value.latestVersion;

  const purePath = split.join("/");

  return { version, purePath };
});

const versions = computed(() =>
  (
    (mounted.value && window.__FD_PAGE_VERSIONS__?.[segments.value.purePath])
    || options.value.allVersions
  )
    .map((v) => v || options.value.latestVersion)
    .toSorted(collator.compare)
    .toReversed()
);

/**
route format: `/[locale/][version/]path/to/[file-name]`
- `[locale/]` is not added for pages in English
- `[version/]` is not added for the latest version
- `[file-name]` is not added for index.md files
*/
const getRoute = (v: string) => {
  if (v === segments.value.version) {
    return route.hash || "#";
  }

  return `/${[
    data.localeIndex.value !== "root" && data.localeIndex.value,
    v !== options.value.latestVersion && v,
    segments.value.purePath,
  ]
    .filter(Boolean)
    .join("/")}`;
};

const item = computed(() => ({
  text: `${icon} ${!props.h1 && props.screenMenu === false ? options.value.switcherTitle : segments.value.version}`,
  items: [
    ...versions.value.map((v) => ({
      text: options.value.switcherLabel.replace("%s", v),
      link: getRoute(v),
      activeMatch: v === segments.value.version ? "(?=)" : "(?!)",
    })),
    versions.value.length <= 1 && {
      text: options.value.noOtherVersions,
      link: "",
    },
  ].filter(Boolean) as DefaultTheme.NavItemWithLink[],
  activeMatch: "(?!)",
}));
</script>

<template>
  <VPNavMenuGroup
    :item
    :screen="screenMenu"
    :class="h1 && ['VPBadge', segments.version === options.latestVersion ? 'info' : 'warning']"
  />
</template>

<style scoped>
:deep([class^="vpi-"]:not([class$="-icon"])) {
  margin-bottom: 1px;
  font-size: 1.5rem;
  vertical-align: middle;
}

:deep(span.VPLink) {
  padding-inline: 0.75rem;

  font-size: 0.875rem;
  font-weight: normal;
  font-style: italic;
  color: var(--vp-c-text-1);
}

.VPBadge.VPFlyout {
  z-index: 1;
  padding: 0;
  letter-spacing: 0;

  &:deep(.button) {
    height: 1.75rem;
    padding-inline: 0.5rem;

    .text {
      line-height: revert;
    }
  }

  &:deep(.menu) {
    top: 1.75rem;
    right: revert;

    ul {
      margin: 0;
      padding-left: 0;
      list-style: none;

      li {
        margin-top: 0;

        a {
          text-decoration: none;
        }
      }
    }
  }
}
</style>
