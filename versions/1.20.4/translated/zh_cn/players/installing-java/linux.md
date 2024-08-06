---
title: 在 Linux 上安装 Java
description: 手把手指导如何在 Linux 上安装 Java。
authors:
  - IMB11

search: false
---

# 在 Linux 上安装 Java

这个指南将会指引您在 Linux 上安装 Java 17。

## 1. 验证 Java 是否已经安装

打开终端输入 `java -version` 并按下 `回车`。

![输入 "java -version" 的终端](/assets/players/installing-java/linux-java-version.png)

:::warning
要使用大多数现代 Minecraft 版本，您至少需要安装 Java 17。 如果此命令显示版本低于 17，您需要更新您的现有 Java。
:::

## 2. 下载并安装 Java 17

我们推荐使用 OpenJDK 17，他可以在大多数 Linux 发行版中可用。

### Arch Linux

:::info
更多在 Arch Linux 上安装 Java 的信息可以参考 [Arch Linux 中文维基](https://wiki.archlinuxcn.org/wiki/Java)。
:::

您可以从官方仓库安装最新版 JRE：

```sh
sudo pacman -S jre-openjdk
```

如果您正在运行服务器不需要图形化用户接口，您可以安装 headless 版本：

```sh
sudo pacman -S jre-openjdk-headless
```

如果您计划开发模组，您需要安装 JDK：

```sh
sudo pacman -S jdk-openjdk
```

### Debian/Ubuntu

您可以用以下命令使用 `apt` 安装 Java17：

```sh
sudo apt update
sudo apt install openjdk-17-jdk
```

### Fedora

您可以用以下命令使用 `dnf` 安装 Java17：

```sh
sudo dnf install java-17-openjdk
```

如果您不需要图形化接口，您可以安装 headless 版本：

```sh
sudo dnf install java-17-openjdk-headless
```

如果您计划开发模组，您需要安装 JDK：

```sh
sudo dnf install java-17-openjdk-devel
```

### 其他 Linux 发行版

如果您的发行版未在上文列出，您可以从 [Adoptium](https://adoptium.net/zh-CN/temurin/) 下载最新版 JRE。

如果您计划开发模组，您应该参考您的发行版的替代指南。

## 3. 验证是否已安装 Java 17

安装完成后，您可以打开终端并输入 `java -version` 来验证 Java 17 是否已安装。

如果指令成功执行，您可以看到类似前文所示的内容，java 版本被展示出来：

![输入 "java -version" 的终端](/assets/players/installing-java/linux-java-version.png)
