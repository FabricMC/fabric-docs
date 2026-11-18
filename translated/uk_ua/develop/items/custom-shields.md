---
title: Власні щити
description: Дізнайтеся, як створити власні щити та налаштовувати їхні властивості.
authors:
  - cassiancc
  - ChampionAsh5357
resources:
  https://mcsrc.dev/1/26.2/net/minecraft/client/renderer/special/ShieldSpecialRenderer: Рендерер стандартного щита
  https://minecraft.wiki/w/Data_component_format/blocks_attacks: Компонент даних блокування атак
---

<!--  -->

:::info ПЕРЕДУМОВА

Спершу потрібно зрозуміти, як [створити інструмент](./custom-tools). У цьому посібнику також йдеться про генерацію даних для [рецептів](../data-generation/recipes), [моделей](../data-generation/item-models) і [теґів](../data-generation/tags) предметів.

:::

Щити здатні захистити вас від атак. Щоб додати до гри новий щит, вам знадобляться `Item`, дві моделі предмета, клієнтський предмет, рецепти, теґи предмета та спеціальний рендерер.

## Створення предмета {#item}

:::info ПЕРЕДУМОВА

Подробиці див. у документації щодо [створення предметів](./first-item).

:::

Для цього прикладу ми використаємо той самий теґ предмета для лагодження, що й на сторінках власних [обладунків](./custom-armor) та [інструментів](./custom-tools). Ми визначаємо посилання на теґ наступним чином:

<<< @/reference/latest/src/main/java/com/example/docs/item/armor/GuiditeArmorMaterial.java#repair_tag

Потім ми створюємо id предмета та реєструємо його із такими компонентами.

- [**Візерунок стяга**](https://minecraft.wiki/w/Data_component_format/banner_patterns): Створює предмет із порожнім набором візерунків для стяга.
- [**Можна полагодити**](https://minecraft.wiki/w/Data_component_format/repairable): Створює предмет, який можна полагодити за допомогою вказаного теґу предмета.
- [**Можна спорядити / Не можна замінити**](https://minecraft.wiki/w/Data_component_format/equippable): В інтерфейсі натискання на предмет із затиснутою клавішею Shift спорядить його в іншу руку. У світі гри натисканні ПКМ з цим предметом у руках не призведе до його спорядження.
- [**Блокування атак**](https://minecraft.wiki/w/Data_component_format/blocks_attacks): Створює предмет, що блокує атаки. Цей приклад використовує значення стандартного щита.
  - Це — _компонент із відкладеним завантаженням_, це означає, що він завантажується вже після завантаження світу, що дозволяє йому посилатися на об'єкти пакетів даних, як-от теґи.
- [**Звук ламання**](https://minecraft.wiki/w/Data_component_format/break_sound): Коли предмет ламається, він відтворює спеціальний звук.

<<< @/reference/latest/src/main/java/com/example/docs/item/ModItemIds.java#shield

<<< @/reference/latest/src/main/java/com/example/docs/item/ModItems.java#shield

Не забудьте додати його до вкладки творчості, якщо ви хочете отримати доступ до нього з інвентарю творчості!

<<< @/reference/latest/src/main/java/com/example/docs/item/ModItems.java#add_guidite_shield_to_create_tab

## Створення спеціального рендерера {#special-renderer}

Для рендера щита ми використовуватимемо спеціальний рендерер, а не звичайну модель предмета.

Почнемо зі створення розташування шару моделі, яке вказує на розташування моделі щита:

<<< @/reference/latest/src/client/java/com/example/docs/item/shield/GuiditeShieldLayers.java#layer

Потім зареєструємо шар у вашому ініціалізаторі клієнта:

<<< @/reference/latest/src/client/java/com/example/docs/ExampleModClient.java#shield_layer

Далі створимо спеціальний рендерер для цього предмета. Ця реалізація базується на стандартному класі [`ShieldSpecialRenderer`](https://mcsrc.dev/1/26.2/net/minecraft/client/renderer/special/ShieldSpecialRenderer), але зі змінами, що дозволяють використовувати власні спрайти з клієнтського предмета. Ми передамо ці спрайти рендереру в наступному розділі.

Рендерер складний, тому ми розберемо його по частинах.

### Конструктор {#constructor}

Конструктор рендерера приймає чотири параметри:

- `SpriteGetter` інтерфейс, що надає спрайти з `Identifier`.
- Модель, яку ми будемо використовувати в нашому випадку це `ShieldModel`.
- Це звична біла текстура (надана в клієнтському предметі), надана як `SpriteId`.
- Текстура, що використовується за відсутності барвників або візерунків стяга; надається як `SpriteId`.

Конструктор зберігає всі чотири параметри як поля, щоб ми могли використовувати їх згодом.

<<< @/reference/latest/src/client/java/com/example/docs/item/shield/GuiditeShieldSpecialRenderer.java#renderer

### Витягування {#extraction}

Під час витягування даних для рендера нам потрібна незмінна копія даних, що містить лише інформацію, необхідну для рендера предмета. Ми можемо отримати це з `ItemStack`, перетворивши його `DataComponentMap` на незмінну версію в методі `extractArgument`:

<<< @/reference/latest/src/client/java/com/example/docs/item/shield/GuiditeShieldSpecialRenderer.java#extract_argument

### Межі {#extents}

Ми також визначимо межі моделі, встановивши її обмежувальний куб, який використовується для рендера та анімації моделі:

<<< @/reference/latest/src/client/java/com/example/docs/item/shield/GuiditeShieldSpecialRenderer.java#extents

### Подання {#submission}

Процес подання відповідає за логіку того, _що саме_ має бути від рендерено. Логіка рендера щита виконує такі дії:

1. Отримання візерунки стяга щита та зберігання в `patterns`. Якщо щит не має візерунків, то він отримує `BannerPatternLayers.EMPTY`.
2. Отримання кольору барвника щита та зберігання його в `baseColor`. Якщо щит не має кольору, значення змінної отримує `null`.
3. Якщо щит має візерунки або був забарвлений, використовується текстура `base`. Якщо ні, використовується `base_nopattern`.
4. Надсилання моделі щита для рендера, використовуючи надані параметри та текстуру.
5. Якщо на щиті є візерунки, надсилання їх.
6. Якщо щит зачарований, показує ефект блиску.

<<< @/reference/latest/src/client/java/com/example/docs/item/shield/GuiditeShieldSpecialRenderer.java#submit

### Необроблені моделі {#unbaked-models}

Вам також знадобиться [необроблена модель](https://docs.neoforged.net/docs/resources/client/models/modelsystem), яка використовується для посилання на рендерер моделі та надання їй спрайтів.

<<< @/reference/latest/src/client/java/com/example/docs/item/shield/GuiditeShieldSpecialRenderer.java#unbaked

## Створення моделі {#model}

:::info ПЕРЕДУМОВА

Щоб отримати додаткові відомості, перегляньте документацію щодо створення [моделей предметів](../data-generation/item-models).

:::

Ми створимо дві моделі предмета — одну для звичайного стану, а іншу для стану блокування щитом, — а також умовний клієнтський предмет для щита з нашими власними текстурами:

:::: tabs

== Початковий код

::: info

Ці моделі генеровані даними. Щоб отримати додаткові відомості, перегляньте документацію щодо створення [моделей предметів](../data-generation/item-models).

:::

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModModelProvider.java#shield

== Клієнтський предмет

`generated/assets/example-mod/items/guidite_shield.json`

<<< @/reference/latest/src/main/generated/assets/example-mod/items/guidite_shield.json

== Моделі предмета

`generated/assets/example-mod/models/item/guidite_shield.json`

<<< @/reference/latest/src/main/generated/assets/example-mod/models/item/guidite_shield.json

`generated/assets/example-mod/models/item/guidite_shield_blocking.json`

<<< @/reference/latest/src/main/generated/assets/example-mod/models/item/guidite_shield_blocking.json

== Текстури

<DownloadEntry visualURL="/assets/develop/items/guidite_shield_base_hd.png" downloadURL="/assets/develop/items/guidite_shield_base.png">Звична текстура Guidite Shield</DownloadEntry>

<DownloadEntry visualURL="/assets/develop/items/guidite_shield_base_nopattern_hd.png" downloadURL="/assets/develop/items/guidite_shield_base_nopattern.png">Звична текстура Guidite Shield без стяга</DownloadEntry>

::::

## Створення рецепта оздоблення щита {#recipe}

:::info ПЕРЕДУМОВА

Подробиці див. у документації щодо генерації [рецептів](../data-generation/recipes).

:::

У режимі виживання отримати наш щит можна двома способами: або створити звичайний щит, або оздобити його візерунками стяга.

Рецепт майстрування звичного щита може бути яким завгодно. З іншого боку, рецепт оздобленого щита можна створити в постачальнику рецептів таким чином:

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModRecipeProvider.java#shield_decoration

Визначивши цей рецепт, ви тепер можете наносити візерунки стяга на свій щит:

![Застосування візерунка стяга в сітці майстрування](/assets/develop/items/shield_banner_example.png)

## Додання предмета щита до теґу {#tags}

:::info ПЕРЕДУМОВА

Щоб отримати додаткові відомості, перегляньте документацію щодо створення [теґів предмета](../data-generation/tags).

:::

Вам також слід розмістити свій щит у відповідних теґах предметів:

- `ItemTags.DURABILITY_ENCHANTABLE`, щоб дозволити йому бути зачарованим на незламність та лагодження,
- `ConventionalItemTags.SHIELD_TOOLS` — теґ, який розробники модів можуть використовувати для реалізації специфічної для щитів поведінки, наприклад, унікальних зачарувань.

У вашому постачальнику теґів предмета додайте такі рядки до `addTags`:

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModItemTagProvider.java#shield_tags

Це майже все! Якщо ви зайдете в гру, то маєте побачити свій щит на вкладці «Бойове приладдя» у меню інвентарю творчості.

![Кінцевий щит у грі(/assets/develop/items/shield_use.png)
