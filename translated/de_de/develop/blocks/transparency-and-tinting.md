---
title: Transparenz und Färbung
description: Lerne, wie du das Aussehen von Blöcken manipulierst und diese dynamisch färbst.
authors:
  - cassiancc
  - dicedpixels
---

Manchmal möchtest du vielleicht, dass das Aussehen von Blöcken im Spiel speziell behandelt wird. Beispielsweise können einige Blöcke transparent erscheinen, während andere eine Färbung erhalten.

Schauen wir uns einmal an, wie wir das Aussehen eines Blocks verändern können.

In diesem Beispiel registrieren wir einen Block. Wenn du mit diesem Vorgang nicht vertraut bist, lies bitte zuerst die Informationen zur [Registrierung eines Blocks](./first-block).

@[code lang=java transcludeWith=:::block](@/reference/latest/src/main/java/com/example/docs/appearance/ExampleModAppearance.java)

Stelle sicher, folgendes hinzuzufügen:

- Ein [Blockzustand](./blockstates) in `/blockstates/waxcap.json`
- Ein [Modell](./block-models) in `/models/block/waxcap.json`
- Eine [Textur](./first-block#models-and-textures) in `/textures/block/waxcap.png`

Wenn alles korrekt ist, kannst du den Block im Spiel sehen. Du wirst jedoch feststellen, dass der Block nach dem Platzieren nicht richtig aussieht.

![Falsches Block Aussehen](/assets/develop/transparency-and-tinting/block_appearance_0.png)

Das liegt daran, dass eine Textur mit Transparenz ein paar zusätzliche Einstellungen benötigt.

## Block Aussehen manipulieren {#manipulating-block-appearance}

Selbst wenn die Textur deines Blocks transparent oder durchscheinend ist, erscheint sie dennoch undurchsichtig. Um dies zu beheben, musst du die _Chunk-Abschnitt-Ebene_ deines Blocks einstellen.

Chunk-Abschnitts-Ebenen sind Kategorien, die zum Gruppieren verschiedener Arten von Blockoberflächen für das Rendern verwendet werden. Dies erlaubt dem Spiel für jeden Typ die richtigen visuellen Effekte und Optimierungen zu verwenden.

Wir müssen unseren Block mit der richtigen Chunk-Abschnitt-Ebene registrieren. Vanilla bietet die folgenden Optionen.

- `SOLID`: Der Standard, ein durchgehender Block ohne Transparenz.
- `CUTOUT` und `CUTOUT_MIPPED`: Ein Block, der Transparenz nutzt, zum Beispiel Glas oder Blumen. `CUTOUT_MIPPED` wird auf Distanz besser aussehen.
- `TRANSLUCENT`: Ein Block, der durchscheinende (teilweise transparente) Pixel verwendet, zum Beispiel gefärbtes Glas oder Wasser.

Unser Beispiel hat Transparenz, daher wird es `CUTOUT` verwenden.

Registriere deinen Block in deinem **Client Initialisierer** mit der richtigen `ChunkSectionLayer` unter Verwendung der Fabric-API `BlockRenderLayerMap`.

@[code lang=java transcludeWith=:::block_render_layer_map](@/reference/latest/src/client/java/com/example/docs/appearance/ExampleModAppearanceClient.java)

Jetzt sollte dein Block die richtige Transparenz aufweisen.

![Richtiges Aussehen des Blocks](/assets/develop/transparency-and-tinting/block_appearance_1.png)

## Block Farb Provider {#block-color-providers}

Auch wenn unser Block im Spiel gut aussieht, ist seine Textur graustufig. Wir könnten dynamisch eine Färbung anwenden, ähnlich wie Vanilla Laub seine Farbe je nach Biom ändert.

Die Fabric API stellt `ColorProviderRegistry` zur Verfügung, um einen Provider einer Farbquelle zu registrieren, den wir verwenden werden, um den Block dynamisch einzufärben.

Verwenden wir diese API, um eine Färbung zu registrieren, sodass unser Waxcap-Block grün aussieht, wenn er auf Gras platziert wird, und ansonsten braun.

Registriere deinen Block in deinem **Client Initialisierer** zusammen mit der entsprechenden Logik in der `ColorProviderRegistry`.

@[code lang=java transcludeWith=:::color_provider](@/reference/latest/src/client/java/com/example/docs/appearance/ExampleModAppearanceClient.java)

Nun wird der Block entsprechend seiner Position eingefärbt.

![Block mit Farb Provider](/assets/develop/transparency-and-tinting/block_appearance_2.png)
