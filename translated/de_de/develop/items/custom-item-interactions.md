---
title: Benutzerdefinierte Item-Interaktionen
description: Lerne, wie man ein Item erstellt, welches eingebaute Vanilla Events nutzt.
authors:
  - IMB11
---

Mit einfachen Items ist es nicht getan - irgendwann braucht man ein Item, das mit der Welt interagiert, wenn es benutzt wird.

Es gibt einige Schlüsselklassen, die du verstehen musst, bevor du einen Blick auf die Vanilla-Item-Events wirfst.

## InteractionResult {#interactionresult}

Ein `InteractionResult` teilt dem Spiel den Status des Events mit, ob es bestanden/ignoriert, fehlgeschlagen oder erfolgreich war.

Eine erfolgreiche Interaktion kann auch dazu verwendet werden, den Stack in der Hand zu transformieren.

```java
ItemStack heldStack = user.getStackInHand(hand);
heldStack.decrement(1);
InteractionResult.SUCCESS.heldItemTransformedTo().success(heldStack);
```

## Überschreibbare Events {#overridable-events}

Glücklicherweise verfügt die Klasse Item über viele Methoden, die überschrieben werden können, um zusätzliche Funktionen zu deinen Items hinzuzufügen.

::: info

Ein hervorragendes Beispiel für die Verwendung dieser Events findet sich auf der Seite [SoundEvents abspielen](../sounds/using-sounds), die das Ereignis `useOn` verwendet, um einen Sound abzuspielen, wenn der Spieler mit der rechten Maustaste auf einen Block klickt.

:::

| Methode                | Information                                                                               |
| ---------------------- | ----------------------------------------------------------------------------------------- |
| `hurtEnemy`            | Ausgeführt, wenn ein Spieler eine Entität schlägt.                        |
| `mineBlock`            | Ausgeführt, wenn ein Spieler einen Block abbaut.                          |
| `inventoryTick`        | Jeden Tick ausgeführt, solange das Item im Inventar ist.                  |
| `onCraftedPostProcess` | Ausgeführt, wenn das Item hergestellt wurde.                              |
| `useOn`                | Ausgeführt, wenn der Spieler auf einen Block mit einem Item rechtsklickt. |
| `use`                  | Ausgeführt, wenn ein Spieler ein Item rechtsklickt.                       |

## Das `use()` Event {#use-event}

Angenommen, du möchtest ein Item erstellen, das einen Blitz vor dem Spieler herbeiruft - dann musst du eine benutzerdefinierte Klasse erstellen.

@[code transcludeWith=:::1](@/reference/latest/src/main/java/com/example/docs/item/custom/LightningStick.java)

Das `use`-Event ist wahrscheinlich das nützlichste von allen - du kannst dieses Event benutzen, um unseren Blitz zu erschaffen, du solltest ihn 10 Blöcke vor dem Spieler spawnen, in dessen Richtung er schaut.

@[code transcludeWith=:::2](@/reference/latest/src/main/java/com/example/docs/item/custom/LightningStick.java)

Wie üblich solltest du deine Items registrieren, ein Modell und eine Textur hinzufügen.

Wie du sehen kannst, sollte der Blitz 10 Blöcke vor dir - dem Spieler - erscheinen.

<VideoPlayer src="/assets/develop/items/custom_items_0.webm">Den Blitzstick benutzen</VideoPlayer>
