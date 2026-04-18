---
title: Enum Erweiterung
description: Erfahre, wie man mit Mixin und dem Klassenoptimierer Einträge zu Enums hinzufügen kann.
authors:
  - cassiancc
  - CelDaemon
  - its-miroma
  - Jab125
  - LlamaLad7
  - MildestToucan
---

Die Enum Erweiterung ist eine Mixin-Funktion, mit der sich zuverlässig neue Einträge zu einem Enum hinzufügen lassen.

Wenn du auf Minecraft-Enums abzielst, kannst du Mixins zusammen mit [Klassenoptimierung](../class-tweakers) verwenden, um neue Enum-Einträge im dekompilierten Quellcode anzuzeigen. Wenn diese Option auf [transitiv](../class-tweakers/index#transitive-entries) gesetzt ist, sehen Mods, die auf deinen Mod aufbauen, ebenfalls deine hinzugefügten Einträge.

::: warning

Für die Enum Erweiterung ist mindestens Loader 0.19.0 für die Mixin-Unterstützung und mindestens Loom 1.16 für die Klassenoptimierer-Unterstützung erforderlich.

Außerdem muss in den Kopfzeilen von Klassenoptimierern `v2` als Version angegeben werden, damit die Enum Erweiterungen verwendet werden können.

:::

## Das Mixin erstellen {#creating-the-mixin}

Bevor du die Mixin-Klasse erstellst, stelle sicher, dass Loader 0.19.0 oder höher als explizite Abhängigkeit
in deiner Datei `fabric.mod.json` aufgeführt ist:

```json:no-line-numbers
...
"depends": {
  ...
  "fabricloader": ">=0.19.0"
  ...
}
...
```

Selbst wenn du die richtige Loader-Version als Gradle-Abhängigkeit verwendest, musst du explizit mindestens Version 0.19.0 als Abhängigkeit angeben, um diese Mixin-Funktion verwenden zu können.

Um eine Enum Erweiterung zu erstellen, definiere ein `enum` in deinem Mixin-Paket, versehe es mit der Annotation `@Mixin` und füge deine Konstanten so hinzu, als wären sie Teil der Ziel-Enum-Klasse. Lasst uns zum Beispiel einen neuen Eintrag zu `RecipeBookType` hinzufügen:

<<< @/reference/latest/src/main/java/com/example/docs/mixin/class_tweakers/RecipeBookTypeMixin.java#enum-extension-no-impls-example-mixin

:::warning WICHTIG

Du solltest die von dir hinzugefügten Enum-Konstanten stets deine Mod-ID voranstellen, um ihre Eindeutigkeit zu gewährleisten. Für diese Dokumentationen verwenden wir `EXAMPLE_MOD_`.

:::

### Argumente an den Konstruktor übergeben {#passing-constructor-arguments}

Wenn das Ziel-Enum keinen Standardkonstruktor hat, musst du einen Konstruktor der Zielklasse überschreiben und die erforderlichen Argumente an die Deklaration deines hinzugefügten Eintrags übergeben.

Lasst uns zum Beispiel einen neuen Eintrag zu `RecipeCategory` hinzufügen. Erstelle einen Konstruktor, der dem gewünschten in der Zielklasse entspricht, und versehe ihn mit der Annotation `@Shadow`.

<<< @/reference/latest/src/main/java/com/example/docs/mixin/class_tweakers/RecipeCategoryMixin.java#enum-extension-ctor-impls-example-mixin

### Abstrakte Methoden implementieren {#implementing-abstract-methods}

Um die abstrakten Methoden eines Ziel-Enum zu implementieren, überschreibe die abstrakte Methode und implementiere sie anschließend in deinem hinzugefügten Eintrag. Lasst uns zum Beispiel einen neuen Eintrag zu `ConversionType` hinzufügen:

<<< @/reference/latest/src/main/java/com/example/docs/mixin/class_tweakers/ConversionTypeMixin.java#enum-extension-abstract-method-impls-example-mixin

## Den Eintrag für den Klassenoptimierer erstellen {#making-the-class-tweaker-entry}

Wenn du eine Minecraft-Enum bearbeiten möchtest, kannst du einen Klassenoptimierer-Eintrag verwenden, um das betreffende Enum im dekompilierten Quellcode sichtbar zu ändern.

Um diese Funktion zu aktivieren, stelle bitte sicher, dass du Loom 1.16 oder höher verwendest und die [Version in der Kopfzeile](../class-tweakers/index#file-format) auf `v2` setzt.

Die Syntax für einen Eintrag zur Erweiterung eines Enum ist:

```:no-line-numbers
extend-enum  <targetClassName>  <ENUM_CONSTANT_NAME>
```

Bei Klassenoptimierung verwenden Klassen ihre [internen Namen](../mixins/bytecode#class-names).

Beispielsweise würde der Eintrag im Klassenoptimierer für die Konstante `RecipeBookType`, die wir im [Mixin-Abschnitt](#creating-the-mixin) hinzugefügt haben, wie folgt lauten:

<<< @/reference/latest/src/main/resources/example-mod.classtweaker#enum-extension-no-impls-example-entry{classtweaker:no-line-numbers}

## Änderungen anwenden {#applying-changes}

Du musst dein Gradle-Projekt aktualisieren und die [Quellen neu generieren](../getting-started/generating-sources), bevor du die hinzugefügten Enum-Einträge in der dekompilierten Quelle sehen kannst.
Wenn die Änderungen nicht angezeigt werden, kannst du versuchen, die Datei zu [validieren](../class-tweakers/index#validating-the-file) und zu prüfen, ob Fehler auftreten.

::: info

Im dekompilierten Quellcode wirst du weder [an den Konstruktor übergebene Argumente](#passing-constructor-arguments) noch [Methodenimplementierungen](#implementing-abstract-methods) oder andere Elemente sehen.
Das liegt daran, dass diese vom Mixin verwaltet werden und erst zur Laufzeit angewendet werden.

:::

Du kannst die Enum-Konstante jetzt in deinem Code verwenden:

<<< @/reference/latest/src/main/java/com/example/docs/enum_extension/ExampleModEnumExtension.java#enum-extension-added-constant-usage-example

Wenn du es nur über ein Mixin hinzufügst und es nicht im dekompilierten Quellcode enthalten ist, kannst du gegen dieses prüfen, indem du den Namen vergleichst:

<<< @/reference/latest/src/main/java/com/example/docs/enum_extension/ExampleModEnumExtension.java#enum-extension-added-constant-no-ct-usage-example-check

Wenn du die Konstante an mehreren Stellen verwenden musst, rufe sie mit `valueOf` ab und speichere das Ergebnis in einem Feld:

<<< @/reference/latest/src/main/java/com/example/docs/enum_extension/ExampleModEnumExtension.java#enum-extension-added-constant-no-ct-usage-example-store

## Fallstricke {#pitfalls}

Die Enum-Erweiterung kann nicht garantieren, dass die von dir hinzugefügten Einträge keine Fehler verursachen.

Es liegt in deiner Verantwortung, die Verwendungen des Ziel-Enum zu überprüfen und Probleme nach Möglichkeit zu vermeiden. Wenn du einige Probleme nicht lösen kannst und es zu Abstürzen kommt, ist es vielleicht am besten, die Enum Erweiterung überhaupt nicht zu verwenden.

In diesem Abschnitt werden einige Muster behandelt, auf die man bei der Erweiterung von Enums achten und die man vermeiden sollte; die Aufzählung ist jedoch nicht vollständig.

### Switch-Audrücke {#switch-expressions}

Switch-Ausdrücke werden häufig zur Verarbeitung von Enum-Konstanten verwendet. Aus diesem Grund kann es zu einem Absturz kommen, wenn ein Switch-Ausdruck Einträge, die von anderen Mods hinzugefügt wurden, nicht verarbeitet. Nehmen wir zum Beispiel an, wir hätten den folgenden Switch-Ausdruck:

<<< @/reference/latest/src/main/java/com/example/docs/enum_extension/ExampleModEnumExtension.java#enum-extension-problematic-switch-expr-example

Beachte, dass es keine `default`-Klausel gibt. Auch wenn wir alle Werte in dem Vanilla-Enum und in unserem eigenen Enum behandelt haben, würde dies zu einem Fehler führen, wenn ein anderer Mod einen anderen Eintrag hinzufügt.

Wie kannst du dies vermeiden? Es gibt keinen allgemeingültigen Weg, um Abstürze zu vermeiden - dein Vorgehen sollte jeweils individuell angepasst werden. Generell gilt jedoch:

- Wenn sich der `switch`-Ausdruck in einer einfachen Methode befindet, kannst du ihn mithilfe eines Mixin bearbeiten
- Wenn der `switch`-Ausdruck aus einem Mod stammt, solltest du versuchen, Kontakt zu den Entwicklern aufzunehmen, um gemeinsam eine kompatible Lösung zu finden. Andernfalls musst du möglicherweise ein Mixin für den anderen Mod erstellen.

### Serialisierte Enums {#serialized-enums}

Bestimmte Einträge in Enums werden automatisch serialisiert. Ein Beispiel hierfür ist das Enum `Variants` in der Klasse `Axolotl`.

Durch die Erweiterung dieser Enums würde dein benutzerdefinierter Eintrag unter dem Namensraum von Minecraft serialisiert werden, und in einigen Versionen könnte dies auf der Grundlage einer numerischen ID geschehen.
Das ist nicht gut, da es sich auf die Indizes aller anderen Einträge auswirken kann.

Es ist am besten, die Erweiterung von Enums gänzlich zu vermeiden, wenn deine Einträge auf diese Weise serialisiert werden. Stattdessen solltest du nach einer API suchen, falls eine verfügbar ist.
