---
title: Pozioni
description: Impara come aggiungere pozioni personalizzate per vari effetti di stato.
authors:
  - dicedpixels
  - PandoricaVi
  - Drakonkinst
---

# Pozioni

Le pozioni sono oggetti consumabili che conferiscono un effetto ad un'entità. Un giocatore può preparare delle pozioni usando l'Alambicco oppure ottenerle come oggetti attraverso varie meccaniche di gioco.

## Pozioni Personalizzate

Aggiungere una pozione è simile al metodo per aggiungere un oggetto. Dovrai creare un'istanza della tua pozione e registrarla chiamando `BrewingRecipeRegistry.registerPotionRecipe`.

:::info
Quando Fabric API è presente, `BrewingRecipeRegistry.registerPotionRecipe` è reso disponibile attraverso un Access Widener.
:::

### Creare la Pozione

Inziamo dichiarando un attributo per conservare la tua istanza `Pozione`. Utilizzeremo direttamente la classe dell'initializer per conservarla.

@[code lang=java transclude={18-27}](@/reference/latest/src/main/java/com/example/docs/potion/FabricDocsReferencePotions.java)

Passiamo una istanza di `StatusEffectInstance`, che prende 3 parametri:

- `StatusEffect type` - Un effetto. Qui usiamo il nostro effetto personalizzato. In alternativa puoi accedere agli effetti vanilla attraverso `net.minecraft.entity.effect.StatusEffects`.
- `int duration` - La durata dell'effetto espressa in game ticks.
- `int amplifier` - Un amplificatore per l'effetto. Ad sempio, Sollecitudine II avrebbe un amplificatore di 1.

:::info
Per creare il tuo effetto personalizzato, per favore guarda la guida [Effetti](../entities/effects.md).
:::

### Registrare la Pozione

Nel nostro initializer, chiamiamo `BrewingRecipeRegistry.registerPotionRecipe`.

@[code lang=java transclude={30-30}](@/reference/latest/src/main/java/com/example/docs/potion/FabricDocsReferencePotions.java)

`registerPotionRecipe` prende 3 parametri:

- `Potion input` - La pozione iniziale. Solitamente questa può essere una Ampolla d'Acqua o una Pozione Strana.
- `Item item` - L'oggetto che rappresenta l'ingrediente principale della pozione.
- `Potion output` - La pozione risultante.

Se utilizzi Fabric API, il mixin invoker non è necessario, ed una chiamata diretta a `BrewingRecipeRegistry.registerPotionRecipe` può essere fatta.

L'esempio per intero:

@[code lang=java transcludeWith=:::1](@/reference/latest/src/main/java/com/example/docs/potion/FabricDocsReferencePotions.java)

Una volta registrato, puoi distillare una pozione di Tater usando una patata.

![Effetto nell'inventario del giocatore](/assets/develop/tater-potion.png)

::: info
**Registering Potions Using an `Ingredient`**

Con l'aiuto di Fabric API, è possibile registrare una pozione usando un `Ingrediente` anziché un `Item` (oggetto) usando `
net.fabricmc.fabric.api.registry.FabricBrewingRecipeRegistry`.
:::

### Registrare la Pozione senza Fabric API

Senza Fabric API, `BrewingRecipeRegistry.registerPotionRecipe` sarà privato. Per accedere a questo metodo usa il seguente mixin invoker o un Access Widener.

@[code lang=java transcludeWith=:::1](@/reference/latest/src/main/java/com/example/docs/mixin/potion/BrewingRecipeRegistryInvoker.java)
