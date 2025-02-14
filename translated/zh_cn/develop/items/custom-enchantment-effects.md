---
title: 自定义魔咒效果
description: 学习如何创建自己的魔咒效果。
authors:
  - krizh-p
---

# 自定义魔咒 {#custom-enchantments}

从 1.21 开始，Minecraft 中的自定义魔咒通过”数据驱动“的方式添加。 这让添加一些简单的魔咒（如增加攻击伤害）变得更容易，但创建复杂的魔咒则更具挑战性。 这个过程包括将魔咒分解成 _效果组件_。

效果组件包含定义魔咒特殊效果的代码。 Minecraft 原版已支持一些默认效果，例如物品损害值、击退和经验。

:::tip
来看看 [Minecraft Wiki 的附魔效果组件页面](https://zh.minecraft.wiki/w/%E9%AD%94%E5%92%92%E6%95%B0%E6%8D%AE%E6%A0%BC%E5%BC%8F#%E5%AE%9A%E4%B9%89)，检查 Minecraft 默认效果是否满足你的需求。 本指南假定你了解如何配置“简单”的数据驱动魔咒，并侧重于创建默认不支持的自定义魔咒效果。
:::

## 自定义魔咒效果 {#custom-enchantment-effects}

先创建 `enchanyments` 文件夹，然后在里面创建 `effect` 文件夹。 在里面，创建记录类 `LightningEnchantmentEffect`。

现在，创建构造器，并覆盖 `EnchantmentEntityEffect` 接口的方法。 还要创建 `CODEC` 变量以编码解码我们的效果，可以了解更多[关于 codec 的信息](../codecs)。

我们的大部分代码都将进入 `apply()` 事件，当魔咒生效的条件得到满足时，该事件就会被调用。 我们稍后会配置这个 `Effect` 以在实体被击中时调用，但现在，让我们编写简单的代码来实现用闪电击中目标。

@[code transcludeWith=#entrypoint](@/reference/latest/src/main/java/com/example/docs/enchantment/effect/LightningEnchantmentEffect.java)

这里，变量 `amount` 表示与附魔等级成比例的数值。 我们可以根据等级来修改魔咒的效果。 在上面的代码中，我们使用附魔的等级来决定生成多少闪电。

## 注册魔咒效果 {#registering-the-enchantment-effect}

就像我们的模组中的其他部分，我们将会把我们的 `EnchantmentEffect` 加入到 Minecraft 的注册表中。 为了实现这一点，添加一个叫做 `ModEnchantmentEffects`（或者你想叫什么就叫什么）的类，和一个辅助方法来注册我们的魔咒。 确保在你的主类中调用 `registerModEnchantmentEffects()` 方法，这个主类应该包含 `onInitialize()` 方法。

@[code transcludeWith=#entrypoint](@/reference/latest/src/main/java/com/example/docs/enchantment/ModEnchantmentEffects.java)

## 创建魔咒 {#creating-the-enchantment}

现在我们有了一个魔咒效果！ 最后一步是创建一个魔咒，应用我们自定义的效果。 这可以通过创建类似于 Minecraft 数据包中的 JSON 文件来实现，在这篇文档中，将向你展示如何使用 Fabric 的数据生成工具来动态生成 JSON。 要开始，请创建一个名为 `EnchantmentGenerator` 的类。

在这个类中，我们先注册我们的魔咒对象，并使用 `configure()` 方法来在程序中创建 JSON。

@[code transcludeWith=#entrypoint](@/reference/latest/src/client/java/com/example/docs/datagen/EnchantmentGenerator.java)

在继续之前，应确保你的项目已为数据生成进行了配置。如果您不确定，请 [查看相关文档页面](../data-generation/setup)。

在最后，我们必须要告诉我们的模组去把 `EnchantmentGenerator` 加入到数据生成任务列表中。 为了实现这一点，只需要简单的把 `EnchantmentGenerator` 加入到 `onInitializeDataGenerator` 方法中。

@[code transclude={24-24}](@/reference/latest/src/client/java/com/example/docs/datagen/FabricDocsReferenceDataGenerator.java)

现在，当你运行你的模组的数据生成任务，附魔表 JSON 将会生成在 `generated` 文件夹内。 下面是一个例子：

@[code](@/reference/latest/src/main/generated/data/fabric-docs-reference/enchantment/thundering.json)

你需要在 `zh_cn.json` 中给你的自定义魔咒添加一个有意义的名字：

```json
"enchantment.FabricDocsReference.thundering": "Thundering",
```

现在你应该有了一个可以正常工作的自定义附魔效果！ 附魔一个武器，然后攻击一个生物试试吧。 下面的视频里有一个例子：

<VideoPlayer src="/assets/develop/enchantment-effects/thunder.webm">使用雷电（Thundering）魔咒</VideoPlayer>
