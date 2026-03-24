---
title: 数据生成设置
description: 使用 Fabric API 设置数据生成的指南。
authors:
  - ArkoSammy12
  - Earthcomputer
  - haykam821
  - Jab125
  - matthewperiut
  - modmuss50
  - Shnupbups
  - skycatminepokie
  - SolidBlock-cn
authors-nogithub:
  - jmanc3
  - mcrafterzz
---

## 数据生成是什么？ {#what-is-data-generation}

数据生成 (又称 datagen) 是一种 API，用于以编程方式生成配方、进度、标签、物品模型、语言文件、战利品表以及基本上任何基于 JSON 的内容。

## 启用数据生成 {#enabling-data-generation}

### 在项目创建时 {#enabling-data-generation-at-project-creation}

启用数据生成的最简单方法是在创建项目时。 使用[模板生成器](https://fabricmc.net/develop/template/)时，勾选“启用数据生成”框。

![模板生成器上勾选的“数据生成”框](/assets/develop/data-generation/data_generation_setup_01.png)

::: tip

如果启用了数据生成，应该有一个“Data Generation”运行配置和一个 `runDatagen` Gradle 任务。

:::

### 手动 {#manually-enabling-data-generation}

首先，我们需要在 `build.gradle` 文件中启用 datagen。

@[code transcludeWith=:::datagen-setup:configure](@/reference/build.gradle)

接下来，我们需要一个入口点类。 这是我们的数据生成的起点。 将其放在 `client` 包中的某个位置——本示例将其放在 `src/client/java/com/example/docs/datagen/ExampleModDataGenerator.java`。

@[code lang=java transcludeWith=:::datagen-setup:generator](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModDataGenerator.java)

最后，我们需要告诉 Fabric 我们的 `fabric.mod.json` 中的入口点：

<!-- prettier-ignore -->

```json
{
  // ...
  "entrypoints": {
    // ...
    "client": [
      // ...
    ],
    "fabric-datagen": [ // [!code ++]
      "com.example.docs.datagen.ExampleModDataGenerator" // [!code ++]
    ] // [!code ++]
  }
}
```

::: warning

别忘了在前一个入口点方块后面加一个逗号（`,`）！

:::

关闭并重新打开 IntelliJ 以创建 datagen 的运行配置。

## 创建包 {#creating-a-pack}

在数据生成入口点的 `onInitializeDataGenerator` 方法中，我们需要创建一个 `Pack`。 稍后，你将添加**提供程序**（provider），将生成的数据放入此 `Pack` 中。

@[code lang=java transcludeWith=:::datagen-setup:pack](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModDataGenerator.java)

## 运行数据生成 {#running-data-generation}

要运行数据生成，请使用 IDE 中的运行配置，或者在控制台中运行 `./gradlew runDatagen`。 生成的文件将创建在 `src/main/generated` 中。

## 下一步 {#next-steps}

现在数据生成已设置完毕，我们需要添加**提供程序**（provider）。 这些提供程序将生成要添加到 `Pack` 的数据。 以下页面概述了如何执行此操作。

- [进度](./advancements)
- [战利品表](./loot-tables)
- [配方](./recipes)
- [标签](./tags)
- [翻译](./translations)
- [方块模型](./block-models)
- [物品模型](./item-models)
