---
title: Зовнішній вигляд предметів
description: Дізнайтеся, як динамічно тонувати предмети за допомогою спеціальних джерел відтінків.
authors:
  - dicedpixels
---

Ви можете маніпулювати зовнішнім виглядом предмета через його клієнтський предмет. У Minecraft Wiki є [список стандартних модифікацій](https://minecraft.wiki/w/Items_model_definition#Items_model_types).

З них типово використовуваним типом є _джерела відтінків_, який дозволяє змінювати колір елемента на основі попередньо визначених умов.

Існує лише кілька [попередньо визначених джерел відтінків](https://minecraft.wiki/w/Items_model_definition#Tint_sources_types), тому подивімося, як створити власні.

Для цього прикладу зареєструймо предмет. Якщо ви не знайомі з цим процесом, спершу прочитайте про [реєстрацію предмета](./first-item).

@[code lang=java transcludeWith=:::item](@/reference/latest/src/main/java/com/example/docs/appearance/ExampleModAppearance.java)

Обов’язково додайте:

- [Клієнтський предмет](./first-item#creating-the-client-item) у `/items/waxcap.json`
- [Модель предмета](./item-models) у `/models/item/waxcap.json`
- [Текстура](./first-item#adding-a-texture) у `/textures/item/waxcap.png`

Предмет має з’явитися в грі.

![Реєстрація предмета](/assets/develop/item-appearance/item_tint_0.png)

Як бачите, ми використовуємо текстуру градацій сірого. Додаймо трохи кольору за допомогою джерела тону.

## Джерела відтінків предметів {#item-tint-sources}

Зареєструймо спеціальне джерело відтінку, щоб розфарбувати наш предмет Waxcap, щоб під час дощу предмет виглядав синім, інакше він виглядав коричневим.

Спершу вам потрібно буде визначити власне джерело відтінку предмета. Це робиться шляхом реалізації інтерфейсу ItemTintSource для класу або запису.

@[code lang=java transcludeWith=:::tint_source](@/reference/latest/src/client/java/com/example/docs/appearance/RainTintSource.java)

Оскільки це частина визначення клієнтського предмета, значення відтінку можна змінити за допомогою пакета ресурсів. Тож вам потрібно визначити [мапу кодеків(../codecs#mapcodec), який здатний читати ваше визначення відтінку. У цьому випадку джерело відтінку матиме значення `int`, що описує колір, який він матиме під час дощу. Ми можемо використовувати вбудований `ExtraCodecs.RGB_COLOR_CODEC`, щоб створити наш кодек.

@[code lang=java transclude={17-20}](@/reference/latest/src/client/java/com/example/docs/appearance/RainTintSource.java)

Потім ми можемо повернути цей кодек у `type()`.

@[code lang=java transclude={35-38}](@/reference/latest/src/client/java/com/example/docs/appearance/RainTintSource.java)

Нарешті, ми можемо надати реалізацію для `calculate`, яка вирішить, яким буде колір відтінку. Значення `color` — це значення, яке надходить із пакета ресурсів.

@[code lang=java transclude={26-33}](@/reference/latest/src/client/java/com/example/docs/appearance/RainTintSource.java)

Потім нам потрібно зареєструвати наше джерело відтінку предмета. Це робиться в **ініціалізаторі клієнта** за допомогою `ID_MAPPER`, оголошеного в `ItemTintSources`.

@[code lang=java transcludeWith=:::item_tint_source](@/reference/latest/src/client/java/com/example/docs/appearance/ExampleModAppearanceClient.java)

Коли це буде зроблено, ми можемо використовувати наше джерело відтінку предмета у визначенні клієнтського предмета.

@[code lang=json transclude](@/reference/latest/src/main/generated/assets/example-mod/items/waxcap.json)

Ви можете спостерігати за зміною кольору предмета в грі.

![Відтінок предмета в грі](/assets/develop/item-appearance/item_tint_1.png)
