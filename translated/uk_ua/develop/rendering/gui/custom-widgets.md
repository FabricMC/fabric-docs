---
title: Власні віджети
description: Почнімо створювати власний віджет для вашого екрану.
authors:
  - IMB11
---

Віджети — це, по суті, контейнерні компоненти візуалізації, які можна додавати на екран і з якими гравець може взаємодіяти за допомогою різних подій, таких як натискання мишкою, клавіш тощо.

## Створення віджету {#creating-a-widget}

Є кілька способів створити клас віджетів, наприклад розширити `ClickableWidget`. Цей клас надає багато корисних утиліт, як-от керування шириною, висотою, позицією та обробку подій – він реалізує інтерфейси `Drawable`, `Element`, `Narratable` і `Selectable`:

- `Drawable` - для візуалізації - потрібен для реєстрації віджета на екрані за допомогою методу `addDrawableChild`.
- `Element` — для подій — обов’язковий, якщо ви хочете обробляти такі події, як натискання мишкою, клавіш тощо.
- `Narratable` - для спеціальних можливостей - необхідний, щоб зробити ваш віджет доступним для програм зчитування з екрана та інших інструментів доступності.
- `Selectable` — для вибору — обов’язковий, якщо ви хочете зробити свій віджет доступним для вибору за допомогою клавіші <kbd>Tab</kbd> — це також сприяє доступності.

@[code lang=java transcludeWith=:::1](@/reference/latest/src/client/java/com/example/docs/rendering/screens/CustomWidget.java)

## Додавання віджету до екрана {#adding-the-widget-to-the-screen}

Як і всі віджети, вам потрібно додати його на екран за допомогою методу `addDrawableChild`, який надається класом `Screen`. Переконайтеся, що ви робите це в методі `init`.

@[code lang=java transcludeWith=:::3](@/reference/latest/src/client/java/com/example/docs/rendering/screens/CustomScreen.java)

![Власний віджет на екрані](/assets/develop/rendering/gui/custom-widget-example.png)

## Події віджету {#widget-events}

Ви можете обробляти такі події, як натискання мишею, клавіш, перевизначаючи `onMouseClicked`, `onMouseReleased`, `onKeyPressed` та інші методи.

Наприклад, ви можете змусити віджет змінювати колір, коли на нього наводите курсор, використовуйте метод `isHovered()`, наданий класом `ClickableWidget`:

@[code lang=java transcludeWith=:::2](@/reference/latest/src/client/java/com/example/docs/rendering/screens/CustomWidget.java)

![Приклад наведення](/assets/develop/rendering/gui/custom-widget-events.webp)
