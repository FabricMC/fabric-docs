---
title: 崩溃报告
description: 学习如何处理崩溃报告以及如何阅读。
authors:
  - IMB11
---

# 崩溃报告{#crash-reports}

:::tip
如果在查找崩溃原因时遇到任何困难，可以在 [Fabric Discord](https://discord.gg/v6v4pMv) 的 `#player-support` 或 `#server-admin-support` 频道中寻求帮助。
:::

崩溃报告是解决游戏或服务器问题的重要部分， 包含大量关于崩溃的信息，可以帮助你找到崩溃的原因。

## 寻找崩溃报告{#finding-crash-reports}

崩溃报告存储在游戏目录中的 `crash-reports` 文件夹中。 如果是服务器，则存储在服务器目录中的 `crash-reports` 文件夹中。

对于第三方启动器，你应该参考其文档，了解在哪里可以找到崩溃报告。

以下位置能找到崩溃报告：

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

## 阅读崩溃报告{#reading-crash-reports}

崩溃报告的篇幅很长，读起来可能十分费解， 然而包含大量关于崩溃的信息，可以帮助你找到崩溃的原因。

在本指南中，我们将以 [该崩溃报告](https://github.com/FabricMC/fabric-docs/blob/main/public/assets/players/crash-report-example.txt) 为例。

:::details 显示崩溃报告

<<< @/public/assets/players/crash-report-example.txt{log}

:::

### 崩溃报告的各部分{#crash-report-sections}

崩溃报告由几个部分组成，每个部分用标题分隔：

- `---- Minecraft Crash Report ----`，报告的摘要。 该部分包含导致崩溃的主要错误原因、发生时间和相关堆栈跟踪。 这是崩溃报告中最重要的部分，因为堆栈跟踪通常会提及到导致崩溃的模组。
- `-- Last Reload --`，这部分不太有用，除非崩溃发生在资源重载过程中（<kbd>F3</kbd>+<kbd>T</kbd>）。 该部分将包含上次重载的发生时间，以及重载过程中出现的任何错误的相关堆栈跟踪。 这些错误通常是由资源包引起的，可以忽略，除非是这些资源包导致的游戏出现问题。
- `-- System Details --`，本部分包含有关系统的信息，如操作系统、Java 版本和分配给游戏的内存量。 该部分有助于确定你使用的 Java 版本是否正确，以及是否为游戏分配了足够的内存。
  - 在此部分中，Fabric 将插入一些自定义内容，其标题为 `Fabric Mods:`，后面是所有已安装模组的列表。 该部分有助于判断模组之间是否可能已发生冲突。

### 分解崩溃报告{#breaking-down-the-crash-report}

既然已经知道崩溃报告的每个部分是什么，就可以开始分解崩溃报告并找出崩溃原因。

利用上面链接的崩溃示例，我们可以分析崩溃报告并找到崩溃原因，包括导致崩溃的模组。

在这个情形中，`---- Minecraft Crash Report ---- ` 部分中的堆栈跟踪最重要，因为包含导致崩溃的主要错误。 在这里，错误为`java.lang.NullPointerException: Cannot invoke "net.minecraft.class_2248.method_9539()" because "net.minecraft.class_2248.field_10540" is null`.

堆栈跟踪中提到了大量模组，因此很难指出原因，不过，首先要做的是查找导致崩溃的模组。

<!-- TODO: show part of this file -->

<<< @/public/assets/players/crash-report-example.txt{8-9,14-15 log}

在这里，导致崩溃的模组是 `snownee`，因为它是堆栈跟踪中提到的第一个模组。

不过，提到的模组有很多，可能意味着模组之间存在一些兼容性问题，导致崩溃的模组可能并不是出错的模组。 在这种情况下，最好向模组作者报告崩溃情况，让他们调查崩溃原因。

## Mixin 崩溃{#mixin-crashes}

:::info
Mixin 是一种修改游戏而无需修改游戏的源代码的方式。 许多模组都用了 mixin，这对于开发者来说是非常强大的工具。
:::

当有 mixin 崩溃时，通常会在堆栈跟踪中提到该 mixin 类以及该 mixin 类修改的类。

方法 mixin 会在堆栈跟踪中包含 `modid$handlerName`，其中 `modid` 是模组的 ID，`handlerName` 是 mixin 处理器的名称。

```:no-line-numbers
... net.minecraft.class_2248.method_3821$$$modid$handlerName() ... // [!code focus]
```

你可以使用此信息找到导致崩溃的模组，并向模组作者报告崩溃情况。

## 如何处理崩溃报告{#what-to-do-with-crash-reports}

处理崩溃报告的最佳方法是将其上传到在线粘贴板网站，然后将链接分享给模组作者，可以是在他们的问题追踪器，或通过某种联系方式（Discord 等）。

这可以让模组作者调查崩溃、复现崩溃并解决导致崩溃的问题。

常用的崩溃报告粘贴网站有：

- [GitHub Gist](https://gist.github.com/)
- [mclo.gs](https://mclo.gs/)
- [Pastebin](https://pastebin.com/)
