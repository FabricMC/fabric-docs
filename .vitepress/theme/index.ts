import mediumZoom from "medium-zoom";
import { Theme, useData, useRoute } from "vitepress";
import DefaultTheme from "vitepress/theme";
import { h, nextTick, onMounted, watch } from "vue";

import AuthorsComponent from "./components/AuthorsComponent.vue";
import ColorSwatch from "./components/ColorSwatch.vue";
import DownloadEntry from "./components/DownloadEntry.vue";
import NotFoundComponent from "./components/NotFoundComponent.vue";
import VersionReminder from "./components/VersionReminder.vue";
import VersionSwitcher from "./components/VersionSwitcher.vue";
import VideoPlayer from "./components/VideoPlayer.vue";

import "./style.css";

export default {
  extends: DefaultTheme,
  enhanceApp({ app }) {
    // Vidstack Videoplayer Component
    app.config.compilerOptions.isCustomElement = (tag) =>
      tag.startsWith("media-");
    app.component("VideoPlayer", VideoPlayer);

    // Custom Components for Pages
    app.component("DownloadEntry", DownloadEntry);
    app.component("ColorSwatch", ColorSwatch);

    // Versioning Plugin Components
    app.component("VersionSwitcher", VersionSwitcher);
  },
  Layout() {
    const children = {
      "doc-before": () => h(VersionReminder),
      "aside-outline-before": () => h(VersionReminder),
      "aside-outline-after": () => h(AuthorsComponent),
    };

    if (useData().page.value.isNotFound) {
      children["not-found"] = () => h(NotFoundComponent);
    }

    return h(DefaultTheme.Layout, null, children);
  },
  setup() {
    const route = useRoute();
    const initZoom = () => {
      mediumZoom(".main img", { background: "var(--vp-c-bg)" });
    };
    onMounted(() => {
      initZoom();
    });
    watch(
      () => route.path,
      () => nextTick(() => initZoom())
    );
  },
} satisfies Theme;
