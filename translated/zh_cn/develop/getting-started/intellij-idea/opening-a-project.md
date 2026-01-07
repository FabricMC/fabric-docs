---
title: 在 IntelliJ IDEA 中打开项目
description: 在 IntelliJ IDEA 中打开 Minecraft 模组项目的指南。
authors:
  - Cactooz
  - dicedpixels
  - IBM11
  - radstevee
  - Thomas​1034
prev:
  text: 设置 IntelliJ IDEA
  link: ./setting-up
next:
  text: 在 IntelliJ IDEA 中启动游戏
  link: ./launching-the-game
---

在启动对话框中选择你的项目。

![打开项目提示](/assets/develop/getting-started/intellij/welcome.png)

如果你已经在 IDE 中，请从 **文件** > **打开**。

![文件打开](/assets/develop/getting-started/intellij/file-open.png)

## 导入项目 {#importing-the-project}

在 IntelliJ IDEA 中打开该项目之后，IDEA 会自动加载项目的 Gradle 配置并执行必要的初始化任务。

如果收到一个关于 Gradle 构建脚本的通知，应该点击 `Import Gradle Project` 按钮以导入 Gradle 项目：

![找到 Gradle 构建脚本](/assets/develop/getting-started/intellij/gradle-build-script.png)

项目导入好之后，你可以在项目资源管理器中看到项目的文件，就能够开始开发你的模组了。
