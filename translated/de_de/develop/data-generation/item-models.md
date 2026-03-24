---
title: Generierung von Item Modellen
description: Ein Leitfaden zur Generierung von Itemmodellen mit dem Datengenerator.
authors:
  - CelDaemon
  - Fellteros
  - skycatminepokie
  - VatinMc
---

<!---->

:::info VORAUSSETZUNGEN

Stelle sicher, dass du die [Einrichtung des Datengenerators](./setup) abgeschlossen und dein [erstes Item](../items/first-item) erstellt hast.

:::

Für jedes Itemmodell, das wir generieren möchten, müssen wir zwei separate JSON-Dateien erstellen:

1. Ein **Itemmodell**, das die Texturen, die Drehung und das allgemeine Aussehen des Items definiert. Es wird im Verzeichnis `generated/assets/example-mod/models/item` abgelegt.
2. Ein **Client Item**, das anhand verschiedener Kriterien wie Komponenten, Interaktionen und mehr definiert, welches Modell verwendet werden soll. Es wird im Verzeichnis `generated/assets/example-mod/items` abgelegt.

## Einrichtung {#setup}

Zuerst müssen wir unseren Modell Provider erstellen.

::: tip

Du kannst den `FabricModelProvider` wiederverwenden, der in [Generation von Blockmodellen](./block-models#setup) erstellt wurde.

:::

Erstellen eine Klasse, die von `FabricModelProvider` erbt, und implementiere die beiden abstrakten Methoden: `generateBlockStateModels` und `generateItemModels`.
Dann erstelle einen Konstruktor, der mit `super` übereinstimmt.

@[code transcludeWith=:::provider](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModModelProvider.java)

Registriere diese Klasse in deinem `DataGeneratorEntrypoint` innerhalb der Methode `onInitializeDataGenerator`.

@[code transcludeWith=:::datagen-models:register](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModDataGenerator.java)

## Eingebaute Itemmodelle {#built-in}

Für Itemmodelle verwenden wir die Methode `generateItemModels`. Sein Parameter `ItemModelGenerators itemModelGenerator` ist für die Generierung der Itemmodelle zuständig und enthält auch Methoden, um dies zu tun.

Hier findest du eine Übersicht über die am häufigsten verwendeten Methoden des Itemmodell Generators.

### Einfach {#simple}

Einfache Itemmodelle sind der Standard und werden von den meisten Minecraft Items verwendet. Ihr übergeordnetes Modell ist `GENERATED`. Sie verwenden ihre 2D-Textur im Inventar und werden im Spiel in 3D gerendert. Ein Beispiel wären Boote, Kerzen oder Farbstoffe.

::: tabs

== Quellcode

@[code transcludeWith=:::generated](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModModelProvider.java)

== Client Item

`generated/assets/example-mod/items/ruby.json`

@[code](@/reference/latest/src/main/generated/assets/example-mod/items/ruby.json)

== Itemmodell

`generated/assets/example-mod/models/item/ruby.json`

@[code](@/reference/latest/src/main/generated/assets/example-mod/models/item/ruby.json)

Die genauen Standardwerte für Rotation, Skalierung und Positionierung des Modells findest du in der Datei [`generated.json` aus den Minecraft-Assets](https://mcasset.cloud/1.21.11/assets/minecraft/models/item/generated.json).

== Textur

<DownloadEntry visualURL="/assets/develop/data-generation/item-model/ruby_big.png" downloadURL="/assets/develop/data-generation/item-model/ruby.png">Rubin Textur</DownloadEntry>

:::

### Handgehalten {#handheld}

Handgehaltene Itemmodelle werden in der Regel für Werkzeuge und Waffen (Äxte, Schwerter, Dreizack) verwendet. Sie sind etwas anders rotiert und positioniert als die einfachen Modelle, damit sie in der Hand natürlicher aussehen.

::: tabs

== Quellcode

@[code transcludeWith=:::handheld](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModModelProvider.java)

== Client Item

`generated/assets/example-mod/items/guidite_axe.json`

@[code](@/reference/latest/src/main/generated/assets/example-mod/items/guidite_axe.json)

== Itemmodell

`generated/assets/example-mod/models/item/guidite_axe.json`

@[code](@/reference/latest/src/main/generated/assets/example-mod/models/item/guidite_axe.json)

Die genauen Standardwerte für Rotation, Skalierung und Positionierung des Modells findest du in der Datei [`handheld.json` aus den Minecraft-Assets](https://mcasset.cloud/1.21.11/assets/minecraft/models/item/handheld.json).

== Textur

<DownloadEntry visualURL="/assets/develop/data-generation/item-model/guidite_axe_big.png" downloadURL="/assets/develop/data-generation/item-model/guidite_axe.png">Guidite-Axt-Textur</DownloadEntry>

:::

### Färbbar {#dyeable}

Die Methode für färbbare Items generiert ein einfaches Itemmodell und ein Client Item, der die Farbe der Färbung angibt. Diese Methode erfordert einen Standard-Dezimalwert für die Farbe, der verwendet wird, wenn das Item nicht gefärbt ist. Der Standardwert für Leder ist `0xFFA06540`.

:::: tabs

== Quellcode

@[code transcludeWith=:::dyeable](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModModelProvider.java)

:::warning WICHTIG

Du musst dein Item zu dem Tag `temTags.DYEABLE` hinzufügen, um es in deinem Inventar färben zu können!

:::

== Client Item

`generated/assets/example-mod/items/leather_gloves.json`

@[code](@/reference/latest/src/main/generated/assets/example-mod/items/leather_gloves.json)

== Itemmodell

`generated/assets/example-mod/models/item/leather_gloves.json`

@[code](@/reference/latest/src/main/generated/assets/example-mod/models/item/leather_gloves.json)

== Textur

<DownloadEntry visualURL="/assets/develop/data-generation/item-model/leather_gloves_big.png" downloadURL="/assets/develop/data-generation/item-model/leather_gloves.png">Lederhandschuhe Textur</DownloadEntry>

== Vorschau

![Lederhandschuhe färben](/assets/develop/data-generation/item-model/leather_gloves_dyeing.png)

::::

### Bedingt {#conditional}

Als Nächstes werden wir uns mit der Erstellung von Itemmodellen befassen, deren Darstellung sich ändert, wenn eine bestimmte Bedingung erfüllt ist, die durch den zweiten Parameter `BooleanProperty` angegeben wird. Hier sind einige davon:

| Eigenschaft     | Beschreibung                                                                                                                                                                     |
| --------------- | -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| `IsKeybindDown` | Wahr, wenn eine bestimmte Taste gedrückt wird.                                                                                                                   |
| `IsUsingItem`   | Wahr, wenn das Item verwendet wird (z. B. beim Blocken mit einem Schild).                                     |
| `Broken`        | Wahr, wenn das Item eine Haltbarkeit von 0 hat (z. B. ändert die Elytra die Textur, wenn sie zerbrochen ist). |
| `HasComponent`  | Wahr, wenn das Item eine bestimmte Komponente enthält.                                                                                                           |

Der dritte und vierte Parameter sind die Modelle, die verwendet werden sollen, wenn die Eigenschaft `true` oder `false` ist.

:::: tabs

== Quellcode

@[code transcludeWith=:::condition](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModModelProvider.java)

:::warning WICHTIG

Um den in `ItemModelUtils.plainModel()` übergebenen `Identifier` zu erhalten, verwende immer `itemModelGenerator.createFlatItemModel()`, da sonst nur die Client Items generiert werden, nicht jedoch die Itemmodelle!

:::

== Client Item

`generated/assets/example-mod/items/flashlight.json`

@[code](@/reference/latest/src/main/generated/assets/example-mod/items/flashlight.json)

== Itemmodelle

`generated/assets/example-mod/models/item/flashlight.json`

@[code](@/reference/latest/src/main/generated/assets/example-mod/models/item/flashlight.json)

`generated/assets/example-mod/models/item/flashlight_lit.json`

@[code](@/reference/latest/src/main/generated/assets/example-mod/models/item/flashlight_lit.json)

== Texturen

<DownloadEntry visualURL="/assets/develop/data-generation/item-model/flashlight_textures_big.png" downloadURL="/assets/develop/data-generation/item-model/flashlight_textures.zip">Taschenlampen-Texturen</DownloadEntry>

== Vorschau

<VideoPlayer src="/assets/develop/data-generation/item-model/flashlight_turning_on.webm">Taschenlampe ein- und ausschalten</VideoPlayer>

::::

### Zusammensetzung {#composite}

Zusammengesetzte Itemmodelle bestehen aus einer oder mehreren Texturen, die übereinander gelegt sind. Dafür gibt es keine Vanilla Methoden; du musst das Feld `itemModelOutput` des `itemModelGenerator` verwenden und `accept()` darauf aufrufen.

::: tabs

== Quellcode

@[code transcludeWith=:::composite](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModModelProvider.java)

== Client Item

`generated/assets/example-mod/items/enhanced_hoe.json`

@[code](@/reference/latest/src/main/generated/assets/example-mod/items/enhanced_hoe.json)

== Itemmodelle

`generated/assets/example-mod/models/item/enhanced_hoe.json`

@[code](@/reference/latest/src/main/generated/assets/example-mod/models/item/enhanced_hoe.json)

`generated/assets/example-mod/models/item/enhanced_hoe_plus.json`

@[code](@/reference/latest/src/main/generated/assets/example-mod/models/item/enhanced_hoe_plus.json)

== Texturen

<DownloadEntry visualURL="/assets/develop/data-generation/item-model/enhanced_hoe_textures_big.png" downloadURL="/assets/develop/data-generation/item-model/enhanced_hoe_textures.zip">Verbesserte Hacken-Texturen</DownloadEntry>

:::

### Auswahl {#select}

Rendert ein Itemmodell basierend auf dem Wert einer bestimmten Eigenschaft. Dies sind einige davon:

| Eigenschaft         | Beschreibung                                                                                                                                                                       |
| ------------------- | ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| `ContextDimension`  | Rendert ein Itemmodell basierend auf der Dimension, in der sich der Spieler befindet (Oberwelt, Nether, End).                                   |
| `MainHand`          | Rendert ein Itemmodell, wenn das Item in der Haupthand des Spielers ausgerüstet ist.                                                                               |
| `DisplayContext`    | Rendert ein Itemmodell basierend auf der Position des Objekts (`ground`, `fixed`, `head`, ...). |
| `ContextEntityType` | Rendert ein Itemmodell basierend auf der Entität, die das Item hält.                                                                                               |

In diesem Beispiel ändert das Item seine Textur beim Wechsel zwischen den Dimensionen: In der Oberwelt ist es grün, im Nether rot und im End schwarz.

::: tabs

== Quellcode

@[code transcludeWith=:::select](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModModelProvider.java)

== Client Item

`generated/assets/example-mod/items/dimensional_crystal.json`

@[code](@/reference/latest/src/main/generated/assets/example-mod/items/dimensional_crystal.json)

== Itemmodelle

`generated/assets/example-mod/models/item/dimensional_crystal_overworld.json`

@[code](@/reference/latest/src/main/generated/assets/example-mod/models/item/dimensional_crystal_overworld.json)

`generated/assets/example-mod/models/item/dimensional_crystal_nether.json`

@[code](@/reference/latest/src/main/generated/assets/example-mod/models/item/dimensional_crystal_nether.json)

`generated/assets/example-mod/models/item/dimensional_crystal_end.json`

@[code](@/reference/latest/src/main/generated/assets/example-mod/models/item/dimensional_crystal_end.json)

== Texturen

<DownloadEntry visualURL="/assets/develop/data-generation/item-model/dimensional_crystal_textures_big.png" downloadURL="/assets/develop/data-generation/item-model/dimensional_crystal_textures.zip">Dimensionale Kristalltexturen</DownloadEntry>

== Vorschau

![Dimensionaler Kristall ändert die Textur basierend auf der Dimension](/assets/develop/data-generation/item-model/crystal.png)

:::

### Reichweiten Erweiterung {#range-dispatch}

Rendert ein Itemmodell basierend auf dem Wert einer numerischen Eigenschaft. Nimmt ein Item und eine Liste von Varianten entgegen, die jeweils mit einem Wert gepaart sind. Beispiele hierfür sind der Kompass, der Bogen und der Pinsel.

Es gibt eine ganze Reihe unterstützter Eigenschaften, hier einige Beispiele:

| Eigenschaft   | Beschreibung                                                                                                                 |
| ------------- | ---------------------------------------------------------------------------------------------------------------------------- |
| `Cooldown`    | Rendert ein Itemmodell basierend auf der verbleibenden Abklingzeit des Items.                                |
| `Count`       | Rendert ein Itemmodell basierend auf der Stack-Größe.                                                        |
| `UseDuration` | Rendert ein Itemmodell basierend darauf, wie lange das Item verwendet wird.                                  |
| `Damage`      | Rendert ein Itemmodell basierend auf dem Angriffsschaden (Komponente `minecraft:damage`). |

In diesem Beispiel wird `Count` verwendet, um die Textur je nach Stackgröße von einem Messer auf bis zu drei Messer zu ändern.

::: tabs

== Quellcode

@[code transcludeWith=:::range-dispatch](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModModelProvider.java)

== Client Item

`generated/assets/example-mod/items/throwing_knives.json`

@[code](@/reference/latest/src/main/generated/assets/example-mod/items/throwing_knives.json)

== Itemmodelle

`generated/assets/example-mod/models/item/throwing_knives_one.json`

@[code](@/reference/latest/src/main/generated/assets/example-mod/models/item/throwing_knives_one.json)

`generated/assets/example-mod/models/item/throwing_knives_two.json`

@[code](@/reference/latest/src/main/generated/assets/example-mod/models/item/throwing_knives_two.json)

`generated/assets/example-mod/models/item/throwing_knives_three.json`

@[code](@/reference/latest/src/main/generated/assets/example-mod/models/item/throwing_knives_three.json)

== Texturen

<DownloadEntry visualURL="/assets/develop/data-generation/item-model/throwing_knives_textures_big.png" downloadURL="/assets/develop/data-generation/item-model/throwing_knives_textures.zip">Texturen für Wurfmesser</DownloadEntry>

== Vorschau

![Wurfmesser verändern die Textur basierend auf der Anzahl](/assets/develop/data-generation/item-model/throwing_knives_example.png)

:::

## Benutzerdefinierte Itemmodelle {#custom}

Generierung von Itemmodellen muss nicht nur mit Standardmethoden erfolgen; du kannst natürlich auch deine eigenen erstellen. In diesem Abschnitt erstellen wir ein benutzerdefiniertes Modell für ein Ballon Item.

Alle Felder und Methoden für diesen Teil des Tutorials werden in einer statischen inneren Klasse mit dem Namen `CustomItemModelGenerator` deklariert.

:::details Zeige `CustomItemModelGenerator`

@[code transcludeWith=:::custom-item-model-generator:::](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModModelProvider.java)

:::

### Erstellen eines benutzerdefinierten übergeordneten Itemmodell {#custom-parent}

Zunächst erstellen wir ein übergeordnetes Itemmodell, das definiert, wie das Item im Spiel aussieht. Nehmen wir an, wir möchten, dass der Ballon wie einfache Itemmodelle aussieht, jedoch hoch skaliert.

Dazu erstellen wir die Datei `resources/assets/example-mod/models/item/scaled2x.json`, legen als übergeordnetes Modell `item/generated` fest und überschreiben dann die Skalierung.

@[code](@/reference/latest/src/main/resources/assets/example-mod/models/item/scaled2x.json)

Dadurch wird das Modell visuell doppelt so groß wie die einfachen Modelle.

### Das `ModelTemplate` erstellen {#custom-item-model}

Als Nächstes müssen wir eine Instanz der Klasse `ModelTemplate` erstellen. Sie wird das tatsächliche [übergeordnete Itemmodell](#custom-parent) in unserem Mod repräsentieren.

@[code transcludeWith=:::custom-item-model:::](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModModelProvider.java)

Die Methode `item()` erstellt eine neue Instanz von `ModelTemplate`, die auf die zuvor erstellte Datei `scaled2x.json` verweist.

Textur-Slot `LAYER0` repräsentiert die Texturvariable `#layer0`, welche dann durch einen Bezeichner ersetzt wird, der auf eine Textur verweist.

### Hinzufügen einer benutzdefinierten Methode für den Datengenerator {#custom-datagen-method}

Der letzte Schritt besteht darin, eine benutzerdefinierte Methode zu erstellen, die in der Methode `generateItemModels()` aufgerufen wird und für die Generierung unserer Itemmodelle zuständig ist.

@[code transcludeWith=:::custom-item-datagen-method](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModModelProvider.java)

Lasst uns durchgehen, wofür die Parameter stehen:

1. `Item item`: Das Item, für welches wir die Modelle generieren.
2. `ItemModelGenerators generator`: Das Selbe, das an die Methode `generateItemModels()` übergeben wird. Wird für dessen Felder verwendet.

Zuerst erhalten wir den `Identifier` des Items mit `SCALED2X.create()`, wobei wir ein `TextureMapping` und den `modelOutput` aus unserem Parameter `generator` übergeben.

Dann verwenden wir ein weiteres Feld, den `itemModelOutput` (das im Wesentlichen als Consumer dient), und verwenden die Methode `accept()`, damit die Modelle tatsächlich generiert werden.

### Die benutzdefinierte Methode aufrufen {#custom-call}

Jetzt müssen wir nur noch unsere Methode in der Methode `generateItemModels()` aufrufen.

@[code transcludeWith=:::custom-balloon](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModModelProvider.java)

Vergiss nicht, eine Texturdatei hinzuzufügen!

<DownloadEntry visualURL="/assets/develop/data-generation/item-model/balloon_big.png" downloadURL="/assets/develop/data-generation/item-model/balloon.png">Ballon Textur</DownloadEntry>

## Quellen und Links {#sources-and-links}

Weitere Informationen findest du in den Beispiel-Tests in der [Fabric API](https://github.com/FabricMC/fabric/blob/1.21.11/fabric-data-generation-api-v1/src/) und im [Beispiel Mod](https://github.com/FabricMC/fabric-docs/tree/main/reference) dieser Dokumentation.
