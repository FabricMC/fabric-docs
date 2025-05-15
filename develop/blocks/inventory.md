---
title: Block Inventories
description: Learn how to add inventories to your blocks.
authors:
  - natri0
---

In Minecraft, a best practice when creating blocks that can store items is to implement `Inventory`. It is implemented by blocks like chests and furnaces. This makes it possible to, for example, interact with the block using hoppers.

In this tutorial we'll create a block that uses its inventory to duplicate any items placed in it.

## Creating the Block {#creating-the-block}

This should be familiar to the reader if they've followed the [Creating Your First Block](../blocks/first-block) and [Block Entities](../blocks/block-entities) guides. We'll create a `DuplicatorBlock` that extends `BlockWithEntity` and implements `BlockEntityProvider`.

@[code transcludeWith=:::1](@/reference/latest/src/main/java/com/example/docs/block/custom/DuplicatorBlock.java)

Then, we need to create a `DuplicatorBlockEntity`, which needs to implement the `Inventory` interface. Thankfully, there's a helper called `ImplementedInventory` that does most of the work, leaving us with just a few methods to implement.

@[code transcludeWith=:::1](@/reference/latest/src/main/java/com/example/docs/block/entity/custom/DuplicatorBlockEntity.java)

The `items` list is where the inventory's contents are stored. For this block we have it set to a size of 1 slot for the input.

Don't forget to register the block and block entity in their respective classes!

### Saving & Loading {#saving-loading}

Just like with regular `BlockEntities`, if we want the contents to persist between game reloads, we need to save it as NBT. Thankfully, Mojang provides a helper class called `Inventories` with all the necessary logic.

@[code transcludeWith=:::2](@/reference/latest/src/main/java/com/example/docs/block/entity/custom/DuplicatorBlockEntity.java)

## Interacting with the Inventory {#interacting-with-the-inventory}

Technically, the inventory is already functional. However, to insert items, we currently need to use hoppers. Let's make it so that we can insert items by right-clicking the block.

To do that, we need to override the `onUseWithItem` method in the `DuplicatorBlock`:

@[code transcludeWith=:::2](@/reference/latest/src/main/java/com/example/docs/block/custom/DuplicatorBlock.java)

Here, if the player is holding an item and there is an empty slot, we move the item from the player's hand to the block's inventory and return `ItemActionResult.SUCCESS`.

Now, when you right-click the block with an item, you'll no longer have it! If you run `/data get block` on the block, you'll see the item in the `Items` field in the NBT.

![Duplicator block and output of `/data get block` showing the item in the inventory](/assets/develop/blocks/inventory_1.png)

### Duplicating Items {#duplicating-items}

Actually, on second thought, shouldn't a _duplicator_ block duplicate items? Let's make it so that the block duplicates the stack you threw in it, but only two items at a time. And let's make it wait a second every time to not spam the player with items!

To do this, we'll add a `tick` function to the `DuplicatorBlockEntity`, and a field to store how much we've been waiting:

@[code transcludeWith=:::3](@/reference/latest/src/main/java/com/example/docs/block/entity/custom/DuplicatorBlockEntity.java)

The `DuplicatorBlock` should now have a `getTicker` method that returns a reference to `DuplicatorBlockEntity::tick`.

<VideoPlayer src="/assets/develop/blocks/inventory_2.mp4">Duplicator block duplicating an oak log</VideoPlayer>

## Sided Inventories {#sided-inventories}

By default, you can insert and extract items from the inventory from any side. However, this might not be the desired behavior sometimes: for example, a furnace only accepts fuel from the side and items from the top.

To create this behavior, we need to implement the `SidedInventory` interface in the `BlockEntity`. This interface has three methods:

- `getInvAvailableSlots(Direction)` lets you control which slots can be interacted with from a given side.
- `canInsert(int, ItemStack, Direction)` lets you control whether an item can be inserted into a slot from a given side.
- `canExtract(int, ItemStack, Direction)` lets you control whether an item can be extracted from a slot from a given side.

Let's modify the `DuplicatorBlockEntity` to only accept items from the top:

@[code transcludeWith=:::4](@/reference/latest/src/main/java/com/example/docs/block/entity/custom/DuplicatorBlockEntity.java)

The `getInvAvailableSlots` returns an array of the slot _indices_ that can be interacted with from the given side. In this case, we only have a single slot (`0`), so we return an array with just that index.

Also, we should modify the `onUseWithItem` method of the `DuplicatorBlock` to actually respect the new behavior:

@[code transcludeWith=:::3](@/reference/latest/src/main/java/com/example/docs/block/custom/DuplicatorBlock.java)

Now, if we try to insert items from the side instead of the top, it won't work!

<VideoPlayer src="/assets/develop/blocks/inventory_3.webm">The Duplicator only activating when interacting with its top side</VideoPlayer>
