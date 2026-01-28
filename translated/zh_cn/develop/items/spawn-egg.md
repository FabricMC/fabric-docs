---
title: 生成蛋
description: 学习如何注册生成蛋物品。
authors:
  - PEQB1145
---

::: info 先决条件
你必须首先了解[如何创建一个物品](./first-item)，然后才能将其转变为生成蛋。
:::

生成蛋是一种特殊物品，使用时会生成对应的生物。你可以在你的[物品类](./first-item#preparing-your-items-class)中通过 `register` 方法，并传入 `SpawnEggItem::new` 参数来注册它。

@[code transcludeWith=:::spawn_egg](@/reference/latest/src/main/java/com/example/docs/item/ModItems.java)

![没有纹理的生成蛋物品](/assets/develop/items/spawn_egg_1.png)

在它完全可用之前，仍有几件事要做：你必须添加一个纹理、一个物品模型、一个客户端物品、一个名称，并将生成蛋添加到合适的创造模式分组中。

## 添加纹理 {#adding-a-texture}

在 `assets/example-mod/textures/item` 目录中创建 16x16 的物品纹理，文件名与物品的 id 保持一致：`custom_spawn_egg.png`。下面提供了一个示例纹理。

<DownloadEntry visualURL="/assets/develop/items/spawn_egg.png" downloadURL="/assets/develop/items/spawn_egg_small.png">纹理</DownloadEntry>

## 添加模型 {#adding-a-model}

在 `assets/example-mod/models/item` 目录中创建物品模型，文件名与物品的 id 保持一致：`custom_spawn_egg.json`。

@[code](@/reference/latest/src/main/generated/assets/example-mod/models/item/custom_spawn_egg.json)

## 创建客户端物品 {#creating-the-client-item}

在 `assets/example-mod/items` 目录中创建客户端物品 JSON 文件，文件名与物品模型的 id 保持一致：`custom_spawn_egg.json`。

@[code](@/reference/latest/src/main/generated/assets/example-mod/items/custom_spawn_egg.json)

![带有客户端物品的生成蛋物品](/assets/develop/items/spawn_egg_2.png)

## 命名生成蛋 {#naming-the-spawn-egg}

为了命名生成蛋，翻译键 `item.example-mod.custom_spawn_egg` 必须被赋值。这个过程类似于[命名物品](./first-item#naming-the-item)。

创建或编辑位于 `src/main/resources/assets/example-mod/lang/en_us.json` 的 JSON 文件，并放入翻译键及其对应值：

```json
{
  "item.example-mod.custom_spawn_egg": "自定义生成蛋"
}
```

## 添加到创造模式分组 {#adding-to-a-creative-mode-tab}

生成蛋被添加到生成蛋 `CreativeModeTab` 中，这是在[物品类](./first-item#preparing-your-items-class)的 `initialize()` 方法里完成的。

@[code transcludeWith=:::spawn_egg_creative_tab](@/reference/latest/src/main/java/com/example/docs/item/ModItems.java)

![带有名称和创造模式分组的生成蛋物品](/assets/develop/items/spawn_egg_3.png)

查看[将物品添加到创造模式分组](./first-item#adding-the-item-to-a-creative-tab)以获取更详细的信息。
