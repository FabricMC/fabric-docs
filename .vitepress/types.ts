import { DefaultTheme, UserConfig } from "vitepress";
import { Versioned } from "vitepress-versioning-plugin";

export namespace Fabric {
  export interface AuthorsOptions {
    /**
     * @default "Page Authors"
     */
    heading: string;

    /**
     * @default "%s (not from GitHub)"
     */
    noGitHub: string;
  }

  export interface BannerOptions {
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
  }

  export interface DownloadOptions {
    /**
     * Set custom text for download button.
     *
     * @default "Download %s"
     */
    text: string;
  }

  export type EnvOptions = "build" | "dev" | "github" | number;

  export interface NotFoundOptions {
    /**
     * @default "404"
     */
    code: string;

    /**
     * The locale's code on Crowdin
     */
    crowdinLocale: string;

    /**
     * Set aria label for Crowdin link.
     *
     * @default "Open the Crowdin editor"
     */
    crowdinLinkLabel: string;

    /**
     * Set custom Crowdin link text.
     *
     * @default "Localize on Crowdin"
     */
    crowdinLinkText: string;

    /**
     * Set aria label for English link.
     *
     * @default "Open the English version"
     */
    englishLinkLabel: string;

    /**
     * Set custom English link text.
     *
     * @default "Read in English"
     */
    englishLinkText: string;

    /**
     * Set aria label for home link.
     *
     * @default "Go to the home page"
     */
    linkLabel: string;

    /**
     * Set custom home link text.
     *
     * @default "Take Me Home"
     */
    linkText: string;

    /**
     * Set custom not found description.
     *
     * @default "This page got tangled in the yarn"
     */
    quote: string;

    /**
     * The possible different quotes.
     */
    quotes: string[];

    /**
     * Set custom not found message.
     *
     * @default "Page not found"
     */
    title: string;

    /**
     * Special not found message.
     *
     * @default "Page knot found"
     */
    pooh: string;
  }

  export interface VersionOptions {
    reminder: {
      /**
       * @default "This page is written for version %s."
       */
      latestVersion: string;

      /**
       * @default "This page is written for version %s.\nDocumentation for older versions may be incomplete."
       */
      oldVersionMojang: string;

      /**
       * @default "This page is written for version %s in Yarn Mappings.\nDocumentation for older versions may be incomplete."
       */
      oldVersionYarn: string;
    };

    switcher: {
      /**
       * @default "Minecraft %s"
       */
      label: string;

      /**
       * @default "No other versions"
       */
      none: string;
    };
  }

  export interface SidebarItem extends Versioned.SidebarItem {
    text: string;
    items?: SidebarItem[];
  }

  export interface Sidebar extends DefaultTheme.SidebarMulti {
    [path: string]: SidebarItem[];
  }

  export interface ThemeConfig extends Versioned.ThemeConfig {
    authors: AuthorsOptions;
    banner: BannerOptions;
    download: DownloadOptions;
    env: EnvOptions;
    notFound: NotFoundOptions;
    sidebar: Sidebar;
    version: VersionOptions;
  }

  export type Config = UserConfig<ThemeConfig> & Versioned.Config;
}
