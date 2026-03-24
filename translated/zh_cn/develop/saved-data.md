---
title: 保存数据
description: 在游戏会话之间保存数据。
authors:
  - dicedpixels
---

保存数据是 Minecraft 自带的数据持久化方案，用于在不同游戏会话之间保存数据。

数据会保存到硬盘上，游戏关闭后再打开时会重新加载。 这些数据通常有作用域限制（比如某个世界）。 数据以 [NBT](https://zh.minecraft.wiki/w/NBT%E6%A0%BC%E5%BC%8F) 格式写入磁盘，通过 [Codec](./codecs) 进行序列化和反序列化。

让我们来看一个简单的场景，我们需要保存玩家破坏的方块数量。 我们可以在逻辑服务器端保存这个计数。

我们可以使用 `PlayerBlockBreakEvents.AFTER` 事件配合一个简单的静态整数字段来存储这个值，并在聊天栏显示出来。

```java
private static int blocksBroken = 0; // keeps track of the number of blocks broken

PlayerBlockBreakEvents.AFTER.register((level, player, pos, state, blockEntity) -> {
    blocksBroken++; // increment the counter each time a block is broken
    player.displayClientMessage(Component.literal("Blocks broken: " + blocksBroken), false);
});
```

现在，当你破坏一个方块时，你会看到一条显示计数的消息。

![方块破坏消息](/assets/develop/saved-data/block-breaking.png)

如果你重启 Minecraft，再次加载世界并开始破坏方块，你会发现这个计数被重置了。 这就是我们需要保存数据的地方。 我们可以存储这个计数，这样下次加载世界时，我们可以获取已保存的计数并从那个点开始继续递增。

## 储存数据 {#saving-data}

`SavedData` 是负责管理数据保存/加载的主类。 由于它是抽象类，你需要提供具体实现。

### 设置数据类 {#setting-up-a-data-class}

我们把数据类命名为 `SavedBlockData`，让它继承 `SavedData`。

这个类需要包含一个字段来记录破坏的方块数量，以及一个获取和一个递增这个数字的方法。

@[code lang=java transcludeWith=:::basic_structure](@/reference/latest/src/main/java/com/example/docs/saveddata/SavedBlockData.java)

为了序列化和反序列化这些数据，我们需要定义一个 Codec。 我们可以使用 Minecraft 提供的各种基础 Codec 来组合构建一个 Codec。

你需要一个带 `int` 参数的构造方法来初始化这个类。

@[code lang=java transcludeWith=:::ctor](@/reference/latest/src/main/java/com/example/docs/saveddata/SavedBlockData.java)

然后我们可以构建一个 Codec。

@[code lang=java transcludeWith=:::codec](@/reference/latest/src/main/java/com/example/docs/saveddata/SavedBlockData.java)

我们应该在数据实际修改时调用 `setDirty()`，以便 Minecraft 知道应该将其保存到磁盘。

@[code lang=java transcludeWith=:::set_dirty](@/reference/latest/src/main/java/com/example/docs/saveddata/SavedBlockData.java)

最后，我们需要有一个 `SavedDataType` 来描述我们的保存数据。 第一个参数对应将在世界的 `data` 目录中创建的文件名。

@[code lang=java transcludeWith=:::type](@/reference/latest/src/main/java/com/example/docs/saveddata/SavedBlockData.java)

### 访问保存的数据 {#accessing-saved-data}

如前所述，保存数据可以关联到一个作用域，比如当前世界。 在这种情况下，我们的数据将是世界数据的一部分。 我们可以获取世界的 `DimensionDataStorage` 来添加和修改我们的数据。

我们把这个逻辑放到一个工具方法中。

@[code lang=java transcludeWith=:::method](@/reference/latest/src/main/java/com/example/docs/saveddata/SavedBlockData.java)

### 使用保存数据 {#using-saved-data}

现在我们已经设置好了一切，让我们来保存一些数据吧。

我们可以重用第一个场景，让它不再递增字段，而是调用我们 `SavedBlockData` 中的 `incrementBlocksBroken` 方法。

@[code lang=java transcludeWith=:::event_registration](@/reference/latest/src/main/java/com/example/docs/saveddata/ExampleModSavedData.java)

这应该会递增这个值并将其保存到硬盘。

如果你重启 Minecraft，加载世界并破坏一个方块，你会看到之前保存的计数现在被递增了。

如果你查看世界的 `data` 文件夹，你会看到一个名为 `saved_block_data.dat` 的 `.dat` 文件。
用 NBT 浏览器打开这个文件，它会显示我们的数据是如何保存的。

![NBTg](/assets/develop/saved-data/nbt.png)
