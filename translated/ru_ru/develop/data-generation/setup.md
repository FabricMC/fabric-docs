---
title: Настройка генерации данных
description: Руководство по настройке генерации данных с помощью API Fabric.
authors:
  - ArkoSammy12
  - Earthcomputer
  - haykam821
  - Jab125
  - matthewperiut
  - modmuss50
  - Shnupbups
  - skycatminepokie
  - SolidBlock-cn
authors-nogithub:
  - jmanc3
  - mcrafterzz
---

## Что такое генерация данных? {#what-is-data-generation}

Генерация данных (или datagen) - это API для программной генерации рецептов, улучшений, тегов, моделей предметов, языковых файлов, таблиц лута и вообще всего, что основано на JSON.

## Активация генерации данных {#enabling-data-generation}

### При создании проекта {#enabling-data-generation-at-project-creation}

Проще всего включить datagen при создании проекта. Установите флажок "Включить генерацию данных" при использовании [генератора шаблонов] (https://fabricmc.net/develop/template/).

![Установленный флажок "Генерация данных" на генераторе шаблонов](/assets/develop/data-generation/data_generation_setup_01.png)

::: tip

Если datagen включен, у вас должна быть конфигурация запуска "Генерация данных" и Gradle-задача `runDatagen`.

:::

### Руководство {#manually-enabling-data-generation}

Сначала нам нужно включить datagen в файле `build.gradle`.

@[code transcludeWith=:::datagen-setup:configure](@/reference/build.gradle)

Далее нам нужен класс точки входа. Именно здесь начинается наш датаген. Разместите его где-нибудь в пакете `client` - в данном примере он находится по адресу`src/client/java/com/example/docs/datagen/ExampleModDataGenerator.java`.

@[code lang=java transcludeWith=:::datagen-setup:generator](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModDataGenerator.java)

Наконец, нам нужно сообщить Fabric о точке входа в нашем файле `fabric.mod.json`:

<!-- prettier-ignore -->

```json
{
  // ...
  "entrypoints": {
    // ...
    "client": [
      // ...
    ],
    "fabric-datagen": [ // [!code ++]
      "com.example.docs.datagen.ExampleModDataGenerator" // [!code ++]
    ] // [!code ++]
  }
}
```

::: warning

Не забудьте добавить запятую (`,`) после предыдущего блока точки входа!

:::

Закройте и снова откройте IntelliJ, чтобы создать конфигурацию запуска для datagen.

## Создание пакета {#creating-a-pack}

Внутри метода `onInitializeDataGenerator` вашей точки входа datagen нам нужно создать `Pack`. Позже вы добавите **провайдеров**, которые будут помещать сгенерированные данные в этот `Pack`.

@[code lang=java transcludeWith=:::datagen-setup:pack](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModDataGenerator.java)

## Запуск генерации данных {#running-data-generation}

Чтобы запустить datagen, используйте конфигурацию run в вашей IDE или выполните команду `./gradlew runDatagen` в консоли. Сгенерированные файлы будут созданы в `src/main/generated`.

## Следующие шаги {#next-steps}

Теперь, когда datagen настроен, нам нужно добавить **провайдеров**. Именно они генерируют данные для добавления в ваш `Pack`. На следующих страницах описано, как это сделать.

- [Достижения](./advancements)
- [Таблицы лута](./loot-tables)
- [Рецепты](./recipes)
- [Теги](./tags)
- [Переводы](./translations)
- [Модели блоков](./block-models)
- [Модели предметов](./item-models)
