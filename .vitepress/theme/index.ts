import mediumZoom from "medium-zoom";
import { type HeadConfig, inBrowser, type Theme, useData, useRouter } from "vitepress";
import { enhanceAppWithTabs } from "vitepress-plugin-tabs/client";
import DefaultTheme from "vitepress/theme";
import { h, nextTick, onMounted, watch } from "vue";

import redirects from "../redirects";
import AuthorsComponent from "./components/AuthorsComponent.vue";
import BannerComponent from "./components/BannerComponent.vue";
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
    const { page, frontmatter, theme } = useData();

    const children = {
      "doc-before": () => [
        frontmatter.value.title ? h("h1", { class: "vp-doc" }, frontmatter.value.title) : null,
        h(AuthorsComponent),
        h(VersionReminder),
      ],
      "aside-outline-after": () => [h(VersionReminder), h(AuthorsComponent)],
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
    const data = useData();
    const router = useRouter();

    const initZoom = () => mediumZoom(".main img", { background: "var(--vp-c-bg)" });
    onMounted(() => initZoom());
    watch(
      () => router.route.path,
      () => nextTick(() => initZoom())
    );

    watch(
      () => data.page.value.isNotFound,
      (isNotFound) => {
        if (!isNotFound || !inBrowser) return;

        const split = router.route.path.toLowerCase().split("/").slice(1);
        const locale = /^..[-_]..$/.test(split[0]) ? split.shift()!.replace("-", "_") : undefined;

        let theRest = split.join("/");
        for (const r of redirects) {
          theRest = theRest.replace(r.from, r.dest);
        }

        const newPath = ["", locale, theRest].filter((s) => s !== undefined).join("/");
        if (router.route.path !== newPath) {
          const newUrl = `${location.origin}${newPath}`;
          ((data.frontmatter.value.head ??= []) as HeadConfig[]).push(
            ["link", { rel: "canonical", href: newUrl }],
            ["meta", { "http-equiv": "refresh", "content": `0; url=${newUrl}` }]
          );
        }
      },
      { immediate: true }
    );
  },
} satisfies Theme;
