---
title: 进度生成
description: 使用数据生成器设置进度生成的指南。
authors:
  - CelDaemon
  - MattiDragon
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

首先，我们需要创建 Provider。 创建一个继承 `FabricAdvancementProvider` 的类，并填入基本方法：

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModAdvancementProvider.java#datagen_advancements_provider_start

接着在 `DataGeneratorEntrypoint` 入口点的 `onInitializeDataGenerator` 方法里注册这个类。

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModDataGenerator.java#datagen_advancements_register

## 进度结构 {#advancement-structure}

进度由几个不同的部分组成。 除了称为“准则”（criterion）的要求外，可能还有：

- `DisplayInfo`，告诉游戏如何向玩家显示进度。
- `AdvancementRequirements` 是一系列准则的列表，要求每个子列表中至少完成一项准则。
- `AdvancementRewards` 是玩家完成进度后获得的奖励。
- `Strategy` 告诉进度如何处理多个准则，以及
- 父级 `Advancement`，用于组织玩家在“进度”屏幕上看到的层次结构。

## 简单进度 {#simple-advancements}

这是一个获取泥土方块的简单进度：

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModAdvancementProvider.java#datagen_advancements_simple_advancement

:::details JSON 输出

<<< @/reference/latest/src/main/generated/data/example-mod/advancement/get_dirt.json

:::

## 父级 {#parent}

为了创建或扩展进度树，我们可以为进度设置父级。 为此，调用 `Advancement.Builder#parent(...)` 并传入父进度的引用。

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModAdvancementProvider.java#reference_parent

如果没有直接指向父进度的引用（例如使用原版进度作为父级），则可以使用标识符创建一个占位符。

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModAdvancementProvider.java#placeholder_parent

现在，你的进度应该会以树状结构显示在进度菜单中。

![进度树](/assets/develop/data-generation/advancement_tree.png)

## 多个准则 {#multiple-criteria}

为了给进度设置更高级的条件，我们可以多次调用 `Advancement.Builder#addCriteria(...)` 并添加额外的准则。

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModAdvancementProvider.java#multiple_criteria

默认情况下，所有准则都满足，进度才能完成。 我们可以通过提供不同的策略来改变此行为。

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModAdvancementProvider.java#requirements_strategy

## 奖励 {#rewards}

我们可以为进度附加奖励，玩家完成进度后即可获得奖励。 我们可以通过调用 `Advancement.Builder#rewards(...)` 并传入要添加的奖励来实现。

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModAdvancementProvider.java#experience_reward

还有其他多种奖励类型可供选择：

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModAdvancementProvider.java#reward_types

## 自定义准则 {#custom-criteria}

::: warning

虽然数据生成器可以放在客户端侧，但 `准则（Criterion）` 与 `谓词（Predicates）` 必须位于主源集（main source set）中（即同时存在于两侧），因为服务器需要触发并计算它们。

:::

### 定义 {#definitions}

**准则**（criterion/criteria）是指玩家可以做的事情（或可能发生在玩家身上的事情），这些事情可以被计入进度的达成。 游戏附带许多[准则](https://zh.minecraft.wiki/w/进度定义格式#可用准则触发器)，可以在 `net.minecraft.advancement.criterion` 包中找到。 一般来说，仅当你在游戏中实现自定义机制时才需要新的准则。

**条件**是根据准则来评估的。 只有满足所有相关条件时，准则才会被计入。 条件通常用谓词来表达。

**谓词**是一种接受值并返回 `boolean` 的东西。 例如，如果物品是钻石，则 `Predicate<Item>` 可能返回 `true`，而如果实体与村民不敌对，则 `Predicate<LivingEntity>` 可能返回 `true`。

### 创建自定义准则 {#creating-custom-criteria}

首先，我们需要实现一个新的机制。 让我们告诉玩家每次破坏方块时他们使用了什么工具。

<<< @/reference/latest/src/main/java/com/example/docs/advancement/ExampleModDatagenAdvancement.java#datagen_advancements_entrypoint

请注意，这个代码确实很烂。 `HashMap` 没有存储在任何持久位置，因此每次重新启动游戏时它都会被重置。 这只是为了展示 `Criterion`。 开始游戏并且试一下吧！

接下来，让我们创建自定义准则 `UseToolCriterion`。 它将需要自己的 `Conditions` 类来配合它，因此我们将同时创建它们：

<<< @/reference/latest/src/main/java/com/example/docs/advancement/UseToolCriterion.java#datagen_advancements_criterion_base

好多新名词！ 让我们慢慢分析它们：

- `UseToolCriterion` 是一个 `SimpleCriterionTrigger`，`Conditions` 可以应用于它。
- `Conditions` 有一个 `playerPredicate` 字段。 所有的 `Conditions` 都应有一个玩家谓词（技术上来讲是 LootContextPredicate\`）。
- `Conditions` 也有一个 `CODEC`。 这个 `Codec` 只是其一个字段 `playerPredicate` 的 codec，带有在它们之间进行转换的额外指令（`xmap`）。

::: info

要了解有关 codec 的更多信息，请参阅 [Codec](../codecs) 页面。

:::

我们需要一种方法来检查条件是否满足。 因此我们得向 `Conditions` 添加辅助方法：

<<< @/reference/latest/src/main/java/com/example/docs/advancement/UseToolCriterion.java#datagen_advancements_conditions_test

现在我们已经有了准则及其条件，我们需要一种触发它的方式。 为 `UseToolCriterion` 添加一个触发方法：

<<< @/reference/latest/src/main/java/com/example/docs/advancement/UseToolCriterion.java#datagen_advancements_criterion_trigger

快完成了！ 接下来，我们需要一个可以使用的准则实例。 我们把它放入一个名为 `ModCriteria` 的新类中。

<<< @/reference/latest/src/main/java/com/example/docs/advancement/ModCriteria.java#datagen_advancements_mod_criteria

为了确保我们的准则在正确的时间进行初始化，添加一个空白的 `init` 方法：

<<< @/reference/latest/src/main/java/com/example/docs/advancement/ModCriteria.java#datagen_advancements_mod_criteria_init

并在模组的初始化器中调用它：

<<< @/reference/latest/src/main/java/com/example/docs/advancement/ExampleModDatagenAdvancement.java#datagen_advancements_call_init

最后，我们需要触发我们的准则。 将其添加到我们在主模组类中向玩家发送消息的地方。

<<< @/reference/latest/src/main/java/com/example/docs/advancement/ExampleModDatagenAdvancement.java#datagen_advancements_trigger_criterion

你的崭新准则现已可供使用！ 现在把它添加到我们的 Provider 中：

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModAdvancementProvider.java#datagen_advancements_custom_criteria_advancement

再次运行数据生成器任务，你就可以获得新的进度了！

## 带参数的条件 {#conditions-with-parameters}

现在实现的进度看起来很不错，但是如果我们只想在完成 5 次准则之后才授予进度该怎么办呢？ 或者类似的需要多次达成某种准则才能完成的成就呢？ 为此，我们需要为条件提供一个参数。 你可以继续使用 `UseToolCriterion`，也可以使用新的 `ParameterizedUseToolCriterion`。 实践中，你应该只使用参数化版本，但在本教程中我们将保留这两个版本。

让我们自下而上地开始。 我们需要检查要求是否满足，因此让我们编辑 `Conditions#requirementsMet` 方法：

<<< @/reference/latest/src/main/java/com/example/docs/advancement/ParameterizedUseToolCriterion.java#datagen_advancements_new_requirements_met

`requiredTimes` 不存在，因此将其作为 `Conditions` 的一个参数：

<<< @/reference/latest/src/main/java/com/example/docs/advancement/ParameterizedUseToolCriterion.java#datagen_advancements_new_parameter

现在我们的 codec 在报错。 让我们为这次变更编写一个新的 codec：

<<< @/reference/latest/src/main/java/com/example/docs/advancement/ParameterizedUseToolCriterion.java#datagen_advancements_new_codec

接下来，我们需要修复我们的 `trigger` 方法：

<<< @/reference/latest/src/main/java/com/example/docs/advancement/ParameterizedUseToolCriterion.java#datagen_advancements_new_trigger

如果你制定了新准则，我们需要将其添加到 `ModCriteria`

<<< @/reference/latest/src/main/java/com/example/docs/advancement/ModCriteria.java#datagen_advancements_new_mod_criteria

然后在主类中调用它，就在原来的位置：

<<< @/reference/latest/src/main/java/com/example/docs/advancement/ExampleModDatagenAdvancement.java#datagen_advancements_trigger_new_criterion

将进度添加到 Provider ：

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModAdvancementProvider.java#datagen_advancements_new_custom_criteria_advancement

再次运行数据生成，就搞定了！

## 资源条件 {#resource-conditions}

要为数据生成的进度应用[资源条件](../resource-conditions)，将 consumer 用 `withConditions` 包围，并提供你需要应用的任何资源条件。 这会生成一个应用了资源条件的进度：

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModAdvancementProvider.java#datagen_advancements_conditions
