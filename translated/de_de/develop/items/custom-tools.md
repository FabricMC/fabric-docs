---
title: Werkzeuge und Waffen
description: Lerne, wie du deine eigenen Werkzeuge erstellst und deren Eigenschaften konfigurierst.
authors:
  - bluebear94
  - cassiancc
  - ChampionAsh5357
  - IMB11
  - its-miroma
  - NotNightSky
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

Du kannst deine Werkzeug-Items erstellen, indem du dieselben Klassen und Funktionen verwendest, die wir in dem Leitfaden [Dein erstes Item erstellen](./first-item) implementiert haben:

:::: tabs

== Schwert

Der float-Parameter bezieht sich jeweils auf den Angriffsschaden und die Angriffsgeschwindigkeit.

<<< @/reference/latest/src/main/java/com/example/docs/item/ModItemIds.java#guidite_sword

<<< @/reference/latest/src/main/java/com/example/docs/item/ModItems.java#guidite_sword

== Speer

Die float-Parameter beziehen sich auf die Angriffsdauer, den Schadensmultiplikator, die Verzögerung, den Zeitpunkt des Absteigens vom Speer, den Schwellenwert für das Absteigen, den Zeitpunkt des Rückstoßes, den Rückstoß-Schwellenwert, den Zeitpunkt des Schadens und den Schadens-Schwellenwert.

<<< @/reference/latest/src/main/java/com/example/docs/item/ModItemIds.java#guidite_spear

<<< @/reference/latest/src/main/java/com/example/docs/item/ModItems.java#guidite_spear

== Spitzhacke

Der float-Parameter bezieht sich jeweils auf den Angriffsschaden und die Angriffsgeschwindigkeit.

<<< @/reference/latest/src/main/java/com/example/docs/item/ModItemIds.java#guidite_pickaxe

<<< @/reference/latest/src/main/java/com/example/docs/item/ModItems.java#guidite_pickaxe

== Axt

Für Äxte solltest du ein `AxeItem` anstelle eines generischen `Item` erstellen. Dies implementiert axspezifische Rechtsklick-Aktionen und ruft automatisch die Methode `axe` von `Item.Properties` auf.

Der float-Parameter bezieht sich jeweils auf den Angriffsschaden und die Angriffsgeschwindigkeit.

<<< @/reference/latest/src/main/java/com/example/docs/item/ModItemIds.java#guidite_axe

<<< @/reference/latest/src/main/java/com/example/docs/item/ModItems.java#guidite_axe

== Schaufel

Für Schaufeln solltest du ein `ShovelItem` anstelle eines generischen `Item` erstellen. Dadurch werden Schaufel-spezifische Rechtsklick-Aktionen implementiert, und die Methode `shovel` von `Item.Properties` wird automatisch aufgerufen.

Der float-Parameter bezieht sich jeweils auf den Angriffsschaden und die Angriffsgeschwindigkeit.

<<< @/reference/latest/src/main/java/com/example/docs/item/ModItemIds.java#guidite_shovel

<<< @/reference/latest/src/main/java/com/example/docs/item/ModItems.java#guidite_shovel

== Hacke

Für Hacken solltest du ein `HoeItem` anstelle eines generischen `Item` erstellen. Dadurch werden Hacken-spezifische Rechtsklick-Aktionen implementiert, und die Methode `hoe` von `Item.Properties` wird automatisch aufgerufen.

Der float-Parameter bezieht sich jeweils auf den Angriffsschaden und die Angriffsgeschwindigkeit.

<<< @/reference/latest/src/main/java/com/example/docs/item/ModItemIds.java#guidite_hoe

<<< @/reference/latest/src/main/java/com/example/docs/item/ModItems.java#guidite_hoe

::::

Vergiss nicht, sie zu einem Kreativtab hinzuzufügen, wenn du vom Kreativinventar aus auf sie zugreifen willst!

<<< @/reference/latest/src/main/java/com/example/docs/item/ModItems.java#add_guidite_sword_to_create_tab

Du musst auch eine Textur, eine Itemübersetzung und ein Itemmodell hinzufügen. Für das Itemmodell solltest du jedoch anstelle von dem üblichen `item/generated` das Modell `item/handheld` als übergeordnetes Modell verwenden.

## Assets {#models}

:::: tabs

::::

Du wirst auch eine [Textur](./first-item#adding-a-texture), [eine Übersetzung](./first-item#naming-the-item), [ein Client Item](./first-item#creating-the-client-item) und [ein Itemmodell](./item-models) hinzufügen müssen. Für das Itemmodell solltest du jedoch anstelle von dem üblichen `item/generated` das Modell `item/handheld` als übergeordnetes Modell verwenden.

::: info

Die folgenden Modelle wurden mithilfe der Datengenerierung erstellt. Weitere Informationen findest du in der Dokumentation zum Thema [Erstellen von Itemmodellen](../data-generation/item-models).

:::

::: tabs

== Schwert

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModModelProvider.java#guidite_sword

== Speer

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModModelProvider.java#guidite_spear

== Spitzhacke

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModModelProvider.java#guidite_pickaxe

== Axt

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModModelProvider.java#guidite_axe

== Schaufel

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModModelProvider.java#guidite_shovel

== Hacke

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModModelProvider.java#guidite_hoe

:::

Hier sind alle in diesem Leitfaden verwendeten Beispieltexturen:

<DownloadEntry downloadURL="/assets/develop/items/tools.zip">Guidite-Werkzeugtexturen</DownloadEntry>

## Werkzeugitems taggen {#tags}

:::info VORAUSSETZUNGEN

Weitere Informationen findest du in der Dokumentation zur Erstellung von [Item Tags](../data-generation/tags).

:::

Es wird außerdem empfohlen, dein Werkzeug in den entsprechenden Item Tags einzuordnen. Werkzeuge verfügen über eigene Tags, wie beispielsweise `ItemTags.SWORDS`, die für die Verzauberbarkeit und andere spezifische Logik verwendet werden, beispielsweise ob ein Schwungkraft-Schaden verursacht werden soll.

Füge die folgenden Zeilen zu `addTags` in deinem Item Tag Provider hinzu:

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModItemTagProvider.java#tool_tags

Das war's dann auch schon! Im Spiel solltest du deine Werkzeuge in der Registerkarte "Tools and Utilities" im Kreativ Inventar Menü sehen.

![Fertige Werkzeuge im Inventar](/assets/develop/items/tools_1.png)
