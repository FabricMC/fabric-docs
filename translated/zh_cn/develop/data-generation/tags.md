---
title: 标签生成
description: 使用 Datagen 设置标签生成的指南。
authors:
  - IMB11
  - skycatminepokie
  - Spinoscythe
authors-nogithub:
  - mcrafterzz
---

:::info 前提
首先，请确保你已完成 [Datagen 设置](./setup) 。
:::

## 设置 {#setup}

首先，创建你自己的 `extends FabricTagProvider<T>` 类，其中 `T` 是您希望提供标签的类型。 这是你的**提供程序**。 在这里我们将展示如何创建 `Item` 标签，但同样的原则对其他场景也适用。 让你的 IDE 填充所需的代码，然后用你的类型的 `RegistryKey` 替换 `registryKey` 构造函数参数：

@[code lang=java transcludeWith=:::datagen-tags:provider](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModItemTagProvider.java)

:::tip
您需要为每种类型的标签提供不同的提供程序（例如，一个 `FabricTagProvider<EntityType<?>>` 和一个 `FabricTagProvider<Item>`）。
:::

要完成设置，将此提供程序添加到 `onInitializeDataGenerator` 方法中的 `DataGeneratorEntrypoint`。

@[code lang=java transclude={30-30}](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModDataGenerator.java)

## 创建标签 {#creating-a-tag}

现在你创建了提供程序，让我们为其添加一个标签。 首先，创建一个 `TagKey<T>`：

@[code lang=java transcludeWith=:::datagen-tags:tag-key](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModItemTagProvider.java)

接下来，在提供程序的 `configure` 方法中调用 `getOrCreateTagBuilder`。 自那里，你可以添加单个物品，添加其他标签，或用此标签替换预先存在的标签。

如果想添加标签，使用 `addOptionalTag`，因为标签的内容可能不会在 datagen 期间加载。 如果你确定标签已加载，调用 `addTag`。

要强制添加标签并忽略损坏的格式，使用 `forceAddTag`。

@[code lang=java transcludeWith=:::datagen-tags:build](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModItemTagProvider.java)
