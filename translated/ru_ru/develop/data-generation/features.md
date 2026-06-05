---
title: Генерация структур
description: Руководство по генерации структур в мире с помощью datagen.
authors:
  - cassiancc
  - CelDaemon
  - its-miroma
  - JonyBoy19
  - Wind292
---

<!---->

:::info ТРЕБОВАНИЯ

Сначала убедитесь, что вы [установили datagen](./setup).

:::

Структуры (Features) в Minecraft — это природные или генерируемые объекты в мире, такие как деревья, цветы, руды или озера. Структуры отличаются от строений (Structures) (например, деревень, храмов...), которые можно найти с помощью команды `/locate`.

Генерация структур в мирах Minecraft делится на 3 части:

- **Конфигурируемые структуры**: определяют, чем является объект; например, одиночное дерево
- **Правила размещения**: определяют, как объекты должны располагаться, в каком направлении, их относительное положение и т. д.; например, размещение деревьев в лесу
- **Модификации биомов**: определяют, где именно структуры размещаются в мире; например, координаты всего леса

## Настройка {#setup}

Сначала необходимо создать наш провайдер. Создайте класс, который расширяет `FabricDynamicRegistryProvider`, внутри основного пакета (`main`) и заполните базовые методы:

<<< @/reference/latest/src/main/java/com/example/docs/worldgen/ExampleModWorldgenProvider.java#datagen_world_provider

В методе `configure` мы вызовем `addAll`, чтобы гарантировать генерацию всех файлов для наших структур.

<<< @/reference/latest/src/main/java/com/example/docs/worldgen/ExampleModWorldgenProvider.java#worldgen_add_entries

Затем добавьте этот провайдер в ваш класс `DataGeneratorEntrypoint` внутри метода `onInitializeDataGenerator`:

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModDataGenerator.java#add_worldgen_provider

Далее создайте класс для конфигурируемых структур и класс для правил размещения. Им не нужно ничего расширять.

Класс конфигурируемых структур и класс правил размещения должны иметь публичный метод для регистрации и определения ваших структур. Его аргумент (который мы назвали `context`) должен иметь тип `BootstrapContext<ConfiguredFeature<?, ?>>` для конфигурируемой структуры или `BootstrapContext<PlacedFeature>` для правила размещения.

В вашем классе `DataGeneratorEntrypoint` добавьте приведенные ниже строки в метод `buildRegistry`, заменив имя метода на выбранное вами:

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModDataGenerator.java#datagen_world_registries

Если у вас еще нет метода `buildRegistry`, создайте его и добавьте аннотацию `@Override`.

## Конфигурируемые структуры {#configured-features}

Чтобы структура естественно появлялась в нашем мире, мы должны начать с определения конфигурируемой структуры в нашем классе конфигурируемых структур.

Прежде чем что-либо делать, давайте создадим класс конфигурируемых структур внутри основного пакета и объявим метод `configure`:

<<< @/reference/latest/src/main/java/com/example/docs/worldgen/ExampleModWorldConfiguredFeatures.java#datagen_world_configure_features_class

Теперь давайте добавим кастомную конфигурируемую структуру для алмазной жилы. Сначала зарегистрируйте ключ для `ConfiguredFeature` в вашем классе конфигурируемых структур:

<<< @/reference/latest/src/main/java/com/example/docs/worldgen/ExampleModWorldConfiguredFeatures.java#datagen_world_configured_key

::: tip

Второй аргумент в `Identifier` (в данном примере `diamond_block_vein`) — это то, что вы будете использовать для призыва объекта с помощью команды `/place`, что очень полезно при отладке.

:::

### Руды {#ores}

Затем внутри метода `configure` мы создадим `RuleTest`, который определяет, какие блоки может заменять ваша структура. Например, этот `RuleTest` разрешает замену каждого блока с тегом `DEEPSLATE_ORE_REPLACEABLES`:

<<< @/reference/latest/src/main/java/com/example/docs/worldgen/ExampleModWorldConfiguredFeatures.java#datagen_world_ruletest

Далее, также внутри метода `configure`, нам нужно создать `OreConfiguration`, который указывает игре, на что именно заменять блоки.

<<< @/reference/latest/src/main/java/com/example/docs/worldgen/ExampleModWorldConfiguredFeatures.java#datagen_world_ore_feature_config

Вы можете указать несколько вариантов в списке для разных типов поверхностей. Например, давайте настроим разные варианты для камня и глубинного сланца:

<<< @/reference/latest/src/main/java/com/example/docs/worldgen/ExampleModWorldConfiguredFeatures.java#datagen_world_multi_ore_feature_config

Наконец, нам нужно зарегистрировать нашу конфигурируемую структуру в игре внутри метода `configure`!

<<< @/reference/latest/src/main/java/com/example/docs/worldgen/ExampleModWorldConfiguredFeatures.java#datagen_world_conf_feature_register

### Деревья {#trees}

Чтобы создать дерево, вам сначала нужно создать `TreeConfiguration` внутри метода configure:

<<< @/reference/latest/src/main/java/com/example/docs/worldgen/ExampleModWorldConfiguredFeatures.java#datagen_world_tree_feature_config

Вот за что отвечает каждый аргумент:

1. Задает тип блока для ствола дерева; например, алмазные блоки
2. Настраивает форму ствола и алгоритм его высоты с помощью генератора ствола (`TrunkPlacer`)
3. Задает тип блока для листвы дерева; например, золотые блоки
4. Определяет форму и размер листвы с помощью генератора листвы (`FoliagePlacer`)
5. Управляет тем, как ствол дерева сужается на разной высоте (используется преимущественно для больших стволов)

::: tip

Мы _настоятельно_ рекомендуем поэкспериментировать с этими значениями, чтобы создать уникальное дерево, которое понравится именно **вам**!

В качестве ориентира вы можете использовать встроенные генераторы стволов (TrunkPlacer) и листвы (FoliagePlacer) из ванильных деревьев.

:::

Затем нам нужно зарегистрировать наше дерево, добавив следующую строку в метод `configure` класса `ExampleModWorldConfiguredFeatures`.

<<< @/reference/latest/src/main/java/com/example/docs/worldgen/ExampleModWorldConfiguredFeatures.java#datagen_world_tree_register

## Правила размещения структур {#placement-features}

Следующим шагом для добавления структуры в игру является создание правила её размещения (Placement Feature).

Давайте создадим класс для размещения структур внутри основного пакета и добавим в него метод `configure`, как мы делали ранее:

<<< @/reference/latest/src/main/java/com/example/docs/worldgen/ExampleModWorldPlacedFeatures.java#datagen_world_placed_features_class

В методе `configure` вашего класса размещения структур создайте переменную, аналогичную приведенной ниже:

<<< @/reference/latest/src/main/java/com/example/docs/worldgen/ExampleModWorldPlacedFeatures.java#datagen_world_conf_feature_register

В вашем классе размещения структур определите ключ для вашей структуры:

<<< @/reference/latest/src/main/java/com/example/docs/worldgen/ExampleModWorldPlacedFeatures.java#datagen_world_placed_key

### Модификаторы размещения {#placement-modifiers}

Далее внутри метода configure нам нужно определить модификаторы размещения (Placement Modifiers). Это параметры, которые вы задаете для настройки генерации структуры. Они могут быть какими угодно: от частоты появления до начальной высоты по координате Y. Вы можете использовать столько модификаторов, сколько вам необходимо.

<<< @/reference/latest/src/main/java/com/example/docs/worldgen/ExampleModWorldPlacedFeatures.java#datagen_world_placement_modifiers

Функции каждого из перечисленных модификаторов заключаются в следующем:

- **`CountPlacement`**: примерно определяет количество экземпляров этой структуры (в данном случае жил) на один чанк
- **`BiomeFilter`**: позволяет нам контролировать, в каких биомах/измерениях появляется структура (мы подробнее разберем это далее)
- **`InSquarePlacement`**: распределяет структуры по площади чанка более псевдослучайным образом
- **`HeightRangePlacement`**: задает диапазон координат Y, в котором может генерироваться структура. Он поддерживает три основных типа распределения:
  1. **Равномерное** (Uniform): все значения Y в пределах диапазона имеют одинаковую вероятность появления структуры. Если вы сомневаетесь, используйте этот вариант.

  2. **Трапециевидное** (Trapezoid): значения Y, расположенные ближе к середине диапазона, имеют более высокую вероятность появления структуры.

  3. **Смещенное к низу** (Biased-Bottom):

     Использует логарифмическую шкалу, при которой более низкие значения Y имеют большую вероятность получить структуру. Принимает начальную координату Y, ниже которой структура никогда не появится. Второй аргумент — максимальная высота генерации структуры. Третий аргумент определяет диапазон в блоках, на который распространяется максимальная вероятность.

::: tip

Деревья и другие наземные структуры должны включать модификатор `PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP` вместо `HeightRangePlacement`, чтобы гарантировать появление дерева именно на поверхности земли.

:::

Теперь, когда у нас есть модификаторы, мы можем зарегистрировать наше правило размещения структуры в методе `configure`:

<<< @/reference/latest/src/main/java/com/example/docs/worldgen/ExampleModWorldPlacedFeatures.java#datagen_world_register_placed_feature

## Модификации биомов {#biome-modifications}

Ну и напоследок, нужно добавить наше правило размещения структуры в `BiomeModifications` во время инициализации мода. Мы можем сделать это, добавив следующий код в наш [инициализатор мода](../getting-started/project-structure#entrypoints):

<<< @/reference/latest/src/main/java/com/example/docs/ExampleMod.java#datagen_world_biome_modifications

::: tip

Для деревьев, вторым параметром следует установить `GenerationStep.Decoration.VEGETAL_DECORATION,`

:::

### Генерация в конкретных биомах {#biome-specific-generation}

Изменяя аргумент `BiomeSelectors`, мы можем сделать так, чтобы наша структура появлялась только в определенном типе биома:

<<< @/reference/latest/src/main/java/com/example/docs/ExampleMod.java#datagen_world_selective_biome_modifications

В это примере генерация будет происходить только в биомах, отмеченных тегом `minecraft:is_forest`.

## Запуск Datagen {#running-datagen}

Теперь, когда вы запустите генерацию данных (datagen), вы увидите JSON-файл в папке `src/main/generated/data/example-mod/worldgen/configured_feature` для каждой добавленной конфигурируемой структуры, а также файл в папке `src/main/generated/data/example-mod/worldgen/placed_feature` для каждого правила размещения!

:::details Сгенерированный файл конфигурируемой структуры

<<< @/reference/latest/src/main/generated/data/example-mod/worldgen/configured_feature/diamond_block_vein.json

:::

:::details Сгенерированный файл размещения структуры

<<< @/reference/latest/src/main/generated/data/example-mod/worldgen/placed_feature/diamond_block_ore_placed.json

:::

<!---->
