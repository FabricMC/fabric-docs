---
title: Власні типи рецептів
description: Дізнайтеся, як створити власний тип рецепта.
authors:
  - cassiancc
  - skippyall
---

Власні типи рецептів — це спосіб створювати керовані даними рецепти для власної механіки майстрування вашого мода. Як приклад, ми створимо тип рецепта для блока покращувача, подібного до ковальського стола.

## Створення класу введення рецептів {#creating-your-recipe-input-class}

Перш ніж ви зможете почати створювати наш рецепт, вам потрібна реалізація `RecipeInput`, яка може зберігати вхідні предмети в інвентарі нашого блока. Ми хочемо, щоб рецепт покращення мав два вхідні предмети: базовий покращуваний предмет та саме покращення.

@[code transcludeWith=:::recipeInput](@/reference/latest/src/main/java/com/example/docs/recipe/UpgradingRecipeInput.java)

## Створення класу рецепта {#creating-the-recipe-class}

Тепер, коли у нас є спосіб зберігати вхідні предмети, ми можемо створити нашу реалізацію `Recipe`. Реалізації цього класу представляють окремий рецепт, визначений у пакеті даних. Вони несуть відповідальність за перевірку інгредієнтів і вимог рецепта, а також за поєднання цього в результат.

Почнемо з визначення результату та інгредієнтів рецепта.

@[code transcludeWith=:::baseClass](@/reference/latest/src/main/java/com/example/docs/recipe/UpgradingRecipe.java)

Зверніть увагу, як ми використовуємо об’єкти `Ingredient` для наших вхідних предметів. Це дозволяє нашому рецепту приймати кілька предметів взаємозамінно.

## Реалізація методів {#implementing-the-methods}

Далі запровадимо методи з інтерфейсу рецептів. Цікавими є методи `matches` і `assemble`. Метод `matches` перевіряє, чи вхідні предмети з нашої реалізації `RecipeInput` відповідають нашим інгредієнтам. Потім метод `assemble` створює кінцевий `ItemStack`.

Щоб перевірити, чи збігаються інгредієнти, ми можемо використати метод `test` наших інгредієнтів.

@[code transcludeWith=:::implementing](@/reference/latest/src/main/java/com/example/docs/recipe/UpgradingRecipe.java)

## Створення серіалізатора рецептів {#creating-a-recipe-serializer}

Серіалізатор рецептів використовує [`MapCodec`](./codecs/#mapcodec) для читання рецепта з JSON і `StreamCodec` для надсилання його через мережу.

Ми використаємо `RecordCodecBuilder#mapCodec`, щоб створити мапу кодека для нашого рецепта. Це дозволяє нам об’єднати наявні кодеки Minecraft у наші власні:

@[code transcludeWith=:::mapCodec](@/reference/latest/src/main/java/com/example/docs/recipe/UpgradingRecipeSerializer.java)

Кодек потоку можна створити подібним чином за допомогою `StreamCodec#composite`:

@[code transcludeWith=:::streamCodec](@/reference/latest/src/main/java/com/example/docs/recipe/UpgradingRecipeSerializer.java)

Використаймо ці кодеки для реалізації методів із `RecipeSerializer`:

@[code transcludeWith=:::implementing](@/reference/latest/src/main/java/com/example/docs/recipe/UpgradingRecipeSerializer.java)

Тепер ми зареєструємо серіалізатор рецепта, а також тип рецепта. Ви можете зробити це в ініціалізаторі вашого мода або в окремому класі за допомогою методу, викликаного ініціалізатором вашого мода:

@[code transcludeWith=:::registration](@/reference/latest/src/main/java/com/example/docs/recipe/ExampleModRecipes.java)

Повертаючись до нашого класу рецептів, тепер ми можемо додати методи, які повертають щойно зареєстровані об’єкти:

@[code transcludeWith=:::implementRegistryObjects](@/reference/latest/src/main/java/com/example/docs/recipe/UpgradingRecipe.java)

Щоб завершити наш власний тип рецепта, нам просто потрібно реалізувати інші методи `placementInfo` і `recipeBookCategory`, які використовуються книгою рецептів для розміщення нашого рецепта на екрані. Наразі ми просто повернемо `PlacementInfo.NOT_PLACEABLE` і `null`, оскільки книгу рецептів не можна легко розширити до модових робочих станків. Ми також перевизначимо `isSpecial`, щоб повернути true, щоб запобігти запуску й реєстрації помилок деякої іншої логіки, пов’язаної з книгою рецептів.

@[code transcludeWith=:::recipeBook](@/reference/latest/src/main/java/com/example/docs/recipe/UpgradingRecipe.java)

## Створення рецепта {#creating-a-recipe}

Наш тип рецепта зараз працює, але нам все ще бракує двох важливих речей: рецепта для нашого типу рецепта та способу його створення.

Спочатку створимо рецепт. У теці `resources` створіть файл у `data/example-mod/recipe` з розширенням `.json`. Кожен json-файл рецепта має ключ `"type"`, який посилається на серіалізатор рецепта. Інші ключі визначаються кодеком цього серіалізатора рецепта.

У нашому випадку дійсний файл рецепта виглядає так:

@[code](@/reference/latest/src/main/resources/data/example-mod/recipe/upgrading/diamond_pickaxe.json)

## Створення меню {#creating-a-menu}

::: info

Подробиці про створення меню див. у розділі [меню контейнерів](blocks/container-menus).

:::

Щоб ми могли створити наш рецепт в інтерфейсі, ми створимо блок із [меню](./blocks/container-menus):

@[code transcludeWith=:::menu](@/reference/latest/src/main/java/com/example/docs/menu/custom/UpgradingMenu.java)

Тут багато чого розібрати! Це меню має два вхідні слоти та один вихідний.

Вхідний контейнер є анонімним підкласом `SimpleContainer`, який викликає метод `slotsChanged` меню, коли його предмети змінюються. У `slotsChanged` ми створюємо екземпляр нашого вхідного класу рецепта, заповнюючи його двома вхідними слотами.

Щоб перевірити, чи відповідає він будь-яким рецептам, ми спочатку переконаємося, що ми знаходимося на рівні сервера, оскільки клієнти не знають, які існують рецепти. Потім ми отримаємо `RecipeManager` через `serverLevel.recipeAccess()`.

Ми викличемо `serverLevel.recipeAccess().getRecipeFor` з нашим уведенням рецепта, щоб отримати рецепт, який відповідає введеним даним. Якщо рецепт знайдено, ми можемо додати або видалити результат із результату контейнера.

Щоб визначити, коли користувач виймає результат, ми створюємо анонімний підклас `Slot`. Потім метод `onTake` нашого меню видаляє введені предмети.

Щоб запобігти видаленню предметів, важливо скинути введені дані, коли екран закрито, як показано в методі `removed`.

## Синхронізація рецептів {#recipe-synchronization}

::: info

Цей розділ необов’язковий і потрібен, лише якщо вам потрібно, щоб клієнти знали про рецепти.

:::

Як згадувалося раніше, рецепти повністю обробляються на логічному сервері. Однак у деяких випадках клієнту може знадобитися знати, які існують рецепти — прикладом зі стандартної гри є каменеріз, яки повинен показувати доступні варіанти рецептів для певного інгредієнта. Крім того, плаґіни певних засобів перегляду рецептів, зокрема [JEI](https://modrinth.com/mod/jei), запускаються на логічному клієнті, вимагаючи від вас використання API синхронізації рецептів Fabric.

Щоб синхронізувати ваші рецепти, просто викличте `RecipeSynchronization.synchronizeRecipeSerializer` у своєму ініціалізаторі мода та надайте серіалізатор рецепта свого мода:

@[code transcludeWith=:::recipeSync](@/reference/latest/src/main/java/com/example/docs/recipe/ExampleModRecipes.java)

Після синхронізації рецепти можна отримати в будь-який момент із менеджера рецептів рівня клієнта:

@[code transcludeWith=:::recipeSyncClient](@/reference/latest/src/client/java/com/example/docs/ExampleModRecipesClient.java)
