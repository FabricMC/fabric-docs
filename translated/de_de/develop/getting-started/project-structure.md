---
title: Projektstruktur
description: Ein Überblick über die Struktur eines Fabric-Mod-Projekts.
authors:
  - IMB11
---

Auf dieser Seite wird die Struktur eines Fabric-Mod-Projekts und der Zweck der einzelnen Dateien und Ordner im Projekt erläutert.

## `fabric.mod.json` {#fabric-mod-json}

Die Datei [`fabric.mod.json`](../loader/fabric-mod-json) ist die Hauptdatei, die deinen Mod dem Fabric Loader beschreibt. Sie enthält Informationen wie die ID der Mod, die Version und die Abhängigkeiten.

Die wichtigsten Felder in der Datei `fabric.mod.json` sind:

- `id`: Die Mod-ID, Welche einzigartig sein sollte.
- `name`: Der Name der Mod.
- `environment`: Die Umgebung in der deine Mod läuft, wie beispielsweise `client`, `server`, oder `*` für beide.
- `entrypoints`: Die Einstiegspunkte, die deine Mod bereitstellt, wie beispielsweise `main` oder `client`.
- `depends`: Die Mods, von denen deine Mod abhängt.
- `mixins`: Die Mixins, die deine Mod bereitstellt.

## Einstiegspunkte {#entrypoints}

Wie bereits zuvor erwähnt, enthält die Datei [`fabric.mod.json`](../loader/fabric-mod-json) ein Feld mit dem Namen `entrypoints` - dieses Feld wird verwendet, um die Einstiegspunkte anzugeben, die dein Mod bereitstellt.

Der Template-Mod-Generator erstellt standardmäßig sowohl einen `main`- als auch einen `client`-Einstiegspunkt:

- Der Einstiegspunkt `main` wird für allgemeinen Code verwendet und ist in einer Klasse enthalten, die `ModInitializer` implementiert
- Der `client` Einstiegspunkt wird für clientspezifischen Code genutzt und seine Klasse implementiert `ClientModInitializer`

Diese Einstiegspunkte werden jeweils aufgerufen, wenn das Spiel beginnt.

Hier ist ein Beispiel für einen einfachen `main`-Einstiegspunkt, der eine Nachricht an die Konsole ausgibt, wenn das Spiel startet:

<<< @/reference/latest/src/main/java/com/example/docs/ExampleMod.java#entrypoint

## `src/main/resources` {#src-main-resources}

Der Ordner `src/main/resources` wird verwendet, um die Ressourcen zu speichern, die deine Mod verwendet, wie Texturen, Modelle und Sounds.

Es ist auch der Ort, an dem sich die Datei `fabric.mod.json` und alle Mixin-Konfigurationsdateien befinden, die deine Mod verwendet.

Assets werden in einer Struktur gespeichert, die die Struktur von Ressourcenpaketen widerspiegelt - eine Textur für einen Block würde zum Beispiel in `assets/example-mod/textures/block/block.png` gespeichert werden.

## `src/client/resources` {#src-client-resources}

Der Ordner `src/client/resources` wird verwendet, um Client-spezifische Ressourcen zu speichern, wie Texturen, Modelle und Sounds, die nur auf der Client-Seite verwendet werden.

## `src/main/java` {#src-main-java}

Der Ordner `src/main/java` wird verwendet, um den Java-Quellcode für deine Mod zu speichern - er existiert sowohl auf der Client- als auch auf der Serverumgebung.

## `src/client/java` {#src-client-java}

Der Ordner `src/client/java` wird verwendet, um clientspezifischen Java-Quellcode zu speichern, wie zum Beispiel Rendering-Code oder clientseitige Logik - wie zum Beispiel Blockfarbenprovider.
