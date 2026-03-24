---
title: 自定义魔咒效果
description: 学习如何创建自己的魔咒效果。
authors:
  - CelDaemon
  - krizh-p
---

从 1.21 开始，Minecraft 中的自定义魔咒通过“数据驱动”的方式添加。 这让添加一些简单的魔咒（如增加攻击伤害）变得更容易，但创建复杂的魔咒则更具挑战性。 这个过程包括将魔咒分解成 _效果组件_。

效果组件包含定义魔咒特殊效果的代码。 Minecraft 原版已支持一些默认效果，例如物品损害值、击退和经验。

::: tip

来看看 [Minecraft Wiki 的附魔效果组件页面](https://zh.minecraft.wiki/w/%E9%AD%94%E5%92%92%E6%95%B0%E6%8D%AE%E6%A0%BC%E5%BC%8F#%E5%AE%9A%E4%B9%89)，检查 Minecraft 默认效果是否满足你的需求。 本指南假定你了解如何配置“简单”的数据驱动魔咒，并侧重于创建默认不支持的自定义魔咒效果。

:::

## 自定义魔咒效果 {#custom-enchantment-effects}

先创建 `enchantment` 文件夹，然后在里面创建 `effect` 文件夹。 在里面，创建记录类 `LightningEnchantmentEffect`。

现在，创建构造器，并覆盖 `EnchantmentEntityEffect` 接口的方法。 还要创建 `CODEC` 变量以编码解码我们的效果；可以了解更多关于 [codec](../codecs) 的信息。

我们的大部分代码都将进入 `apply()` 事件，当魔咒生效的条件得到满足时，该事件就会被调用。 我们稍后会配置这个 `Effect` 以在实体被击中时调用，但现在，让我们编写简单的代码来实现用闪电击中目标。

@[code transcludeWith=#entrypoint](@/reference/latest/src/main/java/com/example/docs/enchantment/effect/LightningEnchantmentEffect.java)

这里，变量 `amount` 表示与附魔等级成比例的数值。 我们可以根据等级来修改魔咒的效果。 在上面的代码中，我们使用附魔的等级来决定生成多少闪电。

## 注册魔咒效果 {#registering-the-enchantment-effect}

就像我们的模组中的其他部分，我们将会把我们的 `EnchantmentEffect` 加入到 Minecraft 的注册表中。 为了实现这一点，添加一个叫做 `ModEnchantmentEffects`（或者你想叫什么就叫什么）的类，和一个辅助方法来注册我们的魔咒。 确保在你的主类中调用 `registerModEnchantmentEffects()` 方法，这个主类应该包含 `onInitialize()` 方法。

@[code transcludeWith=#entrypoint](@/reference/latest/src/main/java/com/example/docs/enchantment/ModEnchantmentEffects.java)

## 创建魔咒 {#creating-the-enchantment}

现在我们有了一个魔咒效果！ 最后一步是创建应用我们自定义的效果的魔咒。 我们可以使用数据驱动的魔咒系统来创建魔咒，只需要在我们的模组的资源中添加一个 JSON 文件即可。

在 `data/example-mod/enchantments` 创建 JSON 文件。 文件的名称就会是魔咒的 ID：`thundering.json` 就会是 `example-mod:thundering`。

::: info

关于文件格式的更多信息，请参阅[中文 Minecraft Wiki - 魔咒定义格式](https://zh.minecraft.wiki/w/魔咒定义格式)。

要快速生成自定义魔咒，可使用 [Misode generator](https://misode.github.io/enchantment/)。

:::

对于这个例子，我们使用以下魔咒定义，添加 `thundering` 魔咒并使用我们自定义的 `lightning_effect`：

@[code](@/reference/latest/src/main/generated/data/example-mod/enchantment/thundering.json)

你需要在 `en_us.json` 以及 `zh_cn.json` 中给你的自定义魔咒一个可读的名字：

```json
"enchantment.example-mod.thundering": "Thundering",
```

现在你应该有了一个可以正常生效的自定义附魔效果！ 附魔一个武器，然后攻击一个生物试试吧。 下面的视频里有一个例子：

<VideoPlayer src="/assets/develop/enchantment-effects/thunder.webm">使用雷电（Thundering）魔咒</VideoPlayer>
