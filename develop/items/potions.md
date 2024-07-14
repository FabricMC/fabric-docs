---
title: Potions
description: Learn how to add a custom potion for various status effects.
authors:
  - dicedpixels
  - PandoricaVi
  - Drakonkinst
  - JaaiDead

---

# Potions {#potions}

Potions are consumables that grants an entity an effect. A player can brew potions using a Brewing Stand or obtain them
as items through various other game mechanics.

## Custom Potions {#custom-potions}

Just like items and blocks, potions need to be registered.

### Creating the Potion {#creating-the-potion}

Let's start by declaring a field to store your `Potion` instance. We will be directly using the initializer class to
hold this.

@[code lang=java transclude={19-27}](@/reference/latest/src/main/java/com/example/docs/potion/FabricDocsReferencePotions.java)

We pass an instance of `StatusEffectInstance`, which takes 3 parameters:

- `RegistryEntry<StatusEffect> type` - An effect. We use our custom effect here. Alternatively you can access vanilla effects
  through vanilla's `StatusEffects` class.
- `int duration` - Duration of the effect in game ticks.
- `int amplifier` - An amplifier for the effect. For example, Haste II would have an amplifier of 1.

::: info
To create your own potion effect, please see the [Effects](../entities/effects) guide.
:::

### Registering the Potion {#registering-the-potion}

In our initializer, we will use the `FabricBrewingRecipeRegistryBuilder.BUILD` event to register our potion using the `BrewingRecipeRegistry.registerPotionRecipe` method.

@[code lang=java transclude={29-42}](@/reference/latest/src/main/java/com/example/docs/potion/FabricDocsReferencePotions.java)

`registerPotionRecipe` takes 3 parameters:

- `RegistryEntry<Potion> input` - The starting potion's registry entry. Usually this can be a Water Bottle or an Awkward Potion.
- `Item item` - The item which is the main ingredient of the potion.
- `RegistryEntry<Potion> output` - The resultant potion's registry entry.

Once registered, you can brew a Tater potion using a potato.

![Effect in player inventory](/assets/develop/tater-potion.png)
