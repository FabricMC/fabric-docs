---
title: 资源条件
description: 让模组的数据有条件加载的指引。
authors:
  - cassiancc
resources:
  https://github.com/FabricMC/fabric-api/blob/26.1.2/fabric-data-generation-api-v1/src/testmod/java/net/fabricmc/fabric/test/datagen/DataGeneratorTestEntrypoint.java: Fabric API 的 Data Generation Test Mod
---

设计与其他模组的融合时，经常需要一种方式来定义模组的资源何时应被加载。 因此，Fabric API 提供了资源条件。

默认情况下，此 API 可用于配方、进度、战利品表、谓词和物品修饰器。

资源条件可以通过[数据生成](./data-generation/setup)添加，也可手动写 JSON。 关于更多如何通过数据生成添加资源条件的信息，请看数据生成的文档。

加载条件会加到 JSON 文件的根部。

:::details 带有条件的配方，仅在标签不为空时加载。

<<< @/reference/latest/src/main/generated/data/example-mod/recipe/sand.json

:::

## 内置条件 {#built-in}

Fabric API 提供了九种内置条件供你的模组使用。

### 运算符 {#operators}

这些是标准布尔运算符。

#### 真 {#true}

总是成功：

<<< @/reference/latest/src/main/generated/reports/example-mod/resource_condition_examples/true.json

#### 非 {#not}

反转 `value` 中指定的加载条件。 例如，以下内容将失败：

<<< @/reference/latest/src/main/generated/reports/example-mod/resource_condition_examples/not.json

#### 或 {#or}

若 `values` 中的条件至少有一个成功，则成功。 例如，以下内容将成功：

<<< @/reference/latest/src/main/generated/reports/example-mod/resource_condition_examples/or.json

#### 与 {#and}

若 `values` 中的每个条件都成功，则成功。 例如，以下内容将失败：

<<< @/reference/latest/src/main/generated/reports/example-mod/resource_condition_examples/and.json

### 模组全部加载 {#all-mods-loaded}

若 `values` 中的所有模组都已加载，则成功。 例如，仅当 `example-mod` 和 `another-mod` 都加载时，以下内容才会成功：

<<< @/reference/latest/src/main/generated/reports/example-mod/resource_condition_examples/all_mods_loaded.json

### 模组任一加载 {#any-mods-loaded}

若 `values` 中的模组至少有一个已加载，则成功。 例如，如果 `example-mod` 或 `another-mod`（或两者）加载，以下内容将成功：

<<< @/reference/latest/src/main/generated/reports/example-mod/resource_condition_examples/any_mods_loaded.json

### 标签非空 {#tags-populated}

若指定的注册表 `registry` 包含 `values` 中的所有标签，则成功。
例如，如果加载了物品标签 `example-mod:smelly_items`，以下内容将成功：

<<< @/reference/latest/src/main/generated/reports/example-mod/resource_condition_examples/tags_populated.json

### 功能启用 {#features-enabled}

若 `features` 中的所有[功能开关](https://minecraft.wiki/w/Experiments#Java_Edition)都已启用，则成功。 例如，如果 `minecraft:vanilla` 和 `minecraft:redstone_experiments` 都启用，以下内容将成功：

<<< @/reference/latest/src/main/generated/reports/example-mod/resource_condition_examples/features_enabled.json

### 注册表包含 {#registry-contains}

若注册表包含 `values` 中的所有标识符，则成功。 例如，如果注册表中存在 `minecraft:cobblestone`，以下内容将成功：

<<< @/reference/latest/src/main/generated/reports/example-mod/resource_condition_examples/registry_contains.json

## 自定义条件 {#custom-conditions}

:::info 前置知识

在设置自定义资源条件之前，你必须先了解[如何创建 Codec](./codecs)。

:::

Fabric API 还提供了创建自定义资源条件的灵活性。

为了演示这一点，我们将创建一个检查当前日期的条件。 这可用于万圣节或愚人节等节日的特殊行为。

### 准备你的条件 {#preparing-your-condition}

为了简化操作，我们将创建一个辅助方法，通过名称和 [`MapCodec`](./codecs#mapcodec) 来实例化你的资源条件。 你应该将此方法放在名为 `ModResourceConditions`（或你喜欢的任何名字）的类中。

::: tip

Fabric 在其内置条件中也执行了相同的操作；你可以参考 `DefaultResourceConditionTypes` 类来查看其实际应用。

:::

<<< @/reference/latest/src/main/java/com/example/docs/conditions/ModResourceConditions.java#create

### 创建你的条件 {#creating-your-condition}

一个资源条件由三部分组成：

- 一个接收数值的构造函数。
- 一个用于序列化这些数值的 `MapCodec`。
- 一个使用这些数值来确定条件是否应该通过的 `test` 方法。

我们将为资源条件创建一个名为 `DateMatchesResourceCondition` 的新类。 首先，创建一个接收代表月份的 `int` 和代表日期的 `int` 的新 `record`：

<<< @/reference/latest/src/main/java/com/example/docs/conditions/DateMatchesResourceCondition.java#record

接下来，添加一个反映构造函数所接收内容的 `MapCodec`：

<<< @/reference/latest/src/main/java/com/example/docs/conditions/DateMatchesResourceCondition.java#codec

:::details 什么是 `validate`？

该 Codec 使用 `.validate` 方法来确保提供的日期切实存在，其使用的是同样名为 `validate` 的辅助方法中的逻辑：

<<< @/reference/latest/src/main/java/com/example/docs/conditions/DateMatchesResourceCondition.java#validate

这仅与当前示例相关。

:::

接下来，我们将添加一个检查当前日期的 `test` 方法。 此示例基于原版自身 `SpecialDates` 中的逻辑。

<<< @/reference/latest/src/main/java/com/example/docs/conditions/DateMatchesResourceCondition.java#test

### 注册你的条件 {#registering-your-condition}

回到 `ModResourceConditions` 中，我们现在可以注册我们的资源条件了：

<<< @/reference/latest/src/main/java/com/example/docs/conditions/ModResourceConditions.java#register

此条件类型随后也可以在 `DateMatchesResourceCondition` 中被引用：

<<< @/reference/latest/src/main/java/com/example/docs/conditions/DateMatchesResourceCondition.java#type

请务必在你的[模组初始化器](./getting-started/project-structure#entrypoints)中调用 `ModResourceConditions.register`：

<<< @/reference/latest/src/main/java/com/example/docs/conditions/ExampleModResourceConditions.java#init

### 使用你的条件 {#using-your-condition}

现在，我们有了一个当系统日期与资源条件中提供的日期相匹配时便会成功的条件。 例如，此条件将仅在愚人节时成功：

<<< @/reference/latest/src/main/generated/reports/example-mod/resource_condition_examples/date_matches.json
