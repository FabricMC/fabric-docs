---
title: Launching the Game in Zed
description: Learn how to launch a Minecraft instance from Zed.
authors:
  - itzzmateo
prev:
  text: Opening a Project in Zed
  link: ./opening-a-project
next:
  text: Generating Sources in Zed
  link: ./generating-sources
---

The Fabric toolchain integrates with Zed to provide a way to run a game instance to test and debug your mod.

## Using Gradle Tasks {#using-gradle-tasks}

To run the game, you can use the integrated terminal. Open a new terminal through **Terminal** > **New Terminal** and run the following commands:

::: code-group

```sh:no-line-numbers [Run Client]
./gradlew runClient
```

```sh:no-line-numbers [Run Server]
./gradlew runServer
```

:::

## Debugging {#debugging}

Zed supports debugging Java applications through the Debug Adapter Protocol. To debug your mod, you can use the Gradle task with debugging enabled:

```sh:no-line-numbers
./gradlew runClient --debug-jvm
```

This will start the game in debug mode, allowing you to set breakpoints and inspect variables.

::: tip

For a more integrated debugging experience, consider using [IntelliJ IDEA](../intellij-idea/launching-the-game) which has built-in debugging support for Gradle projects.

:::
