---
title: Biome Generation
description: A guide to making custom biomes spawn naturally in game.
authors:
  - Wind292
  - NotNightSky
  - TerraformersMC
---

<!---->

::: info PREREQUISITES

Make sure you've completed the [datagen setup](./setup) and [biome creation](./biome-creation) process first.

:::

## Dependencies {#dependencies}

To change what biomes can spawn in the overworld in minecraft you will need\* a external mod to manage the generation of biomes as the Fabric API does not implement it itself.

::: info

\* It is possible to add your own custom biomes directly with mixins by overwriting the target dimension's data file, but it is not recommended. If you do so the compatibility with other mods' biomes will be terrible.

:::

The most popular libraries are [Biolith](https://github.com/TerraformersMC/Biolith) and [Terrablender](https://github.com/glitchfiend/terrablender).
In this guide we are going to be using Biolith because it is lighter-weight and more simple to use than Terrablender.

## Getting Started With Biolith {#getting-started-biolith}

This section is going to be following [Biolith's examples](https://github.com/gniftygnome/biolith-examples) README page.

First set the Biolith version in `gradle.properties`:

@[code lang=groovy](@/reference/latest/gradle.properties)

and then add Biolith's repository to our `build.gradle`:

@[code lang=groovy transcludeWith=:::biome-generation:biolith-repo](@/reference/latest/build.gradle)

Lastly add Biolith as a dependency in `build.gradle`:

```groovy
dependencies {
  implementation("com.terraformersmc:biolith-fabric:${project.biolith_version}")
  // ...
}
```

## Adding Biomes with Biolith {#adding-biomes-biolith}

To add a biome to one of the default dimensions it is as simple as adding as follows to your mod initialization:

@[code lang=java transcludeWith=:::world-gen-biomes:overworld-addition](@/reference/latest/src/main/java/com/example/docs/ExampleMod.java)

To add a biome to another dimension you can just use the other variants of the method above, for example:

@[code lang=java transcludeWith=:::world-gen-biomes:nether-addition](@/reference/latest/src/main/java/com/example/docs/ExampleMod.java)

If you want to add a biome to a custom dimension refer to the `Multi Noise` section of the [Dimension Creation page](./dimension-creation/#multi-noise).

::: tip

Highly recommend to read [this wiki page](https://minecraft.wiki/w/World_generation) on how biomes are generated and what the `Climate.Parameter` values represent.

:::

<!-- -->