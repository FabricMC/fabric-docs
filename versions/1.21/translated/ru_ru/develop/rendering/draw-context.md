---
title: Использование контекста рисования
description: Узнайте, как использовать класс DrawContext для визуализации различных фигур, текста и текстур.
authors:
  - IMB11
---

На этой странице предполагается, что вы ознакомились со страницей [Основные концепции рендеринга](./basic-concepts).

Класс `DrawContext` является основным классом, используемым для рендеринга в игре. Он используется для рендеринга фигур, текста и текстур, а так же, как было показано ранее, для управления `MatrixStack` и использования `BufferBuilder`.

## Рисование фигур {#drawing-shapes}

Класс `DrawContext` можно использовать для простого рисования **квадратных** фигур. Если вы хотите нарисовать треугольники или любую другую неквадратную фигуру, вам понадобится `BufferBuilder`.

### Рисование прямоугольников {#drawing-rectangles}

Для рисования закрашенного прямоугольника можно использовать метод `DrawContext.fill(...)`.

@[code lang=java transcludeWith=:::1](@/reference/1.21/src/client/java/com/example/docs/rendering/DrawContextExampleScreen.java)

![Прямоугольник](/assets/develop/rendering/draw-context-rectangle.png)

### Контуры рисования/границы {#drawing-outlines-borders}

Допустим, мы хотим обвести только что нарисованный прямоугольник. Мы можем использовать метод `DrawContext.drawBorder(...)`, чтобы нарисовать контур.

@[code lang=java transcludeWith=:::2](@/reference/1.21/src/client/java/com/example/docs/rendering/DrawContextExampleScreen.java)

![Прямоугольник с рамкой](/assets/develop/rendering/draw-context-rectangle-border.png)

### Рисование отдельных линий {#drawing-individual-lines}

Для рисования линий мы можем использовать методы `DrawContext.drawHorizontalLine(...)` и `DrawContext.drawVerticalLine(...)`.

@[code lang=java transcludeWith=:::3](@/reference/1.21/src/client/java/com/example/docs/rendering/DrawContextExampleScreen.java)

![Линии](/assets/develop/rendering/draw-context-lines.png)

## Менеджер ножниц {#the-scissor-manager}

Класс `DrawContext` имеет встроенный менеджер ножниц. Это позволяет вам легко привязать визуализацию к определенной области. Это полезно для отображения таких вещей, как всплывающие подсказки или другие элементы, которые не должны отображаться за пределами определенной области.

### Использование менеджера ножниц {#using-the-scissor-manager}

:::tip
Области ножниц могут быть вложенными! Но убедитесь, что вы отключаете менеджер ножниц столько же раз, сколько и включали его.
:::

Чтобы включить менеджер ножниц, просто используйте метод `DrawContext.enableScissor(...)`. Аналогично, чтобы отключить менеджер ножниц, используйте метод `DrawContext.disableScissor()`.

@[code lang=java transcludeWith=:::4](@/reference/1.21/src/client/java/com/example/docs/rendering/DrawContextExampleScreen.java)

![Регион ножниц в действии](/assets/develop/rendering/draw-context-scissor.png)

Как видите, даже несмотря на то, что мы говорим игре отрисовывать градиент по всему экрану, она отрисовывает его только в области ножниц.

## Текстуры для рисования {#drawing-textures}

Не существует единственно «правильного» способа отрисовки текстур на экране, поскольку метод `drawTexture(...)` имеет множество различных перегрузок. В этом разделе будут рассмотрены наиболее распространенные варианты использования.

### Рисование всей текстуры {#drawing-an-entire-texture}

Обычно рекомендуется использовать перегрузку, которая задает параметры `textureWidth` и `textureHeight`. Это связано с тем, что класс `DrawContext` примет эти значения, если вы их не предоставите, что иногда может быть неверным.

@[code lang=java transcludeWith=:::5](@/reference/1.21/src/client/java/com/example/docs/rendering/DrawContextExampleScreen.java)

![Пример рисования всей текстуры](/assets/develop/rendering/draw-context-whole-texture.png)

### Рисование части текстуры {#drawing-a-portion-of-a-texture}

Вот тут-то и появляются `u` и `v`. Эти параметры определяют верхний левый угол текстуры для рисования, а параметры `regionWidth` и `regionHeight` определяют размер части текстуры для рисования.

Давайте возьмем эту текстуру в качестве примера.

![Текстура книги рецептов](/assets/develop/rendering/draw-context-recipe-book-background.png)

Если мы хотим нарисовать только область, содержащую увеличительное стекло, мы можем использовать следующие значения `u`, `v`, `regionWidth` и `regionHeight`:

@[code lang=java transcludeWith=:::6](@/reference/1.21/src/client/java/com/example/docs/rendering/DrawContextExampleScreen.java)

![Текстура области](/assets/develop/rendering/draw-context-region-texture.png)

## Рисунок текста {#drawing-text}

Класс `DrawContext` имеет различные интуитивно понятные методы визуализации текста — для краткости они здесь не будут рассматриваться.

Допустим, мы хотим нарисовать на экране «Hello World». Для этого мы можем использовать метод `DrawContext.drawText(...)`.

@[code lang=java transcludeWith=:::7](@/reference/1.21/src/client/java/com/example/docs/rendering/DrawContextExampleScreen.java)

![Рисование текста](/assets/develop/rendering/draw-context-text.png)
