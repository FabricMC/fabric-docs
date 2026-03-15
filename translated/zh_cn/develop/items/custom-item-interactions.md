---
title: 自定义物品交互
description: 学习如何创建使用内置原版事件的物品
authors:
  - IMB11
---

基础物品的功能是有限的——你还是需要个能在使用时与世界交互的物品。

有些关键的类你必须理解，然后才能看看原版的物品事件。

## InteractionResult {#interactionresult}

`InteractionResult` 告诉游戏事件的状态，即事件是否被通过/忽略、失败或成功。

一次成功的交互也可以用来改变手中的堆叠。

```java
ItemStack heldStack = user.getStackInHand(hand);
heldStack.decrement(1);
InteractionResult.SUCCESS.heldItemTransformedTo().success(heldStack);
```

## 可重写事件 {#overridable-events}

`Item` 类有许多方法可以被重写，从而为物品添加额外的功能。

::: info

[播放声音](../sounds/using-sounds) 这里有一个比较好的例子，它通过重写 `useOn` 实现了在玩家右击方块时播放声音的功能。

:::

| 可重写的方法                 | 信息                                                 |
| ---------------------- | -------------------------------------------------- |
| `hurtEnemy`            | 当玩家攻击实体时被调用                                        |
| `mineBlock`            | 当玩家挖掘方块时被调用                                        |
| `inventoryTick`        | 当物品在物品栏中时，每刻运行一次。                                  |
| `onCraftedPostProcess` | 当物品被合成时调用                                          |
| `useOn`                | 当玩家手持物品右键方块时调用(确切的说是对着方块按下使用按键) |
| `use`                  | 当玩家手持物品按下右键时调用(确切的说是按下使用按键)     |

## `use()`（使用）事件{#use-event}

假设你想制作一个在玩家面前召唤闪电束的物品，这显然需要创建一个自定义的物品类。

@[code transcludeWith=:::1](@/reference/latest/src/main/java/com/example/docs/item/custom/LightningStick.java)

`use` 事件可能是最有用的事件之一：你可以利用这个事件来召唤闪电。下面的代码实现了在玩家面向的方向前 10 个方块的位置生成闪电。

@[code transcludeWith=:::2](@/reference/latest/src/main/java/com/example/docs/item/custom/LightningStick.java)

同样的，你需要注册物品，并添加模型和纹理。

正如你所见，闪电应该在你（玩家）面前 10 个方块远处产生。

<VideoPlayer src="/assets/develop/items/custom_items_0.webm">使用闪电棒</VideoPlayer>
