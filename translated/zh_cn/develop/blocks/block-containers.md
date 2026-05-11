---
title: 方块容器
description: 学习如何向方块实体添加容器。
authors:
  - natri0
resources:
  https://docs.neoforged.net/docs/inventories/container/: Containers - NeoForge 文档
---

创建用于存储物品的方块（例如箱子和熔炉）时，最好实现容器 `Container` 接口。 这样就可以使用漏斗等与方块进行交互。

在本教程中，我们将创建一个方块，利用其容器复制放置在其中的任何物品。

## 创建方块 {#creating-the-block}

如果读者已阅读过[创建你的第一个方块](../blocks/first-block)和[方块实体](../blocks/block-entities)指南，那么这部分内容应该比较熟悉。 我们将会创建一个继承了 'BaseEntityBlock' 和实现了 'EntityBlock' 的“复制方块”。

<<< @/reference/latest/src/main/java/com/example/docs/block/custom/DuplicatorBlock.java#block

然后，我们需要创建一个 `DuplicatorBlockEntity`，它需要实现 `Container` 接口。 大多数的容器都以相同的模式工作。你可以复制和黏贴叫做 'ImplementedContainer' 的辅助类可以帮助你了解更多。我们这里只列举几项重要的实现方法。

:::details 显示 `ImplementedContainer`

<<< @/reference/latest/src/main/java/com/example/docs/container/ImplementedContainer.java

:::

<<< @/reference/latest/src/main/java/com/example/docs/block/entity/custom/DuplicatorBlockEntity.java#be

`items` 列表用于存储容器的内容。 对于这个方块，我们将其输入槽的大小设置为 1。

别忘了在各自的类中注册方块和方块实体！

### 保存和加载 {#saving-loading}

如果我们希望游戏内容像原版 `BlockEntity` 一样在游戏重新加载后仍然存在，我们需要将其保存为 NBT。 幸运的是，Mojang 提供了一个名为 `ContainerHelper` 的辅助类，其中包含了所有必要的逻辑。

<<< @/reference/latest/src/main/java/com/example/docs/block/entity/custom/DuplicatorBlockEntity.java#save

## 与容器交互 {#interacting-with-the-container}

从技术上讲，容器已经可以正常工作了。 但是，目前我们需要使用漏斗来放入物品。 让我们把它改成可以通过右键点击方块来放入物品。

为此，我们需要重写 `DuplicatorBlock` 中的 `useItemOn` 方法：

<<< @/reference/latest/src/main/java/com/example/docs/block/custom/DuplicatorBlock.java#useon

这里，如果玩家持有物品并且有空槽位，我们将物品从玩家手中移动到方块的容器中，并返回 `InteractionResult.SUCCESS`。

现在，当你右键点击带有物品的方块时，你将不再拥有该物品！ 如果你对该方块运行 `/data get block` 命令，你会在 NBT 的 `Items` 字段中看到物品。

![复制器方块和 /data get block 的输出，显示容器中的物品](/assets/develop/blocks/container_1.png)

### 复制物品 {#duplicating-items}

现在我们来修改一下，让这个方块复制你扔进去的物品堆，但每次只复制两个。 而且每次复制后都要等一秒钟，以免物品刷爆！

为此，我们将向 `DuplicatorBlockEntity` 添加一个 `tick` 函数，以及一个用于存储等待时间的字段：

<<< @/reference/latest/src/main/java/com/example/docs/block/entity/custom/DuplicatorBlockEntity.java#tick

`DuplicatorBlock` 现在应该有一个 `getTicker` 方法，该方法返回对 `DuplicatorBlockEntity::tick` 的引用。

<VideoPlayer src="/assets/develop/blocks/container_2.mp4">复制器方块复制橡木原木</VideoPlayer>

## 世界容器 {#worldly-containers}

默认情况下，你可以从容器的任意一侧放入和取出物品。 但是，有时这可能并非所需行为：例如，熔炉只能从侧面接收燃料，从顶部接收物品。

为了实现这种行为，我们需要在 `BlockEntity` 中实现 `WorldlyContainer` 接口。 该接口包含三个方法：

- `getSlotsForFace(Direction)` 允许你控制可以从给定侧与哪些槽位进行交互。
- `canPlaceItemThroughFace(int, ItemStack, Direction)` 允许你控制是否可以从给定的一侧将物品输入到槽位中。
- `canTakeItemThroughFace(int, ItemStack, Direction)` 允许你控制是否可以从给定侧的槽位中取出物品。

让我们修改 `DuplicatorBlockEntity`，使其只接受来自顶部的物品：

<<< @/reference/latest/src/main/java/com/example/docs/block/entity/custom/DuplicatorBlockEntity.java#accept

`getSlotsForFace` 返回一个数组，其中包含可以从给定侧进行交互的槽位 _索引_。 在本例中，我们只有一个槽位 (`0`)，因此我们返回一个仅包含该索引的数组。

此外，我们应该修改 `DuplicatorBlock` 的 `useItemOn` 方法，使其真正遵循新的行为：

<<< @/reference/latest/src/main/java/com/example/docs/block/custom/DuplicatorBlock.java#place

现在，如果我们尝试从侧面而不是顶部输入物品，那就行不通了！

<VideoPlayer src="/assets/develop/blocks/container_3.webm">复制器只有在与其顶部交互时才会激活</VideoPlayer>

## 菜单 {#menus}

要像使用箱子一样通过菜单访问新的容器方块，请参阅[容器菜单](./container-menus)指南。
