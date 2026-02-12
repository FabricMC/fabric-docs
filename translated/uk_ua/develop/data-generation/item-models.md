---
title: Генерація моделі предмета
description: Дізнайтеся як генерувати моделі предмета через datagen.
authors:
  - CelDaemon
  - Fellteros
  - skycatminepokie
  - VatinMc
---

<!---->

:::info ПЕРЕДУМОВИ

Переконайтеся, що ви завершили [налаштування даних](./setup) і створили свій [перший предмет](../items/first-item).

:::

Для кожної моделі предмета, яку ми хочемо створити, ми повинні створити два окремих файли JSON:

1. **Модель предмета**, яка визначає текстури, обертання та загальний вигляд предмета. Він знаходиться в каталозі `generated/assets/example-mod/models/item`.
2. **Клієнтський предмет**, який визначає, яку модель слід використовувати на основі різних критеріїв, таких як компоненти, взаємодії тощо. Він знаходиться в каталозі `generated/assets/example-mod/items`.

## Налаштування {#setup}

По-перше, ми повинні створити наш постачальник моделі.

::: tip

Ви можете повторно використовувати `FabricModelProvider`, створений у [генерації моделі блока](./block-models#setup).

:::

Створіть клас, який розширює `FabricModelProvider`, і реалізуйте обидва абстрактні методи: `generateBlockStateModels` і `generateItemModels`.
Тепер, створімо конструктор, що відповідає `super`.

@[code transcludeWith=:::provider](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModModelProvider.java)

Зареєструйте цей клас у своїй `DataGeneratorEntrypoint` в рамках методу `onInitializeDataGenerator`.

@[code transcludeWith=:::datagen-models:register](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModDataGenerator.java)

## Убудовані моделі предметів {#built-in}

Для моделей предметів ми будемо використовувати метод `generateItemModels`. Його параметр `ItemModelGenerators itemModelGenerator` відповідає за генерацію моделей предметів, а також містить методи для цього.

Ось посилання на найпоширеніші методи генерації моделі предмета.

### Простий {#simple}

Прості моделі предметів є типовими, і саме вони використовуються в більшості предметів Minecraft. Їхня батьківська модель — `GENERATED`. Вони використовують свою 2D текстуру в інвентарі та промальовуються в 3D у грі. Прикладом можуть бути човни, свічки або барвники.

::: tabs

== Вихідний код

@[code transcludeWith=:::generated](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModModelProvider.java)

== Клієнтський предмет

`generated/assets/example-mod/items/ruby.json`

@[code](@/reference/latest/src/main/generated/assets/example-mod/items/ruby.json)

== Модель предмета

`generated/assets/example-mod/models/item/ruby.json`

@[code](@/reference/latest/src/main/generated/assets/example-mod/models/item/ruby.json)

Ви можете знайти точні усталені значення для обертання, масштабування та позиціювання моделі у файлі [`generated.json` з ресурсів Minecraft](https://mcasset.cloud/1.21.11/assets/minecraft/models/item/generated.json).

== Текстура

<DownloadEntry visualURL="/assets/develop/data-generation/item-model/ruby_big.png" downloadURL="/assets/develop/data-generation/item-model/ruby.png">Текстура рубіна</DownloadEntry>

:::

### У руці {#handheld}

Ручні моделі предметів зазвичай використовуються як інструменти та зброя (сокири, мечі, тризубець). Вони повертаються та розташовуються трохи інакше, ніж звичайні моделі, щоб виглядати природніше в руці.

::: tabs

== Вихідний код

@[code transcludeWith=:::handheld](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModModelProvider.java)

== Клієнтський предмет

`generated/assets/example-mod/items/guidite_axe.json`

@[code](@/reference/latest/src/main/generated/assets/example-mod/items/guidite_axe.json)

== Модель предмета

`generated/assets/example-mod/models/item/guidite_axe.json`

@[code](@/reference/latest/src/main/generated/assets/example-mod/models/item/guidite_axe.json)

Ви можете знайти точні усталені значення для обертання, масштабування та позиціювання моделі у файлі [`handheld.json` з ресурсів Minecraft](https://mcasset.cloud/1.21.11/assets/minecraft/models/item/handheld.json).

== Текстура

<DownloadEntry visualURL="/assets/develop/data-generation/item-model/guidite_axe_big.png" downloadURL="/assets/develop/data-generation/item-model/guidite_axe.png">Текстура ґуїдітової сокири </DownloadEntry>

:::

### Перефарбовуваність {#dyeable}

Метод для фарбувальних предметів генерує просту модель предмета та клієнтський предмет, який визначає колір відтінку. Для цього методу потрібне десяткове усталеного значення кольору, яке використовується, коли річ не пофарбована. Усталеним значенням для шкіри є `0xFFA06540`.

:::: tabs

== Вихідний код

@[code transcludeWith=:::dyeable](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModModelProvider.java)

:::warning ВАЖЛИВО

Ви повинні додати свій предмет до теґу `ItemTags.DYEABLE`, щоб мати можливість фарбувати його у своєму інвентарі!

:::

== Клієнтський предмет

`generated/assets/example-mod/items/leather_gloves.json`

@[code](@/reference/latest/src/main/generated/assets/example-mod/items/leather_gloves.json)

== Модель предмета

`generated/assets/example-mod/models/item/leather_gloves.json`

@[code](@/reference/latest/src/main/generated/assets/example-mod/models/item/leather_gloves.json)

== Текстура

<DownloadEntry visualURL="/assets/develop/data-generation/item-model/leather_gloves_big.png" downloadURL="/assets/develop/data-generation/item-model/leather_gloves.png">Текстура шкіряних рукавиць</DownloadEntry>

== Попередній перегляд

![Фарбування шкіряних рукавиць](/assets/develop/data-generation/item-model/leather_gloves_dyeing.png)

::::

### Умовний {#conditional}

Далі ми розглянемо створення моделей предмета, які змінюють свій візуальний вигляд залежно від виконання певної умови, зазначеної другим параметром `BooleanProperty`. Ось деякі з них:

| Властивість     | Опис                                                                                                                       |
| --------------- | -------------------------------------------------------------------------------------------------------------------------- |
| `IsKeybindDown` | Правда, коли натиснуто вказану клавішу.                                                                    |
| `IsUsingItem`   | Правда, коли предмет використовується (наприклад, під час блокування щитом).            |
| `Broken`        | Так, якщо предмет має 0 міцності (наприклад, елітри змінюють текстуру, коли ламаються). |
| `HasComponent`  | Так, якщо предмет містить певний компонент.                                                                |

Третій і четвертий параметри — це моделі, які будуть використовуватися, коли властивість має значення `true` або `false` відповідно.

:::: tabs

== Вихідний код

@[code transcludeWith=:::condition](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModModelProvider.java)

:::warning ВАЖЛИВО

Щоб отримати `Identifier`, який передається в `ItemModelUtils.plainModel()`, завжди використовуйте `itemModelGenerator.createFlatItemModel()`, інакше будуть згенеровані лише клієнтські предмети, а не моделі!

:::

== Клієнтський предмет

`generated/assets/example-mod/items/flashlight.json`

@[code](@/reference/latest/src/main/generated/assets/example-mod/items/flashlight.json)

== Моделі предмета

`generated/assets/example-mod/models/item/flashlight.json`

@[code](@/reference/latest/src/main/generated/assets/example-mod/models/item/flashlight.json)

`generated/assets/example-mod/models/item/flashlight_lit.json`

@[code](@/reference/latest/src/main/generated/assets/example-mod/models/item/flashlight_lit.json)

== Текстури

<DownloadEntry visualURL="/assets/develop/data-generation/item-model/flashlight_textures_big.png" downloadURL="/assets/develop/data-generation/item-model/flashlight_textures.zip">Текстури ліхтарика</DownloadEntry>

== Попередній перегляд

<VideoPlayer src="/assets/develop/data-generation/item-model/flashlight_turning_on.webm">Увімкнення та вимкнення ліхтарика</VideoPlayer>

::::

### Композит {#composite}

Складені моделі предметів складаються з однієї або кількох текстур, накладених одна на одну. Для цього немає стандартних методів; вам потрібно використовувати поле `itemModelOutput` `itemModelGenerator` і викликати в ньому `accept()`.

::: tabs

== Вихідний код

@[code transcludeWith=:::composite](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModModelProvider.java)

== Клієнтський предмет

`generated/assets/example-mod/items/enhanced_hoe.json`

@[code](@/reference/latest/src/main/generated/assets/example-mod/items/enhanced_hoe.json)

== Моделі предмета

`generated/assets/example-mod/models/item/enhanced_hoe.json`

@[code](@/reference/latest/src/main/generated/assets/example-mod/models/item/enhanced_hoe.json)

`generated/assets/example-mod/models/item/enhanced_hoe_plus.json`

@[code](@/reference/latest/src/main/generated/assets/example-mod/models/item/enhanced_hoe_plus.json)

== Текстури

<DownloadEntry visualURL="/assets/develop/data-generation/item-model/enhanced_hoe_textures_big.png" downloadURL="/assets/develop/data-generation/item-model/enhanced_hoe_textures.zip">Покращена текстура мотики</DownloadEntry>

:::

### Вибір {#select}

Рендерить модель предмета на основі значення певної властивості. Ось деякі з них:

| Властивість         | Опис                                                                                                                                   |
| ------------------- | -------------------------------------------------------------------------------------------------------------------------------------- |
| `ContextDimension`  | Рендерить модель предмета на основі виміру, у якому знаходиться гравець (Верхній світ, Незер, Енд). |
| `MainHand`          | Рендерить модель предмета, якщо предмет знаходиться в головній руці гравця.                                            |
| `DisplayContext`    | Рендерить моделі предмета залежно від того, де знаходиться предмет (`ground`, `fixed`, `head`, …).  |
| `ContextEntityType` | Рендерить модель предмета на основі сутності, яка зберігає елемент.                                                    |

У цьому прикладі предмет змінює текстуру під час подорожі між вимірами: він зелений у Верхньому світі, червоний у Незері та чорний в Енді.

::: tabs

== Вихідний код

@[code transcludeWith=:::select](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModModelProvider.java)

== Клієнтський предмет

`generated/assets/example-mod/items/dimensional_crystal.json`

@[code](@/reference/latest/src/main/generated/assets/example-mod/items/dimensional_crystal.json)

== Моделі предмета

`generated/assets/example-mod/models/item/dimensional_crystal_overworld.json`

@[code](@/reference/latest/src/main/generated/assets/example-mod/models/item/dimensional_crystal_overworld.json)

`generated/assets/example-mod/models/item/dimensional_crystal_nether.json`

@[code](@/reference/latest/src/main/generated/assets/example-mod/models/item/dimensional_crystal_nether.json)

`generated/assets/example-mod/models/item/dimensional_crystal_end.json`

@[code](@/reference/latest/src/main/generated/assets/example-mod/models/item/dimensional_crystal_end.json)

== Текстури

<DownloadEntry visualURL="/assets/develop/data-generation/item-model/dimensional_crystal_textures_big.png" downloadURL="/assets/develop/data-generation/item-model/dimensional_crystal_textures.zip">Текстури вимірного кристала</DownloadEntry>

== Попередній перегляд

![Текстура вимірного кристала, що змінюється на основі виміру](/assets/develop/data-generation/item-model/crystal.png)

:::

### Розсилка діапазону {#range-dispatch}

Рендерить модель предмета на основі значення числової властивості. Уключає предмет і список варіантів, кожен з яких поєднується зі значенням. Приклади включають компас, лук і щітку.

Існує досить багато підтримуваних властивостей, ось кілька прикладів:

| Властивість   | Опис                                                                                                                                   |
| ------------- | -------------------------------------------------------------------------------------------------------------------------------------- |
| `Cooldown`    | Рендерить модель предмета на основі залишкового часу перезарядки предмета.                                             |
| `Count`       | Рендерить модель предмета на основі розміру стосу.                                                                     |
| `UseDuration` | Рендерить модель предмета на основі того, як довго він використовується.                                               |
| `Damage`      | Рендерить модель предмета на основі шкоди від атаки (компонент «minecraft:damage»). |

У цьому прикладі використовується `Count`, змінюючи текстуру від одного ножа до трьох залежно від розміру стосу.

::: tabs

== Вихідний код

@[code transcludeWith=:::range-dispatch](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModModelProvider.java)

== Клієнтський предмет

`generated/assets/example-mod/items/throwing_knives.json`

@[code](@/reference/latest/src/main/generated/assets/example-mod/items/throwing_knives.json)

== Моделі предмета

`generated/assets/example-mod/models/item/throwing_knives_one.json`

@[code](@/reference/latest/src/main/generated/assets/example-mod/models/item/throwing_knives_one.json)

`generated/assets/example-mod/models/item/throwing_knives_two.json`

@[code](@/reference/latest/src/main/generated/assets/example-mod/models/item/throwing_knives_two.json)

`generated/assets/example-mod/models/item/throwing_knives_three.json`

@[code](@/reference/latest/src/main/generated/assets/example-mod/models/item/throwing_knives_three.json)

== Текстури

<DownloadEntry visualURL="/assets/develop/data-generation/item-model/throwing_knives_textures_big.png" downloadURL="/assets/develop/data-generation/item-model/throwing_knives_textures.zip">Текстури кидальних ножів</DownloadEntry>

== Попередній перегляд

![Метальні ножі змінюють текстуру залежно від кількості](/assets/develop/data-generation/item-model/throwing_knives_example.png)

:::

## Спеціальні моделі предметів {#custom}

Генерувати моделі предметів не обов’язково лише за допомогою методів; ви, звичайно, можете створити свій власний. У цьому розділі ми створимо власну модель для предмета повітряної кулі.

Усі поля та методи для цієї частини посібника оголошено у статичному внутрішньому класі під назвою `CustomItemModelGenerator`.

:::details Показ `CustomItemModelGenerator`

@[code transcludeWith=:::custom-item-model-generator:::](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModModelProvider.java)

:::

### Створення власної батьківської моделі {#custom-parent}

Спочатку створімо модель батьківського предмета, яка визначає, як предмет виглядає в грі. Скажімо, ми хочемо, щоб повітряна куля виглядала як прості моделі предметів, але в збільшеному масштабі.

Для цього ми створимо `resources/assets/example-mod/models/item/scaled2x.json`, встановимо батьківською модель `item/generated`, а потім перевизначимо масштабування.

@[code](@/reference/latest/src/main/resources/assets/example-mod/models/item/scaled2x.json)

Це зробить модель візуально у два рази більше простих.

### Створення `ModelTemplate` {#custom-item-model}

Далі нам потрібно створити екземпляр класу `ModelTemplate`. Він представлятиме фактичну [модель батьківського предмета] (#custom-parent) у нашому моді.

@[code transcludeWith=:::custom-item-model:::](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModModelProvider.java)

Метод `item()` створює новий екземпляр `ModelTemplate`, вказуючи на файл `scaled2x.json`, який ми створили раніше.

TextureSlot `LAYER0` представляє змінну текстури `#layer0`, яка потім буде замінена ідентифікатором, що вказує на текстуру.

### Додавання власного методу Datagen {#custom-datagen-method}

Останнім кроком є ​​створення спеціального методу, який буде викликаний у методі `generateItemModels()` і відповідатиме за створення наших моделей предметів.

@[code transcludeWith=:::custom-item-datagen-method](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModModelProvider.java)

Розберімося, для чого потрібні параметри:

1. `Item item`: предмет, для якого ми створюємо моделі.
2. `ItemModelGenerators generator`: те саме, що передається в метод `generateItemModels()`. Використовується для своїх полів.

Спочатку ми отримуємо `Identifier` предмета за допомогою `SCALED2X.create()`, передаючи `TextureMapping` і `modelOutput` з нашого параметра `generator`.

Потім ми використаємо інше його поле, `itemModelOutput` (яке, по суті, працює як споживач), і використаємо метод `accept()`, щоб моделі фактично були згенеровані.

### Виклик власного методу {#custom-call}

Тепер нам потрібно лише викликати наш метод у методі `generateItemModels()`.

@[code transcludeWith=:::custom-balloon](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModModelProvider.java)

Не забудьте додати файл текстури!

<DownloadEntry visualURL="/assets/develop/data-generation/item-model/balloon_big.png" downloadURL="/assets/develop/data-generation/item-model/balloon.png">Текстура повітряної кульки</DownloadEntry>

## Джерела та посилання {#sources-and-links}

Ви можете переглянути приклади тестів у [Fabric API](https://github.com/FabricMC/fabric/blob/1.21.11/fabric-data-generation-api-v1/src/), [прикладний мод](https://github.com/FabricMC/fabric-docs/tree/main/reference) цієї документації для отримання додаткової інформації.
