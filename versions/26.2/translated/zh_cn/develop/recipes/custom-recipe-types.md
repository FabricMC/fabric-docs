---
title: 自定义配方类型
description: 了解如何创建自定义配方类型。
authors:
  - cassiancc
  - ekulxam
  - skippyall
resources:
  https://docs.neoforged.net/docs/resources/server/recipes/custom/: 自定义配方 - NeoForge 文档
  https://docs.neoforged.net/docs/resources/server/recipes/ingredients/: 原材料 - NeoForge 文档
---

<!---->

::: warning

如果你尝试为原版工作站点添加自定义配方类型，请参阅[扩展原版配方](./extending-vanilla-recipes)。

:::

自定义配方类型是一种为你的模组的自定义合成机制创建数据驱动的配方的方法。 例如，我们将创建一个类似于锻造台的升级方块配方类型。

## 创建配方输入类 {#creating-your-recipe-in​​put-class}

在开始创建配方之前，你需要实现一个 `RecipeInput` 接口，用于存放方块物品栏中的输入物品。 我们希望升级配方有两个输入物品：一个是待升级的基础物品，另一个是升级后的物品本身。

<<< @/reference/26.2/src/main/java/com/example/docs/recipe/UpgradingRecipeInput.java#recipe_input

::: info

或者，我们可以用一个 `List` 替换两个 `ItemStack`。 这同样适用于使用两个 `Ingredient` 或 `ItemStack` 的其他类。

:::

## 创建配方类 {#creating-the-recipe-class}

现在我们有了存储输入项的方法，就可以创建 `Recipe` 实现。 此类实现代表数据包中定义的单个配方。 它们负责检查配方的原材料和要求，并将它们组装成结果。

让我们先来定义一下结果和配方中的原材料。

<<< @/reference/26.2/src/main/java/com/example/docs/recipe/UpgradingRecipe.java#base_class

请注意我们如何使用 `Ingredient` 对象作为输入。 这使得我们的配方可以互换地接受多种原材料。 （另请参阅资源部分中 NeoForge 文档的“原材料”页面。）

## 实现方法 {#implementing-the-methods}

接下来，我们来实现配方接口中的方法。 其中比较有趣的是 `matches` 和 `assemble`。 `matches` 方法用于测试我们 `RecipeInput` 实现中的输入物品是否与我们的原材料匹配。 然后，`assemble` 方法会创建合成的 `ItemStack`。

为了测试原材料是否匹配，我们可以使用原材料的 `test` 方法。

<<< @/reference/26.2/src/main/java/com/example/docs/recipe/UpgradingRecipe.java#implementing

## 创建配方序列化器 {#creating-a-recipe-serializer}

配方序列化器使用 [`MapCodec`](../serialization/codecs/#mapcodec) 从 JSON 中读取配方，并使用 `StreamCodec` 通过网络发送配方。

我们将使用 `RecordCodecBuilder#mapCodec` 为我们的配方构建一个映射编解码器。 它允许我们将 Minecraft 现有的编解码器整合到我们自己的编解码器中：

<<< @/reference/26.2/src/main/java/com/example/docs/recipe/UpgradingRecipe.java#map_codec

可以使用 `StreamCodec#composite` 以类似的方式创建流编解码器：

<<< @/reference/26.2/src/main/java/com/example/docs/recipe/UpgradingRecipe.java#stream_codec

现在我们将注册配方序列化器以及配方类型。 你可以在模组的初始化器中完成此操作，也可以在一个单独的类中完成，并通过模组初始化器调用该方法：

<<< @/reference/26.2/src/main/java/com/example/docs/recipe/ExampleModRecipes.java#registration

回到我们的配方类，现在我们可以添加返回我们刚刚注册的对象的方法了：

<<< @/reference/26.2/src/main/java/com/example/docs/recipe/UpgradingRecipe.java#implement_registry_objects

为了完成我们的自定义配方类型，我们只需要实现剩余的 `placementInfo`、`showNotification`、`group` 和 `recipeBookCategory` 方法，配方书会使用这些方法将我们的配方放在屏幕上。 目前，我们将只返回 `PlacementInfo.NOT_PLACEABLE` 和 `null`，因为配方书无法轻易扩展到模组的工作站。 我们还要重写 `isSpecial` 函数使其返回真，以防止其他一些与配方书相关的逻辑运行并记录错误。

<<< @/reference/26.2/src/main/java/com/example/docs/recipe/UpgradingRecipe.java#recipe_book

## 创建配方 {#creating-a-recipe}

我们的配方类型现在已经可以正常工作了，但我们仍然缺少两件重要的东西：一个是适用于我们配方类型的配方，另一个是合成它的方法。

首先，我们来创建一个配方。 在你的 `resources` 文件夹 `data/example-mod/recipe` 目录下，创建一个扩展名为 `.json` 的文件。 每个配方 JSON 文件都有一个 `"type"` 键，指向该配方的序列化器。 其他键则由该序列化器的编解码器定义。

在我们的例子中，一个有效的配方文件如下所示：

<<< @/reference/26.2/src/main/resources/data/example-mod/recipe/upgrading/diamond_pickaxe.json

## 配方同步 {#recipe-synchronization}

::: info

本部分为可选，仅当你 需要客户端了解配方时才需要。

:::

如前所述，配方完全由逻辑服务器处理。 然而在某些情况下，客户端可能需要知道有哪些配方存在——例如原版中的切石机，它需要显示给定原材料可用的配方选项。 此外，某些配方查看器（包括 [JEI](https://modrinth.com/mod/jei)）的插件运行在逻辑客户端上，这就需要你 使用 Fabric 的配方同步 API。

要同步你的配方，只需在你的模组初始化器中调用`RecipeSynchronization.synchronizeRecipeSerializer`，并提供你的模组的配方序列化器即可：

<<< @/reference/26.2/src/main/java/com/example/docs/recipe/ExampleModRecipes.java#recipe_sync

同步完成后，可以随时从客户端的配方管理器中检索配方：

<<< @/reference/26.2/src/client/java/com/example/docs/ExampleModRecipesClient.java#recipe_sync_client

## 工作站点 {#workstations}

想创建与配方类型相匹配的自定义工作站点吗？ 参阅[工作站点](../blocks/workstations)。
