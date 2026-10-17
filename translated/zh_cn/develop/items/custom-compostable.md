---
title: 自定义可堆肥物
description: 了解如何创建你自己的可堆肥物品。
authors:
  - NotNightSky
  - its-miroma
---

可堆肥物品是指放入堆肥桶中后能够转换为骨粉的物品。让我们看看如何创建自定义可堆肥物。

## 创建物品 {#creating-the-item}

让我们创建一个名为“Bone Marrow”的堆肥物品。像往常一样，我们首先[创建一个物品](./first-item)：

<<< @/reference/latest/src/main/java/com/example/docs/item/ModItemIds.java#bone_marrow_resource

<<< @/reference/latest/src/main/java/com/example/docs/item/ModItems.java#bone_marrow

要使其能够堆肥，我们将把它添加到来自 Fabric 注册表 API 的 `CompostableRegistry.INSTANCE` 注册表中：

<<< @/reference/latest/src/main/java/com/example/docs/item/ModItems.java#compostable_item

像往常一样，不要忘记添加纹理、翻译、创造模式标签页等等。这是一个纹理示例：

<DownloadEntry visualURL="/assets/develop/items/bone_marrow_big.png" downloadURL="/assets/develop/items/bone_marrow.png">纹理</DownloadEntry>

以下是在堆肥桶中使用它时的效果：

<VideoPlayer src="/assets/develop/items/using_bone_marrow.webm">使用 Bone Marrow 作为堆肥物品</VideoPlayer>
