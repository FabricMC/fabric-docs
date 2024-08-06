import { existsSync, readdirSync, readFileSync } from "fs";
import { resolve } from "path/posix";
import { DefaultTheme, LocaleConfig } from "vitepress";

import DevelopSidebar from "./sidebars/develop";
import PlayersSidebar from "./sidebars/players";
import { Fabric } from "./types";

function getTranslationsResolver(
  localeFolder: string,
  fileName: string
): (key: string) => string {
  const file = resolve(
    __dirname,
    "..",
    "translated",
    localeFolder === "root" ? ".." : localeFolder,
    fileName
  );

  const strings = existsSync(file)
    ? JSON.parse(readFileSync(file, "utf-8"))
    : {};

  if (localeFolder === "root") {
    return (key: string) => strings[key] || key;
  } else {
    return (key: string) =>
      strings[key] || getTranslationsResolver("root", fileName)(key);
  }
}
export const getWebsiteResolver = (localeFolder: string) =>
  getTranslationsResolver(localeFolder, "website_translations.json");
export const getSidebarResolver = (localeFolder: string) =>
  getTranslationsResolver(localeFolder, "sidebar_translations.json");

export function processExistingEntries(sidebar: DefaultTheme.SidebarMulti): DefaultTheme.SidebarMulti {
  // Get locales from __dirname/../translated/* folder names.
  const localeFolders = readdirSync(resolve(__dirname, "..", "translated"), { withFileTypes: true })
    .filter((dirent) => dirent.isDirectory())
    .map((dirent) => dirent.name);

  // Get all versioning entries that match <locale>/<version>
  // aka, does not match <locale>/players or <locale>/develop or /develop or /players

  const keys = Object.keys(sidebar);
  const versionedEntries = keys.filter((key) => {
    return !key.includes("/players") && !key.includes("/develop") && localeFolders.some((locale) => key.includes(locale));
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

    sidebar[`/${locale}/${version}/players/`] = getLocalisedSidebar(playersToCopy, locale);
    sidebar[`/${locale}/${version}/develop/`] = getLocalisedSidebar(developToCopy, locale);

    // Delete the original entry.
    delete sidebar[entry];
  }

  for (const version of versionsMet) {
    // @ts-ignore
    sidebar[`/${version}/players/`] = getLocalisedSidebar(sidebar[`/${version}/players/`], "root");
    // @ts-ignore
    sidebar[`/${version}/develop/`] = getLocalisedSidebar(sidebar[`/${version}/develop/`], "root");
  }

  return sidebar;
}

export function getLocalisedSidebar(
  sidebar: Fabric.SidebarItem[],
  localeCode: string
): Fabric.SidebarItem[] {
  const sidebarResolver = getSidebarResolver(localeCode);
  const localisedSidebar = JSON.parse(JSON.stringify(sidebar));

  for (const item of localisedSidebar) {
    if (item.translatable === false) {
      continue;
    }

    item.text = sidebarResolver(item.text);

    if (item.link && localeCode !== "root" && item.process !== false) {
      item.link = `/${localeCode}${item.link}`;
    }

    if (item.items) {
      item.items = getLocalisedSidebar(item.items, localeCode);
    }
  }

  return localisedSidebar;
}

/**
 * Generates translated sidebars for a given root directory and sidebars.
 *
 * @param sidebars - An object containing sidebars to translate, keyed by URL.
 * @param dirname - The root directory to generate translated sidebars for.
 * @returns An object containing translated sidebars, keyed by locale URL.
 */
export function generateTranslatedSidebars(
  sidebars: { [url: string]: Fabric.SidebarItem[] },
  dirname: string
): { [localeUrl: string]: Fabric.SidebarItem[] } {
  const translatedSidebars: { [key: string]: Fabric.SidebarItem[] } = {};

  for (const key of Object.keys(sidebars)) {
    const sidebar = sidebars[key];
    translatedSidebars[key] = getLocalisedSidebar(sidebar, "root");
  }

  const translatedFolder = resolve(dirname, "..", "translated");

  for (const folder of readdirSync(translatedFolder, { withFileTypes: true })
    .filter((dirent) => dirent.isDirectory())
    .map((dirent) => dirent.name)) {
    for (const key of Object.keys(sidebars)) {
      const sidebar = sidebars[key];
      translatedSidebars["/" + folder + key] = getLocalisedSidebar(
        sidebar,
        folder
      );
    }
  }

  return translatedSidebars;
}

/**
 * Generates a theme configuration for a given locale.
 *
 * @param localeCode - The code of the locale ("root" for English).
 * @returns A theme configuration object.
 */
function generateTranslatedThemeConfig(localeCode: string): Fabric.ThemeConfig {
  const websiteResolver = getWebsiteResolver(localeCode);

  const crowdinCode = (localeCode: string): string | null => {
    // https://developer.crowdin.com/language-codes
    // TODO: this is hardcoded
    const crowdinOverrides = {
      es_es: "es-ES",
      pt_br: "pt-BR",
      zh_cn: "zh-CN",
      zh_tw: "zh-TW",
    };

    if (localeCode === "root") {
      return null;
    } else {
      // @ts-ignore
      return crowdinOverrides[localeCode] ?? localeCode.split("_")[0];
    }
  };

  return {
    // https://vitepress.dev/reference/default-theme-config
    authors: {
      heading: websiteResolver("authors.heading"),
      nogithub: websiteResolver("authors.nogithub"),
    },

    banner: {
      text: websiteResolver("banner"),
      discord: websiteResolver("social.discord"),
      github: websiteResolver("social.github"),
    },

    darkModeSwitchLabel: websiteResolver("mode_switcher"),

    darkModeSwitchTitle: websiteResolver("mode_dark"),

    docFooter: {
      next: websiteResolver("footer.next"),
      prev: websiteResolver("footer.prev"),
    },

    editLink: {
      pattern: "https://github.com/FabricMC/fabric-docs/edit/main/:path",
      text: websiteResolver("github_edit"),
    },

    externalLinkIcon: true,

    langMenuLabel: websiteResolver("lang_switcher"),

    lastUpdated: {
      text: websiteResolver("last_updated"),
    },

    lightModeSwitchTitle: websiteResolver("mode_light"),

    logo: "/logo.png",

    nav: [
      { text: websiteResolver("nav.home"), link: "https://fabricmc.net/" },
      {
        text: websiteResolver("nav.download"),
        link: "https://fabricmc.net/use",
      },
      {
        text: websiteResolver("nav.contribute"),
        items: [
          // TODO: Expand on this later, with guidelines for loader+loom potentially?
          {
            text: websiteResolver("title"),
            link:
              (localeCode === "root" ? "" : `/${localeCode}`) + "/contributing",
          },
          {
            text: websiteResolver("nav.contribute.api"),
            link: "https://github.com/FabricMC/fabric/blob/1.20.4/CONTRIBUTING.md",
          },
        ],
      },
      {
        // TODO: Allow custom component to have i18n translations for mobile navigation bar.
        component: "VersionSwitcher"
      }
    ],

    notFound: {
      code: websiteResolver("404.code"),
      title: websiteResolver("404.title"),
      quote: websiteResolver("404.quote"),

      crowdinCode: crowdinCode(localeCode),
      crowdinLinkLabel: websiteResolver("404.crowdin_link.label"),
      crowdinLinkText: websiteResolver("404.crowdin_link"),

      englishLinkLabel: websiteResolver("404.english_link.label"),
      englishLinkText: websiteResolver("404.english_link"),

      linkLabel: websiteResolver("404.link.label"),
      linkText: websiteResolver("404.link"),
    },

    outline: {
      label: websiteResolver("outline"),
      level: "deep",
    },

    returnToTopLabel: websiteResolver("return_to_top"),

    search: {
      provider: "local",
      options: {
        translations: {
          button: {
            buttonAriaLabel: websiteResolver("search.button"),
            buttonText: websiteResolver("search.button"),
          },
          modal: {
            backButtonTitle: websiteResolver("search.back"),
            displayDetails: websiteResolver("search.display_details"),
            noResultsText: websiteResolver("search.no_results"),
            resetButtonTitle: websiteResolver("search.reset"),
            footer: {
              closeKeyAriaLabel: websiteResolver("search.footer.close.key"),
              closeText: websiteResolver("search.footer.close"),
              navigateDownKeyAriaLabel: websiteResolver(
                "search.footer.down.key"
              ),
              navigateText: websiteResolver("search.footer.navigate"),
              navigateUpKeyAriaLabel: websiteResolver("search.footer.up.key"),
              selectKeyAriaLabel: websiteResolver("search.footer.select.key"),
              selectText: websiteResolver("search.footer.select"),
            },
          },
        },
      },
    },

    sidebar: generateTranslatedSidebars(
      {
        "/players/": PlayersSidebar,
        "/develop/": DevelopSidebar,
      },
      __dirname
    ),

    sidebarMenuLabel: websiteResolver("sidebar_menu"),

    siteTitle: websiteResolver("title"),

    socialLinks: [
      {
        icon: "github",
        link: "https://github.com/FabricMC/fabric-docs",
        ariaLabel: websiteResolver("social.github"),
      },
      {
        icon: "discord",
        link: "https://discord.gg/v6v4pMv",
        ariaLabel: websiteResolver("social.discord"),
      },
    ],

    versionSwitcher: false,
  };
}

/**
 * Loads locales and generates a LocaleConfig object.
 *
 * @param dirname - The root directory of the project.
 * @returns A LocaleConfig object with locales and their corresponding themeConfig.
 */
export function loadLocales(dirname: string): LocaleConfig<Fabric.ThemeConfig> {
  const translatedFolder = resolve(dirname, "..", "translated");
  const localeFolders = readdirSync(translatedFolder, { withFileTypes: true })
    .filter((dirent) => dirent.isDirectory())
    .map((dirent) => dirent.name);

  const websiteResolver = getWebsiteResolver("root");
  const locales: LocaleConfig<Fabric.ThemeConfig> = {
    root: {
      description: websiteResolver("description"),
      label: "🇬🇧 English",
      lang: "en",
      themeConfig: generateTranslatedThemeConfig("root"),
      title: websiteResolver("title"),
    },
  };

  for (const localeCode of localeFolders) {
    const websiteResolver = getWebsiteResolver(localeCode);

    if (!existsSync(resolve(translatedFolder, localeCode, "index.md"))) {
      continue;
    }

    const language = localeCode.slice(0, 2);
    const region = localeCode.slice(3, 5).toUpperCase();

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
        : localeNameInEnglish.of(language)! +
        " - " +
        regionNameInEnglish.of(region);

    const localisedName =
      localeNameInLocale.of(language)![0].toUpperCase() +
      localeNameInLocale.of(language)!.slice(1);

    const countryFlag = String.fromCodePoint(
      ...region.split("").map((char) => 127397 + char.charCodeAt(0))
    );

    locales[localeCode] = {
      description: websiteResolver("description"),
      label: `${countryFlag} ${localisedName} (${englishName})`,
      lang: localeCode,
      link: `/${localeCode}/`,
      themeConfig: generateTranslatedThemeConfig(localeCode),
      title: websiteResolver("title"),
    };
  }

  return locales;
}
