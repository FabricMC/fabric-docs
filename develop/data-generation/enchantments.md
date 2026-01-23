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

Then, use this register method to register your new enchantment.

@[code transcludeWith=:::register-enchantment](@/reference/latest/src/main/java/com/example/docs/enchantment/ModEnchantments.java)

Now we will add the generator, create the `ExampleModEnchantmentGenerator` class. This class should extend `FabricDynamicRegistryProvider`, and have its constructor and abstract methods implemented.

@[code transcludeWith=:::provider](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModEnchantmentGenerator.java)

Then, add the registration helper to the created class.

@[code transcludeWith=:::register](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModEnchantmentGenerator.java)

Ensure your new generator is registered in your `DataGeneratorEntrypoint`.

@[code transcludeWith=:::datagen-enchantments:register](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModDataGenerator.java)

## Creating the enchantment {#creating-the-enchantment}

To create the definition for our custom enchantment, we will use the `register` method in our generator class.

@[code transcludeWith=:::register-enchantment](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModEnchantmentGenerator.java)
