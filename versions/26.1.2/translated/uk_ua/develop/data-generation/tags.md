---
title: Генерація теґів
description: Інструкція з налаштування генерації теґів за допомогою генерації даних.
authors:
  - CelDaemon
  - IMB11
  - skycatminepokie
  - Spinoscythe
authors-nogithub:
  - mcrafterzz
---

<!---->

:::info ПЕРЕДУМОВИ

Спершу переконайтеся, що ви виконали процес [налаштування генерації даних](./setup).

:::

## Налаштування {#setup}

Тут ми покажемо, як створити теґи `Item`, але той самий принцип застосовується і до інших речей.

Fabric надає кілька постачальників допоміжних теґів, включаючи один для предметів; `FabricTagsProvider.ItemTagsProvider`. Ми будемо використовувати цей допоміжний клас для цього прикладу.

Ви можете створити власний клас, який розширює `FabricTagsProvider<T>`, де `T` — це тип речей, для яких ви хочете надати теґ. Це ваш **постачальник**.

Нехай ваша IDE заповнить необхідний код, а потім замініть параметр конструктора `resourceKey` на `ResourceKey` для вашого типу:

<<< @/reference/26.1.2/src/client/java/com/example/docs/datagen/ExampleModItemTagProvider.java#datagen_tags_provider

::: tip

Вам знадобиться інший постачальник для кожного типу теґу (наприклад, один `FabricTagsProvider<EntityType<?>>` і один `FabricTagsProvider<Item>`).

:::

Щоб завершити налаштування, додайте цього постачальника до своєї `DataGeneratorEntrypoint` у методі `onInitializeDataGenerator`.

<<< @/reference/26.1.2/src/client/java/com/example/docs/datagen/ExampleModDataGenerator.java#datagen_tags_register

## Створення теґу {#creating-a-tag}

Тепер, коли ви створили постачальника, нумо додамо до нього теґ. Спочатку створіть `TagKey<T>`:

<<< @/reference/26.1.2/src/client/java/com/example/docs/datagen/ExampleModItemTagProvider.java#datagen_tags_tag_key

Потім викличте `getOrCreateTagBuilder` всередині методу `configure` вашого постачальника. Звідти ви можете додавати окремі предмети, додавати інші теґи або змусити цей теґ замінити вже наявні теґи.

Якщо ви хочете додати теґ, використовуйте `addOptionalTag`, оскільки вміст теґу може не завантажуватися під час створення генерації даних. Якщо ви впевнені, що теґ завантажено, викличте `addTag`.

Щоб примусово додати теґ і ігнорувати несправний формат, використовуйте `forceAddTag`.

<<< @/reference/26.1.2/src/client/java/com/example/docs/datagen/ExampleModItemTagProvider.java#datagen_tags_build
