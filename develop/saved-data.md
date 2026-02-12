---
title: Saved Data
description: Saving data between game sessions.
authors:
  - dicedpixels
---

Saved Data is Minecraft's built-in solution to save data across sessions.

The data is saved to disk and reloaded when the game is closed and opened again. This data is usually scoped (e.g. the level). Data is written to the disk as [NBT](https://minecraft.wiki/w/NBT_format) and [Codecs](./codecs) are used to serialize/deserialize this data.

Let's look at a simple scenario where we need to save the number of blocks broken by the player. We can keep this count on the logical server.

We can use the `PlayerBlockBreakEvents.AFTER` event with a simple static integer field to store this value and post it as a chat message.

```java
private static int blocksBroken = 0; // keeps track of the number of blocks broken

PlayerBlockBreakEvents.AFTER.register((level, player, pos, state, blockEntity) -> {
    blocksBroken++; // increment the counter each time a block is broken
    player.displayClientMessage(Component.literal("Blocks broken: " + blocksBroken), false);
});
```

Now, when you break a block, you'll see a message with the count.

![Block Breaking](/assets/develop/saved-data/block-breaking.png)

If you restart Minecraft, load the world and start breaking blocks, you'll notice the count has reset. This is where we need Saved Data. We can then store this count, so that the next time you load the world, we can get the saved count and start incrementing it from that point.

## Saving Data {#saving-data}

`SavedData` is the main class responsible for managing the saving/loading of data. As it is an abstract class, you're expected to provide an implementation.

### Setting Up a Data Class {#setting-up-a-data-class}

Let's name our data class `SavedBlockData` and have it extend `SavedData`.

This class will contain a field to keep track of the number of blocks broken as well as a method to get and a method to increment this number.

@[code lang=java transcludeWith=:::basic_structure](@/reference/latest/src/main/java/com/example/docs/saveddata/SavedBlockData.java)

For serializing and deserializing this data, we need to define a Codec. We can compose a Codec using various primitive Codecs provided by Minecraft.

You'll need a constructor with an `int` argument to initialize the class.

@[code lang=java transcludeWith=:::ctor](@/reference/latest/src/main/java/com/example/docs/saveddata/SavedBlockData.java)

Then we can build a Codec.

@[code lang=java transcludeWith=:::codec](@/reference/latest/src/main/java/com/example/docs/saveddata/SavedBlockData.java)

We should call `setDirty()` when data is actually modified, so Minecraft knows it should be saved to the disk.

@[code lang=java transcludeWith=:::set_dirty](@/reference/latest/src/main/java/com/example/docs/saveddata/SavedBlockData.java)

Finally, we're required to have a `SavedDataType` that describes our saved data. The first argument corresponds to the name of the file that will be created in the `data` directory of the world.

@[code lang=java transcludeWith=:::type](@/reference/latest/src/main/java/com/example/docs/saveddata/SavedBlockData.java)

### Accessing Saved Data {#accessing-saved-data}

As mentioned earlier, saved data can be associated with a scope like the current level. In this case, our data will be a part of the level data. We can get the `DimensionDataStorage` of the level to add and modify our data.

We'll put this logic into a utility method.

@[code lang=java transcludeWith=:::method](@/reference/latest/src/main/java/com/example/docs/saveddata/SavedBlockData.java)

### Using Saved Data {#using-saved-data}

Now that we have everything set up, let's save some data.

We can reuse the first scenario and instead of incrementing the field, we can call our `incrementBlocksBroken` from our `SavedBlockData`.

@[code lang=java transcludeWith=:::event_registration](@/reference/latest/src/main/java/com/example/docs/saveddata/ExampleModSavedData.java)

This should increment the value and save it to the disk.

If you restart Minecraft, load the world and break a block, you'll see that the previously saved count is now incremented.

If you go into the `data` directory of the world, you'll see a `.dat` file with the name of `saved_block_data.dat`.
Opening this file in a NBT reader will show how our data is saved within.

![NBTg](/assets/develop/saved-data/nbt.png)
