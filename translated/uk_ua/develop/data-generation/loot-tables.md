---
title: Генерація таблиць здобичі
description: Посібник із налаштування генерації таблиць здобичі.
authors:
  - skycatminepokie
  - Spinoscythe
  - Alphagamer47
  - matthewperiut
  - JustinHuPrime
authors-nogithub:
  - mcrafterzz
  - jmanc3
---

:::info ПЕРЕДУМОВИ
Спершу переконайтеся, що ви виконали процес [налаштування datagen](./setup).
:::

Вам знадобляться різні постачальники (класи) для блоків, скринь і сутностей. Не забувайте додати їх усі до свого пакета у вашій `DataGeneratorEntrypoint` у методі `onInitializeDataGenerator`.

@[code lang=java transclude={32-33}](@/reference/latest/src/client/java/com/example/docs/datagen/FabricDocsReferenceDataGenerator.java)

## Пояснення таблиць здобичі {#loot-tables-explained}

**Таблиці здобичі** визначають, що ви отримуєте від добування блоку (не включаючи вміст, як у скринях), вбивства сутності або відкриття щойно згенерованого контейнера. Кожна таблиця здобичі має **пули**, з яких вибираються предмети. Таблиці здобичі також мають **функції**, які певним чином змінюють отриману здобич.

Пули здобичі мають **записи**, **умови**, функції, **кидки** та **бонусні кидки**. Записи — це групи, послідовності або можливості предметів, або просто предмети. Умови — це речі, які перевіряються у світі, як-от чари на знаряддях або випадковий шанс. Мінімальна кількість записів, вибраних пулом, називається кидками, а все, що перевищує цю кількість, називається бонусним кидком.

## Блоки {#blocks}

Щоб з блоків випадали предмети, включаючи себе, нам потрібно створити таблицю здобичі. Створіть клас, який `extends FabricBlockLootTableProvider`:

@[code lang=java transcludeWith=:::datagen-loot-tables:block-provider](@/reference/latest/src/client/java/com/example/docs/datagen/FabricDocsReferenceBlockLootTableProvider.java)

Обов’язково додайте цього постачальника до свого пакету!

Існує багато допоміжних методів, які допоможуть вам створити свої таблиці здобичі. Ми не будемо розглядати їх усі, тому обов’язково перевірте їх у своєму IDE.

Нумо додаймо якусь здобич у метод `generate`:

@[code lang=java transcludeWith=:::datagen-loot-tables:block-drops](@/reference/latest/src/client/java/com/example/docs/datagen/FabricDocsReferenceBlockLootTableProvider.java)

## Скрині {#chests}

Скриня здобичі трохи хитріша, ніж здобич блоків. Створіть клас, який `розширює SimpleFabricLootTableProvider`, подібно до прикладу нижче, **і додайте його до свого пакету**.

@[code lang=java transcludeWith=:::datagen-loot-tables:chest-provider](@/reference/latest/src/client/java/com/example/docs/datagen/FabricDocsReferenceChestLootTableProvider.java)

We'll need a `RegistryKey<LootTable>` for our loot table. Помістімо це в новий клас під назвою `ModLootTables`. Якщо ви використовуєте розділені джерела, переконайтеся, що це джерело є у вашому `main` наборі джерел.

@[code lang=java transcludeWith=:::datagen-loot-tables:mod-loot-tables](@/reference/latest/src/main/java/com/example/docs/ModLootTables.java)

Потім ми можемо створити таблицю здобичі всередині методу `generate` вашого постачальника.

@[code lang=java transcludeWith=:::datagen-loot-tables:chest-loot](@/reference/latest/src/client/java/com/example/docs/datagen/FabricDocsReferenceChestLootTableProvider.java)
