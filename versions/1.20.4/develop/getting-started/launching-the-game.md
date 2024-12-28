---
title: Launching the Game
description: Learn how to utilize the various launch profiles to start and debug your mods in a live game environment.
authors:
  - IMB11

search: false
---

# Launching the Game {#launching-the-game}

Fabric Loom provides a variety of launch profiles to help you start and debug your mods in a live game environment. This guide will cover the various launch profiles and how to use them to debug and playtest your mods.

## Launch Profiles {#launch-profiles}

If you're using IntelliJ IDEA, you can find the launch profiles in the top-right corner of the window. Click the dropdown menu to see the available launch profiles.

There should be a client and server profile, with the option to either run it normally or in debug mode:

![Launch Profiles](/assets/develop/getting-started/launch-profiles.png)

## Gradle Tasks {#gradle-tasks}

If you're using the command line, you can use the following Gradle commands to start the game:

- `./gradlew runClient` - Start the game in client mode.
- `./gradlew runServer` - Start the game in server mode.

The only problem with this approach is that you can't easily debug your code. If you want to debug your code, you'll need to use the launch profiles in IntelliJ IDEA or via your IDE's Gradle integration.

## Hotswapping Classes {#hotswapping-classes}

When you run the game in debug mode, you can hotswap your classes without restarting the game. This is useful for quickly testing changes to your code.

You're still quite limited though:

- You can't add or remove methods
- You can't change method parameters
- You can't add or remove fields

## Hotswapping Mixins {#hotswapping-mixins}

If you're using Mixins, you can hotswap your Mixin classes without restarting the game. This is useful for quickly testing changes to your Mixins.

You will need to install the Mixin Java agent for this to work though.

### 1. Locate the Mixin Library Jar {#1-locate-the-mixin-library-jar}

In IntelliJ IDEA, you can find the mixin library jar in the "External Libraries" section of the "Project" section:

![Mixin Library](/assets/develop/getting-started/mixin-library.png)

You will need to copy the jar's "Absolute Path" for the next step.

### 2. Add the `-javaagent` VM Argument {#2-add-the--javaagent-vm-argument}

In your "Minecraft Client" and or "Minecraft Server" run configuration, add the following to the VM Arguments option:

```:no-line-numbers
-javaagent:"path to mixin library jar here"
```

![VM Arguments Screenshot](/assets/develop/getting-started/vm-arguments.png)

Now, you should be able to modify the contents of your mixin methods during debugging and have the changes take effect without restarting the game.
