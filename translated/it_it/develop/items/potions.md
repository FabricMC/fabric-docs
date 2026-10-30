---
title: Pozioni
description: Impara come aggiungere pozioni personalizzate per vari effetti dei mob.
authors:
  - cassiancc
  - dicedpixels
  - Drakonkinst
  - JaaiDead
  - PandoricaVi
---

Le pozioni sono oggetti consumabili che conferiscono un effetto a un'entità. Un giocatore può preparare delle pozioni usando l'Alambicco oppure ottenerle come oggetti attraverso varie meccaniche di gioco.

## Pozioni Personalizzate {#custom-potions}

Proprio come gli oggetti e i blocchi, le pozioni devono essere registrate.

## Preparare la classe degli ID delle pozioni {#preparing-your-potion-id-class}

Se hai già completato la pagina [Creare il Tuo Primo Oggetto](../items/first-item), questo processo ti sembrerà molto familiare - dovrai creare delle classi dove memorizzare gli `Identifier` delle tue pozioni, come `ResourceKey`.

Questi riferimenti alle pozioni saranno usati per la [data-generazione dei tag delle pozioni](../data-generation/tags).

Puoi mettere questo metodo in una classe chiamata `ModPotionIds` (o qualsiasi altro nome).

::: tip

Anche Mojang fa lo stesso per le loro pozioni! Prendi ispirazione dalla classe `PotionIds`.

:::

<<< @/reference/latest/src/main/java/com/example/docs/potion/ModPotionIds.java#mod_potion_ids_class

### Creare la Pozione {#creating-the-potion}

Iniziamo dichiarando un attributo che conterrà la tua istanza `Potion`. Useremo direttamente una classe che implementi `ModInitializer` per conservarla. Nota che abbiamo usato `Registry.registerForHolder` perché, come per gli effetti dei mob, la maggior parte dei metodi vanilla legati alle pozioni le richiedono in holder.

<<< @/reference/latest/src/main/java/com/example/docs/potion/ExampleModPotions.java#register_potion

Passiamo una istanza di `MobEffectInstance`, che accetta 3 parametri:

- `Holder<MobEffect> effect` - Un effetto, rappresentato come holder. Qui usiamo il nostro effetto personalizzato. In alternativa puoi accedere agli effetti vanilla attraverso la classe vanilla `MobEffects`.
- `int duration` - Durata dell'effetto espressa in tick di gioco.
- `int amplifier` - Un amplificatore per l'effetto. Per esempio, Sollecitudine II avrebbe un amplificatore di 1.

::: info

Per creare il tuo effetto personalizzato per la pozione, per favore guarda la guida [Effetti](../entities/effects).

:::

### Registrare la Pozione {#registering-the-potion}

Nel nostro initializer, useremo l'evento `FabricBrewingRecipeRegistryBuilder.BUILD` per registrare la nostra pozione usando il metodo `BrewingRecipeRegistry.registerPotionRecipe`.

<<< @/reference/latest/src/main/java/com/example/docs/potion/ExampleModPotions.java#register_recipes

`addMix` accetta 3 parametri:

- `Holder<Potion> from` - La pozione iniziale, rappresentata come holder. Solitamente questa può essere una Ampolla d'Acqua o una Pozione Strana.
- `Item item` - L'oggetto che rappresenta l'ingrediente principale della pozione.
- `Holder<Potion> to` - La pozione risultante, rappresentata come holder.

Una volta registrato, puoi distillare una pozione Tater usando una patata.

![Effetto nell'inventario del giocatore](/assets/develop/tater-potion.png)
