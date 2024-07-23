---
title: Creating Your First Block
description: Learn how to create your first custom block in Minecraft.
authors:
  - IMB11
---

# Creating Your First Block {#creating-your-first-block}

Blocks are the building blocks of Minecraft (no pun intended) - just like everything else in Minecraft, they're stored in registries.

## Preparing Your Blocks Class {#preparing-your-blocks-class}

If you've completed the [Creating Your First Item](../items/first-item) page, this process will feel extremely familiar - you will need to create a method that registers your block, and it's block item.

You should put this method in a class called `ModBlocks` (or whatever you want to name it).

Mojang does something extremely similar like this with vanilla blocks; you can refer to the `Blocks` class to see how they do it.

@[code transcludeWith=:::1](@/reference/latest/src/main/java/com/example/docs/block/ModBlocks.java)

---

Just like with items, you need to ensure that the class is loaded so that all static fields containing your block instances are initialized.

You can do this by creating a dummy `initialize` method, which can be called in your mod initializer to trigger the static initialization.

::: info
If you are unaware of what static initialization is, it is the process of initializing static fields in a class. This is done when the class is loaded by the JVM, and is done before any instances of the class are created.
:::

```java
public class ModBlocks {
    // ...

    public static void initialize() {}
}
```

@[code transcludeWith=:::1](@/reference/latest/src/main/java/com/example/docs/block/FabricDocsReferenceBlocks.java)

## Creating And Registering Your Block {#creating-and-registering-your-block}

Similarly to items, blocks take a `Blocks.Settings` class in their constructor, which specifies properties about the block, such as its sound effects and mining level.

We will not cover all the options here—you can view the class yourself to see the various options, which should be self-explanatory.

For example purposes, we will be creating a simple block that has the properties of dirt, but is a different material.

::: tip
You can also use `AbstractBlock.Settings.copy(AbstractBlock block)` to copy the settings of an existing block, in this case, we could have used `Blocks.DIRT` to copy the settings of dirt, but for example purposes we'll use the builder.
:::

@[code transcludeWith=:::2](@/reference/latest/src/main/java/com/example/docs/block/ModBlocks.java)

To automatically create the block item, we can pass `true` to the `shouldRegisterItem` parameter of the `register` method we created in the previous step.

### Adding Your Block to an Item Group {#adding-your-block-to-an-item-group}

Since the `BlockItem` is automatically created and registered, to add it to an item group, you must use the `Block.asItem()` method to get the `BlockItem` instance.

For this example, we'll use a custom item group created in the [Custom Item Groups](../items/custom-item-groups) page.

@[code transcludeWith=:::3](@/reference/latest/src/main/java/com/example/docs/block/ModBlocks.java)

---

You should now notice that your block is in the creative inventory, and can be placed in the world!

![Block in world without suitable model or texture](/assets/develop/blocks/first_block_0.png).

There are a few issues though - the block item is not named, and the block has no texture, block model or item model.

## Adding Block Translations {#adding-block-translations}

To add a translation, you must create a translation key in your translation file - `assets/<mod id here>/lang/en_us.json`.

Minecraft will use this translation in the creative inventory and other places where the block name is displayed, such as command feedback.

```json
{
    "block.mod_id.condensed_dirt": "Condensed Dirt"
}
```

You can either restart the game or build your mod and press <kbd>F3</kbd> + <kbd>T</kbd> to apply changes - and you should see that the block has a name in the creative inventory and other places such as the statistics screen.

## Models and Textures {#models-and-textures}

All block textures can be found in the `assets/<mod id here>/textures/block` folder - an example texture for the "Condensed Dirt" block is free to use.

<DownloadEntry type="Texture" visualURL="/assets/develop/blocks/first_block_1.png" downloadURL="/assets/develop/blocks/first_block_1_small.png" />

To make the texture show up in-game, you must create a block and item model which can be found in the respective locations for the "Condensed Dirt" block:

- `assets/<mod id here>/models/block/condensed_dirt.json`
- `assets/<mod id here>/models/item/condensed_dirt.json`

The item model is pretty simple, it can just use the block model as a parent - since most block models have support for being rendered in a GUI:

@[code](@/reference/latest/src/main/resources/assets/fabric-docs-reference/models/item/condensed_dirt.json)

The block model however, in our case, must parent the `block/cube_all` model:

@[code](@/reference/latest/src/main/resources/assets/fabric-docs-reference/models/block/condensed_dirt.json)

When you load into the game, you may notice that the texture is still missing. This is because you need to add a blockstate definition.

## Creating the Block State Definition {#creating-the-block-state-definition}

The blockstate definition is used to instruct the game on which model to render based on the current state of the block.

For the example block, which doesn't have a complex blockstate, only one entry is needed in the definition.

This file should be located in the `assets/mod_id/blockstates` folder, and its name should match the block ID used when registering your block in the `ModBlocks` class. For instance, if the block ID is `condensed_dirt`, the file should be named `condensed_dirt.json`.

@[code](@/reference/latest/src/main/resources/assets/fabric-docs-reference/blockstates/condensed_dirt.json)

Blockstates are really complex, which is why they are addressed in an upcoming page: [Block States](./blockstates)

Restarting the game, or reloading via <kbd>F3</kbd> + <kbd>T</kbd> to apply changes - you should be able to see the block texture in the inventory and physically in the world:

![Block in world with suitable texture and model](/assets/develop/blocks/first_block_4.png)

## Adding Block Drops {#adding-block-drops}

When breaking the block in survival, you may see that the block does not drop - you might want this functionality, however to make your block drop as an item on break you must implement its loot table - the loot table file should be placed in the `data/<mod id here>/loot_table/blocks/` folder.

::: info
For a greater understanding of loot tables, you can refer to the [Minecraft Wiki - Loot Tables](https://minecraft.wiki/w/Loot_table) page.
:::

@[code](@/reference/latest/src/main/resources/data/fabric-docs-reference/loot_tables/blocks/condensed_dirt.json)

This loot table provides a single item drop of the block item when the block is broken, and when it is blown up by an explosion.

## Recommending a Harvesting Tool {#recommending-a-harvesting-tool}

You may also want your block to be harvestable only by a specific tool - for example, you may want to make your block faster to harvest with a shovel.

All the tool tags should be placed in the `data/minecraft/tags/block/mineable/` folder - where the name of the file depends on the type of tool used, one of the following:

- `hoe.json`
- `axe.json`
- `pickaxe.json`
- `shovel.json`

The contents of the file are quite simple - it is a list of items that should be added to the tag.

This example adds the "Condensed Dirt" block to the `shovel` tag.

@[code](@/reference/latest/src/main/resources/data/minecraft/tags/mineable/shovel.json)

## Mining Levels {#mining-levels}

Similarly, the mining level tag can be found in the same folder, and respects the following format:

- `needs_stone_tool.json` - A minimum level of stone tools
- `needs_iron_tool.json` - A minimum level of iron tools
- `needs_diamond_tool.json` - A minimum level of diamond tools.

The file has the same format as the harvesting tool file - a list of items to be added to the tag.

## Extra Notes {#extra-notes}

If you're adding multiple blocks to your mod, you may want to consider using [Data Generation](https://fabricmc.net/wiki/tutorial:datagen_setup) to automate the process of creating block and item models, blockstate definitions, and loot tables.
