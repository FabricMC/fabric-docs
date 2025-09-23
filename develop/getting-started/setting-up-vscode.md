---
title: Setting up Visual Studio Code
description: A step-by-step guide on how to set up Visual Studio Code to create mods using Fabric.
authors:
  - dicedpixels
---

Visual Studio Code is a free, extensible and lightweight code editor.

:::info
While it is possible to develop mods using Visual Studio Code, we recommend IntelliJ IDEA for it's dedicated Java tooling, advanced features and community created plugins such as **Minecraft Development**.
:::

::: info PREREQUISITES
Make sure you've [installed a JDK](./setting-up-a-development-environment#installing-jdk-21) first.
:::

## Installation {#installation}

You can download Visual Studio Code from [code.visualstudio.com](https://code.visualstudio.com/) or through your preferred package manager.

![Visual Studio Code Download Page](/assets/develop/getting-started/vscode/download.png)

## Prerequisites {#prerequisites}

Visual Studio Code does not provide Java language support out of the box. However, Microsoft provides a convenient extension pack that contains all the necessary extensions to enable Java language support.

You can install this extension pack from [Visual Studio Marketplace](https://marketplace.visualstudio.com/items?itemName=vscjava.vscode-java-pack).

![Extension Pack for Java](/assets/develop/getting-started/vscode/extension.png)

Or, within Visual Studio Code itself, through the Extensions view.

![Extension Pack for Java in Extension view](/assets/develop/getting-started/vscode/extension-view.png)

The **Language Support for Java** extension will present you with a startup screen to set up a JDK. You can do so if you have not already.

## Importing a Project {#importing-a-project}

Assuming you already have a project, you can open it through **File** > **Open Folder**.

![File Open](/assets/develop/getting-started/vscode/file-open.png)

Then select the relevant folder from the folder picker.

![Open Folder](/assets/develop/getting-started/vscode/open-folder.png)

You should see an indicator and a notification of the project import progress.

![Notification](/assets/develop/getting-started/vscode/notification.png)

## Generating Launch Targets {#generating-launch-targets}

To run the game with debugging support enabled, you will need to generate launch targets. This can be done by running the `vscode` Gradle task, either from the Gradle view or from the terminal.

<!-- markdownlint-disable MD036-->
**Terminal**

Open a new terminal through **Terminal** > **New Terminal** and run:

```sh:no-line-numbers
./gradlew vscode
```

![vscode in Terminal](/assets/develop/getting-started/vscode/terminal-vscode.png)

**Gradle View**

Open the Gradle view and navigate to the `vscode` task in **Tasks** > **ide**. Double click or use the **Run Task** button to execute the task.

![vscode in Gradle View](/assets/develop/getting-started/vscode/gradle-vscode.png)

### Using Launch Targets {#using-launch-targets}

Once launch targets are generated, you can use them by opening the **Run and Debug** view, selecting the desired target and pressing the **Start Debugging** button (<kbd>F5</kbd>).

![Launch Targets](/assets/develop/getting-started/vscode/launch-targets.png)

## Generating Minecraft Sources {#generating-minecraft-sources}

The Fabric toolchain lets you access the Minecraft source code by generating it locally. You can use Visual Studio Code to conveniently navigate through this generated source code.

To generate sources, you need to run the `genSources` Gradle task.

**Terminal**

Open a terminal and run:

```powershell:no-line-numbers
./gradlew genSources
```

![genSources in Terminal](/assets/develop/getting-started/vscode/terminal-gensources.png)

**Gradle View**

Open the Gradle view and run the `genSources` task in **Tasks** > **fabric**.

![genSources Gradle Task](/assets/develop/getting-started/vscode/gradle-gensources.png)

## Searching for a Minecraft Class {#searching-for-a-minecraft-class}

Once sources are generated. it should be possible for you to search or view Minecraft classes.

### Viewing Class Definitions {#viewing-class-definitions}

**Quick Open** (<kbd>Ctrl</kbd>+<kbd>P</kbd>):

Type `#` followed by the class name (e.g. `#Identifier`).

![Quick Open](/assets/develop/getting-started/vscode/quick-open.png)

**Go to Definition**:

From source code, you can navigate to a class definition by <kbd>Ctrl</kbd> + clicking on the class name or by right-clicking on it and selecting "Go to Definition" (<kbd>F12</kbd>).

![Go to Definition](/assets/develop/getting-started/vscode/go-to-definition.png)

### Finding References {#finding-references}

You can find all usages of a class by right-clicking on a class name and clicking **Find All References**.

![Find All References](/assets/develop/getting-started/vscode/find-all-references.png)

:::info
If the functions above do not work as expected, it's likely that sources are not attached properly. This can generally be fixed by cleaning up the workspace cache.

- Click the **Show Java Status Menu** button in the status bar.

![Show Java Status](/assets/develop/getting-started/vscode/java-ready.png)

- In the menu that just opened, click **Clean Workspace Cache...** and confirm the operation.

![Clear Workspace](/assets/develop/getting-started/vscode/clear-workspace.png)

- Close and reopen the project.

:::

## Viewing Bytecode {#viewing-bytecode}

Viewing bytecode is necessary when writing mixins. However, Visual Studio Code lacks native support for bytecode viewing, and the few extensions which add it might not work.

In such case, you can use Java's inbuilt `javap` to view bytecode.

- **Locate the path to Minecraft JAR:**

    Open the Explorer view, expand the **Java Projects** section. Expand the **Reference Libraries** node in the project tree and locate a JAR with `minecraft-` in its name. Right-click on the JAR and copy the full path.

    It might look something like this:

    ```:no-line-numbers
    C:/project/.gradle/loom-cache/minecraftMaven/net/minecraft/minecraft-merged-503b555a3d/1.21.8-net.fabricmc.yarn.1_21_8.1.21.8+build.1-v2/minecraft-merged-503b555a3d-1.21.8-net.fabricmc.yarn.1_21_8.1.21.8+build.1-v2.jar
    ```

![Copy Path](/assets/develop/getting-started/vscode/copy-path.png)

- **Run `javap`:**

    You can then run `javap` by providing the above path as the `cp` (class path) and the fully qualified class name as the final argument.

    ```sh
    javap -cp C:/project/.gradle/loom-cache/minecraftMaven/net/minecraft/minecraft-merged-503b555a3d/1.21.8-net.fabricmc.yarn.1_21_8.1.21.8+build.1-v2/minecraft-merged-503b555a3d-1.21.8-net.fabricmc.yarn.1_21_8.1.21.8+build.1-v2.jar -c -private net.minecraft.util.Identifier
    ```

    This will print the bytecode in your terminal output.

![javap](/assets/develop/getting-started/vscode/javap.png)
