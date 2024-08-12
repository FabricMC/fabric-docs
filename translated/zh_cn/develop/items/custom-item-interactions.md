---
title: 自定义物品交互
description: 学习如何创建使用内置原版事件的物品
authors:
  - IMB11
---

# 自定义物品交互{#custom-item-interactions}

基础物品的功能是有限的——你还是需要个能在使用时与世界交互的物品。

有些关键的类你必须理解，然后才能看看原版的物品事件。

## 带类型的操作结果(TypedActionResult) {#typedactionresult}

对于物品来说，最常见的 `TypedActionResult` 是用于 `ItemStacks` 的——他负责告诉游戏在事件发生后是否需要替换物品堆叠(item stack)。

如果事件中没有发生任何变化，你应该使用 `TypedActionResult#pass(stack)` 方法，其中 `stack` 是当前的物品堆。

获取物品堆叠的一种方式是通过玩家的手。 需要返回 `TypedActionResult` 的事件往往会将手作为参数传递给事件方法。

```java
TypedActionResult.pass(user.getStackInHand(hand))
```

如果传递返回当前的物品堆叠, 那么无论将事件声明为什么——失败(failed)、通过(passed) 或忽略(ignored)、 成功(successful)，物品堆叠都不会发生变化。 _译者注：在源代码中并没有ignored的枚举值, 可能的情况是`PASS`被用来表示事件未处理，游戏将继续执行默认行为。此处注解可能不准确, 希望后来者指正_

当想要清空物品堆叠时，你应当传递一个空的。 同样的，当想要减少物品堆叠的数量时，你应当先获取当该品堆，并按数量减少他，就像下面那样：

```java
ItemStack heldStack = user.getStackInHand(hand);
heldStack.decrement(1);
TypedActionResult.success(heldStack);
```

## 操作结果(ActionResult) {#actionresult}

同样，`ActionResult` 告诉游戏事件的状态，无论是被忽略(PASS)、失败(FAIL) 还是成功(Success)。_译者注：`ActionResult`实际上是一个枚举类，而`TypedActionResult`包装了 这个类，不仅可以表示结果的状态，还可以携带附加的数据，比如 item stack_

## 可以被重写的事件(Overridable Events) {#overridable-events}

`Item` 类有许多方法可以被重写，从而为物品添加额外的功能。

:::info
[Playing SoundEvents](../sounds/using-sounds) 这里有一个比较好的例子, 他通过重写`useOnBlock` 实现了在玩家右击方块时播放声音的功能。
:::

| 方法              | 信息                                                 |
| --------------- | -------------------------------------------------- |
| `postHit`       | 当玩家攻击实体时被调用                                        |
| `postMine`      | 当玩家挖掘方块时被调用                                        |
| `inventoryTick` | 当物品在物品栏(inventory)中时，每一tick调用一次 |
| `onCraft`       | 当物品被合成时调用                                          |
| `useOnBlock`    | 当玩家手持物品右键方块时调用(确切的说是对着方块按下使用按键) |
| `use`           | 当玩家手持物品按下右键时调用(确切的说是按下使用按键)     |

## 以使用(use) 事件为例

假设你想制作一个在玩家面前召唤闪电的物品，这显然需要创建一个自定义的物品类。

@[code transcludeWith=:::1](@/reference/latest/src/main/java/com/example/docs/item/custom/LightningStick.java)

`use` 事件可能是最有用的事件之一：你可以利用这个事件来召唤闪电。下面的代码实现了在玩家面向的方向前10个方块的位置生成闪电。

@[code transcludeWith=:::2](@/reference/latest/src/main/java/com/example/docs/item/custom/LightningStick.java)

同样的，你需要注册物品，并添加模型和纹理。

然后，效果就是这样

<VideoPlayer src="/assets/develop/items/custom_items_0.webm" title="Using the Lightning Stick" />
