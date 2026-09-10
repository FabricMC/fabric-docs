---
title: Zu 26.2 portieren
description: Leitfaden für die Portierung auf Minecraft 26.2, die aktuellste Version von Minecraft.
authors:
  - cassiancc
  - ChampionAsh5357
resources:
  https://fabricmc.net/2026/06/15/262.html: Fabric für Minecraft 26.2
  https://minecraft.wiki/w/Java_Edition_26.2: Java Edition 26.2 - Minecraft Wiki
  https://docs.neoforged.net/primer/docs/26.2/: ChampionAsh5357's 26.1 -> 26.2 Leitfäden zur Migration
---

Minecraft ist ein Spiel, das sich ständig weiterentwickelt, wobei neue Versionen das Spiel in einer Weise verändern, die sich auf Modder auswirkt. Dieser Artikel deckt die allgemeinen Schritte ab, die man befolgen kann, um seinen Mod auf die neueste stabile Version von Minecraft zu aktualisieren.

::: info

Dieses Dokumentation behandelt die Migration von **26.1** auf **26.2**. Wenn du nach einer anderen Migration suchst, wechsle mithilfe des Dropdown-Menüs in der oberen rechten Ecke zur Zielversion.

:::

## Das Buildskript aktualisieren {#build-script}

Beginne damit, die Dateien `gradle/wrapper/gradle-wrapper.properties`, `gradle.properties` und `build.gradle` deines Mods auf die neuesten Versionen zu aktualisieren. Solltest du auf Probleme stoßen, empfehlen wir dir, einen Blick in den [Fabric Beispielmod](https://github.com/FabricMC/fabric-example-mod/tree/26.2) zu werfen.

1. Aktualisiere Gradle auf die aktuellste Version, indem du den folgenden Befehl ausführst: `./gradlew wrapper --gradle-version latest`
2. Hebe Minecraft, den Fabric Loader, Fabric Loom und die Fabric API an, entweder in der `gradle.properties` (empfohlen) oder in der `build.gradle`. Die empfohlenen Versionen der Fabric-Komponenten findest du auf der [Fabric Develop-Seite](https://fabricmc.net/develop/).
3. Aktualisiere Gradle, indem du auf die Schaltfläche zur Aktualisierung in der oberen rechten Ecke von IntelliJ IDEA klickst. Wenn diese Schaltfläche nicht sichtbar ist, kannst du das Leeren der Caches erzwingen, indem du `./gradlew --refresh-dependencies` ausführst.

## Den Code aktualisieren {#porting-guides}

Nachdem das Buildskript auf 26.2 aktualisiert wurde, kannst du nun deinen Mod durchgehen und allen geänderten Code aktualisieren, um ihn mit dem Snapshot kompatibel zu machen.

- [Fabric for Minecraft 26.2 im Fabric-Blog](https://fabricmc.net/2026/06/15/262.html) enthält eine allgemeine Erläuterung der Änderungen, die in der Version 26.2 an der Fabric-API vorgenommen wurden.
- [_Minecraft: Java Edition 26.2_ im Minecraft-Blog](https://www.minecraft.net/en-us/article/minecraft-java-edition-26-2) ist die offizielle Übersicht über die Funktionen, die in 26.2 eingeführt wurden.
- [_Java Edition 26.2_ im Minecraft Wiki](https://minecraft.wiki/w/Java_Edition_26.2) ist eine inoffizielle Zusammenfassung der Inhalte des Updates.
- [NeoForge's _Minecraft 26.1 -> 26.2 Mod Leitfäden zur Migration_](https://docs.neoforged.net/primer/docs/26.2/) deckt die Migration von 26.1 auf 26.2 mit Fokus auf Änderungen des Vanilla Code ab.

<!---->
