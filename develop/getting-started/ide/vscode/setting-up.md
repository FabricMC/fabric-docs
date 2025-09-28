---
title: Setting up Visual Studio Code
description: A step-by-step guide on how to set up Visual Studio Code to create mods using Fabric.
authors:
  - dicedpixels
---

::: warning IMPORTANT
While it is possible to develop mods using Visual Studio Code, we recommend against it.
Consider using [IntelliJ IDEA](./setting-up-intellij-idea), which has dedicated Java tooling, advanced features and useful community-created plugins such as **Minecraft Development**.
:::

::: info PREREQUISITES
Make sure you've [installed a JDK](../../setting-up-a-development-environment#installing-jdk-21) first.
:::

## Installation {#installation}

You can download Visual Studio Code from [code.visualstudio.com](https://code.visualstudio.com/) or through your preferred package manager.

![Visual Studio Code Download Page](/assets/develop/getting-started/vscode/download.png)

## Prerequisites {#prerequisites}

Visual Studio Code does not provide Java language support out of the box. However, Microsoft provides a convenient extension pack that contains all the necessary extensions to enable Java language support.

You can install this extension pack from [Visual Studio Marketplace](https://marketplace.visualstudio.com/items?itemName=vscjava.vscode-java-pack).

![Extension Pack for Java](/assets/develop/getting-started/vscode/extension.png)

Or, within Visual Studio Code itself, through the Extensions view.

![Extension Pack for Java in Extension view](/assets/develop/getting-started/vscode/extension-view.png)

The **Language Support for Java** extension will present you with a startup screen to set up a JDK. You can do so if you have not already.
