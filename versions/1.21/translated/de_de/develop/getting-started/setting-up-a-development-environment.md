---
title: Entwicklungsumgebung einrichten
description: Ein Schritt-für-Schritt-Leitfaden, wie man eine Entwicklungsumgebung für die Erstellung von Mods mit Fabric einrichtet.
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
---

# Entwicklungsumgebung einrichten {#setting-up-a-development-environment}

Um mit der Entwicklung von Mods mit Fabric zu beginnen, musst du eine Entwicklungsumgebung mit IntelliJ IDEA einrichten.

## JDK 17 installieren {#installing-jdk-21}

Um Mods für Minecraft 1.21 zu entwickeln, benötigst du das JDK 17.

Wenn du Hilfe bei der Installation von Java benötigst, kannst du die verschiedenen Java-Installationsanleitungen im Abschnitt [Leitfäden für Spieler](../../players/index) nachlesen.

## IntelliJ IDEA installieren {#installing-intellij-idea}

:::info
Natürlich kannst du auch andere IDEs verwenden, wie zum Beispiel Eclipse oder Visual Studio Code, aber die meisten Seiten dieser Dokumentation gehen davon aus, dass du IntelliJ IDEA verwendest - wenn du eine andere IDE verwendest, solltest du die Dokumentation für deine IDE lesen.
:::

Wenn du IntelliJ IDEA nicht installiert hast, kannst du es von der [offiziellen Website](https://www.jetbrains.com/idea/download/) herunterladen - befolge die Installationsschritte für dein Betriebssystem.

Die Community-Edition von IntelliJ IDEA ist kostenlos und Open Source, und sie ist die empfohlene Version für das Modding mit Fabric.

Möglicherweise musst du nach unten scrollen, um den Download-Link für die Community-Edition zu finden - er sieht wie folgt aus:

![Aufforderung für den Download der IDEA Community Edition](/assets/develop/getting-started/idea-community.png)

## IDEA Plugins installieren {#installing-idea-plugins}

Obwohl diese Plugins nicht unbedingt notwendig sind, können sie das Modding mit Fabric erheblich erleichtern - deshalb solltest du in Erwägung ziehen, sie zu installieren.

### Minecraft Development {#minecraft-development}

Das Minecraft Development Plugin bietet Unterstützung für das Modding mit Fabric und ist das wichtigste Plugin, das man installieren sollte.

Du kannst es installieren, indem du IntelliJ IDEA öffnest und dann zu `File > Settings > Plugins > Marketplace Tab` navigierst - suche nach `Minecraft Development` in der Suchleiste und klicke dann auf die `Install` Schaltfläche.

Alternativ kannst du es von der [Plugin-Seite](https://plugins.jetbrains.com/plugin/8327-minecraft-development) herunterladen und dann installieren, indem du zu `File > Settings > Plugins > Install Plugin From Disk` navigierst.
