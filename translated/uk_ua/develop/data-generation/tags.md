---
title: Генерація теґів
description: Інструкція з налаштування генерації теґів.
authors:
  - skycatminepokie
  - IMB11
  - Spinoscythe
authors-nogithub:
  - mcrafterzz
---

# Генерація теґів {#tag-generation}

:::info ПЕРЕДУМОВИ
Спершу переконайтеся, що ви виконали процес [налаштування datagen](./setup).
:::

## Налаштування {#setup}

По-перше, створіть власний клас, який `розширює FabricTagProvider<T>`, де `T` — це тип речей, для яких ви хочете надати теґ. Це ваш **постачальник**. Тут ми покажемо, як створити теґи `Item`, але той самий принцип застосовується і до інших речей. Нехай ваша IDE заповнить необхідний код, а потім замініть параметр конструктора `registryKey` на `RegistryKey` для вашого типу:

@[code lang=java transcludeWith=:::datagen-tags:provider](@/reference/latest/src/client/java/com/example/docs/datagen/FabricDocsReferenceItemTagProvider.java)

:::info ПРИМІТКА
Вам знадобиться інший постачальник для кожного типу теґу (наприклад, один `FabricTagProvider<EntityType<?>>` і один `FabricTagProvider<0>`).
:::

Щоб завершити налаштування, додайте цього постачальника до своєї `DataGeneratorEntrypoint` у методі `onInitializeDataGenerator`.

@[code lang=java transclude={29-29}](@/reference/latest/src/client/java/com/example/docs/datagen/FabricDocsReferenceDataGenerator.java)

## Створення теґу

Тепер, коли ви створили постачальника, нумо додамо до нього теґ. Спочатку створіть `TagKey<0>`:

@[code lang=java transcludeWith=:::datagen-tags:tag-key](@/reference/latest/src/client/java/com/example/docs/datagen/FabricDocsReferenceItemTagProvider.java)

Потім викличте `getOrCreateTagBuilder` всередині методу `configure` вашого постачальника. Звідти ви можете додавати окремі предмети, додавати інші теґи або змусити цей теґ замінити вже існуючі теґи.

Якщо ви хочете додати теґ, використовуйте `addOptionalTag`, оскільки вміст теґу може не завантажуватися під час створення datagen. Якщо ви впевнені, що теґ завантажено, викличте `addTag`.

Щоб примусово додати теґ і ігнорувати несправний формат, використовуйте `forceAddTag`.

@[code lang=java transcludeWith=:::datagen-tags:build](@/reference/latest/src/client/java/com/example/docs/datagen/FabricDocsReferenceItemTagProvider.java)
