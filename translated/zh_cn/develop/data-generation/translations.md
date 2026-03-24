---
title: 翻译生成
description: 使用数据生成设置翻译生成的指南。
authors:
  - CelDaemon
  - IMB11
  - MattiDragon
  - skycatminepokie
  - Spinoscythe
authors-nogithub:
  - jmanc3
  - mcrafterzz
  - sjk1949
---

<!---->

:::info 前置条件

请确保你已经完成[数据生成器设置](./setup)章节。

:::

## 设置 {#setup}

首先，我们要创建**提供程序**。 请记住，提供程序才是为我们生成数据的。 创建一个继承 `FabricLanguageProvider` 的类，填入基本方法：

@[code lang=java transcludeWith=:::datagen-translations:provider](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModEnglishLangProvider.java)

::: tip

对于想要生成的每种语言，需要不同的提供程序（例如一个 `ExampleEnglishLangProvider` 还有一个 `ExamplePirateLangProvider`）。

:::

接着在 `DataGeneratorEntrypoint` 入口点的 `onInitializeDataGenerator` 方法里注册这个类。

@[code lang=java transcludeWith=:::datagen-translations:register](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModDataGenerator.java)

## 创建翻译 {#creating-translations}

除了创建原始翻译、来自 `Identifier` 的翻译以及从现有的文件复制（通过传递 `Path`）之外，还有用于翻译物品、方块、标签、统计数据、实体、状态效果、创造模式物品栏、实体属性和魔咒的辅助方法。 只需在 `translationBuilder` 上调用 `add`，添加你想要翻译的内容以及应该翻译成的内容：

@[code lang=java transcludeWith=:::datagen-translations:build](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModEnglishLangProvider.java)

## 使用翻译 {#using-translations}

生成的翻译取代了其他教程中添加的许多翻译，但你也可以在任何使用 `Component` 对象的地方使用。 在我们的示例中，如果我们想允许资源包翻译我们的问候语，我们使用 `Component.translatable` 而不是 \`Component.literal：

```java
ChatComponent chatHud = Minecraft.getInstance().gui.getChat();
chatHud.addMessage(Component.literal("Hello there!")); // [!code --]
chatHud.addMessage(Component.translatable("text.example-mod.greeting")); // [!code ++]
```
