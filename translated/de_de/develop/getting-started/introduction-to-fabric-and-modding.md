---
title: Einführung in Fabric und Modding
description: "Eine kurze Einführung in Fabric und Modding in Minecraft: Java Edition."
authors:
  - IMB11
  - itsmiir
authors-nogithub:
  - basil4088
---

# Einführung in Fabric und Modding

## Voraussetzungen

Bevor du anfängst, solltest du ein Grundverständnis für die Entwicklung mit Java und ein Verständnis für objektorientierte Programmierung (OOP) haben.

Wenn du mit diesen Konzepten nicht vertraut bist, solltest du dir einige Tutorials zu Java und OOP ansehen, bevor du mit dem Modding beginnst.

- [W3: Java Tutorials](https://www.w3schools.com/java/)
- [Codecademy: Java lernen](https://www.codecademy.com/learn/learn-java)
- [W3: Java OOP](https://www.w3schools.com/java/java_oop.asp)
- [Medium: Einführung in die OOP](https://medium.com/@Adekola_Olawale/beginners-guide-to-object-oriented-programming-a94601ea2fbd)

### Begriffserklärung

Bevor wir beginnen, wollen wir einige Begriffe erläutern, die bei der Arbeit mit Fabric vorkommen werden:

- **Mod**: Eine Modifikation des Spiels, die neue Funktionen hinzufügt oder bestehende ändert.
- **Mod-Loader**: Ein Tool, das Mods in das Spiel lädt, wie zum Beispiel der Fabric Loader.
- **Mixin**: Ein Werkzeug zum Ändern des Spielcodes zur Laufzeit - siehe [Mixin-Einführung](https://fabricmc.net/wiki/tutorial:mixin_introduction) für weitere Informationen.
- **Gradle**: Ein Build-Automatisierungstool zum Erstellen und Kompilieren von Mods, das von Fabric zum Erstellen von Mods verwendet wird.
- **Mappings**: Eine Reihe von Mappings, die obfuskierten Code auf menschenlesbaren Code abbilden.
- **Obfuskation**: Der Prozess, der Code schwer verständlich macht und von Mojang verwendet wird, um den Code von Minecraft zu schützen.
- **Remapping**: Der Prozess der Umwandlung von obfuskierten Code in für Menschen lesbaren Code.

## Was ist Fabric? {#what-is-fabric}

Fabric ist eine leichtgewichtige Modding-Werkzeugbox für Minecraft: Java Edition.

Sie ist als einfache und leicht zu bedienende Modding-Plattform konzipiert. Fabric ist ein von der Gemeinschaft getragenes Projekt und ist Open Source, was bedeutet, dass jeder zu dem Projekt beitragen kann.

Du solltest die vier Hauptkomponenten von Fabric kennen:

- **Fabric Loader**: Ein flexibler, plattformunabhängiger Mod-Loader für Minecraft und andere Spiele und Anwendungen.
- **Fabric Loom**: Ein Gradle-Plugin, das es Entwicklern ermöglicht, Mods einfach zu entwickeln und zu debuggen.
- **Fabric API**: Eine Reihe von APIs und Werkezuge für Mod-Entwickler, die du bei der Erstellung von Mods verwenden kannst.
- **Yarn**: Eine Reihe offener Minecraft-Mappings, die unter der Creative Commons Zero-Lizenz für jeden frei nutzbar sind.

## Warum wird Fabric für das Modden von Minecraft benötigt? {#why-is-fabric-necessary-to-mod-minecraft}

> Beim Modding wird ein Spiel modifiziert, um sein Verhalten zu ändern oder neue Funktionen hinzuzufügen - im Fall von Minecraft kann dies alles sein, vom Hinzufügen neuer Items, Blöcke oder Entitäten bis hin zur Änderung der Spielmechanik oder dem Hinzufügen neuer Spielmodi.

Minecraft: Die Java Edition wird von Mojang obfuskiert, was Modifikationen allein schwierig macht. Mit Hilfe von Modding-Werkzeugen wie Fabric wird das Modding jedoch viel einfacher. Es gibt verschiedene Mapping-Systeme, die bei diesem Prozess helfen können.

Loom wandelt den obfuskierten Code mit Hilfe dieser Mappings in ein für Menschen lesbares Format um, was es Moddern erleichtert, den Code des Spiels zu verstehen und zu verändern. Yarn ist eine beliebte und ausgezeichnete Wahl für diese Mappings, aber es gibt auch andere Optionen. Jedes Mapping-Projekt kann seine eigenen Stärken oder Schwerpunkte haben.

Mit Loom kannst du auf einfache Weise Mods entwickeln und Mods gegen remapped Code kompilieren, und mit Fabric Loader kannst du diese Mods in das Spiel laden.

## Was bietet die Fabric API und warum ist sie nötig? {#what-does-fabric-api-provide-and-why-is-it-needed}

> Fabric API ist eine Sammlung von APIs und Werkzeugen, die Mod-Entwickler bei der Erstellung von Mods verwenden können.

Die Fabric API bietet eine Vielzahl von APIs, die auf der bestehenden Funktionalität von Minecraft aufbauen - zum Beispiel neue Hooks und Events, die Modder nutzen können, oder neue Utilities und Werkzeuge, die das Modding vereinfachen - wie zum Beispiel Access Wideners und die Möglichkeit, auf interne Registrys zuzugreifen, wie zui, Beispiel die Registry für kompostierbare Items.

Während die Fabric API leistungsstarke Funktionen bietet, können einige Aufgaben, wie zum Beispiel die grundlegende Blockregistrierung, auch ohne sie mit den Vanilla APIs durchgeführt werden.
