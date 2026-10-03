---
title: Benutzerdefinierte Schilde
description: Lerne, wie du deine eigenen Schilde erstellst und deren Eigenschaften konfigurierst.
authors:
  - cassiancc
  - ChampionAsh5357
resources:
  https://mcsrc.dev/1/26.2/net/minecraft/client/renderer/special/ShieldSpecialRenderer: Vanilla Schild Renderer
  https://minecraft.wiki/w/Data_component_format/blocks_attacks: Blockiert Angriffe Datenkomponente
---

<!--  -->

:::info VORAUSSETZUNGEN

Du musst zuerst versehen, wie man [ein Werkzerug erstellt](./custom-tools). Dieser Leitfaden referenziert auch die Datengenerierung von [Rezepten](../data-generation/recipes), [Itemmodellen](../data-generation/item-models) und [Item Tags](../data-generation/tags).

:::

Schilde können dazu verwendet werden, sich vor Angriffen zu schützen. Um dem Spiel einen neuen Schild hinzuzufügen, benötigst du ein `Item`, zwei Itemmodelle, ein Client Item, Rezepte, Item Tags und einen speziellen Renderer.

## Das Item erstellen {#item}

:::info VORAUSSETZUNGEN

Weitere Informationen findest du in der Dokumentation zur [Erstellung von Items](./first-item).

:::

Für dieses Beispiel verwenden wir dasselbe Reperatur Item Tag, das wir bereits auf den Seiten [Benutzerdefinierte Rüstungen](./custom-armor) und [Benutzerdefinierte Werkzeuge](./custom-tools) verwendet haben. Wir definieren die Tag-Referenz wie folgt:

<<< @/reference/latest/src/main/java/com/example/docs/item/armor/GuiditeArmorMaterial.java#repair_tag

Dann, erstellen wir eine Item ID und registrieren ein Item mit den folgenden Komponenten.

- [**Bannervorlagen**](https://minecraft.wiki/w/Data_component_format/banner_patterns): Erstellt ein Item mit einem leeren Set an Bannervorlagen.
- [**Reparierbar**](https://minecraft.wiki/w/Data_component_format/repairable): Erstellt ein Item, dass mit einem gegebenen Item Tag repariert werden kann.
- [**Ausrüstbar/Nicht austauschbar**](https://minecraft.wiki/w/Data_component_format/equippable): Ein Shift-Klick auf das Item im GUI, wird es in der zweiten Hand ausrüsten. In der Spielwelt wird das Item durch einen Rechtsklick nicht ausgerüstet.
- [**Blockiert Angriffe**](https://minecraft.wiki/w/Data_component_format/blocks_attacks): Erstellt ein Item, dass Angriffs blockiert. Dieses Beispiel verwendet Werte von dem Vanilla Schild.
  - Dies ist eine _verzögerte Komponente_, das heißt, sie wird erst geladen, nachdem die Welt geladen wurde, sodass sie auf Datapack-Objekte wie Tags verweisen kann.
- [**Zerstörungssound**](https://minecraft.wiki/w/Data_component_format/break_sound): Wenn das Item zerstört wird, wird der angegebene Ton abgespielt.

<<< @/reference/latest/src/main/java/com/example/docs/item/ModItemIds.java#shield

<<< @/reference/latest/src/main/java/com/example/docs/item/ModItems.java#shield

Vergiss nicht, ihn zu einem Kreativtab hinzuzufügen, wenn du vom Kreativinventar aus auf ihn zugreifen willst!

<<< @/reference/latest/src/main/java/com/example/docs/item/ModItems.java#add_guidite_shield_to_create_tab

## Den speziellen Renderer erstellen {#special-renderer}

Wir werden einen speziellen Renderer verwenden, um den Schild zu rendern, anstatt das normale Itemmodell zu verwenden.

Zuerst erstellen wir einen Ort für die Modell-Ebenen, der anzeigt, wo das Schildmodell ist:

<<< @/reference/latest/src/client/java/com/example/docs/item/shield/GuiditeShieldLayers.java#layer

Registriere dann die Ebene in deinem Client Initialisierer:

<<< @/reference/latest/src/client/java/com/example/docs/ExampleModClient.java#shield_layer

Dann werden wir einen speziellen Renderer für das Item erstellen. Dieser basiert auf dem Vanilla [`ShieldSpecialRenderer`](https://mcsrc.dev/1/26.2/net/minecraft/client/renderer/special/ShieldSpecialRenderer), wobei Änderungen vorgenommen wurden, damit er benutzerdefinierte Sprites vom Client Item übernehmen kann. Im nächsten Abschnitt werden wir diese Sprites dem Renderer zur Verfügung stellen.

Der Renderer ist kompliziert, daher werden wir ihn Schritt für Schritt erklären.

### Konstruktor {#constructor}

Der Konstruktor des Renderer akzeptiert vier Parameter:

- Ein Interface `SpriteGetter`, das Sprites anhand von `Identifier` bereitstellen kann.
- Das Modell, das wir verwenden werden, ist in diesem Fall ein `ShieldModel`.
- Die weiße Grundtextur (im Client Item enthalten), die als `SpriteId` bereitgestellt wird.
- Die Textur, die verwendet wird, wenn keine Färbung oder Bannermuster vorhanden sind, wird als `SpriteId` bereitgestellt.

Der Konstruktor speichert alle vier Parameter als Felder, damit wir sie später verwenden können.

<<< @/reference/latest/src/client/java/com/example/docs/item/shield/GuiditeShieldSpecialRenderer.java#renderer

### Extraktion {#extraction}

Beim Extrahieren der zu rendernenden Daten benötigen wir eine unveränderliche Kopie der Daten, die ausschließlich die für die Darstellung des Items erforderlichen Informationen enthält. Wir können dies aus dem `ItemStack` abrufen, indem wir dessen `DataComponentMap` in `extractArgument` in eine unveränderliche Version umwandeln:

<<< @/reference/latest/src/client/java/com/example/docs/item/shield/GuiditeShieldSpecialRenderer.java#extract_argument

### Ausmaß {#extents}

Außerdem werden wir die Ausmaße des Modells, die dessen Begrenzungsrahmen definieren, die für das Rendering und Animationen im Modell verwendet werden:

<<< @/reference/latest/src/client/java/com/example/docs/item/shield/GuiditeShieldSpecialRenderer.java#extents

### Übermittlung {#submission}

Der Übermittlungsprozess behandelt die Logik, _was_ gerendert werden soll. Die Logik des Schild Render macht folgendes:

1. Rufe die Bannervorlagen des Schildes ab und speichere sie in `patterns`. Wenn der Schild keine Bannervorlagen hat, wird es auf `BannerPatternLayers.EMPTY` gesetzt.
2. Rufe die Färbung des Schildes ab und speichere sie in `baseColor`. Wenn Der Schild keine Färbung hat, wird diese Variable auf `null` gesetzt.
3. Wenn das Schild Bannervorlagen or eine Färbung hat, verwende die Textur `base`. Wenn nicht, verwende die Textur `base_nopattern`.
4. Übermittle das zu rendernde Schildmodell unter Verwendung der angegebenen Parameter und der Textur.
5. Wenn der Schild Bannervorlagen hat, übermittle auch diese.
6. Wenn der Schild verzaubert ist, übermittle den Verzauberungsglanz.

<<< @/reference/latest/src/client/java/com/example/docs/item/shield/GuiditeShieldSpecialRenderer.java#submit

### Unverarbeitetes Modell {#unbaked-models}

Außerdem benötigst du ein [unverarbeitetes Modell](https://docs.neoforged.net/docs/resources/client/models/modelsystem), das dazu dient, auf den Modell-Renderer zu verweisen und dem Modell die Sprites bereitzustellen.

<<< @/reference/latest/src/client/java/com/example/docs/item/shield/GuiditeShieldSpecialRenderer.java#unbaked

## Das Modell erstellen {#model}

:::info VORAUSSETZUNGEN

Weitere Informationen findest du in der Dokumentation zur Erstellung von [Itemmodellen](../data-generation/item-models).

:::

Wir werden zwei Itemmodelle erstellen - eines für den Normalzustand und eines für den Fall, dass der Schild blockt - sowie ein bedingtes Client Item für den Schild mit unseren benutzerdefinierten Texturen:

:::: tabs

== Quellcode

::: info

Diese Modelle sind datengeneriert. Weitere Informationen findest du in der Dokumentation zur Erstellung von [Itemmodellen](../data-generation/item-models).

:::

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModModelProvider.java#shield

== Client Item

`generated/assets/example-mod/items/guidite_shield.json`

<<< @/reference/latest/src/main/generated/assets/example-mod/items/guidite_shield.json

== Itemmodelle

`generated/assets/example-mod/models/item/guidite_shield.json`

<<< @/reference/latest/src/main/generated/assets/example-mod/models/item/guidite_shield.json

`generated/assets/example-mod/models/item/guidite_shield_blocking.json`

<<< @/reference/latest/src/main/generated/assets/example-mod/models/item/guidite_shield_blocking.json

== Texturen

<DownloadEntry visualURL="/assets/develop/items/guidite_shield_base_hd.png" downloadURL="/assets/develop/items/guidite_shield_base.png">Guidite Schild Basistextur</DownloadEntry>

<DownloadEntry visualURL="/assets/develop/items/guidite_shield_base_nopattern_hd.png" downloadURL="/assets/develop/items/guidite_shield_base_nopattern.png">Guidite Schild Basis (keine Bannervorlagen) Textur</DownloadEntry>

::::

## Das Rezept für das dekorierte Schild erstellen {#recipe}

:::info VORAUSSETZUNGEN

Weitere Informationen findest du in der Dokumentation zur Generierung von [Rezepten](../data-generation/recipes).

:::

Es gibt zwei Möglichkeiten, im Überlebensmodus an unseren Schild zu kommen: Entweder man stellt einen normalen Schild her oder man verziert einen mit Bannermustern.

Das Handwerks-Rezept für den Basisschild kann beliebig sein. Andererseits lässt sich das Rezept für den dekorierten Schild im Rezept-Provider wie folgt erstellen:

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModRecipeProvider.java#shield_decoration

Nachdem dieses Rezept definiert ist, kannst du nun Bannervorlagen auf deinem Schild anbringen:

![Bannervorlagen im Werkbank-Raster anwenden](/assets/develop/items/shield_banner_example.png)

## Schild Items taggen {#tags}

:::info VORAUSSETZUNGEN

Weitere Informationen findest du in der Dokumentation zur Generierung von [Item Tags](../data-generation/tags).

:::

Außerdem solltest du dein Schild in eine passende Item Tags einordnen:

- `ItemTags.DURABILITY_ENCHANTABLE`, damit es mit den Verzauberungen Reparatur und Haltbarkeit verzaubert werden kann,
- `ConventionalItemTags.SHIELD_TOOLS`, das von Moddern für schildspezifisches Verhalten genutzt werden kann, wie beispielsweise benutzerdefinierte Schildverzauberungen.

Füge in deinem Item Tag Provider die folgenden Zeilen zu `addTags` hinzu:

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModItemTagProvider.java#shield_tags

Das war's dann auch schon! Wenn du das Spiel startest, solltest du deinen Schild im Reiter "Combat" (Kampf) des Kreativ-Inventarmenüs sehen.

![Fertiges Schild im Spiel](/assets/develop/items/shield_use.png)
