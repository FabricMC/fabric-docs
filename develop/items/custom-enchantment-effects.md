---
title: Custom Enchantment Effects
description: Learn how to create your enchantment effects.
authors:
  - krizh-p
---

# Custom Enchantments {#custom-enchantments}

Since 1.21, custom enchantments have taken a "data-driven" approach; while this makes adding simple enchantments (ie. increasing attack damage) more straightforward and easy, it also complicates creating complex enchantments. This was done by breaking down what enchantments do into _effect components_.

An effect component contains code for what special things an enchantment should do. By default, Minecraft supports various effects such as item damage, knockback, experience, and more--however, this guide will focus on creating custom enchantment effects that are not supported by default.

**[You are heavily suggested to first determine if the default Minecraft effects will work for your usecase before contuining](https://minecraft.wiki/w/Enchantment_definition#Effect_components)**. The rest of the guide will assume you understand how "simple" data-driven enchantments are configured.

## Custom Enchantment Effects {#custom-enchantment-effects}

Start by creating an `enchantment` folder, and within it create a folder `effect`. Within the `effect` folder, we'll create the `LightningEnchantmentEffect` record.

Next, we can create a constructor and override the `EnchantmentEntityEffect` interface methods. We'll also create `CODEC` variable to encode and decode our effect; for more information on Codecs, [see their respective wiki page](https://docs.fabricmc.net/develop/codecs).

The bulk of our code will go into the `apply()` event, which is called when the criteria for your enchantment to work is met. We'll later configure this Effect to be called when an entity is hit, but for now let's write simple code to strike the target with lightning.

@[code transcludeWith=#entrypoint](@/reference/latest/src/main/java/com/example/docs/enchantment/effect/LightningEnchantmentEffect.java)

Here the `amount` variable indicates a value scaled to the level of the enchantment. We can use this to modify how effective the enchantment is based on level. In the code above, we are using the level of the enchantment to determine how many lightning strikes are spawned.

## Registering the Enchantment Effect {#registering-the-enchantment-effect}

Like every other component of your mod, we'll have to add this EnchantmentEffect to Minecraft's registry. To do so, add a class `ModEnchantmentEffects` (or whatever you want to name it) and a helper method to register the enchantment. Be sure to call the `registerModEnchantmentEffects()` in your main class which contains the `onInitialize()` method.

@[code transcludeWith=#entrypoint](@/reference/latest/src/main/java/com/example/docs/enchantment/ModEnchantmentEffects.java)

## Creating the Enchantment {#creating-the-enchantment}

Now we have an enchantment effect! Lastly, we'll create an enchantment to apply our custom effect to. This can be done by creating a JSON file like in datapacks, however, in this guide we'll generate the JSON dynamically using Fabric's data generation tools. To start, create an `EnchantmentGenerator` class.

Within this class, we'll first register a new enchantment, and then use the `configure()` method to create our JSON programatically.

@[code transcludeWith=#entrypoint](@\reference\latest\src\main\java\com\example\docs\data\EnchantmentGenerator.java)

Before procedding, you should ensure your project is configured for data generation; if you are unsure, [view the respective wiki page](https://fabricmc.net/wiki/tutorial:datagen_setup).

Lastly, we must tell our mod to add our `EnchantmentGenerator` to the list of data generation tasks. To do so, simply add the `EnchantmentGenerator` to this inside of the `onInitializeDataGenerator` class.

@[code transcludeWith=#initdatagen](@\reference\latest\src\main\java\com\example\docs\FabricDocsReferenceDataGenerator.java)

Now, when you run your mod's data generation task, enchantment JSONs will be generated inside the `generated` folder. An example can be seen below:

@[code](@\reference\latest\src\main\generated\data\fabric-docs-reference\enchantment\thundering.json)

You should also add translations to your `en_us.json` file to give your enchantment a readable name

```json
    "enchantment.FabricDocsReference.thundering": "Thundering"`
```

You should now be able to see our enchantment by running the Client task and opening up Minecraft.

<VideoPlayer src="/assets/develop/enchantment-effects/thunder.webm" title="Using the Lightning Effect" />
