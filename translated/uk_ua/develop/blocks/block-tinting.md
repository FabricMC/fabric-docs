---
title: Відтінки блока
description: Дізнайтеся, як динамічно відтінювати блок.
authors:
  - cassiancc
  - dicedpixels
---

Іноді вам може знадобитися, щоб зовнішній вигляд блоків оброблявся спеціально в грі. Наприклад, деякі блоки, такі як трава, отримують відтінок.

Розгляньмо, як ми можемо маніпулювати зовнішнім виглядом блока.

Для цього прикладу зареєструймо блок. Якщо ви не знайомі з цим процесом, спершу прочитайте про [реєстрацію блока](./first-block).

@[code lang=java transcludeWith=:::waxcap-tinting](@/reference/latest/src/main/java/com/example/docs/block/ModBlocks.java)

Обов’язково додайте:

- [Стан блока](./blockstates) у `/blockstates/waxcap.json`
- [Модель блока](./block-models) у `/models/block/waxcap.json`
- [Текстура](./first-block#models-and-textures) у `/textures/block/waxcap.png`

Якщо все правильно, ви зможете побачити блок у грі.

![Правильний вигляд блока](/assets/develop/transparency-and-tinting/block_appearance_1.png)

## Джерела відтінку блока {#block-tint-sources}

Попри те, що наш блок має кращий виглядає в грі, його текстура має відтінки сірого. Ми могли б динамічно застосовувати колірний відтінок, наприклад, як стандартне листя змінює колір залежно від біомів.

Fabric API надає `BlockColorRegistry` для реєстрації списку `BlockTintSource`, який ми будемо використовувати для динамічного фарбування блока.

Скористаймося цим API, щоб зареєструвати відтінок таким чином, що коли наш блок Waxcap розміщено на траві, він виглядатиме зеленим, інакше буде коричневим.

У вашому **ініціалізаторі клієнта** зареєструйте свій блок у `ColorProviderRegistry` разом із відповідною логікою.

@[code lang=java transcludeWith=:::color_provider](@/reference/latest/src/client/java/com/example/docs/appearance/ExampleModAppearanceClient.java)

Тепер блок буде тонований залежно від місця його розміщення.

![Блок із постачальником кольорів](/assets/develop/transparency-and-tinting/block_appearance_2.png)
