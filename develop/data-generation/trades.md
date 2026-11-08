---
title: Villager Trade Generation
description: A guide to setting up villager trade generation with datagen.
authors:
  - NotNightSky
---

::: info PREREQUISITES

Make sure you've completed the [datagen setup](./setup) and [tags setup](./tags) process first.

:::

## Setup Villager Trades {#setup-villager-trades}

In the datagen package, create a class that extends `FabricDynamicRegistryProvider`. In this newly created class, add a constructor that matches `super`, and implement the `configure` and `getName` methods.

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModVillagerTradesProvider.java#provider

Then, add the `create` helper method to the newly created class.

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModVillagerTradesProvider.java#create_helper

Now add the `bootstrap` method. Here, we will be registering the villager trades we want to add to the game.

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModVillagerTradesProvider.java#bootstrap

In your `DataGeneratorEntrypoint`, override the `buildRegistry` method and register our bootstrap method.

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModDataGenerator.java#datagen_villager_trades_bootstrap

Finally, ensure your new generator is registered within the `onInitializeDataGenerator` method.

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModDataGenerator.java#datagen_villager_trades_register

## Creating the Villager Trade {#creating-the-villager-trade}

For this example, we will be creating villager trades that allow the player to trade emeralds for the guidite armor set.

Before generating the actual villager trade, we need to create the resource keys for our custom villager trade. We will use the `create` method in our provider class.

The resource key is written as `profession/level/in_out`, where `profession` is the villager profession, `level` is the villager level, and `in` is the item being traded for `out`. The same naming convention applies to the resource key's variable name, but in all caps with underscores instead of slashes.

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModVillagerTradesProvider.java#trade_resource_keys

Now, we can register our villager trades in the provider's `bootstrap` method, using the resource keys we created.

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModVillagerTradesProvider.java#bootstrap_register

Here, the `register` method takes in the resource key and a `VillagerTrade` object.

The villager trade overload used in this example takes in the following parameters:

| Parameter            | Description                                                                       |
| -------------------- | --------------------------------------------------------------------------------- |
| `wants`              | The cost of the trade, in terms of the item and quantity                          |
| `gives`              | The item that will be given to the player                                         |
| `maxUses`            | The maximum number of times this trade can be used                                |
| `xp`                 | The amount of experience points the player will receive                           |
| `reputationDiscount` | The amount of discount provided based on the player's reputation                  |
| `merchantPredicate`  | Optional predicate used to determine if the trade should be offered to the player |
| `givenItemModifiers` | Optional list of modifiers to apply to the item given to the player               |

::: tip

For more information on villager trades, check out the `VillagerTrades` class.

:::

## Setup Trade Tags {#tags}

Now, to actually be able to obtain the villager trades in-game, we need to create tags for them.

In the datagen package, create a class that extends `FabricTagProvider<VillagerTrade>`. In this newly created class, add a constructor that matches `super`, and implement the `addTags` method.

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModVillagerTradesTagProvider.java#provider

And in your `DataGeneratorEntrypoint`, ensure your tags provider is registered within the `onInitializeDataGenerator` method.

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModDataGenerator.java#datagen_villager_trades_tag_register

## Creating the Trade Tags {#creating-the-trade-definition}

In the `addTags` method, we will be adding the villager trades to their respective tags.

All the tags can be found in the `VillagerTradeTags` class.

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModVillagerTradesTagProvider.java#tags

Now, we can run data generation, and our new villager trades will be available in-game!

![Trade in game](/assets/develop/data-generation/trades_in_game.png)
