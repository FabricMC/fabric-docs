---
title: Розширення стандартних рецептів
description: Дізнайтеся, як створювати власні рецепти для вже наявних робочих станків.
authors:
  - ekulxam
  - lynndova
resources:
  https://docs.neoforged.net/docs/resources/server/recipes/builtin/: Убудовані типи рецептів — Документація NeoForge
---

Якщо ви намагаєтеся додати рецепт для наявного робочого станка(наприклад, ковальського стола, верстака чи каменеріза), вам, ймовірно, потрібно лише [створити клас рецепта](./custom-recipe-types#creating-the-recipe-class), [реалізувати його методи](./custom-recipe-types#implementing-the-methods), [зареєструвати серіалізатор](./custom-recipe-types#creating-a-recipe-serializer) і [створити JSON-файл(и) рецепта](./custom-recipe-types#creating-a-recipe), оскільки логіку блока, меню та екрана вже реалізовано Mojang. Розгляньмо кілька прикладів.

## Огляд {#overview}

Кожен стандартний робочий станок має власний `RecipeType`, визначений в інтерфейсі `RecipeType`. Для функціонування кожен робочий станок потребує певного підтипу `Recipe`.

::: warning

Зауважте: якщо ви не зміните базове меню, ваші рецепти обмежуватимуться вхідними та вихідними даними, які воно передбачає. Наприклад, ковальський стіл має три вхідні слоти й один вихідний (у стандартній грі це зазвичай `Optional<Ingredient> template`, `Ingredient base`, `Optional<Ingredient> addition` та `ItemStackTemplate result`). Однак у межах класу `Recipe` ви маєте значну свободу в налаштуванні вхідних даних для отримання результатів.

:::

## Ковальський стіл {#smithing-table}

Створімо новий тип ковальського рецепта, який накладає зачарування на вихідний предмет для отримання кінцевого результату.

Ковальський стіл очікує на будь-яку реалізацію інтерфейсу `SmithingRecipe`, яка повертає `RecipeTypes.SMITHING`. Створюючи новий `SmithingRecipe`, можна просто створити новий клас і реалізувати інтерфейс `SmithingRecipe`, але іншим прийнятним варіантом є успадкування від `SimpleSmithingRecipe` — стандартного класу гри, який уже реалізує `SmithingRecipe`.

<<< @/reference/26.2/src/main/java/com/example/docs/recipe/extending/EnchantingSmithingRecipe.java#enchanting_smithing

Ого, знову купа тексту. Схоже, це часто повторюється в документації до рецептів (ха-ха). Спробуймо розібратися, що відбувається.

Перші кілька рядків містять наші `Codec`, `MapCodec` та `StreamCodec` для серіалізації та синхронізації даних рецепта. Для зачарувань ми використовуємо `Object2IntOpenHashMap`, щоб можна було зіставити довільні зачарування з рівнем.

Після розділу серіалізації йдуть згадані раніше `template`, `base` та `addition`, але замість `ItemStackTemplate result` ми маємо `Object2IntOpenHashMap enchantments`.

Метод `assemble` є основою власного рецепта та забезпечує отримання вихідного `ItemStack` під час його виконання. У цьому випадку ми використовуємо допоміжний метод із класу `EnchantmentHelper`, щоб застосувати зачарування з нашої мапи.

Наш `PlacementInfo` переважно допомагає розміщувати рецепти в книзі рецептів, тоді як `RecipeDisplay` допомагає показувати їх у цій книзі.

:::details Відступ: Покази слота

Якби ви спробували створити власну реалізацію методу `display`, то швидко помітили б, що не зможете створити `SlotDisplay` для отриманого результату. Це пов’язано з тим, що ваш результат є динамічним і залежить від `base` з `Ingredient`, з якого не так просто отримати `ItemStack`. Однак у нашому класі рецепта ми надали коректне перевизначення методу `display`. Що тут діється?

<<< @/reference/26.2/src/main/java/com/example/docs/recipe/extending/EnchantingSmithingDemoSlotDisplay.java#slot_display

Ми створили власну реалізацію `SlotDisplay`. Ця конкретна реалізація дозволяє показувати результат із бажаними зачаруваннями.

У нашому методі `resolve` ми спочатку створюємо `RandomSource` і `BinaryOperator<ItemStack>`, а потім передаємо їх обидва до `SlotDisplay.applyDemoTransformation` (цей метод є статичним, але приватним, тому нам потрібен викликач міксина).

<<< @/reference/26.2/src/main/java/com/example/docs/mixin/accessor/SlotDisplayAccessor.java#demo_invoker

`applyDemoTransformation` дозволяє застосовувати зміни до `ItemStack`, що показуються в `SlotDisplay`. Він приймає `BinaryOperator<ItemStack>`, що дозволяє змінювати дані `base` на основі `material`. Це корисно для таких рецептів, як рецепти орнаментів, де колір орнаменту готового виробу змінюється залежно від матеріалу. Однак ми застосовуємо наші зачарування безпосередньо до базового стосу, ігноруючи матеріал (рецепт лише перевіряє наявність потрібного матеріалу перед дозволом на створення), тому ми фактично можемо опустити поле `material` у нашій реалізації `SlotDisplay` (у такому разі замість `material` до методу `applyDemoTransformation` передаватиметься `SlotDisplay.Empty.INSTANCE`).

:::

Нарешті, нам потрібно зареєструвати наш серіалізатор рецептів і тип показу слота.

<<< @/reference/26.2/src/main/java/com/example/docs/recipe/ExampleModRecipes.java#enchanting_smithing_registration

::: info

Цей рецепт і досі керується даними.

<<< @/reference/26.2/src/main/resources/data/example-mod/recipe/smithing_enchanting/netherite_sword_smithing_enchanting.json

:::

![Супернезеритовий меч](/assets/develop/recipes/smithing_enchanting.png)

## Верстак {#crafting-table}

Подібна ситуація виникає під час створення нового рецепта для майстрування. Очікуваним типом є інтерфейс `CraftingRecipe`, і якщо `ShapedRecipe` та `ShapelessRecipe` недостатньо, ми рекомендуємо натомість розширити клас `CustomRecipe`. Радимо переглянути підтипи інтерфейсу рецептів на цільовому робочому станку, щоб знайти той, що відповідає вашим потребам.

Як приклад, створимо власний рецепт, що дозволяє додавати зілля до підозрілої юшки.

<<< @/reference/26.2/src/main/java/com/example/docs/recipe/extending/StewSpikingCraftingRecipe.java#stew_spiking

Як завжди, нам потрібно зареєструвати наш серіалізатор рецептів.

<<< @/reference/26.2/src/main/java/com/example/docs/recipe/ExampleModRecipes.java#stew_spiking_registration

::: info

Цей рецепт і досі керується даними.

<<< @/reference/26.2/src/main/resources/data/example-mod/recipe/stew_spiking/stew_spiking.json

Нам потрібен лише тип, щоб Minecraft знав, що ми хочемо завантажити рецепт.

:::

_Тс-с-с, нікому не кажи! >:)_
![Я додав стільки шкідливих зіллів у цю юшку](/assets/develop/recipes/stew_spiking.png)

## Каменеріз {#stonecutter}

Рецепти для каменеріза відокремлені від інших рецептів у класах `RecipeManager` та `RecipeAccess`, оскільки каменетес повинен показувати та дозволяти вибір будь-якого з доступних для нього рецептів на основі одного вхідного предмета (обробка меню з книгами рецептів здійснюється через `ClientRecipeBook`, де сервер передає клієнту необхідні рецепти). Просте успадкування від `StonecutterRecipe` (на відміну від інших, це не інтерфейс!) та перевизначення методу `assemble` має спрацювати для більшості сценаріїв використання, що виходять за межі простого створення JSON-файлу рецепта для каменеріза.
