---
title: Block Entitäten
description: Lerne, wie du Block Entitäten für deine benutzerdefinierten Blöcke erstellst.
authors:
  - natri0
---

# Block Entitäten {#block-entities}

Block Entitäten sind ein Weg für Blöcke zusätzliche Daten, die nicht Teil des Blockzustands sind, zu speichern: Inventarinhalte, benutzerdefinierter Name und so weiter.
Minecraft nutzt Block Entitäten für Blöcke, wie Kisten, Öfen und Befehlsblöcke.

Als Beispiel werden wir einen Block erstellen, der zählt, wie oft er mit der rechten Maustaste angeklickt wurde.

## Erstellen der Block Entität {#creating-the-block-entity}

Damit Minecraft die neuen Block Entitäten erkennt und lädt, müssen wir einen Block Entität Typen erstellen. Das machen wir, indem wir die `BlockEntity` Klasse erweitern und in einer neuen `ModBlockEntities` Klasse registrieren.

@[code transcludeWith=:::1](@/reference/1.21/src/main/java/com/example/docs/block/entity/custom/CounterBlockEntity.java)

Wenn eine `BlockEntity` registriert wird, gibt es einen `BlockEntityType` zurück, wie bei dem `COUNTER_BLOCK_ENTITY`, welches wir oben benutzt haben:

@[code transcludeWith=:::1](@/reference/1.21/src/main/java/com/example/docs/block/entity/ModBlockEntities.java)

:::tip
Man beachte, dass der Konstruktor von der `CounterBlockEntity` zwei Parameter benötigt, der Konstruktor von der `BlockEntity` jedoch drei: den `BlockEntityType`, `BlockPos` und den `BlockState`.
Wenn wir den `BlockEntityType` nicht hart kodiert hätten, würde die Klasse `ModBlockEntities` nicht kompiliert werden! Das liegt daran, dass die `BlockEntityFactory`, die ein funktionales Interface ist, eine Funktion beschreibt, die nur zwei Parameter benötigt, genau wie unser Konstruktor.
:::

## Erstellen des Blocks {#creating-the-block}

Um als Nächstes die Block Entität zu nutzen, brauchen wir einen Block, der `BlockEntityProvider` implementiert. Lass uns einen erstellen und `CounterBlock` nennen.

:::tip
Es gibt zwei Wege, um das zu machen:

- Einen Block erstellen, der `BlockWithEntity` erweitert und die `createBlockEntity` Methode implementiert (_und_ die `getRenderType` Methode, da `BlockWithEntity` den Block standardmäßig Unsichtbar macht)
- Einen Block erstellen, der `BlockEntityProvider` implementiert und die `createBlockEntity` Methode überschreibt

Wir werden in diesem Beispiel den ersten Weg nutzen, da `BlockWithEntity` ein paar nützliche Funktionen anbietet.
:::

@[code transcludeWith=:::1](@/reference/1.21/src/main/java/com/example/docs/block/custom/CounterBlock.java)

Da wir die `BlockWithEntity` Klasse erweitern, müssen wir auch die `createCodec` Methode implementieren, was aber recht leicht ist.

Im Gegensatz zu Blöcken, die Singletons sind, wird für jede Instanz des Blocks eine neue Blockentität erstellt. Dies geschieht mit der Methode `createBlockEntity`, die die Position und den `BlockState` entgegennimmt und ein `BlockEntity` zurückgibt, oder `null`, wenn es keins geben sollte.

Vergiss nicht, den Block in der Klasse `ModBlocks` zu registrieren, genau wie in der Anleitung [Deinen ersten Block erstellen](../blocks/first-block):

@[code transcludeWith=:::5](@/reference/1.21/src/main/java/com/example/docs/block/ModBlocks.java)

## Nutzen der Block Entität {#using-the-block-entity}

Jetzt, da wir eine Blockentität haben, können wir sie verwenden, um die Anzahl der Rechtsklicks auf den Block zu speichern. Dafür werden wir der Klasse `CounterBlockEntity` ein Feld `clicks` hinzufügen:

@[code transcludeWith=:::2](@/reference/1.21/src/main/java/com/example/docs/block/entity/custom/CounterBlockEntity.java)

Die Methode `markDirty`, die in `incrementClicks` verwendet wird, teilt dem Spiel mit, dass die Daten dieser Entität aktualisiert wurden; dies wird nützlich sein, wenn wir die Methoden hinzufügen, um den Zähler zu serialisieren und ihn aus der Speicherdatei zurückzuladen.

Als Nächstes müssen wir dieses Feld jedes Mal erhöhen, wenn der Block mit der rechten Maustaste angeklickt wird. Dies geschieht indem die Methode `onUse` in der Klasse `CounterBlock` überschrieben wird:

@[code transcludeWith=:::2](@/reference/1.21/src/main/java/com/example/docs/block/custom/CounterBlock.java)

Da die `BlockEntity` nicht an die Methode übergeben wird, verwenden wir `world.getBlockEntity(pos)`, und wenn die `BlockEntity` nicht gültig ist, kehren wir aus der Methode zurück.

!["You've clicked the block for the 6th time" Nachricht auf dem Bildschirm nach dem Rechtsklick](/assets/develop/blocks/block_entities_1.png)

## Speichern und Laden von Daten {#saving-loading}

Da wir nun einen funktionierenden Block haben, sollten wir dafür sorgen, dass der Zähler zwischen den Neustarts des Spiels nicht zurückgesetzt wird. Dies geschieht durch Serialisierung in NBT, wenn das Spiel speichert, und Deserialisierung, wenn es geladen wird.

Die Serialisierung erfolgt mit der Methode `writeNbt`:

@[code transcludeWith=:::3](@/reference/1.21/src/main/java/com/example/docs/block/entity/custom/CounterBlockEntity.java)

Hier fügen wir die Felder hinzu, die in dem übergebenen `NbtCompound` gespeichert werden sollen: im Fall des Zählerblocks ist das das Feld `clicks`.

Das Lesen ist ähnlich, aber anstatt in dem `NbtCompound` zu speichern, holt man sich die Werte, die man vorher gespeichert hat, und speichert sie in den Feldern der BlockEntity:

@[code transcludeWith=:::4](@/reference/1.21/src/main/java/com/example/docs/block/entity/custom/CounterBlockEntity.java)

Wenn wir nun speichern und das Spiel neu laden, sollte der Zählerblock dort weitermachen, wo er beim Speichern aufgehört hat.

## Ticker {#tickers}

Das Interface `BlockEntityProvider` definiert auch eine Methode namens `getTicker`, mit der für jede Instanz des Blocks bei jedem Tick Code ausgeführt werden kann. Wir können das implementieren, indem wir eine statische Methode erstellen, die als `BlockEntityTicker` verwendet wird:

Die Methode `getTicker` sollte auch prüfen, ob der übergebene `BlockEntityType` derselbe ist wie der, den wir verwenden, und wenn ja, die Funktion zurückgeben, die bei jedem Tick aufgerufen wird. Glücklicherweise gibt es eine Hilfsfunktion, die diese Prüfung in `BlockWithEntity` durchführt:

@[code transcludeWith=:::3](@/reference/1.21/src/main/java/com/example/docs/block/custom/CounterBlock.java)

`CounterBlockEntity::tick` ist ein Verweis auf die statische Methode `tick`, die wir in der Klasse `CounterBlockEntity` erstellen sollten. Eine solche Strukturierung ist nicht erforderlich, aber es ist eine gute Praxis, um den Code sauber und übersichtlich zu halten.

Nehmen wir an, wir wollen, dass der Zähler nur alle 10 Ticks (2 Mal pro Sekunde) erhöht werden kann. Wir können dies tun, indem wir der Klasse `CounterBlockEntity` ein Feld `ticksSinceLast` hinzufügen und es bei jedem Tick erhöhen:

@[code transcludeWith=:::5](@/reference/1.21/src/main/java/com/example/docs/block/entity/custom/CounterBlockEntity.java)

Vergiss nicht, dieses Feld zu serialisieren und zu deserialisieren!

Jetzt können wir `ticksSinceLast` verwenden, um zu prüfen, ob der Zähler in `incrementClicks` erhöht werden kann:

@[code transcludeWith=:::6](@/reference/1.21/src/main/java/com/example/docs/block/entity/custom/CounterBlockEntity.java)

:::tip
Wenn die Blockentität nicht zu ticken scheint, überprüfe den Registrierungscode! Es sollte die Blöcke, die für diese Entität gültig sind, an den `BlockEntityType.Builder`, übergeben, sonst wird eine Warnung in der Konsole ausgegeben:

```text
[13:27:55] [Server thread/WARN] (Minecraft) Block entity fabric-docs-reference:counter @ BlockPos{x=-29, y=125, z=18} state Block{fabric-docs-reference:counter_block} invalid for ticking:
```

:::
