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

Um die Registrierung von Items zu vereinfachen, kannst du eine Methode erstellen, die einen String Identifikator, einige Itemeinstellungen und eine Factory zur Erstellung der `Item`-Instanz akzeptiert.

Diese Methode erstellt einen Item mit dem angegebenen Bezeichner und registriert ihn in der Item Registry des Spiels.

Du kannst diese Methode in eine Klasse namens `ModItems` (oder wie immer du die Klasse nennen willst) einfügen.

Mojang macht das auch mit ihren Items! Inspiriere dich von der Klasse `Items`.

@[code transcludeWith=:::1](@/reference/latest/src/main/java/com/example/docs/item/ModItems.java)

Beachte die Verwendung einer [`Function`](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/util/function/Function.html) Schnittstelle für die Factory, die es uns später ermöglichen wird, zu spezifizieren, wie wir unser Item aus den Itemeinstellungen mit `Item::new` erstellen wollen.

## Ein Item registrieren {#registering-an-item}

Mit der Methode kannst du nun ein Item registrieren.

Die register-Methode nimmt eine Instanz von der `Item.Settings` Klasse als Parameter entgegen. Mit dieser Klasse kannst du die Eigenschaften des Items mit Hilfe verschiedener Erstellungsmethoden konfigurieren.

::: tip
If you want to change your item's stack size, you can use the `maxCount` method in the `Item.Settings` class.

Dies funktioniert nicht, wenn du das Item als beschädigungsfähig markiert hast, da die Stackgröße für beschädigungsfähige Gegenstände immer 1 ist, um Duplikations-Exploits zu verhindern.
:::

@[code transcludeWith=:::2](@/reference/latest/src/main/java/com/example/docs/item/ModItems.java)

`Item::new` weist die register-Funktion an, eine Item-Instanz aus einer `Item.Settings`-Instanz zu erzeugen, indem sie den Item-Konstruktor (`new Item(...)`) aufruft, der eine `Item.Settings`-Instanz als Parameter entgegennimmt.

Wenn du nun jedoch versuchst, den modifizierten Client auszuführen, kannst du sehen, dass unser Item im Spiel noch nicht existiert! Der Grund dafür ist, dass du die Klasse nicht statisch initialisiert hast.

Um dies zu tun, kannst du eine öffentliche, statische Methode zur initialisierung deiner Klasse hinzufügen und diese in deiner [Mod-Initialisierer](./getting-started/project-structure#entrypoints) Klasse aufrufen. Derzeit braucht diese Methode nichts zu enthalten.

@[code transcludeWith=:::3](@/reference/latest/src/main/java/com/example/docs/item/ModItems.java)

@[code transcludeWith=:::1](@/reference/latest/src/main/java/com/example/docs/item/FabricDocsReferenceItems.java)

Der Aufruf einer Methode einer Klasse initialisiert diese statisch, wenn sie nicht vorher geladen wurde - das bedeutet, dass alle `static` Felder ausgewertet werden. Dafür ist diese Dummy-Methode `initialize` gedacht.

## Ein Item zu einer Itemgruppe hinzufügen {#adding-the-item-to-an-item-group}

:::info
Wenn du den Artikel einer benutzerdefinierten `ItemGroup` hinzufügen möchtest, findest du weitere Informationen auf der Seite [Benutzerdefinierte Itemgruppe](./custom-item-groups).
:::

Für ein Beispiel, in dem wir dieses Element zu den Zutaten `ItemGroup` hinzufügen, musst du die Itemgruppen-Events der Fabric API verwenden - insbesondere `ItemGroupEvents.modifyEntriesEvent`.

Dies kann in der Methode `initialize` deiner Itemklasse geschehen.

@[code transcludeWith=:::4](@/reference/latest/src/main/java/com/example/docs/item/ModItems.java)

Wenn du das Spiel lädst, kannst du sehen, dass unser Item registriert wurde und sich in der Gruppe der Zutaten befindet:

![Item in der Zutatengruppe](/assets/develop/items/first_item_0.png)

Es fehlen jedoch folgende Punkte:

- Itemmodell
- Textur
- Übersetzung (Name)

## Das Item benennen {#naming-the-item}

Für das Item gibt es derzeit keine Übersetzung, du musst also eine hinzufügen. Der Übersetzungsschlüssel wurde bereits von Minecraft bereitgestellt: `item.mod_id.suspicious_substance`.

Erstelle eine neue JSON-Datei unter dem Pfad `src/main/resources/assets/mod-id/lang/en_us.json` und setze den Übersetzungsschlüssel und seinen Wert:

```json
{
  "item.mod_id.suspicious_substance": "Suspicious Substance"
}
```

Du kannst entweder das Spiel neu starten oder deinen Mod bauen und <kbd>F3</kbd>+<kbd>T</kbd> drücken, um die Änderungen zu übernehmen.

## Eine Textur und ein Modell hinzufügen {#adding-a-texture-and-model}

Um deinem Item eine Textur und ein Modell zu geben, erstelle einfach ein 16x16 Texturbild für dein Item und speichere es im Ordner `assets/mod-id/textures/item`. Benenne die Texturdatei genauso wie den Bezeichner des Items, aber mit der Erweiterung `.png`.

Als Beispiel kannst du diese Textur für `suspicious_substance.png` verwenden.

<DownloadEntry visualURL="/assets/develop/items/first_item_1.png" downloadURL="/assets/develop/items/first_item_1_small.png">Textur</DownloadEntry>

Wenn du das Spiel neu startest/ladest, solltest du sehen, dass das Item immer noch keine Textur hat, weil du ein Modell hinzufügen musst, das diese Textur verwendet.

Du wirst ein einfaches `item/generated`-Modell erstellen, das eine Eingabetextur und sonst nichts enthält.

Erzeuge das Modell JSON im Ordner `assets/mod-id/models/item`, mit dem gleichen Namen wie das Element; `suspicious_substance.json`

@[code](@/reference/latest/src/main/generated/assets/fabric-docs-reference/models/item/suspicious_substance.json)

### Das Modell JSON niederbrechen {#breaking-down-the-model-json}

- `parent`: Dies ist das Elternmodell von dem dieses Modell erben wird. In diesem Fall ist es das Modell `item/generated`.
- `textures`: Dies ist der Ort, wo du die Textur für das modell definierst. Der `layer0` Schlüssel ist die Textur, die das Modell nutzen wird.

Die meisten Items werden das Modell `item/generated` als übergeordnetes Modell verwenden, da es ein einfaches Modell ist, das nur die Textur anzeigt.

Es gibt Alternativen, z. B. `item/handheld`, das für Items verwendet wird, die der Spieler in der Hand hält, wie z. B. Werkzeuge.

## Erstellung der Itemmodell-Beschreibung {#creating-the-item-model-description}

Minecraft weiß nicht, wo deine Item-Model Dateien zu finden sind, deswegen müssen wir eine Item-Model-Beschreibung zur Verfügung stellen.

Erstelle die JSON-Beschreibung des Items im Verzeichnis `assets/mod-id/items`, mit demselben Dateinamen wie der Bezeichner des Items: `suspicious_substance.json`.

@[code](@/reference/latest/src/main/generated/assets/fabric-docs-reference/items/suspicious_substance.json)

### Aufschlüsselung des JSON der Itemmodell-Beschreibung {#breaking-down-the-item-model-description-json}

- `model`: Das ist die Eigenschaft, die die Referenz zu unserem Modell beinhaltet.
  - `type`: Dies ist der Typ unseres Modell. Für die meisten Items sollte dies `minecraft:model` sein.
  - `model`: Dies ist die Bezeichnung des Modells. Es sollte diese Form haben: `mod-id:item/item_name`

Dein Item sollte nun im Spiel wie folgt aussehen:

![Item mit dem korrektem Modell](/assets/develop/items/first_item_2.png)

## Das Item kompostierbar oder zu einem Brennstoff machen {#making-the-item-compostable-or-a-fuel}

Die Fabric API bietet verschiedene Register, die verwendet werden können, um zusätzliche Eigenschaften zu deinen Items hinzuzufügen.

Wenn du zum Beispiel dein Item kompostierbar machen willst, kannst du die `CompostableItemRegistry` verwenden:

@[code transcludeWith=:::_10](@/reference/latest/src/main/java/com/example/docs/item/ModItems.java)

Alternativ, wenn du dein Item zu einem Brennstoff machen willst, kannst du das Event `FuelRegistryEvents.BUILD` verwenden:

@[code transcludeWith=:::_11](@/reference/latest/src/main/java/com/example/docs/item/ModItems.java)

## Hinzufügen eines einfachen Craftingrezepts {#adding-a-basic-crafting-recipe}

<!-- In the future, an entire section on recipes and recipe types should be created. For now, this suffices. -->

Wenn du ein Crafting-Rezept für deine Items hinzufügen möchtest, musst du eine Rezept-JSON-Datei in den Ordner `data/mod-id/recipe` legen.

Weitere Informationen über das Rezeptformat findest du in diesen Ressourcen:

- [Rezept JSON Generator](https://crafting.thedestruc7i0n.ca/)
- [Minecraft Wiki - Rezept JSON](https://minecraft.wiki/w/Recipe#JSON_Format)

## Benutzerdefinierte Tooltips {#custom-tooltips}

Wenn du möchtest, dass dein Item einen benutzerdefinierten Tooltip hat, musst du eine Klasse erstellen, die `Item` erbt und die Methode `appendTooltip` überschreibt.

:::info
In diesem Beispiel wird die Klasse `LightningStick` verwendet, die auf der Seite [Benutzerdefinierte Iteminteraktionen](./custom-item-interactions) erstellt wurde.
:::

@[code lang=java transcludeWith=:::3](@/reference/latest/src/main/java/com/example/docs/item/custom/LightningStick.java)

Jeder Aufruf von `add()` fügt dem Tooltip eine Zeile hinzu.

![Tooltip Beispiel](/assets/develop/items/first_item_3.png)
