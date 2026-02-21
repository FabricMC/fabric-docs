---
title: 魔咒生成
description: 通过数据生成器生成魔咒的指南。
authors:
  - CelDaemon
---

<!---->

:::info 前置条件

请确保你已经完成[数据生成器设置](./setup)章节。

:::

## 设置 {#setup}

在接入生成器之前，在主源码集内添加`enchantment`包，然后创建一个`ModEnchantments`类。 接着在这个新类中添加`key`方法。

@[code transcludeWith=:::key-helper](@/reference/latest/src/main/java/com/example/docs/enchantment/ModEnchantments.java)

使用此方法为你的魔咒创建一个`ResourceKey`。

@[code transcludeWith=:::register-enchantment](@/reference/latest/src/main/java/com/example/docs/enchantment/ModEnchantments.java)

现在，我们已经准备好添加生成器了。 在数据生成器包里，创建一个继承`FabricDynamicRegistryProvider`的类。 在这个新创建的类里，添加一个匹配`super`的构造函数，并实现`configure`和`getName`方法。

@[code transcludeWith=:::provider](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModEnchantmentGenerator.java)

接着，在新建的类中添加`register`辅助方法。

@[code transcludeWith=:::register-helper](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModEnchantmentGenerator.java)

现在添加 `bootstrap` 方法。 在这里，我们将注册想要添加到游戏中的魔咒。

@[code transcludeWith=:::bootstrap](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModEnchantmentGenerator.java)

在你的 `DataGeneratorEntrypoint` 类中，重写 `buildRegistry` 方法并注册我们的 bootstrap 方法。

@[code transcludeWith=:::datagen-enchantments:bootstrap](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModDataGenerator.java)

最后，确保在 `onInitializeDataGenerator` 方法中注册了你的新生成器。

@[code transcludeWith=:::datagen-enchantments:register](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModDataGenerator.java)

## 创建魔咒 {#creating-the-enchantment}

为了创建自定义魔咒的定义，我们将使用生成器类中的 `register` 方法。

在生成器的 `bootstrap` 方法中注册你的魔咒，并调用 `ModEnchantments` 中已注册的魔咒。

在本例中，我们将使用[自定义魔咒效果](../items/custom-enchantment-effects)中创建的魔咒效果，但你也可以使用[原版魔咒效果](https://zh.minecraft.wiki/w/%E9%AD%94%E5%92%92%E5%AE%9A%E4%B9%89%E6%A0%BC%E5%BC%8F#%E9%AD%94%E5%92%92%E6%95%88%E6%9E%9C%E7%BB%84%E4%BB%B6)。

@[code transcludeWith=:::register-enchantment](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModEnchantmentGenerator.java)

现在只需运行数据生成，你的新魔咒即可在游戏中使用！

## 效果条件 {#effect-conditions}

大多数魔咒效果类型都是条件性效果。 在添加这些效果时，可以向 `withEffect` 调用传递相应的条件。

::: info

若要概览可用的条件类型及其用法，请参阅[`Enchantments` 类](https://mcsrc.dev/#1/1.21.11_unobfuscated/net/minecraft/world/item/enchantment/Enchantments#L126)。

:::

@[code transcludeWith=:::effect-conditions](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModEnchantmentGenerator.java)

## 多重效果 {#multiple-effects}

可以通过链式调用 `withEffect` 来为一个魔咒添加多种魔咒效果。 不过，这种方法要求你为每个效果单独指定效果条件。

若要在多个效果之间共享已定义的条件和目标，可使用`AllOf`将它们合并为单个效果。

@[code transcludeWith=:::multiple-effects](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModEnchantmentGenerator.java)

注意，使用方法取决于所添加效果的类型。 例如，`EnchantmentValueEffect`需要使用`AnyOf.valueEffects`。 不同类型的效果仍需额外调用`withEffect`。

## 附魔台 {#enchanting-table}

虽然我们在魔咒定义中已指定了魔咒权重（或几率），但默认情况下它不会出现在附魔台中。 若要使该魔咒能被村民交易并出现在附魔台中，需将其添加至`non_treasure`标签。

为了实现这一点，我们可以创建一个标签提供者。 在`datagen`包中创建一个继承自 `FabricTagProvider<Enchantment>` 的类。 随后实现构造函数，将`Registries.ENCHANTMENT`作为`registryKey`参数传递给`super`，并创建`addTags`方法。

@[code transcludeWith=:::provider](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModEnchantmentTagProvider.java)

现在，我们可以通过在`addTags`方法内调用构建器，将我们的魔咒添加至 `EnchantmentTags.NON_TREASURE`。

@[code transcludeWith=:::non-treasure-tag](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModEnchantmentTagProvider.java)

## 诅咒 {#curses}

诅咒同样通过标签实现。 我们可以复用["附魔台"章节](#enchanting-table)中的标签提供者。

在`addTags`方法中，只需将你的魔咒添加至`CURSE`标签，即可将其标记为诅咒。

@[code transcludeWith=:::curse-tag](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModEnchantmentTagProvider.java)
