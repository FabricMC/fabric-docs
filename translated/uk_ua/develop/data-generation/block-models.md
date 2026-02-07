---
title: Генерація моделі блока
description: Тут ви можете навчитися генерувати стани та моделі блоків за допомогою datagen.
authors:
  - CelDaemon
  - Fellteros
  - IMB11
  - its-miroma
  - natri0
---

<!---->

:::info ПЕРЕДУМОВИ

Спершу переконайтеся, що ви виконали процес [налаштування datagen](./setup).

:::

## Налаштування {#setup}

По-перше, ми повинні створити наш ModelProvider. Створіть клас, який розширює `FabricModelProvider`. Реалізуйте обидва абстрактні методи: `generateBlockStateModels` і `generateItemModels`.
Нарешті, створімо конструктор, що відповідає super.

@[code lang=java transcludeWith=:::provider](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModModelProvider.java)

Зареєструйте цей клас у своїй `DataGeneratorEntrypoint` в рамках методу `onInitializeDataGenerator`.

@[code transcludeWith=:::datagen-models:register](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModDataGenerator.java)

## Стани та моделі блока {#blockstates-and-block-models}

```java
@Override
public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
}
```

Для моделей блоків ми зосереджуватимемося насамперед на методі `generateBlockStateModels`. Зверніть увагу на параметр `BlockStateModelGenerator blockStateModelGenerator` — цей об'єкт відповідатиме за генерацію всіх файлів JSON.
Ось декілька зручних прикладів, які можна використовувати для створення бажаних моделей:

### Усі прості куби {#simple-cube-all}

@[code lang=java transcludeWith=:::cube-all](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModModelProvider.java)

Це найпоширеніша функція. Він генерує файл моделі JSON для звичайної моделі блока `cube_all`. Одна текстура використовується для всіх шести сторін, у цьому випадку ми використовуємо `steel_block`.

@[code](@/reference/latest/src/main/generated/assets/example-mod/models/block/steel_block.json)

Він також генерує файл JSON зі станом блока. Оскільки у нас немає властивостей стану блока (наприклад, Axis, Facing…), достатньо одного варіанту, який використовується кожного разу, коли блок розміщується.

@[code](@/reference/latest/src/main/generated/assets/example-mod/blockstates/steel_block.json)

<DownloadEntry visualURL="/assets/develop/data-generation/block-model/steel_block_big.png" downloadURL="/assets/develop/data-generation/block-model/steel_block.png">Текстура блока сталі</DownloadEntry>

### Сінглтони {#singletons}

Метод `registerSingleton` надає файли моделі JSON на основі `TexturedModel`, який ви передаєте, і єдиного варіанту стану блока.

@[code lang=java transcludeWith=:::cube-top-for-ends](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModModelProvider.java)

Цей метод створить моделі для звичайного куба, який використовує файл текстури `pipe_block` для сторін і файл текстури `pipe_block_top` для верхньої та нижньої сторін.

@[code](@/reference/latest/src/main/generated/assets/example-mod/models/block/pipe_block.json)

::: tip

Якщо ви не можете вибрати, яку `TextureModel` використовувати, відкрийте клас `TexturedModel` і подивіться на [`TextureMaps`](#using-texture-map)!

:::

<DownloadEntry visualURL="/assets/develop/data-generation/block-model/pipe_block_textures_big.png" downloadURL="/assets/develop/data-generation/block-model/pipe_block_textures.zip">Текстура блока труби</DownloadEntry>

### Пул текстур блока {#block-texture-pool}

@[code lang=java transcludeWith=:::block-texture-pool-normal](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModModelProvider.java)

Іншим корисним методом є `registerCubeAllModelTexturePool`: визначте текстури, передавши «base block», а потім додайте «children», які матимуть ті самі текстури.
У цьому випадку ми передали `RUBY_BLOCK`, тому сходи, плита та паркан використовуватимуть текстуру `RUBY_BLOCK`.

::: warning

Він також створить [просту модель куба з усіма JSON](#simple-cube-all) для «base block», щоб переконатися, що він має модель блока.

Зверніть увагу на це, якщо ви змінюєте модель блока цього конкретного блока, оскільки це призведе до помилки.

:::

Ви також можете додати `BlockFamily`, який генеруватиме моделі для всіх своїх «children».

@[code lang=java transcludeWith=:::family-declaration](@/reference/latest/src/main/java/com/example/docs/block/ModBlocks.java)

@[code lang=java transcludeWith=:::block-texture-pool-family](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModModelProvider.java)

<DownloadEntry visualURL="/assets/develop/data-generation/block-model/ruby_block_big.png" downloadURL="/assets/develop/data-generation/block-model/ruby_block.png">Текстура блока рубіна</DownloadEntry>

### Двері та люки {#doors-and-trapdoors}

@[code lang=java transcludeWith=:::door-and-trapdoor](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModModelProvider.java)

Двері та люки трохи відрізняються. Тут ви повинні створити три нові текстури — дві для дверей і одну для люка.

1. Двері:
   - Він має дві частини — верхню половину і нижню половину. **Кожному потрібна власна текстура:** у цьому випадку `ruby_door_top` для верхньої половини та `ruby_door_bottom` для нижньої.
   - Метод registerDoor() створить моделі для всіх орієнтацій дверей, як відкритих, так і закритих.
   - **Вам також потрібна текстура предмета!** Покладіть її в теку `assets/example-mod/textures/item/`.
2. Люк:
   - Тут вам потрібна лише одна текстура, у цьому випадку під назвою `ruby_trapdoor`. Він буде використовуватися для всіх сторін.
   - Оскільки `TrapdoorBlock` має властивість `FACING`, ви можете використовувати закоментований метод для генерації файлів моделі з повернутими текстурами = люк буде «орієнтованим». В іншому випадку він виглядатиме однаково незалежно від того, у якому напрямку він дивиться.

<DownloadEntry visualURL="/assets/develop/data-generation/block-model/ruby_door_trapdoor_big.png" downloadURL="/assets/develop/data-generation/block-model/ruby_door_trapdoor_textures.zip">Текстури рубінових дверей та люків</DownloadEntry>

## Власні моделі блока {#custom-block-models}

У цьому розділі ми створимо моделі для вертикальної дубової колоди з текстурами дубової колоди.

Усі поля та методи для цієї частини посібника оголошено у статичному внутрішньому класі під назвою `CustomBlockStateModelGenerator`.

:::details Показ `CustomBlockStateModelGenerator`

@[code transcludeWith=:::custom-blockstate-model-generator](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModModelProvider.java)

:::

### Власний клас блока {#custom-block-class}

Створіть блок `VerticalSlab` з властивостями `FACING` і булевою властивістю `SINGLE`, як у підручнику [станів блоків(../blocks/blockstates). `SINGLE` вкаже, чи є обидві плити.
Тоді вам слід перевизначити `getOutlineShape` і `getCollisionShape`, щоб контур рендерився правильно, а блок мав правильну форму колізії.

@[code lang=java transcludeWith=:::custom-voxels](@/reference/latest/src/main/java/com/example/docs/block/custom/VerticalSlabBlock.java)

@[code lang=java transcludeWith=:::custom-collision](@/reference/latest/src/main/java/com/example/docs/block/custom/VerticalSlabBlock.java)

Також замініть метод `canReplace()`, інакше ви не зможете зробити плиту повним блоком.

@[code lang=java transcludeWith=:::custom-replace](@/reference/latest/src/main/java/com/example/docs/block/custom/VerticalSlabBlock.java)

І готово! Тепер ви можете протестувати блок і помістити його в гру.

### Батьківська модель блока {#parent-block-model}

Створімо батьківську модель блока. Він визначатиме розмір, положення в руці чи інших слотах, а також координати `x` і `y` текстури.
Для цього рекомендується використовувати такий редактор, як [Blockbench](https://www.blockbench.net/), оскільки створення вручну є справді виснажливим процесом. Це має виглядати приблизно так:

@[code lang=json](@/reference/latest/src/main/resources/assets/example-mod/models/block/vertical_slab.json)

Перегляньте [як форматуються стани блоків](https://minecraft.wiki/w/Blockstates_definition_format), щоб дізнатися більше.
Зверніть увагу на ключові слова `#bottom`, `#top`, `#side`. Вони діють як змінні, які можуть бути встановлені моделями, які мають цю як батьківську:

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

Значення `bottom` замінить заповнювач `#bottom` і так далі. **Помістіть його в теку `resources/assets/example-mod/models/block/`.**

### Власна модель {#custom-model}

Ще нам знадобиться екземпляр класу `Model`. Він представлятиме фактичну [батьківську модель блока](#parent-block-model) у нашому моді.

@[code lang=java transcludeWith=:::custom-model](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModModelProvider.java)

Метод `block()` створює нову `Model`, вказуючи на файл `vertical_slab.json` у теці `resources/assets/example-mod/models/block/`.
`TextureSlot` представляють «заповнювачі» (`#bottom`, `#top`, …) як об'єкт.

### Використання мапи текстури {#using-texture-map}

Що робить `TextureMapping`? Він фактично надає ідентифікатори, які вказують на текстури. Технічно вона поводиться як звичайна мапа — ви пов’язуєте `TextureSlot` (ключ) з `Identifier` (значення).

Ви можете використати стандартні, як-от `TextureMapping.cube()` (який пов’язує всі `TextureKeys` з тим самим `Identifier`), або створити новий, створивши новий екземпляр, а потім використавши `.put()` для зв’язування ключів зі значеннями.

::: tip

`TextureMapping.cube()` пов'язує всі `TextureSlot` з одним `Identifier`, незалежно від того, скільки їх є!

:::

Оскільки ми хочемо використовувати текстури дубової колоди, але маємо `BOTTOM`, `TOP` і `SIDE` `TextureSlot`s, нам потрібно створити новий.

@[code lang=java transcludeWith=:::custom-texture-map](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModModelProvider.java)

`Нижня` та `верхня` сторони використовуватимуть `oak_log_top.png`, бокові — `oak_log.png`.

::: warning

Усі `TextureSlot` у TextureMap **мають** збігатися з усіма `TextureSlot` у вашій моделі батьківського блока!

:::

### Власний метод `BlockModelDefinitionGenerator` {#custom-supplier-method}

`BlockModelDefinitionGenerator` містить усі варіанти стану блока, його оберт та інші параметри, як-от UV lock.

@[code lang=java transcludeWith=:::custom-supplier](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModModelProvider.java)

Спочатку ми створюємо новий `BlockModelDefinitionGenerator` за допомогою `MultiVariantGenerator.dispatch()`.
Потім ми створюємо новий `PropertyDispatch`, який містить параметри для всіх варіантів блока, в цьому випадку `FACING` і `SINGLE`, і передаємо його в `MultiVariantGenerator`.
Укажіть, яка модель і які перетворення (uvlock, rotation) використовуються під час використання `.register()`.
Наприклад:

- Рядок 6: _одинарна_ плита, звернена на північ, тому ми будемо використовувати модель без повороту
- Рядок 9: _одинарна_ плита, спрямована на захід, тому ми повернемо модель навколо осі Y на 270°
- Рядки 10-13: _не одинарна_ плита, яка виглядає як повний блок, і нам не потрібно її обертати

### Власний метод datagen {#custom-datagen-method}

Останній крок — створення фактичного методу, який можна викликати, і який генеруватиме JSON.
Але для чого ці параметри?

1. `BlockModelGenerators generator`, той самий, який передано в `generateBlockStateModels`.
2. `Block vertSlabBlock` — це блок, для якого ми будемо генерувати файли JSON.
3. `Block fullBlock` — це модель, яка використовується, коли властивість `SINGLE` має значення false = блок плити виглядає як повний блок.
4. `TextureMapping textures` визначає фактичні текстури, які використовує модель. Див. розділ [використання мапи текстур](#using-texture-map).

@[code lang=java transcludeWith=:::custom-gen](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModModelProvider.java)

Спочатку ми отримуємо `Identifier` моделі однієї плити за допомогою `VERTICAL_SLAB.create()`. Потім ми отримуємо `Identifier` моделі повного блока за допомогою `ModelLocationUtils.getModelLocation()`.

Потім ми передаємо ці дві моделі в `createVerticalSlabBlockStates`, який сам передається в споживач `blockStateOutput`, який генерує файли JSON для моделей.

Нарешті, ми створюємо модель для предмета вертикальної плити за допомогою `BlockModelGenerators.registerSimpleItemModel()`.

І це все! Тепер все, що залишилося зробити, це викликати наш метод у нашому `ModelProvider`:

@[code lang=java transcludeWith=:::custom-method-call](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModModelProvider.java)

## Джерела та посилання {#sources-and-links}

Ви можете переглянути приклади тестів у [Fabric API](https://github.com/FabricMC/fabric/blob/1.21.11/fabric-data-generation-api-v1/src/) та [прикладному моді](https://github.com/FabricMC/fabric-docs/tree/main/reference) цієї документації, щоб дізнатися більше.

Ви також можете знайти більше прикладів використання власних методів даних, переглянувши відкритий вихідний код модів, наприклад [Vanilla+ Blocks](https://github.com/Fellteros/vanillablocksplus) і [Vanilla+ Verticals](https://github.com/Fellteros/vanillavsplus) від Fellteros.
