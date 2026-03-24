---
title: 标签生成
description: 使用 Datagen 设置标签生成的指南。
authors:
  - CelDaemon
  - IMB11
  - skycatminepokie
  - Spinoscythe
authors-nogithub:
  - mcrafterzz
---

<!---->

:::info 前置条件

请确保你已经完成[数据生成器设置](./setup)章节。

:::

## 设置 {#setup}

在这里我们将展示如何创建 `Item` 标签，但同样的原则对其他场景也适用。

Fabric 提供了几个辅助标签提供程序，其中一个用于物品，`FabricTagProvider.ItemTagProvider`。 我们将使用这个辅助类作为这个例子。

您可以创建自己的类来继承 `FabricTagProvider<T>`，其中 `T` 是您想要为其提供标签的类型。 这是你的**提供程序**。

让你的 IDE 填充所需的代码，然后用你的类型的 `ResourceKey` 替换 `resourceKey` 构造函数参数：

@[code lang=java transcludeWith=:::datagen-tags:provider](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModItemTagProvider.java)

::: tip

你需要为每种类型的标签提供不同的提供程序（例如，一个 `FabricTagProvider<EntityType<?>>` 和一个 `FabricTagProvider<Item>`）。

:::

接着在 `DataGeneratorEntrypoint` 入口点的 `onInitializeDataGenerator` 方法里注册这个类。

@[code lang=java transcludeWith=:::datagen-tags:register](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModDataGenerator.java)

## 创建标签 {#creating-a-tag}

现在你创建了提供程序，让我们为其添加一个标签。 首先，创建一个 `TagKey<T>`：

@[code lang=java transcludeWith=:::datagen-tags:tag-key](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModItemTagProvider.java)

接下来，在提供程序的 `configure` 方法中调用 `valueLookupBuilder`。 自那里，你可以添加单个物品，添加其他标签，或用此标签替换预先存在的标签。

如果想添加标签，使用 `addOptionalTag`，因为标签的内容可能不会在 datagen 期间加载。 如果你确定标签已加载，调用 `addTag`。

要强制添加标签并忽略损坏的格式，使用 `forceAddTag`。

@[code lang=java transcludeWith=:::datagen-tags:build](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModItemTagProvider.java)
