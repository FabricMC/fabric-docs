---
title: Klassenpfadgruppen
description: Dokumentation für die Fabric Loom Klassenpfadgruppen Funktion.
authors:
  - modmuss50
---

Loom bietet eine DSL zur Konfiguration der System-Eigenschaft Klassenpfadgruppen des Fabric-Loaders. Dadurch kann der Fabric Loader verschiedene Klassenpfadeinträge gruppieren, was für Mods nützlich ist, die ihren Code in mehrere Quellensätze aufteilen, z. B. Client- und Common-Code oder Common- und plattformspezifischer Code. Dies kann wichtig sein, um sicherzustellen, dass deine Mod-Ressourcen korrekt geladen werden. Wenn du einen einzelnen Mod hast, der aus mehreren Quellensätzen besteht, solltest du alle Quellensätze im Block `loom.mods` definieren, um sicherzustellen, dass Fabric Loader sie richtig gruppieren kann. Diese Funktion gilt nur, wenn du das Spiel in deiner Entwicklungsumgebung ausführst, und hat keinen Einfluss auf die endgültige Mod-JAR-Datei, die von deinem Build erstellt wird (da alles in einer einzigen JAR-Datei gepackt ist).

```groovy
loom {
    mods {
        "example-mod" {
            sourceSet sourceSets.main
            sourceSet sourceSets.client
        }
    }
}
```

Im obigen Beispiel wird `example-mod` aus zwei Quellsätzen gebaut: `main` und `client`. Loom konfiguriert den Fabric Loader so, dass diese Quellensätze unter derselben Klassenpfadgruppe zusammengefasst werden, um sicherzustellen, dass sie zur Laufzeit korrekt geladen werden.

```groovy
loom {
    mods {
        "example-mod" {
            sourceSet sourceSets.main
            sourceSet sourceSets.client
        }
        "example-mod-test" {
            sourceSet sourceSets.testmod
        }
    }
}
```

Im obigen Beispiel wird der `example-mod-test` aus einem einzigen Quellensatz erstellt: `testmod`. Loom konfiguriert den Fabric Loader so, dass dieser Quellensatz unter seiner eigenen Klassenpfadgruppe gruppiert wird, getrennt von `example-mod`.

## Subprojekte {#multi-project}

Wenn du Mods definieren möchtest, die sich über mehrere Gradle-Projekte erstrecken (üblich bei Multi-Plattform-Setups), kannst du dies tun, indem du den Namen des Quellsatzes und den Projektpfad angibst.

```groovy
loom {
    mods {
        "example-mod" {
            sourceSet sourceSets.main
            sourceSet("main", ":core")
        }
    }
}
```

Wenn das Projekt, auf das du angewiesen bist, Loom nicht verwendet, musst du das Plugin `net.fabricmc.fabric-loom-companion` auf dieses Projekt anwenden. Dadurch kann das Loom-Projekt auf die erforderlichen Daten in einer Weise zugreifen, die den Best Practices von Gradle entspricht. Dieses Plugin gibt nur die notwendigen Informationen für Klassenpfadgruppen weiter und wendet keine der anderen Loom-Funktionen an.

```groovy
plugins {
    id 'net.fabricmc.fabric-loom-companion'
}
```

## Eingebettete Abhängigkeiten {#shaded-dependencies}

Wenn du Abhängigkeiten in deine Mod-JAR-Datei einbindest, solltest du auch die Konfiguration definieren, die die eingebundenen Abhängigkeiten im Block `loom.mods` enthält. Dies stellt sicher, dass der Fabric Loader eingebettete Abhängigkeiten richtig mit dem Code deines Mods gruppieren kann. Du solltest dies nicht für andere Mod-Abhängigkeiten oder Abhängigkeiten tun, die du mit `include` in Jar-in-Jar-Dateien einbindest.

```groovy
loom {
    mods {
        "example-mod" {
            sourceSet sourceSets.main
            configuration configurations.shade
        }
    }
}
```
