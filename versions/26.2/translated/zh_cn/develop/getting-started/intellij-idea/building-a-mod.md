---
title: 在 IntelliJ IDEA 中构建模组
description: 了解如何使用 IntelliJ IDEA 构建可供分享或在生产环境中测试的 Minecraft 模组。
authors:
  - cassiancc
  - cputnam-a11y
  - gdude2002
  - Scotsguy
prev:
  text: 在 IntelliJ IDEA 中生成源代码
  link: ./generating-sources
next:
  text: IntelliJ IDEA 提示和技巧
  link: ./tips-and-tricks
---

在 IntelliJ IDEA 中，打开右侧的 Gradle 工具窗口，于 tasks 节点下执行 `build` 任务。 生成的 JAR 文件将位于项目目录下的 `build/libs` 文件夹中。 若在非开发环境下使用，请选取文件名最短的那个 JAR 文件。

![IntelliJ IDEA 侧边栏中突出显示的构建任务](/assets/develop/getting-started/build-idea.png)

![build/libs 文件夹中突出显示的修正后文件](/assets/develop/getting-started/build-libs.png)

## 安装与分享 {#installing-and-sharing}

完成上述步骤后，该模组即可[按常规方式安装](../../../players/installing-mods)，或上传至可靠的模组托管网站，如 [CurseForge](https://www.curseforge.com/minecraft) 和 [Modrinth](https://modrinth.com/discover/mods)。
