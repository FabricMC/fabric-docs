---
title: Генерация моделей предметов
description: Руководство по генерации моделей предметов с помощью datagen.
authors:
  - CelDaemon
  - Fellteros
  - skycatminepokie
  - VatinMc
resources:
  https://github.com/FabricMC/fabric/blob/1.21.11/fabric-data-generation-api-v1/src/: Примеры тестов из Fabric API
---

<!---->

:::info ТРЕБОВАНИЯ

Убедитесь что вы завершили [установку datagen](./setup) и создали свой [первый предмет](../items/first-item).

:::

Для каждой модели предмета, которую мы хотим сгенерировать, необходимо создать два отдельных JSON-файла:

1. **Модель предмета** (Item model): определяет текстуры, вращение и общий вид предмета. Сохраняется в директорию `generated/assets/example-mod/models/item`.
2. **Клиентский предмет** (Client item): определяет, какая модель должна использоваться на основе различных критериев (компоненты, взаимодействия и т. д.). Сохраняется в директорию `generated/assets/example-mod/items`.

## Настройка{#setup}

Сначала необходимо создать наш провайдер моделей.

::: tip

Вы можете повторно использовать класс `FabricModelProvider`, созданный на этапе [генерации моделей блоков](./block-models#setup).

:::

Создайте класс, расширяющий `FabricModelProvider` и реализуйте оба абстрактных метода: `generateBlockStateModels` и `generateItemModels`.
Затем создайте конструктор, соответствующий суперклассу (super).

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModModelProvider.java#provider

Зарегистрируйте этот класс в вашем `DataGeneratorEntrypoint` внутри метода `onInitializeDataGenerator`.

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModDataGenerator.java#datagen_models_register

## Встроенные модели предметов {#build-in}

Для создания моделей предметов мы будем использовать метод `generateItemModels`. Его параметр `ItemModelGenerators itemModelGenerator` отвечает за генерацию моделей и содержит необходимые для этого методы.

Ниже приведено описание наиболее часто используемых методов генератора моделей предметов.

### Простые {#simple}

Простые модели предметов используются по умолчанию для большинства предметов в Minecraft. Их родительской моделью (parent model) является `GENERATED`. В инвентаре они используют 2D-текстуру, а в игре рендерятся в 3D. Примеры: лодки, свечи или красители.

::: tabs

== Исходный код

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModModelProvider.java#generated

== Предмет на клиенте

`generated/assets/example-mod/items/ruby.json`

<<< @/reference/latest/src/main/generated/assets/example-mod/items/ruby.json

== Модель предмета

`generated/assets/example-mod/models/item/ruby.json`

<<< @/reference/latest/src/main/generated/assets/example-mod/models/item/ruby.json

Точные значения по умолчанию для поворота, масштабирования и расположения модели можно найти в файле [`generated.json`, содержащемся в ресурсах Minecraft](https://mcasset.cloud/1.21.11/assets/minecraft/models/item/generated.json).

== Текстуры

<DownloadEntry visualURL="/assets/develop/data-generation/item-model/ruby_big.png" downloadURL="/assets/develop/data-generation/item-model/ruby.png">Текстура блока рубинов</DownloadEntry>

:::

### Удерживаемые в руке {#handheld}

Модели типа `Handheld` обычно используются для инструментов и оружия (топоры, мечи, трезубец). Они повернуты и расположены в пространстве немного иначе, чем простые модели, чтобы выглядеть в руке более естественно.

::: tabs

== Исходный код

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModModelProvider.java#handheld

== Предмет на клиенте

`generated/assets/example-mod/items/guidite_axe.json`

<<< @/reference/latest/src/main/generated/assets/example-mod/items/guidite_axe.json

== Модель предмета

`generated/assets/example-mod/models/item/guidite_axe.json`

<<< @/reference/latest/src/main/generated/assets/example-mod/models/item/guidite_axe.json

Точные значения по умолчанию для поворота, масштабирования и расположения модели можно найти в файле [`handheld.json`, содержащемся в ресурсах Minecraft](https://mcasset.cloud/1.21.11/assets/minecraft/models/item/handheld.json).

== Текстуры

<DownloadEntry visualURL="/assets/develop/data-generation/item-model/guidite_axe_big.png" downloadURL="/assets/develop/data-generation/item-model/guidite_axe.png">Текстура топора Guidite</DownloadEntry>

:::

### Окрашиваемые {#dyeable}

Метод для окрашиваемых предметов генерирует простую модель предмета и клиентский предмет, который задает цвет оттенка (tint color). Этому методу требуется дефолтное десятичное значение цвета, которое используется, когда предмет не окрашен. Дефолтное значение для кожи — `0xFFA06540`.

:::: tabs

== Исходный код

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModModelProvider.java#dyeable

:::warning ВАЖНО

Вам необходимо создать [`DyeRecipe`](./recipes#dye-recipes) для вашего предмета, чтобы его можно было окрашивать в инвентаре!

:::

== Предмет на клиенте

`generated/assets/example-mod/items/leather_gloves.json`

<<< @/reference/latest/src/main/generated/assets/example-mod/items/leather_gloves.json

== Модель предмета

`generated/assets/example-mod/models/item/leather_gloves.json`

<<< @/reference/latest/src/main/generated/assets/example-mod/models/item/leather_gloves.json

== Текстуры

<DownloadEntry visualURL="/assets/develop/data-generation/item-model/leather_gloves_big.png" downloadURL="/assets/develop/data-generation/item-model/leather_gloves.png">Текстура кожаных перчаток</DownloadEntry>

== Предпросмотр

![Окрашивание кожаных перчаток](/assets/develop/data-generation/item-model/leather_gloves_dyeing.png)

::::

### Условные {#conditional}

Далее мы рассмотрим генерацию моделей предметов, которые меняют свой внешний вид при выполнении определенного условия, задаваемого вторым параметром `BooleanProperty`. Вот некоторые из них:

| Свойство        | Описание                                                                                                                                        |
| --------------- | ----------------------------------------------------------------------------------------------------------------------------------------------- |
| `IsKeybindDown` | Значение «True», если нажата указанная клавиша.                                                                                 |
| `IsUsingItem`   | Значение «True», если предмет используется (например, при блокировании щитом).                               |
| `Broken`        | Значение «True», если у предмета прочность равна 0 (например, у элитр текстура меняется, когда они сломаны). |
| `HasComponent`  | Значение «True», если у предмета есть определённый компонент.                                                                   |

Третий и четвертый параметры — это модели, которые будут использоваться, когда свойство принимает значения `true` или `false` соответственно.

:::: tabs

== Исходный код

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModModelProvider.java#condition

:::warning ВАЖНО

Чтобы получить `Identifier`, который передается в `ItemModelUtils.plainModel()`, всегда используйте `itemModelGenerator.createFlatItemModel()`. В противном случае будут сгенерированы только клиентские предметы, а не сами модели!

:::

== Предмет на клиенте

`generated/assets/example-mod/items/flashlight.json`

<<< @/reference/latest/src/main/generated/assets/example-mod/items/flashlight.json

== Модели предметов

`generated/assets/example-mod/models/item/flashlight.json`

<<< @/reference/latest/src/main/generated/assets/example-mod/models/item/flashlight.json

`generated/assets/example-mod/models/item/flashlight_lit.json`

<<< @/reference/latest/src/main/generated/assets/example-mod/models/item/flashlight_lit.json

== Текстуры

<DownloadEntry visualURL="/assets/develop/data-generation/item-model/flashlight_textures_big.png" downloadURL="/assets/develop/data-generation/item-model/flashlight_textures.zip">Текстура фонарика</DownloadEntry>

== Предпросмотр

<VideoPlayer src="/assets/develop/data-generation/item-model/flashlight_turning_on.webm">Фонарик включается и выключается</VideoPlayer>

::::

### Составные {#composite}

Составные модели предметов состоят из одной или нескольких текстур, наложенных друг на друга слоями. Для этого в ванильной игре нет готовых методов; вам нужно использовать поле `itemModelOutput` объекта `itemModelGenerator` и вызвать на нем метод `accept()`.

::: tabs

== Исходный код

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModModelProvider.java#composite

== Предмет на клиенте

`generated/assets/example-mod/items/enhanced_hoe.json`

<<< @/reference/latest/src/main/generated/assets/example-mod/items/enhanced_hoe.json

== Модели предметов

`generated/assets/example-mod/models/item/enhanced_hoe.json`

<<< @/reference/latest/src/main/generated/assets/example-mod/models/item/enhanced_hoe.json

`generated/assets/example-mod/models/item/enhanced_hoe_plus.json`

<<< @/reference/latest/src/main/generated/assets/example-mod/models/item/enhanced_hoe_plus.json

== Текстуры

<DownloadEntry visualURL="/assets/develop/data-generation/item-model/enhanced_hoe_textures_big.png" downloadURL="/assets/develop/data-generation/item-model/enhanced_hoe_textures.zip">Текстура улучшенной мотыги</DownloadEntry>

:::

### Выборочные {#select}

Рендерит модель предмета на основе значения определенного свойства. Вот некоторые из них:

| Свойство            | Описание                                                                                                                                                                                                                     |
| ------------------- | ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| `ContextDimension`  | Отображает модель предмета в зависимости от измерения, в котором находится игрок (Overworld, Nether, End).                                                                                |
| `MainHand`          | Отображает модель предмета, когда он находится в основной руке игрока.                                                                                                                                       |
| `DisplayContext`    | Отображает модель предмета в зависимости от того, где он находится (`ground` - на земле, `fixed` - на фиксированном объекте, `head` - на голове и т. д.). |
| `ContextEntityType` | Отображает модель предмета в зависимости от сущности, удерживающей этот предмет.                                                                                                                             |

В этом примере текстура предмета меняется при перемещении между измерениями: в обычном мире он зелёный, в нижнем мире — красный, а в мире края — чёрный.

::: tabs

== Исходный код

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModModelProvider.java#select

== Предмет на клиенте

`generated/assets/example-mod/items/dimensional_crystal.json`

<<< @/reference/latest/src/main/generated/assets/example-mod/items/dimensional_crystal.json

== Модели предметов

`generated/assets/example-mod/models/item/dimensional_crystal_overworld.json`

<<< @/reference/latest/src/main/generated/assets/example-mod/models/item/dimensional_crystal_overworld.json

`generated/assets/example-mod/models/item/dimensional_crystal_nether.json`

<<< @/reference/latest/src/main/generated/assets/example-mod/models/item/dimensional_crystal_nether.json

`generated/assets/example-mod/models/item/dimensional_crystal_end.json`

<<< @/reference/latest/src/main/generated/assets/example-mod/models/item/dimensional_crystal_end.json

== Текстуры

<DownloadEntry visualURL="/assets/develop/data-generation/item-model/dimensional_crystal_textures_big.png" downloadURL="/assets/develop/data-generation/item-model/dimensional_crystal_textures.zip">Текстура пространственного кристалла</DownloadEntry>

== Предпросмотр

![Меняющаяся, в зависимости от измерения, текстура кристалла](/assets/develop/data-generation/item-model/crystal.png)

:::

### Распределение по диапазону {#range-dispatch}

Рендерит модель предмета на основе значения числового свойства (numeric property). Принимает предмет и список вариантов, каждый из которых привязан к конкретному значению. Примеры: компас, лук и кисть.

Существует много поддерживаемых свойств, вот некоторые из них:

| Свойство      | Описание                                                                                                             |
| ------------- | -------------------------------------------------------------------------------------------------------------------- |
| `Cooldown`    | Рендерит модель предмета на основе оставшегося времени перезарядки предмета.                         |
| `Count`       | Рендерит модель предмета на основе количества предметов в слоте.                                     |
| `UseDuration` | Рендерит модель предмета на основе того, как долго предмет уже используется.                         |
| `Damage`      | Рендерит модель предмета на основе урона от атаки (компонент `minecraft:damage`). |

В данном примере используется свойство `Count`, которое меняет текстуру от одного до трех ножей в зависимости от количества предметов в слоте.

::: tabs

== Исходный код

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModModelProvider.java#range_dispatch

== Предмет на клиенте

`generated/assets/example-mod/items/throwing_knives.json`

<<< @/reference/latest/src/main/generated/assets/example-mod/items/throwing_knives.json

== Модели предметов

`generated/assets/example-mod/models/item/throwing_knives_one.json`

<<< @/reference/latest/src/main/generated/assets/example-mod/models/item/throwing_knives_one.json

`generated/assets/example-mod/models/item/throwing_knives_two.json`

<<< @/reference/latest/src/main/generated/assets/example-mod/models/item/throwing_knives_two.json

`generated/assets/example-mod/models/item/throwing_knives_three.json`

<<< @/reference/latest/src/main/generated/assets/example-mod/models/item/throwing_knives_three.json

== Текстуры

<DownloadEntry visualURL="/assets/develop/data-generation/item-model/throwing_knives_textures_big.png" downloadURL="/assets/develop/data-generation/item-model/throwing_knives_textures.zip">Текстуры метательных ножей</DownloadEntry>

== Предпросмотр

![Текстура метательных ножей меняется, в зависимости от количества предметов в слоте](/assets/develop/data-generation/item-model/throwing_knives_example.png)

:::

## Пользовательские модели предметов {#custom}

Генерация моделей предметов не ограничивается только ванильными методами; вы, конечно же, можете создавать свои собственные. В этом разделе мы создадим модель для предмета «Воздушный шар» (balloon).

Все поля и методы для этой части руководства объявлены в статическом внутреннем классе (static inner class) `CustomItemModelGenerator`.

:::details Показать `CustomItemModelGenerator`

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModModelProvider.java#custom_item_model_generator

:::

### Создание родительской модели {#custom-parent}

Сначала давайте создадим родительскую модель предмета (parent item model), которая определяет, как предмет выглядит в игре. Допустим, мы хотим, чтобы воздушный шар выглядел как простая модель предмета, но в увеличенном масштабе.

Для этого мы создадим файл `resources/assets/example-mod/models/item/scaled2x.json`, укажем в качестве родителя (parent) модель `item/generated`, а затем переопределим параметры масштабирования (scaling).

<<< @/reference/latest/src/main/resources/assets/example-mod/models/item/scaled2x.json

Это сделает модель визуально в два раза больше, чем простые модели.

### Создание `ModelTemplate` {#custom-item-model}

Далее нам нужно создать экземпляр класса `ModelTemplate`. Он будет представлять фактическую [родительскую модель](#custom-parent) предмета внутри нашего мода.

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModModelProvider.java#custom_item_model

Метод `item()` создает новый экземпляр `ModelTemplate`, указывая на файл `scaled2x.json`, который мы создали ранее.

Слот текстуры TextureSlot `LAYER0` представляет переменную текстуры `#layer0`, которая затем будет заменена на идентификатор, указывающий на конкретную текстуру.

### Добавление кастомного метода Datagen {#custom-datagen-method}

Последний шаг — создание кастомного метода, который будет вызываться в методе `generateItemModels()` и отвечать за генерацию наших моделей предметов.

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModModelProvider.java#custom_item_datagen_method

Давайте разберем, за что отвечают параметры:

1. `Item item`: предмет, для которого мы генерируем модели.
2. `ItemModelGenerators generator`: тот же объект, который передается в метод `generateItemModels()`. Используется ради его полей.

Сначала мы получаем `Identifier` предмета с помощью `SCALED2X.create()`, передавая туда `TextureMapping` и `modelOutput` из нашего параметра `generator`.

Затем мы используем еще одно его поле — `itemModelOutput` (которое по сути работает как потребитель/consumer) — и вызываем метод `accept()`, чтобы модели действительно сгенерировались.

### Вызов кастомного метода {#custom-call}

Теперь нам осталось только вызвать наш метод внутри метода `generateItemModels()`.

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModModelProvider.java#custom_balloon

Не забудьте добавить файл текстуры!

<DownloadEntry visualURL="/assets/develop/data-generation/item-model/balloon_big.png" downloadURL="/assets/develop/data-generation/item-model/balloon.png">Текстура воздушного шара</DownloadEntry>
