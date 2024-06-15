import DefaultTheme from 'vitepress/theme'
import { Theme, useRoute } from 'vitepress';
import { h, nextTick, onMounted, watch } from 'vue'

import mediumZoom from 'medium-zoom';

import PageAuthorComponent from './components/PageAuthorComponent.vue'
import BannerComponent from './components/BannerComponent.vue'
import DownloadEntry from './components/DownloadEntry.vue'
import ColorSwatch from './components/ColorSwatch.vue'

// Import style fixes and customizations.
import './style.css'

export default {
  extends: DefaultTheme,
  enhanceApp({ app }) {
    app.component('DownloadEntry', DownloadEntry)
    app.component('ColorSwatch', ColorSwatch)
  },
  Layout() {
    return h(DefaultTheme.Layout, null, {
      'aside-outline-after': () => h(PageAuthorComponent),
      'layout-top': () => h(BannerComponent)
    })
  },
  setup() {
    const route = useRoute();
    const initZoom = () => {
      mediumZoom('.main img', { background: 'var(--vp-c-bg)' });
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
