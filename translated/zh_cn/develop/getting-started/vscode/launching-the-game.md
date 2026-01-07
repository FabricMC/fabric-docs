---
title: 在 VS Code 中启动游戏
description: 了解如何从 Visual Studio Code 启动 Minecraft 实例。
authors:
  - dicedpixels
prev:
  text: 在 VS Code 中打开项目
  link: ./opening-a-project
next:
  text: 在 VS Code 中生成源代码
  link: ./generating-sources
---

Fabric 工具链与 Visual Studio Code 集成，提供了一种运行游戏实例来测试和调试模组的便捷方法。

## 生成启动目标 {#generating-launch-targets}

要在启用调试支持的情况下运行游戏，你需要通过运行 `vscode` Gradle 任务来生成启动目标。

这可以在 Visual Studio Code 中的 Gradle 视图中完成：打开它，然后在 **任务** > **`ide`** 中找到 `vscode` 任务。 双击或使用**运行任务**按钮执行该任务。

![Gradle 视图中的 vscode 任务](/assets/develop/getting-started/vscode/gradle-vscode.png)

或者，也可以直接使用终端：通过 **终端** > **新建终端** 打开一个新终端，然后运行：

```sh:no-line-numbers
./gradlew vscode
```

![终端中的 vscode 任务](/assets/develop/getting-started/vscode/terminal-vscode.png)

### 使用启动目标 {#using-launch-targets}

一旦生成启动目标，就可以通过打开**运行和调试**视图，选择所需的目标并按**开始调试**按钮（<kbd>F5</kbd>）来使用它们。

![启动目标](/assets/develop/getting-started/vscode/launch-targets.png)
