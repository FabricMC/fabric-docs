---
title: Генерація функцій
description: Дізнайтеся, як генерувати функції у світі за допомогою генерації даних.
authors:
  - cassiancc
  - CelDaemon
  - its-miroma
  - JonyBoy19
  - Wind292
---

<!---->

:::info ПЕРЕДУМОВИ

Спершу переконайтеся, що ви виконали процес [налаштування генерації даних](./setup).

:::

Функції Minecraft — це природні або створені шаблони світу, наприклад дерева, квіти, руди чи озера. Функції відрізняються від структур (наприклад, сіл, храмів…), які можна знайти за допомогою команди `/locate`.

Генерація функцій світів Minecraft розбита на 3 частини:

- **Налаштовані функції**: визначають, що таке функція; наприклад, одне дерево
- **Функції розміщення**: тут визначається, як мають розміщуватися функції, у якому напрямку, відносне розміщення тощо; наприклад, розміщення дерев у лісі
- **Модифікації біому**: це визначає, де у світі розміщені функції; наприклад, координати всього лісу

## Налаштування {#setup}

По-перше, нам потрібно створити свого постачальника. Створіть клас, який розширює `FabricDynamicRegistryProvider` усередині пакета `main`, і заповніть базові методи:

<<< @/reference/latest/src/main/java/com/example/docs/worldgen/ExampleModWorldgenProvider.java#datagen_world_provider

У методі `configure` ми викличемо `addAll`, щоб забезпечити створення всіх файлів для наших функцій.

<<< @/reference/latest/src/main/java/com/example/docs/worldgen/ExampleModWorldgenProvider.java#worldgen_add_entries

Потім додайте цього постачальника до свого класу `DataGeneratorEntrypoint` у методі `onInitializeDataGenerator`:

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModDataGenerator.java#add_worldgen_provider

Далі створіть клас для налаштованих функцій і клас для розміщених функцій. Їм не потрібно нічого розширювати.

Налаштований клас функцій і розміщений клас функцій повинні мати загальнодоступний метод для реєстрації та визначення ваших функцій. Його аргумент, який ми назвали `context`, має бути `BootstrapContext<ConfiguredFeature<?, ?>>` для налаштованої функції або `BootstrapContext<PlacedFeature>` для розміщеної функції.

У вашому класі `DataGeneratorEntrypoint` додайте наведені нижче рядки до методу `buildRegistry`, замінивши назву методу тим, що ви вибрали:

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModDataGenerator.java#datagen_world_registries

Якщо у вас ще немає методу `buildRegistry`, створіть його та додайте анотацію `@Override`.

## Налаштовані функції {#configured-features}

Щоб функція природно з’явилася в нашому світі, ми повинні почати з визначення налаштованої функції в нашому класі налаштованих функцій.

Перш ніж ми зможемо щось зробити, створімо клас налаштованих функцій усередині пакета `main` і оголосимо метод `configure`:

<<< @/reference/latest/src/main/java/com/example/docs/worldgen/ExampleModWorldConfiguredFeatures.java#datagen_world_configure_features_class

Тепер, додаймо спеціальну налаштовану функцію для жили діамантової руди. Спочатку зареєструйте ключ для `ConfiguredFeature` у вашому налаштованому класі функцій:

<<< @/reference/latest/src/main/java/com/example/docs/worldgen/ExampleModWorldConfiguredFeatures.java#datagen_world_configured_key

::: tip

Другий аргумент для `Identifier` (`diamond_block_vein` у цьому прикладі) — це те, що ви б використали для появи в структурі за допомогою команди `/place`, що корисно для налагодження.

:::

### Руди {#ores}

Далі ми створимо `RuleTest` усередині методу `configure`, який керує тим, які блоки ваша функція може замінити. Наприклад, цей `RuleTest` дозволяє замінювати кожен блок на теґ `DEEPSLATE_ORE_REPLACEABLES`:

<<< @/reference/latest/src/main/java/com/example/docs/worldgen/ExampleModWorldConfiguredFeatures.java#datagen_world_ruletest

Далі, також у методі `configure`, нам потрібно створити `OreConfiguration`, який повідомляє грі, чим замінити блоки.

<<< @/reference/latest/src/main/java/com/example/docs/worldgen/ExampleModWorldConfiguredFeatures.java#datagen_world_ore_feature_config

Ви можете мати кілька випадків у списку для різних варіантів. Наприклад, установімо інший варіант для каменю та глиболанцю:

<<< @/reference/latest/src/main/java/com/example/docs/worldgen/ExampleModWorldConfiguredFeatures.java#datagen_world_multi_ore_feature_config

Нарешті, нам потрібно зареєструвати нашу налаштовану функцію в нашій грі в методі `configure`!

<<< @/reference/latest/src/main/java/com/example/docs/worldgen/ExampleModWorldConfiguredFeatures.java#datagen_world_conf_feature_register

### Дерева {#trees}

Щоб створити спеціальне дерево, вам потрібно спочатку створити `TreeConfiguration` усередині методу `configure`:

<<< @/reference/latest/src/main/java/com/example/docs/worldgen/ExampleModWorldConfiguredFeatures.java#datagen_world_tree_feature_config

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

<<< @/reference/latest/src/main/java/com/example/docs/worldgen/ExampleModWorldConfiguredFeatures.java#datagen_world_tree_register

## Функції розміщення {#placement-features}

Наступним кроком у додаванні функції до гри є створення її розміщення.

Створімо клас розміщених функцій всередині пакета `main` і надамо йому метод `configure`, як раніше:

<<< @/reference/latest/src/main/java/com/example/docs/worldgen/ExampleModWorldPlacedFeatures.java#datagen_world_placed_features_class

У методі `configure` вашого розміщеного класу функцій створіть змінну, подібну до наведеної нижче:

<<< @/reference/latest/src/main/java/com/example/docs/worldgen/ExampleModWorldPlacedFeatures.java#datagen_world_conf_feature_register

У вашому класі розміщення функції визначте ключ для розміщеної функції:

<<< @/reference/latest/src/main/java/com/example/docs/worldgen/ExampleModWorldPlacedFeatures.java#datagen_world_placed_key

### Модифікатори розміщення {#placement-modifiers}

Далі нам потрібно визначити наші модифікатори розміщення в методі `configure`, які є атрибутами, які ви встановлюєте під час створення функції. Це може бути що завгодно: від частоти появи до початкового рівня `y`. Ви можете мати стільки модифікаторів, скільки забажаєте.

<<< @/reference/latest/src/main/java/com/example/docs/worldgen/ExampleModWorldPlacedFeatures.java#datagen_world_placement_modifiers

Функції кожного модифікатора в списку такі:

- **`CountPlacement`**: Приблизно кількість екземплярів цієї функції (у цьому випадку жил) на чанк
- **`BiomeFilter`**: дозволяє нам контролювати, у яких біоми/виміри з'являється (ми зробимо це пізніше)
- **`InSquarePlacement`**: розподіляє функції більш псевдовипадково
- **`HeightRangePlacement`**: визначає діапазон координат `y`, де може з’явитися функція; вона підтримує три основні типи дистрибуції:
  1. **Uniform**: Усі значення `y` в межах діапазону з однаковою ймовірністю містять функцію. Якщо ви не впевнені, просто скористайтеся цим.

  2. **Trapezoid**:
     Значення `y`, ближчі до середнього значення `y`, мають вищу ймовірність містити функцію.

  3. **Biased-Bottom**:

     Використовує логарифмічну шкалу, де нижчі значення `y` мають більшу ймовірність отримати функцію. Він отримує початкову координату `y`, нижче якої функція ніколи не створюється. Другий аргумент — це максимальна висота, на якій може з’явитися функція. Третій аргумент визначає діапазон у блоках, на який поширюється максимальна ймовірність.

::: tip

Дерева та інші поверхневі структури мають містити модифікатор `PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP` замість `HeightRangePlacement`, щоб переконатися, що дерево з’являється на поверхні.

:::

Тепер, коли у нас є модифікатори, ми можемо зареєструвати нашу розміщену функцію в методі `configure`:

<<< @/reference/latest/src/main/java/com/example/docs/worldgen/ExampleModWorldPlacedFeatures.java#datagen_world_register_placed_feature

## Модифікації біому {#biome-modifications}

Нарешті, нам потрібно додати нашу розміщену функцію до `BiomeModifications` під час ініціалізації мода. Ми можемо зробити це, додавши наступне до нашого [ініціалізатора мода](../getting-started/project-structure#entrypoints):

<<< @/reference/latest/src/main/java/com/example/docs/ExampleMod.java#datagen_world_biome_modifications

::: tip

Для дерев другий параметр має бути `GenerationStep.Decoration.VEGETAL_DECORATION,`

:::

### Особливості генерації біому {#biome-specific-generation}

Змінивши аргумент `BiomeSelectors`, ми можемо створювати нашу функцію лише в певному типі біому:

<<< @/reference/latest/src/main/java/com/example/docs/ExampleMod.java#datagen_world_selective_biome_modifications

Це з’явиться лише в біомах, позначених теґом біому `minecraft:is_forest`.

## Запуск генерації даних {#running-datagen}

Тепер, коли ви запускаєте генерацію, ви маєте побачити файл `.json` у `src/main/generated/data/example-mod/worldgen/configured_feature` для кожної налаштованої функції, яку ви додали, а також файл у `src/main/generated/data/example-mod/worldgen/placed_feature` для кожної розміщеної функції!

:::details Згенерований файл для налаштованої функції

<<< @/reference/latest/src/main/generated/data/example-mod/worldgen/configured_feature/diamond_block_vein.json

:::

:::details Згенерований файл для розміщеної функції

<<< @/reference/latest/src/main/generated/data/example-mod/worldgen/placed_feature/diamond_block_ore_placed.json

:::

<!---->
