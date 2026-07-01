---
title: Власні віджети
description: Почнімо створювати власний віджет для вашого екрана.
authors:
  - IMB11
---

Віджети — це, по суті, контейнерні компоненти рендера, які можна додавати на екран і з якими гравець може взаємодіяти за допомогою різних подій, таких як натискання миші, клавіш тощо.

## Створення віджету {#creating-a-widget}

Є кілька способів створити клас віджетів, наприклад розширити `AbstractWidget`. Цей клас надає багато корисних утиліт, таких як керування шириною, висотою, позицією та обробка подій — він реалізує інтерфейси `Renderable`, `GuiEventListener`, `NarrationSupplier` і `NarratableEntry`:

- `Renderable` — для рендера — потрібен для реєстрації віджета на екрані за допомогою методу `addRenderableWidget`.
- `GuiEventListener` — для подій — обов’язковий, якщо ви хочете обробляти такі події, як натискання мишею, натискання клавіш тощо.
- `NarrationSupplier` — для доступності — необхідний, щоб зробити ваш віджет доступним для програм зчитування з екрана та інших інструментів доступності.
- `NarratableEntry` – для вибору – обов’язковий, якщо ви хочете зробити свій віджет доступним для вибору за допомогою клавіші <kbd>Tab</kbd> – це також сприяє доступності.

<<< @/reference/26.1.2/src/client/java/com/example/docs/rendering/screens/CustomWidget.java#widget

## Додавання віджету до екрана {#adding-the-widget-to-the-screen}

Як і всі віджети, вам потрібно додати його на екран за допомогою методу `addRenderableWidget`, який надається класом `Screen`. Переконайтеся, що ви робите це в методі `init`.

<<< @/reference/26.1.2/src/client/java/com/example/docs/rendering/screens/CustomScreen.java#add_custom_widget

![Власний віджет на екрані](/assets/develop/rendering/gui/custom-widget-example.png)

## Події віджету {#widget-events}

Ви можете обробляти такі події, як натискання мишею, натискання клавіш, перевизначаючи `mouseClicked`, `afterMouseAction`, `keyPressed` та інші методи.

Наприклад, ви можете змусити віджет змінювати колір, коли на нього наводите курсор, використовуючи метод `isHovered()`, наданий класом `AbstractWidget`:

<<< @/reference/26.1.2/src/client/java/com/example/docs/rendering/screens/CustomWidget.java#on_hover_event

![Приклад наведення](/assets/develop/rendering/gui/custom-widget-events.webp)
