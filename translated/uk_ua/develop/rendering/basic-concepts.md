---
title: Основні концепції промальовування
description: Прочитайте про основні концепції промальовування, що використовує механізм промальовування гри.
authors:
  - "0x3C50"
  - IMB11
---

::: warning
Although Minecraft is built using OpenGL, as of version 1.17+ you cannot use legacy OpenGL methods to render your own things. Instead, you must use the new `BufferBuilder` system, which formats rendering data and uploads it to OpenGL to draw.

Загалом, ви можете використовувати систему промальовування Minecraft або створити власну, яка використовує `GL.glDrawElements()`.
:::

На цій сторінці описано основи промальовування за допомогою нової системи, а також ключову термінологію та поняття.

Хоча більша частина промальовування в Minecraft абстрагується за допомогою різних методів `DrawContext`, і вам, ймовірно, не потрібно чіпати нічого, згаданого тут, все одно важливо розуміти основи того, як працює промальовування.

## `Tessellator` {#the-tessellator}

`Tessellator` - це основний клас, який використовується для відтворення речей у Minecraft. В грі є лише один такий. Ви можете отримати екземпляр за допомогою `Tessellator.getInstance()`.

## `BufferBuilder` {#the-bufferbuilder}

`BufferBuilder` — це клас, який використовується для форматування та завантаження даних візуалізації в OpenGL. Він використовується для створення буфера, який потім завантажується в OpenGL для малювання.

`Tessellator` використовується для створення `BufferBuilder`, який використовується для форматування та завантаження даних візуалізації в OpenGL.

### Ініціалізація `BufferBuilder` {#initializing-the-bufferbuilder}

Перш ніж ви зможете записати щось у `BufferBuilder`, ви повинні ініціалізувати його. Це робиться за допомогою методу `Tessellator#begin(...)`, який використовує `VertexFormat` і режим малювання та повертає `BufferBuilder`.

#### Вершинні формати {#vertex-formats}

`VertexFormat` визначає елементи, які ми включаємо в наш буфер даних, і описує, як ці елементи мають бути передані в OpenGL.

Доступні такі елементи `VertexFormat`:

| Елемент                                       | Формат                                                                                  |
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

#### Режими малювання {#draw-modes}

Режим малювання визначає спосіб малювання даних. Доступні такі режими малювання:

| Режим малювання             | Опис                                                                                                                                                                    |
| --------------------------- | ----------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| `DrawMode.LINES`            | Кожен елемент складається з 2 вершин і представлений у вигляді однієї лінії.                                                                            |
| `DrawMode.LINE_STRIP`       | Для першого елемента потрібно 2 вершини. Додаткові елементи малюються лише з 1 новою вершиною, створюючи суцільну лінію.                |
| `DrawMode.DEBUG_LINES`      | Подібно до `DrawMode.LINES`, але ширина лінії на екрані завжди становить рівно один піксель.                                                            |
| `DrawMode.DEBUG_LINE_STRIP` | Те саме, що `DrawMode.LINE_STRIP`, але ширина ліній завжди один піксель.                                                                                |
| `DrawMode.TRIANGLES`        | Кожен елемент складається з 3 вершин, які утворюють трикутник.                                                                                          |
| `DrawMode.TRIANGLE_STRIP`   | Починається з 3 вершин для першого трикутника. Кожна додаткова вершина утворює новий трикутник із двома останніми вершинами.            |
| `DrawMode.TRIANGLE_FAN`     | Починається з 3 вершин для першого трикутника. Кожна додаткова вершина утворює новий трикутник з першою вершиною та останньою вершиною. |
| `DrawMode.QUADS`            | Кожен елемент складається з 4 вершин, що утворюють чотирикутник.                                                                                        |

### Запис до `BufferBuilder` {#writing-to-the-bufferbuilder}

Після ініціалізації `BufferBuilder` ви можете записати дані в нього.

`BufferBuilder` дозволяє нам побудувати наш буфер, вершина за вершиною. Щоб додати вершину, ми використовуємо метод `buffer.vertex(matrix, float, float, float)`. Параметр `matrix` — це матриця перетворення, яку ми обговоримо більш детально пізніше. Три параметри float представляють (x, y, z) координати положення вершини.

Цей метод повертає конструктор вершин, за допомогою якого ми можемо вказати додаткову інформацію для вершини. Під час додавання цієї інформації вкрай важливо дотримуватися порядку визначеного нами `VertexFormat`. Якщо ми цього не зробимо, OpenGL може неправильно інтерпретувати наші дані. Після того, як ми закінчили побудову вершини, просто продовжуйте додавати інші вершини та дані до буфера, доки не закінчите.

Також варто зрозуміти концепцію вибракування. Вибракування— це процес видалення меж тривимірної форми, які не видно з точки зору глядача. Якщо вершини межі вказано в неправильному порядку, межа може не відтворюватися належним чином через вибракування.

#### Що таке матриця перетворення? {#what-is-a-transformation-matrix}

Матриця перетворення — це матриця 4x4, яка використовується для перетворення вектора. У Minecraft матриця трансформації просто перетворює координати, які ми надаємо, у виклик вершини. Перетворення можуть масштабувати нашу модель, переміщувати її та обертати.

Її іноді називають матрицею позиції або матрицею моделі.

Зазвичай його отримують за допомогою класу `MatrixStack`, який можна отримати за допомогою об’єкта `DrawContext`:

```java
drawContext.getMatrices().peek().getPositionMatrix();
```

#### Промальовування трикутної смуги {#rendering-a-triangle-strip}

Простіше пояснити, як писати в `BufferBuilder`, використовуючи практичний приклад. Скажімо, ми хочемо промалювати щось за допомогою режиму малювання `DrawMode.TRIANGLE_STRIP` і формату вершини `POSITION_COLOR`.

Ми збираємося намалювати вершини в наступних точках на HUD (по черзі):

```txt
(20, 20)
(5, 40)
(35, 40)
(20, 60)
```

Це має дати нам чудовий ромб - оскільки ми використовуємо режим малювання `TRIANGLE_STRIP`, промальовування виконає наступні кроки:

![Чотири кроки, які показують розташування вершин на екрані для утворення двох трикутників](/assets/develop/rendering/concepts-practical-example-draw-process.png)

Оскільки в цьому прикладі ми малюємо HUD, ми використаємо подію `HudRenderCallback`:

@[code lang=java transcludeWith=:::1](@/reference/latest/src/client/java/com/example/docs/rendering/RenderingConceptsEntrypoint.java)

Це призводить до того, що на HUD малюється наступне:

![Фінальний результат](/assets/develop/rendering/concepts-practical-example-final-result.png)

:::tip
Спробуйте повозитися з кольорами та розташуванням вершин, щоб побачити, що вийде! Ви також можете спробувати використовувати різні режими малювання та формати вершин.
:::

## `MatrixStack` {#the-matrixstack}

Навчившись писати в `BufferBuilder`, ви можете вдаватися в питання, як трансформувати вашу модель - або навіть анімувати її. Тут на допомогу приходить клас `MatrixStack`.

Клас `MatrixStack` має такі методи:

- `push()` - надсилає нову матрицю в стек.
- `pop()` – знімає верхню матрицю зі стек.
- `peek()` - повертає верхню матрицю в стек.
- `translate(x, y, z)` – перекладає верхню матрицю в стек.
- `scale(x, y, z)` – масштабує верхню матрицю в стеці.

Ви також можете помножити верхню матрицю в стеці за допомогою кватерніонів, які ми розглянемо в наступному розділі.

Беручи з нашого прикладу вище, ми можемо збільшити та зменшити масштаб ромбу за допомогою `MatrixStack` і `tickDelta` - це "прогрес" між останнім ігровим тиком і наступним ігровим тиком. Ми роз’яснимо це пізніше на сторінці [промальовування в HUD](./hud#render-tick-counter).

::: warning
You must first push the matrix stack and then pop it after you're done with it. If you don't, you'll end up with a broken matrix stack, which will cause rendering issues.

Переконайтеся, що натиснули стек матриць, перш ніж отримати матрицю перетворення!
:::

@[code lang=java transcludeWith=:::2](@/reference/latest/src/client/java/com/example/docs/rendering/RenderingConceptsEntrypoint.java)

![Відео, на якому показано масштабування ромбу](/assets/develop/rendering/concepts-matrix-stack.webp)

## Кватерніони (обертові речі) {#quaternions-rotating-things}

Кватерніони — це спосіб представлення обертання в тривимірному просторі. Вони використовуються для обертання верхньої матриці в `MatrixStack` за допомогою методу `multiply(Quaternion, x, y, z)`.

Дуже малоймовірно, що вам коли-небудь знадобиться безпосередньо використовувати клас кватерніонів, оскільки Minecraft надає різні попередньо зібрані екземпляри кватерніонів у своєму службовому класі `RotationAxis`.

Скажімо, ми хочемо обертати ромб навколо осі z. Ми можемо зробити це за допомогою методу `MatrixStack` і `multiply(Quaternion, x, y, z)`.

@[code lang=java transcludeWith=:::3](@/reference/latest/src/client/java/com/example/docs/rendering/RenderingConceptsEntrypoint.java)

Результатом цього є наступне:

![Відео, на якому показано, як ромб обертається навколо осі z](/assets/develop/rendering/concepts-quaternions.webp)
