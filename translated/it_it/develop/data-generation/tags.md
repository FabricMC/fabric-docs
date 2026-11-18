---
title: Generazione di Tag
description: Una guida per configurare la generazione di tag con datagen.
authors:
  - cassiancc
  - CelDaemon
  - IMB11
  - MarcusElg
  - skycatminepokie
  - Spinoscythe
---

<!---->

:::info PREREQUISITI

Assicurati di aver prima completato il processo di [configurazione della datagen](./setup).

:::

## Configurazione {#setup}

Qui mostreremo come creare tag di `Item`, ma lo stesso principio si applica ad altre cose.

Fabric fornisce più fornitori ausiliari di tag incluso uno per gli oggetti; `FabricTagsProvider.ItemTagsProvider`. Useremo questa classe ausiliaria per questo esempio.

Puoi anche creare la tua classe che estenda `FabricTagsProvider<T>`, dove `T` è il tipo di ciò per cui vuoi fornire un tag. Questo è il tuo **fornitore**.

Lascia che il tuo IDE compili il codice richiesto, poi sostituisci il parametro `resourceKey` del costruttore con la `ResourceKey` per il tuo tipo:

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModItemTagProvider.java#datagen_tags_provider

::: tip

Ti servirà un fornitore diverso per ogni tipo di tag (per esempio un `FabricTagsProvider<EntityType<?>>` e un `FabricTagsProvider<Item>`).

:::

Per completare la configurazione, aggiungi questo fornitore alla tua `DataGeneratorEntrypoint` nel metodo `onInitializeDataGenerator`.

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModDataGenerator.java#datagen_tags_register

## Creare un Tag {#creating-a-tag}

Ora che hai creato un fornitore, aggiungiamoci un tag. Anzitutto, crea una `TagKey<T>`:

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModItemTagProvider.java#datagen_tags_tag_key

Poi, chiama `builder` nel metodo `configure` del tuo fornitore. Da lì, puoi aggiungere gli ID degli oggetti individualmente, aggiungere altri tag, o fare in modo che questo tag ne sostituisca di preesistenti.

Se vuoi aggiungere un tag, usa `addOptionalTag`, poiché i contenuti del tag potrebbero non essere caricati durante la datagen. Se sei sicuro che il tag sia caricato, chiama `addTag`.

Per aggiungere un tag forzatamente ignorando il formato errato, usa `forceAddTag`.

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModItemTagProvider.java#datagen_tags_build

## Tradurre i tag {#translating-tags}

I tag creati dalla tua mod dovrebbero essere accompagnati da traduzioni, cosicché altre mod - come le anteprime di ricette - possano mostrarne il nome correttamente. Se uno dei tuoi tag mancasse di traduzione, potresti leggere un avviso simile al seguente nei log:

> Dev warning - Untranslated Item Tags detected. Please translate your item tags so other mods such as recipe viewers can properly display your tag's name.

Seguiremo la [guida per la generazione di traduzioni](./translations) per aggiungere una traduzione per il nostro tag. Usando lo stesso costruttore di traduzioni, passiamo la `TagKey` e il nome visualizzato:

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModEnglishLangProvider.java#tag_name
