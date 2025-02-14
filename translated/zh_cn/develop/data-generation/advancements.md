---
title: 进度生成
description: 一份如何使用 datagen 生成进度的指南。
authors:
  - skycatminepokie
  - MattiDragon
  - Spinoscythe
authors-nogithub:
  - jmanc3
  - mcrafterzz
---

:::info 前提
首先，请确保你已完成 [Datagen 设置](./setup) 。
:::

## 设置 {#setup}

首先，我们需要创建 Provider。 创建一个继承 `FabricAdvancementProvider` 的类，并填入基本方法：

@[code lang=java transcludeWith=:::datagen-advancements:provider-start](@/reference/latest/src/client/java/com/example/docs/datagen/FabricDocsReferenceAdvancementProvider.java)

要完成设置，请将此提供程序添加到 `onInitializeDataGenerator` 方法中的 `DataGeneratorEntrypoint`。

@[code lang=java transclude={25-25}](@/reference/latest/src/client/java/com/example/docs/datagen/FabricDocsReferenceDataGenerator.java)

## 进度结构{#advancement-structure}

一项进度是由几个不同的部分组成的。 除了称为“准则”的要求外，它可能还具有：

- `AdvancementDisplay` 告诉游戏如何向玩家展示进度，
- `AdvancementRequirements` 是一系列准则的列表，要求每个子列表中至少完成一项准则，
- `AdvancementRewards` 是玩家完成进度后获得的奖励。
- `CriterionMerger` 告诉进度如何处理多个准则，以及
- 父级 `Advancement`，用于组织您在“进度”屏幕上看到的层次结构。

## 简单进度{#simple-advancements}

以下是获取土块的简单进度：

@[code lang=java transcludeWith=:::datagen-advancements:simple-advancement](@/reference/latest/src/client/java/com/example/docs/datagen/FabricDocsReferenceAdvancementProvider.java)

:::warning
当构建你的进度条目时，请记住函数接受 `String` 格式的进度的 `Identifier`！
:::

:::details JSON 输出
@[code lang=json](@/reference/latest/src/main/generated/data/fabric-docs-reference/advancement/get_dirt.json)
:::

## 另一个示例{#one-more-example}

为了掌握要领，我们再添加一项进度。 我们将练习添加奖励、使用多项准则以及指定父级：

@[code lang=java transcludeWith=:::datagen-advancements:second-advancement](@/reference/latest/src/client/java/com/example/docs/datagen/FabricDocsReferenceAdvancementProvider.java)

## 自定义准则{#custom-criteria}

:::warning
虽然 datagen 可以在客户端，但是 `Criterion` 和 `Predicate` 位于主源集（双方）中，因为服务器需要触发和评估它们。
:::

### 定义{#definitions}

**准则**（英语：criterion/criteria）是指玩家可以做的事情（或可能发生在玩家身上的事情），这些事情可以被计入进度的达成。 游戏附带许多[准则](https://zh.minecraft.wiki/w/%E8%BF%9B%E5%BA%A6%E5%AE%9A%E4%B9%89%E6%A0%BC%E5%BC%8F#%E5%87%86%E5%88%99%E8%A7%A6%E5%8F%91%E5%99%A8)，可以在 `net.minecraft.advancement.criterion` 包中找到。 一般来说，仅当您在游戏中实现自定义机制时才需要新的准则。

**条件**是根据准则来评估的。 只有满足所有相关条件时，准则才会被计入。 条件通常用谓词来表达。

**谓词**是一种接受值并返回 `boolean` 的东西。 例如，如果物品是钻石，则 `Predicate<Item>` 可能返回 `true`，而如果实体与村民不敌对，则 `Predicate<LivingEntity>` 可能返回 `true`。

### 创建自定义准则{#creating-custom-criteria}

首先，我们需要实现一个新的机制。 让我们告诉玩家每次破坏方块时他们使用了什么工具。

@[code lang=java transcludeWith=:::datagen-advancements:entrypoint](@/reference/latest/src/main/java/com/example/docs/advancement/FabricDocsReferenceDatagenAdvancement.java)

请注意，这个代码确实很烂。 `HashMap` 没有存储在任何持久位置，因此每次重新启动游戏时它都会被重置。 这只是为了展示 `Criterion`。 开始游戏并且试一下吧！

接下来，让我们创建自定义准则 `UseToolCriterion`。 它将需要自己的 `Conditions` 类来配合它，因此我们将同时创建它们：

@[code lang=java transcludeWith=:::datagen-advancements:criterion-base](@/reference/latest/src/main/java/com/example/docs/advancement/UseToolCriterion.java)

哇，好多呀！ 让我们分解一下。

- `UseToolCriterion` 是一个 `AbstractCriterion`，`Conditions` 可以应用于它。
- `Conditions` 有一个 `playerPredicate` 字段。 所有的 `Conditions` 都应有一个玩家谓词（技术上来讲是 LootContextPredicate\`）。
- `Conditions` 也有一个 `CODEC`。 这个 `Codec` 只是其一个字段 `playerPredicate` 的 codec，带有在它们之间进行转换的额外指令（`xmap`）。

:::info
要了解有关 codec 的更多信息，请参阅 [Codec](../codecs) 页面。
:::

我们需要一种方法来检查条件是否满足。 我们向 `Conditions` 添加一个辅助方法：

@[code lang=java transcludeWith=:::datagen-advancements:conditions-test](@/reference/latest/src/main/java/com/example/docs/advancement/UseToolCriterion.java)

现在我们已经有了准则及其条件，我们需要一种触发它的方式。 为 `UseToolCriterion` 添加一个触发方法：

@[code lang=java transcludeWith=:::datagen-advancements:criterion-trigger](@/reference/latest/src/main/java/com/example/docs/advancement/UseToolCriterion.java)

快完成了！ 接下来，我们需要一个可以使用的准则实例。 我们把它放入一个名为 `ModCriteria` 的新类中。

@[code lang=java transcludeWith=:::datagen-advancements:mod-criteria](@/reference/latest/src/main/java/com/example/docs/advancement/ModCriteria.java)

为了确保我们的准则在正确的时间进行初始化，添加一个空白的 `init` 方法：

@[code lang=java transcludeWith=:::datagen-advancements:mod-criteria-init](@/reference/latest/src/main/java/com/example/docs/advancement/ModCriteria.java)

并在你的模组初始化程序中调用它：

@[code lang=java transcludeWith=:::datagen-advancements:call-init](@/reference/latest/src/main/java/com/example/docs/advancement/FabricDocsReferenceDatagenAdvancement.java)

最后，我们需要触发我们的准则。 将其添加到我们在主模组类中向玩家发送消息的地方。

@[code lang=java transcludeWith=:::datagen-advancements:trigger-criterion](@/reference/latest/src/main/java/com/example/docs/advancement/FabricDocsReferenceDatagenAdvancement.java)

你的崭新准则现已可供使用！ 我们将其添加到我们的提供程序中：

@[code lang=java transcludeWith=:::datagen-advancements:custom-criteria-advancement](@/reference/latest/src/client/java/com/example/docs/datagen/FabricDocsReferenceAdvancementProvider.java)

再次运行 datagen 任务，您就可以获得新的进度了！

## 带参数的条件{#conditions-with-parameters}

这一切都很好，但是如果我们只想在做了 5 次之后才授予进度该怎么办呢？ 那为什么不再来一个 10 次的呢？ 为此，我们需要为条件提供一个参数。 您可以继续使用 `UseToolCriterion`，也可以遵循新的 `ParameterizedUseToolCriterion`。 实际上，您应该只拥有一个参数化版本，但在本教程中我们将保留这两个版本。

让我们自下而上地开展工作。 我们需要检查要求是否满足，因此让我们编辑 `Conditions#requirementsMet` 方法：

@[code lang=java transcludeWith=:::datagen-advancements:new-requirements-met](@/reference/latest/src/main/java/com/example/docs/advancement/ParameterizedUseToolCriterion.java)

`requiredTimes` 不存在，因此将其作为 `Conditions` 的一个参数：

@[code lang=java transcludeWith=:::datagen-advancements:new-parameter](@/reference/latest/src/main/java/com/example/docs/advancement/ParameterizedUseToolCriterion.java)

现在我们的 codec 在报错。 让我们为新的变更编写一个新的 codec：

@[code lang=java transcludeWith=:::datagen-advancements:new-codec](@/reference/latest/src/main/java/com/example/docs/advancement/ParameterizedUseToolCriterion.java)

接下来，我们需要修复我们的 `trigger` 方法：

@[code lang=java transcludeWith=:::datagen-advancements:new-trigger](@/reference/latest/src/main/java/com/example/docs/advancement/ParameterizedUseToolCriterion.java)

如果你制定了新准则，我们需要将其添加到 `ModCriteria`

@[code lang=java transcludeWith=:::datagen-advancements:new-mod-criteria](@/reference/latest/src/main/java/com/example/docs/advancement/ModCriteria.java)

然后在主类中调用它，就在原来的位置：

@[code lang=java transcludeWith=:::datagen-advancements:trigger-new-criterion](@/reference/latest/src/main/java/com/example/docs/advancement/FabricDocsReferenceDatagenAdvancement.java)

将进度添加到您的提供程序：

@[code lang=java transcludeWith=:::datagen-advancements:new-custom-criteria-advancement](@/reference/latest/src/client/java/com/example/docs/datagen/FabricDocsReferenceAdvancementProvider.java)

再次运行 datagen，就搞定了！
