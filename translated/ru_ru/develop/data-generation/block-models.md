---
title: Генерация модели блока
description: Руководство по созданию моделей блоков и состояний блока с помощью datagen.
authors:
  - CelDaemon
  - Fellteros
  - IMB11
  - its-miroma
  - natri0
resources:
  https://www.blockbench.net/: Скачать Blockbench
  https://github.com/FabricMC/fabric/blob/1.21.11/fabric-data-generation-api-v1/src/: Примеры тестов из Fabric API
  https://github.com/Fellteros/vanillablocksplus: Vanilla+ блоки от Fellteros
  https://github.com/Fellteros/vanillavsplus: Vanilla+ вертикали от Fellteros
---

<!---->

:::info ТРЕБОВАНИЯ

Сначала убедитесь, что вы [установили datagen](./setup).

:::

## Установка {#setup}

Во-первых, нам понадобится создать наш ModelProvider. Создайте класс, который расширяет (extends) `FabricModelProvider`. Реализуйте оба абстрактных метода: `generateBlockStateModels` и `generateItemModels`.
Напоследок, создайте конструктор, соответствующий суперклассу (super).

@[code lang=java transcludeWith=:::provider](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModModelProvider.java)

Зарегистрируйте этот класс в вашей точке `DataGeneratorEntrypoint` в методе `onInitializeDataGenerator`.

@[code transcludeWith=:::datagen-models:register](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModDataGenerator.java)

## Состояния блоков и модели блоков {#blockstates-and-block-models}

```java
@Override
public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
}
```

Что касается моделей блоков, мы сосредоточимся в первую очередь на методе `generateBlockStateModels`. Обратите внимание на параметр `BlockStateModelGenerator blockStateModelGenerator` — этот объект будет отвечать за генерацию всех файлов JSON.
Вот несколько полезных примеров, которые вы можете использовать для создания нужных вам моделей:

### Простой куб со всех сторон (Simple Cube All) {#simple-cube-all}

@[code lang=java transcludeWith=:::cube-all](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModModelProvider.java)

Это самая часто используемая функция. Она генерирует файл JSON-модели для обычного блока типа `cube_all`. Одна и та же текстура используется для всех шести сторон, в этом случае мы используем `steel_block`.

@[code](@/reference/latest/src/main/generated/assets/example-mod/models/block/steel_block.json)

Она также генерирует JSON-файл состояния блока (blockstate). Поскольку у нас нет свойств состояния блока (напр. Axis, Facing, ...), достаточно одного варианта, который используется каждый раз, когда блок поставлен.

@[code](@/reference/latest/src/main/generated/assets/example-mod/blockstates/steel_block.json)

<DownloadEntry visualURL="/assets/develop/data-generation/block-model/steel_block_big.png" downloadURL="/assets/develop/data-generation/block-model/steel_block.png">Текстура стального блока</DownloadEntry>

### Одиночные варианты (Singletons) {#singletons}

Метод `registerSingleton` предоставляет файлы JSON-моделей на основе переданной вам `TexturedModel` и один вариант blockstate.

@[code lang=java transcludeWith=:::cube-top-for-ends](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModModelProvider.java)

Этот метод генерирует модели для обычного куба, который использует текстурный файл `pipe_block` для сторон и текстурный файл `pipe_block_top` для верхней и нижней сторон.

@[code](@/reference/latest/src/main/generated/assets/example-mod/models/block/pipe_block.json)

::: tip

Если вы затрудняетесь с выбором, какую `TextureModel` использовать, откройте класс `TexturedModel` и посмотрите на [`TextureMapping`](#using-texture-map)!

:::

<DownloadEntry visualURL="/assets/develop/data-generation/block-model/pipe_block_textures_big.png" downloadURL="/assets/develop/data-generation/block-model/pipe_block_textures.zip">Текстура блока трубы</DownloadEntry>

### Набор блочных текстур (Block Texture Pool) {#block-texture-pool}

@[code lang=java transcludeWith=:::block-texture-pool-normal](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModModelProvider.java)

Еще один полезный метод - `registerCubeAllModelTexturePool`: определите текстуры, передав "базовый блок", а затем добавьте "дочерние", которые будут иметь те же самые текстуры.
В данном случае мы передали `RUBY_BLOCK`, поэтому лестница, плита и ограждение будут использовать текстуру `RUBY_BLOCK`.

::: warning

Он также сгенерирует [простую модель куба в формате JSON](#simple-cube-all) для "базового блока", чтобы убедиться, что у него есть модель блока.

Помните об этом, если вы меняете модель блока в данном конкретном блоке, так как это приведет к ошибке en.

:::

Вы также можете добавить `BlockFamily`, который будет генерировать модели для всех своих "детей".

@[code lang=java transcludeWith=:::family-declaration](@/reference/latest/src/main/java/com/example/docs/block/ModBlocks.java)

@[code lang=java transcludeWith=:::block-texture-pool-family](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModModelProvider.java)

<DownloadEntry visualURL="/assets/develop/data-generation/block-model/ruby_block_big.png" downloadURL="/assets/develop/data-generation/block-model/ruby_block.png">Текстура блока рубинов</DownloadEntry>

### Двери и Люки {#doors-and-trapdoors}

@[code lang=java transcludeWith=:::door-and-trapdoor](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModModelProvider.java)

С дверями и люками всё немного иначе. Здесь вам понадобится создать три новые текстуры: две для двери и одну для люка.

1. Дверь:
   - Она состоит из двух частей — верхней и нижней половины. \*\* Каждому нужна своя текстура:\*\* в данном случае `ruby_door_top` для верхней половины и `ruby_door_bottom` для нижней.
   - Метод `registerDoor()` создаст модели для всех ориентаций двери, как открытых, так и закрытых.
   - **Вам также нужна текстура предмета!** Поместите ее в папку `assets/example-mod/textures/item/`.
2. Люк:
   - Здесь вам понадобится только одна текстура, в данном случае с именем `ruby_trapdoor`. Он будет использоваться для всех сторон.
   - Поскольку `TrapdoorBlock` имеет свойство `FACING`, вы можете использовать закомментированный метод для генерации файлов моделей с повернутыми текстурами = люк будет "ориентируемым". В противном случае он будет выглядеть одинаково независимо от того, в какую сторону он направлен.

<DownloadEntry visualURL="/assets/develop/data-generation/block-model/ruby_door_trapdoor_big.png" downloadURL="/assets/develop/data-generation/block-model/ruby_door_trapdoor_textures.zip">Текстуры рубиновой двери и люка</DownloadEntry>

## Пользовательские модели блока {#custom-block-models}

В этом разделе мы создадим модели для вертикального сруба из дубовых бревен с текстурами дубовых бревен.

Все поля и методы для этой части руководства объявлены в статическом вложенном классе (static inner class) с именем `CustomBlockStateModelGenerator`.

:::details Раскрыть класс `CustomBlockStateModelGenerator`

@[code transcludeWith=:::custom-blockstate-model-generator](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModModelProvider.java)

:::

### Класс пользовательского блока (Custom Block Class) {#custom-block-class}

Создайте блок `VerticalSlab` со свойством `FACING` и булевым свойством `SINGLE`, как в учебнике [Block States](../blocks/blockstates). `SINGLE` укажет, есть ли обе плиты.
Затем следует переопределить `getOutlineShape` и `getCollisionShape`, чтобы контур отрисовывался правильно, а блок имел правильную форму столкновения.

@[code lang=java transcludeWith=:::custom-voxels](@/reference/latest/src/main/java/com/example/docs/block/custom/VerticalSlabBlock.java)

@[code lang=java transcludeWith=:::custom-collision](@/reference/latest/src/main/java/com/example/docs/block/custom/VerticalSlabBlock.java)

Также переопределите метод `canReplace()`, иначе вы не сможете сделать плиту полным блоком.

@[code lang=java transcludeWith=:::custom-replace](@/reference/latest/src/main/java/com/example/docs/block/custom/VerticalSlabBlock.java)

Готово! Теперь вы можете протестировать блок и поместить его в игру.

### Модель родительского блока {#parent-block-model}

Теперь давайте создадим модель родительского блока. Он определит размер, положение в руке или других слотах, а также координаты `x` и `y` текстуры.
Рекомендуется использовать для этого редактор, например [Blockbench](https://www.blockbench.net/), т.к. делать это вручную - очень утомительный процесс. Это должно выглядеть примерно так:

@[code lang=json](@/reference/latest/src/main/resources/assets/example-mod/models/block/vertical_slab.json)

Доп. информацию см. в разделе [Как форматируются состояния блоков](https://minecraft.wiki/w/Blockstates_definition_format).
Обратите внимание на ключевые слова `#bottom`, `#top`, `#side`. Они работают как переменные, значения которых могут быть заданы в моделях, использующих текущую модель в качестве родительской (parent).

```json
{
  "parent": "minecraft:block/cube_bottom_top",
  "textures": {
    "bottom": "minecraft:block/sandstone_bottom",
    "side": "minecraft:block/sandstone",
    "top": "minecraft:block/sandstone_top"
  }
}
```

Значение `bottom` заменит заполнитель `#bottom` и т. д. **Поместите его в папку `resources/assets/example-mod/models/block/`.**

### Кастомная модель {#custom-model}

Еще одна вещь, которая нам понадобится -- это экземпляр класса `Model`. Он будет представлять фактическую [модель родительского блока](#parent-block-model) внутри нашего мода.

@[code lang=java transcludeWith=:::custom-model](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModModelProvider.java)

Метод `block()` создает новую `Model`, указывая на файл `vertical_slab.json` в папке `resources/assets/example-mod/models/block/`.
Ключи `TextureSlot` представляют собой "заполнители" (placeholders) (`#bottom`, `#top`, ...) как объект.

### Использование карты текстур {#using-texture-map}

Что делает `TextureMapping`? На самом деле он предоставляет идентификаторы (Identifier), которые указывают на текстуры. Технически он работает как обычная карта (Map) — вы связываете слот текстуры `TextureSlot` (ключ) с идентификатором `Identifier` (значение).

Вы можете использовать либо стандартные ванильные варианты, такие как `TextureMapping.cube()` (который связывает все слоты `TextureSlot` с одним и тем же идентификатором), либо создать свой собственный, создав новый экземпляр класса и затем используя метод `.put()` для связывания ключей со значениями.

::: tip

`TextureMapping.cube()` связывает абсолютно все слоты `TextureSlot` с одним и тем же идентификатором, сколько бы их ни было!

:::

Поскольку мы хотим использовать текстуры дубового бревна, но при этом у нас есть слоты текстур `BOTTOM` (низ), `TOP` (верх) и `SIDE` (бока), нам нужно создать новую карту текстур.

@[code lang=java transcludeWith=:::custom-texture-map](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModModelProvider.java)

Для нижней и верхней граней будет использоваться файл `oak_log_top.png`, для боковых граней — файл `oak_log.png`.

::: warning

Все `TextureSlot` в TextureMapping **должны** совпадать со всеми `TextureSlot` в родительской блочной модели!

:::

### Пользовательский метод `BlockModelDefinitionGenerator` {#custom-supplier-method}

`BlockModelDefinitionGenerator` содержит все варианты состояний блока (blockstate), их вращение и другие параметры, такие как блокировка UV-координат (UV lock).

@[code lang=java transcludeWith=:::custom-supplier](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModModelProvider.java)

Сначала мы создаем новый `BlockModelDefinitionGenerator` с помощью метода `MultiVariantGenerator.dispatch()`.
Затем мы создаем новый `PropertyDispatch`, который содержит параметры для всех вариантов блока — в данном случае `FACING` и `SINGLE` — и передаем его в `MultiVariantGenerator`.
С помощью метода `.register()` укажите, какая модель и какие трансформации (uvlock, вращение) используются.
Например:

- Строка 6: _одиночная_ плита, направленная на север, поэтому мы используем модель без вращения
- Строка 9: _одиночная_ плита, направленная на запад, поэтому мы поворачиваем модель по оси Y на 270°
- Строки 10-13: _двойная_ плита (свойство SINGLE равно false), которая выглядит как полный блок, и нам не нужно её вращать

### Кастомный метод Datagen{#custom-datagen-method}

Последний шаг — создание самого метода, который вы сможете вызывать для генерации JSON-файлов.
Но за что отвечают его параметры?

1. `BlockModelGenerators generator` — тот же генератор, который был передан в `generateBlockStateModels`.
2. `Block vertSlabBlock` — блок, для которого мы будем генерировать JSON-файлы.
3. `Block fullBlock — блок, модель которого используется, когда свойство SINGLE имеет значение false (то есть когда блок плиты выглядит как полный блок).`
4. `TextureMapping textures` — определяет конкретные текстуры, которые использует модель. См. главу [Использование карты текстур](#using-texture-map).

@[code lang=java transcludeWith=:::custom-gen](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModModelProvider.java)

Сначала мы получаем идентификатор (`Identifier`) модели одиночной плиты с помощью `VERTICAL_SLAB.create()`. Затем мы получаем идентификатор модели полного блока с помощью `ModelLocationUtils.getModelLocation()`.

После этого мы передаем обе эти модели в метод `createVerticalSlabBlockStates`, который, в свою очередь, передается в потребитель `blockStateOutput` (consumer), генерирующий JSON-файлы для моделей.

Наконец, мы создаем модель для предмета вертикальной плиты (в инвентаре) с помощью `BlockModelGenerators.registerSimpleItemModel()`.

Вот и всё! Теперь осталось только вызвать наш метод в классе `ModelProvider`:

@[code lang=java transcludeWith=:::custom-method-call](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModModelProvider.java)
