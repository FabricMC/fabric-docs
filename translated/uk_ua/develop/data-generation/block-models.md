---
title: Генерація моделей блоків
description: Тут ви можете навчитися генерувати стани та моделі блоків за допомогою datagen.
authors:
  - Fellteros
  - natri0
  - IMB11
  - its-miroma
---

:::info ПЕРЕДУМОВИ
Спершу переконайтеся, що ви виконали [налаштування datagen](./setup).
:::

## Налаштування {#setup}

По-перше, ми повинні створити наш ModelProvider. Створімо клас `extends FabricModelProvider`. Реалізуйте обидва абстрактні методи: `generateBlockStateModels` і `generateItemModels`.
Нарешті, створімо конструктор, що відповідає super.

@[code lang=java transcludeWith=:::datagen-model:provider](@/reference/latest/src/client/java/com/example/docs/datagen/FabricDocsReferenceModelProvider.java)

Зареєструйте цей клас у своїй `DataGeneratorEntrypoint` в рамках методу `onInitializeDataGenerator`.

## Стани та моделі блоку {#blockstates-and-block-models}

```java
@Override
public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
}
```

Для моделей блоків ми зосереджуватимемося насамперед на методі `generateBlockStateModels`. Зверніть увагу на параметр `BlockStateModelGenerator blockStateModelGenerator` - цей об'єкт відповідатиме за генерацію всіх файлів JSON.
Ось декілька зручних прикладів, які можна використовувати для створення бажаних моделей:

### Усі прості куби

@[code lang=java transcludeWith=:::datagen-model:cube-all](@/reference/latest/src/client/java/com/example/docs/datagen/FabricDocsReferenceModelProvider.java)

Це найпоширеніша функція. Він генерує файл моделі JSON для звичайної моделі ,kjrf `cube_all`. Одна текстура використовується для всіх шести сторін, у цьому випадку ми використовуємо `steel_block`.

@[code](@/reference/latest/src/main/generated/assets/fabric-docs-reference/models/block/steel_block.json)

Він також генерує файл JSON зі станом блоку. Оскільки у нас немає властивостей стану блоку (наприклад, Axis, Facing...), достатньо одного варіанту, який використовується кожного разу, коли блок розміщується.

@[code](@/reference/latest/src/main/generated/assets/fabric-docs-reference/blockstates/steel_block.json)

<0>Блок сталі</0>

### Сінглтони {#singletons}

Метод `registerSingleton` надає файли моделі JSON на основі `TexturedModel`, який ви передаєте, і єдиного варіанту стану блоку.

@[code lang=java transcludeWith=:::datagen-model:cube-top-for-ends](@/reference/latest/src/client/java/com/example/docs/datagen/FabricDocsReferenceModelProvider.java)

Цей метод створить моделі для звичайного куба, який використовує файл текстури `pipe_block` для сторін і файл текстури `pipe_block_top` для верхньої та нижньої сторін.

@[code](@/reference/latest/src/main/generated/assets/fabric-docs-reference/models/block/pipe_block.json)

:::tip
Якщо ви не можете вибрати, яку `TextureModel` використовувати, відкрийте клас `TexturedModel` і подивіться на [`TextureMaps`](#using-texture-map)!
:::

<DownloadEntry visualURL="/assets/develop/data-generation/block-model/pipe_block_textures_big.png" downloadURL="/assets/develop/data-generation/block-model/pipe_block_textures.zip">Блок труби</DownloadEntry>

### Пул текстур блока {#block-texture-pool}

@[code lang=java transcludeWith=:::datagen-model:block-texture-pool-normal](@/reference/latest/src/client/java/com/example/docs/datagen/FabricDocsReferenceModelProvider.java)

Іншим корисним методом є `registerCubeAllModelTexturePool`: визначте текстури, передавши «base block», а потім додайте «children», які матимуть ті самі текстури.
У цьому випадку ми передали `RUBY_BLOCK`, тому сходи, плита та паркан використовуватимуть текстуру `RUBY_BLOCK`.

:::warning
Він також створить [просту модель куба з усіма JSON](#simple-cube-all) для «base block», щоб переконатися, що він має модель блоку.

Зверніть увагу на це, якщо ви змінюєте модель блоку цього конкретного блоку, оскільки це призведе до помилки.
:::

Ви також можете додати `BlockFamily`, який генеруватиме моделі для всіх своїх «children».

@[code lang=java transcludeWith=:::datagen-model:family-declaration](@/reference/latest/src/main/java/com/example/docs/block/ModBlocks.java)

@[code lang=java transcludeWith=:::datagen-model:block-texture-pool-family](@/reference/latest/src/client/java/com/example/docs/datagen/FabricDocsReferenceModelProvider.java)

<DownloadEntry visualURL="/assets/develop/data-generation/block-model/ruby_block_big.png" downloadURL="/assets/develop/data-generation/block-model/ruby_block.png">Блок рубіну</DownloadEntry>

### Двері та люки {#doors-and-trapdoors}

@[code lang=java transcludeWith=:::datagen-model:door-and-trapdoor](@/reference/latest/src/client/java/com/example/docs/datagen/FabricDocsReferenceModelProvider.java)

Двері та люки трохи відрізняються. Тут ви повинні створити три нові текстури - дві для дверей і одну для люка.

1. Двері:
   - Він має дві частини - верхню половину і нижню половину. **Кожному потрібна власна текстура:** у цьому випадку `ruby_door_top` для верхньої половини та `ruby_door_bottom` для нижньої.
   - Метод registerDoor() створить моделі для всіх орієнтацій дверей, як відкритих, так і закритих.
   - **Вам також потрібна текстура предмета!** Покладіть її в теку `assets/<mod_id>/textures/item/`.
2. Люк:
   - Тут вам потрібна лише одна текстура, у цьому випадку під назвою `ruby_trapdoor`. Він буде використовуватися для всіх сторін.
   - Оскільки `TrapdoorBlock` має властивість `FACING`, ви можете використовувати закоментований метод для генерації файлів моделі з повернутими текстурами = люк буде "орієнтованим". В іншому випадку він виглядатиме однаково незалежно від того, у якому напрямку він дивиться.

<DownloadEntry visualURL="/assets/develop/data-generation/block-model/ruby_door_trapdoor_big.png" downloadURL="/assets/develop/data-generation/block-model/ruby_door_trapdoor_textures.zip">Рубінові двері та люки</DownloadEntry>

## Власні моделі блоку

У цьому розділі ми створимо моделі для вертикальної дубової колоди з текстурами дубової колоди.

Точка 2. - 6. оголошуються у внутрішньому статичному допоміжному класі під назвою `CustomBlockStateModelGenerator`._

### Власний клас блоку {#custom-block-class}

Створіть блок `VerticalSlab` з властивостями `FACING` і булевою властивістю `SINGLE`, як у підручнику [Block States](../blocks/blockstates). `SINGLE` вкаже, чи є обидві плити.
Тоді вам слід перевизначити `getOutlineShape` і `getCollisionShape`, щоб контур промальовувався правильно, а блок мав правильну форму колізії.

@[code lang=java transcludeWith=:::datagen-model-custom:voxels](@/reference/latest/src/main/java/com/example/docs/block/custom/VerticalSlabBlock.java)

@[code lang=java transcludeWith=:::datagen-model-custom:collision](@/reference/latest/src/main/java/com/example/docs/block/custom/VerticalSlabBlock.java)

Також замініть метод `canReplace()`, інакше ви не зможете зробити плиту повним блоком.

@[code lang=java transcludeWith=:::datagen-model-custom:replace](@/reference/latest/src/main/java/com/example/docs/block/custom/VerticalSlabBlock.java)

І готово! Тепер ви можете протестувати блок і помістити його в гру.

### Батьківська модель блоку {#parent-block-model}

Давайте но створимо батьківську модель блоку. Він визначатиме розмір, положення в руці чи інших слотах, а також координати `x` і `y` текстури.
Для цього рекомендується використовувати такий редактор, як [Blockbench](https://www.blockbench.net/), оскільки створення вручну є справді виснажливим процесом. Це має виглядати приблизно так:

@[code lang=json](@/reference/latest/src/main/resources/assets/fabric-docs-reference/models/block/vertical_slab.json)

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

Значення `bottom` замінить заповнювач `#bottom` і так далі. **Помістіть його в теку `resources/assets/mod_id/models/block/`.**

### Власна модель {#custom-model}

Ще нам знадобиться екземпляр класу `Model`. Він представлятиме фактичну [батьківську модель блоку](#parent-block-model) у нашому моді.

@[code lang=java transcludeWith=:::datagen-model-custom:model](@/reference/latest/src/client/java/com/example/docs/datagen/FabricDocsReferenceModelProvider.java)

Метод `block()` створює нову `модель`, вказуючи на файл `vertical_slab.json` у теці `resources/assets/mod_id/models/block/`.
`TextureKey` представляють "заповнювачі" (`#bottom`, `#top`, ...) як об'єкт.

### Використання мапи текстури {#using-texture-map}

Що робить `TextureMap`? Він фактично надає ідентифікатори, які вказують на текстури. Технічно вона поводиться як звичайна мапа – ви пов’язуєте `TextureKey` (ключ) з `ідентифікатором` (значення).

Ви можете використати стандартні, як-от `TextureMap.all()` (який пов’язує всі TextureKeys з тим самим ідентифікатором), або створити новий, створивши новий екземпляр, а потім використавши `.put()` для зв’язування ключів зі значеннями.

:::tip
`TextureMap.all()` пов'язує всі TextureKeys з одним ідентифікатором, незалежно від того, скільки їх є!
:::

Оскільки ми хочемо використовувати текстури дубової колоди, але маємо `BOTTOM`, `TOP` і `SIDE` `TextureKey`, нам потрібно створити нову.

@[code lang=java transcludeWith=:::datagen-model-custom:texture-map](@/reference/latest/src/client/java/com/example/docs/datagen/FabricDocsReferenceModelProvider.java)

Для `нижньої` та `верхньої` граней використовуватиметься `oak_log_top.png`, а з боків — `oak_log.png`.

:::warning
Усі `TextureKey`s у TextureMap **мають** збігатися з усіма `TextureKey`s у вашій моделі батьківського блоку!
:::

### Власний метод `BlockStateSupplier` {#custom-supplier-method}

`BlockStateSupplier` містить усі варіанти стану блоку, їх rotation та інші параметри, як-от uvlock.

@[code lang=java transcludeWith=:::datagen-model-custom:supplier](@/reference/latest/src/client/java/com/example/docs/datagen/FabricDocsReferenceModelProvider.java)

Спочатку ми створюємо новий `VariantsBlockStateSupplier` за допомогою `VariantsBlockStateSupplier.create()`.
Потім ми створюємо новий `BlockStateVariantMap`, який містить параметри для всіх варіантів блоку, в цьому випадку `FACING` і `SINGLE`, і передаємо його в `VariantsBlockStateSupplier`.
Укажіть, яка модель і які перетворення (uvlock, rotation) використовуються під час використання `.register()`.
Наприклад:

- На першому рядку блок дивиться на північ і є єдиним => ми використовуємо модель без повороту.
- На четвертому рядку блок дивиться на захід і є одинарним => ми повертаємо модель по осі Y на 270°.
- На шостому рядку блок дивиться на схід, але не одинарний => він виглядає як звичайна дубова колода => нам не потрібно його обертати.

### Власний метод datagen {#custom-datagen-method}

Останній крок – створення фактичного методу, який можна викликати, і який генеруватиме JSON.
Але для чого ці параметри?

1. `Generator BlockStateModelGenerator`, той самий, який передано в `generateBlockStateModels`.
2. `Block vertSlabBlock` — це блок, для якого ми будемо генерувати файли JSON.
3. `Block fullBlock` - це модель, яка використовується, коли властивість `SINGLE` має значення false = блок плити виглядає як повний блок.
4. `TextureMap textures` визначає фактичні текстури, які використовує модель. Див. розділ [використання мапи текстур](#using-texture-map).

@[code lang=java transcludeWith=:::datagen-model-custom:gen](@/reference/latest/src/client/java/com/example/docs/datagen/FabricDocsReferenceModelProvider.java)

Спочатку ми отримуємо `ідентифікатор` моделі однієї плити за допомогою `VERTICAL_SLAB.upload()`. Потім ми отримуємо `ідентифікатор` моделі повного блоку за допомогою `ModelIds.getBlockModelId()` і передаємо ці дві моделі в `createVerticalSlabBlockStates`.
`BlockStateSupplier` передається в `blockStateCollector`, так що файли JSON фактично генеруються.
Крім того, ми створюємо модель для предмета вертикальної плити за допомогою `BlockStateModelGenerator.registerParentedItemModel()`.

І це все! Тепер все, що залишилося зробити, це викликати наш метод у нашому `ModelProvider`:

@[code lang=java transcludeWith=:::datagen-model-custom:method-call](@/reference/latest/src/client/java/com/example/docs/datagen/FabricDocsReferenceModelProvider.java)

## Джерела та посилання {#sources-and-links}

Ви можете переглянути приклади тестів у [Fabric API](https://github.com/FabricMC/fabric/blob/1.21.4/fabric-data-generation-api-v1/src/) та в цій документації [Reference Mod](https://github.com/FabricMC/fabric-docs/tree/main/reference) для отримання додаткової інформації.

Ви також можете знайти більше прикладів використання власних методів даних, переглянувши відкритий вихідний код модів, наприклад [Vanilla+ Blocks](https://github.com/Fellteros/vanillablocksplus) і [Vanilla+ Verticals](https://github.com/Fellteros/vanillavsplus) від Fellteros.
