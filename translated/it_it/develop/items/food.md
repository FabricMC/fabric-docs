---
title: Alimenti
description: Impara come aggiungere una FoodComponent ad un oggetto per renderlo edibile, e come configurarlo.
authors:
  - IMB11
---

# Alimenti {#food-items}

Gli alimenti sono un aspetto cruciale di sopravvivenza in Minecraft, per cui quando si creano oggetti edibili devi considerare l'utilizzo del cibo con altri oggetti edibili.

A meno che tu non voglia creare una mod con oggetti troppo potenti, dovresti tenere in considerazione:

- Quanta fame aggiunge o toglie l'oggetto edibile?
- Quali effetti di pozione fornisce?
- È accessibile presto o tardi nel gioco?

## Aggiungere la Componente Alimento {#adding-the-food-component}

Per aggiungere la componente alimentare ad un oggetto, possiamo passarla all'istanza `Item.Setttings`:

```java
new Item.Settings().food(new FoodComponent.Builder().build())
```

Per ora questo rende l'oggetto edibile, e nulla di più.

La classe `FoodComponent.Builder` ha qualche metodo che ti permette di modificare cosa succede quando un giocatore mangia il tuo oggetto:

| Metodo               | Descrizione                                                                                        |
| -------------------- | -------------------------------------------------------------------------------------------------- |
| `nutrition`          | Imposta la quantità di punti fame che l'oggetto sazierà.                           |
| `saturationModifier` | Imposta la quantita di punti di saturazione che l'oggetto aggiungerà.              |
| `alwaysEdible`       | Permette al tuo oggetto di essere consumato indipendentemente dal livello di fame. |

Quando avrai modificato il costruttore come preferisci, puoi chiamare il metodo `build()` per ottenere la `FoodComponent`.

Se vuoi aggiungere effetti di stato al giocatore quando mangiano il tuo cibo, devi usare la classe `ConsumableComponent` assieme a `FoodComponent`, come noti nell'esempio seguente:

@[code transcludeWith=:::5](@/reference/latest/src/main/java/com/example/docs/item/ModItems.java)

Come nell'esempio della pagina [Creare il Tuo Primo Oggetto](./first-item), useremo la componente sopra:

@[code transcludeWith=:::poisonous_apple](@/reference/latest/src/main/java/com/example/docs/item/ModItems.java)

Questo rende l'oggetto:

- Sempre edibile, può essere mangiato indipendentemente dal livello di fame.
- Fornisce sempre Avvelenamento II per 6 secondi appena consumato.

<VideoPlayer src="/assets/develop/items/food_0.webm" title="Eating the Poisonous Apple" />
