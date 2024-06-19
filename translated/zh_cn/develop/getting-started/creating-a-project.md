---
title: 创建一个项目
description: 一个关于如何使用Fabric模板模组生成器创建一个新的模组项目的逐步指南。
authors:
  - IMB11
---

# 创建一个项目

Fabric提供了一种简单的方法来使用Fabric模板模组生成器来创建一个新的模组项目——如果你愿意，你可以使用示例模组代码仓库手动创建一个新项目，请参考[手动创建项目](#manual-project-creation)。

## 生成一个项目

你可以使用[Fabric模板模组生成器](https://fabricmc.net/develop/template/)为你的模组生成一个新项目——你应该填写必要的字段，比如包名和模组名称，以及你想要基于开发的Minecraft版本。

![生成器预览图](/assets/develop/getting-started/template-generator.png)

如果你想要使用Kotlin语言开发，或者想要添加数据生成器，可以在“Advanced Options”部分中选择对应的选项。

![“Advanced Options”部分](/assets/develop/getting-started/template-generator-advanced.png)

在你填写了必需的字段后，单击“Generate”按钮，生成器将以zip文件的形式创建一个新项目供您使用。

你需要将这个zip文件解压到你想要的位置，然后在IntelliJ IDEA中打开解压的文件夹:

![打开项目按钮提示](/assets/develop/getting-started/open-project.png)

## 导入项目

你在IntelliJ IDEA中打开了项目之后，IDEA会自动加载项目的Gradle配置并执行必要的初始化任务。

如果你收到一个关于Gradle构建脚本的通知，你应该点击`Import Gradle Project`按钮：

![Gradle按钮提示](/assets/develop/getting-started/gradle-prompt.png)

项目被导入后，你可以在项目资源管理器中看到项目的文件，并且能够开始开发你的模组。

## 手动创建项目

:::warning
你需要安装[Git](https://git-scm.com/)来克隆示例模组代码仓库。
:::

如果你不能使用Fabric示例模组生成器，你可以按照以下步骤手动创建一个新项目。

首先，使用Git来克隆示例模组代码仓库：

```sh
git clone https://github.com/FabricMC/fabric-example-mod/ my-mod-project
```

这会将代码仓库克隆进一个叫`my-mod-project`的新文件夹。

然后，您应该删除`.git`文件夹，并在IntelliJ IDEA中打开项目。 如果你找不到`.git`文件夹，你需要在你的文件资源管理器中启用显示隐藏文件。 如果你找不到`.git`文件夹，你需要在你的文件资源管理器中启用显示隐藏文件。

你在IntelliJ IDEA中打开了项目之后，IDEA会自动加载项目的Gradle配置并执行必要的初始化任务。

如上所述，如果你收到一个关于Gradle构建脚本的通知，你应该点击`Import Gradle Project`按钮：

### 修改模板

当项目被导入，你需要修改项目的信息，以匹配你的模组的信息:

- 修改项目中的`gradle.properties`文件，把`maven_group`和`archive_base_name`修改为匹配你的模组的信息。
- 修改项目中的`fabric.mod.json`文件，把`id`、`name`和`description`修改为匹配你的模组的信息。
- 确保修改你的项目的Minecraft的版本，映射，Fabric模组加载器和Fabric loom——所有这些都可以通过<https://fabricmc.net/develop/>查询，以匹配您希望的目标版本。

你还可以修改包名和模组的主类来匹配你的模组的细节。
