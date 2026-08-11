---
title: Оружие и инструменты
description: Узнайте, как создавать собственные инструменты и настраивать их свойства.
authors:
  - bluebear94
  - cassiancc
  - ChampionAsh5357
  - IMB11
  - its-miroma
resources:
  https://docs.neoforged.net/docs/items/tools/: Инструменты — NeoForge Docs (кроме эксклюзивных функций Neo)
---

Инструменты необходимы для выживания и развития, позволяя игрокам собирать ресурсы, строить здания и защищаться.

## Создание Материала Инструмента {#creating-a-tool-material}

Вы можете создать инструментальный материал, создав новый объект "ToolMaterial" и сохранив его в поле, которое позже можно будет использовать для создания предметов инструмента, использующих этот материал.

<<< @/reference/26.1.2/src/main/java/com/example/docs/item/ModItems.java#guidite_tool_material

Конструктор `ToolMaterial` принимает следующие параметры в указанном порядке:

| Параметр                  | Описание                                                                                                                                                                                      |
| ------------------------- | --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| `incorrectBlocksForDrops` | Если блок находится в теге `incorrectBlocksForDrops`, это означает, что при использовании инструмента из этого `ToolMaterial` на этом блоке, блок не выпадет в виде предмета. |
| `durability`              | Долговечность всех инструментов, изготовленных из этого `ToolMaterial`.                                                                                                       |
| `speed`                   | Скорость майнинга инструментов, изготовленных из этого `ToolMaterial`.                                                                                                        |
| `attackDamageBonus`       | Инструменты, изготовленные из этого `ToolMaterial`, будут наносить дополнительный урон при атаке.                                                                             |
| `enchantmentValue`        | "Зачарованность" инструментов, изготовленных из этого `ToolMaterial`.                                                                                                         |
| `repairItems`             | Любые предметы, указанные в этом теге, могут быть использованы для ремонта инструментов из этого `ToolMaterial` на наковальне.                                                |

В этом примере мы будем использовать тот же тег предметов для починки, который мы будем использовать для брони. Мы определяем ссылку на тег следующим образом:

<<< @/reference/26.1.2/src/main/java/com/example/docs/item/armor/GuiditeArmorMaterial.java#repair_tag

Если вам трудно определить сбалансированные значения для любого из числовых параметров, вам следует рассмотреть возможность использования стандартные константы материала инструмента, таких как `ToolMaterial.STONE` or `ToolMaterial.DIAMOND`.

### Создание тега материала инструмента {#creating-the-tool-material-tag}

Для нашего тега `incorrectBlocksForDrops` мы можем создать тег, аналогичный ванильным тегам `minecraft:incorrect_for_*_drops`, определяют блоки, которые **не** будут выпадать при добыче этим материалом. Давайте определим ссылку на тег следующим образом:

<<< @/reference/26.1.2/src/main/java/com/example/docs/item/ModItems.java#guidite_incorrect_blocks_tag

Затем мы определяем содержимое тега с помощью JSON-файла тега. Давайте сделаем так, чтобы инструменты из гуидита могли добывать те же блоки, что и деревянные инструменты, а также медную руду и глубокосланцевую медную руду:

<<< @/reference/26.1.2/src/main/resources/data/example-mod/tags/block/incorrect_for_guidite_tool.json

Обратите внимание, что этот пример наследует от более слабого материала инструмента и _удаляет_ записи, наш более сильный материал может добывать, наследуя все остальные блоки, которые дерево не может добыть.

::: tip

Мы также можем сделать наоборот: унаследовать от более сильного инструмента и _добавить_ дополнительные блоки, для которых инструменты из гуидита не подходят.

Например, если бы мы хотели создать инструмент, который работал бы как железный, но не мог добывать алмазную руду, `values` должен был бы содержать `#minecraft:incorrect_for_iron_tool` и `#minecraft:diamond_ores`.

Если вы хотите, чтобы ваш материал инструмента добывал те же блоки, что и существующий, вы можете включить соответствующий тег в определение вашего тега без каких-либо дополнений или удалений. Это рекомендуется вместо передачи существующего тега в качестве `incorrectBlocksForDrops` вашего материала, чтобы пользователи могли настраивать неподходящие блоки для каждого из материалов независимо.

:::

## Регистрация предметов-инструментов {#registering-tool-items}

Используя ту же вспомогательную функцию, что и в руководстве [Создание вашего первого элемента](./first-item), вы можете создавать свои инструменты:

<<< @/reference/26.1.2/src/main/java/com/example/docs/item/ModItems.java#guidite_sword

Два плавающих значений (`1f, 1f`) относятся к урону от атаки инструмента и скорости атаки инструмента соответственно.

Для лопат, топоров и мотыг вам следует создавать `ShovelItem`, `AxeItem` или `HoeItem` вместо обычного `Item`, поскольку они реализуют специфичные для инструментов действия по правому клику:

<<< @/reference/26.1.2/src/main/java/com/example/docs/item/ModItems.java#axe

::: info

`ShovelItem`, `AxeItem` и `HoeItem` вызывают методы `shovel`, `axe` или `hoe` класса `Item.Properties` в своих конструкторах.

:::

Не забудьте добавить их во вкладку предметов в творческом режиме, если вы хотите быстро получить к ним доступ!

<<< @/reference/26.1.2/src/main/java/com/example/docs/item/ModItems.java#add_guidite_sword_to_create_tab

Вам также придется добавить текстуру, перевод предмета и модель предмета. Однако для модели предмета вы захотите использовать модель `item/handheld` в качестве родительского вместо обычной модели `item/generated`.

## Ресурсы {#models}

Вам также нужно будет добавить [текстуру](./first-item#adding-a-texture), [перевод](./first-item#naming-the-item), [клиентский предмет](./first-item#creating-the-client-item) и [модель предмета](./item-models). Однако для модели предмета вы захотите использовать модель `item/handheld` в качестве родительского вместо обычной модели `item/generated`.

В этом примере мы определим следующие клиентский предмет, модель и текстуру для предмета «Меч из гуидита»:

:::: tabs

== Исходный код

::: info

Эта модель может быть сгенерирована с помощью данных. Для получения дополнительной информации см. документацию по генерации [моделей предметов](../data-generation/item-models).

:::

<<< @/reference/26.1.2/src/client/java/com/example/docs/datagen/ExampleModModelProvider.java#sword

== Предмет на клиенте

`generated/assets/example-mod/items/guidite_sword.json`

<<< @/reference/26.1.2/src/main/generated/assets/example-mod/items/guidite_sword.json

== Модели предмета

`generated/assets/example-mod/models/item/guidite_sword.json`

<<< @/reference/26.1.2/src/main/generated/assets/example-mod/models/item/guidite_sword.json

== Текстуры

<DownloadEntry visualURL="/assets/develop/items/tools_0.png" downloadURL="/assets/develop/items/tools_0_small.png">Текстура меча из гуидита</DownloadEntry>

::::

Аналогичный принцип применяется и к предмету "Топор из гуидита".

::: tabs

== Исходный код

<<< @/reference/26.1.2/src/client/java/com/example/docs/datagen/ExampleModModelProvider.java#handheld

== Предмет на клиенте

`generated/assets/example-mod/items/guidite_axe.json`

<<< @/reference/26.1.2/src/main/generated/assets/example-mod/items/guidite_axe.json

== Модели предмета

`generated/assets/example-mod/models/item/guidite_axe.json`

<<< @/reference/26.1.2/src/main/generated/assets/example-mod/models/item/guidite_axe.json

== Текстуры

<DownloadEntry visualURL="/assets/develop/data-generation/item-model/guidite_axe_big.png" downloadURL="/assets/develop/data-generation/item-model/guidite_axe.png">Текстура топора Guidite</DownloadEntry>

:::

## Тегирование предметов-инструментов {#tags}

:::info ПРЕДВАРИТЕЛЬНЫЕ ТРЕБОВАНИЯ

Для получения дополнительной информации см. документацию по генерации [тегов предметов](../data-generation/tags).

:::

Также рекомендуется поместить ваш инструмент в соответствующие теги предметов. Инструменты имеют свои собственные отдельные теги, такие как `ItemTags.SWORDS`, которые используются для зачаровываемости и другой специфической логики, например, для применения урона по области (свип-атаки).

В вашем провайдере тегов предметов добавьте следующие строки в `addTags`:

<<< @/reference/26.1.2/src/client/java/com/example/docs/datagen/ExampleModItemTagProvider.java#sword_tags

Вот и все! Если вы зайдёте в игру, вы должны увидеть свои инструменты на вкладке «Инструменты и полезности» в меню творческого инвентаря.

![Готовые инструменты в инвентаре](/assets/develop/items/tools_1.png)
