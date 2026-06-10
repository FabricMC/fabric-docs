---
title: Ressourcenbedingungen
description: Ein Leitfaden, wie du das Laden der Daten deines Mods bedingt zulassen kannst.
authors:
  - cassiancc
resources:
  https://github.com/FabricMC/fabric-api/blob/26.1.2/fabric-data-generation-api-v1/src/testmod/java/net/fabricmc/fabric/test/datagen/DataGeneratorTestEntrypoint.java: Fabric API's Datengenerierung Test Mod
---

Bei der Entwicklung von Integrationen mit anderen Mods ist es häufig erforderlich, festzulegen, wann die Ressourcen des eigenen Mods geladen werden sollen. Aus diesem Grund bietet die Fabric-API Ressourcenbedingungen an.

Standardmäßig kann diese API mit Rezepten, Fortschritten, Beutetabellen, Prädikaten und Itemmodifikatoren verwendet werden.

Ressourcenbedingungen können entweder über die [Datengenerierung](./data-generation/setup) oder über manuelles Schreiben von JSON hinzugefügt werden. Weitere Informationen zum Hinzufügen von Ressourcenbedingungen über die Datengenerierung findest du in der Dokumentation zur Datengenerierung.

Die Ladebedingungen werden an den Anfang einer JSON-Datei eingefügt.

:::details Ein Rezept mit einer Bedingung, die dafür sorgt, dass es nur geladen wird, wenn ein Tag gefüllt ist.

<<< @/reference/latest/src/main/generated/data/example-mod/recipe/sand.json

:::

## Eingebaute Bedingungen {#built-in}

Die Fabric-API bietet neun integrierte Bedingungen, die du in deinem Mod verwenden kannst.

### Operatoren {#operators}

Dies sind die gewöhnlichen Booleschen Operatoren.

#### Wahr {#true}

Ist immer erfolgreich:

<<< @/reference/latest/src/main/generated/reports/example-mod/resource_condition_examples/true.json

#### Nicht {#not}

Kehrt die in `value` angegebene Ladebedingung um. Beispielsweise schlägt Folgendes fehl:

<<< @/reference/latest/src/main/generated/reports/example-mod/resource_condition_examples/not.json

#### Oder {#or}

Erfolgreich, wenn mindestens eine der Bedingungen in `values` erfüllt ist. Beispielsweise ist Folgendes erfolgreich:

<<< @/reference/latest/src/main/generated/reports/example-mod/resource_condition_examples/or.json

#### Und {#and}

Erfolgreich, wenn alle Bedingungen in `values` erfüllt sind. Beispielsweise schlägt Folgendes fehl:

<<< @/reference/latest/src/main/generated/reports/example-mod/resource_condition_examples/and.json

### Alle Mods geladen {#all-mods-loaded}

Erfolgreich, wenn alle Mods in `values` geladen sind. Beispielsweise ist das Folgende nur erfolgreich, wenn sowohl `example-mod` als auch `another-mod` geladen sind:

<<< @/reference/latest/src/main/generated/reports/example-mod/resource_condition_examples/all_mods_loaded.json

### Irgendwelche Mods geladen {#any-mods-loaded}

Erfolgreich, wenn mindestens einer der Mods in `values` geladen ist. Beispielsweise ist Folgendes erfolgreich, wenn entweder `example-mod` oder `another-mod` oder beide geladen sind:

<<< @/reference/latest/src/main/generated/reports/example-mod/resource_condition_examples/any_mods_loaded.json

### Tags gefüllt {#tags-populated}

Erfolgreich, wenn die angegebene `registry` alle Tags in `values` enthält. Beispielsweise ist Folgendes erfolgreich, wenn das Item-Tag `example-mod:smelly_items` geladen ist:

<<< @/reference/latest/src/main/generated/reports/example-mod/resource_condition_examples/tags_populated.json

### Aktivierte Funktionen {#features-enabled}

Ist erfolgreich, wenn alle [Funktions-Flags](https://minecraft.wiki/w/Experiments#Java_Edition) in `features` aktiviert sind. Beispielsweise ist Folgendes erfolgreich, wenn sowohl `minecraft:vanilla` als auch `minecraft:redstone_experiments` aktiviert sind:

<<< @/reference/latest/src/main/generated/reports/example-mod/resource_condition_examples/features_enabled.json

### Die Registry enthält {#registry-contains}

Ist erfolgreich, wenn die Registry alle Bezeichner in `values` enthält. Beispielsweise ist Folgendes erfolgreich, wenn `minecraft:cobblestone` in der Registry vorhanden ist:

<<< @/reference/latest/src/main/generated/reports/example-mod/resource_condition_examples/registry_contains.json

## Benutzerdefinierte Bedingungen {#custom-conditions}

:::info VORAUSSETZUNGEN

Bevor du eine benutzerdefinierte Ressourcenbedingung festlegst, musst du zunächst verstehen, [wie man einen Codec erstellt](./codecs).

:::

Die Fabric-API bietet zudem die Flexibilität, eigene Ressourcenbedingungen zu definieren.

Um dies zu veranschaulichen, werden wir eine Bedingung erstellen, die das aktuelle Datum überprüft. Dies könnte für besonderes Verhalten an Feiertagen wie Halloween oder Aprilscherze genutzt werden.

### Deine Bedingungen vorbereiten {#preparing-your-condition}

Der Einfachheit halber erstellen wir eine Hilfsmethode, die deine Ressourcenbedingung anhand eines Namens und eines [`MapCodec`](./codecs#mapcodec) instanziiert. Du solltest diese Methode in eine Klasse mit dem Namen `ModResourceConditions` (oder wie auch immer du sie nennen willst) einfügen.

::: tip

Fabric macht das mit seinen eingebauten Bedingungen genauso; in der Klasse `DefaultResourceConditionTypes` kannst du dir dies in der Aktion ansehen.

:::

<<< @/reference/latest/src/main/java/com/example/docs/conditions/ModResourceConditions.java#create

### Eine Bedingungen erstellen {#creating-your-condition}

Eine Ressourcenbedingung besteht aus drei Teilen:

- Einen Konstruktor, der Werte akzeptiert.
- Ein `MapCodec`, um diese Werte zu serialisieren.
- Eine Methode `test`, die anhand der Werte ermittelt, ob die Bedingung erfüllt sein sollte.

Wir werden eine neue Klasse für die Ressourcenbedingung mit dem Namen `DateMatchesResourceCondition` erstellen. Erstelle zuerst einen neuen `record`, der einen `int`-Wert für den Monat und einen `int`-Wert für den Tag akzeptiert:

<<< @/reference/latest/src/main/java/com/example/docs/conditions/DateMatchesResourceCondition.java#record

Füge als Nächstes einen `MapCodec` hinzu, der die vom Konstruktor akzeptierten Werte widerspiegelt:

<<< @/reference/latest/src/main/java/com/example/docs/conditions/DateMatchesResourceCondition.java#codec

:::details Was ist `validate`?

Dieser Codec verwendet die Methode `.validate`, um sicherzustellen, dass das angegebene Datum existieren kann, wobei er die Logik einer ebenfalls `validate` genannten Hilfsmethode verwendet:

<<< @/reference/latest/src/main/java/com/example/docs/conditions/DateMatchesResourceCondition.java#validate

Dies ist nur für genau dieses Beispiel relevant.

:::

Als Nächstes fügen wir eine Methode `test` hinzu, die das aktuelle Datum überprüft. Dieses Beispiel basiert auf der Logik aus Vanilla selbst, in `SpecialDates`.

<<< @/reference/latest/src/main/java/com/example/docs/conditions/DateMatchesResourceCondition.java#test

### Deine Bedingung registrieren {#registering-your-condition}

Zurück in `ModResourceConditions` können wir unsere Ressourcenbedingung jetzt registrieren:

<<< @/reference/latest/src/main/java/com/example/docs/conditions/ModResourceConditions.java#register

Auf diese Bedingungsart kann dann auch von `DateMatchesResourceCondition` aus verwiesen werden:

<<< @/reference/latest/src/main/java/com/example/docs/conditions/DateMatchesResourceCondition.java#type

Stelle sicher `ModResourceConditions.register` in deinem [Mod-Initialisierer](./getting-started/project-structure#entrypoints) aufzurufen:

<<< @/reference/latest/src/main/java/com/example/docs/conditions/ExampleModResourceConditions.java#init

### Deine Bedingung verwenden {#using-your-condition}

Jetzt haben wir eine Bedingung, die erfüllt ist, wenn das Systemdatum mit dem in der Ressourcenbedingung angegebenen Datum übereinstimmt. Diese Bedingung wird beispielsweise nur zu Aprilscherzen erfüllt:

<<< @/reference/latest/src/main/generated/reports/example-mod/resource_condition_examples/date_matches.json
