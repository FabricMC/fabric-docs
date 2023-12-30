---
title: Installing Mods
description: A step by step guide on how to install mods for Fabric.
---

# Installing Mods

This guide will walk you through installing mods for Fabric using the Minecraft Launcher.

For third party launchers, you should consult their documentation.

## 1. Download the Mod

::: warning
You should only download mods from sources you trust, for more information on finding mods, see the [Finding Mods](./finding-mods.md) guide.
:::

The majority of mods require [Fabric API](https://modrinth.com/mod/fabric-api) as well, so you should download that too.

When downloading mods, ensure that:

- They work on the version of Minecraft you want to play on.
- They are for Fabric, not NeoForge, Rift, or any other mod loader.
- Furthermore, they are for the correct edition of Minecraft (Java Edition).

## 2. Move the mod to the `mods` folder.

The mods folder can be found in the following locations for each operating system:

::: code-group

```:no-line-numbers [Windows]
%appdata%\.minecraft\mods
```

```:no-line-numbers [macOS]
~/Library/Application Support/minecraft/mods
```

```:no-line-numbers [Linux]
~/.minecraft/mods
```

:::

Once you've found the `mods` folder, you can move the mod `.jar` files into it.

![Installed mods in the mods folder.](/assets/players/installing-mods.png)

## 3. You're done!

Once you've moved the mods into the `mods` folder, you can open the Minecraft Launcher and select the Fabric profile from the dropdown in the bottom left corner and press play!

![Minecraft Launcher with Fabric profile selected.](/assets/players/installing-fabric/launcher-screen.png)

## Troubleshooting

If you encounter any issues whilst following this guide, you can ask for help in the [Fabric Discord](https://discord.gg/v6v4pMv) in the `#player-support` channel.

You can also attempt to troubleshoot the issue yourself by reading the troubleshooting pages:

- [Crash Reports](./troubleshooting/crash-reports.md)
- [Uploading Logs](./troubleshooting/uploading-logs.md)