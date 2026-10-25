---
title: Внешний вид предметов
description: Узнайте, как динамически окрашивать предметы с помощью настраиваемых источников окрашивания.
authors:
  - dicedpixels
---

Вы можете управлять внешним видом предмета через его клиентскую часть (client item). [Список стандартных ванильных модификаций](https://minecraft.wiki/w/Items_model_definition#Items_model_types) доступен на Minecraft Wiki.

Среди них часто используется тип _Tint Sources_ (источники окрашивания), который позволяет изменять цвет предмета на основе предопределенных условий.

Существует лишь несколько [источников окрашивания](https://minecraft.wiki/w/Items_model_definition#Tint_sources_types), поэтому давайте разберём, как создать собственный.

В этом примере мы будем использовать предмет блока `ModBlocks.WAXCAP`, зарегистрированный в разделе [Тонирование блоков](../blocks/block-tinting).

Завершите создание предмета, добавив:

- [Клиентскую часть предмета](./first-item#creating-the-client-item) в `/items/waxcap.json`
- [Модель предмета](./item-models) в `/models/item/waxcap.json`
- [Тестуру](./first-item#adding-a-texture) в `/textures/item/waxcap.png`

Теперь у предмета должна появиться текстура.

![Зарегистрированный предмет](/assets/develop/item-appearance/item_tint_0.png)

Как вы можете видеть, мы используем текстуру в оттенках серого (grayscale). Давайте добавим цвет с помощью источника окрашивания (tint source).

## Источники окрашивания предметов {#item-tint-sources}

Давайте зарегистрируем свой источник окрашивания, чтобы покрасить наш предмет Waxcap: во время дождя предмет будет синим, а в остальное время — коричневым.

Сначала вам нужно определить источник окрашивания предмета (item tint source). Это делается путем реализации интерфейса `ItemTintSource` в классе или записи (record).

<<< @/reference/latest/src/client/java/com/example/docs/appearance/RainTintSource.java#tint_source_class

Поскольку это часть клиентского определения предмета, значения окрашивания можно изменить с помощью ресурс-пака. Поэтому вам нужно определить [MapCodec](../codecs#mapcodec), способный считывать ваше определение окрашивания. В данном случае источник окрашивания будет иметь целочисленное значение (`int`), описывающее цвет во время дождя. Мы можем использовать встроенный `ExtraCodecs.RGB_COLOR_CODEC` для сборки нашего кодека.

<<< @/reference/latest/src/client/java/com/example/docs/appearance/RainTintSource.java#map_codec

Затем мы можем вернуть этот кодек в методе `type()`.

<<< @/reference/latest/src/client/java/com/example/docs/appearance/RainTintSource.java#return_codec

Наконец, мы можем реализовать метод `calculate`, который будет определять итоговый цвет окрашивания. Значение `color` — это как раз тот цвет, который приходит из ресурс-пака.

<<< @/reference/latest/src/client/java/com/example/docs/appearance/RainTintSource.java#calculate

После этого нам нужно зарегистрировать наш источник окрашивания предмета. Это делается в **клиентском инициализаторе** с помощью `ID_MAPPER`, объявленного в `ItemTintSources`.

<<< @/reference/latest/src/client/java/com/example/docs/appearance/ExampleModAppearanceClient.java#register_item_tint_source

Как только это будет готово, мы сможем использовать наш источник окрашивания в клиентском определении предмета.

<<< @/reference/latest/src/main/generated/assets/example-mod/items/waxcap.json

Теперь вы можете наблюдать за изменением цвета предмета прямо в игре.

![Окрашивание предмета в игре](/assets/develop/item-appearance/item_tint_1.png)
