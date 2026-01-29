---
title: 物品外观
description: 学习如何通过自定义颜色来源动态地为物品着色。
authors:
  - PEQB1145
---

你可以通过物品的客户端物品定义来操控其外观。Minecraft Wiki 上有一份[原版可用的物品模型修改方式列表](https://minecraft.wiki/w/Items_model_definition#Items_model_types)。

其中，一个常用的类型是**颜色来源**，它允许你根据预设条件改变物品的颜色。

[预定义的颜色来源](https://minecraft.wiki/w/Items_model_definition#Tint_sources_types)数量有限，下面我们来看看如何创建自己的颜色来源。

在这个例子中，我们先注册一个物品。如果你对此不熟悉，请先阅读[物品注册](./first-item)的相关内容。

@[code lang=java transcludeWith=:::item](@/reference/latest/src/main/java/com/example/docs/appearance/ExampleModAppearance.java)

确保添加了：

-   一个在 `/items/waxcap.json` 的[客户端物品](./first-item#creating-the-client-item)。
-   一个在 `/models/item/waxcap.json` 的[物品模型](./item-models)。
-   一个在 `/textures/item/waxcap.png` 的[纹理](./first-item#adding-a-texture)。

物品应该能在游戏中显示。

![已注册的物品](/assets/develop/item-appearance/item_tint_0.png)

如你所见，我们使用了灰度纹理。现在，让我们使用一个颜色来源来添加一些色彩。

## 物品颜色来源 {#item-tint-sources}

让我们为我们的蜡盖菇物品注册一个自定义颜色来源，使得在下雨时物品看起来是蓝色的，否则看起来是棕色的。

首先，你需要定义一个自定义的物品颜色来源。这可以通过在一个类或记录上实现 `ItemTintSource` 接口来完成。

@[code lang=java transcludeWith=:::tint_source](@/reference/latest/src/client/java/com/example/docs/appearance/RainTintSource.java)

由于这是客户端物品定义的一部分，颜色值可以通过资源包来更改。因此，你需要定义一个能够读取你的颜色定义的 [Map Codec](../codecs#mapcodec)。这里，我们的颜色来源将有一个 `int` 类型的值，描述下雨时的颜色。我们可以使用内置的 `ExtraCodecs.RGB_COLOR_CODEC` 来构建我们的 Codec。

@[code lang=java transclude={17-20}](@/reference/latest/src/client/java/com/example/docs/appearance/RainTintSource.java)

然后我们可以在 `type()` 方法中返回这个 Codec。

@[code lang=java transclude={35-38}](@/reference/latest/src/client/java/com/example/docs/appearance/RainTintSource.java)

最后，我们可以提供一个 `calculate` 方法的实现来决定最终的颜色值。参数 `color` 的值来自资源包的定义。

@[code lang=java transclude={26-33}](@/reference/latest/src/client/java/com/example/docs/appearance/RainTintSource.java)

接下来，我们需要注册我们的物品颜色来源。这需要在**客户端初始化器**中，使用 `ItemTintSources` 中声明的 `ID_MAPPER` 来完成。

@[code lang=java transcludeWith=:::item_tint_source](@/reference/latest/src/client/java/com/example/docs/appearance/ExampleModAppearanceClient.java)

完成后，我们就可以在客户端物品定义中使用我们的物品颜色来源了。

@[code lang=json transclude](@/reference/latest/src/main/generated/assets/example-mod/items/waxcap.json)

你可以在游戏中观察到物品颜色的变化。

![游戏中的物品颜色](/assets/develop/item-appearance/item_tint_1.png)
