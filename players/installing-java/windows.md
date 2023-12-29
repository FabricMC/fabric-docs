---
title: Installing Java on Windows
description: A step by step guide on how to install Java on Windows.
---

# Installing Java on Windows

Java is required to run Minecraft and the majority of third party launchers. This guide will show you how to install Java on Windows.

## 1. Verify if Java is already installed.

To check if Java is already installed, you must first open the command prompt.

You can do this by pressing <kbd>Win</kbd> + <kbd>R</kbd> and typing `cmd.exe` into the box that appears.

![Windows Run Dialog with "cmd.exe" in the run bar.](/assets/players/installing-java/windows-run-dialog.png)

Once you have opened the command prompt, type `java -version` and press <kbd>Enter</kbd>.

If the command runs successfully, you will see something like this. If the command failed, proceed to the next step.

![Command prompt with "java -version" typed in.](/assets/players/installing-java/windows-java-version.png)

::: warning
To use the majority of modern Minecraft versions, you'll need at least Java 17 installed. If this command displays any version lower than 17, you'll need to update your existing java installation.
:::

## 2. Download the Java 17 installer.

To install Java 17, you'll need to download the installer from [AdoptOpenJDK](https://adoptium.net/en-GB/temurin/releases/?os=windows&package=jdk&version=17).

You'll want to download the `Windows Installer (.msi)` version:

![AdoptOpenJDK download page with Windows Installer (.msi) highlighted.](/assets/players/installing-java/windows-download-java.png)

You should choose `x86` if you have a 32-bit operating system, or `x64` if you have a 64-bit operating system.

The majority of modern computers will have a 64-bit operating system, but if you're unsure, you can go back to the command prompt and type `wmic os get osarchitecture` to find out.

## 3. Run the installer!

Follow the steps in the installer to install Java 17, when you reach this page, you should set the following features to “Entire feature will be installed on local hard drive”:

- `Set JAVA_HOME variable`
- `JavaSoft (Oracle) registry keys`

![Java 17 installer with "Set JAVA_HOME variable" and "JavaSoft (Oracle) registry keys" highlighted.](/assets/players/installing-java/windows-wizard-screenshot.png)

Once you've done that, you can click `Next` and continue with the installation.

## 4. Verify that Java 17 is installed.

Once the installation is complete, you can verify that Java 17 is installed by opening the command prompt again and typing `java -version`.

If the command runs successfully, you will see something like shown before, where the java version is displayed:

![Command prompt with "java -version" typed in.](/assets/players/installing-java/windows-java-version.png)

---

If you encounter any issues, feel free to ask for help in the [Fabric Discord](https://discord.gg/v6v4pMv) in the `#player-support` channel.