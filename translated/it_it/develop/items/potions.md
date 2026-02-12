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

### Creare la Pozione {#creating-the-potion}

Iniziamo dichiarando un attributo che conterrà la tua istanza `Potion`. Useremo direttamente una classe che implementi `ModInitializer` per conservarla. Nota che abbiamo usato `Registry.registerForHolder` perché, come per gli effetti dei mob, la maggior parte dei metodi vanilla legati alle pozioni le richiedono in holder.

@[code lang=java transclude={18-27}](@/reference/latest/src/main/java/com/example/docs/potion/ExampleModPotions.java)

Passiamo una istanza di `MobEffectInstance`, che accetta 3 parametri:

- `Holder<MobEffect> type` - Un effetto, rappresentato come holder. Qui usiamo il nostro effetto personalizzato. In alternativa puoi accedere agli effetti vanilla attraverso la classe vanilla `MobEffects`.
- `int duration` - Durata dell'effetto espressa in tick di gioco.
- `int amplifier` - Un amplificatore per l'effetto. Per esempio, Sollecitudine II avrebbe un amplificatore di 1.

::: info

Per creare il tuo effetto personalizzato per la pozione, per favore guarda la guida [Effetti](../entities/effects).

:::

### Registrare la Pozione {#registering-the-potion}

Nel nostro initializer, useremo l'evento `FabricBrewingRecipeRegistryBuilder.BUILD` per registrare la nostra pozione usando il metodo `BrewingRecipeRegistry.registerPotionRecipe`.

@[code lang=java transclude={29-40}](@/reference/latest/src/main/java/com/example/docs/potion/ExampleModPotions.java)

`registerPotionRecipe` accetta 3 parametri:

- `Holder<Potion> input` - La pozione iniziale, rappresentata come holder. Solitamente questa può essere una Ampolla d'Acqua o una Pozione Strana.
- `Item item` - L'oggetto che rappresenta l'ingrediente principale della pozione.
- `Holder<Potion> output` - La pozione risultante, rappresentata come holder.

Una volta registrato, puoi distillare una pozione Tater usando una patata.

![Effetto nell'inventario del giocatore](/assets/develop/tater-potion.png)
