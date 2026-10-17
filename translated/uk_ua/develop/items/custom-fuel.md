---
title: Власне паливо
description: Дізнайтеся, як створити власні предмети палива.
authors:
  - NotNightSky
  - its-miroma
---

Паливо — це предмети, які можна використовувати в печі для плавки руди та приготування їжі. Погляньмо, як можна створити власне паливо.

## Створення предмета {#creating-the-item}

Створімо предмет палива під назвою «Quark-Gloun Plasma». Ми почнемо зі [створення предмета](./first-item), як і завжди:

<<< @/reference/latest/src/main/java/com/example/docs/item/ModItemIds.java#quark_gluon_plasma_resource

<<< @/reference/latest/src/main/java/com/example/docs/item/ModItems.java#quark_gluon_plasma

Щоб зробити його паливом, ми використаємо подію `FuelValueEvents.BUILD` з Fabric API Реєстрів Умісту:

<<< @/reference/latest/src/main/java/com/example/docs/item/ModItems.java#fuel_item

І звісно ж, не забудьте додати текстуру, переклад, у вкладку режиму творчости й усе таке. Ось приклад текстури:

<DownloadEntry visualURL="/assets/develop/items/quark_gluon_plasma_big.png" downloadURL="/assets/develop/items/quark_gluon_plasma.png">Текстура</DownloadEntry>

Ось як це виглядає при використанні як паливо в печі:

<VideoPlayer src="/assets/develop/items/fuel_in_furnace.webm">Використання Quark-Gluon Plasma в печі</VideoPlayer>
