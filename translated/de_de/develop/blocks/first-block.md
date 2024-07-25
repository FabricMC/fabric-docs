---
title: Deinen ersten Block erstellen
description: Lerne, wie du deinen ersten benutzerdefinierten Block in Minecraft erstellen kannst.
authors:
  - IMB11
---

# Deinen ersten Block erstellen {#creating-your-first-block}

Blöcke sind die Baublöcke von Minecraft (kein Wortspiel beabsichtigt) - genau wie alles andere in Minecraft, werden sie in Registern gespeichert.

## Deine Blockklasse vorbereiten {#preparing-your-blocks-class}

Wenn du die Seite [Dein erstes Item erstellen](../items/first-item) abgeschlossen hast, wird dir dieser Prozess sehr vertraut vorkommen - Du musst eine Methode erstellen, die deinen Block und sein Blockitem registriert.

Du solltest diese Methode in eine Klasse mit dem Namen `ModBlocks` (oder wie auch immer du sie nennen willst) einfügen.

Mojang macht etwas sehr ähnliches mit Vanilleblöcken; Sie können sich die Klasse `Blocks` ansehen, um zu sehen, wie sie es machen.

@[code transcludeWith=:::1](@/reference/latest/src/main/java/com/example/docs/block/ModBlocks.java)

---

Genau wie bei den Items musst du sicherstellen, dass die Klasse geladen ist, damit alle statischen Felder, die Ihre Blockinstanzen enthalten, initialisiert werden.

Du kannst dies tun, indem du eine Dummy-Methode `initialize` erstellst, die in deinem Mod-Initialisierer aufgerufen werden kann, um die statische Initialisierung auszulösen.

:::info
Wenn du nicht weißt, was statische Initialisierung ist, ist es der Prozess der Initialisierung von statischen Feldern in einer Klasse. Dies geschieht, wenn die Klasse von der JVM geladen wird, und zwar bevor Instanzen der Klasse erstellt werden.
:::

```java
public class ModBlocks {
    // ...

    public static void initialize() {}
}
```

@[code transcludeWith=:::2](@/reference/latest/src/main/java/com/example/docs/block/ModBlocks.java)

## Erstellen und Registrieren deines Blocks {#creating-and-registering-your-block}

Ähnlich wie Items nehmen Blöcke in ihrem Konstruktor die Klasse `Blocks.Settings` auf, die Eigenschaften des Blocks festlegt, wie z.B. seine Soundeffekte und die Abbauebene.

Wir werden hier nicht alle Optionen behandeln - Du kannst die Klasse selbst ansehen, um die verschiedenen Optionen zu sehen, die selbsterklärend sein sollten.

Als Beispiel werden wir einen einfachen Block erstellen, der die Eigenschaften von Erde hat, aber ein anderes Material ist.

:::tip
Du kannst auch `AbstractBlock.Settings.copy(AbstractBlock block)` verwenden, um die Einstellungen eines bestehenden Blocks zu kopieren. In diesem Fall hätten wir auch `Blocks.DIRT` verwenden können, um die Einstellungen von Erde zu kopieren, aber für das Beispiel verwenden wir den Builder.
:::

@[code transcludeWith=:::2](@/reference/latest/src/main/java/com/example/docs/block/ModBlocks.java)

Um das Blockitem automatisch zu erstellen, können wir dem Parameter `shouldRegisterItem` der Methode `register`, die wir im vorherigen Schritt erstellt haben, `true` übergeben.

### Deinen Block zu einer Itemgruppe hinzufügen {#adding-your-block-to-an-item-group}

Da das `BlockItem` automatisch erstellt und registriert wird, musst du, um ihn zu einer Itemgruppe hinzuzufügen, die Methode `Block.asItem()` verwenden, um die `BlockItem`-Instanz zu erhalten.

In diesem Beispiel wird eine benutzerdefinierte Itemgruppe verwendet, die auf der Seite [Benutzerdefinierte Itemgruppe](../items/custom-item-groups) erstellt wurde.

@[code transcludeWith=:::3](@/reference/latest/src/main/java/com/example/docs/block/ModBlocks.java)

---

Du solltest nun feststellen, dass sich dein Block im Kreativ-Inventar befindet und in der Welt platziert werden kann!

![Block in der Welt ohne passendes Modell oder Textur](/assets/develop/blocks/first_block_0.png).

Es gibt jedoch ein paar Probleme - das Blockitem ist nicht benannt, und der Block hat keine Textur, kein Blockmodell und kein Itemmodell.

## Blockübersetzungen hinzufügen {#adding-block-translations}

Um eine Übersetzung hinzuzufügen, musst du einen Übersetzungsschlüssel in deiner Übersetzungsdatei erstellen - `assets/<mod id here>/lang/en_us.json`.

Minecraft verwendet diese Übersetzung im Kreativ-Inventar und an anderen Stellen, an denen der Blockname angezeigt wird, wie z. B. bei der Befehlsrückmeldung.

```json
{
    "block.mod_id.condensed_dirt": "Condensed Dirt"
}
```

Du kannst entweder das Spiel neu starten oder deinen Mod erstellen und <kbd>F3</kbd> + <kbd>T</kbd> drücken, um die Änderungen zu übernehmen - und du solltest sehen, dass der Block einen Namen im kreativen Inventar und an anderen Stellen wie dem Statistikbildschirm hat.

## Modelle und Texturen {#models-and-textures}

Alle Blocktexturen befinden sich im Ordner `assets/<mod id here>/textures/block` - eine Beispieltextur für den Block "Condensed Dirt" ist frei verwendbar.

<DownloadEntry type="Texture" visualURL="/assets/develop/blocks/first_block_1.png" downloadURL="/assets/develop/blocks/first_block_1_small.png" />

Damit die Textur im Spiel angezeigt wird, musst du einen Block und ein Itemmodell erstellen, die du an den entsprechenden Stellen für den "Condensed Dirt"-Block finden kannst:

- `assets/<mod id here>/models/block/condensed_dirt.json`
- `assets/<mod id here>/models/item/condensed_dirt.json`

Das Itemmodell ist ziemlich einfach, es kann einfach das Blockmodell als Elternteil verwenden - da die meisten Blockmodelle Unterstützung für die Darstellung in einer grafischen Benutzeroberfläche haben:

@[code](@/reference/latest/src/main/resources/assets/fabric-docs-reference/models/item/condensed_dirt.json)

Das Blockmodell muss jedoch in unserem Fall dem Modell `block/cube_all` übergeordnet sein:

@[code](@/reference/latest/src/main/resources/assets/fabric-docs-reference/models/block/condensed_dirt.json)

Wenn du das Spiel lädst, wirst du feststellen, dass die Textur noch fehlt. Dies liegt daran, dass du eine Blockzustand-Definition hinzufügen musst.

## Eine Blockzustandsdefinition erstellen {#creating-the-block-state-definition}

Die Blockzustand-Definition wird verwendet, um dem Spiel mitzuteilen, welches Modell je nach dem aktuellen Zustand des Blocks gerendert werden soll.

Für den Beispielblock, der keinen komplexen Blockzustand hat, ist nur ein Eintrag in der Definition erforderlich.

Diese Datei sollte sich im Ordner `assets/mod_id/blockstates` befinden, und ihr Name sollte mit der Block-ID übereinstimmen, die bei der Registrierung des Blocks in der Klasse `ModBlocks` verwendet wurde. Wenn die Block-ID beispielsweise `condensed_dirt` lautet, sollte die Datei `condensed_dirt.json` heißen.

@[code](@/reference/latest/src/main/resources/assets/fabric-docs-reference/blockstates/condensed_dirt.json)

Blockzustände sind sehr komplex, weshalb sie auf einer späteren Seite behandelt werden: [Blockzustände](./blockstates)

Starte das Spiel neu oder lade es über <kbd>F3</kbd> + <kbd>T</kbd> neu, um die Änderungen zu übernehmen - Du solltest die Blocktextur im Inventar und physisch in der Welt sehen können:

![Block in der Welt mit einer passenden Textur und Modell](/assets/develop/blocks/first_block_4.png)

## Blockdrops hinzufügen {#adding-block-drops}

Wenn man den Block im Survival-Modus abbaut, kann es sein, dass der Block nicht fallen gelassen wird - diese Funktionalität ist vielleicht erwünscht, aber um den Block als Item fallen zu lassen, wenn er abgebaut wird, muss man seine Beutetabelle implementieren - die Beutetabellendatei sollte in den Ordner `data/<mod id here>/loot_table/blocks/` abgelegt werden.

:::info
Für ein besseres Verständnis der Beutetabellen kannst du dir die Seite [Minecraft Wiki - Beutetabellen](https://de.minecraft.wiki/w/Beutetabellen) ansehen.
:::

@[code](@/reference/latest/src/main/resources/data/fabric-docs-reference/loot_tables/blocks/condensed_dirt.json)

Diese Beutetabelle enthält einen einzelnen Gegenstand, der fallen gelassen wird, wenn der Block abgebaut wird und wenn er durch eine Explosion gesprengt wird.

## Ein Abbauwerkzeug vorschlagen {#recommending-a-harvesting-tool}

Vielleicht möchtest du auch, dass dein Block nur mit einem bestimmten Werkzeug abgebaut werden kann - zum Beispiel möchtest du, dass dein Block schneller mit einer Schaufel abgebaut werden kann.

Alle Tool-Tags sollten im Ordner `data/minecraft/tags/block/mineable/` abgelegt werden - der Name der Datei hängt von der Art des verwendeten Tools ab, einer der folgenden:

- `hoe.json`
- `axe.json`
- `pickaxe.json`
- `shovel.json`

Der Inhalt der Datei ist recht einfach - es handelt sich um eine Liste von Elementen, die dem Tag hinzugefügt werden sollen.

In diesem Beispiel wird der Block "Condensed Dirt" zum Tag `shovel` hinzugefügt.

@[code](@/reference/latest/src/main/resources/data/minecraft/tags/mineable/shovel.json)

## Abbauebene {#mining-levels}

In ähnlicher Weise befindet sich das Tag für die Abbauebene im selben Ordner und hat das folgende Format:

- `needs_stone_tool.json` - Eine minimale Ebene für Steinwerkzeuge.
- `needs_iron_tool.json` - Eine minimale Ebene für Eisenwerkzeuge.
- `needs_diamond_tool.json` - Eine minimale Ebene für Diamantwerkzeuge.

Die Datei hat das gleiche Format wie die Datei des Abbauwerkzeuges - eine Liste von Items, die dem Tag hinzugefügt werden sollen.

## Zusatz Notizen {#extra-notes}

Wenn du mehrere Blöcke zu deinem Mod hinzufügst, solltest du die [Datengenerierung](https://fabricmc.net/wiki/tutorial:datagen_setup) in Betracht ziehen, um den Prozess der Erstellung von Block- und Itemmodellen, Blockzustandsdefinitionen und Beutetabellen zu automatisieren.
