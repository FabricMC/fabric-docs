---
title: Zu 26.1 portieren
description: Leitfaden für die Portierung auf Minecraft 26.1, die aktuellste Version von Minecraft.
authors:
  - cassiancc
  - ChampionAsh5357
resources:
  https://fabricmc.net/2026/03/14/261.html: Fabric für Minecraft 26.1
  ./26.1/fabric-api: Leitfaden zur Portierung auf die Fabric API 26.1
  https://minecraft.wiki/w/Java_Edition_26.1: Java Edition 26.1 - Minecraft Wiki
  https://github.com/neoforged/.github/blob/main/primers/26.1/index.md: ChampionAsh5357's 1.21.11 -> 26.1 Migration Primers
---

Die Version 26.1 von Minecraft ist nicht verschleiert, ebenso wie ihre Snapshots. Vor diesem Hintergrund musst du mehr Änderungen als üblich an deinen Buildskripten vornehmen, um die Portierung durchzuführen.

::: info

Dieses Dokumentation behandelt die Migration von **1.21.11** auf **26.1**. Wenn du nach einer anderen Migration suchst, wechsle mithilfe des Dropdown-Menüs in der oberen rechten Ecke zur Zielversion.

:::

## Voraussetzungen {#prerequisites}

Wenn dein Mod noch die Yarn-Mappings von Fabric verwendet, musst du zunächst [deinen Mod auf die offiziellen Mappings von Mojang migrieren](../../../develop/porting/mappings/), bevor du ihn auf die 26.1 portieren kannst.

Wenn du die IntelliJ IDEA verwendest, musst du sie ebenfalls auf die Version `2025.3` oder höher aktualisieren, um die vollständige Unterstützung für Java 25 zu erhalten.

## Das Buildskript aktualisieren {#build-script}

Beginne damit, die Dateien `gradle/wrapper/gradle-wrapper.properties`, `gradle.properties` und `build.gradle` deines Mods auf die neuesten Versionen zu aktualisieren und folge dann den unteren Schritten. Solltest du auf Probleme stoßen, empfehlen wir dir, einen Blick in den [Fabric Beispielmod](https://github.com/FabricMC/fabric-example-mod/tree/26.1) zu werfen.

1. Aktualisiere Gradle auf die aktuellste Version, indem du den folgenden Befehl ausführst: `./gradlew wrapper --gradle-version latest`
2. Hebe Minecraft, den Fabric Loader, Fabric Loom und die Fabric API an, entweder in der `gradle.properties` (empfohlen) oder in der `build.gradle`. Die empfohlenen Versionen der Fabric-Komponenten findest du auf der [Fabric Develop-Seite](https://fabricmc.net/develop/).
3. Ändere oben in der `build.gradle` die Version von Loom, die du verwendest, von `id "fabric-loom"` zu `id "net.fabricmc.fabric-loom"`. Wenn du Loom in der `settings.gradle` spezifizierst, ändere es dort auch.
4. Entferne die Zeile `mappings` von dem Abschnitt dependencies deiner `build.gradle`.
5. Ersetze jegliche Instanzen von `modImplementation` oder `modCompileOnly` mit `implementation` und `compileOnly`.
6. Entferne oder ersetze alle Mods, die für Versionen vor 26.1 erstellt wurden, durch Versionen, die mit diesem Update kompatibel sind.
   - Keine der vorhandenen Mods für die Version 1.21.11 oder älter von Minecraft funktioniert mit 26.1, auch nicht als reine Kompilierungsabhängigkeit.
7. Setze die Java Kompatibilität auf 25, anstelle von 21.
8. Ersetze alle Erwähnungen von `remapJar` mit `jar`.
9. Aktualisiere Gradle, indem du auf die Schaltfläche zur Aktualisierung in der oberen rechten Ecke von IntelliJ IDEA klickst. Wenn diese Schaltfläche nicht sichtbar ist, kannst du das Leeren der Caches erzwingen, indem du `./gradlew --refresh-dependencies` ausführst.

## Den Code aktualisieren {#porting-guides}

Nachdem das Buildskript auf 26.1 aktualisiert wurde, kannst du nun deinen Mod durchgehen und allen geänderten Code aktualisieren, um ihn mit dem Snapshot kompatibel zu machen.

- [Fabric for Minecraft 26.1 im Fabric-Blog](https://fabricmc.net/2025/12/05/12111.html) enthält eine allgemeine Erläuterung der Änderungen, die in der Version 26.1 an der Fabric-API vorgenommen wurden.
- Der [Fabric API 26.1 Leitfaden zur Portierung](./fabric-api) listet die Umbenennungen auf, die in den 26.1 Snapshots an der Fabric API durchgeführt wurden, um sie an die Namen von Mojang anzupassen.
- [_Java Edition 26.1_ im Minecraft Wiki](https://minecraft.wiki/w/Java_Edition_26.1) ist eine inoffizielle Zusammenfassung der Inhalte des Updates.
- [NeoForge's _Minecraft 1.21.11 -> 26.1 Mod Migration Primer_](https://github.com/neoforged/.github/blob/main/primers/26.1/index.md) deckt die Migration mit Fokus auf Änderungen des Vanilla Code von 1.21.11 zu 26.1 ab.

<!---->
