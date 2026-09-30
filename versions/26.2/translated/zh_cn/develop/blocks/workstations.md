---
title: 工作站
description: 学习怎么去创建一个工作站。
authors:
  - cassiancc
  - ekulxam
  - skippyall
---

<!---->

:::info 前置知识

这个工作站使用了一种自定义配方类型，具体可以参考[自定义配方类型](../recipes/custom-recipe-types)。

:::

本教程将指导你如何创建自定义工作站。 与箱子不同，工作站不一定需要在UI关闭后保留其物品栏（例如工作台之类的方块不会保存其物品栏，但其他方块，如熔炉则会保存）。 出于演示目的，我们这里将不使用方块实体。

## 创建菜单 {#creating-a-menu}

::: info

有关创建菜单的更多详细信息，请参阅[容器菜单](./container-menus)。

:::

为了允许我们在图形界面（GUI）中创建配方，我们将创建一个带有菜单的方块。 要打开菜单，我们需要重写 `Block` 类中的一些方法：

<<< @/reference/26.2/src/main/java/com/example/docs/block/custom/UpgradingBlock.java#openmenu

之后，我们就可以开始创建菜单了。

<<< @/reference/26.2/src/main/java/com/example/docs/menu/custom/UpgradingMenu.java#menu

为了配合这个菜单，我们还需要一个自定义的输出结果槽位 `Slot`。

<<< @/reference/26.2/src/main/java/com/example/docs/menu/custom/UpgradingResultSlot.java#slot

这里的信息量很大！ 这个菜单包含两个输入槽位和一个输出槽位 `UpgradingResultSlot`。

输入容器是 `SimpleContainer` 的一个匿名子类，当其物品发生变化时，它会调用菜单的 `slotsChanged` 方法。 在 `slotsChanged` 中，我们创建一个配方输入类的实例，并用两个输入槽位填充它。

为了查看它是否匹配任何配方，我们首先要确保我们位于服务器级别，因为客户端不知道存在哪些配方。 然后，我们将通过 `serverLevel.recipeAccess()` 获取 `RecipeManager`。

:::details 补充说明：配方同步

> 如果客户端不知道存在哪些配方，那么配方书是如何工作的？

很高兴你问了这个问题。 服务器会根据你解锁了哪些配方来告知客户端存在哪些配方（解锁通过完成每个配方的进度 JSON 中描述的特定条件来实现，例如获得某个物品或进入水中（对于船而言））。 然而，对于配方查看类模组来说这相当令人头疼。它们理想情况下希望能看到所有可用的配方，但现在只能看到客户端从服务器获取到的配方。 为了绕过这一限制，我们可以[使用 Fabric API 来同步我们的配方](../recipes/custom-recipe-types#recipe-synchronization)。

:::

我们将调用 `serverLevel.recipeAccess().getRecipeFor` 并传入我们的配方输入，以获取与输入匹配的配方。 如果找到了配方，我们可以将结果添加到结果容器中或从中移除结果。

为了检测玩家何时取出了输出结果，我们重写了 `UpgradingResultSlot` 的 `onTake` 方法。 我们菜单的 `onTake` 方法随后会减少输入物品的数量。

为了确保玩家处于与方块互动的有效范围内，我们重写了 `stillValid`。

::: warning

请确保传递给 `stillValid` 作为参数的 `Block` 就是打开该菜单的方块！ 如果没有这样做，菜单和屏幕可能会在打开后立即自行关闭。

:::

最后，为了防止物品被吞掉，如 `removed` 方法中所示，在屏幕关闭时将输入的物品丢落回世界是非常重要的。

::: info

你可能已经注意到，有多个方法中包含了 `ContainerLevelAccess#execute` 调用。 这是 Mojang 使用的一个包装类，用于确保在发生交互时使用的是正确的 `Level` 和位置，并防止玩家访问他们不应该访问的容器。 注意，当对特殊的 `NULL` `ContainerLevelAccess` 调用 `execute` 时，它不会执行任何操作。

:::

`Slot` 的 `mayPlace` 方法返回 `false`，这样玩家就无法将物品放入结果槽位；而 `isFake` 方法则告诉 `Screen` 其包含的物品堆（暂时）没有所有者。

你还需要将菜单添加到注册表中：

<<< @/reference/26.2/src/main/java/com/example/docs/menu/ModMenuTypes.java#upgrading_menu_registration

最后，我们需要注册我们的方块：

<<< @/reference/26.2/src/main/java/com/example/docs/block/ModBlockItemIds.java#workstation

<<< @/reference/26.2/src/main/java/com/example/docs/block/ModBlocks.java#workstation

### 实现 `quickMoveStack` {#implementing-quick-move-stack}

::: info

另请参阅：[容器菜单：创建菜单](./container-menus#creating-the-menu)

:::

在菜单中按住 Shift 键进行点击时，就会调用快速移动。

<<< @/reference/26.2/src/main/java/com/example/docs/menu/custom/SuperiorUpgradingMenu.java#quickMove

哇，又是好多代码。 让我们来试着梳理一下这里发生了什么。

通常，当从物品栏区域快速移动物品堆时，菜单首先会检查被点击的槽位是否是结果槽位（索引为 0）。 如果是，菜单会尝试将结果物品堆移动到物品栏中；如果移动失败，则什么也不会发生。

接下来，菜单会检查被点击的槽位是否属于物品栏。 如果是，菜单就会尝试将物品堆移动到输入槽位中。 如果移动失败，我们会尝试在物品栏内部移动该物品堆（在快捷栏中点击槽位会将其物品堆移动到物品栏的其他 27 个槽位中，反之亦然）。

如果被点击的槽位既不是结果槽位，也不在物品栏内，那么该槽位几乎可以确定是我们两个输入槽位中的一个，因此我们需要将其物品堆移回物品栏中。

### 屏幕{#screen}

::: info

可另见[容器菜单](./container-menus#creating-the-screen)

:::

目前，我们可以先借用原版铁砧的背景纹理。

<<< @/reference/26.2/src/client/java/com/example/docs/rendering/screens/inventory/UpgradingScreen.java#screen

别忘了在你的 `ClientModInitializer` 中将菜单类型绑定到屏幕，如下所示：

<<< @/reference/26.2/src/client/java/com/example/docs/ExampleModRecipesClient.java#register_with_menu

## 配方余料 {#recipe-remainders}

想要制作支持余料的配方吗？ 我们建议你看看 `net.minecraft.world.inventory.ResultSlot#getRemainingItems`。 工作台使用此方法作为其结果槽位，因此可以找到许多与本文档相似之处，但也有一些差异。
