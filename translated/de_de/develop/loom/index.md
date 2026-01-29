---
title: Loom
description: Dokumentation für das Fabric Loom Gradle Plugin.
authors:
  - Atakku
  - caoimhebyrne
  - Daomephsta
  - JamiesWhiteShirt
  - Juuxel
  - kb-1000
  - modmuss50
  - SolidBlock-cn
---

Fabric Loom, oder kurz Loom, ist ein [Gradle](https://gradle.org/) Plugin für die Entwicklung von Mods im Fabric-Ökosystem.

Loom bietet Hilfsmittel, um Minecraft und Mods in einer Entwicklungsumgebung zu installieren, so dass du mit Blick auf die Minecraft-Verschleierung und dessen Unterschiede zwischen den Distributionen und Versionen verknüpfen kannst. Es bietet auch Laufkonfigurationen für die Verwendung mit dem Fabric Loader, der Mixin-Kompilierung und Hilfsmitteln für das Jar-in-Jar-System des Fabric Loaders.

Loom unterstützt _alle_ Versionen von Minecraft, auch die, die nicht offiziell von der Fabric API unterstützt werden, da es versionsunabhängig ist.

:::warning WICHTIG

Diese Seite ist eine Referenz für alle Optionen und Funktionen von Loom. Wenn du gerade erst anfängst, lies bitte die [Einführung in Fabric](../).

:::

## Plugin IDs {#plugin-ids}

Loom verwendet unterschiedliche Plugin IDs:

- `net.fabricmc.fabric-loom`, für nicht verschleierte Versionen (Minecraft 26.1 oder neuer)
- `net.fabricmc.fabric-loom-remap`, für verschleierte Versionen (Minecraft 1.21.11 oder älter)
- `fabric-loom` (veraltet), wird nur aus Gründen der Abwärtskompatibilität mit verschleierten Versionen unterstützt. Verwende stattdessen `net.fabricmc.fabric-loom-remap`
- `net.fabricmc.fabric-loom-companion`, in fortgeschrittenen Multiprojekt-Umgebungen. Lies mehr über [Unterprojekte](./classpath-groups#multi-project)

## Auf Unterprojekten aufbauen {#subprojects}

Beim Einrichten eines Multiprojekt-Builds, das von einem anderen Loom-Projekt abhängt, solltest du die Konfiguration `namedElements` verwenden, wenn du von dem anderen Projekt abhängig bist. Standardmäßig werden die "Ausgaben" eines Projekts auf Zwischennamen umgeschrieben. Die Konfiguration `namedElements` enthält die Projektausgaben, die nicht neu geordnet wurden.

```groovy
dependencies {
 implementation project(path: ":name", configuration: "namedElements")
}
```

Wenn du geteilte Quellensätze in einem Multiprojekt-Build verwendest, musst du auch eine Abhängigkeit für den Client-Quellensatz des anderen Projekts hinzufügen.

```groovy
dependencies {
 clientImplementation project(":name").sourceSets.client.output
}
```

## Client & Gemeinsamer Code aufteilen {#split-sources}

Eine häufige Ursache für Serverabstürze waren jahrelang Mods, die bei der Installation auf einem Server versehentlich reinen Client-Code aufriefen. Neuere Loom- und Loader-Versionen bieten die Möglichkeit, den gesamten Client-Code in einen eigenen Quellsatz zu verschieben. Dadurch wird das Problem bei der Kompilierung behoben, aber der Build führt immer noch zu einer einzigen JAR-Datei, die auf beiden Seiten funktioniert.

Der folgende Ausschnitt aus einer `build.gradle`-Datei zeigt, wie du dies für deinen Mod aktivieren kannst. Da dein Mod nun auf zwei Quellensätze aufgeteilt ist, musst du die neue DSL verwenden, um die Quellensätze deines Mods zu definieren. Dies ermöglicht es dem Fabric Loader, den Klassenpfad deiner Mods zusammenzufassen. Dies ist auch für einige andere komplexe Multiprojekt-Konfigurationen nützlich.

Minecraft 1.18 (1.19 empfohlen), Loader 0.14 und Loom 1.0 oder später sind nötig, um den Client und den Allgemeinen Code aufzuteilen.

```groovy
loom {
 splitEnvironmentSourceSets()

 mods {
   example-mod {
     sourceSet sourceSets.main
     sourceSet sourceSets.client
   }
 }
 }
```

## Fehler beheben {#issues}

Loom und/oder Gradle können manchmal aufgrund von beschädigten Cache-Dateien fehlschlagen. Das Ausführen von `./gradlew build --refresh-dependencies` zwingt Gradle und Loom dazu, alle Dateien neu herunterzuladen und neu zu erstellen. Dies kann einige Minuten dauern, reicht aber in der Regel aus, um Probleme mit dem Cache zu beheben.

## Einrichtung der Entwicklungsumgebung {#setup}

Loom ist so konzipiert, dass es sofort einsatzbereit ist, indem du einfach einen Arbeitsbereich in deiner IDE einrichtest. Es tut einiges hinter den Kulissen, um eine Entwicklungsumgebung mit Minecraft zu schaffen:

- Lädt die Client und Server Jar von den offiziellen Kanälen für die konfigurierte Minecraft Version herunter.
- Führt die Client- und das Server-Jar zusammen, um eine zusammengeführte Jar mit `@Environment`- und `@EnvironmentInterface`-Annotationen zu erzeugen.
- Lädt die konfigurierten Mappings herunter.
- Mappt die zusammengeführte Jar auf die Zwischen-Mappings, um eine Zwischen-Jar zu erzeugen.
- Stellt die zusammengeführte Jar auf Yarn-Mappings um, um eine gemappte Jar zu erzeugen.
- Optional: Dekompiliert die gemappte Jar, um eine gemappte Quellcode Jar und eine Linienmap zu erzeugen und wendet die Lininmap zu der gemappten Jar.
- Fügt Minecraft-Abhängigkeiten hinzu.
- Lädt Minecraft-Assets herunter.
- Verarbeitet und schließt Mod-erweiterte Abhängigkeiten ein.

## Caches {#caches}

- `${GRADLE_HOME}/caches/fabric-loom`: Der Usercache, ein Cache der für alle Loom-Projekte für einen Nutzer geteilt wird. Dient zum Zwischenspeichern von Minecraft-Assets, Jars, zusammengeführten Jars, Zwischen-Jars und gemappten Jars
- `.gradle/loom-cache`: Der persistente Cache des Stammprojektes, ein Cache der durch das Projekt und dessen Unterprojekten geteilt wird. Dient zum Zwischenspeichern von neu gemappten Mods sowie von generierten, eingeschlossenen Mod-Jars
- `**/build/loom-cache`: Der (Unter-) Projekt Build Cache

## Konfiguration von Abhängigkeiten {#configurations}

- `minecraft`: Definiert die Version von Minecraft, die in der Entwicklungsumgebung genutzt werden soll
- `mappings`: Definiert die Mappings, die in der Entwicklungsumgebung genutzt werden sollen.
- `modImplementation`, `modApi` und `modRuntime`: Erweiterte Varianten von `implementation`, `api` und `runtime` für Mod-Abhängigkeiten. Wird neu gemappt, um den Mappings in der Entwicklungsumgebung zu entsprechen und es werden alle verschachtelten Jars entfernt.
- `include`: Deklariert eine Abhängigkeit, die als eine Jar-in-Jar in dem `remapJar`-Ausgaben enthalten sein sollte. Diese Konfiguration von Abhängigkeiten ist nicht transitiv. Für nicht-Mod-Abhängigkeiten, wird Loom eine Mod-Jar mit einer `fabric.mod.json` unter Verwendung der Mod ID für den Namen und der selben Version generieren.

## Standardkonfiguration {#configuration}

- Wendet die folgenden Plugins an: `java`, `eclipse`
- Fügt die folgenden Maven Repositories hinzu: [Fabric](https://maven.fabricmc.net/), [Mojang](https://libraries.minecraft.net/) und Maven Central
- Konfiguriert die `eclipse` Aufgabe, um durch die Aufgabe `genEclipseRuns` finalisiert zu werden.
- Wenn ein `.idea`-Ordner im Stammprojekt vorhanden ist, lädt er Assets herunter (falls nicht aktuell) und installiert die Laufkonfiguration in `.idea/runConfigurations`.
- Fügt `net.fabricmc:fabric-mixin-compile-extensions` und dessen Abhängigkeiten mit der `annotationProcessor` Konfiguration für Abhängigkeiten hinzu
- Konfiguriert alle nicht-Test JavaCompile Aufgaben mit der Konfiguration für den Mixin Annotationsprozessor.
- Konfiguriert die Aufgabe `remapJar` so, dass sie eine JAR mit demselben Namen wie die Aufgabe `jar` ausgibt, und fügt dann eine "dev"-Kennzeichnung zur Aufgabe `jar` hinzu.
- Konfiguriert die Aufgabe `remapSourcesJar`, um die Ausgabe der Aufgabe `sourcesJar` zu verarbeiten, wenn die Aufgabe existiert.
- Fügt die `remapJar` Aufgabe und die `remapSourcesJar` Aufgabe als Abhängigkeit der `build` Aufgabe hinzu.
- Konfiguriert die `remapJar` Aufgabe und die `remapSourcesJar` Aufgabe, so dass diese, wenn ausgeführt, ihre Ausgaben als `archives`-Artifakte hinzufügen.
- Fügt für jede MavenPublication (aus dem `maven-publish` Plugin) manuell Abhängigkeiten an die POM für Mod-erweiterte Konfigurationen für Abhängigkeiten an, sofern die Konfiguration für Abhängigkeiten einen Maven-Bereich hat.

Alle Laufkonfigurationen haven das Laufverzeichnis `${projectDir}/run` und das VM-Argument `-Dfabric.development=true`. Die Hauptklassen für Laufkonfigurationen werden normalerweise durch eine `fabric-installer.json`-Datei im Stamm der JAR-Datei vom Fabric Loader definiert, wenn diese als Mod-Abhängigkeit enthalten ist, aber die Datei kann durch jede Mod-Abhängigkeit definiert werden. Wenn keine solche Datei gefunden wird, werden die Hauptklassen `net.fabricmc.loader.launch.knot.KnotClient` und `net.fabricmc.loader.launch.knot.KnotServer` verwendet.
