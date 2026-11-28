---
title: 自定义可堆肥物
description: 了解如何创建你自己的可堆肥物品。
authors:
  - its-miroma
  - NotNightSky
---

可堆肥物品是指放入堆肥桶中后能够转换为骨粉的物品。让我们看看如何创建自定义可堆肥物。

## 创建物品 {#creating-the-item}

让我们创建一个名为“Bone Marrow”的堆肥物品。我们像往常一样开始[创建物品](../first-item)，然后对其应用 `Compostable` 组件，可以直接通过 `.component`，或者如果你引用的是 `ResourceKey<ContextIntProvider>`，则可以通过 `.compostable` 辅助方法：

<<< @/reference/latest/src/main/java/com/example/docs/item/ModItemIds.java#bone_marrow_resource

<<< @/reference/latest/src/main/java/com/example/docs/item/ModItems.java#bone_marrow

或者，如果注册物品的不是我们，我们可以使用 Fabric Item API 中的 `DefaultItemComponentEvents.MODIFY` 事件：

<<< @/reference/latest/src/main/java/com/example/docs/item/ModItems.java#compostable_item

像往常一样，不要忘记添加纹理、翻译、创造模式标签页等等。这是一个纹理示例：

<DownloadEntry visualURL="/assets/develop/items/bone_marrow_big.png" downloadURL="/assets/develop/items/bone_marrow.png">纹理</DownloadEntry>

以下是在堆肥桶中使用它时的效果：

![骨髓用作堆肥材料](/assets/develop/items/using_bone_marrow.webm)
