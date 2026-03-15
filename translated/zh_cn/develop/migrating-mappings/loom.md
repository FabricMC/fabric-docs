---
title: 使用 Loom 迁移映射
description: 了解如何使用 Fabric Loom 迁移模组的混淆映射。
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

为了达到最佳效果，建议更新到 Loom 1.13 或更高版本，因为它允许迁移 mixin、访问加宽器（Access Widener）和客户端源集。

:::

## 迁移到 Mojang 映射 {#migrating-to-mojmap}

首先，你需要运行 `migrateMappings` 命令，将你当前的映射迁移到 Mojang 映射。 例如，以下命令会将映射迁移到 1.21.11 版本的 Mojang 映射：

::: code-group

```powershell:no-line-numbers [Windows]
./gradlew.bat migrateMappings --mappings "net.minecraft:mappings:1.21.11"
```

```sh:no-line-numbers [macOS/Linux]
./gradlew migrateMappings --mappings "net.minecraft:mappings:1.21.11"
```

```sh:no-line-numbers [IntelliJ]
migrateMappings --mappings "net.minecraft:mappings:1.21.11"
```

:::

你可以将 `1.21.11` 替换为你要迁移自的 Minecraft 版本。 该版本必须与你当前运行的 Minecraft 版本相同。 **请勿修改 `gradle.properties` 或 `build.gradle` 文件！**

### 编辑你的源代码 {#editing-sources-mojmap}

默认情况下，迁移后的源代码会出现在 `remappedSrc` 中，而不是覆盖你现有的项目。 你需要将 `remappedSrc` 中的源代码复制到原始文件夹。 为了以防万一，请务必备份原始源代码。

如果你使用的是 Loom 1.13 或更高版本，则可以使用程序参数 `--overrideInputsIHaveABackup` 直接替换源代码。

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

### 更新 Gradle {#updating-gradle-mojmap}

如果你之前使用的是 Yarn，现在可以将 `build.gradle` 文件中依赖项部分的映射替换为 Mojang 映射。

```groovy
dependencies {
    [...]
    mappings "net.fabricmc:yarn:${project.yarn_mappings}:v2" // [!code --]
    mappings loom.officialMojangMappings() // [!code ++]
}
```

现在你可以在 IDE 中刷新 Gradle 项目来应用更改。

### 最终修改 {#final-changes-mojmap}

大部分工作已经完成了！ 现在你需要检查源代码，看看是否有过时的 Mixin 目标或尚未重新映射的代码。

像 [mappings.dev](https://mappings.dev/) 或 [Linkie](https://linkie.shedaniel.dev/mappings?namespace=yarn&translateMode=ns&translateAs=mojang_raw&search=) 这样的工具可以帮助你熟悉新的映射。

## 迁移到 Yarn {#migrating-to-yarn}

::: warning

1.21.11 是 Yarn 映射可用的最后一个发布。 如果计划将模组升级到 26.1 或以上版本，模组应当基于 Mojang 的映射。

:::

首先，你需要运行 `migrateMappings` 命令，将你当前的映射转换为 Yarn 映射。 该命令可以在 [Develop 网站](https://fabricmc.net/develop)的映射迁移（Mappings Migration）部分找到。 例如：

::: code-group

```powershell:no-line-numbers [Windows]
./gradlew.bat migrateMappings --mappings "1.21.11+build.3"
```

```sh:no-line-numbers [macOS/Linux]
./gradlew migrateMappings --mappings "1.21.11+build.3"
```

```sh:no-line-numbers [IntelliJ]
migrateMappings --mappings "1.21.11+build.3"
```

:::

你可以将 `1.21.11` 替换为你要迁移自的 Minecraft 版本。 该版本必须与你当前运行的 Minecraft 版本相同。 **请勿修改 `gradle.properties` 或 `build.gradle` 文件！**

### 编辑你的源代码 {#editing-sources-yarn}

默认情况下，迁移后的源代码会出现在 `remappedSrc` 中，而不是覆盖你现有的项目。 你需要将 `remappedSrc` 中的源代码复制到原始文件夹。 为了以防万一，请务必备份原始源代码。

如果你使用的是 Loom 1.13 或更高版本，则可以使用程序参数 `--overrideInputsIHaveABackup` 直接替换源代码。

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

### 更新 Gradle {#updating-gradle-yarn}

如果你正在从 Mojang 映射进行迁移，现在可以将 `build.gradle` 文件中依赖项部分的映射替换为 Yarn 映射。 请务必使用 [Develop 网站](https://fabricmc.net/develop)上指定的 Yarn 版本更新 `gradle.properties` 文件。

**`gradle.properties`**

```properties
yarn_mappings=1.21.11+build.3
```

**`build.gradle`**

```groovy
dependencies {
    [...]
    mappings loom.officialMojangMappings() // [!code --]
    mappings "net.fabricmc:yarn:${project.yarn_mappings}:v2" // [!code ++]
}
```

现在你可以在 IDE 中刷新 Gradle 项目来应用更改。

### 最终修改 {#final-changes-yarn}

大部分工作已经完成了！ 现在你需要检查源代码，看看是否有过时的 Mixin 目标或尚未重新映射的代码。

像 [mappings.dev](https://mappings.dev/) 或 [Linkie](https://linkie.shedaniel.dev/mappings?namespace=mojang_raw&translateMode=ns&translateAs=yarn&search=) 这样的工具可以帮助你熟悉新的映射。

## 额外配置 {#additional-configurations}

### 迁移拆分源代码 {#migrating-split-sources}

Loom 1.13 新增了 `migrateClientMappings` 任务，可用于将客户端源集迁移到新的映射。 例如，要迁移到 Mojang 映射：

::: code-group

```powershell:no-line-numbers [Windows]
./gradlew.bat migrateClientMappings --mappings "net.minecraft:mappings:1.21.11"
```

```sh:no-line-numbers [macOS/Linux]
./gradlew migrateClientMappings --mappings "net.minecraft:mappings:1.21.11"
```

```sh:no-line-numbers [IntelliJ]
migrateClientMappings --mappings "net.minecraft:mappings:1.21.11"
```

:::

如果你使用的是旧版本的 Loom，请参阅[其他配置](#other-configurations)。

### 迁移 Access Widener {#migrating-access-wideners}

Loom 1.13 新增了 `migrateClassTweakerMappings` 任务，可用于将访问加宽器（Access Widener）迁移到新的映射。 例如，要迁移到 Mojang 映射：

::: code-group

```powershell:no-line-numbers [Windows]
./gradlew.bat migrateClassTweakerMappings --mappings "net.minecraft:mappings:1.21.11"
```

```sh:no-line-numbers [macOS/Linux]
./gradlew migrateClassTweakerMappings --mappings "net.minecraft:mappings:1.21.11"
```

```sh:no-line-numbers [IntelliJ]
migrateClassTweakerMappings --mappings "net.minecraft:mappings:1.21.11"
```

:::

### 其他配置 {#other-configurations}

- 使用 `--input path/to/source` 指定 Java 源文件的来源。 默认为：`src/main/java`。 你可以使用此选项通过传递 `--input src/client/java` 来迁移客户端源集。
- 使用 `--output path/to/output` 指定重新映射后的源代码的输出位置。 默认为：`remappedSrc`。 你可以使用 `src/main/java` 覆盖现有源文件，但请确保事先备份。
- 使用 `--mappings some_group:some_artifact:some_version:some_qualifier` 指定自定义映射文件的来源。 默认为：`net.fabricmc:yarn:<version-you-inputted>:v2`。 使用 `net.minecraft:mappings:<minecraft-version>` 迁移到官方 Mojang 映射。

例如，要将客户端源集就地迁移到 Mojang 映射（覆盖现有源代码）：

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

<!---->
