---
title: 在 IntelliJ IDEA 中生成源代码
description: 在 IntelliJ IDEA 中生成 Minecraft 源代码的指南。
authors:
  - dicedpixels
prev:
  text: 在 IntelliJ IDEA 中启动游戏
  link: ./launching-the-game
next:
  text: IntelliJ IDEA 提示和技巧
  link: ./tips-and-tricks
---

Fabric 工具链允许你通过在本地生成 Minecraft 源代码来访问它，并且你可以使用 IntelliJ IDEA 方便地浏览它。 要生成源代码，你需要运行 `genSources` Gradle 任务。

可以像上面一样在 Gradle 视图中执行此操作，方法是在 **Tasks** > **`fabric`** 中运行 `genSources` 任务：
![Gradle 面板中的 genSources 任务](/assets/develop/getting-started/intellij/gradle-gensources.png)

或者，你也可以从终端运行以下命令：

```sh:no-line-numbers
./gradlew genSources
```

![终端中的 genSources 任务](/assets/develop/getting-started/intellij/terminal-gensources.png)

## 附加源代码 {#attaching-sources}

IntelliJ 需要额外一步将生成的源代码附加到项目。

要这样做，请打开任意 Minecraft 类。 你可以按 <kbd>Ctrl</kbd> + 单击转到定义，从而打开类或使用“Search everywhere”来打开类。

我们打开 `MinecraftServer.class` 举例。 你现在应该会看到顶部有一个蓝色横幅，上面有个“**Choose Sources...**”链接。

![选择源代码](/assets/develop/getting-started/intellij/choose-sources.png)

单击“Choose Sources...”打开文件选择器对话框。 默认情况下，此对话框将在生成源代码的正确位置打开。

选择以 **`-sources`** 结尾的文件，然后单击**打开**确认选择。

![选择源代码对话框](/assets/develop/getting-started/intellij/choose-sources-dialog.png)

你现在应该可以搜索参考了。 如果你使用的是包含 Javadoc 的映射集，例如 [Parchment](https://parchmentmc.org/)（用于 Mojang 映射）或 Yarn，你现在也应该可以看到 Javadoc。

![源代码中的 Javadoc 注释](/assets/develop/getting-started/intellij/javadoc.png)
