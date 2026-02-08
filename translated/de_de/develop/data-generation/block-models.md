---
title: Generation von Blockmodellen
description: Ein Leitfaden zur Generierung von Blockmodellen und Blockzuständen mit dem Datengenerator.
authors:
  - CelDaemon
  - Fellteros
  - IMB11
  - its-miroma
  - natri0
---

<!---->

:::info VORAUSSETZUNGEN

Stelle sicher, dass du den Prozess der [Einrichtung des Datengenerators](./setup) zuerst abgeschlossen hast.

:::

## Einrichtung {#setup}

Zuerst müssen wir unseren ModelProvider erstellen. Erstelle eine Klasse, welche von `FabricModelProvider` erbt. Implementiere beide abstrakten Methoden: `generateBlockStateModels` und `generateItemModels`.
Zum Schluss, erstelle einen Konstruktor, der zu super passt.

@[code lang=java transcludeWith=:::provider](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModModelProvider.java)

Registriere diese Klasse in deinem `DataGeneratorEntrypoint` innerhalb der Methode `onInitializeDataGenerator`.

@[code transcludeWith=:::datagen-models:register](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModDataGenerator.java)

## Blockzustände und Blockmodelle {#blockstates-and-block-models}

```java
@Override
public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
}
```

Für Blockmodelle werden wir uns hauptsächlich auf die `generateBlockStateModels`-Methode fokusieren. Beachte den Parameter `BlockStateModelGenerator blockStateModelGenerator` - dieses Objekt wird für die Generierung aller JSON-Dateien verantwortlich sein.
Hier sind einige praktische Beispiele, die du zur Generierung deiner gewünschten Modelle verwenden kannst:

### Einfacher Cube All {#simple-cube-all}

@[code lang=java transcludeWith=:::cube-all](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModModelProvider.java)

Dies ist die am häufigsten verwendete Funktion. Sie generiert eine JSON-Modell-Datei für ein normales `cube_all` Blockmodell. Eine Textur wird für alle sechs Seiten genutzt, in diesem Fall nutzen wir `steel_block`.

@[code](@/reference/latest/src/main/generated/assets/example-mod/models/block/steel_block.json)

Sie generiert auch eine Blockzustand-JSON-Datei. Da wir keine Blockzustand-Eigenschaften (z. B. Achsen, Ausrichtung, ...) haben, ist eine Variante ausreichend und wird jedes Mal verwendet, wenn der Block platziert wird.

@[code](@/reference/latest/src/main/generated/assets/example-mod/blockstates/steel_block.json)

<DownloadEntry visualURL="/assets/develop/data-generation/block-model/steel_block_big.png" downloadURL="/assets/develop/data-generation/block-model/steel_block.png">Stahlblock Textur</DownloadEntry>

### Singletons {#singletons}

Die `registerSingleton`-Methode liefert JSON-Modelldateien basierend auf dem übergebenen `TexturedModel` und einer einzelnen Blockzustand-Variante.

@[code lang=java transcludeWith=:::cube-top-for-ends](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModModelProvider.java)

Diese Methode wird Modelle für einen normalen Würfel generieren, der die Texturdatei `pipe_block` für die Seiten und die Texturdatei `pipe_block_top` für die obere und untere Seite nutzt.

@[code](@/reference/latest/src/main/generated/assets/example-mod/models/block/pipe_block.json)

::: tip

Wenn du dich nicht entscheiden kannst, welches `TextureModel` du verwenden sollst, öffne die Klasse `TexturedModel` und sieh dir die [`TextureMaps`](#using-texture-map) an!

:::

<DownloadEntry visualURL="/assets/develop/data-generation/block-model/pipe_block_textures_big.png" downloadURL="/assets/develop/data-generation/block-model/pipe_block_textures.zip">Rohrblock Textur</DownloadEntry>

### Block-Textur-Pool {#block-texture-pool}

@[code lang=java transcludeWith=:::block-texture-pool-normal](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModModelProvider.java)

Eine andere nützliche Methode ist `registerCubeAllModelTexturePool`: Definiere die Texturen, indem du den "Basisblock" übergibst, und füge dann die "Kinder" hinzu, die die gleichen Texturen haben.
In diesem Fall haben wir den `RUBY_BLOCK` übergeben, so dass die Treppe, die Stufe und der Zaun die Textur `RUBY_BLOCK` verwenden werden.

::: warning

Sie wird auch ein [einfaches Cube All JSON-Modell](#simple-cube-all) für den "Basisblock" generieren, um sicherzustellen, dass er ein Blockmodell hat.

Sei dir dessen bewusst, wenn du das Blockmodell dieses bestimmten Blocks änderst, da dies zu einem Fehler führen wird.

:::

Du kannst auch eine `BlockFamily` anhängen, die Modelle für alle ihre "Kinder" generieren wird.

@[code lang=java transcludeWith=:::family-declaration](@/reference/latest/src/main/java/com/example/docs/block/ModBlocks.java)

@[code lang=java transcludeWith=:::block-texture-pool-family](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModModelProvider.java)

<DownloadEntry visualURL="/assets/develop/data-generation/block-model/ruby_block_big.png" downloadURL="/assets/develop/data-generation/block-model/ruby_block.png">Rubinblock Textur</DownloadEntry>

### Türen und Falltüren {#doors-and-trapdoors}

@[code lang=java transcludeWith=:::door-and-trapdoor](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModModelProvider.java)

Türen und Falltüren sein ein wenig anders. Hier musst du drei neue Texturen erstellen - zwei für die Türe, und eine für die Falltüre.

1. Die Tür:
   - Sie hat zwei Teile - die obere und die untere Hälfte. **Jede benötigt ihre eigene Textur:** In diesem Fall `ruby_door_top` für die obere und `ruby_door_bottom` für die untere Hälfte.
   - Die Methode `registerDoor()` wird Modelle für alle Ausrichtungen der Tür, sowohl offen als auch geschlossen erstellen.
   - **Du benötigst auch eine Itemtextur!** Lege sie in dem Ordner `assets/example-mod/textures/item/` ab.
2. Die Falltür:
   - Hier benötigst du nur eine Textur, die in diesem Fall `ruby_trapdoor` heißt. Diese wird für alle Seiten genutzt.
   - Da `TrapdoorBlock` eine Eigenschaft `FACING` hat, kannst du die auskommentierte Methode verwenden, um Modell-Dateien mit rotierten Texturen zu generieren = Die Falltüre wird "orientierbar" sein. Andernfalls sieht sie immer gleich aus, egal in welche Richtung sie gerichtet ist.

<DownloadEntry visualURL="/assets/develop/data-generation/block-model/ruby_door_trapdoor_big.png" downloadURL="/assets/develop/data-generation/block-model/ruby_door_trapdoor_textures.zip">Rubintür und Falltür Texturen</DownloadEntry>

## Benutzerdefinierte Blockmodelle {#custom-block-models}

In diesem Abschnitt werden wir die Modelle für eine vertikale Eichenstammstufe, mit einer Eichenstamm-Textur, erstellen.

Alle Felder und Methoden für diesen Teil des Tutorials werden in einer statischen inneren Klasse mit dem Namen `CustomBlockStateModelGenerator` deklariert.

:::details Zeige `CustomBlockStateModelGenerator`

@[code transcludeWith=:::custom-blockstate-model-generator](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModModelProvider.java)

:::

### Benutzerdefinierte Blockklasse {#custom-block-class}

Erstelle einen Block `VerticalSlab` mit einer Eigenschaft `FACING` und einer boolean-Eigenschaft `SINGLE`, wie in dem Tutorial [Blockzustände](../blocks/blockstates) beschrieben. `SINGLE` zeigt an, ob beide Stufen sind.
Dann solltest du `getOutlineShape` und `getCollisionShape` überschreiben, so dass die Umrandung korrekt gerendert wird und der Block die richtige Kollisionsform hat.

@[code lang=java transcludeWith=:::custom-voxels](@/reference/latest/src/main/java/com/example/docs/block/custom/VerticalSlabBlock.java)

@[code lang=java transcludeWith=:::custom-collision](@/reference/latest/src/main/java/com/example/docs/block/custom/VerticalSlabBlock.java)

Überschreibe auch die Methode `canReplace()`, sonst kannst du die Stufe nicht zu einem vollen Block machen.

@[code lang=java transcludeWith=:::custom-replace](@/reference/latest/src/main/java/com/example/docs/block/custom/VerticalSlabBlock.java)

Und du bist fertig! Du kannst jetzt den Block testen und im Spiel platzieren.

### Übergeordnetes Blockmodell {#parent-block-model}

Lasst und jetzt ein übergeordnetes Blockmodell erstellen. Es bestimmt die Größe, Position in der Hand oder in anderen Slots und die `x` und `y` Koordinaten der Textur.
Es wird empfohlen für dies einen Editor, wie [Blockbench](https://www.blockbench.net/) zu verwenden, da die manuelle Erstellung ein wirklich mühsamer Prozess ist. Es sollte wie folgt aussehen:

@[code lang=json](@/reference/latest/src/main/resources/assets/example-mod/models/block/vertical_slab.json)

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

Der Wert `bottom` wird den Platzhalter `#bottom` ersetzen und so weiter. **Füge es in den Ordner `resources/assets/example-mod/models/block/` ein.**

### Benutzerdefinierte Modelle {#custom-model}

Eine weitere Sache, die wir benötigen, ist eine Instanz der Klasse `Model`. Sie wird das tatsächliche [übergeordnete Blockmodell](#parent-block-model) in unserem Mod repräsentieren.

@[code lang=java transcludeWith=:::custom-model](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModModelProvider.java)

Die Methode `block()` erstellt ein neues `Model`, das auf die Datei `vertical_slab.json` in unserem Ordner `resources/assets/example-mod/models/block/` verweist.
Die `TextureSlot`s repräsentieren die "Platzhalter" (`#bottom`, `#top`, ...) als ein Objekt.

### Die Textur Map verwenden {#using-texture-map}

Was macht das`TextureMapping`? Sie liefert die Identifikatoren, die auf die Textur verweisen. Technisch gesehen verhält sie sich wie eine normale Map - man verbindet einen `TextureSlot` (Schlüssel) mit einem `Identifier` (Wert).

Du kannst entweder die von Vanilla verwenden, wie `TextureMapping.cube()` (die alle `TextureKeys` mit dem selben `Identifier` verknüpft), oder eine Neue erstellen, indem du eine neue Instanz erstellst und dann `.put()` aufrufst, um Schlüssel mit Werten zu verknüpfen.

::: tip

`TextureMapping.cube()` verknüpft alle `TextureSlot`s mit dem selben `Identifier`, egal wie viele es davon gibt!

:::

Da wir die Eichenstammtexturen nutzen wollen, aber die `BOTTOM`, `TOP` und `SIDE` `TextureSlot`s haben, müssen wir eine neue erstellen.

@[code lang=java transcludeWith=:::custom-texture-map](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModModelProvider.java)

Die `bottom` und `top` Flächen werden `oak_log_top.png` verwenden, die Seiten werden `oak_log.png` verwenden.

::: warning

Alle `TextureSlot`s in deiner TextureMap **müssen** mit den `TextureSlot`s in deinem übergeordneten Blockmodell übereinstimmen!

:::

### Benutzerdefinierte `BlockModelDefinitionGenerator`-Methode {#custom-supplier-method}

Der `BlockModelDefinitionGenerator` beinhaltet alle Varianten an Blockzuständen, deren Rotation und anderen Optionen, wie die UV-Sperre.

@[code lang=java transcludeWith=:::custom-supplier](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModModelProvider.java)

Zuerst erstellen wir einen neuen `BlockModelDefinitionGenerator` unter Verwendung von `MultiVariantGenerator.dispatch()`.
Dann erstellen wir einen neuen `PropertyDispatch`, der Parameter für alle Varianten des Blocks beinhaltet, in diesem Fall `FACING` und `SINGLE` und übergeben diesen in den `MultiVariantGenerator`.
Gebe an, welches Modell und welche Transformation (uvlock, rotation) bei der Verwendung von `.register()` genutzt werden sollen.
Zum Beispiel:

- Zeile 6: _Einzelne_ Stufe nach Norden ausgerichtet, daher verwenden wir das Modell ohne Rotation
- Zeile 9: _einzelne_ Stufe nach Westen ausgerichtet, daher rotieren wir das Modell um 270° um die Y-Achse
- Zeilen 10-13: _Nicht-Einzelne_ Stufe, die wie ein voller Block aussieht und nicht rotiert werden muss

### Benutzerdefinierte Methode für den Datengenerator {#custom-datagen-method}

Der letzte Schritt - die Erstellung der tatsächlichen Methode, die du aufrufen kannst und die die JSONs generieren wird.
Aber für was sind die Parameter?

1. `BlockModelGenerators generator`, das gleiche, dass wir an `generateBlockStateModels` übergeben haben.
2. `Block vertSlabBlock` ist der Block, zu dem wir die JSONs generieren werden.
3. `Block fullBlock` - ist das Modell, dass genutzt wird, wenn die Eigenschaft `SINGLE` false ist = der Stufenblock sieht wie ein voller Block aus.
4. `TextureMapping textures` definiert die tatsächlichen Texturen, die das Modell nutzt. Siehe das Kapitel [die Texture Map verwenden](#using-texture-map).

@[code lang=java transcludeWith=:::custom-gen](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModModelProvider.java)

Zuerst erhalten wir den `Identifier` des einzelnen Stufenmodell mit `VERTICAL_SLAB.create()`. Dann erhalten wir den `Identifier` des vollen Blockmodell mit `ModelLocationUtils.getModelLocation()`.

Wir können dann diese zwei Modelle an `createVerticalSlabBlockStates` übergeben, welches selbst in den Consumer `blockStateOutput` übergeben wird, welcher die JSON-Dateien für die Modelle generiert.

Schließlich erstellen wir ein Modell für das Item der vertikalen Stufe mit `BlockModelGenerators.registerSimpleItemModel()`.

Und das ist alles! Jetzt müssen wir nur noch unsere Methode in unserem `ModelProvider` aufrufen:

@[code lang=java transcludeWith=:::custom-method-call](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModModelProvider.java)

## Quellen und Links {#sources-and-links}

Du kannst für weitere Informationen die Beispieltests der [Fabric API](https://github.com/FabricMC/fabric/blob/1.21.11/fabric-data-generation-api-v1/src/) und die im [Beispiel-Mod](https://github.com/FabricMC/fabric-docs/tree/main/reference) dieser Dokumentation ansehen.

Du kannst auch weitere Beispiele für die Verwendung von benutzerdefinierten Methoden für den Datengenerator finden, indem du den Opens Source-Code von Mods durchsuchst, zum Beispiel [Vanilla+ Blocks](https://github.com/Fellteros/vanillablocksplus) und [Vanilla+ Verticals](https://github.com/Fellteros/vanillavsplus) von Fellteros.
