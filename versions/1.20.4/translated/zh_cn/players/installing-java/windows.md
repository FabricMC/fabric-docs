---
title: 在 Windows 上安装 Java
description: 在 Windows 上安装 Java 的逐步指南。
authors:
  - IMB11

search: false
---

# 在 Windows 上安装 Java

这个指南将会指引您在 Windows 上安装 Java 17。

Minecraft 启动器附带了自己的 Java 安装，因此这部分只在你想使用 Fabric 的 `.jar` 安装程序，或者你想使用 Minecraft 服务器的 `.jar` 时有关。

## 1. 检查 Java 是否已被安装

要检查 Java 是否已安装，你首先必须打开命令提示符。

你可以通过按下 <kbd>Win</kbd> + <kbd>R</kbd> 并在出现的对话框中输入 `cmd.exe` 来实现它。

![Windows运行对话框中的「cmd.exe」](/assets/players/installing-java/windows-run-dialog.png)

打开命令提示符后，输入 `java -version` 并按下 <kbd>Enter</kbd> 键。

如果命令成功运行，你会看到类似这样的内容。 如果命令运行失败，请继续进行下一步。

![命令提示符中输入了「java -version」](/assets/players/installing-java/windows-java-version.png)

:::warning
要使用较新的 Minecraft (1.19.x 及以上) 版本，你至少需要安装版本 ≥ 17 的 Java。 如果运行该命令后显示 Java 版本低于 17，你需要更新设备上现有 Java。
:::

## 2. 下载 Java 17 安装程序

要安装 Java 17，你需要从 [Adoptium](https://adoptium.net/en-GB/temurin/releases/?os=windows&package=jdk&version=17) 下载安装程序。

你需要下载 `Windows Installer (.msi)` 版本：

![Adoptium 下载页面，使用了 Windows 安装程序 (.msi)](/assets/players/installing-java/windows-download-java.png)

如果你有 32 位操作系统，应该选择 `x86`；如果你有 64 位操作系统，则应该选择 `x64`。

现代大多数电脑都运行 64 位操作系统。 如果你不确定，请尝试使用 64 位的下载。

## 3. 运行安装程序！

按照安装程序的步骤安装 Java 17。 当你到达这个页面时，你应该将以下功能设置为「整个功能将安装在本机硬盘上」：

- `Set JAVA_HOME environment variable` - 这将加入到你的PATH中。
- `JavaSoft (Oracle) registry keys`

![Java 17 安装程序，具有「Set JAVA_HOME variable」和「JavaSoft (Oracle) registry keys」](/assets/players/installing-java/windows-wizard-screenshot.png)

完成后，你可以按 `下一步` 继续安装。

## 4. 检查 Java 17 是否已被正确安装

安装完成后，您可以打开终端并输入 `java -version` 来验证 Java 17 是否已安装。

如果命令成功执行，你可以看到类似前文所示的内容，Java 版本被显示出来：

![命令提示符中输入了「java -version」](/assets/players/installing-java/windows-java-version.png)

---

如果遇到任何问题，你可以在 [Fabric Discord](https://discord.gg/v6v4pMv) 的 `#player-support` 频道中寻求帮助。
