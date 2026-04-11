---
title: Enchantment Generation
description: A guide to generating enchantments via datagen.
authors:
  - CelDaemon
---

<!---->

::: info PREREQUISITES

Make sure you've completed the [datagen setup](./setup) process first.

:::

## Setup {#setup}

Before implementing the generator, create the `enchantment` package in the main source set and add the `ModEnchantments` class to it. Then add the `key` method to this new class.

<<< @/reference/latest/src/main/java/com/example/docs/enchantment/ModEnchantments.java#key-helper

Use this method to create a `ResourceKey` for your enchantment.

<<< @/reference/latest/src/main/java/com/example/docs/enchantment/ModEnchantments.java#register-enchantment

Now, we're ready to add the generator. In the datagen package, create a class that extends `FabricDynamicRegistryProvider`. In this newly created class, add a constructor that matches `super`, and implement the `configure` and `getName` methods.

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModEnchantmentGenerator.java#provider

Then, add the `register` helper method to the newly created class.

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModEnchantmentGenerator.java#register-helper

Now add the `bootstrap` method. Here, we will be registering the enchantments we want to add to the game.

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModEnchantmentGenerator.java#bootstrap

In your `DataGeneratorEntrypoint`, override the `buildRegistry` method and register our bootstrap method.

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModDataGenerator.java#datagen-enchantments--bootstrap

Finally, ensure your new generator is registered within the `onInitializeDataGenerator` method.

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModDataGenerator.java#datagen-enchantments--register

## Creating the Enchantment {#creating-the-enchantment}

To create the definition for our custom enchantment, we will use the `register` method in our generator class.

Register your enchantment in the generator's `bootstrap` method, using the enchantment registered in `ModEnchantments`.

In this example, we will be using the enchantment effect created in [Custom Enchantment Effects](../items/custom-enchantment-effects), but you can also make use of the [vanilla enchantment effects](https://minecraft.wiki/w/Enchantment_definition#Effect_components).

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModEnchantmentGenerator.java#register-enchantment

Now simply run data generation, and your new enchantment will be available in-game!

## Effect Conditions {#effect-conditions}

Most enchantment effect types are conditional effects. When adding these effects, it is possible to pass conditions to the `withEffect` call.

::: info

For an overview of the available condition types and their usage, see [the `Enchantments` class](https://mcsrc.dev/#1/1.21.11_unobfuscated/net/minecraft/world/item/enchantment/Enchantments#L126).

:::

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModEnchantmentGenerator.java#effect-conditions

## Multiple Effects {#multiple-effects}

`withEffect` can be chained to add multiple enchantment effects to a single enchantment. However, this method requires you to specify the effect conditions for every effect.

To instead share the defined conditions and targets across multiple effects, `AllOf` can be used to merge them into a single effect.

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModEnchantmentGenerator.java#multiple-effects

Note that the method to use depends on the type of effect being added. For example, `EnchantmentValueEffect` requires `AnyOf.valueEffects` instead. Differing effect types still require additional `withEffect` calls.

## Enchanting Table {#enchanting-table}

While we have specified the enchantment weight (or chance) in our enchantment definition, it will not appear in the enchanting table by default. To allow our enchantment to be traded by villagers and appear in the enchanting table, we need to add it to the `non_treasure` tag.

To do this, we can create a tag provider. Create a class that extends `FabricTagProvider<Enchantment>` in the `datagen` package. Then implement the constructor with `Registries.ENCHANTMENT` as the `registryKey` parameter to `super`, and create the `addTags` method.

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModEnchantmentTagProvider.java#provider

We can now add our enchantment to `EnchantmentTags.NON_TREASURE` by calling the builder from within the `addTags` method.

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModEnchantmentTagProvider.java#non-treasure-tag

## Curses {#curses}

Curses are also implemented using tags. We can use the tag provider from [the Enchanting Table section](#enchanting-table).

In the `addTags` method, simply add your enchantment to the `CURSE` tag to mark it as a curse.

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModEnchantmentTagProvider.java#curse-tag
