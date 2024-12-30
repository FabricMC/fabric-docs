---
title: Block Entities
description: Learn how to create block entities for your custom blocks.
authors:
  - natri0
---

# Block Entities {#block-entities}

Block entities are a way to store additional data for a block, that is not part of the block state: inventory contents, custom name and so on.
Minecraft uses block entities for blocks like chests, furnaces, and command blocks.

As an example, we will create a block that counts how many times it has been right-clicked.

## Creating the Block Entity {#creating-the-block-entity}

To make Minecraft recognize and load the new block entities, we need to create a block entity type. This is done by extending the `BlockEntity` class and registering it in a new `ModBlockEntities` class.

@[code transcludeWith=:::1](@/reference/latest/src/main/java/com/example/docs/block/entity/custom/CounterBlockEntity.java)

Registering a `BlockEntity` yields a `BlockEntityType` like the `COUNTER_BLOCK_ENTITY` we've used above:

@[code transcludeWith=:::1](@/reference/latest/src/main/java/com/example/docs/block/entity/ModBlockEntities.java)

::: tip
Note how the constructor of the `CounterBlockEntity` takes two parameters, but the `BlockEntity` constructor takes three: the `BlockEntityType`, the `BlockPos`, and the `BlockState`.
If we didn't hard-code the `BlockEntityType`, the `ModBlockEntities` class wouldn't compile! This is because the `BlockEntityFactory`, which is a functional interface, describes a function that only takes two parameters, just like our constructor.
:::

## Creating the Block {#creating-the-block}

Next, to actually use the block entity, we need a block that implements `BlockEntityProvider`. Let's create one and call it `CounterBlock`.

::: tip
There's two ways to approach this:

- create a block that extends `BlockWithEntity` and implement the `createBlockEntity` method (_and_ the `getRenderType` method, since `BlockWithEntity` makes it invisible by default)
- create a block that implements `BlockEntityProvider` by itself and override the `createBlockEntity` method

We'll use the first approach in this example, since `BlockWithEntity` also provides some nice utilities.
:::

@[code transcludeWith=:::1](@/reference/latest/src/main/java/com/example/docs/block/custom/CounterBlock.java)

Using `BlockWithEntity` as the parent class means we also need to implement the `createCodec` method, which is rather easy.

Unlike blocks, which are singletons, a new block entity is created for every instance of the block. This is done with the `createBlockEntity` method, which takes the position and `BlockState`, and returns a `BlockEntity`, or `null` if there shouldn't be one.

Don't forget to register the block in the `ModBlocks` class, just like in the [Creating Your First Block](../blocks/first-block) guide:

@[code transcludeWith=:::5](@/reference/latest/src/main/java/com/example/docs/block/ModBlocks.java)

## Using the Block Entity {#using-the-block-entity}

Now that we have a block entity, we can use it to store the number of times the block has been right-clicked. We'll do this by adding a `clicks` field to the `CounterBlockEntity` class:

@[code transcludeWith=:::2](@/reference/latest/src/main/java/com/example/docs/block/entity/custom/CounterBlockEntity.java)

The `markDirty` method, used in `incrementClicks`, tells the game that this entity's data has been updated; this will be useful when we add the methods to serialize the counter and load it back from the save file.

Next, we need to increment this field every time the block is right-clicked. This is done by overriding the `onUse` method in the `CounterBlock` class:

@[code transcludeWith=:::2](@/reference/latest/src/main/java/com/example/docs/block/custom/CounterBlock.java)

Since the `BlockEntity` is not passed into the method, we use `world.getBlockEntity(pos)`, and if the `BlockEntity` is not valid, return from the method.

!["You've clicked the block for the 6th time" message on screen after right-clicking](/assets/develop/blocks/block_entities_1.png)

## Saving and Loading Data {#saving-loading}

Now that we have a functional block, we should make it so that the counter doesn't reset between game restarts. This is done by serializing it into NBT when the game saves, and deserializing when it's loading.

Serialization is done with the `writeNbt` method:

@[code transcludeWith=:::3](@/reference/latest/src/main/java/com/example/docs/block/entity/custom/CounterBlockEntity.java)

Here, we add the fields that should be saved into the passed `NbtCompound`: in the case of the counter block, that's the `clicks` field.

Reading is similar, but instead of saving to the `NbtCompound` you get the values you saved previously, and save them in the BlockEntity's fields:

@[code transcludeWith=:::4](@/reference/latest/src/main/java/com/example/docs/block/entity/custom/CounterBlockEntity.java)

Now, if we save and reload the game, the counter block should continue from where it left off when saved.

## Tickers {#tickers}

The `BlockEntityProvider` interface also defines a method called `getTicker`, which can be used to run code every tick for each instance of the block. We can implement that by creating a static method that will be used as the `BlockEntityTicker`:

The `getTicker` method should also check if the passed `BlockEntityType` is the same as the one we're using, and if it is, return the function that will be called every tick. Thankfully, there is a utility function that does the check in `BlockWithEntity`:

@[code transcludeWith=:::3](@/reference/latest/src/main/java/com/example/docs/block/custom/CounterBlock.java)

`CounterBlockEntity::tick` is a reference to the static method `tick` we should create in the `CounterBlockEntity` class. Structuring it like this is not required, but it's a good practice to keep the code clean and organized.

Let's say we want to make it so that the counter can only be incremented once every 10 ticks (2 times a second). We can do this by adding a `ticksSinceLast` field to the `CounterBlockEntity` class, and increasing it every tick:

@[code transcludeWith=:::5](@/reference/latest/src/main/java/com/example/docs/block/entity/custom/CounterBlockEntity.java)

Don't forget to serialize and deserialize this field!

Now we can use `ticksSinceLast` to check if the counter can be increased in `incrementClicks`:

@[code transcludeWith=:::6](@/reference/latest/src/main/java/com/example/docs/block/entity/custom/CounterBlockEntity.java)

::: tip
If the block entity does not seem to tick, try checking the registration code! It should pass the blocks that are valid for this entity into the `BlockEntityType.Builder`, or else it will give a warning in the console:

```text
[13:27:55] [Server thread/WARN] (Minecraft) Block entity fabric-docs-reference:counter @ BlockPos{x=-29, y=125, z=18} state Block{fabric-docs-reference:counter_block} invalid for ticking:
```

:::
