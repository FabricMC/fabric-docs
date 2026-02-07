---
title: Генерація рецептів
description: Посібник із налаштування генерації рецептів за допомогою datagen.
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

Спершу переконайтеся, що ви виконали процес [налаштування datagen](./setup).

:::

## Налаштування {#setup}

По-перше, нам знадобиться наш постачальник. Створіть клас, який розширює `FabricRecipeProvider`. Уся наша генерація рецептів відбуватиметься всередині методу `buildRecipes` нашого постачальника.

@[code lang=java transcludeWith=:::datagen-recipes:provider](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModRecipeProvider.java)

Щоб завершити налаштування, додайте цього постачальника до своєї `DataGeneratorEntrypoint` у методі `onInitializeDataGenerator`.

@[code lang=java transcludeWith=:::datagen-recipes:register](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModDataGenerator.java)

## Безформні рецепти {#shapeless-recipes}

Безформні рецепти досить прості. Просто додайте їх до методу `buildRecipes` у вашому постачальнику:

@[code lang=java transcludeWith=:::datagen-recipes:shapeless](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModRecipeProvider.java)

## Формні рецепти {#shaped-recipes}

Для формного рецепта ви визначаєте форму за допомогою `String`, а потім визначаєте, що означає кожен `char` у `String`.

@[code lang=java transcludeWith=:::datagen-recipes:shaped](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModRecipeProvider.java)

::: tip

Існує багато допоміжних методів для створення звичайних рецептів. Перевірте, що може запропонувати `RecipeProvider`! Використовуйте <kbd>Alt</kbd>+<kbd>7</kbd> в IntelliJ, щоб відкрити структуру класу, включаючи список методів.

:::

## Інші рецепти {#other-recipes}

Інші рецепти працюють так само, але вимагають кількох додаткових параметрів. Наприклад, рецепти плавки повинні знати, скільки досвіду присудити.

@[code lang=java transcludeWith=:::datagen-recipes:other](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModRecipeProvider.java)
