import { DefaultTheme } from "vitepress";

export interface ExtendedSidebarItem extends DefaultTheme.SidebarItem {
  disableTranslation?: boolean;
  process?: boolean;
  items?: ExtendedSidebarItem[];
}