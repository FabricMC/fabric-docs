---
title: Zu 1.21.11 portieren
description: Ein Leitfaden für die Portierung auf die neueste Version von Minecraft.
authors:
  - cassiancc
---

Minecraft ist ein Spiel, das sich ständig weiterentwickelt, wobei neue Versionen das Spiel in einer Weise verändern, die sich auf Modder auswirkt. Dieser Artikel deckt die allgemeinen Schritte ab, die man befolgen kann, um seinen Mod auf die neueste stabile Version von Minecraft zu aktualisieren.

::: info

Dieses Dokumentation behandelt die Migration von **1.21.10** auf **1.21.11**. Wenn du nach einer anderen Migration suchst, wechsle mithilfe des Dropdown-Menüs in der oberen rechten Ecke zur Zielversion.

:::

## Das Buildskript aktualisieren {#build-script}

Beginne damit, die Dateien `gradle/wrapper/gradle-wrapper.properties`, `gradle.properties` und `build.gradle` deines Mods auf die neuesten Versionen zu aktualisieren:

1. Aktualisiere Gradle auf die aktuellste Version, indem du den folgenden Befehl ausführst: `./gradlew wrapper --gradle-version latest`
2. Hebe Minecraft, den Fabric Loader, Fabric Loom und die Fabric API an, entweder in der `gradle.properties` (empfohlen) oder in der `build.gradle`. Die empfohlenen Versionen der Fabric-Komponenten findest du auf der [Fabric Develop-Website](https://fabricmc.net/develop/).
3. Aktualisiere Gradle, indem du auf die Schaltfläche zur Aktualisierung in der oberen rechten Ecke von IntelliJ IDEA klickst. Wenn diese Schaltfläche nicht sichtbar ist, kannst du das Leeren der Caches erzwingen, indem du `./gradlew --refresh-dependencies` ausführst.

## Den Code aktualisieren {#porting-guides}

Nachdem das Buildskript auf 1.21.11 aktualisiert wurde, kannst du nun deinen Mod durchgehen und allen geänderten Code aktualisieren, um ihn mit der neuen Version kompatibel zu machen.

Um dir bei der Aktualisierung zu helfen, dokumentieren Modder die Änderungen, auf die sie gestoßen sind, in Artikeln wie dem Fabric Blog und den Anleitungen zur Portierung von NeoForge.

- [_Fabric for Minecraft 1.21.11_ im Fabric-Blog](https://fabricmc.net/2025/12/05/12111.html) enthält eine allgemeine Erläuterung der Änderungen, die in 1.21.11 an der Fabric-API vorgenommen wurden.
- [_Minecraft: Java Edition 1.21.11_ im Minecraft-Blog](https://www.minecraft.net/en-us/article/minecraft-java-edition-1-21-11) ist die offizielle Übersicht an Funktionen, die in 1.21.11 eingeführt wurden.
- [_Java Edition 1.21.11_ im Minecraft Wiki](https://minecraft.wiki/w/Java_Edition_1.21.11) ist eine inoffizielle Zusammenfassung der Inhalte des Updates.
- [slicedlime's Data & Resource Pack News in Minecraft 1.21.11](https://www.youtube.com/watch?v=5yY25GoWQhs&pp=0gcJCSkKAYcqIYzv) deckt Informationen ab, die für die Aktualisierung der Daten- und ressourcenpaketgesteuerten Inhalte deines Mods relevant sind.
- [NeoForge's _Minecraft 1.21.10 -> 1.21.11 Mod Migration Primer_](https://github.com/neoforged/.github/blob/main/primers/1.21.11/index.md) deckt die Migration mit Fokus auf Änderungen des Vanilla Code von 1.21.10 zu 1.21.11 ab.
  - Bitte beachten, dass es sich bei dem verlinkten Artikel um Material von Dritten handelt, das nicht von Fabric gepflegt wird. Es unterliegt dem Urheberrecht von @ChampionAsh5357 und ist lizenziert unter [Creative Commons Attribution 4.0 International](https://creativecommons.org/licenses/by/4.0/).
