---
title: Generierung von Rezepten
description: Ein Leitfaden zur Einrichtung der Generierung von Rezepten mit dem Datengenerator.
authors:
  - skycatminepokie
  - Spinoscythe
authors-nogithub:
  - jmanc3
  - mcrafterzz
---

:::info VORAUSSETZUNGEN
Stelle sicher, dass du den Prozess der [Einrichtung der Datengenerierung](./setup) zuerst abgeschlossen hast.
:::

## Einrichten {#setup}

Zuerst benötigen wir unseren Provider. Erstelle eine Klasse, die `extends FabricRecipeProvider`. Die ganze Generierung der Rezepte wird innerhalb der `generate` Methode unseres Provider geschehen.

@[code lang=java transcludeWith=:::datagen-recipes:provider](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModRecipeProvider.java)

Um die Einrichtung abzuschließen, füge den Provider zu deinem `DataGeneratorEntrypoint` in der `onInitializeDataGenerator` Methode hinzu.

@[code lang=java transclude={32-32}](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModDataGenerator.java)

## Formlose Rezepte {#shapeless-recipes}

Formlose Rezepte sind relativ unkompliziert. Füge sie einfach zu der `generate` Methode in deinem Provider hinzu:

@[code lang=java transcludeWith=:::datagen-recipes:shapeless](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModRecipeProvider.java)

## Geformte Rezepte {#shaped-recipes}

Für ein geformtes Rezept, definierst du die Form unter Verwendung eines `String`, dann definiere, was jedes `char` in dem `String` repräsentiert.

@[code lang=java transcludeWith=:::datagen-recipes:shaped](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModRecipeProvider.java)

:::tip
Es gibt viele Hilfsmethoden für die Erstellung von allgemeinen Rezepten. Sieh dir an, was der `RecipeProvider` anbietet! Nutze `Alt + 7` in IntelliJ, um die Struktur, einschließlich einer Liste an Methoden, einer Klasse zu öffnen.
:::

## Andere Rezepte {#other-recipes}

Andere Rezepte funktionieren ähnlich, aber erfordern einige zusätzliche Parameter. Zum Beispiel, Schmelzrezepte müssen wissen, wie viel Erfahrung zu vergeben ist.

@[code lang=java transcludeWith=:::datagen-recipes:other](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModRecipeProvider.java)

## Benutzerdefinierte Rezepttypen {#custom-recipe-types}
