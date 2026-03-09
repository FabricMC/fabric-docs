---
title: 在 Windows 上安装 Java
description: 在 Windows 上安装 Java 的逐步指南。
authors:
  - IMB11
  - skycatminepokie
next: false
---

这个指南将会指引您在 Windows 上安装 Java 21。

Minecraft 启动器附带了自己的 Java 安装，因此这部分只在你想使用 Fabric 的 `.jar` 安装器，或者你想使用 Minecraft 服务器的 `.jar` 时有关。

## 1. 检查 Java 是否已经安装 {#1-check-if-java-is-already-installed}

要检查 Java 是否已安装，你首先必须打开命令提示符。

命令提示符可以通过按下 <kbd>Windows</kbd>+<kbd>R</kbd> 再在弹出的窗口中输入 `cmd.exe` 来打开。

![Windows 运行对话框中的“cmd.exe”](/assets/players/installing-java/windows-run-dialog.png)

打开命令提示符后，输入 `java -version` 并按下 <kbd>Enter</kbd> 键。

如果命令成功运行，你会看到类似这样的内容。 如果命令运行失败，请继续进行下一步。

![输入了“java -version”的命令提示符](/assets/players/installing-java/windows-java-version.png)

::: warning

要使用 Minecraft 1.21.11，至少需要安装 Java 21。

如果此命令显示的版本低于 21，则需要更新现有的 Java 安装，请继续阅读本页。

:::

## 2. 下载 Java 21 安装程序 {#2-download-the-java-installer}

要安装 Java 21，你需要从 [Adoptium](https://adoptium.net/temurin/releases?version=21&os=windows&arch=any&mode=filter) 下载安装程序。

你需要下载 `Windows Installer (.msi)` 版本：

![Adoptium 下载页面，高亮标出了 Windows 安装程序 (.msi)](/assets/players/installing-java/windows-download-java.png)

如果你使用的是 32 位操作系统，请选择 `x86`；如果你使用的是 64 位操作系统，请选择 `x64`。

大多数现代电脑都运行 64 位操作系统。 如果你不确定，试着下载 64 位的版本。

## 3. 运行安装器！ {#3-run-the-installer}

按照安装程序的步骤安装 Java 21。 到达这一页时，应该将以下功能设置为“整个功能将安装在本地硬盘上”：

- `设置或重写 JAVA_HOME 变量`——这会被添加到你的 PATH 中。
- `JavaSoft (Oracle) registry keys`

![高亮了“设置或重写 JAVA_HOME 变量”和“JavaSoft (Oracle) registry keys”的 Java 21 安装器](/assets/players/installing-java/windows-wizard-screenshot.png)

做完这些后，你可以点击 `下一步` 并继续安装。

::: warning

在你重启计算机之前，Windows 可能不会将 Java 已安装这一信息告知其他程序。

**继续操作前，请务必重启计算机！**

:::

## 4. 验证是否已安装 Java 21{#4-verify-that-java-is-installed}

安装完成后，你可以打开命令提示符并输入 `java -version` 来验证 Java 21 是否已安装。

若命令执行成功，你将看到与前文类似的界面，其中会显示 Java 版本：

![输入了“java -version”的命令提示符](/assets/players/installing-java/windows-java-version.png)

如有任何问题，欢迎在 [Fabric Discord](https://discord.fabricmc.net/) 的 `#player-support` 频道中提问。
