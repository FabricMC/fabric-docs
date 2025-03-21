---
title: Генерація даних налаштування
description: Посібник із генерації даних налаштування за допомогою API Fabric.
authors:
  - ArkoSammy12
  - Earthcomputer
  - haykam821
  - Jab125
  - matthewperiut
  - modmuss50
  - Shnupbups
  - skycatminepokie
  - SolidBlock
authors-nogithub:
  - jmanc3
  - mcrafterzz
---

## Що таке генерація даних? {#what-is-data-generation}

Генерація даних (або datagen) — це API для програмної генерації рецептів, досягнень, теґів, моделей предметів, мовних файлів, таблиць здобичі та всього, що базується на JSON.

## Увімкнення генерації даних {#enabling-data-generation}

### Під час створення проєкту {#enabling-data-generation-at-project-creation}

Найпростіший спосіб увімкнути datagen – під час створення проєкту. Поставте прапорець «Увімкнути генерацію даних» під час використання [генератора шаблонів](https://fabricmc.net/develop/template/).

![Позначене поле «Генерація даних» у генераторі шаблонів](/assets/develop/data-generation/data_generation_setup_01.png)

:::tip
Якщо datagen увімкнено, ви повинні мати конфігурацію запуску "Data Generation" і завдання Gradle "runDatagen".
:::

### Власноруч {#manually-enabling-data-generation}

По-перше, нам потрібно ввімкнути datagen у файлі `build.gradle`.

@[code lang=groovy transcludeWith=:::datagen-setup:configure](@/reference/build.gradle)

Далі нам потрібен клас точки входу. Ось де починається наш datagen. Розмістіть це десь у пакеті `client` - у цьому прикладі це розміщено в `src/client/java/com/example/docs/datagen/FabricDocsReferenceDataGenerator.java`.

@[code lang=java transcludeWith=:::datagen-setup:generator](@/reference/latest/src/client/java/com/example/docs/datagen/FabricDocsReferenceDataGenerator.java)

Нарешті, нам потрібно повідомити Fabric про точку входу в нашому `fabric.mod.json`:

```json
{
  // ...
  "entrypoints": {
    // ...
    "client": [
      // ...
    ],
    "fabric-datagen": [ // [!code ++]
      "com.example.docs.datagen.FabricDocsReferenceDataGenerator" // [!code ++]
    ] // [!code ++]
  }
}
```

:::warning
Не забудьте додати кому (`,`) після попереднього блоку точки входу!
:::

Закрийте та знову відкрийте IntelliJ, щоб створити налаштування запуску для datagen.

## Створення пакету {#creating-a-pack}

Усередині методу `onInitializeDataGenerator` вашої точки входу даних нам потрібно створити `Pack`. Пізніше ви додасте **постачальників**, які додадуть згенеровані дані в цей `Pack`.

@[code lang=java transcludeWith=:::datagen-setup:pack](@/reference/latest/src/client/java/com/example/docs/datagen/FabricDocsReferenceDataGenerator.java)

## Запуск генерації даних {#running-data-generation}

Щоб запустити datagen, використовуйте налаштування запуску у вашій IDE або запустіть `./gradlew runDatagen` у консолі. Згенеровані файли буде створено в `src/main/generated`.

## Наступні кроки {#next-steps}

Тепер, коли datagen налаштовано, нам потрібно додати **постачальників**. Це те, що генерує дані для додавання до вашого `Pack`. На наступних сторінках описано, як це зробити.

- [Досягнення](./advancements)
- [Таблиці здобичі](./loot-tables)
- [Рецепти](./recipes)
- [Теґи](./tags)
- [Переклад](./translations)
