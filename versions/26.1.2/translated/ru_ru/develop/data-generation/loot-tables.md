---
title: Генерация таблиц добычи
description: Руководство по настройке генерации таблицы добычи с помощью datagen.
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

:::info ТРЕБОВАНИЯ

Сначала убедитесь, что вы [установили datagen](./setup).

:::

Вам понадобятся разные провайдеры (классы) для блоков, сундуков и сущностей. Не забудьте добавить их все в свой пакет в `DataGeneratorEntrypoint` в методе `onInitializeDataGenerator`.

<<< @/reference/26.1.2/src/client/java/com/example/docs/datagen/ExampleModDataGenerator.java#datagen_loot_tables_register

## Как работают таблицы добычи {#loot-tables-explained}

**Таблицы добычи** определяют, что вы получите при разрушении блока (за исключением содержимого, например в сундуках), убийстве сущности или открытии вновь сгенерированного контейнера. Каждая таблица лута (loot table) состоит из **пулов** (pools), из которых выбираются предметы. Таблицы лута также содержат **функции** (functions), которые определенным образом изменяют финальный лут.

Пулы лута содержат **записи** (entries), **условия** (conditions), функции (functions), **броски** (rolls) и **бонусные броски** (bonus rolls). Записи представляют собой группы, последовательности или варианты предметов, либо просто одиночные предметы. Условия — это проверяемые в мире параметры, такие как зачарования на инструменте или случайный шанс выпадения. Минимальное количество записей, выбираемых пулом, называется бросками, а всё, что свыше этого — бонусными бросками.

## Блоки {#blocks}

Чтобы из блоков выпадали предметы — включая сам блок — необходимо создать таблицу лута. Создайте класс, который расширяет (extends) `FabricBlockLootTableProvider`:

<<< @/reference/26.1.2/src/client/java/com/example/docs/datagen/ExampleModBlockLootTableProvider.java#datagen_loot_tables_block_provider

Не забудьте добавить этот провайдер в свой пакет!

Существует множество вспомогательных методов, которые помогут вам в создании таблиц лута. Мы не будем рассматривать их все, поэтому обязательно изучите их в своей IDE.

Давайте добавим несколько результатов бросков в метод `generate`:

<<< @/reference/26.1.2/src/client/java/com/example/docs/datagen/ExampleModBlockLootTableProvider.java#datagen_loot_tables_block_drops

## Сундуки {#chests}

Лут для сундуков устроен немного сложнее, чем лут для блоков. Создайте класс, который расширяет `SimpleFabricLootTableProvider`, аналогично примеру ниже, и **добавьте его в свой пакет**.

<<< @/reference/26.1.2/src/client/java/com/example/docs/datagen/ExampleModChestLootTableProvider.java#datagen_loot_tables_chest_provider

Для нашей таблицы лута понадобится `ResourceKey<LootTable>`. Давайте поместим его в новый класс под названием `ModLootTables`. Убедитесь, что он находится в наборе исходных текстов `main`, если вы используете раздельные источники.

<<< @/reference/26.1.2/src/main/java/com/example/docs/ModLootTables.java#datagen_loot_tables_mod_loot_tables

Затем мы можем сгенерировать таблицу лута внутри метода `generate` вашего провайдера.

<<< @/reference/26.1.2/src/client/java/com/example/docs/datagen/ExampleModChestLootTableProvider.java#datagen_loot_tables_chest_loot

## Условная загрузка ресурсов {#resource-conditions}

Чтобы применить [условную загрузку ресурсов](../resource-conditions) к динамически генерируемой таблице добычи, вызовите метод `withConditions` и укажите любые условия ресурсов, которые вы хотите применить, а затем вызовите метод из провайдера таблиц добычи, например `dropSelf`. В результате будет создана таблица добычи с примененными условиями ресурсов:

<<< @/reference/26.1.2/src/client/java/com/example/docs/datagen/ExampleModBlockLootTableProvider.java#datagen_loot_tables_conditions
