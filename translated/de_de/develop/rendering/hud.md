---
title: Auf dem HUD rendern
description: Lerne, wie man die Fabric Hud API zum Rendern auf dem HUD verwendet.
authors:
  - IMB11
  - kevinthegreat1
---

Wir haben bereits auf der Seite [Grundlegende Rendering-Konzepte](./basic-concepts) und [den Zeichenkontext verwenden](./draw-context) kurz über das Rendern von Dingen auf dem Hud gesprochen, daher beschränken wir uns auf dieser Seite auf die Hud-API und den Parameter `DeltaTracker`.

## `HudRenderCallback` {#hudrendercallback}

::: warning

Zuvor stellte Fabric den `HudRenderCallback` zur Verfügung, um auf dem HUD zu rendern. Aufgrund von Änderungen am HUD-Rendering wurde dieses Event extrem eingeschränkt und ist seit der Fabric API 0.116 veraltet. Von der Verwendung wird dringend abgeraten.

:::

## `HudElementRegistry` {#hudelementregistry}

Fabric bietet die Hud-API zum Rendern und Überlagern von Elementen auf dem HUD an.

Zu Beginn müssen wir einen Listener für die `HudElementRegistry` registrieren, der deine Elemente registriert. Jedes Element ist ein `HudElement`. Eine `HudElement`-Instanz ist normalerweise ein Lambda, das eine Instanz von `GuiGraphics` und `DeltaTracker` als Parameter entgegennimmt. Siehe `HudElementRegistry` und die zugehörigen Javadocs für weitere Einzelheiten zur Verwendung der API.

Der Zeichenkontext kann verwendet werden, um auf die verschiedenen Rendering-Hilfsmittel zuzugreifen, die vom Spiel zur Verfügung gestellt werden, und um auf den Rohmatrix-Stapel zuzugreifen. Du solltest dir die Seite [Den Zeichenkontext verwenden](./draw-context) ansehen, um mehr über den Zeichenkontext zu erfahren.

### Delta Tracker {#delta-tracker}

Die Klasse `DeltaTracker` erlaubt dir, den aktuellen Wert von `gameTimeDeltaPartialTick` abzurufen. `gameTimeDeltaPartialTick` ist der "Fortschritt" zwischen dem letzten Spieltick und dem nächsten Spieltick.

Wenn wir zum Beispiel von einem Szenario mit 200 FPS ausgehen, führt das Spiel ungefähr alle 10 Bilder einen neuen Tick aus. In jedem Bild gibt `gameTimeDeltaPartialTick` an, wie weit der letzte Tick vom nächsten entfernt ist. Bei mehr als 11 Bildern könntest du folgendes sehen:

| Bild | `gameTimeDeltaPartialTick`      |
| :--: | ------------------------------- |
|  `1` | `1`: Neuer Tick |
|  `2` | `1/10 = 0.1`                    |
|  `3` | `2/10 = 0.2`                    |
|  `4` | `3/10 = 0.3`                    |
|  `5` | `4/10 = 0.4`                    |
|  `6` | `5/10 = 0.5`                    |
|  `7` | `6/10 = 0.6`                    |
|  `8` | `7/10 = 0.7`                    |
|  `9` | `8/10 = 0.8`                    |
| `10` | `9/10 = 0.9`                    |
| `11` | `1`: Neuer Tick |

Du kannst `gameTimeDeltaPartialTick` mit einem Aufruf von `deltaTracker.getGameTimeDeltaPartialTick(false)` abrufen, wobei der boolesche Parameter `ignoreFreeze` ist, was dir im Wesentlichen erlaubt, zu ignorieren, wenn Spieler den Befehl `/tick freeze` verwenden.

In der Praxis solltest du `gameTimeDeltaPartialTick` nur verwenden, wenn deine Animationen auf Minecrafts Ticks aufbauen. Für zeitbasierte Animationen verwende `Util.getMillis()`, das die Zeit in der realen Welt misst.

In diesem Beispiel werden wir `Util.getMillis()` verwenden, um die Farbe eines Quadrats, das auf dem HUD gerendert wird, linear zu interpolieren.

@[code lang=java transcludeWith=:::1](@/reference/latest/src/client/java/com/example/docs/rendering/HudRenderingEntrypoint.java)

![Eine Farbe im im Laufe der Zeit überblenden](/assets/develop/rendering/hud-rendering-deltatick.webp)

Wieso versuchst du nicht, `gameTimeDeltaPartialTick` zu verwenden und zu sehen, was mit der Animation passiert, wenn du den Befehl `/tick freeze` ausführst? Du solltest sehen, wie die Animation an Ort und Stelle einfriert, wenn `gameTimeDeltaPartialTick` konstant wird (vorausgesetzt, du hast `false` als Parameter an `DeltaTracker#getGameTimeDeltaPartialTick` übergeben)
