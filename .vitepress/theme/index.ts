import mediumZoom from "medium-zoom";
import { inBrowser, type Theme, useData, useRouter } from "vitepress";
import { enhanceAppWithTabs } from "vitepress-plugin-tabs/client";
import DefaultTheme from "vitepress/theme";
import { h, nextTick, watch } from "vue";
import AuthorsComponent from "./components/AuthorsComponent.vue";
import BannerComponent from "./components/BannerComponent.vue";
import ChoiceComponent from "./components/ChoiceComponent.vue";
import ColorSwatch from "./components/ColorSwatch.vue";
import DownloadEntry from "./components/DownloadEntry.vue";
import NotFoundComponent from "./components/NotFoundComponent.vue";
import References from "./components/References.vue";
import VersionSwitcher from "./components/VersionSwitcher.vue";
import VideoPlayer from "./components/VideoPlayer.vue";
import "./style.css";

export default {
  extends: DefaultTheme,
  enhanceApp: ({ app }) => {
    enhanceAppWithTabs(app);

    // VidStack VideoPlayer Component
    app.config.compilerOptions.isCustomElement = (tag) => tag.startsWith("media-");

    // Custom Components for Pages
    app.component("ChoiceComponent", ChoiceComponent);
    app.component("ColorSwatch", ColorSwatch);
    app.component("DownloadEntry", DownloadEntry);
    app.component("VideoPlayer", VideoPlayer);

    // Versioning Plugin Components
    app.component("VersionSwitcher", VersionSwitcher);
  },
  Layout: () => {
    const { page, theme } = useData();

    const children = {
      "doc-before": () => h(AuthorsComponent),
      "doc-footer-before": () => h(References),
      "aside-outline-after": () => [h(AuthorsComponent), h(References)],
    };

    if (theme.value.env !== "github") {
      (children as any)["layout-top"] = () => h(BannerComponent);
    }

    if (page.value.isNotFound) {
      (children as any)["not-found"] = () => h(NotFoundComponent);
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
