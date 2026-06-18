---
title: Генерация рецептов
description: Гайд для настройки рецептов с помощью datagen.
authors:
  - CelDaemon
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

## Установка {#setup}

Во-первых, нам понадобится провайдер. Создайте класс, который расширяет `FabricRecipeProvider`. Вся генерация рецептов будет происходить внутри метода `buildRecipes` нашего провайдера.

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModRecipeProvider.java#datagen_recipes_provider

Чтобы завершить настройку, добавьте этот провайдер к вашей `DataGeneratorEntrypoint` в методе `onInitializeDataGenerator`.

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModDataGenerator.java#datagen_recipes_register

## Бесформенные рецепты {#shapeless-recipes}

Рецепты бесформенных крафтов довольно просты. Просто добавьте их в метод `buildRecipes` вашего провайдера:

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModRecipeProvider.java#datagen_recipes_shapeless

### Рецепты окрашивания {#dye-recipes}

Рецепты окрашивания используются для покраски предметов в вашем инвентаре в определённый цвет.

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModRecipeProvider.java#datagen_recipes_dye

## Рецепты по форме {#shaped-recipes}

Для рецепта с формой вы определяете форму с помощью `String`, а затем определяете, что представляет собой каждый `символ` в `String`.

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModRecipeProvider.java#datagen_recipes_shaped

::: tip

Здесь есть множество вспомогательных методов для создания обычных рецептов. Посмотрите, что может предложить `RecipeProvider`! Нажмите <kbd>Alt</kbd>+<kbd>7</kbd> в IntelliJ, чтобы открыть структуру класса вместе со списком его методов.

:::

## Другие рецепты {#other-recipes}

Другие рецепты работают аналогично, но требуют нескольких дополнительных параметров. Например, для рецептов плавки необходимо знать, сколько опыта нужно присудить.

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModRecipeProvider.java#datagen_recipes_smelting

Рецепты внутри Коптильни (Smoker) регистрируются отлично от рецептов в печах остальных видов – они используют `SimpleCookingRecipeBuilder.smoking`:

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModRecipeProvider.java#datagen_recipes_smoking

## Условная загрузка ресурсов {#resource-conditions}

Чтобы применить [условную загрузку ресурсов](../resource-conditions) к динамически генерируемому рецепту, оберните выходной результат методом `withConditions` и укажите любые условия ресурсов, которые вы хотите применить. В результате будут созданы рецепт и достижение с примененными условиями ресурсов:

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModRecipeProvider.java#datagen_recipes_conditions
