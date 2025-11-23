---
title: Data Attachments
description: A guide covering basic usage of Fabric's new Data Attachment API
authors:
  - cassiancc
---

The Data Attachment API is a recent and experimental addition to Fabric API. It allows for developers to easily attach arbitrary data to Entities, Block Entities, Worlds, and Chunks. The attached data can be stored and synchronized through [Codecs](./codecs) and Stream Codecs, so users of this API will want to be familiar with those before using this API.

## Creating a Data Attachment {#creating-attachments}

You'll start with a call to `AttachmentRegistry.create`. The following example creates a basic data attachment that does not sync or persist across restarts.

```java
public static final AttachmentType<String> EXAMPLE_STRING_ATTACHMENT = AttachmentRegistry.create(
  ResourceLocation.fromNamespaceAndPath("example-mod", "example_string_attachment") // The ID of your attachment.
)
```

`AttachmentRegistry` also contains other methods for creating basic data attachments, including the following:

- `AttachmentRegistry.create()` - Creates a data attachment. Restarting the game will clear the attachment.
- `AttachmentRegistry.createPersistent()` - A helper method that creates a data attachment that will persist between game restarts.
- `AttachmentRegistry.createDefaulted()` - A helper method that creates a data attachment with a default value. Restarting the game will reset the attachment to its default value.

If you need a data attachment that is both persistent and synchronized, you can also set that behaviour using the `create` method. Examples of these can be seen below.

### Syncing a Data Attachment {#syncing-attachments}

Data attachments can also be synchronized, giving you a lot of flexibility when creating them. Consider the following example - this data attachment has an initial value and is synchronized between the server and every client.

```java
// Register a type of attached data. This data can be attached to anything, this is only a type
public static final AttachmentType<BlockPos> EXAMPLE_BLOCK_POS_ATTACHMENT = AttachmentRegistry.create(
  ResourceLocation.fromNamespaceAndPath("example-mod", "example_block_pos_attachment"),
  builder->builder // This example uses a builder chain to configure the attachment data type. Note that builder chains only work with `.create`!
    .initializer(()->new BlockPos(0, 0, 0)) // The default value of the attachment, if one has not been set.
    .syncWith(
      BlockPos.STREAM_CODEC,  // Dicates how to turn the data into a packet to send to players.
      AttachmentSyncPredicate.all() // Dictates who to send the data to.
    )
 );
```

#### Attachment Sync Predicates {#attachment-sync-predicates}

The example above was synced to every player, but that might not fit your use case. The default options include the following, but you can also build your own predicate by referencing the `AttachmentSyncPredicate` class.

- `AttachmentSyncPredicate.all()` - A predicate that syncs an attachment with all clients.
- `AttachmentSyncPredicate.targetOnly()` - A predicate that syncs an attachment only with the target it is attached to, when that is a player. If the target isn't a player, the attachment will be synced with no clients.
- `AttachmentSyncPredicate.allButTarget()` - A predicate that syncs an attachment with every client except the target it is attached to, when that is a player. When the target isn't a player, the attachment will be synced with all clients.

### Persisting Data Attachments {#persisting-attachments}

Data attachments can also be made to persist across game restarts with the `persistent` method and across the death of the entity it is attached to with the `copyOnDeath` method.

The `persistent` method takes a `Codec` so that the game knows how to serialize the data.

```java
// Register a type of attached data. This data can be attached to anything, this is only a type
public static final AttachmentType<BlockPos> EXAMPLE_BLOCK_POS_ATTACHMENT = AttachmentRegistry.create(
  ResourceLocation.fromNamespaceAndPath("example-mod", "example_block_pos_attachment"),
  builder->builder // This example uses a builder chain to configure the attachment data type.  Note that builder chains only work with `.create`! 
    .initializer(()->new BlockPos(0, 0, 0);) // The default value of the attachment, if one has not been set.
    .persistent(BlockPos.CODEC) // Dicates how this attachment's data should be saved and loaded.
    .copyOnDeath() // Dictates that this attachment should persist even after the entity dies.
 );
```

## Reading From a Data Attachment {#reading-attachments}

Methods to read from a Data Attachment have been injected onto the `Entity`, `BlockEntity`, and `ChunkAccess` classes. Using it is as simple as calling one of the methods, which return the value of the attached data.

```java

// Checks if the given AttachmentType has attached data, returning a boolean.
entity.hasAttached(EXAMPLE_STRING_ATTACHMENT);

// Gets the data associated with the given AttachmentType, or null if it doesn't yet exist.
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

Methods to write from a Data Attachment have been injected onto the `Entity`, `BlockEntity`, and `ChunkAccess` classes. Using it is as simple as calling one of the methods, which return the value of the attached data.

```java
// Sets the data associated with the given AttachmentType, returning the previous value.
entity.setAttached(EXAMPLE_STRING_ATTACHMENT, "new value");

// Removes the data associated with the given AttachmentType, returning the previous value.
entity.removeAttached(EXAMPLE_STRING_ATTACHMENT);
```
