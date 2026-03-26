---
title: Генерація функцій
description: Дізнайтеся, як генерувати функції у світі за допомогою генерації даних.
authors:
  - cassiancc
  - its-miroma
  - Wind292
---

<!---->

:::info ПЕРЕДУМОВИ

Спершу переконайтеся, що ви виконали процес [налаштування генерації даних](./setup).

:::

Генерація функцій світів Minecraft розбита на 3 частини:

- **Налаштовані функції**: визначають, що таке функція; наприклад, одне дерево
- **Розміщення функції**: тут визначається, як мають розташовуватися функції, у якому напрямку, відносне розташування тощо; наприклад, розміщення дерев у лісі
- **Модифікації біому**: це визначає, де у світі розміщені функції; наприклад, координати всього лісу

::: info

Функції Minecraft — це природні або створені шаблони світу, наприклад дерева, квіти, руди чи озера. Функції відрізняються від структур (наприклад, сіл, храмів…), які можна знайти за допомогою команди `/locate`.

:::

## Налаштування {#setup}

По-перше, нам потрібно створити свого постачальника. Створіть клас, який розширює `FabricDynamicRegistryProvider` та заповніть базові методи:

@[code lang=java transcludeWith=:::datagen-world:provider](@/reference/latest/src/main/java/com/example/docs/worldgen/ExampleModWorldgenProvider.java)

Потім додайте цього постачальника до свого класу `DataGeneratorEntrypoint` у методі `onInitializeDataGenerator`:

@[code lang=java transclude={67-67}](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModDataGenerator.java)

Далі створіть клас для налаштованих функцій і клас для розміщених функцій. Їм не потрібно нічого розширювати.

Налаштований клас функцій і розміщений клас функцій повинні мати загальнодоступний метод для реєстрації та визначення ваших функцій. Його аргумент, який ми назвали `context`, має бути `BootstrapContext<ConfiguredFeature<?, ?>>` для налаштованої функції або `BootstrapContext<PlacedFeature>` для розміщеної функції.

У вашому класі `DataGeneratorEntrypoint` додайте наведені нижче рядки до методу `buildRegistry`, замінивши назву методу тим, що ви вибрали:

@[code lang=java transcludeWith=:::datagen-world:registries](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModDataGenerator.java)

Якщо у вас ще немає методу `buildRegistry`, створіть його та додайте анотацію `@Override`.

## Налаштовані функції {#configured-features}

Щоб функція природно з’явилася в нашому світі, ми повинні почати з визначення налаштованої функції в нашому класі налаштованих функцій. Додаймо спеціальну налаштовану функцію для жили діамантової руди.

Спочатку зареєструйте ключ для `ConfiguredFeature` у вашому налаштованому класі функцій:

@[code lang=java transcludeWith=:::datagen-world:configured-key](@/reference/latest/src/main/java/com/example/docs/worldgen/ExampleModWorldConfiguredFeatures.java)

::: tip

Другий аргумент для `Identifier` (`diamond_block_vein` у цьому прикладі) — це те, що ви б використали для появи в структурі за допомогою команди `/place`, що корисно для налагодження.

:::

### Руди {#ores}

Далі ми створимо `RuleTest`, який контролює, які блоки ваша функція може замінити. Наприклад, цей `RuleTest` дозволяє замінювати кожен блок на теґ `DEEPSLATE_ORE_REPLACEABLES`:

@[code lang=java transcludeWith=:::datagen-world:ruletest](@/reference/latest/src/main/java/com/example/docs/worldgen/ExampleModWorldConfiguredFeatures.java)

Далі нам потрібно створити `OreConfiguration`, який повідомляє грі, чим замінити блоки.

@[code lang=java transcludeWith=:::datagen-world:ore-feature-config](@/reference/latest/src/main/java/com/example/docs/worldgen/ExampleModWorldConfiguredFeatures.java)

Ви можете мати кілька випадків у списку для різних варіантів. Наприклад, установімо інший варіант для каменю та глиболанцю:

@[code lang=java transcludeWith=:::datagen-world:multi-ore-feature-config](@/reference/latest/src/main/java/com/example/docs/worldgen/ExampleModWorldConfiguredFeatures.java)

Нарешті, нам потрібно зареєструвати нашу налаштовану функцію в нашій грі!

@[code lang=java transcludeWith=:::datagen-world:conf-feature-register](@/reference/latest/src/main/java/com/example/docs/worldgen/ExampleModWorldConfiguredFeatures.java)

### Дерева {#trees}

Щоб створити спеціальне дерево, спочатку потрібно створити `TreeConfiguration`:

@[code lang=java transcludeWith=:::datagen-world:tree-feature-config](@/reference/latest/src/main/java/com/example/docs/worldgen/ExampleModWorldConfiguredFeatures.java)

Ось що робить кожен аргумент:

1. Визначає тип блока для стовбура дерева; наприклад, блоки діаманта
2. Налаштовує форму та висоту стовбура за допомогою розміщувача стовбура
3. Визначає тип блока для листя дерева; наприклад, блоки золота
4. Визначає форму та розмір листя за допомогою розміщувача листя
5. Контролює, як стовбур дерева звужується на різних висотах, переважно для великих стовбурів

::: tip

Ми _настійно_ рекомендуємо вам поекспериментувати з цими значеннями, щоб створити спеціальне дерево, яким **ви** будете задоволені!

Ви можете використовувати вбудовані розміщувачі для стовбура та листя ванільних дерев як орієнтир.

:::

Далі нам потрібно зареєструвати наше дерево, додавши наступний рядок до методу `configure` `ExampleModWorldConfiguredFeatures`.

@[code lang=java transcludeWith=:::datagen-world:tree-register](@/reference/latest/src/main/java/com/example/docs/worldgen/ExampleModWorldConfiguredFeatures.java)

## Функції розміщення {#placement-features}

Наступним кроком у додаванні функції до гри є створення її розміщення.

У методі `configure` вашого розміщеного класу функцій створіть змінну, подібну до наведеної нижче:

@[code lang=java transcludeWith=:::datagen-world:conf-feature-register](@/reference/latest/src/main/java/com/example/docs/worldgen/ExampleModWorldPlacedFeatures.java)

У вашому класі розміщення функції визначте ключ для розміщеної функції.

@[code lang=java transcludeWith=:::datagen-world:placed-key](@/reference/latest/src/main/java/com/example/docs/worldgen/ExampleModWorldPlacedFeatures.java)

### Модифікатори розміщення {#placement-modifiers}

Далі нам потрібно визначити наші модифікатори розміщення, які є атрибутами, які ви встановлюєте під час створення функції. Це може бути що завгодно: від частоти появи до початкового рівня `y`. Ви можете мати стільки модифікаторів, скільки забажаєте.

@[code lang=java transcludeWith=:::datagen-world:placement-modifier](@/reference/latest/src/main/java/com/example/docs/worldgen/ExampleModWorldPlacedFeatures.java)

Функції кожного модифікатора в списку такі:

- **CountPlacement**: Приблизно кількість екземплярів цієї функції (у цьому випадку жил) на чанк
- **BiomeFilter**: дозволяє нам контролювати, у яких біоми/виміри з'являється (ми зробимо це пізніше)
- **InSquarePlacement**: розподіляє функції більш псевдовипадково
- **HeightRangePlacement**: визначає діапазон координат `y`, де може з’явитися функція; вона підтримує три основні типи дистрибуції:
  1. **Uniform**: Усі значення `y` в межах діапазону з однаковою ймовірністю містять функцію. Якщо ви не впевнені, просто скористайтеся цим.

  2. **Trapezoid**:
     Значення `y`, ближчі до середнього значення `y`, мають вищу ймовірність містити функцію.

  3. **Biased-Bottom**: Використовує логарифмічну шкалу, де нижчі значення `y` мають більшу ймовірність отримати функцію. Він отримує початкову координату `y`, нижче якої функція ніколи не створюється. Другий аргумент — це максимальна висота, на якій може з’явитися функція. Третій аргумент визначає діапазон у блоках, на який поширюється максимальна ймовірність.

::: tip

Дерева та інші поверхневі структури мають містити модифікатор `PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP` замість `HeightRangePlacement`, щоб переконатися, що дерево з’являється на поверхні.

:::

Тепер, коли у нас є модифікатори, ми можемо зареєструвати нашу розміщену функцію:

@[code lang=java transcludeWith=:::datagen-world:register-placed-feature](@/reference/latest/src/main/java/com/example/docs/worldgen/ExampleModWorldPlacedFeatures.java)

## Модифікації біому {#biome-modifications}

Нарешті, нам потрібно додати нашу розміщену функцію до `BiomeModifications` під час ініціалізації мода. Ми можемо зробити це, додавши наступне до нашого ініціалізатора мода:

@[code lang=java transcludeWith=:::datagen-world:biome-modifications](@/reference/latest/src/main/java/com/example/docs/ExampleMod.java)

::: tip

Для дерев другий параметр має бути `GenerationStep.Decoration.VEGETAL_DECORATION,`

:::

### Особливості генерації біому {#biome-specific-generation}

Змінивши аргумент `BiomeSelectors`, ми можемо створювати нашу функцію лише в певному типі біому:

@[code lang=java transcludeWith=:::datagen-world:selective-biome-modifications](@/reference/latest/src/main/java/com/example/docs/ExampleMod.java)

Це з’явиться лише в біомах, позначених теґом біому `minecraft:is_forest`.

## Запуск генерації даних {#running-datagen}

Тепер, коли ви запускаєте генерацію, ви маєте побачити файл `.json` у `src/main/generated/data/example-mod/worldgen/configured_feature` для кожної налаштованої функції, яку ви додали, а також файл у `src/main/generated/data/example-mod/worldgen/placed_feature` для кожної розміщеної функції!

:::details Згенерований файл для налаштованої функції

@[code lang=json](@/reference/latest/src/main/generated/data/example-mod/worldgen/configured_feature/diamond_block_vein.json)

:::

:::details Згенерований файл для розміщеної функції

@[code lang=json](@/reference/latest/src/main/generated/data/example-mod/worldgen/placed_feature/diamond_block_ore_placed.json)

:::

<!---->
