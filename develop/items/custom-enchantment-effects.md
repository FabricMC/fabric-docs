---
title: Custom Enchantment Effects
description: Learn how to create your enchantment effects.
authors:
  - CelDaemon
  - krizh-p
---

Starting from version 1.21, custom enchantments in Minecraft use a "data-driven" approach. This makes it easier to add simple enchantments, like increasing attack damage, but more challenging to create complex ones. The process involves breaking down enchantments into _effect components_.

An effect component contains the code that defines the special effects of an enchantment. Minecraft supports various default effects, such as item damage, knockback, and experience.

::: tip

Be sure to check if the default Minecraft effects satisfy your needs by visiting [the Minecraft Wiki's Enchantment Effect Components page](https://minecraft.wiki/w/Enchantment_definition#Effect_components). This guide assumes you understand how to configure "simple" data-driven enchantments and focuses on creating custom enchantment effects that aren't supported by default.

:::

## Custom Enchantment Effects {#custom-enchantment-effects}

Start by creating an `enchantment` folder, and within it, create an `effect` folder. Within that, we'll create the `LightningEnchantmentEffect` record.

Next, we can create a constructor and override the `EnchantmentEntityEffect` interface methods. We'll also create a `CODEC` variable to encode and decode our effect; you can read more about [Codecs here](../codecs).

The bulk of our code will go into the `apply()` event, which is called when the criteria for your enchantment to work is met. We'll later configure this `Effect` to be called when an entity is hit, but for now, let's write simple code to strike the target with lightning.

@[code transcludeWith=#entrypoint](@/reference/latest/src/main/java/com/example/docs/enchantment/effect/LightningEnchantmentEffect.java)

Here, the `amount` variable indicates a value scaled to the level of the enchantment. We can use this to modify how effective the enchantment is based on level. In the code above, we are using the level of the enchantment to determine how many lightning strikes are spawned.

## Registering the Enchantment Effect {#registering-the-enchantment-effect}

Like every other component of your mod, we'll have to add this `EnchantmentEffect` to Minecraft's registry. To do so, add a class `ModEnchantmentEffects` (or whatever you want to name it) and a helper method to register the enchantment. Be sure to call the `registerModEnchantmentEffects()` in your main class, which contains the `onInitialize()` method.

@[code transcludeWith=#entrypoint](@/reference/latest/src/main/java/com/example/docs/enchantment/ModEnchantmentEffects.java)

## Creating the Enchantment {#creating-the-enchantment}

Now we have an enchantment effect! The final step is to create an enchantment that applies our custom effect. While this can be done by creating a JSON file similar to those in datapacks, this guide will show you how to generate the JSON dynamically using Fabric's data generation tools. To begin, create an `ExampleModEnchantmentGenerator` class.

Within this class, we'll first register a new enchantment, and then use the `configure()` method to create our JSON programmatically.

@[code transcludeWith=#entrypoint](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModEnchantmentGenerator.java)

Before proceeding, you should ensure your project is configured for data generation; if you are unsure, [view the respective docs page](../data-generation/setup).

Lastly, we must tell our mod to add our `EnchantmentGenerator` to the list of data generation tasks. To do so, simply add the `EnchantmentGenerator` to this inside of the `onInitializeDataGenerator` method.

@[code transcludeWith=:::custom-enchantments:register-generator](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModDataGenerator.java)

Now, when you run your mod's data generation task, enchantment JSONs will be generated inside the `generated` folder. An example can be seen below:

@[code](@/reference/latest/src/main/generated/data/example-mod/enchantment/thundering.json)

You should also add translations to your `en_us.json` file to give your enchantment a readable name:

```json
"enchantment.example-mod.thundering": "Thundering",
```

You should now have a working custom enchantment effect! Test it by enchanting a weapon with the enchantment and hitting a mob. An example is given in the following video:

<VideoPlayer src="/assets/develop/enchantment-effects/thunder.webm">Using the Thundering Enchantment</VideoPlayer>
