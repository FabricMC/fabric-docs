---
title: Generating Minecraft Sources in IntelliJ IDEA
description: How to generate Minecraft sources.
authors:
  - dicedpixels
---

The Fabric toolchain lets you access the Minecraft source code by generating it locally, and you can use IntelliJ IDEA to conveniently navigate through it. To generate sources, you need to run the `genSources` Gradle task.

This can be done from the Gradle View like above, by running the `genSources` task in **Tasks** > **`fabric`**:
![`genSources` Task in Gradle Panel](/assets/develop/getting-started/intellij/gradle-gensources.png)

Or you can also run the command from the terminal:

```sh:no-line-numbers
./gradlew genSources
```

![`genSources` Task in Terminal](/assets/develop/getting-started/intellij/terminal-gensources.png)

## Attaching Sources {#attaching-sources}

IntelliJ requires one additional step of attaching generated sources to the project.

To do this, open any Minecraft class. You can <kbd>Ctrl</kbd> + Click to go to the definition, which opens the class or use "Search everywhere" to open a class.

Lets open `MinecraftServer.class` as an example. You should now see a blue banner on the top with a "**Choose Sources...**" link.

![Choose Sources](/assets/develop/getting-started/intellij/choose-sources.png)

Click on "**Choose Sources...**" to open a file selector dialog. This dialog will open at the correct location of generated sources by default.

Select the file that ends with **`-sources`** and press **Open** to confirm the selection.

![Choose Sources Dialog](/assets/develop/getting-started/intellij/choose-sources-dialog.png)

If the correct file is open, you should now be able to see Javadoc comments as well as have the ability to search for references.

![Javadoc Comments in Sources](/assets/develop/getting-started/intellij/javadoc.png)
