---
title: Generation von Blockmodellen
description: Ein Leitfaden zur Generierung von Blockmodellen und Blockzuständen mit dem Datengenerator.
authors:
  - Fellteros
  - IMB11
  - its-miroma
  - natri0
---

:::info VORAUSSETZUNGEN
Stelle sicher, dass du den Prozess der [Einrichtung der Datengenerierung](./setup) zuerst abgeschlossen hast.
:::

## Einrichten {#setup}

Zuerst müssen wir unseren ModelProvider erstellen. Erstelle eine Klasse, welche `extends FabricModelProvider`. Implementiere beide abstrakten Methoden: `generateBlockStateModels` und `generateItemModels`.
Zum Schluss, erstelle einen Konstruktor, der zu super passt.

@[code lang=java transcludeWith=:::datagen-model:provider](@/reference/latest/src/client/java/com/example/docs/datagen/FabricDocsReferenceModelProvider.java)

Registriere diese Klasse in deinem `DataGeneratorEntrypoint` innerhalb der `onInitializeDataGenerator`-Methode.

## Blockzustände und Blockmodelle {#blockstates-and-block-models}

```java
@Override
public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
}
```

Für Blockmodelle werden wir uns hauptsächlich auf die `generateBlockStateModels`-Methode fokusieren. Beachte den Parameter `BlockStateModelGenerator blockStateModelGenerator` - dieses Objekt wird für die Generierung aller JSON-Dateien verantwortlich sein.
Hier sind einige praktische Beispiele, die du zur Generierung deiner gewünschten Modelle verwenden kannst:

### Einfacher Cube All {#simple-cube-all}

@[code lang=java transcludeWith=:::datagen-model:cube-all](@/reference/latest/src/client/java/com/example/docs/datagen/FabricDocsReferenceModelProvider.java)

Dies ist die am häufigsten verwendete Funktion. Sie generiert eine JSON-Modell-Datei für ein normales `cube_all` Blockmodell. Eine Textur wird für alle sechs Seiten genutzt, in diesem Fall nutzen wir `steel_block`.

@[code](@/reference/latest/src/main/generated/assets/fabric-docs-reference/models/block/steel_block.json)

Sie generiert auch eine Blockzustand-JSON-Datei. Da wir keine Blockzustand-Eigenschaften (z. B. Achsen, Ausrichtung, ...) haben, ist eine Variante ausreichend und wird jedes Mal verwendet, wenn der Block platziert wird.

@[code](@/reference/latest/src/main/generated/assets/fabric-docs-reference/blockstates/steel_block.json)

<DownloadEntry visualURL="/assets/develop/data-generation/block-model/steel_block_big.png" downloadURL="/assets/develop/data-generation/block-model/steel_block.png">Stahlblock</DownloadEntry>

### Singletons {#singletons}

Die `registerSingleton`-Methode liefert JSON-Modelldateien basierend auf dem übergebenen `TexturedModel` und einer einzelnen Blockzustand-Variante.

@[code lang=java transcludeWith=:::datagen-model:cube-top-for-ends](@/reference/latest/src/client/java/com/example/docs/datagen/FabricDocsReferenceModelProvider.java)

Diese Methode wird Modelle für einen normalen Würfel generieren, der die Texturdatei `pipe_block` für die Seiten und die Texturdatei `pipe_block_top` für die obere und untere Seite nutzt.

@[code](@/reference/latest/src/main/generated/assets/fabric-docs-reference/models/block/pipe_block.json)

:::tip
Wenn du dich nicht entscheiden kannst, welches `TextureModel` du verwenden sollst, öffne die Klasse `TexturedModel` und sieh dir die [`TextureMaps`](#using-texture-map) an!
:::

<DownloadEntry visualURL="/assets/develop/data-generation/block-model/pipe_block_textures_big.png" downloadURL="/assets/develop/data-generation/block-model/pipe_block_textures.zip">Rohrblock</DownloadEntry>

### Block-Textur-Pool {#block-texture-pool}

@[code lang=java transcludeWith=:::datagen-model:block-texture-pool-normal](@/reference/latest/src/client/java/com/example/docs/datagen/FabricDocsReferenceModelProvider.java)

Eine andere nützliche Methode ist `registerCubeAllModelTexturePool`: Definiere die Texturen, indem du den "Basisblock" übergibst, und füge dann die "Kinder" hinzu, die die gleichen Texturen haben.
In diesem Fall haben wir den `RUBY_BLOCK` übergeben, so dass die Treppe, die Stufe und der Zaun die Textur `RUBY_BLOCK` verwenden werden.

:::warning
Sie wird auch ein [einfaches Cube All JSON-Modell](#simple-cube-all) für den "Basisblock" generieren, um sicherzustellen, dass er ein Blockmodell hat.

Sei dir dessen bewusst, wenn du das Blockmodell dieses bestimmten Blocks änderst, da dies zu einem Fehler führen wird.
:::

Du kannst auch eine `BlockFamily` anhängen, die Modelle für alle ihre "Kinder" generieren wird.

@[code lang=java transcludeWith=:::datagen-model:family-declaration](@/reference/latest/src/main/java/com/example/docs/block/ModBlocks.java)

@[code lang=java transcludeWith=:::datagen-model:block-texture-pool-family](@/reference/latest/src/client/java/com/example/docs/datagen/FabricDocsReferenceModelProvider.java)

<DownloadEntry visualURL="/assets/develop/data-generation/block-model/ruby_block_big.png" downloadURL="/assets/develop/data-generation/block-model/ruby_block.png">Rubinblock</DownloadEntry>

### Türen und Falltüren {#doors-and-trapdoors}

@[code lang=java transcludeWith=:::datagen-model:door-and-trapdoor](@/reference/latest/src/client/java/com/example/docs/datagen/FabricDocsReferenceModelProvider.java)

Türen und Falltüren sein ein wenig anders. Hier musst du drei neue Texturen erstellen - zwei für die Türe, und eine für die Falltüre.

1. Die Tür:
  - Sie hat zwei Teile - die obere und die untere Hälfte. **Jede benötigt ihre eigene Textur:** In diesem Fall `ruby_door_top` für die obere und `ruby_door_bottom` für die untere Hälfte.
  - Die Methode `registerDoor()` wird Modelle für alle Ausrichtungen der Tür, sowohl offen als auch geschlossen erstellen.
  - **Du benötigst auch eine Itemtextur!** Lege sie in dem Ordner `assets/<mod_id>/textures/item/` ab.
2. Die Falltür:
  - Hier benötigst du nur eine Textur, die in diesem Fall `ruby_trapdoor` heißt. Diese wird für alle Seiten genutzt.
  - Da `TrapdoorBlock` eine Eigenschaft `FACING` hat, kannst du die auskommentierte Methode verwenden, um Modell-Dateien mit rotierten Texturen zu generieren = Die Falltüre wird "orientierbar" sein. Andernfalls sieht sie immer gleich aus, egal in welche Richtung sie gerichtet ist.

<DownloadEntry visualURL="/assets/develop/data-generation/block-model/ruby_door_trapdoor_big.png" downloadURL="/assets/develop/data-generation/block-model/ruby_door_trapdoor_textures.zip">Rubintür und Falltür</DownloadEntry>

## Benutzerdefinierte Blockklasse {#custom-block-class}

In diesem Abschnitt werden wir die Modelle für eine vertikale Eichenstammstufe, mit einer Eichenstamm-Textur, erstellen.

_Punkte 2. - 6. werden in einer inneren, statischen Hilfsklasse namens `CustomBlockStateModelGenerator` deklariert._

### Benutzerdefinierte Blockmodelle {#custom-block-models}

Erstelle einen Block `VerticalSlab` mit einer Eigenschaft `FACING` und einer boolean-Eigenschaft `SINGLE`, wie in dem Tutorial [Block States](../blocks/blockstates) beschrieben. `SINGLE` zeigt an, ob beide Stufen sind.
Dann solltest du `getOutlineShape` und `getCollisionShape` überschreiben, so dass die Umrandung korrekt gerendert wird und der Block die richtige Kollisionsform hat.

@[code lang=java transcludeWith=:::datagen-model-custom:voxels](@/reference/latest/src/main/java/com/example/docs/block/custom/VerticalSlabBlock.java)

@[code lang=java transcludeWith=:::datagen-model-custom:collision](@/reference/latest/src/main/java/com/example/docs/block/custom/VerticalSlabBlock.java)

Überschreibe auch die Methode `canReplace()`, sonst kannst du die Stufe nicht zu einem vollen Block machen.

@[code lang=java transcludeWith=:::datagen-model-custom:replace](@/reference/latest/src/main/java/com/example/docs/block/custom/VerticalSlabBlock.java)

Und du bist fertig! Du kannst jetzt den Block austesten und im Spiel platzieren.

### Übergeordnetes Blockmodell {#parent-block-model}

Lasst und jetzt ein übergeordnetes Blockmodell erstellen. Es bestimmt die Größe, Position in der Hand oder in anderen Slots und die `x` und `y` Koordinaten der Textur.
Es wird empfohlen für dies einen Editor, wie [Blockbench](https://www.blockbench.net/) zu verwenden, da die manuelle Erstellung ein wirklich mühsamer Prozess ist. Es sollte wie folgt aussehen:

@[code lang=json](@/reference/latest/src/main/resources/assets/fabric-docs-reference/models/block/vertical_slab.json)

Für weitere Informationen, siehe dir an [wie Blockzustände formatiert sind](https://minecraft.wiki/w/Blockstates_definition_format).
Beachte die Schlüsselwörter `#bottom`, `#top`, `#side`. Sie dienen als Variablen, die von Modellen gesetzt werden können, die dieses Modell als übergeordnetes Modell haben:

```json
{
  "parent": "minecraft:block/cube_bottom_top",
  "textures": {
    "bottom": "minecraft:block/sandstone_bottom",
    "side": "minecraft:block/sandstone",
    "top": "minecraft:block/sandstone_top"
  }
}
```

Der Wert `bottom` wird den Platzhalter `#bottom` ersetzen und so weiter. **Füge es in den Ordner `resources/assets/mod_id/models/block/` ein.**

### Benutzerdefiniertes Modell {#custom-model}

Eine weitere Sache, die wir benötigen, ist eine Instanz der Klasse `Model`. Sie wird das tatsächliche [übergeordnete Blockmodell](#parent-block-model) in unserem Mod repräsentieren.

@[code lang=java transcludeWith=:::datagen-model-custom:model](@/reference/latest/src/client/java/com/example/docs/datagen/FabricDocsReferenceModelProvider.java)

Die Methode `block()` erstellt ein neues `Model`, das auf die Datei `vertical_slab.json` in unserem Ordner `resources/assets/mod_id/models/block/` zeigt.
Die `TextureKey`s repräsentieren die "Platzhalter" (`#bottom`, `#top`, ...) als ein Objekt.

### Die Texture Map verwenden {#using-texture-map}

Was macht die `TextureMap`? Sie liefert die Identifikatoren, die auf die Textur verweisen. Technisch gesehen verhält sie sich wie eine normale Map - man verbindet einen `TextureKey` (Schlüssel) mit einem `Identifier` (Wert).

Du kannst entweder die von Vanilla verwenden, wie `TextureMap.all()` (die alle TextureKeys mit dem selben Identifikator verknüpft), oder eine neue erstellen, indem du eine neue Instanz erstellst und dann `.put()` aufrufst, um die Schlüssel mit Werten zu verknüpfen.

:::tip
`TextureMap.all()` verknüpft alle TextureKeys mit dem selben Identifikator, egal wie viele es davon gibt!
:::

Da wir die Eichenstammtexturen nutzen wollen, aber die `BOTTOM`, `TOP` und `SIDE` `TextureKey`s haben, müssen wir eine neue erstellen.

@[code lang=java transcludeWith=:::datagen-model-custom:texture-map](@/reference/latest/src/client/java/com/example/docs/datagen/FabricDocsReferenceModelProvider.java)

Die `bottom` und `top` Flächen werden `oak_log_top.png` verwenden, die Seiten werden `oak_log.png` verwenden.

:::warning
Alle `TextureKey`s in deiner TextureMap **müssen** mit den `TextureKey`s in deinem übergeordneten Blockmodell übereinstimmen!
:::

### Benutzerdefinierte `BlockStateSupplier`-Methode {#custom-supplier-method}

Der `BlockStateSupplier` beinhaltet alle Varianten an Blockzuständen, deren Rotation und anderen Optionen, wie uvlock.

@[code lang=java transcludeWith=:::datagen-model-custom:supplier](@/reference/latest/src/client/java/com/example/docs/datagen/FabricDocsReferenceModelProvider.java)

Zuerst erstellen wir einen neuen `VariantsBlockStateSupplier` mit Hilfe von `VariantsBlockStateSupplier.create()`.
Dann erstellen wir eine neue `BlockStateVariantMap`, die Parameter für alle Varianten des Blocks beinhaltet, in diesem Fall `FACING` und `SINGLE` und übergeben diese in den `VariantsBlockStateSupplier`.
Gebe an, welches Modell und welche Transformation (uvlock, rotation) bei der Verwendung von `.register()` genutzt wird.
Zum Beispiel:

- In der ersten Zeile, zeigt der Block nach Norden und ist einzeln => Wir verwenden das Modell ohne Rotation.
- In der vierten Zeile, zeigt der Block nach Westen und ist einzeln => Wir rotieren das Modell auf der Y-Achse für 270°.
- In der sechsten Zeile, zeigt der Block nach Osten, ist jedoch nicht einzeln => Er sieht aus wie ein normaler Eichenstamm => Wie müssen ihn nicht rotieren.

### Benutzerdefinierte Methode für den Datengenerator {#custom-datagen-method}

Der letzte Schritt - die Erstellung der tatsächlichen Methode, die du aufrufen kannst und die die JSONs generiert.
Aber für was sind die Parameter?

1. `BlockStateModelGenerator generator`, das gleiche, dass wir an `generateBlockStateModels` übergeben haben.
2. `Block vertSlabBlock` ist der Block, zu dem wir die JSONs generieren werden.
3. `Block fullBlock` - ist das Modell, dass genutzt wird, wenn die Eigenschaft `SINGLE` false ist = der Stufenblock sieht wie ein voller Block aus.
4. `TextureMap textures` definiert die tatsächlichen Texturen, die das Modell nutzt. Siehe das Kapitel [Die Texture Map nutzen verwenden](#using-texture-map).

@[code lang=java transcludeWith=:::datagen-model-custom:gen](@/reference/latest/src/client/java/com/example/docs/datagen/FabricDocsReferenceModelProvider.java)

Zunächst erhalten wir den `Identifier` des einzelnen Stufenmodell mit `VERTICAL_SLAB.upload()`. Dann erhalten wir den `Identifier` des vollen Blockmodells mit `ModelIds.getBlockModelId()`, und übergeben diese beiden Modelle an `createVerticalSlabBlockStates`.
Der `BlockStateSupplier` wird an den `blockStateCollector` übergeben, so dass die JSON-Dateien tatsächlich generiert werden.
Außerdem, erstellen wir ein Modell für das Item der vertikalen Stufe mit `BlockStateModelGenerator.registerParentedItemModel()`.

Und dies ist alles! Jetzt müssen wir nur noch unsere Methode in unserem `ModelProvider` aufrufen:

@[code lang=java transcludeWith=:::datagen-model-custom:method-call](@/reference/latest/src/client/java/com/example/docs/datagen/FabricDocsReferenceModelProvider.java)

## Quellen und Links {#sources-and-links}

Du kannst für weitere Informationen die Beispieltests in der [Fabric API](https://github.com/FabricMC/fabric/blob/1.21.4/fabric-data-generation-api-v1/src/) und im [Referenz-Mod](https://github.com/FabricMC/fabric-docs/tree/main/reference) dieser Dokumentation ansehen.

Du kannst auch weitere Beispiele für die Verwendung von benutzerdefinierten Methoden für den Datengenerator finden, indem du den Open-Source-Code von Mods durchsuchst, zum Beispiel [Vanilla+ Blocks](https://github.com/Fellteros/vanillablocksplus) und [Vanilla+ Verticals](https://github.com/Fellteros/vanillavsplus) von Fellteros.
