---
title: Data Attachments
description: A guide covering basic usage of Fabric's new Data Attachment API.
authors:
  - cassiancc
  - DennisOchulor
---

The Data Attachment API is a recent and experimental addition to Fabric API. It allows developers to easily attach arbitrary data to Entities, Block Entities, Levels, and Chunks. The attached data can be stored and synchronized through [Codecs](./codecs) and Stream Codecs, so you should familiarize with those before using it.

## Creating a Data Attachment {#creating-attachments}

You'll start with a call to `AttachmentRegistry.create`. The following example creates a basic data attachment that does not sync or persist across restarts.

```java
public static final AttachmentType<String> EXAMPLE_STRING_ATTACHMENT = AttachmentRegistry.create(
  ResourceLocation.fromNamespaceAndPath("example-mod", "example_string_attachment") // The ID of your attachment
)
```

`AttachmentRegistry` contains a few methods for creating basic data attachments, including:

- `AttachmentRegistry.create()`: Creates a data attachment. Restarting the game will clear the attachment.
- `AttachmentRegistry.createPersistent()`: Creates a data attachment that will persist between game restarts.
- `AttachmentRegistry.createDefaulted()`: Creates a data attachment with a default value, which you can read with `getAttachedOrCreate`. Restarting the game will clear the attachment.

The behavior of each method can also be replicated and further customized with the `builder` parameter of `create`, through the [method chaining pattern](https://en.wikipedia.org/wiki/Method_chaining).

### Syncing a Data Attachment {#syncing-attachments}

If you need a data attachment to both be persistent and synchronized between server and clients, you can set that behavior using the `create` method, which allows configuration through a `builder` chain. For example:

```java
public static final AttachmentType<BlockPos> EXAMPLE_BLOCK_POS_ATTACHMENT = AttachmentRegistry.create(
  ResourceLocation.fromNamespaceAndPath("example-mod", "example_block_pos_attachment"),
  builder -> builder
    .initializer(() -> new BlockPos(0, 0, 0)) // The default value of the attachment, if one has not been set.
    .syncWith(
      BlockPos.STREAM_CODEC,  // Dictates how to turn the data into a packet to send to clients.
      AttachmentSyncPredicate.all() // Dictates who to send the data to.
    )
 );
```

The example above synced to every player, but that might not fit your use case. Here are some other default predicates, but you can also build your own by referencing the `AttachmentSyncPredicate` class.

- `AttachmentSyncPredicate.all()`: Syncs the attachment with all clients.
- `AttachmentSyncPredicate.targetOnly()`: Syncs the attachment only with the target it is attached to. Note that the syncing can only happen if the target is a player.
- `AttachmentSyncPredicate.allButTarget()`: Syncs the attachment with every client except the target it is attached to. Note that the exception can only apply if the target is a player.

### Persisting Data Attachments {#persisting-attachments}

Data attachments can also be set to persist across game restarts by calling the `persistent` method on the builder chain. It takes in a `Codec` so that the game knows how to serialize the data.

They can be set to perdure even after the death or [conversion](https://minecraft.wiki/w/Mob_conversion) of the target with the `copyOnDeath` method.

```java
public static final AttachmentType<BlockPos> EXAMPLE_BLOCK_POS_ATTACHMENT = AttachmentRegistry.create(
  ResourceLocation.fromNamespaceAndPath("example-mod", "example_block_pos_attachment"),
  builder -> builder
    .initializer(() -> new BlockPos(0, 0, 0);) // The default value of the attachment, if one has not been set.
    .persistent(BlockPos.CODEC) // Dictates how this attachment's data should be saved and loaded.
    .copyOnDeath() // Dictates that this attachment should persist even after the entity dies or converts.
 );
```

## Reading From a Data Attachment {#reading-attachments}

Methods to read from a Data Attachment have been injected onto the `Entity`, `BlockEntity`, `ServerLevel` and `ChunkAccess` classes. Using it is as simple as calling one of the methods, which return the value of the attached data.

```java
// Checks if the given AttachmentType has attached data, returning a boolean.
entity.hasAttached(EXAMPLE_STRING_ATTACHMENT);

// Gets the data associated with the given AttachmentType, or `null` if it doesn't exist.
entity.getAttached(EXAMPLE_STRING_ATTACHMENT);

// Gets the data associated with the given AttachmentType, throwing a `NullPointerException` if it doesn't exist.
entity.getAttachedOrThrow(EXAMPLE_STRING_ATTACHMENT);

// Gets the data associated with the given AttachmentType, setting the value if it doesn't exist.
entity.getAttachedOrSet(EXAMPLE_STRING_ATTACHMENT, "basic");
entity.getAttachedOrSet(EXAMPLE_BLOCK_POS_ATTACHMENT, new BlockPos(0, 0, 0););

// Gets the data associated with the given AttachmentType, returning the provided value if it doesn't exist.
entity.getAttachedOrElse(EXAMPLE_STRING_ATTACHMENT, "basic");
entity.getAttachedOrElse(EXAMPLE_BLOCK_POS_ATTACHMENT, new BlockPos(0, 0, 0););
```

## Writing To a Data Attachment {#writing-attachments}

Methods to write to a Data Attachment have been injected onto the `Entity`, `BlockEntity`, `ServerLevel` and `ChunkAccess` classes. Calling one of the following methods will update the value of the attached data, and return the previous value (or `null` if there wasn't one).

```java
// Sets the data associated with the given AttachmentType, returning the previous value.
entity.setAttached(EXAMPLE_STRING_ATTACHMENT, "new value");

// Modifies the data associated with the given AttachmentType in place, returning the currently attached value. Note that currentValue is null if there is no previously attached data.
entity.modifyAttached(EXAMPLE_STRING_ATTACHMENT, currentValue -> "The length was " + (currentValue == null ? 0 : currentValue.length()));

// Removes the data associated with the given AttachmentType, returning the previous value.
entity.removeAttached(EXAMPLE_STRING_ATTACHMENT);
```

:::warning
You should always use immutable types for attachment data, and you should also update attachment data with API methods only. Doing otherwise may cause attachment data to not persist or sync properly.
:::

## Larger Attachments {#larger-attachments}

Although data attachments could store any form of data for which a Codec can be written, they shine when syncing individual values. This is because a data attachment is immutable: modifying part of its value (for example a single field of an object) means replacing it entirely, triggering a full sync to every client tracking it.

Instead, you could achieve more intricate attachments by splitting them into multiple fields, and organizing them with a helper class. For example, if you need two fields related to a player's stamina, you may build something like this:

@[code lang=java transcludeWith=:::stamina](@/reference/latest/src/main/java/com/example/docs/attachment/Stamina.java)

This helper class can then be used like so:

```java
Player player = getPlayer();
Stamina.get(player).getCurrentStamina();
```
