---
title: Блоки-сутності
description: Навчіться створювати блоки-сутності.
authors:
  - natri0
---

Блоки-сутності — це спосіб зберігати додаткові дані для блоку, які не є частиною стану блоку: вміст інвентарю, спеціальна назва тощо.
Minecraft використовує блоки-сутності для блоків, як-от скрині, печі й командні блоки.

Як приклад, ми створимо блок, який підраховує, скільки разів було натиснуто ПКМ.

## Створення блоку-сутність {#creating-the-block-entity}

Щоб змусити Minecraft розпізнавати та завантажувати нові блоки-сутності, нам потрібно створити тип блоку-сутності. Це робиться шляхом розширення класу `BlockEntity` і реєстрації його в новому класі `ModBlockEntities`.

@[code transcludeWith=:::1](@/reference/latest/src/main/java/com/example/docs/block/entity/custom/CounterBlockEntity.java)

Реєстрація `BlockEntity` дає `BlockEntityType`, подібний до `COUNTER_BLOCK_ENTITY`, який ми використовували вище:

@[code transcludeWith=:::1](@/reference/latest/src/main/java/com/example/docs/block/entity/ModBlockEntities.java)

:::tip
Зверніть увагу, що конструктор `CounterBlockEntity` приймає два параметри, а конструктор `BlockEntity` приймає три: `BlockEntityType`, `BlockPos` і `BlockState`.
Якби ми не жорстко закодували `BlockEntityType`, клас `ModBlockEntities` не компілювався б! Це тому, що `BlockEntityFactory`, який є функціональним інтерфейсом, описує функцію, яка приймає лише два параметри, як і наш конструктор.
:::

## Створення блоку {#creating-the-block}

Далі, щоб фактично використовувати блок-сутність нам потрібен блок, який реалізує `BlockEntityProvider`. Нумо створімо один і назвемо його `CounterBlock`.

:::tip
Є два способи підійти до цього:

- створити блок, який розширює `BlockWithEntity` і реалізувати метод `createBlockEntity`
- створити блок, який сам по собі реалізує `BlockEntityProvider` і замінити метод `createBlockEntity`

У цьому прикладі ми використаємо перший підхід, оскільки `BlockWithEntity` також надає кілька крутих штук.
:::

@[code transcludeWith=:::1](@/reference/latest/src/main/java/com/example/docs/block/custom/CounterBlock.java)

Використання `BlockWithEntity` як батьківського класу означає, що нам також потрібно реалізувати метод `createCodec`, який досить простий.

На відміну від блоків, які є одинарними, нова блок-сутність створюється для кожного екземпляра блоку. Це робиться за допомогою методу `createBlockEntity`, який приймає позицію та `BlockState` і повертає `BlockEntity` або `null`, якщо його не має бути.

Не забудьте зареєструвати блок у класі `ModBlocks`, як у посібнику [Створення вашого першого блоку](../blocks/first-block):

@[code transcludeWith=:::5](@/reference/latest/src/main/java/com/example/docs/block/ModBlocks.java)

## Використання блоку-сутності {#using-the-block-entity}

Тепер, коли у нас є блок-сутність, ми можемо використовувати його для збереження кількості натискань ПКМ по блоку. Ми зробимо це, додавши поле `clicks` до класу `CounterBlockEntity`:

@[code transcludeWith=:::2](@/reference/latest/src/main/java/com/example/docs/block/entity/custom/CounterBlockEntity.java)

Метод `markDirty`, який використовується в `incrementClicks`, повідомляє грі, що дані цієї сутності було оновлено; це буде корисно, коли ми додамо методи серіалізації лічильника та завантаження його назад із файлу збереження.

Далі нам потрібно збільшувати це поле кожного разу, коли по блоку натискають ПКМ. Це робиться шляхом перевизначення методу `onUse` в класі `CounterBlock`:

@[code transcludeWith=:::2](@/reference/latest/src/main/java/com/example/docs/block/custom/CounterBlock.java)

Оскільки `BlockEntity` не передається в метод, ми використовуємо `world.getBlockEntity(pos)`, і якщо `BlockEntity` недійсний, повертаємося з методу.

Оскільки `BlockEntity` не передається в метод, ми використовуємо `world.getBlockEntity(pos)`, і якщо `BlockEntity` недійсний, повертаємося з методу.

## Збереження і завантаження даних {#saving-loading}

Тепер, коли у нас є функціональний блок, ми повинні зробити так, щоб лічильник не обнулявся між перезавантаженнями гри. Це робиться шляхом серіалізації в NBT під час збереження гри та десеріалізації під час завантаження.

Серіалізація виконується за допомогою методу `writeNbt`:

@[code transcludeWith=:::3](@/reference/latest/src/main/java/com/example/docs/block/entity/custom/CounterBlockEntity.java)

Тут ми додаємо поля, які слід зберегти в переданому `NbtCompound`: у випадку блоку лічильника це поле `clicks`.

Тут ми додаємо поля, які слід зберегти в переданому `NbtCompound`: у випадку блоку лічильника це поле `clicks`.

@[code transcludeWith=:::4](@/reference/latest/src/main/java/com/example/docs/block/entity/custom/CounterBlockEntity.java)

Тепер, якщо ми збережемо та перезавантажимо гру, блок лічильника має продовжуватися з того місця, на якому він зупинився під час збереження.

## Тикери {#tickers}

Інтерфейс `BlockEntityProvider` також визначає метод під назвою `getTicker`, який можна використовувати для запуску коду кожного тика для кожного екземпляра блоку. Ми можемо реалізувати це, створивши статичний метод, який використовуватиметься як `BlockEntityTicker`:

Метод `getTicker` також має перевірити, чи переданий `BlockEntityType` збігається з тим, який ми використовуємо, і якщо це так, повертати функцію, яка буде викликатися кожного такту. На щастя, є допоміжна функція, яка виконує перевірку в `BlockWithEntity`:

@[code transcludeWith=:::3](@/reference/latest/src/main/java/com/example/docs/block/custom/CounterBlock.java)

`CounterBlockEntity::tick` — це посилання на статичний метод `tick`, який ми повинні створити в класі `CounterBlockEntity`. Структурувати його таким чином не обов’язково, але це гарна практика, щоб код був чистим і організованим.

Скажімо, ми хочемо зробити так, щоб лічильник можна було збільшувати лише один раз кожні 10 тактів (2 рази на секунду). Ми можемо зробити це, додавши поле `ticksSinceLast` до класу `CounterBlockEntity` і збільшуючи його кожного такту:

@[code transcludeWith=:::5](@/reference/latest/src/main/java/com/example/docs/block/entity/custom/CounterBlockEntity.java)

Не забудьте серіалізувати та десеріалізувати це поле!

Не забудьте серіалізувати та десеріалізувати це поле!

@[code transcludeWith=:::6](@/reference/latest/src/main/java/com/example/docs/block/entity/custom/CounterBlockEntity.java)

:::tip
Якщо блок-сутність не працює, спробуйте перевірити реєстраційний код! Він має передати блоки, дійсні для цієї сутності, у `BlockEntityType.Builder`, інакше він видасть попередження на консолі:

```text
[13:27:55] [Server thread/WARN] (Minecraft) Block entity fabric-docs-reference:counter @ BlockPos{x=-29, y=125, z=18} state Block{fabric-docs-reference:counter_block} invalid for ticking:
```

:::
