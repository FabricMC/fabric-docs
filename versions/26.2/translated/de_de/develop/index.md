---
title: Leitfäden für Entwickler
description: Unsere von der Community verfassten Leitfäden für Entwickler behandeln viele Themen, von der Erstellung eines Mods und der Einrichtung deiner Umgebung bis hin zu Rendering, Netzwerken, Datengenerierung und vielem mehr.
authors:
  - IMB11
  - its-miroma
  - itsmiir
authors-nogithub:
  - basil4088
resources:
  https://github.com/FabricMC: FabricMC-Organisation auf GitHub
  https://github.com/FabricMC/fabric-docs/tree/main/reference/26.2: In der Dokumentation referenzierte ExampleMod
  https://java-programming.mooc.fi/: "University of Helsinki: Java Programming MOOC"
  https://dev.java/learn/: "Java Platform Group von Oracle: Learn Java"
  https://www.codecademy.com/learn/learn-java: "Codecademy: Learn Java"
  https://www.coursera.org/specializations/java-programming: "Duke University (über Coursera): Spezialisierung Java-Programmierung und Grundlagen der Softwaretechnik"
  https://www.youtube.com/watch?v=A74TOX803D0: "freeCodeCamp (YouTube): Java Programming for Beginners"
  https://javabook.mccue.dev/: "Modern Java (Online-Lehrbuch)"
---

<!-- markdownlint-configure-file { MD033: { allowed_elements: [script, ul, li, a ] } } -->

<script setup lang="ts">
import { useData } from "vitepress";

const javaResources = Object.entries(useData().frontmatter.value.resources).slice(2);
</script>

Fabric ist ein leichtgewichtiger Modding-Werkzeugkasten für die Minecraft: Java Edition, die einfach und benutzerfreundlich gestaltet ist. Es ermöglicht Entwicklern, Modifikationen ("Mods") am Originalspiel vorzunehmen, um neue Funktionen hinzuzufügen oder bestehende Mechaniken zu ändern.

Diese Dokumentation führt dich durch das Modding mit Fabric, von der [Erstellung deines ersten Mods](./getting-started/creating-a-project) und der [Einrichtung deiner Umgebung](./getting-started/setting-up) bis hin zu fortgeschrittenen Themen wie [Rendering](./rendering/basic-concepts), [Netzwerken](./networking), [Datengenerierung](./data-generation/setup) und vielem mehr.

Sieh dir die Seitenleiste für eine Liste aller verfügbaren Seiten an.

::: tip

Falls du es zu irgendeinem Zeitpunkt benötigst, steht dir ein voll funktionsfähiger Mod mit dem gesamten Quellcode dieser Dokumentation im Ordner [`/reference` auf GitHub](https://github.com/FabricMC/fabric-docs/tree/main/reference/26.2) zur Verfügung.

:::

## Voraussetzungen {#prerequisites}

Bevor du startest mit Fabric zu modden, musst du über gewisse Kenntnisse in der Entwicklung mit Java und in der objektorientierten Programmierung im Allgemeinen verfügen.

Hier sind einige Ressourcen, die dir helfen könnten, dich mit Java und OOP vertraut zu machen:

<ul>
  <li v-for="[url, title] in javaResources" :key="url">
    <a :href="url" target="_blank" rel="noreferrer">{{ title }}</a>
  </li>
</ul>

## Was bietet Fabric? {#what-does-fabric-offer}

Das Fabric-Projekt konzentriert sich auf drei Hauptkomponenten:

- **Fabric Loader**: Ein flexibler, plattformunabhängiger Mod-Loader, der in erster Linie für die Minecraft: Java Edition entwickelt wurde
- **Fabric API**: Eine Reihe ergänzender APIs und Tools, die Mod-Entwickler bei der Erstellung von Mods verwenden können
- **Fabric Loom**: Ein [Gradle](https://gradle.org/) Plugin, das Entwicklern ermöglicht einfach Mods zu entwickeln und zu debuggen

### Was bietet die Fabric API? {#what-does-fabric-api-offer}

Die Fabric-API bietet eine Vielzahl von APIs, die auf den Standardfunktionen aufbauen und eine erweiterte oder einfachere Entwicklung ermöglichen.

Zum Beispiel bietet es neue Hooks, Events, Hilfsmittel wie transitive Zugriffserweiterungen, Zugriff auf interne Registries wie die Registry für kompostierbare Items und vieles mehr.
