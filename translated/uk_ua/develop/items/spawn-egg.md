---
title: Яйця виклику
description: Прочитайте як зареєструвати предмет яйця виклику.
authors:
  - cassiancc
  - Fellteros
  - skycatminepokie
  - VatinMc
---

<!---->

:::info ПЕРЕДУМОВИ

Ви повинні спочатку зрозуміти, [як створити предмет](./first-item), який потім можна перетворити на яйце виклику.

:::

Яйця виклику — це спеціальні предмети, які при використанні породжують відповідного моба. Ви можете зареєструвати його за допомогою методу `register` з вашого [класу предмета](./first-item#preparing-your-items-class), передавши йому `SpawnEggItem::new`.

@[code transcludeWith=:::spawn_egg](@/reference/latest/src/main/java/com/example/docs/item/ModItems.java)

![Предмет яйця виклику без текстури](/assets/develop/items/spawn_egg_1.png)

Є ще кілька речей, які потрібно зробити, перш ніж він буде готовий: ви повинні додати текстуру, модель предмета, клієнтський предмет, назву та додати яйце виклику до відповідної вкладки творчості.

## Додання текстури {#adding-a-texture}

Створіть текстуру предмета розміром 16x16 у каталозі `assets/example-mod/textures/item` з такою самою назвою файлу, як ID предмета: `custom_spawn_egg.png`. Приклад текстури наведено нижче.

<DownloadEntry visualURL="/assets/develop/items/spawn_egg.png" downloadURL="/assets/develop/items/spawn_egg_small.png">Текстура</DownloadEntry>

## Додання моделі {#adding-a-model}

Створіть модель предмета в каталозі `assets/example-mod/models/item` з тим самим ім’ям файлу, що й ID предмета: `custom_spawn_egg.json`.

@[code](@/reference/latest/src/main/generated/assets/example-mod/models/item/custom_spawn_egg.json)

## Створення клієнтського предмета {#creating-the-client-item}

Створіть JSON клієнтського предмета в каталозі `assets/example-mod/items` з тим самим назвою файлу, що й ID моделі предмета: `custom_spawn_egg.json`.

@[code](@/reference/latest/src/main/generated/assets/example-mod/items/custom_spawn_egg.json)

![Предмет яйця виклику з клієнтського предмета](/assets/develop/items/spawn_egg_2.png)

## Назва яйця виклику {#naming-the-spawn-egg}

Щоб назвати яйце виклику, ключу перекладу `item.example-mod.custom_spawn_egg` має бути присвоєно значення. Цей процес подібний до [іменування предмета](./first-item#naming-the-item).

Створіть або відредагуйте файл JSON за адресою: `src/main/resources/assets/example-mod/lang/en_us.json` (`uk_ua.json` для української) і введіть ключ перекладу та його значення:

```json
{
  "item.example-mod.custom_spawn_egg": "Custom Spawn Egg"
}
```

## Додання до вкладки творчості {#adding-to-a-creative-mode-tab}

Яйце виклику додається до `CreativeModeTab` яйця породження в методі `initialize()` [класу предмета](./first-item#preparing-your-items-class).

@[code transcludeWith=:::spawn_egg_creative_tab](@/reference/latest/src/main/java/com/example/docs/item/ModItems.java)

![Предмет яйця виклику з назвою та у вкладці творчості](/assets/develop/items/spawn_egg_3.png)

Див. [додавання предмета до вкладки режиму творчості](./first-item#adding-the-item-to-a-creative-tab), щоб отримати докладнішу інформацію.
