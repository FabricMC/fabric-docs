---
title: Генерація рецептів
description: Посібник із налаштування генерації рецептів за допомогою генерації даних.
authors:
  - CelDaemon
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

## Налаштування {#setup}

По-перше, нам знадобиться наш постачальник. Створіть клас, який розширює `FabricRecipeProvider`. Уся наша генерація рецептів відбуватиметься всередині методу `buildRecipes` нашого постачальника.

<<< @/reference/26.1.2/src/client/java/com/example/docs/datagen/ExampleModRecipeProvider.java#datagen_recipes_provider

Щоб завершити налаштування, додайте цього постачальника до своєї `DataGeneratorEntrypoint` у методі `onInitializeDataGenerator`.

<<< @/reference/26.1.2/src/client/java/com/example/docs/datagen/ExampleModDataGenerator.java#datagen_recipes_register

## Безформні рецепти {#shapeless-recipes}

Безформні рецепти досить прості. Просто додайте їх до методу `buildRecipes` у вашому постачальнику:

<<< @/reference/26.1.2/src/client/java/com/example/docs/datagen/ExampleModRecipeProvider.java#datagen_recipes_shapeless

### Рецепти забарвлення {#dye-recipes}

Рецепти забарвлення використовуються для зміни кольору предметів в інвентарі.

<<< @/reference/26.1.2/src/client/java/com/example/docs/datagen/ExampleModRecipeProvider.java#datagen_recipes_dye

## Формні рецепти {#shaped-recipes}

Для формного рецепта ви визначаєте форму за допомогою `String`, а потім визначаєте, що означає кожен `char` у `String`.

<<< @/reference/26.1.2/src/client/java/com/example/docs/datagen/ExampleModRecipeProvider.java#datagen_recipes_shaped

::: tip

Існує багато допоміжних методів для створення звичайних рецептів. Перевірте, що може запропонувати `RecipeProvider`! Використовуйте <kbd>Alt</kbd>+<kbd>7</kbd> в IntelliJ, щоб відкрити структуру класу, включаючи список методів.

:::

## Інші рецепти {#other-recipes}

Інші рецепти працюють так само, але вимагають кількох додаткових параметрів. Наприклад, рецепти плавки повинні знати, скільки досвіду присудити.

<<< @/reference/26.1.2/src/client/java/com/example/docs/datagen/ExampleModRecipeProvider.java#datagen_recipes_smelting

Копчення трохи відрізняється, воно не використовує той самий генератор рецептів, що й плавильні блоки.

<<< @/reference/26.1.2/src/client/java/com/example/docs/datagen/ExampleModRecipeProvider.java#datagen_recipes_smoking

## Умови ресурсів {#resource-conditions}

Щоб застосувати [умову ресурсів](../resource-conditions) до рецепта, згенерованого на основі даних, оберніть результат за допомогою `withConditions` і вкажіть будь-які умови ресурсів, які ви хочете застосувати. Потім буде створено рецепт і досягнення, які мають умови ресурсів:

<<< @/reference/26.1.2/src/client/java/com/example/docs/datagen/ExampleModRecipeProvider.java#datagen_recipes_conditions
