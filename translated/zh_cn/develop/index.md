---
title: 开发者指南
description: 我们的社区编写的开发者指南，涵盖许多主题，从创建一个模组和设置你的开发环境，一直到渲染、网络通信、数据生成等。
authors:
  - IMB11
  - its-miroma
  - itsmiir
authors-nogithub:
  - basil4088
---

Fabric 是一款适用于 Minecraft：Java 版的轻量级模组工具链，设计简洁易用， 让开发者能够对原版游戏进行修改（“模组”），添加新功能或更改现有机制。

本文档将指导你使用 Fabric 进行模组开发，从[创建第一个模组](./getting-started/creating-a-project)和[设置环境](./getting-started/setting-up)，到[渲染](./rendering/basic-concepts)、[网络](./networking)、[数据生成](./data-generation/setup)等高级主题，应有尽有。

完整的页面列表请查看侧边栏。

::: tip

如果你需要，包含本文档所有代码的完整有效模组可在 [Github 中的 `/reference` 文件夹](https://github.com/FabricMC/fabric-docs/tree/main/reference/latest)中找到。

:::

## 前置条件 {#prerequisites}

在开始使用 Fabric 编写模组之前，你需要对 Java 开发以及面向对象编程有一定了解。

以下是一些也许能帮助你熟悉 Java 和 OOP （面向对象编程）的资源：

- [W3: Java Tutorials](https://www.w3schools.com/java/)
- [Codecademy: Learn Java](https://www.codecademy.com/learn/learn-java)
- [W3: Java OOP](https://www.w3schools.com/java/java_oop.asp)
- [Medium: Introduction to OOP](https://medium.com/@Adekola_Olawale/beginners-guide-to-object-oriented-programming-a94601ea2fbd)

## Fabric 能提供什么？ {#what-does-fabric-offer}

Fabric 项目主要由三个核心组件组成：

- **Fabric Loader**：一个灵活且跨平台的模组加载器，主要面向《Minecraft：Java 版》
- **Fabric API**：一套供模组开发者在创作模组时使用的配套 API 和工具集
- **Fabric Loom**：[Gradle](https://gradle.org/) 插件，让开发者能够轻松开发和调试模组

### Fabric API 能提供什么？ {#what-does-fabric-api-offer}

Fabric API 提供了一系列构建在原版功能之上的 API，可以进行高级或简单的开发。

例如，它提供了新的钩子、事件、实用工具（例如传递访问加宽器）、对内部注册表（例如可堆肥物品注册表）的访问等等。
