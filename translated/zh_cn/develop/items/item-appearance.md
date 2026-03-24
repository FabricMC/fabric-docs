---
title: 物品外观
description: 学习如何使用自定着色源动态地为物品着色。
authors:
  - dicedpixels
---

你可以通过客户端物品来修改物品的外观。 Minecraft Wiki 上有一个[原版修改列表](https://zh.minecraft.wiki/w/%E7%89%A9%E5%93%81%E6%A8%A1%E5%9E%8B%E6%98%A0%E5%B0%84#%E7%B1%BB%E5%9E%8B)。

其中一种常用的是 _着色源_，它允许你根据预设条件改变物品的颜色。

[预定义的着色源](https://zh.minecraft.wiki/w/%E7%89%A9%E5%93%81%E6%A8%A1%E5%9E%8B%E6%98%A0%E5%B0%84#model)数量有限，所以我们来看看如何创建自己的着色源。

在本例中，我们先来注册一个物品。 如果你不熟悉这个过程，请先阅读关于[物品注册](./first-item)的说明。

@[code lang=java transcludeWith=:::item](@/reference/latest/src/main/java/com/example/docs/appearance/ExampleModAppearance.java)

请务必添加：

- `/items/waxcap.json` 中的[客户端物品](./first-item#creating-the-client-item)
- `/models/item/waxcap.json` 中的[物品模型](./item-models)
- `/textures/item/waxcap.png` 中的[纹理](./first-item#adding-a-texture)

物品应该会出现在游戏中。

![注册的物品](/assets/develop/item-appearance/item_tint_0.png)

如你所见，我们使用的是灰度纹理。 接下来，我们使用着色源添加一些颜色。

## 物品着色源 {#item-tint-sources}

让我们注册一个自定义着色源来为 Waxcap 物品着色，这样下雨时物品会呈现蓝色，其他情况则呈现棕色。

首先，你需要定义一个自定义物品着色源。 这可以通过在类或记录上实现 `ItemTintSource` 接口来实现。

@[code lang=java transcludeWith=:::tint_source](@/reference/latest/src/client/java/com/example/docs/appearance/RainTintSource.java)

由于这是客户端物品定义的一部分，因此可以通过资源包更改色调值。 所以你需要定义一个能够读取色调定义的 [Map Codec](../codecs#mapcodec)。 在本例中，色调源将包含一个 `int` 值，用于描述下雨时的颜色。 我们可以使用内置的 `ExtraCodecs.RGB_COLOR_CODEC` 来构建我们的 Codec。

@[code lang=java transclude={17-20}](@/reference/latest/src/client/java/com/example/docs/appearance/RainTintSource.java)

然后我们可以在 `type()` 中返回这个 Codec。

@[code lang=java transclude={35-38}](@/reference/latest/src/client/java/com/example/docs/appearance/RainTintSource.java)

最后，我们可以提供一个 `calculate` 的实现，用于决定色调颜色。 `color` 的值来自资源包。

@[code lang=java transclude={26-33}](@/reference/latest/src/client/java/com/example/docs/appearance/RainTintSource.java)

接下来，我们需要注册我们的物品着色源。 这是在**客户端初始化器**中使用在 `ItemTintSources` 中声明的 `ID_MAPPER` 完成的。

@[code lang=java transcludeWith=:::item_tint_source](@/reference/latest/src/client/java/com/example/docs/appearance/ExampleModAppearanceClient.java)

完成此操作后，我们就可以在客户端物品定义中使用我们的物品着色源。

@[code lang=json transclude](@/reference/latest/src/main/generated/assets/example-mod/items/waxcap.json)

你可以在游戏中观察到物品颜色的变化。

![游戏内物品色调](/assets/develop/item-appearance/item_tint_1.png)
