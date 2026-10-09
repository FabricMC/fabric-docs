---
title: Власні снаряди
description: Дізнайтеся, як додати власні снаряди.
authors:
  - ayutac
  - cassiancc
  - ChampionAsh5357
  - dicedpixels
  - Earthcomputer
  - ekulxam
  - haykam
  - kanpov
  - NetUserGet
  - onlyspxctre
  - patrickmsm
  - tianjun
  - upcraftlp
resources:
  https://minecraft.wiki/w/Projectile: Снаряди — Вікі Minecraft
  https://docs.neoforged.net/docs/entities/#projectiles: Снаряди — Документація NeoForge
---

Снаряди — це сутності, що можуть бути випущені гравцем або іншою сутністю. У цьому посібнику, ми розглянемо реалізацію простого снаряда, як-от сніжка.

Ми назвемо наш снаряд Hot Tater. Це буде картопля, яка підпалює блок або сутність, в яку влучає.

:::info ПЕРЕДУМОВИ

Для створення снаряда потрібно зареєструвати як предмет, так і сутність, тому ми радимо ознайомитися з посібниками створення вашого першого [предмета](../items/first-item) та [сутності](./first-entity).

:::

## Створення сутності снаряда {#creating-the-projectile-entity}

Створімо `HotTaterEntity`, який розширює `ThrowableItemProjectile`. Цей клас повинен бути у `main` початковому наборі.

Клас `ThrowableItemProjectile` оброблює фізику та форму предмета снаряда.

<<< @/reference/latest/src/main/java/com/example/docs/projectile/HotTaterEntity.java#entity

Тут відбувається чимало всього. Розгляньмо важливий розділ коду.

### Конструктори {#constructors}

Ми визначимо 3 конструктори. Вони використовуються реєстрацією сутності, породженням снаряда та його перетворенням.

<<< @/reference/latest/src/main/java/com/example/docs/projectile/HotTaterEntity.java#constructors

### Перевизначення `getDefaultItem()` {#override-get-default-item}

Визначає форму предмета цього снаряда.

<<< @/reference/latest/src/main/java/com/example/docs/projectile/HotTaterEntity.java#default_item

:::warning ВАЖЛИВО

Ваше IDE може повідомляти, що не може розпізнати цей предмет: ми створимо його згодом, у розділі [реєстрації](#registration).

:::

### Перевизначення `onHitBlock()` {#override-on-hit-block}

Визначає поведінку при влучанні снаряда в блок. Ми перевіряємо де снаряд улучив та встановлюємо сторону влучання блока, в який улучили. Ця логіка обробляється на сервері.

<<< @/reference/latest/src/main/java/com/example/docs/projectile/HotTaterEntity.java#on_hit_block

### Перевизначення `onHitEntity()` {#override-on-hit-entity}

Визначає поведінку при влучанні снаряда в сутність. Ми встановимо, щоб сутність, в яку влучили, горіла протягом 5 секунд.

<<< @/reference/latest/src/main/java/com/example/docs/projectile/HotTaterEntity.java#on_hit_entity

### Перевизначення `onHit()` {#override-on-hit}

Визначає поведінку, коли снаряд улучає будь-куди, чи то блоки, чи то сутність. Ми використаємо це для зникання снаряда; без цього, снаряд просто буде існувати.

<<< @/reference/latest/src/main/java/com/example/docs/projectile/HotTaterEntity.java#on_hit

## Створення предмета {#creating-the-item}

Ми зареєструємо простий предмет. Далі на потрібно реалізувати логіку кидання, наш клас `HotTaterItem` розширюватиме `Item` та реалізуватиме `ProjectileItem`. Цей клас повинен бути у `main` початковому наборі.

<<< @/reference/latest/src/main/java/com/example/docs/projectile/HotTaterItem.java#item

Це стандартна реалізація предмета, з деякими особливими методами для `ProjectileItem`. Це виглядатиме якось так:

### Перевизначення `asProjectile()` {#override-as-projectile}

Цей метод перетворює предмет у форму сутності.

<<< @/reference/latest/src/main/java/com/example/docs/projectile/HotTaterItem.java#as_projectile

### Перевизначення `use()` {#override-use}

Визначає дію, яка стається при використанні предмета. У нашому випадку ми викликаємо `Projectile.spawnProjectileFromRotation()`, метод для породження снаряда.

Окрім стандартних параметрів (рівень, стіс предмета і гравець), цей допоміжний метод приймає три додаткові плавучі значення:

- `yOffset`: зміщення для нахилу (обертання навколо осі X, вгору або вниз), у градусах. Від’ємні значення спрямовують кут початкової швидкості вгору.
- `pow`: множник швидкості руху снаряда.
- `uncertainty`: неточність снаряда. 0 значить нульовий розкид снаряда. Зі збільшенням цього значення снаряди розкидатимуться сильніше, навіть якщо їх запускати з однаковими початковими положенням і орієнтацією.

Подробиці можна знайти в [статті на Вікі Minecraft про снаряди](https://minecraft.wiki/w/Projectile#Initial_conditions).

:::details Чому цей параметр називається `yOffset`?

Ми теж не знаємо, шановний читачу. Попри назву, [зміщення застосовується до кута нахилу](https://mcsrc.dev/2/26.2/net/minecraft/world/entity/projectile/Projectile#L156) — тобто до повороту _швидкості_ навколо осі X:

```java
float yd = -Mth.sin((xRot + yOffset) * (float) (Math.PI / 180.0));
```

Наприклад, коли стандартна гра використовує значення [`yOffset` з `-20.0F`, для вибухових зіллів](https://mcsrc.dev/2/26.2/net/minecraft/world/item/ThrowablePotionItem#L28), це змінює кут нахилу початкової швидкості з `source.getXRot()` на `source.getXRot() - 20.0F` (на 20 градусів угору, у бік неба).

Можливо, влучнішою назвою була б `xRotOffset`.

:::

Нарешті, ми нараховуємо показник `ITEM_USED`, витрачаємо один предмет зі стосу та позначаємо взаємодію як успішну.

<<< @/reference/latest/src/main/java/com/example/docs/projectile/HotTaterItem.java#use

## Реєстрація {#registration}

Зареєструйте предмет, як ми це зробили в посібнику [створення вашого першого предмета](../items/first-item#registering-an-item). Для початку, визначимо ключ предмета у `ModItemIds`:

<<< @/reference/latest/src/main/java/com/example/docs/item/ModItemIds.java#hot_tater

Потім зареєструймо предмет у `ModItems` разом з іншими:

<<< @/reference/latest/src/main/java/com/example/docs/item/ModItems.java#hot_tater

Не забудьте додати [модель](../items/first-item#adding-a-model), [текстуру](../items/first-item#adding-a-texture), [клієнтський предмет](../items/first-item#creating-the-client-item) та [назву](../items/first-item#naming-the-item), використовуючи ідентифікатор `hot_tater`. Вам також слід [додати предмет до вкладки режиму творчости](../items/first-item#adding-the-item-to-a-creative-tab). Ось приклад текстури:

<DownloadEntry visualURL="/assets/develop/projectiles/hot_tater_preview.png" downloadURL="/assets/develop/projectiles/hot_tater.png">Текстура</DownloadEntry>

Також зареєструйте сутність — як ми це робили в посібнику [створення вашої першої сутності](./first-entity#preparing-your-first-entity) — додавши її як статичне поле в `ModEntityTypes`. Оскільки сутності та предмети зберігаються в окремих реєстрах, тип сутності просто використовує той самий шлях, що й предмет — як-от стандартна `snowball`:

<<< @/reference/latest/src/main/java/com/example/docs/entity/ModEntityTypes.java#hot_tater

Нарешті, використаймо стандартний `ThrownItemRenderer` в ініціалізаторі клієнта:

<<< @/reference/latest/src/client/java/com/example/docs/projectile/ExampleModProjectileClient.java#renderer

І готово!

<VideoPlayer src="/assets/develop/projectiles/hot-tater.mp4">Hot Tater улучає в селянина та підпалює</VideoPlayer>
