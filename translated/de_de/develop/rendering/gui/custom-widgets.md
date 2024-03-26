---
title: Eigene Widgets
description: Lerne, wie man eigene Widgets für deinen Bildschirm erstellt.
authors:
  - IMB11
---

# Eigene Widgets

Widgets sind im Wesentlichen in Containern untergebrachte Rendering-Komponenten, die zu einem Bildschirm hinzugefügt werden können und mit denen der Spieler durch verschiedene Ereignisse wie Mausklicks, Tastendruck usw.

## Ein Widget erstellen

Es gibt mehrere Möglichkeiten, eine Widget-Klasse zu erstellen, beispielsweise durch die Erweiterung von `ClickableWidget`. Diese Klasse bietet viele nützliche Funktionen, wie beispielsweise die Verwaltung von Breite, Höhe und Position sowie die Behandlung von Ereignissen.

- `Drawable` - zum Rendern - Erforderlich, um das Widget über die Methode `addDrawableChild` auf dem Bildschirm zu registrieren.
- `Element` - für Ereignisse - Erforderlich, wenn du Ereignisse wie Mausklicks, Tastendrücke usw.
- `Narratable` - für die Barrierefreiheit - Erforderlich, um dein Widget für Bildschirmleser und andere Barrierefreiheitstools zugänglich zu machen.
- `Selectable` - für die Auswahl - Erforderlich, wenn du dein Widget mit der <kbd>Tab</kbd>-Taste auswählbar machen willst - dies hilft auch bei der Barrierefreiheit.

@[code lang=java transcludeWith=:::1](@/reference/latest/src/client/java/com/example/docs/rendering/screens/CustomWidget.java)

## Das Widget zum Bildschirm hinzufügen

Wie alle Widgets musst du es mit der Methode `addDrawableChild`, die von der Klasse `Screen` bereitgestellt wird, zum Bildschirm hinzufügen. Stelle sicher, dass du dies in der Methode `init` machst.

@[code lang=java transcludeWith=:::1](@/reference/latest/src/client/java/com/example/docs/rendering/screens/CustomWidget.java)

![Ein Eignes Widget am Bildschirm.](/assets/develop/rendering/gui/custom-widget-example.png)

## Widget Events

Du kannst Ereignisse wie Mausklicks und Tastendrücke behandeln, indem du die Methoden `onMouseClicked`, `onMouseReleased`, `onKeyPressed` und andere Methoden überschreibst.

Du kannst zum Beispiel dafür sorgen, dass das Widget die Farbe wechselt, wenn man mit dem Mauszeiger darüber fährt, indem du die Methode `isHovered()` verwendest, die von der Klasse `ClickableWidget` bereitgestellt wird:

@[code lang=java transcludeWith=:::2](@/reference/latest/src/client/java/com/example/docs/rendering/screens/CustomWidget.java)

![Hover-Event Beispiel](/assets/develop/rendering/gui/custom-widget-events.webp)
