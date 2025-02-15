---
title: Rendering im Hud
description: Lerne, wie man das Event HudRenderCallback nutzt, um im Hud zu rendern.
authors:
  - IMB11
---

Wir haben bereits auf der Seite [Grundlegende Rendering-Konzepte](./basic-concepts) und [Den Zeichenkontext verwenden](./draw-context) kurz über das Rendern von Dingen auf dem Hud gesprochen, daher beschränken wir uns auf dieser Seite auf das Event `HudRenderCallback` und den Parameter `tickDelta`.

## HudRenderCallback {#hudrendercallback}

Das Event `HudRenderCallback`, das von der Fabric API bereitgestellt wird, wird bei jedem Frame aufgerufen und wird zum Rendern von Dingen auf dem HUD verwendet.

Um dieses Event zu registrieren, kannst du einfach `HudRenderCallback.EVENT.register` aufrufen und ein Lambda übergeben, welches einen `DrawContext` und eine `RenderTickCounter` Instanz als Parameter entegennimmt.

Der Zeichenkontext kann verwendet werden, um auf die verschiedenen Rendering-Utilities zuzugreifen, die vom Spiel zur Verfügung gestellt werden, und um auf den Rohmatrix-Stapel zuzugreifen.

Du solltest dir die Seite [Den Zeichenkontext verwenden](./draw-context) ansehen, um mehr über den Zeichenkontext zu erfahren.

### Renderer Tickzähler {#render-tick-counter}

Die `RenderTickCounter` Klasse erlaubt es dir, den aktuellen `tickDelta` Wert abzurufen.

`tickDelta` ist der "Fortschritt" zwischen dem letzten Spieltick und dem nächsten Spieltick.

Wenn wir zum Beispiel von einem Szenario mit 200 FPS ausgehen, führt das Spiel ungefähr alle 10 Bilder einen neuen Tick aus. In jedem Frame gibt `tickDelta` an, wie weit der letzte Tick vom nächsten entfernt ist. Bei mehr als 10 Bildern könntest du folgendes sehen:

| Frame | tickDelta                       |
| :---: | ------------------------------- |
|   1   | `1`: Neuer Tick |
|   2   | `0.11 (1÷9)`                    |
|   3   | `0.22 (2÷9)`                    |
|   4   | `0.33 (3÷9)`                    |
|   5   | `0.44 (4÷9)`                    |
|   6   | `0.55 (5÷9)`                    |
|   7   | `0.66 (6÷9)`                    |
|   8   | `0.77 (7÷9)`                    |
|   9   | `0.88 (8÷9)`                    |
|   10  | `1`: Neuer Tick |

Praktischerweise solltest du `tickDelta` nur verwenden, wenn deine Animationen von Minecrafts Ticks abhängen. Für zeitbasierte Animationen verwende `Util.getMeasuringTimeMs()`, das die Zeit in der realen Welt misst.

Du kannst `tickDelta` mit der Funktion `renderTickCounter.getTickDelta(false);` abrufen, wobei der boolesche Parameter `ignoreFreeze` ist, was dir im Wesentlichen erlaubt, zu ignorieren, wenn Spieler den Befehl `/tick freeze` verwenden.

In diesem Beispiel werden wir `Util.getMeasuringTimeMs()` verwenden, um die Farbe eines Quadrats, das auf dem HUD gerendert wird, linear zu interpolieren.

@[code lang=java transcludeWith=:::1](@/reference/latest/src/client/java/com/example/docs/rendering/HudRenderingEntrypoint.java)

![](/assets/develop/rendering/hud-rendering-deltatick.webp)

Wieso versuchst du nicht, `tickDelta` zu verwenden und zu sehen, was mit der Animation passiert, wenn du den Befehl `/tick freeze` ausführst? Du solltest sehen, wie die Animation an Ort und Stelle einfriert, wenn `tickDelta` konstant wird (vorausgesetzt, du hast `false` als Parameter an `RenderTickCounter#getTickDelta` übergeben)
