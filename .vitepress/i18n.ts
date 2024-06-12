import { existsSync, readdirSync, readFileSync } from "fs";
import { resolve } from "path/posix";
import { DefaultTheme, LocaleConfig } from "vitepress";
import DevelopSidebar from "./sidebars/develop";
import PlayersSidebar from './sidebars/players';
import { ExtendedSidebarItem } from "./sidebars/utils";

/**
 * Loads locales and generates a LocaleConfig object.
 * 
 * @param rootDir - The root directory of the project.
 * @returns A LocaleConfig object with locales and their corresponding themeConfig.
 */
export function loadLocales(rootDir: string): LocaleConfig<DefaultTheme.Config> {
  const translatedFolder = resolve(rootDir, "..", "translated");
  const translatedFolders = readdirSync(translatedFolder, { withFileTypes: true })
    .filter(dirent => dirent.isDirectory())
    .map(dirent => dirent.name);

  const locales: LocaleConfig<DefaultTheme.Config> = {
    root: {
      label: 'English',
      lang: 'en',
      themeConfig: generateTranslatedThemeConfig(null)
    }
  };

  for (const folder of translatedFolders) {
    if (!existsSync(resolve(translatedFolder, folder, "index.md"))) {
      continue;
    }

    let firstHalf: string = folder.slice(0, 2);
    let secondHalf: string = folder.slice(3, 5);

    let locale = new Intl.DisplayNames([`${firstHalf}-${secondHalf.toUpperCase()}`], { type: 'language' });
    let localeName = locale?.of(`${firstHalf}-${secondHalf.toUpperCase()}`)!;

    // Capitalize the first letter of the locale name
    localeName = localeName.charAt(0).toUpperCase() + localeName.slice(1);

    locales[folder] = {
      label: localeName,
      link: `/${folder}/`,
      lang: folder,
      themeConfig: generateTranslatedThemeConfig(folder),
    }
  }

  return locales;
}

/**
 * Returns a resolving function for any navbar strings.
 * @param localDir - The directory of the locale (null for English).
 */
function getNavbarResolver(localDir: string | null): (key: string) => string {
  // Load navbar_translations.json of locale and english.
  const fallbackTranslations = JSON.parse(readFileSync(resolve(__dirname, "..", "navbar_translations.json"), "utf-8"));
  let translations: any;

  if(localDir == null) {
    translations = fallbackTranslations
  } else {
    if(!existsSync(resolve("translated", localDir, "navbar_translations.json"))) {
      translations = fallbackTranslations;
    } else {
      translations = JSON.parse(readFileSync(resolve("translated", localDir, "navbar_translations.json"), "utf-8"));
    }
  }

  return (key: string) => {
    return translations[key] ?? fallbackTranslations[key] ?? key;
  }
}

/**
 * Generates a theme configuration for a given locale.
 * 
 * @param localeDir - The directory of the locale (null for English).
 * @returns A theme configuration object.
 */
function generateTranslatedThemeConfig(localeDir: string | null): DefaultTheme.Config {
  const navbarResolver = getNavbarResolver(localeDir);

  return {
    // https://vitepress.dev/reference/default-theme-config
    nav: [
      { text: navbarResolver('home'), link: 'https://fabricmc.net/' },
      { text: navbarResolver('download'), link: 'https://fabricmc.net/use' },
      {
        text: navbarResolver('contribute'), items: [
          // TODO: Expand on this later, with guidelines for loader+loom potentially?
          {
            text: navbarResolver('title'),
            link: `${localeDir ? `/${localeDir}` : ''}/contributing`
          },
          {
            text: navbarResolver('contribute.api'),
            link: 'https://github.com/FabricMC/fabric/blob/1.20.4/CONTRIBUTING.md'
          }
        ]
      },
    ],

    // TODO: localise version switcher

    search: {
      provider: 'local'
    },

    outline: "deep",

    sidebar: generateTranslatedSidebars(__dirname, {
      '/players/': PlayersSidebar,
      '/develop/': DevelopSidebar,
    }),

    editLink: {
      pattern: ({ filePath }) => {
        return `https://github.com/FabricMC/fabric-docs/edit/main/${filePath}`
      },
      text: localeDir ? readTranslations(resolve(__dirname, localeDir))['github.edit'] : 'Edit this page on GitHub'
    },

    socialLinks: [
      { icon: 'github', link: 'https://github.com/FabricMC/fabric-docs' },
      { icon: 'discord', link: 'https://discord.gg/v6v4pMv' }
    ],

    logo: "/logo.png",

    siteTitle: navbarResolver('title')
  };
}

/**
 * Generates translated sidebars for a given root directory and sidebars.
 * 
 * @param rootDir - The root directory to generate translated sidebars for.
 * @param sidebars - An object containing sidebars to translate, keyed by URL.
 * @returns An object containing translated sidebars, keyed by locale URL.
 */
function generateTranslatedSidebars(rootDir: string, sidebars: { [url: string]: ExtendedSidebarItem[] }): { [localeUrl: string]: ExtendedSidebarItem[] } {
  function applyTranslations(
    locale: string,
    translationSource: { [key: string]: string },
    fallbackSource: { [key: string]: string },
    sidebar: ExtendedSidebarItem[]
  ): ExtendedSidebarItem[] {
    const sidebarCopy = JSON.parse(JSON.stringify(sidebar));

    for (const item of sidebarCopy) {
      if (item.disableTranslation) continue;

      item.text = translationSource[item.text] ?? fallbackSource[item.text];

      if (item.link && locale !== "en_us" && item.process !== false) {
        item.link = `/${locale}${item.link}`;
      }

      if (item.items) {
        item.items = applyTranslations(locale, translationSource, fallbackSource, item.items);
      }
    }

    return sidebarCopy;
  }

  const englishFallbacks = readTranslations(resolve(rootDir, ".."));
  const translatedFolder = resolve(rootDir, "..", "translated");
  const translatedFolders = readdirSync(translatedFolder, { withFileTypes: true })
    .filter(dirent => dirent.isDirectory())
    .map(dirent => dirent.name);

  const translatedSidebars: { [localeUrl: string]: ExtendedSidebarItem[] } = {};

  for (const sidebarPair of Object.entries(sidebars)) {
    const [url, sidebar] = sidebarPair;
    translatedSidebars[url] = applyTranslations("en_us", englishFallbacks, englishFallbacks, sidebar);
  }

  for (const folder of translatedFolders) {
    const translations = readTranslations(resolve(translatedFolder, folder));
    for (const sidebarPair of Object.entries(sidebars)) {
      const [url, sidebar] = sidebarPair;
      translatedSidebars[`/${folder}${url}`] = applyTranslations(folder, translations, englishFallbacks, sidebar);
    }
  }

  return translatedSidebars;
}

function readTranslations(folder: string | null): { [key: string]: string } {
  folder ??= resolve(__dirname, '..');
  const sidebarPath = resolve(folder, "sidebar_translations.json");
  if (!existsSync(sidebarPath)) {
    return readTranslations(null);
  }

  return JSON.parse(readFileSync(sidebarPath, "utf-8"));
}
