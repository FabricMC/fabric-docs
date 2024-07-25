---
title: Benutzerdefinierte Rüstung
description: Lerne, wie du deine eigenen Rüstungssets erstellst.
authors:
  - IMB11
---

# Benutzerdefinierte Rüstung {#custom-armor}

Die Rüstung bietet dem Spieler eine bessere Verteidigung gegen Angriffe von Mobs und anderen Spielern.

## Eine Rüstungsmaterial Klasse erstellen {#creating-an-armor-materials-class}

Genau wie Items und Blöcke müssen auch Rüstungsmaterialien registriert werden. Wir werden eine Klasse `ModArmorMaterials` erstellen, um unsere benutzerdefinierten Rüstungsmaterialien zum Zweck der Organisation zu speichern.

Du musst eine statische Methode `initialize()` zu dieser Klasse hinzufügen und sie vom Einstiegspunkt deines Mods aus aufrufen, damit die Materialien registriert werden.

```java
// Within the ModArmorMaterials class
public static void initialize() {};
```

:::warning
Achte darauf, die Methode **vor** du das Item registrierst aufzurufen, da die Materialien registriert werden müssen, bevor das Item erstellt werden kann.
:::

```java
@Override
public void onInitialize() {
  ModArmorMaterials.initialize();
}
```

---

Innerhalb dieser `ModArmorMaterials` Klasse, musst du eine statische Methode erstellen, welche das Rüstungsmaterial registrieren wird. Diese Methode sollte einen Registrierungseintrag für das Material zurückgeben, da dieser Eintrag vom Rüstungsitem-Konstruktor zur Erstellung des Rüstungsitem verwendet wird.

@[code transcludeWith=:::1](@/reference/latest/src/main/java/com/example/docs/item/armor/ModArmorMaterials.java)

## Rüstungsmaterial Eigenschaften {#armor-material-properties}

:::tip
Wenn du Schwierigkeiten hast, einen guten Wert für eine dieser Eigenschaften zu finden, solltest du dir die Vanilla-Rüstungsmaterialien in der Klasse `ArmorMaterials` ansehen.
:::

Bei der Erstellung eines Rüstungsmaterials musst du die folgenden Eigenschaften festlegen:

### Verteidigungspunkte {#defense-points}

:::warning
Vergewissere dich, dass du jeder Art von Rüstungsteil, die du erstellst und als Item registrieren willst, einen Wert zuweisen. Wenn du ein Item für ein Rüstungsteil ohne einen festgelegten Verteidigungspunktwert machst, wird das Spiel abstürzen.
:::

Die Map `defensePoints` wird verwendet, um die Anzahl der Verteidigungspunkte zu definieren, die jedes Rüstungsteil zur Verfügung stellen wird. Je höher die Zahl, desto mehr Schutz bietet das Rüstungsteil. Die Map sollte einen Eintrag für jeden Rüstungsteil-Typ enthalten.

### Verzauberbarkeit {#enchantability}

Die Eigenschaft `enchantability` bestimmt, wie leicht die Rüstung verzaubert werden kann. Je höher die Zahl, desto mehr Verzauberungen kann die Rüstung erhalten.

### Ausrüstungssound {#equip-sound}

Die Eigenschaft `equipSound` ist der Sound, der gespielt wird, wenn die Rüstung ausgerüstet wird. Dieser Sound sollte ein Registry-Eintrag eines `SoundEvent` sein. Wirf einen Blick auf die Seite [Benutzerdefinierte Soundevents](../sounds/custom), wenn du in Erwägung ziehst, benutzerdefinierte Sounds zu erstellen, anstatt dich auf Vanilla-Sounds innerhalb der Klasse `SoundEvents` zu beruhen.

### Reparaturzutat(en) {#repair-ingredient}

Die Eigenschaft `repairIngredientSupplier` ist ein Lieferant einer `Ingredient`, die zur Reparatur der Rüstung verwendet wird. Diese Zutat kann so ziemlich alles sein, es wird empfohlen, sie so einzustellen, dass sie mit der Herstellungszutat des Materials übereinstimmt, mit der die Rüstungsgegenstände tatsächlich hergestellt werden.

### Härte {#toughness}

Die Eigenschaft `toughness` bestimmt, wie viel Schaden die Rüstung absorbiert. Je höher die Zahl, desto mehr Schaden kann die Rüstung absorbieren.

### Rückstoßwiderstand {#knockback-resistance}

Die Eigenschaft `knockbackResistance` legt fest, wie viel Rückstoß der Spieler reflektiert, wenn er getroffen wird. Je höher die Zahl, desto weniger Rückschlag erhält der Spieler.

### Färbbar {#dyeable}

Die Eigenschaft `dyeable` ist ein boolescher Wert, der angibt, ob die Rüstung gefärbt werden kann. Wenn diese Option auf `true` gesetzt ist, kann die Rüstung mit Hilfe von Farbstoffen in einer Werkbank gefärbt werden.

Wenn du dich dafür entscheidest, deine Rüstung färbbar zu machen, muss die Texturen deiner Rüstungsebene und deines Items **für das Färben ausgelegt** sein, da die Farbe die Textur überlagert und nicht ersetzt. Schau dir zum Beispiel die Vanille-Lederrüstung an. Die Texturen sind in Graustufen gehalten und die Farbe wird als Overlay aufgetragen, wodurch die Rüstung ihre Farbe ändert.

## Das Rüstungsmaterial registrieren {#registering-the-armor-material}

Nachdem du nun eine Utility-Methode erstellt hast, die zur Registrierung von Rüstungsmaterialien verwendet werden kann, kannst du deine benutzerdefinierten Rüstungsmaterialien als statisches Feld in der Klasse `ModArmorMaterials` registrieren.

In diesem Beispiel werden wir eine kreative Guidite-Rüstung mit den folgenden Eigenschaften verwenden:

@[code transcludeWith=:::2](@/reference/latest/src/main/java/com/example/docs/item/armor/ModArmorMaterials.java)

## Rüstungsitems erstellen {#creating-the-armor-items}

Nachdem du das Material registriert hast, kannst du die Rüstungsitems in deiner Klasse `ModItems` erstellen:

Natürlich muss ein Rüstungsset nicht jeden Typ abdecken, man kann auch ein Set mit nur Stiefeln oder Hosen etc. haben. - Der Vanille-Schildkrötenpanzerhelm ist ein gutes Beispiel für ein Rüstungsset mit fehlenden Slots.

### Haltbarkeit {#durability}

Im Gegensatz zu `ToolMaterial` speichert `ArmorMaterial` keine Informationen über die Haltbarkeit von Items.
Aus diesem Grund muss die Haltbarkeit manuell zu den `Item.Settings` der Rüstungsitems hinzugefügt werden, wenn diese registriert werden.

Dies kann durch die Methode `maxDamage` in der Klasse `Item.Settings` bewirkt werden.
Die verschiedenen Rüstungsslots haben unterschiedliche Grundhaltbarkeiten, die üblicherweise mit einem gemeinsamen Multiplikator des Rüstungsmaterials multipliziert werden, es können aber auch fest kodierte Werte verwendet werden.

Für die Guidite-Rüstung werden wir einen gemeinsamen Multiplikator für die Rüstung verwenden, der zusammen mit dem Rüstungsmaterial gespeichert wird:

@[code transcludeWith=:::3](@/reference/latest/src/main/java/com/example/docs/item/armor/ModArmorMaterials.java)

Anschließend können wir die Rüstungsitems unter Verwendung der Haltbarkeitskonstante erstellen:

@[code transcludeWith=:::6](@/reference/latest/src/main/java/com/example/docs/item/ModItems.java)

Außerdem musst du die Items **einer Itemgruppe hinzufügen**, wenn du möchtest, dass sie über das kreative Inventar zugänglich sind.

Wie bei allen Items solltest du auch für diese Übersetzungsschlüssel erstellen.

## Texturierung und Modellierung {#texturing-and-modelling}

Du wirst zwei Sets von Texturen erstellen müssen:

- Texturen und Modelle für die Items selbst.
- Die eigentliche Rüstungstextur, die sichtbar ist, wenn eine Entität die Rüstung trägt.

### Itemtexturen und Modell {#item-textures-and-model}

Diese Texturen unterscheiden sich nicht von anderen Items - Du musst die Texturen erstellen und ein generisches Itemmodell erstellen, was in der Anleitung [Erstellen des ersten Items](./first-item#adding-a-texture-and-model) behandelt wurde.

Als Beispiel dient das folgende Textur- und Modell-JSON als Referenz.

<DownloadEntry type="Item Textures" visualURL="/assets/develop/items/armor_0.png" downloadURL="/assets/develop/items/example_armor_item_textures.zip" />

:::info
Du benötigst JSON-Modelldateien für alle Gegenstände, nicht nur für den Helm. Es ist das gleiche Prinzip wie bei anderen Itemmodellen.
:::

@[code](@/reference/latest/src/main/resources/assets/fabric-docs-reference/models/item/guidite_helmet.json)

Wie du sehen kannst, sollten die Rüstungsitems im Spiel geeignete Modelle haben:

![Rüstungsitem Modelle](/assets/develop/items/armor_1.png)

## Rüstungstexturen und Modelle {#armor-textures-and-model}

Wenn eine Entität deine Rüstung trägt, wird die fehlende Textur angezeigt:

![Kaputtes Rüstungsmodell an einem Spieler](/assets/develop/items/armor_2.png).

Es gibt zwei Schichten für die Rüstungstextur, beide müssen vorhanden sein.

Da der Name des Rüstungsmaterial in unserem Fall `guidite` lautet, werden die Texturen wie folgt angeordnet:

- `assets/<mod-id>/textures/models/armor/guidite_layer_1.png`
- `assets/<mod-id>/textures/models/armor/guidite_layer_2.png`

<DownloadEntry type="Armor Model Textures" noVisualURL="true" downloadURL="/assets/develop/items/example_armor_layer_textures.zip" />

Die erste Schicht enthält Texturen für den Helm und den Brustpanzer, während die zweite Schicht Texturen für Hosen und Stiefel enthält.

Wenn diese Texturen vorhanden sind, solltest du deine Rüstung auf Entitäten sehen können, die sie tragen:

![Funktionierendes Rüstungsmodell an einem Spieler](/assets/develop/items/armor_3.png).
