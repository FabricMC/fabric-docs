---
title: Custom Widgets
description: Learn how to create custom widgets for your screens.
authors:
  - IMB11
---

# Custom Widgets

Widgets are essentially containerized rendering components that can be added to a screen, and can be interacted with by the player through various events such as mouse clicks, key presses, and more.

## Creating A Widget

There are multiple ways to create a widget class, such as extending `ClickableWidget`. This class provides a lot of useful utilities, such as managing width, height, position, and handling events - it implements the `Drawable`, `Element`, `Narratable`, and `Selectable` interfaces:

- `Drawable` - for rendering - Required to register the widget to the screen via the `addDrawableChild` method.
- `Element` - for events - Required if you want to handle events such as mouse clicks, key presses, and more.
- `Narratable` - for accessibility - Required to make your widget accessible to screen readers and other accessibility tools.
- `Selectable` - for selection - Required if you want to make your widget selectable using the <kbd>Tab</kbd> key - this also aids in accessibility.

@[code lang=java transcludeWith=:::1](@/reference/latest/src/client/java/com/example/docs/rendering/screens/CustomWidget.java)

## Adding The Widget To The Screen

Like all widgets, you need to add it to the screen using the `addDrawableChild` method, which is provided by the `Screen` class. Make sure you do this in the `init` method.

@[code lang=java transcludeWith=:::3](@/reference/latest/src/client/java/com/example/docs/rendering/screens/CustomScreen.java)

![Custom widget on screen.](/assets/develop/rendering/gui/custom-widget-example.png)

## Widget Events

You can handle events such as mouse clicks, key presses, by overriding the `onMouseClicked`, `onMouseReleased`, `onKeyPressed`, and other methods.

For example, you can make the widget change color when it's hovered over by using the `isHovered()` method provided by the `ClickableWidget` class:

@[code lang=java transcludeWith=:::2](@/reference/latest/src/client/java/com/example/docs/rendering/screens/CustomWidget.java)

![Hover Event Example](/assets/develop/rendering/gui/custom-widget-events.webp)