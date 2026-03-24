---
title: 刷怪蛋
description: 了解如何注册刷怪蛋物品。
authors:
  - cassiancc
  - Fellteros
  - skycatminepokie
  - VatinMc
---

<!---->

:::info 前置条件

你必须先了解[如何创建一个物品](./first-item)，然后才能举一反三，转变成刷怪蛋。

:::

刷怪蛋是一种特殊物品，使用后会生成相应的生物。 你可以通过向[物品类](./first-item#preparing-your-items-class)中的 `register` 方法传递 `SpawnEggItem::new` 来注册一个。

@[code transcludeWith=:::spawn_egg](@/reference/latest/src/main/java/com/example/docs/item/ModItems.java)

![没有纹理的刷怪蛋物品](/assets/develop/items/spawn_egg_1.png)

在它准备就绪之前，还有几件事要做：你必须添加纹理、物品模型、客户端物品、名称，并将刷怪蛋添加到相应的创造标签页。

## 添加纹理 {#adding-a-texture}

在 `assets/example-mod/textures/item` 目录下创建一张 16x16 的物品纹理，文件名与物品 ID 相同：`custom_spawn_egg.png`。 下面提供了一个纹理示例。

<DownloadEntry visualURL="/assets/develop/items/spawn_egg.png" downloadURL="/assets/develop/items/spawn_egg_small.png">纹理</DownloadEntry>

## 添加模型 {#adding-a-model}

在 `assets/example-mod/models/item` 目录中创建物品模型，文件名与物品的 ID 相同：`custom_spawn_egg.json`。

@[code](@/reference/latest/src/main/generated/assets/example-mod/models/item/custom_spawn_egg.json)

## 创建客户端物品 {#creating-the-client-item}

在 `assets/example-mod/items` 目录中创建客户端物品 JSON，文件名与物品模型的 ID 相同：`custom_spawn_egg.json`。

@[code](@/reference/latest/src/main/generated/assets/example-mod/items/custom_spawn_egg.json)

![有客户端物品的刷怪蛋物品](/assets/develop/items/spawn_egg_2.png)

## 给刷怪蛋命名 {#naming-the-spawn-egg}

要为刷怪蛋命名，必须为翻译键 `item.example-mod.custom_spawn_egg` 赋值。 此过程与[命名物品](./first-item#naming-the-item)类似。

创建或编辑位于 `src/main/resources/assets/example-mod/lang/en_us.json`（简体中文为 `zh_cn.json`）的 JSON 文件，并添加翻译键及其值：

```json
{
  "item.example-mod.custom_spawn_egg": "Custom Spawn Egg"
}
```

## 添加到创造模式标签页 {#adding-to-a-creative-mode-tab}

刷怪蛋被添加到[物品类](./first-item#preparing-your-items-class)的 `initialize()` 方法中的刷怪蛋 `CreativeModeTab` 中。

@[code transcludeWith=:::spawn_egg_creative_tab](@/reference/latest/src/main/java/com/example/docs/item/ModItems.java)

![带有名称和创造标签页的刷怪蛋物品](/assets/develop/items/spawn_egg_3.png)

请查看[将物品添加到创造模式标签页](./first-item#adding-the-item-to-a-creative-tab)以了解更多详细信息。
