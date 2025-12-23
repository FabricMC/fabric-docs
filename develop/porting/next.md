---
title: Porting to 26.1 Snapshots
description: A guide for porting to the unobfuscated versions of Minecraft.
authors:
  - cassiancc
---



The upcoming 26.1 version of Minecraft is unobfuscated, as are its snapshots. With this in mind, you'll need to make more changes to your build scripts than usual if you want to port to it. Note that if your mod was using Fabric's Yarn Mappings and you have not yet [migrated your mod to Mojang's official obfuscation mappings](migrating-mappings), you will need to do so before porting to 26.1.

If you are using IntelliJ IDEA, you will also need to update it to `2025.3` or higher for full Java 25 support.

::: info
These docs are focused on migrating from 1.21.11 -> 26.1. If you have not yet ported to 1.21.11, please read that article first.
:::

## Updating the Build Script {#build-script}

Start by updating your mod's `gradle/wrapper/gradle-wrapper.properties`, `gradle.properties`, and `build.gradle` to the latest versions.

1. Update Gradle to the latest version by running the following command: `./gradlew wrapper --gradle-version latest`
2. Bump Minecraft, Fabric Loader, Fabric Loom and Fabric API, either in your `gradle.properties` (recommended) or in your `build.gradle`. Find the recommended versions of the Fabric components for this version on the [Develop site](https://fabricmc.net/develop/).
    - Note that since 26.1 snapshots are not stable versions of Minecraft, you will need to select this version manually from the dropdown.
3. At the top of your `build.gradle`, change the version of Loom you are using from `id "fabric-loom"` to `id "net.fabricmc.fabric-loom"`. If you specify Loom in `setttings.gradle`, change it there as well.
4. Remove the `mappings` line from the dependencies section of your `build.gradle`.
5. Replace any instances of `modImplementation` or `modCompileOnly` with `implementation` and `compileOnly`.
6. Remove or replace any mods made for versions before 26.1 with versions compatible with this update.
    - No existing mods for 1.21.11 or older versions of Minecraft will work on 26.1, even as a compile-only dependency.
7. Replace any mentions of `remapJar` with `jar`.
8. Refresh Gradle using the refresh button in the top right corner of IntelliJ IDEA or by running `./gradlew --refresh-dependencies`.

## Updating the Code {#porting-guides}

After your buildscript has been updated to 26.1, you can now go through your mod and update any code that has changed to be compatible with your new version.

As 26.1 has not yet released, there is no documentation for the changes in this update.
