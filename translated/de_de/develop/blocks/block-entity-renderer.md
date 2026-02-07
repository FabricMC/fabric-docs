---
title: Block Entität Renderer
description: Erfahre, wie du das Rendern mit Block Entitäts Renderern aufwerten kannst.
authors:
  - natri0
---

Manchmal reicht das Nutzen von Minecraft's Modellformat nicht aus. Wenn du dynamisches Rendering zu dessen visuellen Elemten hinzufügen willst, wirst du einen `BlockEntityRenderer` nutzen müssen.

Lasst uns als Beispiel den Zählerblock aus dem [Artikel zu Block Entitäten](../blocks/block-entities) die Zahl an Klicks auf der Oberseite anzeigen lassen.

## Erstellen eines BlockEntityRenderer {#creating-a-blockentityrenderer}

Das Block-Entität-Rendering verwendet ein Submit-/Render-System, bei dem du zunächst die zum Rendern eines Objekts auf dem Bildschirm erforderlichen Daten übermittelst. Das Spiel rendert das Objekt dann anhand des übermittelten Status.

Beim Erstellen eines `BlockEntityRenderer` für die `CounterBlockEntity` ist es wichtig, wenn das Projekt geteilte Quellen für den Client und den Server nutzt, die Klasse in das passende Quellenverzeichnis, wie `src/client/`, zu platzieren. Der Zugriff auf Rendering-bezogene Klassen direkt im `src/main/` Quellenverzeichnis ist nicht sicher, da diese Klassen möglicherweise am Server nicht geladen sind.

Zunächst müssen wir einen `BlockEntityRenderState` für unsere `CounterBlockEntity` erstellen, um die Daten zu speichern, die für das Rendern verwendet werden. In diesem Fall müssen die `clicks` während des Renderns verfügbar sein.

@[code transcludeWith=::render-state](@/reference/latest/src/client/java/com/example/docs/rendering/blockentity/CounterBlockEntityRenderState.java)

Dann erstellen wir einen `BlockEntityRenderer` für unsere `CounterBlockEntity`.

@[code transcludeWith=:::1](@/reference/latest/src/client/java/com/example/docs/rendering/blockentity/CounterBlockEntityRenderer.java)

Die neue Klasse hat einen Konstruktor mit einem `BlockEntityRendererProvider.Context` als Parameter. Der `Context` hat einige nützliche Rendering-Hilfsmittel, wie den `ItemRenderer` oder `TextRenderer`.
Durch die Aufnahme eines derartigen Konstruktors, wird es außerdem möglich den Konstuktor als funktionales Interface der `BlockEntityRendererProvider` selbst zu verwenden:

@[code transcludeWith=:::1](@/reference/latest/src/client/java/com/example/docs/ExampleModBlockEntityRenderer.java)

Wir werden einige Methoden überschreiben, um den Renderzustand zusammen mit der Methode `render` einzurichten, in der die Rendering-Logik eingerichtet wird.

`createRenderState` kann verwendet werden, um den Renderzustand zu initialisieren.

@[code transclude={31-34}](@/reference/latest/src/client/java/com/example/docs/rendering/blockentity/CounterBlockEntityRenderer.java)

`extractRenderState` kan verwendet werden, um den Renderzustand mit Daten von einer Entität zu aktuaisieren.

@[code transclude={36-42}](@/reference/latest/src/client/java/com/example/docs/rendering/blockentity/CounterBlockEntityRenderer.java)

Du solltest Renderer für Blockentitäten in deiner Klasse `ClientModInitializer` registrieren.

`BlockEntityRenderers` ist eine Registrierung, die jeden `BlockEntityType` mit benutzerdefinierten Rendering-Code dem entsprechenden `BlockEntityRenderer` zuordnet.

## Auf Blöcke zeichnen {#drawing-on-blocks}

Jetzt, da wir den Renderer haben, können wir zeichnen. Die Methode `render` wird bei jedem Frame aufgerufen und ist der Ort, an dem die Magie des Renderns passiert.

### Umher bewegen {#moving-around}

Zunächst müssen wir den Text versetzen und drehen, damit er sich auf der oberen Seite des Blocks befindet.

::: info

Wie der Name bereits vermuten lässt ist der `PoseStack` ein _Stapel_, was bedeutet, dass du Transformationen darauf hinzufügen (push) und davon entfernen (pop) kannst.
Eine gute Faustregel ist es, einen neuen Block an den Anfang der `render`-Methode hinzuzufügen und ihn am Ende wieder zu entfernen, so dass das Rendern eines Blocks die anderen nicht beeinflusst.

Mehr Informationen zu dem `PoseStack` kann in dem [Artikel zu den grundlegenden Konzepten des Rendering](../rendering/basic-concepts) gefunden werden.

:::

Zum besseren Verständnis der erforderlichen Verschiebungen und Drehungen sollten wir sie visualisieren. In diesem Bild ist der grüne Block die Position, an der der Text gezeichnet werden würde, standardmäßig am äußersten linken unteren Punkt des Blocks:

![Standard Position des Rendern](/assets/develop/blocks/block_entity_renderer_1.png)

Zunächst müssen wir den Text auf der X- und Z-Achse in die Mitte und ihn dann an der Y-Achse an den oberen Rand des Blocks verschieben:

![Grüner Block am obersten zentriertem Punkt](/assets/develop/blocks/block_entity_renderer_2.png)

Died wird durch einen einzelnen `translate` Aufruf gemacht:

```java
matrices.translate(0.5, 1, 0.5);
```

Somit ist die _Verschiebung_ erledigt, _Drehung_ und _Skalierung_ bleiben.

Standardmäßig wird der Text auf der XY-Ebene gezeichnet, also müssen wir ihn um 90 Grad um die X-Achse drehen, damit er auf der XZ-Ebene nach oben zeigt:

![Grüner Block, nach oben schauend, am obersten zentriertem Punkt](/assets/develop/blocks/block_entity_renderer_3.png)

Der `PoseStack` hat keine `rotate` Methode, stattdessen müssen wir `multiply` und `Axis.XP` verwenden:

```java
matrices.multiply(Axis.XP.rotationDegrees(90));
```

Jetzt ist der Text an der korrekten Position, aber ist zu groß. Der `BlockEntityRenderer` ordnet den ganzen Block zu einem `[-0.5, 0.5]` Würfel zu, während der `TextRenderer` X-Koordinaten von `[0, 9]` verwendet. Somit müssen wir es um den Faktor 18 herunter skalieren:

```java
matrices.scale(1/18f, 1/18f, 1/18f);
```

Jetzt sieht die ganze Transformation wie folgt aus:

@[code transcludeWith=:::2](@/reference/latest/src/client/java/com/example/docs/rendering/blockentity/CounterBlockEntityRenderer.java)

### Zeichnen von Text {#drawing-text}

Wie bereits früher erwähnt, hat der an den Konstruktor unseres Renderers übergebene `Context` einen `TextRenderer`, den wir für das Messen von Text (`width`) verwenden können, was für die Zentrierung nützlich ist.

Um den Text zu zeichnen, übermitteln wir die erforderlichen Daten an die Render-Warteschlange. Da wir Text zeichnen, können wir die Methode `submitText` verwenden, die über die Instanz `OrderedRenderCommandQueue` bereitgestellt wird, die an die Methode `render` übergeben wird.

@[code transcludeWith=:::3](@/reference/latest/src/client/java/com/example/docs/rendering/blockentity/CounterBlockEntityRenderer.java)

Die Methode `submitText` nimmt einige Paramter, aber die Wichtigsten sind:

- die zu zeichnende `FormattedCharSequence`;
- seine `x` und `y` Koordinaten;
- der RGB `color` Wert;
- die `Matrix4f`, die beschreibt, wie er transformiert werden soll (um eine aus einem `PoseStack` zu erhalten, können wir `.last().pose()` verwenden, um die `Matrix4f` für den obersten Eintrag zu erhalten).

Und nach dieser ganzen Arbeit, ist hier das Ergebnis:

![Zählerblock mit einer Zahl auf der Oberseite](/assets/develop/blocks/block_entity_renderer_4.png)
