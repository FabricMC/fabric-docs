---
title: Den Zeichenkontext verwenden
description: Lerne, wie man die Klasse DrawContext verwendet, um verschiedene Formen, Texte und Texturen zu rendern.
authors:
  - IMB11

search: false
---

# Den Zeichenkontext verwenden

Diese Seite setzt voraus, dass du einen Blick auf die Seite [Grundlegende Rendering-Konzepte](./basic-concepts) geworfen hast.

Die Klasse `DrawContext` ist die Hauptklasse, die für das Rendering im Spiel verwendet wird. Sie wird für das Rendern von Formen, Text und Texturen verwendet und, wie zuvor gesehen, für die Bearbeitung von `MatrixStack`s und die Verwendung von `BufferBuilder`n.

## Formen zeichnen

Die Klasse `DrawContext` kann verwendet werden, um auf einfache Weise **quadratische** Formen zu zeichnen. Wenn du Dreiecke oder andere nicht-quadratische Formen zeichnen willst, musst du einen `BufferBuilder` verwenden.

### Zeichnen von Dreiecken

Du kannst die Methode `DrawContext.fill(...)` verwenden, um ein gefülltes Rechteck zu zeichnen.

@[code lang=java transcludeWith=:::1](@/reference/latest/src/client/java/com/example/docs/rendering/DrawContextExampleScreen.java)

![Ein Rechteck](/assets/develop/rendering/draw-context-rectangle.png)

### Zeichnen von Umrissen/Rahmen

Nehmen wir an, wir wollen das Rechteck, das wir gerade gezeichnet haben, umreißen. Wir können die Methode `DrawContext.drawBorder(...)` verwenden, um einen Umriss zu zeichnen.

@[code lang=java transcludeWith=:::2](@/reference/latest/src/client/java/com/example/docs/rendering/DrawContextExampleScreen.java)

![Ein Rechteck mit einer Umrandung](/assets/develop/rendering/draw-context-rectangle-border.png)

### Zeichnen von individuellen Linien

Wir können die Methoden `DrawContext.drawHorizontalLine(...)` und `DrawContext.drawVerticalLine(...)` verwenden, um Linien zu zeichnen.

@[code lang=java transcludeWith=:::3](@/reference/latest/src/client/java/com/example/docs/rendering/DrawContextExampleScreen.java)

![Linien](/assets/develop/rendering/draw-context-lines.png)

## Der Scheren-Manager

Die Klasse `DrawContext` hat einen eingebauten Scheren-Manager. So kannst du dein Rendering ganz einfach auf einen bestimmten Bereich beschränken. Dies ist nützlich für das Rendern von Dingen wie Tooltips oder anderen Elementen, die nicht außerhalb eines bestimmten Bereichs gerendert werden sollen.

### Den Scheren-Manager nutzen

:::tip
Scheren-Regionen können verschachtelt werden! Stelle sicher, dass du den Scheren-Manager genauso oft deaktivierst, wie du ihn aktiviert hast.
:::

Um den Scheren-Manager zu aktivieren, verwende einfach die Methode `DrawContext.enableScissor(...)`. Um den Scheren-Manager zu deaktivieren, verwende die Methode `DrawContext.disableScissor()`.

@[code lang=java transcludeWith=:::4](@/reference/latest/src/client/java/com/example/docs/rendering/DrawContextExampleScreen.java)

[Scheren-Region in Aktion](/assets/develop/rendering/draw-context-scissor.png)

Wie du siehst, wird der Farbverlauf nur innerhalb der Scheren-Region gerendert, obwohl wir der Oberfläche sagen, dass sie den Farbverlauf über die gesamte Oberfläche rendern soll.

## Zeichnen von Texturen

Es gibt nicht den einen "richtigen" Weg, um Texturen auf einen Bildschirm zu zeichnen, da die Methode `drawTexture(...)` viele verschiedene Überladungen hat. In diesem Abschnitt werden die häufigsten Anwendungsfälle behandelt.

### Zeichnen einer ganzen Textur

Im Allgemeinen wird empfohlen, dass man die Überladung verwendet, die die Parameter `textureWidth` und `textureHeight` angibt. Der Grund dafür ist, dass die Klasse `DrawContext` diese Werte entgegennimmt, wenn du sie nicht angibst, was manchmal falsch sein kann.

@[code lang=java transcludeWith=:::5](@/reference/latest/src/client/java/com/example/docs/rendering/DrawContextExampleScreen.java)

![Beispiel für das Zeichnen einer ganzen Textur](/assets/develop/rendering/draw-context-whole-texture.png)

### Zeichnen eines Teils einer Textur

Hier kommen `u` und `v` ins Spiel. Diese Parameter geben die obere linke Ecke der zu zeichnenden Textur an, und die Parameter `regionWidth` und `regionHeight` geben die Größe des zu zeichnenden Teils der Textur an.

Nehmen wir diese Textur als Beispiel.

![Textur des Rezeptbuchs](/assets/develop/rendering/draw-context-recipe-book-background.png)

Wenn wir nur einen Bereich zeichnen wollen, der die Lupe enthält, können wir die folgenden Werte `u`, `v`, `regionWidth` und `regionHeight` verwenden:

@[code lang=java transcludeWith=:::6](@/reference/latest/src/client/java/com/example/docs/rendering/DrawContextExampleScreen.java)

![Textur der Region](/assets/develop/rendering/draw-context-region-texture.png)

## Zeichnen von Text

Die Klasse `DrawContext` verfügt über verschiedene selbsterklärende Methoden zum Rendern von Texten, die hier aus Gründen der Kürze nicht behandelt werden.

Nehmen wir an, wir wollen "Hello World" auf die Oberfläche zeichnen. Wir können dazu die Methode `DrawContext.drawText(...)` verwenden.

@[code lang=java transcludeWith=:::7](@/reference/latest/src/client/java/com/example/docs/rendering/DrawContextExampleScreen.java)

![Einen Text zeichnen](/assets/develop/rendering/draw-context-text.png)
