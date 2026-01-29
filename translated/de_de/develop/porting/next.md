---
title: Zu 26.1 Snapshots portieren
description: Leitfaden für die Portierung auf Snapshots der nächsten Version von Minecraft.
authors:
  - cassiancc
---

Die kommende Version 26.1 von Minecraft ist nicht verschleiert, ebenso wie ihre Snapshots. Mit diesem Hintergrund musst du mehr Änderungen als üblich an deinen Buildskripten vornehmen, um die Portierung durchzuführen.

::: info

Dieses Dokumentation behandelt die Migration von **1.21.11** auf die Snapshots von **26.1**. Wenn du auf die **1.21.11** migrieren möchtest, lies den Artikel über die [Portierung auf 1.21.11](./current).

:::

Beachte, dass du, wenn dein Mod noch die Yarn-Mappings von Fabric verwendet, zunächst [deinen Mod auf die offiziellen Mappings von Mojang migrieren musst](../migrating-mappings/), bevor du ihn auf die 26.1 portieren kannst.

Wenn du die IntelliJ IDEA verwendest, musst du sie ebenfalls auf die Version `2025.3` oder höher aktualisieren, um die vollständige Unterstützung für Java 25 zu erhalten.

## Das Buildskript aktualisieren {#build-script}

Beginne damit, die Dateien `gradle/wrapper/gradle-wrapper.properties`, `gradle.properties` und `build.gradle` deines Mods auf die neuesten Versionen zu aktualisieren.

1. Aktualisiere Gradle auf die aktuellste Version, indem du den folgenden Befehl ausführst: `./gradlew wrapper --gradle-version latest`
2. Hebe Minecraft, den Fabric Loader, Fabric Loom und die Fabric API an, entweder in der `gradle.properties` (empfohlen) oder in der `build.gradle`. Die empfohlenen Versionen der Fabric-Komponenten findest du auf der [Fabric Develop-Website](https://fabricmc.net/develop/).
   - Beachte, dass 26.1-Snapshots keine stabilen Versionen von Minecraft sind und du diese Version daher manuell aus der Dropdown-Liste auswählen musst.
3. Ändere oben in der `build.gradle` die Version von Loom, die du verwendest, von `id "fabric-loom"` zu `id "net.fabricmc.fabric-loom"`. Wenn du Loom in der `settings.gradle` spezifizierst, ändere es dort auch.
4. Entferne die Zeile `mappings` von dem Abschnitt dependencies deiner `build.gradle`.
5. Ersetze jegliche Instanzen von `modImplementation` oder `modCompileOnly` mit `implementation` und `compileOnly`.
6. Entferne oder ersetze alle Mods, die für Versionen vor 26.1 erstellt wurden, durch Versionen, die mit diesem Update kompatibel sind.
   - Keine der vorhandenen Mods für Minecraft 1.21.11 oder ältere Versionen funktioniert mit 26.1, auch nicht als reine Kompilierungsabhängigkeit.
7. Ersetze alle Erwähnungen von `remapJar` mit `jar`.
8. Aktualisiere Gradle, indem du auf die Schaltfläche zur Aktualisierung in der oberen rechten Ecke von IntelliJ IDEA klickst. Wenn diese Schaltfläche nicht sichtbar ist, kannst du das Leeren der Caches erzwingen, indem du `./gradlew --refresh-dependencies` ausführst.

## Den Code aktualisieren {#porting-guides}

Nachdem das Buildskript auf 26.1 aktualisiert wurde, kannst du nun deinen Mod durchgehen und allen geänderten Code aktualisieren, um ihn mit dem Snapshot kompatibel zu machen.

:::warning WICHTIG

Da Minecraft 26.1 noch in der Snapshot Phase ist, gibt es noch keine Dokumentation zu spezifischen Änderungen. Viel Glück, du bist auf dich allein gestellt!

:::

<!---->
