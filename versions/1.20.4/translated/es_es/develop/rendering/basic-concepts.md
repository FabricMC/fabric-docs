---
title: Conceptos Básicos de Renderización
description: Aprende sobre los conceptos básicos de renderización usando el motor de renderización de Minecraft.
authors:
  - IMB11
  - "0x3C50"

search: false
---

# Conceptos Básicos de Renderización

::: warning
Although Minecraft is built using OpenGL, as of version 1.17+ you cannot use legacy OpenGL methods to render your own things. Instead, you must use the new `BufferBuilder` system, which formats rendering data and uploads it to OpenGL to draw.

En resumen, tienes que usar el motor de renderizado de Minecraft, o hacer tu propio que utilize `GL.glDrawElements()` (dibujarElementos).
:::

Esta página tratará los conceptos básicos de renderizado usando el nuevo sistema, cubriendo la terminología y conceptos importantes.

Aunque mucho del proceso de renderización de Minecraft está abstraído mediante varios métodos en la clase `DrawContext` (Contexto de Dibujado), y el hecho de que probablemente no tendrás que tocar nada de lo mencionado aquí, es importante que entiendas como funciona la renderización básica.

## El `Tessellator` (Teselador)

El `Tessellator` is la clase principical usada para renderizar cosas en Minecraft. Es una clase de tipo única, lo que quiere decir que solo hay una instancia de él en el juego. Puedes obtener esta instancia mediante `Tessellator.getInstance()`.

## El `BufferBuilder` (Constructor de Buffer)

El `BufferBuilder` es la clase usada para formatear y subir información o datos de renderización a OpenGL. Es usado para crear un buffer, el cual es subido a OpenGL para que lo dibuje.

El `Tessellator` es usado para crear un `BufferBuilder`, el cual es usado para formatear y subir datos de renderizado a OpenGL. Puedes crear un `BufferBuilder` usando `Tessellator.getBuffer()`.

### Inicializando el `BufferBuilder`

Antes de que puedas enviar datos al `BufferBuilder`, debes inicializarlo. Esto se hace mediante `BufferBuilder.begin(...)`, el cual acepta un `VertexFormat` (Formato de Vertice) y un modo de dibujado.

#### Formatos de Vertice

El `VertexFormat` define los elementos que incluimos en nuestro buffer de datos y determina como estos elementos deberían ser transmitidos a OpenGL.

Los siguientes elementos de `VertexFormat` están disponibles:

| Elemento                                      | Formato                                                                                    |
| --------------------------------------------- | ------------------------------------------------------------------------------------------ |
| `BLIT_SCREEN`                                 | `{ posición (3 flotantes: x, y y z), uv (2 flotantes), color (4 ubytes) }`                 |
| `POSITION_COLOR_TEXTURE_LIGHT_NORMAL`         | `{ posición, color, textura uv, luz de textura (2 shorts), normal de textura (3 sbytes) }` |
| `POSITION_COLOR_TEXTURE_OVERLAY_LIGHT_NORMAL` | `{ posición, color, textura uv, cubierta (2 shorts), luz de textura, normal (3 sbytes) }`  |
| `POSITION_TEXTURE_COLOR_LIGHT`                | `{ posición, textura uv, color, luz de textura }`                                          |
| `POSITION`                                    | `{ posición }`                                                                             |
| `POSITION_COLOR`                              | `{ posición, color }`                                                                      |
| `LINES`                                       | `{ posición, color, normal }`                                                              |
| `POSITION_COLOR_LIGHT`                        | `{ posición, color, luz }`                                                                 |
| `POSITION_TEXTURE`                            | `{ posición, uv }`                                                                         |
| `POSITION_COLOR_TEXTURE`                      | `{ posición, color, uv }`                                                                  |
| `POSITION_TEXTURE_COLOR`                      | `{ posición, uv, color }`                                                                  |
| `POSITION_COLOR_TEXTURE_LIGHT`                | `{ posición, color, uv, luz }`                                                             |
| `POSITION_TEXTURE_LIGHT_COLOR`                | `{ posición, uv, luz, color }`                                                             |
| `POSITION_TEXTURE_COLOR_NORMAL`               | `{ posición, uv, color, normal }`                                                          |

#### Modos de Dibujado

El modo de dibujado define como son dibujados los datos. Los siguientes modos de dibujado están disponibles:

| Modo de Dibujado            | Descripción                                                                                                                                                                |
| --------------------------- | -------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| `DrawMode.LINES`            | Cada elemento está compuesto de 2 vértices y es representado como una sola línea.                                                                          |
| `DrawMode.LINE_STRIP`       | El primer elemento requiere 2 vértices. Elementos adicionales son dibujados con un solo nuevo vértice, creando una línea continua.         |
| `DrawMode.DEBUG_LINES`      | Similar a `DrawMode.LINES`, pero la línea es siempre exactamente un pixel de ancho en la pantalla.                                                         |
| `DrawMode.DEBUG_LINE_STRIP` | Similar a `DrawMode.LINE_STRIP`, pero las líneas son siempre un pixel de ancho.                                                                            |
| `DrawMode.TRIANGLES`        | Cada elemento está compuesto de 3 vértices, formando un triángulo.                                                                                         |
| `DrawMode.TRIANGLE_STRIP`   | Empieza con 3 vértices para formar el primer triángulo. Cada vértice adicional produce un nuevo triángulo usando los dos últimos vértices. |
| `DrawMode.TRIANGLE_FAN`     | Empieza con 3 vértices para formar el primer triángulo. Cada vértice adicional produce un nuevo triángulo con el primer y último vértice.  |
| `DrawMode.QUADS`            | Cada elemento está formado de 4 vértices, formando un cuadrilátero.                                                                                        |

### Escribir datos para el `BufferBuilder`

Una vez inicializado el `BufferBuilder`, puedes escribir datos en él.

El `BufferBuilder` nos permite construir nuestro buffer, vértice por vértice. Para agregar un vértice, usamos el método `buffer.vertex(matriz, flotante, flotante, flotante)`. El parámetro `matriz` es la matriz de transformaciones, el cual discutiremos más adelante. Los 3 parámetros flotantes representan las coordenadas (x, y, z) de la posición del vértice.

Este método retorna un constructor de vértice, el cual podemos usar para especificar información adicional para el vértice. Es crucial seguir el orden de nuestro `VertexFormat` definido a la hora de agregar esta información. De lo contrario, `OpenGL` puede que no interprete nuestros datos correctamente. Una vez que hemos terminado de construir nuestro vértice, podemos llamar el metodo `next()`. Esto finaliza el vértice actual y prepara el constructor para el siguiente.

También es importante entender el concepto de _culling_ (eliminación). _Culling_ es el proceso de remover caras de un objeto 3D que no son visibles desde la perspectiva del observador. Si los vértices de una cara son especificadas en el orden incorrecto, las caras pueden no mostrarse correctamente debido al _culling_.

#### ¿Que es una Matriz de Transformación?

Una matriz de transformación es una matriz 4x4 que es usada para transformar un vector. En Minecraft, la matriz de transformación solo transforma las coordenadas que le damos al llamado de vértices. La transformación puede modificar el tamaño de nuestro modelo, moverlo o rotarlo.

También se conoce como la matriz de posición, o la matriz de modelo.

Usualmente es obtenida mediante la clase `MatrixStack` (Pila de Matrices), la cual puede ser obtenida mediante el objeto de `DrawContext`:

```java
drawContext.getMatrices().peek().getPositionMatrix();
```

#### Un Ejemplo Práctico: Renderizar una Banda de Triángulos

Es más facil explicar como escribir al `BufferBuilder` usando un ejemplo práctico. Digamos que queremos renderizar algo usando el modo de dibujado de `DrawMode.TRIANGLE_STRIP` y el formato de vértices de `POSITION_COLOR`.

Vamos a dibujar vértices usando los siguientes puntos en el HUD (en orden):

```txt
(20, 20)
(5, 40)
(35, 40)
(20, 60)
```

Esto nos debería dar un hermoso diamante - ya que estamos usando el modo de dibujado de `TRIANGLE_STRIP`, el renderizado realizará los siguientes pasos:

![Cuatro pasos que muestran el posicionamiento de los vértices en la pantalla para formar dos triángulos](/assets/develop/rendering/concepts-practical-example-draw-process.png)

Como estamos dibujando en el HUD en este ejemplo, usaremos el evento de `HudRenderCallback`:

@[code lang=java transcludeWith=:::1](@/reference/latest/src/client/java/com/example/docs/rendering/RenderingConceptsEntrypoint.java)

Esto resulta en lo siguiente siendo dibujado en el HUD:

![Resultado Final](/assets/develop/rendering/concepts-practical-example-final-result.png)

:::tip
¡Intenta jugar con los colores y las posiciones del vértice para ver que pasa! También puedes intentar usar diferentes modos de dibujado y formatos de vértice.
:::

## El `MatrixStack`

Después de aprender a escribir al `BufferBuilder`, puede que te preguntes como transformar el modelo - o incluso animarlo. Aquí es donde entra la clase `MatrixStack`.

La clase `MatrixStack` tiene los siguientes métodos:

- `push()` - Empuja una nueva matrix sobre la pila.
- `pop()` - Suelta la matriz en el tope de la pila.
- `peek()` - Retorna la matriz en el tope de la pila.
- `translate(x, y, z)` - Traslada la matriz en el tope de la pila.
- `scale(x, y, z)` - Cambia el tamaño de la matriz en el tope de la pila.

También puedes multiplicar la matriz en el tope de la pila usando cuaterniones, los cuales cubriremos en la siguiente sección.

Tomando el ejemplo de arriba, podemos hacer que nuestro diamante aumente o disminuya su tamaño usando el `MatrixStack` y el `tickDelta` - el cual es el tiempo que ha pasado desde el último frame.

::: warning
You must first push the matrix stack and then pop it after you're done with it. If you don't, you'll end up with a broken matrix stack, which will cause rendering issues.

¡Asegúrate de empujar la pila de matrices antes de tener una matriz de transformación!
:::

@[code lang=java transcludeWith=:::2](@/reference/latest/src/client/java/com/example/docs/rendering/RenderingConceptsEntrypoint.java)

![Un video mostrando el diamante aumentando y disminuyendo su tamaño](/assets/develop/rendering/concepts-matrix-stack.webp)

## Cuaterniones (Para Rotar Cosas)

Los cuaterniones son una manera de representar rotaciones en un espacio tridimensional. Son usados para rotar la matriz en el tope de la pila en el `MatrixStack` usando el método de `multiply(Quaternion, x, y, z)`.

Es muy improbable que tengas que usar una clase de `Quaternion` directamente, ya que Minecraft nos da varias instancias de `Quaternion` ya incluidas en la clase de utilidades de `RotationAxis`.

Digamos que queremos rotar nuestro diamante alrededor del eje z. Podemos hacer esto mediante la clase `MatrixStack` y el método `multiply(Quaternion, x, y, z)`.

@[code lang=java transcludeWith=:::3](@/reference/latest/src/client/java/com/example/docs/rendering/RenderingConceptsEntrypoint.java)

El resultado será el siguiente:

![Un video que muestra el diamante rotando alrededor del eje z](/assets/develop/rendering/concepts-quaternions.webp)
