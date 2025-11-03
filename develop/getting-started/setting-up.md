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
outline: false
---

## Install JDK 21 {#install-jdk-21}

To develop mods for Minecraft 1.21.10, you will need JDK 21.

If you need help installing Java, you can refer to the various Java installation guides in the [player guides section](../../players/index).

## Set Up Your IDE {#set-up-your-ide}

To start developing mods with Fabric, you will need to set up a development environment using IntelliJ IDEA (recommended), or alternatively Visual Studio Code.

<ChoiceComponent :choices="[
  {
    name: 'IntelliJ IDEA',
    href: './intellij-idea/opening-a-project',
    icon: 'simple-icons:intellijidea',
    color: '#FE2857',
  },
  {
    name: 'Visual Studio Code',
    href: './vscode/opening-a-project',
    icon: 'codicon:vscode',
    color: '#007ACC',
  },
]" />
