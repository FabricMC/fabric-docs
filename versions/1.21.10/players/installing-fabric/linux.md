---
title: Installing Fabric on Linux
description: A step-by-step guide on how to install Fabric on Linux.
authors:
  - Benonardo
  - ezfe
  - IMB11
  - modmuss50
next: false
---

::: info PREREQUISITES
You may need to [install Java](../installing-java/linux) before running the `.jar`.
:::

<!-- #region common -->

## 1. Download the Fabric Installer {#1-download-the-fabric-installer}

Download the `.jar` version of the Fabric Installer from the [Fabric Website](https://fabricmc.net/use/), by clicking on `Download installer (Universal/.JAR)`.

## 2. Run the Fabric Installer {#2-run-the-fabric-installer}

Close Minecraft and the Minecraft Launcher first before running the installer.

Open a terminal and run the installer using Java:

```sh
java -jar fabric-installer.jar
```

Once you open the installer, you should see a screen like this:

![Fabric Installer with "Install" highlighted](/assets/players/installing-fabric/installer-screen.png)

<!-- #endregion common -->

Select your desired Minecraft version and click `Install`. Make sure that `Create Profile` is checked.

## 3. Finish Setup {#3-finish-setup}

Once installation completes, open the Minecraft Launcher. Then select the Fabric profile from the version dropdown and click Play.

![Minecraft Launcher with Fabric profile selected](/assets/players/installing-fabric/launcher-screen.png)

You can now add mods to your game. See the [Finding Trustworthy Mods](../finding-mods) guide for info.

If you encounter issues, feel free to ask for help in the [Fabric Discord](https://discord.gg/v6v4pMv) in the `#player-support` channel.
