import { existsSync, readdirSync, readFileSync } from "fs";
import { resolve } from "path/posix";
import { ExtendedSidebarItem } from "./sidebars/utils";
import { DefaultTheme, LocaleConfig } from "vitepress";
import PlayersSidebar from './sidebars/players'
import DevelopSidebar from "./sidebars/develop"

export function applyTranslations(locale: string, translationSource: { [key: string]: string; }, fallbackSource: { [key: string]: string }, sidebar: ExtendedSidebarItem[]): ExtendedSidebarItem[] {
  const sidebarCopy = JSON.parse(JSON.stringify(sidebar));

  for (const item of sidebarCopy) {
    if (item.disableTranslation) continue;

    if (!translationSource[item.text]) {
      if (fallbackSource[item.text]) {
        item.text = fallbackSource[item.text];
      }
    } else {
      item.text = translationSource[item.text];
    }

    if (item.link && locale !== "en_us" && item.process !== false) {
      // Prefix the link with the locale
      item.link = `/${locale}${item.link}`;
    }

    if (item.items) {
      item.items = applyTranslations(locale, translationSource, fallbackSource, item.items);
    }
  }

  return sidebarCopy;
}

export function generateTranslatedSidebars(_rootDir: string, sidebars: { [url: string]: ExtendedSidebarItem[]; }): { [localeUrl: string]: ExtendedSidebarItem[]; } {
  const sidebarResult = {};

  const englishFallbacks = JSON.parse(readFileSync(resolve(_rootDir, "..", "sidebar_translations.json"), "utf-8"));

  // Create the default english sidebar.
  for (const sidebarPair of Object.entries(sidebars)) {
    const [url, sidebar] = sidebarPair;
    sidebarResult[url] = applyTranslations("en_us", englishFallbacks, englishFallbacks, sidebar);
  }

  const translatedFolder = resolve(_rootDir, "..", "translated");

  // Get all folder names from the translated folder
  const translatedFolders = readdirSync(translatedFolder, { withFileTypes: true })
    .filter(dirent => dirent.isDirectory())
    .map(dirent => dirent.name);

  for (const folder of translatedFolders) {
    const sidebarPath = resolve(translatedFolder, folder, "sidebar_translations.json")
    const indexPath = resolve(translatedFolder, folder, "index.md")

    if (!existsSync(indexPath)) {
      continue;
    }

    // If sidebar translations dont exist, use english fallback.
    if (!existsSync(sidebarPath)) {
      for (const sidebarPair of Object.entries(sidebars)) {
        const [url, sidebar] = sidebarPair;
        sidebarResult[`/${folder}${url}`] = sidebarResult[url];
      }

      continue;
    }

    const translations: { [key: string]: string; } = JSON.parse(readFileSync(sidebarPath, "utf-8"));

    for (const sidebarPair of Object.entries(sidebars)) {
      const [url, sidebar] = sidebarPair;

      sidebarResult[`/${folder}${url}`] = applyTranslations(folder, translations, englishFallbacks, sidebar);
    }
  }

  return sidebarResult;
}

export function generateThemeConfig(_localeDir: string | null): DefaultTheme.Config {
  return {
    // https://vitepress.dev/reference/default-theme-config
    nav: [
      { text: 'Home', link: 'https://fabricmc.net/' },
      { text: 'Download', link: 'https://fabricmc.net/use' },
      {
        text: 'Contribute', items: [
          // Expand on this later, with guidelines for loader+loom potentially?
          {
            text: 'Fabric Documentation',
            link: (_localeDir == null ? '' : `/${_localeDir}`) + "/contributing"
          },
          {
            text: 'Fabric API',
            link: 'https://github.com/FabricMC/fabric/blob/1.20.4/CONTRIBUTING.md'
          }
        ]
      },
    ],

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
      text: 'Edit this page on GitHub'  // TODO: Localise this text
    },

    socialLinks: [
      { icon: 'github', link: 'https://github.com/FabricMC/fabric-docs' },
      { icon: 'discord', link: 'https://discord.gg/v6v4pMv' }
    ],

    logo: "/logo.png"
  };
}

export function loadLocales(_rootDir: string): LocaleConfig<DefaultTheme.Config> {
  const translatedFolder = resolve(_rootDir, "..", "translated");

  // Get all folder names from the translated folder
  const translatedFolders = readdirSync(translatedFolder, { withFileTypes: true })
    .filter(dirent => dirent.isDirectory())
    .map(dirent => dirent.name);

  const locales: LocaleConfig<DefaultTheme.Config> = {};

  for (const folder of translatedFolders) {
    const indexPath = resolve(translatedFolder, folder, "index.md")

    // Dont add language if index.md does not exist
    if (!existsSync(indexPath)) {
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
      themeConfig: generateThemeConfig(folder),
    }
  }

  return locales;
}
