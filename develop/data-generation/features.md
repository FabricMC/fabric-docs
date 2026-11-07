---
title: Feature Generation
description: A guide to generating features in the world with datagen.
authors:
  - cassiancc
  - CelDaemon
  - its-miroma
  - Jimmy474
  - JonyBoy19
  - Wind292
---

<!---->

::: info PREREQUISITES

Make sure you've completed the [datagen setup](./setup) process first, and you understand [Dynamic Registry Population](./dynamic-registry)using datagen.

:::

Features in Minecraft are natural or generated patterns in the world, like trees, flowers, ores, or lakes. Features are different from structures (for example villages, temples...), which can be found with the `/locate` command.

The generation for features of Minecraft worlds is broken down into 3 parts:

- **Configured Features**: this defines what a feature is; for example, a single tree
- **Placement Features**: this defines how the features should be laid out, in which direction, relative location, and so on; for example, the placement of trees in a forest
- **Biome Modifications**: this defines where the features are placed in the world; for example, the coordinates of the whole forest

## Setup {#setup}

Creating new configured features and placement features is done in the same way as [Dynamic Registry Population](./dynamic-registry), so create a similar class and configure it to use the `Registries.CONFIGURED_FEATURE` and `Registries.PLACED_FEATURE` instead of the custom key.

<<< @/reference/latest/src/main/java/com/example/docs/worldgen/ExampleModWorldgenProvider.java#datagen_world_provider

The `bootstrap` method for these two registries can become big quickly, so putting them in their own separate classes is recommended.
The `T` of `BootstrapContext<T>` should be `ConfiguredFeature<?, ?>` for configured features and `PlacedFeature` for placed features.

## Configured Features {#configured-features}

To make a feature naturally spawn in our world, we should start by defining a configured feature in our configured features class.

<<< @/reference/latest/src/main/java/com/example/docs/worldgen/ExampleModWorldConfiguredFeatures.java#datagen_world_configure_features_class

Now, let's add a custom configured feature for a Diamond Ore vein. First, register the key for the `ConfiguredFeature` in your configured feature class:

<<< @/reference/latest/src/main/java/com/example/docs/worldgen/ExampleModWorldConfiguredFeatures.java#datagen_world_configured_key

::: tip

The second argument to the `Identifier` (`diamond_block_vein` in this example) is what you would use to spawn in the structure with the `/place` command, which is helpful for debugging.

:::

### Ores {#ores}

Next, we'll make a `RuleTest` inside the `bootstrap` method that controls which blocks your feature can replace. For example, this `RuleTest` allows the replacement of every block with the tag `DEEPSLATE_ORE_REPLACEABLES`:

<<< @/reference/latest/src/main/java/com/example/docs/worldgen/ExampleModWorldConfiguredFeatures.java#datagen_world_ruletest

Next, also inside the `bootstrap` method, we need to create the `OreConfiguration`, which tells the game what to replace blocks with.

<<< @/reference/latest/src/main/java/com/example/docs/worldgen/ExampleModWorldConfiguredFeatures.java#datagen_world_ore_feature_config

You can have multiple cases in the list for different variants. For example, let's set a different variant for stone and deepslate:

<<< @/reference/latest/src/main/java/com/example/docs/worldgen/ExampleModWorldConfiguredFeatures.java#datagen_world_multi_ore_feature_config

Lastly, we need to register our configured feature to our game inside the `bootstrap` method!

<<< @/reference/latest/src/main/java/com/example/docs/worldgen/ExampleModWorldConfiguredFeatures.java#datagen_world_conf_feature_register

### Trees {#trees}

To make a custom tree, you need to first create a `TreeConfiguration` inside the `bootstrap` method:

<<< @/reference/latest/src/main/java/com/example/docs/worldgen/ExampleModWorldConfiguredFeatures.java#datagen_world_tree_feature_config

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

Next, we need to register our tree by adding the following line to the `bootstrap` method of `ExampleModWorldConfiguredFeatures`.

<<< @/reference/latest/src/main/java/com/example/docs/worldgen/ExampleModWorldConfiguredFeatures.java#datagen_world_tree_register

## Placement Features {#placement-features}

The next step in adding a feature to the game is creating its Placement Feature.

<<< @/reference/latest/src/main/java/com/example/docs/worldgen/ExampleModWorldPlacedFeatures.java#datagen_world_placed_features_class

In your placed features class's `bootstrap` method, create a variable like the one below:

<<< @/reference/latest/src/main/java/com/example/docs/worldgen/ExampleModWorldPlacedFeatures.java#datagen_world_conf_feature_register

In your placed features class, define the key for your placed feature:

<<< @/reference/latest/src/main/java/com/example/docs/worldgen/ExampleModWorldPlacedFeatures.java#datagen_world_placed_key

### Placement Modifiers {#placement-modifiers}

Next, we need to define our Placement Modifiers inside the `bootstrap` method, which are attributes that you set when spawning the feature. These can be anything: from the spawn frequency, to the starting `y` level. You can have as few or as many modifiers as you like.

<<< @/reference/latest/src/main/java/com/example/docs/worldgen/ExampleModWorldPlacedFeatures.java#datagen_world_placement_modifiers

The function of each modifier listed is as follows:

- **`CountPlacement`**: Roughly the amount of instances of this feature (in this case veins) per chunk
- **`BiomeFilter`**: Allows us to control what biomes/dimensions it spawns (we'll do more with this later)
- **`InSquarePlacement`**: Spreads the features more pseudo-randomly
- **`HeightRangePlacement`**: Specifies the range of `y` coordinates where a feature can spawn; it supports three main types of distributions:
  1. **Uniform**:
     All `y` values within the range are equally likely to contain the feature. If you're unsure, just use this one.

  2. **Trapezoid**:
     `y` values closer to the median `y` value have a higher probability of containing the feature.

  3. **Biased-Bottom**:

     Uses a logarithmic scale where lower `y` values are more likely to get the feature. It receives a starting `y` coordinate, below which the feature never spawns. The second argument is the maximum height where the feature can spawn. The third argument defines a range in blocks over which the maximum probability is extended.

::: tip

Trees and other surface structures should include the modifier `PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP` instead of `HeightRangePlacement`, to make sure the tree spawns on the surface.

:::

Now that we have the modifiers, we can register our placed feature in the `bootstrap` method:

<<< @/reference/latest/src/main/java/com/example/docs/worldgen/ExampleModWorldPlacedFeatures.java#datagen_world_register_placed_feature

## Biome Modifications {#biome-modifications}

Lastly, we need to add our placed feature to `BiomeModifications` during mod initialization. We can do this by adding the following to our [mod initializer](../getting-started/project-structure#entrypoints):

<<< @/reference/latest/src/main/java/com/example/docs/ExampleMod.java#datagen_world_biome_modifications

::: tip

For trees, the second parameter should be set to `GenerationStep.Decoration.VEGETAL_DECORATION,`

:::

### Biome Specific Generation {#biome-specific-generation}

By changing the `BiomeSelectors` argument, we can have our feature spawn only in a specific type of biome:

<<< @/reference/latest/src/main/java/com/example/docs/ExampleMod.java#datagen_world_selective_biome_modifications

This would only spawn in biomes tagged with the `minecraft:is_forest` biome tag.

## Running Datagen {#running-datagen}

Now, when you run datagen, you should see a `.json` file under `src/main/generated/data/example-mod/worldgen/configured_feature` for each configured feature you added, and a file under `src/main/generated/data/example-mod/worldgen/placed_feature` for each placed feature as well!

::: details Generated File for the Configured Feature

<<< @/reference/latest/src/main/generated/data/example-mod/worldgen/configured_feature/diamond_block_vein.json

:::

::: details Generated File for the Placed Feature

<<< @/reference/latest/src/main/generated/data/example-mod/worldgen/placed_feature/diamond_block_ore_placed.json

:::

<!---->
