---
title: Launching the Game in VS Code
description: Learn how to launch a Minecraft instance from Visual Studio Code.
authors:
  - dicedpixels
prev:
  text: Opening a Project in VS Code
  link: ./opening-a-project
next:
  text: Generating Sources in VS Code
  link: ./generating-sources
---

The Fabric toolchain integrates with Visual Studio Code to provide a convenient way to run a game instance to test and debug your mod.

## Generating Launch Targets {#generating-launch-targets}

To run the game with debugging support enabled, you will need to generate launch targets by running the `vscode` Gradle task.

This can be done from the Gradle View from within Visual Studio Code: open it and navigate to the `vscode` task in **Tasks** > **`ide`**. Double click or use the **Run Task** button to execute the task.

![`vscode` Task in Gradle View](/assets/develop/getting-started/vscode/gradle-vscode.png)

Alternatively you can use the terminal directly: open a new terminal through **Terminal** > **New Terminal** and run:

```sh:no-line-numbers
./gradlew vscode
```

![`vscode` Task in Terminal](/assets/develop/getting-started/vscode/terminal-vscode.png)

### Using Launch Targets {#using-launch-targets}

Once launch targets are generated, you can use them by opening the **Run and Debug** view, selecting the desired target and pressing the **Start Debugging** button (<kbd>F5</kbd>).

![Launch Targets](/assets/develop/getting-started/vscode/launch-targets.png)
