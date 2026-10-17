---
title: Власні компостовані предмети
description: Дізнайтеся, як створити власні компостовані предмети.
authors:
  - NotNightSky
  - its-miroma
---

Компостовані предмети — це ті, що перетворюються на кісткове борошно, коли їх поміщають у компостер. Погляньмо, як можна створити власний компостований предмет.

## Створення предмета {#creating-the-item}

Створімо компостований предмет під назвою «Bone Marrow». Ми почнемо зі [створення предмета](./first-item), як і завжди:

<<< @/reference/latest/src/main/java/com/example/docs/item/ModItemIds.java#bone_marrow_resource

<<< @/reference/latest/src/main/java/com/example/docs/item/ModItems.java#bone_marrow

Щоб зробити його компостованим, ми додамо реєстр `CompostableRegistry.INSTANCE` з Fabric API Реєстрації:

<<< @/reference/latest/src/main/java/com/example/docs/item/ModItems.java#compostable_item

І звісно ж, не забудьте додати текстуру, переклад, у вкладку режиму творчости й усе таке. Ось приклад текстури:

<DownloadEntry visualURL="/assets/develop/items/bone_marrow_big.png" downloadURL="/assets/develop/items/bone_marrow.png">Текстура</DownloadEntry>

Ось як це виглядає під час використання в компостері:

<VideoPlayer src="/assets/develop/items/using_bone_marrow.webm">Використання Bone Marrow в компостері</VideoPlayer>
