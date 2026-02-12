---
title: Block Entitäten
description: Lerne, wie du Block Entitäten für deine benutzerdefinierten Blöcke erstellst.
authors:
  - natri0
---

Block Entitäten sind ein Weg für Blöcke zusätzliche Daten, die nicht Teil des Blockzustands sind, zu speichern: Inventarinhalte, benutzerdefinierter Name und so weiter.
Minecraft nutzt Block Entitäten für Blöcke, wie Kisten, Öfen und Befehlsblöcke.

Als Beispiel werden wir einen Block erstellen, der zählt, wie oft er mit der rechten Maustaste angeklickt wurde.

## Erstellen der Block Entität {#creating-the-block-entity}

Damit Minecraft die neuen Block Entitäten erkennt und lädt, müssen wir einen Block Entität Typen erstellen. Das machen wir, indem wir die `BlockEntity` Klasse erweitern und in einer neuen `ModBlockEntities` Klasse registrieren.

@[code transcludeWith=:::1](@/reference/latest/src/main/java/com/example/docs/block/entity/custom/CounterBlockEntity.java)

Wenn eine `BlockEntity` registriert wird, gibt es einen `BlockEntityType` zurück, wie bei dem `COUNTER_BLOCK_ENTITY`, welches wir oben benutzt haben:

@[code transcludeWith=:::1](@/reference/latest/src/main/java/com/example/docs/block/entity/ModBlockEntities.java)

::: tip

Man beachte, dass der Konstruktor von der `CounterBlockEntity` zwei Parameter benötigt, der Konstruktor von der `BlockEntity` jedoch drei: den `BlockEntityType`, `BlockPos` und den `BlockState`.
Wenn wir den `BlockEntityType` nicht hart kodiert hätten, würde die Klasse `ModBlockEntities` nicht kompiliert werden! Das liegt daran, dass die `BlockEntityFactory`, die ein funktionales Interface ist, eine Funktion beschreibt, die nur zwei Parameter benötigt, genau wie unser Konstruktor.

:::

## Erstellen des Blocks {#creating-the-block}

Um als Nächstes die Blockentität zu nutzen, brauchen wir einen Block, der `EntityBlock` implementiert. Lass uns einen erstellen und diesen `CounterBlock` nennen.

::: tip

Es gibt zwei Wege, um dies zu erreichen:

- Einen Block erstellen, der von `BaseEntityBlock` erbt und die Methode `createBlockEntity` implementiert
- Einen Block erstellen, der `EntityBlock` implementiert und die Methode `createBlockEntity` überschreibt

Wir werden in diesem Beispiel den ersten Weg nutzen, da `BaseEntityBlock` ein paar nützliche Hilfsfunktionen bietet.

:::

@[code transcludeWith=:::1](@/reference/latest/src/main/java/com/example/docs/block/custom/CounterBlock.java)

Die Verwendung von `BaseEntityBlock` als übergeordnete Klasse bedeutet, dass wir auch die Methode `createCodec` implementieren müssen, was relativ einfach ist.

Im Gegensatz zu Blöcken, die Singletons sind, wird für jede Instanz des Blocks eine neue Blockentität erstellt. Dies geschieht mit der Methode `createBlockEntity`, die die Position und den `BlockState` entgegennimmt und ein `BlockEntity` zurückgibt, oder `null`, wenn es keins geben sollte.

Vergiss nicht, den Block in der Klasse `ModBlocks` zu registrieren, genau wie in der Anleitung [Deinen ersten Block erstellen](../blocks/first-block):

@[code transcludeWith=:::5](@/reference/latest/src/main/java/com/example/docs/block/ModBlocks.java)

## Nutzen der Block Entität {#using-the-block-entity}

Jetzt, da wir eine Blockentität haben, können wir sie verwenden, um die Anzahl der Rechtsklicks auf den Block zu speichern. Dafür werden wir der Klasse `CounterBlockEntity` ein Feld `clicks` hinzufügen:

@[code transcludeWith=:::2](@/reference/latest/src/main/java/com/example/docs/block/entity/custom/CounterBlockEntity.java)

Die Methode `setChanged`, die in `incrementClicks` verwendet wird, teilt dem Spiel mit, dass die Daten dieser Entität aktualisiert wurden; dies wird nützlich sein, wenn wir die Methoden hinzufügen, um den Zähler zu serialisieren und ihn aus der Speicherdatei zurückzuladen.

Als Nächstes müssen wir dieses Feld jedes Mal erhöhen, wenn der Block mit der rechten Maustaste angeklickt wird. Dies geschieht indem die Methode `useWithoutItem` in der Klasse `CounterBlock` überschrieben wird:

@[code transcludeWith=:::2](@/reference/latest/src/main/java/com/example/docs/block/custom/CounterBlock.java)

Da die `BlockEntity` nicht an die Methode übergeben wird, verwenden wir `level.getBlockEntity(pos)`, und wenn die `BlockEntity` nicht gültig ist, kehren wir aus der Methode zurück.

!["You've clicked the block for the 6th time" Nachricht auf dem Bildschirm nach dem Rechtsklick](/assets/develop/blocks/block_entities_1.png)

## Speichern und Laden von Daten {#saving-loading}

Da wir nun einen funktionierenden Block haben, sollten wir dafür sorgen, dass der Zähler zwischen den Neustarts des Spiels nicht zurückgesetzt wird. Dies geschieht durch Serialisierung in NBT, wenn das Spiel speichert, und Deserialisierung, wenn es geladen wird.

Das Speichern in NBT erfolgt über `ValueInput`s und `ValueOutput`s. Diese Views sind für die Speicherung von Fehlern bei der Kodierung/Dekodierung und die Verfolgung von Registrierungen während des gesamten Serialisierungsprozesses verantwortlich.

Du kannst aus einem `ValueInput` mit der Methode `read` lesen, indem du einen `Codec` für den gewünschten Typ übergibst. Ebenso kannst du in einen `ValueOutput` schreiben, indem du die Methode `store` verwendest und einen Codec für den Typ sowie den Wert übergibst.

Es gibt auch Methoden für primitive Datentypen, wie z. B. `getInt`, `getShort`, `getBoolean` usw. zum Lesen und `putInt`, `putShort`, `putBoolean` usw. zum Schreiben. Die View bietet auch Methoden für das Arbeiten mit Listen, nullbaren Typen und verschachtelten Objekten.

Die Serialisierung erfolgt mit der Methode `saveAdditional`:

@[code transcludeWith=:::3](@/reference/latest/src/main/java/com/example/docs/block/entity/custom/CounterBlockEntity.java)

Hier fügen wir die Felder hinzu, die in dem übergebenen `ValueOutput` gespeichert werden sollen: Im Fall des Zählerblocks ist es das Feld `clicks`.

Das Lesen funktioniert ähnlich, indem du die zuvor gespeicherten Werte aus dem `ValueInput` abrufst und in den Feldern der BlockEntity speicherst:

@[code transcludeWith=:::4](@/reference/latest/src/main/java/com/example/docs/block/entity/custom/CounterBlockEntity.java)

Wenn wir nun speichern und das Spiel neu laden, sollte der Zählerblock dort weitermachen, wo er beim Speichern aufgehört hat.

Obwohl `saveAdditional` und `loadAdditional` das Speichern und Laden auf und von der Festplatte regeln, gibt es noch ein Problem:

- Der Server weiß den korrekten `clicks` Wert.
- Der Client erhält nicht den korrekten Wert, wenn der Chunk geladen wird.

Um dies zu beheben, überschreiben wir `getUpdateTag`:

@[code transcludeWith=:::7](@/reference/latest/src/main/java/com/example/docs/block/entity/custom/CounterBlockEntity.java)

Wenn sich nun ein Spieler anmeldet oder in einen Chunk geht, in dem der Block vorhanden ist, sieht er sofort den korrekten Zählerwert.

## Ticker {#tickers}

Das Interface `EntityBlock` definiert auch eine Methode namens `getTicker`, mit der für jede Instanz des Blocks bei jedem Tick Code ausgeführt werden kann. Wir können das implementieren, indem wir eine statische Methode erstellen, die als `BlockEntityTicker` verwendet wird:

Die Methode `getTicker` sollte auch prüfen, ob der übergebene `BlockEntityType` derselbe ist wie der, den wir verwenden, und wenn ja, die Funktion zurückgeben, die bei jedem Tick aufgerufen wird. Glücklicherweise gibt es eine Hilfsfunktion, die diese Prüfung in `BaseEntityBlock` durchführt:

@[code transcludeWith=:::3](@/reference/latest/src/main/java/com/example/docs/block/custom/CounterBlock.java)

`CounterBlockEntity::tick` ist ein Verweis auf die statische Methode `tick`, die wir in der Klasse `CounterBlockEntity` erstellen sollten. Eine solche Strukturierung ist nicht erforderlich, aber es ist eine gute Praxis, um den Code sauber und übersichtlich zu halten.

Nehmen wir an, wir wollen, dass der Zähler nur alle 10 Ticks (2 Mal pro Sekunde) erhöht werden kann. Wir können dies tun, indem wir der Klasse `CounterBlockEntity` ein Feld `ticksSinceLast` hinzufügen und es bei jedem Tick erhöhen:

@[code transcludeWith=:::5](@/reference/latest/src/main/java/com/example/docs/block/entity/custom/CounterBlockEntity.java)

Vergiss nicht, dieses Feld zu serialisieren und zu deserialisieren!

Jetzt können wir `ticksSinceLast` verwenden, um zu prüfen, ob der Zähler in `incrementClicks` erhöht werden kann:

@[code transcludeWith=:::6](@/reference/latest/src/main/java/com/example/docs/block/entity/custom/CounterBlockEntity.java)

::: tip

Wenn die Blockentität nicht zu ticken scheint, überprüfe den Registrierungscode! Es sollte die Blöcke, die für diese Entität gültig sind, an den `BlockEntityType.Builder`, übergeben, sonst wird eine Warnung in der Konsole ausgegeben:

```log
[13:27:55] [Server thread/WARN] (Minecraft) Block entity example-mod:counter @ BlockPos{x=-29, y=125, z=18} state Block{example-mod:counter_block} invalid for ticking:
```

:::

<!---->
