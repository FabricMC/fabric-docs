---
title: Porting
description: A guide for porting to new versions of Minecraft.
authors:
  - cassiancc
---

Minecraft is a game that's constantly moving, with new versions changing the game in ways that affect modders. This article covers the general steps one might follow to update their mod to a new version of Minecraft.

## Updating the Build Script {#build-script}

Start by updating your mod's `gradle/wrapper/gradle-wrapper.properties`, `gradle.properties`, and `build.gradle` to the latest versions.

1. Update Gradle to the latest version by running the following command: `./gradlew wrapper --gradle-version latest`
2. Bump Minecraft, Fabric Loader, Fabric Loom and Fabric API, either in your `gradle.properties` (recommended) or in your `build.gradle`. Find the recommended versions of the Fabric components for this version on the [Develop site](https://fabricmc.net/develop/).
3. Refresh Gradle using the refresh button in the top right corner of IntelliJ IDEA or by running `./gradlew --refresh-dependencies`.

## Updating the Code {#porting-guides}

After your buildscript has been updated to 1.21.11, you can now go through your mod and update any code that has changed to be compatible with your new version.

To help you with updating, modders will document the changes they came across in articles, like the Fabric Blog, and NeoForge's porting primers.

- [_Fabric for Minecraft 1.21.11_ on the Fabric blog](https://fabricmc.net/2025/12/05/12111.html) contains a high-level explanation of the changes made to Fabric API in 1.21.11.
- [NeoForge's _Minecraft 1.21.10 -> 1.21.11 Mod Migration Primer_](https://github.com/neoforged/.github/blob/main/primers/1.21.11/index.md) covers migrating from 1.21.10 to 1.21.11, focusing only on vanilla code changes.
  - Please note that the linked article is third-party material, not maintained by Fabric. It's under copyright of @ChampionAsh5357, and licensed under [Creative Commons Attribution 4.0 International](http://creativecommons.org/licenses/by/4.0/).
- [_Minecraft: Java Edition 1.21.11_ on the Minecraft blog](https://www.minecraft.net/en-us/article/minecraft-java-edition-1-21-11) is the official overview of the features introduced in 1.21.11.
- [_Java Edition 1.21.11_ on the Minecraft Wiki](https://minecraft.wiki/w/Java_Edition_1.21.11) is an unofficial summary of the contents of the update.
