---
title: Fabric 和模组简介
description: Minecraft：Java 版中 Fabric 和模组的简要介绍。
authors:
  - IMB11
  - itsmiir
authors-nogithub:
  - basil4088

search: false
---

# Fabric 和模组简介

## 先决条件

在开始学习之前，你应该对 Java 开发有基本的了解，并对面向对象编程（OOP）有所认识。

如果你不熟悉这些概念，在开始开发之前，你可能需要了解一些有关 Java 和 OOP 的教程，以下是可以用来学习 Java 和 OOP 的一些资源：

- [W3: Java Tutorials](https://www.w3schools.com/java/)
- [Codecademy: Learn Java](https://www.codecademy.com/learn/learn-java)
- [W3: Java OOP](https://www.w3schools.com/java/java_oop.asp)
- [Medium: Introduction to OOP](https://medium.com/@Adekola_Olawale/beginners-guide-to-object-oriented-programming-a94601ea2fbd)

### 术语

开始之前，先来看看使用 Fabric 编写模组时会遇到的一些术语：

- **模组（Mod）**： 对游戏的修改，添加新功能或更改现有功能。
- **模组加载器（Mod Loader）**： 将模组载入游戏的工具，例如 Fabric Loader。
- **Mixin**： 运行时修改游戏代码的工具——更多信息请参阅 [Mixin 介绍](https://fabricmc.net/wiki/zh_cn::tutorial:mixin_introduction) 。
- **Gradle**： 用于构建和编译模组的自动化构建工具，Fabric 用其构建模组。
- **映射（Mappings）**： 将被混淆的代码转化为人类可读代码的映射的集合。
- **混淆（Obfuscation）**： 使代码无法被人类阅读的过程，Mojang 用其来保护 Minecraft 的源代码。
- **重映射（Remapping）**： 将混淆代码映射为人类可读代码的过程。

## Fabric 是什么？ {#what-is-fabric}

Fabric 是用于 Minecraft: Java Edition 的轻量级模组开发工具链。

Fabric 旨在成为简单易用的模组开发平台。 Fabric 是由社区驱动的项目，而且开源，这意味着任何人都可以为项目做出贡献。

你应该了解的 Fabric 的四个主要组成部分：

- **Fabric Loader**： 灵活的独立于平台的模组加载器，专为 Minecraft 及其他游戏和应用程序而设计。
- **Fabric Loom**：Gradle 插件，使开发者能够轻松开发和调试模组。
- **Fabric API**：一套 API 和工具，供模组开发者在创建模组时使用。
- **Yarn**： 一套开放的 Minecraft 映射表，在 Creative Commons Zero 许可证下供所有人任意使用。

## 为什么开发 Minecraft 模组需要 Fabric？ {#why-is-fabric-necessary-to-mod-minecraft}

> “模组（Modding）”是指修改游戏以改变其行为或添加新功能的过程，就 Minecraft 而言，这可以是添加新物品、方块或实体，也可以是改变游戏机制或添加新的游戏模式。

Minecraft: Java Edition 被 Mojang 混淆，因此很难单独修改。 不过，在 Fabric 等模组开发工具的帮助下，修改变得更加容易。 有一些映射系统可以协助这一过程。

Loom 使用这些映射将混淆代码重映射为人类可读的格式，使模组开发者更容易理解和修改游戏代码。 在这方面，Yarn 是一个富有人气且十分优秀的映射选择，但也有其他选择。 每个映射表项目都有自己的优势和侧重点。

Loom 可让你轻松开发且编译重映射模组的代码，而 Fabric Loader 可让你将这些模组加载到游戏中。

## Fabric API 提供哪些功能，为什么需要它？ {#what-does-fabric-api-provide-and-why-is-it-needed}

> Fabric API 是一套 API 和工具，供模组开发者在创建模组时使用。

Fabric API 在 Minecraft 现有功能的基础上提供了一系列使开发更方便的 API。例如，提供新的 Hook 和事件供开发者使用，或提供新的实用程序和工具让魔改变得更容易，如访问加宽器（Access Wideners）和访问内部注册表（如可堆肥物品注册表）的能力。

虽然 Fabric API 提供了强大的功能，但有些任务，如基本的方块注册，不使用 Fabric API 也能完成。
