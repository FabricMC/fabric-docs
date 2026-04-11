---
title: Potions
description: Learn how to add a custom potion for various mob effects.
authors:
  - cassiancc
  - dicedpixels
  - Drakonkinst
  - JaaiDead
  - PandoricaVi
---

Potions are consumables that grants an entity an effect. A player can brew potions using a Brewing Stand or obtain them
as items through various other game mechanics.

## Custom Potions {#custom-potions}

Just like items and blocks, potions need to be registered.

### Creating the Potion {#creating-the-potion}

Let's start by declaring a field to hold your `Potion` instance. We will be directly using a `ModInitializer`-implementing class to
hold this. Note the use of `Registry.registerForHolder`, as, like mob effects, most vanilla methods that use potions prefer them as holders.

<<< @/reference/latest/src/main/java/com/example/docs/potion/ExampleModPotions.java#register-potion

We pass an instance of `MobEffectInstance`, which takes 3 parameters:

- `Holder<MobEffect> effect` - An effect, represented as a holder. We use our custom effect here. Alternatively you can access vanilla effects
  through vanilla's `MobEffects` class.
- `int duration` - Duration of the effect in game ticks.
- `int amplifier` - An amplifier for the effect. For example, Haste II would have an amplifier of 1.

::: info

To create your own potion effect, please see the [Effects](../entities/effects) guide.

:::

### Registering the Potion {#registering-the-potion}

In our initializer, we will use the `FabricBrewingRecipeRegistryBuilder.BUILD` event to register our potion using the `BrewingRecipeRegistry.registerPotionRecipe` method.

<<< @/reference/latest/src/main/java/com/example/docs/potion/ExampleModPotions.java#register-recipes

`addMix` takes 3 parameters:

- `Holder<Potion> from` - The starting potion, represented by a holder. Usually this can be a Water Bottle or an Awkward Potion.
- `Item item` - The item which is the main ingredient of the potion.
- `Holder<Potion> to` - The resultant potion, represented by a holder.

Once registered, you can brew a Tater potion using a potato.

![Effect in player inventory](/assets/develop/tater-potion.png)
