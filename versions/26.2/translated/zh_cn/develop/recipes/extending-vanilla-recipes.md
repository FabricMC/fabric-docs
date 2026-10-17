---
title: 扩展原版配方
description: 了解如何为已有的工作站点制作自定义配方。
authors:
  - ekulxam
  - lynndova
resources:
  https://docs.neoforged.net/docs/resources/server/recipes/builtin/: 内置配方类型 - NeoForge 文档
---

如果你尝试向已有的工作站点（例如锻造台、工作台或切石机）添加配方，你通常只需要[创建配方类](./custom-recipe-types#creating-the-recipe-class)、[实现其方法](./custom-recipe-types#implementing-the-methods)、[注册序列化器](./custom-recipe-types#creating-a-recipe-serializer)并[创建配方 JSON](./custom-recipe-types#creating-a-recipe)，因为方块、菜单和屏幕逻辑都已（由 Mojang）完成。 让我们看一些示例。

## 概述 {#overview}

每个原版工作站点都有自己的 `RecipeType`，定义在 `RecipeType` 接口中。 每个工作站点都需要某种特定子类型的 `Recipe` 才能正常工作。

::: warning

请注意，除非你修改底层的菜单，否则你的配方会受限于菜单所能提供的输入和输出。 例如，锻造台有三个输入和一个输出（在原版中，它们通常是 `Optional<Ingredient> template`、`Ingredient base`、`Optional<Ingredient> addition` 和 `ItemStackTemplate result`）。 然而，在 `Recipe` 类内部，你在配置输入以生成输出方面享有很大自由度。

:::

## 锻造台 {#smithing-table}

让我们创建一种新的锻造配方类型，它将魔咒应用于基础输入物品以生成输出。

锻造台需要实现 `SmithingRecipe` 接口的任意类型，该接口返回 `RecipeTypes.SMITHING`。 在制作新的 `SmithingRecipe` 时，你可以简单地新建一个类并实现 `SmithingRecipe`；但另一种有效的方式是继承 `SimpleSmithingRecipe`（一个原版类），它已经实现了 `SmithingRecipe`。

<<< @/reference/26.2/src/main/java/com/example/docs/recipe/extending/EnchantingSmithingRecipe.java#enchanting_smithing

哇，又是好多字。 这似乎在配方文档中很常见（哈哈）。 让我们来弄清楚这里发生了什么。

前几行包含我们的 `Codec`、`MapCodec` 和 `StreamCodec`，用于序列化和同步配方的详细信息。 我们为魔咒使用了 `Object2IntOpenHashMap`，以便可以将任意魔咒映射到其等级。

在序列化部分之后，有前面提到的 `template`、`base` 和 `addition`，但与 `ItemStackTemplate result` 不同，我们使用的是 `Object2IntOpenHashMap enchantments`。

`assemble` 方法是自定义配方的核心，它负责在合成配方时提供输出的 `ItemStack`。 在这种情况下，我们使用 `EnchantmentHelper` 中的辅助方法来应用映射表中的魔咒。

我们的 `PlacementInfo` 主要辅助通过配方书放置配方，而 `RecipeDisplay` 则有助于在配方书中展示配方。

:::details 补充说明：槽位显示

如果你尝试自己重写 `display`，你会很快注意到你无法为结果创建 `SlotDisplay`，因为你的结果是基于 `base` 动态生成的，而 `base` 是一个无法轻松从中获取 `ItemStack` 的 `Ingredient`。 不过，我们在配方类中提供了一个有效的 `display` 重写示例。 这是怎么回事？

<<< @/reference/26.2/src/main/java/com/example/docs/recipe/extending/EnchantingSmithingDemoSlotDisplay.java#slot_display

我们创建了一个 `SlotDisplay` 的自定义实现。 这个特定实现允许显示带有指定魔咒的结果。

在我们的 `resolve` 方法中，我们首先创建一个 `RandomSource` 和一个 `BinaryOperator<ItemStack>`，然后将两者传递给 `SlotDisplay.applyDemoTransformation`（它是静态的但为私有，所以我们需要一个 Mixin invoker）。

<<< @/reference/26.2/src/main/java/com/example/docs/mixin/accessor/SlotDisplayAccessor.java#demo_invoker

`applyDemoTransformation` 允许对在 `SlotDisplay` 中显示的 `ItemStack` 应用变更。 它接收一个 `BinaryOperator<ItemStack>`，以便可以根据 `material` 修改 `base` 的数据。 这对于像纹饰配方这样的情况很有用，在此类配方中，结果的纹饰颜色会根据材料的不同而有所变化。 但是，我们直接将魔咒应用到基础物品堆上，而忽略材料（配方仅在允许合成前检查是否存在正确的材料），因此我们实际上可以在 `SlotDisplay` 实现中省略 `material` 字段（此时会传入 `SlotDisplay.Empty.INSTANCE` 代替 `material` 传给 `applyDemoTransformation`）。

:::

最后，我们需要注册配方序列化器和槽位显示类型。

<<< @/reference/26.2/src/main/java/com/example/docs/recipe/ExampleModRecipes.java#enchanting_smithing_registration

::: info

这个配方仍然是数据驱动的。

<<< @/reference/26.2/src/main/resources/data/example-mod/recipe/smithing_enchanting/netherite_sword_smithing_enchanting.json

:::

![超级下界合金剑](/assets/develop/recipes/smithing_enchanting.png)

## 工作台 {#crafting-table}

在制作新的合成配方时也会遇到类似的情况。 预期的类型是 `CraftingRecipe` 接口，如果 `ShapedRecipe`（有序合成配方）和 `ShapelessRecipe`（无序合成配方）不够用，那么我们建议你继承 `CustomRecipe`。 我们鼓励你查看目标工作站点配方接口的子类型，看看是否能找到符合你需求的接口。

作为示例，让我们创建一个自定义合成配方，允许将药水注入谜之炖菜中。

<<< @/reference/26.2/src/main/java/com/example/docs/recipe/extending/StewSpikingCraftingRecipe.java#stew_spiking

一如既往，我们需要注册配方序列化器。

<<< @/reference/26.2/src/main/java/com/example/docs/recipe/ExampleModRecipes.java#stew_spiking_registration

::: info

这个配方仍然是数据驱动的。

<<< @/reference/26.2/src/main/resources/data/example-mod/recipe/stew_spiking/stew_spiking.json

我们只需要类型，以便 Minecraft 知道我们要加载该配方。

:::

_嘘，别告诉任何人！ >:)_
![我在这碗炖菜里加了好多伤害药水](/assets/develop/recipes/stew_spiking.png)

## 切石机 {#stonecutter}

切石机配方在 `RecipeManager`/`RecipeAccess` 中与其他 `Recipe` 是分开的，因为切石机需要在给定单个输入时显示并选择其所有有效的配方（带有配方书的菜单是通过 `ClientRecipeBook` 处理的，服务器将必要的配方发给客户端）。 只需继承 `StonecutterRecipe`（与其他类不同，这并不是一个接口！） 并重写 `assemble` 方法，除了单纯制作切石配方 JSON 外，就能适用于绝大多数使用场景。
