---
title: Grundlegende Rendering-Konzepte
description: Lerne, die grundlegenden Konzepte des Renderings mit der Rendering-Engine von Minecraft kennen.
authors:
  - "0x3C50"
  - IMB11
  - MildestToucan
---

::: warning
Although Minecraft is built using OpenGL, as of version 1.17+ you cannot use legacy OpenGL methods to render your own things. Instead, you must use the new `BufferBuilder` system, which formats rendering data and uploads it to OpenGL to draw.

Zusammenfassend kann man sagen, dass man das Rendering-System von Minecraft benutzen muss, oder ein eigenes, das `GL.glDrawElements()` benutzt.
:::

:::warning WICHTIGES UPDATE

Beispielsweise wurde dieses Modell in 1.21.8 für das GUI-Rendering übernommen, und die Methoden von `GuiGraphics` fügen einfach zum Renderstatus hinzu. Das eigentliche Hochladen in den `BufferBuilder` erfolgt am Ende der Vorbereitungsphase, nachdem alle Elemente zum `RenderState` hinzugefügt wurden. Siehe `GuiRenderer#prepare`.

Dieser Artikel behandelt die Grundlagen des Renderns und ist zwar nach wie vor relevant, doch in den meisten Fällen gibt es höhere Abstraktionsebenen für eine bessere Leistung und Kompatibilität. Für weitere Informationen siehe [Rendering in der Welt](./world).
:::

Auf dieser Seite werden die Grundlagen des Renderings mit dem neuen System behandelt, wobei die wichtigsten Begriffe und Konzepte erläutert werden.

Obwohl ein Großteil des Renderings in Minecraft durch die verschiedenen Methoden von `GuiGraphics` abstrahiert wird und du wahrscheinlich nichts von dem, was hier erwähnt wird, anfassen musst, ist es trotzdem wichtig, die Grundlagen zu verstehen, wie Rendering funktioniert.

## Der `Tesselator` {#the-tesselator}

Der `Tesselator` ist die Hauptklasse, die zum Rendern von Dingen in Minecraft verwendet wird. Es ist ein Singleton, das heißt es gibt nur eine Instanz davon im Spiel. Du kannst die Instanz mit `Tesselator.getInstance()` erhalten.

## Der `BufferBuilder` {#the-bufferbuilder}

Der `BufferBuilder` ist die Klasse, die zum Formatieren und Hochladen von Rendering-Daten in OpenGL verwendet wird. Sie wird verwendet, um einen Puffer zu erstellen, der dann zum Zeichnen in OpenGL hochgeladen wird.

Der `Tesselator` wird verwendet, um einen `BufferBuilder` zu erstellen, der zum Formatieren und Hochladen von Rendering-Daten in OpenGL verwendet wird.

### Den `BufferBuilder` initialisieren {#initializing-the-bufferbuilder}

Bevor du etwas in den `BufferBuilder` schreiben kannst, musst du ihn initialisieren. Dies geschieht mit der Methode `Tesselator#begin(...)`, die ein `VertexFormat` und einen Zeichenmodus entgegennimmt und einen `BufferBuilder` zurückgibt.

#### Vertex Formate {#vertex-formats}

Das `VertexFormat` definiert die Elemente, die wir in unseren Datenpuffer aufnehmen und umreißt, wie diese Elemente an OpenGL übertragen werden sollen.

Die folgenden Standard `VertexFormat` Elemente stehen in `DefaultVertexFormat` zur Verfügung:

| Element                       | Format                                                                                  |
| ----------------------------- | --------------------------------------------------------------------------------------- |
| `EMPTY`                       | `{ }`                                                                                   |
| `BLOCK`                       | `{ position, color, texture uv, texture light (2 shorts), texture normal (3 sbytes) }`  |
| `NEW_ENTITY`                  | `{ position, color, texture uv, overlay (2 shorts), texture light, normal (3 sbytes) }` |
| `PARTICLE`                    | `{ position, texture uv, color, texture light }`                                        |
| `POSITION`                    | `{ position }`                                                                          |
| `POSITION_COLOR`              | `{ position, color }`                                                                   |
| `POSITION_COLOR_NORMAL`       | `{ position, color, normal }`                                                           |
| `POSITION_COLOR_LIGHTMAP`     | `{ position, color, light }`                                                            |
| `POSITION_TEX`                | `{ position, uv }`                                                                      |
| `POSITION_TEX_COLOR`          | `{ position, uv, color }`                                                               |
| `POSITION_COLOR_TEX_LIGHTMAP` | `{ position, color, uv, light }`                                                        |
| `POSITION_TEX_LIGHTMAP_COLOR` | `{ position, uv, light, color }`                                                        |
| `POSITION_TEX_COLOR_NORMAL`   | `{ position, uv, color, normal }`                                                       |

#### Zeichenmodi {#draw-modes}

Der Zeichenmodus legt fest, wie die Daten gezeichnet werden. Die folgenden Zeichenmodi sind in `VertexFormat.Mode` verfügbar:

| Zeichenmodus       | Beschreibung                                                                                                                                                                           |
| ------------------ | -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| `LINES`            | Jedes Element besteht aus 2 Eckpunkten und wird als eine einzige Linie dargestellt.                                                                                    |
| `LINE_STRIP`       | Das erste Element benötigt 2 Eckpunkte. Zusätzliche Elemente werden nur mit einem neuen Eckpunkt gezeichnet, wodurch eine durchgehende Linie entsteht. |
| `DEBUG_LINES`      | Ähnlich wie `Mode.LINES`, aber die Linie ist immer genau ein Pixel breit auf dem Bildschirm.                                                                           |
| `DEBUG_LINE_STRIP` | Wie `Mode.LINE_STRIP`, aber die Linien sind immer ein Pixel breit.                                                                                                     |
| `TRIANGLES`        | Jedes Element besteht aus 3 Eckpunkten, die ein Dreieck bilden.                                                                                                        |
| `TRIANGLE_STRIP`   | Beginnt mit 3 Eckpunkten für das erste Dreieck. Jeder weitere Eckpunkt bildet ein neues Dreieck mit den letzten beiden Eckpunkten.                     |
| `TRIANGLE_FAN`     | Beginnt mit 3 Eckpunkten für das erste Dreieck. Jeder weitere Scheitelpunkt bildet ein neues Dreieck mit dem ersten und dem letzten Scheitelpunkt.     |
| `QUADS`            | Jedes Element besteht aus 4 Scheitelpunkten, die ein Viereck bilden.                                                                                                   |

### In den `BufferBuilder` schreiben {#writing-to-the-bufferbuilder}

Sobald der `BufferBuilder` initialisiert ist, kannst du Daten in ihn schreiben.

Der `BufferBuilder` erlaubt uns, unseren Puffer Punkt für Punkt zu konstruieren. Um einen Vertex hinzuzufügen, verwenden wir die Methode `buffer.addVertex(Matrix4f, float, float, float)`. Der Parameter `Matrix4f` ist die Transformationsmatrix, auf die wir später noch näher eingehen werden. Die drei Float-Parameter stellen die (x, y, z) Koordinaten der Eckpunktposition dar.

Diese Methode gibt einen Eckpunkt-Builder zurück, den wir verwenden können, um zusätzliche Informationen für den Eckpunkt anzugeben. Es ist wichtig, dass die Reihenfolge der von uns definierten `VertexFormat` beim Hinzufügen dieser Informationen eingehalten wird. Andernfalls könnte OpenGL unsere Daten nicht richtig interpretieren. Nachdem wir mit der Erstellung eines Scheitelpunktes fertig sind, füge einfach weitere Scheitelpunkte und Daten in den Puffer ein, bis du fertig bist.

Es lohnt sich auch, das Konzept des Culling zu verstehen. Culling ist der Prozess, bei dem Flächen einer 3D-Form entfernt werden, die aus der Perspektive des Betrachters nicht sichtbar sind. Wenn die Eckpunkte für eine Fläche in der falschen Reihenfolge angegeben werden, wird die Fläche aufgrund von Culling möglicherweise nicht korrekt dargestellt.

#### Was ist eine Transformationsmatrix? {#what-is-a-transformation-matrix}

Eine Transformationsmatrix ist eine 4x4-Matrix, die zur Transformation eines Vektors verwendet wird. In Minecraft transformiert die Transformationsmatrix lediglich die Koordinaten, die wir in den Aufruf von `addVertex` hineingeben. Mit den Transformationen kann unser Modell skaliert, verschoben und gedreht werden.

Sie wird manchmal auch als Positionsmatrix oder als Modellmatrix bezeichnet.

Es wird normalerweise über die Klasse `Matrix3x2fStack` bezogen, die über das Objekt von `GuiGraphics` über einen Aufruf von `GuiGraphics#pose()` bezogen werden kann.

#### Ein praktisches Beispiel: Rendering eines Dreiecksstreifens {#rendering-a-triangle-strip}

Es ist einfacher, anhand eines praktischen Beispiels zu erklären, wie man in den `BufferBuilder` schreibt. Nehmen wir an, wir wollen etwas mit dem Zeichenmodus `VertexFormat.Mode.TRIANGLE_STRIP` und dem Vertexformat `POSITION_COLOR` rendern.

Wir werden Eckpinkt an den folgenden Punkten auf dem HUD zeichnen (in dieser Reihenfolge):

```:no-line-numbers
(20, 20)
(5, 40)
(35, 40)
(20, 60)
```

Dies sollte einen schönen Diamanten ergeben - da wir den Zeichenmodus `TRIANGLE_STRIP` verwenden, wird der Renderer die folgenden Schritte durchführen:

![Vier Schritte, die die Platzierung der Eckpunkte auf der Oberfläche zeigen, um zwei Dreiecke zu formen](/assets/develop/rendering/concepts-practical-example-draw-process.png)

Da wir in diesem Beispiel auf dem HUD zeichnen, werden wir die `HudElementRegistry` verwenden:

:::warning WICHTIGES UPDATE

Außerdem stimmt der folgende Code nicht vollständig mit der obigen Erklärung überein: Du musst nicht manuell in den `BufferBuilder` schreiben, da die Methoden von `GuiGraphics` während der Vorbereitung automatisch in den `BufferBuilder` des HUD schreiben.

Lies für weitere Informationen die wichtige Aktualisierung oben.
:::

**Registrierung des Elements:**

@[code lang=java transcludeWith=:::registration](@/reference/latest/src/client/java/com/example/docs/rendering/RenderingConceptsEntrypoint.java)

**Implementation von `hudLayer()`:**

@[code lang=java transcludeWith=:::hudLayer](@/reference/latest/src/client/java/com/example/docs/rendering/RenderingConceptsEntrypoint.java)

Dies führt dazu, dass auf dem HUD folgendes gezeichnet wird:

![Endergebnis](/assets/develop/rendering/concepts-practical-example-final-result.png)

:::tip
Versuche, mit den Farben und Positionen der Eckpunkte herumzuspielen, um zu sehen, was passiert! Du kannst auch verschiedene Zeichenmodi und Vertex-Formate ausprobieren.
:::

## Der `PoseStack` {#the-posestack}

::: warning
This section's code and the text are discussing different things!

Der Code zeigt `Matrix3x2fStack`, das seit 1.21.8 für das HUD-Rendering verwendet wird, während der Text `PoseStack` beschreibt, das leicht abweichende Methoden hat.

Lies für weitere Informationen die wichtige Aktualisierung oben.
:::

Nachdem du gelernt hast, wie man in den `BufferBuilder` schreibt, fragst du dich vielleicht, wie du dein Modell transformieren oder sogar animieren kannst. Hier kommt die Klasse `PoseStack` ins Spiel.

Die Klasse `PoseStack` hat folgende Methoden:

- `pushPose()` - Schiebt eine neue Matrix auf den Stack.
- `popPose()` - Nimmt die oberste Matrix vom Stapel.
- `popPose()` - Gibt die oberste Matrix des Stapels zurück.
- `translate(x, y, z)` - Verschiebt die oberste Matrix auf dem Stapel.
- `translate(vec3)`
- `scale(x, y, z)` - Skaliert die oberste Matrix auf dem Stapel.

Du kannst auch die oberste Matrix auf dem Stapel mit Quaternionen multiplizieren, was wir im nächsten Abschnitt behandeln werden.

Ausgehend von unserem obigen Beispiel können wir unseren Diamanten nach oben und unten skalieren, indem wir den `PoseStack` und `tickDelta` verwenden - was der "Fortschritt" zwischen dem letztem Spieltick und dem nächsten Spieltick ist. Wir werden dies später auf der Seite [Rendering im HUD](./hud#render-tick-counter) erläutern.

::: warning
You must first push the matrix stack and then pop it after you're done with it. If you don't, you'll end up with a broken matrix stack, which will cause rendering issues.

Stelle sicher, dass du den Matrixstapel verschiebst, bevor du eine Transformationsmatrix erhältst!
:::

@[code lang=java transcludeWith=:::2](@/reference/latest/src/client/java/com/example/docs/rendering/RenderingConceptsEntrypoint.java)

![Ein Video, das die Vergrößerung und Verkleinerung eines Diamanten zeigt](/assets/develop/rendering/concepts-matrix-stack.webp)

## Quaternionen (rotierende Dinge) {#quaternions-rotating-things}

::: warning
This section's code and the text are discussing different things!

Der Code zeigt das Rendering auf dem HUD, während der Text das Rendern des 3D-Weltraums beschreibt.

Lies für weitere Informationen die wichtige Aktualisierung oben.
:::

Quaternionen sind eine Methode zur Darstellung von Drehungen im 3D-Raum. Sie werden verwendet, um die oberste Matrix auf dem `PoseStack` über die Methode `rotateAround(quaternionfc, x, y, z)` zu rotieren.

Es ist sehr unwahrscheinlich, dass du jemals eine Quaternion-Klasse direkt verwenden musst, da Minecraft verschiedene vorgefertigte Quaternion-Instanzen in seiner `Axis` Hilfsklasse bereitstellt.

Nehmen wir an, wir wollen unseren Quader um die Z-Achse drehen. Wir können dies tun, indem wir den `PoseStack` und die Methode `rotateAround(quaternionfc, x, y, z)` verwenden.

@[code lang=java transcludeWith=:::3](@/reference/latest/src/client/java/com/example/docs/rendering/RenderingConceptsEntrypoint.java)

Daraus ergibt sich Folgendes:

![Ein Video, das die Drehung des Diamanten um die Z-Achse zeigt](/assets/develop/rendering/concepts-quaternions.webp)
