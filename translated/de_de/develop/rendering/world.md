---
title: Rendering in der Welt
description: Erstelle und verwende benutzerdefinierte Render-Pipelines, wenn Vanilla-Pipelines deinen Anforderungen nicht entsprechen.
authors:
  - AzureAaron
  - kevinthegreat1
---

<!---->

:::info VORAUSSETZUNGEN

Stelle sicher, dass du zuerst [Grundlegende Rendering-Konzepte](./basic-concepts) gelesen hast. Diese Seite baut auf diesen Konzepten auf und diskutiert, wie man Objekte in der Welt rendert.

Diese Seite erkundet einige modernere Rendering-Konzepte. Du wirst mehr über die zwei aufgeteilten Phasen des Rendering erfahren: "Extraktion" (oder "Vorbereitung") und "Zeichnen" (oder "Rendering"). In diesem Leitfaden bezeichnen wir die Phase "Extraktion/Vorbereitung" als "Extraktionsphase" und die Phase "Zeichnen/Rendern" als "Zeichenphase".

:::

Um benutzerdefinierte Objekte in der Welt zu rendern, hast du zwei Möglichkeiten. Du kannst in das vorhandene Vanilla-Rendering injizieren und deinen Code hinzufügen, aber dadurch bist du auf die vorhandenen Vanilla-Render-Pipelines beschränkt. Wenn die vorhandenen Vanilla-Render-Pipelines deinen Anforderungen nicht entsprechen, benötigst du eine benutzerdefinierte Render-Pipeline.

Bevor wir uns mit benutzerdefinierten Render-Pipelines befassen, wollen wir uns zunächst das Vanilla-Rendering ansehen.

## Die Extraktions- und Zeichenphasen {#the-extraction-and-drawing-phases}

Wie in [Grundlegende Rendering-Konzepte](./basic-concepts) erwähnt, arbeiten die aktuellen Minecraft-Updates daran, das Rendering in zwei Phasen aufzuteilen: "Extraktion" und "Zeichnen".

Alle für das Rendering erforderlichen Daten werden während der "Extraktionsphase" gesammelt. Dazu gehört beispielsweise das Schreiben in den gepufferten Builder. Das Schreiben von Eckpunkten in den gepufferten Builder über `buffer.addVertex` ist Teil der "Extraktionsphase". Beachte, dass viele Methoden zwar mit "draw" oder "render" beginnen, jedoch während der "Extraktionsphase" aufgerufen werden sollten. Du solltest alle Elemente hinzufügen, die du während dieser Phase rendern möchtest.

Wenn die "Extraktionsphase" abgeschlossen ist, beginnt die "Zeichenphase" und der gepufferte Builder wird erstellt. Während dieser Phase wird der gepufferte Builder auf den Bildschirm gezeichnet. Das ultimative Ziel dieser Aufteilung in "Extraktion" und "Zeichnen" besteht darin, das Zeichnen des vorherigen Bildern parallel zum Extrahieren des nächsten Bilds zu ermöglichen und so die Leistung zu verbessern.

Nachdem wir nun diese beiden Phasen kennengelernt haben, wollen wir uns ansehen, wie man eine benutzerdefinierte Render-Pipeline erstellt.

## Benutzerdefinierte Render-Pipelines {#custom-render-pipelines}

Nehmen wir an, wir möchten Wegpunkte rendern, die durch Wände hindurch sichtbar sein sollen. Die nächstgelegene Vanilla-Pipeline dafür wäre `RenderPipelines#DEBUG_FILLED_BOX`, aber diese rendert nicht durch Wände hindurch, sodass wir eine benutzerdefinierte Render-Pipeline benötigen.

### Eine benutzerdefinierte Render-Pipeline definieren {#defining-a-custom-render-pipeline}

Wir definieren benutzerdefinierte Render-Pipelines in einer Klasse:

@[code lang=java transcludeWith=:::custom-pipelines:define-pipeline](@/reference/latest/src/client/java/com/example/docs/rendering/CustomRenderPipeline.java)

### Extraktionsphase {#extraction-phase}

Wir implementieren zuerst die "Extraktionsphase". Wir können diese Methode während der "Extraktionsphase" aufrufen, um einen Wegpunkt hinzuzufügen, der gerendert werden soll.

@[code lang=java transcludeWith=:::custom-pipelines:extraction-phase](@/reference/latest/src/client/java/com/example/docs/rendering/CustomRenderPipeline.java)

Beachte, dass die im Konstruktor von `BufferAllocator` verwendete Größe von der verwendeten Render-Pipeline abhängt. In unserem Fall ist es `RenderType.SMALL_BUFFER_SIZE`.

Wenn du mehrere Wegpunkte rendern möchtest, rufe diese Methode mehrmals auf. Stelle sicher, dass du dies während der "Extraktionsphase" machst, BEVOR die "Zeichenphase" beginnt, in der der Puffer-Builder gebaut wird.

### Renderzustände {#render-states}

Beachte, dass wir im obigen Code den `BufferBuilder` in einem Feld speichern. Das liegt daran, dass wir es in der "Zeichnungsphase" benötigen. In diesem Fall ist der `BufferBuilder` unser "Renderzustand" oder unsere "extrahierten Daten". Wenn du während der "Zeichenphase" zusätzliche Daten benötigst, solltest du eine benutzerdefinierte Render-Zustandklasse erstellen, um den `BufferedBuilder` und alle zusätzlichen Rendering-Daten, die du benötigst, zu speichern.

### Zeichenphase {#drawing-phase}

Jetzt werden wir die "Zeichenphase" implementieren. Dies sollte aufgerufen werden, nachdem alle Wegpunkte, die du rendern möchtest, während der "Extraktionsphase" zum `BufferBuilder` hinzugefügt wurden.

@[code lang=java transcludeWith=:::custom-pipelines:drawing-phase](@/reference/latest/src/client/java/com/example/docs/rendering/CustomRenderPipeline.java)

### Aufräumen {#cleaning-up}

Schließlich müssen wir die Ressourcen bereinigen, wenn der Spielrenderer geschlossen wird. `GameRenderer#close` sollte diese Methode aufrufen, und dafür musst du derzeit mit einem Mixin in `GameRenderer#close` injizieren.

@[code lang=java transcludeWith=:::custom-pipelines:clean-up](@/reference/latest/src/client/java/com/example/docs/rendering/CustomRenderPipeline.java)

@[code lang=java](@/reference/latest/src/client/java/com/example/docs/mixin/client/GameRendererMixin.java)

### Finaler Code {#final-code}

Durch die Kombination aller oben genannten Schritte, erhalten wir eine einfache Klasse, die einen Wegpunkt bei `(0, 100, 0)` durch Wände hindurch rendert.

@[code lang=java](@/reference/latest/src/client/java/com/example/docs/rendering/CustomRenderPipeline.java)

Vergiss auch nicht das `GameRendererMixin`! Hier ist das Ergebnis:

![Ein Wegpunkt, der durch Wände hindurch gerendert wird](/assets/develop/rendering/world-rendering-custom-render-pipeline-waypoint.png)
