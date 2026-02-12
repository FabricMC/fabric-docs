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

## 1. 检查 Java 是否已经安装{#1-check-if-java-is-already-installed}

要检查 Java 是否已安装，你首先必须打开命令提示符。

命令提示符可以通过按下<kbd>Windows</kbd>+<kbd>R</kbd>再在弹出的窗口中输入`cmd.exe`来打开。

![Windows运行对话框中的「cmd.exe」](/assets/players/installing-java/windows-run-dialog.png)

打开命令提示符后，输入 `java -version` 并按下 <kbd>Enter</kbd> 键。

如果命令成功运行，你会看到类似这样的内容。 如果命令运行失败，请继续进行下一步。

![命令提示符中输入了「java -version」](/assets/players/installing-java/windows-java-version.png)

::: warning

你至少需要Java 21来游玩Minecraft1.21.11。

如果这条命令输出的版本低于21，你就需要更新你已安装的Java；请继续阅读这篇文章。

:::

## 2. 下载Java 21安装器 {#2-download-the-java-installer}

为了安装Java21，你需要从[Adoptium](https://adoptium.net/temurin/releases?version=21&os=windows&arch=any&mode=filter)下载安装器。

你将会下载的是`Windows Installer (.msi)`版本：

![有Windows安装器（.msi）高亮的Adoptium下载页面](/assets/players/installing-java/windows-download-java.png)

如果你用的是32位操作系统，你应当选择`x86`，或`64`如果你用的是64位操作系统。

现代计算机基本上都是64位操作系统。 如果你对此不清楚，试着下载64位的版本。

## 3. 运行安装器！ {#3-run-the-installer}

跟随安装器中的步骤来安装Java 21。 当你到达这一页时，你应当设置以下内容成“整个功能将安装在本地硬盘上”：

- `设置或重写JAVA_HOME变量`——这会被添加到你的PATH中。
- `JavaSoft (Oracle) registry keys`

![高亮了“设置或重写JAVA_HOME变量”和“JavaSoft (Oracle) registry keys”的Java 21安装器](/assets/players/installing-java/windows-wizard-screenshot.png)

做完这些后，你可以按下`Next`然后继续你的安装。

::: warning

Windows不总是会告诉其他应用Java已经被安装，直到你重启你的计算机。

**在继续前请确保你已经重启了你的计算机！**

:::

## 4. 确认Java 21已经被安装 {#4-verify-that-java-is-installed}

安装一完成，你就可以通过打开命令提示符并键入`java -version`来确认Java已经被安装。

如果命令成功完成，你将会看到一些之前展示过的输出，可是Java版本会像这样：

![命令提示符中输入了「java -version」](/assets/players/installing-java/windows-java-version.png)

如果你有任何问题，欢迎在[Fabric Discord](https://discord.fabricmc.net/)的`#player-support`频道中提问。
