---
title: 自定义组件
description: 学习如何给您的界面创建自定义组件。
authors:
  - IMB11
---

# 自定义组件{#custom-widgets}

组件本质上是容器化的界面元素，可以被添加到屏幕中供玩家交互，交互方式包括鼠标点击、键盘输入等。

## 创建组件{#creating-a-widget}

有很多种创建组件的方式，例如继承 `ClickableWidget`。 这个类提供了许多实用功能，比如控制组件的尺寸和位置，以及接收用户输入事件。事实上这些功能由 `Drawable`、`Element`、`Narratable`、`Selectable` 接口规定：

- `Drawable` 用于渲染，需要通过 `Screen#addDrawableChild` 将组件注册到屏幕中。
- `Element` 用于事件，比如鼠标点击、键盘输入等，需要这个来处理事件。
- `Narratable` 用于无障碍，让组件能够通过屏幕阅读器或其他无障碍工具访问。
- `Selectable` 用于选择，实现此接口后组件可以由 <kbd>Tab</kbd> 键选中，这也能帮助无障碍。

@[code lang=java transcludeWith=:::1](@/reference/latest/src/client/java/com/example/docs/rendering/screens/CustomWidget.java)

## 将组件添加到屏幕{#adding-the-widget-to-the-screen}

如同其他组件，您需要使用 `Screen#addDrawableChild` 来将组件添加到界面中。 请确保这一步在 `Screen#init` 方法中完成。

@[code lang=java transcludeWith=:::3](@/reference/latest/src/client/java/com/example/docs/rendering/screens/CustomScreen.java)

![屏幕中的自定义组件](/assets/develop/rendering/gui/custom-widget-example.png)

## 组件事件{#widget-events}

您可以自定义用户输入事件的处理逻辑，比如覆写 `onMouseClicked`、`onMouseReleased`、`onKeyPressed` 等方法。

举个例子，您可以使用 `ClickableWidget#isHovered` 方法来使组件在鼠标悬停时变色。

@[code lang=java transcludeWith=:::2](@/reference/latest/src/client/java/com/example/docs/rendering/screens/CustomWidget.java)

![鼠标悬停事件](/assets/develop/rendering/gui/custom-widget-events.webp)
