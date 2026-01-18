---
title: 在 VS Code 中生成源代码
description: 在 Visual Studio Code 中生成 Minecraft 源代码的指南。
authors:
  - dicedpixels
prev:
  text: 在 VS Code 中启动游戏
  link: ./launching-the-game
next:
  text: VS Code 提示和技巧
  link: ./tips-and-tricks
---

Fabric 工具链允许你通过在本地生成 Minecraft 源代码来访问它，并且你可以使用 Visual Studio Code 方便地浏览它。 要生成源代码，你需要运行 `genSources` Gradle 任务。

可以像上面一样在 Gradle 视图中执行此操作，方法是在 **Tasks** > **`fabric`** 中运行 `genSources` 任务：
![Gradle 视图中的 genSources 任务](/assets/develop/getting-started/vscode/gradle-gensources.png)

或者，你也可以从终端运行以下命令：

```sh:no-line-numbers
./gradlew genSources
```

![终端中的 genSources 任务](/assets/develop/getting-started/vscode/terminal-gensources.png)
