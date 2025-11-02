---
title: Migrating Mappings
description: Learn how to migrate your mod's obfuscation mappings.
authors:
  - cassiancc
---

::: warning
This automated process does not yet handle Mixins or reflection. Updated tooling is currently in the works that will fix these errors. This tooling will be available before the mandatory switch to Mojang names in a future version of Minecraft.
:::

Fabric Loom makes use of obfuscation mappings in order to mod the game, usually either Fabric's own Yarn Mappings or the official Mojang Mappings. As a developer, you may wish to switch your mod's mappings from Yarn to Mojang Mappings, or vice versa, especially if you are planning to updating your mod to the next Game Drop after Mounts of Mayhem, which removes the obfuscation process so all mods use Mojang's names.

For that purpose, Loom allows semi-automatic updating of the mappings used in a Java codebase through the `migrateMappings` task.

Loom does not support migrating code written in Kotlin. [Third party tools](https://github.com/Deftu/ReplayMod-Remap) are available.

## What Are Mappings? {#mappings}

Minecraft; Java Edition has been obfuscated since its release - replacing human friendly names like `Creeper` with something like `brc`. In order to effectively mod it, Fabric Loom makes use of obsucation maps, reference material which translates obfuscated class names like `brc` into human-friendly names like `CreeperEntity`.

As a Fabric developer, you'll encounter three main sets of names:

- **Intermediary**: The mapping set used by compiled Fabric mods, making use of numbers instead of names. These names are stable, so they do not change as much between releases are other mappings, allowing mods meant for one version to work on others, as long as the game itself hasn't changed too much. An example mapping would be `class_1548`.
- **Yarn**: An open source mapping set developed by Fabric to mod the game with. Most Fabric mods used Yarn Mappings, as they were the default prior to 2025. An example mapping would be `CreeperEntity`.
- **Mojang Mappings**: The game's official obfuscation mappings, released by Mojang in 2019 in order to aid mod development. An example mapping would be `Creeper`.

## Migrating to Mojang Mappings {#migrating-to-mojmap}

First, you'll need a `migrateMappings` command that will convert your current mappings to Mojang Mappings. For example, the following command would migrate to Mojang Mappings for 1.21.10.

```groovy
migrateMappings --mappings "net.minecraft:mappings:1.21.10"
```

You can replace `1.21.10` with the version of Minecraft you are migrating from. This must be the same version of Minecraft you are currently running. **Do not modify your `gradle.properties` or `build.gradle` yet!**

You can run this command in the terminal, prefixed with `./gradlew` on Linux/MacOS or `./gradlew.bat` on Windows, or by adding it as a run configuration in IntelliJ Idea.

### Editing Your Sources {#editing-sources-mojmap}

Your migrated sources will appear in `remappedSrc`. Verify that the migration produced valid migrated code.

Copy the sources from `remappedSrc` to the original folder. Keep the original sources backed up just in case.

### Updating Gradle {#updating-gradle-mojmap}

If you are coming from Yarn, you can now replace your mappings in your `build.gradle`'s dependencies section with Mojang Mappings.

```groovy
dependencies {
    [...]
      mappings loom.officialMojangMappings()
//    mappings "net.fabricmc:yarn${project.yarn_mappings}:v2"
}
```

You can now refresh the Gradle project in your IDE to apply your changes.

### Final Changes {#final-changes-mojmap}

That's the bulk of the work done! You'll now want to go through your source code to check and update any Mixin targets that may be outdated, and potentially fix any code that was not correctly remapped.

Tools like [mappings.dev](https://mappings.dev/) or [Linkie](https://linkie.shedaniel.dev/) will be helpful to familiarize yourself with your new mappings.

## Migrating to Yarn {#migrating-to-yarn}

First, you'll need a `migrateMappings` command that will convert your current mappings to Yarn Mappings. This can be easily found on [the Develop site](https://fabricmc.net/develop) under Mappings Migration, and an example is given below as well.

```groovy
migrateMappings --mappings "1.21.10+build.2"
```

You can replace `1.21.10` with the version of Minecraft you are migrating from. This must be the same version of Minecraft you are currently running. **Do not modify your `gradle.properties` or `build.gradle` yet!**

You can run this command in the terminal, prefixed with `./gradlew` on Linux/MacOS or `./gradlew.bat` on Windows, or by adding it as a run configuration in IntelliJ Idea.

### Editing Your Sources {#editing-sources-yarn}

Your migrated sources will appear in `remappedSrc`. Verify that the migration produced valid migrated code.

Copy the sources from `remappedSrc` to the original folder. Keep the original sources backed up just in case.

### Updating Gradle {#updating-gradle-yarn}

If you are coming from Mojang Mappings, you can now replace your mappings in your `build.gradle`'s dependencies section with Yarn Mappings. Make sure to also update your `gradle.properties` file with the Yarn version specified on [the Develop site](https://fabricmc.net/develop).

**`gradle.properties`**

```groovy
yarn_mappings=1.21.10+build.2
```

**`build.gradle`**

```groovy
dependencies {
    [...]
//   mappings loom.officialMojangMappings()
     mappings "net.fabricmc:yarn${project.yarn_mappings}:v2"
}
```

You can now refresh the Gradle project in your IDE to apply your changes.

### Final Changes {#final-changes-yarn}

That's the bulk of the work done! You'll now want to go through your source code to check and update any Mixin targets that may be outdated, and potentially fix any code that was not correctly remapped.

Tools like [mappings.dev](https://mappings.dev/) or [Linkie](https://linkie.shedaniel.dev/) will be helpful to familiarize yourself with your new mappings.

## Migrating Split Sources {#migrating-split-sources}

At this time, users of split sources currently need to migrate their main source set with the process above, then do the process again, specifying the path to their client source set. As a reference, the command to migrate a client source set to Mojang Mappings is below.

```groovy
migrateMappings --mappings "net.minecraft:mappings:1.21.10" --input src/client/java
```
