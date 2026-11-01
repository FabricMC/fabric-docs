---
title: Setting Up Your Development Environment
description: A step-by-step guide on how to set up a development environment to create mods using Fabric.
authors:
  - 2xsaiko
  - Andrew6rant
  - asiekierka
  - Daomephsta
  - dicedpixels
  - falseresync
  - IMB11
  - its-miroma
  - liach
  - mkpoli
  - modmuss50
  - natanfudge
  - SolidBlock-cn
  - TelepathicGrunt
authors-nogithub:
  - siglong
---

## Install JDK 25 {#install-jdk-21}

To develop mods for Minecraft 26.1, you will need JDK 25.

::: tip Recommended for Linux
On Linux, we recommend using [SDKMan!](https://sdkman.io/) to manage JDK installations.
It makes it easy to install and switch between different Java versions.
:::

If you need help installing Java, you can refer to the [Java installation guides](../../players/installing-java/).

## Set Up Your IDE {#set-up-your-ide}

To start developing mods with Fabric, you will need to set up a development environment using IntelliJ IDEA (recommended), or alternatively Visual Studio Code or Zed.

<ChoiceComponent :choices="[
  {
    name: 'IntelliJ IDEA',
    href: './intellij-idea/setting-up',
    icon: 'simple-icons:intellijidea',
    color: '#FE2857',
  },
  {
    name: 'Visual Studio Code',
    href: './vscode/setting-up',
    icon: 'codicon:vscode',
    color: '#007ACC',
  },
  {
    name: 'Zed',
    href: './zed/setting-up',
    icon: 'simple-icons:zedindustries',
    color: '#282C34',
  },
]" />
