---
title: Installing Java on macOS
description: A step-by-step guide on how to install Java on macOS.
authors:
  - dexman545
  - ezfe
next: false
---

This guide will walk you through installing Java 21 on macOS.

The Minecraft Launcher comes with its own Java installation, so this section is only relevant if you want to use the Fabric `.jar` based installer, or if you want to use the Minecraft Server `.jar`.

## 1. Check if Java Is Already Installed {#1-check-if-java-is-already-installed}

In Terminal (located in `/Applications/Utilities/Terminal.app`) type the following, and press <kbd>Enter</kbd>:

```sh
$(/usr/libexec/java_home -v 21)/bin/java --version
```

You should see something like the following:

```text:no-line-numbers
openjdk 21.0.9 2025-10-21 LTS
OpenJDK Runtime Environment Temurin-21.0.9+10 (build 21.0.9+10-LTS)
OpenJDK 64-Bit Server VM Temurin-21.0.9+10 (build 21.0.9+10-LTS, mixed mode, sharing)
```

Notice the version number: in the example above it is `21.0.9`.

::: warning

To use Minecraft 1.21.11, you'll need at least Java 21 installed.

If this command displays any version lower than 21, you'll need to update your existing Java installation; keep reading this page.

:::

## 2. Downloading and Installing Java 21 {#2-downloading-and-installing-java}

We recommend using [Adoptium's build of OpenJDK 21](https://adoptium.net/temurin/releases?version=21&os=mac&arch=any&mode=filter):

![Temurin Java Download Page](/assets/players/installing-java/macos-download-java.png)

Make sure to select version "21 - LTS", and choose the `.PKG` installer format.
You should also choose the correct architecture depending on your system's chip:

- If you have an Apple M-series chip, choose `aarch64` (the default)
- If you have an Intel chip, choose `x64`
- Follow these [instructions to know which chip is in your Mac](https://support.apple.com/en-us/116943)

After downloading the `.pkg` installer, open it and follow the prompts:

![Temurin Java Installer](/assets/players/installing-java/macos-installer.png)

You will have to enter your administrator password to complete the installation:

![macOS Password Prompt](/assets/players/installing-java/macos-password-prompt.png)

### Using Homebrew {#using-homebrew}

If you already have [Homebrew](https://brew.sh) installed, you can install Java 21 using `brew` instead:

```sh
brew install --cask temurin@21
```

## 3. Verify That Java 21 Is Installed {#3-verify-that-java-is-installed}

Once the installation is complete, you can verify that Java 21 is active by opening Terminal again and typing `$(/usr/libexec/java_home -v 21)/bin/java --version`.

If the command succeeds, you should see something like this:

```text:no-line-numbers
openjdk 21.0.9 2025-10-21 LTS
OpenJDK Runtime Environment Temurin-21.0.9+10 (build 21.0.9+10-LTS)
OpenJDK 64-Bit Server VM Temurin-21.0.9+10 (build 21.0.9+10-LTS, mixed mode, sharing)
```

If you encounter any issues, feel free to ask for help in the [Fabric Discord](https://discord.fabricmc.net/) in the `#player-support` channel.
