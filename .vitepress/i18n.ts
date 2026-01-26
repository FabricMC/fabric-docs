import * as fs from "node:fs";
import * as path from "node:path/posix";
import * as tinyglobby from "tinyglobby";

import Develop from "./sidebars/develop";
import Players from "./sidebars/players";
import { Fabric } from "./types";

export const getLocaleNames = (translatedDir: string) => [
  "en_us",
  ...tinyglobby
    .globSync(`${translatedDir}/*`, { onlyDirectories: true })
    .filter((f) => fs.existsSync(path.resolve(f, "index.md")))
    .map((f) => path.relative(translatedDir, f)),
];

const locales = getLocaleNames(`${import.meta.dirname}/../translated`);

export const getResolver = (file: string, locale: string, warn = true): ((k: string) => string) => {
  const filePath = path.resolve(
    import.meta.dirname,
    "..",
    "translated",
    locale === "en_us" ? ".." : locale,
    file
  );

  const strings: Record<string, string> = fs.existsSync(filePath)
    ? JSON.parse(fs.readFileSync(filePath, "utf-8"))
    : {};

  if (warn && locale === "en_us") {
    for (const fileK of Object.keys(strings)) {
      if (!/^[a-z0-9_.]+$/.test(fileK)) {
        console.warn(`${file}: unusual character in key: ${fileK}`);
      }
    }
  }

  return (k) => strings[k] || (locale === "en_us" ? k : getResolver(file, "en_us", false)(k));
};

export const getSidebar = (locale: string) => {
  const returned: Fabric.Sidebar = {};
  const resolver = getResolver("sidebar_translations.json", locale);

  const normalizeSidebar = (sidebar: Fabric.SidebarItem[]) => {
    const returned: Fabric.SidebarItem[] = JSON.parse(JSON.stringify(sidebar));

    for (const item of returned) {
      item.text = resolver(item.text);
      if (item.items) item.items = normalizeSidebar(item.items);
      if (locale !== "en_us" && item.link?.startsWith("/")) {
        item.link = `/${locale}${item.link}`;
      }
    }

    return returned;
  };

  returned[`${locale === "en_us" ? "" : `/${locale}`}/develop/`] = normalizeSidebar(Develop);
  returned[`${locale === "en_us" ? "" : `/${locale}`}/players/`] = normalizeSidebar(Players);

  return returned;
};

export const getLocales = () => {
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

  for (const locale of locales) {
    const intlLocale = intlLocaleOverrides[locale] ?? locale.replace("_", "-");
    const crowdinLocale = crowdinLocaleOverrides[locale] ?? locale.split("_")[0];

    const resolver = getResolver("website_translations.json", locale);
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
                text: "Fabric Yarn",
                link: "https://github.com/FabricMC/yarn",
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

          crowdinLocale,
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
            link: `https://crowdin.com/project/fabricmc/${crowdinLocale}`,
            ariaLabel: resolver("social.crowdin"),
          },
        ],

        version: {
          reminder: {
            latestVersion: resolver("version.reminder.latest_version"),
            oldVersionMojang: resolver("version.reminder.old_version_mojang"),
            oldVersionYarn: resolver("version.reminder.old_version_yarn"),
          },

          switcher: {
            label: resolver("version.switcher.label"),
            none: resolver("version.switcher.none"),
          },
        },

        versionSwitcher: false,
      },
    };
  }

  return returned;
};
