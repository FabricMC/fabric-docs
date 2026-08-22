---
title: Setting Up Zed
description: A step-by-step guide on how to set up Zed to create mods using Fabric.
authors:
  - itzzmateo
prev:
  text: Setting Up Your IDE
  link: ../setting-up
next:
  text: Opening a Project in Zed
  link: ./opening-a-project
resources:
  https://zed.dev/: Download Zed
  https://zed.dev/docs/languages/java: Java Support in Zed
---

<!---->

::: warning IMPORTANT

While it is possible to develop mods using Zed, we recommend using [IntelliJ IDEA](../intellij-idea/setting-up) instead. IntelliJ IDEA has dedicated Java tooling, advanced features and useful community-created plugins such as **Minecraft Development**.

:::

::: info PREREQUISITES

Make sure you've [installed a JDK](../setting-up#install-jdk-21) first.

:::

## Installation {#installation}

You can download Zed from [zed.dev](https://zed.dev/) or install it using your preferred package manager.

::: code-group

```sh:no-line-numbers [macOS]
brew install --cask zed
```

```sh:no-line-numbers [Linux]
curl https://zed.dev/install.sh | sh
```

:::

## Java Support {#java-support}

Zed has built-in Java support via the Java extension. To enable it, open the Extensions view by pressing <kbd>Ctrl</kbd>+<kbd>Shift</kbd>+<kbd>X</kbd> and search for "Java". Install the Java extension to get language support.

Alternatively, you can install it from the command palette by pressing <kbd>Ctrl</kbd>+<kbd>Shift</kbd>+<kbd>P</kbd> and typing "Extensions: Install Extensions".

::: tip

The Java extension will automatically detect your JDK installation if you have followed the [JDK installation guide](../setting-up#install-jdk-21).

:::
