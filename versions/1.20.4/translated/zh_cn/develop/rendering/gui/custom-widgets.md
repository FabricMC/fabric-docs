---
title: 自定义组件
description: 学习如何给您的界面创建自定义组件。
authors:
  - IMB11

search: false
---

# 自定义组件

组件是一类容器化的界面元素，它们可以被添加到界面上供玩家交互，交互方式包括鼠标点击、键盘输入等。

## 创建组件

有很多种创建组件的方式，最常用的是继承自 `ClickableWidget`。 这个类提供了许多实用功能，比如控制组件的尺寸和位置，以及接收用户输入事件。事实上这些功能由 `Drawable`、`Element`、`Narratable`、`Selectable` 接口规定：

- `Drawable` 用于指定渲染逻辑。当一个组件实现此接口时，您可以通过 `Screen#addDrawableChild` 将组件对象添加至界面中。
- `Element` 用于接收用户输入事件，比如鼠标点击、键盘输入等。
- `Narratable` 用于提供无障碍信息，无障碍功能（如复述）通过此接口访问组件内容。
- `Selectable` 用于聚焦组件，实现此接口后组件可以由 `Tab` 键选中，这也是一种无障碍功能。

@[code lang=java transcludeWith=:::1](@/reference/latest/src/client/java/com/example/docs/rendering/screens/CustomWidget.java)

## 将组件添加至界面

如同其他组件，您需要使用 `Screen#addDrawableChild` 来将组件添加到界面中。 请确保这一步在 `Screen#init` 方法中完成。

@[code lang=java transcludeWith=:::3](@/reference/latest/src/client/java/com/example/docs/rendering/screens/CustomScreen.java)

![界面上的自定义组件](/assets/develop/rendering/gui/custom-widget-example.png)

## 用户输入事件

您可以自定义用户输入事件的处理逻辑，比如覆写 `onMouseClicked`、`onMouseReleased`、`onKeyPressed` 等方法。

举个例子，您可以使用 `ClickableWidget#isHovered` 方法来使组件在鼠标悬停时变色。

@[code lang=java transcludeWith=:::2](@/reference/latest/src/client/java/com/example/docs/rendering/screens/CustomWidget.java)

![鼠标悬停事件](/assets/develop/rendering/gui/custom-widget-events.webp)
