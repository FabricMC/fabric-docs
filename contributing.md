---
# for GitHub
TIP: "Read on on the website: https://docs.fabricmc.net/contributing"

# #region frontmatter-mandatory
# Page title.
# Should be short, in title case, and uniform with other pages.
# Should not contain class names or other obscure technical terms.
title: Contribution Guidelines

# Page description.
# Can be longer and more thorough, but preferably it should be one sentence.
# If relevant, this is where the main technical terms can be introduced.
description: "An introduction to the Fabric Docs: its structure, style, and how to contribute to it."

# Authors of the page.
# Alphabetical list of GitHub usernames of people who contributed to the page.
# Can include: writers, asset creators, impactful reviewers, code authors...
# These will be shown in the "Page Authors" section of the right aside.
authors:
  - dicedpixels
  - fungoza
  - IMB11
  - its-miroma
  - modmuss50
  - notlin4
# #endregion frontmatter-mandatory

# #region frontmatter-optional
# Authors of the page who don't have a GitHub account.
# List of usernames of people who contributed despite not having a GitHub account.
# Oftentimes, this is a result of migrating an article from the Fabric Wiki.
# These will also be shown in the "Page Authors" section of the right aside.
authors-nogithub:
# - SomeWikiUser

# External resources relevant to the article.
# Map of links and titles of sources and further insight from the web.
# These will be shown in the "Sources & Resources" section of the right aside.
resources:
  https://github.com/FabricMC/community/blob/main/CODE_OF_CONDUCT.md: Fabric Code of Conduct
  https://github.com/FabricMC/fabric-docs/blob/main/LICENSE: License of the Fabric Docs
  https://vitepress.dev/guide/markdown: Supported Markdown features - VitePress

# Files referenced by this page.
# This rarely needs to be used, because snippets are automatically detected.
# These will be shown in the "Files Referenced" section of the right aside.
files:
  - "@/contributing.md"

# Files to be excluded from references.
# When a list, can be used to ignore some automatically detected files.
# When set to `true`, disables the "Files Referenced" section entirely.
filesExclude: true

# VitePress supports other frontmatter options.
# See: https://vitepress.dev/reference/frontmatter-config
# endregion frontmatter-optional

editLink: false
---

Thank you for your interest in contributing to the Fabric Docs!

## Introduction to the Project {#introduction}

The Fabric Documentation ("Docs") is an open source of knowledge for modding Minecraft: Java Edition using [Fabric](https://fabricmc.net/). The Docs are developed in an [open-source repository](https://github.com/FabricMC/fabric-docs), but discussion may also happen in the **#docs** channel of the [Discord server](https://discord.fabricmc.net/).

The website uses [VitePress](https://vitepress.dev/), a static site generator that produces beautiful web pages from Markdown files. There are also some [custom extensions and plugins installed](#framework), which we'll talk about later.

The standout feature of the Docs is that the code is placed in a [reference mod called `ExampleMod`](https://github.com/FabricMC/fabric-docs/tree/main/reference/latest), which is built on every commit to ensure the code is correct.

We also support translating the Docs to other languages, and that happens on [Crowdin](https://crowdin.com/project/fabricmc). New translations may take up to a week or more to appear on the website.

When a new version of Minecraft is released, the old documentation is frozen in place and archived to be accessible through the version dropdown in the nav bar above. Old versions are usually set in stone, and new changes to the latest version are rarely backported.

## How Can I Contribute? {#how-to}

There are many ways you can contribute to the Fabric Docs: adding new pages, correcting information, fixing typos, translating to other languages, and much more...

::: warning IMPORTANT

By contributing to any Fabric-owned repository, you acknowledge and agree to the [Fabric Code of Conduct](https://github.com/FabricMC/community/blob/main/CODE_OF_CONDUCT.md).

By contributing to the Fabric Docs, you agree to release your pages and code under the [CC-BY-NC-SA 4.0 license adopted by Fabric Docs](https://github.com/FabricMC/fabric-docs/blob/main/LICENSE).

:::

### Making Small Changes {#label-correction}

The best way to start helping and familiarizing yourself with the project is to make small corrections to the English articles.

Spotted a misspelling or a broken link? At the bottom of every page on the website, there is a link to edit it on GitHub:

!["Edit this page on GitHub" button](/assets/contributing/edit-on-github.png)

That will open the GitHub Web UI, where you can make changes without downloading anything. You'll need to create an account with GitHub, though; that way we will also be able to give you proper credit.

::: tip

If you want to make a change to some code, you might not find it in that Markdown file. This probably means it is a [snippet taken from the `ExampleMod`](#snippets), and you should change it there.

:::

After that, you should follow the instructions on the screen to commit your changes and open a Pull Request to merge them to the website.

Someone from the Docs Team should then notice your PR; if they approve of it, they will merge it!

### Translating to Another Language {#label-i18n-l10n}

The Docs are translated into multiple languages to help non-English speakers with modding Minecraft: Java Edition. If you want to help with that, you can use Crowdin.

Let's say you speak Italian, and while reading a page, you came across a spelling mistake:

![A paragraph in Italian with a spelling mistake: "legera" should be "leggera"](/assets/contributing/lang-spelling-mistake.png)

To fix it, you should visit the [Crowdin project](https://crowdin.com/project/fabricmc), sign up for an account if you don't have one yet, select your language (Italian), and select the file you want to correct:

![Selecting the language Italian on Crowdin](/assets/contributing/crowdin-select-lang.png)

![Opening the file on Crowdin](/assets/contributing/crowdin-open-file.png)

On the Preview tab you'll see what the page currently looks like, and you can find the string you want to correct there:

![Preview of the file with an error on Crowdin](/assets/contributing/crowdin-preview-with-error.png)

Suggest the correction on the right side, then save your suggestion:

![Correction submitted on Crowdin](/assets/contributing/crowdin-corrected-suggestion.png)

Now, you have to wait. Translations are pulled once a week, on Saturday at 7:37 UTC. When that happens, you'll see a new PR opened on GitHub containing changes from the week, including your correction. Here's an [example of a localization PR](https://github.com/FabricMC/fabric-docs/pull/546):

![Translation PR on GitHub](/assets/contributing/github-l10n-pr.png)

When someone from the Docs Team finally approves the PR and merges it, you'll see your correction live on the website.

This is the same process you should follow if you want to translate an entire page anew: you should find the file on Crowdin, suggest translations for all its sentences, and wait for them to be merged to the main site.

::: info

If your language is not listed, you should tell us with a message on the **#docs** channel on the [Discord server](https://discord.fabricmc.net/).

:::

### Creating New Content {#label-new-content}

If you wish to contribute an entirely new page or set of pages, you are welcome to do so!

Contributions follow the [GitHub flow](https://docs.github.com/en/get-started/using-github/github-flow):

1. [Fork the Docs repository](https://github.com/FabricMC/fabric-docs/fork) to your personal account
2. Create a new branch for the contribution, appropriately named
3. Commit your changes and push them to that branch
4. Open a Pull Request to the Docs repository
5. Wait for and acknowledge reviews from the Docs Team
6. When the content is ready, the Docs Team will merge it to the site

When creating a new page, you should do the following:

- Add an entry to the sidebar in `.vitepress/sidebars/<sidebar>.ts`, and a translation key to `sidebar_translations.json`
- Consider opening a draft PR early in the writing instead of waiting for full completion, so you can get initial feedback and automatic CI checks
- Follow the [style guidelines](#style), including adding proper credits in `authors` in the form of GitHub usernames
- Respect the licenses, especially if referencing other people's code
- You might want to [work on the project locally](#local) to preview and test the pages and the code

### Modifying the Framework {#label-framework}

Sometimes the change you want to apply isn't to the content, but rather to the website structure or visual appearance. We call these framework changes, and you can find greater detail [regarding the file structure](#structure) below.

Usually, you may want to discuss these wide-scale suggestions with the Docs Team before working on them, because they will affect every page on the site. You can do that either in an [issue on the repository](https://github.com/FabricMC/fabric-docs/issues) or in the **#docs** channel on the [Discord server](https://discord.fabricmc.net/).

We're especially wary about introducing new NPM dependencies, because that widens the attack surface and introduces new points of potential failure.

### Reviewing Others' Content {#review}

::: info

This section is primarily for members of the Docs Team, but could be useful for anyone.

:::

When reviewing someone else's PR, make sure that:

- The style guidelines are respected
- The licenses have been respected (ours and other people's)
- The page has been added to the sidebar
- All authors and co-authors are given proper credit
  - Pay extra attention to **#wiki** contributors, asset creators, mods from which code has been borrowed, and writers and reviewers who made substantial structural and substantive changes to the page
- External resources and sources are mentioned in the frontmatter
- Grammar is correct. Consider using [LanguageTool](https://languagetool.org/)
- Content is up-to-date. Pay close attention to Mojang Mappings

## How Is the Project Set Up? {#structure}

The Docs contain two kinds of pages: player-oriented how-tos, and development-aiding guides and references.

### Player Pages {#player}

The pages for players are under `/players`. They must be simple to follow, and must take into account all three operating systems where relevant: Windows, macOS, and Linux. These pages will be reviewed with greater scrutiny because of how many people will come across them. Relevant paths:

- `.vitepress/sidebars/player.ts`: the common sidebar for player pages
- `sidebar_translations.json`, keys prefixed with `player.`: titles of the pages as seen in the sidebar
- `public/assets/players/`: [assets](#assets) related to player pages, such as images, log files...

### Developer Pages {#develop}

On the other hand, the pages for developers are under `/develop`. These have a greater scope: some are guides, and some are references. Relevant paths:

- `.vitepress/sidebars/develop.ts`: the common sidebar for developer pages
- `sidebar_translations.json`, keys prefixed with `develop.`: titles of the pages as seen in the sidebar
- `public/assets/develop/`: [assets](#assets) related to developer pages, such as images, video clips...
- `reference/latest/`: `ExampleMod` containing the source code that will be [embedded in the pages](#snippets)

### Framework {#framework}

You will also find the VitePress framework configuration under `.vitepress`. In there you'll find:

- `config/`: contains general VitePress configuration (`index.ts`), localization setup (`i18n.ts`), and SEO transformations (`transform.ts`)
- `plugins/`: contains Vite plugins that should be used when retrieving data from files or transforming them _before_ the build
- `sidebars/`: contains sidebar configuration, as mentioned earlier
- `theme/`: contains the theme configuration, and in particular it holds [custom components](#components) under `components/`
- `redirects.ts`: is used to configure client-side redirects, using regular expressions
- `bump.ts`: is used when a new version of Minecraft is released; archives the current version and initiates a migration to the new one
- `../website_translations.json`: contains translations of the UI elements on the website

### Other Files {#no-chewing}

Then, there are two other subsystems that shouldn't normally be touched manually:

- `translated/`: contains the pages translated by contributors on [Crowdin](https://crowdin.com/project/fabricmc)
- `versions/`, `.vitepress/sidebars/versioned`, `reference/*.*/`: contains an archive of the documentation for older versions

### Linting {#ci}

Finally, an intricate combination of CI and linting tools does a general check of the files to ensure formal correctness and uniform styling:

- [`markdownlint`](https://github.com/DavidAnson/markdownlint) is configured to finely control the Markdown pages, even with some custom rules
- [`prettier`](https://prettier.io/) is an opinionated code formatter that will take care of TypeScript, Vue, JSON, and other file types
- [`checkstyle`](https://checkstyle.sourceforge.io/) is a tool that makes Java code adhere to one same coding standard, defined in `reference/checkstyle.xml`
- `.github/workflows/build.yaml` will run for every PR, and it will ensure that:
  - The mod and the pages build correctly
  - The mod passes tests
  - Generated files are up-to-date with `runDatagen`

### Deployment {#deploy}

The main website is hosted on [GitHub Pages](https://pages.github.com/), but PR previews are managed via [Netlify](https://www.netlify.com/).

### Local Setup {#local}

If you want to preview your work locally before opening a PR, you can set up a copy of the project locally. You'll need [Git](https://git-scm.com/), [Node.js](https://nodejs.org/), and [pnpm](https://pnpm.io/).

If you already have [forked the repository](#label-new-content), you can clone your fork on your system:

```sh
# Replace [YOUR-USERNAME] with your GitHub username
git clone https://github.com/[YOUR-USERNAME]/fabric-docs.git
```

The first time you need to install the NPM dependencies:

```sh
pnpm install
```

Now, when working on the pages, you can spin up a development environment, which supports hot-reload:

```sh
pnpm dev
# Open the preview in your browser: http://fabric-docs.localhost:5173/
```

To work on the [`ExampleMod`](#develop), you'll need to [have a JDK installed](./players/installing-java/).

The most common tasks are building the mod, regenerating data files, and testing the mod in-game:

::: code-group

```powershell [Windows]
cd reference
.\gradlew build
.\gradlew runDatagen
.\gradlew runClient
```

```sh [macOS / Linux]
cd reference
./gradlew build
./gradlew runDatagen
./gradlew runClient
```

:::

## Regarding the Style {#style}

Here are some general tips and guidelines for writing Docs pages:

::: tabs

== Rules

- Follow American rules of grammar
- Add hyphens where appropriate
- Avoid the singular first person "I"
- Use inline `code` for file names, classes, and the like
- Use title case for headings, which also need an anchor
- Use descriptive labels for links, not "here"
- Limit the number of verbs per sentence
- Avoid complex grammar: long sentences, passive voices...
- Avoid specifying that the modded game is Minecraft

== ✔️ Do

_Emphasis highlights differences._

### Adding a Button {#adding-a-button}

Let's add a **gray** checkbox **centered** on top of the screen, which will be **colored** green when **left-clicked**.

To make it **recognizable**, it will be **labeled** "Enable **aging** **in-game**". Because this needs to be **localizable**, we'll reference **`LocalizationHelper`** and add a key to the **`en_us.json`** file. [Read more here](#adding-a-button).

_The GUI engine will render the checkbox like any regular button, without special logic. This means it's up to us to implement the toggling **behavior**. We should also add a delay to prevent spamming the button._

== ❌ Don't

_Emphasis highlights differences._

<!-- markdownlint-capture -->
<!-- markdownlint-disable -->

### Adding a button

I recommend adding a **grey** checkbox **centred** on top of the screen, which will be **coloured** green when **left clicked**.

To make it **recognisable**, it will be **labelled** "Enable **ageing** **ingame**". Because this needs to be **localisable**, we'll reference **LocalisationHelper** and add a key to the **en_gb.json** file. Read more on the [l10n guide](#adding-a-button).

_**Said** checkbox **is rendered** by the **Minecraft** GUI engine as a regular button, which **means** it **is** up to us to **implement** different **behaviour** **depending** on the current state that the checkbox **is** in, and it also **requires** a delay to **prevent** the button from **being spammed** by the player._

<!-- markdownlint-restore -->

:::

There are also some additional plugins and features you can use in your pages:

### Frontmatter {#frontmatter}

Each page must start with a YAML frontmatter, which contains metadata related to the page itself. Here are the supported fields, some of which are mandatory, and some of which are not:

::: code-group

<<< @/contributing.md#frontmatter-mandatory{yaml} [Mandatory]

<<< @/contributing.md#frontmatter-optional{yaml} [Advanced]

:::

### Links {#links}

A documentation website works best when its pages are interconnected, which is why you should consider linking pages together.

However, the versioning system in place requires you to use **relative links** when referencing other pages in the documentation. You should also not add the file extension to internal links either.

When linking to external websites, consider whether that resource is relevant enough to be added to the `resources` section of the [frontmatter](#frontmatter).

In either case, you should follow the [best practices for accessible link text](https://www.wcag.com/blog/writing-meaningful-link-text/#Best_Practices_for_Accessible_Link_Writing). Importantly, avoid undescriptive text such as "Click here" or "Read more", and avoid bare URLs.

### Assets {#assets}

You might need to add images, videos, or other assets to your page. You should place those files within `/public/assets/`, and in that directory you need to mirror the file path. For example:

- **File path**: `/develop/blocks/first-block.md`
- **Image name**: `block-texture.png`
- **Image path**: `/public/assets/develop/blocks/first-block/block-texture.png`

When linking to the image in the page, you should add [proper alternative text](https://www.a11y-collective.com/blog/alternative-text/).

### Containers {#containers}

VitePress supports [five custom containers](https://vitepress.dev/guide/markdown#custom-containers): `info`, `tip`, `warning`, `danger`, and the collapsible `details`. These stand out on the page because they have a different background color.

::: tip

Less is more. Containers are very colorful, so they must be used sparingly.

Readers might focus on the containers more than the surrounding text. Use them only when you need to highlight something important.

:::

You should avoid adding custom titles for all containers except `details`. The only custom titles allowed are:

- `::: info PREREQUISITES`
- `::: warning IMPORTANT`

### Code Snippets {#snippets}

When adding code blocks to the documentation, you're supposed to place it within the [`ExampleMod`](#develop) and reference parts of it with [snippets](https://vitepress.dev/guide/markdown#import-code-snippets). For example:

::: tabs

== Preview

Here's an example of a getter-setter pattern in Java:

<<< @/contributing.md#number{java}

== Markdown

```md
Here's an example of a getter-setter pattern in Java:

<<< @/reference/latest/src/main/java/com/example/docs/NumberHolder.java#number
```

== Java

```java
public class NumberHolder {
  // #region number
  private int number;

  // #endregion number

  public NumberHolder(int number) {
    this.setNumber(number);
  }

  // #region number
  public int getNumber() {
    return this.number;
  }

  private void setNumber(int number) {
    this.number = number;
  }
  // #endregion number
}
```

:::

Notice how, in the example above, the constructor of the Java class was intertwined between the region comments. The advantage of snippets is that we can "hide" irrelevant parts of the code by alternating region comments.

### Highlights {#highlight}

Sometimes you might want to highlight specific lines in the code. You can do that by using special `![code]` comments, or by specifying that when including a snippet:

:::: tabs

== Preview

Here are some of the possible highlights in code blocks:

```ts
const var1 = 1; // [!code highlight]

const var2 = 2; // [!code --]
const var3 = 3; // [!code ++]

const var4 = 4; // [!code warning]
const var5 = 5; // [!code error]
```

== Markdown

Here are some of the possible highlights in code blocks:

````md
```ts
const var1 = 1; // [!code highlight]  // [!code highlight]

const var2 = 2; // [!code --]         // [!code --]
const var3 = 3; // [!code ++]         // [!code ++]

const var4 = 4; // [!code warning]    // [!code warning]
const var5 = 5; // [!code error]      // [!code error]
```
````

::::

### Tabs {#tabs}

You can add a `::: tabs` component, implemented by [`vitepress-plugin-tabs`](https://vitepress-plugins.sapphi.red/tabs/#syntax), like such:

:::: tabs

=== Preview

<!-- #region tabs -->

Follow these instructions to install Fabric:

::: tabs

== Windows

1. Visit the [Fabric Installer website](https://fabricmc.net/use/installer)
2. Click on "Download for Windows"
3. Run the installer and follow the steps

== macOS / Linux

1. Visit the [Fabric Installer website](https://fabricmc.net/use/installer)
2. Click on "Download installer (Universal/.JAR)"
3. Run the installer and follow the steps

:::

<!-- #endregion tabs -->

=== Markdown

<<< @/contributing.md#tabs

::::

### Code Groups {#code-groups}

[Code groups](https://vitepress.dev/guide/markdown#code-groups) are very similar to tabs, but they should be used when the tabs only contain a code block:

:::: tabs

== Preview

<!-- #region code-group -->

You may run this command in your project's root to build your mod:

::: code-group

```powershell:no-line-numbers [Windows]
.\gradlew.bat build
```

```sh:no-line-numbers [macOS / Linux]
./gradlew build
```

:::

<!-- #endregion code-group -->

== Markdown

<<< @/contributing.md#code-group

::::

### Custom Components {#components}

Fabric Docs has implemented some custom components that you might need to import within your page. These are available under `.vitepress/theme/components/`. Here are the most important ones:

#### `ChoiceComponent` {#choice}

::: tabs

== Preview

<!-- #region choice -->

Choose your mappings:

<ChoiceComponent :choices="[
  {
    name: 'Mojang Mappings',
    href: 'https://www.minecraft.net/en-us/article/removing-obfuscation-in-java-edition',
    color: '#EF323D',
    // Icons from Iconify: https://icon-sets.iconify.design/
    icon: 'cib:mojang',
  },
  {
    name: 'Fabric Yarn',
    color: '#2764CF',
    icon: 'file-icons:fabric',
    // When href is not specified, the choice is not clickable
  },
]" />

<!-- #endregion choice -->

== Markdown

<<< @/contributing.md#choice

:::

#### `DownloadEntry` {#download}

::: tabs

== Preview

<!-- #region download -->

You can download the favicon used by this website:

<DownloadEntry downloadURL="/favicon.png" visualURL="/logo.png">Favicon</DownloadEntry>

<!-- #endregion download -->

== Markdown

<<< @/contributing.md#download

:::

#### `VideoPlayer` {#video}

The video player supports both [assets](#assets) and external videos:

::: tabs

== Preview

<!-- #region video -->

Check out this video for the leaked spec of `fabric.mod.json` v2:

<VideoPlayer src="https://www.youtube.com/watch?v=dQw4w9WgXcQ">fabric.mod.json v2 Released!!!</VideoPlayer>

<!-- #endregion video -->

== Markdown

<<< @/contributing.md#video

:::

<!---->
