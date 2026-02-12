---
title: Генерація теґів
description: Інструкція з налаштування генерації теґів.
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

Спершу переконайтеся, що ви виконали процес [налаштування datagen](./setup).

:::

## Налаштування {#setup}

Тут ми покажемо, як створити теґи `Item`, але той самий принцип застосовується і до інших речей.

Fabric надає кілька постачальників допоміжних теґів, включаючи один для предметів; `FabricTagProvider.ItemTagProvider`. Ми будемо використовувати цей допоміжний клас для цього прикладу.

Ви можете створити власний клас, який розширює `FabricTagProvider<T>`, де `T` — це тип речей, для яких ви хочете надати теґ. Це ваш **постачальник**.

Нехай ваша IDE заповнить необхідний код, а потім замініть параметр конструктора `resourceKey` на `ResourceKey` для вашого типу:

@[code lang=java transcludeWith=:::datagen-tags:provider](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModItemTagProvider.java)

::: tip

Вам знадобиться інший постачальник для кожного типу теґу (наприклад, один `FabricTagProvider<EntityType<?>>` і один `FabricTagProvider<Item>`).

:::

Щоб завершити налаштування, додайте цього постачальника до своєї `DataGeneratorEntrypoint` у методі `onInitializeDataGenerator`.

@[code lang=java transcludeWith=:::datagen-tags:register](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModDataGenerator.java)

## Створення теґу {#creating-a-tag}

Тепер, коли ви створили постачальника, нумо додамо до нього теґ. Спочатку створіть `TagKey<T>`:

@[code lang=java transcludeWith=:::datagen-tags:tag-key](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModItemTagProvider.java)

Потім викличте `getOrCreateTagBuilder` всередині методу `configure` вашого постачальника. Звідти ви можете додавати окремі предмети, додавати інші теґи або змусити цей теґ замінити вже наявні теґи.

Якщо ви хочете додати теґ, використовуйте `addOptionalTag`, оскільки вміст теґу може не завантажуватися під час створення datagen. Якщо ви впевнені, що теґ завантажено, викличте `addTag`.

Щоб примусово додати теґ і ігнорувати несправний формат, використовуйте `forceAddTag`.

@[code lang=java transcludeWith=:::datagen-tags:build](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModItemTagProvider.java)
