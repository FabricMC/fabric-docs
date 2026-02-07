---
title: Gespeicherte Daten
description: Speichern von Daten zwischen Spielsitzungen.
authors:
  - dicedpixels
---

Gespeicherte Daten sind die integrierte Lösung von Minecraft, um Daten über mehrere Sitzungen hinweg zu speichern.

Die Daten werden auf dem Datenträger gespeichert und beim Schließen und erneuten Öffnen des Spiels wieder geladen. Diese Daten sind in der Regel auf einen Geltungsbereich begrenzt (z. B. auf das Level). Die Daten werden als [NBT](https://minecraft.wiki/w/NBT_format) auf den Datenträger geschrieben, und [Codecs](./codecs) werden verwendet, um diese Daten zu serialisieren/deserialisieren.

Betrachten wir ein einfaches Szenario, in dem wir die Anzahl der vom Spieler abgebauten Blöcke speichern müssen. Wir können diese Anzahl auf dem logischen Server speichern.

Wir können das Event `PlayerBlockBreakEvents.AFTER` mit einem einfachen statischen Integer-Feld verwenden, um diesen Wert zu speichern und als Chat-Nachricht zu versenden.

```java
private static int blocksBroken = 0; // keeps track of the number of blocks broken

PlayerBlockBreakEvents.AFTER.register((level, player, pos, state, blockEntity) -> {
    blocksBroken++; // increment the counter each time a block is broken
    player.displayClientMessage(Component.literal("Blocks broken: " + blocksBroken), false);
});
```

Wenn du jetzt einen Block abbaust, wird eine Nachricht mit der Anzahl angezeigt.

![Abbau eines Blocks](/assets/develop/saved-data/block-breaking.png)

Wenn du Minecraft neu startest, die Welt lädst und anfängst, Blöcke abzubauen, wirst du feststellen, dass der Zähler zurückgesetzt wurde. Hier benötigen wir gespeicherte Daten. Wir können diese Anzahl dann speichern, sodass wir beim nächsten Laden der Welt die gespeicherte Anzahl abrufen und ab diesem Punkt weiterzählen können.

## Daten speichern {#saving-data}

`SavedData` ist die Hauptklasse, die für das Speichern und Laden von Daten zuständig ist. Da es sich um eine abstrakte Klasse handelt, wird von dir erwartet, dass du eine Implementierung bereitstellst.

### Eine Datenklasse einrichten {#setting-up-a-data-class}

Nennen wir unsere Datenklasse `SavedBlockData` und lassen wir sie `SavedData` erweitern.

Diese Klasse enthält ein Feld, um die Anzahl der abgebauten Blöcke zu verfolgen, sowie eine Methode zum Abrufen und eine Methode zum Erhöhen dieser Anzahl.

@[code lang=java transcludeWith=:::basic_structure](@/reference/latest/src/main/java/com/example/docs/saveddata/SavedBlockData.java)

Um diese Daten zu serialisieren und zu deserialisieren, müssen wir einen Codec definieren. Wir können einen Codec aus verschiedenen primitiven Codecs zusammenstellen, die von Minecraft bereitgestellt werden.

Du benötigst einen Konstruktor mit einem `int`-Argument, um die Klasse zu initialisieren.

@[code lang=java transcludeWith=:::ctor](@/reference/latest/src/main/java/com/example/docs/saveddata/SavedBlockData.java)

Dann können wir einen Codec bauen.

@[code lang=java transcludeWith=:::codec](@/reference/latest/src/main/java/com/example/docs/saveddata/SavedBlockData.java)

Wir sollten `setDirty()` aufrufen, wenn Daten tatsächlich geändert werden, damit Minecraft weiß, dass sie auf der Festplatte gespeichert werden müssen.

@[code lang=java transcludeWith=:::set_dirty](@/reference/latest/src/main/java/com/example/docs/saveddata/SavedBlockData.java)

Schließlich benötigen wir einen `SavedDataType`, der unsere gespeicherten Daten beschreibt. Das erste Argument entspricht dem Namen der Datei, die im Verzeichnis `data` der Welt erstellt wird.

@[code lang=java transcludeWith=:::type](@/reference/latest/src/main/java/com/example/docs/saveddata/SavedBlockData.java)

### Auf gespeicherte Daten zugreifen {#accessing-saved-data}

Wie bereits erwähnt, können gespeicherte Daten mit einem Geltungsbereich wie dem aktuellen Level verknüpft werden. In diesem Fall sind unsere Daten Teil der Level-Daten. Wir können die `DimensionDataStorage` des Level abrufen, um unsere Daten hinzuzufügen und zu ändern.

Wir werden diese Logik in eine Hilfsmethode einbauen.

@[code lang=java transcludeWith=:::method](@/reference/latest/src/main/java/com/example/docs/saveddata/SavedBlockData.java)

### Gespeicherte Daten verwenden {#using-saved-data}

Nachdem wir jetzt alles eingerichtet haben, speichern wir einige Daten.

Wir können das erste Szenario wiederverwenden und anstatt das Feld zu erhöhen, können wir unsere Funktion `incrementBlocksBroken` aus unseren `SavedBlockData` aufrufen.

@[code lang=java transcludeWith=:::event_registration](@/reference/latest/src/main/java/com/example/docs/saveddata/ExampleModSavedData.java)

Dies sollte den Wert erhöhen und ihn auf dem Datenträger speichern.

Wenn du Minecraft neu startest, die Welt lädst und einen Block abbaust, wirst du sehen, dass die zuvor gespeicherte Anzahl jetzt erhöht ist.

Wenn du in das Verzeichnis `data ` der Welt gehst, siehst du eine `.dat `-Datei mit dem Namen `saved_block_data.dat `.
Wenn du diese Datei in einem NBT-Reader öffnest, siehst du, wie unsere Daten darin gespeichert sind.

![NBTg](/assets/develop/saved-data/nbt.png)
