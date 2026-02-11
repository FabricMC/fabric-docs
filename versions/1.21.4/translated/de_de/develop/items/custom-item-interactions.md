---
title: Benutzerdefinierte Item-Interaktionen
description: Lerne, wie man ein Item erstellt, welches eingebaute Vanilla Events nutzt.
authors:
  - IMB11
---

Mit einfachen Items ist es nicht getan - irgendwann braucht man ein Item, das mit der Welt interagiert, wenn es benutzt wird.

Es gibt einige Schlüsselklassen, die du verstehen musst, bevor du einen Blick auf die Vanilla-Item-Events wirfst.

## InteractionResultHolder {#typedactionresult}

Bei Items ist das häufigste `InteractionResultHolder` für `ItemStacks` - diese Klasse sagt dem Spiel, was der Item-Stack ersetzen soll (oder nicht), nachdem das Event eingetreten ist.

Wenn in dem Event nichts vorgefallen ist, solltest du die Methode `InteractionResultHolder#pass(stack)` verwenden, wobei `stack` der aktuelle Item-Stack ist.

Du kannst den aktuellen Item-Stack ermitteln, indem du den Stack in der Hand des Spielers abrufst. Normalerweise übergeben Events, die ein `InteractionResultHolder` erfordern, die Hand an die Eventmethode.

```java
InteractionResultHolder.pass(user.getStackInHand(hand))
```

Wenn du den aktuellen Stack übergibst, wird sich nichts ändern, unabhängig davon, ob du das Event als fehlgeschlagen, bestanden/ignoriert oder erfolgreich deklarierst.

Wenn du den aktuellen Stack löschen willst, solltest du einen leeren Stack übergeben. Dasselbe gilt für das Dekrementieren: Du holst den aktuellen Stapel und dekrementierst ihn um den gewünschten Betrag:

```java
ItemStack heldStack = user.getStackInHand(hand);
heldStack.decrement(1);
InteractionResultHolder.success(heldStack);
```

## InteractionResult {#actionresult}

In ähnlicher Weise teilt ein `InteractionResult` dem Spiel den Status des Events mit, ob es bestanden/ignoriert, fehlgeschlagen oder erfolgreich war.

## Überschreibbare Events {#overridable-events}

Glücklicherweise verfügt die Klasse Item über viele Methoden, die überschrieben werden können, um zusätzliche Funktionen zu deinen Items hinzuzufügen.

:::info
Ein hervorragendes Beispiel für die Verwendung dieser Events findet sich auf der Seite [SoundEvents abspielen](../sounds/using-sounds), die das Ereignis `useOn` verwendet, um einen Sound abzuspielen, wenn der Spieler mit der rechten Maustaste auf einen Block klickt.
:::

| Methode         | Informationen                                                                             |
| --------------- | ----------------------------------------------------------------------------------------- |
| `hurtEnemy`       | Ausgeführt, wenn ein Spieler eine Entität schlägt.                        |
| `mineBlock`      | Ausgeführt, wenn ein Spieler einen Block abbaut.                          |
| `inventoryTick` | Jeden Tick ausgeführt, solange das Item im Inventar ist.                  |
| `onCraftedPostProcess`       | Ausgeführt, wenn das Item hergestellt wurde.                              |
| `useOn`    | Ausgeführt, wenn der Spieler auf einen Block mit einem Item rechtsklickt. |
| `use`           | Ausgeführt, wenn ein Spieler ein Item rechtsklickt.                       |

## Das `use()` Event {#use-event}

Angenommen, du möchtest ein Item erstellen, das einen Blitz vor dem Spieler herbeiruft - dann musst du eine benutzerdefinierte Klasse erstellen.

@[code transcludeWith=:::1](@/reference/1.21.4/src/main/java/com/example/docs/item/custom/LightningStick.java)

Das `use`-Event ist wahrscheinlich das nützlichste von allen - du kannst dieses Event benutzen, um unseren Blitz zu erschaffen, du solltest ihn 10 Blöcke vor dem Spieler spawnen, in dessen Richtung er schaut.

@[code transcludeWith=:::1](@/reference/1.21.4/src/main/java/com/example/docs/item/custom/LightningStick.java)

Wie üblich solltest du deine Items registrieren, ein Modell und eine Textur hinzufügen.

Wie du sehen kannst, sollte der Blitz 10 Blöcke vor dir - dem Spieler - erscheinen.

<VideoPlayer src="/assets/develop/items/custom_items_0.webm">Den Blitzstick benutzen</VideoPlayer>
