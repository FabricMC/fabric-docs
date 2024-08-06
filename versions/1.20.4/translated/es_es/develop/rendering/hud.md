---
title: Renderizar en el Hud (Pantalla de Visualización Frontal)
description: Aprende a usar el evento de HudRenderCallback (Callback de Renderizado de Hud) para renderizar para el hud.
authors:
  - IMB11

search: false
---

# Renderizar en el Hud (Pantalla de Visualización Frontal)

Ya hemos tocado brevemente el tema sobre como renderizar cosas en el Hud en la guía de [Conceptos Básicos de Renderizado](./basic-concepts) y [Usando el Contexto de Dibujado](./draw-context), así que en esta página nos enfocaremos en el evento de `HudRenderCallback` y el parametro de `deltaTick`.

## HudRenderCallback (Callback de Renderizado de Hud)

El evento `HudRenderCallback` - proveído por el Fabric API - es llamado en cada frame (cuadro), y es usado para renderizar cosas en el HUD.

Para registrar a este evento, simplemente puedes llamar `HudRenderCallback.EVENT.register` y pasar una expresión Lambda el cual tiene como parámetros un `DrawContext` (Contexto de Dibujado) y un valor `float` (flotante) ("deltaTick").

El contexto de dibujado puede ser usado para acceder las diferentes utilidades de renderizados dadas por el juego, y acceder el matrix stack (pila de matrices).

Deberías echar un vistazo a la página de [Contexto de Dibujado](./draw-context) para aprender más sobre el contexto de dibujado.

### DeltaTick (Diferencia de Ticks)

El parámetro `deltaTick` es el tiempo en segundos desde el último frame, en segundos. Esto puede ser usado para hacer animaciones u otros efectos basados en el tiempo.

#### Ejemplo: Realizar una interpolación lineal sobre el Tiempo

Digamos que quieres realizar una interpolación lineal sobre el tiempo. Puedes usar el parámetro `deltaTick` para esto.

@[code lang=java transcludeWith=:::1](@/reference/latest/src/client/java/com/example/docs/rendering/HudRenderingEntrypoint.java)

![](/assets/develop/rendering/hud-rendering-deltatick.webp)
