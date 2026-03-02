---
title: Potions
description: Learn how to add a custom potion for various mob effects.
authors:
  - dicedpixels
  - PandoricaVi
  - Drakonkinst

search: false
---

Potions are consumables that grants an entity an effect. A player can brew potions using a Brewing Stand or obtain them
as items through various other game mechanics.

## Custom Potions {#custom-potions}

Adding a potion follows a similar path as adding an item. You will create an instance of your potion and register it by
calling `PotionBrewing.addMix`.

::: info
When Fabric API is present, `PotionBrewing.addMix` is made accessible through an Access Widener.
:::

### Creating the Potion {#creating-the-potion}

Let's start by declaring a field to store your `Potion` instance. We will be directly using the initializer class to
hold this.

@[code lang=java transclude={18-27}](@/reference/1.20.4/src/main/java/com/example/docs/potion/ExampleModPotions.java)

We pass an instance of `MobEffectInstance`, which takes 3 parameters:

- `MobEffect type` - An effect. We use our custom effect here. Alternatively you can access vanilla effects
  through `net.minecraft.entity.effect.MobEffects`.
- `int duration` - Duration of the effect in game ticks.
- `int amplifier` - An amplifier for the effect. For example, Haste II would have an amplifier of 1.

::: info
To create your own effect, please see the [Effects](../entities/effects) guide.
:::

### Registering the Potion {#registering-the-potion}

In our initializer, we call `PotionBrewing.addMix`.

@[code lang=java transclude={30-30}](@/reference/1.20.4/src/main/java/com/example/docs/potion/ExampleModPotions.java)

`addMix` takes 3 parameters:

- `Potion input` - The starting potion. Usually this can be a Water Bottle or an Awkward Potion.
- `Item item` - The item which is the main ingredient of the potion.
- `Potion output` - The resultant potion.

If you use Fabric API, the mixin invoker is not necessary and a direct call
to `PotionBrewing.addMix` can be done.

The full example:

@[code lang=java transcludeWith=:::1](@/reference/1.20.4/src/main/java/com/example/docs/potion/ExampleModPotions.java)

Once registered, you can brew a Tater potion using a potato.

![Effect in player inventory](/assets/develop/tater-potion.png)

::: info Registering Potions Using an `Ingredient`

With the help of Fabric API, it's possible to register a potion using an `Ingredient` instead of an `Item` using `
net.fabricmc.fabric.api.registry.FabricPotionBrewing`.
:::

### Registering the Potion Without Fabric API {#registering-the-potion-without-fabric-api}

Without Fabric API, `PotionBrewing.addMix` will be private. In order to access this method, use
the following mixin invoker or use an Access Widener.

@[code lang=java transcludeWith=:::1](@/reference/1.20.4/src/main/java/com/example/docs/mixin/potion/PotionBrewingInvoker.java)
