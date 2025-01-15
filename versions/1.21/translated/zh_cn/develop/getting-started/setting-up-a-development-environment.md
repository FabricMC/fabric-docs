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
authors-nogithub:
  - siglong
---

# 设置开发环境{#setting-up-a-development-environment}

要开始使用 Fabric 开发模组，需要使用 IntelliJ IDEA 设置开发环境。

## 安装 JDK 21 {#installing-jdk-21}

为 Minecraft 1.21 开发模组，需要 JDK 21。

如果需要安装 Java 方面的帮助，可以参考[玩家指南部分](../../players/index)中的各种 Java 安装指南。

## 安装 IntelliJ IDEA{#installing-intellij-idea}

:::info
你显然可以使用其他 IDE， 比如 Eclipse 或 Visual Studio Code，但本文档站点上的大多数页面都假定你使用的是 IntelliJ IDEA - 如果你使用的是其他 IDE，则应参考那些 IDE 的文档。
:::

如果没有安装 IntelliJ IDEA，可以从[官方网站](https://www.jetbrains.com/idea/download/)下载 - 按照你的操作系统的安装步骤操作。

IntelliJ IDEA 的社区版是免费且开源的，是使用 Fabric 开发模组的推荐版本。

你可能需要向下滚动才能找到社区版下载链接 - 如下所示：

![IDEA 社区版下载提示](/assets/develop/getting-started/idea-community.png)

## 安装 IDEA 插件{#installing-idea-plugins}

这些插件虽然不是绝对必要的，但可以让使用 Fabric 开发模组更容易 - 应该要考虑安装。

### Minecraft Development {#minecraft-development}

Minecraft Development 插件为使用 Fabric 开发模组提供支持，是要安装的最重要的插件。

如要安装，可以打开 IntelliJ IDEA，然后在搜索栏中导航到 `文件 > 设置 > 插件 > Marketplace 标签页` - 在搜索框中搜索 `Minecraft Development`，然后点击 `安装` 按钮。

或者你可以从[插件页面](https://plugins.jetbrains.com/plugin/8327-minecraft-development)下载，然后依次点击 `文件 > 设置 > 插件 > 从硬盘上安装插件` 来安装。
