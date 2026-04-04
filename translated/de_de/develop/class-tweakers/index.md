---
title: Klassenoptimierer
description: Lerne, was Klassenoptimierer sind und wie man sie einrichtet.
authors:
  - cassiancc
  - Earthcomputer
  - its-miroma
  - MildestToucan
---

Klassenoptimierer, die früher als Zugriffserweiterer bezeichnet wurden, bevor sie um weitere Funktionen erweitert wurden, bieten Transformationswerkzeuge, die die Bytecode-Bearbeitung durch Mixins ergänzen. Außerdem ermöglichen sie den Zugriff auf bestimmte Laufzeitänderungen innerhalb der Entwicklungsumgebung.

::: warning

Klassenoptimierer sind nicht an eine bestimmte Minecraft-Version gebunden, stehen jedoch erst ab Fabric Loader 0.18.0 und Loom 1.12 zur Verfügung und können nur auf Vanilla-Minecraft-Klassen angewendet werden.

:::

## Einrichtung {#setup}

### Dateiformat {#file-format}

Klassenoptimierer-Dateien werden üblicherweise nach deiner Mod-ID benannt, z. B. `example-mod.classtweaker`, damit IDE-Plugins sie leichter erkennen können. Sie sollten im Ordner `resources` gespeichert werden.

Die Datei muss in der ersten Zeile die folgende Kopfzeile enthalten:

```classtweaker
classTweaker v1 official
```

Klassenoptimierer Dateien können Leerzeilen und Kommentare enthalten, die mit `#` beginnen. Kommentare können am Ende einer Zeile beginnen.

Der Syntax kann je nach verwendeter Funktion variieren, doch werden Änderungen jeweils als "Einträge" in separaten Zeilen deklariert und beginnen mit einer "Richtlinie", die die Art der anzuwendenden Änderung angibt.
Die Elemente eines Eintrags können durch beliebige Leerzeichen, einschließlich Tabulatoren, voneinander getrennt werden.

#### Transitive Einträge {#transitive-entries}

Damit deine Änderungen am dekompilierten Quellcode für Mods sichtbar sind, die von deinem Mod abhängen, füge der Anweisung den Präfix `transitive-` hinzu:

```classtweaker:no-line-numbers
# Transitive Access Widening directives
transitive-accessible
transitive-extendable
transitive-mutable

# Transitive Interface Injection directive
transitive-inject-interface
```

### Den Dateiort angeben {#specifying-the-file-location}

Der Speicherort der Klassenoptimierer Datei muss in deinen Dateien `build.gradle` und `fabric.mod.json` angegeben werden. Denke daran, dass du außerdem Fabric Loader 0.18.0 oder höher benötigst, um Klassenoptimierer verwenden zu können.

Die Spezifikationen sind weiterhin nach Zugriffserweiterern benannt, um die Abwärtskompatibilität zu gewährleisten.

#### build.gradle {#build-gradle}

@[code lang=gradle:no-line-numbers transcludeWith=:::classtweaker-setup:gradle:::](@/reference/latest/build.gradle)

#### fabric.mod.json {#fabric-mod-json}

```json:no-line-numbers
...

"accessWidener": "example-mod.classtweaker",

...
```

Nachdem du den Speicherort der Datei in deiner Datei `build.gradle` angegeben hast, lade dein Gradle-Projekt in der IDE unbedingt neu.

## Die Datei validieren {#validating-the-file}

Standardmäßig ignorieren Klassenoptimierer Einträge, die auf nicht auffindbare Änderungsziele verweisen. Um zu überprüfen, ob alle in der Datei angegebenen Klassen, Felder und Methoden gültig sind, führe die Gradle-Aufgabe `validateAccessWidener` aus.

Fehlermeldungen weisen zwar auf jeden ungültigen Eintrag hin, geben jedoch nicht unbedingt an, welcher Teil des Eintrag ungültig ist.
