---
title: Меню контейнеров
description: Руководство по созданию простого меню для блока-контейнера.
authors:
  - bluebear94
  - cassiancc
  - ChampionAsh5357
  - CelDaemon
  - Tenneb22
resources:
  https://docs.neoforged.net/docs/inventories/menus: Меню — документация NeoForge
---

<!---->

:::info ТРЕБОВАНИЯ

Сначала прочитайте [Контейнеры блоков](./block-containers), чтобы ознакомиться с созданием блочной сущности-контейнера.

:::

При открытии контейнера, например сундука, для отображения его содержимого в основном нужны две вещи:

- `Screen`, который отвечает за отрисовку содержимого и фона на экране.
- `Menu`, который обрабатывает логику shift-клика и синхронизацию между сервером и клиентом.

В этом руководстве мы создадим сундук из земли с контейнером 3×3, который можно открыть правым кликом и открыть экран.

## Создание блока {#creating-the-block}

Сначала нам нужно создать блок и блочную сущность; подробнее см. в разделе [Контейнеры блоков](./block-containers#creating-the-block).

<<< @/reference/latest/src/main/java/com/example/docs/block/custom/DirtChestBlock.java#block

<<< @/reference/latest/src/main/java/com/example/docs/block/entity/custom/DirtChestBlockEntity.java#be

Помимо стандартных методов сущности блока, нам необходимо переопределить метод `stillValid`. Этот метод вызывается каждый тик для проверки того, нужно ли принудительно закрыть меню для игрока.
Мы будем использовать стандартную реализацию этого метода из класса `ContainerHelper`. Она проверяет, существует ли еще наша сущность блока и находится ли игрок в радиусе взаимодействия.

<<< @/reference/latest/src/main/java/com/example/docs/block/entity/custom/DirtChestBlockEntity.java#container_still_valid

После реализации нашего меню оно будет закрываться автоматически, когда игрока отталкивает от блока.

<VideoPlayer src="/assets/develop/blocks/menu_still_valid.webm">Меню контейнера закрывается, когда игрок выходит из радиуса взаимодействия.</VideoPlayer>

### Открытие меню {#opening-the-screen}

Мы хотим иметь возможность открывать меню, поэтому обработаем это в методе `useWithoutItem`:

<<< @/reference/latest/src/main/java/com/example/docs/block/custom/DirtChestBlock.java#use

### Реализация MenuProvider {#implementing-menuprovider}

Чтобы добавить функциональность меню, теперь нам нужно реализовать `MenuProvider` в блочной сущности:

<<< @/reference/latest/src/main/java/com/example/docs/block/entity/custom/DirtChestBlockEntity.java#menu

Метод `getDisplayName` возвращает название блока, которое будет отображаться в верхней части экрана.

## Создание меню {#creating-the-menu}

`createMenu` требует вернуть меню, но мы ещё не создали его для нашего блока. Для этого мы создадим класс `DirtChestMenu`, который наследуется от `AbstractContainerMenu`:

<<< @/reference/latest/src/main/java/com/example/docs/menu/custom/DirtChestMenu.java#menu

Клиентский конструктор вызывается на клиенте, когда сервер запрашивает открытие меню. Он создаёт пустой контейнер, который затем автоматически синхронизируется с реальным контейнером на сервере.

Серверный конструктор вызывается на сервере, и поскольку он знает содержимое контейнера, он может напрямую передать его в качестве аргумента.

`quickMoveStack` обрабатывает перемещение предметов по Shift-клику внутри меню. Этот пример повторяет поведение ванильных меню, таких как сундуки и раздатчики.

Затем нам нужно зарегистрировать меню в новом классе `ModMenuType`:

<<< @/reference/latest/src/main/java/com/example/docs/menu/ModMenuType.java#register_menu

Теперь мы можем задать возвращаемое значение `createMenu` в блочной сущности, чтобы использовать наше меню:

<<< @/reference/latest/src/main/java/com/example/docs/block/entity/custom/DirtChestBlockEntity.java#provider_implemented

::: info

Метод `createMenu` вызывается только на сервере, поэтому мы используем серверный конструктор и передаём в него `this` (блочную сущность) как параметр контейнера.

:::

## Создание экрана {#creating-the-screen}

Чтобы действительно отобразить содержимое контейнера на клиенте, нам также нужно создать экран для нашего меню.
Создадим новый класс, который наследуется от `AbstractContainerScreen`:

<<< @/reference/latest/src/client/java/com/example/docs/rendering/screens/inventory/DirtChestScreen.java#screen

В качестве фона этого экрана мы используем стандартную текстуру экрана раздатчика, так как наш земляной сундук использует такую же раскладку слотов. В качестве альтернативы вы можете использовать собственную текстуру для `CONTAINER_TEXTURE`.

Поскольку это экран для меню, нам также нужно зарегистрировать его на клиенте с помощью метода `MenuScreens#register()`:

<<< @/reference/latest/src/client/java/com/example/docs/ExampleModScreens.java#register_screens

После запуска игры у вас должен появиться земляной сундук, по которому можно кликнуть правой кнопкой, чтобы открыть меню и хранить в нём предметы.

![Меню земляного сундука в игре](/assets/develop/blocks/container_menus_0.png)
