---
title: Repetitive Block Generation
description: Learn how to make repetitive block model generation easier
authors:
  - JonyBoy19
---

When adding several different varrients of the same block to your block model data generation it can get quite
repetetive and make it difficult to find everything when you need to make changes.
Due to this fact it would be significantly easier to do this if you only had to change a few lines.

## Creating Example Repetitive Blocks {#creating-example-repetitive-blocks}

You will need to create two different basic blocks as shown in [creating your first block](./first-block) that we'll be
using in this example.

The blocks we will be adding can be show below:

@[code transcludeWith=:::repetitive-blocks](@/reference/latest/src/main/java/com/example/docs/block/ModBlocks.java)

For example purposes, you may use the following textures for this example:

<DownloadEntry downloadURL="/assets/develop/blocks/repetitive_example_block_textures.zip">Repetitive Block Model Textures</DownloadEntry>

## Adding Repetitive Generation {#adding-repetitive-generation}

Once you have your blocks created you begin adding your repetitive generation the the `ModelProvider`.

You'll start by adding the `getModBlockByName` method to your class.

The `getModblockByName` method will allow you to convert normal strings into registered blocks by matching the block
identifier with the inputed strings.

The method we'll be using is provided below:

@[code transcludeWith=:::by-block-name](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModModelProvider.java)

We also need to add the list that contain the colors the blocks that will be generated and the tool names of any other
blocks:

@[code transcludeWith=:::block-lists](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModModelProvider.java)

Now we create the loop inside of your `BlockModels` method, this loop will be what actualy runs through all of the
repetitive blocks and creates the model json's for all of your blocks and their variants.

We'll be using the loop show below.:

@[code transcludeWith=:::block-loop](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModModelProvider.java)

Now everything is complete, when you run data generation it should create all of the block model json's and the
textures will all be visible in game assuming you followed all instructions correctly.
