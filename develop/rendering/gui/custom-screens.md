---
title: Custom Screens
description: Learn how to create custom screens for your mod.
authors:
  - IMB11
---

# Custom Screens

::: info
This page refers to normal screens, not handled ones - these screens are the ones that are opened by the player on the client, not the ones that are handled by the server.
:::

Screens are essentially the GUIs that the player interacts with, such as the title screen, pause screen etc. 

You can create your own screens to display custom content, a custom settings menu, or more.

## Creating A Screen

To create a screen, you need to extend the `Screen` class and override the `init` method - you may optionally override the `render` method as well - but make sure to call it's super method or it wont render the background, widgets etc.

You should take note that:

- Widgets are not being created in the constructor because the screen is not yet initialized at that point - and certain variables, such as `width` and `height`, are not yet available or not yet accurate.
- The `init` method is called when the screen is being initialized, and it is the best place to create widgets.
  - You add widgets using the `addDrawableChild` method, which accepts any drawable widget.
- The `render` method is called every frame - you can access the draw context, and the mouse position from this method.

As an example, we can create a simple screen that has a button and a label above it.

@[code lang=java transcludeWith=:::1](@/reference/latest/src/client/java/com/example/docs/rendering/screens/CustomScreen.java)

![Custom Screen 1](/assets/develop/rendering/gui/custom-1-example.png)

## Opening The Screen

You can open the screen using the `MinecraftClient`'s `setScreen` method - you can do this from many places, such as a key binding, a command, or a client packet handler.

```java
MinecraftClient.getInstance().setScreen(
  new CustomScreen(Text.empty())
);
```

## Closing The Screen

If you want to close the screen, simply set the screen to `null`:

```java
MinecraftClient.getInstance().setScreen(null);
```

If you want to be fancy, and return to the previous screen, you can pass the current screen into the `CustomScreen` constructor and store it in a field, then use it to return to the previous screen when the `close` method is called.

@[code lang=java transcludeWith=:::2](@/reference/latest/src/client/java/com/example/docs/rendering/screens/CustomScreen.java)

Now, when opening the custom screen, you can pass the current screen as the second argument - so when you call `CustomScreen#close` - it will return to the previous screen.

```java
Screen currentScreen = MinecraftClient.getInstance().currentScreen;
MinecraftClient.getInstance().setScreen(
  new CustomScreen(Text.empty(), currentScreen)
);
```