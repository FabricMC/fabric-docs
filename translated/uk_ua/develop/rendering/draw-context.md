---
title: Використання контексту малювання
description: Взнайте як використовувати клас DrawContext для промальовування різних форм, тексту та текстур.
authors:
  - IMB11
---

Ця сторінка передбачає, що ви переглянули сторінку [основні концепції промальовування](./basic-concepts).

Клас `DrawContext` є основним класом, який використовується для промальовування в грі. Він використовується для промальовування форм, тексту та текстур, і, як бачили раніше, використовується для маніпулювання `MatrixStack` і використання `BufferBuilder`.

## Малювання фігур {#drawing-shapes}

Клас `DrawContext` можна використовувати для легкого малювання **квадратних** фігур. Якщо ви хочете намалювати трикутники або будь-яку неквадратну форму, вам потрібно буде використовувати `BufferBuilder`.

### Малювання прямокутників {#drawing-rectangles}

Щоб намалювати прямокутник із заливкою, можна використати метод `DrawContext.fill(...)`.

@[code lang=java transcludeWith=:::1](@/reference/latest/src/client/java/com/example/docs/rendering/DrawContextExampleScreen.java)

![Прямокутник](/assets/develop/rendering/draw-context-rectangle.png)

### Малювання контурів/меж {#drawing-outlines-borders}

Скажімо, ми хочемо окреслити прямокутник, який ми щойно намалювали. Ми можемо використати метод `DrawContext.drawBorder(...)`, щоб намалювати контур.

@[code lang=java transcludeWith=:::2](@/reference/latest/src/client/java/com/example/docs/rendering/DrawContextExampleScreen.java)

![Прямокутник з межею](/assets/develop/rendering/draw-context-rectangle-border.png)

### Малювання окремих ліній {#drawing-individual-lines}

Ми можемо використовувати методи `DrawContext.drawHorizontalLine(...)` і `DrawContext.drawVerticalLine(...)` для малювання ліній.

@[code lang=java transcludeWith=:::3](@/reference/latest/src/client/java/com/example/docs/rendering/DrawContextExampleScreen.java)

![Лінії](/assets/develop/rendering/draw-context-lines.png)

## Менеджер ножиць {#the-scissor-manager}

Клас `DrawContext` має вбудований менеджер ножиць. Це дає змогу легко прикріпити промальовування до певної області. Це корисно для візуалізації таких речей, як підказки або інші елементи, які не повинні промальовуватися за межами певної області.

### Використання менеджера ножиць {#using-the-scissor-manager}

:::tip
Області ножиць можуть бути вкладеними! Але переконайтеся, що ви вимикаєте менеджер ножиць стільки ж разів, скільки вмикали його.
:::

Щоб увімкнути менеджер ножиць, просто скористайтеся методом `DrawContext.enableScissor(...)`. Так само, щоб вимкнути менеджер ножиць, скористайтеся методом DrawContext.disableScissor()\`.

@[code lang=java transcludeWith=:::4](@/reference/latest/src/client/java/com/example/docs/rendering/DrawContextExampleScreen.java)

![Область ножиць у дії](/assets/develop/rendering/draw-context-scissor.png)

Як бачите, хоча ми наказуємо грі промальовувати градієнт по всьому екрану, він відтворює лише область ножиць.

## Малювання текстур{#drawing-textures}

Не існує єдиного «правильного» способу намалювати текстури на екрані, оскільки метод `drawTexture(...)` має багато різних перевантажень. У цьому розділі розглядаються найпоширеніші випадки використання.

### Малювання всієї текстури {#drawing-an-entire-texture}

Загалом, рекомендується використовувати перевантаження, яке визначає параметри `textureWidth` і `textureHeight`. Це тому, що клас `DrawContext` прийматиме ці значення, якщо ви їх не надасте, що іноді може бути неправильним.

Вам також потрібно буде вказати шар візуалізації, на якому намальована ваша текстура. Для звичайних текстур зазвичай завжди буде `RenderLayer::getGuiTextured`.

@[code lang=java transcludeWith=:::5](@/reference/latest/src/client/java/com/example/docs/rendering/DrawContextExampleScreen.java)

![Приклад малювання всієї текстури](/assets/develop/rendering/draw-context-whole-texture.png)

### Нанесення частини текстури {#drawing-a-portion-of-a-texture}

Ось де входять «u» і «v». Ці параметри визначають верхній лівий кут текстури для малювання, а параметри `regionWidth` і `regionHeight` визначають розмір частини текстури для малювання.

Візьмемо цю текстуру як приклад.

![Текстура книги рецептів](/assets/develop/rendering/draw-context-recipe-book-background.png)

Якщо ми хочемо намалювати лише область, яка містить збільшувальне скло, ми можемо використовувати наступні значення `u`, `v`, `regionWidth` і `regionHeight`:

@[code lang=java transcludeWith=:::6](@/reference/latest/src/client/java/com/example/docs/rendering/DrawContextExampleScreen.java)

![Текстура області](/assets/develop/rendering/draw-context-region-texture.png)

## Малювання тексту {#drawing-text}

Клас `DrawContext` має різні зрозумілі методи промальовування тексту - задля стислості вони не розглядатимуться тут.

Скажімо, ми хочемо намалювати на екрані «Hello World». Для цього ми можемо використати метод `DrawContext.drawText(...)`.

@[code lang=java transcludeWith=:::7](@/reference/latest/src/client/java/com/example/docs/rendering/DrawContextExampleScreen.java)

![Малювання тексту](/assets/develop/rendering/draw-context-text.png)
