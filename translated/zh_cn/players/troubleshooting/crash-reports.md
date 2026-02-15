---
title: 崩溃报告
description: 学习如何处理崩溃报告以及如何阅读。
authors:
  - IMB11
---

<!---->

::: tip

如果在查找崩溃原因时遇到任何困难，可以在 [Fabric Discord](https://discord.fabricmc.net/) 的 `#player-support` 或 `#server-admin-support` 频道中寻求帮助。

:::

崩溃报告是解决游戏或服务器问题的重要部分， 包含大量关于崩溃的信息，可以帮助你找到崩溃的原因。

## 寻找崩溃报告{#finding-crash-reports}

崩溃报告存储在游戏目录中的 `crash-reports` 文件夹中。 如果是服务器，则存储在服务器目录中的 `crash-reports` 文件夹中。

对于第三方启动器，你应该参考其文档，了解在哪里可以找到崩溃报告。

以下位置能找到崩溃报告：

::: code-group

```text:no-line-numbers [Windows]
%appdata%\.minecraft\crash-reports
```

```text:no-line-numbers [macOS]
~/Library/Application Support/minecraft/crash-reports
```

```text:no-line-numbers [Linux]
~/.minecraft/crash-reports
```

:::

## 阅读崩溃报告 {#reading-crash-reports}

崩溃报告的篇幅很长，读起来可能十分费解， 然而包含大量关于崩溃的信息，可以帮助你找到崩溃的原因。

在本指南中，我们将使用[此崩溃报告](/assets/players/crash-report-example.log)。

:::details 显示崩溃报告

<<< @/public/assets/players/crash-report-example.log

:::

### 崩溃报告的章节 {#crash-report-sections}

崩溃报告有一些章节，用标题分隔：

- `---- Minecraft Crash Report ----`，报告的摘要。 该部分包含导致崩溃的主要错误原因、发生时间和相关堆栈跟踪。 这是崩溃报告的最重要的章节，因为堆栈跟踪经常会提及你导致崩溃的模组。
- `-- Last Reload --`，这个章节并不是那么有用，除非崩溃发生在重新加载资源（<kbd>F3</kbd>+<kbd>T</kbd>）时。 这个章节会包含上次重载资源的时间和在重载资源过程中发生的有关错误的堆栈跟踪。 这些错误多半是由资源包产生的，除非在游戏中导致了问题，这些错误是可以被忽略的。
- `-- System Details --`，本部分包含有关系统的信息，如操作系统、Java 版本和分配给游戏的内存量。 该部分有助于确定你使用的 Java 版本是否正确，以及是否为游戏分配了足够的内存。
  - 在此部分中，Fabric 将插入一些自定义内容，其标题为 `Fabric Mods:`，后面是所有已安装模组的列表。 该部分有助于判断模组之间是否可能已发生冲突。

### 分解崩溃报告{#breaking-down-the-crash-report}

既然已经知道崩溃报告的每个部分是什么，就可以开始分解崩溃报告并找出崩溃原因。

利用上面链接的崩溃示例，我们可以分析崩溃报告并找到崩溃原因，包括导致崩溃的模组。

在这个情形中，`---- Minecraft Crash Report ----` 部分中的堆栈跟踪最重要，因为包含导致崩溃的主要错误。

:::details 显示错误详情

<<< @/public/assets/players/crash-report-example.log{7}

:::

堆栈跟踪中提到了大量模组，因此很难指出原因，不过，首先要做的是查找导致崩溃的模组。

在这里，导致崩溃的模组是 `snownee`，因为它是堆栈跟踪中提到的第一个模组。

不过，考虑到提到的模组数量之多，可能意味着模组之间存在某些兼容性问题；导致崩溃的模组并不一定就是出错的那个。 在这种情况下，最好向模组作者报告崩溃情况，让他们调查崩溃原因。

## Mixin 崩溃 {#mixin-crashes}

::: info

Mixin 是一种允许模组在无需修改游戏源代码的情况下对游戏进行修改的方式。 许多模组都用了 mixin，这对于开发者来说是非常强大的工具。

:::

当 mixin 崩溃时，堆栈跟踪中通常会列出该 mixin 及其修改的类。

方法 mixin 会在堆栈跟踪中包含 `mod-id$handlerName`，其中 `mod-id` 是模组的 ID，`handlerName` 是 mixin 处理器的名称。

```text:no-line-numbers
... net.minecraft.class_2248.method_3821$$$mod-id$handlerName() ... // [!code focus]
```

你可以使用此信息找到导致崩溃的模组，并向模组作者报告崩溃情况。

## 如何处理崩溃报告 {#what-to-do-with-crash-reports}

处理崩溃报告的最佳做法是将其上传至在线剪贴板网站，然后将链接分享给模组作者，可以通过他们的问题追踪器，也可以通过某种沟通渠道（如 Discord 等）。

这可以让模组作者调查崩溃、复现崩溃并解决导致崩溃的问题。

经常用于分享崩溃报告的常用剪贴板网站有：

- [GitHub Gist](https://gist.github.com/)
- [mclo.gs](https://mclo.gs/)
- [Pastebin](https://pastebin.com/)
