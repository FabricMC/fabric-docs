---
title: Block Containers
description: Learn how to add containers to your block entities.
authors:
  - natri0
resources:
  https://docs.neoforged.net/docs/inventories/container/: Containers - NeoForge Docs
---

It's good practice when creating blocks that can store items, like chests and furnaces, to implement `Container`. This makes it possible to, for example, interact with the block using hoppers.

In this tutorial we'll create a block that uses its container to duplicate any items placed in it.

## Creating the Block {#creating-the-block}

This should be familiar to the reader if they've followed the [Creating Your First Block](../blocks/first-block) and [Block Entities](../blocks/block-entities) guides. We'll create a `DuplicatorBlock` that extends `BaseEntityBlock` and implements `EntityBlock`.

<<< @/reference/latest/src/main/java/com/example/docs/block/custom/DuplicatorBlock.java#block

Then, we need to create a `DuplicatorBlockEntity`, which needs to implement the `Container` interface. As most containers are generally expected to work the same way, you can copy and paste a helper called `ImplementedContainer` that does most of the work, leaving us with just a few methods to implement.

::: details Show `ImplementedContainer`

<<< @/reference/latest/src/main/java/com/example/docs/container/ImplementedContainer.java

:::

<<< @/reference/latest/src/main/java/com/example/docs/block/entity/custom/DuplicatorBlockEntity.java#be

The `items` list is where the container's contents are stored. For this block we have it set to a size of 1 slot for the input.

Don't forget to register the block and block entity in their respective classes!

### Saving & Loading {#saving-loading}

If we want the contents to persist between game reloads like a vanilla `BlockEntity`, we need to save it as NBT. Thankfully, Mojang provides a helper class called `ContainerHelper` with all the necessary logic.

<<< @/reference/latest/src/main/java/com/example/docs/block/entity/custom/DuplicatorBlockEntity.java#save

## Interacting with the Container {#interacting-with-the-container}

Technically, the container is already functional. However, to insert items, we currently need to use hoppers. Let's make it so that we can insert items by right-clicking the block.

To do that, we need to override the `useItemOn` method in the `DuplicatorBlock`:

<<< @/reference/latest/src/main/java/com/example/docs/block/custom/DuplicatorBlock.java#useon

Here, if the player is holding an item and there is an empty slot, we move the item from the player's hand to the block's container and return `InteractionResult.SUCCESS`.

Now, when you right-click the block with an item, you'll no longer have it! If you run `/data get block` on the block, you'll see the item in the `Items` field in the NBT.

![Duplicator block and output of `/data get block` showing the item in the container](/assets/develop/blocks/container_1.png)

### Duplicating Items {#duplicating-items}

Let's now make it so that the block duplicates the stack you threw in it, but only two items at a time. And let's make it wait a second every time to not spam the player with items!

To do this, we'll add a `tick` function to the `DuplicatorBlockEntity`, and a field to store how much we've been waiting:

<<< @/reference/latest/src/main/java/com/example/docs/block/entity/custom/DuplicatorBlockEntity.java#tick

The `DuplicatorBlock` should now have a `getTicker` method that returns a reference to `DuplicatorBlockEntity::tick`.

<VideoPlayer src="/assets/develop/blocks/container_2.mp4">Duplicator block duplicating an oak log</VideoPlayer>

## Worldly Containers {#worldly-containers}

By default, you can insert and extract items from the container from any side. However, this might not be the desired behavior sometimes: for example, a furnace only accepts fuel from the side and items from the top.

To create this behavior, we need to implement the `WorldlyContainer` interface in the `BlockEntity`. This interface has three methods:

- `getSlotsForFace(Direction)` lets you control which slots can be interacted with from a given side.
- `canPlaceItemThroughFace(int, ItemStack, Direction)` lets you control whether an item can be inserted into a slot from a given side.
- `canTakeItemThroughFace(int, ItemStack, Direction)` lets you control whether an item can be extracted from a slot from a given side.

Let's modify the `DuplicatorBlockEntity` to only accept items from the top:

<<< @/reference/latest/src/main/java/com/example/docs/block/entity/custom/DuplicatorBlockEntity.java#accept

The `getSlotsForFace` returns an array of the slot _indices_ that can be interacted with from the given side. In this case, we only have a single slot (`0`), so we return an array with just that index.

Also, we should modify the `useItemOn` method of the `DuplicatorBlock` to actually respect the new behavior:

<<< @/reference/latest/src/main/java/com/example/docs/block/custom/DuplicatorBlock.java#place

Now, if we try to insert items from the side instead of the top, it won't work!

<VideoPlayer src="/assets/develop/blocks/container_3.webm">The Duplicator only activating when interacting with its top side</VideoPlayer>

## Menus {#menus}

To access the new container block via a menu, like you do with a chest, refer to the [Container Menus](./container-menus) guide.
