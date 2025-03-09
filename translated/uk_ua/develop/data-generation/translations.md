---
title: Генерація перекладу
description: Посібник із налаштування створення перекладу за допомогою datagen.
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

:::info ПЕРЕДУМОВИ
Спершу переконайтеся, що ви виконали процес [налаштування datagen](./setup).
:::

## Налаштування {#setup}

Спочатку ми створимо нашого **постачальника**. Пам’ятайте, що фактично генерують дані для нас постачальники. Створіть клас, який `extends FabricLanguageProvider`, і заповніть базові методи:

@[code lang=java transcludeWith=:::datagen-translations:provider](@/reference/latest/src/client/java/com/example/docs/datagen/FabricDocsReferenceEnglishLangProvider.java)

:::tip
Вам знадобиться окремий постачальник для кожної мови, яку ви хочете створити (наприклад, один `ExampleEnglishLangProvider` і один `ExamplePirateLangProvider`).
:::

Щоб завершити налаштування, додайте цього постачальника до своєї `DataGeneratorEntrypoint` у методі `onInitializeDataGenerator`.

@[code lang=java transclude={28-28}](@/reference/latest/src/client/java/com/example/docs/datagen/FabricDocsReferenceDataGenerator.java)

## Створення перекладу {#creating-translations}

Разом зі створенням необроблених перекладів, перекладів з `Identifier` і копіюванням їх з уже існуючого файлу (передаючи `Path`), існують допоміжні методи для перекладу предметів, блоків, теґів, статистики, сутностей, статус ефектів, групи предметів, атрибути сутностей та зачарування. Просто викличте `add` у `translationBuilder` з тим, що ви хочете перекласти, і на що це має бути перекладено:

@[code lang=java transcludeWith=:::datagen-translations:build](@/reference/latest/src/client/java/com/example/docs/datagen/FabricDocsReferenceEnglishLangProvider.java)

## Використання перекладу {#using-translations}

Згенеровані переклади замінюють багато перекладів, доданих в інших посібниках, але ви також можете використовувати їх усюди, де використовуєте об’єкт `Текст`. У нашому прикладі, якщо ми хочемо дозволити пакетам ресурсів перекладати наше привітання, ми використовуємо `Text.translatable` замість `Text.of`:

```java
ChatHud chatHud = MinecraftClient.getInstance().inGameHud.getChatHud();
chatHud.addMessage(Text.literal("Hello there!")); // [!code --]
chatHud.addMessage(Text.translatable("text.fabric_docs_reference.greeting")); // [!code ++]
```
