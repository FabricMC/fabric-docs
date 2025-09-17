---
title: Data Attachments
description: A guide covering basic usage of Fabric's new Data Attachment API
authors:
  - cassiancc
---

The Data Attachment API is a recent and experimental addition to Fabric API. It allows for developers to easily attach arbitrary data to Entities and Block Entities. The attached data can be stored and synchronized through Codecs and Stream Codecs, so users of this API will want to be familiar with those before using this API.

## Creating a Data Attachment {#creating-attachments}

You'll start with a call to `AttachmentRegistry`, either to its `create`, `createPersistent`, or `createDefaulted` methods.

- `AttachmentRegistry.create()` - Creates a data attachment. Restarting the game will clear the attachment.
- `AttachmentRegistry.createPersistent()` - Creates a data attachment that will persist between game restarts.
- `AttachmentRegistry.createDefaulted()` - Creates a data attachment with a default value. Restarting the game will reset the attachment to its default value.

From there, you'll provide the method with the Identifier of your data attachment. If you have chosen a persistent attachment, you'll also provide the Codec for the data type you'll use. As an example, to create a persistent data attachment that stores a string, you'd use:

```java
public static final AttachmentType<Integer> EXAMPLE_ATTACHMENT = AttachmentRegistry.createPersistent(Identifier.of("fabric-docs-reference", "example_string_attachment"), Codec.STRING)
```

## Syncing a Data Attachment {#syncing-attachments}

Data attachments can also be synchronized, giving you a lot of flexibility when creating them. Consider the following example.

## Reading From a Data Attachment {#reading-attachments}

Methods to read from a Data Attachment have been injected onto the `Entity` and `BlockEntity` classes. Using it is as simple as calling one of the methods, which return the value of the attached data.

```java
// Gets the data associated with the given AttachmentType, or null if it doesn't yet exist.
entity.getAttached(EXAMPLE_ATTACHMENT)

// Gets the data associated with the given AttachmentType, throwing a `NullPointerException` if it doesn't exist.
entity.getAttachedOrThrow(EXAMPLE_ATTACHMENT)

// Gets the data associated with the given AttachmentType, setting the value if it doesn't exist.
entity.getAttachedOrSet(EXAMPLE_ATTACHMENT, "basic")

// Gets the data associated with the given AttachmentType, returning the provided value if it doesn't exist.
entity.getAttachedOrElse(EXAMPLE_ATTACHMENT, "basic")
```

## Writing From a Data Attachment {#writing-attachments}
