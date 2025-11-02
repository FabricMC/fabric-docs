---
title: Migrating Mappings
description: Learn how to migrate your mod's obfuscation mappings.
authors:
  - cassiancc
---

::: info
For best results, it's recommended to update to Loom 1.13 or above, as it allows for migrating Mixins, Access Wideners and client source sets.
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

With the default configuration, your migrated sources will appear in `remappedSrc` rather than overwriting your existing sources. You'll need to copy the sources from `remappedSrc` to the original folder. Keep the original sources backed up just in case.

If you are using Loom 1.13 or above, you can use the program argument `--overrideInputsIHaveABackup` to replace your sources directly.

```groovy
migrateMappings --mappings "net.minecraft:mappings:1.21.10 --overrideInputsIHaveABackup"
```

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

Tools like [mappings.dev](https://mappings.dev/) or [Linkie](https://linkie.shedaniel.dev/mappings?namespace=yarn&translateMode=ns&translateAs=mojang_raw&search=) will be helpful to familiarize yourself with your new mappings.

## Migrating to Yarn {#migrating-to-yarn}

First, you'll need a `migrateMappings` command that will convert your current mappings to Yarn Mappings. This can be easily found on [the Develop site](https://fabricmc.net/develop) under Mappings Migration, and an example is given below as well.

```groovy
migrateMappings --mappings "1.21.10+build.2"
```

You can replace `1.21.10` with the version of Minecraft you are migrating from. This must be the same version of Minecraft you are currently running. **Do not modify your `gradle.properties` or `build.gradle` yet!**

You can run this command in the terminal, prefixed with `./gradlew` on Linux/MacOS or `./gradlew.bat` on Windows, or by adding it as a run configuration in IntelliJ Idea.

### Editing Your Sources {#editing-sources-yarn}

With the default configuration, your migrated sources will appear in `remappedSrc` rather than overwriting your existing sources. You'll need to copy the sources from `remappedSrc` to the original folder. Keep the original sources backed up just in case.

If you are using Loom 1.13 or above, you can use the program argument `--overrideInputsIHaveABackup` to replace your sources directly.

```groovy
migrateMappings --mappings "1.21.10+build.2 --overrideInputsIHaveABackup"
```

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

Tools like [mappings.dev](https://mappings.dev/) or [Linkie](https://linkie.shedaniel.dev/mappings?namespace=mojang_raw&translateMode=ns&translateAs=yarn&search=) will be helpful to familiarize yourself with your new mappings.

## Additional Configurations {#additional-configurations}

### Migrating Split Sources {#migrating-split-sources}

Loom 1.13 adds a new `migrateClientMappings` task that can be used to migrate your client sourceset to your new mappings. An example for migrating to Mojang Mappings can be seen below. If you are using an older version of Loom, see [other configurations](#other-configurations).

```groovy
migrateClientMappings --mappings "net.minecraft:mappings:1.21.10"
```

### Migrating Access Wideners {#migrating-access-wideners}

Loom 1.13 adds a new `migrateClassTweakerMappings` task that can be used to migrate your access wideners to your new mappings. An example for migrating to Mojang Mappings can be seen below.

```groovy
migrateClassTweakerMappings --mappings "net.minecraft:mappings:1.21.10"
```

### Other Configurations {#other-configurations}

- Specify from where to take your Java files with `--input path/to/source`. Default: `src/main/java`. You can use this to migrate a client sourceset by passing `--input src/client/java`.
- Specify where to output the remapped source with `--output path/to/output`. Default: `remappedSrc`. You can use `src/main/java` here to avoid having to copy the remapped classes, but make sure you have a backup.
- Specify a custom place to retrieve the mappings from with `--mappings some_group:some_artifact:some_version:some_qualifier`. Default: `net.fabricmc:yarn:<version-you-inputted>:v2`. Use `net.minecraft:mappings:<minecraft-version>` to migrate to official Mojang mappings.

A complete example that migrates a client source set to Mojang Mappings, overwriting the existing source set is below.

```groovy
migrateMappings --input "src/client/java" --output "src/client/java" --mappings "net.minecraft:mappings:1.21.10"
```
