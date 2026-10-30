---
title: 标签生成
description: 使用 Datagen 设置标签生成的指南。
authors:
  - cassiancc
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

Fabric 提供了几个辅助标签提供程序，其中一个用于物品，`FabricTagsProvider.ItemTagsProvider`。我们将使用这个辅助类作为这个例子。

你可以创建自己的类来继承 `FabricTagsProvider<T>`，其中 `T` 是你想要为其提供标签的类型。这是你的**提供程序**。

让你的 IDE 填充所需的代码，然后用你的类型的 `ResourceKey` 替换 `resourceKey` 构造函数参数：

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModItemTagProvider.java#datagen_tags_provider

::: tip

你需要为每种类型的标签提供不同的提供程序（例如，一个 `FabricTagsProvider<EntityType<?>>` 和一个 `FabricTagsProvider<Item>`）。

:::

接着在 `DataGeneratorEntrypoint` 入口点的 `onInitializeDataGenerator` 方法里注册这个类。

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModDataGenerator.java#datagen_tags_register

## 创建标签 {#creating-a-tag}

现在你创建了提供程序，让我们为其添加一个标签。首先，创建一个 `TagKey<T>`：

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModItemTagProvider.java#datagen_tags_tag_key

接下来，在提供程序的 `configure` 方法中调用 `builder`。自那里，你可以添加单个物品 ID，添加其他标签，或用此标签替换预先存在的标签。

如果想添加标签，使用 `addOptionalTag`，因为标签的内容可能不会在 datagen 期间加载。如果你确定标签已加载，调用 `addTag`。

要强制添加标签并忽略损坏的格式，使用 `forceAddTag`。

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModItemTagProvider.java#datagen_tags_build

## 翻译标签 {#translating-tags}

你所创建的标签应当包含翻译，以便其他模组（例如配方查看类模组）能够正确显示该标签的名称。如果您的某个标签缺少翻译，您可能会在日志中看到类似以下内容的警告：

> Dev warning - Untranslated Item Tags detected. Please translate your item tags so other mods such as recipe viewers can properly display your tag's name.

我们可以按照[翻译生成指南](./translations)为我们的标签添加译文。我们可以使用同一个翻译构建器，并传入 `TagKey` 以及显示名称：

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModEnglishLangProvider.java#tag_name
