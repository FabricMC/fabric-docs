---
title: Pantallas Personalizadas
description: Aprende a crear pantallas y menús personalizadas para tu mod.
authors:
  - IMB11

search: false
---

# Pantallas Personalizadas

:::info
Esta página se refiere a pantallas normales, y no pantallas manejadas - estas pantallas son las que son abiertas por el jugador en el cliente, no las que son manejadas por el servidor.
:::

Las pantallas son esencialmente la interfaz gráfica con la que jugador interactúa con, como el menú principal, el menú de pausa, etc.

Puedes crear tus propios menús para mostrar contenido personalizado, un menú de configuraciones personalizado, o más.

## Creando una Pantalla

Para crear una pantalla, necesitarás extender la clase `Screen` y anular el método `init` (inicializar) - opcionalmente también anular el método `render` - pero asegúrate de llamar su implementación súper, de lo contrario no se renderizará el fondo, los widgets, etc.

Toma en cuenta que:

- Los widgets no son creados en el constructor de la clase porque la pantalla aún no ha sido inicializada a este punto - y ciertas variables, como `width` (ancho) y `height` (altura), aún no están disponibles o no tienen valores correctos.
- El método `init` es llamado cuando la pantalla es inicializada, y es el mejor lugar para crear widgets.
  - Agregas widgets usando el método `addDrawableChild`, el cual acepta cualquier widget dibujable.
- El método `render` es llamado en cada frame - puedes acceder el contexto de dibujado, y la posición del mouse desde este método.

Por ejemplo, podemos crear una pantalla simple que tiene un botón y una etiqueta arriba.

@[code lang=java transcludeWith=:::1](@/reference/latest/src/client/java/com/example/docs/rendering/screens/CustomScreen.java)

![Pantalla Personalizada 1](/assets/develop/rendering/gui/custom-1-example.png)

## Abrir La Panatalla

Puedes abrir la pantalla usando el método `setScreen` de la clase `MinecraftClient` - puedes hacer esto desde muchos lugares, como un _key binding_, un comando, o un manipulador de paquetes de cliente.

```java
MinecraftClient.getInstance().setScreen(
  new CustomScreen(Text.empty())
);
```

## Cerrar La Pantalla

Si quieres cerrar la pantalla, simplemente establece la pantalla actual a un valor `null` (nulo):

```java
MinecraftClient.getInstance().setScreen(null);
```

Si quieres ser más elegante, y retornar a la pantalla anterior, puedes pasar la pantalla actual al constructor de la clase de `CustomScreen` y guardarla en un miembro, y después puedes usarlo para volver a la pantalla anterior cuando el método `close` es llamado.

@[code lang=java transcludeWith=:::2](@/reference/latest/src/client/java/com/example/docs/rendering/screens/CustomScreen.java)

Ahora, cuando abramos la pantalla personalizada, puedes pasar la pantalla actual como el segundo argumento - para que cuando llames `CustomScreen#close` - volverá a la pantalla anterior.

```java
Screen currentScreen = MinecraftClient.getInstance().currentScreen;
MinecraftClient.getInstance().setScreen(
  new CustomScreen(Text.empty(), currentScreen)
);
```
