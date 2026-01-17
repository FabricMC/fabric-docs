---
title: 自定义物品交互
description: 学习如何创建使用内置原版事件的物品。
authors:
  - IMB11
---

基础物品的功能是有限的，因此，你仍然需要一个能够在使用时与世界交互的物品。

在了解原版物品事件之前，有几个关键的类需要首先熟悉。

## 带类型的操作结果 (TypedActionResult) {#typedactionresult}

对于物品来说，最常见的 `TypedActionResult` 是用于 `ItemStacks` 的。它负责告知游戏，在事件处理后是否应该替换当前的物品堆叠。

如果事件中没有对物品堆叠做任何修改，你应该使用 `TypedActionResult#pass(stack)` 方法，其中 `stack` 是当前的物品堆叠。

一种获取物品堆叠的方式是通过玩家手持物品的手。需要返回 `TypedActionResult` 的事件方法通常会将 `hand` 作为参数传入。

```java
TypedActionResult.pass(user.getStackInHand(hand))
```

如果传递返回当前的物品堆叠，那么无论将事件最终被声明为 失败(failed)、通过(passed)、忽略(ignored)、成功(successful)，物品堆叠都不会发生变化。
:::info
在源代码中并没有ignored的枚举值, 可能的情况是`PASS`被用来表示事件未处理，游戏将继续执行默认行为。此处注解可能不准确, 希望后来者指正。
:::

当想要清空物品堆叠时，你应当传递一个空堆叠。 同样，当想要减少物品堆叠的数量时，你应当先获取当前物品堆叠的数量，然后将其递减。具体示例如下：

```java
ItemStack heldStack = user.getStackInHand(hand);
heldStack.decrement(1);
TypedActionResult.success(heldStack);
```

## 操作结果 (ActionResult) {#actionresult}

`ActionResult` 也是一个用于告知游戏事件状态的类，其状态包括 忽略(PASS)、失败(FAIL) 与 成功(Success)。
:::info
译者注: `ActionResult` 实际上是一个枚举类，而 `TypedActionResult` 包装了这个类，不仅可以表示结果的状态，还可以携带附加的数据(比如物品堆叠)。
:::

## 可以被重写的事件 (Overridable Events) {#overridable-events}

`Item` 类提供了许多可以被重写的方法，以便为物品添加额外的功能。

:::info
[播放声音事件](../sounds/using-sounds) 这里有一个比较好的例子，它通过重写 `useOnBlock` 方法实现了在玩家右击方块时播放声音的功能。
:::

| 方法              | 信息                                                 |
| --------------- | -------------------------------------------------- |
| `postHit`       | 当玩家攻击实体时被调用                                        |
| `postMine`      | 当玩家挖掘方块时被调用                                        |
| `inventoryTick` | 当物品在物品栏(inventory)中时，每 tick 调用一次 |
| `onCraft`       | 当物品被合成时调用                                          |
| `useOnBlock`    | 当玩家手持物品右键方块时调用(确切的说是对着方块按下使用按键) |
| `use`           | 当玩家手持物品按下右键时调用(确切的说是按下使用按键)     |

## 以使用 (use) 事件为例{#use-event}

假设你想制作一个在玩家面前召唤闪电束的物品，要实现这个功能，你需要创建一个自定义的物品类。

@[code transcludeWith=:::1](@/reference/latest/src/main/java/com/example/docs/item/custom/LightningStick.java)

`use` 事件可能是最有用的事件之一。例如，你可以利用这个事件来召唤闪电。下面的代码实现了在玩家朝向方向前方 10 个方块的位置生成一道闪电。

@[code transcludeWith=:::2](@/reference/latest/src/main/java/com/example/docs/item/custom/LightningStick.java)

同样的，你需要注册物品，并为其添加模型和纹理。

正如你所见，闪电应该在你（玩家）面前 10 个方块远处生成。

<VideoPlayer src="/assets/develop/items/custom_items_0.webm">使用 Lightning Stick</VideoPlayer>
