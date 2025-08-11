---
title: World Generation
description: A guide to setting up world generation with datagen.
authors:
  - Wind292
authors-nogithub:
---
::: info PREREQUISITES
Make sure you've completed the [datagen setup](./setup) process first.
:::

## World Generation Structure
The generation for features of Minecraft worlds is broken down into 3 parts.
* **Configured Features** This defines what features themselves are; this would be like a single tree in a forest.
* **Placement Features** This defines how the features should be laid out, what direction, relative location, and so on; this would be like the forest.
* **Biome Modifications** This defines where in the world the features are placed on a global scale; this would be like what coordinates the forest would be at.

::: info 
Features in Minecraft are natural or generated objects in the world like trees, ores, lakes, caves, and structures (villages, temples, ...etc).
:::

## Setup
First, we need to make our provider. Create a class that extends `FabricDynamicRegistryProvider` and fill out the base methods: 

@[code lang=java transcludeWith=:::datagen-world:provider](@/reference/latest/src/main/java/com/example/docs/worldgen/FabricDocsReferenceWorldgenProvider.java)

Then add this provider to `DataGeneratorEntrypoint` within the `onInitializeDataGenerator` method:

@[code lang=java transclude={46-46}](@/reference/latest/src/client/java/com/example/docs/datagen/FabricDocsReferenceDataGenerator.java)


Next make a class for your Configured Features and a class for your Placed Features, these don't need to extend anything.

The Configured Feature class and Placed Feature class should both have a public method where you will register and define your Configured / Placed feature.

This method should take a `Registerable<ConfiguredFeature<?, ?>>` argument for the Configured Feature and a `Registerable<PlacedFeature>` for the Placed Feature (both referred to as context in this example).

In your Data Generator class add the lines below to your `buildRegistry` method (Replacing the placeholder methods).

@[code lang=java transcludeWith=:::datagen-world:registries](@/reference/latest/src/client/java/com/example/docs/datagen/FabricDocsReferenceDataGenerator.java)


If you don't already have the `buildRegistry` method, create it and annotate it with an `@Override`.

## Configured Features

To make a feature naturally spawn in our world we should start by defining the Configured Feature in our Configured Features class.

First register the key for the `ConfiguredFeature` in your Configured Feature class by:

@[code lang=java transcludeWith=:::datagen-world:configured-key](@/reference/latest/src/main/java/com/example/docs/worldgen/FabricDocsReferenceWorldConfiguredFeatures.java)

::: tip
The second argument to the `Identifier` (`diamond_block_vein` in this example) is what you would use to spawn in the structure with the `/place` command, which is helpful for debugging.
:::

### Ores
Then you need to make a `RuleTest` for what blocks your feature can replace

This `RuleTest` allows the replacement for every block with the tag `DEEP_SLATE_ORE_REPLACEABLES`; another useful tag for ores is `STONE_ORE_REPLACEABLES`

@[code lang=java transcludeWith=:::datagen-world:ruletest](@/reference/latest/src/main/java/com/example/docs/worldgen/FabricDocsReferenceWorldConfiguredFeatures.java)

Next we need to create the `OreFeatureConfig`. The `OreFeatureConfig` tells the game what to replace blocks with.

@[code lang=java transcludeWith=:::datagen-world:ore-feature-config](@/reference/latest/src/main/java/com/example/docs/worldgen/FabricDocsReferenceWorldConfiguredFeatures.java)

You can have multiple cases in the list for different variants of the ore, for example a deepslate variant:

@[code lang=java transcludeWith=:::datagen-world:multi-ore-feature-config](@/reference/latest/src/main/java/com/example/docs/worldgen/FabricDocsReferenceWorldConfiguredFeatures.java)

Lastly we need to register our Configured Feature to our game!

@[code lang=java transcludeWith=:::datagen-world:conf-feature-register](@/reference/latest/src/main/java/com/example/docs/worldgen/FabricDocsReferenceWorldConfiguredFeatures.java)


### Trees

To make a custom tree you need to first create a `TreeFeatureConfig`:

@[code lang=java transcludeWith=:::datagen-world:tree-feature-config](@/reference/latest/src/main/java/com/example/docs/worldgen/FabricDocsReferenceWorldConfiguredFeatures.java)

- Argument 1 — Specifies the block type for the tree trunk (in this case, diamond blocks).
- Argument 2 — Configures the trunk shape and height behavior using a trunk placer.
- Argument 3 — Specifies the block type for the tree leaves (in this case, gold blocks).
- Argument 4 — Defines the foliage's shape and size using a foliage placer.
- Argument 5 — Controls how the tree trunk tapers at different heights, primarily for larger trunks.

:::tip
We *highly* recommend that you play around with these values to create a custom tree that **you** are happy with! 

There are a few of built-in placers for the Trunk and Foliage from the vanilla trees
:::

Next we need to register our tree:

@[code lang=java transcludeWith=:::datagen-world:tree-register](@/reference/latest/src/main/java/com/example/docs/worldgen/FabricDocsReferenceWorldConfiguredFeatures.java)

## Placement Features
The next step in adding a feature to the game is creating its Placement Feature.

In your Placed Features class's method with the argument of `Registerable<PlacedFeature>` create a variable like the one below:

@[code lang=java transcludeWith=:::datagen-world:conf-feature-register](@/reference/latest/src/main/java/com/example/docs/worldgen/FabricDocsReferenceWorldPlacedFeatures.java)

In your Placed Features class define the key for your Placed Feature.

@[code lang=java transcludeWith=:::datagen-world:placed-key](@/reference/latest/src/main/java/com/example/docs/worldgen/FabricDocsReferenceWorldPlacedFeatures.java)

### Placement Modifiers

Next we need to define our Placement Modifiers. Placement Modifiers are attributes that you add to the spawning of the feature in the game; this can be anything from how often it spawns to what `y` level it spawns at.

You can have as few or as many modifiers as you like.

@[code lang=java transcludeWith=:::datagen-world:placement-modifier](@/reference/latest/src/main/java/com/example/docs/worldgen/FabricDocsReferenceWorldPlacedFeatures.java)

The function of each modifier listed is as follows:
* **CountPlacementModifier** - Roughly the amount of instances of this feature per chunk (in this case veins per chunk)
* **BiomePlacementModifier** - Allows us to control what biomes/dimensions it spawns (we'll do more on this later)
* **SquarePlacementModifier** - Disperses the features more pseudo-randomly
* **HeightRangePlacementModifier** - Specifies the range of `y` coordinates where a feature can spawn; it supports three main types of distributions:
1. **Uniform:**  
   All `y` values within the range are equally likely to contain the feature.

2. **Trapezoid:**  
   `y` values closer to the median `y` value have a higher probability of containing the feature.

3. **Biased-Bottom:**  
   Uses a logarithmic scale where lower `y` values are more common for the feature, starting from a minimum `y` coordinate below which the feature never spawns. The second argument is the max height that the feature can spawn. The third argument defines a range in blocks over which the maximum probability is extended.

::: tip 
If you're unsure which `HeightRangePlacementModifier` to use for your ore, just use Uniform.
:::

::: tip
Trees and other surface structures should include the modifier `PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP` instead of `HeightRangePlacementModifier` to make the tree to spawn at the surface
:::

Now that we have the modifiers we can register our Placed Feature with:

@[code lang=java transcludeWith=:::datagen-world:register-placed-feature](@/reference/latest/src/main/java/com/example/docs/worldgen/FabricDocsReferenceWorldPlacedFeatures.java)

## Biome Modifications
Lastly we need to add our Placed Feature to `BiomeModifications` during mod initialization, we can do this with

@[code lang=java transcludeWith=:::datagen-world:biome-modifications](@/reference/latest/src/main/java/com/example/docs/FabricDocsReference.java)

::: tip
Trees should have the `GenerationStep.Feature` of `GenerationStep.Feature.VEGETAL_DECORATION,`
:::

### Biome Exclusivity
By changing the BiomeSelectors argument we can have our feature only spawn in a specific biome or type of biome.

@[code lang=java transcludeWith=:::datagen-world:selective-biome-modifications](@/reference/latest/src/main/java/com/example/docs/FabricDocsReference.java)

This would only spawn in biomes tagged with the `is_forest` tag.

## Running Datagen
Now when you run datagen you should see a `.json` file in `src/main/generated/data/<modid>/worldgen/configured_feature` for  each Configured Feature you added and a file in `src/main/generated/data/<modid>/worldgen/placed_feature` for each Placed Feature you added!


### Example Configured Feature .json file
@[code lang=json](@/reference/latest/src/main/generated/data/fabric-docs-reference/worldgen/configured_feature/diamond_block_vein.json)



### Example Placed Feature .json file

@[code lang=json](@/reference/latest/src/main/generated/data/fabric-docs-reference/worldgen/placed_feature/diamond_block_ore_placed.json)


## Results

Now when you run your game you should see your feature generating in the world!
