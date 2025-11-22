---
title: Transparency and Tinting
description: Manipulating Block Appearance and Dynamically Tinting Blocks.
authors:
  - dicedpixels
---

Blocks may need to have special ways of handling how they look in-game. For example, some blocks appear transparent and some may have a tint applied to it.

Let's see how we can manipulate the appearance of a block.

For this example, let's register a block.

@[code lang=java transcludeWith=:::block](@/reference/latest/src/main/java/com/example/docs/appearance/ExampleModAppearance.java)

Make sure to add:

- A block state definition (`/blockstates/waxcap.json`)
- A model (`/models/block/waxcap.json`)
- A texture (`/textures/block/waxcap.png`)

If everything is correct, you'll be able to see the block in-game. However, you'll notice that the block when place, doesn't look right.

![Wrong Block Appearance](/assets/develop/transparency-and-tinting/block_appearance_0.png)

This is because a texture with transparency will require a bit of additional setup.

## Manipulating Block Appearance {#manipulating-block-appearance}

Even if your block's texture is transparent or translucent, it still looks opaque. To fix this, you need to set your block's _Chunk Section Layer_.

Chunk Section Layers are categories used to group different types of block surfaces for rendering. This allows the game to use the correct visual effects and optimizations for each type.

We need to register our block with the correct Chunk Section Layer. Fabric API provides `BlockRenderLayerMap` to do this.

In your **client initializer**, register your block with the correct `ChunkSectionLayer`.

@[code lang=java transcludeWith=:::block_render_layer_map](@/reference/latest/src/client/java/com/example/docs/appearance/ExampleModAppearanceClient.java)

Now your block should look correct.

![Correct Block Appearance](/assets/develop/transparency-and-tinting/block_appearance_1.png)

## Block Color Providers {#block-color-providers}

While our block looks fine in game, it has a grayscale texture. It's possible for us to dynamically apply a color tint, in the same way how leaf blocks change color based on biomes.

Fabric API provides `ColorProviderRegistry` to register a tint color provider to dynamically color the block.

Let's use this API to register a tint, so that when our Waxcap block is placed on grass, it will look green, otherwise it'll look brown.

In your **client initializer**, register your block, along with the appropriate logic.

@[code lang=java transcludeWith=:::color_provider](@/reference/latest/src/client/java/com/example/docs/appearance/ExampleModAppearanceClient.java)

Now you'll see the block have a tint based on where its placed.

![Block With Color Provider](/assets/develop/transparency-and-tinting/block_appearance_2.png)
