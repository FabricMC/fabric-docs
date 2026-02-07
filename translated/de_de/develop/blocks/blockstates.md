---
title: Blockzustände
description: Lerne, warum Blockzustände eine großartige Möglichkeit sind, Funktionalität zu deinem Block hinzuzufügen.
authors:
  - IMB11
---

Ein Blockzustand entspricht ein wenig Daten, die einem einzelnen Block in der Minecraft-Welt zugeordnet sind und Informationen über den Block in Form von Eigenschaften enthält - einige Beispiele für Eigenschaften, die Vanilla in Blockzuständen speichert:

- Rotation: Hauptsächlich für Baumstämme und andere natürliche Blöcke verwendet.
- Activated: Wird häufig in Redstone-Geräten und Blöcken wie dem Ofen oder dem Räucherofen verwendet.
- Age: Wird in Samen, Pflanzen, Setzlingen, Seetang, etc. verwendet

Du kannst wahrscheinlich sehen, warum sie nützlich sind - sie vermeiden die Notwendigkeit, NBT-Daten in einer Blockentität zu speichern - was die Weltgröße reduziert und TPS-Probleme verhindert!

Blockzustand-Definitionen finden sich im Ordner `assets/example-mod/blockstates`.

## Beispiel: Säulenblock {#pillar-block}

<!-- Note: This example could be used for a custom recipe types guide, a condensor machine block with a custom "Condensing" recipe? -->

Minecraft verfügt bereits über einige benutzerdefinierte Klassen, mit denen man schnell bestimmte Arten von Blöcken erstellen kann - in diesem Beispiel wird die Erstellung eines Blocks mit der Eigenschaft `axis` durch die Erstellung eines „Condensed Oak Log“-Blocks erläutert.

Die Vanilla `RotatedPillarBlock` Klasse erlaubt, dass der Block in der X, Y oder Z Axe platziert werden kann.

@[code transcludeWith=:::3](@/reference/latest/src/main/java/com/example/docs/block/ModBlocks.java)

Säulenblöcke haben zwei Texturen, oben und an der Seite - sie verwenden das Modell `block/cube_column`.

Wie immer bei allen Blocktexturen befinden sich die Texturdateien in `assets/example-mod/textures/block`

<DownloadEntry visualURL="/assets/develop/blocks/blockstates_0_large.png" downloadURL="/assets/develop/blocks/condensed_oak_log_textures.zip">Texturen</DownloadEntry>

Da der Säulenblock zwei Positionen hat, eine horizontale und eine vertikale, müssen wir zwei separate Modelldateien erstellen:

- `condensed_oak_log_horizontal.json` welche das `block/cube_column_horizontal` Modell erweitert.
- `condensed_oak_log.json` welche das `block/cube_column` Modell erweitert.

Ein Beispiel der Datei `condensed_oak_log_horizontal.json`:

@[code](@/reference/latest/src/main/generated/assets/example-mod/models/block/condensed_oak_log_horizontal.json)

::: info

Beachte, dass Blockzustandsdateien im Ordner `assets/example-mod/blockstates` zu finden sind. Der Name der Blockzustandsdatei sollte mit der Block-ID übereinstimmen, die bei der Registrierung deines Blocks in der Klasse `ModBlocks` verwendet wurde. Wenn die Block-ID beispielsweise `condensed_oak_log` lautet, sollte die Datei `condensed_oak_log.json` heißen.

Einen tieferen Einblick in alle Modifikatoren, die in den Blockzustand-Dateien verfügbar sind, findest du auf der Seite [Minecraft Wiki - Models (Block States)](https://minecraft.wiki/w/Tutorials/Models#Block_states).

:::

Als nächstes müssen wir eine Blockzustand-Datei erstellen. Die Blockzustand-Datei ist der Ort, an dem sich die Magie abspielt - Säulenblöcke haben drei Achsen, daher werden wir für die folgenden Situationen spezielle Modelle verwenden:

- `axis=x` - Wenn der Block entlang der X-Achse platziert wird, drehen wir das Modell so, dass es in die positive X-Richtung zeigt.
- `axis=y` - Wenn der Block entlang der Y-Achse platziert wird, verwenden wir das normale vertikale Modell.
- `axis=z` - Wenn der Block entlang der Z-Achse platziert wird, drehen wir das Modell so, dass es in die positive X-Richtung zeigt.

@[code](@/reference/latest/src/main/generated/assets/example-mod/blockstates/condensed_oak_log.json)

Wie immer musst du eine Übersetzung für deinen Block und ein Objektmodell erstellen, das einem der beiden Modelle übergeordnet ist.

![Beispiel eines Säulenblock im Spiel](/assets/develop/blocks/blockstates_1.png)

## Benutzerdefinierte Blockzustände {#custom-block-states}

Benutzerdefinierte Blockzustände sind ideal, wenn dein Block einzigartige Eigenschaften hat - manchmal kannst du feststellen, dass dein Block Vanilla-Eigenschaften wiederverwenden kann.

Dieses Beispiel wird eine einzigartiges boolesche Eigenschaft mit dem Namen `activated` erstellen - wenn ein Spieler den Block rechtsklickt, wird der Block von `activated=false` zu `activated=true` wechseln - und seine Textur entsprechend ändern.

### Die Eigenschaft erstellen {#creating-the-property}

Zunächst musst du die Eigenschaft selbst erstellen - da es sich um eine boolesche Eigenschaft handelt, wird die Methode `BooleanProperty.create` verwendet.

@[code transcludeWith=:::1](@/reference/latest/src/main/java/com/example/docs/block/custom/PrismarineLampBlock.java)

Als Nächstes müssen wir die Eigenschaft mit der Methode `createBlockStateDefinition` an den Blockzustand-Manager anhängen. Du musst die Methode überschreiben, um auf den Builder zuzugreifen:

@[code transcludeWith=:::2](@/reference/latest/src/main/java/com/example/docs/block/custom/PrismarineLampBlock.java)

Außerdem musst du im Konstruktor deines benutzerdefinierten Blocks einen Standardzustand für die Eigenschaft `activated` festlegen.

@[code transcludeWith=:::3](@/reference/latest/src/main/java/com/example/docs/block/custom/PrismarineLampBlock.java)

### Die Eigenschaft nutzen {#using-the-property}

In diesem Beispiel wird die boolesche Eigenschaft `activated` umgeschaltet, wenn der Spieler mit dem Block interagiert. Hierfür können wir die Methode `useWithoutItem` überschreiben:

@[code transcludeWith=:::4](@/reference/latest/src/main/java/com/example/docs/block/custom/PrismarineLampBlock.java)

### Die Eigenschaft visualisieren {#visualizing-the-property}

Bevor du die Blockzustand-Datei erstellst, musst du Texturen für den aktivierten und den deaktivierten Zustand des Blocks sowie für das Blockmodell bereitstellen.

<DownloadEntry visualURL="/assets/develop/blocks/blockstates_2_large.png" downloadURL="/assets/develop/blocks/prismarine_lamp_textures.zip">Texturen</DownloadEntry>

Nutze dein Wissen über Blockmodelle, um zwei Modelle für den Block zu erstellen: Eines für den aktivierten Zustand und eines für den deaktivierten Zustand. Danach kannst du mit der Erstellung der Blockzustand-Datei beginnen.

Da du eine neue Eigenschaft erstellt hast, musst du die Blockzustand-Datei für den Block aktualisieren, um diese Eigenschaft zu berücksichtigen.

Wenn du mehrere Eigenschaften bei einem Block hast, musst du alle möglichen Kombinationen berücksichtigen. Zum Beispiel würden `activated` und `axis` zu 6 Kombinationen führen (zwei mögliche Werte für `activated` und drei mögliche Werte für `axis`).

Da es für diesen Block nur zwei mögliche Varianten gibt, da er nur eine Eigenschaft hat (`activated`), sieht der Blockzustand JSON etwa so aus:

@[code](@/reference/latest/src/main/generated/assets/example-mod/blockstates/prismarine_lamp.json)

::: tip

Vergiss nicht ein [Client Item](../items/first-item#creating-the-client-item) für den Block zu erstellen, damit es im Inventar angezeigt wird!

:::

Da es sich bei dem Beispielblock um eine Lampe handelt, müssen wir auch dafür sorgen, dass sie Licht ausstrahlt, wenn die Eigenschaft `activated` true ist. Dies kann über die Blockeinstellungen erfolgen, die bei der Registrierung des Blocks an den Konstruktor übergeben werden.

Du kannst die Methode `lightLevel` verwenden, um die vom Block ausgestrahlte Lichtstärke einzustellen. Wir können eine statische Methode in der Klasse `PrismarineLampBlock` erstellen, um die Lichtstärke auf der Grundlage der Eigenschaft `activated` zurückzugeben, und sie als Methodenreferenz an die Methode `lightLevel` übergeben:

@[code transcludeWith=:::5](@/reference/latest/src/main/java/com/example/docs/block/custom/PrismarineLampBlock.java)

@[code transcludeWith=:::4](@/reference/latest/src/main/java/com/example/docs/block/ModBlocks.java)

<!-- Note: This block can be a great starter for a redstone block interactivity page, maybe triggering the blockstate based on redstone input? -->

Wenn du alles vervollständigt hast, sollte das Endergebnis etwa so aussehen wie das folgende:

<VideoPlayer src="/assets/develop/blocks/blockstates_3.webm">Prismarin Lampe im Spiel</VideoPlayer>
