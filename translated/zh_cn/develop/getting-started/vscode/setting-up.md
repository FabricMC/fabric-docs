---
title: 设置 VS Code
description: 有关如何搭建 Visual Studio Code 以使用 Fabric 创作模组的逐步指南。
authors:
  - dicedpixels
prev:
  text: 设置你的 IDE
  link: ../setting-up
next:
  text: 在 VS Code 中打开项目
  link: ./opening-a-project
---

<!---->

:::warning 重要

虽然使用 Visual Studio Code 开发模组是可行的，但是我们不推荐这样做。
建议使用 [IntelliJ IDEA](../intellij-idea/setting-up)，它拥有专门的 Java 工具链、高级功能和有用的社区创建插件，例如 **Minecraft Development**。

:::

:::info 前置条件

请确保先[安装 JDK](../setting-up#install-jdk-21)。

:::

## 安装 {#installation}

你可以从 [code.visualstudio.com](https://code.visualstudio.com/) 下载 Visual Studio Code，或通过您喜欢的包管理器进行下载。

![Visual Studio Code 下载页](/assets/develop/getting-started/vscode/download.png)

## 前置条件 {#prerequisites}

Visual Studio Code 本身不提供 Java 语言支持。 然而，Microsoft 提供了一个方便的扩展包，其中包含所有必要的扩展，以启用 Java 语言支持。

你可以从 [Visual Studio Marketplace](https://marketplace.visualstudio.com/items?itemName=vscjava.vscode-java-pack) 安装这个扩展包。

![Java 扩展包](/assets/develop/getting-started/vscode/extension.png)

或者，你也可以在 Visual Studio Code 本身中，通过扩展视图来安装。

![扩展视图中的 Java 扩展包](/assets/develop/getting-started/vscode/extension-view.png)

**Language Support for Java** 扩展会为你提供一个启动屏幕来设置 JDK。 如果你还没有设置，可以这样做。
