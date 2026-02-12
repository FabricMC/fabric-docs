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
---

<!---->

:::info 先决条件

首先，你得安装一个[JDK](../setting-up#install-jdk-21)。

:::

## 安装IntelliJ IDEA {#installing-intellij-idea}

如果你没有安装IntelliJ IDEA，你可以在它的[官方网站](https://www.jetbrains.com/idea/download/)下载它。请按照属于你操作系统的步骤来安装。

![IntelliJ IDEA下载页面](/assets/develop/getting-started/idea-download.png)

## 安装“Minecraft Development”插件 {#installing-idea-plugins}

Minecraft Development插件提供了对Fabric Mod开发的支持，它是要安装的最重要的插件。

::: tip

虽然这个插件不是必须的，它可以让Fabric Mod开发更方便——你应当考虑安装它。

:::

你可以按照以下步骤来安装：打开IntelliJ IDEA，再找到**文件** > **设置** > **插件** > **Marketplace**选项卡，然后在搜索栏里搜索`Minecraft Development`，接着按下**安装**按钮。

或者，你可以在[插件下载页](https://plugins.jetbrains.com/plugin/8327-minecraft-development)下载它，再通过找到**文件** > **设置** > **插件** > **从磁盘安装插件**。

### 关于创建一个项目 {#about-creating-a-project}

虽然使用这个插件来创建项目是可行的，但我们并不推荐这种方案，因为模板经常是过时的。 作为替代，你可以考虑跟随[创建项目](../creating-a-project)的教程来使用Fabric Mod模板生成器。
