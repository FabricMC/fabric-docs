---
title: Состояния блока
description: Узнать почему состояния блока — это отличный способ добавить визуальной функциональности вашим блокам.
authors:
  - IMB11
---

# Состояния блока {#block-states}

Состояния блока — это фрагмент данных, привязанный к одному блоку в мире Minecraft, содержащий информацию о блоке в виде свойств. Вот несколько примеров свойств, которые стандартный Minecraft хранит в состояниях блока:

- Rotation: В основном используется для древесины и других природных блоков.
- Activated: Широко используется в редстоун-устройствах, а также в таких блоках, как печь или коптильня.
- Age: Используется для культур, растений, саженцев, водорослей и т.п.

Вы, вероятно, понимаете, почему они полезны — они позволяют избежать необходимости хранения данных NBT в сущности блока, что уменьшает размер мира и предотвращает проблемы с TPS!

Определения состояний блока можно найти в папке `assets/<mod id here>/blockstates`.

## Пример: Блок Колонна {#pillar-block}

<!-- Note: This example could be used for a custom recipe types guide, a condensor machine block with a custom "Condensing" recipe? -->

Minecraft уже имеет несколько пользовательских классов, которые позволяют быстро создавать определенные типы блоков. Этот пример демонстрирует создание блока со свойством `axis` на примере блока "Обтёсанная дубовая древесина".

Стандартный класс `PillarBlock` позволяет размещать блоки вдоль осей X, Y или Z.

@[code transcludeWith=:::3](@/reference/latest/src/main/java/com/example/docs/block/ModBlocks.java)

Колоновые блоки имеют две текстуры: верхнюю и боковую. Они используют модель `block/cube_column`.

Как всегда, со всеми текстурами блоков, файлы текстур могут быть найдены в `assets/<mod id here>/textures/block`

<DownloadEntry type="Textures" visualURL="/assets/develop/blocks/blockstates_0_large.png" downloadURL="/assets/develop/blocks/condensed_oak_log_textures.zip" />

Из-за того что колоновый блок имеет две позиции: горизонтальную и вертикальную, мы должны сделать два отдельных файла моделей:

- `condensed_oak_log_horizontal.json`, который дополняет модель `block/cube_column_horizontal`.
- `condensed_oak_log.json`, который дополняет модель `block/cube_column`.

Пример файла `condensed_oak_log_horizontal.json`:

@[code](@/reference/latest/src/main/resources/assets/fabric-docs-reference/models/block/condensed_oak_log_horizontal.json)

---

::: info
Remember, blockstate files can be found in the `assets/<mod id here>/blockstates` folder, the name of the blockstate file should match the block ID used when registering your block in the `ModBlocks` class. For instance, if the block ID is `condensed_oak_log`, the file should be named `condensed_oak_log.json`.

Для более подробного изучения всех доступных модификаторов в файле состояний блоков, ознакомьтесь со страницей [Minecraft Wiki - Models (Block States)](https://minecraft.wiki/w/Tutorials/Models#Block_states).
:::

Далее, нам нужно создать файл состояний блока. Файл состояний блока — это где происходит магия — колоновые блоки имеют три оси, поэтому мы будем использовать конкретные модели для следующих ситуаций:

- `axis=x` - Когда блок помещён вдоль оси X, мы повернём модель так, чтобы она была ориентирована в положительном направлении оси X.
- `axis=y` - Когда блок помещён вдоль оси Y, мы будем использовать нормальную вертикальную модель.
- `axis=z` - Когда блок помещён вдоль оси Z, мы повернём модель так, чтобы она была ориентирована в положительном направлении оси X.

@[code](@/reference/latest/src/main/resources/assets/fabric-docs-reference/blockstates/condensed_oak_log.json)

Как всегда, вам нужно создать перевод для вашего блока и модель предмета, которая будет наследовать одну из двух моделей.

![Пример колонового блока в игре](/assets/develop/blocks/blockstates_1.png)

## Пользовательские состояния блока {#custom-block-states}

Пользовательские состояния блока хороши если ваш блок имеет уникальные свойства. Иногда вы можете обнаружить, что ваш блок может использовать стандартные свойства.

Этот пример создаст уникальное boolean-свойство под названием `activated` - когда игрок нажимает ПКМ на блок, состояние блока меняется с `activated=false` на `activated=true` - соответствующим образом меняя его текстуру.

### Создаём свойство {#creating-the-property}

Сначала вам нужно создать свойство — так как это булево значение, мы будем использовать метод  `BooleanProperty.of`.

@[code transcludeWith=:::1](@/reference/latest/src/main/java/com/example/docs/block/custom/PrismarineLampBlock.java)

Далее нам необходимо добавить свойство к менеджеру состояний блока в методе `appendProperties`. Вам необходимо переопределить метод для доступа к конструктору:

@[code transcludeWith=:::2](@/reference/latest/src/main/java/com/example/docs/block/custom/PrismarineLampBlock.java)

Вам также придется установить состояние по умолчанию для свойства `activated` в конструкторе вашего пользовательского блока.

@[code transcludeWith=:::3](@/reference/latest/src/main/java/com/example/docs/block/custom/PrismarineLampBlock.java)

:::warning
Не забудьте зарегистрировать свой блок, используя пользовательский класс вместо `Block`!
:::

### Использование свойства {#using-the-property}

В этом примере булево свойство `activated` инвертируется, когда игрок взаимодействует с блоком. Для этого мы можем переопределить метод `onUse`:

@[code transcludeWith=:::4](@/reference/latest/src/main/java/com/example/docs/block/custom/PrismarineLampBlock.java)

### Визуализация недвижимости {#visualizing-the-property}

Перед созданием файла состояния блока вам необходимо предоставить текстуры для активированного и деактивированного состояний блока, а также модель блока.

<DownloadEntry type="Textures" visualURL="/assets/develop/blocks/blockstates_2_large.png" downloadURL="/assets/develop/blocks/prismarine_lamp_textures.zip" />

Используйте свои знания о моделях блоков, чтобы создать две модели блока: одну для активированного состояния и одну для деактивированного состояния. После этого можно приступать к созданию файла blockstate.

Поскольку вы создали новое свойство, вам необходимо обновить файл blockstate для блока, чтобы учесть это свойство.

Если в блоке имеется несколько свой блока, вам необходимо учитывать все возможные комбинации. Например, «activated» и «axis» приведут к 6 комбинациям (два возможных значения для «activated» и три возможных значения для «axis»).

Поскольку этот блок имеет только два возможных варианта, поскольку у него есть только одно свойство (`activated`), JSON-код состояния блока будет выглядеть примерно так:

@[code](@/reference/latest/src/main/resources/assets/fabric-docs-reference/blockstates/prismarine_lamp.json)

---

Поскольку пример блока представляет собой лампу, нам также необходимо заставить ее излучать свет, когда свойство `activated` имеет значение true. Это можно сделать с помощью настроек блока, передаваемых конструктору при регистрации блока.

Вы можете использовать метод `luminance` для установки уровня освещенности, излучаемой блоком. Мы можем создать статический метод в классе `PrismarineLampBlock` для возврата уровня освещенности на основе свойства `activated` и передать его как ссылку на метод `luminance`:

@[code transcludeWith=:::5](@/reference/latest/src/main/java/com/example/docs/block/custom/PrismarineLampBlock.java)

@[code transcludeWith=:::4](@/reference/latest/src/main/java/com/example/docs/block/ModBlocks.java)

---

<!-- Note: This block can be a great starter for a redstone block interactivity page, maybe triggering the blockstate based on redstone input? -->

После того как вы все завершите, конечный результат должен выглядеть примерно так:

<VideoPlayer src="/assets/develop/blocks/blockstates_3.webm" title="Prismarine Lamp Block in-game" />
