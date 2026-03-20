---
title: Porting to 1.21.1
description: A guide for porting to the 1.21.1 version of Minecraft.
authors:
  - cassiancc
---

Minecraft is a game that's constantly evolving, with new versions changing the game in ways that affect modders. This article covers the general steps one might follow to update their mod to the 1.21.1 update of Minecraft.

1.21.1 is a hotfix for 1.21, so these docs will focus on 1.21.1 rather than 1.21, as there is no reason to continue to support a version with critical issues. Most 1.21 mods should work on 1.21.1.

::: info
These docs discuss migrating from **1.20.4** to **1.21.1**. If you're looking for another migration, switch to the target version by using the dropdown in the top-right corner.
:::

## Updating the Build Script {#build-script}

Start by updating your mod's `gradle/wrapper/gradle-wrapper.properties`, `gradle.properties`, and `build.gradle` to the latest versions:

1. Update Gradle to the latest version by running the following command: `./gradlew wrapper --gradle-version latest`
2. Bump Minecraft, Fabric Loader, Fabric Loom and Fabric API, either in `gradle.properties` (recommended) or in `build.gradle`. Find the recommended versions of the Fabric components on the [Fabric Develop site](https://fabricmc.net/develop/).
3. Refresh Gradle by using the refresh button in the top-right corner of IntelliJ IDEA. If this button is not visible, you can force caches to be cleared by running `./gradlew --refresh-dependencies`.

## Updating the Code {#porting-guides}

After the build script has been updated to 1.21.1, you can now go through your mod and update any code that has changed to make it compatible with the new version.

To help you with updating, modders will document the changes they came across in articles, like the Fabric Blog, and NeoForge's porting primers.

- [_Fabric for Minecraft 1.21 & 1.21.1_ on the Fabric blog](https://fabricmc.net/2024/05/31/121.html) contains a high-level explanation of the changes made to Fabric API in 1.21 and 1.21.1.
- [_Minecraft: Java Edition 1.21_ on the Minecraft blog](https://www.minecraft.net/en-us/article/minecraft-java-edition-1-21-1) is the official overview of the features introduced in 1.21.1.
  - [_Minecraft: Java Edition 1.21.1_ on the Minecraft blog](https://www.minecraft.net/en-us/article/minecraft-java-edition-1-21-1) is the official overview of the features introduced in 1.21.1.
- [_Java Edition 1.21_ on the Minecraft Wiki](https://minecraft.wiki/w/Java_Edition_1.21.1) is an unofficial summary of the contents of the update.
  - [_Java Edition 1.21.1_ on the Minecraft Wiki](https://minecraft.wiki/w/Java_Edition_1.21.1) is an unofficial summary of the contents of the update.
- [slicedlime's Data Pack News in Minecraft 1.21](https://www.youtube.com/watch?v=x1rT4o7Q1dY) covers information relevant to updating your mod's data pack driven content.
- [NeoForge's _Minecraft 1.20.6 -> 1.21 Mod Migration Primer_](https://github.com/neoforged/.github/blob/main/primers/1.21/index.md) covers migrating from 1.20.6 to 1.21, focusing only on vanilla code changes.
  - [NeoForge's _Minecraft 1.21 -> 1.21.1 Mod Migration Primer_](https://github.com/neoforged/.github/blob/main/primers/1.21.1/index.md) covers migrating from 1.21.7 to 1.21.8, focusing only on vanilla code changes.
  - Please note that these linked articles are third-party material, not maintained by Fabric. They are under copyright of @ChampionAsh5357, and licensed under [Creative Commons Attribution 4.0 International](https://creativecommons.org/licenses/by/4.0/).
