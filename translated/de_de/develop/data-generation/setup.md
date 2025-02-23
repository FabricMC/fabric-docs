---
title: Einrichtung der Datengenerierung
description: Ein Leitfaden zur Einrichtung der Datengenerierung mit der Fabric API.
authors:
  - skycatminepokie
  - modmuss50
  - earthcomputer
  - shnupbups
  - arkosammy12
  - haykam821
  - matthewperiut
  - SolidBlock-cn
  - Jab125
authors-nogithub:
  - jmanc3
  - macrafterzz
---

## Was ist Datengenerierung? {#what-is-data-generation}

Datengenerierung (oder datagen) ist eine API für das programmatische generieren von Rezepten, Fortschritten, Tags, Itemmodellen, Sprachdateien, Beutetabllen und im Grunde allem auf JSON Basis.

## Aktivieren der Datengenerierung {#enabling-data-generation}

### Bei der Projekterstellung {#enabling-data-generation-at-project-creation}

Der leichteste Weg die Datengenerierung zu aktivieren ist bei der Projekterstellung. Aktiviere das Kontrollkästchen "Enable Data Generation", wenn du den [Vorlagengenerator](https://fabricmc.net/develop/template/) verwendest.

![Das aktivierte "Data Generation" Kontrollkästchen beim Vorlagengenerator](/assets/develop/data-generation/data_generation_setup_01.png)

:::tip
Wenn die Datengenerierung aktiviert ist, solltest du eine "Data Generation" Laufkonfiguration udn einen `runDatagen` Gradle Task haben.
:::

### Manuell {#manually-enabling-data-generation}

Zuerst müssen wir die Datengenerierung in der `build.Gradle`-Datei aktivieren.

@[code lang=groovy transcludeWith=:::datagen-setup:configure](@/reference/build.gradle)

Als nächstes, benötigen wir eine Klasse für den Einstiegspunkt. Dies ist dort, wo unsere Datengenerierung startet. Platziere diese irgendwo in dem `client` Packet - dieses Beispiel platziert diese in `src/client/java/com/example/docs/datagen/FabricDocsReferenceDataGenerator.java`.

@[code lang=java transcludeWith=:::datagen-setup:generator](@/reference/latest/src/client/java/com/example/docs/datagen/FabricDocsReferenceDataGenerator.java)

Schließlich müsen wir Fabric in der `fabric.mod.json` über den Einstiegspunkt informieren:

```json
{
  // ...
  "entrypoints": {
    // ...
    "client": [
      // ...
    ],
    "fabric-datagen": [ // [!code ++]
      "com.example.docs.datagen.FabricDocsReferenceDataGenerator" // [!code ++]
    ] // [!code ++]
  }
}
```

:::warning
Vergesse nicht, ein Komma (`,`) nach dem vorherigen Einstiegspunkt-Block hinzuzufügen!
:::

Schließe IntelliJ und öffne es erneut, um eine Laufkonfiguration für die Datengenerierung zu erstellen.

## Ein Packet erstellen {#creating-a-pack}

Innerhalb der Methode `onInitializeDataGenerator` deines Einstiegspunktes für die Datengenerierung m+ssen wir ein `Pack` erstellen. Später fügst du **Provider** hinzu, die generierte Daten in dieses `Pack` einfügen.

@[code lang=java transcludeWith=:::datagen-setup:pack](@/reference/latest/src/client/java/com/example/docs/datagen/FabricDocsReferenceDataGenerator.java)

## Ausführen des Datengenerators {#running-data-generation}

Um die Datengenerierung auszuführen, nutze die Laufkonfiguration in deiner IDE oder führe in der Konsole `./gradlew runDatagen` aus. Die generierten Dateien werden in `src/main/generated` erstellt.

## Nächste Schritte {#next-steps}

Nachdem der Datengenerator nun eingerichtet ist, müssen wir **Provider** hinzufügen. Diese erzeugen die Daten, die du zu deinem `Pack` hinzufügst. Auf den folgenden Seiten wird beschrieben, wie du dies tun kannst.

- [Fortschritte](./advancements)
- [Beutetabellen](./loot-tables)
- [Rezepte](./recipes)
- [Tags](./tags)
- [Übersetzungen](./translations)
