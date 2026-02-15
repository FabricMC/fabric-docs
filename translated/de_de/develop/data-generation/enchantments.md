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

@[code transcludeWith=:::key-helper](@/reference/latest/src/main/java/com/example/docs/enchantment/ModEnchantments.java)

Nutze diese Methode, um einen `ResourceKey` für deine Verzauberung zu erstellen.

@[code transcludeWith=:::register-enchantment](@/reference/latest/src/main/java/com/example/docs/enchantment/ModEnchantments.java)

Jetzt sind wir bereit, den Generator hinzuzufügen. Erstelle im Paket datagen eine Klasse, die von `FabricDynamicRegistryProvider` erbt. Füge in diese neu erstellte Klasse einen Konstruktor hinzu, der mit `super` übereinstimmt, und implementiere die Methoden `configure` und `getName`.

@[code transcludeWith=:::provider](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModEnchantmentGenerator.java)

Füge dann eine Hilfsmethode `register` zu der neu erstellen Klasse hinzu.

@[code transcludeWith=:::register-helper](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModEnchantmentGenerator.java)

Füge jetzt die Methode `bootstrap` hinzu. Hier werden wir die Verzauberungen registrieren, die wir zum Spiel hinzufügen wollen.

@[code transcludeWith=:::bootstrap](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModEnchantmentGenerator.java)

Überschreibe in deinem `DataGeneratorEntrypoint` die Methode `buildRegistry` und registriere unsere Methode bootstrap.

@[code transcludeWith=:::datagen-enchantments:bootstrap](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModDataGenerator.java)

Stelle schließlich sicher, dass dein neuer Generator innerhalb der Methode `onInitializeDataGenerator` registriert ist.

@[code transcludeWith=:::datagen-enchantments:register](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModDataGenerator.java)

## Die Verzauberung erstellen {#creating-the-enchantment}

Um die Definition für unsere benutzerdefinierte Verzauberung zu erstellen, verwenden wir die Methode `register` in unserer Generator-Klasse.

Registriere deine Verzauberung in der Methode `bootstrap` des Generators unter Verwendung der in `ModEnchantments` registrierten Verzauberung.

In diesem Beispiel verwenden wir den in [Benutzerdefinierte Verzauberungseffekte](../items/custom-enchantment-effects) erstellten Verzauberungseffekt, aber du kannst auch die [Vanilla Verzauberungseffekte](https://minecraft.wiki/w/Enchantment_definition#Effect_components) verwenden.

@[code transcludeWith=:::register-enchantment](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModEnchantmentGenerator.java)

Führen nun einfach die Datengenerierung aus, und deine neue Verzauberung wird im Spiel verfügbar sein!

## Effektbedingungen {#effect-conditions}

Die meisten Verzauberungseffekte sind bedingte Effekte. Beim Hinzufügen dieser Effekte ist es möglich, Bedingungen an den Aufruf von `withEffect` zu übergeben.

::: info

Eine Übersicht über die verfügbaren Zustandstypen und deren Verwendung findest du in der Klasse `Enchantments` (https://mcsrc.dev/#1/1.21.11_unobfuscated/net/minecraft/world/item/enchantment/Enchantments#L126).

:::

@[code transcludeWith=:::effect-conditions](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModEnchantmentGenerator.java)

## Mehrere Effekte {#multiple-effects}

`withEffect` kann verkettet werden, um einer einzelnen Verzauberung mehrere Verzauberungseffekte hinzuzufügen. Bei dieser Methode musst du jedoch die Effektbedingungen für jeden Effekt angeben.

Um stattdessen die definierten Bedingungen und Ziele über mehrere Effekte hinweg zu teilen, kann `AllOf` verwendet werden, um sie zu einem einzigen Effekt zusammenzufassen.

@[code transcludeWith=:::multiple-effects](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModEnchantmentGenerator.java)

Beachte, dass die zu verwendende Methode vom Typ des hinzugefügten Effekts abhängt. Zum Beispiel benötigt `EnchantmentValueEffect` stattdessen `AnyOf.valueEffects`. Unterschiedliche Effekt-Typen erfordern weiterhin zusätzliche Aufrufe von `withEffect`.

## Verzauberungstisch {#enchanting-table}

Obwohl wir das Gewicht der Verzauberung (oder die Wahrscheinlichkeit) in unserer Verzauberungsdefinition angegeben haben, wird es standardmäßig nicht im Zaubertisch angezeigt. Damit unsere Verzauberung von Dorfbewohnern gehandelt werden kann und im Zaubertisch erscheint, müssen wir sie zum Tag `non_treasure` hinzufügen.

Dazu können wir einen Tag Provider erstellen. Erstelle in dem Paket `datagen` eine Klasse, die von `FabricTagProvider<Enchantment>` erbt. Implementiere dann den Konstruktor mit `Registries.ENCHANTMENT` als Parameter `registryKey` für `super` und erstelle die Methode `addTags`.

@[code transcludeWith=:::provider](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModEnchantmentTagProvider.java)

Wir können jetzt unsere Verzauberung zu `EnchantmentTags.NON_TREASURE` hinzufügen, indem wir den Builder aus der Methode `addTags` heraus aufrufen.

@[code transcludeWith=:::non-treasure-tag](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModEnchantmentTagProvider.java)

## Flüche {#curses}

Flüche werden auch mit Tags implementiert. Wir können den Tag Provider aus dem [Abschnitt der Zaubertisch](#enchanting-table) verwenden.

Füge in der Methode `addTags` einfach deine Verzauberung zum Tag `CURSE` hinzu, um sie als Fluch zu kennzeichnen.

@[code transcludeWith=:::curse-tag](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModEnchantmentTagProvider.java)
