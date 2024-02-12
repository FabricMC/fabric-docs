import { readdirSync, readFileSync } from "fs";
import { resolve } from "path/posix";
import { ExtendedSidebarItem } from "./sidebars/utils";
import { DefaultTheme, LocaleConfig, LocaleSpecificConfig } from "vitepress";
import inter from "inter";

export function applyTranslations(translationSource: { [key: string]: string; }, sidebar: ExtendedSidebarItem[]): ExtendedSidebarItem[] {
  const sidebarCopy = JSON.parse(JSON.stringify(sidebar));

  for (const item of sidebarCopy) {
    if (item.disableTranslation) continue;

    if (translationSource[item.text]) {
      item.text = translationSource[item.text];
    }

    if (item.items) {
      item.items = applyTranslations(translationSource, item.items);
    }
  }

  return sidebarCopy;
}

export function generateTranslatedSidebars(_rootDir: string, sidebars: { [url: string]: ExtendedSidebarItem[]; }): { [localeUrl: string]: ExtendedSidebarItem[]; } {
  // For all translations in path.resolve(_rootDir, .., translated, locale_code, sidebar_translations.json) - load the translations and apply them.
  const sidebarResult = {};

  const translatedFolder = resolve(_rootDir, "..", "translated");

  // Get all folder names from the translated folder
  const translatedFolders = readdirSync(translatedFolder, { withFileTypes: true })
    .filter(dirent => dirent.isDirectory())
    .map(dirent => dirent.name);

  for (const folder of translatedFolders) {
    const translations: { [key: string]: string; } = JSON.parse(readFileSync(resolve(translatedFolder, folder, "sidebar_translations.json"), "utf-8"));

    for (const sidebarPair of Object.entries(sidebars)) {
      const [url, sidebar] = sidebarPair;

      if (folder == "en_us") {
        sidebarResult[url] = applyTranslations(translations, sidebar);
      } else {
        sidebarResult[`/${folder}${url}`] = applyTranslations(translations, sidebar);
      }
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

  // Remove the en_us folder
  translatedFolders.splice(translatedFolders.indexOf("en_us"), 1);

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

  console.log(locales);

  return locales;
}
