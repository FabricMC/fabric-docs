---
title: Entitätsattribute
description: Erfahre, wie du Entitäten benutzerdefinierte Attribute hinzufügen kannst.
authors:
  - cassiancc
  - cprodhomme
  - Tenneb22
resources:
  https://minecraft.wiki/w/Attribute: Attribute - Minecraft Wiki
  https://docs.neoforged.net/docs/entities/attributes: Attribute - NeoForge Docs (ausgenommen Neo exklusive)
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

<<< @/reference/26.1.2/src/main/java/com/example/docs/entity/attribute/ModAttributes.java#register

Anschließend registrieren wir ein Attribut namens `AGGRO_RANGE` mit dem Namen `aggro_range`, einem Standardwert von `8.0`, einem minimalen Wert von `0` und einem maximalen Wert, der so hoch wie möglich eingestellt ist. Dieses Attribut wird nicht mit den Spielern synchronisiert.

<<< @/reference/26.1.2/src/main/java/com/example/docs/entity/attribute/ModAttributes.java#attributes

### Benutzerdefinierte Attribute übersetzen {#attribute-translation}

Um den Attributnamen in einem für Menschen lesbaren Format anzuzeigen, musst du die Datei `assets/example-mod/lang/en_us.json` wie folgt ändern:

```json
{
  "attribute.name.example-mod.aggro_range": "Aggro Range"
}
```

### Initialisierung {#initialization}

Um sicherzustellen, dass das Attribut ordnungsgemäß registriert wird, musst du sicherstellen, dass es beim Start des Mods initialisiert wird. Dies kann erreicht werden, indem du zu deiner Klasse eine öffentliche statische Initialisierungsmethode hinzufügst und diese aus deiner [Mod-Initialisierungsklasse](../getting-started/project-structure#entrypoints) aufrufst. Derzeit benötigt diese Methode keine Inhalte.

<<< @/reference/26.1.2/src/main/java/com/example/docs/entity/attribute/ModAttributes.java#initialize

<<< @/reference/26.1.2/src/main/java/com/example/docs/entity/attribute/ExampleModAttributes.java#init

Der Aufruf einer Methode einer Klasse initialisiert diese statisch, wenn sie nicht vorher geladen wurde - das bedeutet, dass alle `static` Felder ausgewertet werden. Dafür ist diese Dummy-Methode `initialize` gedacht.

## Attribute anwenden {#apply-the-attribute}

Attribute müssen an eine Entität angehängt werden, um wirksam zu werden. Dies geschieht in der Regel in der Methode, in der die Attribute einer Entität erstellt oder geändert werden.

Vanilla bietet ebenfalls Attribute, darunter [maximale Gesundheit](https://minecraft.wiki/w/Attribute#Max_health), [Bewegungsgeschwindigkeit](https://minecraft.wiki/w/Attribute#Movement_speed) und [Angriffsschaden](https://minecraft.wiki/w/Attribute#Attack_damage). Für eine vollständige Liste, siehe die Vanilla-Klasse `Attributes` und im [Minecraft Wiki](https://minecraft.wiki/w/Attribute).

Dies ist ein Beispiel dafür, wie man Vanilla-Attribute und das zuvor erstellte Attribut `AGGRO_RANGE` zur Mini-Golem-Entität aus dem Leitfaden [Erstellen deiner ersten Entität](./first-entity) hinzufügt.

<<< @/reference/26.1.2/src/main/java/com/example/docs/entity/MiniGolemEntity.java#attributes

## Lesen und modifizieren von Attributen {#reading-modifying-attributes}

Ein Attribut an sich ist lediglich eine an eine Entität angehängte Information. Damit es nützlich ist, müssen wir in der Lage sein, daraus zu lesen und darin zu schreiben. Dazu gibt es zwei Möglichkeiten: Entweder du rufst die `AttributeInstance` der Entität ab oder du rufst den Wert direkt ab.

<<< @/reference/26.1.2/src/gametest/java/com/example/docs/entity/EntityAttributesGameTest.java#reading_entity_attributes

Eine `AttributeInstance` ermöglicht mehr Flexibilität, beispielsweise das Festlegen eines `AttributeModifier` für das Attribut unter Verwendung einer der [drei Standardoperationen für Attributmodifikatoren](https://minecraft.wiki/w/Attribute#Operations). Modifikatoren können permanent (in NBT gespeichert) oder transitiv (nicht in NBT gespeichert) sein und werden mit `addPermanentModifier` bzw. `addTransitiveModifier` hinzugefügt.

<<< @/reference/26.1.2/src/gametest/java/com/example/docs/entity/EntityAttributesGameTest.java#modifying_entity_attributes

Sobald du Zugriff auf den Attributwert hast, kannst du ihn in der KI deiner Entität verwenden.
