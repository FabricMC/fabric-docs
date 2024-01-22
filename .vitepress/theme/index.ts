import DefaultTheme from 'vitepress/theme'
import { h } from 'vue'

import PageAuthorComponent from './components/PageAuthorComponent.vue'

// Import style fixes and customizations.
import './style.css'

export default {
  extends: DefaultTheme,
  Layout() {
    return h(DefaultTheme.Layout, null, {
      'aside-outline-after': () => h(PageAuthorComponent)
    })
  }
};
