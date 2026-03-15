---
title: 在 macOS 上安装 Java
description: 在 macOS 上安装 Java 的逐步指南。
authors:
  - dexman545
  - ezfe
next: false
---

本指南将会指引你在 macOS 上安装 Java 21。

Minecraft 启动器自带 Java 安装，因此本节仅在你想要使用 Fabric 的 `.jar` 安装程序，或想要使用 Minecraft 服务器 `.jar` 时才相关。

## 1. 检查 Java 是否已经安装 {#1-check-if-java-is-already-installed}

在终端（位于 `/Applications/Utilities/Terminal.app`）中输入以下命令，然后按 <kbd>Enter</kbd> 键：

```sh
$(/usr/libexec/java_home -v 21)/bin/java --version
```

你应该会看到类似以下内容：

```text:no-line-numbers
openjdk 21.0.9 2025-10-21 LTS
OpenJDK Runtime Environment Temurin-21.0.9+10 (build 21.0.9+10-LTS)
OpenJDK 64-Bit Server VM Temurin-21.0.9+10 (build 21.0.9+10-LTS, mixed mode, sharing)
```

请注意版本号：在上例中，版本号为 `21.0.9`。

::: warning

要使用 Minecraft 1.21.11，你至少需要安装 Java 21。

如果此命令显示的版本低于 21，则需要更新现有的 Java 安装，请继续阅读本页。

:::

## 2. 下载并安装 Java 21 {#2-downloading-and-installing-java}

我们推荐使用 [Adoptium 构建的 OpenJDK 21](https://adoptium.net/temurin/releases?version=21&os=mac&arch=any&mode=filter)：

![Temurin Java 下载页面](/assets/players/installing-java/macos-download-java.png)

请务必选择“21 - LTS”版本，并选择 `.PKG` 安装程序格式。
你还应根据你系统的芯片选择正确的架构：

- 如果你使用的是 Apple M 系列芯片，请选择 `aarch64`（默认）
- 如果你使用的是 Intel 芯片，请选择 `x64`
- 请按照此说明[了解你的 Mac 中安装的是哪种芯片](https://support.apple.com/zh-cn/116943)

下载 `.pkg` 安装程序后，打开它并按照提示操作：

![Temurin Java 安装程序](/assets/players/installing-java/macos-installer.png)

你需要输入管理员密码才能完成安装：

![macOS 密码提示](/assets/players/installing-java/macos-password-prompt.png)

### 使用 Homebrew {#using-homebrew}

如果你已经安装了 [Homebrew](https://brew.sh)，则可以改用 `brew` 安装 Java 21：

```sh
brew install --cask temurin@21
```

## 3. 验证是否已安装 Java 21 {#3-verify-that-java-is-installed}

安装完成后，你可以再次打开终端并输入 `$(/usr/libexec/java_home -v 21)/bin/java --version` 来验证 Java 21 是否已安装。

如果命令执行成功，你应该会看到类似这样的输出：

```text:no-line-numbers
openjdk 21.0.9 2025-10-21 LTS
OpenJDK Runtime Environment Temurin-21.0.9+10 (build 21.0.9+10-LTS)
OpenJDK 64-Bit Server VM Temurin-21.0.9+10 (build 21.0.9+10-LTS, mixed mode, sharing)
```

如有任何问题，欢迎在 [Fabric Discord](https://discord.fabricmc.net/) 的 `#player-support` 频道中提问。
