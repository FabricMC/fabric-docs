---
title: Building a Mod in IntelliJ IDEA
description: Learn how to build a Minecraft mod using IntelliJ IDEA, to test it in a production environment.
authors:
  - cassiancc
prev:
  text: Generating Sources in IntelliJ IDEA
  link: ./generating-sources
next:
  text: Tips and Tricks for IntelliJ IDEA
  link: ./tips-and-tricks
---

In IntelliJ IDEA, open the Gradle tab on the right and execute `build` under tasks. The JARs should appear in the `build/libs` folder in your project directory. Use the JAR file with the shortest name outside development.

![The sidebar of IntelliJ IDEA showing a highlighted build task](/assets/develop/getting-started/build-idea.png)

![The build/libs folder with the corrected file highlighted](/assets/develop/getting-started/build-libs.png)

## Installing and Sharing {#installing-and-sharing}

From there, the mod can be [installed as normal](../../players/installing-mods), or uploaded to trustworthy mod hosting sites like [CurseForge](https://www.curseforge.com/minecraft) and [Modrinth](https://modrinth.com/discover/mods).
