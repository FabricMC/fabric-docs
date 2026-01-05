---
title: 在 IntelliJ IDEA 中生成源码
description: 在 IntelliJ IDEA 中生成 Minecraft 源码的指南。
authors:
  - PEQB1145
---

Fabric 工具链允许你通过在本地生成 Minecraft 源代码来访问它，你可以使用 IntelliJ IDEA 方便地浏览这些源代码。要生成源码，你需要运行 Gradle 任务 `genSources`。

这可以像上面一样从 Gradle 视图完成，在 **Tasks** > **`fabric`** 中运行 `genSources` 任务：
![Gradle 面板中的 `genSources` 任务](/assets/develop/getting-started/intellij/gradle-gensources.png)

或者你也可以在终端中运行命令：

```sh:no-line-numbers
./gradlew genSources
```

![终端中的 `genSources` 任务](/assets/develop/getting-started/intellij/terminal-gensources.png)

## 附加源码 {#attaching-sources}

IntelliJ 还需要一个额外的步骤，将生成的源码附加到项目中。

要执行此操作，请打开任意的 Minecraft 类。你可以按 <kbd>Ctrl</kbd> + 点击来跳转到定义（这会打开该类），或者使用“随处搜索”来打开一个类。

以打开 `MinecraftServer.class` 为例。此时你应该会在顶部看到一个蓝色的横幅，其中包含一个“**Choose Sources...**”链接。

![选择源码](/assets/develop/getting-started/intellij/choose-sources.png)

点击“**Choose Sources...**”以打开一个文件选择器对话框。默认情况下，此对话框会打开到生成源码的正确位置。

选择以 **`-sources`** 结尾的文件，然后按 **Open** 确认选择。

![选择源码对话框](/assets/develop/getting-started/intellij/choose-sources-dialog.png)

现在你应该能够搜索引用了。如果你使用的是包含 Javadoc 的映射集，例如 [Parchment](https://parchmentmc.org/)（用于 Mojang 映射）或 Yarn，那么现在也应该能看到 Javadoc。

![源码中的 Javadoc 注释](/assets/develop/getting-started/intellij/javadoc.png)
