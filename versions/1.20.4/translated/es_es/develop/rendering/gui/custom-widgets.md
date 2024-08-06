---
title: Widgets Personalizados
description: Aprende a crear widgets personalizados para tus pantallas o menús.
authors:
  - IMB11

search: false
---

# Widgets Personalizados

Los widgets son esencialmente componentes renderizados que pueden ser agregados a una pantalla, y que pueden ser usados por el jugador mediante varios eventos como un click del mouse, presionar una tecla, y más.

## Crear un Widget

Hay varias maneras de crear una clase widget, como extender `ClickableWidget` (Widget Clickeable). Esta clase provee varias utilidades, como por ejemplo para manejar el ancho, la altura, la posición, y para manejar eventos - implementa las interfaces `Drawable` (Dibujable), `Element` (Elemento), `Narratable` (Narrable), y `Selectable` (Seleccionable):

- `Drawable` - para renderizar - Requerido para registrar el widget a la pantalla mediante el método `addDrawableChild`.
- `Element` - para eventos - Requerido para manejar eventos como clicks del mouse, cuando se presiona una tecla, y más.
- `Narratable` - para accesibilidad - Requerido para que tu widget sea accesible a lectores de pantalla y otras herramientas de accesibilidad.
- `Seleccionable` - para selecciones - Requerido si quieres que tu widget sea seleccionable usando la tecla <kbd>Tab</kbd> - esto también ayuda en accesibilidad.

@[code lang=java transcludeWith=:::1](@/reference/latest/src/client/java/com/example/docs/rendering/screens/CustomWidget.java)

## Agregar el Widget a La Pantalla

Como todos los widgets, necesitarás agregarlo a la pantalla mediante el método `addDrawableChild`, el cual es proveído por la clase `Screen`. Asegúrate de hacerlo en el método `init`.

@[code lang=java transcludeWith=:::3](@/reference/latest/src/client/java/com/example/docs/rendering/screens/CustomScreen.java)

![Widget personalizado en la pantalla](/assets/develop/rendering/gui/custom-widget-example.png)

## Eventos de Widget

Puedes manejar eventos como clicks del mouse, cuando se presiona una tecla, anulando el método `onMouseClicked` (duranteMouseClickeado), `onMouseReleased` (duranteMouseSoltado), `onKeyPressed` (duranteTeclaPresionada), y otros métodos.

Por ejemplo, puedes hacer que el widget cambie color cuando el mouse está flotando encima del widget usando el método `isHovering()` proveído por la clase `ClickableWidget`:

@[code lang=java transcludeWith=:::2](@/reference/latest/src/client/java/com/example/docs/rendering/screens/CustomWidget.java)

![Ejemplo de Evento de Mouse Flotando](/assets/develop/rendering/gui/custom-widget-events.webp)
