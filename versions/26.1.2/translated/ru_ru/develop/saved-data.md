---
title: Saved Data
description: Сохранение данных между игровыми сессиями.
authors:
  - dicedpixels
resources:
  https://minecraft.wiki/w/NBT_format: Формат NBT - Minecraft Wiki
  https://docs.neoforged.net/docs/datastorage/saveddata/: Сохранение данных - Документация NeoForge
---

Saved Data — это встроенное в Minecraft решение для сохранения данных между сессиями.

Данные сохраняются на диске и загружаются заново при закрытии и повторном открытии игры. Эти данные обычно имеют определенный объем (например, уровень). Данные записываются на диск в формате [NBT](https://minecraft.wiki/w/NBT_format), а для сериализации/десериализации этих данных используются [кодеки](./codecs).

Рассмотрим простой сценарий, в котором нам нужно сохранить кол-во блоков, разрушенных игроком. Мы можем хранить этот счет на логическом сервере.

Мы можем использовать событие `PlayerBlockBreakEvents.AFTER` с простым статическим целочисленным полем для хранения этого значения и отправки его в виде сообщения в чате.

```java
private static int blocksBroken = 0; // keeps track of the number of blocks broken

PlayerBlockBreakEvents.AFTER.register((level, player, pos, state, blockEntity) -> {
    blocksBroken++; // increment the counter each time a block is broken
    player.displayClientMessage(Component.literal("Blocks broken: " + blocksBroken), false);
});
```

Теперь, когда вы разбиваете блок, вы увидите сообщение с кол-вом.

![Разрушение блоков](/assets/develop/saved-data/block-breaking.png)

Если вы перезапустите Minecraft, загрузите мир и начнете разрушать блоки, вы заметите, что счетчик сбросился. Здесь нам понадобятся Saved Data. Затем мы можем сохранить это значение, чтобы при следующей загрузке мира мы могли получить сохраненное значение и начать его инкрементировать с этого момента.

## Saving Data {#saving-data}

`SavedData` — основной класс, отвечающий за управление сохранением/загрузкой данных. Поскольку это абстрактный класс, от вас ожидается предоставление реализации.

### Настройка класса данных {#setting-up-a-data-class}

Назовем наш класс данных `SavedBlockData` и сделаем его расширением `SavedData`.

Этот класс будет содержать поле для отслеживания кол-ва разбитых блоков, а также метод для получения и метод для увеличения этого числа.

<<< @/reference/26.1.2/src/main/java/com/example/docs/saveddata/SavedBlockData.java#basic_structure

Для сериализации и десериализации этих данных нам необходимо определить кодек. Мы можем создать кодек, используя различные примитивные кодеки, предоставляемые Minecraft.

Для инициализации класса потребуется конструктор с аргументом `int`.

<<< @/reference/26.1.2/src/main/java/com/example/docs/saveddata/SavedBlockData.java#ctor

Затем мы можем создать кодек.

<<< @/reference/26.1.2/src/main/java/com/example/docs/saveddata/SavedBlockData.java#codec

Мы должны вызывать метод `setDirty()`, когда данные действительно изменяются, чтобы Minecraft знал, что их необходимо сохранить на диск.

<<< @/reference/26.1.2/src/main/java/com/example/docs/saveddata/SavedBlockData.java#set_dirty

Наконец, нам необходимо иметь `SavedDataType`, который описывает наши сохраненные данные. Первый аргумент соответствует имени файла, который будет создан в каталоге `data` мира.

<<< @/reference/26.1.2/src/main/java/com/example/docs/saveddata/SavedBlockData.java#type

### Доступ к Saved Data {#accessing-saved-data}

Как упоминалось ранее, сохраненные данные могут быть связаны с областью действия, такой как текущий уровень. В данном случае наши данные будут частью данных уровня. Мы можем получить `DimensionDataStorage` уровня, чтобы добавить и изменить наши данные.

Мы поместим эту логику в служебный метод.

<<< @/reference/26.1.2/src/main/java/com/example/docs/saveddata/SavedBlockData.java#method

### Использование Saved Data {#using-saved-data}

Теперь, когда все настроено, давайте сохраним некоторые данные.

Мы можем повторно использовать первый сценарий и вместо инкрементирования поля вызвать нашу функцию `incrementBlocksBroken` из нашего `SavedBlockData`.

<<< @/reference/26.1.2/src/main/java/com/example/docs/saveddata/ExampleModSavedData.java#event_registration

Это должно увеличить значение и сохранить его на диске.

Если вы перезапустите Minecraft, загрузите мир и разрушите блок, вы увидите, что ранее сохраненное кол-во теперь увеличилось.

Если вы зайдете в папку «data» мира, то увидите файл «.dat» с именем «saved_block_data.dat».
Открыв этот файл в программе чтения NBT, вы увидите, как в нем сохраняются наши данные.

![NBTg](/assets/develop/saved-data/nbt.png)
