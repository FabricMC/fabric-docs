---
title: Setting up a Development Environment
description: A step-by-step guide on how to set up a development environment to create mods using Fabric.
authors:
  - IMB11
  - andrew6rant
  - SolidBlock-cn
  - modmuss50
  - daomephsta
  - liach
  - telepathicgrunt
  - 2xsaiko
  - natanfudge
  - mkpoli
  - falseresync
  - asiekierka
authors-nogithub:
  - siglong

search: false
---

# Setting up a Development Environment {#setting-up-a-development-environment}

To start developing mods with Fabric, you will need to set up a development environment using IntelliJ IDEA.

## Installing JDK 17 {#installing-jdk-17}

To develop mods for Minecraft 1.20.4, you will need JDK 17.

If you need help installing Java, you can refer to the various Java installation guides in the [player guides section](../../players/index).

## Installing IntelliJ IDEA {#installing-intellij-idea}

::: info
You can obviously use other IDEs, such as Eclipse or Visual Studio Code, but the majority of the pages on this documentation site will assume that you are using IntelliJ IDEA - you should refer to the documentation for your IDE if you are using a different one.
:::

If you do not have IntelliJ IDEA installed, you can download it from the [official website](https://www.jetbrains.com/idea/download/) - follow the installation steps for your operating system.

The Community edition of IntelliJ IDEA is free and open-source, and it is the recommended version for modding with Fabric.

You may have to scroll down to find the Community edition download link - it looks like the following:

![IDEA Community Edition Download Prompt](/assets/develop/getting-started/idea-community.png)

## Installing IDEA Plugins {#installing-idea-plugins}

Although these plugins aren't strictly necessary, they can make modding with Fabric much easier - you should consider installing them.

### Minecraft Development {#minecraft-development}

The Minecraft Development plugin provides support for modding with Fabric, and it is the most important plugin to install.

You can install it by opening IntelliJ IDEA, and then navigating to `File > Settings > Plugins > Marketplace Tab` - search for `Minecraft Development` in the search bar, and then click the `Install` button.

Alternatively, you can download it from the [plugin page](https://plugins.jetbrains.com/plugin/8327-minecraft-development) and then install it by navigating to `File > Settings > Plugins > Install Plugin From Disk`.
