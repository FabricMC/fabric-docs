---
title: Tips and Tricks for VS Code
description: Useful tips and tricks to make your work easier.
authors:
  - dicedpixels
prev:
  text: Generating Sources in VS Code
  link: ./generating-sources
next: false
---

It's important to learn how to traverse the generate sources so that you can debug and get an understanding of the inner workings of Minecraft. Here we outline some common IDE usages.

## Searching for a Minecraft Class {#searching-for-a-minecraft-class}

Once sources are generated. it should be possible for you to search or view Minecraft classes.

### Viewing Class Definitions {#viewing-class-definitions}

**Quick Open** (<kbd>Ctrl</kbd>+<kbd>P</kbd>): Type `#` followed by the class name (e.g. `#Identifier`).

![Quick Open](/assets/develop/getting-started/vscode/quick-open.png)

**Go to Definition** (<kbd>F12</kbd>): From source code, navigate to a class definition by <kbd>Ctrl</kbd> + clicking on its name, or by right-clicking it and selecting "Go to Definition".

![Go to Definition](/assets/develop/getting-started/vscode/go-to-definition.png)

### Finding References {#finding-references}

You can find all usages of a class by right-clicking on a class name and clicking **Find All References**.

![Find All References](/assets/develop/getting-started/vscode/find-all-references.png)

::: info

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
