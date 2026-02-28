---
title: Feature Generation
description: A guide to generating features in the world with datagen.
authors:
  - cassiancc
  - Wind292
---

<!---->

::: info PREREQUISITES

Make sure you've completed the [datagen setup](./setup) process first.

:::

The generation for features of Minecraft worlds is broken down into 3 parts:

- **Configured Features**: this defines what a feature is; for example, a single tree
- **Placement Features**: this defines how the features should be laid out, in which direction, relative location, and so on; for example, the placement of trees in a forest
- **Biome Modifications**: this defines where the features are placed in the world; for example, the coordinates of the whole forest

::: info

Features in Minecraft are natural or generated patterns in the world, like trees, flowers, ores, or lakes. Features are different from structures (for example villages, temples...), which can be found with the `/locate` command.

:::

## Setup {#setup}

First, we need to make our provider. Create a class that extends `FabricDynamicRegistryProvider` and fill out the base methods:

@[code lang=java transcludeWith=:::datagen-world:provider](@/reference/latest/src/main/java/com/example/docs/worldgen/ExampleModWorldgenProvider.java)

Then add this provider to your `DataGeneratorEntrypoint` class within the `onInitializeDataGenerator` method:

@[code lang=java transclude={67-67}](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModDataGenerator.java)

Next, make a class for your configured features and a class for your placed features. These don't need to extend anything.

The configured feature class and placed feature class should both have a public method to register and define your features. Its argument, that we called `context`, should be a `BootstrapContext<ConfiguredFeature<?, ?>>` for the configured feature, or a `BootstrapContext<PlacedFeature>` for the placed feature.

In your `DataGeneratorEntrypoint` class, add the lines below to your `buildRegistry` method, replacing the method name with what you chose:

@[code lang=java transcludeWith=:::datagen-world:registries](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModDataGenerator.java)

If you don't already have the `buildRegistry` method, create it and annotate it with an `@Override`.

## Configured Features {#configured-features}

To make a feature naturally spawn in our world, we should start by defining a configured feature in our configured features class. Let's add a custom configured feature for a Diamond Ore vein.

First, register the key for the `ConfiguredFeature` in your configured feature class:

@[code lang=java transcludeWith=:::datagen-world:configured-key](@/reference/latest/src/main/java/com/example/docs/worldgen/ExampleModWorldConfiguredFeatures.java)

::: tip

The second argument to the `Identifier` (`diamond_block_vein` in this example) is what you would use to spawn in the structure with the `/place` command, which is helpful for debugging.

:::

### Ores {#ores}

Next, we'll make a `RuleTest` that controls which blocks your feature can replace. For example, this `RuleTest` allows the replacement of every block with the tag `DEEPSLATE_ORE_REPLACEABLES`:

@[code lang=java transcludeWith=:::datagen-world:ruletest](@/reference/latest/src/main/java/com/example/docs/worldgen/ExampleModWorldConfiguredFeatures.java)

Next, we need to create the `OreConfiguration`, which tells the game what to replace blocks with.

@[code lang=java transcludeWith=:::datagen-world:ore-feature-config](@/reference/latest/src/main/java/com/example/docs/worldgen/ExampleModWorldConfiguredFeatures.java)

You can have multiple cases in the list for different variants. For example, let's set a different variant for stone and deepslate:

@[code lang=java transcludeWith=:::datagen-world:multi-ore-feature-config](@/reference/latest/src/main/java/com/example/docs/worldgen/ExampleModWorldConfiguredFeatures.java)

Lastly, we need to register our configured feature to our game!

@[code lang=java transcludeWith=:::datagen-world:conf-feature-register](@/reference/latest/src/main/java/com/example/docs/worldgen/ExampleModWorldConfiguredFeatures.java)

### Trees {#trees}

To make a custom tree, you need to first create a `TreeConfiguration`:

@[code lang=java transcludeWith=:::datagen-world:tree-feature-config](@/reference/latest/src/main/java/com/example/docs/worldgen/ExampleModWorldConfiguredFeatures.java)

This is what each argument does:

1. Specifies the block type for the tree trunk; for example, diamond blocks
2. Configures the trunk shape and height behavior using a trunk placer
3. Specifies the block type for the tree leaves; for example, gold blocks
4. Defines the foliage's shape and size using a foliage placer
5. Controls how the tree trunk tapers at different heights, primarily for larger trunks

::: tip

We _highly_ recommend that you play around with these values to create a custom tree that **you** are happy with!

You can use the built-in placers for the Trunk and Foliage from the vanilla trees as a reference.

:::

Next, we need to register our tree by adding the following line to the `configure` method of `ExampleModWorldConfiguredFeatures`.

@[code lang=java transcludeWith=:::datagen-world:tree-register](@/reference/latest/src/main/java/com/example/docs/worldgen/ExampleModWorldConfiguredFeatures.java)

## Placement Features {#placement-features}

The next step in adding a feature to the game is creating its Placement Feature.

In your placed features class's `configure` method, create a variable like the one below:

@[code lang=java transcludeWith=:::datagen-world:conf-feature-register](@/reference/latest/src/main/java/com/example/docs/worldgen/ExampleModWorldPlacedFeatures.java)

In your placed features class, define the key for your placed feature.

@[code lang=java transcludeWith=:::datagen-world:placed-key](@/reference/latest/src/main/java/com/example/docs/worldgen/ExampleModWorldPlacedFeatures.java)

### Placement Modifiers {#placement-modifiers}

Next, we need to define our Placement Modifiers, which are attributes that you set when spawning the feature. These can be anything: from the spawn frequency, to the starting `y` level. You can have as few or as many modifiers as you like.

@[code lang=java transcludeWith=:::datagen-world:placement-modifier](@/reference/latest/src/main/java/com/example/docs/worldgen/ExampleModWorldPlacedFeatures.java)

The function of each modifier listed is as follows:

- **CountPlacement**: Roughly the amount of instances of this feature (in this case veins) per chunk
- **BiomeFilter**: Allows us to control what biomes/dimensions it spawns (we'll do more with this later)
- **InSquarePlacement**: Spreads the features more pseudo-randomly
- **HeightRangePlacement**: Specifies the range of `y` coordinates where a feature can spawn; it supports three main types of distributions:
  1. **Uniform**:
     All `y` values within the range are equally likely to contain the feature. If you're unsure, just use this one.

  2. **Trapezoid**:
     `y` values closer to the median `y` value have a higher probability of containing the feature.

  3. **Biased-Bottom**:
     Uses a logarithmic scale where lower `y` values are more likely to get the feature. It receives a starting `y` coordinate, below which the feature never spawns. The second argument is the maximum height where the feature can spawn. The third argument defines a range in blocks over which the maximum probability is extended.

::: tip

Trees and other surface structures should include the modifier `PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP` instead of `HeightRangePlacement`, to make sure the tree spawns on the surface.

:::

Now that we have the modifiers, we can register our placed feature:

@[code lang=java transcludeWith=:::datagen-world:register-placed-feature](@/reference/latest/src/main/java/com/example/docs/worldgen/ExampleModWorldPlacedFeatures.java)

## Biome Modifications {#biome-modifications}

Lastly, we need to add our placed feature to `BiomeModifications` during mod initialization. We can do this by adding the following to our mod initiializer:

@[code lang=java transcludeWith=:::datagen-world:biome-modifications](@/reference/latest/src/main/java/com/example/docs/ExampleMod.java)

::: tip

For trees, the second parameter should be set to `GenerationStep.Decoration.VEGETAL_DECORATION,`

:::

### Biome Specific Generation {#biome-specific-generation}

By changing the `BiomeSelectors` argument, we can have our feature spawn only in a specific type of biome:

@[code lang=java transcludeWith=:::datagen-world:selective-biome-modifications](@/reference/latest/src/main/java/com/example/docs/ExampleMod.java)

This would only spawn in biomes tagged with the `minecraft:is_forest` biome tag.

## Running Datagen {#running-datagen}

Now, when you run datagen, you should see a `.json` file under `src/main/generated/data/example-mod/worldgen/configured_feature` for each configured feature you added, and a file under `src/main/generated/data/example-mod/worldgen/placed_feature` for each placed feature as well!

::: details Generated File for the Configured Feature

@[code lang=json](@/reference/latest/src/main/generated/data/example-mod/worldgen/configured_feature/diamond_block_vein.json)

:::

::: details Generated File for the Placed Feature

@[code lang=json](@/reference/latest/src/main/generated/data/example-mod/worldgen/placed_feature/diamond_block_ore_placed.json)

:::
