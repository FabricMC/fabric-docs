import mediumZoom from "medium-zoom";
import { inBrowser, type Theme, useData, useRouter } from "vitepress";
import { enhanceAppWithTabs } from "vitepress-plugin-tabs/client";
import DefaultTheme from "vitepress/theme";
import { h, nextTick, watch } from "vue";
import Authors from "./layout/Authors.vue";
import Banner from "./layout/Banner.vue";
import ChoiceComponent from "./components/ChoiceComponent.vue";
import ColorSwatch from "./components/ColorSwatch.vue";
import DownloadEntry from "./components/DownloadEntry.vue";
import Range from "./components/Range.vue";
import VersionSwitcher from "./components/VersionSwitcher.vue";
import VideoPlayer from "./components/VideoPlayer.vue";
import FullscreenCode from "./layout/FullscreenCode.vue";
import NotFound from "./layout/NotFound.vue";
import References from "./layout/References.vue";
import "./style.css";

export default {
  extends: DefaultTheme,
  enhanceApp: ({ app }) => {
    enhanceAppWithTabs(app);

    // Custom Components for Pages
    app.component("ChoiceComponent", ChoiceComponent);
    app.component("ColorSwatch", ColorSwatch);
    app.component("DownloadEntry", DownloadEntry);
    app.component("Range", Range);
    app.component("VideoPlayer", VideoPlayer);

    // Versioning Plugin Components
    app.component("VersionSwitcher", VersionSwitcher);
  },
  Layout: () => {
    const { page, theme } = useData();

    const children = {
      "doc-before": () => h(Authors),
      "doc-footer-before": () => h(References),
      "aside-outline-after": () => [h(Authors), h(References)],
      "layout-bottom": () => h(FullscreenCode),
    };

    if (theme.value.env !== "github") {
      (children as any)["layout-top"] = () => h(Banner);
    }

    if (page.value.isNotFound) {
      (children as any)["not-found"] = () => h(NotFound);
    }

    return h(DefaultTheme.Layout, null, children);
  },
  setup: () => {
    const router = useRouter();

    // Replace data-gen head script, which updates head tags
    router.onAfterRouteChange = () => {
      const oldScript = document.querySelector("script[data-gen]");
      if (!oldScript) return;

      const newScript = document.createElement("script");
      newScript.innerHTML = oldScript.innerHTML;
      newScript.setAttribute("data-gen", "");
      oldScript.parentNode!.replaceChild(newScript, oldScript);
    };

    watch(
      () => router.route.path,
      () =>
        nextTick(() => {
          if (!inBrowser) return;
          mediumZoom(".main img", { background: "var(--vp-c-bg)" });
        }),
      { immediate: true }
    );
  },
} satisfies Theme;
