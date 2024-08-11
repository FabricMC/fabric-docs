---
title: Schadensarten
description: Lerne, wie man benutzerdefinierte Schadensarten hinzufügt.
authors:
  - dicedpixels
  - hiisuuii
  - mattidragon

search: false
---

# Schadensarten

Schadensarten definieren die Arten von Schaden, die Entitäten erleiden können. Seit Minecraft 1.19.4 ist die Erstellung neuer Schadensarten datengesteuert, das heißt sie werden mithilfe von JSON-Dateien erstellt.

## Eine Schadensart erstellen

Lass uns eine benutzerdefinierte Schadensart mit dem Namen _Tater_ erstellen. Wir beginnen mit der Erstellung einer JSON-Datei für deinen benutzerdefinierten Schaden. Diese Datei wird im `data`-Verzeichnis deines Mods in einem Unterverzeichnis mit dem Namen `damage_type` abgelegt.

```:no-line-numbers
resources/data/fabric-docs-reference/damage_type/tater.json
```

Sie hat folgende Struktur:

@[code lang=json](@/reference/latest/src/main/generated/data/fabric-docs-reference/damage_type/tater.json)

Diese benutzerdefinierte Schadensart verursacht jedes Mal, wenn ein Spieler Schaden erleidet, einen Anstieg von 0,1 an [Erschöpfung](https://de.minecraft.wiki/w/Hunger#Ersch%C3%B6pfung), wenn der Schaden von einer lebenden Nicht-Spieler-Quelle (z.B. Weiterhin skaliert sich die Höhe des verursachten Schadens mit dem Schwierigkeitsgrad der Welt.

::: info

Im [Minecraft Wiki](https://de.minecraft.wiki/w/Schadensarten#Dateiformat) findest du alle möglichen Schlüssel und Werte.

:::

### Auf eine Schadensart durch Code zugreifen

Wenn wir über den Code auf unsere benutzerdefinierte Schadensart zugreifen müssen, verwenden wir seinen `RegistryKey`, um eine Instanz von `DamageSource` zu erstellen.

Der `RegistryKey` kann wie folgt ermittelt werden:

@[code lang=java transcludeWith=:::1](@/reference/latest/src/main/java/com/example/docs/damage/FabricDocsReferenceDamageTypes.java)

### Schadensarten verwenden

Um die Verwendung von benutzerdefinierten Schadensarten zu demonstrieren, werden wir einen benutzerdefinierten Block mit dem Namen _Tater-Block_ verwenden. Wenn eine lebende Entität auf einen _Tater-Block_ tritt, verursacht er _Tater_ Schaden.

Du kannst `onSteppedOn` überschreiben, um diesen Schaden zu zuzufügen.

Wir beginnen mit der Erstellung einer `DamageSource` unserer benutzerdefinierten Schadensart.

@[code lang=java transclude={21-24}](@/reference/latest/src/main/java/com/example/docs/damage/TaterBlock.java)

Dann rufen wir `entity.damage()` mit unserer `DamageSource` und einem Betrag auf.

@[code lang=java transclude={25-25}](@/reference/latest/src/main/java/com/example/docs/damage/TaterBlock.java)

Die vollständige Implementierung des Blocks:

@[code lang=java transcludeWith=:::1](@/reference/latest/src/main/java/com/example/docs/damage/TaterBlock.java)

Wenn nun eine lebende Entität auf unseren benutzerdefinierten Block tritt, erleidet sie mit unserer benutzerdefinierten Schadensart 5 Schaden (2,5 Herzen).

### Benutzerdefinierte Todesnachricht

Du kannst eine Todesnachricht für die Schadensart im Format `death.attack.<message_id>` in der Datei `en_us.json` unseres Mods definieren.

@[code lang=json transclude={4-4}](@/reference/latest/src/main/resources/assets/fabric-docs-reference/lang/en_us.json)

Beim Tod durch unsere Schadensart wirst du die folgende Todesnachricht sehen:

![Effekt im Inventar eines Spielers](/assets/develop/tater-damage-death.png)

### Schadensart-Tags

Einige Schadensarten können Rüstung, Statuseffekte usw. Tags werden verwendet, um diese Art von Eigenschaften von Schadensarten zu kontrollieren.

Vorhandene Schadensarten-Tags kannst du in `data/minecraft/tags/damage_type` finden.

::: info

Im [Minecraft Wiki](https://minecraft.wiki/w/Tag#Damage_types) kannst du eine umfassende Liste der Schadensarten-Tags finden.

:::

Fügen wir unsere Tater-Schadensart dem Schadensart-Tag `bypasses_armor` hinzu.

Um unsere Schadensart zu einem dieser Tags hinzuzufügen, erstellen wir eine JSON-Datei im Namespace `minecraft`.

```:no-line-numbers
data/minecraft/tags/damage_type/bypasses_armor.json
```

Mit folgendem Inhalt:

@[code lang=json](@/reference/latest/src/main/generated/data/minecraft/tags/damage_type/bypasses_armor.json)

Stelle sicher, dass dein Tag das bestehende Tag nicht ersetzt, indem du den Schlüssel `replace` auf `false` setzt.
