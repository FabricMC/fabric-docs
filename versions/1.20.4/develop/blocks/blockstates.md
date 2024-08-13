---
title: Block States
description: Learn why blockstates are a great way to add visual functionality to your blocks.
authors:
  - IMB11

search: false
---

# Block States {#block-states}

A block state is a piece of data attached to a singular block in the Minecraft world containing information on the block in the form of properties - some examples of properties vanilla stores in block states:

- Rotation: Mostly used for logs and other natural blocks.
- Activated: Heavily used in redstone devices, and blocks such as the furnace or smoker.
- Age: Used in crops, plants, saplings, kelp etc.

You can probably see why they are useful - they avoid the need to store NBT data in a block entity - reducing the world size, and preventing TPS issues!

Blockstate definitions are found in the `assets/<mod id here>/blockstates` folder.

## Example: Pillar Block {#pillar-block}

<!-- Note: This example could be used for a custom recipe types guide, a condensor machine block with a custom "Condensing" recipe? -->

Minecraft has some custom classes already that allow you quickly create certain types of blocks - this example goes through the creation of a block with the `axis` property by creating a "Condensed Oak Log" block.

The vanilla `PillarBlock` class allows the block to be placed in the X, Y or Z axis.

@[code transcludeWith=:::3](@/reference/1.20.4/src/main/java/com/example/docs/block/ModBlocks.java)

Pillar blocks have two textures, top and side - they use the `block/cube_column` model.

As always, with all block textures, the texture files can be found in `assets/<mod id here>/textures/block`

<DownloadEntry type="Textures" visualURL="/assets/develop/blocks/blockstates_0_large.png" downloadURL="/assets/develop/blocks/condensed_oak_log_textures.zip" />

Since the pillar block has two positions, horizontal and vertical, we'll need to make two separate model files:

- `condensed_oak_log_horizontal.json` which extends the `block/cube_column_horizontal` model.
- `condensed_oak_log.json` which extends the `block/cube_column` model.

An example of the `condensed_oak_log_horizontal.json` file:

@[code](@/reference/1.20.4/src/main/resources/assets/fabric-docs-reference/models/block/condensed_oak_log_horizontal.json)

---

::: info
Remember, blockstate files can be found in the `assets/<mod id here>/blockstates` folder, the name of the blockstate file should match the block ID used when registering your block in the `ModBlocks` class. For instance, if the block ID is `condensed_oak_log`, the file should be named `condensed_oak_log.json`.

For a more in-depth look at all the modifiers available in the blockstate files, check out the [Minecraft Wiki - Models (Block States)](https://minecraft.wiki/w/Tutorials/Models#Block_states) page.
:::

Next, we need to create a blockstate file. The blockstate file is where the magic happens—pillar blocks have three axes, so we'll use specific models for the following situations:

- `axis=x` - When the block is placed along the X axis, we will rotate the model to face the positive X direction.
- `axis=y` - When the block is placed along the Y axis, we will use the normal vertical model.
- `axis=z` - When the block is placed along the Z axis, we will rotate the model to face the positive X direction.

@[code](@/reference/1.20.4/src/main/resources/assets/fabric-docs-reference/blockstates/condensed_oak_log.json)

As always, you'll need to create a translation for your block, and an item model which parents either of the two models.

![Example of Pillar block in-game](/assets/develop/blocks/blockstates_1.png)

## Custom Block States {#custom-block-states}

Custom block states are great if your block has unique properties - sometimes you may find that your block can re-use vanilla properties.

This example will create a unique boolean property called `activated` - when a player right-clicks on the block, the block will go from `activated=false` to `activated=true` - changing its texture accordingly.

### Creating The Property {#creating-the-property}

Firstly, you'll need to create the property itself - since this is a boolean, we'll use the `BooleanProperty.of` method.

@[code transcludeWith=:::1](@/reference/1.20.4/src/main/java/com/example/docs/block/custom/PrismarineLampBlock.java)

Next, we have to append the property to the blockstate manager in the `appendProperties` method. You'll need to override the method to access the builder:

@[code transcludeWith=:::2](@/reference/1.20.4/src/main/java/com/example/docs/block/custom/PrismarineLampBlock.java)

You'll also have to set a default state for the `activated` property in the constructor of your custom block.

@[code transcludeWith=:::3](@/reference/1.20.4/src/main/java/com/example/docs/block/custom/PrismarineLampBlock.java)

::: warning
Don't forget to register your block using the custom class instead of `Block`!
:::

### Using The Property {#using-the-property}

This example flips the boolean `activated` property when the player interacts with the block. We can override the `onUse` method for this:

@[code transcludeWith=:::4](@/reference/1.20.4/src/main/java/com/example/docs/block/custom/PrismarineLampBlock.java)

### Visualizing The Property {#visualizing-the-property}

Before creating the blockstate file, you will need to provide textures for both the activated and deactivated states of the block, as well as the block model.

<DownloadEntry type="Textures" visualURL="/assets/develop/blocks/blockstates_2_large.png" downloadURL="/assets/develop/blocks/prismarine_lamp_textures.zip" />

Use your knowledge of block models to create two models for the block: one for the activated state and one for the deactivated state. Once you've done that, you can begin creating the blockstate file.

Since you created a new property, you will need to update the blockstate file for the block to account for that property.

If you have multiple properties on a block, you'll need to account for all possible combinations. For example, `activated` and `axis` would lead to 6 combinations (two possible values for `activated` and three possible values for `axis`).

Since this block only has two possible variants, as it only has one property (`activated`), the blockstate JSON will look something like this:

@[code](@/reference/1.20.4/src/main/resources/assets/fabric-docs-reference/blockstates/prismarine_lamp.json)

---

Since the example block is a lamp, we also need to make it emit light when the `activated` property is true. This can be done through the block settings passed to the constructor when registering the block.

You can use the `luminance` method to set the light level emitted by the block, we can create a static method in the `PrismarineLampBlock` class to return the light level based on the `activated` property, and pass it as a method reference to the `luminance` method:

@[code transcludeWith=:::5](@/reference/1.20.4/src/main/java/com/example/docs/block/custom/PrismarineLampBlock.java)

@[code transcludeWith=:::4](@/reference/1.20.4/src/main/java/com/example/docs/block/ModBlocks.java)

---

<!-- Note: This block can be a great starter for a redstone block interactivity page, maybe triggering the blockstate based on redstone input? -->

Once you've completed everything, the final result should look something like the following:

<VideoPlayer src="/assets/develop/blocks/blockstates_3.webm" title="Prismarine Lamp Block in-game" />
