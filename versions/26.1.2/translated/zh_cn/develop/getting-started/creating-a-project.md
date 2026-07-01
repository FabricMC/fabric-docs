---
title: 创建项目
description: 关于如何使用 Fabric 模板模组生成器创建新的模组项目的逐步指南。
authors:
  - Cactooz
  - IMB11
  - radstevee
  - Thomas1034​
resources:
  https://fabricmc.net/develop/template/: Fabric 模板模组生成器
  https://fabricmc.net/develop/: 使用 Fabric 网站开发
---

Fabric 提供了一种简单的方法来使用 Fabric 模板模组生成器来创建新的模组项目——如果你愿意，你可以使用示例模组代码仓库手动创建一个新项目，请参考 [手动创建项目](#manual-project-creation) 章节。

## 生成项目{#generating-a-project}

你可以使用 [Fabric 模板模组生成器](https://fabricmc.net/develop/template/)为你的模组生成一个新项目——你应该填写必要的字段，例如模组名称、包名以及你想开发的 Minecraft 版本。

包名应为小写，用点分隔，并保持唯一以避免与其他程序员的包冲突。 它通常被格式化为反向互联网域名，例如 `com.example.example-mod`。

:::warning 重要

请记住你的模组 ID！ 当你在这些文档中找到 `example-mod` ，尤其是在文件路径中时，你必须用你自己的替换它。

例如，如果你的模组 ID 是 **`my-cool-mod`**，则使用 **`resources/assets/my-cool-mod`** 而不是 _`resources/assets/example-mod`_。

:::

![生成器预览图](/assets/develop/getting-started/template-generator.png)

如果你 想使用 Kotlin、Kotlin 构建脚本或添加数据生成器，可以在 `Advanced Options` 部分选择相应的选项。

::: info

本网站提供的代码示例使用了[未混淆游戏中的名称](../porting/mappings/#whats-going-on-with-mappings) 如果你 现有的模组使用了除 Mojang 提供的映射之外的其他映射，请参阅我们的文档[移植到 26.1](../porting/index) 了解更多信息。

:::

![Advanced Options 部分](/assets/develop/getting-started/template-generator-advanced.png)

填写完所需字段后，单击 `Generate` 按钮，生成器将以 zip 文件的形式创建新项目供你使用。

你需要将这个 zip 文件解压到你想要的位置，然后在你的 IDE 中打开解压的文件夹。

::: tip

在为你的项目选择路径时，你应该遵循以下规则：

- 避免云存储目录（例如 Microsoft OneDrive）
- 避免非 ASCII 字符（例如表情符号、重音字母、汉字）
- 避免空格

一个“良好”的路径示例可以是： `C:\Projects\YourProjectName`

:::

## 手动创建项目 {#manual-project-creation}

:::info 前置条件

你需要安装 [Git](https://git-scm.com/) 来克隆示例模组代码仓库。

:::

如果不能使用 Fabric 模板模组生成器，可以按以下步骤手动创建新项目。

首先，使用 Git 克隆示例模组代码仓库：

```sh
git clone https://github.com/FabricMC/fabric-example-mod.git example-mod
```

这将把代码仓库克隆到一个名为 `example-mod` 的新文件夹中。

然后你应该从克隆的代码仓库中删除 `.git` 文件夹，并·打开项目。 如果找不到 `.git` 文件夹，你需要在你的文件资源管理器中启用显示隐藏文件。

### 设置你的 IDE {#setting-up}

在你的 IDE 中打开了项目之后，IDEA 会自动加载项目的 Gradle 配置并执行必要的初始化任务。

如果你 收到有关 Gradle 构建脚本的通知，则应单击 `Import Gradle Project` 按钮。

更多信息，请查看[设置你的 IDE](./setting-up)页面。

### 修改模板 {#modifying-the-template}

导入项目后，你应该修改项目的详细信息以匹配你的模组的信息：

- 修改项目的 `gradle.properties` 文件，把 `maven_group` 和 `archive_base_name` 修改为匹配你的模组的信息。
- 修改 `fabric.mod.json` 文件，把 `id`、`name` 和 `description` 修改为匹配你的模组的信息。
- 请务必更新 Minecraft 版本、地图文件、加载器和 Loom，所有这些都可以通过[开发者网站](https://fabricmc.net/develop/)查询，以匹配你 希望使用的目标版本。

然后，你可以修改包名和模组的主类来匹配你的模组的详细信息。
