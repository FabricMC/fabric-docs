---
title: Blockcontainer
description: Lerne, wie du Container zu deinen Blockentitäten hinzufügst.
authors:
  - natri0
---

Es empfiehlt sich, bei der Erstellung von Blöcken, die Items speichern können, wie Truhen und Öfen, `Container` zu implementieren. Dadurch ist es beispielsweise möglich, mit dem Block mithilfe von Trichtern zu interagieren.

In diesem Tutorial erstellen wir einen Block, der seinen Container verwendet, um alle darin platzierten Items zu duplizieren.

## Erstellen des Blocks {#creating-the-block}

Dies sollte dem Leser bekannt sein, wenn er die Leitfäden [Erstellen deines ersten Blocks](../blocks/first-block) und [Block-Entitäten](../blocks/block-entities) befolgt hat. Wir werden einen `DuplicatorBlock` erstellen, der von `BaseEntityBlock` erbt und `EntityBlock` implementiert.

@[code transcludeWith=:::block](@/reference/latest/src/main/java/com/example/docs/block/custom/DuplicatorBlock.java)

Dann müssen wir eine `DuplicatorBlockEntity` erstellen, welche das Interface `Container` implementieren muss. Da die meisten Container in der Regel auf die gleiche Weise funktionieren, kannst du einen Helfer namens `ImplementedContainer` kopieren und einfügen, der den Großteil der Arbeit übernimmt, sodass wir nur noch wenige Methoden implementieren müssen.

:::details Zeige `ImplementedContainer`

@[code](@/reference/latest/src/main/java/com/example/docs/container/ImplementedContainer.java)

:::

@[code transcludeWith=:::be](@/reference/latest/src/main/java/com/example/docs/block/entity/custom/DuplicatorBlockEntity.java)

In der Liste `items` wird der Inhalt des Containers gespeichert. Für diesen Block haben wir eine Größe von 1 Slot für die Eingabe festgelegt.

Vergiss nicht den Block und die Blockentität in deren jeweiligen Klasse zu registrieren!

### Speichern & Laden {#saving-loading}

Wenn wir möchten, dass die Inhalte zwischen den Spielneustarts wie bei einer Vanilla `BlockEntity` erhalten bleiben, müssen wir sie als NBT speichern. Dankenswerterweise stellt Mojang eine Hilfsklasse namens `ContainerHelper` mit der gesamten erforderlichen Logik zur Verfügung.

@[code transcludeWith=:::save](@/reference/latest/src/main/java/com/example/docs/block/entity/custom/DuplicatorBlockEntity.java)

## Mit dem Container interagieren {#interacting-with-the-container}

Technisch gesehen, funktioniert der Container bereits. Um jedoch Items einzufügen, müssen wir derzeit Trichter verwenden. Lasst uns es so einrichten, dass wir Items durch einen Rechtsklick auf den Block einfügen können.

Um dies zu tun, müssen wir die Methode `useItemOn` in `DuplicatorBlock` überschreiben:

@[code transcludeWith=:::useon](@/reference/latest/src/main/java/com/example/docs/block/custom/DuplicatorBlock.java)

Wenn der Spieler ein Item hält und ein Platz frei ist, verschieben wir das Item aus der Hand des Spielers in den Container des Blocks und geben `InteractionResult.SUCCESS` zurück.

Wenn du jetzt mit der rechten Maustaste auf den Block mit einem Item klickst, wirst du es nicht mehr haben! Wenn du `/data get block` auf dem Block ausführst, wirst du das Item in dem Feld `items` des NBT sehen.

![Duplizierungsblock und Ausgabe von /data get block, die das Item im Container anzeigt](/assets/develop/blocks/container_1.png)

### Items duplizieren {#duplicating-items}

Lasst uns jetzt dafür sorgen, dass der Block den Stack, den du hineingeworfen hast, dupliziert, jedoch nur jeweils zwei Items auf einmal. Und lassen wir es jedes Mal eine Sekunde warten, um den Spieler nicht mit Items zuzuspammen!

Dazu fügen wir eine Funktion `tick` zur `DuplicatorBlockEntity` hinzu und ein Feld, um zu speichern, wie lange wir gewartet haben:

@[code transcludeWith=:::tick](@/reference/latest/src/main/java/com/example/docs/block/entity/custom/DuplicatorBlockEntity.java)

Der `DuplicatorBlock` sollte nun über eine Methode `getTicker` verfügen, die eine Referenz auf `DuplicatorBlockEntity::tick` zurückgibt.

<VideoPlayer src="/assets/develop/blocks/container_2.mp4">Duplizierungsblock, der einen Eichenstamm dupliziert</VideoPlayer>

## Weltbasierte Container {#worldly-containers}

Standardmäßig kannst du Items von jeder Seite aus in den Container einfügen und aus ihm entnehmen. Dies ist jedoch unter Umständen nicht immer erwünscht: Beispielsweise nimmt ein Ofen Brennstoff nur von der Seite und Items nur von oben auf.

Um dieses Verhalten zu erzeugen, müssen wir das Interface `WorldlyContainer` in `BlockEntity` implementieren. Das Interface hat drei Methoden:

- `getSlotsForFace(Direction)` lässt dir steuern, mit welchen Slots von einer bestimmten Seite aus interagiert werden kann.
- `canPlaceItemThroughFace(int, ItemStack, Direction)` lässt dir steuern, ob ein Item von einer gegebenen Seite in einen Slot eingefügt werden kann.
- `canTakeItemThroughFace(int, ItemStack, Direction)` lässt dir steuern, ob ein Item von einer gegebenen Seite entnommen werden kann.

Lasst uns die `DuplicatorBlockEntity` bearbeiten, um nur Items von der Oberseite zu akzeptieren:

@[code transcludeWith=:::accept](@/reference/latest/src/main/java/com/example/docs/block/entity/custom/DuplicatorBlockEntity.java)

Die Methode `getSlotsForFace` gibt ein Array der Slot _Indizes_ zurück, mit denen von der angegebenen Seite aus interagiert werden kann. In diesem Fall haben wir nur einen einzigen Slot (`0`), daher geben wir ein Array mit genau diesem Index zurück.

Außerdem sollten wir die Methode `useItemOn` von `DuplicatorBlock` ändern, um das neue Verhalten tatsächlich zu berücksichtigen:

@[code transcludeWith=:::place](@/reference/latest/src/main/java/com/example/docs/block/custom/DuplicatorBlock.java)

Wenn wir nun versuchen, Items von der Seite statt von oben einzufügen, funktioniert das nicht!

<VideoPlayer src="/assets/develop/blocks/container_3.webm">Der Duplizierer wird nur aktiviert, wenn man mit seiner Oberseite interagiert.</VideoPlayer>
