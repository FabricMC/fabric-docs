---
title: 食物物品
description: 学会如何给物品添加 FoodComponent 以让它可食用，并配置。
authors:
  - IMB11
---

# 食物物品{#food-items}

食物是生存 Minecraft 的核心方面，所以创建可食用的物品时，需要考虑食物的用途以及其他可食用物品。

除非是在制作有过于强的物品的模型，否则应该考虑：

- 你的可食用物品会添加或减少多少饥饿值。
- 会给予什么药水效果？
- 是在游戏早期还是末期可用的？

## 添加食物组件{#adding-the-food-component}

要为物品添加食物组件，可以先传递到 `Item.Settings` 实例：

```java
new Item.Settings().food(new FoodComponent.Builder().build())
```

现在，只要让物品可食用，没有别的。

`FoodComponent` 类有很多方法，允许你修改玩家吃你的物品时发生的事情：

| 方法                   | 描述                                                       |
| -------------------- | -------------------------------------------------------- |
| `hunger`             | 设置你的物品会补充的饥饿值的数量。                                        |
| `saturationModifier` | 设置你的物品会增加的饱和度的数量。                                        |
| `meat`               | 将你的物品描述为肉。 食肉动物，例如狼，将能够吃。                                |
| `alwaysEdible`       | 允许无论饥饿值均能吃你的物品。                                          |
| `snack`              | 将你的物品描述为零食。                                              |
| `statusEffect`       | 吃你的物品时添加状态效果。 通常传递到此方法的是一个状态效果实例和概率，其中概率是小数（`1f = 100%`） |

按照你的喜好修改了 builder 后，可以调用 `build()` 方法以获取 `FoodComponent`。

@[code transcludeWith=:::5](@/reference/latest/src/main/java/com/example/docs/item/ModItems.java)

与 [创建你的第一个物品](./first-item) 类似，该示例将使用上述的组件：

@[code transcludeWith=:::poisonous_apple](@/reference/latest/src/main/java/com/example/docs/item/ModItems.java)

这会让物品：

- 总是可食用，无论饥饿值均可以吃。
- 是“零食”。
- 吃完会总会给予 6 秒中毒 II。

<VideoPlayer src="/assets/develop/items/food_0.webm" title="Eating the Suspicious Substance" />
