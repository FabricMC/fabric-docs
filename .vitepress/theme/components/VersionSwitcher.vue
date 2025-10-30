<script setup lang="ts">
import { useData, useRouter } from "vitepress";
import VPFlyout from "vitepress/dist/client/theme-default/components/VPFlyout.vue";
import VPLink from "vitepress/dist/client/theme-default/components/VPLink.vue";
import { computed, onBeforeMount, ref } from "vue";

// Define component properties
const props = defineProps<{
  versioningPlugin: { versions: string[]; latestVersion: string };
  screenMenu?: boolean;
}>();

const data = useData();
const router = useRouter();

const text = computed(() => data.theme.value.version.switcher);

// State refs
const currentVersion = ref<string>(props.versioningPlugin.latestVersion);
const isOpen = ref(false);

// Helper function to find the matching version from the current route path
function getVersionFromPath(path: string): string {
  for (const v of props.versioningPlugin.versions) {
    if (path.includes(`/${v}/`)) {
      return v;
    }
  }
  return props.versioningPlugin.latestVersion;
}

// Before route change, update the current version
router.onBeforeRouteChange = (to: string) => {
  if (to === "/") {
    currentVersion.value = props.versioningPlugin.latestVersion;
    return true;
  }
  currentVersion.value = getVersionFromPath(to);
  return true;
};

// On mount, detect the version from the router's current path
onBeforeMount(() => {
  currentVersion.value = getVersionFromPath(router.route.path);
});

// Toggle the open state in screen menu mode
const toggle = () => {
  isOpen.value = !isOpen.value;
};

// Constructs the appropriate route path for a given version
function buildRoutePath(currentPath: string, locale: string | null, newV: string) {
  const currentV = currentVersion.value;
  const latestV = props.versioningPlugin.latestVersion;
  const pathParts = currentPath.split("/").filter(Boolean);

  // version goes after locale, if the path starts with it
  const versionIndex = pathParts[0] === locale ? 1 : 0;

  // remove the version segment, if present after the locale
  if (pathParts[versionIndex] === currentV) {
    pathParts.splice(versionIndex, 1);
  }

  // insert the new version segment, if needed, after the locale
  if (newV !== latestV) {
    pathParts.splice(versionIndex, 0, newV);
  }

  return "/" + pathParts.join("/") + "/";
}

// Navigate to the selected version
function visitVersion(version: string) {
  const localeKeys = Object.keys(data.site.value.locales);
  const isLocalized = localeKeys.some((key) => router.route.path.startsWith(`/${key}/`));
  const locale = isLocalized
    ? localeKeys.find((key) => router.route.path.startsWith(`/${key}/`)) || null
    : null;

  const route = buildRoutePath(router.route.path, locale, version);

  router.go(route);
  currentVersion.value = version;
}
</script>

<template>
  <!-- Flyout version switcher for desktop -->
  <VPFlyout
    v-if="!screenMenu"
    class="VPVersionSwitcher"
    icon="vpi-versioning"
    :button="currentVersion"
    :label="text"
  >
    <div class="items">
      <!-- Link to the latest version if it's not the current one -->
      <VPLink
        href="#"
        v-if="currentVersion != versioningPlugin.latestVersion"
        @click="visitVersion(versioningPlugin.latestVersion)"
      >
        {{ versioningPlugin.latestVersion }}
      </VPLink>
      <!-- Render links for each version -->
      <template v-for="version in versioningPlugin.versions" :key="version">
        <VPLink href="#" :tag="'a'" v-if="currentVersion != version" @click="visitVersion(version)">
          {{ version }}
        </VPLink>
      </template>
    </div>
  </VPFlyout>

  <!-- Screen menu switcher (e.g. mobile) -->
  <div v-else class="VPScreenVersionSwitcher" :class="{ open: isOpen }">
    <button
      class="button"
      aria-controls="navbar-group-version"
      :aria-expanded="isOpen"
      @click="toggle"
    >
      <span class="button-text"> <span class="vpi-versioning icon" />{{ text }} </span>
      <span class="vpi-plus button-icon" />
    </button>

    <div id="navbar-group-version" class="items">
      <VPLink href="#" @click="visitVersion(versioningPlugin.latestVersion)">
        {{ versioningPlugin.latestVersion }}
      </VPLink>
      <template v-for="version in versioningPlugin.versions" :key="version">
        <VPLink href="#" @click="visitVersion(version)">
          {{ version }}
        </VPLink>
      </template>
    </div>
  </div>
</template>

<style scoped>
.vpi-versioning.option-icon {
  margin-right: 2px !important;
}

.link {
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
.link:hover {
  color: var(--vp-c-brand-1);
  background-color: var(--vp-c-default-soft);
}
.link.active {
  color: var(--vp-c-brand-1);
}
.VPVersionSwitcher {
  display: flex;
  align-items: center;
}
.icon {
  padding: 8px;
}
.title {
  padding: 0 24px 0 12px;
  line-height: 32px;
  font-size: 14px;
  font-weight: 700;
  color: var(--vp-c-text-1);
}
.VPScreenVersionSwitcher {
  border-bottom: 1px solid var(--vp-c-divider);
  height: 48px;
  overflow: hidden;
  transition: border-color 0.5s;
}
.VPScreenVersionSwitcher .items {
  visibility: hidden;
}
.VPScreenVersionSwitcher.open .items {
  visibility: visible;
}
.VPScreenVersionSwitcher.open {
  padding-bottom: 10px;
  height: auto;
}
.VPScreenVersionSwitcher.open .button {
  padding-bottom: 6px;
  color: var(--vp-c-brand-1);
}
.VPScreenVersionSwitcher.open .button-icon {
  transform: rotate(45deg);
}
.VPScreenVersionSwitcher button .icon {
  margin-right: 8px;
}
.button {
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
}
.button:hover {
  color: var(--vp-c-brand-1);
}
.button-icon {
  transition: transform 0.25s;
}
.group:first-child {
  padding-top: 0px;
}
.group + .group,
.group + .item {
  padding-top: 4px;
}
</style>
