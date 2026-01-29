---
title: VS Code einrichten
description: Ein Schritt-für-Schritt-Leitfaden, wie man Visual Studio Code für die Erstellung von Mods mit Fabric einrichtet.
authors:
  - dicedpixels
prev:
  text: Deine Entwicklungsumgebung einrichten
  link: ../setting-up
next:
  text: Ein Projekt in VS Code öffnen
  link: ./opening-a-project
---

<!---->

:::warning WICHTIG

Es ist zwar möglich, Mods mit Visual Studio Code zu entwickeln, wir raten jedoch davon ab.
Erwäge die Verwendung vo [IntelliJ IDEA](../intellij-idea/setting-up), das über spezielle Java-Tools, erweiterte Funktionen und nützliche, von der Community entwickelte Plugins wie **Minecraft Development** verfügt.

:::

:::info VORAUSSETZUNGEN

Stelle sicher, dass du zuerst [ein JDK installiert hast](../setting-up#install-jdk-21).

:::

## Installation {#installation}

Du kannst Visual Studio Code von [code.visualstudio.com](https://code.visualstudio.com/) oder über deinen bevorzugten Paketmanager herunterladen.

![Visual Studio Code Download Seite](/assets/develop/getting-started/vscode/download.png)

## Voraussetzungen {#prerequisites}

Visual Studio Code bietet standardmäßig keine Unterstützung für die Programmiersprache Java. Microsoft bietet jedoch ein praktisches Erweiterungspaket an, das alle erforderlichen Erweiterungen enthält, um die Java-Sprachunterstützung zu aktivieren.

Du kannst dieses Erweiterungspaket über den [Visual Studio Marketplace](https://marketplace.visualstudio.com/items?itemName=vscjava.vscode-java-pack) installieren.

![Erweiterungspaket für Java](/assets/develop/getting-started/vscode/extension.png)

Oder innerhalb von Visual Studio Code selbst über die Erweiterungsansicht.

![Erweiterungspaket für Java in der Erweiterungsansicht](/assets/develop/getting-started/vscode/extension-view.png)

Die Erweiterung **Language Support for Java** zeigt dir einen Startbildschirm zur Einrichtung eines JDK an. Du kannst dies machen, falls du es noch nicht gemacht hast.
