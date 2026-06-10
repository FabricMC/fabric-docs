---
title: Generierung von Rezepten
description: Ein Leitfaden zur Einrichtung der Generierung von Rezepten mit dem Datengenerator.
authors:
  - CelDaemon
  - skycatminepokie
  - Spinoscythe
authors-nogithub:
  - jmanc3
  - mcrafterzz
---

<!---->

:::info VORAUSSETZUNGEN

Stelle sicher, dass du den Prozess der [Einrichtung des Datengenerators](./setup) zuerst abgeschlossen hast.

:::

## Einrichtung {#setup}

Zuerst benötigen wir unseren Provider. Erstelle eine Klasse, die von `FabricRecipeProvider` erbt. Die ganze Generierung der Rezepte wird innerhalb der Methode `buildRecipes` unseres Provider geschehen.

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModRecipeProvider.java#datagen_recipes_provider

Um die Einrichtung abzuschließen, füge den Provider zu deinem `DataGeneratorEntrypoint` in der `onInitializeDataGenerator` Methode hinzu.

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModDataGenerator.java#datagen_recipes_register

## Formlose Rezepte {#shapeless-recipes}

Formlose Rezepte sind relativ unkompliziert. Füge sie einfach zu der Methode `buildRecipes` in deinem Provider hinzu:

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModRecipeProvider.java#datagen_recipes_shapeless

### Färberezepte {#dye-recipes}

Färberezepte werden verwendet, um Items in deinem Inventar zu färben.

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModRecipeProvider.java#datagen_recipes_dye

## Geformte Rezepte {#shaped-recipes}

Für ein geformtes Rezept, definierst du die Form unter Verwendung eines `String`, dann definiere, was jedes `char` in dem `String` repräsentiert.

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModRecipeProvider.java#datagen_recipes_shaped

::: tip

Es gibt viele Hilfsmethoden für die Erstellung von allgemeinen Rezepten. Sieh dir an, was der `RecipeProvider` anbietet! Nutze <kbd>Alt</kbd>+<kbd>7</kbd> in IntelliJ, um die Struktur einer Klasse, einschließlich einer Liste an Methoden, zu öffnen.

:::

## Andere Rezepte {#other-recipes}

Andere Rezepte funktionieren ähnlich, aber erfordern einige zusätzliche Parameter. Zum Beispiel, Schmelzrezepte müssen wissen, wie viel Erfahrung zu vergeben ist.

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModRecipeProvider.java#datagen_recipes_smelting

Beim Räuchern ist es etwas anders: Hier kommt nicht derselbe Rezeptgenerator zum Einsatz wie bei Blöcken, die wie Schmelzöfen funktionieren.

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModRecipeProvider.java#datagen_recipes_smoking

## Ressourcenbedingungen {#resource-conditions}

Um eine [Ressourcenbedingung](../resource-conditions) auf ein datengeneriertes Rezept anzuwenden, umschließe die Ausgabe mit `withConditions` und gebe die gewünschten Ressourcenbedingungen an. Dadurch wird ein Rezept und ein Fortschritt generiert, auf die Ressourcenbedingungen angewendet werden:

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModRecipeProvider.java#datagen_recipes_conditions
