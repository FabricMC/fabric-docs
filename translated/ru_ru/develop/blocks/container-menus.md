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

@[code transcludeWith=:::block](@/reference/latest/src/main/java/com/example/docs/block/custom/DirtChestBlock.java)

@[code transcludeWith=:::be](@/reference/latest/src/main/java/com/example/docs/block/entity/custom/DirtChestBlockEntity.java)

### Открытие меню {#opening-the-screen}

Мы хотим иметь возможность открывать меню, поэтому обработаем это в методе `useWithoutItem`:

@[code transcludeWith=:::use](@/reference/latest/src/main/java/com/example/docs/block/custom/DirtChestBlock.java)

### Реализация MenuProvider {#implementing-menuprovider}

Чтобы добавить функциональность меню, теперь нам нужно реализовать `MenuProvider` в блочной сущности:

@[code transcludeWith=:::menu](@/reference/latest/src/main/java/com/example/docs/block/entity/custom/DirtChestBlockEntity.java)

Метод `getDisplayName` возвращает название блока, которое будет отображаться в верхней части экрана.

## Создание меню {#creating-the-menu}

`createMenu` требует вернуть меню, но мы ещё не создали его для нашего блока. Для этого мы создадим класс `DirtChestMenu`, который наследуется от `AbstractContainerMenu`:

@[code transcludeWith=:::menu](@/reference/latest/src/main/java/com/example/docs/menu/custom/DirtChestMenu.java)

Клиентский конструктор вызывается на клиенте, когда сервер запрашивает открытие меню. Он создаёт пустой контейнер, который затем автоматически синхронизируется с реальным контейнером на сервере.

Серверный конструктор вызывается на сервере, и поскольку он знает содержимое контейнера, он может напрямую передать его в качестве аргумента.

`quickMoveStack` обрабатывает перемещение предметов по Shift-клику внутри меню. Этот пример повторяет поведение ванильных меню, таких как сундуки и раздатчики.

Затем нам нужно зарегистрировать меню в новом классе `ModMenuType`:

@[code transcludeWith=:::registerMenu](@/reference/latest/src/main/java/com/example/docs/menu/ModMenuType.java)

Теперь мы можем задать возвращаемое значение `createMenu` в блочной сущности, чтобы использовать наше меню:

@[code transcludeWith=:::providerImplemented](@/reference/latest/src/main/java/com/example/docs/block/entity/custom/DirtChestBlockEntity.java)

::: info

Метод `createMenu` вызывается только на сервере, поэтому мы используем серверный конструктор и передаём в него `this` (блочную сущность) как параметр контейнера.

:::

## Создание экрана {#creating-the-screen}

Чтобы действительно отобразить содержимое контейнера на клиенте, нам также нужно создать экран для нашего меню.
Создадим новый класс, который наследуется от `AbstractContainerScreen`:

@[code transcludeWith=:::screen](@/reference/latest/src/client/java/com/example/docs/rendering/screens/inventory/DirtChestScreen.java)

В качестве фона этого экрана мы используем стандартную текстуру экрана раздатчика, так как наш земляной сундук использует такую же раскладку слотов. В качестве альтернативы вы можете использовать собственную текстуру для `CONTAINER_TEXTURE`.

Поскольку это экран для меню, нам также нужно зарегистрировать его на клиенте с помощью метода `MenuScreens#register()`:

@[code transcludeWith=:::registerScreens](@/reference/latest/src/client/java/com/example/docs/ExampleModScreens.java)

После запуска игры у вас должен появиться земляной сундук, по которому можно кликнуть правой кнопкой, чтобы открыть меню и хранить в нём предметы.

![Меню земляного сундука в игре](/assets/develop/blocks/container_menus_0.png)
