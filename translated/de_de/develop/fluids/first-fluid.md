---
title: Deine erste Flüssigkeit erstellen
description: Lerne, wie du deine erste benutzerdefinierte Flüssigkeit in Minecraft erstellen kannst.
authors:
  - AlbanischeWurst
  - AlexiyOrlov
  - cassiancc
  - CelDaemon
  - Clomclem
  - comp500
  - Daomephsta
  - Earthcomputer
  - florensie
  - Fusion-Flux
  - InfinityChances
  - Kilip1000
  - MaxURhino
  - SolidBlock-cn
  - SuperSoupr
  - Virtuoel
  - UpcraftLP
authors-nogithub:
  - alfiejfs
  - salvopelux
---

<!---->

:::info VORAUSSETZUNGEN

Zunächst musst du verstehen, wie man [einen Block erstellt](../blocks/first-block) und wie man [ein Item erstellt](../items/first-item).

:::

Dieses Beispiel wird die Erstellung einer Säureflüssigkeit behandeln, die Entitäten, die darin steht, Schaden zufügt, schwächt und blind macht. Dazu benötigen wir zwei Flüssigkeits-Instanzen für den Ausgangs- und den Endzustand, einen Flüssigkeitsblock, ein Eimer-Item und ein Flüssigkeits-Tag.

## Eine Flüssigkeitsklasse erstellen {#creating-the-fluid-class}

Zunächst erstellen wir eine abstrakte Klasse, in diesem Fall mit dem Namen `AcidFluid`, die von der Basisklasse `FlowingFluid` erbt. Dann werden wir alle Methoden überschreiben, die für die Quelle und die fließende Flüssigkeit gleich sein sollen.

Achte besonders auf die folgenden Methoden:

- `animateTick` wird zur Darstellung von Partikeln und zur Wiedergabe von Sound verwendet. Das unten gezeigte Verhalten basiert auf Wasser, das beim Fließen Geräusche abspielt und unter Wasser sprudelnde Partikel hat.
- `entityInside` dient dazu, zu steuern, was geschehen soll, wenn eine Entität die Flüssigkeit berührt. Wir nehmen Wasser als Grundlage und löschen damit jedes Feuer bei Entitäten, sorgen aber auch dafür, dass es den Entitäten im Inneren Schaden zufügt, sie schwächt und blind macht - es ist schließlich Säure.
- `canBeReplacedWith` übernimmt einen Teil der fließenden Logik - beachte, dass `ModFluidTags.ACID` noch nicht definiert ist; darauf gehen wir am Ende ein.

Wenn wir all das zusammenfassen, erhalten wir folgende Klasse:

@[code transcludeWith=:::abstractFluid](@/reference/latest/src/main/java/com/example/docs/fluid/custom/AcidFluid.java)

Innerhalb von `AcidFluid` werden wir zwei Unterklassen für die Flüssigkeiten `Source` und `Flowing` erstellen.

@[code transcludeWith=:::fluidSubclasses](@/reference/latest/src/main/java/com/example/docs/fluid/custom/AcidFluid.java)

### Flüssigkeiten registrieren {#registering-fluids}

Als Nächstes erstellen wir eine Klasse, um alle Flüssigkeits-Instanzen zu registrieren. Wir nennen sie `ModFluids`.

@[code transcludeWith=:::register](@/reference/latest/src/main/java/com/example/docs/fluid/ModFluids.java)

Genau wie bei Blöcken musst du sicherstellen, dass die Klasse geladen ist, damit alle statischen Felder, die deine Flüssigkeits-Instanzen enthalten, initialisiert werden. Du kannst dies tun, indem du eine Dummy-Methode `initialize` erstellst, die in deinem [Mod-Initialisierer](../getting-started/project-structure#entrypoints) aufgerufen werden kann, um die statische Initialisierung auszulösen.

Kehren nun zur Klasse `AcidFluid` zurück und füge diese Methoden hinzu, um die registrierten Flüssigkeits-Instanzen mit dieser Flüssigkeit zu verknüpfen:

@[code transcludeWith=:::sources](@/reference/latest/src/main/java/com/example/docs/fluid/custom/AcidFluid.java)

Bisher haben wir den Ausgangszustand der Flüssigkeit und ihren Strömungszustand erfasst. Als Nächstes müssen wir einen Eimer und einen `LiquidBlock` dafür registrieren.

### Flüssigkeitsblöcke registrieren {#fluid-blocks}

Fügen wir nun einen Flüssigkeitsblock für unsere Flüssigkeit hinzu. Dies wird von einigen Befehlen wie `setblock` benötigt, damit deine Flüssigkeit in der Welt existieren kann. Falls du das noch nicht getan hast, solltest du dir ansehen, [wie du deinen ersten Block erstellst](../blocks/first-block).

Öffne deine Klasse `ModBlocks` und registriere den folgenden `LiquidBlock`:

@[code transcludeWith=:::acid](@/reference/latest/src/main/java/com/example/docs/block/ModBlocks.java)

Überschreibe dann diese Methode in `AcidFluid`, um deinen Block mit der Flüssigkeit zu verknüpfen:

@[code transcludeWith=:::legacyBlock](@/reference/latest/src/main/java/com/example/docs/fluid/custom/AcidFluid.java)

### Eimer registrieren {#buckets}

Flüssigkeiten werden in Minecraft normalerweise in Eimern aufbewahrt. Schauen wir uns also an, wie wir ein Item mit dem Namen Eimer mit Säure hinzufügen können. Falls du das noch nicht getan hast, solltest du dir ansehen, [wie du dein erstes Item erstellst](../items/first-item).

Öffne deine Klasse `ModItems` und registriere das folgende `BucketItem`:

@[code transcludeWith=:::acid_bucket](@/reference/latest/src/main/java/com/example/docs/item/ModItems.java)

Überschreibe dann diese Methode in `AcidFluid`, um deinen Eimer mit der Flüssigkeit zu verknüpfen:

@[code transcludeWith=:::bucket](@/reference/latest/src/main/java/com/example/docs/fluid/custom/AcidFluid.java)

Denke daran, dass Items eine Übersetzung, eine [Textur](../items/first-item#adding-a-texture), ein [Modell](../items/first-item#adding-a-model) und ein [Client-Item](../items/first-item#creating-the-client-item) mit dem Namen `acid_bucket` benötigen, damit sie korrekt dargestellt werden. Eine Beispiel-Textur ist unten aufgeführt.

<DownloadEntry visualURL="/assets/develop/fluids/acid_bucket.png" downloadURL="/assets/develop/fluids/acid_bucket_small.png">Textur</DownloadEntry>

Es wird außerdem empfohlen, den Eimer deines Mods dem Item-Tag `ConventionalItemTags.BUCKET` hinzuzufügen, damit andere Mods ihn entsprechend verarbeiten können. Entweder [manuell](#tagging) oder durch [Datengenerierung](../data-generation/tags).

## Taggen deiner Flüssigkeiten {#tagging}

::: info

Benutzer von [Datengenerierung](../data-generation/tags) möchten möglicherweise Tags über `FabricTagProvider.FluidTagProvider` registrieren, anstatt sie manuell zu schreiben.

:::

Da eine Flüssigkeit im fließenden und im ruhenden Zustand als zwei getrennte Blöcke betrachtet wird, wird häufig ein Tag verwendet, um beide Zustände gemeinsam zu überprüfen. Wir erstellen ein Flüssigkeits-Tag in `data/example-mod/tags/fluid/acid.json`:

<<< @/reference/latest/src/main/generated/data/example-mod/tags/fluid/acid.json

::: tip

Minecraft bietet außerdem weitere Tags, mit denen sich das Verhalten von Flüssigkeiten steuern lässt:

- Wenn die Flüssigkeit deines Mods sich wie Wasser verhalten soll (Wassernebel, von Schwämmen aufgesaugt, schwimmbar, verlangsamt Entitäten ...), solltest du in Erwägung ziehen, sie dem Flüssigkeits-Tag `minecraft:water` hinzuzufügen.
- Wenn sie sich wie Lava verhalten soll (Lavanebel, für Striders/Ghasts schwimmbar, verlangsamt Entitäten…), solltest du es dem Flüssigkeits-Tag `minecraft:lava` hinzufügen.
- Wenn du nur _einige_ dieser Funktionen benötigst, kannst du Mixins verwenden, um das Verhalten genau anzupassen.

:::

Für diese Demo fügen wir außerdem das Tag der Säureflüssigkeit zum Tag Wasserflüssigkeit hinzu, `data/minecraft/tags/fluid/water.json`.

<<< @/reference/latest/src/main/generated/data/minecraft/tags/fluid/water.json

## Deine Flüssigkeiten texturieren {#textures}

Um deiner Flüssigkeit eine Textur zuzuweisen, solltest du die `FluidRenderHandlerRegistry` der Fabric-API verwenden.

::: tip

Der Einfachheit halber verwendet diese Demo `BlockTintSources.constant`, um der Standard-Wassertextur einen konstanten Grünstich zu anzuwenden. Weitere Informationen zu der `BlockTintSource` findest du unter [Blockfärbung](../blocks/block-tinting).

:::

Füge die folgenden Zeilen zu deinem `ClientModInitializer` hinzu, um ein `FluidModel.Unbaked` zu erstellen, das zwei `Material` für die Texturen – eines für die ruhende Quelle und eines für die fließende Flüssigkeit – sowie eine Blockfärbungs-Quelle für die Farbe, mit der es eingefärbt werden soll, verwendet.

@[code transcludeWith=:::fluid_texture](@/reference/latest/src/client/java/com/example/docs/appearance/ExampleModAppearanceClient.java)

Jetzt haben wir alles, was wir brauchen, um die Säure im Spiel zu sehen! Du kannst `setblock` oder das Item Säureeimer verwenden, um Säure in der Welt zu platzieren.

![Ein Screenshot einer grünen Säureflüssigkeit in der Spielwelt](/assets/develop/fluids/acid.png)
