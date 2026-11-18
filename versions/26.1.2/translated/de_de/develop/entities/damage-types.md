---
title: Schadensarten
description: Lerne, wie man benutzerdefinierte Schadensarten hinzufügt.
authors:
  - dicedpixels
  - hiisuuii
  - MattiDragon
resources:
  https://minecraft.wiki/w/Damage_type: Schadensarten - Minecraft Wiki
  https://docs.neoforged.net/docs/resources/server/damagetypes/: Schadensarten & Schadenquellen - NeoForge Docs (ausgenommen Datengenerator)
---

Schadensarten definieren die Arten von Schaden, die Entitäten erleiden können. Seit Minecraft 1.19.4 ist die Erstellung neuer Schadensarten datengesteuert, das heißt sie werden mithilfe von JSON-Dateien erstellt.

## Eine Schadensart erstellen {#creating-a-damage-type}

Lass uns eine benutzerdefinierte Schadensart mit dem Namen _Tater_ erstellen. Wir beginnen mit der Erstellung einer JSON-Datei für deinen benutzerdefinierten Schaden. Diese Datei wird im `data`-Verzeichnis deines Mods in einem Unterverzeichnis mit dem Namen `damage_type` abgelegt.

```text:no-line-numbers
resources/data/example-mod/damage_type/tater.json
```

Sie hat folgende Struktur:

<<< @/reference/26.1.2/src/main/generated/data/example-mod/damage_type/tater.json

Diese benutzerdefinierte Schadensart verursacht jedes Mal, wenn ein Spieler Schaden erleidet, einen Anstieg von 0,1 an [Erschöpfung](https://de.minecraft.wiki/w/Hunger#Ersch%C3%B6pfung), wenn der Schaden von einer lebenden Nicht-Spieler-Quelle (z.B. Weiterhin skaliert sich die Höhe des verursachten Schadens mit dem Schwierigkeitsgrad der Welt.

::: info

Im [Minecraft Wiki](https://de.minecraft.wiki/w/Schadensarten#Dateiformat) findest du alle möglichen Schlüssel und Werte.

:::

### Zugriff Auf Schadensarten Durch Code {#accessing-damage-types-through-code}

Wenn wir über den Code auf unsere benutzerdefinierte Schadensart zugreifen müssen, verwenden wir seinen `ResourceKey`, um eine Instanz von `DamageSource` zu erstellen.

Der `ResourceKey` kann wie folgt ermittelt werden:

<<< @/reference/26.1.2/src/main/java/com/example/docs/damage/ExampleModDamageTypes.java#damage_type

### Schadensarten verwenden {#using-damage-types}

Um die Verwendung von benutzerdefinierten Schadensarten zu demonstrieren, werden wir einen benutzerdefinierten Block mit dem Namen _Tater-Block_ verwenden. Wenn eine lebende Entität auf einen _Tater-Block_ tritt, verursacht er _Tater_ Schaden.

Du kannst `stepOn` überschreiben, um diesen Schaden zu zuzufügen.

Wir beginnen mit der Erstellung einer `DamageSource` unserer benutzerdefinierten Schadensart.

<<< @/reference/26.1.2/src/main/java/com/example/docs/damage/TaterBlock.java#create_damage_source

Dann rufen wir `entity.hurtServer()` mit dem aktuellen Level, unserer `DamageSource` auf und einem Betrag auf.

<<< @/reference/26.1.2/src/main/java/com/example/docs/damage/TaterBlock.java#hurt_entity

Die vollständige Implementierung des Blocks:

<<< @/reference/26.1.2/src/main/java/com/example/docs/damage/TaterBlock.java#complete_block

Wenn nun eine lebende Entität auf unseren benutzerdefinierten Block tritt, erleidet sie mit unserer benutzerdefinierten Schadensart 5 Schaden (2,5 Herzen).

### Benutzerdefinierte Todesnachricht {#custom-death-message}

Du kannst eine Todesnachricht für die Schadensart im Format `death.attack.message_id` in der Datei `en_us.json` unseres Mods definieren.

```json
{
  "death.attack.tater": "%1$s died from Tater damage!"
}
```

Beim Tod durch unsere Schadensart wirst du die folgende Todesnachricht sehen:

![Effekt im Inventar eines Spielers](/assets/develop/tater-damage-death.png)

### Schadensart-Tags {#damage-type-tags}

Einige Schadensarten können Rüstungen, Mobseffekte und Ähnliches umgehen. Tags werden verwendet, um diese Art von Eigenschaften von Schadensarten zu kontrollieren.

Vorhandene Schadensarten-Tags kannst du in `data/minecraft/tags/damage_type` finden.

::: info

Eine umfassende Liste der Tags für Schadensarten findest du im [Minecraft-Wiki](https://minecraft.wiki/w/Damage_type_tag_(Java_Edition)).

:::

Fügen wir unsere Tater-Schadensart dem Schadensart-Tag `bypasses_armor` hinzu.

Um unsere Schadensart zu einem dieser Tags hinzuzufügen, erstellen wir eine JSON-Datei im Namespace `minecraft`.

```text:no-line-numbers
data/minecraft/tags/damage_type/bypasses_armor.json
```

Mit folgendem Inhalt:

<<< @/reference/26.1.2/src/main/generated/data/minecraft/tags/damage_type/bypasses_armor.json

Stelle sicher, dass dein Tag das bestehende Tag nicht ersetzt, indem du den Schlüssel `replace` auf `false` setzt.
