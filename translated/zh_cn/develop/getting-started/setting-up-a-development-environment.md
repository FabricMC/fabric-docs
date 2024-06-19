---
title: 设置开发环境
description: 关于如何搭建 Fabric 开发环境的逐步指南。
authors:
  - IMB11
  - andrew6rant
  - SolidBlock-cn
  - modmuss50
  - daomephsta
  - liach
  - telepathicgrunt
  - 2xsaiko
  - natanfudge
  - mkpoli
  - falseresync
  - asiekierka
---

<!-- No GitHub profiles for: siglong -->

# 设置开发环境

要开始使用 Fabric 开发模组，您需要使用 IntelliJ IDEA 设置开发环境。

## 安装 JDK 17

要为 Minecraft 1.20.4 开发模组，您将需要 JDK 17。

如果您需要安装 Java 方面的帮助，可以参考[玩家指南部分](../../players/index.md)中的各种 Java 安装指南。

## 安装 IntelliJ IDEA

:::info
你显然可以使用其他 IDE， 比如 Eclipse 或 Visual Studio Code，但本文档站点上的大多数页面都假定你使用的是 IntelliJ IDEA - 如果你使用的是其他 IDE，则应参考那些 IDE 的文档。
:::

如果您没有安装IntelliJ IDEA，可以从[官方网站](https://www.jetbrains.com/idea/download/)下载 - 按照操作系统的安装步骤进行操作。

IntelliJ IDEA 的社区版是免费和开源的，它是使用 Fabric 进行模组开发的推荐版本。

你可能需要向下滚动才能找到社区版下载链接 - 如下所示：

![IDEA 社区版下载提示](/assets/develop/getting-started/idea-community.png)

## 安装 IDEA 插件

虽然这些插件不是绝对必要的，但它们可以让用 Fabric 进行模组开发变得更加容易 - 您应该考虑安装它们。

### Minecraft开发

Minecraft Development 插件支持使用 Fabric 进行模组开发，它是最重要的安装插件。

您可以通过打开 IntelliJ IDEA 来安装它，然后在搜索栏中导航到 `文件 > 设置 > 插件 > Marketplace Tab` - 在搜索框中搜索 `Minecraft Development`，然后点击 `安装` 按钮。

或者你可以从 [插件页面](https://plugins.jetbrains.com/plugin/8327-minecraft-development) 下载它，然后依次点击 `文件 > 设置 > 插件 > 从硬盘上安装插件` 选项卡来安装这个插件。
