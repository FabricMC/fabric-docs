---
title: Einen Mod in IntelliJ bauen
description: Lerne, wie du IntelliJ IDEA verwendest, um einen Minecraft Mod zu bauen, der geteilt oder in einer Produktivumgebung getestet werden kann.
authors:
  - cassiancc
  - cputnam-a11y
  - gdude2002
  - Scotsguy
prev:
  text: Quellcode in IntelliJ IDEA generieren
  link: ./generating-sources
next:
  text: Tipps und Tricks für IntelliJ IDEA
  link: ./tips-and-tricks
---

Öffne in IntelliJ IDEA die Registerkarte `Gradle` auf der rechten Seite und führe unter Tasks `build` aus. Die JAR-Dateien sollten im Ordner `build/libs` deines Projektverzeichnis erscheinen. Verwende außerhalb der Entwicklung die JAR-Datei mit dem kürzesten Namen.

![Die Seitenleiste von IntelliJ IDEA mit einer hervorgehobenen Build-Aufgabe](/assets/develop/getting-started/build-idea.png)

![Der Ordner build/libs mit der korrigierten Datei hervorgehoben](/assets/develop/getting-started/build-libs.png)

## Installieren und Teilen {#installing-and-sharing}

Von dort aus kann der Mod [wie gewohnt installiert werden](../../../players/installing-mods) oder auf vertrauenswürdige Mod-Hosting-Seiten wie [CurseForge](https://www.curseforge.com/minecraft) und [Modrinth](https://modrinth.com/discover/mods) hochgeladen werden.
