---
title: Werkzeuge und Waffen
description: Lerne, wie du deine eigenen Werkzeuge erstellst und deren Eigenschaften konfigurierst.
authors:
  - IMB11
---

Werkzeuge sind für das Überleben und das Vorankommen unerlässlich, denn sie ermöglichen es den Spielern, Ressourcen zu sammeln, Gebäude zu bauen und sich zu verteidigen.

## Ein Werkzeugmaterial erstellen {#creating-a-tool-material}

Du kannst ein Werkzeugmaterial erstellen, indem du ein neues `ToolMaterial`-Objekt instanziierst und es in einem Feld speicherst, das später verwendet werden kann, um die Werkzeugelemente zu erstellen, die das Material verwenden.

@[code transcludeWith=:::guidite_tool_material](@/reference/latest/src/main/java/com/example/docs/item/ModItems.java)

Der `ToolMaterial`-Konstruktor akzeptiert die folgenden Parameter, in dieser spezifischen Reihenfolge:

| Parameter                 | Beschreibung                                                                                                                                                                                                       |
| ------------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------ |
| `incorrectBlocksForDrops` | Wenn ein Block im incorrectBlocksForDrops-Tag ist, bedeutet das, dass der Block keine Gegenstände fallen lässt, wenn man ein Werkzeug aus diesem `ToolMaterial` zum abbauen dieses Blocks benutzt. |
| `durability`              | Die Haltbarkeit aller Werkzeuge, die aus diesem `ToolMaterial` bestehen.                                                                                                                           |
| `speed`                   | Die Abbaugeschwindigkeit der Werkzeuge, die aus diesem `ToolMaterial` bestehen.                                                                                                                    |
| `attackDamageBonus`       | Der zusätzliche Angriffsschaden der Werkzeuge, die aus diesem `ToolMaterial` sind.                                                                                                                 |
| `enchantmentValue`        | Die "Verzauberbarkeit" von Werkzeugen, die aus diesem `ToolMaterial` bestehen.                                                                                                                     |
| `repairItems`             | Alle Gegenstände, die in diesem Tag enthalten sind, können verwendet werden, um Werkzeuge aus diesem `ToolMaterial` in einem Amboss zu reparieren.                                                 |

Für dieses Beispiel verwenden wir denselben Gegenstand zur Reparatur, den wir auch für Rüstungen verwenden werden. Wir definieren die Tag-Referenz wie folgt:

@[code transcludeWith=:::repair_tag](@/reference/latest/src/main/java/com/example/docs/item/armor/GuiditeArmorMaterial.java)

Wenn du Schwierigkeiten hast, ausgewogene Werte für einen der numerischen Parameter zu bestimmen, solltest du dir die Vanilla-Werkzeugmaterialkonstanten ansehen, wie zum Beispiel `ToolMaterial.STONE` oder `ToolMaterial.DIAMOND`.

## Werkzeugitems erstellen {#creating-tool-items}

Mit der gleichen Hilfsfunktion wie in der Anleitung [Erstelle dein ersten Item](./first-item) kannst du deine Werkzeugitems erstellen:

@[code transcludeWith=:::7](@/reference/latest/src/main/java/com/example/docs/item/ModItems.java)

Die beiden Float-Werte (1f, 1f) beziehen sich auf den Angriffsschaden des Werkzeugs bzw. die Angriffsgeschwindigkeit des Werkzeugs.

Vergiss nicht, sie zu einem Kreativtab hinzuzufügen, wenn du vom Kreativinventar aus auf sie zugreifen willst!

@[code transcludeWith=:::8](@/reference/latest/src/main/java/com/example/docs/item/ModItems.java)

Du musst auch eine Textur, eine Itemübersetzung und ein Itemmodell hinzufügen. Für das Itemmodell solltest du jedoch anstelle von dem üblichen `item/generated` das Modell `item/handheld` als übergeordnetes Modell verwenden.

In diesem Beispiel verwende ich das folgende Modell und die folgende Textur für den Gegenstand "Guidite Sword":

@[code](@/reference/latest/src/main/generated/assets/example-mod/models/item/guidite_sword.json)

<DownloadEntry visualURL="/assets/develop/items/tools_0.png" downloadURL="/assets/develop/items/tools_0_small.png">Textur</DownloadEntry>

Das war's dann auch schon! Im Spiel solltest du deine Werkzeuge auf der Registerkarte Werkzeuge im Kreativ Inventar sehen.

![Fertige Werkzeuge im Inventar](/assets/develop/items/tools_1.png)
