---
title: Contribution Guidelines
description: Guidelines for contributions to the Fabric Documentation.
---

# Contribution Guidelines {#contributing}

This website uses [VitePress](https://vitepress.dev/) to generate static HTML from various Markdown files. You should familiarize yourself with the Markdown extensions that VitePress supports [here](https://vitepress.dev/guide/markdown#features).

There are three ways you can contribute to this website:

- [Translating Documentation](#translating-documentation)
- [Contributing Content](#contributing-content)
- [Contributing Framework](#contributing-framework)

All contributions must follow our [style guidelines](#style-guidelines).

## Translating Documentation {#translating-documentation}

If you want to translate the documentation into your language, you can do this on the [Fabric Crowdin page](https://crowdin.com/project/fabricmc).

## Contributing Content {#contributing-content}

Content contributions are the main way to contribute to the Fabric Documentation.

All content contributions go through three stages:

1. Guidance for Expansion (if needed)
2. Content Verification
3. Cleanup (Grammar etc.)

All content must follow our [style guidelines](#style-guidelines).

### 1. Prepare Your Changes {#1-prepare-your-changes}

This website is open-source, and it is developed in a GitHub repository, which means that we rely on the GitHub flow:

1. [Fork the GitHub repository](https://github.com/FabricMC/fabric-docs/fork)
2. Create a new branch on your fork
3. Make your changes on that branch
4. Open a Pull Request to the original repository

You can read more about the GitHub flow [here](https://docs.github.com/en/get-started/using-github/github-flow).

You can either make changes from the web interface on GitHub, or you can develop and preview the website locally.

#### <Badge type="tip">locally</Badge> Cloning Your Fork {#clone-your-fork}

If you want to develop locally, you will need to install [Git](https://git-scm.com/).

After that, clone your fork of the repository with:

```sh
# make sure to replace "your-username" with your actual username
git clone https://github.com/your-username/fabric-docs.git
```

#### <Badge type="tip">locally</Badge> Installing Dependencies {#install-dependencies}

If you want to preview your changes locally, you will need to install [Node.js 18+](https://nodejs.org/en/).

After that, make sure to install all dependencies with:

```sh
npm install
```

#### <Badge type="tip">locally</Badge> Running the Development Server {#run-the-development-server}

This will allow you to preview your changes locally at `localhost:5173` and will automatically reload the page when you make changes.

```sh
npm run dev
```

Now you can open and browse the website from the browser by visiting `http://localhost:5173`.

#### <Badge type="tip">locally</Badge> Building the Website {#building-the-website}

This will compile all Markdown files into static HTML files and place them in `.vitepress/dist`:

```sh
npm run build
```

#### <Badge type="tip">locally</Badge> Previewing the Built Website {#previewing-the-built-website}

This will start a local server on port `4173` serving the content found in `.vitepress/dist`:

```sh
npm run preview
```

#### <Badge type="tip">locally</Badge> Opening a Pull Request {#opening-a-pull-request}

Once you're happy with your changes, you may `push` your changes:

```sh
git add .
git commit -m "Description of your changes"
git push
```

Then, follow the link in the output of `git push` to open a PR.

### 2. Guidance for Expansion if Needed {#2-guidance-for-expansion-if-needed}

If the documentation team thinks that you could expand upon your pull request, a member of the team will add the `can-expand` label to your pull request alongside a comment explaining what they think you could expand upon. If you agree with the suggestion, you can expand upon your pull request.

Don't feel pressured to expand upon your pull request. If you don't want to expand upon your pull request, you can simply ask for the `can-expand` label to be removed.

If you do not want to expand upon your pull request, but you are happy for someone else to expand upon it at a later date, it's best to create an issue on the [Issues page](https://github.com/FabricMC/fabric-docs/issues) and explain what you think could be expanded upon.

### 3. Content Verification {#3-content-verification}

All pull requests adding content undergo content verification, this is the most important stage as it ensures that the content is accurate and follows the Fabric Documentation style guide.

### 4. Cleanup {#4-cleanup}

This stage is where the documentation team will fix any grammar issues and make any other changes they deem necessary before merging the pull request!

## Contributing Framework {#contributing-framework}

Framework refers to the internal structure of the website, any pull requests that modify the framework of the website will be labeled with the `framework` label.

You should really only make framework pull requests after consulting with the documentation team on the [Fabric Discord](https://discord.gg/v6v4pMv) or via an issue.

::: info
Modifying sidebar files and the navigation bar configuration does not count as a framework pull request.
:::

## Style Guidelines {#style-guidelines}

If you are unsure about anything, you can ask in the [Fabric Discord](https://discord.gg/v6v4pMv) or via GitHub Discussions.

### Write the Original in American English {#write-the-original-in-american-english}

All original documentation is written in English, following the American rules of grammar.

While you may use [LanguageTool](https://languagetool.org/) to check your grammar as you type, don't stress too much about it. Our documentation team will review and correct grammar during the cleanup stage. However, making an effort to get it right initially can save us time.

### Add Data to the Frontmatter {#add-data-to-the-frontmatter}

Each page must have a `title` and a `description` in the frontmatter.

Remember to also add your GitHub username to `authors` in the frontmatter of the Markdown file! This way we can give you proper credit.

```md
---
title: Title of the Page
description: This is the description of the page.
authors:
  - your-username
---

# Title of the Page {#title-of-the-page}

...
```

### Add Anchors to Headings {#add-anchors-to-headings}

Each heading must have an anchor, which is used to link to that heading:

```md
# This Is a Heading {#this-is-a-heading}
```

The anchor must use lowercase characters, numbers and dashes.

### Place Code Within the `/reference` Mod {#place-code-within-the-reference-mod}

If you create or modify pages containing code, place the code in an appropriate location within the reference mod (located in the `/reference` folder of the repository). Then, use the [code snippet feature offered by VitePress](https://vitepress.dev/guide/markdown#import-code-snippets) to embed the code.

For example, to highlight lines 15-21 of the `FabricDocsReference.java` file from the reference mod:

::: code-group

```md
<<< @/reference/latest/src/main/java/com/example/docs/FabricDocsReference.java{15-21}
```

<<< @/reference/latest/src/main/java/com/example/docs/FabricDocsReference.java{15-21}[java]

:::

If you need a greater span of control, you can use the [transclude feature from `markdown-it-vuepress-code-snippet-enhanced`](https://github.com/fabioaanthony/markdown-it-vuepress-code-snippet-enhanced).

For example, this will embed the sections of the file above that are marked with the `#entrypoint` tag:

::: code-group

```md
@[code transcludeWith=#entrypoint](@/reference/latest/src/main/java/com/example/docs/FabricDocsReference.java)
```

@[code transcludeWith=#entrypoint](@/reference/latest/src/main/java/com/example/docs/FabricDocsReference.java)

:::

### Create a Sidebar for Each New Section {#create-a-sidebar-for-each-new-section}

If you're creating a new section, you should create a new sidebar in the `.vitepress/sidebars` folder and add it to the `i18n.mts` file.

If you need assistance with this, please ask in the [Fabric Discord](https://discord.gg/v6v4pMv)'s `#docs` channel.

### Add New Pages to the Relevant Sidebars {#add-new-pages-to-the-relevant-sidebars}

When creating a new page, you should add it to the relevant sidebar in the `.vitepress/sidebars` folder.

Again, if you need assistance, ask in the Fabric Discord in the `#docs` channel.

### Place Media in `/assets` {#place-media-in-assets}

Any images should be placed in a suitable place in the `/public/assets` folder.

### Use Relative Links! {#use-relative-links}

This is because of the versioning system in place, which will process the links to add the version beforehand. If you use absolute links, the version number will not be added to the link.

You must also not add the file extension to the link either.

For example, to link to the page found in `/players/index.md` from the page `/develop/index.md`, you would have to do the following:

::: code-group

```md:no-line-numbers [✅ Correct]
This is a relative link!
[Page](../players/index)
```

```md:no-line-numbers [❌ Wrong]
This is an absolute link.
[Page](/players/index)
```

```md:no-line-numbers [❌ Wrong]
This relative link has the file extension.
[Page](../players/index.md)
```

:::
