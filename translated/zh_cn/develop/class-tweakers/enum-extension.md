---
title: 枚举扩展
description: 学习如何使用 Mixin 和类调整器向枚举添加条目。
authors:
  - cassiancc
  - CelDaemon
  - its-miroma
  - Jab125
  - LlamaLad7
  - MildestToucan
---

枚举扩展是一项 Mixin 功能，可用于可靠地向枚举添加新的条目。

在以 Minecraft 枚举为目标时，你可以将 mixin 与[类调整](../class-tweakers)结合使用，使新的枚举条目显示在反编译源码中。 如果该修改被设置为[传递性](../class-tweakers/index#transitive-entries)的，那么依赖你的模组的其他模组也能看到你添加的条目。

::: warning

枚举扩展要求至少使用 Loader 0.19.0 以获得 Mixin 支持，并且至少使用 Loom 1.16 以获得类调整器支持。

除此之外，若要使用枚举扩展，类调整器文件头部必须将版本指定为 `v2`。

:::

## 创建 Mixin {#creating-the-mixin}

在创建 Mixin 类之前，请确保你的 `fabric.mod.json` 文件中显式依赖了 Loader 0.19.0 或更高版本：

```json:no-line-numbers
...
"depends": {
  ...
  "fabricloader": ">=0.19.0"
  ...
}
...
```

即使你已经在 Gradle 依赖中使用了正确的 Loader 版本，也必须显式声明至少依赖 0.19.0，才能启用这一 Mixin 功能。

若要创建枚举扩展，请在你的 mixin 包中创建一个 `enum`，为其添加 `@Mixin` 注解，并像向目标枚举类本身添加常量一样，在其中添加你的常量。 例如，我们向 `RecipeBookType` 添加一个新条目：

<<< @/reference/latest/src/main/java/com/example/docs/mixin/class_tweakers/RecipeBookTypeMixin.java#enum-extension-no-impls-example-mixin

:::warning 重要

你应当始终为自己添加的枚举常量加上你的模组 ID 前缀，以确保其唯一性。 在这些文档中，我们将使用 `EXAMPLE_MOD_`。

:::

### 传入构造函数参数 {#passing-constructor-arguments}

如果目标枚举没有默认构造函数，你必须 shadow 一个目标类的构造函数，并在声明新增条目时传入所需参数。

例如，我们来添加一个新的 `RecipeCategory` 条目。 创建一个与目标类中所需构造函数匹配的构造函数，并使用 `@Shadow` 标注它。

<<< @/reference/latest/src/main/java/com/example/docs/mixin/class_tweakers/RecipeCategoryMixin.java#enum-extension-ctor-impls-example-mixin

### 实现抽象方法 {#implementing-abstract-methods}

若要实现目标枚举中的抽象方法，请先 shadow 该抽象方法，然后在你添加的条目中重写并实现它。 例如，我们来添加一个新的 `ConversionType` 条目：

<<< @/reference/latest/src/main/java/com/example/docs/mixin/class_tweakers/ConversionTypeMixin.java#enum-extension-abstract-method-impls-example-mixin

## 创建类调整器条目 {#making-the-class-tweaker-entry}

如果你的目标是 Minecraft 枚举，可以使用类调整器条目在反编译源码中可见地修改目标枚举。

若要启用这一功能，请记得使用 Loom 1.16 或更高版本，并将[文件头部版本](../class-tweakers/index#file-format)设置为 `v2`。

枚举扩展条目的语法如下：

```:no-line-numbers
extend-enum  <targetClassName>  <ENUM_CONSTANT_NAME>
```

在类调整中，类使用它们的[内部名称](../mixins/bytecode#class-names)。

例如，对于我们在 [Mixin 章节](#creating-the-mixin)中添加的 `RecipeBookType` 常量，其类调整器条目如下：

<<< @/reference/latest/src/main/resources/example-mod.classtweaker#enum-extension-no-impls-example-entry{classtweaker:no-line-numbers}

## 应用更改 {#applying-changes}

在反编译源码中看到你添加的枚举条目之前，你需要刷新 Gradle 项目并[重新生成源码](../getting-started/generating-sources)。
如果修改没有出现，可以尝试[验证](../class-tweakers/index#validating-the-file)该文件，并检查是否有错误。

::: info

你不会在反编译源码中看到[传入的构造函数参数](#passing-constructor-arguments)、[方法实现](#implementing-abstract-methods)或其他相关元素。
这是因为这些内容由 Mixin 处理，并且只会在运行时应用。

:::

现在，你可以在代码中使用该枚举常量：

<<< @/reference/latest/src/main/java/com/example/docs/enum_extension/ExampleModEnumExtension.java#enum-extension-added-constant-usage-example

如果你只是通过 Mixin 添加了该常量，而它并不存在于反编译源码中，则可以通过比较名称来判断它：

<<< @/reference/latest/src/main/java/com/example/docs/enum_extension/ExampleModEnumExtension.java#enum-extension-added-constant-no-ct-usage-example-check

如果你需要在多个地方使用该常量，可以调用 `valueOf` 获取它，并将结果存储在一个字段中：

<<< @/reference/latest/src/main/java/com/example/docs/enum_extension/ExampleModEnumExtension.java#enum-extension-added-constant-no-ct-usage-example-store

## 注意事项 {#pitfalls}

枚举扩展无法保证你添加的条目不会破坏任何内容。

你有责任检查目标枚举的使用情况，并尽可能避免问题。 如果某些问题无法解决并导致崩溃，最好完全不要使用枚举扩展。

本节会介绍一些在扩展枚举时需要留意并尽量避免的模式，但并不涵盖所有情况。

### switch 表达式 {#switch-expressions}

switch 语句常用于处理枚举常量。 因此，如果某个 switch 表达式没有处理其他模组添加的条目，就可能发生崩溃。 例如，假设我们有以下 switch 表达式：

<<< @/reference/latest/src/main/java/com/example/docs/enum_extension/ExampleModEnumExtension.java#enum-extension-problematic-switch-expr-example

请注意，这里没有 `default` 分支。 即使我们已经处理了原版枚举中的所有值以及我们自己添加的值，如果另一个模组添加了不同的条目，这里仍然会抛出异常。

如何避免这种情况？ 并不存在一种通用方法可以避免所有崩溃，你的处理方式应根据具体情况调整。 不过一般来说：

- 如果 `switch` 表达式位于原版方法中，可以使用 Mixin 对其进行修改
- 如果 `switch` 表达式来自某个模组，应尝试联系其开发者，共同制定兼容方案。 否则，你可能需要为另一个模组创建 Mixin

### 被序列化的枚举 {#serialized-enums}

某些枚举的条目会被自动序列化。 `Axolotl` 类中的 `Variants` 枚举就是一个例子。

扩展这些枚举会使你的自定义条目在 Minecraft 的命名空间下被序列化；在某些版本中，这甚至可能基于数字 ID 进行。
这并不理想，因为它可能会影响所有其他条目的索引。

如果某个枚举的条目会以这种方式被序列化，最好完全避免扩展该枚举。 相反，你可以考虑寻找可用的 API。
