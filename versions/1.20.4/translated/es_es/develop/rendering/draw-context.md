---
title: Usando el Contexto de Dibujado
description: Aprende a como usar la clase `DrawContext` para renderizar varios objectos, texto y texturas.
authors:
  - IMB11

search: false
---

# Usando el Contexto de Dibujado

Esta página asume que ya has visto la página de [Conceptos Básicos de Renderizado](./basic-concepts).

La clase `DrawContext` es la clase principal usada para renderizar cosas en el juego. Es usada para renderizar objetos, texto y texturas y, como ya hemos visto, es usada para manipular diferentes `MatrixStack`s y usar `BufferBuilder`s.

## Dibujar Formas

La clase `DrawContext` puede ser usada para fácilmente dibujar formas **basadas en cuadrados**. Si quieres dibujar triángulos, o cualquier forma no basada en cuadrados, necesitarás usar un `BufferBuilder`.

### Dibujando Rectángulos

Puedes usar el método `DrawContext.fill(...)` para dibujar un rectángulo rellenado.

@[code lang=java transcludeWith=:::1](@/reference/latest/src/client/java/com/example/docs/rendering/DrawContextExampleScreen.java)

![Un rectángulo](/assets/develop/rendering/draw-context-rectangle.png)

### Dibujar Contornos/Bordes

Digamos que queremos delinear el rectángulo que acabamos de dibujar. Podemos usar el método `DrawContext.drawBorder(...)` para dibujar un contorno.

@[code lang=java transcludeWith=:::2](@/reference/latest/src/client/java/com/example/docs/rendering/DrawContextExampleScreen.java)

![Rectángulo con bordes](/assets/develop/rendering/draw-context-rectangle-border.png)

### Dibujar Líneas Individuales

Podemos usar los métodos `DrawContext.drawHorizontalLine(...)` y `DrawContext.drawVerticalLine(...)` para dibujar líneas.

@[code lang=java transcludeWith=:::3](@/reference/latest/src/client/java/com/example/docs/rendering/DrawContextExampleScreen.java)

![Líneas](/assets/develop/rendering/draw-context-lines.png)

## El _Scissor Manager_ (Gestor de Tijeras)

La clase `DrawContext` tiene un _scissor manager_ ya incluido. Esto te permite cortar tu renderizado a un área específica. Esto es útil para renderizar cosas como un _tooltip_ (información de herramienta), u otros elementos que no deberían ser renderizados fuera un área en específico.

### Usando el _Scissor Manager_

:::tip
¡Las regiones de tijeras pueden ser anidadas! Pero asegúrate de deshabilitar el _scissor manager_ la misma cantidad de veces que lo habilitaste.
:::

Para habilitar el _scissor manager_, simplemente usa el método `DrawContext.enableScissor(...)`. De igual forma, para deshabilitar el _scissor manager_, usa el método `DrawContext.disableScissor()`.

@[code lang=java transcludeWith=:::4](@/reference/latest/src/client/java/com/example/docs/rendering/DrawContextExampleScreen.java)

![Regiones de tijera en acción](/assets/develop/rendering/draw-context-scissor.png)

Como puedes ver, aunque le dijimos al juego que renderice la gradiente por toda la pantalla, solo hace dentro de la región de tijera.

## Dibujar Texturas

No hay una sola manera "correcta" de dibujar texturas en la pantalla, ya que el método `drawTexture(...)` tiene varias sobrecargas. Esta sección cubrirá los usos más comunes.

### Dibujar una Textura Entera

Generalmente, es recomendado que uses la sobrecarga que especifique los parámetros de `textureWidth` y el `textureHeight`. Esto es porque la clase `DrawContext` asumirá estos valores si no los provees, los cuales pueden estar incorrectos algunas veces.

@[code lang=java transcludeWith=:::5](@/reference/latest/src/client/java/com/example/docs/rendering/DrawContextExampleScreen.java)

![Ejemplo de diubjar la textura entera](/assets/develop/rendering/draw-context-whole-texture.png)

### Dibujar una Porción de una Textura

Aquí es donde `u` y `v` entran en pie. Estos parámetros especifican la esquina superior izquierda de la textura a dibujar, y los parámetros `regionWidth` y `regionHeight` especifican el tamaño de la porción de las texturas a dibujar.

Tomemos esta textura como ejemplo.

![Textura del Libro de Recetas](/assets/develop/rendering/draw-context-recipe-book-background.png)

Si solo queremos dibujar una región que contiene el lente magnificador, podemos usar los siguientes valores para `u`, `v`, `regionWidth`, y `regionHeight`:

@[code lang=java transcludeWith=:::6](@/reference/latest/src/client/java/com/example/docs/rendering/DrawContextExampleScreen.java)

![Región de Textura](/assets/develop/rendering/draw-context-region-texture.png)

## Dibujar Texto

La clase `DrawContext` tiene varios métodos fáciles de entender para renderizar texto - para ser breves, no serán cubiertos aquí.

Digamos que queremos dibujar "Hello World" en la pantalla. Podemos usar el método `DrawContext.drawText(...)` para esto.

@[code lang=java transcludeWith=:::7](@/reference/latest/src/client/java/com/example/docs/rendering/DrawContextExampleScreen.java)

![Dibujar Texto](/assets/develop/rendering/draw-context-text.png)
