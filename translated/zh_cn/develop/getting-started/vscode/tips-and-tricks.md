---
title: VS Code 提示和技巧
description: 有用的提示和技巧让你的工作更轻松。
authors:
  - dicedpixels
prev:
  text: 在 VS Code 中生成源代码
  link: ./generating-sources
next: false
---

学习如何翻阅生成的源代码非常重要，这样你才能调试并理解 Minecraft 的内部工作原理。 这里我们概述了一些常见的 IDE 用法。

## 搜索 Minecraft 类 {#searching-for-a-minecraft-class}

生成源代码后， 你就应该可以搜索或查看 Minecraft 类。

### 查看类定义 {#viewing-class-definitions}

**快速打开**（<kbd>Ctrl</kbd>+<kbd>P</kbd>）：输入`#` 后跟类名（例如 `#Identifier`）。

![快速打开](/assets/develop/getting-started/vscode/quick-open.png)

**转到定义**（<kbd>F12</kbd>）：从源代码中，通过按 <kbd>Ctrl</kbd> + 单击其名称，或右键单击它并选择“转到定义”导航到类定义。

![转到定义](/assets/develop/getting-started/vscode/go-to-definition.png)

### 查找引用 {#finding-references}

你可以通过右键单击类名并单击**查找所有引用**来查找类的所有使用情况。

![查找所有引用](/assets/develop/getting-started/vscode/find-all-references.png)

::: info

如果上述函数无法按预期工作，很可能是源文件没有正确附加。 这通常可以通过清理工作区缓存来修复。

- 单击状态栏中的**显示 Java 状态菜单**按钮。

![显示 Java 状态](/assets/develop/getting-started/vscode/java-ready.png)

- 在刚刚打开的菜单中，点击\*\*清除工作区缓存...\*\*并确认操作。

![清理工作区](/assets/develop/getting-started/vscode/clear-workspace.png)

- 关闭并重新打开项目。

:::

## 查看字节码 {#viewing-bytecode}

在编写 mixins 时，查看字节码是必要的。 然而，Visual Studio Code 缺乏对字节码查看的原生支持，而添加此功能的几个扩展可能无法正常工作。

在这种情况下，你可以使用 Java 的内置 `javap` 来查看字节码。

- **定位 Minecraft JAR 的路径：**

  打开资源管理器视图，展开 **Java Projects** 部分。 展开项目树中的 **Reference Libraries** 节点，并找到名称中包含 `minecraft-` 的 JAR 文件。 右键单击该 JAR 文件并复制完整路径。

  它可能看起来像这样：

  ```:no-line-numbers
  C:/project/.gradle/loom-cache/minecraftMaven/net/minecraft/minecraft-merged-503b555a3d/1.21.8-net.fabricmc.yarn.1_21_8.1.21.8+build.1-v2/minecraft-merged-503b555a3d-1.21.8-net.fabricmc.yarn.1_21_8.1.21.8+build.1-v2.jar
  ```

![复制路径](/assets/develop/getting-started/vscode/copy-path.png)

- **运行 `javap`：**

  然后，你可以通过将上述路径作为 `cp`（类路径）并提供完全限定的类名作为最后一个参数来运行 `javap` 。

  ```sh
  javap -cp C:/project/.gradle/loom-cache/minecraftMaven/net/minecraft/minecraft-merged-503b555a3d/1.21.8-net.fabricmc.yarn.1_21_8.1.21.8+build.1-v2/minecraft-merged-503b555a3d-1.21.8-net.fabricmc.yarn.1_21_8.1.21.8+build.1-v2.jar -c -private net.minecraft.util.Identifier
  ```

  这将打印字节码到你的终端输出。

![javap](/assets/develop/getting-started/vscode/javap.png)
