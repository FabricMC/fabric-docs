---
title: Стан блока
description: Дізнайтеся, чому стан блока це чудовий спосіб додати візуальну функціональність для ваших блоків.
authors:
  - IMB11
---

Стан блока — це частина даних, що прикріплюється до окремого блока у світі Minecraft та містить інформацію про блок у вигляді властивостей. Ось кілька прикладів властивостей, які в стандартній версії гри зберігаються в станах блоків:

- Rotation: Зазвичай використовується для колод та інших природних блоків.
- Activated: Широко використовується у пристроях з редстоуном і блоках, таких як піч або коптильня.
- Age: Використовується в урожаях, рослинности, паростках, ламінарії тощо.

Ви, напевно, розумієте чому вони корисні — вони уникають необхідності зберігати дані NBT дані у блоках-сутностях — зменшуючи розмір світу, та запобігаючи проблемам із TPS!

Визначення станів блока знаходиться у теці `assets/example-mod/blockstates`.

## Наприклад: Блок колона {#pillar-block}

<!-- Note: This example could be used for a custom recipe types guide, a condensor machine block with a custom "Condensing" recipe? -->

Minecraft має деякі власні класи, які дозволять вам швидко створити певні типи блоків — в цьому прикладі розглядається створення блока з властивістю `axios` шляхом створення блока «Condensed Oak Log».

Усталений клас `RotatedPillarBlock` дозволяє розміщувати блок на осях X, Y або Z.

@[code transcludeWith=:::3](@/reference/latest/src/main/java/com/example/docs/block/ModBlocks.java)

Блоки колон мають дві текстури, верхню та бокову — вони використовують модель `block/cube_column`.

Як і зазвичай, з усіма текстурами блоків, текстури можна знайти в `assets/example-mod/textures/block`

<DownloadEntry visualURL="/assets/develop/blocks/blockstates_0_large.png" downloadURL="/assets/develop/blocks/condensed_oak_log_textures.zip">Текстури</DownloadEntry>

Оскільки стовпчик має два положення, горизонтальне та вертикальне, нам потрібно буде створити два окремих файли моделі:

- `condensed_oak_log_horizontal.json`, який розширює модель `block/cube_column_horizontal`.
- `condensed_oak_log.json`, який розширює модель `block/cube_column`.

Приклад файлу `condensed_oak_log_horizontal.json`:

@[code](@/reference/latest/src/main/generated/assets/example-mod/models/block/condensed_oak_log_horizontal.json)

::: info

Пам'ятайте, файли стану блока можна знайти в теці `assets/example-mod/blockstates`, а його назва має збігатися з ID блока, використаним під час реєстрації вашого блока в класі `ModBlocks`. Наприклад, якщо ID блока `condensed_oak_log`, файл повинен мати назву `condensed_oak_log.json`.

Щоб детальніше ознайомитися з усіма модифікаторами, доступними у файлах стану блока, перегляньте сторінку [Wiki Minecraft — Моделі (Стани блоків)](https://minecraft.wiki/w/Tutorials/Models#Block_states).

:::

Далі нам потрібно створити файл стану блока, і саме тут відбувається магія. Блоки колони мають три осі, тому ми будемо використовувати конкретні моделі для таких ситуацій:

- `axis=x` — коли блок розміщено вздовж осі X, ми повертатимемо модель у позитивному напрямку X.
- `axis=y` — коли блок розміщено вздовж осі Y, ми будемо використовувати звичайну вертикальну модель.
- `axis=z` — коли блок розміщується вздовж осі Z, ми повертаємо модель у позитивний X.

@[code](@/reference/latest/src/main/generated/assets/example-mod/blockstates/condensed_oak_log.json)

Як завжди, вам потрібно буде створити переклад для свого блока та модель предмета, яка є батьками будь-якої з двох моделей.

![Приклад блока колони у грі](/assets/develop/blocks/blockstates_1.png)

## Власні стани блоків {#custom-block-states}

Спеціальні стани блока чудові, якщо ваш блок має унікальні властивості — іноді ви можете виявити, що ваш блок може повторно використовувати усталені властивості.

У цьому прикладі буде створено унікальну логічну властивість під назвою `activated` - коли гравець натискає ПКМ по блоку, він змінюватиме значення `activated=false` на `activated=true`, відповідно змінюючи свою текстуру.

### Створення властивості {#creating-the-property}

По-перше, вам потрібно буде створити саму властивість — оскільки це логічне значення, ми використаємо метод `BooleanProperty.create`.

@[code transcludeWith=:::1](@/reference/latest/src/main/java/com/example/docs/block/custom/PrismarineLampBlock.java)

Далі ми маємо додати властивість до менеджера стану блока в методі  `createBlockStateDefinition`. Щоб отримати доступ до конструктора, вам потрібно змінити метод:

@[code transcludeWith=:::2](@/reference/latest/src/main/java/com/example/docs/block/custom/PrismarineLampBlock.java)

Ви також повинні встановити усталений стан для властивості `activated` у конструкторі вашого спеціального блока.

@[code transcludeWith=:::3](@/reference/latest/src/main/java/com/example/docs/block/custom/PrismarineLampBlock.java)

### Використання властивості {#using-the-property}

У цьому прикладі змінюється логічна властивість `activated`, коли гравець взаємодіє з блоком. Для цього ми можемо замінити метод `useWithoutItem`:

@[code transcludeWith=:::4](@/reference/latest/src/main/java/com/example/docs/block/custom/PrismarineLampBlock.java)

### Візуалізація властивості {#visualizing-the-property}

Перед створенням файлу стану блока вам потрібно буде надати текстури як для активованого, так і для деактивованого станів блока, а також для моделі блока.

<DownloadEntry visualURL="/assets/develop/blocks/blockstates_2_large.png" downloadURL="/assets/develop/blocks/prismarine_lamp_textures.zip">Текстури</DownloadEntry>

Використовуйте свої знання про моделі блоків, щоб створити дві моделі для блока: одну для активованого стану та одну для деактивованого стану. Зробивши це, ви можете розпочати створення файлу стану блока.

Оскільки ви створили нову властивість, вам потрібно буде оновити файл стану блока для врахування цієї властивості.

Якщо у вас є кілька властивостей у блоці, вам потрібно врахувати всі можливі комбінації. Наприклад, `activated` і `axis` призведе до 6 комбінацій (два можливі значення для `activated` і три можливі значення для `axis`).

Оскільки цей блок має лише два можливі варіанти, оскільки він має лише одну властивість («активовано»), стан блока JSON виглядатиме приблизно так:

@[code](@/reference/latest/src/main/generated/assets/example-mod/blockstates/prismarine_lamp.json)

::: tip

Не забудьте додати [клієнтський предмет](../items/first-item#creating-the-client-item) для блока, щоб він показувався в інвентарі!

:::

Оскільки прикладом блока є лампа, нам також потрібно змусити її випромінювати світло, коли властивість `activated` має значення true. Це можна зробити через налаштування блока, передані конструктору під час реєстрації блока.

Ви можете використовувати метод `lightLevel`, щоб установити рівень світла, випромінюваного блоком, ми можемо створити статичний метод у класі `PrismarineLampBlock`, щоб повернути рівень освітлення на основі властивості `activated`, і передати його як посилання на метод до методу `lightLevel`:

@[code transcludeWith=:::5](@/reference/latest/src/main/java/com/example/docs/block/custom/PrismarineLampBlock.java)

@[code transcludeWith=:::4](@/reference/latest/src/main/java/com/example/docs/block/ModBlocks.java)

<!-- Note: This block can be a great starter for a redstone block interactivity page, maybe triggering the blockstate based on redstone input? -->

Коли ви все завершите, кінцевий результат має виглядати приблизно так:

<VideoPlayer src="/assets/develop/blocks/blockstates_3.webm">Призмариновий ліхтар у грі</VideoPlayer>
