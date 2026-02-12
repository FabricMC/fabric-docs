---
title: 食物物品
description: 学会如何给物品添加 FoodComponent 以让它可食用，并配置。
authors:
  - IMB11
---

食物是生存 Minecraft 的核心方面，所以创建可食用的物品时，需要考虑食物的用途以及其他可食用物品。

除非是在制作有过于强的物品的模型，否则应该考虑：

- 你的可食用物品会添加或减少多少饥饿值。
- 会给予什么药水效果？
- 是在游戏早期还是末期可用的？

## 添加食物组件{#adding-the-food-component}

要为物品添加食物组件，可以先传递到 `Item.Properties` 实例：

```java
new Item.Properties().food(new FoodProperties.Builder().build())
```

现在，只要让物品可食用，没有别的。

`FoodProperties.Builder` 类有某些方法，允许你修改玩家吃你的物品时发生的事情：

| 方法                   | 描述                |
| -------------------- | ----------------- |
| `nutrition`          | 设置你的物品会补充的饥饿值的数量。 |
| `saturationModifier` | 设置你的物品会增加的饱和度的数量。 |
| `alwaysEdible`       | 允许无论饥饿值均能吃你的物品。   |

按照你的喜好修改了 builder 后，可以调用 `build()` 方法以获取 `FoodProperties`。

如果你想在玩家食用食物时添加状态效果，则需要添加一个 `Consumable` 组件以及 `FoodProperties` 组件，如下例所示：

@[code transcludeWith=:::5](@/reference/latest/src/main/java/com/example/docs/item/ModItems.java)

与 [创建你的第一个物品](./first-item) 类似，该示例将使用上述的组件：

@[code transcludeWith=:::poisonous_apple](@/reference/latest/src/main/java/com/example/docs/item/ModItems.java)

这会让物品：

- 总是可食用，无论饥饿值均可以吃。
- 吃完会总会给予 6 秒中毒 II。

<VideoPlayer src="/assets/develop/items/food_0.webm">吃 Poisonous Apple</VideoPlayer>
