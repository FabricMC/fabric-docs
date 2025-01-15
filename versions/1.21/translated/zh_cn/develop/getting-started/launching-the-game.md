---
title: 启动游戏
description: 了解如何利用各种启动配置文件在实时游戏环境中启动和调试你的模组。
authors:
  - IMB11
  - Tenneb22
---

# 启动游戏{#launching-the-game}

Fabric Loom 提供了各种启动配置文件，可以帮助你在实时游戏环境中启动以及调试你的模组。 本指南将介绍各种启动配置文件以及如何用它们来调试和在游戏中测试你的模组。

## 启动配置文件{#launch-profiles}

如果在使用 IntelliJ IDEA，那么可以从窗口右上角找到启动配置文件。 单击下拉菜单可以查看可用的启动配置文件。

应该有一个客户端和服务器配置文件，可以选择正常运行或在调试模式下运行它：

![启动配置文件](/assets/develop/getting-started/launch-profiles.png)

## Gradle 任务{#gradle-tasks}

如果使用的是命令行，则可以使用以下 Gradle 命令启动游戏：

- `./gradlew runClient` - 以客户端模式启动游戏。
- `./gradlew runServer` - 以服务器模式启动游戏。

这种方法的唯一问题是无法轻松调试代码。 如果要调试代码，则需要使用 IntelliJ IDEA 中的启动配置文件或通过你所使用的 IDE 中的 Gradle 集成。

## 热交换类{#hotswapping-classes}

在调试模式下运行游戏时，可以热交换你的类而无需重启游戏。 这对于快速测试代码的更改很有用。

但你仍然受到很大限制：

- 你无法添加或移除方法
- 你无法更改方法参数
- 你无法添加或移除字段

但是，通过使用 [JetBrains Runtime](https://github.com/JetBrains/JetBrainsRuntime)，你可以绕过大部分限制，甚至可以添加或删除类和方法。 这样大多数更改无需重启游戏即可生效。

不要忘记在 Minecraft 运行配置中的 VM 参数选项中添加以下内容：

```:no-line-numbers
-XX:+AllowEnhancedClassRedefinition
```

## 热交换 Mixin{#hotswapping-mixins}

如果正在使用 Mixin，则可以热交换 Mixin 类而无需重启游戏。 这对于快速测试 Mixin 的更改很有用。

但是你需要安装 Mixin Java 代理才能使其正常工作。

### 1. 找到 Mixin 库 Jar{#1-locate-the-mixin-library-jar}

在 IntelliJ IDEA 中，你可以在“项目”部分的“外部库”部分中找到 mixin 库 jar：

![Mixin 库](/assets/develop/getting-started/mixin-library.png)

你需要复制 jar 的“绝对路径”以供下一步使用。

### 2. 添加 `-javaagent` VM 参数{#2-add-the--javaagent-vm-argument}

在你的“Minecraft 客户端”和/或“Minecraft 服务器”运行配置中，将以下内容添加到 VM 参数选项：

```:no-line-numbers
-javaagent:"此处为 mixin 库 jar 的路径"
```

![VM 参数屏幕截图](/assets/develop/getting-started/vm-arguments.png)

现在，你应该能够在调试期间修改 mixin 方法的内容，并且无需重启游戏即可使更改生效。
