---
title: Генерація таблиць здобичі
description: Посібник із налаштування генерації таблиць здобичі.
authors:
  - Alphagamer47
  - CelDaemon
  - JustinHuPrime
  - matthewperiut
  - skycatminepokie
  - Spinoscythe
authors-nogithub:
  - jmanc3
  - mcrafterzz
---

<!---->

:::info ПЕРЕДУМОВИ

Спершу переконайтеся, що ви виконали процес [налаштування datagen](./setup).

:::

Вам знадобляться різні постачальники (класи) для блоків, скринь і сутностей. Не забувайте додати їх усі до свого пакета у вашій `DataGeneratorEntrypoint` у методі `onInitializeDataGenerator`.

@[code lang=java transcludeWith=:::datagen-loot-tables:register](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModDataGenerator.java)

## Пояснення таблиць здобичі {#loot-tables-explained}

**Таблиці здобичі** визначають, що ви отримуєте від добування блока (не включаючи вміст, як у скринях), убивства сутності або відкриття щойно згенерованого вмістища. Кожна таблиця здобичі має **пули**, з яких вибираються предмети. Таблиці здобичі також мають **функції**, які певним чином змінюють отриману здобич.

Пули здобичі мають **записи**, **умови**, функції, **кидки** та **бонусні кидки**. Записи — це групи, послідовності або можливості предметів, або просто предмети. Умови — це речі, які перевіряються у світі, як-от зачарування на спорядженні або випадковий шанс. Мінімальна кількість записів, вибраних пулом, називається кидками, а все, що перевищує цю кількість, називається бонусним ролом.

## Блоки {#blocks}

Щоб з блоків випадали предмети, включаючи себе, нам потрібно створити таблицю здобичі. Створіть клас, який розширює `FabricBlockLootTableProvider`:

@[code lang=java transcludeWith=:::datagen-loot-tables:block-provider](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModBlockLootTableProvider.java)

Обов’язково додайте цього постачальника до свого пакета!

Існує багато допоміжних методів, які допоможуть вам створити свої таблиці здобичі. Ми не будемо розглядати їх усі, тому обов’язково перевірте їх у своєму IDE.

Нумо додаймо якусь здобич у метод `generate`:

@[code lang=java transcludeWith=:::datagen-loot-tables:block-drops](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModBlockLootTableProvider.java)

## Скрині {#chests}

Скриня здобичі трохи хитріша, ніж здобич блоків. Створіть клас, який розширює `SimpleFabricLootTableProvider`, подібно до прикладу нижче, **і додайте його до свого пакету**.

@[code lang=java transcludeWith=:::datagen-loot-tables:chest-provider](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModChestLootTableProvider.java)

We'll need a `ResourceKey<LootTable>` for our loot table. Помістімо це в новий клас під назвою `ModLootTables`. Якщо ви використовуєте розділені джерела, переконайтеся, що це джерело є у вашому `main` наборі джерел.

@[code lang=java transcludeWith=:::datagen-loot-tables:mod-loot-tables](@/reference/latest/src/main/java/com/example/docs/ModLootTables.java)

Потім ми можемо створити таблицю здобичі всередині методу `generate` вашого постачальника.

@[code lang=java transcludeWith=:::datagen-loot-tables:chest-loot](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModChestLootTableProvider.java)
