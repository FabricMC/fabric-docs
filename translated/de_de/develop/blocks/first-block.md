---
title: Deinen ersten Block erstellen
description: Lerne, wie du deinen ersten benutzerdefinierten Block in Minecraft erstellen kannst.
authors:
  - CelDaemon
  - Earthcomputer
  - IMB11
  - its-miroma
  - xEobardThawne
---

Blöcke sind die Baublöcke von Minecraft (kein Wortspiel beabsichtigt) - genau wie alles andere in Minecraft, werden sie in Registern gespeichert.

## Deine Blockklasse vorbereiten {#preparing-your-blocks-class}

Wenn du die Seite [Dein erstes Item erstellen](../items/first-item) abgeschlossen hast, wird dir dieser Prozess sehr vertraut vorkommen - Du musst eine Methode erstellen, die deinen Block und sein Block-Item registriert.

Du solltest diese Methode in eine Klasse mit dem Namen `ModBlocks` (oder wie auch immer du sie nennen willst) einfügen.

Mojang macht etwas sehr ähnliches mit Vanilleblöcken; Sie können sich die Klasse `Blocks` ansehen, um zu sehen, wie sie es machen.

@[code transcludeWith=:::1](@/reference/latest/src/main/java/com/example/docs/block/ModBlocks.java)

Genau wie bei den Items musst du sicherstellen, dass die Klasse geladen ist, damit alle statischen Felder, die Ihre Blockinstanzen enthalten, initialisiert werden.

Du kannst dies tun, indem du eine Dummy-Methode `initialize` erstellst, die in deinem [Mod-Initialisierer](../getting-started/project-structure#entrypoints) aufgerufen werden kann, um die statische Initialisierung auszulösen.

::: info

Wenn du nicht weißt, was statische Initialisierung ist, ist es der Prozess der Initialisierung von statischen Feldern in einer Klasse. Dies geschieht, wenn die Klasse von der JVM geladen wird, und zwar bevor Instanzen der Klasse erstellt werden.

:::

```java
public class ModBlocks {
    // ...

    public static void initialize() {}
}
```

@[code transcludeWith=:::1](@/reference/latest/src/main/java/com/example/docs/block/ExampleModBlocks.java)

## Erstellen und Registrieren deines Blocks {#creating-and-registering-your-block}

Ähnlich wie Items nehmen Blöcke in ihrem Konstruktor die Klasse `BlockBehavior.Properties` auf, die Eigenschaften des Blocks festlegt, wie z.B. seine Soundeffekte und die Abbauebene.

Wir werden hier nicht alle Optionen behandeln - Du kannst die Klasse selbst ansehen, um die verschiedenen Optionen zu sehen, die selbsterklärend sein sollten.

Als Beispiel werden wir einen einfachen Block erstellen, der die Eigenschaften von Erde hat, aber ein anderes Material ist.

- Wir erstellen unsere Blockeinstellungen auf ähnliche Weise, wie wir sie im Item-Tutorial erstellt haben.
- Wir weisen die Methode `register` an, eine `Block`-Instanz aus den Blockeinstellungen zu erstellen, indem wir den `Block`-Konstruktor aufrufen.

::: tip

Du kannst auch `BlockBehavior.Properties.ofFullCopy(ofFullCopy block)` verwenden, um die Einstellungen eines bestehenden Blocks zu kopieren. In diesem Fall hätten wir auch `Blocks.DIRT` verwenden können, um die Einstellungen von Erde zu kopieren, aber für das Beispiel verwenden wir den Builder.

:::

@[code transcludeWith=:::2](@/reference/latest/src/main/java/com/example/docs/block/ModBlocks.java)

Um das Blockitem automatisch zu erstellen, können wir dem Parameter `shouldRegisterItem` der Methode `register`, die wir im vorherigen Schritt erstellt haben, `true` übergeben.

### Dein Blockitem zu einem Kreativtab hinzufügen {#adding-your-block-s-item-to-a-creative-tab}

Da das `BlockItem` automatisch erstellt und registriert wird, musst du, um es zu einem Kreativtab hinzuzufügen, die Methode `Block.asItem()` verwenden, um die `BlockItem`-Instanz zu erhalten.

Für dieses Beispiel werden wir den Block zu dem Tab `BUILDING_BLOCKS` hinzufügen. Um den Block stattdessen zu einem benutzerdefinierten Kreativtab hinzuzufügen, siehe [benuterdefinierte Kreativtabs](../items/custom-item-groups).

@[code transcludeWith=:::6](@/reference/latest/src/main/java/com/example/docs/block/ModBlocks.java)

Du solltest dies innerhalb deiner `initialize()`-Funktion deiner Klasse hinzufügen.

Du solltest nun feststellen, dass sich dein Block im Kreativ-Inventar befindet und in der Welt platziert werden kann!

![Block in der Welt ohne passendes Modell oder Textur](/assets/develop/blocks/first_block_0.png)

Es gibt jedoch ein paar Probleme - das Blockitem ist nicht benannt, und der Block hat keine Textur, kein Blockmodell und kein Itemmodell.

## Blockübersetzungen hinzufügen {#adding-block-translations}

Um eine Übersetzung hinzuzufügen, musst du einen Übersetzungsschlüssel in deiner Übersetzungsdatei erstellen - `assets/example-mod/lang/en_us.json`.

Minecraft verwendet diese Übersetzung im Kreativ-Inventar und an anderen Stellen, an denen der Blockname angezeigt wird, wie z. B. bei der Befehlsrückmeldung.

```json
{
  "block.example-mod.condensed_dirt": "Condensed Dirt"
}
```

Du kannst entweder das Spiel neu starten oder deinen Mod erstellen und <kbd>F3</kbd>+<kbd>T</kbd> drücken, um die Änderungen zu übernehmen - und du solltest sehen, dass der Block einen Namen im kreativen Inventar und an anderen Stellen wie dem Statistikbildschirm hat.

## Modelle und Texturen {#models-and-textures}

Alle Blocktexturen befinden sich im Ordner `assets/example-mod/textures/block` - eine Beispieltextur für den Block "Condensed Dirt" ist frei verwendbar.

<DownloadEntry visualURL="/assets/develop/blocks/first_block_1.png" downloadURL="/assets/develop/blocks/first_block_1_small.png">Textur</DownloadEntry>

Damit die Textur im Spiel angezeigt wird, musst du ein Blockmodell erstellen, das in der Datei `assets/example-mod/models/block/condensed_dirt.json` für den "Condensed Dirt"-Block gefunden werden kann. Für diesen Block werden wir den Modelltyp `block/cube_all` verwenden.

@[code](@/reference/latest/src/main/generated/assets/example-mod/models/block/condensed_dirt.json)

Damit der Block in deinem Inventar angezeigt wird, musst du ein [Client Item](../items/first-item#creating-the-client-item) erstellen, das auf dein Blockmodell verweist. Für dieses Beispiel kann das Client Item für den "Condensed Dirt" Block unter `assets/example-mod/items/condensed_dirt.json` gefunden werden.

@[code](@/reference/latest/src/main/generated/assets/example-mod/items/condensed_dirt.json)

::: tip

Du musst nur ein Client Item erstellen, wenn du ein `BlockItem` zusammen mit deinem Block registriert hast!

:::

Wenn du das Spiel lädst, wirst du feststellen, dass die Textur noch fehlt. Dies liegt daran, dass du eine Blockzustand-Definition hinzufügen musst.

## Eine Blockzustandsdefinition erstellen {#creating-the-block-state-definition}

Die Blockzustand-Definition wird verwendet, um dem Spiel mitzuteilen, welches Modell je nach dem aktuellen Zustand des Blocks gerendert werden soll.

Für den Beispielblock, der keinen komplexen Blockzustand hat, ist nur ein Eintrag in der Definition erforderlich.

Diese Datei sollte sich im Ordner `assets/example-mod/blockstates` befinden, und ihr Name sollte mit der Block-ID übereinstimmen, die bei der Registrierung des Blocks in der Klasse `ModBlocks` verwendet wurde. Wenn die Block-ID beispielsweise `condensed_dirt` lautet, sollte die Datei `condensed_dirt.json` heißen.

@[code](@/reference/latest/src/main/generated/assets/example-mod/blockstates/condensed_dirt.json)

::: tip

Blockstates sind unglaublich komplex, weshalb sie als Nächstes auf [einer eigenen Seite](./blockstates) behandelt werden.

:::

Starte das Spiel neu oder lade es über <kbd>F3</kbd>+<kbd>T</kbd> neu, um die Änderungen zu übernehmen - Du solltest die Blocktextur im Inventar und physisch in der Welt sehen können:

![Block in der Welt mit einer passenden Textur und Modell](/assets/develop/blocks/first_block_4.png)

## Blockdrops hinzufügen {#adding-block-drops}

Wenn man den Block im Survival-Modus abbaut, kann es sein, dass der Block nicht fallen gelassen wird - diese Funktionalität ist vielleicht erwünscht, aber um den Block als Item fallen zu lassen, wenn er abgebaut wird, muss man seine Beutetabelle implementieren - die Beutetabellendatei sollte in den Ordner `data/example-mod/loot_table/blocks/` abgelegt werden.

::: info

Für ein besseres Verständnis der Beutetabellen kannst du dir die Seite [Minecraft Wiki - Beutetabellen](https://de.minecraft.wiki/w/Beutetabellen) ansehen.

:::

@[code](@/reference/latest/src/main/resources/data/example-mod/loot_tables/blocks/condensed_dirt.json)

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

Wenn du möchtest, dass ein Tool zum Abbau des Blocks erforderlich ist, musst du die Blockeinstellungen um den Zusatz `.requiresCorrectToolForDrops()` erweitern und das entsprechende Mining-Tag hinzufügen.

## Abbauebene {#mining-levels}

Ähnlich verhält es sich mit dem Mining-Level-Tag, das im Ordner `data/minecraft/tags/block/` zu finden ist und das folgende Format hat:

- `needs_stone_tool.json` - Eine minimale Ebene für Steinwerkzeuge
- `needs_iron_tool.json` - Eine minimale Ebene für Eisenwerkzeuge
- `needs_diamond_tool.json` - Eine minimale Ebene für Diamantwerkzeuge.

Die Datei hat das gleiche Format wie die Datei des Abbauwerkzeuges - eine Liste von Items, die dem Tag hinzugefügt werden sollen.

## Zusatz Notizen {#extra-notes}

Wenn du mehrere Blöcke zu deinem Mod hinzufügst, solltest du die [Datengenerierung](../data-generation/setup) in Betracht ziehen, um den Prozess der Erstellung von Block- und Itemmodellen, Blockzustandsdefinitionen und Beutetabellen zu automatisieren.
