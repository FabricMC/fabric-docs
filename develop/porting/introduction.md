---
title: Porting
description: A guide for porting to new versions of Minecraft.
authors:
  - cassiancc
---

Minecraft is a game that's constantly in flux, with new versions constantly bringing new changes to the game that affect modders. This article covers the general process one would go through to update their mod to a new version.

## Updating the Buildscript {#buildscript}

Start by updating your mod's `gradle/wrapper/gradle-wrapper.properties`, `gradle.properties`, and `build.gradle` to the latest versions.

1. Update Gradle to the latest version by running the following command: `./gradlew wrapper --gradle-version latest`
2. Update Loom to the latest version, in your `gradle.properties` (recommended) or at the top of `build.gradle`. For Minecraft 1.21.11, this would be `1.14-SNAPSHOT`.
3. Update Minecraft, Fabric Loader, and Fabric API versions in your `gradle.properties` (recommended) or in your `build.gradle`. The latest versions of Fabric are available on the [Develop site](https://fabricmc.net/develop/).
4. Refresh Gradle.

## Updating the Code {#porting-guides}

After your buildscript has been updated to 1.21.11, you can now go through your mod and update any code that has changed to be compatible with your new version. To make this transition easier, modders document the changes to the game in articles like the Fabric Blog and NeoForge's porting primers.

- [Fabric for Minecraft 1.21.11](https://fabricmc.net/2025/12/05/12111.html) contains a high-level explanation of the changes made to Fabric API in 1.21.11.
- [NeoForge's Minecraft 1.21.10 -> 1.21.11 Mod Migration Primer](https://github.com/neoforged/.github/blob/main/primers/1.21.11/index.md) covers migrating from 1.21.10 to 1.21.11, focusing only on vanilla code changes. Please note that NeoForge's primers are third party materials available under the copyright of @ChampionAsh5357 and licensed under the [Creative Commons Attribution 4.0 International](http://creativecommons.org/licenses/by/4.0/).
- [Minecraft's blog post, Minecraft Java Edition 1.21.11](https://www.minecraft.net/en-us/article/minecraft-java-edition-1-21-11) covers the features included in 1.21.11.
