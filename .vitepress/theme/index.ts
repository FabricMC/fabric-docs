import { useData } from "vitepress";
import DefaultTheme from "vitepress/theme";
import { h } from "vue";

import BannerComponent from "./components/BannerComponent.vue";
import NotFoundComponent from "./components/NotFoundComponent.vue";
import AuthorsComponent from "./components/AuthorsComponent.vue";

import "./style.css";

export default {
  extends: DefaultTheme,
  Layout() {
    const children = {
      "aside-outline-after": () => h(AuthorsComponent),
      "layout-top": () => h(BannerComponent),
    };

    if (useData().page.value.isNotFound) {
      children["not-found"] = () => h(NotFoundComponent);
    }

    return h(DefaultTheme.Layout, null, children);
  },
};
