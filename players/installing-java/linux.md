---
title: Installing Java on Linux
description: A step by step guide on how to install Java on Linux.
authors:
  - IMB11
---

# Installing Java on Linux

This guide will walk you through installing Java 17 on Linux.

## 1. Verify if Java is already installed.

Open a terminal, type `java -version`, and press <kbd>Enter</kbd>.

![Terminal with "java -version" typed in.](/assets/players/installing-java/linux-java-version.png)

::: warning
To use the majority of modern Minecraft versions, you'll need at least Java 17 installed. If this command displays any version lower than 17, you'll need to update your existing java installation.
:::

## 2. Downloading and Installing Java 17

We recommend using OpenJDK 17, which is available for most Linux distributions.

### Arch Linux

::: info
For more information on installing Java on Arch Linux, see the [Arch Linux Wiki](https://wiki.archlinux.org/title/Java).
:::

You can install the latest JRE from the official repositories:

```bash
sudo pacman -S jre-openjdk
```

If you're running a server without the need for a graphical interface, you can install the headless version instead:

```bash
sudo pacman -S jre-openjdk-headless
```

If you plan to develop mods, you'll need the JDK instead:

```bash
sudo pacman -S jdk-openjdk
```

### Debian/Ubuntu

You can install java 17 using `apt` with the following commands:

```bash
sudo apt update
sudo apt install openjdk-17-jdk
```

### Fedora

You can install java 17 using `dnf` with the following commands:

```bash
sudo dnf install java-17-openjdk
```

If you don't need a graphical interface, you can install the headless version instead:

```bash
sudo dnf install java-17-openjdk-headless
```

If you plan to develop mods, you'll need the JDK instead:

```bash
sudo dnf install java-17-openjdk-devel
```

### Other Linux Distributions

If your distribution isn't listed above, you can download the latest JRE from [AdoptOpenJDK](https://adoptium.net/en-GB/temurin.html)

You should refer to an alternative guide for your distribution if you plan to develop mods.

## 3. Verify that Java 17 is installed.

Once the installation is complete, you can verify that Java 17 is installed by opening a terminal and typing `java -version`.

If the command runs successfully, you will see something like shown before, where the java version is displayed:

![Terminal with "java -version" typed in.](/assets/players/installing-java/linux-java-version.png)
