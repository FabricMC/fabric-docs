---
title: Dein erstes Item erstellen
description: Lerne, wie man ein einfaches Item registriert und wie man es texturiert, modelliert und benennt.
authors:
  - dicedpixels
  - Earthcomputer
  - IMB11
  - RaphProductions
---

Diese Seite wird dich in einige Schlüssel-Konzepte von Items einführen und wie du sie registrierst, eine Textur, ein Model und einen Namen gibst.

Falls du es nicht weißt, alles in Minecraft wird in Registern gespeichert, genauso wie Items.

## Deine Item-Klasse vorbereiten {#preparing-your-items-class}

Um die Registrierung von Items zu vereinfachen, kannst du eine Methode erstellen, die einen String Identifikator, einige Eigenschaften des Items und eine Factory zur Erstellung der `Item`-Instanz akzeptiert.

Diese Methode erstellt einen Item mit dem angegebenen Bezeichner und registriert ihn in der Item Registry des Spiels.

Du kannst diese Methode in eine Klasse namens `ModItems` (oder wie immer du die Klasse nennen willst) einfügen.

Mojang macht das auch mit ihren Items! Inspiriere dich von der Klasse `Items`.

@[code transcludeWith=:::1](@/reference/latest/src/main/java/com/example/docs/item/ModItems.java)

Beachte, wie wir ein `GenericItem` verwenden, wodurch wir dieselbe Methode `register` zum Registrieren aller Arten von Items verwenden können, die von `Item` erben. Beachte die Verwendung eines [`Function`](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/util/function/Function.html) Interface für die Factory, das es uns später ermöglichen wird, zu spezifizieren, wie wir unser Item aus den Eigenschaften des Items mit `Item::new` erstellen wollen.

## Ein Item registrieren {#registering-an-item}

Mit der Methode kannst du nun ein Item registrieren.

Die Methode register nimmt eine Instanz von der Klasse `Item.InteractionResult` als Parameter entgegen. Mit dieser Klasse kannst du die Eigenschaften des Items mit Hilfe verschiedener Erstellungsmethoden konfigurieren.

::: tip

Wenn du die Stapelgröße deines Items ändern möchtest, kannst du die Methode `stacksTo` in der Klasse `Item.Properties` verwenden.

Dies funktioniert nicht, wenn du das Item als beschädigungsfähig markiert hast, da die Stackgröße für beschädigungsfähige Gegenstände immer 1 ist, um Duplikations-Exploits zu verhindern.

:::

@[code transcludeWith=:::2](@/reference/latest/src/main/java/com/example/docs/item/ModItems.java)

`Item::new` weist die Funktion register an, eine Instanz von Item aus einer Instanz von `Item.Properties` zu erzeugen, indem sie den Item-Konstruktor (`new Item(...)`) aufruft, der eine Instanz von `Item.Properties` als Parameter entgegennimmt.

Wenn du nun jedoch versuchst, den modifizierten Client auszuführen, kannst du sehen, dass unser Item im Spiel noch nicht existiert! Der Grund dafür ist, dass du die Klasse nicht statisch initialisiert hast.

Um dies zu tun, kannst du eine öffentliche, statische Methode zur initialisierung deiner Klasse hinzufügen und diese in deiner [Mod-Initialisierer](./getting-started/project-structure#entrypoints) Klasse aufrufen. Derzeit benötigt diese Methode keine Inhalte.

@[code transcludeWith=:::3](@/reference/latest/src/main/java/com/example/docs/item/ModItems.java)

@[code transcludeWith=:::1](@/reference/latest/src/main/java/com/example/docs/item/ExampleModItems.java)

Der Aufruf einer Methode einer Klasse initialisiert diese statisch, wenn sie nicht vorher geladen wurde - das bedeutet, dass alle `static` Felder ausgewertet werden. Dafür ist diese Dummy-Methode `initialize` gedacht.

## Ein Item zu einem Kreativtab hinzufügen {#adding-the-item-to-a-creative-tab}

::: info

Wenn du ein Item zu einem benutzerdefinierten `CreativeModeTab` hinzufügen möchtest, findest du weitere Informationen auf der Seite [Benutzerdefinierte Kreativtabs](./custom-item-groups).

:::

Für ein Beispiel, in dem wir dieses Item zu dem Zutaten `CreativeModeTab` hinzufügen, musst du die Kreativtab-Events der Fabric API verwenden - insbesondere `ItemGroupEvents.modifyEntriesEvent`

Dies kann in der Methode `initialize` deiner Itemklasse geschehen.

@[code transcludeWith=:::4](@/reference/latest/src/main/java/com/example/docs/item/ModItems.java)

Wenn du das Spiel lädst, kannst du sehen, dass unser Item registriert wurde und sich in der Gruppe der Zutaten im Kreativtab befindet:

![Item in der Zutatengruppe](/assets/develop/items/first_item_0.png)

Es fehlen jedoch folgende Punkte:

- Itemmodell
- Textur
- Übersetzung (Name)

## Das Item benennen {#naming-the-item}

Für das Item gibt es derzeit keine Übersetzung, du musst also eine hinzufügen. Der Übersetzungsschlüssel wurde bereits von Minecraft bereitgestellt: `item.example-mod.suspicious_substance`.

Erstelle eine neue JSON-Datei unter dem Pfad `src/main/resources/assets/example-mod/lang/en_us.json` und setze den Übersetzungsschlüssel und seinen Wert:

```json
{
  "item.example-mod.suspicious_substance": "Suspicious Substance"
}
```

Du kannst entweder das Spiel neu starten oder deinen Mod bauen und <kbd>F3</kbd>+<kbd>T</kbd> drücken, um die Änderungen zu übernehmen.

## Ein Client Item, eine Textur und ein Modell hinzufügen {#adding-a-client-item-texture-and-model}

Damit dein Item ein ansprechendes Erscheinungsbild hat, muss Folgendes gegeben sein:

- [Eine Item-Textur](https://minecraft.wiki/w/Textures#Items)
- [Ein Item-Modell](https://minecraft.wiki/w/Model#Item_models)
- [Ein Client Item](https://minecraft.wiki/w/Items_model_definition)

### Eine Textur hinzufügen {#adding-a-texture}

::: info

Für weitere Informationen zu diesem Thema, sieh dir die [Item Modelle](./item-models) Seite an.

:::

Um deinem Item eine Textur und ein Modell zu geben, erstelle einfach ein 16x16 Texturbild für dein Item und speichere es im Ordner `assets/example-mod/textures/item`. Benenne die Texturdatei genauso wie den Bezeichner des Items, aber mit der Erweiterung `.png`.

Als Beispiel kannst du diese Textur für `suspicious_substance.png` verwenden

<DownloadEntry visualURL="/assets/develop/items/first_item_1.png" downloadURL="/assets/develop/items/first_item_1_small.png">Textur</DownloadEntry>

### Ein Modell hinzufügen {#adding-a-model}

Wenn du das Spiel neu startest/ladest, solltest du sehen, dass das Item immer noch keine Textur hat, weil du ein Modell hinzufügen musst, das diese Textur verwendet.

Wenn du das Spiel neu startest/ladest, solltest du sehen, dass das Item immer noch keine Textur hat, weil du ein Modell hinzufügen musst, das diese Textur verwendet.

Erzeuge das Modell JSON im Ordner `assets/example-mod/models/item`, mit dem gleichen Namen wie das Element; `suspicious_substance.json`

@[code](@/reference/latest/src/main/generated/assets/example-mod/models/item/suspicious_substance.json)

#### Das Modell JSON niederbrechen {#breaking-down-the-model-json}

- `parent`: Dies ist das Elternmodell von dem dieses Modell erben wird. In diesem Fall ist es das Modell `item/generated`.
- `textures`: Dies ist der Ort, wo du die Textur für das modell definierst. Der `layer0` Schlüssel ist die Textur, die das Modell nutzen wird.

Die meisten Items werden das Modell `item/generated` als übergeordnetes Modell verwenden, da es ein einfaches Modell ist, das nur die Textur anzeigt.

Es gibt Alternativen, z. B. `item/handheld`, das für Items verwendet wird, die der Spieler in der Hand hält, wie z. B. Werkzeuge.

### Ein Client Item erstellen {#creating-the-client-item}

Minecraft weiß nicht automatisch, wo die Dateien deiner Item-Modelle zu finden sind, deswegen müssen wir ein Client Item zur Verfügung stellen.

Erstelle das JSON für das Client Item im Verzeichnis `assets/example-mod/items`, mit demselben Dateinamen wie der Bezeichner des Items: `suspicious_substance.json`.

@[code](@/reference/latest/src/main/generated/assets/example-mod/items/suspicious_substance.json)

#### Das Client Item JSON aufschlüsseln {#breaking-down-the-client-item-json}

- `model`: Das ist die Eigenschaft, die die Referenz zu unserem Modell beinhaltet.
  - `type`: Dies ist der Typ unseres Modell. Für die meisten Items sollte dies `minecraft:model` sein
  - `model`: Dies ist die Bezeichnung des Modells. Es sollte diese Form haben: `example-mod:item/item_name`

Dein Item sollte nun im Spiel wie folgt aussehen:

![Item mit dem korrektem Modell](/assets/develop/items/first_item_2.png)

## Das Item kompostierbar oder zu einem Brennstoff machen {#making-the-item-compostable-or-a-fuel}

Die Fabric API bietet verschiedene Register, die verwendet werden können, um zusätzliche Eigenschaften zu deinen Items hinzuzufügen.

Wenn du zum Beispiel dein Item kompostierbar machen willst, kannst du die `CompostingChanceRegistry` verwenden:

@[code transcludeWith=:::\_10](@/reference/latest/src/main/java/com/example/docs/item/ModItems.java)

Alternativ, wenn du dein Item zu einem Brennstoff machen willst, kannst du das Event `FuelRegistryEvents.BUILD` verwenden:

@[code transcludeWith=:::\_11](@/reference/latest/src/main/java/com/example/docs/item/ModItems.java)

## Hinzufügen eines einfachen Craftingrezepts {#adding-a-basic-crafting-recipe}

<!-- In the future, an entire section on recipes and recipe types should be created. For now, this suffices. -->

Wenn du ein Crafting-Rezept für deine Items hinzufügen möchtest, musst du eine Rezept-JSON-Datei in den Ordner `data/example-mod/recipe` legen.

Weitere Informationen über das Rezeptformat findest du in diesen Ressourcen:

- [Rezept JSON Generator](https://crafting.thedestruc7i0n.ca/)
- [Minecraft Wiki - Rezept JSON](https://minecraft.wiki/w/Recipe#JSON_Format)

## Benutzerdefinierte Tooltips {#custom-tooltips}

Wenn du möchtest, dass dein Item einen benutzerdefinierten Tooltip hat, musst du eine Klasse erstellen, die `Item` erbt und die Methode `appendHoverText` überschreibt.

::: info

In diesem Beispiel wird die Klasse `LightningStick` verwendet, die auf der Seite [Benutzerdefinierte Iteminteraktionen](./custom-item-interactions) erstellt wurde.

:::

@[code lang=java transcludeWith=:::3](@/reference/latest/src/main/java/com/example/docs/item/custom/LightningStick.java)

Jeder Aufruf von `accept()` fügt dem Tooltip eine Zeile hinzu.

![Tooltip Beispiel](/assets/develop/items/first_item_3.png)
