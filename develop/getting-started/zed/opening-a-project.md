---
title: Opening a Project in Zed
description: How to open a Minecraft mod project in Zed.
authors:
  - itzzmateo
prev:
  text: Setting Up Zed
  link: ./setting-up
next:
  text: Launching the Game in Zed
  link: ./launching-the-game
---

Assuming you already have a project, you can open it through **File** > **Open**.

You can also open a project from the command line:

```sh:no-line-numbers
zed /path/to/your/project
```

## Importing the Project {#importing-the-project}

Once you've opened the project in Zed, the Java extension should automatically detect the Gradle configuration and start importing the project.

You should see indicators in the status bar showing the import progress. Once complete, you should be able to navigate through your project's files.
