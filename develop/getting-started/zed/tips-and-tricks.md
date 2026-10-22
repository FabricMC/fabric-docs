---
title: Tips and Tricks for Zed
description: Useful tips and tricks to make your work easier in Zed.
authors:
  - itzzmateo
prev:
  text: Generating Sources in Zed
  link: ./generating-sources
next: false
---

This page gives useful bits of information to speed up and ease the workflow of developers using Zed for Fabric mod development.

## Code Navigation {#code-navigation}

### Go to Definition {#go-to-definition}

You can jump to a symbol's definition by holding <kbd>Ctrl</kbd> and clicking on it, or by pressing <kbd>F12</kbd>.

### Go to References {#go-to-references}

To find all references to a symbol, right-click on it and select **Go to References**, or use the keyboard shortcut <kbd>Shift</kbd>+<kbd>F12</kbd>.

### File Finder {#file-finder}

Open the file finder with <kbd>Ctrl</kbd>+<kbd>P</kbd> to quickly navigate between files in your project.

### Symbol Search {#symbol-search}

Search for symbols across your project with <kbd>Ctrl</kbd>+<kbd>Shift</kbd>+<kbd>O</kbd>.

## Useful Zed Features {#useful-zed-features}

### Terminal Integration {#terminal-integration}

Zed has a built-in terminal. Open it with <kbd>Ctrl</kbd>+<kbd>`</kbd> to run Gradle commands without leaving the editor.

### Multi-cursor Editing {#multi-cursor-editing}

Hold <kbd>Alt</kbd> and click to add multiple cursors for simultaneous editing.

### Command Palette {#command-palette}

Access all of Zed's commands with <kbd>Ctrl</kbd>+<kbd>Shift</kbd>+<kbd>P</kbd> to open the command palette.

## Viewing Bytecode {#viewing-bytecode}

Viewing bytecode is necessary when writing mixins. Zed doesn't have native bytecode viewing support, so you'll need to use Java's inbuilt `javap` tool.

Open the terminal and run:

```sh:no-line-numbers
javap -cp /path/to/minecraft.jar -c -private net.minecraft.util.Identifier
```

This will print the bytecode in your terminal output.

::: tip

For a more integrated bytecode viewing experience, consider using [IntelliJ IDEA](../intellij-idea/tips-and-tricks#viewing-bytecode) which has built-in bytecode support.

:::
