---
title: Projektstruktur
description: Ein Überblick über die Struktur eines Fabric-Mod-Projekts.
authors:
  - IMB11

search: false
---

# Projektstruktur

Auf dieser Seite wird die Struktur eines Fabric-Mod-Projekts und der Zweck der einzelnen Dateien und Ordner im Projekt erläutert.

## `fabric.mod.json`

Die Datei `fabric.mod.json` ist die Hauptdatei, die deinen Mod für den Fabric Loader beschreibt. Sie enthält Informationen wie die ID des Mods, die Version und die Abhängigkeiten.

Die wichtigsten Felder in der Datei `fabric.mod.json` sind:

- `id`: Die Mod-ID, Welche einzigartig sein sollte.
- `name`: Der Name des Mods.
- `environment`: Die Umgebung in der dein Mod läuft, wie beispielsweise `client`, `server`, oder `*` für beide.
- `entrypoints`: Die Einstiegspunkte, die dein Mod bereitstellt, wie beispielsweise `main` oder `client`.
- `depends`: Die Mods, von denen dein Mod abhängt.
- `mixins`: Die Mixins, die dein Mod bereitstellt.

Nachfolgend siehst du eine Beispieldatei `fabric.mod.json` - dies ist die Datei `fabric.mod.json` für das Referenzprojekt, das diese Dokumentationsseite betreibt.

:::details Referenzprojekt `fabric.mod.json`
@[code lang=json](@/reference/latest/src/main/resources/fabric.mod.json)
:::

## Einstiegspunkte

Wie bereits erwähnt, enthält die Datei `fabric.mod.json` ein Feld namens `entrypoints` - dieses Feld wird verwendet, um die Einstiegspunkte anzugeben, die dein Mod bereitstellt.

Der Vorlagen-Mod-Generator erstellt standardmäßig sowohl einen `main`- als auch einen `client`-Einstiegspunkt - der `main`-Einstiegspunkt wird für allgemeinen Code verwendet, der `client`-Einstiegspunkt für Client-spezifischen Code. Diese Einstiegspunkte werden jeweils aufgerufen, wenn das Spiel beginnt.

@[code lang=java transcludeWith=#entrypoint](@/reference/latest/src/main/java/com/example/docs/FabricDocsReference.java)

Das obige ist ein Beispiel für einen einfachen `main`-Einstiegspunkt, der eine Nachricht an die Konsole ausgibt, wenn das Spiel startet.

## `src/main/resources`

Der Ordner `src/main/resources` wird verwendet, um die Ressourcen zu speichern, die dein Mod verwendet, wie Texturen, Modelle und Sounds.

Es ist auch der Ort, an dem sich die Datei `fabric.mod.json` und alle Mixin-Konfigurationsdateien befinden, die dein Mod verwendet.

Assets werden in einer Struktur gespeichert, die die Struktur von Ressourcenpaketen widerspiegelt - eine Textur für einen Block würde zum Beispiel in `assets/modid/textures/block/block.png` gespeichert werden.

## `src/client/resources`

Der Ordner `src/client/resources` wird verwendet, um Client-spezifische Ressourcen zu speichern, wie Texturen, Modelle und Sounds, die nur auf der Client-Seite verwendet werden.

## `src/main/java`

Der Ordner `src/main/java` wird verwendet, um den Java-Quellcode für deinen Mod zu speichern - er existiert sowohl auf der Client- als auch auf der Serverumgebung.

## `src/client/java`

Der Ordner `src/client/java` wird verwendet, um clientspezifischen Java-Quellcode zu speichern, wie zum Beispiel Rendering-Code oder clientseitige Logik - wie zum Beispiel Blockfarbenprovider.
