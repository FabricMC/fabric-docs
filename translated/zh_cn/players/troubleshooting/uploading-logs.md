---
title: 上传日志
description: 如何上传日志以进行疑难解答。
authors:
  - IMB11
---

在进行疑难解答时，通常需要提供日志以帮助寻找问题的原因。

## 为什么我应该上传日志？ {#why-should-i-upload-logs}

相比直接在聊天或论坛帖子中粘贴日志，上传日志能让他人更快速地协助您排查问题。 此外，您无需复制粘贴即可与他人分享日志。

部分粘贴网站还提供日志语法高亮功能，便于阅读，并可能自动屏蔽敏感信息，例如您的用户名或系统信息。

## 崩溃报告 {#crash-reports}

游戏崩溃时会自动生成崩溃报告。 崩溃报告只包含崩溃信息，不包含实际的游戏日志。 崩溃报告位于游戏目录中的 `crash-reports` 文件夹中。

有关崩溃报告的更多信息，请参阅[崩溃报告](./crash-reports)章节。

## 找到日志{#locating-logs}

本指南涵盖了官方 Minecraft 启动器（通常称为“原版启动器”）——对于第三方启动器，你应该参阅其文档。

日志位于游戏目录下的 `logs` 文件夹中，游戏目录可以在以下位置找到，具体取决于你的操作系统：

::: code-group

```text:no-line-numbers [Windows]
%appdata%\.minecraft
```

```text:no-line-numbers [macOS]
~/Library/Application Support/minecraft
```

```text:no-line-numbers [Linux]
~/.minecraft
```

:::

最新的日志文件名为 `latest.log`，先前的日志使用命名格式 `yyyy-mm-dd_number.log.gz`。

## 在线上传日志 {#uploading-logs-online}

日志可以上传到各种服务，例如：

- [Pastebin](https://pastebin.com/)
- [GitHub Gist](https://gist.github.com/)
- [mclo.gs](https://mclo.gs/)
