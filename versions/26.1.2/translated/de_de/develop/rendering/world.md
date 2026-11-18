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

Alle für das Rendering erforderlichen Daten werden während der "Extraktionsphase" gesammelt. Dies schließt beispielsweise den Zugriff auf Weltdaten ein. Beachte, dass viele Methoden zwar mit "draw" oder "render" beginnen, jedoch während der "Extraktionsphase" aufgerufen werden sollten. Du solltest alle Elemente hinzufügen, die du während dieser Phase rendern möchtest.

Wenn die "Extraktionsphase" abgeschlossen ist, beginnt die "Zeichenphase" und der gepufferte Builder wird erstellt. Während dieser Phase wird der gepufferte Builder auf den Bildschirm gezeichnet. Das ultimative Ziel dieser Aufteilung in "Extraktion" und "Zeichnen" besteht darin, das Zeichnen des vorherigen Bildern parallel zum Extrahieren des nächsten Bilds zu ermöglichen und so die Leistung zu verbessern.

Nachdem wir nun diese beiden Phasen kennengelernt haben, wollen wir uns ansehen, wie man eine benutzerdefinierte Render-Pipeline erstellt.

## Benutzerdefinierte Render-Pipelines {#custom-render-pipelines}

Nehmen wir an, wir möchten Wegpunkte rendern, die durch Wände hindurch sichtbar sein sollen. Die nächstgelegene Vanilla-Pipeline dafür wäre `RenderPipelines#DEBUG_FILLED_BOX`, aber diese rendert nicht durch Wände hindurch, sodass wir eine benutzerdefinierte Render-Pipeline benötigen.

### Eine benutzerdefinierte Render-Pipeline definieren {#defining-a-custom-render-pipeline}

Wir definieren benutzerdefinierte Render-Pipelines in einer Klasse:

<<< @/reference/26.1.2/src/client/java/com/example/docs/rendering/CustomRenderPipeline.java#custom_pipelines_define_pipeline

### Extraktionsphase {#extraction-phase}

Wir implementieren zuerst die "Extraktionsphase". Wir können diese Methode während der "Extraktionsphase" aufrufen, um einen Wegpunkt hinzuzufügen, der gerendert werden soll.

<<< @/reference/26.1.2/src/client/java/com/example/docs/rendering/CustomRenderPipeline.java#custom_pipelines_extraction_phase

Wenn du mehrere Wegpunkte rendern möchtest, ändere `waypointState` zu einer Liste und füge mehrere Renderzustände für Wegpunkte hinzu. Stelle sicher, dass du dies während der "Extraktionsphase" machst, BEVOR die "Zeichenphase" beginnt, in der der Puffer-Builder gebaut wird.

### Renderzustände {#render-states}

Beachte, dass wir im obigen Code den `WaypointRenderState` in einem Feld speichern. Das liegt daran, dass wir es in der "Zeichnungsphase" benötigen. In diesem Fall ist der `WaypointRenderState` unser "Renderzustand" oder unsere "extrahierten Daten". Wenn du während der "Zeichenphase" zusätzliche Daten (z. B. aus der Welt) benötigst, solltest du diese in deine benutzerdefinierte Renderzustand-Klasse einfügen.

### Zeichenphase {#drawing-phase}

Jetzt werden wir die "Zeichenphase" implementieren. Dies sollte aufgerufen werden, nachdem alle Wegpunkte, die du rendern möchtest, während der "Extraktionsphase" zum `waypointState` hinzugefügt wurden.

<<< @/reference/26.1.2/src/client/java/com/example/docs/rendering/CustomRenderPipeline.java#custom_pipelines_drawing_phase

Beachte, dass die im Konstruktor des `ByteBufferBuilder` verwendete Größe von der verwendeten Render-Pipeline abhängt. In unserem Fall ist es `RenderType.SMALL_BUFFER_SIZE`.

### Aufräumen {#cleaning-up}

Schließlich müssen wir die Ressourcen bereinigen, wenn der Spielrenderer geschlossen wird. `GameRenderer#close` sollte diese Methode aufrufen, und dafür musst du derzeit mit einem Mixin in `GameRenderer#close` injizieren.

<<< @/reference/26.1.2/src/client/java/com/example/docs/rendering/CustomRenderPipeline.java#custom_pipelines_clean_up

<<< @/reference/26.1.2/src/client/java/com/example/docs/mixin/client/GameRendererMixin.java

### Finaler Code {#final-code}

Durch die Kombination aller oben genannten Schritte, erhalten wir eine einfache Klasse, die einen Wegpunkt bei `(0, 100, 0)` durch Wände hindurch rendert.

<<< @/reference/26.1.2/src/client/java/com/example/docs/rendering/CustomRenderPipeline.java

Vergiss auch nicht das `GameRendererMixin`! Hier ist das Ergebnis:

![Ein Wegpunkt, der durch Wände hindurch gerendert wird](/assets/develop/rendering/world-rendering-custom-render-pipeline-waypoint.png)
