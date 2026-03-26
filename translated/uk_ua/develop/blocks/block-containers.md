---
title: Блоки-контейнери
description: Прочитайте про те, як додати контейнери до ваших блоків-сутностей.
authors:
  - natri0
resources:
  https://docs.neoforged.net/docs/inventories/container/: Контейнери — Документація NeoForge
---

Це гарна практика під час створення блоків, які можуть зберігати предмети, як-от скрині та печі, реалізувати `Контейнер`. Це дає можливість, наприклад, взаємодіяти з блоком за допомогою лійок.

У цьому посібнику ми створимо блок, який використовує свій контейнер для дублювання будь-яких розміщених у ньому предметів.

## Створення блока {#creating-the-block}

Це повинно бути знайоме читачеві, якщо він дотримувався посібників [створення вашого першого блока](../blocks/first-block) і [блока-сутності](../blocks/block-entities). Ми створимо `DuplicatorBlock`, який розширює `BaseEntityBlock` і реалізує `EntityBlock`.

<<< @/reference/latest/src/main/java/com/example/docs/block/custom/DuplicatorBlock.java#block

Потім нам потрібно створити `DuplicatorBlockEntity`, який має реалізувати інтерфейс `Container`. Оскільки зазвичай очікується, що більшість контейнерів працюватимуть однаково, ви можете скопіювати та вставити помічник під назвою `ImplementedContainer`, який виконує більшу частину роботи, залишаючи нам лише кілька методів для реалізації.

:::details Показати `ImplementedContainer`

<<< @/reference/latest/src/main/java/com/example/docs/container/ImplementedContainer.java

:::

<<< @/reference/latest/src/main/java/com/example/docs/block/entity/custom/DuplicatorBlockEntity.java#be

У списку `items` зберігається вміст контейнера. Для цього блока ми встановили розмір 1 слота для входу.

Не забудьте зареєструвати блок і сутність блока у відповідних класах!

### Збереження і завантаження {#saving-loading}

Якщо ми хочемо, щоб вміст зберігався між перезавантаженнями гри, як стандартний `BlockEntity`, нам потрібно зберегти його як NBT. На щастя, Mojang надає допоміжний клас під назвою `ContainerHelper` з усією необхідною логікою.

<<< @/reference/latest/src/main/java/com/example/docs/block/entity/custom/DuplicatorBlockEntity.java#save

## Взаємодія з контейнером {#interacting-with-the-container}

Технічно контейнер вже справний. Однак для вставлення предметів нам наразі потрібні лійки. Зробімо так, щоб ми могли вставляти предмети, натиснувши ПКМ по блоку.

Для цього нам потрібно перевизначити метод `useItemOn` у `DuplicatorBlock`:

<<< @/reference/latest/src/main/java/com/example/docs/block/custom/DuplicatorBlock.java#useon

Тут, якщо гравець тримає предмет і є порожній слот, ми переміщуємо предмет з руки гравця в контейнер блока та повертаємо `InteractionResult.SUCCESS`.

Тепер, коли ви натискаєте ПКМ по блоку предметом, його більше не буде! Якщо ви запустите `/data get block` для блока, ви побачите предмет у полі `Items` у NBT.

![Блок копіювання та вихід /data get block, що показує предмет у контейнері](/assets/develop/blocks/container_1.png)

### Дублювання предметів {#duplicating-items}

Зробімо тепер так, щоб блок дублював стіс, який ви в нього кинули, але лише два предмети одночасно. І нехай кожен раз чекає секунду, щоб не спамити гравця предметами!

Для цього ми додамо функцію `tick` до `DuplicatorBlockEntity` і поле для збереження того, скільки ми чекали:

<<< @/reference/latest/src/main/java/com/example/docs/block/entity/custom/DuplicatorBlockEntity.java#tick

`DuplicatorBlock` тепер має мати метод `getTicker`, який повертає посилання на `DuplicatorBlockEntity::tick`.

<VideoPlayer src="/assets/develop/blocks/container_2.mp4">Блок дублікатора, що дублює дубову колоду</VideoPlayer>

## Світові контейнери {#worldly-containers}

Усталено ви можете вставляти та витягувати предмети з контейнера з будь-якого боку. Однак інколи це може бути не бажаною поведінкою: наприклад, піч приймає паливо лише збоку, а предмети — зверху.

Щоб створити таку поведінку, нам потрібно реалізувати інтерфейс `WorldlyContainer` в `BlockEntity`. Цей інтерфейс має три методи:

- `getSlotsForFace(Direction)` дозволяє контролювати, з якими слотами можна взаємодіяти з певного боку.
- `canPlaceItemThroughFace(int, ItemStack, Direction)` дозволяє контролювати, чи можна вставляти предмет у слот з певного боку.
- `canTakeItemThroughFace(int, ItemStack, Direction)` дозволяє контролювати, чи можна витягти предмет зі слота з певного боку.

Змінімо `DuplicatorBlockEntity`, щоб приймати лише елементи згори:

<<< @/reference/latest/src/main/java/com/example/docs/block/entity/custom/DuplicatorBlockEntity.java#accept

`getSlotsForFace` повертає масив _індексів_ слотів, з якими можна взаємодіяти з даної сторони. У цьому випадку ми маємо лише один слот (`0`), тому ми повертаємо масив лише з цим індексом.

Крім того, ми повинні змінити метод `useItemOn` `DuplicatorBlock`, щоб фактично дотримуватися нової поведінки:

<<< @/reference/latest/src/main/java/com/example/docs/block/custom/DuplicatorBlock.java#place

Тепер, якщо ми спробуємо вставити предмети збоку, а не зверху, це не спрацює!

<VideoPlayer src="/assets/develop/blocks/container_3.webm">Дублікатор активується лише під час взаємодії з його верхньою стороною</VideoPlayer>

## Меню {#menus}

Щоб отримати доступ до нового блока-контейнера через меню, подібно до скрині, зверніться до посібника [меню контейнерів](./container-menus).
