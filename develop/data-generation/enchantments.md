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

@[code transcludeWith=:::key-helper](@/reference/latest/src/main/java/com/example/docs/enchantment/ModEnchantments.java)

Use this method to create a `ResourceKey` for your enchantment.

@[code transcludeWith=:::register-enchantment](@/reference/latest/src/main/java/com/example/docs/enchantment/ModEnchantments.java)

Now we're ready to add the generator. In the datagen package, create a class that extends `FabricDynamicRegistryProvider`.

In this newly created class, add a constructor that matches `super` and implement the `configure` and `getName` methods.

@[code transcludeWith=:::provider](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModEnchantmentGenerator.java)

Then, add the `register` helper method to the newly created class.

@[code transcludeWith=:::register-helper](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModEnchantmentGenerator.java)

Now add the `bootstrap` method. Here, we will be registering the enchantments we want to add to the game.

@[code transcludeWith=:::bootstrap](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModEnchantmentGenerator.java)

In your `DataGeneratorEntrypoint`, override the `buildRegistry` method and register our bootstrap method.

@[code transcludeWith=:::datagen-enchantments:bootstrap](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModDataGenerator.java)

Finally, ensure your new generator is registered within the `onInitializeDataGenerator` method.

@[code transcludeWith=:::datagen-enchantments:register](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModDataGenerator.java)

## Creating the Enchantment {#creating-the-enchantment}

To create the definition for our custom enchantment, we will use the `register` method in our generator class.

Register your enchantment in the generator's `bootstrap` method, using the enchantment registered in `ModEnchantments`.

In this example, we will be using the enchantment effect created in [Custom Enchantment Effects](../items/custom-enchantment-effects), but you can also make use of the [vanilla enchantment effects](https://minecraft.wiki/w/Enchantment_definition#Effect_components).

@[code transcludeWith=:::register-enchantment](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModEnchantmentGenerator.java)

Now simply run data generation, and your new enchantment will be available in-game!

## Effect Conditions {#effect-conditions}

Most enchantment effect types are conditional effects. When adding these effects, it is possible to pass conditions to the `withEffect` call.

::: info

For an overview of the available condition types and their usage, see [the `Enchantments` class](https://mcsrc.dev/#1/1.21.11_unobfuscated/net/minecraft/world/item/enchantment/Enchantments#L126).

:::

@[code transcludeWith=:::effect-conditions](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModEnchantmentGenerator.java)

## Multiple Effects {#multiple-effects}

`withEffect` can be chained to add multiple enchantment effects to a single enchantment. However, this method requires you to specify the effect conditions for every effect.

To instead share the defined conditions and targets across multiple effects, `AllOf` can be used to merge them into a single effect.

@[code transcludeWith=:::multiple-effects](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModEnchantmentGenerator.java)

Note that the method to use depends on the type of effect being added. For example, `EnchantmentValueEffect` requires `AnyOf.valueEffects` instead. Differing effect types still require additional `withEffect` calls.
