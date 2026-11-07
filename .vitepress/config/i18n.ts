import * as fs from "node:fs";
import * as path from "node:path";
import { AT, LOCALES } from "../constants.ts";
import { DEVELOP_SIDEBAR } from "../sidebars/develop.ts";
import { PLAYERS_SIDEBAR } from "../sidebars/players.ts";
import type { Fabric } from "../types.d.ts";

const translationFileCache = new Map<string, Record<string, any>>();
const readTranslationFile = <T extends Record<string, any>>(file: string, locale: string): T => {
  const filePath = path.join(AT, "translated", locale === "en_us" ? ".." : locale, file);
  if (!translationFileCache.has(filePath)) {
    try {
      translationFileCache.set(filePath, JSON.parse(fs.readFileSync(filePath, "utf-8")));
    } catch {
      return {} as T;
    }
  }

  return translationFileCache.get(filePath) as T;
};

type Resolver<T extends Record<string, any>> = <K extends string & keyof T>(k: K) => T[K];
const getResolver = <T extends Record<string, any>>(file: string, locale: string): Resolver<T> => {
  const strings = readTranslationFile<T>(file, locale);
  const fallback = readTranslationFile<T>(file, "en_us");

  return (k) => strings[k] || fallback[k];
};

export const getWebsiteResolver = (locale: string): Resolver<Fabric.Translations["website"]> => {
  const file = "website_translations.json";
  const resolver = getResolver<Fabric.Translations["website"]>(file, locale);

  if (locale === "en_us") {
    for (const k of Object.keys(readTranslationFile(file, locale))) {
      if (!/^([/][/])?[a-z0-9_.]*$/.test(k)) {
        console.warn(`${file}: unusual character in key '${k}'`);
      }
    }
  }

  return (k) => {
    const returned = resolver(k);

    if (locale === "en_us" && !returned) {
      console.warn(`${file}: missing translation for key '${k}'`);
    }

    return returned;
  };
};

export const getSidebar = (locale: string) => {
  const returned: Fabric.Sidebar = {};

  const file = "sidebar_translations.json";
  const resolver = getResolver<Fabric.Translations["sidebar"]>(file, locale);

  const localePrefix = locale === "en_us" ? "" : `/${locale}`;

  const normalizeSidebar = (sidebar: Fabric.SidebarItem[], base = "") => {
    const returned = (JSON.parse(JSON.stringify(sidebar)) as Fabric.SidebarItem[]) //
      .map((i) => (typeof i === "string" ? { link: i } : i));

    for (const i of returned) {
      const k = i.text || `${i.base || base}${i.link || ""}`;

      // @ts-expect-error
      i.text = resolver(k) || (i.link && k.endsWith("/") ? resolver("introduction") : "");

      if (locale === "en_us" && !i.text) {
        console.warn(`${file}: missing translation for key '${k}'`);
      }

      if (i.items) {
        i.items = normalizeSidebar(i.items, i.base || base);
      }

      i.base = `${localePrefix}${i.base || base}`;
    }

    return returned;
  };

  returned[`${localePrefix}/develop/`] = normalizeSidebar([DEVELOP_SIDEBAR]);
  returned[`${localePrefix}/players/`] = normalizeSidebar([PLAYERS_SIDEBAR]);

  return returned;
};

export const getLocaleConfig = () => {
  const returned: Fabric.Config["locales"] = {};

  // https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/Intl/DisplayNames
  const intlLocaleOverrides: Record<string, string> = {
    zh_cn: "zh-Hans",
    zh_tw: "zh-Hant",
  };

  // https://developer.crowdin.com/language-codes
  const crowdinLocaleOverrides: Record<string, string> = {
    en_us: "",
    es_es: "es-ES",
    pt_br: "pt-BR",
    zh_cn: "zh-CN",
    zh_tw: "zh-TW",
  };

  for (const locale of LOCALES) {
    const intlLocale =
      intlLocaleOverrides[locale]
      || locale.replace(/..$/, (m) => m.toUpperCase()).replace("_", "-");
    const crowdinLocale = crowdinLocaleOverrides[locale] ?? locale.split("_")[0];

    const resolver = getWebsiteResolver(locale);
    const intl = new Intl.DisplayNames(intlLocale, {
      languageDisplay: "standard",
      style: "short",
      type: "language",
    });

    returned[locale === "en_us" ? "root" : locale] = {
      lang: intlLocale,
      link: locale === "en_us" ? "/" : `/${locale}/`,
      label: intl
        .of(intlLocale)!
        .replace(/^\p{CWU}/u, (firstChar) => firstChar.toLocaleUpperCase(intlLocale)),

      title: resolver("title"),
      description: resolver("description"),

      themeConfig: {
        authors: {
          heading: resolver("authors.heading"),
          noGitHub: resolver("authors.no_github"),
        },

        banner: {
          local: {
            build: resolver("banner.local.build"),
            dev: resolver("banner.local.dev"),
          },
          pr: {
            text: resolver("banner.pr"),
            link: resolver("banner.pr.link"),
          },
        },

        code: {
          copied: resolver("code.copied"),
          copy: resolver("code.copy"),
          enterFullscreen: resolver("code.enter_fullscreen"),
          exitFullscreen: resolver("code.exit_fullscreen"),
          wrap: resolver("code.wrap"),
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
          pattern:
            locale === "en_us"
              ? "https://github.com/FabricMC/fabric-docs/edit/main/:path"
              : `https://crowdin.com/project/fabricmc/${crowdinLocale}`,
          text: locale === "en_us" ? resolver("edit_github") : resolver("edit_crowdin"),
        },

        footer: {
          copyright: resolver("footer.copyright").replace(
            "%s",
            [
              '<a href="https://github.com/FabricMC/fabric-docs/blob/-/LICENSE" target="_blank" rel="noreferrer">',
              resolver("footer.license"),
              "</a>",
            ].join("")
          ),
          message: resolver("footer.message"),
        },

        langMenuLabel: resolver("lang_switcher"),

        lastUpdated: {
          text: resolver("last_updated"),
        },

        lightModeSwitchTitle: resolver("mode_light"),

        nav: [
          {
            text: resolver("nav.home"),
            link: "https://fabricmc.net/",
            noIcon: true,
          },
          {
            text: resolver("nav.contribute"),
            link: `${locale === "en_us" ? "" : `/${locale}`}/contributing`,
          },
          {
            text: resolver("nav.repo"),
            items: [
              {
                text: "Fabric API",
                link: "https://github.com/FabricMC/fabric",
              },
              {
                text: "Fabric Loader",
                link: "https://github.com/FabricMC/fabric-loader",
              },
              {
                text: "Fabric Loom",
                link: "https://github.com/FabricMC/fabric-loom",
              },
              {
                text: "Fabric Annotater",
                link: "https://github.com/FabricMC/mcsrc",
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
          pooh: resolver("404.title.pooh"),
          quotes: resolver("404.quotes") as never,

          crowdinLinkLabel: resolver("404.crowdin_link.label"),
          crowdinLinkText: resolver("404.crowdin_link"),

          englishLinkLabel: resolver("404.english_link.label"),
          englishLinkText: resolver("404.english_link"),

          linkLabel: resolver("404.link.label"),
          linkText: resolver("404.link"),
        },

        outline: {
          label: resolver("outline"),
        },

        references: {
          files: resolver("references.files"),
          resources: resolver("references.resources"),
        },

        returnToTopLabel: resolver("return_to_top"),

        search: {
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

        sidebar: getSidebar(locale),

        sidebarMenuLabel: resolver("sidebar_menu"),

        siteTitle: resolver("title"),

        skipToContentLabel: resolver("skip_to_content"),

        socialLinks: [
          {
            icon: "simple-icons:github",
            link: "https://github.com/FabricMC/fabric-docs",
            ariaLabel: resolver("social.github"),
          },
          {
            icon: "simple-icons:discord",
            link: "https://discord.fabricmc.net/",
            ariaLabel: resolver("social.discord"),
          },
          {
            icon: "simple-icons:crowdin",
            link: `https://crowdin.com/project/fabricmc/${crowdinLocale}`,
            ariaLabel: resolver("social.crowdin"),
          },
        ],

        version: {
          switcherLabel: resolver("version.switcher.label"),
          switcherTitle: resolver("version.switcher.title"),
          noOtherVersions: resolver("version.switcher.none"),
        },

        versionSwitcher: false,
      },
    };
  }

  return returned;
};
