---
title: Creating Blocks
description: A guide for creating simple blocks.
authors:
  - apple502j
---

# Creating Blocks
One of the most common things to do in your mod is adding custom blocks.

A block, at its simplest form, has no functionality and only one "block state". A block state is a combination of properties, or variations, for the same block - things like rotations, crop age, or redstone activation state. For example, chains have 6 block states; 3 for the `axis` property, times 2 for the `waterlogged` property. A block can have only finite number of pre-defined block states. A block that needs more storage and functionality must have an associated "block entity". A block entity can store infinite, customizable data and perform logics every tick.

This tutorial explains the basics: a cubic block with no associated block entity.

## Simple blocks
Let's add a block with no functionality, named "border". To do so, first create a `Block` instance and store it in a `static final` field in your class (usually your `ModInitializer`):

@[code lang=java transcludeWith=:::1](@/reference/latest/src/main/java/com/example/docs/block/FabricDocsReferenceBlocks.java)

The `Block` constructor takes an `AbstractBlock.Settings` instance. This configures the properties common to all block states. Here, `strength(1.5f)` specifies the block's hardness; this is the same as that of natural stones. (Note that this does **not** specify that you need a pickaxe to mine this block.) There are many settings, from sounds to light level and map colors; try experiment with them!

Then, inside your `ModInitializer`'s `onInitialize` method, the block instance must be registered in `Registries.BLOCK`, like this:

@[code lang=java transcludeWith=:::2](@/reference/latest/src/main/java/com/example/docs/block/FabricDocsReferenceBlocks.java)

Note that the item for your block is registered separately. Some blocks (like fire) do not have an item form, but most do. To register the corresponding item, create a `BlockItem` instance and register it under `Registries.ITEM`.

@[code lang=java transcludeWith=:::3](@/reference/latest/src/main/java/com/example/docs/block/FabricDocsReferenceBlocks.java)

## Block models and textures
A block has a model, which describes the shape and textures of the rendered block. (This is different from the shape used for checking collision.) A block can have multiple models depending on the block state; the model is picked according to the "blockstate file". Items, including block-placing items, have separate models.

Let's give the border block a texture with white background and black borders, like this. Most block textures are 16-by-16 square. This is placed at `/textures/block/border.png`, inside the mod resource pack (`/assets/(mod id)/` in the `resources` directory).

![](/reference/latest/src/main/resources/assets/fabric-docs-reference/textures/block/border.png)

This texture is then referenced by a model file named `border.json`, placed under `/models/block` directory inside the mod resource pack. Since the block has the same texture on all six sides, we use `cube_all` as the parent model.

@[code lang=json](@/reference/latest/src/main/resources/assets/fabric-docs-reference/models/block/border.json)

Finally, there is the blockstates file, placed under `/blockstates`.

@[code lang=json](@/reference/latest/src/main/resources/assets/fabric-docs-reference/blockstates/border.json)

The model for the item must be provided separately under `/models/item/border.json`.

@[code lang=json](@/reference/latest/src/main/resources/assets/fabric-docs-reference/models/item/border.json)

Here are the border blocks, in-game:

![Hey, a fountain!](/assets/develop/blocks/border-block-fountain.png)

## Multiple block states
Let's add a block that can be rotated horizontally, like a carved pumpkin. The block has a button texture on one side - let's call it "machine prototype". This requires making a custom class for your block. The class name should end with "Block", like `MachinePrototypeBlock`.

All blocks inherit from `Block`. Right now, it only has a constructor (which is the same as the `Block` one).

@[code lang=java transcludeWith=:::1](@/reference/latest/src/main/java/com/example/docs/block/MachinePrototypeBlock.java)

You can copy-paste the definition and registration for the border block in your `ModInitializer`, and replace `Block` with `MachinePrototypeBlock`:

@[code lang=java transcludeWith=:::4](@/reference/latest/src/main/java/com/example/docs/block/FabricDocsReferenceBlocks.java)

Let's add the properties! Here, we're using `facing`, or `Properties.HORIZONTAL_FACING`. The values of this property are `Direction`s - specifically, `NORTH`, `EAST`, `WEST`, or `SOUTH`. To add that property to the block states, first override `appendProperties`:

@[code lang=java transcludeWith=:::2](@/reference/latest/src/main/java/com/example/docs/block/MachinePrototypeBlock.java)

Then, we can set the default block state inside the constructor. We follow the usual practice of using `Direction.NORTH` as the default. Here, we use a new method: `BlockState#with` method, which returns the block state with the passed property set to the passed value.

@[code lang=java transcludeWith=:::3](@/reference/latest/src/main/java/com/example/docs/block/MachinePrototypeBlock.java)

That said, adding a property is usually not enough. There needs to be some logic that actually uses the property. For example, the block state should reflect the direction the player faces when placing - otherwise commands would be necessary to make the block face other directions. They also need to handle structure blocks rotating the block. Those are accomplished by overriding these 3 methods:

@[code lang=java transcludeWith=:::4](@/reference/latest/src/main/java/com/example/docs/block/MachinePrototypeBlock.java)

Here, we use another, very important method: `BlockState#get`, which returns the value for the property.

::: tip
Your code editor or compiler might output a "deprecation warning". These can be ignored - in reality, nothing is deprecated here.

Why are they marked as deprecated, then? Here, it is how Mojang marks methods that are "override-only". Those methods should not be called by modded code (except in `super` call).
:::

## Modeling with multiple states
The machine prototype block also needs a texture. Because the block is directional, however, the block texture should ideally indicate the direction. We reuse the texture for the border, except for the front face, which uses this texture:

![](/reference/latest/src/main/resources/assets/fabric-docs-reference/textures/block/machine_prototype.png)

The model, this time, is inherited from `orientable`. `orientable` can be used to specify different textures for the front, top, and sides. This time, though, the top and side textures are the same.

@[code lang=json](@/reference/latest/src/main/resources/assets/fabric-docs-reference/models/block/machine_prototype.json)

The blockstate file looks like this. Notice how it specifies the block states using the `property=value` format, and the added `y` fields (which specify the rotation).

@[code lang=json](@/reference/latest/src/main/resources/assets/fabric-docs-reference/blockstates/machine_prototype.json)

As always, the machine prototype item needs a separate model. This is basically the same as the border one (with the ID replaced), so it is omitted.

## Adding functionality
Although many blocks in the game are purely decorational, some are functional as well. Block functions range from player interactions to random ticks (like crop growth) or redstone functions. Because the machine prototype remains a prototype, why not make it produce a sound when used - an explosion sound?

When a block is "used" - or, interacted with, the game calls the block's `onUse` method. The method gets some arguments, like the block state, position, and most importantly the player. It returns `ActionResult`, which indicates the result of the interaction:

- `SUCCESS`. Yay, the action worked!
- `CONSUME`. The interaction was successful, but the player hand should not swing.
- `CONSUME_PARTIAL`. The interaction was successful, but the player hand should not swing and the block use statistics should not be incremented.
- `PASS`. The block was not used, and let the player hand's item do its job.

Here is the example of `onUse` method, playing the explosion sound. Notice how it only plays the sound on the logical server side.

@[code lang=java transcludeWith=:::5](@/reference/latest/src/main/java/com/example/docs/block/MachinePrototypeBlock.java)

Of course, the block does not actually explode. Making it do so, however, is left as an exercise for the readers. (Hint: check `World#createExplosion`.)
