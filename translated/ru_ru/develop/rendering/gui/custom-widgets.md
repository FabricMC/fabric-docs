---
title: Пользовательские виджеты
description: Узнайте, как создавать пользовательские виджеты для ваших экранов.
authors:
  - IMB11
---

# Пользовательские виджеты {#custom-widgets}

Виджеты — это контейнеризированные компоненты рендеринга, которые можно добавить на экран, где игрок взаимодействует через различные события, например щелчки мыши или нажатия клавиш.

## Создание виджета {#creating-a-widget}

Существует несколько способов создания класса виджета, например, путем расширения `ClickableWidget`. Этот класс предоставляет множество полезных утилит, таких как управление шириной, высотой, положением и обработка событий. Он реализует интерфейсы `Drawable`, `Element`, `Narratable` и `Selectable`:

- `Drawable` — для рендеринга — требуется для регистрации виджета на экране с помощью метода `addDrawableChild`.
- `Элемент` — для событий — требуется, если вы хотите обрабатывать такие события, как щелчки мыши, нажатия клавиш и т. д.
- `Narratable` — для доступности — требуется, чтобы сделать ваш виджет доступным для программ чтения с экрана и других инструментов обеспечения доступности.
- `Selectable` — для выбора — требуется, если вы хотите сделать виджет доступным для выбора с помощью клавиши <kbd>Tab</kbd> - это также способствует повышению доступности.

@[code lang=java transcludeWith=:::1](@/reference/latest/src/client/java/com/example/docs/rendering/screens/CustomWidget.java)

## Добавление виджета на экран {#adding-the-widget-to-the-screen}

Как и все виджеты, его необходимо добавить на экран с помощью метода `addDrawableChild`, который предоставляется классом `Screen`. Обязательно сделайте это в методе `init`.

@[code lang=java transcludeWith=:::3](@/reference/latest/src/client/java/com/example/docs/rendering/screens/CustomScreen.java)

![Пользовательский виджет на экране](/assets/develop/rendering/gui/custom-widget-example.png)

## События виджета {#widget-events}

Вы можете обрабатывать такие события, как щелчки мыши и нажатия клавиш, переопределяя методы `onMouseClicked`, `onMouseReleased`, `onKeyPressed` и другие.

Например, вы можете заставить виджет менять цвет при наведении на него курсора, используя метод `isHovered()`, предоставляемый классом `ClickableWidget`:

@[code lang=java transcludeWith=:::2](@/reference/latest/src/client/java/com/example/docs/rendering/screens/CustomWidget.java)

![Пример события наведения](/assets/develop/rendering/gui/custom-widget-events.webp)
