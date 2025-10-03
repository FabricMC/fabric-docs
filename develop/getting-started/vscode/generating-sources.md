---
title: Generating Minecraft Sources in Visual Studio Code
description: How to generate Minecraft sources.
authors:
  - dicedpixels
---

The Fabric toolchain lets you access the Minecraft source code by generating it locally, and you can use Visual Studio Code to conveniently navigate through it. To generate sources, you need to run the `genSources` Gradle task.

This can be done from the Gradle View like above, by running the `genSources` task in **Tasks** > **`fabric`**:
![`genSources` Task in Gradle View](/assets/develop/getting-started/vscode/gradle-gensources.png)

Or you can also run the command from the terminal:

```sh:no-line-numbers
./gradlew genSources
```

![`genSources` Task in Terminal](/assets/develop/getting-started/vscode/terminal-gensources.png)
