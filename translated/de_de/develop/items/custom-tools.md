---
title: Werkzeuge und Waffen
description: Lerne, wie du deine eigenen Werkzeuge erstellst und deren Eigenschaften konfigurierst.
authors:
  - bluebear94
  - cassiancc
  - ChampionAsh5357
  - IMB11
  - its-miroma
resources:
  https://docs.neoforged.net/docs/items/tools/: Werkzeuge - NeoForge Docs (ausgenommen Neo exklusive)
---

Werkzeuge sind für das Überleben und das Vorankommen unerlässlich, denn sie ermöglichen es den Spielern, Ressourcen zu sammeln, Gebäude zu bauen und sich zu verteidigen.

## Ein Werkzeugmaterial erstellen {#creating-a-tool-material}

Du kannst ein Werkzeugmaterial erstellen, indem du ein neues `ToolMaterial`-Objekt instanziierst und es in einem Feld speicherst, das später verwendet werden kann, um die Werkzeugelemente zu erstellen, die das Material verwenden.

<<< @/reference/latest/src/main/java/com/example/docs/item/ModItems.java#guidite_tool_material

Der `ToolMaterial`-Konstruktor akzeptiert die folgenden Parameter, in dieser spezifischen Reihenfolge:

| Parameter                 | Beschreibung                                                                                                                                                                                                                                                                                                                                                                                                                                             |
| ------------------------- | -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| `incorrectBlocksForDrops` | Wenn ein Block im Tag `incorrectBlocksForDrops` ist, bedeutet das, dass der Block keine Gegenstände fallen lässt, wenn man ein Werkzeug aus diesem `ToolMaterial` zum abbauen dieses Blocks benutzt.Befindet sich ein Block im Tag `incorrectBlocksForDrops`, bedeutet dies, dass der Block keine Item fallen lässt, wenn du ein Werkzeug, das aus diesem `ToolMaterial` hergestellt wurde, auf diesen Block verwendest. |
| `durability`              | Die Haltbarkeit aller Werkzeuge, die aus diesem `ToolMaterial` bestehen.                                                                                                                                                                                                                                                                                                                                                                 |
| `speed`                   | Die Abbaugeschwindigkeit der Werkzeuge, die aus diesem `ToolMaterial` bestehen.                                                                                                                                                                                                                                                                                                                                                          |
| `attackDamageBonus`       | Der zusätzliche Angriffsschaden der Werkzeuge, die aus diesem `ToolMaterial` sind.                                                                                                                                                                                                                                                                                                                                                       |
| `enchantmentValue`        | Die "Verzauberbarkeit" von Werkzeugen, die aus diesem `ToolMaterial` bestehen.                                                                                                                                                                                                                                                                                                                                                           |
| `repairItems`             | Alle Gegenstände, die in diesem Tag enthalten sind, können verwendet werden, um Werkzeuge aus diesem `ToolMaterial` in einem Amboss zu reparieren.                                                                                                                                                                                                                                                                                       |

Für dieses Beispiel verwenden wir dasselbe Item Tag zur Reparatur, das wir auch für Rüstungen verwenden werden. Wir definieren die Tag-Referenz wie folgt:

<<< @/reference/latest/src/main/java/com/example/docs/item/armor/GuiditeArmorMaterial.java#repair_tag

Wenn du Schwierigkeiten hast, ausgewogene Werte für einen der numerischen Parameter zu bestimmen, solltest du dir die Vanilla-Werkzeugmaterialkonstanten ansehen, wie zum Beispiel `ToolMaterial.STONE` oder `ToolMaterial.DIAMOND`.

### Ein Werkzeugmaterial Tag erstellen {#creating-the-tool-material-tag}

Für unser Tag `incorrectBlocksForDrops` können wir ein Tag erstellen, das zu den Vanilla Tags `minecraft:incorrect_for_*_drops` ähnlich ist. Diese legen fest, welche Blöcke beim Abbau mit dem jeweiligen Material **nicht** fallen gelassen werden. Lasst uns die Tag-Referenz wie folgt definieren:

<<< @/reference/latest/src/main/java/com/example/docs/item/ModItems.java#guidite_incorrect_blocks_tag

Als Nächstes definieren wir den Inhalt des Tags mithilfe einer Tag JSON-Datei. Lasst uns die Guidite-Werkzeuge dieselben Blöcke abbauen wie die Holzwerkzeuge, zusätzlich zu Kupfererz und Tiefschiefer-Kupfererz:

<<< @/reference/latest/src/main/resources/data/example-mod/tags/block/incorrect_for_guidite_tool.json

Beachte, dass dieses Beispiel von einem schwächeren Werkzeugmaterial abgeleitet ist und Einträge _entfernt_, die unser stärkeres Material abbauen kann, wobei es alle anderen Blöcke erbt, die Holz nicht abbauen kann.

::: tip

Wir könnten auch das umgekehrte machen: Von einem stärkeren Werkzeug erben und zusätzliche Blöcke _anhängen_, für die Guidite-Werkezge nicht geeignet sind.

Als Beispiel, wenn wir ein Werkzeug erstellen wollten, das wie Eisen funktioniert, aber kein Diamanterz abbauen kann, müsste `values` die Einträge `#minecraft:incorrect_for_iron_tool` und `#minecraft:diamond_ores` enthalten.

Wenn du möchtest, dass dein Werkzeug dieselben Blöcke abbaut wie ein bereits vorhandenes, kannst du das entsprechende Tag ohne Änderungen in die Definition deines Tags aufnehmen. Dies wird gegenüber der Übergabe des vorhandenen Tag als `incorrectBlocksForDrops` deines Materials empfohlen, damit Benutzer die falschen Blöcke für jedes Material unabhängig voneinander konfigurieren können.

:::

## Werkzeugitems registrieren {#registering-tool-items}

Mit der gleichen Hilfsfunktion wie in der Anleitung [Erstelle dein ersten Item](./first-item) kannst du deine Werkzeugitems erstellen:

<<< @/reference/latest/src/main/java/com/example/docs/item/ModItems.java#guidite_sword

Die beiden Float-Werte (1f, 1f) beziehen sich auf den Angriffsschaden des Werkzeugs bzw. die Angriffsgeschwindigkeit des Werkzeugs.

Für Schaufeln, Äxte und Hacken solltest du statt eines generischen `Item` ein `ShovelItem`, `AxeItem` oder `HoeItem` erstellen, da diese werkzeugspezifische Rechtsklick-Aktionen implementieren:

<<< @/reference/latest/src/main/java/com/example/docs/item/ModItems.java#axe

::: info

`ShovelItem`, `AxeItem` und `HoeItem` rufen in ihren Konstruktoren die Methode `shovel`, `axe` oder `hoe` von `Item.Properties` auf.

:::

Vergiss nicht, sie zu einem Kreativtab hinzuzufügen, wenn du vom Kreativinventar aus auf sie zugreifen willst!

<<< @/reference/latest/src/main/java/com/example/docs/item/ModItems.java#add_guidite_sword_to_create_tab

Du musst auch eine Textur, eine Itemübersetzung und ein Itemmodell hinzufügen. Für das Itemmodell solltest du jedoch anstelle von dem üblichen `item/generated` das Modell `item/handheld` als übergeordnetes Modell verwenden.

## Assets {#models}

Du wirst auch eine [Textur](./first-item#adding-a-texture), [eine Übersetzung](./first-item#naming-the-item), [ein Client Item](./first-item#creating-the-client-item) und [ein Itemmodell](./item-models) hinzufügen müssen. Für das Itemmodell solltest du jedoch anstelle von dem üblichen `item/generated` das Modell `item/handheld` als übergeordnetes Modell verwenden.

In diesem Beispiel werden wir das folgende Client Item, Modell und die folgende Textur für das Item "Guidite Sword" definieren:

:::: tabs

== Quellcode

::: info

Das Modell kann datengeneriert werden. Weitere Informationen findest du in der Dokumentation zur Erstellung von [Itemmodellen](../data-generation/item-models).

:::

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModModelProvider.java#sword

== Client Item

`generated/assets/example-mod/items/guidite_sword.json`

<<< @/reference/latest/src/main/generated/assets/example-mod/items/guidite_sword.json

== Item-Modell

`generated/assets/example-mod/models/item/guidite_sword.json`

<<< @/reference/latest/src/main/generated/assets/example-mod/models/item/guidite_sword.json

== Textur

<DownloadEntry visualURL="/assets/develop/items/tools_0.png" downloadURL="/assets/develop/items/tools_0_small.png">Guidite Sword Textur</DownloadEntry>

::::

Ein ähnliches Muster gilt für das Item "Guidite Axe".

::: tabs

== Quellcode

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModModelProvider.java#handheld

== Client Item

`generated/assets/example-mod/items/guidite_axe.json`

<<< @/reference/latest/src/main/generated/assets/example-mod/items/guidite_axe.json

== Item-Modell

`generated/assets/example-mod/models/item/guidite_axe.json`

<<< @/reference/latest/src/main/generated/assets/example-mod/models/item/guidite_axe.json

== Textur

<DownloadEntry visualURL="/assets/develop/data-generation/item-model/guidite_axe_big.png" downloadURL="/assets/develop/data-generation/item-model/guidite_axe.png">Guidite-Axt-Textur</DownloadEntry>

:::

## Werkzeugitems taggen {#tags}

:::info VORAUSSETZUNGEN

Weitere Informationen findest du in der Dokumentation zur Erstellung von [Item Tags](../data-generation/tags).

:::

Es wird außerdem empfohlen, dein Werkzeug in den entsprechenden Item Tags einzuordnen. Werkzeuge verfügen über eigene Tags, wie beispielsweise `ItemTags.SWORDS`, die für die Verzauberbarkeit und andere spezifische Logik verwendet werden, beispielsweise ob ein Schwungkraft-Schaden verursacht werden soll.

Füge die folgenden Zeilen zu `addTags` in deinem Item Tag Provider hinzu:

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModItemTagProvider.java#sword_tags

Das war's dann auch schon! Im Spiel solltest du deine Werkzeuge in der Registerkarte "Tools and Utilities" im Kreativ Inventar Menü sehen.

![Fertige Werkzeuge im Inventar](/assets/develop/items/tools_1.png)
