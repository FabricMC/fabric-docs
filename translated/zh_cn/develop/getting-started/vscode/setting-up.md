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

:::warning 重要
虽然可以使用 Visual Studio Code 开发模组，但我们不建议这样做。
建议使用 [IntelliJ IDEA](../intellij-idea/setting-up)，它拥有专用的 Java 工具、高级功能以及实用的社区插件，如 **Minecraft Development**。
:::

:::info 前提
首先确保你已经[安装了 JDK](../setting-up#install-jdk-21)。
:::

## 安装 {#installation}

可以从 [code.visualstudio.com](https://code.visualstudio.com/) 或通过偏好的包管理器下载 Visual Studio Code。

![Visual Studio Code 下载页](/assets/develop/getting-started/vscode/download.png)

## 先决条件 {#prerequisites}

Visual Studio Code 不提供开箱即用的 Java 语言支持。 不过，Microsoft 提供了一个便捷的扩展包，其中包含启用 Java 语言支持所需的所有扩展。

你可以从 [Visual Studio Marketplace](https://marketplace.visualstudio.com/items?itemName=vscjava.vscode-java-pack) 安装这个扩展包。

![Java 扩展包](/assets/develop/getting-started/vscode/extension.png)

或者，也可以在 Visual Studio Code 内部通过“扩展”视图进行安装。

![“扩展”视图中的 Java 扩展包](/assets/develop/getting-started/vscode/extension-view.png)

**Language Support for Java** 扩展会显示一个启动屏幕，用于设置 JDK。 若你尚未设置，请立即安装。
