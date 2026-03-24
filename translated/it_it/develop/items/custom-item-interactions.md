---
title: Interazioni tra Oggetti Personalizzate
description: Impara come si crea un oggetto che usa gli eventi vanilla integrati.
authors:
  - IMB11
---

Gli oggetti basilari non possono arrivare lontano - prima o poi ti servirà un oggetto che interagisce con il mondo quando lo si usa.

Ci sono alcune classi chiave che devi comprendere prima di dare un'occhiata agli eventi degli oggetti vanilla.

## InteractionResult {#interactionresult}

Un `InteractionResult` informa il gioco sullo stato dell'evento, sia che sia saltato/ignorato, fallito o riuscito.

Un'interazione riuscita può anche essere usata per trasformare lo stack in mano.

```java
ItemStack heldStack = user.getStackInHand(hand);
heldStack.decrement(1);
InteractionResult.SUCCESS.heldItemTransformedTo().success(heldStack);
```

## Event con Override {#overridable-events}

Fortunatamente, la classe Item ha molti metodi di cui si può fare override per aggiungere funzionalità ai tuoi oggetti.

::: info

Un ottimo esempio di questi eventi in uso si trova nella pagina [Riprodurre Suoni](../sounds/using-sounds), che usa l'evento `useOn` per riprodurre un suono quando il giocatore clicca un blocco con il tasto destro.

:::

| Metodo                 | Informazioni                                                                                            |
| ---------------------- | ------------------------------------------------------------------------------------------------------- |
| `hurtEnemy`            | Eseguito dopo che il giocatore colpisce un'entità.                                      |
| `mineBlock`            | Eseguito dopo che il giocatore rompe un blocco.                                         |
| `inventoryTick`        | Eseguito ad ogni tick mentre l'oggetto è nell'inventario.                               |
| `onCraftedPostProcess` | Eseguito quando l'oggetto viene craftato.                                               |
| `useOn`                | Eseguito quando il giocatore clicca un blocco con il tasto destro, mentre ha l'oggetto. |
| `use`                  | Eseguito quando il giocatore clicca con il tasto destro mentre ha l'oggetto.            |

## L'Evento `use()` {#use-event}

Immaginiamo che tu voglia fare in modo che l'oggetto crei un lampo davanti al giocatore - dovrai creare una classe personalizzata.

@[code transcludeWith=:::1](@/reference/latest/src/main/java/com/example/docs/item/custom/LightningStick.java)

L'evento `use` è probabilmente il più utile tra tutti - puoi usare questo evento per generare il lampo, dovresti generarlo 10 blocchi davanti al giocatore, nella direzione verso cui è diretto.

@[code transcludeWith=:::2](@/reference/latest/src/main/java/com/example/docs/item/custom/LightningStick.java)

Come sempre, dovresti registrare il tuo oggetto, aggiungere un modello e una texture.

Come puoi notare, il lampo dovrebbe essere generato 10 blocchi davanti a te, giocatore.

<VideoPlayer src="/assets/develop/items/custom_items_0.webm">Usare il Bastone del Lampo</VideoPlayer>
