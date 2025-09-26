---
title: 方块实体
description: 学习如何为您的自定义方块创建方块实体。
authors:
  - natri0
---

方块实体是一种存储额外数据的方法，这些数据不是方块状态的一部分：库存内容、自定义名称等。
Minecraft 将方块实体用于箱子、熔炉和命令方块等方块。

例如，我们将创建一个方块，计算它被右键单击的次数。

## 创建方块实体 {#creating-the-block-entity}

为了让 Minecraft 识别并加载新的方块实体，我们需要创建一个方块实体类型。 这是通过扩展 'BlockEntity' 类并将其注册到一个新的 'ModBlockEntities' 类来完成的。 这是通过扩展 'BlockEntity' 类并将其注册到一个新的 'ModBlockEntities' 类来完成的。

@[code transcludeWith=:::1](@/reference/latest/src/main/java/com/example/docs/block/entity/custom/CounterBlockEntity.java)

注册一个 'BlockEntity' 会产生一个 'BlockEntityType'，就像我们上面使用的 'COUNTER_BLOCK_ENTITY' 一样：

@[code transcludeWith=:::1](@/reference/latest/src/main/java/com/example/docs/block/entity/ModBlockEntities.java)

:::tip
注意 'CounterBlockEntity' 的构造函数需要两个参数，而 'BlockEntity' 的构造函数需要三个参数：'BlockEntityType'、'BlockPos' 和 'BlockState'。
如果我们没有对 'BlockEntityType' 进行硬编码，那么 'ModBlockEntities' 类就不会编译！ 这是因为 'BlockEntityFactory' 是一个函数式接口，它描述了一个只接受两个参数的函数，就像我们的构造函数一样。
如果我们没有对 'BlockEntityType' 进行硬编码，那么 'ModBlockEntities' 类就不会编译！ 这是因为 'BlockEntityFactory' 是一个函数式接口，它描述了一个只接受两个参数的函数，就像我们的构造函数一样。
:::

## 创建方块{#creating-the-block}

接下来，要实际使用方块实体，我们需要一个实现 'BlockEntityProvider' 的方块。 让我们创建一个并将其命名为 'CounterBlock'。 让我们创建一个并将其命名为 'CounterBlock'。

:::tip
有两种方法来实现这个：

- 创建一个扩展 `BlockWithEntity` 的方块并实现 `createBlockEntity` 方法
- 创建一个自行实现 `BlockEntityProvider` 的方块并重写 `createBlockEntity` 方法

我们将在本例中使用第一种方法，因为 `BlockWithEntity` 也提供了一些不错的实用程序。
:::

@[code transcludeWith=:::1](@/reference/latest/src/main/java/com/example/docs/block/custom/CounterBlock.java)

使用 `BlockWithEntity` 作为父类意味着我们还需要实现 `createCodec` 方法，这相当容易。

与单例方块不同，每个方块实例都会创建一个新的方块实体。 与单例方块不同，每个方块实例都会创建一个新的方块实体。 这是通过 `createBlockEntity` 方法完成的，该方法采用位置和 `BlockState`，并返回 `BlockEntity`，如果不应该有则返回 `null`。

不要忘记在 `ModBlocks` 类中注册该方块，就像在[创建你的第一个方块](../blocks/first-block)指南中一样：

@[code transcludeWith=:::5](@/reference/latest/src/main/java/com/example/docs/block/ModBlocks.java)

## 使用方块实体 {#using-the-block-entity}

现在我们有了一个方块实体，我们可以用它来存储该方块被右键单击的次数。 现在我们有了一个方块实体，我们可以用它来存储该方块被右键单击的次数。 我们通过向 `CounterBlockEntity` 类添加 `clicks` 字段来实现这一点：

@[code transcludeWith=:::2](@/reference/latest/src/main/java/com/example/docs/block/entity/custom/CounterBlockEntity.java)

`incrementClicks` 中使用的 `markDirty` 方法告诉游戏该实体的数据已更新；当我们添加方法来序列化计数器并从保存文件加载回来时，这将很有用。

接下来，我们需要在每次右键单击方块时增加该字段。 这是通过重写 `CounterBlock` 类中的 `onUse` 方法来实现的： 这是通过重写 `CounterBlock` 类中的 `onUse` 方法来实现的：

@[code transcludeWith=:::2](@/reference/latest/src/main/java/com/example/docs/block/custom/CounterBlock.java)

由于没有将 `BlockEntity` 传递到方法中，我们使用 `world.getBlockEntity(pos)`，如果 `BlockEntity` 无效，则从方法返回。

![右键单击后屏幕上显示“You've clicked the block for the 6th time”消息](/assets/develop/blocks/block_entities_1.png)

## 保存和加载数据 {#saving-loading}

现在我们有了一个功能方块，我们应该使计数器在游戏重启时不会重置。 这是通过在游戏保存时将其序列化为 NBT，并在加载时反序列化来实现的。 这是通过在游戏保存时将其序列化为 NBT，并在加载时反序列化来实现的。

序列化是通过 `writeNbt` 方法完成的：

@[code transcludeWith=:::3](@/reference/latest/src/main/java/com/example/docs/block/entity/custom/CounterBlockEntity.java)

在这里，我们添加应该保存到传递的 `NbtCompound` 中的字段：在计数器方块的情况下，这就是 `clicks` 字段。

读取类似，但不是保存到 `NbtCompound`，而是获取之前保存的值，并将它们保存在 BlockEntity 的字段中：

@[code transcludeWith=:::4](@/reference/latest/src/main/java/com/example/docs/block/entity/custom/CounterBlockEntity.java)

现在，如果我们保存并重新加载游戏，计数器方块应该从保存时停止的地方继续。

## Ticker {#tickers}

`BlockEntityProvider` 接口还定义了一个名为 `getTicker` 的方法，该方法可用于为方块的每个实例每次运行代码。 我们可以通过创建一个用作 `BlockEntityTicker` 的静态方法来实现这一点： 我们可以通过创建一个用作 `BlockEntityTicker` 的静态方法来实现这一点：

`getTicker` 方法还应检查传递的 `BlockEntityType` 是否与我们正在使用的相同，如果相同，则返回每刻时调用的函数。 值得庆幸的是，有一个实用函数可以在 `BlockWithEntity` 中执行检查： 值得庆幸的是，有一个实用函数可以在 `BlockWithEntity` 中执行检查：

@[code transcludeWith=:::3](@/reference/latest/src/main/java/com/example/docs/block/custom/CounterBlock.java)

`CounterBlockEntity::tick` 是我们应该在 `CounterBlockEntity` 类中创建的静态方法 `tick` 的引用。 像这样构建它不是必需，但它是一种保持代码干净和有序的很好的做法。 像这样构建它不是必需，但它是一种保持代码干净和有序的很好的做法。

假设我们想让计数器每 10 刻（每秒 2 次）只能增加一次。 假设我们想让计数器每 10 刻（每秒 2 次）只能增加一次。 我们可以通过向 `CounterBlockEntity` 类添加 `ticksSinceLast` 字段并在每刻时增加它来实现这一点：

@[code transcludeWith=:::5](@/reference/latest/src/main/java/com/example/docs/block/entity/custom/CounterBlockEntity.java)

别忘了序列化和反序列化这个字段！

现在我们可以使用 `ticksSinceLast` 来检查计数器是否可以在 `incrementClicks` 中增加：

@[code transcludeWith=:::6](@/reference/latest/src/main/java/com/example/docs/block/entity/custom/CounterBlockEntity.java)

:::tip
如果区块实体似乎没有打钩，请尝试检查关于注册到上下文的代码！ 如果区块实体似乎没有打钩，请尝试检查关于注册到上下文的代码！ 它应将对该实体有效的块传入到 `BlockEntityType.Builder` 中，否则会在控制台中发出警告：

```text
[13:27:55] [Server thread/WARN] (Minecraft) Block entity example-mod:counter @ BlockPos{x=-29, y=125, z=18} state Block{example-mod:counter_block} invalid for ticking:
```

:::
