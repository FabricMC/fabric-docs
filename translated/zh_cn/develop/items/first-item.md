---
title: 创建你的第一个物品
description: 学习如何注册简单的物品，以及如何给物品添加纹理、模型和名称。
authors:
  - IMB11
  - dicedpixels
---

# 创建你的第一个物品{#creating-your-first-item}

本页会带你介绍物品的一些关键概念，以及如果注册物品、添加纹理、添加模型、命名。

如果你还不知道的话，Minecraft 中的一切都存储在注册表中，物品也不例外。

## 准备你的物品类 {#preparing-your-items-class}

要简化物品注册，可以创建一个方法，接收一个物品实例和字符串 id。

这个方法会用提供的 id 创建物品并在游戏的物品注册表中注册。

你可以将这个方法放在叫做 `ModItems` 的类中（也可以是其他你想要的名称）。

Mojang 也是对物品这么做的！ 看看 `Items` 类以了解。

@[code transcludeWith=:::1](@/reference/latest/src/main/java/com/example/docs/item/ModItems.java)

## 注册物品{#registering-an-item}

你现在可以用这个方法注册物品

物品的构造方法会接收一个 `Items.Settings` 类的实例作为参数。 这个类允许你通过一系列构造器方法配置物品的属性。

::: tip
If you want to change your item's stack size, you can use the `maxCount` method in the `Items.Settings`/`FabricItemSettings` class.

如果将物品标记为可被破坏，那么这就不会生效，因为可被破坏的物品的堆叠大小永远是 1 以避免重复损坏。
:::

@[code transcludeWith=:::2](@/reference/latest/src/main/java/com/example/docs/item/ModItems.java)

但是，进到游戏后，你会发现我们的物品不存在！ 这是因为你还没有静态初始化类。

你可以在你的类中添加一个 public static 的初始化方法，然后在你的 `ModInitializer` 类中调用。 当前，方法不需要里面有任何东西。

@[code transcludeWith=:::3](@/reference/latest/src/main/java/com/example/docs/item/ModItems.java)

@[code transcludeWith=:::1](@/reference/latest/src/main/java/com/example/docs/item/FabricDocsReferenceItems.java)

对类调用一个方法会静态初始化，如果还没有加载的话——这意味着所有的 `static` 字段都会计算。 这就是这个占位的 `initialize` 的方法的目的。

## 将物品添加到物品组{#adding-the-item-to-an-item-group}

:::info
如果想要将物品添加到自定义的 `ItemGroup`，请参阅[自定义物品组](./custom-item-groups)页面以了解更多。
:::

这里为举例，我们将这个物品添加到原材料物品组中，你需要使用 Fabric API 的 item group event——也就是 `ItemGroupEvents.modifyEntriesEvent`。

你可以在你的物品类的 `initialize` 方法中完成。

@[code transcludeWith=:::4](@/reference/latest/src/main/java/com/example/docs/item/ModItems.java)

载入游戏，你会看到我们的物品已经注册好了，并且在原材料物品组中。

![原材料组中的物品](/assets/develop/items/first_item_0.png)

然而，还缺这些：

- 物品模型
- 纹理
- 翻译（名称）

## 给物品命名{#naming-the-item}

物品当前还没有翻译，所以需要添加。 Minecraft 已经提供好了翻译键：`item.mod_id.suspicious_substance`。

创建新的 JSON 文件：`src/main/resources/assets/<mod id here>/lang/en_us.json`，并将翻译键和名称放在里面：

```json
{
    "item.mod_id.suspicious_substance": "Suspicious Substance"
}
```

要应用更改，可以重启游戏，或者构建模组并按下<kbd>F3</kbd> + <kbd>T</kbd>。

## 添加纹理和模型{#adding-a-texture-and-model}

要给你的物品纹理和模型，先简单地为物品创建一个16x16的纹理图像，并存储在 `assets/<mod id here>/textures/item` 文件夹中。 根据物品的 id 命名纹理文件的名字，但要有 `.png` 扩展名。

例如，将示例纹理用于 `suspicious_substance.png`。

<DownloadEntry type="Texture" visualURL="/assets/develop/items/first_item_1.png" downloadURL="/assets/develop/items/first_item_1_small.png" />

重启或重新加载游戏时，你会发现物品还没有纹理，这是因为需要添加一个使用了此纹理的模型。

简单创建一个 `item/generated` 模型，接收一个输入纹理，没有其他的。

在 `assets/<mod id here>/models/item` 文件夹内创建模型 JSON，名称与物品相同， `suspicious_substance.json`

@[code](@/reference/latest/src/main/resources/assets/fabric-docs-reference/models/item/suspicious_substance.json)

### 逐个分析模型 JSON{#breaking-down-the-model-json}

- `parent`：模型要继承的模型。 在这个例子中，是 `item/generated` 模型。
- `textures`：为模型定义纹理的地方。 `layer0` 键是模型使用的纹理。

大多物品继承的模型是 `item/generate`，因为这是显示纹理的简单模型。

也有其他的，比如 `item/handheld`，用于拿在玩家手中的物品，例如工具。

你的物品在游戏内看上去应该是这样：

![模型正确的物品](/assets/develop/items/first_item_2.png)

## 让物品可堆肥或作燃料 {#making-the-item-compostable-or-a-fuel}

Fabric API 添加了各种注册表，可用于为物品添加额外属性。

例如，要让物品可堆肥，可以使用 `CompostableItemRegistry`：

@[code transcludeWith=:::_10](@/reference/latest/src/main/java/com/example/docs/item/ModItems.java)

又如，如果要让物品可作燃料，可以使用 `FuelRegistry` 类。

@[code transcludeWith=:::_11](@/reference/latest/src/main/java/com/example/docs/item/ModItems.java)

## 添加基本的合成配方 {#adding-a-basic-crafting-recipe}

<!-- In the future, an entire section on recipes and recipe types should be created. For now, this suffices. -->

如果要为你的物品添加合成配方，需要将配方 JSON 文件放在 `data/<mod id here>/recipes` 文件夹中。

更多关于配方格式的信息，可参考以下资源：

- [配方 JSON 生成器](https://crafting.thedestruc7i0n.ca/)
- [中文 Minecraft Wiki - 配方 JSON](https://zh.minecraft.wiki/w/配方#JSON格式)

## 自定义物品提示 {#custom-tooltips}

如果要让你的物品有自定义的物品提示，需要创建继承了 `Item` 的类，并覆盖 `appendTooltip` 方法。

:::info
这个例子使用 `LightningStick` 类，这是在[自定义物品交互](./custom-item-interactions)页面创建的。
:::

@[code lang=java transcludeWith=:::3](@/reference/latest/src/main/java/com/example/docs/item/custom/LightningStick.java)

每次调用 `add()` 都会添加一行提示。

![物品提示演示](/assets/develop/items/first_item_3.png)
