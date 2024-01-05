---
title: Yarn
description: Technical Information about Yarn, the open source mappings used by the majority of Fabric projects.
---

# Yarn

Yarn is a set of open, unencumbered Minecraft mappings, free for everyone to use under the Creative Commons Zero license. The intention is to let everyone mod Minecraft freely and openly, while also being able to innovate and process the mappings as they see fit.

Yarn uses the [Enigma](./specifications/enigma.md) mappings format to store the mapped classes, fields and methods that are obfuscated.

You can view these Enigma mappings in the `/mappings` folder of the [GitHub Repository](https://github.com/FabricMC/yarn).

## Contributing to Yarn

::: warning
You will need a considerably powerful computer to open the Enigma GUI due to the sheer amount of mapping files that are loaded. 

You should also have a read of Yarn's [Contributing Guidelines](https://github.com/FabricMC/yarn/blob/23w51b/CONTRIBUTING.md) and [Conventions](https://github.com/FabricMC/yarn/blob/23w51b/CONVENTIONS.md) before making any changes.
:::

To contribute to yarn, simply clone the repository and open it in IntelliJ IDEA or a similar Java IDE.

Once the project has loaded, simply run the `yarn` task via your IDE or `./gradlew yarn` in a terminal.

This should open the Enigma GUI, where you can modify the mappings easily.

*For more information on Enigma, you should refer to the [Enigma](./enigma.md) technical page.*

Once you've made your changes, make sure to save and quit Enigma, commit your changes, and make a pull request!

## Generating a Mapped Jar

To generate a mapped jar, simply run the `mapNamedJar` task. This task will build a deobfuscated jar with yarn mappings and automatically mapped fields (enums, etc.) - any unmapped names will be filled with intermediary names.

This jar can be opened in any common class file viewer - such as your IDE - where you can view the methods, fields and classes with their names.




