---
title: Власні інструменти та зброя
description: Хочете дізнатися, як створювати власні інструменти та налаштовувати їхні властивості? Прочитайте про це тут.
authors:
  - bluebear94
  - cassiancc
  - ChampionAsh5357
  - IMB11
  - its-miroma
resources:
  https://docs.neoforged.net/docs/items/tools/: Інструменти — Документація NeoForge (крім ексклюзивів Neo)
---

Інструменти необхідні для виживання та прогресу, дозволяючи гравцям збирати ресурси, будувати будівлі та захищатися.

## Створення інструментального матеріалу {#creating-a-tool-material}

Ви можете створити матеріал інструменту, створивши екземпляр нового об’єкта `ToolMaterial` і зберігши його в полі, яке можна використовувати пізніше для створення предметів інструменту, які використовують матеріал.

<<< @/reference/latest/src/main/java/com/example/docs/item/ModItems.java#guidite_tool_material

Конструктор `ToolMaterial` приймає такі параметри в такому конкретному порядку:

| Параметр                  | Опис                                                                                                                                                                                                                         |
| ------------------------- | ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| `incorrectBlocksForDrops` | Якщо блок знаходиться в неправильному теґу `incorrectBlocksForDrops`, це означає, що коли ви використовуєте інструмент, виготовлений із цього `ToolMaterial` на цьому блоці, блок не скине жодних предметів. |
| `durability`              | Міцність усіх інструментів із цього `ToolMaterial`.                                                                                                                                                          |
| `speed`                   | Швидкість добування інструментів із цього `ToolMaterial`.                                                                                                                                                    |
| `attackDamageBonus`       | Інструменти з цього `ToolMaterial` матимуть додаткову шкоду від атаки.                                                                                                                                       |
| `enchantmentValue`        | «Зачаровуваність» інструментів, які є з цього `ToolMaterial`.                                                                                                                                                |
| `repairItems`             | Будь-які предмети в цьому теґу можна використовувати для лагодження інструментів цього `ToolMaterial` у ковадлі.                                                                                             |

Для цього прикладу ми будемо використовувати той самий теґ предмета лагодження, який будемо використовувати для обладунків. Ми визначаємо посилання на теґ наступним чином:

<<< @/reference/latest/src/main/java/com/example/docs/item/armor/GuiditeArmorMaterial.java#repair_tag

Якщо вам важко визначити збалансовані значення для будь-якого з числових параметрів, вам слід розглянути константи інструментального матеріалу, такі як `ToolMaterial.STONE` або `ToolMaterial.DIAMOND`.

### Створення теґу матеріалу інструмента {#creating-the-tool-material-tag}

Для нашого теґу `incorrectBlocksForDrops` ми можемо створити теґ, схожий на стандартний теґ `minecraft:incorrect_for_*_drops`, який визначає блоки, які **не** випадуть під час добування за допомогою матеріалу. Визначмо посилання на теґ наступним чином:

<<< @/reference/latest/src/main/java/com/example/docs/item/ModItems.java#guidite_incorrect_blocks_tag

Далі ми визначаємо вміст теґу за допомогою теґу JSON. Зробімо ґуїдинові інструменти здатними добувати ті самі блоки, що й дерев’яні інструменти, а також мідну руду та глибосланцеву мідну руду:

<<< @/reference/latest/src/main/resources/data/example-mod/tags/block/incorrect_for_guidite_tool.json

Зауважте, що цей приклад успадковує слабший матеріал інструмента і _видаляє_ записи, які наш сильніший матеріал може добувати, успадковуючи всі інші блоки, які не може видобути дерево.

::: tip

Ми також могли б зробити навпаки: успадкувати від потужнішого інструмента та _додати_ додаткові блоки, для яких гуїдитові інструменти непридатні.

Наприклад, якщо ми хочемо створити інструмент, який працював би як залізо, але не міг добувати діамантову руду, `values` мав би містити `#minecraft:incorrect_for_iron_tool` і `#minecraft:diamond_ores`.

Якщо ви хочете, щоб ваш матеріал інструмента добував ті самі блоки, що й наявний, ви можете включити відповідний теґ у визначенні вашого теґу без будь-яких доповнень чи видалень. Це рекомендовано замість передачі наявного теґу як `incorrectBlocksForDrops` вашого матеріалу, щоб користувачі могли налаштувати неправильні блоки для кожного матеріалу незалежно.

:::

## Реєстрація предметів інструментів {#registering-tool-items}

Використовуючи ту саму службову функцію, що й у посібнику [створення вашого першого предмета](./first-item), ви можете створювати предмети інструментів:

<<< @/reference/latest/src/main/java/com/example/docs/item/ModItems.java#guidite_sword

Два рухомих значення (`1f, 1f`) стосуються шкоди від атаки інструмента та швидкості атаки інструмента відповідно.

Для лопат, сокир і мотик вам слід створити `ShovelItem`, `AxeItem` або `HoeItem` замість загального `Item`, оскільки вони реалізують дії, пов’язані з інструментом у якого є дії ПКМ:

<<< @/reference/latest/src/main/java/com/example/docs/item/ModItems.java#axe

::: info

`ShovelItem`, `AxeItem` і `HoeItem` викликають метод `shovel`, `axe` або `hoe` для `Item.Properties` у своїх конструкторах.

:::

Не забудьте додати їх до вкладок творчості, якщо ви хочете отримати доступ до них з інвентарю творчості!

<<< @/reference/latest/src/main/java/com/example/docs/item/ModItems.java#add_guidite_sword_to_create_tab

Вам також доведеться додати текстуру, переклад предмета та модель предмета. Однак для моделі item ви захочете використовувати модель `item/handheld` як батьківську модель замість звичайної `item/generated`.

## Assets {#models}

Вам також потрібно буде додати [текстуру](./first-item#adding-a-texture), [переклад](./first-item#naming-the-item), [клієнтський переклад](./first-item#creating-the-client-item) і [модель предмета](./item-models). Однак для моделі item ви захочете використовувати модель `item/handheld` як батьківську модель замість звичайної `item/generated`.

У цьому прикладі ми визначимо наступний клієнтський предмет, модель і текстуру для предмета «Guidite Sword»:

:::: tabs

== Початковий код

::: info

Моделі предметів можна згенерувати. Щоб отримати додаткові відомості, перегляньте документацію щодо створення [моделей предметів](../data-generation/item-models).

:::

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModModelProvider.java#sword

== Клієнтський предмет

`generated/assets/example-mod/items/guidite_sword.json`

<<< @/reference/latest/src/main/generated/assets/example-mod/items/guidite_sword.json

== Модель предмета

`generated/assets/example-mod/models/item/guidite_sword.json`

<<< @/reference/latest/src/main/generated/assets/example-mod/models/item/guidite_sword.json

== Текстура

<DownloadEntry visualURL="/assets/develop/items/tools_0.png" downloadURL="/assets/develop/items/tools_0_small.png">Текстура Guidite Sword</DownloadEntry>

::::

Подібна схема застосовується до предмета «Guidite Axe».

::: tabs

== Початковий код

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModModelProvider.java#handheld

== Клієнтський предмет

`generated/assets/example-mod/items/guidite_axe.json`

<<< @/reference/latest/src/main/generated/assets/example-mod/items/guidite_axe.json

== Модель предмета

`generated/assets/example-mod/models/item/guidite_axe.json`

<<< @/reference/latest/src/main/generated/assets/example-mod/models/item/guidite_axe.json

== Текстура

<DownloadEntry visualURL="/assets/develop/data-generation/item-model/guidite_axe_big.png" downloadURL="/assets/develop/data-generation/item-model/guidite_axe.png">Текстура Guidite Axe</DownloadEntry>

:::

## Додання предметів інструментів до теґу {#tags}

:::info ПЕРЕДУМОВИ

Щоб отримати додаткові відомості, перегляньте документацію щодо створення [теґів предмета](../data-generation/tags).

:::

Також рекомендується розміщувати інструмент у відповідних теґах предметів. Інструменти мають власні індивідуальні теґи, такі як `ItemTags.SWORDS`, які використовуються для зачаровуваності та іншої специфічної логіки, наприклад, чи потрібно завдавати нищівну атаку.

У вашому постачальнику теґів предмета додайте такі рядки до `addTags`:

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModItemTagProvider.java#sword_tags

Це майже все! Якщо ви зайдете в гру, ви побачите свої інструменти на вкладці «Інструменти й прилади» в меню інвентарю творчости.

![Готові інструменти в інвентарі](/assets/develop/items/tools_1.png)
