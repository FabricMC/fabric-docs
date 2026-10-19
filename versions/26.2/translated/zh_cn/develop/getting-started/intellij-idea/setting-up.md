---
title: 设置 IntelliJ IDEA
description: 有关如何搭建 IntelliJ IDEA 以使用 Fabric 创作模组的逐步指南。
authors:
  - 2xsaiko
  - Andrew6rant
  - asiekierka
  - Daomephsta
  - dicedpixels
  - falseresync
  - IMB11
  - liach
  - mkpoli
  - modmuss50
  - natanfudge
  - SolidBlock-cn
  - TelepathicGrunt
authors-nogithub:
  - siglong
prev:
  text: 设置你的 IDE
  link: ../setting-up
next:
  text: 在 IntelliJ IDEA 中打开项目
  link: ./opening-a-project
resources:
  https://www.jetbrains.com/idea/download/: 从 JetBrains 下载 IntelliJ IDEA
  https://www.jetbrains.com/help/idea/getting-started.html: IntelliJ IDEA 入门指南
  https://plugins.jetbrains.com/plugin/8327-minecraft-development: Minecraft 开发插件 - JB Marketplace
---

<!---->

:::info 先决条件

请确保先[安装 JDK](../setting-up#install-jdk-21)。

:::

## 安装 IntelliJ IDEA {#installing-intellij-idea}

::: warning

需要 IntelliJ IDEA 2025.3 或更高版本才能修改 26.1。

:::

如果尚未安装 IntelliJ IDEA，或者**版本低于 2025.3**，可以从[官方网站](https://www.jetbrains.com/idea/download/)下载，并按照适用于你操作系统的安装步骤操作。

![IntelliJ IDEA 下载提示](/assets/develop/getting-started/idea-download.png)

## 安装“Minecraft Development”插件 {#installing-idea-plugins}

Minecraft Development 插件为使用 Fabric 开发模组提供支持，是要安装的最重要的插件。

::: tip

这个插件虽然不是必需的，但能让利用 Fabric 制作模组容易得多——所以应该考虑安装。

:::

如要安装，可以打开 IntelliJ IDEA，然后导航到 **文件** > **设置** > **插件** > **市场** 标签页，然后在搜索栏中搜索 `Minecraft Development`，然后单击 **安装** 按钮。

或者，你可以在[插件页面](https://plugins.jetbrains.com/plugin/8327-minecraft-development)下载，再依次进入**文件** > **设置** > **插件** > **从磁盘安装插件**以安装。

### 关于创建项目 {#about-creating-a-project}

虽然可以使用此插件创建项目，但不建议这样做，因为模板经常会过期。 你可以考虑按照[创建项目](../creating-a-project)中的说明使用 Fabric 模板模组生成器。
