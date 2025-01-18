---
title: Model Generation
description: A guide to generating models, item models and blockstates via datagen
authors:
  - Fellteros
---

# Model Generation {#model-generation}

::: info PREREQUISITES
Make sure you've completed the [datagen setup](./setup) process first.
:::

## Setup {#setup}

First, we will need to create our ModelProvider. Create a plain Java class that `extends FabricModelProvider`. Implement both abstract methods, `generateBlockStateModels` & `generateItemModels`.
Lastly, create constructor matching super.

:::tip
If you're developing mods in JetBrains' Intellij Idea, you can press `Alt+Shift+Enter` while hovering over the problem to bring up a quick fix menu!
:::

@[code lang=java transcludeWith=:::datagen-model:provider](@/reference/latest/src/client/java/com/example/docs/datagen/FabricDocsReferenceModelProvider.java)

Register this class in your `DataGeneratorEntrypoint` within the `onInitializeDataGenerator` method.

## Blockstates and block models {#blockstates-and-block-models}

::: info PREREQUISITES
You'll have to register the blocks, to which you want the data to be generated. Also see the blockstate and first block tutorials.
:::

This part of the tutorial will happen entirely in the `generateBlockStateModels` method. Notice the parameter `BlockStateModelGenerator blockStateModelGenerator` - this class will be responsible for generating all the JSON files.
Here are some handy examples you can use to generate your desired models:

### Simple Cube All {#simple-cube-all}

@[code lang=java transcludeWith=:::datagen-model:cube-all](@/reference/latest/src/client/java/com/example/docs/datagen/FabricDocsReferenceModelProvider.java)

The easiest and most basic method there is. It generates a JSON model file for a normal cubic block. It provides the same texture (named `steel_block`) for all six sides.

```json
{
  "parent": "minecraft:block/cube_all",
  "textures": {
    "all": "fabric-docs-reference:block/steel_block"
  }
}
```

It also generates a blockstate JSON file. Since we have no blockstates (e.g. Axis, Facing, ...), one variant is sufficient, and is used everytime the block is placed.

```json
{
  "variants": {
    "": {
      "model": "fabric-docs-reference:block/steel_block"
    }
  }
}
```

### Singletons {#singletons}

The `registerSingleton` method provides JSON model files based on the `TexturedModel` you pass in and a single blockstate variant. For example

@[code lang=java transcludeWith=:::datagen-model:cube-top-for-ends](@/reference/latest/src/client/java/com/example/docs/datagen/FabricDocsReferenceModelProvider.java)

will generate models for a normal cube, that uses a PNG file `pipe_block_side` for the sides and a PNG file `pipe_block_top` for the top and bottom sides.

:::tip
If you're stuck choosing which TextureModel you should use, open the `TexturedModel` class and look at the `TextureMaps`!
:::

### Block Texture Pool {#block-texture-pool}

@[code lang=java transcludeWith=:::datagen-model:block-texture-pool](@/reference/latest/src/client/java/com/example/docs/datagen/FabricDocsReferenceModelProvider.java)

Another useful method is `registerCubeAllModelTexturePool`. Define the textures by passing in the "base block", and then append the "children", which will have the same textures. <br>
In this case, we passed in the `RUBY_BLOCK`, so the stairs, slab and fence will use the `RUBY_BLOCK` texture.
***It will also generate a [simple cube all JSON model](#simple-cube-all-simple-cube-all) for the "base block" to ensure the texture provision!***

::: warning
Make sure you're using the correct Block class when passing in the block!
For example, in the `.stairs()` method, you have to pass in an instance of StairsBlock; in `.slab()`, it has to be an instance of SlabBlock and so on! 
:::


