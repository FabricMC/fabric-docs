---
title: Porting to 26.1 Snapshots
description: Guidelines for porting to snapshots of the next version of Minecraft.
authors:
  - cassiancc
---

The upcoming 26.1 version of Minecraft is unobfuscated, as are its snapshots. With this in mind, you'll need to make more changes to your build scripts than usual in order to port to it.

::: info

These docs discuss migrating from **1.21.11** to the snapshots of **26.1**. If you're looking to migrate to **1.21.11**, check out the article on [Porting to 1.21.11](./current).

:::

Note that if your mod is still using Fabric's Yarn Mappings, you'll first need to [migrate your mod to Mojang's official mappings](../migrating-mappings) before porting to 26.1.

If you are using IntelliJ IDEA, you will also need to update it to `2025.3` or higher for full Java 25 support.

## Updating the Build Script {#build-script}

Start by updating your mod's `gradle/wrapper/gradle-wrapper.properties`, `gradle.properties`, and `build.gradle` to the latest versions.

1. Update Gradle to the latest version by running the following command: `./gradlew wrapper --gradle-version latest`
2. Bump Minecraft, Fabric Loader, Fabric Loom and Fabric API, either in `gradle.properties` (recommended) or in `build.gradle`. Find the recommended versions of the Fabric components on the [Fabric Develop site](https://fabricmc.net/develop/).
   - Note that, since 26.1 snapshots are not stable versions of Minecraft, you will need to select this version manually from the dropdown.
3. At the top of `build.gradle`, change the version of Loom you are using from `id "fabric-loom"` to `id "net.fabricmc.fabric-loom"`. If you specify Loom in `settings.gradle`, change it there as well.
4. Remove the `mappings` line from the dependencies section of `build.gradle`.
5. Replace any instances of `modImplementation` or `modCompileOnly` with `implementation` and `compileOnly`.
6. Remove or replace any mods made for versions before 26.1 with versions compatible with this update.
   - No existing mods for 1.21.11 or older versions of Minecraft will work on 26.1, even as a compile-only dependency.
7. Replace any mentions of `remapJar` with `jar`.
8. Refresh Gradle by using the refresh button in the top-right corner of IntelliJ IDEA. If this button is not visible, you can force caches to be cleared by running `./gradlew --refresh-dependencies`.

## Updating the Code {#porting-guides}

After the build script has been updated to 26.1, you can now go through your mod and update any code that has changed to make it compatible with the snapshot.

::: warning IMPORTANT

Because Minecraft 26.1 is still in the snapshot stage, there is no documentation about specific changes yet. Good luck, you're on your own!

:::

<!---->
