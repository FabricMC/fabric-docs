---
title: Nahrungsmittel Items
description: Lerne, wie man einen FoodComponent zu einem Item hinzufügt, um es essbar zu machen, und wie man es konfiguriert.
authors:
  - IMB11
---

# Nahrungsmittel Items {#food-items}

Nahrung ist ein zentraler Aspekt des Überlebens in Minecraft. Wenn du also essbare Items erstellst, musst du die Verwendung der Nahrung mit anderen essbaren Gegenständen berücksichtigen.

Es sei denn, du machst einen Mod mit übermächtigen Gegenständen, solltest du folgendes in Betracht ziehen:

- Wie viel Hunger dein Nahrungsmittel verursacht oder beseitigt.
- Welche Trankwirkung(en) gewährt es?
- Ist es im frühen Spiel oder im Endgame zugänglich?

## Den Nahrungsmittel Component hinzufügen {#adding-the-food-component}

Um eine Nahrungsmittel Component zu einem Artikel hinzuzufügen, können wir sie an die Instanz `FabricItemSettings` übergeben:

```java
new FabricItemSettings().food(new FoodComponent.Builder().build())
```

Im Moment wird der Gegenstand dadurch nur essbar, mehr nicht.

Die Klasse `FoodComponent.Builder` hat viele Methoden, mit denen du ändern kannst, was passiert, wenn ein Spieler dein Item isst:

| Methode              | Beschreibung                                                                                                                                                                                                                                       |
| -------------------- | -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| `hunger`             | Legt die Menge an Hungerpunkten fest, die das Item wieder auffüllt.                                                                                                                                                                |
| `saturationModifier` | Legt die Anzahl der Sättigungspunkte fest, die das Item hinzufügen wird.                                                                                                                                                           |
| `meat`               | Deklariert dein Item als Fleisch. Fleischfressende Lebewesen, wie Wölfe, können sie fressen.                                                                                                                       |
| `alwaysEdible`       | Ermöglicht es, dass dein Item unabhängig von dem Hungerlevel gegessen werden kann.                                                                                                                                                 |
| `snack`              | Deklariert dein Item als Snack.                                                                                                                                                                                                    |
| `statusEffect`       | Fügt einen Statuseffekt hinzu, wenn du dein Item isst. Normalerweise wird dieser Methode eine Instanz des Statuseffekts und eine Chance übergeben, wobei die Chance ein dezimaler Prozentsatz ist (`1f = 100%`) |

Wenn du den Builder nach deinen Wünschen geändert hast, kannst du die Methode `build()` aufrufen, um den `FoodComponent` zu erhalten

Anhand des Beispiels, das auf der Seite [Dein erstes Item erstellen](./first-item) erstellt wurde, werde ich die folgenden Optionen für den Builder verwenden:

@[code transcludeWith=:::5](@/reference/latest/src/main/java/com/example/docs/item/ModItems.java)

Dies macht das Item:

- Immer essbar, es kann unabhängig vom Hungerlevel gegessen werden.
- Ein "snack".
- Immer den Effekt Vergiftung II für 6 Sekunden, wenn gegessen.

<VideoPlayer src="/assets/develop/items/food_0.webm" title="Eating the Suspicious Substance" />
