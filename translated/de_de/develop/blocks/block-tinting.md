---
title: Blockfärbung
description: Lerne, wie man einen Block dynamisch färbt.
authors:
  - cassiancc
  - dicedpixels
---

Manchmal möchtest du vielleicht, dass das Aussehen von Blöcken im Spiel speziell behandelt wird. Beispielsweise wird bei einigen Blöcken, wie zum Beispiel Gras, eine Färbung angewendet.

Schauen wir uns einmal an, wie wir das Aussehen eines Blocks verändern können.

In diesem Beispiel registrieren wir einen Block. Wenn du mit diesem Vorgang nicht vertraut bist, lies bitte zuerst die Informationen zur [Registrierung eines Blocks](./first-block).

@[code lang=java transcludeWith=:::waxcap-tinting](@/reference/latest/src/main/java/com/example/docs/block/ModBlocks.java)

Stelle sicher, folgendes hinzuzufügen:

- Ein [Blockzustand](./blockstates) in `/blockstates/waxcap.json`
- Ein [Modell](./block-models) in `/models/block/waxcap.json`
- Eine [Textur](./first-block#models-and-textures) in `/textures/block/waxcap.png`

Wenn alles korrekt ist, kannst du den Block im Spiel sehen.

![Richtiges Aussehen des Blocks](/assets/develop/transparency-and-tinting/block_appearance_1.png)

## Block Farbquellen {#block-tint-sources}

Auch wenn unser Block im Spiel gut aussieht, ist seine Textur graustufig. Wir könnten dynamisch eine Färbung anwenden, ähnlich wie Vanilla Laub seine Farbe je nach Biom ändert.

Die Fabric API stellt `BlockColorRegistry` zur Verfügung, um eine Liste an `BlockTintSource` zu registrieren, den wir verwenden werden, um den Block dynamisch einzufärben.

Verwenden wir diese API, um eine Färbung zu registrieren, sodass unser Waxcap-Block grün aussieht, wenn er auf Gras platziert wird, und ansonsten braun.

Registriere deinen Block in deinem **Client Initialisierer** zusammen mit der entsprechenden Logik in der `ColorProviderRegistry`.

@[code lang=java transcludeWith=:::color_provider](@/reference/latest/src/client/java/com/example/docs/appearance/ExampleModAppearanceClient.java)

Nun wird der Block entsprechend seiner Position eingefärbt.

![Block mit Farb Provider](/assets/develop/transparency-and-tinting/block_appearance_2.png)
