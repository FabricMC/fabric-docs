---
title: 自定义燃料
description: 了解如何创建你自己的燃料物品。
authors:
  - its-miroma
  - NotNightSky
---

燃料是在熔炉中用于冶炼矿石和烹饪食物的物品。让我们看看如何创建自定义燃料。

## 创建物品 {#creating-the-item}

让我们创建一个名为“夸克-胶子等离子体”的燃料物品。我们像往常一样开始[创建物品](../first-item)，然后对其应用 `CookingFuel` 组件，可以直接通过 `.component`，或者如果你引用的是 `ResourceKey<ContextIntProvider>`，则可以通过 `.cookingFuel` 辅助方法：

<<< @/reference/latest/src/main/java/com/example/docs/item/ModItemIds.java#quark_gluon_plasma_resource

<<< @/reference/latest/src/main/java/com/example/docs/item/ModItems.java#quark_gluon_plasma

或者，如果注册物品的不是我们，我们可以使用 Fabric Item API 中的 `DefaultItemComponentEvents.MODIFY` 事件：

<<< @/reference/latest/src/main/java/com/example/docs/item/ModItems.java#fuel_item

像往常一样，不要忘记添加纹理、翻译、创造模式标签页等等。这是一个纹理示例：

<DownloadEntry visualURL="/assets/develop/items/quark_gluon_plasma_big.png" downloadURL="/assets/develop/items/quark_gluon_plasma.png">纹理</DownloadEntry>

以下是在熔炉中将其用作燃料时的效果：

![使用夸克胶子等离子体作为燃料](/assets/develop/items/fuel_in_furnace.webm)
