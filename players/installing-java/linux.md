---
title: Installing Java on Linux
description: A step-by-step guide on how to install Java on Linux.
authors:
  - IMB11
next: false
---

This guide will walk you through installing Java 21 on Linux.

The Minecraft Launcher comes with its own Java installation, so this section is only relevant if you want to use the Fabric `.jar` based installer, or if you want to use the Minecraft Server `.jar`.

## 1. Check if Java Is Already Installed {#1-check-if-java-is-already-installed}

Open a terminal, type `java -version`, and press <kbd>Enter</kbd>.

![Terminal with "java -version" typed in](/assets/players/installing-java/linux-java-version.png)

::: warning

To use Minecraft 1.21.11, you'll need at least Java 21 installed.

If this command displays any version lower than 21, you'll need to update your existing Java installation; keep reading this page.

:::

## 2. Downloading and Installing Java 21 {#2-downloading-and-installing-java}

We recommend using OpenJDK 21, which is available for most Linux distributions.

### Arch Linux {#arch-linux}

::: info

For more information on installing Java on Arch Linux, see the [Arch Linux Wiki](https://wiki.archlinux.org/title/Java).

:::

You can install the latest JRE from the official repositories:

```sh
sudo pacman -S jre-openjdk
```

If you're running a server without the need for a graphical UI, you can install the headless version instead:

```sh
sudo pacman -S jre-openjdk-headless
```

If you plan to develop mods, you'll need the JDK instead:

```sh
sudo pacman -S jdk-openjdk
```

### Debian/Ubuntu {#debian-ubuntu}

You can install Java 21 using `apt` with the following commands:

```sh
sudo apt update
sudo apt install openjdk-21-jdk
```

### Fedora {#fedora}

You can install Java 21 using `dnf` with the following commands:

```sh
sudo dnf install java-21-openjdk
```

If you don't need a graphical UI, you can install the headless version instead:

```sh
sudo dnf install java-21-openjdk-headless
```

If you plan to develop mods, you'll need the JDK instead:

```sh
sudo dnf install java-21-openjdk-devel
```

### Other Linux Distributions {#other-linux-distributions}

If your distribution isn't listed above, you can download the latest JRE from [Adoptium](https://adoptium.net/temurin/)

You should refer to an alternative guide for your distribution if you plan to develop mods.

## 3. Verify That Java 21 Is Installed {#3-verify-that-java-is-installed}

Once the installation is complete, you can verify that Java 21 is installed by opening a terminal and typing `java -version`.

If the command runs successfully, you will see something like shown before, where the Java version is displayed:

![Terminal with "java -version" typed in](/assets/players/installing-java/linux-java-version.png)
