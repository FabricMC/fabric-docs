---
title: Сущности блока
description: Узнайте, как создавать блочные сущности для своих пользовательских блоков.
authors:
  - natri0
resources:
  https://docs.neoforged.net/docs/blockentities/: Блок-сущности — документация NeoForge
---

Сущности блока - это способ хранения дополнительных данных для блока, которые не являются частью состояния блока: содержимое инвентаря, пользовательское имя и так далее.
В Minecraft используются блочные сущности для таких блоков, как сундуки, печи и командные блоки.

В качестве примера мы создадим блок, который будет подсчитывать, сколько раз на нем щелкнули ПКМ.

## Создание сущности блока {#creating-the-block-entity}

Чтобы Minecraft распознал и загрузил новые сущности блока, нам нужно создать тип сущности блока. Это делается путем расширения класса `BlockEntity` и регистрации его в новом классе `ModBlockEntities`.

@[code transcludeWith=:::1](@/reference/latest/src/main/java/com/example/docs/block/entity/custom/CounterBlockEntity.java)

Регистрация `BlockEntity` дает `BlockEntityType`, подобный `COUNTER_BLOCK_ENTITY`, который мы использовали выше:

@[code transcludeWith=:::1](@/reference/latest/src/main/java/com/example/docs/block/entity/ModBlockEntities.java)

::: tip

Обратите внимание, что конструктор `CounterBlockEntity` принимает два параметра, а конструктор `BlockEntity` - три: `BlockEntityType`, `BlockPos` и `BlockState`.
Если бы мы жёстко не закодировали `BlockEntityType`, класс `ModBlockEntities` не скомпилировался бы! Это происходит потому, что `BlockEntityFactory`, является функциональным интерфейсом, описывает функцию, которая принимает только два параметра, как и наш конструктор.

:::

## Создание блока {#creating-the-block}

Далее, чтобы действительно использовать сущность блока, нам нужен блок, реализующий `EntityBlock`. Давайте создадим такой блок и назовем его `CounterBlock`.

::: tip

К этому можно подойти двумя способами:

- создать блок, который расширяет `BaseEntityBlock` и реализует метод `createBlockEntity`
- создать блок, реализующий `EntityBlock` самостоятельно и переопределить метод `createBlockEntity`

В этом примере мы будем использовать первый подход, поскольку `BaseEntityBlock` также предоставляет несколько хороших утилит.

:::

@[code transcludeWith=:::1](@/reference/latest/src/main/java/com/example/docs/block/custom/CounterBlock.java)

Использование `BaseEntityBlock` в качестве родительского класса означает, что нам также необходимо реализовать метод `createCodec`, что довольно просто.

В отличие от блоков, которые являются сингл тонами, для каждого экземпляра блока создается новая сущность блока. Это делается с помощью метода `createBlockEntity`, который принимает позицию и `BlockState`, и возвращает `BlockEntity`, или `null`, если его не должно быть.

Не забудьте зарегистрировать блок в классе `ModBlocks`, как в [Создайте свой первый блок].(../blocks/first-block) guide:

@[code transcludeWith=:::5](@/reference/latest/src/main/java/com/example/docs/block/ModBlocks.java)

## Использование сущности блока {#using-the-block-entity}

Теперь, когда у нас есть сущность блока, мы можем использовать её для хранения кол-ва раз, когда на блоке щёлкали ПКМ. Для этого мы добавим поле `clicks` в класс `CounterBlockEntity`:

@[code transcludeWith=:::2](@/reference/latest/src/main/java/com/example/docs/block/entity/custom/CounterBlockEntity.java)

Метод `setChanged`, используемый в `incrementClicks`, сообщает игре, что данные этой сущности были обновлены; это может быть полезно, когда мы добавим методы для сериализации счётчика и загрузки его обратно из файла сохранения.

Далее нам нужно увеличивать это поле каждый раз, когда на блоке щелкают ПКМ. Это делается путём переопределения метода `useWithoutItem` в классе `CounterBlock`:

@[code transcludeWith=:::2](@/reference/latest/src/main/java/com/example/docs/block/custom/CounterBlock.java)

Поскольку `BlockEntity` не передается в метод, мы используем `level.getBlockEntity(pos)`, и если `BlockEntity` не является действительным, возвращаемся из метода.

!["Вы нажали на блок в 6-й раз" сообщение на экране после щелчка ПКМ](/assets/develop/blocks/block_entities_1.png)

## Сохранение и загрузка данных {#saving-loading}

Теперь, когда у нас есть функциональный блок, мы должны сделать так, чтобы счетчик не сбрасывался между перезапусками игры. Это делается путем сериализации в NBT при сохранении игры и десериализации при ее загрузке.

Сохранение в NBT осуществляется с помощью `ValueInput` и `ValueOutput`. Эти классы отвечают за сохранение ошибок с кодирования/декодирования, а также за отслеживание реестров во время процесса сериализации.

Вы можете читать данные из `ValueInput`, используя метод `read`, передавая `Codec` для нужного типа. Аналогично, вы можете записывать данные в `ValueOutput`, используя метод `store`, передавая `Codec` для типа и само значение.

Также есть методы для примитивных типов, такие как `getInt`, `getShort`, `getBoolean` и т. д. для записи и `putInt`, `putShort`, `putBoolean` и т. д. для записи. View также предоставляет методы для работы со списками, nullable-типами и вложенными объектами.

Сериализация выполняется с помощью метода `saveAdditional`:

@[code transcludeWith=:::3](@/reference/latest/src/main/java/com/example/docs/block/entity/custom/CounterBlockEntity.java)

Здесь мы добавляем поля, которые должны быть сохранены в переданный `ValueOutput`: в случае блока-счётчика это поле `clicks`.

Чтение происходит аналогично: вы получаете ранее сохранённые значения из `ValueInput` и сохраняете их в поля BlockEntity:

@[code transcludeWith=:::4](@/reference/latest/src/main/java/com/example/docs/block/entity/custom/CounterBlockEntity.java)

Теперь, если мы сохраним и перезагрузим игру, блок счетчика должен продолжиться с того места, на котором он остановился при сохранении.

Хотя `saveAdditional` и `loadAdditional` отвечают за сохранение и загрузку на диск и с диска, остаётся проблема:

- Сервер знает правильное значение `clicks`.
- Клиент не получает правильное значение при загрузке чанка.

Чтобы исправить это, мы переопределяем `getUpdateTag`:

@[code transcludeWith=:::7](@/reference/latest/src/main/java/com/example/docs/block/entity/custom/CounterBlockEntity.java)

Теперь, когда игрок входит в игру или перемещается в чанк, где есть блок, он сразу же увидит правильное значение счетчика.

## Тики {#tickers}

Интерфейс `EntityBlock` также определяет метод `getTicker`, который можно использовать для выполнения кода каждый тик для каждого экземпляра блока. Мы можем реализовать это, создав статический метод, который будет использоваться в качестве `BlockEntityTicker`:

Метод `getTicker` также должен проверить, совпадает ли переданный `BlockEntityType` с тем, который мы используем, и если да, то вернуть функцию, которая будет вызываться каждый тик. К счастью, существует служебная функция `BaseEntityBlock`, которая выполняет эту проверку:

@[code transcludeWith=:::3](@/reference/latest/src/main/java/com/example/docs/block/custom/CounterBlock.java)

`CounterBlockEntity::tick` - это ссылка на статический метод `tick`, который мы должны создать в классе `CounterBlockEntity`. Структурировать его таким образом не обязательно, но это хорошая практика, чтобы сохранить код чистым и организованным.

Допустим, мы хотим сделать так, чтобы счетчик увеличивался только раз в 10 тиков (2 раза в секунду). Мы можем сделать это, добавив поле `ticksSinceLast` в класс `CounterBlockEntity` и увеличивая его с каждым тиком:

@[code transcludeWith=:::5](@/reference/latest/src/main/java/com/example/docs/block/entity/custom/CounterBlockEntity.java)

Не забудьте сериализовать и десериализовать это поле!

Теперь мы можем использовать `ticksSinceLast`, чтобы проверить, можно ли увеличить счетчик в `incrementClicks`:

@[code transcludeWith=:::6](@/reference/latest/src/main/java/com/example/docs/block/entity/custom/CounterBlockEntity.java)

::: tip

Если блок-сущность не отображается, попробуйте проверить регистрационный код! Он должен передать в `BlockEntityType.Builder` блоки, которые действительны для этой сущности, иначе он выдаст предупреждение в консоли:

```log
[13:27:55] [Server thread/WARN] (Minecraft) Block entity example-mod:counter @ BlockPos{x=-29, y=125, z=18} state Block{example-mod:counter_block} invalid for ticking:
```

:::

<!---->
