---
title: 战利品表生成
description: 使用 Datagen 设置战利品表生成的指南。
authors:
  - skycatminepokie
  - Spinoscythe
  - Alphagamer47
  - matthewperiut
  - JustinHuPrime
authors-nogithub:
  - mcrafterzz
  - jmanc3
---

:::info 前提
首先，请确保你已完成 [Datagen 设置](./setup) 。
:::

需要针对方块、箱子和实体提供不同的提供程序（类）。 请记住在 `onInitializeDataGenerator` 方法中的 `DataGeneratorEntrypoint` 中将它们全部添加到包中。

@[code lang=java transclude={32-33}](@/reference/latest/src/client/java/com/example/docs/datagen/FabricDocsReferenceDataGenerator.java)

## 战利品表详解{#loot-tables-explained}

**战利品表**定义了你破坏一个方块（不包括内容，如箱子里的东西）、杀死一个实体或打开一个新生成的容器所能得到的东西。 每个战利品表都有**随机池**，可从中选择物品。 战利品表还具有**函数**，可以通过某种方式修改最终的战利品。

战利品随机池有**抽取项（entries）**、**条件（conditions）**、函数（functions）、**抽取次数（rolls）**和**额外抽取次数（bonus rolls）**。 抽取项是物品的组、序列、可能性，或者仅仅是物品本身。 条件是在世界中需要被测试的事物，例如工具上的附魔或一个随机的概率。 随机池选择的最小抽取项数称为抽取次数（rolls），超过该数目的任何抽取项称为额外抽取次数（bonus rolls）。

## 方块{#blocks}

为了让方块掉落物品（包括本身），我们需要制作一个战利品表。 创建一个 `extends FabricBlockLootTableProvider` 的类：

@[code lang=java transcludeWith=:::datagen-loot-tables:block-provider](@/reference/latest/src/client/java/com/example/docs/datagen/FabricDocsReferenceBlockLootTableProvider.java)

确保将此提供程序添加到包中！

有很多辅助方法可用于帮助构建战利品表。 我们不会逐一介绍，因此请确保在您的 IDE 中检查它们。

我们在 `generate` 方法中添加一些掉落物：

@[code lang=java transcludeWith=:::datagen-loot-tables:block-drops](@/reference/latest/src/client/java/com/example/docs/datagen/FabricDocsReferenceBlockLootTableProvider.java)

## 箱子{#chests}

箱子的战利品比方块的战利品稍微复杂一些。 创建一个类似于下面示例的 `extends SimpleFabricLootTableProvider` 类**并将其添加到您的包中**。

@[code lang=java transcludeWith=:::datagen-loot-tables:chest-provider](@/reference/latest/src/client/java/com/example/docs/datagen/FabricDocsReferenceChestLootTableProvider.java)

我们需要一个 `RegistryKey<LootTable>` 作为战利品表。 我们把它放入一个名为 `ModLootTables` 的新类中。 如果你使用分割源，请确保它位于你的 `main` 源集中。

@[code lang=java transcludeWith=:::datagen-loot-tables:mod-loot-tables](@/reference/latest/src/main/java/com/example/docs/ModLootTables.java)

然后，我们可以在提供程序的 `generate` 方法中生成一个战利品表。

@[code lang=java transcludeWith=:::datagen-loot-tables:chest-loot](@/reference/latest/src/client/java/com/example/docs/datagen/FabricDocsReferenceChestLootTableProvider.java)
