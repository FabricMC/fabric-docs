---
title: Werkzeuge und Waffen
description: Lerne, wie du deine eigenen Werkzeuge erstellst und deren Eigenschaften konfigurierst.
authors:
  - IMB11
---

# Werkzeuge {#tools}

Werkzeuge sind für das Überleben und das Vorankommen unerlässlich, denn sie ermöglichen es den Spielern, Ressourcen zu sammeln, Gebäude zu bauen und sich zu verteidigen.

## Ein Werkzeugmaterial erstellen {#creating-a-tool-material}

::: info
If you're creating multiple tool materials, consider using an `Enum` to store them. Vanilla does this in the `ToolMaterials` class, which stores all the tool materials that are used in the game.

Diese Klasse kann auch verwendet werden, um die Eigenschaften deines Werkzeugmaterials im Verhältnis zu Vanilla-Werkzeugmaterialien zu bestimmen.
:::

Du kannst ein Werkzeugmaterial erstellen, indem du eine neue Klasse erstellst, die es erbt - in diesem Beispiel werde ich "Guidite"-Werkzeuge erstellen:

@[code transcludeWith=:::1](@/reference/latest/src/main/java/com/example/docs/item/tool/GuiditeMaterial.java)

Das Werkzeugmaterial gibt dem Spiel die folgenden Informationen:

- ### Haltbarkeit - `getDurability()` {#durability}

  Wie oft das Werkzeug verwendet werden kann, bevor es bricht.

  **Beispiel**

  @[code transcludeWith=:::2](@/reference/latest/src/main/java/com/example/docs/item/tool/GuiditeMaterial.java)

- ### Abbaugeschwindigkeit - `getMiningSpeedMultiplier()` {#mining-speed}

  Wenn das Werkzeug zum Brechen von Blöcken verwendet wird, wie schnell sollte es die Blöcke brechen?

  Zu Referenzzwecken hat das Diamantwerkzeugmaterial eine Abbaugeschwindigkeit von `8.0F`, während das Steinwerkzeugmaterial eine Abbaugeschwindigkeit von `4.0F` hat.

  **Beispiel**

  @[code transcludeWith=:::3](@/reference/latest/src/main/java/com/example/docs/item/tool/GuiditeMaterial.java)

- ### Angriffsgeschwindigkeit - `getAttackDamage()` {#attack-damage}

  Wie viele Schadenspunkte sollte das Werkzeug verursachen, wenn es als Waffe gegen eine andere Entität eingesetzt wird?

  **Beispiel**

  @[code transcludeWith=:::4](@/reference/latest/src/main/java/com/example/docs/item/tool/GuiditeMaterial.java)

- ### Inverse Tag - `getMiningLevel()` {#inverse-tag}

  Das inverse Tag zeigt an, was das Werkzeug _**nicht**_ abbauen kann. Die Verwendung des Tags `BlockTags.INCORRECT_FOR_WOODEN_TOOL` verhindert beispielsweise, dass das Werkzeug bestimmte Blöcke abbaut:

  ```json
  {
    "values": [
      "#minecraft:needs_diamond_tool",
      "#minecraft:needs_iron_tool",
      "#minecraft:needs_stone_tool"
    ]
  }
  ```

  Das bedeutet, dass das Werkzeug keine Blöcke abbauen kann, die ein Diamant-, Eisen- oder Steinwerkzeug benötigen.

  **Beispiel**

  Wir werden das Eisenwerkzeug-Tag verwenden. Dies verhindert, dass Guidite-Werkzeuge Blöcke abbauen, die ein stärkeres Werkzeug als Eisen erfordern.

  @[code transcludeWith=:::5](@/reference/latest/src/main/java/com/example/docs/item/tool/GuiditeMaterial.java)

  Du kannst `TagKey.of(...)` verwenden, um einen benutzerdefinierten Tag-Schlüssel zu erstellen, wenn du einen benutzerdefiniertes Tag verwenden willst.

- ### Verzauberbarkeit - `getEnchantability()` {#enchantability}

  Wie einfach ist es, mit diesem Gegenstand bessere und höherstufige Verzauberungen zu erhalten? Zum Vergleich: Gold hat eine Verzauberungsfähigkeit von 22, während Netherit eine Verzauberungsfähigkeit von 15 hat.

  **Beispiel**

  @[code transcludeWith=:::6](@/reference/latest/src/main/java/com/example/docs/item/tool/GuiditeMaterial.java)

- ### Reparatur-Zutat(en) - `getRepairIngredient()` {#repair-ingredient}

  Welche Items werden zur Reparatur des Werkzeugs verwendet?

  **Beispiel**

  @[code transcludeWith=:::7](@/reference/latest/src/main/java/com/example/docs/item/tool/GuiditeMaterial.java)

Sobald du dein Werkzeugmaterial erstellt und nach deinen Wünschen angepasst hast, kannst du eine Instanz davon erstellen, die in den Konstruktoren der Werkzeugitems verwendet werden kann.

@[code transcludeWith=:::8](@/reference/latest/src/main/java/com/example/docs/item/tool/GuiditeMaterial.java)

## Werkzeugitems erstellen {#creating-tool-items}

Mit der gleichen Hilfsfunktion wie in der Anleitung [Erstelle dein ersten Item](./first-item) kannst du deine Werkzeugitems erstellen:

@[code transcludeWith=:::7](@/reference/latest/src/main/java/com/example/docs/item/ModItems.java)

Vergiss nicht, sie zu einer Itemgruppe hinzuzufügen, wenn du vom Kreativ-Inventar aus auf sie zugreifen willst!

@[code transcludeWith=:::8](@/reference/latest/src/main/java/com/example/docs/item/ModItems.java)

Du musst auch eine Textur, eine Itemübersetzung und ein Itemmodell hinzufügen. Für das Itemmodell solltest du jedoch das Modell `item/handheld` als übergeordnetes Modell verwenden.

In diesem Beispiel verwende ich das folgende Modell und die folgende Textur für den Gegenstand "Guidite Sword":

@[code](@/reference/latest/src/main/resources/assets/fabric-docs-reference/models/item/guidite_sword.json)

<DownloadEntry type="Texture" visualURL="/assets/develop/items/tools_0.png" downloadURL="/assets/develop/items/tools_0_small.png" />

---

Das war's dann auch schon! Im Spiel solltest du deine Werkzeuge auf der Registerkarte Werkzeuge im Kreativ Inventar sehen.

![Fertige Werkzeuge im Inventar](/assets/develop/items/tools_1.png)
