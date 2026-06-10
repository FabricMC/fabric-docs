---
title: Меню контейнерів
description: Посібник, що пояснює, як створити просте меню для блока-контейнера.
authors:
  - bluebear94
  - cassiancc
  - ChampionAsh5357
  - CelDaemon
  - Tenneb22
resources:
  https://docs.neoforged.net/docs/inventories/menus: Меню — Документація NeoForge
---

<!---->

:::info ПЕРЕДУМОВИ

Вам слід спочатку прочитати про [блоки-контейнери](./block-containers), щоб ознайомитися зі створенням блока-сутності-контейнера.

:::

Під час відкриття контейнера, наприклад скрині, для показу його вмісту потрібні дві речі:

- `Screen`, який обробляє рендер вмісту та тла на екрані.
- `Menu`, яке обробляє логіку натискання клавіш Shift і синхронізацію між сервером і клієнтом.

У цьому посібнику ми створимо скриню з ґрунту із контейнером 3x3, до якого можна отримати доступ, натиснувши ПКМ та відкривши екран.

## Створення блока {#creating-the-block}

По-перше, ми хочемо створити блок і блок-сутність; читайте більше в посібнику [блоків-контейнерів](./block-containers#creating-the-block).

<<< @/reference/latest/src/main/java/com/example/docs/block/custom/DirtChestBlock.java#block

<<< @/reference/latest/src/main/java/com/example/docs/block/entity/custom/DirtChestBlockEntity.java#be

На додаток до звичайних методів блока-сутності, нам потрібно перевизначити метод `stillValid`. Цей метод буде викликатися щоразу, щоб перевірити, чи потрібно примусово вивести гравця з меню.
Ми використаємо усталену реалізацію цього з `ContainerHelper`, яка перевіряє, чи наш блок-сутність усе ще існує та чи гравець знаходиться в межах діапазону взаємодії.

<<< @/reference/latest/src/main/java/com/example/docs/block/entity/custom/DirtChestBlockEntity.java#container_still_valid

Після впровадження нашого меню воно закриється автоматично, коли гравця відштовхнуть.

<VideoPlayer src="/assets/develop/blocks/menu_still_valid.webm">Меню контейнера закривається, коли гравець виходить за межі діапазону</VideoPlayer>

### Відкриття меню {#opening-the-screen}

Ми хочемо мати можливість якимось чином відкрити меню, тому ми впораємося з цим за допомогою методу `useWithoutItem`:

<<< @/reference/latest/src/main/java/com/example/docs/block/custom/DirtChestBlock.java#use

### Реалізація MenuProvider {#implementing-menuprovider}

Щоб додати функціональність меню, тепер нам потрібно реалізувати `MenuProvider` в блоці-сутності:

<<< @/reference/latest/src/main/java/com/example/docs/block/entity/custom/DirtChestBlockEntity.java#menu

Метод getDisplayName повертає назву блока, яку буде показано у верхній частині екрана.

## Створення меню {#creating-the-menu}

`createMenu` хоче, щоб ми повернули меню, але ми ще не створили його для нашого блока. Для цього ми створимо клас `DirtChestMenu`, який розширює `AbstractContainerMenu`:

<<< @/reference/latest/src/main/java/com/example/docs/menu/custom/DirtChestMenu.java#menu

Клієнтський конструктор викликається на клієнті, коли сервер хоче, щоб він відкрив меню. Він створює порожній контейнер, який потім автоматично синхронізується з фактичним контейнером на сервері.

Серверний конструктор викликається на сервері, і оскільки він знає вміст контейнера, він може безпосередньо передати його як аргумент.

`quickMoveStack` обробляє предмети меню, натискання з утриманим Shift. Цей приклад повторює поведінку стандартних меню, таких як скрині та роздавачів.

Потім нам потрібно зареєструвати меню в новому класі `ModMenuType`:

<<< @/reference/latest/src/main/java/com/example/docs/menu/ModMenuType.java#register_menu

Тепер ми можемо встановити значення, що повертається `createMenu` в блоці-сутності, щоб використовувати наше меню:

<<< @/reference/latest/src/main/java/com/example/docs/block/entity/custom/DirtChestBlockEntity.java#provider_implemented

::: info

Метод `createMenu` викликається лише на сервері, тому ми викликаємо конструктор на стороні сервера та передаємо `this` (блок-сутність) як параметр контейнера.

:::

## Створення екрана {#creating-the-screen}

Щоб фактично показувати вміст контейнера на клієнті, нам також потрібно створити екран для нашого меню.
Ми створимо новий клас, який розширює `AbstractContainerScreen`:

<<< @/reference/latest/src/client/java/com/example/docs/rendering/screens/inventory/DirtChestScreen.java#screen

Для тла цього екрана ми просто використовуємо стандартну текстуру екрана роздавача, тому що наша скриня з ґрунту використовує той самий макет слотів. Ви також можете надати власну текстуру для `CONTAINER_TEXTURE`.

Оскільки це екран для меню, нам також потрібно зареєструвати його на клієнті за допомогою методу `MenuScreens#register()`:

<<< @/reference/latest/src/client/java/com/example/docs/ExampleModScreens.java#register_screens

Після завантаження гри у вас має бути скриня з ґрунту, меню якої ви можете відкрити за допомогою ПКМ та зберігати там предмети.

![Меню скрині з ґрунту в грі](/assets/develop/blocks/container_menus_0.png)
