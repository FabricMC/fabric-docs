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

Before implementing the generator, create the `ModEnchantments` class and create the `register` method.

@[code transcludeWith=:::register-method](@/reference/latest/src/main/java/com/example/docs/enchantment/ModEnchantments.java)

We will now use this register method to register the new enchantment.

@[code transcludeWith=:::register-enchantment](@/reference/latest/src/main/java/com/example/docs/enchantment/ModEnchantments.java)

Now we're ready to add the generator. Create the `ExampleModEnchantmentGenerator` class, it should extend `FabricDynamicRegistryProvider` and have its constructor and abstract methods implemented.

@[code transcludeWith=:::provider](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModEnchantmentGenerator.java)

Then, add the registration helper to the created class.

@[code transcludeWith=:::register-helper](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModEnchantmentGenerator.java)

Finally, ensure your new generator is registered in your `DataGeneratorEntrypoint`.

@[code transcludeWith=:::datagen-enchantments:register](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModDataGenerator.java)

## Creating the Enchantment {#creating-the-enchantment}

To create the definition for our custom enchantment, we will use the `register` method in our generator class.

Register your enchantment in the generator's `configure` method, using the enchantment registered in `ModEnchantments`.

In this example, we will be using the enchantment effect created in [Custom Enchantment Effects](../items/custom-enchantment-effects).

@[code transcludeWith=:::register-enchantment](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModEnchantmentGenerator.java)

Now simply run data generation, and your new enchantment will be available in-game!

## Enchantment Effect Conditions {#effect-conditions}

Most enchantment effect types are conditional effects. When adding these effects, it is possible to pass conditions to the `withEffect` call.

::: info

For an overview of the available condition types and their usage, see [the `Enchantments` class](https://mcsrc.dev/#1/1.21.11_unobfuscated/net/minecraft/world/item/enchantment/Enchantments#L126).

:::

@[code transcludeWith=:::effect-conditions](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModEnchantmentGenerator.java)

## Multiple Enchantment Effects {#multiple-enchantment-effects}

`withEffect` can be chained to add multiple enchantment effects to a single enchantment. However, this method requires you to specify the effect conditions for every effect.

To instead share the defined conditions and targets across multiple effects, `AllOf` can be used to merge them into a single effect.

@[code transcludeWith=:::multiple-effects](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModEnchantmentGenerator.java)

Note that the method to use depends on the type of effect being added. For example, `EnchantmentValueEffect` requires `AnyOf.valueEffects` instead. Differing effect types still require additional `withEffect` calls.
