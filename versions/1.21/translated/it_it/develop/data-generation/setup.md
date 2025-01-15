---
title: Configurazione della Generazione di Dati
description: Una guida per configurare la Generazione di Dati con l'API di Fabric.
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

# Configurazione della Generazione di Dati {#data-generation-setup}

## Cos'è la Generazione di Dati? {#what-is-data-generation}

La generazione di dati (datagen) è un'API per generare programmaticamente ricette, progressi, tag, modelli di oggetti, file di lingua, loot table, e praticamente qualsiasi cosa basata su JSON.

## Attivare la Generazione di Dati {#enabling-data-generation}

### Durante la Creazione del Progetto {#enabling-data-generation-at-project-creation}

Il modo più semplice per attivare la datagen è durante la creazione del progetto. Attiva la casella "Enable Data Generation" mentre usi il [generatore di mod modello](https://fabricmc.net/develop/template).

![La casella "Data Generation" attiva nel generatore di mod modello](/assets/develop/data-generation/data_generation_setup_01.png)

:::tip
Se la datagen è attiva, dovresti avere una configurazione di esecuzione "Data Generation" e un'operazione Gradle `runDatagen`.
:::

### Manualmente {#manually-enabling-data-generation}

Anzitutto, dobbiamo attivare la datagen nel file `build.gradle`.

@[code lang=groovy transcludeWith=:::datagen-setup:configure](@/reference/build.gradle)

Poi ci serve una classe entrypoint. È qui che comincia la nostra datagen. Mettila da qualche parte nel package `client` - questo esempio la inserisce in `src/client/java/com/example/docs/datagen/FabricDocsReferenceDataGenerator.java`.

@[code lang=java transcludeWith=:::datagen-setup:generator](@/reference/1.21/src/client/java/com/example/docs/datagen/FabricDocsReferenceDataGenerator.java)

Infine, informiamo Fabric dell'entrypoint nel nostro `fabric.mod.json`:

```json
{
  // ...
  "entrypoints": {
    // ...
    "client": [
      // ...
    ],
    "fabric-datagen": [ // [!code ++]
      "com.exmaple.docs.datagen.FabricDocsReferenceDataGenerator" // [!code ++]
    ] // [!code ++]
  }
}
```

:::warning
Non dimenticare di aggiungere una virgola (`,`) dopo il blocco entrypoint precedente!
:::

Chiudi e riapri IntelliJ per creare una configurazione di esecuzione per la datagen.

## Creare un Pack {#creating-a-pack}

Nel metodo `onInitializeDataGenerator` del tuo entrypoint di datagen, dobbiamo creare un `Pack`. Dopo aggiungerai dei **fornitori**, che metteranno i dati generati in questo `Pack`.

@[code lang=java transcludeWith=:::datagen-setup:pack](@/reference/1.21/src/client/java/com/example/docs/datagen/FabricDocsReferenceDataGenerator.java)

## Eseguire la Generazione di Dati {#running-data-generation}

Per eseguire la datagen, usa la configurazione di esecuzione nel tuo IDE, o esegui `./gradlew runDatagen` nella console. I file generati saranno creati in `src/main/generated`.

## Prossimi Passi {#next-steps}

Ora che la datagen è configurata, dobbiamo aggiungerle dei **fornitori**. Questi sono ciò che genera i dati da aggiungere al tuo `Pack`. Le pagine successive mostrano come si fa questo.

- [Progressi](./advancements)
- [Loot Table](./loot-tables)
- [Ricette](./recipes)
- [Tag](./tags)
- [Traduzioni](./translations)
