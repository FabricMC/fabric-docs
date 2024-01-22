# Fabric Documentation Contribution Guidelines

This website uses [VitePress](https://vitepress.vuejs.org/) to generate static HTML from the various markdown files. You should familiarize yourself with the markdown extensions that VitePress supports [here.](https://vitepress.vuejs.org/guide/markdown.html#features)

## Table Of Contents

- [Fabric Documentation Contribution Guidelines](#fabric-documentation-contribution-guidelines)
  - [How To Contribute](#how-to-contribute)
  - [Contributing Framework](#contributing-framework)
  - [Contributing Content](#contributing-content)
    - [Style Guidelines](#style-guidelines)
    - [Guidance for Expansion](#guidance-for-expansion)
    - [Content Verification](#content-verification)
    - [Cleanup](#cleanup)

## How To Contribute

It's recommended that you create a new branch on your fork of the repository for each pull request you make. This makes it easier to manage multiple pull requests at once.

**If you want to preview your changes locally, you will need to install [Node.js 18+](https://nodejs.org/en/)**

Before running any of these commands, make sure to run `npm install` to install all dependencies.

**Running the development server:**

This will allow you to preview your changes locally at `localhost:3000` and will automatically reload the page when you make changes.

```bash
npm run dev
```

**Building the website:**

This will compile all markdown files into static HTML files and place them in `.vitepress/dist`

```bash
npm run build
```

**Previewing the built website:**

This will start a local server on port 3000 serving the content found in `.vitepress/dist`

```bash
npm run preview
```

## Contributing Framework

Framework refers to the internal structure of the website, any pull requests that modify the framework of the website should be labeled with the `framework` label.

You should really only make framework pull requests after consulting with the documentation team on the [Fabric Discord](https://discord.gg/v6v4pMv) or via an issue.

**Note: Modifying sidebar files and the navigation bar configuration does not count as a framework pull request.**

## Contributing Content

Content contributions are the main way to contribute to the Fabric Documentation. 

All content should follow our style guidelines.

### Style Guidelines

All pages on the Fabric Documentation website should follow the style guide. If you are unsure about anything, you can ask in the [Fabric Discord](https://discord.gg/v6v4pMv) or via GitHub Discussions.

The style guide is as follows:

1. All pages must have a title and description in the frontmatter.

    ```md
    ---
    title: This is the title of the page
    description: This is the description of the page
    authors:
      - GitHubUsernameHere
    ---
    
    # ...
    ```

2. If you create or modify pages containing code, place the code in an appropriate location within the reference mod (located in the `/reference` folder of the repository). Then, use the [code snippet feature offered by VitePress](https://vitepress.dev/guide/markdown#import-code-snippets) to embed the code, or if you need a greater span of control, you can use the [transclude feature from `markdown-it-vuepress-code-snippet-enhanced`](https://github.com/fabioaanthony/markdown-it-vuepress-code-snippet-enhanced).

    **Example:**

    ```md
    <<< @/reference/latest/src/main/java/com/example/docs/FabricDocsReference.java{15-21 java}
    ```

    This will embed lines 15-21 of the `FabricDocsReference.java` file in the reference mod.

    The resulting code snippet will look like this:

    ```java
      @Override
      public void onInitialize() {
        // This code runs as soon as Minecraft is in a mod-load-ready state.
        // However, some things (like resources) may still be uninitialized.
        // Proceed with mild caution.

        LOGGER.info("Hello Fabric world!");
      }
    ```

    **Transclude Example:**

    ```md
    @[code transcludeWith=#test_transclude](@/reference/.../blah.java)
    ```

    This will embed the sections of `blah.java` that are marked with the `#test_transclude` tag.

    For example:

    ```java
    public final String test = "Bye World!"

    // #test_transclude
    public void test() {
      System.out.println("Hello World!");
    }
    // #test_transclude
    ```

    Only the code between the `#test_transclude` tags will be embedded.

    ```java
    public void test() {
      System.out.println("Hello World!");
    }
    ```

3. We follow American English grammar rules. While you can use [LanguageTool](https://languagetool.org/) to check your grammar as you type, don't stress too much about it. Our documentation team will review and correct grammar during the cleanup stage. However, making an effort to get it right initially can save us time.

4. If you're creating a new section, you should create a new sidebar in the `.vitepress/sidebars` folder and add it to the `config.mts` file. If you need assistance with this, please ask in the [Fabric Discord](https://discord.gg/v6v4pMv)'s `#wiki` channel.

5. When creating a new page, you should add it to the relevant sidebar in the `.vitepress/sidebars` folder. Again, if you need assistance, ask in the Fabric Discord in the `#wiki` channel.

6. Any images should be placed in a suitable place in the `/assets` folder.

7. ⚠️ **When linking other pages, use relative links.** ⚠️ 
    
    This is because of the versioning system in place, which will process the links to add the version beforehand. If you use absolute links, the version number will not be added to the link.

    For example, for a page in the `/players` folder,to link to the `installing-fabric` page found in `/players/installing-fabric.md`, you would have to do the following:

    ```md
    [This is a link to another page](./installing-fabric.md)
    ```

    You should **NOT** do the following:

    ```md
    [This is a link to another page](/player/installing-fabric)
    ```

All content contributions go through three stages:

1. Guidance for expansion (if possible)
2. Content Verification
3. Cleanup (Grammar etc.)

### Guidance for Expansion

If the documentation team thinks that you could expand upon your pull request, a member of the team will add the `expansion` label to your pull request alongside a comment explaining what they think you could expand upon. If you agree with the suggestion, you can expand upon your pull request. 

**Don't feel pressured to expand upon your pull request.** If you don't want to expand upon your pull request, you can simply ask for the `expansion` label to be removed.

If you do not want to expand upon your pull request, but you are happy for someone else to expand upon it at a later date, it's best to create an issue on the [Issues page](https://github.com/FabricMC/fabric-docs/issues) and explain what you think could be expanded upon.

### Content Verification

All pull requests adding content undergo content verification, this is the most important stage as it ensures that the content is accurate and follows the Fabric Documentation style guide.

### Cleanup

This stage is where the documentation team will fix any grammar issues and make any other changes they deem necessary before merging the pull request!
