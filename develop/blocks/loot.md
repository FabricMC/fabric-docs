---
title: Block Loot Tables
description: A guide for making block loot tables.
authors:
  - apple502j
---

# Block Loot Tables

::: warning
This page assumes you have already created a block. If you haven't, see the [Creating Blocks](./basics.md) page.
:::

Loot tables specify the drops from breaking a block or killing a mob. Without a loot table, a block drops nothing. 

These are located inside the mod data pack (`/data/(mod id)/` in the `resources` directory). 

For example, the loot table for the new border block should be placed at `/data/fabric-docs-reference/loot_tabes/blocks/border.json`.

Here is a loot table for blocks that drop itself:

<!-- TODO: Datagen when data generation section is implemented. -->

@[code lang=json](@/reference/latest/src/main/resources/data/fabric-docs-reference/loot_tables/blocks/border.json)

The loot table is somewhat complex, because it specifies that the block should only drop if it survives an explosion if exploded. 

For example, if creepers blow up the block, it is not guaranteed to drop. This matches the behavior of many blocks in the vanilla game.

## Tool Requirements

A block might require a tool to trigger any loot-tables associated with the block, which is indicated by `.requiresTool()` block setting. 

The exact tool is specified using tags like `minecraft:mineable/pickaxe` and `minecraft:needs_stone_tool`.

For example, let's require the `machine_prototype` block to require at least a stone pickaxe to drop itself - `data/minecraft/tags/blocks/needs_stone_tool.json`.

<!-- TODO: Datagen when data generation section is implemented. -->

```json
{
    "replace": false,
    "values": [
        "fabric-docs-reference:machine_prototype"
    ]
}
```

## Dropping XP Orbs

::: warning
A block that can drop experience should not drop itself when mined (unless the tool has Silk Touch) to prevent XP duplication.
:::

To make a block drop experience orbs, override `onStacksDropped` like this:

```java
@Override
public void onStacksDropped(BlockState state, ServerWorld world, BlockPos pos, ItemStack tool, boolean dropExperience) {
    super.onStacksDropped(state, world, pos, tool, dropExperience);
    if (dropExperience) {
      // Drop 5 XP
      dropExperienceWhenMined(world, pos, tool, ConstantIntProvider.create(5)); 
    }
}
```