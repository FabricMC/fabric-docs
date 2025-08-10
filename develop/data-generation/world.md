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
The generation for features of minecraft worlds is broken down into 3 parts.
* **Configured Features** This defines what features themselves are; this would be like a single tree in a forest.
* **Placement Features** This defines how the features should be laid out, what direction, relative location, ...etc; this would be like the forest.
* **Biome Modifications** This defines where in the world the features are placed on a global scale; this would be like what coordinates the forest would be at.

::: info 
Features in Minecraft are natural or generated objects in the world like trees, ores, lakes, caves, and structures (villages, temples, ...etc).
:::

## Setup
First, we need to make our provider. Create a class that extends `FabricDynamicRegistryProvider` and fill out the base methods: 

```java
public class FabricDocsReferenceWorldgenProvider extends FabricDynamicRegistryProvider {
    public FabricDocsReferenceWorldgenProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup registries, Entries entries) {
    }

    @Override
    public String getName() {
        return "";
    }
}
```


Then add this provider to `DataGeneratorEntrypoint` within the `onInitializeDataGenerator` method:

```java
pack.addProvider(FabricDocsReferenceWorldgenProvider::new);
```


Next make a class for your Configured Features and a class for your Placed Features, these don't need to extend anything.

The Configured Feature class and Placed Feature class should both have a public method where you will register and define your Configured / Placed feature.

This method should take a `Registerable<ConfiguredFeature<?, ?>>` argument for the Configured Feature and a `Registerable<PlacedFeature>` for the Placed Feature (both referred to as context in this example).

In your Data Generator class add the lines below to your `buildRegistry` method (Replacing the placeholder methods).

```java
registryBuilder.addRegistry(RegistryKeys.CONFIGURED_FEATURE, FabricDocsReferenceConfiguredFeatures::<Your Method>);
registryBuilder.addRegistry(RegistryKeys.PLACED_FEATURE, FabricDocsReferencePlacedFeatures::<Your Method>);
```

If you don't already have the `buildRegistry` method, create it and annotate it with an `@Override`.

## Configured Features

To make a feature naturally spawn in our world we should start by defining the configured feature in our Configured Features class. 

First register the key for the `ConfiguredFeature` by:

```java
public static final RegistryKey<ConfiguredFeature<?, ?>> DIAMOND_BLOCK_VEIN_CONFIGURED_KEY =
    RegistryKey.of(
        RegistryKeys.CONFIGURED_FEATURE,
        Identifier.of(FabricDocsReference.MOD_ID, "diamond_block_vein") 
    );
```
::: tip
The second argument to the `Identifier` (`diamond_block_vien` in this example) is what you would use to spawn in the structure with the `/place` command, which is helpful for debugging.
:::

### Ores
Then you need to make a `RuleTest` for what blocks your feature can replace

This `RuleTest` allows the replacement for every block with the tag `DEEP_SLATE_ORE_REPLACEABLES`; another common tag for ores is `STONE_ORE_REPLACEABLES`

```java
RuleTest deepslateReplaceableRule = new TagMatchRuleTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES);
```

Next we need to create the `OreFeatureConfig`. The `OreFeatureConfig` tells the game what to replace blocks with.

```java
List<OreFeatureConfig.Target> diamondBlockOreConfig = 
    List.of(
        OreFeatureConfig.createTarget(deepslateReplaceableRule, Blocks.DIAMOND_BLOCK.getDefaultState()) 
    );
```

You can have multiple cases in the list for different variants of the ore, for example a deepslate variant:

```java
List<OreFeatureConfig.Target> diamondBlockOreConfig = 
    List.of(
        OreFeatureConfig.createTarget(deepslateReplaceableRule, Blocks.DIAMOND_BLOCK.getDefaultState()),
        OreFeatureConfig.createTarget(stoneReplaceableRule, Blocks.IRON_BLOCK.getDefaultState()),
    );
```

Lastly we need to register our configured feature to our game!

```java
context.register(
    DIAMOND_BLOCK_VEIN_CONFIGURED_KEY,
    new ConfiguredFeature<>(
        Feature.ORE,
        new OreFeatureConfig(diamondBlockOreConfig, 10)) // 10 is the blocks per vein
    )
```

### Trees

To make a custom tree you need to first create a `TreeFeatureConfig`:

```java
TreeFeatureConfig diamondTree = new TreeFeatureConfig.Builder(
        // Trunk / Logs
        BlockStateProvider.of(Blocks.DIAMOND_BLOCK), 
        new StraightTrunkPlacer(4, 2, 0), 
        // Leaves
        BlockStateProvider.of(Blocks.GOLD_BLOCK), 
        new BlobFoliagePlacer(ConstantIntProvider.create(2), ConstantIntProvider.create(0), 3), 

        new TwoLayersFeatureSize(0, 0, 0) 
).build();
```
- **What block to use as the logs** — Specifies the block type for the tree trunk (in this case, diamond blocks).
- **How to generate the trunk** — Configures the trunk shape and height behavior using a trunk placer.
- **What block to use for the leaves** — Specifies the block type for the tree leaves (in this case, gold blocks).
- **Leaves shape** — Defines the foliage's shape and size using a foliage placer.
- **Used for larger trunk thicknesses to dictate when to taper off** — Controls how the tree trunk tapers at different heights, primarily for larger trunks.

:::tip
We *highly* recommend that you play around with these values to create a tree that **you** are happy with! 

There are a few of built-in placers for the Trunk and Foliage from the vanilla trees
:::

Next we need to register our tree:
```java
context.register(DIAMOND_TREE_KEY, new ConfiguredFeature<>(Feature.TREE, diamondTree));

```

## Placement Features
The next step in adding a feature to the game is creating its Placement Feature.

In your Placed Features class's method with the argument of `Registerable<PlacedFeature>` create a variable like the one below:
```java
RegistryEntryLookup<ConfiguredFeature<?,?>> configuredFeatures = context.getRegistryLookup(RegistryKeys.CONFIGURED_FEATURE);
```

In your Placed Features class define a `RegistryKey<PlacedFeature>`.

```java
public static final RegistryKey<PlacedFeature> DIAMOND_BLOCK_ORE_PLACED_KEY =
    RegistryKey.of( 
        RegistryKeys.PLACED_FEATURE,
        Identifier.of(TemplateMod.MOD_ID, "diamond_block_ore_placed")
    );
```

### Placement Modifiers

Next we need to define our `List<PlacementModifier>`.

You can have as few or as many modifiers as you like.

```java
List<PlacementModifier> diamondBlockModifiers = List.of(
        CountPlacementModifier.of(2),
        BiomePlacementModifier.of(),
        SquarePlacementModifier.of(),
        HeightRangePlacementModifier.of(BiasedToBottomHeightProvider.create(YOffset.BOTTOM, YOffset.fixed(0),3))
);
```

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
If you're unsure which `HeightRangePlacementModifier` to use, just use Uniform.
:::

::: tip
Trees and other surface structures should include the modifier `PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP` instead of `HeightRangePlacementModifier` to make the tree to spawn at the surface
:::

Now that we have the modifiers we can register our Placed Feature with:

```java
context.register(
    DIAMOND_BLOCK_ORE_PLACED_KEY,
    new PlacedFeature(
        configuredFeatures.getOrThrow(ConfiguredFeatures.DIAMOND_BLOCK_VEIN_KEY),
        diamondBlockModifiers
    )
);
```

## Biome Modifications
Lastly we need to add our Placed Feature to `BiomeModifications` during mod initialization, we can do this with

```java
BiomeModifications.addFeature(
    BiomeSelectors.foundInOverworld(),
    GenerationStep.Feature.UNDERGROUND_ORES,
    PlacedFeatures.DIAMOND_BLOCK_ORE_PLACED_KEY
);
```

::: tip
Trees should have the `GenerationStep.Feature` of `GenerationStep.Feature.VEGETAL_DECORATION,`
:::

### Biome Exclusivity
By changing the BiomeSelectors argument we can have our feature only spawn in a specific biome or type of biome.

```java
BiomeModifications.addFeature(
    BiomeSelectors.tag(TagKey.of(RegistryKeys.BIOME, Identifier.of("minecraft", "is_forest"))),
    GenerationStep.Feature.UNDERGROUND_ORES,
    PlacedFeatures.DIAMOND_BLOCK_ORE_PLACED_KEY
);
```

This would only spawn in biomes tagged with the `is_forest` tag.

## Running Datagen
Now when you run datagen you should see a `.json` file in `src/main/generated/data/<modid>/worldgen/configured_feature` for each Configured Feature you added and a file in `src/main/generated/data/<modid>/worldgen/placed_feature` for each Placed Feature you added!

### Example Configured Feature .json file
```json
{
  "type": "minecraft:ore",
  "config": {
    "discard_chance_on_air_exposure": 0.0,
    "size": 5,
    "targets": [
      {
        "state": {
          "Name": "minecraft:diamond_block"
        },
        "target": {
          "predicate_type": "minecraft:tag_match",
          "tag": "minecraft:deepslate_ore_replaceables"
        }
      }
    ]
  }
}
```

### Example Placed Feature .json file
```json
{
  "feature": "template-mod:diamond_block_vein",
  "placement": [
    {
      "type": "minecraft:count",
      "count": 2
    },
    {
      "type": "minecraft:in_square"
    },
    {
      "type": "minecraft:height_range",
      "height": {
        "type": "minecraft:biased_to_bottom",
        "inner": 3,
        "max_inclusive": {
          "absolute": 0
        },
        "min_inclusive": {
          "above_bottom": 0
        }
      }
    }
  ]
}

```
## Results

Now when you run your game you should see your feature generating in the world!

<!-- ![A diamond-block vein](/assets/develop/data-generation/world/diamond_block_vein.png) -->
