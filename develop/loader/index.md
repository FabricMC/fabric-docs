---
title: Fabric Loader
description: The lightweight mod loader that powers the Fabric project.
authors:
  - cassiancc
  - falseresync
  - jamieswhiteshirt
  - Llamalad7
authors-nogithub:
  - liach
  - solidblock
---

Fabric Loader is Fabric's lightweight mod loader. It provides the necessary tools to make Minecraft modifiable without depending on a specific version of the game. Game specific (and game version specific) hooks belong in Fabric API. It is possible to adapt Fabric Loader for many Java applications (for instance games like Slay the Spire and Starmade).

Fabric Loader has services to allow mods to have some code executed during initialization, to transform classes, declare and provide mod dependencies, all in a number of different environments.

The Javadoc for the latest vesrsion of Fabric Loader's can be found on the [Develop](https://fabricmc.net/develop/) site.

The current instance of Fabric Loader can be retrieved by using `FabricLoader.getInstance()`. As an example, `FabricLoader.getInstance().isModLoaded` can be used to check for the presence of another running mod.

## Mods {#mods}

A mod is a jar with a [`fabric.mod.json`](./fabric-mod-json) mod metadata file in its root declaring how it should be loaded. It primarily declares a mod ID and version as well as [entrypoints](https://wiki.fabricmc.net/documentation:entrypoint) and mixin configurations. The mod ID identifies the mod so that any mod with the same ID is considered to be the same mod. Only one version of a mod may be loaded at a time. A mod may declare other mods that it depends on or conflicts with. Fabric Loader will attempt to satisfy dependencies and load the appropriate versions of mods, or fail to launch otherwise.

Fabric Loader makes all mods equally capable of modifying the game. As an example, anything Fabric API does can be done by any other mod.

Mods are loaded both from the classpath and from the `mods` directory. This directory can be changed with the `fabric.modsFolder` system property.

## Nested JARs {#nested-jars}

Nested JARs allow a mod to provide its own dependencies, so Fabric Loader can pick the best version matching the dependencies instead of requiring separate installation of dependencies. They also allow clean packaging of submodules, so each module can be used separately. Non-mod libraries can be repackaged as mods for nested JAR usage. A mod may bundle a number of other mods within its JAR. A nested JAR must itself also be a mod, which again can have nested JARs. Fabric Loader will load nested JARs while attempting to satisfy dependency constraints.

Nested JARs are extracted to the disk when the game is run. Nested JARs must be declared by their paths relative to the containing JAR's root.

Using Fabric Loom's `include` option will automatically handle nesting the jar.

## Entrypoints {#entrypoints}

Fabric Loader has an [entrypoint](https://wiki.fabricmc.net/documentation:entrypoint) system, which is used by mods to expose parts of the code for usage by Fabric Loader or other mods. Fabric Loader uses it for mod initialization. Initializers are loaded and called early during the game's initialization which allows a mod to run some code to make its modifications. These entrypoints are typically used to bootstrap mods by registering registry objects, event listeners and other callbacks for doing things later.

## Mixin {#mixin}

Mixin allows mods to transform Minecraft classes and even mod classes, and is the only method of class transformation that Fabric Loader officially supports. A mod can declare its own mixin configuration which enables the use of Mixin.

Mixin was not originally made for Fabric, so Fabric Loader uses a modified version of Mixin. However, the documentation of the upstream version is still mostly valid. Fabric's modifications include allowing all default injection points inside constructors, optimizing out unused callback infos, providing fixes for backwards compatibility, fixing static shadows, allowing injectors in interfaces, and more.

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
