import mediumZoom from "medium-zoom";
import * as semver from "semver";
import { inBrowser, type Theme, useData, useRouter } from "vitepress";
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
      // TODO: set link rel=canonical, meta http-equiv=refresh
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
        const version = /^[0-9]+\.[0-9]+/.test(split[0]) ? split.shift()! : undefined;
        let theRest = split.join("/");

        const latestVersion =
          data.site.value.locales.root.themeConfig.nav.at(-1).props.versioningPlugin.latestVersion;
        for (const r of redirects) {
          if (!semver.satisfies(version ?? latestVersion, r.appliesTo ?? "*")) continue;
          if (r.from === theRest) theRest = r.dest;
          if (r.from instanceof RegExp) theRest = theRest.replace(r.from, r.dest);
        }

        const transformed = ["", locale, version, theRest].filter((s) => s !== undefined).join("/");

        if (router.route.path !== transformed) {
          nextTick(() => router.go(transformed));
        }
      },
      { immediate: true }
    );
  },
} satisfies Theme;
