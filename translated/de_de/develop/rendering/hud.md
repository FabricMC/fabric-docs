---
title: Rendering im Hud
description: Lerne, wie man das Event HudRenderCallback nutzt, um im Hud zu rendern.
authors:
  - IMB11
---

# Rendering im Hud

Wir haben bereits auf der Seite [Grundlegende Rendering-Konzepte](./basic-concepts) und [Den Zeichenkontext verwenden](./draw-context) kurz über das Rendern von Dingen auf dem Hud gesprochen, daher beschränken wir uns auf dieser Seite auf das Event `HudRenderCallback` und den Parameter `deltaTick`.

## HudRenderCallback

Das Event `HudRenderCallback`, das von der Fabric API bereitgestellt wird, wird bei jedem Frame aufgerufen und wird zum Rendern von Dingen auf dem HUD verwendet.

Um dieses Event zu registrieren, kannst du einfach `HudRenderCallback.EVENT.register` aufgrufen und ein Lambda übergeben, welches einen `DrawContext` und einen `float` (deltaTick) als Parameter benötigt.

Der Zeichenkontext kann verwendet werden, um auf die verschiedenen Rendering-Utilities zuzugreifen, die vom Spiel zur Verfügung gestellt werden, und um auf den Rohmatrix-Stapel zuzugreifen.

Du solltest dir die Seite [Den Zeichenkontext verwenden](./draw-context) ansehen, um mehr über den Zeichenkontext zu erfahren.

### DeltaTick

Der `deltaTick` bezieht sich auf die Zeit seit dem letzten Frame in Sekunden. Dies kann für Animationen und andere zeitbasierte Effekte verwendet werden.

Nehmen wir an, du möchtest eine Farbe im Laufe der Zeit auslöschen. Du kannst den `deltaTickManager` verwenden, um den deltaTick zu erhalten und ihn über die Zeit zu speichern, um die Farbe zu lerpen:

@[code lang=java transcludeWith=:::1](@/reference/latest/src/client/java/com/example/docs/rendering/HudRenderingEntrypoint.java)

![](/assets/develop/rendering/hud-rendering-deltatick.webp)
