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

Спершу переконайтеся, що ви виконали процес [налаштування генерації даних](./setup).

:::

Вам знадобляться різні постачальники (класи) для блоків, скринь і сутностей. Не забувайте додати їх усі до свого пакета у вашій `DataGeneratorEntrypoint` у методі `onInitializeDataGenerator`.

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModDataGenerator.java#datagen_loot_tables_register

## Пояснення таблиць здобичі {#loot-tables-explained}

**Таблиці здобичі** визначають, що ви отримуєте від добування блока (не включаючи вміст, як у скринях), убивства сутності або відкриття щойно згенерованого вмістища. Кожна таблиця здобичі має **пули**, з яких вибираються предмети. Таблиці здобичі також мають **функції**, які певним чином змінюють отриману здобич.

Пули здобичі мають **записи**, **умови**, функції, **кидки** та **бонусні крутки**. Записи — це групи, послідовності або можливості предметів, або просто предмети. Умови — це речі, які перевіряються у світі, як-от зачарування на спорядженні або випадковий шанс. Мінімальна кількість записів, вибраних пулом, називається кидками, а все, що перевищує цю кількість, називається бонусним ролом.

## Блоки {#blocks}

Щоб з блоків випадали предмети, включаючи себе, нам потрібно створити таблицю здобичі. Створіть клас, який розширює `FabricBlockLootTableProvider`:

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModBlockLootTableProvider.java#datagen_loot_tables_block_provider

Обов’язково додайте цього постачальника до свого пакета!

Існує багато допоміжних методів, які допоможуть вам створити свої таблиці здобичі. Ми не будемо розглядати їх усі, тому обов’язково перевірте їх у своєму IDE.

Нумо додаймо якусь здобич у метод `generate`:

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModBlockLootTableProvider.java#datagen_loot_tables_block_drops

## Скрині {#chests}

Скриня здобичі трохи хитріша, ніж здобич блоків. Створіть клас, який розширює `SimpleFabricLootTableProvider`, подібно до прикладу нижче, **і додайте його до свого пакета**.

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModChestLootTableProvider.java#datagen_loot_tables_chest_provider

Нам знадобиться `ResourceKey<LootTable>` для нашої таблиці здобичі. Помістімо це в новий клас під назвою `ModLootTables`. Якщо ви використовуєте розділені джерела, переконайтеся, що це джерело є у вашому `main` наборі джерел.

<<< @/reference/latest/src/main/java/com/example/docs/ModLootTables.java#datagen_loot_tables_mod_loot_tables

Потім ми можемо створити таблицю здобичі всередині методу `generate` вашого постачальника.

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModChestLootTableProvider.java#datagen_loot_tables_chest_loot

## Умови ресурсів {#resource-conditions}

Щоб застосувати [умову ресурсів](../resource-conditions) до згенерованої таблиці здобичі, викличте `withConditions` і вкажіть будь-які умови ресурсів, які ви хочете застосувати, а потім викликайте метод із постачальника таблиці здобичі, наприклад `dropSelf`. Тоді буде створено таблицю здобичі із застосованими умовами ресурсів:

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModBlockLootTableProvider.java#datagen_loot_tables_conditions
