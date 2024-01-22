---
title: Uploading Logs
description: How to upload logs for troubleshooting.
authors:
  - IMB11
---

# Uploading Logs

When troubleshooting issues, it is often necessary to provide logs to help identify the cause of the issue. 

## Why should I upload logs?

Uploading logs allows others to help you troubleshoot your issues much quicker than simply pasting the logs into a chat or forum post. It also allows you to share your logs with others without having to copy and paste them.

Some paste sites also provide syntax highlighting for logs, which makes them easier to read, and may censor sensitive information, such as your username, or system information.

## Crash Reports

Crash reports are automatically generated when the game crashes. They only contain crash information and not the actual logs of the game. They are located in the `crash-reports` folder in the game directory.

For more information on crash reports, see [Crash Reports](./crash-reports.md).

## Locating Logs

This guide covers the official Minecraft Launcher (commonly referred to as the "vanilla launcher") - for third party launchers, you should consult their documentation.

Logs are located in the `logs` folder in the game directory, the game directory can be found in the following locations depending on your operating system:

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

The latest log is called `latest.log`, and previous logs use the naming pattern `yyy-mm-dd_number.log.gz`.

## Uploading Logs

Logs can be uploaded to a variety of services, such as:

- [Pastebin](https://pastebin.com/)
- [GitHub Gist](https://gist.github.com/)
- [MCLogs](https://mclo.gs/)