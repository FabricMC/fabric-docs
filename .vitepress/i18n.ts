import { existsSync, readdirSync, readFileSync } from "fs";
import { resolve } from "path/posix";
import { LocaleConfig } from "vitepress";
import { Versioned } from "vitepress-versioning-plugin";

import Develop from "./sidebars/develop";
import Players from "./sidebars/players";
import { Fabric } from "./types";

const getResolver = (locale: string, fileName: string): ((k: string) => string) => {
  const file = resolve(__dirname, "..", "translated", locale === "root" ? ".." : locale, fileName);

  const strings: Record<string, string> = existsSync(file)
    ? JSON.parse(readFileSync(file, "utf-8"))
    : {};

  if (locale === "root") {
    return (k: string) => strings[k] || k;
  } else {
    return (k: string) => strings[k] || getResolver("root", fileName)(k);
  }
};

const getSidebarResolver = (locale: string) => getResolver(locale, "sidebar_translations.json");

export const getWebsiteResolver = (locale: string) =>
  getResolver(locale, "website_translations.json");

export const processExistingEntries = (sidebar: Versioned.Sidebar): Versioned.Sidebar => {
  // Get locales from __dirname/../translated/* folder names.
  const locales = readdirSync(resolve(__dirname, "..", "translated"), {
    withFileTypes: true,
  })
    .filter((dirent) => dirent.isDirectory())
    .map((dirent) => dirent.name);

  // Get all versioning entries that match <locale>/<version>
  // aka, does not match <locale>/players or <locale>/develop or /develop or /players

  const keys = Object.keys(sidebar);
  const versionedEntries = keys.filter((key) => {
    return (
      !key.includes("/players")
      && !key.includes("/develop")
      && locales.some((locale) => key.includes(locale))
    );
  });

  const versionsMet = new Set<string>();
  // Now, ensure that <locale>/<version>/players and <locale>/<version>/develop are included in the sidebar. Use <version>/players and <version>/develop as the key.
  for (const entry of versionedEntries) {
    const split = entry.split("/");
    const locale = split[1];
    const version = split[2];

    versionsMet.add(version);

    const playersToCopy = JSON.parse(JSON.stringify(sidebar[`/${version}/players/`]));
    const developToCopy = JSON.parse(JSON.stringify(sidebar[`/${version}/develop/`]));

    sidebar[`/${locale}/${version}/players/`] = getLocalizedSidebar(playersToCopy, locale);
    sidebar[`/${locale}/${version}/develop/`] = getLocalizedSidebar(developToCopy, locale);

    // Delete the original entry.
    delete sidebar[entry];
  }

  for (const version of versionsMet) {
    sidebar[`/${version}/players/`] = getLocalizedSidebar(
      sidebar[`/${version}/players/`] as Fabric.SidebarItem[],
      "root"
    );
    sidebar[`/${version}/develop/`] = getLocalizedSidebar(
      sidebar[`/${version}/develop/`] as Fabric.SidebarItem[],
      "root"
    );
  }

  return sidebar;
};

const getLocalizedSidebar = (
  sidebar: Fabric.SidebarItem[],
  locale: string
): Fabric.SidebarItem[] => {
  const resolver = getSidebarResolver(locale);
  const localizedSidebar = JSON.parse(JSON.stringify(sidebar));

  for (const item of localizedSidebar) {
    if (item.translatable === false) {
      continue;
    }

    item.text = resolver(item.text);

    if (item.link && locale !== "root" && item.process !== false) {
      item.link = `/${locale}${item.link}`;
    }

    if (item.items) {
      item.items = getLocalizedSidebar(item.items, locale);
    }
  }

  return localizedSidebar;
};

/**
 * Generates translated sidebars for a given root directory and sidebars.
 *
 * @param sidebars - An object containing sidebars to translate, keyed by URL.
 * @returns An object containing translated sidebars, keyed by locale URL.
 */
const translateSidebars = (
  sidebars: Record<string, Fabric.SidebarItem[]>
): Record<string, Fabric.SidebarItem[]> => {
  const translatedSidebars: Record<string, Fabric.SidebarItem[]> = {};

  for (const key of Object.keys(sidebars)) {
    const sidebar = sidebars[key];
    translatedSidebars[key] = getLocalizedSidebar(sidebar, "root");
  }

  const translatedFolder = resolve(__dirname, "..", "translated");

  for (const folder of readdirSync(translatedFolder, { withFileTypes: true })
    .filter((dirent) => dirent.isDirectory())
    .map((dirent) => dirent.name)) {
    for (const key of Object.keys(sidebars)) {
      const sidebar = sidebars[key];
      translatedSidebars[`/${folder}${key}`] = getLocalizedSidebar(sidebar, folder);
    }
  }

  return translatedSidebars;
};

/**
 * Generates a theme configuration for a given locale.
 *
 * @param locale - The code of the locale ("root" for English).
 * @returns A theme configuration object.
 */
const translateThemeConfig = (locale: string): Fabric.ThemeConfig => {
  const resolver = getWebsiteResolver(locale);

  const crowdinCode = (localeCode: string): string | null => {
    // https://developer.crowdin.com/language-codes
    // TODO: this is hardcoded
    const crowdinOverrides: Record<string, string> = {
      es_es: "es-ES",
      pt_br: "pt-BR",
      zh_cn: "zh-CN",
      zh_tw: "zh-TW",
    };

    if (localeCode === "root") {
      return null;
    } else {
      return crowdinOverrides[localeCode] ?? localeCode.split("_")[0];
    }
  };

  return {
    // https://vitepress.dev/reference/default-theme-config
    authors: {
      heading: resolver("authors.heading"),
      noGitHub: resolver("authors.no_github"),
    },

    banner: {
      text: resolver("banner"),
      discord: resolver("social.discord"),
      github: resolver("social.github"),
    },

    darkModeSwitchLabel: resolver("mode_switcher"),

    darkModeSwitchTitle: resolver("mode_dark"),

    docFooter: {
      next: resolver("footer.next"),
      prev: resolver("footer.prev"),
    },

    download: {
      text: resolver("download"),
    },

    editLink: {
      pattern: "https://github.com/FabricMC/fabric-docs/edit/main/:path",
      text: resolver("github_edit"),
    },

    externalLinkIcon: true,

    footer: {
      copyright: resolver("footer.copyright").replace(
        "%s",
        `<a href=\"https://github.com/FabricMC/fabric-docs/blob/main/LICENSE\" target=\"_blank\" rel=\"noreferrer\">${resolver(
          "footer.license"
        )}</a>`
      ),
      message: resolver("footer.message"),
    },

    langMenuLabel: resolver("lang_switcher"),

    lastUpdated: {
      text: resolver("last_updated"),
    },

    lightModeSwitchTitle: resolver("mode_light"),

    logo: "/logo.png",

    nav: [
      { text: resolver("nav.home"), link: "https://fabricmc.net/" },
      {
        text: resolver("nav.download"),
        link: "https://fabricmc.net/use",
      },
      {
        text: resolver("nav.contribute"),
        items: [
          // TODO: Expand on this later, with guidelines for loader+loom potentially?
          {
            text: resolver("title"),
            link: `${locale === "root" ? "" : `/${locale}`}/contributing`,
          },
          {
            text: resolver("nav.contribute.api"),
            link: "https://github.com/FabricMC/fabric/blob/1.20.4/CONTRIBUTING.md",
          },
        ],
      },
      {
        component: "VersionSwitcher",
      },
    ],

    notFound: {
      code: resolver("404.code"),
      title: resolver("404.title"),
      quote: resolver("404.quote"),

      crowdinCode: crowdinCode(locale),
      crowdinLinkLabel: resolver("404.crowdin_link.label"),
      crowdinLinkText: resolver("404.crowdin_link"),

      englishLinkLabel: resolver("404.english_link.label"),
      englishLinkText: resolver("404.english_link"),

      linkLabel: resolver("404.link.label"),
      linkText: resolver("404.link"),
    },

    outline: {
      label: resolver("outline"),
      level: "deep",
    },

    returnToTopLabel: resolver("return_to_top"),

    search: {
      provider: "local",
      options: {
        translations: {
          button: {
            buttonAriaLabel: resolver("search.button"),
            buttonText: resolver("search.button"),
          },
          modal: {
            backButtonTitle: resolver("search.back"),
            displayDetails: resolver("search.display_details"),
            noResultsText: resolver("search.no_results"),
            resetButtonTitle: resolver("search.reset"),
            footer: {
              closeKeyAriaLabel: resolver("search.footer.close.key"),
              closeText: resolver("search.footer.close"),
              navigateDownKeyAriaLabel: resolver("search.footer.down.key"),
              navigateText: resolver("search.footer.navigate"),
              navigateUpKeyAriaLabel: resolver("search.footer.up.key"),
              selectKeyAriaLabel: resolver("search.footer.select.key"),
              selectText: resolver("search.footer.select"),
            },
          },
        },
      },
    },

    sidebar: translateSidebars({ "/players/": Players, "/develop/": Develop }),

    sidebarMenuLabel: resolver("sidebar_menu"),

    siteTitle: resolver("title"),

    socialLinks: [
      {
        icon: "github",
        link: "https://github.com/FabricMC/fabric-docs",
        ariaLabel: resolver("social.github"),
      },
      {
        icon: "discord",
        link: "https://discord.gg/v6v4pMv",
        ariaLabel: resolver("social.discord"),
      },
      {
        icon: "crowdin",
        link: `https://crowdin.com/project/fabricmc/${crowdinCode(locale) ?? ""}`,
        ariaLabel: resolver("social.crowdin"),
      },
    ],

    version: {
      reminder: resolver("version.reminder"),
      switcher: resolver("version.switcher"),
    },

    versionSwitcher: false,
  };
};

/**
 * Loads locales and generates a LocaleConfig object.
 *
 * @returns A LocaleConfig object with locales and their corresponding themeConfig.
 */
export const getLocales = (): LocaleConfig<Fabric.ThemeConfig> => {
  const translatedFolder = resolve(__dirname, "..", "translated");
  const localeFolders = readdirSync(translatedFolder, { withFileTypes: true })
    .filter((dirent) => dirent.isDirectory())
    .map((dirent) => dirent.name);

  const resolver = getWebsiteResolver("root");
  const locales: LocaleConfig<Fabric.ThemeConfig> = {
    root: {
      description: resolver("description"),
      label: "🇬🇧 English",
      lang: "en",
      themeConfig: translateThemeConfig("root"),
      title: resolver("title"),
    },
  };

  for (const locale of localeFolders) {
    const resolver = getWebsiteResolver(locale);

    if (!existsSync(resolve(translatedFolder, locale, "index.md"))) {
      continue;
    }

    const language = locale.slice(0, 2);
    const region = locale.slice(3, 5).toUpperCase();

    const localeNameInLocale = new Intl.DisplayNames(language, {
      type: "language",
    });
    const localeNameInEnglish = new Intl.DisplayNames("en", {
      type: "language",
    });
    const regionNameInEnglish = new Intl.DisplayNames(["en"], {
      type: "region",
    });

    const englishName =
      language === region.toLowerCase()
        ? localeNameInEnglish.of(language)!
        : `${localeNameInEnglish.of(language)!} - ${regionNameInEnglish.of(region)}`;

    const localizedName = `${localeNameInLocale.of(language)![0].toUpperCase()}${localeNameInLocale
      .of(language)!
      .slice(1)}`;

    const countryFlag = String.fromCodePoint(
      ...region.split("").map((char) => 127397 + char.charCodeAt(0))
    );

    locales[locale] = {
      description: resolver("description"),
      label: `${countryFlag} ${localizedName} (${englishName})`,
      lang: locale,
      link: `/${locale}/`,
      themeConfig: translateThemeConfig(locale),
      title: resolver("title"),
    };
  }

  return locales;
};
