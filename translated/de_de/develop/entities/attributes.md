---
title: Entitätsattribute
description: Erfahre, wie du Entitäten benutzerdefinierte Attribute hinzufügen kannst.
authors:
  - cassiancc
  - cprodhomme
---

Attribute bestimmen die Eigenschaften, die deine modifizierte Entität besitzen kann. Mit Fabric kannst du eigene benutzerdefinierte Attribute erstellen, die die Spielmechanik verbessern, und auch die Vanillaattribute anwenden.

## Ein Benutzerdefiniertes Attribut erstellen {#creating-a-custom-attribute}

Lasst uns ein benutzerdefiniertes Attribut mit dem Namen `AGGRO_RANGE` erstellen. Dieses Attribut wird die Entfernung steuern, aus der eine Entität potenzielle Bedrohungen erkennen und darauf reagieren kann.

### Die Attributklasse definieren {#define-the-attribute-class}

Beginne damit, eine Java-Klasse zu erstellen, um die Definition und Registrierung deines Attributs unter der Codestruktur deines Mods zu verwalten. Dieses Beispiel erstellt die folgenden Funktionen in einer Klasse namens `ModAttributes`.

Beginne zunächst mit einer einfachen Hilfsmethode, um die Attribute deines Mods zu registrieren. Diese Methode akzeptiert die folgenden Parameter und registriert ein Attribut.

- Ein `String`, der den Namen deines Attributs darstellt
- Ein `double`, der den Standardwert deines Attributs darstellt
- Ein `double`, welcher der niedrigste Wert sein wird, den dein Attribut erreichen wird
- Ein `double`, welcher der höchste Wert sein wird, den dein Attribut erreichen wird
- Ein `boolean`, der angibt, ob das Attribut mit Clients synchronisiert wird

@[code lang=java transcludeWith=:::register](@/reference/latest/src/main/java/com/example/docs/entity/attribute/ModAttributes.java)

Anschließend registrieren wir ein Attribut namens `AGGRO_RANGE` mit dem Namen `aggro_range`, einem Standardwert von `8.0`, einem minimalen Wert von `0` und einem maximalen Wert, der so hoch wie möglich eingestellt ist. Dieses Attribut wird nicht mit den Spielern synchronisiert.

@[code lang=java transcludeWith=:::attributes](@/reference/latest/src/main/java/com/example/docs/entity/attribute/ModAttributes.java)

### Benutzerdefinierte Attribute übersetzen {#attribute-translation}

Um den Attributnamen in einem für Menschen lesbaren Format anzuzeigen, musst du die Datei `assets/example-mod/lang/en_us.json` wie folgt ändern:

```json
{
  "attribute.name.example-mod.aggro_range": "Aggro Range"
}
```

### Initialisierung {#initialization}

Um sicherzustellen, dass das Attribut ordnungsgemäß registriert wird, musst du sicherstellen, dass es beim Start des Mods initialisiert wird. Dies kann erreicht werden, indem du zu deiner Klasse eine öffentliche statische Initialisierungsmethode hinzufügst und diese aus deiner [Mod-Initialisierungsklasse](../getting-started/project-structure#entrypoints) aufrufst. Derzeit benötigt diese Methode keine Inhalte.

@[code lang=java transcludeWith=:::initialize](@/reference/latest/src/main/java/com/example/docs/entity/attribute/ModAttributes.java)

@[code lang=java transcludeWith=:::init](@/reference/latest/src/main/java/com/example/docs/entity/attribute/ExampleModAttributes.java)

Der Aufruf einer Methode einer Klasse initialisiert diese statisch, wenn sie nicht vorher geladen wurde - das bedeutet, dass alle `static` Felder ausgewertet werden. Dafür ist diese Dummy-Methode `initialize` gedacht.

## Attribute anwenden {#apply-the-attribute}

Attribute müssen an eine Entität angehängt werden, um wirksam zu werden. Dies geschieht in der Regel in der Methode, in der die Attribute einer Entität erstellt oder geändert werden.

Vanilla bietet ebenfalls Attribute, darunter [maximale Gesundheit](https://minecraft.wiki/w/Attribute#Max_health), [Bewegungsgeschwindigkeit](https://minecraft.wiki/w/Attribute#Movement_speed) und [Angriffsschaden](https://minecraft.wiki/w/Attribute#Attack_damage), wie unten zu sehen ist. Für eine vollständige Liste, siehe die Vanilla-Klasse `Attributes` und im [Minecraft Wiki](https://minecraft.wiki/w/Attribute).

Als Demo werden wir die zuvor erstellten Attribute Maximale Gesundheit, Bewegungsgeschwindigkeit, Angriffsschaden und Aggro-Reichweite einbeziehen.

<!-- TODO: move to the reference mod -->

```java
public static AttributeSupplier.Builder createEntityAttributes() {
    return Mob.createMobAttributes()
        .add(Attributes.MAX_HEALTH, 25.0)
        .add(Attributes.MOVEMENT_SPEED, 0.22)
        .add(Attributes.ATTACK_DAMAGE, 3.0)
        .add(ModAttributes.AGGRO_RANGE, 8.0);
}
```

## Lesen und modifizieren von Attributen {#reading-modifying-attributes}

Ein Attribut an sich ist lediglich eine an eine Entität angehängte Information. Damit es nützlich ist, müssen wir in der Lage sein, daraus zu lesen und darin zu schreiben. Dazu gibt es zwei Möglichkeiten: Entweder du rufst die `AttributeInstance` der Entität ab oder du rufst den Wert direkt ab.

```java
entity.getAttribute(ModAttributes.AGGRO_RANGE) // returns an `AttributeInstance`
entity.getAttributeValue(ModAttributes.AGGRO_RANGE) // returns a double with the current value
entity.getAttributeBaseValue(ModAttributes.AGGRO_RANGE) // returns a double with the base value
```

Eine `AttributeInstance` ermöglicht mehr Flexibilität, beispielsweise das Festlegen eines `AttributeModifier` für das Attribut unter Verwendung einer der [drei Standardoperationen für Attributmodifikatoren](https://minecraft.wiki/w/Attribute#Operations). Modifikatoren können permanent (in NBT gespeichert) oder transitiv (nicht in NBT gespeichert) sein und werden mit `addPermanentModifier` bzw. `addTransitiveModifier` hinzugefügt.

```java
attribute.addPermanentModifier(
    new AttributeModifier(
        Identifier.fromNamespaceAndPath(ExampleMod.MOD_ID, "increased_range"), // the ID of your modifier, should be static so it can be removed
        8, // how much to modify it
        AttributeModifier.Operation.ADD_VALUE // what operator to use, see the wiki page linked above
    ));
```

Sobald du Zugriff auf den Attributwert hast, kannst du ihn in der KI deiner Entität verwenden.
