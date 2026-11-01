---
title: Enchantment Generation
description: A guide to generating enchantments via datagen.
authors:
  - CelDaemon
  - Jimmy474
---

<!---->

::: info PREREQUISITES

Make sure you've completed the [datagen setup](./setup) process first, and you understand [Dynamic Registry Population](./dynamic-registry.md) using datagen.

:::

## Setup {#setup}

Before implementing the generator, create the `enchantment` package in the main source set and add the `ModEnchantments` class to it. Then add the `key` method to this new class.

<<< @/reference/latest/src/main/java/com/example/docs/enchantment/ModEnchantments.java#key_helper

Use this method to create a `ResourceKey` for your enchantment.

<<< @/reference/latest/src/main/java/com/example/docs/enchantment/ModEnchantments.java#register_enchantment

Adding new entries to any existing registry has the same process as [Dynamic Registry Population](./dynamic-registry.md),
so create a similar class and configure it to use the `Registries.ENCHANTMENT` instead of the custom key.

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModEnchantmentGenerator.java#provider

::: info

The datagen process is separate from minecraft runtime, and it runs on its own,
therefore using built in registries in `Entries.addAll()` doesn't create `.json` files for existing entries.
It only creates `.json` files for entries that the `bootstrap` method creates and loads into memory.

:::

For the `bootstrap` method the `T` of `BootstrapContext<T>` should be `Enchantment`.

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModEnchantmentGenerator.java#bootstrap

Then, add the `register` helper method to the newly created class.

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModEnchantmentGenerator.java#register_helper

## Creating the Enchantment {#creating-the-enchantment}

To create the definition for our custom enchantment, we will use the `register` method in our generator class.

Register your enchantment in the generator's `bootstrap` method, using the enchantment registered in `ModEnchantments`.

In this example, we will be using the enchantment effect created in [Custom Enchantment Effects](../items/custom-enchantment-effects), but you can also make use of the [vanilla enchantment effects](https://minecraft.wiki/w/Enchantment_definition#Effect_components).

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModEnchantmentGenerator.java#register_enchantment

Now simply run data generation, and your new enchantment will be available in-game!

## Effect Conditions {#effect-conditions}

Most enchantment effect types are conditional effects. When adding these effects, it is possible to pass conditions to the `withEffect` call.

::: info

For an overview of the available condition types and their usage, see [the `Enchantments` class](https://mcsrc.dev/#1/1.21.11_unobfuscated/net/minecraft/world/item/enchantment/Enchantments#L126).

:::

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModEnchantmentGenerator.java#effect_conditions

## Multiple Effects {#multiple-effects}

`withEffect` can be chained to add multiple enchantment effects to a single enchantment. However, this method requires you to specify the effect conditions for every effect.

To instead share the defined conditions and targets across multiple effects, `AllOf` can be used to merge them into a single effect.

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModEnchantmentGenerator.java#multiple_effects

Note that the method to use depends on the type of effect being added. For example, `EnchantmentValueEffect` requires `AnyOf.valueEffects` instead. Differing effect types still require additional `withEffect` calls.

## Enchanting Table {#enchanting-table}

While we have specified the enchantment weight (or chance) in our enchantment definition, it will not appear in the enchanting table by default. To allow our enchantment to be traded by villagers and appear in the enchanting table, we need to add it to the `non_treasure` tag.

To do this, we can create a tag provider. Create a class that extends `FabricTagProvider<Enchantment>` in the `datagen` package. Then implement the constructor with `Registries.ENCHANTMENT` as the `registryKey` parameter to `super`, and create the `addTags` method.

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModEnchantmentTagProvider.java#provider

We can now add our enchantment to `EnchantmentTags.NON_TREASURE` by calling the builder from within the `addTags` method.

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModEnchantmentTagProvider.java#non_treasure_tag

## Curses {#curses}

Curses are also implemented using tags. We can use the tag provider from [the Enchanting Table section](#enchanting-table).

In the `addTags` method, simply add your enchantment to the `CURSE` tag to mark it as a curse.

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModEnchantmentTagProvider.java#curse_tag
