import { readdirSync, readFileSync } from "fs";
import { resolve } from "path/posix";
import { ExtendedSidebarItem } from "./sidebars/utils";
import { DefaultTheme, LocaleConfig, LocaleSpecificConfig } from "vitepress";
import inter from "inter";

export function applyTranslations(translationSource: { [key: string]: string; }, fallbackSource: { [key: string]: string }, sidebar: ExtendedSidebarItem[]): ExtendedSidebarItem[] {
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

    if (item.items) {
      item.items = applyTranslations(translationSource, fallbackSource, item.items);
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
    sidebarResult[url] = applyTranslations(englishFallbacks, englishFallbacks, sidebar);
  }

  const translatedFolder = resolve(_rootDir, "..", "translated");

  // Get all folder names from the translated folder
  const translatedFolders = readdirSync(translatedFolder, { withFileTypes: true })
    .filter(dirent => dirent.isDirectory())
    .map(dirent => dirent.name);

  for (const folder of translatedFolders) {
    const translations: { [key: string]: string; } = JSON.parse(readFileSync(resolve(translatedFolder, folder, "sidebar_translations.json"), "utf-8"));

    for (const sidebarPair of Object.entries(sidebars)) {
      const [url, sidebar] = sidebarPair;

      sidebarResult[`/${folder}${url}`] = applyTranslations(translations, englishFallbacks, sidebar);
    }
  }

  return sidebarResult;
}

export function loadLocales(_rootDir: string): LocaleConfig<DefaultTheme.Config> {
  const translatedFolder = resolve(_rootDir, "..", "translated");

  // Get all folder names from the translated folder
  const translatedFolders = readdirSync(translatedFolder, { withFileTypes: true })
    .filter(dirent => dirent.isDirectory())
    .map(dirent => dirent.name);

  const locales: LocaleConfig<DefaultTheme.Config> = {};

  for (const folder of translatedFolders) {
    let firstHalf: string = folder.slice(0, 2);
    let secondHalf: string = folder.slice(3, 5);

    let localeName: string = "";
    if (firstHalf == secondHalf) {
      localeName = inter.load(folder).getLanguage(folder.slice(0, 2)).displayName;
    } else {
      localeName = inter.load(folder).getLanguage(folder).displayName;
    }

    localeName = localeName.charAt(0).toUpperCase() + localeName.slice(1);

    locales[folder] = {
      label: localeName,
      link: `/${folder}/`,
      lang: folder,
    }
  }

  return locales;
}
