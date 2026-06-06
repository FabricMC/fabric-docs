---
title: 配方生成
description: 使用数据生成器设置配方生成的指南。
authors:
  - CelDaemon
  - skycatminepokie
  - Spinoscythe
authors-nogithub:
  - jmanc3
  - mcrafterzz
---

<!---->

:::info 前置条件

请确保你已经完成[数据生成器设置](./setup)章节。

:::

## 设置 {#setup}

首先，我们需要 provider。 创建一个继承 `FabricRecipeProvider` 的类。 我们所有的配方生成都将在提供程序的 `buildRecipes` 方法中进行。

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModRecipeProvider.java#datagen_recipes_provider

接着在 `DataGeneratorEntrypoint` 入口点的 `onInitializeDataGenerator` 方法里注册这个类。

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModDataGenerator.java#datagen_recipes_register

## 无序配方 {#shapeless-recipes}

无序配方相当的简单。 只需将其添加到 provider 中的 `buildRecipes` 方法中：

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModRecipeProvider.java#datagen_recipes_shapeless

### 染料配方 {#dye-recipes}

染料配方用于给物品栏中的物品染色。

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModRecipeProvider.java#datagen_recipes_dye

## 有序配方 {#shaped-recipes}

对于有序配方，可以使用 `String` 定义有序，然后定义 `String` 中每个 `char` 代表什么。

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModRecipeProvider.java#datagen_recipes_shaped

::: tip

有很多辅助方法可用于创建普通配方。 查看 `RecipeProvider` 提供的内容！ 在 IntelliJ 中按 <kbd>Alt</kbd>+<kbd>7</kbd> 打开类的结构，其中包括方法列表。

:::

## 其他配方 {#other-recipes}

其他配方的工作原理类似，但需要一些额外的参数。 比如烧炼配方需要了解奖励多少经验。

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModRecipeProvider.java#datagen_recipes_smelting

烟熏配方有点不同，不像类似烟熏炉的方块那样使用相同的配方生成器。

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModRecipeProvider.java#datagen_recipes_smoking

## 资源条件

要为数据生成的配方应用[资源条件](../resource-conditions)，将 output 用 `withConditions` 包围，并提供你需要应用的任何资源条件。 这会生成应用了资源条件的配方和进度：

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModRecipeProvider.java#datagen_recipes_conditions
