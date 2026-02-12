---
title: IntelliJ IDEA einrichten
description: Ein Schritt-für-Schritt-Leitfaden, wie man IntelliJ IDEA für die Erstellung von Mods mit Fabric einrichtet.
authors:
  - 2xsaiko
  - Andrew6rant
  - asiekierka
  - Daomephsta
  - dicedpixels
  - falseresync
  - IMB11
  - liach
  - mkpoli
  - modmuss50
  - natanfudge
  - SolidBlock-cn
  - TelepathicGrunt
authors-nogithub:
  - siglong
prev:
  text: Deine Entwicklungsumgebung einrichten
  link: ../setting-up
next:
  text: Ein Projekt in IntelliJ IDEA öffnen
  link: ./opening-a-project
---

<!---->

:::info VORAUSSETZUNGEN

Stelle sicher, dass du zuerst [ein JDK installiert hast](../setting-up#install-jdk-21).

:::

## IntelliJ IDEA installieren {#installing-intellij-idea}

Wenn du IntelliJ IDEA nicht installiert hast, kannst du es von der [offiziellen Website](https://www.jetbrains.com/idea/download/) herunterladen - befolge die Installationsschritte für dein Betriebssystem.

![IntelliJ IDEA Download Aufforderung](/assets/develop/getting-started/idea-download.png)

## "Minecraft Development" Plugin installieren {#installing-idea-plugins}

Das Minecraft Development Plugin bietet Unterstützung für das Modding mit Fabric und ist das wichtigste Plugin, das man installieren sollte.

::: tip

Obwohl dieses Plugin nicht unbedingt notwendig ist, kannst es das Modding mit Fabric sehr erleichtern - deshalb solltest du in Betracht ziehen, es zu installieren.

:::

Du kannst es installieren, indem du IntelliJ IDEA öffnest und dann zu **File** > **Settings** > **Plugins** > **Marketplace** Tab navigierst - suche nach `Minecraft Development` in der Suchleiste und klicke dann auf die **Install** Schaltfläche.

Alternativ kannst du es von der [Plugin-Seite](https://plugins.jetbrains.com/plugin/8327-minecraft-development) herunterladen und dann installieren, indem du zu **File** > **Settings** > **Plugins** > **Install Plugin From Disk** navigierst.

### Über das Erstellen von Projekten {#about-creating-a-project}

Es ist zwar möglich, ein Projekt mit dem Plugin "Minecraft Development" von IntelliJ Idea zu erstellen, dies wird jedoch nicht empfohlen, da die Vorlagen oft veraltet sind. Wir empfehlen, mit [Ein Projekt erstellen](../creating-a-project) fortzufahren, um den Fabric Template Mod Generator zu verwenden.
