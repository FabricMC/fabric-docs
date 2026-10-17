import { SiteConfig, SiteData, UserConfig } from "vitepress";
import { Versioned } from "vitepress-versioning-plugin";
import { ALL_VERSIONS, LATEST_VERSION } from "./constants.ts";

export interface Translations {
  sidebar: typeof import("../sidebar_translations.json");
  website: typeof import("../website_translations.json");
}

export type SidebarItem =
  | string
  | (Omit<Versioned.SidebarItem, "text" | "link" | "base" | "items">
      & (
        | { text: string; base?: never; link?: never; items: SidebarItem[] }
        | { text?: never; base?: string; link: string; items?: SidebarItem[] }
        | { text?: never; base: string; link?: never; items: SidebarItem[] }
      ));

export interface ThemeConfig extends Versioned.ThemeConfig {
  authors: {
    /**
     * @default "Page Authors"
     */
    heading: string;

    /**
     * @default "%s (not from GitHub)"
     */
    noGitHub: string;
  };

  banner: {
    local: {
      /**
       * @default "This is a local build"
       */
      build: string;

      /**
       * @default "This is a local preview"
       */
      dev: string;
    };

    pr: {
      /**
       * @default "This is a preview of %s"
       */
      text: string;

      /**
       * @default "Pull Request #%d"
       */
      link: string;
    };
  };

  code: {
    /**
     * @default "Full Screen"
     */
    enterFullscreen: string;

    /**
     * @default "Exit Full Screen"
     */
    exitFullscreen: string;

    /**
     * @default "Toggle Wrap Code"
     */
    wrap: string;
  };

  download: {
    /**
     * Set custom text for download button.
     *
     * @default "Download %s"
     */
    text: string;
  };

  env: typeof import("./constants.ts").ENV;

  notFound: {
    /**
     * @default "404"
     */
    code: string;

    /**
     * @default "Open the Crowdin editor"
     */
    crowdinLinkLabel: string;

    /**
     * @default "Localize on Crowdin"
     */
    crowdinLinkText: string;

    /**
     * @default "Open the English version"
     */
    englishLinkLabel: string;

    /**
     * @default "Read in English"
     */
    englishLinkText: string;

    /**
     * @default "Go to the home page"
     */
    linkLabel: string;

    /**
     * @default "Take Me Home"
     */
    linkText: string;

    /**
     * The possible different quotes.
     */
    quotes: string[];

    /**
     * @default "Page not found"
     */
    title: string;

    /**
     * @default "Page knot found"
     */
    pooh: string;
  };

  references: {
    /**
     * @default "Files Referenced"
     */
    files: string;

    /**
     * @default "Sources & Resources"
     */
    resources: string;
  };

  sidebar: Record<string, SidebarItem[]>;

  version: {
    allVersions: typeof ALL_VERSIONS;
    latestVersion: typeof LATEST_VERSION;

    /**
     * @default "Minecraft %s"
     */
    switcherLabel: string;

    /**
     * @default "Select Version"
     */
    switcherTitle: string;

    /**
     * @default "No other versions"
     */
    noOtherVersions: string;
  };
}

export type Config = UserConfig<ThemeConfig> & Versioned.Config;

declare global {
  var VITEPRESS_CONFIG: SiteConfig<ThemeConfig>;

  interface Window {
    __FD_PAGE_VERSIONS__?: Record<string, string[]>;
    __VP_HASH_MAP__?: Record<string, string>;
    __VP_SITE_DATA__?: SiteData<ThemeConfig>;
  }
}
