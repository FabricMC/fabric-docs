---
title: Generating Sources in Zed
description: A guide to generating Minecraft sources in Zed.
authors:
  - itzzmateo
prev:
  text: Launching the Game in Zed
  link: ./launching-the-game
next:
  text: Tips and Tricks for Zed
  link: ./tips-and-tricks
---

The Fabric toolchain lets you access the Minecraft source code by generating it locally, and you can use Zed to conveniently navigate through it. To generate sources, you need to run the `genSources` Gradle task.

You can run this command from the terminal:

```sh:no-line-numbers
./gradlew genSources
```

Once the task completes, you should be able to navigate through the generated Minecraft source files in Zed.
