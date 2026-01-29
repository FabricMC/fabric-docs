---
title: Einrichten deiner Entwicklungsumgebung
description: Ein Schritt-für-Schritt-Leitfaden, wie man eine Entwicklungsumgebung für die Erstellung von Mods mit Fabric einrichtet.
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

## JDK 21 installieren {#install-jdk-21}

Um Mods für Minecraft 1.21.11 zu entwickeln, benötigst du ein JDK 21.

Wenn du Hilfe bei der Installation von Java benötigst, kannst du den [Leitfaden zur Java Installation](../../players/installing-java/) zu Rate ziehen.

## Einrichten deiner Entwicklungsumgebung {#set-up-your-ide}

Um mit der Entwicklung von Mods mit Fabric zu beginnen, musst du eine Entwicklungsumgebung mit IntelliJ IDEA (empfohlen) oder alternativ Visual Studio Code einrichten.

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
]" />
