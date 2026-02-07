---
title: Блоки-сутності
description: Навчіться створювати блоки-сутності.
authors:
  - natri0
---

Блоки-сутності — це спосіб зберігати додаткові дані для блока, які не є частиною стану блока: вміст інвентарю, спеціальна назва тощо.
Minecraft використовує блоки-сутності для блоків, як-от скрині, печі й командні блоки.

Як приклад, ми створимо блок, який підраховує, скільки разів було натиснуто ПКМ.

## Створення блока-сутності {#creating-the-block-entity}

Щоб змусити Minecraft розпізнавати та завантажувати нові блоки-сутності, нам потрібно створити тип блока-сутності. Це робиться шляхом розширення класу `BlockEntity` і реєстрації його в новому класі `ModBlockEntities`.

@[code transcludeWith=:::1](@/reference/latest/src/main/java/com/example/docs/block/entity/custom/CounterBlockEntity.java)

Реєстрація `BlockEntity` дає `BlockEntityType`, подібний до `COUNTER_BLOCK_ENTITY`, який ми використовували вище:

@[code transcludeWith=:::1](@/reference/latest/src/main/java/com/example/docs/block/entity/ModBlockEntities.java)

::: tip

Зверніть увагу, що конструктор `CounterBlockEntity` приймає два параметри, а конструктор `BlockEntity` приймає три: `BlockEntityType`, `BlockPos` і `BlockState`.
Якби ми не жорстко закодували `BlockEntityType`, клас `ModBlockEntities` не компілювався б! Це тому, що `BlockEntityFactory`, який є функціональним інтерфейсом, описує функцію, яка приймає лише два параметри, як і наш конструктор.

:::

## Створення блока {#creating-the-block}

Далі, щоб фактично використовувати блок-сутність нам потрібен блок, який реалізує `EntityBlock`. Створімо один і назвемо його `CounterBlock`.

::: tip

Є два способи підійти до цього:

- створити блок, який розширює `BaseEntityBlock` і реалізувати метод `createBlockEntity`
- створити блок, який сам собою реалізує `EntityBlock` і замінити метод `createBlockEntity`

У цьому прикладі ми використаємо перший підхід, оскільки `BaseEntityBlock` також надає кілька крутих штук.

:::

@[code transcludeWith=:::1](@/reference/latest/src/main/java/com/example/docs/block/custom/CounterBlock.java)

Використання `BaseEntityBlock` як батьківського класу означає, що нам також потрібно реалізувати метод `createCodec`, який досить простий.

На відміну від блоків, які є одинарними, нова блок-сутність створюється для кожного екземпляра блока. Це робиться за допомогою методу `createBlockEntity`, який приймає позицію та `BlockState` і повертає `BlockEntity` або `null`, якщо його не має бути.

Не забудьте зареєструвати блок у класі `ModBlocks`, як у посібнику [створення вашого першого блока](../blocks/first-block):

@[code transcludeWith=:::5](@/reference/latest/src/main/java/com/example/docs/block/ModBlocks.java)

## Використання блока-сутності {#using-the-block-entity}

Тепер, коли у нас є блок-сутність, ми можемо використовувати його для збереження кількості натискань ПКМ по блоку. Ми зробимо це, додавши поле `clicks` до класу `CounterBlockEntity`:

@[code transcludeWith=:::2](@/reference/latest/src/main/java/com/example/docs/block/entity/custom/CounterBlockEntity.java)

Метод `setChanged`, який використовується в `incrementClicks`, повідомляє грі, що дані цієї сутності було оновлено; це буде корисно, коли ми додамо методи серіалізації лічильника та завантаження його назад із файлу збереження.

Далі нам потрібно збільшувати це поле кожного разу, коли по блоку натискають ПКМ. Це робиться шляхом перевизначення методу `useWithoutItem` в класі `CounterBlock`:

@[code transcludeWith=:::2](@/reference/latest/src/main/java/com/example/docs/block/custom/CounterBlock.java)

Оскільки `BlockEntity` не передається в метод, ми використовуємо `level.getBlockEntity(pos)`, і якщо `BlockEntity` недійсний, повертаємося з методу.

![Повідомлення «You've clicked the block for the 6th time» на екрані після натискання ПКМ](/assets/develop/blocks/block_entities_1.png)

## Збереження і завантаження даних {#saving-loading}

Тепер, коли у нас є функціональний блок, ми повинні зробити так, щоб лічильник не обнулявся між перезавантаженнями гри. Це робиться шляхом серіалізації в NBT під час збереження гри та десеріалізації під час завантаження.

Збереження в NBT виконується через `ValueInput`s і `ValueOutput`. Ці подання відповідають за зберігання помилок кодування/декодування та відстеження реєстрів протягом усього процесу серіалізації.

Ви можете читати з `ValueInput` за допомогою методу `read`, передаючи `Codec` для потрібного типу. Так само ви можете писати в `ValueOutput` за допомогою методу `store`, передаючи кодек для типу та значення.

Існують також методи для примітивів, наприклад `getInt`, `getShort`, `getBoolean` тощо для читання та `putInt`, `putShort`, `putBoolean` тощо для запису. Представлення також надає методи для роботи зі списками, типами, що допускають null-значення, і вкладеними об’єктами.

Серіалізація виконується за допомогою методу `saveAdditional`:

@[code transcludeWith=:::3](@/reference/latest/src/main/java/com/example/docs/block/entity/custom/CounterBlockEntity.java)

Тут ми додаємо поля, які мають бути збережені в переданому `ValueOutput`: у випадку блока лічильника, це поле `clicks`.

Читання аналогічно, ви отримуєте значення, які ви зберегли раніше з `ValueInput`, і зберігаєте їх у полях BlockEntity:

@[code transcludeWith=:::4](@/reference/latest/src/main/java/com/example/docs/block/entity/custom/CounterBlockEntity.java)

Тепер, якщо ми збережемо та перезавантажимо гру, блок лічильника має продовжуватися з того місця, на якому він зупинився під час збереження.

У той час як `saveAdditional` і `loadAdditional` керують збереженням і завантаженням на носій і з носія, все ще є проблема:

- Сервер знає правильне значення `clicks`.
- Клієнт не отримує правильне значення під час завантаження чанку.

Щоб виправити це, ми перевизначаємо `getUpdateTag`:

@[code transcludeWith=:::7](@/reference/latest/src/main/java/com/example/docs/block/entity/custom/CounterBlockEntity.java)

Тепер, коли гравець увійде або переміститься в чанк, де існує блок, він одразу побачить правильне значення лічильника.

## Тикери {#tickers}

Інтерфейс `EntityBlock` також визначає метод під назвою `getTicker`, який можна використовувати для запуску коду кожного такту для кожного екземпляра блока. Ми можемо реалізувати це, створивши статичний метод, який використовуватиметься як `BlockEntityTicker`:

Метод `getTicker` також має перевірити, чи переданий `BlockEntityType` збігається з тим, який ми використовуємо, і якщо це так, повертати функцію, яка буде викликатися кожного такту. На щастя, є допоміжна функція, яка виконує перевірку в `BaseEntityBlock`:

@[code transcludeWith=:::3](@/reference/latest/src/main/java/com/example/docs/block/custom/CounterBlock.java)

`CounterBlockEntity::tick` — це посилання на статичний метод `tick`, який ми повинні створити в класі `CounterBlockEntity`. Структурувати його таким чином не обов’язково, але це гарна практика, щоб код був чистим і організованим.

Скажімо, ми хочемо зробити так, щоб лічильник можна було збільшувати лише один раз кожні 10 тактів (2 рази на секунду). Ми можемо зробити це, додавши поле `ticksSinceLast` до класу `CounterBlockEntity` і збільшуючи його кожного такту:

@[code transcludeWith=:::5](@/reference/latest/src/main/java/com/example/docs/block/entity/custom/CounterBlockEntity.java)

Не забудьте серіалізувати та десеріалізувати це поле!

Тепер ми можемо використовувати `ticksSinceLast`, щоб перевірити, чи можна збільшити лічильник у `incrementClicks`:

@[code transcludeWith=:::6](@/reference/latest/src/main/java/com/example/docs/block/entity/custom/CounterBlockEntity.java)

::: tip

Якщо блок-сутність не працює, спробуйте перевірити реєстраційний код! Він має передати блоки, дійсні для цієї сутності, у `BlockEntityType.Builder`, інакше він видасть попередження на консолі:

```log
[13:27:55] [Server thread/WARN] (Minecraft) Block entity example-mod:counter @ BlockPos{x=-29, y=125, z=18} state Block{example-mod:counter_block} invalid for ticking:
```

:::

<!---->
