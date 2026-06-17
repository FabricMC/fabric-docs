---
title: Porting to 26.2
description: Guidelines for porting to Minecraft 26.2, the latest version of Minecraft.
authors:
  - cassiancc
  - ChampionAsh5357
resources:
  https://fabricmc.net/2026/06/15/262.html: Fabric for Minecraft 26.2
  https://minecraft.wiki/w/Java_Edition_26.1: Java Edition 26.2 - Minecraft Wiki
  https://github.com/neoforged/.github/blob/main/primers/26.2/index.md: ChampionAsh5357's 26.1 -> 26.2 Migration Primers
---

Minecraft is a game that's constantly evolving, with new versions changing the game in ways that affect modders. This article covers the general steps one might follow to update their mod to the newest stable version of Minecraft.

::: info

These docs discuss migrating from **26.1** to **26.2**. If you're looking for another migration, switch to the target version by using the dropdown in the top-right corner.

:::

## Prerequisites {#prerequisites}

If you are migrating to 26.2 from 1.21.11 or below, please see the [Porting to 26.1](../../26.1.2/develop/porting/) article first.

## Updating the Build Script {#build-script}

Start by updating your mod's `gradle/wrapper/gradle-wrapper.properties`, `gradle.properties`, and `build.gradle` to the latest versions. If you run into trouble, consider referencing the [Fabric Example Mod](https://github.com/FabricMC/fabric-example-mod/tree/26.2).

1. Update Gradle to the latest version by running the following command: `./gradlew wrapper --gradle-version latest`
2. Bump Minecraft, Fabric Loader, Fabric Loom and Fabric API, either in `gradle.properties` (recommended) or in `build.gradle`. Find the recommended versions of the Fabric components on the [Fabric Develop site](https://fabricmc.net/develop/).
3. Refresh Gradle by using the refresh button in the top-right corner of IntelliJ IDEA. If this button is not visible, you can force caches to be cleared by running `./gradlew --refresh-dependencies`.

## Updating the Code {#porting-guides}

After the build script has been updated to 26.2, you can now go through your mod and update any code that has changed to make it compatible with the snapshot.

- [Fabric for Minecraft 26.1 on the Fabric blog](https://fabricmc.net/2026/06/15/262.html) contains a high-level explanation of the changes made to Fabric API in 26.1.
- [_Java Edition 26.2_ on the Minecraft Wiki](https://minecraft.wiki/w/Java_Edition_26.2) is an unofficial summary of the contents of the update.
- [NeoForge's _Minecraft 26.1 -> 26.2 Mod Migration Primer_](https://github.com/neoforged/.github/blob/main/primers/26.1/index.md) covers migrating from 26.1 to 26.2, focusing only on vanilla code changes.

<!---->
