---
title: 自定义屏幕
description: 学习如何给你的模组创建自定义屏幕。
authors:
  - IMB11
---

<!---->

::: info

本文所述的是普通的屏幕，不是菜单——这类屏幕是由玩家独自在客户端打开的，不需要服务端的参与。

:::

屏幕是指玩家可以交互的 GUI，比如标题屏幕、暂停屏幕等。

你 可以创建自己的屏幕来展示自定义内容、自定义配置目录等。

## 创建屏幕 {#creating-a-screen}

要创建屏幕，你 需要继承 `Screen` 类并重写 `init` 方法。你 可能还需要重写 `extractRenderState` 方法，但是请保证调用 `super.render`， 否则背景和组件都不会渲染。

您需要注意：

- 组件不应该在 `Screen` 的构造函数里创建，因为此时屏幕还没有初始化，并且某些变量（比如屏幕的宽 `width` 和高 `height`）也还没有正确地初始化。
- 当屏幕正在初始化时，`init` 方法将被调用，这是创建组件对象的最佳时机。
  - 你可以通过 `addRenderableWidget` 方法来添加组件，这个方法接收任何实现了 `Drawable` 和 `Element` 接口的组件对象。
- `extractRenderState` 方法将在每一帧被调用，你 可以在这个方法里获取诸多上下文，比如鼠标的位置。

举个例子，我们可以创建一个简单的屏幕，这个屏幕有一个按钮和一个按钮的标签。

@[code lang=java transcludeWith=:::1](@/reference/latest/src/client/java/com/example/docs/rendering/screens/CustomScreen.java)

![自定义屏幕 1](/assets/develop/rendering/gui/custom-1-example.png)

## 打开屏幕 {#opening-the-screen}

你可以使用 `Minecraft` 的 `setScreen` 方法打开屏幕——你可以从很多地方执行此操作，例如按键绑定、命令或客户端数据包处理程序。

```java
Minecraft.getInstance().setScreen(
  new CustomScreen(Component.empty())
);
```

## 关闭屏幕 {#closing-the-screen}

当你 想要关闭屏幕时，只需将屏幕设为 `null` 即可：

```java
Minecraft.getInstance().setScreen(null);
```

如果你 希望在关闭屏幕时返回到上一个屏幕，你 可以将当前屏幕对象传入自定义的 `CustomScreen` 构造函数，把它保存为字段，然后重写 `close` 方法，将实现修改为 `this.client.setScreen(/* 你 保存的上一个屏幕 */)` 即可。

@[code lang=java transcludeWith=:::2](@/reference/latest/src/client/java/com/example/docs/rendering/screens/CustomScreen.java)

现在，当你 按照上面的步骤打开屏幕时，你 可以给构造函数的第二个参数传入当前屏幕对象，这样当你 调用 `CustomScreen#close` 的时候，游戏就会回到上一个屏幕。

```java
Screen currentScreen = Minecraft.getInstance().currentScreen;
Minecraft.getInstance().setScreen(
  new CustomScreen(Component.empty(), currentScreen)
);
```
