---
title: Generierung von Verzauberungen
description: Ein Leitfaden zur Generierung von Verzauberungen mit dem Datengenerator.
authors:
  - CelDaemon
---

<!---->

:::info VORAUSSETZUNGEN

Stelle sicher, dass du den Prozess der [Einrichtung des Datengenerators](./setup) zuerst abgeschlossen hast.

:::

## Einrichtung {#setup}

Bevor du den Generator implementierst, erstelle das Paket `enchantment` im Hauptquellensatz und füge die Klasse `ModEnchantments` zu diesem hinzu. Dann füge die Methode `key` zu dieser neuen Klasse hinzu.

<<< @/reference/26.2/src/main/java/com/example/docs/enchantment/ModEnchantments.java#key_helper

Nutze diese Methode, um einen `ResourceKey` für deine Verzauberung zu erstellen.

<<< @/reference/26.2/src/main/java/com/example/docs/enchantment/ModEnchantments.java#register_enchantment

Jetzt sind wir bereit, den Generator hinzuzufügen. Erstelle im Paket datagen eine Klasse, die von `FabricDynamicRegistryProvider` erbt. Füge in diese neu erstellte Klasse einen Konstruktor hinzu, der mit `super` übereinstimmt, und implementiere die Methoden `configure` und `getName`.

<<< @/reference/26.2/src/client/java/com/example/docs/datagen/ExampleModEnchantmentGenerator.java#provider

Füge dann eine Hilfsmethode `register` zu der neu erstellen Klasse hinzu.

<<< @/reference/26.2/src/client/java/com/example/docs/datagen/ExampleModEnchantmentGenerator.java#register_helper

Füge jetzt die Methode `bootstrap` hinzu. Hier werden wir die Verzauberungen registrieren, die wir zum Spiel hinzufügen wollen.

<<< @/reference/26.2/src/client/java/com/example/docs/datagen/ExampleModEnchantmentGenerator.java#bootstrap

Überschreibe in deinem `DataGeneratorEntrypoint` die Methode `buildRegistry` und registriere unsere Methode bootstrap.

<<< @/reference/26.2/src/client/java/com/example/docs/datagen/ExampleModDataGenerator.java#datagen_enchantments_bootstrap

Stelle schließlich sicher, dass dein neuer Generator innerhalb der Methode `onInitializeDataGenerator` registriert ist.

<<< @/reference/26.2/src/client/java/com/example/docs/datagen/ExampleModDataGenerator.java#datagen_enchantments_register

## Die Verzauberung erstellen {#creating-the-enchantment}

Um die Definition für unsere benutzerdefinierte Verzauberung zu erstellen, verwenden wir die Methode `register` in unserer Generator-Klasse.

Registriere deine Verzauberung in der Methode `bootstrap` des Generators unter Verwendung der in `ModEnchantments` registrierten Verzauberung.

In diesem Beispiel verwenden wir den in [Benutzerdefinierte Verzauberungseffekte](../items/custom-enchantment-effects) erstellten Verzauberungseffekt, aber du kannst auch die [Vanilla Verzauberungseffekte](https://minecraft.wiki/w/Enchantment_definition#Effect_components) verwenden.

<<< @/reference/26.2/src/client/java/com/example/docs/datagen/ExampleModEnchantmentGenerator.java#register_enchantment

Führen nun einfach die Datengenerierung aus, und deine neue Verzauberung wird im Spiel verfügbar sein!

## Effektbedingungen {#effect-conditions}

Die meisten Verzauberungseffekte sind bedingte Effekte. Beim Hinzufügen dieser Effekte ist es möglich, Bedingungen an den Aufruf von `withEffect` zu übergeben.

::: info

Eine Übersicht über die verfügbaren Zustandstypen und deren Verwendung findest du in der Klasse `Enchantments` (https://mcsrc.dev/#1/1.21.11_unobfuscated/net/minecraft/world/item/enchantment/Enchantments#L126).

:::

<<< @/reference/26.2/src/client/java/com/example/docs/datagen/ExampleModEnchantmentGenerator.java#effect_conditions

## Mehrere Effekte {#multiple-effects}

`withEffect` kann verkettet werden, um einer einzelnen Verzauberung mehrere Verzauberungseffekte hinzuzufügen. Bei dieser Methode musst du jedoch die Effektbedingungen für jeden Effekt angeben.

Um stattdessen die definierten Bedingungen und Ziele über mehrere Effekte hinweg zu teilen, kann `AllOf` verwendet werden, um sie zu einem einzigen Effekt zusammenzufassen.

<<< @/reference/26.2/src/client/java/com/example/docs/datagen/ExampleModEnchantmentGenerator.java#multiple_effects

Beachte, dass die zu verwendende Methode vom Typ des hinzugefügten Effekts abhängt. Zum Beispiel benötigt `EnchantmentValueEffect` stattdessen `AnyOf.valueEffects`. Unterschiedliche Effekt-Typen erfordern weiterhin zusätzliche Aufrufe von `withEffect`.

## Verzauberungstisch {#enchanting-table}

Obwohl wir das Gewicht der Verzauberung (oder die Wahrscheinlichkeit) in unserer Verzauberungsdefinition angegeben haben, wird es standardmäßig nicht im Zaubertisch angezeigt. Damit unsere Verzauberung von Dorfbewohnern gehandelt werden kann und im Zaubertisch erscheint, müssen wir sie zum Tag `non_treasure` hinzufügen.

Dazu können wir einen Tag Provider erstellen. Erstelle in dem Paket `datagen` eine Klasse, die von `FabricTagProvider<Enchantment>` erbt. Implementiere dann den Konstruktor mit `Registries.ENCHANTMENT` als Parameter `registryKey` für `super` und erstelle die Methode `addTags`.

<<< @/reference/26.2/src/client/java/com/example/docs/datagen/ExampleModEnchantmentTagProvider.java#provider

Wir können jetzt unsere Verzauberung zu `EnchantmentTags.NON_TREASURE` hinzufügen, indem wir den Builder aus der Methode `addTags` heraus aufrufen.

<<< @/reference/26.2/src/client/java/com/example/docs/datagen/ExampleModEnchantmentTagProvider.java#non_treasure_tag

## Flüche {#curses}

Flüche werden auch mit Tags implementiert. Wir können den Tag Provider aus dem [Abschnitt der Zaubertisch](#enchanting-table) verwenden.

Füge in der Methode `addTags` einfach deine Verzauberung zum Tag `CURSE` hinzu, um sie als Fluch zu kennzeichnen.

<<< @/reference/26.2/src/client/java/com/example/docs/datagen/ExampleModEnchantmentTagProvider.java#curse_tag
