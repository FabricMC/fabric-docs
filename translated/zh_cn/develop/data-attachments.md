---
title: 数据附加
description: 涵盖 Fabric 的新数据附加 API 的基本用法的指南。
authors:
  - cassiancc
  - DennisOchulor
---

数据附加 API 是 Fabric API 中一项近期推出的实验性功能， 允许开发者轻松地将任意数据附加到实体、方块实体、Level 和区块。 附加的数据可以通过 [Codec](./codecs) 和流 [Codec](./codecs) 进行存储和同步，因此在使用前你应该熟悉这些 Codec。

## 创建数据附加 {#creating-attachments}

首先，你需要调用 `AttachmentRegistry.create` 方法。 以下示例创建一个基本数据附加，不会在重启后同步或保留。

@[code lang=java transcludeWith=:::string](@/reference/latest/src/main/java/com/example/docs/attachment/ExampleModAttachments.java)

`AttachmentRegistry` 包含一些用于创建基本数据附加的方法，包括：

- `AttachmentRegistry.create()`：创建一个数据附加。 重启游戏会清除附加。
- `AttachmentRegistry.createPersistent()`：创建一个在游戏重启后仍然有效的数据附加。
- `AttachmentRegistry.createDefaulted()`：创建一个带有默认值的数据附加，可以使用 `getAttachedOrCreate` 读取该默认值。 重启游戏会清除附加。

使用用[方法链模式](https://en.wikipedia.org/wiki/Method_chaining)，使用 `create` 的 `builder` 参数来复制和进一步自定义每个方法的行为。

### 同步数据附加{#syncing-attachments}

如果需要数据附加在服务器和客户端之间既持久又同步，可以使用 `create` 方法设置此行为，允许通过 `builder` 链来配置。 例如：

@[code lang=java transcludeWith=:::pos](@/reference/latest/src/main/java/com/example/docs/attachment/ExampleModAttachments.java)

上面的例子会同步给每位玩家，但可能不适用于你的使用例。 这是一些其他默认的谓词，但你也可以通过引用 `AttachmentSyncPredicate` 类来构建自己的。

- `AttachmentSyncPredicate.all()`：将附加同步给所有客户端。
- `AttachmentSyncPredicate.targetOnly()`：仅将附加同步给附加到的目标。 注意只有当目标是玩家时，才会发生同步。
- `AttachmentSyncPredicate.allButTarget()`：将数据同步给所有客户端，除了被附加到的目标。 注意只有当目标是玩家时，才会有此例外。

### 持久数据附加{#persisting-attachments}

数据附加也可以在游戏重启时保持，在 builder 链调用 `persistent` 方法即可， 此方法接收一个 `Codec` 这样游戏知道如何序列化数据。

还可以设置为即使是目标死后或[转化](https://zh.minecraft.wiki/w/生物转化)后也维持，调用 `copyOnDeath` 方法即可。

@[code lang=java transcludeWith=:::persistent](@/reference/latest/src/main/java/com/example/docs/attachment/ExampleModAttachments.java)

## 从数据附加中读取{#reading-attachments}

用于从数据附加中读取的方法已被注入到 `Entity`、`BlockEntity`、`ServerLevel` 和 `ChunkAccess` 类。 就像调用其他常规方法一样，返回值是附加的数据。

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

## 写入数据附加{#writing-attachments}

用于向数据附加中写入的方法已被注入到 `Entity`、`BlockEntity`、`ServerLevel` 和 `ChunkAccess` 类。 调用以下方法之一会更新被附加的数据的值，返回此前的值（如果此前没有则为 `null`）。

```java
// Sets the data associated with the given AttachmentType, returning the previous value.
entity.setAttached(EXAMPLE_STRING_ATTACHMENT, "new value");

// Modifies the data associated with the given AttachmentType in place, returning the currently attached value. Note that currentValue is null if there is no previously attached data.
entity.modifyAttached(EXAMPLE_STRING_ATTACHMENT, currentValue -> "The length was " + (currentValue == null ? 0 : currentValue.length()));

// Removes the data associated with the given AttachmentType, returning the previous value.
entity.removeAttached(EXAMPLE_STRING_ATTACHMENT);
```

::: warning

数据附加始终都应使用不可变的类型，也仅应使用 API 的方法来更新， 否则可能会导致数据附加不能持续或恰当同步。

:::

## 更大的附加{#larger-attachments}

尽管数据附加可以以 codec 可写入的方式存储任何形式的数据，但只对同步的单个的数据时恰到好处， 这是因为数据附加是不可变的，修改其一部分值（例如对象的单个字段）意味着需要整个替换，并触发同步到每个追踪该数据附加的客户端。

不过，你可以将其拆分为多个字段，并使用一个辅助类来组织，实现复杂些的附加。 例如，如果需要两个有关玩家的体力值的字段，可以像这样：

@[code lang=java transcludeWith=:::stamina](@/reference/latest/src/main/java/com/example/docs/attachment/Stamina.java)

辅助类可以这样使用：

```java
Player player = getPlayer();
Stamina.get(player).getCurrentStamina();
```
