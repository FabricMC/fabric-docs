import { useIcon, withBase } from "vitepress";
import { type MaybeRefOrGetter, toValue } from "vue";

export const useIconSpan = (icon: MaybeRefOrGetter<string>) => {
  const iconClass = useIcon(icon as never).value;

  return import.meta.env.DEV
    ? `<span class="${iconClass}" style="--icon: url('${withBase(`/_vpi/${toValue(icon).replace(":", "/")}.svg`)}');"></span>`
    : `<span class="${iconClass}"></span>`;
};
