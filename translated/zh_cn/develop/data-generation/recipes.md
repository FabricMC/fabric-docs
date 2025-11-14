---
title: 配方生成
description: 使用 Datagen 设置配方生成的指南。
authors:
  - skycatminepokie
  - Spinoscythe
authors-nogithub:
  - jmanc3
  - mcrafterzz
---

:::info 前提
首先，请确保你已完成 [Datagen 设置](./setup) 。
:::

## 设置 {#setup}

首先，我们需要提供程序。 创建一个 `extends FabricRecipeProvider` 的类。 我们所有的配方生成都将在提供程序的 `generate` 方法中进行。

@[code lang=java transcludeWith=:::datagen-recipes:provider](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModRecipeProvider.java)

要完成设置，将此提供程序添加到 `onInitializeDataGenerator` 方法中的 `DataGeneratorEntrypoint`。

@[code lang=java transclude={32-32}](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModDataGenerator.java)

## 无序配方 {#shapeless-recipes}

无序配方相当的简单。 只需将它们添加到提供程序中的 `generate` 方法中：

@[code lang=java transcludeWith=:::datagen-recipes:shapeless](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModRecipeProvider.java)

## 有序配方 {#shaped-recipes}

对于有序配方，可以使用 `String` 定义有序，然后定义 `String` 中每个 `char` 代表什么。

@[code lang=java transcludeWith=:::datagen-recipes:shaped](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModRecipeProvider.java)

:::tip
有很多辅助方法可用于创建普通配方。 查看 `RecipeProvider` 提供的内容！ 在 IntelliJ 中按 `Alt + 7` 打开类的结构，其中包括方法列表。
:::

## 其他配方 {#other-recipes}

其他配方的工作原理类似，但需要一些额外的参数。 比如烧炼配方需要了解奖励多少经验。

@[code lang=java transcludeWith=:::datagen-recipes:other](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModRecipeProvider.java)

## 自定义配方类型 {#custom-recipe-types}
