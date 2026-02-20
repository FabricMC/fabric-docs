---
title: Building a Mod
description: Learn how to build a Minecraft mod and test it in a production environment.
authors:
  - cassiancc
---

Once your mod is ready for testing, you're able to export it into a JAR file which can be shared on mod hosting websites, or used to test your mod in production alongside other mods.

## Building in IntelliJ IDEA {#idea}

In IntelliJ IDEA, open the Gradle tab on the right and execute `build` under tasks. The JARs should appear in the `build/libs` folder in your project directory. Use the JAR file with the shortest name outside development.

![The sidebar of IntelliJ IDEA showing a highlighted build task](/assets/develop/getting-started/build-idea.png)

![The build/libs folder with the corrected file highlighted](/assets/develop/getting-started/build-libs.png)

## Building in the Terminal {#terminal}

::: info

Using the terminal to build a mod rather than an IDE may cause issues if your default Java installation does not match what the project is expecting. For more reliable builds, consider using IntelliJ IDEA.

:::

Open a terminal from the same directory as the mod project directory, and run the following command:

::: code-group

```powershell:no-line-numbers [Windows]
./gradlew.bat build
```

```sh:no-line-numbers [macOS/Linux]
./gradlew build
```

```sh:no-line-numbers [IntelliJ Run Configuration]
build
```

:::

The JARs should appear in the `build/libs` folder in your project. Use the JAR file with the shortest name outside development.

## Installing and Sharing {#installing-and-sharing}

From there, the mod can be [installed as normal](../../players/installing-mods), or uploaded to trustworthy mod hosting sites like [CurseForge](https://www.curseforge.com/minecraft) and [Modrinth](https://modrinth.com/discover/mods).
