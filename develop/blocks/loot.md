---
title: Changing Block Drops
description: A guide for making block loot tables.
authors:
  - apple502j
---

# Changing Block Drops
We will continue from [the previous guide](./basics), this time, changing block drops.

## Loot Tables
Loot tables specify the drops from breaking a block or killing a mob. Without a loot table, a block drops nothing. These are located inside the mod data pack (`/data/(mod id)/` in the `resources` directory). For example, the loot table for the new border block should be placed at `/data/fabric-docs-reference/loot_tabes/blocks/border.json`.

Here is a loot table for blocks that drop itself:

@[code lang=json](@/reference/latest/src/main/resources/data/fabric-docs-reference/loot_tables/blocks/border.json)

The loot table is somewhat complex, because it specifies that the block should only drop if it survives an explosion if exploded. For example, if creepers blew up the block, it is not guaranteed to drop. This matches the behavior for many blocks in the vanilla game.

## Setting Required Tools
A block might require a tool to drop the item, which is indicated by `.requiresTool()` block setting. The exact tool is specified using tags like `minecraft:mineable/pickaxe` and `minecraft:needs_stone_tool`.

```java
{
    "replace": false,
    "values": [
        "fabric-docs-reference:machine_prototype"
    ]
}
```

## Dropping XP Orbs
To make a block drop experience orbs, override `onStacksDropped` like this:

```java
@Override
public void onStacksDropped(BlockState state, ServerWorld world, BlockPos pos, ItemStack tool, boolean dropExperience) {
  super.onStacksDropped(state, world, pos, tool, dropExperience);
  if (dropExperience) {
    // drop 5 XP
    dropExperienceWhenMined(world, pos, tool, ConstantIntProvider.create(5)); 
  }
}
```

::: warning
A block that can drop experience should not drop itself when mined (unless the tool has Silk Touch) to prevent XP duplication.
:::
