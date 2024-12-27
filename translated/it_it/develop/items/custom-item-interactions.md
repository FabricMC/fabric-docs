---
title: Interazioni tra Oggetti Personalizzate
description: Impara come si crea un oggetto che usa gli eventi vanilla integrati.
authors:
  - IMB11
---

# Interazioni tra Oggetti Personalizzate {#custom-item-interactions}

Gli oggetti basilari non possono arrivare lontano - prima o poi ti servirà un oggetto che interagisce con il mondo quando lo si usa.

Ci sono alcune classi chiave che devi comprendere prima di dare un'occhiata agli eventi degli oggetti vanilla.

## TypedActionResult {#typedactionresult}

Per gli oggetti, il `TypedActionResult` che incontrerai più comunemente è per gli `ItemStacks` - questa classe informa il gioco riguardo a cosa sostituire (o non) nello stack di oggetti dopo che l'evento è avvenuto.

Se non è successo nulla nell'evento, dovresti usare il metodo `TypedActionResult#pass(stack)`, dove `stack` è lo stack di oggetti corrente.

Puoi ottenere lo stack di oggetti corrente ottenendo il contenuto della mano del giocatore. Di solito gli eventi che richiedono un `TypedActionResult` passano la mano al metodo dell'evento.

```java
TypedActionResult.pass(user.getStackInHand(hand))
```

Se passi lo stack corrente - nulla cambierà, anche dichiarando l'evento come fallito, saltato/ignorato o riuscito.

Se volessi eliminare lo stack corrente, dovresti passarne uno vuoto. Lo stesso si può dire per la riduzione, puoi analizzare lo stack corrente e diminuirlo della quantità che vuoi:

```java
ItemStack heldStack = user.getStackInHand(hand);
heldStack.decrement(1);
TypedActionResult.success(heldStack);
```

## ActionResult {#actionresult}

Similmente, un `ActionResult` informa il gioco sullo stato dell'evento, sia che sia saltato/ignorato, fallito o riuscito.

## Event con Override {#overridable-events}

Fortunatamente, la classe Item ha molti metodi di cui si può fare override per aggiungere funzionalità ai tuoi oggetti.

:::info
Un ottimo esempio di questi eventi in uso si trova nella pagina [Riprodurre Suoni](../sounds/using-sounds), che usa l'evento `useOnBlock` per riprodurre un suono quando il giocatore clicca un blocco con il tasto destro.
:::

| Metodo          | Informazioni                                                                                            |
| --------------- | ------------------------------------------------------------------------------------------------------- |
| `postHit`       | Eseguito dopo che il giocatore colpisce un'entità.                                      |
| `postMine`      | Eseguito dopo che il giocatore rompe un blocco.                                         |
| `inventoryTick` | Eseguito ad ogni tick mentre l'oggetto è nell'inventario.                               |
| `onCraft`       | Eseguito quando l'oggetto viene craftato.                                               |
| `useOnBlock`    | Eseguito quando il giocatore clicca un blocco con il tasto destro, mentre ha l'oggetto. |
| `use`           | Eseguito quando il giocatore clicca con il tasto destro mentre ha l'oggetto.            |

## L'Evento `use()` {#use-event}

Immaginiamo che tu voglia fare in modo che l'oggetto crei un lampo davanti al giocatore - dovrai creare una classe personalizzata.

@[code transcludeWith=:::1](@/reference/latest/src/main/java/com/example/docs/item/custom/LightningStick.java)

L'evento `use` è probabilmente il più utile tra tutti - puoi usare questo evento per generare il lampo, dovresti generarlo 10 blocchi davanti al giocatore, nella direzione verso cui è diretto.

@[code transcludeWith=:::2](@/reference/latest/src/main/java/com/example/docs/item/custom/LightningStick.java)

Come sempre, dovresti registrare il tuo oggetto, aggiungere un modello e una texture.

Come puoi notare, il lampo dovrebbe essere generato 10 blocchi davanti a te, giocatore.

<VideoPlayer src="/assets/develop/items/custom_items_0.webm" title="Using the Lightning Stick" />
