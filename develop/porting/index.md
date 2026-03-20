---
title: Porting to 1.21.11
description: A guide for porting to the latest version of Minecraft.
authors:
  - cassiancc
---

Minecraft is a game that's constantly evolving, with new versions changing the game in ways that affect modders. This article covers the general steps one might follow to update their mod to the newest stable version of Minecraft.

::: info

These docs discuss migrating from **1.21.10** to **1.21.11**. If you're looking for another migration, switch to the target version by using the dropdown in the top-right corner.

:::

## Updating the Build Script {#build-script}

Start by updating your mod's `gradle/wrapper/gradle-wrapper.properties`, `gradle.properties`, and `build.gradle` to the latest versions:

1. Update Gradle to the latest version by running the following command: `./gradlew wrapper --gradle-version latest`
2. Bump Minecraft, Fabric Loader, Fabric Loom and Fabric API, either in `gradle.properties` (recommended) or in `build.gradle`. Find the recommended versions of the Fabric components on the [Fabric Develop site](https://fabricmc.net/develop/).
3. Refresh Gradle by using the refresh button in the top-right corner of IntelliJ IDEA. If this button is not visible, you can force caches to be cleared by running `./gradlew --refresh-dependencies`.

## Updating the Code {#porting-guides}

After the build script has been updated to 1.21.11, you can now go through your mod and update any code that has changed to make it compatible with the new version.

To help you with updating, modders will document the changes they came across in articles, like the Fabric Blog, and NeoForge's porting primers.

- [_Fabric for Minecraft 1.21.11_ on the Fabric blog](https://fabricmc.net/2025/12/05/12111.html) contains a high-level explanation of the changes made to Fabric API in 1.21.11.
- [_Minecraft: Java Edition 1.21.11_ on the Minecraft blog](https://www.minecraft.net/en-us/article/minecraft-java-edition-1-21-11) is the official overview of the features introduced in 1.21.11.
- [_Java Edition 1.21.11_ on the Minecraft Wiki](https://minecraft.wiki/w/Java_Edition_1.21.11) is an unofficial summary of the contents of the update.
- [slicedlime's Data & Resource Pack News in Minecraft 1.21.11](https://www.youtube.com/watch?v=5yY25GoWQhs&pp=0gcJCSkKAYcqIYzv) covers information relevant to updating your mod's data and resource pack driven content.
- [NeoForge's _Minecraft 1.21.10 -> 1.21.11 Mod Migration Primer_](https://github.com/neoforged/.github/blob/main/primers/1.21.11/index.md) covers migrating from 1.21.10 to 1.21.11, focusing only on vanilla code changes.
  - Please note that the linked article is third-party material, not maintained by Fabric. It's under copyright of @ChampionAsh5357, and licensed under [Creative Commons Attribution 4.0 International](https://creativecommons.org/licenses/by/4.0/).
