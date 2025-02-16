---
title: 翻译生成
description: 使用 Datagen 设置翻译生成的指南。
authors:
  - skycatminepokie
  - MattiDragon
  - IMB11
  - Spinoscythe
authors-nogithub:
  - sjk1949
  - mcrafterzz
  - jmanc3
---

:::info 前提
首先，请确保你已完成 [Datagen 设置](./setup) 。
:::

## 设置{#setup}

首先，我们要创建**提供程序**。 请记住，提供程序才是为我们生成数据的。 创建一个 `extends FabricLanguageProvider` 的类，填入基本方法：

@[code lang=java transcludeWith=:::datagen-translations:provider](@/reference/latest/src/client/java/com/example/docs/datagen/FabricDocsReferenceEnglishLangProvider.java)

:::tip
对于想要生成的每种语言，需要不同的提供程序（例如一个 `ExampleEnglishLangProvider` 还有一个 `ExamplePirateLangProvider`）。
:::

要完成设置，将此提供程序添加到 `onInitializeDataGenerator` 方法中的 `DataGeneratorEntrypoint`。

@[code lang=java transclude={27-27}](@/reference/latest/src/client/java/com/example/docs/datagen/FabricDocsReferenceDataGenerator.java)

## 创建翻译{#creating-translations}

除了创建原始翻译、来自 `Identifier` 的翻译以及从现有的文件复制（通过传递 `Path`）之外，还有用于翻译物品、方块、标签、统计数据、实体、状态效果、物品组、实体属性和魔咒的辅助方法。 只需在 `translationBuilder` 上调用 `add`，添加你想要翻译的内容以及应该翻译成的内容：

@[code lang=java transcludeWith=:::datagen-translations:build](@/reference/latest/src/client/java/com/example/docs/datagen/FabricDocsReferenceEnglishLangProvider.java)

## 使用翻译{#using-translations}

生成的翻译取代了其他教程中添加的许多翻译，但你也可以在任何使用 `Text` 对象的地方使用。 在我们的示例中，如果我们想允许资源包翻译我们的问候语，我们使用 `Text.translatable` 而不是 `Text.of`：

```java
ChatHud chatHud = MinecraftClient.getInstance().inGameHud.getChatHud();
chatHud.addMessage(Text.literal("Hello there!")); // [!code --]
chatHud.addMessage(Text.translatable("text.fabric_docs_reference.greeting")); // [!code ++]
```
