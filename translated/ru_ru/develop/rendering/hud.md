---
title: Рендеринг в HUD
description: Узнайте, как использовать событие HudRenderCallback для рендеринга в HUD
authors:
  - IMB11
---

# Рендеринг в HUD {#rendering-in-the-hud}

Мы уже кратко затронули тему рендеринга объектов в HUD на странице [Основные концепции рендеринга](./basic-concepts) и [Использование контекста рисования](./draw-context), поэтому на этой странице мы остановимся на событии `HudRenderCallback` и параметре `deltaTick`.

## HudRenderCallback {#hudrendercallback}

Событие `HudRenderCallback`, предоставляемое Fabric API, вызывается в каждом кадре и используется для рендеринга объектов в HUD.

Чтобы зарегистрироваться на это событие, вы можете просто вызвать `HudRenderCallback.EVENT.register` и передать lambda, которое принимает `DrawContext` и `float` (deltaTick) в качестве параметров.

Контекст отрисовки можно использовать для доступа к различным утилитам рендеринга, предоставляемым игрой, а также для доступа к стеку необработанных матриц.

Вам следует посетить страницу [Контекст рисования](./draw-context), чтобы узнать больше о контексте рисования.

### DeltaTick {#deltatick}

`deltaTick` относится ко времени с момента последнего кадра в секундах. Это можно использовать для создания анимации и других временных эффектов.

Например, предположим, что вы хотите изменять цвет с течением времени. Вы можете использовать `deltaTickManager`, чтобы получить deltaTick, и сохранять его с течением времени для преобразования цвета:

@[code lang=java transcludeWith=:::1](@/reference/latest/src/client/java/com/example/docs/rendering/HudRenderingEntrypoint.java)

![Изменение цвета с течением времени](/assets/develop/rendering/hud-rendering-deltatick.webp)
