---
title: Тонирование блоков
description: Узнайте, как динамически тонировать блок.
authors:
  - cassiancc
  - dicedpixels
---

Иногда может потребоваться, чтобы внешний вид блоков обрабатывался в игре особым образом. Например, к некоторым блокам, таким как трава, применяется тонировка.

Давайте посмотрим, как можно изменить внешний вид блока.

Для этого примера давайте зарегистрируем блок. Если вы не знакомы с этим процессом, сначала прочитайте о [регистрации блока](./first-block).

@[code lang=java transcludeWith=:::waxcap-tinting](@/reference/latest/src/main/java/com/example/docs/block/ModBlocks.java)

Обязательно добавьте:

- [Состояние блока](./blockstates) в `/blockstates/waxcap.json`
- [модель](./block-models) в `/models/block/waxcap.json`
- [текстура](./first-block#models-and-textures) в `/textures/block/waxcap.png`

Если все правильно, вы сможете увидеть блок в игре.

![Правильный вид блока](/assets/develop/transparency-and-tinting/block_appearance_1.png)

## Источники тонировки блоков {#block-tint-sources}

Хотя наш блок выглядит нормально в игре, его текстура — в оттенках серого. Мы можем динамически применить цветовую тонировку, как это происходит с листвой в ванильной игре, которая меняет цвет в зависимости от биома.

Fabric API предоставляет `BlockColorRegistry` для регистрации списка `BlockTintSource`, которые мы будем использовать для динамической окраски блока.

Используем этот API, чтобы зарегистрировать тонировку так, чтобы наш блок восковика выглядел зелёным, если он размещён на траве, а в остальных случаях — коричневым.

В вашем **клиентском инициализаторе** зарегистрируйте блок в `ColorProviderRegistry` вместе с соответствующей логикой.

@[code lang=java transcludeWith=:::color_provider](@/reference/latest/src/client/java/com/example/docs/appearance/ExampleModAppearanceClient.java)

Теперь блок будет окрашиваться в зависимости от того, где он размещён.

![Блок с провайдером цвета](/assets/develop/transparency-and-tinting/block_appearance_2.png)
