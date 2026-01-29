---
title: Entwickleranleitungen
description: Unsere von der Community verfassten Leitfäden für Entwickler behandeln viele Themen, von der Erstellung eines Mods und der Einrichtung deiner Umgebung bis hin zu Rendering, Netzwerken, Datengenerierung und vielem mehr.
authors:
  - IMB11
  - its-miroma
  - itsmiir
authors-nogithub:
  - basil4088
---

Fabric ist ein leichtgewichtiger Modding-Werkzeugkasten für die Minecraft: Java Edition, die einfach und benutzerfreundlich gestaltet ist. Es ermöglicht Entwicklern, Modifikationen ("Mods") am Originalspiel vorzunehmen, um neue Funktionen hinzuzufügen oder bestehende Mechaniken zu ändern.

Diese Dokumentation führt dich durch das Modding mit Fabric, von der [Erstellung deines ersten Mods](./getting-started/creating-a-project) und der [Einrichtung deiner Umgebung](./getting-started/setting-up) bis hin zu fortgeschrittenen Themen wie [Rendering](./rendering/basic-concepts), [Netzwerken](./networking), [Datengenerierung](./data-generation/setup) und vielem mehr.

Sieh dir die Seitenleiste für eine Liste aller verfügbaren Seiten an.

::: tip

Falls du es zu irgendeinem Zeitpunkt benötigst, steht dir ein voll funktionsfähiger Mod mit dem gesamten Quellcode dieser Dokumentation im Ordner [`/reference` auf GitHub](https://github.com/FabricMC/fabric-docs/tree/main/reference/latest) zur Verfügung.

:::

## Voraussetzungen {#prerequisites}

Bevor du startest mit Fabric zu modden, musst du über gewisse Kenntnisse in der Entwicklung mit Java und in der objektorientierten Programmierung im Allgemeinen verfügen.

Hier sind einige Ressourcen, die dir helfen könnten, dich mit Java und OOP vertraut zu machen:

- [W3: Java-Tutorials](https://www.w3schools.com/java/)
- [Codecademy: Learn Java](https://www.codecademy.com/learn/learn-java)
- [W3: Java OOP](https://www.w3schools.com/java/java_oop.asp)
- [Medium: Introduction to OOP](https://medium.com/@Adekola_Olawale/beginners-guide-to-object-oriented-programming-a94601ea2fbd)

## Was bietet Fabric? {#what-does-fabric-offer}

Das Fabric-Projekt konzentriert sich auf drei Hauptkomponenten:

- **Fabric Loader**: Ein flexibler, plattformunabhängiger Mod-Loader, der in erster Linie für die Minecraft: Java Edition entwickelt wurde
- **Fabric API**: Eine Reihe ergänzender APIs und Tools, die Mod-Entwickler bei der Erstellung von Mods verwenden können
- **Fabric Loom**: Ein [Gradle](https://gradle.org/) Plugin, das Entwicklern ermöglicht einfach Mods zu entwickeln und zu debuggen

### Was bietet die Fabric API? {#what-does-fabric-api-offer}

Die Fabric-API bietet eine Vielzahl von APIs, die auf den Standardfunktionen aufbauen und eine erweiterte oder einfachere Entwicklung ermöglichen.

Zum Beispiel bietet es neue Hooks, Events, Hilfsmittel wie transitive Zugriffserweiterungen, Zugriff auf interne Registries wie die Registry für kompostierbare Items und vieles mehr.
