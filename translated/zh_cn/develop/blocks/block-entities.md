---
title: 方块实体
description: 学习如何为你的自定义方块创建方块实体。
authors:
  - natri0
---

方块实体是一种为方块存储额外数据的方式，这些数据不属于方块状态的一部分：例如物品栏内容、自定义名称等。
Minecraft 为箱子、熔炉和命令方块等方块使用了方块实体。

作为示例，我们将创建一个能够记录被右键点击次数的方块。

## 创建方块实体 {#creating-the-block-entity}

为了让 Minecraft 识别并加载新的方块实体，我们需要创建一个方块实体类型。 这通过继承 `BlockEntity` 类并在一个新的 `ModBlockEntities` 类中注册它来完成。

@[code transcludeWith=:::1](@/reference/latest/src/main/java/com/example/docs/block/entity/custom/CounterBlockEntity.java)

注册一个 `BlockEntity` 会得到一个 `BlockEntityType`，就像我们上面使用的 `COUNTER_BLOCK_ENTITY`：

@[code transcludeWith=:::1](@/reference/latest/src/main/java/com/example/docs/block/entity/ModBlockEntities.java)

::: tip

注意，`CounterBlockEntity` 的构造函数接收两个参数，而 `BlockEntity` 的构造函数需要三个参数：`BlockEntityType`、`BlockPos` 和 `BlockState`。
如果我们没有硬编码 `BlockEntityType`，`ModBlockEntities` 类将无法通过编译！ 这是因为 `BlockEntityFactory` 是一个函数式接口，描述了一个仅接收两个参数的函数，这与我们的构造函数完全一致。

:::

## 创建方块 {#creating-the-block}

接下来，为了实际使用方块实体，我们需要一个实现了 `EntityBlock` 接口的方块。 我们创建一个，并将其命名为 `CounterBlock`。

::: tip

于此有两种实现方法：

- 创建一个继承 `BaseEntityBlock` 的方块，并实现 `createBlockEntity` 方法
- 创建一个独立实现 `EntityBlock` 接口的方块，并重写其 `createBlockEntity` 方法

在本示例中，我们将采用第一种方法，因为 `BaseEntityBlock` 还提供了一些实用的工具。

:::

@[code transcludeWith=:::1](@/reference/latest/src/main/java/com/example/docs/block/custom/CounterBlock.java)

使用 `BaseEntityBlock` 作为父类意味着我们还需要实现 `createCodec` 方法，这相当简单。

与作为单例的方块不同，每个方块实例都会创建一个新的方块实体。 这是通过 `createBlockEntity` 方法完成的，它接受位置和 `BlockState`，并返回一个 `BlockEntity`，或者如果没有则返回 `null`。

别忘了在 `ModBlocks` 类中注册方块，就像在[创建你的第一个方块](../blocks/first-block)指南中那样：

@[code transcludeWith=:::5](@/reference/latest/src/main/java/com/example/docs/block/ModBlocks.java)

## 使用方块实体 {#using-the-block-entity}

现在我们有了一个方块实体，我们可以用它来存储方块被右键点击的次数。 我们将通过向 `CounterBlockEntity` 类添加一个 `clicks` 字段来实现：

@[code transcludeWith=:::2](@/reference/latest/src/main/java/com/example/docs/block/entity/custom/CounterBlockEntity.java)

在 `incrementClicks` 中使用的 `setChanged` 方法告诉游戏这个实体的数据已经更新；当我们添加序列化计数器并从保存文件加载回来的方法时，这将非常有用。

接下来，我们需要在每次方块被右键点击时递增这个字段。 这是通过重写 `CounterBlock` 类中的 `useWithoutItem` 方法来实现的：

@[code transcludeWith=:::2](@/reference/latest/src/main/java/com/example/docs/block/custom/CounterBlock.java)

由于 `BlockEntity` 没有作为参数传入方法，我们使用 `level.getBlockEntity(pos)`，如果 `BlockEntity` 无效，则从方法返回。

![右键点击后屏幕上显示“你第6次点击了方块”的消息](/assets/develop/blocks/block_entities_1.png)

## 保存和加载数据 {#saving-loading}

现在我们有了一个功能性的方块，我们应该让计数器在游戏重启之间不会重置。 这是通过在游戏保存时将其序列化为 NBT，并在加载时反序列化来实现的。

保存到 NBT 是通过 `ValueInput` 和 `ValueOutput` 完成的。 这些视图负责存储编码/解码过程中的错误，并在整个序列化过程中跟踪注册表。

你可以使用 `read` 方法从 `ValueInput` 中读取，传入所需类型的 `Codec`。 同样，你可以使用 `store` 方法写入 `ValueOutput`，传入类型的 Codec 和值。

还有一些用于基本类型的方法，例如用于读取的 `getInt`、`getShort`、`getBoolean` 等，以及用于写入的 `putInt`、`putShort`、`putBoolean` 等。 视图还提供了处理列表、可空类型和嵌套对象的方法。

序列化是通过 `saveAdditional` 方法完成的：

@[code transcludeWith=:::3](@/reference/latest/src/main/java/com/example/docs/block/entity/custom/CounterBlockEntity.java)

在这里，我们将应该保存的字段添加到传入的 `ValueOutput` 中：对于计数器方块，就是 `clicks` 字段。

读取是类似的，你从 `ValueInput` 中获取之前保存的值，并将它们保存在 BlockEntity 的字段中：

@[code transcludeWith=:::4](@/reference/latest/src/main/java/com/example/docs/block/entity/custom/CounterBlockEntity.java)

现在，如果我们保存并重新加载游戏，计数器方块应该会从保存时的位置继续计数。

虽然 `saveAdditional` 和 `loadAdditional` 负责保存到磁盘和从磁盘加载，但仍然存在一个问题：

- 服务器知道正确的 `clicks` 值。
- 客户端在加载区块时没有接收到正确的值。

为了解决这个问题，我们重写 `getUpdateTag`：

@[code transcludeWith=:::7](@/reference/latest/src/main/java/com/example/docs/block/entity/custom/CounterBlockEntity.java)

现在，当玩家登录或移动到一个存在该方块的区块时，他们将立即看到正确的计数器值。

## 刻处理器 {#tickers}

`EntityBlock` 接口还定义了一个名为 `getTicker` 的方法，可用于为方块的每个实例在每个游戏刻运行代码。 我们可以通过创建一个静态方法来实现，该方法将用作 `BlockEntityTicker`：

`getTicker` 方法还应该检查传入的 `BlockEntityType` 是否与我们正在使用的相同，如果是，则返回将在每个刻调用的函数。 幸运的是，`BaseEntityBlock` 中有一个实用函数可以执行此检查：

@[code transcludeWith=:::3](@/reference/latest/src/main/java/com/example/docs/block/custom/CounterBlock.java)

`CounterBlockEntity::tick` 是对我们应该在 `CounterBlockEntity` 类中创建的静态方法 `tick` 的引用。 像这样组织代码不是必需的，但这是保持代码整洁有序的良好实践。

假设我们希望计数器每 10 个游戏刻（每秒 2 次）只能递增一次。 我们可以通过向 `CounterBlockEntity` 类添加一个 `ticksSinceLast` 字段，并在每个刻递增它来实现：

@[code transcludeWith=:::5](@/reference/latest/src/main/java/com/example/docs/block/entity/custom/CounterBlockEntity.java)

别忘了序列化和反序列化这个字段！

现在我们可以使用 `ticksSinceLast` 来检查在 `incrementClicks` 中是否可以增加计数器：

@[code transcludeWith=:::6](@/reference/latest/src/main/java/com/example/docs/block/entity/custom/CounterBlockEntity.java)

::: tip

如果方块实体似乎没有执行刻处理，请尝试检查注册代码！ 它应该将对此实体有效的方块传递给 `BlockEntityType.Builder`，否则会在控制台中给出警告：

```log
[13:27:55] [Server thread/WARN] (Minecraft) Block entity example-mod:counter @ BlockPos{x=-29, y=125, z=18} state Block{example-mod:counter_block} invalid for ticking:
```

:::

<!---->
