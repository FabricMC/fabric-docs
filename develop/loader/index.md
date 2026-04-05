---
title: Fabric Loader
description: The lightweight mod loader that powers the Fabric project.
authors:
  - cassiancc
  - falseresync
  - jamieswhiteshirt
  - liach
  - Llamalad7
  - SolidBlock-cn
resources:
  https://maven.fabricmc.net/docs/fabric-loader-0.18.6/: Fabric Loader 0.18.6 Javadocs
  https://wiki.fabricmc.net/documentation:entrypoint: Entrypoints - Fabric Wiki
  https://github.com/FabricMC/fabric-loader: Fabric Loader on GitHub
---

Fabric Loader is Fabric's lightweight mod loader. It provides the necessary tools to make any version of Minecraft. On the other hand, game-specific and version-specific hooks belong in Fabric API.

::: info

While Fabric Loader is primarily used for Minecraft, it is possible to create a game provider for other Java applications (for instance games like [Slay the Spire](https://www.youtube.com/watch?v=ZaNI4OJFGTg) or [Hytale](https://github.com/cootshk/Hybric)).

:::

Fabric Loader has services that allow mods to execute code during initialization, transform classes, declare and provide mod dependencies, all that in various environments.

The Javadocs for the latest version of Fabric Loader can be found on the [Develop](https://fabricmc.net/develop/) site.

The current instance of Fabric Loader can be retrieved by using `FabricLoader.getInstance()`. As an example, `FabricLoader.getInstance().isModLoaded` can be used to check whether another mod is running.

## Mods {#mods}

A mod is a jar with a [`fabric.mod.json`](./fabric-mod-json) metadata file in its root declaring how it should be loaded. That file contains a mod ID and version number, as well as [entrypoints](../getting-started/project-structure#entrypoints) and mixin configurations.

The mod ID identifies the mod, and any two mods with the same ID are considered to be the same mod. Only one version of a mod may be loaded at a time.

A mod may also declare which other mods it depends on or conflicts with. Fabric Loader will attempt to satisfy dependencies and load the appropriate versions of mods; if it cannot it will fail to launch the game.

Fabric Loader makes all mods equally capable of modifying the game. As an example, anything Fabric API does can be done by any other mod.

Mods are loaded both from the classpath and from the `mods` directory. This directory can be changed with the `fabric.modsFolder` system property.

## Nested JARs {#nested-jars}

::: info

Using Fabric Loom's `include` option will automatically handle nesting the JAR, including the generation of a `fabric.mod.json` for non-mod JARs.

:::

Nested JARs allow a mod to provide its own dependencies, so Fabric Loader can find the best version while attempting to satisfy dependencies, instead of requiring them to be installed separately.

Nested jars act like any other mod, with their own metadata file, but are contained within the parent JAR. Note that nested mods may themselves embed other children in the same way.

Nested JARs are extracted to the disk when the game is run. Paths to nested JARs must be declared relative to their containing JAR's root.

## Entrypoints {#entrypoints}

Fabric Loader has an [entrypoint](../getting-started/project-structure#entrypoints) system, which is used by mods to expose parts of the code for usage by Fabric Loader or other mods. Fabric Loader uses it for mod initialization. Initializers are loaded and called early during the game's initialization which allows a mod to run some code to make its modifications. These entrypoints are typically used to bootstrap mods by registering registry objects, event listeners and other callbacks for doing things later.

## Mixin {#mixin}

Mixin allows mods to transform Minecraft classes and even mod classes, and is the only method of class transformation that Fabric Loader officially supports. A mod can declare its own mixin configuration which enables the use of Mixin.

Mixin was not originally made for Fabric, so Fabric Loader uses a modified version of Mixin. However, the [documentation](https://maven.fabricmc.net/docs/sponge-mixin-0.17.1+mixin.0.8.7/) of the upstream version is still mostly valid. Fabric's modifications include allowing all default injection points inside constructors, optimizing out unused callback infos, providing fixes for backwards compatibility, fixing static shadows, allowing injectors in interfaces, and more.

<!-- Referenced comments from LlamaLad7 on Fabric Mixin. https://discord.com/channels/507304429255393322/566418023372816394/1002211121903706162 -->

## Mappings {#mappings}

::: info

Mappings are only relevant when using Fabric Loader on obfuscated games, including pre-26.1 versions of Minecraft.

:::

Fabric Loader provides the `MappingResolver` API to determine names of classes, fields and methods with respect to the different environments that mods may be loaded in. This can be used to support reflection in any environment provided Fabric Loader has access to mappings to resolve the name.

```java
FabricLoader.getInstance().getMappingResolver().mapClassName("intermediary", "net.minecraft.class_5421") // Resolves to `RecipeBookType` on named versions of 1.21.11
```

When launched in a non-development environment on an obfuscated game, Fabric Loader will [remap](../porting/mappings/index#mappings) the game jar(s) to intermediary names. Mods designed for obfuscated games are expected to be mapped to intermediary, which will be compatible with this environment. The remapped jars are cached and saved in `${gameDir}/.fabric/remappedJars/${minecraftVersion}` for re-use across launches.
