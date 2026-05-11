---
title: Migrating Mappings using Loom
description: Learn how to migrate your mod's obfuscation mappings using Fabric Loom.
authors:
  - asiekierka
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
  - basil4088
---

<!---->

::: warning

Mappings are not officially supported on 26.1. Before updating a mod that is using Yarn Mappings to 26.1, migrate to Mojang Mappings before changing the Minecraft dependency. The following docs assume you are using 1.21.11 and Loom 1.13 or higher.

:::

## Migrating to Mojang Mappings {#migrating-to-mojmap}

First, you need to run a `migrateMappings` command that will migrate your current mappings to Mojang Mappings. This will replace your current sources - **please be sure to make a back up**. For example, the following command would migrate to Mojang Mappings for 1.21.11:

::: code-group

```powershell:no-line-numbers [Windows]
./gradlew.bat migrateMappings --mappings "net.minecraft:mappings:1.21.11" --overrideInputsIHaveABackup
```

```sh:no-line-numbers [macOS/Linux]
./gradlew migrateMappings --mappings "net.minecraft:mappings:1.21.11" --overrideInputsIHaveABackup
```

```sh:no-line-numbers [IntelliJ]
migrateMappings --mappings "net.minecraft:mappings:1.21.11" --overrideInputsIHaveABackup
```

:::

You can replace `1.21.11` with the version of Minecraft you are migrating from. This must be the same version of Minecraft you are currently running. **Do not modify your `gradle.properties` or `build.gradle` yet!**

### Updating Gradle {#updating-gradle}

You can now replace your mappings in your `build.gradle`'s dependencies section with Mojang Mappings.

```groovy
dependencies {
    [...]
    mappings "net.fabricmc:yarn:${project.yarn_mappings}:v2" // [!code --]
    mappings loom.officialMojangMappings() // [!code ++]
}
```

You can now refresh the Gradle project in your IDE to apply your changes.

### Final Changes {#final-changes}

That's the bulk of the work done! You'll now want to go through your source code to check for any potentially outdated Mixin targets or code that was not remapped.

Tools like [mappings.dev](https://mappings.dev/) or [Linkie](https://linkie.shedaniel.dev/mappings?namespace=yarn&translateMode=ns&translateAs=mojang_raw&search=) will be helpful to familiarize yourself with your new mappings.

## Additional Configurations {#additional-configurations}

### Migrating Split Sources {#migrating-split-sources}

To migrate your client source set to your new mappings, use the `migrateClientMappings` task. For example, to migrate to Mojang Mappings:

::: code-group

```powershell:no-line-numbers [Windows]
./gradlew.bat migrateClientMappings --mappings "net.minecraft:mappings:1.21.11" --overrideInputsIHaveABackup
```

```sh:no-line-numbers [macOS/Linux]
./gradlew migrateClientMappings --mappings "net.minecraft:mappings:1.21.11" --overrideInputsIHaveABackup
```

```sh:no-line-numbers [IntelliJ]
migrateClientMappings --mappings "net.minecraft:mappings:1.21.11" --overrideInputsIHaveABackup
```

:::

If you are using an older version of Loom, see [other configurations](#other-configurations).

### Migrating Class Tweakers {#migrating-class-tweakers}

To migrate your access wideners and class tweakers to your new mappings, use the `migrateClassTweakerMappings` task. For example, to migrate to Mojang Mappings:

::: code-group

```powershell:no-line-numbers [Windows]
./gradlew.bat migrateClassTweakerMappings --mappings "net.minecraft:mappings:1.21.11" --overrideInputsIHaveABackup
```

```sh:no-line-numbers [macOS/Linux]
./gradlew migrateClassTweakerMappings --mappings "net.minecraft:mappings:1.21.11" --overrideInputsIHaveABackup
```

```sh:no-line-numbers [IntelliJ]
migrateClassTweakerMappings --mappings "net.minecraft:mappings:1.21.11" --overrideInputsIHaveABackup
```

:::

### Other Configurations {#other-configurations}

- Specify where to take your Java source files from with `--input path/to/source`. Default: `src/main/java`. You can use this to migrate a client sourceset by passing `--input src/client/java`.
- Specify where to output the remapped source with `--output path/to/output`. Default: `remappedSrc`. You can use `src/main/java` here to overwrite existing sources, but make sure you have a backup.
- Specify a custom place to retrieve the mappings from with `--mappings some_group:some_artifact:some_version:some_qualifier`. Default: `net.fabricmc:yarn:<version-you-inputted>:v2`. Use `net.minecraft:mappings:<minecraft-version>` to migrate to official Mojang mappings.

For example, to migrate a client source set to Mojang Mappings in-place (overwriting the existing sources):

::: code-group

```powershell:no-line-numbers [Windows]
./gradlew.bat migrateMappings --input "src/client/java" --output "src/client/java" --mappings "net.minecraft:mappings:1.21.11"
```

```sh:no-line-numbers [macOS/Linux]
./gradlew migrateMappings --input "src/client/java" --output "src/client/java" --mappings "net.minecraft:mappings:1.21.11"
```

```sh:no-line-numbers [IntelliJ]
migrateMappings --input "src/client/java" --output "src/client/java" --mappings "net.minecraft:mappings:1.21.11"
```

:::

If you need to migrate from Mojang Mappings to Yarn, the following command can be used, substituting `"1.21.11+build.3"` for the Yarn version you need. Note that Yarn is no longer officially supported by Fabric.

::: code-group

```powershell:no-line-numbers [Windows]
./gradlew.bat migrateMappings --mappings "1.21.11+build.3" --overrideInputsIHaveABackup
```

```sh:no-line-numbers [macOS/Linux]
./gradlew migrateMappings --mappings "1.21.11+build.3" --overrideInputsIHaveABackup
```

```sh:no-line-numbers [IntelliJ]
migrateMappings --mappings "1.21.11+build.3" --overrideInputsIHaveABackup
```

:::

<!---->
