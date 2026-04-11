---
title: Loot Table Generation
description: A guide to setting up loot table generation with datagen.
authors:
  - Alphagamer47
  - CelDaemon
  - JustinHuPrime
  - matthewperiut
  - skycatminepokie
  - Spinoscythe
authors-nogithub:
  - jmanc3
  - mcrafterzz
---

<!---->

::: info PREREQUISITES

Make sure you've completed the [datagen setup](./setup) process first.

:::

You will need different providers (classes) for blocks, chests, and entities. Remember to add them all to your pack in your `DataGeneratorEntrypoint` within the `onInitializeDataGenerator` method.

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModDataGenerator.java#datagen-loot-tables--register

## Loot Tables Explained {#loot-tables-explained}

**Loot tables** define what you get from breaking a block (not including contents, like in chests), killing an entity, or opening a newly-generated container. Each loot table has **pools** from which items are selected. Loot tables also have **functions**, which modify the resulting loot in some way.

Loot pools have **entries**, **conditions**, functions, **rolls**, and **bonus rolls**. Entries are groups, sequences, or possibilities of items, or just items. Conditions are things that are tested for in the world, such as enchantments on a tool or a random chance. The minimum number of entries chosen by a pool are called rolls, and anything over that is called a bonus roll.

## Blocks {#blocks}

In order for blocks to drop items - including itself - we need to make a loot table. Create a class that extends `FabricBlockLootTableProvider`:

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModBlockLootTableProvider.java#datagen-loot-tables--block-provider

Make sure to add this provider to your pack!

There's a lot of helper methods available to help you build your loot tables. We won't go over all of them, so make sure to check them out in your IDE.

Let's add a few drops in the `generate` method:

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModBlockLootTableProvider.java#datagen-loot-tables--block-drops

## Chests {#chests}

Chest loot is a little bit tricker than block loot. Create a class that extends `SimpleFabricLootTableProvider` similar to the example below **and add it to your pack**.

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModChestLootTableProvider.java#datagen-loot-tables--chest-provider

We'll need a `ResourceKey<LootTable>` for our loot table. Let's put that in a new class called `ModLootTables`. Make sure this is in your `main` source set if you're using split sources.

<<< @/reference/latest/src/main/java/com/example/docs/ModLootTables.java#datagen-loot-tables--mod-loot-tables

Then, we can generate a loot table inside the `generate` method of your provider.

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModChestLootTableProvider.java#datagen-loot-tables--chest-loot
