---
title: Основные концепции рендеринга
description: Изучите основные концепции рендеринга с использованием движка рендеринга Minecraft.
authors:
  - IMB11
  - "0x3C50"
---

::: warning
Although Minecraft is built using OpenGL, as of version 1.17+ you cannot use legacy OpenGL methods to render your own things. Instead, you must use the new `BufferBuilder` system, which formats rendering data and uploads it to OpenGL to draw.

Подводя итог, вам придется использовать систему рендеринга Minecraft или создать свою собственную, использующую `GL.glDrawElements()`.
:::

На этой странице будут рассмотрены основы рендеринга с использованием новой системы, а также ключевые термины и концепции.

Хотя большая часть рендеринга в Minecraft абстрагирована с помощью различных методов `DrawContext`, и вам, скорее всего, не придется касаться ничего из упомянутого здесь, все равно важно понимать основы того, как работает рендеринг.

## `Tessellator` {#the-tessellator}

`Tessellator` — это основной класс, используемый для рендеринга объектов в Minecraft. Это singleton, то есть в игре существует только один его экземпляр. Экземпляр можно получить с помощью `Tessellator.getInstance()`.

## `BufferBuilder` {#the-bufferbuilder}

`BufferBuilder` — это класс, используемый для форматирования и загрузки данных рендеринга в OpenGL. Он используется для создания буфера, который затем загружается в OpenGL для отрисовки.

`Tessellator` используется для создания `BufferBuilder`, который используется для форматирования и загрузки данных рендеринга в OpenGL.

### Инициализация `BufferBuilder` {#initializing-the-bufferbuilder}

Прежде чем что-либо записать в `BufferBuilder`, его необходимо инициализировать. Это делается с помощью метода `Tessellator#begin(...)`, который принимает `VertexFormat` и режим отрисовки и возвращает `BufferBuilder`.

#### Форматы вершин {#vertex-formats}

`VertexFormat` определяет элементы, которые мы включаем в наш буфер данных, и описывает, как эти элементы должны передаваться в OpenGL.

Доступны следующие элементы `VertexFormat`:

| Элемент                                       | Формат                                                                                  |
| --------------------------------------------- | --------------------------------------------------------------------------------------- |
| `BLIT_SCREEN`                                 | `{ position (3 floats: x, y and z), uv (2 floats), color (4 ubytes) }`                  |
| `POSITION_COLOR_TEXTURE_LIGHT_NORMAL`         | `{ position, color, texture uv, texture light (2 shorts), texture normal (3 sbytes) }`  |
| `POSITION_COLOR_TEXTURE_OVERLAY_LIGHT_NORMAL` | `{ position, color, texture uv, overlay (2 shorts), texture light, normal (3 sbytes) }` |
| `POSITION_TEXTURE_COLOR_LIGHT`                | `{ position, texture uv, color, texture light }`                                        |
| `POSITION`                                    | `{ position }`                                                                          |
| `POSITION_COLOR`                              | `{ position, color }`                                                                   |
| `LINES`                                       | `{ position, color, normal }`                                                           |
| `POSITION_COLOR_LIGHT`                        | `{ position, color, light }`                                                            |
| `POSITION_TEXTURE`                            | `{ position, uv }`                                                                      |
| `POSITION_COLOR_TEXTURE`                      | `{ position, color, uv }`                                                               |
| `POSITION_TEXTURE_COLOR`                      | `{ position, uv, color }`                                                               |
| `POSITION_COLOR_TEXTURE_LIGHT`                | `{ position, color, uv, light }`                                                        |
| `POSITION_TEXTURE_LIGHT_COLOR`                | `{ position, uv, light, color }`                                                        |
| `POSITION_TEXTURE_COLOR_NORMAL`               | `{ position, uv, color, normal }`                                                       |

#### Режимы рисования {#draw-modes}

Режим рисования определяет, как будут отображаться данные. Доступны следующие режимы рисования:

| Режим рисования             | Описание                                                                                                                                                                     |
| --------------------------- | ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| `DrawMode.LINES`            | Каждый элемент состоит из 2 вершин и представлен в виде одной линии.                                                                                         |
| `DrawMode.LINE_STRIP`       | Для первого элемента требуется 2 вершины. Дополнительные элементы рисуются всего с одной новой вершиной, создавая непрерывную линию.         |
| `DrawMode.DEBUG_LINES`      | Аналогично `DrawMode.LINES`, но линия на экране всегда имеет ширину ровно в один пиксель.                                                                    |
| `DrawMode.DEBUG_LINE_STRIP` | То же, что и `DrawMode.LINE_STRIP`, но линии всегда имеют ширину в один пиксель.                                                                             |
| `DrawMode.TRIANGLES`        | Каждый элемент состоит из 3 вершин, образующих треугольник.                                                                                                  |
| `DrawMode.TRIANGLE_STRIP`   | Начинается с 3 вершин первого треугольника. Каждая дополнительная вершина образует новый треугольник с двумя последними вершинами.           |
| `DrawMode.TRIANGLE_FAN`     | Начинается с 3 вершин первого треугольника. Каждая дополнительная вершина образует новый треугольник с первой вершиной и последней вершиной. |
| `DrawMode.QUADS`            | Каждый элемент состоит из 4 вершин, образующих четырехугольник.                                                                                              |

### Запись в `BufferBuilder` {#writing-to-the-bufferbuilder}

После инициализации `BufferBuilder` вы можете записывать в него данные.

`BufferBuilder` позволяет нам создавать наш буфер, вершина за вершиной. Чтобы добавить вершину, мы используем метод `buffer.vertex(matrix, float, float, float)`. Параметр `matrix` — это матрица преобразования, которую мы рассмотрим более подробно позже. Три параметра с плавающей точкой представляют собой координаты (x, y, z) положения вершины.

Этот метод возвращает конструктор вершин, который мы можем использовать для указания дополнительной информации о вершине. При добавлении этой информации крайне важно соблюдать порядок, определенный в нашем `VertexFormat`. Если мы этого не сделаем, OpenGL может неправильно интерпретировать наши данные. После того как мы закончим построение вершины, просто продолжайте добавлять новые вершины и данные в буфер, пока не закончите.

Также стоит понять концепцию отсечения (culling). Отсечение — это процесс удаления граней трехмерной фигуры, которые не видны с точки зрения наблюдателя. Если вершины грани указаны в неправильном порядке, грань может отображаться неправильно из-за отсечения.

#### Что такое матрица трансформации? {#what-is-a-transformation-matrix}

Матрица преобразования — это матрица размером 4x4, которая используется для преобразования вектора. В Minecraft матрица преобразования просто преобразует координаты, которые мы передаем, в вызов вершины. Преобразования позволяют масштабировать нашу модель, перемещать и вращать ее.

Иногда ее называют матрицей позиций или матрицей моделей.

Обычно его можно получить через класс `MatrixStack`, который можно получить через объект `DrawContext`:

```java
drawContext.getMatrices().peek().getPositionMatrix();
```

#### Рендеринг полосы треугольников {#rendering-a-triangle-strip}

Проще объяснить, как писать в `BufferBuilder`, на практическом примере. Допустим, мы хотим что-то визуализировать, используя режим рисования `DrawMode.TRIANGLE_STRIP` и формат вершин `POSITION_COLOR`.

Мы собираемся нарисовать вершины в следующих точках HUD (по порядку):

```txt
(20, 20)
(5, 40)
(35, 40)
(20, 60)
```

Это должно дать нам прекрасный ромб — поскольку мы используем режим рисования `TRIANGLE_STRIP`, render выполнит следующие шаги:

![Четыре шага, демонстрирующие размещение вершин на экране для формирования двух треугольников](/assets/develop/rendering/concepts-practical-example-draw-process.png)

Поскольку в этом примере мы рисуем на HUD, мы будем использовать событие `HudRenderCallback`:

@[code lang=java transcludeWith=:::1](@/reference/1.21/src/client/java/com/example/docs/rendering/RenderingConceptsEntrypoint.java)

В результате на HUD отображается следующее:

![Конечный результат](/assets/develop/rendering/concepts-practical-example-final-result.png)

:::tip
Попробуйте поиграться с цветами и положением вершин, чтобы посмотреть, что получится! Вы также можете попробовать использовать различные режимы рисования и форматы вершин.
:::

## `MatrixStack` {#the-matrixstack}

Узнав, как писать в `BufferBuilder`, вы, возможно, зададитесь вопросом, как преобразовать свою модель или даже анимировать ее. Вот тут-то и появляется класс `MatrixStack`.

Класс `MatrixStack` имеет следующие методы:

- `push()` — помещает новую матрицу в стек.
- `pop()` - Pops the top matrix off the stack.
- `pop()` - Извлекает верхнюю матрицу из стека.
- `translate(x, y, z)` — переводит верхнюю матрицу в стеке.
- `scale(x, y, z)` — масштабирует верхнюю матрицу в стеке.

Вы также можете умножить верхнюю матрицу в стеке, используя кватернионы, о чем мы поговорим в следующем разделе.

Используя наш пример выше, мы можем масштабировать наш алмаз вверх и вниз, используя `MatrixStack` и `tickDelta`, то есть время, прошедшее с момента последнего кадра.

::: warning
You must first push the matrix stack and then pop it after you're done with it. If you don't, you'll end up with a broken matrix stack, which will cause rendering issues.

Обязательно выдвиньте стек матриц, прежде чем получить матрицу преобразования!
:::

@[code lang=java transcludeWith=:::2](@/reference/1.21/src/client/java/com/example/docs/rendering/RenderingConceptsEntrypoint.java)

![Видео, демонстрирующее увеличение и уменьшение размера алмаза](/assets/develop/rendering/concepts-matrix-stack.webp)

## Кватернионы (вращающиеся вещи) {#quaternions-rotating-things}

Кватернионы — это способ представления вращений в трехмерном пространстве. Они используются для поворота верхней матрицы в `MatrixStack` с помощью метода `multiply(Quaternion, x, y, z)`.

Крайне маловероятно, что вам когда-либо понадобится использовать класс Quaternion напрямую, поскольку Minecraft предоставляет различные готовые экземпляры Quaternion в своем служебном классе `RotationAxis`.

Допустим, мы хотим повернуть наш алмаз вокруг оси z. Мы можем сделать это с помощью `MatrixStack` и метода `multiply(Quaternion, x, y, z)`.

@[code lang=java transcludeWith=:::3](@/reference/1.21/src/client/java/com/example/docs/rendering/RenderingConceptsEntrypoint.java)

Результатом этого является следующее:

![Видео, демонстрирующее вращение алмаза вокруг оси z](/assets/develop/rendering/concepts-quaternions.webp)
