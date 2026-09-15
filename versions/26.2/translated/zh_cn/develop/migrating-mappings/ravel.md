---
title: 使用 Ravel 迁移映射
description: 了解如何使用 Ravel 迁移模组的混淆映射。
authors:
  - cassiancc
  - deirn
---

[Ravel](https://github.com/badasintended/ravel) 是一个 IntelliJ IDEA 插件，用于重新映射源代码文件，基于 [IntelliJ 的 PSI](https://plugins.jetbrains.com/docs/intellij/psi.html) 和 [Mapping-IO](https://github.com/FabricMC/mapping-io)， 支持重新映射 Java、Kotlin、Mixin（用 Java 编写）、类调整器和访问加宽器。

你可以从 [JetBrains Marketplace](https://plugins.jetbrains.com/plugin/28938-ravel-remapper) 安装，也可以从 [GitHub Releases](https://github.com/badasintended/ravel/releases) 下载 ZIP 文件，然后点击插件设置中的齿轮图标，再点击 **Install Plugin From Disk**（从磁盘安装插件）进行安装。

![IDEA 从磁盘安装插件](/assets/develop/misc/migrating-mappings/idea_local_plugin.png)

## 迁移映射 {#migrating-mappings}

::: warning

在尝试重新映射源代码之前，先提交所有更改！ **请勿修改 `gradle.properties` 或 `build.gradle` 文件！**

:::

接下来，右键单击编辑器中打开的文件，然后选择 **Refactor** > **Remap Using Ravel**

![右键菜单](/assets/develop/misc/migrating-mappings/ravel_right_click.png)

此时会弹出类似这样的对话框。 你也可以点击顶部菜单中的 **Refactor** 打开对话框。

![Ravel 对话框](/assets/develop/misc/migrating-mappings/ravel_dialog.png)

接下来，点击“+”图标添加映射。 如果你还没有这些文件，请单击下载选项。

::: info

如果没有看到下载按钮，更新 Ravel 到 0.3 或以上版本。

:::

- 要从 Yarn 迁移到 Mojang 映射，首先添加 Yarn 的 `mappings.tiny` 文件，并将**源**命名空间选择为 `named`，**目标**命名空间选择为 `official`。 然后，添加 Mojang 的 `client.txt` 文件，并将**源**命名空间选择为 `target`，**目标**命名空间选择为 `source`。
- 要从 Mojang 映射迁移到 Yarn，首先添加 Mojang 的 `client.txt` 文件，这次将**源**命名空间选择为 `source`，**目标**命名空间选择为 `target`。 然后，添加 Yarn 的 `mappings.tiny` 文件，并将**源**命名空间选择为 `official`，**目标**命名空间选择为 `named`。

然后，单击“+”图标选择要重新映射的模块，或者点击左侧的图标添加所有模块。

然后，单击 **OK** 并等待重新映射完成。

### 更新 Gradle {#updating-gradle}

重新映射完成后，请将映射替换到你模组的 `build.gradle` 中。

```groovy
dependencies {
    mappings "net.fabricmc:yarn:${project.yarn_mappings}:v2" // [!code --]
    mappings loom.officialMojangMappings() // [!code ++]
    // Or the reverse if you're migrating from Mojang Mappings to Yarn
}
```

同时更新你的 `gradle.properties` 文件，移除 `yarn_mappings` 项或将其更新为你正在使用的项。

```properties
yarn_mappings=1.21.11+build.3 # [!code --]
```

### 最终修改 {#final-changes}

大部分工作已经完成了！ 现在你需要检查源代码，看看是否有过时的 Mixin 目标或尚未重新映射的代码。

对于 Ravel 检测到的问题，可以使用快捷键（<kbd>Ctrl</kbd>+<kbd>Shift</kbd>+<kbd>F</kbd>）搜索 `TODO(Ravel)`。

![Ravel TODO 搜索](/assets/develop/misc/migrating-mappings/ravel_todo.png)

像 [mappings.dev](https://mappings.dev/) 或 [Linkie](https://linkie.shedaniel.dev/mappings?namespace=yarn&translateMode=ns&translateAs=mojang_raw&search=&version=1.21.11) 这样的工具可以帮助你熟悉新的映射。
