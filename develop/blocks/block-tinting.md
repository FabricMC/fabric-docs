---
title: Block Tinting
description: Learn how to tint a block dynamically.
authors:
  - cassiancc
  - dicedpixels
---

You may sometimes want the appearance of blocks to be handled specially in-game. For example, some blocks, like Grass, get a tint applied to them.

Let's take a look at how we can manipulate the appearance of a block.

For this example, let's register a block. If you are unfamiliar with this process, please read about [block registration](./first-block) first.

<<< @/reference/latest/src/main/java/com/example/docs/block/ModBlocks.java#waxcap-tinting

Make sure to add:

- A [block state](./blockstates) in `/blockstates/waxcap.json`
- A [model](./block-models) in `/models/block/waxcap.json`
- A [texture](./first-block#models-and-textures) in `/textures/block/waxcap.png`

If everything is correct, you'll be able to see the block in-game.

![Correct Block Appearance](/assets/develop/transparency-and-tinting/block_appearance_1.png)

## Block Tint Sources {#block-tint-sources}

Even though our block looks fine in-game, its texture is grayscale. We could dynamically apply a color tint, like how vanilla Leaves change color based on biomes.

Fabric API provides the `BlockColorRegistry` to register a list of `BlockTintSource`s, which we'll use to dynamically color the block.

Let's use this API to register a tint such that, when our Waxcap block is placed on grass, it will look green, otherwise it'll look brown.

In your **client initializer**, register your block to the `ColorProviderRegistry`, along with the appropriate logic.

<<< @/reference/latest/src/client/java/com/example/docs/appearance/ExampleModAppearanceClient.java#color-provider

Now, the block will be tinted based on where its placed.

![Block With Color Provider](/assets/develop/transparency-and-tinting/block_appearance_2.png)
