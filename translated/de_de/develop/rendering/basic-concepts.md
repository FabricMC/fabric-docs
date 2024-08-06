---
title: Grundlegende Rendering-Konzepte
description: Lerne, die grundlegenden Konzepte des Renderings mit der Rendering-Engine von Minecraft kennen.
authors:
  - IMB11
  - "0x3C50"
---

# Grundlegende Rendering-Konzepte {#basic-rendering-concepts}

::: warning
Although Minecraft is built using OpenGL, as of version 1.17+ you cannot use legacy OpenGL methods to render your own things. Instead, you must use the new `BufferBuilder` system, which formats rendering data and uploads it to OpenGL to draw.

Zusammenfassend kann man sagen, dass man das Rendering-System von Minecraft benutzen muss, oder ein eigenes, das `GL.glDrawElements()` benutzt.
:::

Auf dieser Seite werden die Grundlagen des Renderings mit dem neuen System behandelt, wobei die wichtigsten Begriffe und Konzepte erläutert werden.

Obwohl ein Großteil des Renderings in Minecraft durch die verschiedenen `DrawContext`-Methoden abstrahiert wird und du wahrscheinlich nichts von dem, was hier erwähnt wird, anfassen musst, ist es trotzdem wichtig, die Grundlagen zu verstehen, wie Rendering funktioniert.

## Der `Tessellator` {#the-tessellator}

Der `Tessellator` ist die Hauptklasse, die zum Rendern von Dingen in Minecraft verwendet wird. Es ist ein Singleton, das heißt es gibt nur eine Instanz davon im Spiel. Du kannst die Instanz mit `Tessellator.getInstance()` erhalten.

## Der `BufferBuilder` {#the-bufferbuilder}

Der `BufferBuilder` ist die Klasse, die zum Formatieren und Hochladen von Rendering-Daten in OpenGL verwendet wird. Sie wird verwendet, um einen Puffer zu erstellen, der dann zum Zeichnen in OpenGL hochgeladen wird.

Der `Tessellator` wird verwendet, um einen `BufferBuilder` zu erstellen, der zum Formatieren und Hochladen von Rendering-Daten in OpenGL verwendet wird.

### Den `BufferBuilder` initialisieren {#initializing-the-bufferbuilder}

Bevor du etwas in den `BufferBuilder` schreiben kannst, musst du ihn initialisieren. Dies geschieht mit der Methode `Tessellator#begin(...)`, die ein `VertexFormat` und einen Zeichenmodus entgegennimmt und einen `BufferBuilder` zurückgibt.

#### Vertex Formate {#vertex-formats}

Das `VertexFormat` definiert die Elemente, die wir in unseren Datenpuffer aufnehmen und umreißt, wie diese Elemente an OpenGL übertragen werden sollen.

Die folgenden `VertexFormat` Elemente sind verfügbar:

| Element                                       | Format                                                                                  |
| --------------------------------------------- | --------------------------------------------------------------------------------------- |
| `BLIT_SCREEN`                                 | `{ position (3 floats: x, y and z), uv (2 floats), color (4 ubytes) }`                  |
| `POSITION_COLOR_TEXTURE_LIGHT_NORMAL`         | `{ position, color, texture uv, texture light (2 shorts), texture normal (3 sbytes) }`  |
| `POSITION_COLOR_TEXTURE_OVERLAY_LIGHT_NORMAL` | `{ position, color, texture uv, overlay (2 shorts), texture light, normal (3 sbytes) }` |
| `POSITION_TEXTURE_COLOR_LIGHT`                | `{ position, texture uv, color, texture light }`                                        |
| `POSITION`                                    | `{ position }`                                                                          |
| `POSITION_COLOR`                              | `{ position, color }`                                                                   |
| `LINES`                                       | `{ position, color, normal }`                                                           |
| `POSITION_COLOR_LIGHT`                        | `{ position, color, light }`                                                            |
| `POSITION_TEXTURE`                            | `{ position, uv }`                                                                      |
| `POSITION_COLOR_TEXTURE`                      | `{ position, color, uv }`                                                               |
| `POSITION_TEXTURE_COLOR`                      | `{ position, uv, color }`                                                               |
| `POSITION_COLOR_TEXTURE_LIGHT`                | `{ position, color, uv, light }`                                                        |
| `POSITION_TEXTURE_LIGHT_COLOR`                | `{ position, uv, light, color }`                                                        |
| `POSITION_TEXTURE_COLOR_NORMAL`               | `{ position, uv, color, normal }`                                                       |

#### Zeichenmodi {#draw-modes}

Der Zeichenmodus legt fest, wie die Daten gezeichnet werden. Die folgenden Zeichenmodi sind verfügbar:

| Zeichenmodus                | Beschreibung                                                                                                                                                                           |
| --------------------------- | -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| `DrawMode.LINES`            | Jedes Element besteht aus 2 Eckpunkten und wird als eine einzige Linie dargestellt.                                                                                    |
| `DrawMode.LINE_STRIP`       | Das erste Element benötigt 2 Eckpunkte. Zusätzliche Elemente werden nur mit einem neuen Eckpunkt gezeichnet, wodurch eine durchgehende Linie entsteht. |
| `DrawMode.DEBUG_LINES`      | Ähnlich wie `DrawMode.LINES`, aber die Linie ist immer genau ein Pixel breit auf dem Bildschirm.                                                                       |
| `DrawMode.DEBUG_LINE_STRIP` | Wie `DrawMode.LINE_STRIP`, aber die Linien sind immer ein Pixel breit.                                                                                                 |
| `DrawMode.TRIANGLES`        | Jedes Element besteht aus 3 Eckpunkten, die ein Dreieck bilden.                                                                                                        |
| `DrawMode.TRIANGLE_STRIP`   | Beginnt mit 3 Eckpunkten für das erste Dreieck. Jeder weitere Eckpunkt bildet ein neues Dreieck mit den letzten beiden Eckpunkten.                     |
| `DrawMode.TRIANGLE_FAN`     | Beginnt mit 3 Eckpunkten für das erste Dreieck. Jeder weitere Scheitelpunkt bildet ein neues Dreieck mit dem ersten und dem letzten Scheitelpunkt.     |
| `DrawMode.QUADS`            | Jedes Element besteht aus 4 Scheitelpunkten, die ein Viereck bilden.                                                                                                   |

### In den `BufferBuilder` schreiben

Sobald der `BufferBuilder` initialisiert ist, kannst du Daten in ihn schreiben.

Der `BufferBuilder` erlaubt uns, unseren Puffer Punkt für Punkt zu konstruieren. Um einen Eckpunkt hinzuzufügen, verwenden wir die Methode `buffer.vertex(Matrix, float, float, float)`. Der Parameter `matrix` ist die Transformationsmatrix, auf die wir später noch näher eingehen werden. Die drei Float-Parameter stellen die (x, y, z) Koordinaten der Eckpunktposition dar.

Diese Methode gibt einen Eckpunkt-Builder zurück, den wir verwenden können, um zusätzliche Informationen für den Eckpunkt anzugeben. Es ist wichtig, dass die Reihenfolge der von uns definierten `VertexFormat` beim Hinzufügen dieser Informationen eingehalten wird. Andernfalls könnte OpenGL unsere Daten nicht richtig interpretieren. Nachdem wir mit der Erstellung eines Scheitelpunktes fertig sind, füge einfach weitere Scheitelpunkte und Daten in den Puffer ein, bis du fertig bist.

Es lohnt sich auch, das Konzept des Culling zu verstehen. Culling ist der Prozess, bei dem Flächen einer 3D-Form entfernt werden, die aus der Perspektive des Betrachters nicht sichtbar sind. Wenn die Eckpunkte für eine Fläche in der falschen Reihenfolge angegeben werden, wird die Fläche aufgrund von Culling möglicherweise nicht korrekt dargestellt.

#### Was ist eine Transformationsmatrix? Zeichenmodi

Eine Transformationsmatrix ist eine 4x4-Matrix, die zur Transformation eines Vektors verwendet wird. In Minecraft transformiert die Transformationsmatrix lediglich die Koordinaten, die wir in den Vertex-Aufruf hineingeben. Mit den Transformationen kann unser Modell skaliert, verschoben und gedreht werden.

Sie wird manchmal auch als Positionsmatrix oder als Modellmatrix bezeichnet.

Es wird normalerweise über die Klasse `MatrixStack` bezogen, die über das Objekt `DrawContext` bezogen werden kann:

```java
drawContext.getMatrices().peek().getPositionMatrix();
```

#### Ein praktisches Beispiel: Rendering eines Dreiecksstreifens

Es ist einfacher, anhand eines praktischen Beispiels zu erklären, wie man in den `BufferBuilder` schreibt. Nehmen wir an, wir wollen etwas mit dem Zeichenmodus `DrawMode.TRIANGLE_STRIP` und dem Vertexformat `POSITION_COLOR` rendern.

Wir werden Eckpinkt an den folgenden Punkten auf dem HUD zeichnen (in dieser Reihenfolge):

```txt
(20, 20)
(5, 40)
(35, 40)
(20, 60)
```

Dies sollte einen schönen Diamanten ergeben - da wir den Zeichenmodus `TRIANGLE_STRIP` verwenden, wird der Renderer die folgenden Schritte durchführen:

![Vier Schritte, die die Platzierung der Eckpunkte auf der Oberfläche zeigen, um zwei Dreiecke zu formen](/assets/develop/rendering/concepts-practical-example-draw-process.png)

Da wir in diesem Beispiel auf dem HUD zeichnen, verwenden wir das Event `HudRenderCallback`:

@[code lang=java transcludeWith=:::1](@/reference/latest/src/client/java/com/example/docs/rendering/RenderingConceptsEntrypoint.java)

Dies führt dazu, dass auf dem HUD folgendes gezeichnet wird:

![Endergebnis](/assets/develop/rendering/concepts-practical-example-final-result.png)

:::tip
Versuche, mit den Farben und Positionen der Eckpunkte herumzuspielen, um zu sehen, was passiert! Du kannst auch verschiedene Zeichenmodi und Vertex-Formate ausprobieren.
:::

## Der `MatrixStack`

Nachdem du gelernt hast, wie man in den `BufferBuilder` schreibt, fragst du dich vielleicht, wie du dein Modell transformieren oder sogar animieren kannst. Hier kommt die Klasse `MatrixStack` ins Spiel.

Die Klasse `MatrixStack` hat folgende Methoden:

- `push()` - Schiebt eine neue Matrix auf den Stack.
- `pop()` - Nimmt die oberste Matrix vom Stapel.
- `peek()` - Gibt die oberste Matrix auf dem Stapel zurück.
- `translate(x, y, z)` - Verschiebt die oberste Matrix auf dem Stapel.
- `scale(x, y, z)` - Skaliert die oberste Matrix auf dem Stapel.

Du kannst auch die oberste Matrix auf dem Stapel mit Quaternionen multiplizieren, was wir im nächsten Abschnitt behandeln werden.

Ausgehend von unserem obigen Beispiel können wir unseren Diamanten nach oben und unten skalieren, indem wir `MatrixStack` und `tickDelta` verwenden - das ist die Zeit, die seit dem letzten Frame vergangen ist.

::: warning
You must first push the matrix stack and then pop it after you're done with it. If you don't, you'll end up with a broken matrix stack, which will cause rendering issues.

Stelle sicher, dass du den Matrixstapel verschiebst, bevor du eine Transformationsmatrix erhältst!
:::

@[code lang=java transcludeWith=:::2](@/reference/latest/src/client/java/com/example/docs/rendering/RenderingConceptsEntrypoint.java)

![Ein Video, das die Vergrößerung und Verkleinerung eines Diamanten zeigt](/assets/develop/rendering/concepts-matrix-stack.webp)

## Quaternionen (rotierende Dinge)

Quaternionen sind eine Methode zur Darstellung von Drehungen im 3D-Raum. Sie werden verwendet, um die oberste Matrix auf dem `MatrixStack` über die Methode `multiply(Quaternion, x, y, z)` zu drehen.

Es ist sehr unwahrscheinlich, dass du jemals eine Quaternion-Klasse direkt benutzen musst, da Minecraft verschiedene vorgefertigte Quaternion-Instanzen in seiner `RotationAxis` Utility-Klasse bereitstellt.

Nehmen wir an, wir wollen unseren Diamanten um die Z-Achse drehen. Wir können dies tun, indem wir den `MatrixStack` und die Methode `multiply(Quaternion, x, y, z)` verwenden.

@[code lang=java transcludeWith=:::3](@/reference/latest/src/client/java/com/example/docs/rendering/RenderingConceptsEntrypoint.java)

Daraus ergibt sich Folgendes:

![Ein Video, das die Drehung des Diamanten um die Z-Achse zeigt](/assets/develop/rendering/concepts-quaternions.webp)
