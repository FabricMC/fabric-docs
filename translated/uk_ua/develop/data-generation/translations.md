---
title: Генерація перекладу
description: Посібник із налаштування створення перекладу за допомогою datagen.
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

:::info ПЕРЕДУМОВИ

Спершу переконайтеся, що ви виконали процес [налаштування datagen](./setup).

:::

## Налаштування {#setup}

Спочатку ми створимо нашого **постачальника**. Пам’ятайте, що фактично генерують дані для нас постачальники. Створіть клас, який розширює `FabricLanguageProvider`, і заповніть базові методи:

@[code lang=java transcludeWith=:::datagen-translations:provider](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModEnglishLangProvider.java)

::: tip

Вам знадобиться окремий постачальник для кожної мови, яку ви хочете створити (наприклад, один `ExampleEnglishLangProvider` і один `ExamplePirateLangProvider`).

:::

Щоб завершити налаштування, додайте цього провайдера до своєї `DataGeneratorEntrypoint` у методі `onInitializeDataGenerator`.

@[code lang=java transcludeWith=:::datagen-translations:register](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModDataGenerator.java)

## Створення перекладу {#creating-translations}

Разом зі створенням необроблених перекладів, перекладів з `Identifier` і копіюванням їх з уже наявного файлу (передаючи `Path`), існують допоміжні методи для перекладу предметів, блоків, теґів, статистики, сутностей, ефектів, вкладок творчості, атрибути сутностей та зачарування. Просто викличте `add` у `translationBuilder` з тим, що ви хочете перекласти, і на що це має бути перекладено:

@[code lang=java transcludeWith=:::datagen-translations:build](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModEnglishLangProvider.java)

## Використання перекладу {#using-translations}

Згенеровані переклади замінюють багато перекладів, доданих в інших посібниках, але ви також можете використовувати їх усюди, де використовуєте об’єкт `Component`. У нашому прикладі, якщо ми хочемо дозволити пакетам ресурсів перекладати наше привітання, ми використовуємо `Component.translatable` замість `Component.literal`:

```java
ChatComponent chatHud = Minecraft.getInstance().gui.getChat();
chatHud.addMessage(Component.literal("Hello there!")); // [!code --]
chatHud.addMessage(Component.translatable("text.example-mod.greeting")); // [!code ++]
```
