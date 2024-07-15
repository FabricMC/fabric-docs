---
title: 事件
description: Fabric API 提供的事件的使用指南。
authors:
  - dicedpixels
  - mkpoli
  - daomephsta
  - solidblock
  - draylar
  - jamieswhiteshirt
  - PhoenixVX
  - Juuxel
  - YanisBft
  - liach
  - natanfudge
authors-nogithub:
  - stormyfabric
---

# 事件{#events}

Fabric API 提供了一个系统，允许模组对行为或发生的事（也被定义为游戏中发生的_事件_）做出反应。

事件是一种 Hook ，可满足常见的使用情况，并且增强和优化多个模组间对于同一代码区间的 Hook 的兼容性。 使用事件往往就可以替代 mixin 的使用。

Fabric API 为 Minecraft 代码库中的重要区域提供事件，许多模组制作者可能有兴趣将其接入。

事件由 `net.fabricmc.fabric.api.event.Event` 实例表示，该实例存储并调用_回调（CallBack）_。 一个回调通常只有一个事件实例，存储在回调接口的静态字段 `EVENT` 中，但也有其他可能。 例如，`ClientTickEvents` 会为多个相关连的事件分组。

## 回调{#callbacks}

回调是作为参数传递给事件的一段代码。 当游戏触发事件时，将执行所传递的代码。

### 回调接口{#callback-interfaces}

每个事件都有一个对应的回调接口，通常命名为 `<EventName>Callback`。 回调是通过在事件实例上调用 `register()` 方法注册的，其参数为一个回调接口的实例。

Fabric API 提供的所有事件回调接口，可见 `net.fabricmc.fabric.api.event` 包。

## 监听事件{#listening-to-events}

这个例子注册一个 `AttackBlockCallback`，当玩家徒手击打不掉落物品的方块时，伤害玩家。

@[code lang=java transcludeWith=:::1](@/reference/latest/src/main/java/com/example/docs/event/FabricDocsReferenceEvents.java)

### 将物品添加到已存在的战利品表{#adding-items-to-existing-loot-tables}

有时，你可能需要向战利品表中添加物品。 例如，为原版方块或实体添加掉落物。

最简单的解决方案是替换战利品表文件，但这可能会破坏其他模组的兼容性： 如果他们也想修改呢？ 让我们来看看如何在不覆盖战利品表的情况下将物品添加到战利品表中。

我们将会把添加鸡蛋到煤炭矿石的战利品表里。

#### 监听战利品表加载{#listening-to-loot-table-loading}

Fabric API 有一个在加载战利品表时触发的事件，即 `LootTableEvents.MODIFY`。 你可以在模组初始化入口点中为它注册一个回调。 我们还要检查一下监听的战利品表是否是煤炭矿石战利品表。

@[code lang=java transclude={38-40}](@/reference/latest/src/main/java/com/example/docs/event/FabricDocsReferenceEvents.java)

#### 将物品添加到战利品表{#adding-items-to-the-loot-table}

在战利品表中，物品存储在_战利品池条目_，条目存储在_战利品池_。 要添加物品，我们需要在战利品表中添加一个带有物品条目的池。

我们可以使用 `LootPool#builder` 来创建一个战利品池，并将其添加到战利品表中。

我们的战利品池中没有任何项目，因此我们将使用 `ItemEntry#builder` 创建一个物品条目，并将其添加到战利品池中。

@[code highlight={6-7} transcludeWith=:::2](@/reference/latest/src/main/java/com/example/docs/event/FabricDocsReferenceEvents.java)

## 自定义事件{#custom-events}

游戏中某些区域并没有 Fabric API 提供的事件，因此需要使用 mixin 或者创建自己的事件。

我们将创建一个在羊剪毛时触发的事件。 创建事件的过程是：

- 创建事件回调接口
- 从 mixin 触发事件
- 创建测试实现

### 创建事件回调接口{#creating-the-event-callback-interface}

事件回调接口描述了您监听的事件必须实现哪些功能， 同时也描述了我们如何从 mixin 中调用这个事件。 通常在回调接口中创建一个 `Event` 字段作为我们实际事件的唯一标识符。

对于我们 `Event` 的实现，我们选择用数组来存储他们。 该数组将包含所有监听这个事件的监听器。

我们实现这个事件时，将依次调用这个数组中的所有监听器，直到某个监听器返回非 `ActionResult.PASS`。 这意味着一个监听器可以使用返回值表明“_取消_”、“_批准_”或者“_无所谓，交给下一个监听器处理_”。

使用 `ActionResult` 作为返回值是各个事件处理器之间的常规协作方式。

您将需要创建一个带有 `Event` 实例和响应实现方法的接口。 一个典型的剪羊毛回调像是这样：

@[code lang=java transcludeWith=:::](@/reference/latest/src/main/java/com/example/docs/event/SheepShearCallback.java)

让我们更深入地看看。 当 invoker 被调用时，我们将遍历所有监听器：

@[code lang=java transclude={21-22}](@/reference/latest/src/main/java/com/example/docs/event/SheepShearCallback.java)

然后在监听器上调用我们的方法（在本例中为 `interact`）来获取响应：

@[code lang=java transclude={33-33}](@/reference/latest/src/main/java/com/example/docs/event/SheepShearCallback.java)

如果监听器决定我们必须取消（`ActionResult.FAIL`）或者完成（`ActionResult.SUCCESS`）这个事件，回调将返回这个结果，并且结束循环。 `ActionResult.PASS` 将继续触发下一个监听器，并且在绝大多数没有注册多个监听器的情况下都应该返回 `ActionResult.SUCCESS`：

@[code lang=java transclude={25-30}](@/reference/latest/src/main/java/com/example/docs/event/SheepShearCallback.java)

我们可以在回调类的顶部添加 Javadoc 注释注明每一种 `ActionResult` 的作用。 在本例中，它应该是这样的：

@[code lang=java transclude={9-16}](@/reference/latest/src/main/java/com/example/docs/event/SheepShearCallback.java)

### 从 mixin 中触发事件{#triggering-the-event-from-a-mixin}

我们现在有基本的事件框架了，但还需要触发它。 我们希望在玩家试图剪羊毛时调用事件，所以我们要在当 `SheepEntity#interactMob` 中的`sheared()` 被调用时去调用事件的 `invoker`（例如：羊可以被剪毛且玩家手持剪刀）

@[code lang=java transcludeWith=:::](@/reference/latest/src/main/java/com/example/docs/mixin/event/SheepEntityMixin.java)

### 创建测试实现{#creating-a-test-implementation}

现在我们需要测试一下我们的事件。 你可以在初始化方法（如果需要，在其他区域也可以）中注册监听器，并在其中添加自定义逻辑。 这里有一个例子——羊的脚上掉落的不是羊毛，而是一颗钻石：

@[code lang=java transcludeWith=:::3](@/reference/latest/src/main/java/com/example/docs/event/FabricDocsReferenceEvents.java)

如果你进入游戏并剪羊毛，掉落的应该是钻石而不是羊毛。
