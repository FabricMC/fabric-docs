---
title: 药水
description: 学习如何加入多种生物效果的自定义药水。
authors:
  - cassiancc
  - dicedpixels
  - Drakonkinst
  - JaaiDead
  - PandoricaVi
---

药水是能为实体提供效果的消耗品。 玩家可以使用酿造台酿造药水，或者从其他游戏机制中以物品形式获取。

## 自定义药水{#custom-potions}

和物品和方块一样，药水需要注册。

### 创建物品{#creating-the-potion}

让我们从声明一个用于储存你的 `Potion` 实例的字段开始。 我们将直接使用 `ModInitializer`——实现这个类来持有这个字段。 注意使用了 `Registry.registerForHolder`，就像生物效果一样，许多使用药水的原版方法都优先使用这些作为 holder。

@[code lang=java transclude={18-27}](@/reference/latest/src/main/java/com/example/docs/potion/ExampleModPotions.java)

我们传入一个 `MobEffectInstance` 实例，它的构造方法接收以下 3 个参数：

- `Holder<MobEffect> type` - 效果，表现为一个 holder。 我们在这里使用我们的自定义效果。 你也可以通过原版的 `MobEffects` 类访问原版效果。
- `int duration` - 状态效果的持续时间（以刻计算）。
- `int amplifier` - 状态效果的增幅。 比如 急迫 II 的增幅是 1。

::: info

要创建自己的药水效果，请使用[效果](../entities/effects)指南。

:::

### 注册药水 {#registering-the-potion}

在我们的初始化器中，我们将使用 `FabricBrewingRecipeRegistryBuilder.BUILD` 事件，使用 `BrewingRecipeRegistry.registerPotionRecipe` 方法注册我们的药水。

@[code lang=java transclude={29-40}](@/reference/latest/src/main/java/com/example/docs/potion/ExampleModPotions.java)

`registerPotionRecipe` 接收以下 3 个参数：

- `Holder<Potion> input` - 初始药水，表现为一个 holder。 通常可以是水瓶或粗制的药水。
- `Item item` - 作为药水主要原料的物品。
- `Holder<Potion> output` - 结果药水，表现为一个 holder。

注册完成后，你就可以用马铃薯酿造土豆药水。

![玩家物品栏内的效果](/assets/develop/tater-potion.png)
