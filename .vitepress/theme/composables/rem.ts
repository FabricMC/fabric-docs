import { useEventListener } from "@vueuse/core";
import { inBrowser } from "vitepress";
import { onMounted, readonly, ref } from "vue";

export const useRem = () => {
  const rem = ref(16);

  const updateRem = () => {
    if (inBrowser) {
      rem.value = parseFloat(getComputedStyle(document.documentElement).fontSize) || 16;
    }
  };

  onMounted(updateRem);
  useEventListener("resize", updateRem);

  return readonly(rem);
};
