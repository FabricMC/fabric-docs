---
title: Benutzerdefinierte Widgets
description: Lerne, wie man benutzerdefinierte Widgets für deine Oberfläche erstellt.
authors:
  - IMB11
---

Widgets sind im Wesentlichen in Containern untergebrachte Rendering-Komponenten, die zu einer Oberfläche hinzugefügt werden können und mit denen der Spieler durch verschiedene Ereignisse wie Mausklicks, Tastendruck usw.

## Ein Widget erstellen {#creating-a-widget}

Es gibt mehrere Möglichkeiten, eine Widget-Klasse zu erstellen, beispielsweise durch die Erweiterung von `ClickableWidget`. Diese Klasse bietet viele nützliche Funktionen, wie beispielsweise die Verwaltung von Breite, Höhe und Position sowie die Behandlung von Events. Sie implementiert die Schnittstellen `Drawable`, `Element`, `Narratable` und `Selectable`:

- `Drawable` - zum Rendern - Erforderlich, um das Widget über die Methode `addDrawableChild` in der Oberfläche zu registrieren.
- `Element` - für Events - Erforderlich, wenn du Events wie Mausklicks, Tastendrücke usw.
- `Narratable` - für die Barrierefreiheit - Erforderlich, um dein Widget für Bildschirmleser und andere Barrierefreiheitstools zugänglich zu machen.
- `Selectable` - für die Auswahl - Erforderlich, wenn du dein Widget mit der <kbd>Tab</kbd>-Taste auswählbar machen willst - dies hilft auch bei der Barrierefreiheit.

@[code lang=java transcludeWith=:::1](@/reference/latest/src/client/java/com/example/docs/rendering/screens/CustomWidget.java)

## Das Widget zur Oberfläche hinzufügen {#adding-the-widget-to-the-screen}

Wie alle Widgets musst du es mit der Methode `addDrawableChild`, die von der Klasse `Screen` bereitgestellt wird, zur Oberfläche hinzufügen. Stelle sicher, dass du dies in der Methode `init` machst.

@[code lang=java transcludeWith=:::1](@/reference/latest/src/client/java/com/example/docs/rendering/screens/CustomWidget.java)

![Ein benutzerdefiniertes Widget in einer Oberfläche](/assets/develop/rendering/gui/custom-widget-example.png)

## Widget Events {#widget-events}

Du kannst Events wie Mausklicks und Tastendrücke behandeln, indem du die Methoden `onMouseClicked`, `onMouseReleased`, `onKeyPressed` und andere Methoden überschreibst.

Du kannst zum Beispiel dafür sorgen, dass das Widget die Farbe wechselt, wenn man mit dem Mauszeiger darüber fährt, indem du die Methode `isHovered()` verwendest, die von der Klasse `ClickableWidget` bereitgestellt wird:

@[code lang=java transcludeWith=:::2](@/reference/latest/src/client/java/com/example/docs/rendering/screens/CustomWidget.java)

![Hover-Event Beispiel](/assets/develop/rendering/gui/custom-widget-events.webp)
