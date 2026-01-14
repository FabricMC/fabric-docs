---
title: Data Attachments
description: A guide covering basic usage of Fabric's new Data Attachment API.
authors:
  - cassiancc
  - DennisOchulor
---

The Data Attachment API is a recent and experimental addition to Fabric API. It allows developers to easily attach arbitrary data to Entities, Block Entities, Levels, and Chunks. The attached data can be stored and synced through [Codecs](./codecs) and Stream Codecs, so you should familiarize yourself with those before using it.

## Creating a Data Attachment {#creating-attachments}

You'll start with a call to `AttachmentRegistry.create`. The following example creates a basic Data Attachment that does not sync or persist across restarts.

@[code lang=java transcludeWith=:::string](@/reference/latest/src/main/java/com/example/docs/attachment/ExampleModAttachments.java)

`AttachmentRegistry` contains a few methods for creating basic Data Attachments, including:

- `AttachmentRegistry.create()`: Creates a Data Attachment. Restarting the game will clear the Attachment.
- `AttachmentRegistry.createPersistent()`: Creates a Data Attachment that will persist between game restarts.
- `AttachmentRegistry.createDefaulted()`: Creates a Data Attachment with a default value, which you can read with `getAttachedOrCreate`. Restarting the game will clear the Attachment.

The behavior of each method can also be replicated and further customized with the `builder` parameter of `create`, by applying the [method chaining pattern](https://en.wikipedia.org/wiki/Method_chaining).

### Syncing a Data Attachment {#syncing-attachments}

If you need a Data Attachment to both be persistent and synced between server and clients, you can set that behavior using the `create` method, which allows configuration through a `builder` chain. For example:

@[code lang=java transcludeWith=:::pos](@/reference/latest/src/main/java/com/example/docs/attachment/ExampleModAttachments.java)

The example above synced to every player, but that might not fit your use case. Here are some other default predicates, but you can also build your own by referencing the `AttachmentSyncPredicate` class.

- `AttachmentSyncPredicate.all()`: Syncs the Attachment with all clients.
- `AttachmentSyncPredicate.targetOnly()`: Syncs the Attachment only with the target it is attached to. Note that the syncing can only happen if the target is a player.
- `AttachmentSyncPredicate.allButTarget()`: Syncs the Attachment with every client except the target it is attached to. Note that the exception can only apply if the target is a player.

### Persisting Data Attachments {#persisting-attachments}

Data Attachments can also be set to persist across game restarts by calling the `persistent` method on the builder chain. It takes in a `Codec` so that the game knows how to serialize the data.

They can be set to perdure even after the death or [conversion](https://minecraft.wiki/w/Mob_conversion) of the target with the `copyOnDeath` method.

@[code lang=java transcludeWith=:::persistent](@/reference/latest/src/main/java/com/example/docs/attachment/ExampleModAttachments.java)

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

::: warning

You should always use values with immutable types for Data Attachments, and you should also update them with API methods only. Doing otherwise may cause the Data Attachment to not persist or sync properly.

:::

## Larger Attachments {#larger-attachments}

Although Data Attachments could store any form of data for which a Codec can be written, they shine when syncing individual values. This is because a Data Attachment is immutable: modifying part of its value (for example a single field of an object) means replacing it entirely, triggering a full sync to every client tracking it.

Instead, you could achieve more intricate Attachments by splitting them into multiple fields, and organizing them with a helper class. For example, if you need two fields related to a player's stamina, you may build something like this:

@[code lang=java transcludeWith=:::stamina](@/reference/latest/src/main/java/com/example/docs/attachment/Stamina.java)

This helper class can then be used like so:

```java
Player player = getPlayer();
Stamina.get(player).getCurrentStamina();
```
