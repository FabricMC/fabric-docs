import mediumZoom from "medium-zoom";
import { type Theme, useData, useRoute } from "vitepress";
import DefaultTheme from "vitepress/theme";
import { h, nextTick, onMounted, watch } from "vue";

import AuthorsComponent from "./components/AuthorsComponent.vue";
import ChoiceComponent from "./components/ChoiceComponent.vue";
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
    // VidStack VideoPlayer Component
    app.config.compilerOptions.isCustomElement = (tag) =>
      tag.startsWith("media-");

    // Custom Components for Pages
    app.component("ChoiceComponent", ChoiceComponent);
    app.component("ColorSwatch", ColorSwatch);
    app.component("DownloadEntry", DownloadEntry);
    app.component("VideoPlayer", VideoPlayer);

    // Versioning Plugin Components
    app.component("VersionSwitcher", VersionSwitcher);
  },
  Layout() {
    const { page, frontmatter } = useData();

    const children = {
      "doc-before": () => [
        frontmatter.value.title
          ? h("h1", { class: "vp-doc" }, frontmatter.value.title)
          : null,
        h(VersionReminder),
        h(AuthorsComponent),
      ],
      "aside-outline-before": () => h(VersionReminder),
      "aside-outline-after": () => h(AuthorsComponent),
    };

    if (page.value.isNotFound) {
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
