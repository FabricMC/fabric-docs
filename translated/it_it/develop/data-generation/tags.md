---
title: Generazione di Tag
description: Una guida per configurare la generazione di tag con datagen.
authors:
  - CelDaemon
  - IMB11
  - skycatminepokie
  - Spinoscythe
authors-nogithub:
  - mcrafterzz
---

<!---->

:::info PREREQUISITI

Assicurati di aver prima completato il processo di [configurazione della datagen](./setup).

:::

## Configurazione {#setup}

Qui mostreremo come creare tag di `Item`, ma lo stesso principio si applica ad altre cose.

Fabric fornisce più fornitori ausiliari di tag incluso uno per gli oggetti; `FabricTagProvider.ItemTagProvider`. Useremo questa classe ausiliaria per questo esempio.

Puoi anche creare la tua classe che estenda `FabricTagProvider<T>`, dove `T` è il tipo di ciò per cui vuoi fornire un tag. Questo è il tuo **fornitore**.

Lascia che il tuo IDE compili il codice richiesto, poi sostituisci il parametro `resourceKey` del costruttore con la `ResourceKey` per il tuo tipo:

@[code lang=java transcludeWith=:::datagen-tags:provider](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModItemTagProvider.java)

::: tip

Ti servirà un fornitore diverso per ogni tipo di tag (per esempio un `FabricTagProvider<EntityType<?>>` e un `FabricTagProvider<Item>`).

:::

Per completare la configurazione, aggiungi questo fornitore alla tua `DataGeneratorEntrypoint` nel metodo `onInitializeDataGenerator`.

@[code lang=java transcludeWith=:::datagen-tags:register](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModDataGenerator.java)

## Creare un Tag {#creating-a-tag}

Ora che hai creato un fornitore, aggiungiamoci un tag. Anzitutto, crea una `TagKey<T>`:

@[code lang=java transcludeWith=:::datagen-tags:tag-key](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModItemTagProvider.java)

Poi, chiama `valueLookupBuilder` nel metodo `configure` del tuo fornitore. Da lì, puoi aggiungere oggetti individualmente, aggiungere altri tag, o fare in modo che questo tag ne sostituisca di preesistenti.

Se vuoi aggiungere un tag, usa `addOptionalTag`, poiché i contenuti del tag potrebbero non essere caricati durante la datagen. Se sei sicuro che il tag sia caricato, chiama `addTag`.

Per aggiungere un tag forzatamente ignorando il formato errato, usa `forceAddTag`.

@[code lang=java transcludeWith=:::datagen-tags:build](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModItemTagProvider.java)
