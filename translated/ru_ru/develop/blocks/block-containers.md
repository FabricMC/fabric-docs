---
title: Контейнеры блоков
description: Узнайте, как добавлять контейнеры к вашим сущностям блоков.
authors:
  - natri0
resources:
  https://docs.neoforged.net/docs/inventories/container/: Контейнеры — документация NeoForge
---

Хорошей практикой при создании блоков, которые могут хранить предметы, например, сундуки и печи, является реализация `Container`. Это делает возможным, например, взаимодействие с блоком с помощью воронок.

В этом руководстве мы создадим блок, который будет использовать свой контейнер для дублирования любых помещённых в него предметов.

## Создание блока {#creating-the-block}

Это должно быть знакомо читателю, если он следовал руководствам [Создание вашего первого блока](../blocks/first-block) и [Блок-сущности](../blocks/block-entities). Мы создадим `DuplicatorBlock`, который наследует `BaseEntityBlock` и реализует `EntityBlock`.

<<< @/reference/latest/src/main/java/com/example/docs/block/custom/DuplicatorBlock.java#block

Затем нам нужно создать `DuplicatorBlockEntity`, который должен реализовывать интерфейс `Container`. Поскольку большинство контейнеров обычно работают одинаково, вы можете скопировать вспомогательный класс `ImplementedContainer`, который выполняет большую часть работы, оставляя нам лишь несколько методов для реализации.

:::details Показать `ImplementedContainer`

<<< @/reference/latest/src/main/java/com/example/docs/container/ImplementedContainer.java

:::

<<< @/reference/latest/src/main/java/com/example/docs/block/entity/custom/DuplicatorBlockEntity.java#be

Список `items` — это место, где хранится содержимое контейнера. Для этого блока мы установили размер контейнера равным 1 слоту для входа.

Не забудьте зарегистрировать блок и блок-сущность в соответствующих классах!

### Сохранение & Загрузка {#saving-loading}

Если мы хотим, чтобы содержимое сохранялось между перезагрузками игры, как у ванильной `BlockEntity`, нам нужно сохранять его в формате NBT. К счастью, Mojang предоставляет вспомогательный класс `ContainerHelper`, содержащий всю необходимую логику.

<<< @/reference/latest/src/main/java/com/example/docs/block/entity/custom/DuplicatorBlockEntity.java#save

## Взаимодействие с контейнером {#interacting-with-the-container}

Технически контейнер уже функционирует. Однако, чтобы вставлять предметы, в текущем виде необходимо использовать воронки. Давайте сделаем так, чтобы мы могли вставлять предметы, нажимая ПКМ по блоку.

Для этого нам нужно переопределить метод `useItemOn` в `DuplicatorBlock`:

<<< @/reference/latest/src/main/java/com/example/docs/block/custom/DuplicatorBlock.java#useon

Здесь, если игрок держит предмет и есть пустой слот, мы перемещаем предмет из руки игрока в контейнер блока и возвращаем `InteractionResult.SUCCESS`.

Теперь, когда вы нажимаете ПКМ по блоку с предметом, он исчезнет из вашей руки! Если выполнить команду `/data get block` на блоке, вы увидите предмет в поле `Items` в NBT.

![Блок-дупликатор и вывод команды /data get block, показывающий предмет в контейнере](/assets/develop/blocks/container_1.png)

### Дублирование предметов {#duplicating-items}

Теперь давайте сделаем так, чтобы блок дублировал стак, который вы в него поместили, но только по два предмета за раз. И давайте сделаем задержку в одну секунду, чтобы не заспамливать игрока предметами!

Для этого мы добавим функцию `tick` в `DuplicatorBlockEntity`, а также поле для хранения времени ожидания:

<<< @/reference/latest/src/main/java/com/example/docs/block/entity/custom/DuplicatorBlockEntity.java#tick

Теперь в `DuplicatorBlock` должен быть метод `getTicker`, который возвращает ссылку на `DuplicatorBlockEntity::tick`.

<VideoPlayer src="/assets/develop/blocks/container_2.mp4">Блок-дупликатор дублирует дубовое бревно</VideoPlayer>

## Мировые контейнеры {#worldly-containers}

По умолчанию вы можете вставлять и извлекать предметы из контейнера с любой стороны. Однако такое поведение не всегда желательно: например, печь принимает топливо только сбоку, а предметы — сверху.

Чтобы реализовать такое поведение, нам нужно реализовать интерфейс `WorldlyContainer` в `BlockEntity`. Этот интерфейс имеет три метода:

- `getSlotsForFace(Direction)` позволяет управлять тем, с какими слотами можно взаимодействовать с определённой стороны.
- `canPlaceItemThroughFace(int, ItemStack, Direction)` позволяет управлять тем, можно ли вставить предмет в слот с определённой стороны.
- `canTakeItemThroughFace(int, ItemStack, Direction)` позволяет управлять тем, можно ли извлечь предмет из слота с определённой стороны.

Давайте изменим `DuplicatorBlockEntity`, чтобы он принимал предметы только сверху:

<<< @/reference/latest/src/main/java/com/example/docs/block/entity/custom/DuplicatorBlockEntity.java#accept

`getSlotsForFace` возвращает массив _индексов_ слотов, с которыми можно взаимодействовать с указанной стороны. В данном случае у нас есть только один слот (`0`), поэтому мы возвращаем массив, содержащий только этот индекс.

Также нам следует изменить метод `useItemOn` в `DuplicatorBlock`, чтобы он действительно учитывал новое поведение:

<<< @/reference/latest/src/main/java/com/example/docs/block/custom/DuplicatorBlock.java#place

Теперь, если мы попробуем вставить предметы сбоку, а не сверху, это не сработает!

<VideoPlayer src="/assets/develop/blocks/container_3.webm">Дупликатор срабатывает только при взаимодействии с его верхней стороной</VideoPlayer>

## Меню {#menus}

Чтобы получить доступ к новому блоку-контейнеру через меню, как это делается с сундуком, обратитесь к руководству [Меню контейнеров](./container-menus).
