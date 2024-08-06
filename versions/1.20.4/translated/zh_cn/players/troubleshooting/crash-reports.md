---
title: 崩溃报告
description: 学习如何处理崩溃报告以及如何阅读它们。
authors:
  - IMB11

search: false
---

# 崩溃报告

:::tip
如果你在查找崩溃原因时遇到困难，可以在 [Fabric Discord](https://discord.gg/v6v4pMv) 的 `#player-support` 或 `#server-admin-support` 频道中寻求帮助。
:::

崩溃报告是解决游戏或服务器问题的重要部分。 它包含大量关于崩溃的信息，可以帮助你找到崩溃的原因。 它包含大量关于崩溃的信息，可以帮助你找到崩溃的原因。

## 寻找崩溃报告

它们位于游戏根目录中的 `crash-reports` 文件夹中。 如果是服务器，它们会存储在服务器实例根目录下的 `crash-reports` 文件夹中。 如果是服务器，它们会存储在服务器实例根目录下的 `crash-reports` 文件夹中。

对于第三方启动器，你应该参考其文档，了解在哪里可以找到崩溃报告。

崩溃报告可能在以下位置被找到：

::: code-group

```:no-line-numbers [Windows]
%appdata%\.minecraft\crash-reports
```

```:no-line-numbers [macOS]
~/Library/Application Support/minecraft/crash-reports
```

```:no-line-numbers [Linux]
~/.minecraft/crash-reports
```

:::

## 阅读崩溃报告

崩溃报告的篇幅很长，读起来十分费解。 但无论怎样，它包含着大量关于崩溃的信息，可以帮助你找到崩溃的原因，因此你不得不阅读它。 但无论怎样，它包含着大量关于崩溃的信息，可以帮助你找到崩溃的原因，因此你不得不阅读它。

在本指南中，我们将以 [该崩溃报告](https://github.com/FabricMC/fabric-docs/blob/main/public/assets/players/crash-report-example.txt) 为例。

### 崩溃报告的各部分

崩溃报告由几个部分组成，每个部分都用标题来分隔：

- `---- Minecraft Crash Report ----`，报告摘要部分。 该部分包含导致崩溃的主要错误原因、发生时间和相关堆栈跟踪。 这是崩溃报告中最重要的部分，因为堆栈跟踪通常会提及到导致崩溃的模组。 该部分包含导致崩溃的主要错误原因、发生时间和相关堆栈跟踪。 这是崩溃报告中最重要的部分，因为堆栈跟踪通常会提及到导致崩溃的模组。
- `-- Last Reload --`，除非崩溃发生在资源重载过程中 (<kbd>F3</kbd>+<kbd>T</kbd>) ，否则这部分并没有什么用处。 该部分将包含上次重载的发生时间，以及重载过程中出现的任何错误的相关堆栈跟踪。 这些错误通常是由资源包引起的，可以忽略不计，除非它们导致游戏出现问题。
- `-- System Details --`，本部分包含有关设备的信息，如操作系统、Java 版本和分配给游戏的内存量。 该部分有助于确定你使用的 Java 版本是否正确，以及是否为游戏分配了足够的内存。 该部分有助于确定你使用的 Java 版本是否正确，以及是否为游戏分配了足够的内存。
  - 在此部分中，Fabric 将插入一些自定义内容，其标题为 `Fabric Mods:`，后面是所有已安装模组的列表。 该部分有助于判断模组之间是否有可能发生冲突。 该部分有助于判断模组之间是否有可能发生冲突。

### 分解崩溃报告

既然我们已经知道崩溃报告的每个部分代表着什么，我们就可以开始分解崩溃报告并找出崩溃原因。

利用上面链接的崩溃示例，我们可以分析崩溃报告并找到崩溃原因，包括导致崩溃的模组。

`-- Last Reload --`，除非崩溃发生在资源重载过程中 (<kbd>F3</kbd>+<kbd>T</kbd>) ，否则这部分并没有什么用处。 该部分将包含上次重载的发生时间，以及重载过程中出现的任何错误的相关堆栈跟踪。 这些错误通常是由资源包引起的，可以忽略不计，除非它们导致游戏出现问题。 在这种情况下，`---- Minecraft Crash Report ----` 部分中的堆栈跟踪最为重要，因为它包含导致崩溃的主要错误。 在这里，错误为`java.lang.NullPointerException: Cannot invoke "net.minecraft.class_2248.method_9539()" because "net.minecraft.class_2248.field_10540" is null`.

由于堆栈跟踪中提到了大量的模组，因此很难指认崩溃"凶手"，不过，首先要做的是查找导致崩溃的部分。

```:no-line-numbers
at snownee.snow.block.ShapeCaches.get(ShapeCaches.java:51)
at snownee.snow.block.SnowWallBlock.method_9549(SnowWallBlock.java:26) // [!code focus]
...
at me.jellysquid.mods.sodium.client.render.chunk.compile.pipeline.BlockOcclusionCache.shouldDrawSide(BlockOcclusionCache.java:52)
at link.infra.indium.renderer.render.TerrainBlockRenderInfo.shouldDrawFaceInner(TerrainBlockRenderInfo.java:31)
...
```

在这里，导致崩溃的模组是 `snownee`，因为它是堆栈跟踪中提到的第一个模组。

不过，从堆栈追踪中提到的模组数量来看，这可能意味着模组之间存在一些兼容性问题，导致崩溃的模组可能并不是出错的模组。 在这种情况下，最好向模组作者报告崩溃情况，让他们调查崩溃原因。 在这种情况下，最好向模组作者报告崩溃情况，让他们调查崩溃原因。

## Mixin崩溃

:::info
Mixin 是一种修改游戏的方式，使模组无需破坏性的直接修改游戏的源代码。 它被许多模组使用，对于开发者来说是一款非常强大的工具。 它被许多模组使用，对于开发者来说是一款非常强大的工具。
:::

当有 Mixin 引起的崩溃时，通常会在堆栈跟踪中提到该 Mixin 类以及该 Mixin 类修改的类。

方法 Mixin 的标识 `modid$handlerName` 将被包含在堆栈跟踪中，其中 `modid` 是模组的 ID，而 `handlerName` 是 Mixin 处理部分的名称。

```:no-line-numbers
... net.minecraft.class_2248.method_3821$$$modid$handlerName() ... // [!code focus]
```

你可以使用此信息找到导致崩溃的模组，并向模组作者报告崩溃情况。

## 如何处理崩溃报告

处理崩溃报告的最佳方法是将其上传到在线粘贴板网站，然后在问题追踪处或通过某种联系方式 (Discord 等) 向修改器作者提供崩溃报告网站链接。

这可以让模组作者调查崩溃原因、重现崩溃状况并解决导致崩溃的问题。

常用的崩溃报告粘贴网站有：

- [GitHub Gist](https://gist.github.com/)
- [Pastebin](https://pastebin.com/)
- [mclo.gs](https://mclo.gs/)
