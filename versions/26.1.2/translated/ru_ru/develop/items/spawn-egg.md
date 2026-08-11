---
title: Яйца призыва
description: Узнайте, как зарегистрировать предмет с функционалом призывания сущности.
authors:
  - Earthcomputer
  - JaaiDead
  - cassiancc
  - Fellteros
  - skycatminepokie
  - VatinMc
  - voidedaries
---

<!---->

:::info ТРЕБОВАНИЯ

Сначала необходимо разобраться [как создать предмет](./first-item), который затем можно превратить в яйцо призыва.

Также в этой статье упоминается сущность «Мини-голем» из руководства «[Создание вашей первой сущности](../entities/first-entity)». Если вы не создавали сущность, можете использовать ванильную, например `EntityType.FROG`, вместо `ModEntityTypes.MINI_GOLEM`.

:::

Яйца призыва — это особые предметы, которые при использовании призывают соответствующего моба. Вы можете зарегистрировать такое яйцо с помощью метода `register` из вашего [класса предметов](./first-item#preparing-your-items-class), передав в него `SpawnEggItem::new`.

<<< @/reference/26.1.2/src/main/java/com/example/docs/item/ModItems.java#custom_entity_spawn_egg

Перед завершением остаётся сделать ещё несколько вещей: добавить текстуру, модель предмета, клиентский предмет, название, а также добавить яйцо призыва в соответствующую вкладку творческого режима.

## Добавление текстуры {#adding-a-texture}

Создайте текстуру предмета размером 16×16 в директории `assets/example-mod/textures/item`, имя файла должно совпадать с идентификатором предмета: `mini_golem_spawn_egg.png`. Пример текстуры приведён ниже.

<DownloadEntry visualURL="/assets/develop/entity/mini_golem_spawn_egg.png" downloadURL="/assets/develop/entity/mini_golem_spawn_egg_small.png">Текстура</DownloadEntry>

## Добавление модели {#adding-a-model}

Создайте модель предмета в директории `assets/example-mod/models/item`, имя файла должно совпадать с идентификатором предмета: `mini_golem_spawn_egg.json`.

<<< @/reference/26.1.2/src/main/generated/assets/example-mod/models/item/mini_golem_spawn_egg.json

## Создание клиентского предмета {#creating-the-client-item}

Создайте JSON клиентского предмета в директории `assets/example-mod/items`, имя файла должно совпадать с именем файла модели предмета: `mini_golem_spawn_egg.json`.

<<< @/reference/26.1.2/src/main/generated/assets/example-mod/items/mini_golem_spawn_egg.json

![Добавленный предмет "Яйцо призыва" с клиентским предметом](/assets/develop/entity/mini_golem_spawned.png)

## Именование яйца призыва {#naming-the-spawn-egg}

Чтобы задать название яйцу призыва, ключ перевода `item.example-mod.mini_golem_spawn_egg` должен получить значение. Этот процесс аналогичен тому, что описан в разделе «[Наименование предмета](./first-item#naming-the-item)».

Создайте или отредактируйте JSON-файл по пути: `src/main/resources/assets/example-mod/lang/en_us.json` и поместите в него ключ перевода и его значение:

```json
{
  "item.example-mod.mini_golem_spawn_egg": "Mini Golem Spawn Egg"
}
```

## Добавление на вкладку творческого режима {#adding-to-a-creative-mode-tab}

Яйцо призыва добавляется на вкладку творческого режима для яиц призыва (`CreativeModeTab`) в методе `initialize()` [класса предметов](./first-item#preparing-your-items-class).

<<< @/reference/26.1.2/src/main/java/com/example/docs/item/ModItems.java#spawn_egg_creative_tab

![Созданное яйцо призыва с присвоенным именем и во вкладке творческого режима](/assets/develop/entity/spawn_egg_in_creative.png)

Более подробную информацию см. в разделе «[Добавление предмета на творческую вкладку](./first-item#adding-the-item-to-a-creative-tab)».
