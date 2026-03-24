---
title: Einrichtung der Datengenerierung
description: Ein Leitfaden zur Einrichtung der Datengenerierung mit der Fabric API.
authors:
  - Treeways
  - Earthcomputer
  - haykam821
  - Jab125
  - matthewperiut
  - modmuss50
  - Shnupbups
  - skycatminepokie
  - SolidBlock-cn
authors-nogithub:
  - jmanc3
  - mcrafterzz
---

## Was ist Datengenerierung? {#what-is-data-generation}

Datengenerierung (oder datagen) ist eine API für das programmatische generieren von Rezepten, Fortschritten, Tags, Itemmodellen, Sprachdateien, Beutetabllen und im Grunde allem auf JSON Basis.

## Aktivieren der Datengenerierung {#enabling-data-generation}

### Bei der Projekterstellung {#enabling-data-generation-at-project-creation}

Der leichteste Weg die Datengenerierung zu aktivieren ist bei der Projekterstellung. Aktiviere das Kontrollkästchen "Enable Data Generation", wenn du den [Vorlagengenerator](https://fabricmc.net/develop/template/) verwendest.

![Das aktivierte "Data Generation" Kontrollkästchen beim Vorlagengenerator](/assets/develop/data-generation/data_generation_setup_01.png)

::: tip

Wenn der Datengenerator aktiviert ist, solltest du eine "Data Generation" Laufkonfiguration udn einen `runDatagen` Gradle Task haben.

:::

### Manuell {#manually-enabling-data-generation}

Zuerst müssen wir den Datengenerator in der Datei `build.gradle` aktivieren.

@[code transcludeWith=:::datagen-setup:configure](@/reference/build.gradle)

Als nächstes, benötigen wir eine Klasse für den Einstiegspunkt. Dies ist dort, wo unser Datengenerator startet. Platziere diese irgendwo in dem `client` Packet - dieses Beispiel platziert diese in `src/client/java/com/example/docs/datagen/ExampleModDataGenerator.java`.

@[code lang=java transcludeWith=:::datagen-setup:generator](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModDataGenerator.java)

Schließlich müsen wir Fabric in der `fabric.mod.json` über den Einstiegspunkt informieren:

<!-- prettier-ignore -->

```json
{
  // ...
  "entrypoints": {
    // ...
    "client": [
      // ...
    ],
    "fabric-datagen": [ // [!code ++]
      "com.example.docs.datagen.ExampleModDataGenerator" // [!code ++]
    ] // [!code ++]
  }
}
```

::: warning

Vergesse nicht, nach dem vorherigen Einstiegspunkt-Block ein Komma (`,`) hinzuzufügen!

:::

Schließe IntelliJ und öffne es erneut, um eine Laufkonfiguration für den Datengenerator zu erstellen.

## Ein Pack erstellen {#creating-a-pack}

Innerhalb der Methode `onInitializeDataGenerator` deines Einstiegspunktes für die Datengenerierung müssen wir ein `Pack` erstellen. Später fügst du **Provider** hinzu, die generierte Daten in dieses `Pack` einfügen.

@[code lang=java transcludeWith=:::datagen-setup:pack](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModDataGenerator.java)

## Ausführen der Datengenerierung {#running-data-generation}

Um den Datengenerator auszuführen, nutze die Laufkonfiguration in deiner IDE oder führe in der Konsole `./gradlew runDatagen` aus. Die generierten Dateien werden in `src/main/generated` erstellt.

## Nächste Schritte {#next-steps}

Nachdem der Datengenerator nun eingerichtet ist, müssen wir **Provider** hinzufügen. Diese erzeugen die Daten, die du zu deinem `Pack` hinzufügst. Auf den folgenden Seiten wird beschrieben, wie du dies tun kannst.

- [Fortschritte](./advancements)
- [Beutetabellen](./loot-tables)
- [Rezepte](./recipes)
- [Tags](./tags)
- [Übersetzungen](./translations)
- [Blockmodelle](./block-models)
- [Itemmodelle](./item-models)
