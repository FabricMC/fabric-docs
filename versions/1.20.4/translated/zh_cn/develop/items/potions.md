---
title: 药水
description: 学习如何加入多种状态效果的自定义药水。
authors:
  - dicedpixels
  - PandoricaVi
  - Drakonkinst

search: false
---

# 药水{#potions}

药水是能为实体提供效果的消耗品。 玩家可以使用酿造台酿造药水，或者从其他游戏机制中以物品形式获取。

## 自定义药水{#custom-potions}

添加药水和添加物品的方式类似。 你将创建的你的药水的一个实例，并通过调用 `BrewingRecipeRegistry.registerPotionRecipe` 注册它。

:::info
当 Fabric API 存在时，`BrewingRecipeRegistry.registerPotionRecipe` 的访问权限会被访问加宽器（Access Widener）设置为 `public` 。
:::

### 创建物品{#creating-the-potion}

让我们从声明一个用于储存你的 `Potion` 实例的字段开始。 我们将直接使用入口点类来持有这个字段。

@[code lang=java transclude={18-27}](@/reference/latest/src/main/java/com/example/docs/potion/FabricDocsReferencePotions.java)

我们传入一个 `StatusEffectInstance` 实例，它的构造方法接收以下 3 个参数：

- `StatusEffect type` - 一个状态效果。 我们在这里使用我们的自定义效果。 此外你也可以通过 `net.minecraft.entity.effect.StatusEffects` 访问原版效果。
- `int duration` - 状态效果的持续时间（以刻计算）。
- `int amplifier` - 状态效果的增幅。 比如 急迫 II 的增幅是 1。

:::info
为了创建你自己的效果，请参阅 [状态效果](../entities/effects) 的指南。
:::

### 注册药水{#registering-the-potion}

在我们的入口点中，我们调用 `BrewingRecipeRegistry.registerPotionRecipe`。

@[code lang=java transclude={30-30}](@/reference/latest/src/main/java/com/example/docs/potion/FabricDocsReferencePotions.java)

`registerPotionRecipe` 接收以下 3 个参数：

- `Potion input` - 初始的药水。 通常可以是水瓶或粗制的药水。
- `Item item` - 作为药水主要原料的物品。
- `Potion output` - 产出的药水。

如果你使用 Fabric API，就没有必要使用 Mixin 注入 `@Invoker`，可以直接调用 `BrewingRecipeRegistry.registerPotionRecipe`。

完整的示例：

@[code lang=java transcludeWith=:::1](@/reference/latest/src/main/java/com/example/docs/potion/FabricDocsReferencePotions.java)

注册完成后，你就可以用马铃薯酿造土豆药水。

![玩家物品栏内的效果](/assets/develop/tater-potion.png)

:::info 使用 `Ingredient` 注册药水

在 Fabric API 的帮助下，使用 `
net.fabricmc.fabric.api.registry.FabricBrewingRecipeRegistry` 可以使用 `Ingredient` 而非 `Item` 来注册药水配方。
:::

### 不使用 Fabric API 注册药水配方

没有 Fabric API 时，`BrewingRecipeRegistry.registerPotionRecipe` 将会是 `private` 的。 为了访问这个方法，请使用如下的 `@Invoker` Mixin，或使用访问加宽器（Access Widener）。

@[code lang=java transcludeWith=:::1](@/reference/latest/src/main/java/com/example/docs/mixin/potion/BrewingRecipeRegistryInvoker.java)
