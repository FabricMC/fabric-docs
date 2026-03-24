---
title: Developer Guides
description: Our community-written developer guides cover many topics, from creating a mod and setting up your environment, all the way to rendering, networking, data generation and more.
authors:
  - IMB11
  - its-miroma
  - itsmiir
authors-nogithub:
  - basil4088
---

Fabric is a lightweight modding toolchain for Minecraft: Java Edition, designed to be simple and easy-to-use. It allows developers to apply modifications ("mods") to the vanilla game, to add new features or change existing mechanics.

This documentation will walk you through modding with Fabric, from [creating your first mod](./getting-started/creating-a-project) and [setting up your environment](./getting-started/setting-up), to advanced topics like [rendering](./rendering/basic-concepts), [networking](./networking), [data generation](./data-generation/setup) and much more.

Check out the sidebar for a list of the available pages.

::: tip

In case you need it at any time, a fully-working mod with all the source code of this documentation is available in the [`/reference` folder on GitHub](https://github.com/FabricMC/fabric-docs/tree/main/reference/latest).

:::

## Prerequisites {#prerequisites}

Before you start modding with Fabric, you need to have some understanding of developing with Java, and of Object-Oriented Programming in general.

Here are some resources that might help you familiarize with Java and OOP:

- [W3: Java Tutorials](https://www.w3schools.com/java/)
- [Codecademy: Learn Java](https://www.codecademy.com/learn/learn-java)
- [W3: Java OOP](https://www.w3schools.com/java/java_oop.asp)
- [Medium: Introduction to OOP](https://medium.com/@Adekola_Olawale/beginners-guide-to-object-oriented-programming-a94601ea2fbd)

## What Does Fabric Offer? {#what-does-fabric-offer}

The Fabric Project is centered around three main components:

- **Fabric Loader**: a flexible, platform-independent loader of mods, primarily designed for Minecraft: Java Edition
- **Fabric API**: a complementary set of APIs and tools mod developers can use when creating mods
- **Fabric Loom**: a [Gradle](https://gradle.org/) plugin, enabling developers to easily develop and debug mods

### What Does Fabric API Offer? {#what-does-fabric-api-offer}

Fabric API provides a wide set of APIs that build on top of the vanilla functionality to allow advanced or simpler development.

For example, it provides new hooks, events, utilities such as transitive access wideners, access to internal registries such as the compostable items registry, and more.
