---
title: Block and Item Appearance
description: Manipulating Block and Item Appearance.
authors:
  - dicedpixels
---

Blocks and items may need to have special ways of handling how they look in-game. For example, some blocks appear transparent and some may have a tint applied to it.

Let's see how we can manipulate block and item appearance.

For this example, let's register a block along with an item. We'll use basic settings for both.

@[code lang=java transcludeWith=:::block](@/reference/latest/src/main/java/com/example/docs/appearance/ExampleModAppearance.java)
@[code lang=java transcludeWith=:::item](@/reference/latest/src/main/java/com/example/docs/appearance/ExampleModAppearance.java)

Make sure to add :

- A block state definition (`/blockstates/waxcap.json`)
- A block model (`/models/block/waxcap.json`)
- An item model definition (`/items/waxcap.json`)
- An item model (`/models/item/waxcap.json`)
- A block texture (`/textures/block/waxcap.png`)
- An item texture (`/textures/item/waxcap.png`)

If everything is correct, you'll be able to see the block and item in-game.

While the item looks right, you'll notice that the block does not.

![Wrong Block Appearance](/assets/develop/block-and-item-appearance/block_appearance_0.png)

This is because a texture with transparency will require a bit of additional setup.

## Manipulating Block Appearance {#manipulating-block-appearance}

Even if your block's texture is transparent or translucent, it still looks opaque. To fix this, you need to set your block's _Chunk Section Layer_.

Chunk Section Layers are categories used to group different types of block surfaces for rendering. This allows the game to use the correct visual effects and optimizations for each type.

We need to register our block with the correct Chunk Section Layer. Fabric API provides `BlockRenderLayerMap` to do this.

In your **client initializer**, register your block with the correct `ChunkSectionLayer`.

@[code lang=java transcludeWith=:::block_render_layer_map](@/reference/latest/src/client/java/com/example/docs/appearance/ExampleModAppearanceClient.java)

Now your block should look correct.

![Correct Block Appearance](/assets/develop/block-and-item-appearance/block_appearance_1.png)

## Block Color Providers {#block-color-providers}

While our block looks fine in game, it has a grayscale texture. It's possible for us to dynamically apply a color tint, in the same way how leaf blocks change color based on biomes.

Fabric API provides `ColorProviderRegistry` to register a tint color provider to dynamically color the block.

Let's use this API to register a tint, so that when our Waxcap block is placed on grass, it will look green, otherwise it'll look brown.

In your **client initializer**, register your block, along with the appropriate logic.

@[code lang=java transcludeWith=:::color_provider](@/reference/latest/src/client/java/com/example/docs/appearance/ExampleModAppearanceClient.java)

![Block With Color Provider](/assets/develop/block-and-item-appearance/block_appearance_2.png)

## Item Tint Sources {#item-tint-sources}

Similar to blocks, items can also be tinted. Vanilla Minecraft implements a concept called _Item Tint Sources_, which is a part of the item model definition that allows items to have a tint.

Let's register a custom tint source to color our Waxcap item, so that when it rains, the item will look blue, otherwise it will look brown.

You'll first need to define a custom item tint source. This is done by implementing the `ItemTintSource` interface on a class or a record.

@[code lang=java transcludeWith=:::tint_source](@/reference/latest/src/client/java/com/example/docs/appearance/RainTintSource.java)

Let's look at the code.

As this is a part of the item model definition, it's possible for a tint value to be changed through a resource pack. So you need to define a Map Codec that's capable of reading your tint definition. In our case, our tint source will have a `int` value describing the color it will have when raining. We can use the built-in `ExtraCodecs.RGB_COLOR_CODEC` to compose our Codec.

@[code lang=java transclude={17-19}](@/reference/latest/src/client/java/com/example/docs/appearance/RainTintSource.java)

We can then return this Codec in `type()`.

@[code lang=java transclude={35-38}](@/reference/latest/src/client/java/com/example/docs/appearance/RainTintSource.java)

Finally, we can provide an implementation for `calculate` that would decide what the tint color would be. The current `color` is the color that's read from the resource pack.

@[code lang=java transclude={26-33}](@/reference/latest/src/client/java/com/example/docs/appearance/RainTintSource.java)

We then need to register our item tint source. This is done in the **client initializer** using the `ID_MAPPER` declared in `ItemTintSources`.

@[code lang=java transcludeWith=:::item_tint_source](@/reference/latest/src/client/java/com/example/docs/appearance/ExampleModAppearanceClient.java)

Once this is done, we can use our item tint source in an item model definition.

@[code lang=json transclude](@/reference/latest/src/main/generated/assets/example-mod/items/waxcap.json)

You can observe the item color change in-game.

![Item Tint In Game](/assets/develop/block-and-item-appearance/item_tint_0.png)
