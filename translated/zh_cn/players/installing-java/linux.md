---
title: 在 Linux 上安装 Java
description: 在 Linux 上安装 Java 的逐步指南。
authors:
  - IMB11
next: false
---

这个指南将会指引您在 Linux 上安装 Java 21。

Minecraft 启动器自带 Java 安装，因此本节仅在你想要使用 Fabric 的 `.jar` 安装程序，或想要使用 Minecraft 服务器 `.jar` 时才相关。

## 1. 检查 Java 是否已经安装{#1-check-if-java-is-already-installed}

打开终端输入 `java -version` 并按下 `回车`。

![输入 "java -version" 的终端](/assets/players/installing-java/linux-java-version.png)

::: warning

要使用 Minecraft 1.21.11，至少需要安装 Java 21。

如果此命令显示的版本低于 21，则需要更新现有的 Java 安装，请继续阅读本页。

:::

## 2. 下载并安装 Java 21 {#2-downloading-and-installing-java}

我们推荐使用 OpenJDK 21，可以在大多数 Linux 发行版中可用。

### Arch Linux {#arch-linux}

::: info

更多在 Arch Linux 上安装 Java 的信息可以参考 [Arch Linux 中文维基](https://wiki.archlinuxcn.org/wiki/Java)。

:::

您可以从官方仓库安装最新版 JRE：

```sh
sudo pacman -S jre-openjdk
```

如果您正在运行服务器不需要图形用户界面，您可以安装无头版本：

```sh
sudo pacman -S jre-openjdk-headless
```

如果您计划开发模组，您需要安装 JDK：

```sh
sudo pacman -S jdk-openjdk
```

### Debian/Ubuntu{#debian-ubuntu}

您可以用以下命令使用 `apt` 安装 Java 21：

```sh
sudo apt update
sudo apt install openjdk-21-jdk
```

### Fedora{#fedora}

您可以用以下命令使用 `dnf` 安装 Java 21：

```sh
sudo dnf install java-21-openjdk
```

如果不需要图形化界面，可以安装 headless 版本：

```sh
sudo dnf install java-21-openjdk-headless
```

如果您计划开发模组，您需要安装 JDK：

```sh
sudo dnf install java-21-openjdk-devel
```

### 其他 Linux 发行版{#other-linux-distributions}

如果您的发行版未在上文列出，您可以从 [Adoptium](https://adoptium.net/zh-CN/temurin/) 下载最新版 JRE

如果您计划开发模组，您应该参考您的发行版的替代指南。

## 3. 验证是否已安装 Java 21 {#3-verify-that-java-is-installed}

安装完成后，您可以打开终端并输入 `java -version` 来验证 Java 21 是否已安装。

如果命令成功执行，可以看到类似前文所示的内容，Java 版本显示出来：

![输入 "java -version" 的终端](/assets/players/installing-java/linux-java-version.png)
