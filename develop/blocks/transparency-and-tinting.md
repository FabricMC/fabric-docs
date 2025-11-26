---
title: Transparency and Tinting
description: Learn how to manipulate the appearance of blocks and tint them dynamically.
authors:
  - cassiancc
  - dicedpixels
---

You may sometimes want the appearance of blocks to be handled specially in-game. For example, some blocks may appear transparent, and some others may get a tint applied to them.

Let's take a look at how we can manipulate the appearance of a block.

For this example, let's register a block. If you are unfamiliar with this process, please read about [block registration](./first-block) first.

@[code lang=java transcludeWith=:::block](@/reference/latest/src/main/java/com/example/docs/appearance/ExampleModAppearance.java)

Make sure to add:

- A [block state](./blockstates) in `/blockstates/waxcap.json`
- A [model](./block-models) in `/models/block/waxcap.json`
- A [texture](./first-block#models-and-textures) in `/textures/block/waxcap.png`

If everything is correct, you'll be able to see the block in-game. However, you'll notice that, when placed, the block doesn't look right.

![Wrong Block Appearance](/assets/develop/transparency-and-tinting/block_appearance_0.png)

This is because a texture with transparency will require a bit of additional setup.

## Manipulating Block Appearance {#manipulating-block-appearance}

Even if your block's texture is transparent or translucent, it will still appear opaque. To fix this, you need to set your block's _Chunk Section Layer_.

Chunk Section Layers are categories used to group different types of block surfaces for rendering. This allows the game to use the correct visual effects and optimizations for each type.

We need to register our block with the correct Chunk Section Layer. Vanilla provides the following options.

- `SOLID`: The default, a solid block without any transparency.
- `CUTOUT` and `CUTOUT_MIPPED`: A block that makes use of transparency, for example Glass or Flowers. `CUTOUT_MIPPED` will look better at a distance.
- `TRANSLUCENT`: A block that makes use of translucent (partially transparent) pixels, for example Stained Glass or Water.

Our example has transparency, so it will use `CUTOUT`.

In your **client initializer**, register your block with the correct `ChunkSectionLayer` using Fabric API's `BlockRenderLayerMap`.

@[code lang=java transcludeWith=:::block_render_layer_map](@/reference/latest/src/client/java/com/example/docs/appearance/ExampleModAppearanceClient.java)

Now, your block should have proper transparency.

![Correct Block Appearance](/assets/develop/transparency-and-tinting/block_appearance_1.png)

## Block Color Providers {#block-color-providers}

Even though our block looks fine in-game, its texture is grayscale. We could dynamically apply a color tint, like how vanilla Leaves change color based on biomes.

Fabric API provides `ColorProviderRegistry` to register a tint color provider, which we'll use to dynamically color the block.

Let's use this API to register a tint such that, when our Waxcap block is placed on grass, it will look green, otherwise it'll look brown.

In your **client initializer**, register your block to the `ColorProviderRegistry`, along with the appropriate logic.

@[code lang=java transcludeWith=:::color_provider](@/reference/latest/src/client/java/com/example/docs/appearance/ExampleModAppearanceClient.java)

Now, the block will be tinted based on where its placed.

![Block With Color Provider](/assets/develop/transparency-and-tinting/block_appearance_2.png)
