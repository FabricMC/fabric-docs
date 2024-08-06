---
title: Introduction to Fabric and Modding
description: "A brief introduction to Fabric and modding in Minecraft: Java Edition."
authors:
  - IMB11
  - itsmiir
authors-nogithub:
  - basil4088

search: false
---

# Introduction to Fabric and Modding {#introduction-to-fabric-and-modding}

## Prerequisites {#prerequisites}

Before you start, you should have a basic understanding of developing with Java, and an understanding of Object-Oriented Programming (OOP).

If you are unfamiliar with these concepts, you may want to look into some tutorials on Java and OOP before you start modding, here are some of the resources that you can use to learn Java and OOP:

- [W3: Java Tutorials](https://www.w3schools.com/java/)
- [Codecademy: Learn Java](https://www.codecademy.com/learn/learn-java)
- [W3: Java OOP](https://www.w3schools.com/java/java_oop.asp)
- [Medium: Introduction to OOP](https://medium.com/@Adekola_Olawale/beginners-guide-to-object-oriented-programming-a94601ea2fbd)

### Terminology {#terminology}

Before we start, let's go over some of the terms that you will encounter when modding with Fabric:

- **Mod**: A modification to the game, adding new features or changing existing ones.
- **Mod Loader**: A tool that loads mods into the game, such as Fabric Loader.
- **Mixin**: A tool for modifying the game's code at runtime - see [Mixin Introduction](https://fabricmc.net/wiki/tutorial:mixin_introduction) for more information.
- **Gradle**: A build automation tool used to build and compile mods, used by Fabric to build mods.
- **Mappings**: A set of mappings that map obfuscated code to human-readable code.
- **Obfuscation**: The process of making code difficult to understand, used by Mojang to protect Minecraft's code.
- **Remapping**: The process of mapping obfuscated code to human-readable code.

## What Is Fabric? {#what-is-fabric}

Fabric is a lightweight modding toolchain for Minecraft: Java Edition.

It is designed to be a simple and easy-to-use modding platform. Fabric is a community-driven project, and it is open-source, meaning that anyone can contribute to the project.

You should be aware of the four main components of Fabric:

- **Fabric Loader**: A flexible platform-independent mod loader designed for Minecraft and other games and applications.
- **Fabric Loom**: A Gradle plugin enabling developers to easily develop and debug mods.
- **Fabric API**: A set of APIs and tools for mod developers to use when creating mods.
- **Yarn**: A set of open Minecraft mappings, free for everyone to use under the Creative Commons Zero license.

## Why Is Fabric Necessary to Mod Minecraft? {#why-is-fabric-necessary-to-mod-minecraft}

> Modding is the process of modifying a game in order to change its behavior or add new features - in the case of Minecraft, this can be anything from adding new items, blocks, or entities, to changing the game's mechanics or adding new game modes.

Minecraft: Java Edition is obfuscated by Mojang, making modification alone difficult. However, with the help of modding tools like Fabric, modding becomes much easier. There are several mapping systems that can assist in this process.

Loom remaps the obfuscated code to a human-readable format using these mappings, making it easier for modders to understand and modify the game's code. Yarn is a popular and excellent mappings choice for this, but other options exist as well. Each mapping project may have its own strengths or focus.

Loom allows you to easily develop and compile mods against remapped code, and Fabric Loader allows you to load these mods into the game.

## What Does Fabric API Provide, and Why Is It Needed? {#what-does-fabric-api-provide-and-why-is-it-needed}

> Fabric API is a set of APIs and tools for mod developers to use when creating mods.

Fabric API provides a wide set of APIs that build on top of Minecraft's existing functionality - for example, providing new hooks and events for modders to use, or providing new utilities and tools to make modding easier - such as transitive access wideners and the ability to access internal registries, such as the compostable items registry.

While Fabric API offers powerful features, some tasks, like basic block registration, can be accomplished without it using the vanilla APIs.
