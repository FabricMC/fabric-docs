---
title: Benutzerdefinierte Rüstung
description: Lerne, wie du deine eigenen Rüstungssets erstellst.
authors:
  - IMB11
---

Die Rüstung bietet dem Spieler eine bessere Verteidigung gegen Angriffe von Mobs und anderen Spielern.

## Eine Rüstungsmaterial Klasse erstellen {#creating-an-armor-materials-class}

Technisch gesehen brauchst du keine eigene Klasse für dein Rüstungsmaterial, aber bei der Anzahl an statischen Felder, die du benötigst, ist es auf jeden Fall eine gute Praxis.

Für dieses Beispiel werden wir eine Klasse `GuiditeArmorMaterial` erstellen, um unsere statischen Felder zu speichern.

### Grundhaltbarkeit {#base-durability}

Diese Konstante wird in der Methode `Item.Properties#maxDamage(int damageValue)` verwendet, wenn wir unsere Rüstungsitems erstellen. Sie wird auch als Parameter im Konstruktor `ArmorMaterial` benötigt, wenn wir später unser `ArmorMaterial`-Objekt erstellen.

@[code transcludeWith=:::base_durability](@/reference/latest/src/main/java/com/example/docs/item/armor/GuiditeArmorMaterial.java)

Wenn du Schwierigkeiten hast, eine ausgewogene Grundhaltbarkeit zu bestimmen, kannst du dich an den Instanzen der Vanilla-Rüstungsmaterialien orientieren, die du in dem Interface `ArmorMaterials` findest.

### Ausrüstungs Asset Registry Key {#equipment-asset-registry-key}

Obwohl wie unser `ArmorMaterial` nirgendwo registrieren müssen, sollte man generell alle Registerschlüssel als Konstanten speichern, da das Spiel diese nutzen wird, um die relevanten Texturen für unsere Rüstung zu finden.

@[code transcludeWith=:::material_key](@/reference/latest/src/main/java/com/example/docs/item/armor/GuiditeArmorMaterial.java)

Wir werden dies später an den Konstruktor `ArmorMaterial` übergeben.

### `ArmorMaterial` Instanz {#armormaterial-instance}

Um unser Material zu erstellen, müssen wir eine neue Instanz des `ArmorMaterial`-Record erstellen, wobei die Grundhaltbarkeit und die Konstanten der Material-Registrierungsschlüssel hier verwendet werden.

@[code transcludeWith=:::guidite_armor_material](@/reference/latest/src/main/java/com/example/docs/item/armor/GuiditeArmorMaterial.java)

Der `ArmorMaterial`-Konstruktor akzeptiert die folgenden Parameter, in dieser spezifischen Reihenfolge:

| Parameter             | Beschreibung                                                                                                                                                                                                                                             |
| --------------------- | -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| `durability`          | Sie wird bei der Berechnung der Gesamthaltbarkeit jedes einzelnen Rüstungsteils verwendet, das dieses Material verwendet. Dies sollte die Basis-Haltbarkeitskonstante sein, die Sie zuvor erstellt haben.                |
| `defense`             | Eine Map von `EquipmentType` (eine Aufzählung, die jeden Rüstungsplatz darstellt) zu einem ganzzahligen Wert, der den Verteidigungswert des Materials angibt, wenn es im entsprechenden Rüstungsplatz verwendet wird. |
| `enchantmentValue`    | Die "Verzauberbarkeit" von Rüstungsitems, die dieses Material verwenden.                                                                                                                                                                 |
| `equipSound`          | Ein Registrierungsschlüssel für ein Sound-Event, das abgespielt wird, wenn man eine Rüstung aus diesem Material anlegt. Weitere Informationen zu Sounds findest du auf der Seite [Custom Sounds](../sounds/custom).      |
| `toughness`           | Ein Float-Wert, der das Attribut "Häte" des Rüstungsmaterials darstellt - im Wesentlichen, wie gut die Rüstung Schaden absorbiert.                                                                                                       |
| `knockbackResistance` | Ein Float-Wert, der den Grad der Rückstoßfestigkeit angibt, den das Rüstungsmaterial dem Träger gewährt.                                                                                                                                 |
| `repairIngredient`    | Ein Item-Tag, das alle Items repräsentiert, die zur Reparatur von Rüstungsteilen aus diesem Material in einem Amboss verwendet werden können.                                                                                            |
| `assetId`             | Ein `EquipmentAsset`-Registrierungsschlüssel; dies sollte die zuvor erstellte Registrierungsschlüsselkonstante Rüstungsasset sein.                                                                                                       |

Wir definieren die Referenz für die Bestandteile der Reparatur wie folgt:

@[code transcludeWith=:::repair_tag](@/reference/latest/src/main/java/com/example/docs/item/armor/GuiditeArmorMaterial.java)

Wenn du Schwierigkeiten hast, die Werte für einen der Parameter zu bestimmen, kannst du die Vanilla-Instanzen von `ArmorMaterial` zu Rate ziehen, die in dem Interface `ArmorMaterials` zu finden sind.

## Rüstungsitems erstellen {#creating-the-armor-items}

Nachdem du das Material registriert hast, kannst du die Rüstungsitems in deiner Klasse `ModItems` erstellen:

Natürlich muss ein Rüstungsset nicht jeden Typ abdecken, man kann auch ein Set mit nur Stiefeln oder Hosen etc. haben. - Der Vanille-Schildkrötenpanzerhelm ist ein gutes Beispiel für ein Rüstungsset mit fehlenden Slots.

Im Gegensatz zu `ToolMaterial` speichert `ArmorMaterial` keine Informationen über die Haltbarkeit von Items. Aus diesem Grund muss die Grundhaltbarkeit manuell zu den `Item.Properties` der Rüstungsgegenstände hinzugefügt werden, wenn diese registriert werden.

Dies wird erreicht, indem die Konstante `BASE_DURABILITY`, die wir zuvor erstellt haben, an die Methode `maxDamage` in der Klasse `Item.Properties` übergeben wird.

@[code transcludeWith=:::6](@/reference/latest/src/main/java/com/example/docs/item/ModItems.java)

Außerdem musst du die Items **einem Kreativtab hinzufügen**, wenn du möchtest, dass sie über das Kreativinventar zugänglich sind.

Wie bei allen Items solltest du auch für diese Übersetzungsschlüssel erstellen.

## Texturen und Modelle {#textures-and-models}

Benutzerdefinierte Rüstung {#custom-armor}

### Itemtexturen und Modell {#item-textures-and-model}

Diese Texturen unterscheiden sich nicht von anderen Items - Du musst die Texturen erstellen und ein generisches Itemmodell erstellen, was in der Anleitung [Erstellen des ersten Items](./first-item#adding-a-texture-and-model) behandelt wurde.

Als Beispiel dient das folgende Textur- und Modell-JSON als Referenz.

<DownloadEntry visualURL="/assets/develop/items/armor_0.png" downloadURL="/assets/develop/items/example_armor_item_textures.zip">Item Texturen</DownloadEntry>

::: info

Du benötigst JSON-Modelldateien für alle Gegenstände, nicht nur für den Helm. Es ist das gleiche Prinzip wie bei anderen Itemmodellen.

:::

@[code](@/reference/latest/src/main/generated/assets/example-mod/models/item/guidite_helmet.json)

Wie du sehen kannst, sollten die Rüstungsitems im Spiel geeignete Modelle haben:

![Rüstungsitem Modelle](/assets/develop/items/armor_1.png)

### Rüstungstexturen {#armor-textures}

Wenn eine Entität deine Rüstung trägt, wird nichts angezeigt. Das liegt daran, dass dir die Texturen und die Definitionen der Rüstungsmodelle fehlen.

![Kaputtes Rüstungsmodell an einem Spieler](/assets/develop/items/armor_2.png)

Es gibt zwei Schichten für die Rüstungstextur, beide müssen vorhanden sein.

Zuvor haben wir eine Konstante `ResourceKey<EquipmentAsset>` mit dem Namen `GUIDITE_ARMOR_MATERIAL_KEY` erstellt, die wir an unseren `ArmorMaterial`-Konstruktor übergeben haben. Es wird empfohlen, die Textur ähnlich zu benennen, in unserem Fall also `guidite.png`

- `assets/example-mod/textures/entity/equipment/humanoid/guidite.png` - Enthält Oberkörper- und Stiefeltexturen.
- `assets/example-mod/textures/entity/equipment/humanoid_leggings/guidite.png` - Enthält Hosentexturen.

<DownloadEntry downloadURL="/assets/develop/items/example_armor_layer_textures.zip">Guidite Rüstungsmodell-Texturen</DownloadEntry>

::: tip

Wenn du von einer älteren Version des Spiels auf 1.21.11 aktualisierst, ist der Ordner `humanoid` der Ort, an dem deine Rüstungstextur `layer0.png` liegt, und der Ordner `humanoid_leggings` ist der Ort, an dem deine Rüstungstextur `layer1.png` liegt.

:::

Als Nächstes musst du eine Definition für ein zugehöriges Ausrüstungsmodell erstellen. Diese gehören in den Ordner `/assets/example-mod/equipment/`.

Die Konstante `ResourceKey<EquipmentAsset>`, die wir zuvor erstellt haben, bestimmt den Namen der JSON-Datei. In diesem Fall wird es `guidite.json` sein.

Da wir nur "Humanoide" Rüstungsteile (Helm, Brustpanzer, Hose, Stiefel usw.) hinzufügen wollen , werden die Definitionen der Ausrüstungsmodelle wie folgt aussehen:

@[code](@/reference/latest/src/main/resources/assets/example-mod/equipment/guidite.json)

Wenn die Texturen und die Definition des Rüstungsmodell vorhanden sind, solltest du in der Lage sein, deine Rüstung auf den Entitäten zu sehen, die sie tragen:

![Funktionierendes Rüstungsmodell an einem Spieler](/assets/develop/items/armor_3.png)

<!-- TODO: A guide on creating equipment for dyeable armor could prove useful. -->
