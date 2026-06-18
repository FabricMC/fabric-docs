---
title: Создание вашей первой жидкости
description: Узнайте, как создать жидкость в Minecraft.
authors:
  - AlbanischeWurst
  - AlexiyOrlov
  - cassiancc
  - CelDaemon
  - Clomclem
  - comp500
  - Daomephsta
  - Earthcomputer
  - florensie
  - Fusion-Flux
  - InfinityChances
  - Kilip1000
  - MaxURhino
  - SolidBlock-cn
  - SuperSoupr
  - Virtuoel
  - UpcraftLP
authors-nogithub:
  - alfiejfs
  - salvopelux
---

<!---->

:::info ТРЕБОВАНИЯ

Сначала вам необходимо понять, как [создать блок](../blocks/first-block) и как [создать предмет](../items/first-item).

:::

Этот пример охватывает создание технической жидкости — кислоты, которая наносит урон, накладывает слабость и слепоту на сущностей, находящихся внутри неё. Для этого нам понадобятся два экземпляра жидкости (для состояний источника и текучей жидкости), блок жидкости, предмет ведра и тег жидкости.

## Создание класса жидкости {#creating-the-fluid-class}

Мы начнем с создания абстрактного класса (назовём его `AcidFluid`), который расширяет базовый класс `FlowingFluid`. Затем мы переопределим методы, поведение которых должно быть одинаковым как для источника, так и для текучей жидкости.

Обратите особое внимание на следующие методы:

- `animateTick` используется для отображения частиц и воспроизведения звуков. Поведение ниже основано на воде, которая издает звук при течении и создаёт частицы пузырей под водой.
- `entityInside` используется для обработки того, что должно происходить, когда сущность касается жидкости. Мы возьмем за основу воду и будем тушить огонь на сущностях, но также добавим нанесение урона, слабость и слепоту для сущностей внутри — ведь это всё-таки кислота.
- `canBeReplacedWith` обрабатывает логику растекания. Обратите внимание, что тег `ModFluidTags.ACID` еще не определён, мы разберём его в самом конце.

Объединив всё это вместе, мы получаем следующий класс:

<<< @/reference/latest/src/main/java/com/example/docs/fluid/custom/AcidFluid.java#abstract_fluid

Внутри `AcidFluid` мы создадим два подкласса для состояний `Source` (источник) и `Flowing` (текучая жидкость).

<<< @/reference/latest/src/main/java/com/example/docs/fluid/custom/AcidFluid.java#fluid_subclasses

### Регистрация жидкостей {#registering-fluids}

Затем мы создадим класс для регистрации всех экземпляров жидкостей. Назовём его `ModFluids`.

<<< @/reference/latest/src/main/java/com/example/docs/fluid/ModFluids.java#register

Как и в случае с блоками, вам необходимо убедиться, что класс загружен, чтобы все статические поля, содержащие ваши экземпляры жидкостей, были инициализированы. Вы можете сделать это, создав пустой метод `initialize`, который можно вызвать в [инициализаторе вашего мода](../getting-started/project-structure#entrypoints) для запуска статической инициализации.

Теперь вернитесь в класс `AcidFluid` и добавьте эти методы, чтобы связать зарегистрированные экземпляры жидкостей с этой жидкостью:

<<< @/reference/latest/src/main/java/com/example/docs/fluid/custom/AcidFluid.java#sources

На данный момент мы зарегистрировали состояние источника жидкости и её текучее состояние. Далее нам нужно зарегистрировать ведро и `LiquidBlock` для неё.

### Регистрация блоков жидкостей {#fluid-blocks}

Теперь давайте добавим блок жидкости. Это необходимо для некоторых команд, таких как `setblock`, чтобы ваша жидкость могла существовать в мире. Если такого ещё не делали, вам следует ознакомиться с тем, [как создать свой первый блок](../blocks/first-block).

Откройте ваш класс `ModBlocks` и зарегистрируйте следующий `LiquidBlock`:

<<< @/reference/latest/src/main/java/com/example/docs/block/ModBlocks.java#acid

Затем переопределите этот метод в `AcidFluid`, чтобы связать ваш блок с жидкостью:

<<< @/reference/latest/src/main/java/com/example/docs/fluid/custom/AcidFluid.java#legacy_block

### Регистрация вёдер {#buckets}

Жидкости в Minecraft обычно используют вёдра, поэтому давайте посмотрим, как мы можем добавить предмет «Ведро кислоты» (Bucket of Acid). Если вы такого ещё не делали, вам следует ознакомиться с тем, [как создать свой первый предмет](../items/first-item).

Откройте ваш класс `ModItems` и зарегистрируйте следующий `BucketItem`:

<<< @/reference/latest/src/main/java/com/example/docs/item/ModItems.java#acid_bucket

Затем переопределите этот метод в `AcidFluid`, чтобы связать ваше ведро с жидкостью:

<<< @/reference/latest/src/main/java/com/example/docs/fluid/custom/AcidFluid.java#bucket

Не забудьте, что для корректного рендеринга предметам требуются перевод, [текстура](../items/first-item#adding-a-texture), [модель](../items/first-item#adding-a-model) и [клиентский предмет](../items/first-item#creating-the-client-item) с именем `acid_bucket`. Пример текстуры приведен ниже.

<DownloadEntry visualURL="/assets/develop/fluids/acid_bucket.png" downloadURL="/assets/develop/fluids/acid_bucket_small.png">Текстура</DownloadEntry>

Также рекомендуется добавить ведро из вашего мода в тег предметов `ConventionalItemTags.BUCKET`, чтобы другие моды могли правильно обрабатывать его — либо [вручную](#tagging), либо посредством [генерации данных](../data-generation/tags).

## Тегирование ваших жидкостей {#tagging}

::: info

Пользователи [datagen](../data-generation/tags) могут регистрировать теги через `FabricTagProvider.FluidTagProvider` вместо того, чтобы вписывать их вручную.

:::

Поскольку в текущем и неподвижном состояниях жидкость считается двумя отдельными блоками, для одновременной проверки обоих состояний часто используется тег. Мы создадим тег жидкости в файле `data/example-mod/tags/fluid/acid.json`:

<<< @/reference/latest/src/main/generated/data/example-mod/tags/fluid/acid.json

::: tip

В Minecraft также предусмотрены другие теги для управления поведением жидкостей:

- Если вам нужно, чтобы жидкость из вашего мода вела себя как вода (эффект тумана под водой, впитывание губками, возможность плавать, замедление сущностей...), подумайте над её добавлением в тег жидкости `minecraft:water`.
- Если вам нужно, чтобы она вела себя как лава (эффект тумана в лаве, возможность плавать для страйдеров/гастов, замедление сущностей...), подумайте над её добавлением в тег жидкости `minecraft:lava`.
- Если вам нужны только _некоторые_ из этих свойств, возможно, стоит использовать миксины (mixins) для более точечной настройки поведения.

:::

Для этой демонстрации мы также добавим тег жидкости acid (кислота) в тег жидкости воды `data/minecraft/tags/fluid/water.json`.

<<< @/reference/latest/src/main/generated/data/minecraft/tags/fluid/water.json

## Добавление текстуры {#textures}

Для наложения текстур на жидкость вам следует использовать `FluidRenderHandlerRegistry` из Fabric API.

::: tip

Для простоты в этой демонстрации используется `BlockTintSources.constant` для применения постоянного зеленого оттенка к стандартной (ванильной) текстуре воды. Для получения подробной информации о `BlockTintSource` см. раздел «[Тонирование блоков](../blocks/block-tinting)».

:::

Добавьте следующие строки в ваш `ClientModInitializer`, чтобы создать `FluidModel.Unbaked`. Он принимает два материала (`Materials`) для текстур — один для неподвижного источника и один для текущей жидкости — а также источник тонировки блока (block tint source) для окрашивания в нужный цвет.

<<< @/reference/latest/src/client/java/com/example/docs/appearance/ExampleModAppearanceClient.java#fluid_texture

На данном этапе у нас есть всё необходимое, чтобы увидеть нашу Кислоту в игре! Вы можете использовать команду `setblock` или предмет «Ведро кислоты», чтобы разместить кислоту в мире.

![Скриншот зелёной кислоты в мире](/assets/develop/fluids/acid.png)
