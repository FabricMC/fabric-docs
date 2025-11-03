---
title: Migrating Mappings
description: Learn how to migrate your mod's obfuscation mappings.
authors:
  - cassiancc
  - florensie
  - Friendly-Banana
  - IMB11
  - jamierocks
  - JamiesWhiteShirt
  - MildestToucan
  - modmuss50
  - natanfudge
  - Spinoscythe
  - UpcraftLP
authors-nogithub:
  - asie
  - basil4088
---

::: info
For best results, it's recommended to update to Loom 1.13 or above, as it allows for migrating Mixins, Access Wideners and client source sets.
:::

Historically, Minecraft: Java Edition has made use of obfuscation, which led to the development of obfuscation maps that Fabric Loom uses for modding. There were two choices: either Fabric's own Yarn mappings, or the official Mojang mappings.

Mojang have recently announced [they're removing code obfuscation from Minecraft: Java Edition](https://www.minecraft.net/en-us/article/removing-obfuscation-in-java-edition), and the Fabric Project followed up with [its plan for handling this change](https://fabricmc.net/2025/10/31/obfuscation.html).

You may wish to migrate from Yarn to Mojang Mappings, or vice-versa, especially if you are planning on updating your mod past the Mounts of Mayhem game drop. To do this, Loom offers a semi-automated migration of the mappings through the `migrateMappings` task.

Loom does not support migrating code written in Kotlin.

## What Are Mappings? {#mappings}

Minecraft: Java Edition has been obfuscated since its release, which means replacing human-friendly class names like `Creeper` with something like `brc`. In order to easily mod it, Fabric Loom makes use of obfuscation maps: references which translate obfuscated class names, such as `brc`, back to human-friendly names like `CreeperEntity`.

As a Fabric developer, you'll encounter three main sets of names:

- **Intermediary**: The mapping set used by compiled Fabric mods; for example `brc` may become `class_1548`. The point behind Intermediary is offering a stable set of names across releases, as obfuscated class names change with each new version of Minecraft. This often allows mods built for one version to work on others, as long as the affected parts of the game haven't changed too much.
- **Yarn**: an open-source mapping set developed by Fabric for humans to write mods. Most Fabric mods used Yarn Mappings, as they were the default before 2025. An example mapping might be `CreeperEntity`.
- **Mojang Mappings**: The game's official obfuscation mappings, released by Mojang in 2019 to aid mod development. An example mapping might be `Creeper`.

## Migrating to Mojang Mappings {#migrating-to-mojmap}

First, you need to run a `migrateMappings` command that will migrate your current mappings to Mojang Mappings. For example, the following command would migrate to Mojang Mappings for 1.21.10:

::: code-group

```powershell:no-line-numbers [Windows]
./gradlew.bat migrateMappings --mappings "net.minecraft:mappings:1.21.10"
```

```sh:no-line-numbers [macOS/Linux]
./gradlew migrateMappings --mappings "net.minecraft:mappings:1.21.10"
```

```sh:no-line-numbers [IntelliJ]
migrateMappings migrateMappings --mappings "net.minecraft:mappings:1.21.10"
```

:::

You can replace `1.21.10` with the version of Minecraft you are migrating from. This must be the same version of Minecraft you are currently running. **Do not modify your `gradle.properties` or `build.gradle` yet!**

### Editing Your Sources {#editing-sources-mojmap}

By default, the migrated source code will appear in `remappedSrc`, rather than overwriting your existing project. You'll need to copy the sources from `remappedSrc` to the original folder. Make sure to back up the original sources, just in case.

If you are using Loom 1.13 or above, you can use the program argument `--overrideInputsIHaveABackup` to replace your sources directly.

::: code-group

```powershell:no-line-numbers [Windows]
./gradlew.bat migrateMappings --mappings "net.minecraft:mappings:1.21.10" --overrideInputsIHaveABackup
```

```sh:no-line-numbers [macOS/Linux]
./gradlew migrateMappings --mappings "net.minecraft:mappings:1.21.10" --overrideInputsIHaveABackup
```

```sh:no-line-numbers [IntelliJ]
migrateMappings migrateMappings --mappings "net.minecraft:mappings:1.21.10" --overrideInputsIHaveABackup
```

:::

### Updating Gradle {#updating-gradle-mojmap}

If you are coming from Yarn, you can now replace your mappings in your `build.gradle`'s dependencies section with Mojang Mappings.

```groovy
dependencies {
    [...]
    mappings "net.fabricmc:yarn${project.yarn_mappings}:v2" // [!code --]
    mappings loom.officialMojangMappings() // [!code ++]
}
```

You can now refresh the Gradle project in your IDE to apply your changes.

### Final Changes {#final-changes-mojmap}

That's the bulk of the work done! You'll now want to go through your source code to check for any potentially outdated Mixin targets or code that was not remapped.

Tools like [mappings.dev](https://mappings.dev/) or [Linkie](https://linkie.shedaniel.dev/mappings?namespace=yarn&translateMode=ns&translateAs=mojang_raw&search=) will be helpful to familiarize yourself with your new mappings.

## Migrating to Yarn {#migrating-to-yarn}

First, you need to run a `migrateMappings` command that will convert your current mappings to Yarn Mappings. This can be found on [the Develop site](https://fabricmc.net/develop) under Mappings Migration. For example:

::: code-group

```powershell:no-line-numbers [Windows]
./gradlew.bat migrateMappings --mappings "1.21.10+build.2"
```

```sh:no-line-numbers [macOS/Linux]
./gradlew migrateMappings --mappings "1.21.10+build.2"
```

```sh:no-line-numbers [IntelliJ]
migrateMappings migrateMappings --mappings "1.21.10+build.2"
```

:::

You can replace `1.21.10` with the version of Minecraft you are migrating from. This must be the same version of Minecraft you are currently running. **Do not modify your `gradle.properties` or `build.gradle` yet!**

### Editing Your Sources {#editing-sources-yarn}

By default, the migrated source code will appear in `remappedSrc`, rather than overwriting your existing project. You'll need to copy the sources from `remappedSrc` to the original folder. Make sure to back up the original sources, just in case.

If you are using Loom 1.13 or above, you can use the program argument `--overrideInputsIHaveABackup` to replace your sources directly.

::: code-group

```powershell:no-line-numbers [Windows]
./gradlew.bat migrateMappings --mappings "1.21.10+build.2 --overrideInputsIHaveABackup"
```

```sh:no-line-numbers [macOS/Linux]
./gradlew migrateMappings --mappings "1.21.10+build.2 --overrideInputsIHaveABackup"
```

```sh:no-line-numbers [IntelliJ]
migrateMappings --mappings "1.21.10+build.2 --overrideInputsIHaveABackup"
```

:::

### Updating Gradle {#updating-gradle-yarn}

If you are migrating from Mojang Mappings, you can now replace your mappings in your `build.gradle`'s dependencies section with Yarn Mappings. Make sure to also update your `gradle.properties` file with the Yarn version specified on [the Develop site](https://fabricmc.net/develop).

**`gradle.properties`**

```properties
yarn_mappings=1.21.10+build.2
```

**`build.gradle`**

```groovy
dependencies {
    [...]
    mappings loom.officialMojangMappings() // [!code --]
    mappings "net.fabricmc:yarn${project.yarn_mappings}:v2" // [!code ++]
}
```

You can now refresh the Gradle project in your IDE to apply your changes.

### Final Changes {#final-changes-yarn}

That's the bulk of the work done! You'll now want to go through your source code to check for any potentially outdated Mixin targets or code that was not remapped.

Tools like [mappings.dev](https://mappings.dev/) or [Linkie](https://linkie.shedaniel.dev/mappings?namespace=mojang_raw&translateMode=ns&translateAs=yarn&search=) will be helpful to familiarize yourself with your new mappings.

## Additional Configurations {#additional-configurations}

### Migrating Split Sources {#migrating-split-sources}

Loom 1.13 adds a new `migrateClientMappings` task that can be used to migrate your client sourceset to your new mappings. For example, to migrate to Mojang Mappings:

::: code-group

```powershell:no-line-numbers [Windows]
./gradlew.bat migrateClientMappings --mappings "net.minecraft:mappings:1.21.10"
```

```sh:no-line-numbers [macOS/Linux]
./gradlew migrateClientMappings --mappings "net.minecraft:mappings:1.21.10"
```

```sh:no-line-numbers [IntelliJ]
migrateClientMappings --mappings "net.minecraft:mappings:1.21.10"
```

:::

If you are using an older version of Loom, see [other configurations](#other-configurations).

### Migrating Access Wideners {#migrating-access-wideners}

Loom 1.13 adds a new `migrateClassTweakerMappings` task that can be used to migrate your access wideners to your new mappings. For example, to migrate to Mojang Mappings:

::: code-group

```powershell:no-line-numbers [Windows]
./gradlew.bat migrateClassTweakerMappings --mappings "net.minecraft:mappings:1.21.10"
```

```sh:no-line-numbers [macOS/Linux]
./gradlew migrateClassTweakerMappings --mappings "net.minecraft:mappings:1.21.10"
```

```sh:no-line-numbers [IntelliJ]
migrateClassTweakerMappings --mappings "net.minecraft:mappings:1.21.10"
```

:::

### Other Configurations {#other-configurations}

- Specify where to take your Java source files from with `--input path/to/source`. Default: `src/main/java`. You can use this to migrate a client sourceset by passing `--input src/client/java`.
- Specify where to output the remapped source with `--output path/to/output`. Default: `remappedSrc`. You can use `src/main/java` here to overwrite existing sources, but make sure you have a backup.
- Specify a custom place to retrieve the mappings from with `--mappings some_group:some_artifact:some_version:some_qualifier`. Default: `net.fabricmc:yarn:<version-you-inputted>:v2`. Use `net.minecraft:mappings:<minecraft-version>` to migrate to official Mojang mappings.

For example, to migrate a client source set to Mojang Mappings in-place (overwriting the existing sources):

::: code-group

```powershell:no-line-numbers [Windows]
./gradlew.bat migrateMappings --input "src/client/java" --output "src/client/java" --mappings "net.minecraft:mappings:1.21.10"
```

```sh:no-line-numbers [macOS/Linux]
./gradlew migrateMappings --input "src/client/java" --output "src/client/java" --mappings "net.minecraft:mappings:1.21.10"
```

```sh:no-line-numbers [IntelliJ]
migrateMappings --input "src/client/java" --output "src/client/java" --mappings "net.minecraft:mappings:1.21.10"
```

:::
