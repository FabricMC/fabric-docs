---
title: 崩溃报告
description: 学习如何处理崩溃报告以及如何阅读。
authors:
  - IMB11
---

<!---->

::: tip

如果你有任何关于找到游戏崩溃原因的问题，你可以在[Fabric Discord](https://discord.fabricmc.net/)的`#player-support`或`#server-admin-support`频道中询问。

:::

崩溃报告对于解决你的游戏或服务器问题非常重要。 它们含有很多关于崩溃的信息，可以帮助你找到崩溃的原因。

## 找到崩溃报告 {#finding-crash-reports}

崩溃报告被存储在你的游戏根目录下的`crash-reports`文件夹内。 如果崩溃的是一个服务器，它们会被存储在服务器所在目录下的`crash-reports`文件夹内。

对于第三方启动器，你应该参考它们的文档来了解如何找到崩溃报告。

崩溃报告可以在这些地方被找到：

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

崩溃报告非常长，且有时在阅读时非常令人疑惑。 可是，它们含有不少关于崩溃的信息，它们也可以帮助你找到崩溃的原因。

对于这篇教程而言，我们会使用[这个崩溃报告](/assets/players/crash-report-example.log)作为示例。

:::details 展示崩溃报告

<<< @/public/assets/players/crash-report-example.log

:::

### 崩溃报告的章节 {#crash-report-sections}

崩溃报告有一些章节，被标题分隔：

- `---- Minecraft Crash Report ----`，报告的总结。 这个章节会包含导致崩溃的主要原因，崩溃的时间，和有关的堆栈跟踪。 这是崩溃报告的最重要的章节，因为堆栈跟踪经常会告诉你导致崩溃的Mod。
- `-- Last Reload --`，这个章节并不是那么有用，除非崩溃发生在重新加载资源（<kbd>F3</kbd>+<kbd>T</kbd>）时。 这个章节会包含上次重载资源的时间和在重载资源过程中发生的有关错误的堆栈跟踪。 这些错误多半是由资源包产生的，除非在游戏中导致了问题，这些错误是可以被忽略的。
- `-- System Details --`，这个章节包含你的系统信息，如操作系统、Java版和给游戏分配的内存。 它可以判断你是否使用了正确的Java版本，和你是否分配了足够的内存给游戏。
  - 在这个章节，Fabric会添加一行`Fabric Mods:`，可以告诉你所有你安装的Mod。 这可以很好地判断存不存在冲突的Mod。

### 解决崩溃 {#breaking-down-the-crash-report}

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

Mixin 是一种允许模组在无需修改游戏源代码的情况下对游戏进行修改的方式。 许多模组都使用了 mixin，它对于模组开发者来说是非常强大的工具。

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
