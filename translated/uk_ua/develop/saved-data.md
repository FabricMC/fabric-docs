---
title: Збереженні даних
description: Збереження даних між ігровими сесіями.
authors:
  - dicedpixels
---

Збережені дані — це вбудоване рішення Minecraft для збереження даних під час сеансів.

Дані зберігаються на носію даних та перезавантажуються, коли гру закривають і відкривають знову. Ці дані зазвичай обмежені (наприклад, рівень). Дані записуються на носій, оскільки [NBT](https://minecraft.wiki/w/NBT_format) і [кодеки](./codecs) використовуються для серіалізації/десеріалізації цих даних.

Розгляньмо простий сценарій, коли нам потрібно зберегти кількість блоків, зламаних гравцем. Ми можемо зберегти цей підрахунок на логічному сервері.

Ми можемо використовувати подію `PlayerBlockBreakEvents.AFTER` із простим статичним цілим полем, щоб зберегти це значення та опублікувати його як повідомлення чату.

```java
private static int blocksBroken = 0; // keeps track of the number of blocks broken

PlayerBlockBreakEvents.AFTER.register((level, player, pos, state, blockEntity) -> {
    blocksBroken++; // increment the counter each time a block is broken
    player.displayClientMessage(Component.literal("Blocks broken: " + blocksBroken), false);
});
```

Тепер, коли ви ламаєте блок, ви побачите повідомлення з підрахунком.

![Ламання блока](/assets/develop/saved-data/block-breaking.png)

Якщо ви перезапустите Minecraft, завантажите світ і почнете ламати блоки, ви помітите, що підрахунок скинуто. Ось де нам потрібні збережені дані. Потім ми можемо зберегти цей підрахунок, щоб наступного разу, коли ви завантажуватимете світ, ми могли отримати збережений підрахунок і почати збільшувати її з цього моменту.

## Збереження даних {#saving-data}

`SavedData` — основний клас, який відповідає за керування збереженням/завантаженням даних. Оскільки це абстрактний клас, ви повинні надати реалізацію.

### Налаштування класу даних {#setting-up-a-data-class}

Назвімо наш клас даних `SavedBlockData` і розширимо `SavedData`.

Цей клас міститиме поле для відстеження кількості зламаних блоків, а також метод отримання та метод збільшення цього числа.

@[code lang=java transcludeWith=:::basic_structure](@/reference/latest/src/main/java/com/example/docs/saveddata/SavedBlockData.java)

Для серіалізації та десеріалізації цих даних нам потрібно визначити кодек. Ми можемо створити кодек, використовуючи різні примітивні кодеки, надані Minecraft.

Для ініціалізації класу вам знадобиться конструктор з аргументом `int`.

@[code lang=java transcludeWith=:::ctor](@/reference/latest/src/main/java/com/example/docs/saveddata/SavedBlockData.java)

Тоді ми можемо створити кодек.

@[code lang=java transcludeWith=:::codec](@/reference/latest/src/main/java/com/example/docs/saveddata/SavedBlockData.java)

Ми повинні викликати `setDirty()`, коли дані фактично змінюються, щоб Minecraft знав, що їх потрібно зберегти на носій.

@[code lang=java transcludeWith=:::set_dirty](@/reference/latest/src/main/java/com/example/docs/saveddata/SavedBlockData.java)

Нарешті, ми повинні мати `SavedDataType`, який описує наші збережені дані. Перший аргумент відповідає імені файлу, який буде створено в каталозі `data` світу.

@[code lang=java transcludeWith=:::type](@/reference/latest/src/main/java/com/example/docs/saveddata/SavedBlockData.java)

### Доступ до збережених даних {#accessing-saved-data}

Як згадувалося раніше, збережені дані можуть бути пов’язані з таким обсягом, як поточний рівень. У цьому випадку наші дані будуть частиною даних рівня. Ми можемо отримати `DimensionDataStorage` рівня, щоб додавати та змінювати наші дані.

Ми розмістимо цю логіку в методі утиліти.

@[code lang=java transcludeWith=:::method](@/reference/latest/src/main/java/com/example/docs/saveddata/SavedBlockData.java)

### Використання збережених даних {#using-saved-data}

Тепер, коли ми все налаштували, збережімо деякі дані.

Ми можемо повторно використати перший сценарій і замість того, щоб збільшувати поле, ми можемо викликати наш `incrementBlocksBroken` з нашого `SavedBlockData`.

@[code lang=java transcludeWith=:::event_registration](@/reference/latest/src/main/java/com/example/docs/saveddata/ExampleModSavedData.java)

Це повинно збільшити значення та зберегти його на носію.

Якщо ви перезапустите Minecraft, завантажите світ і зламаєте блок, ви побачите, що раніше збережений підрахунок тепер збільшився.

Якщо ви зайдете в каталог `data` світу, ви побачите файл `.dat` з назвою `saved_block_data.dat`.
Відкриття цього файлу в програмі читання NBT покаже, як зберігаються наші дані.

![NBTg](/assets/develop/saved-data/nbt.png)
