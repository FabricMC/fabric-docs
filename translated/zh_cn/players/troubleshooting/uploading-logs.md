---
title: 上传日志
description: 如何上传日志以进行疑难解答。
authors:
  - IMB11
---

# 上传日志{#uploading-logs}

在进行疑难解答时，通常需要提供日志以帮助寻找问题的原因。

## 为什么我应该上传日志？ {#why-should-i-upload-logs}

上传日志可以让其他人更快地帮助您疑难解答，而不是简单地将日志粘贴到聊天或论坛帖子中， 还允许你与其他人分享你的日志，而无需复制和粘贴。

有些粘贴网站还为日志提供语法高亮，使日志更容易阅读，但可能会审查敏感信息，如用户名或系统信息。

## 崩溃报告{#crash-reports}

游戏崩溃时会自动生成崩溃报告。 崩溃报告只包含崩溃信息，不包含游戏的实际日志。 崩溃报告位于游戏目录中的 `crash-reports` 文件夹中。

有关崩溃报告的更多信息，请参阅[崩溃报告](./crash-reports)。

## 找到日志{#locating-logs}

本指南涵盖了官方 Minecraft 启动器（通常称为“原版启动器”），对于第三方启动器，你应该参阅其文档。

日志位于游戏目录下的 `logs` 文件夹中，游戏目录可以在以下位置找到，具体取决于你的操作系统：

::: code-group

```:no-line-numbers [Windows]
%appdata%\.minecraft
```

```:no-line-numbers [macOS]
~/Library/Application Support/minecraft
```

```:no-line-numbers [Linux]
~/.minecraft
```

:::

最新的日志文件名为 `latest.log`，先前的日志使用命名格式 `yyyy-mm-dd_number.log.gz`。

## 在线上传日志{#uploading-logs-online}

日志可以上传到各种服务，例如：

- [Pastebin](https://pastebin.com/)
- [GitHub Gist](https://gist.github.com/)
- [mclo.gs](https://mclo.gs/)
