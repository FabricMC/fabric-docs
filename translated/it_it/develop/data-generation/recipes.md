---
title: Generazione di Ricette
description: Una guida per configurare la generazione di ricette con datagen.
authors:
  - CelDaemon
  - MarcusElg
  - skycatminepokie
  - Spinoscythe
authors-nogithub:
  - jmanc3
---

<!---->

:::info PREREQUISITI

Assicurati di aver prima completato il processo di [configurazione della datagen](./setup).

:::

## Configurazione {#setup}

Anzitutto, ci serve il nostro fornitore. Crea una classe che estenda `FabricRecipeProvider`. Tutta la nostra generazione di ricette avverrà nel metodo `buildRecipes` del nostro fornitore.

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModRecipeProvider.java#datagen_recipes_provider

Per completare la configurazione, aggiungi questo fornitore alla tua `DataGeneratorEntrypoint` nel metodo `onInitializeDataGenerator`.

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModDataGenerator.java#datagen_recipes_register

## Ricette Senza Forma {#shapeless-recipes}

Le ricette senza forma non sono complesse. Basta aggiungerle al metodo `buildRecipes` nel tuo fornitore:

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModRecipeProvider.java#datagen_recipes_shapeless

### Ricette coloranti {#dye-recipes}

Le ricette coloranti permettono di ricolorare oggetti nell'inventario.

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModRecipeProvider.java#datagen_recipes_dye

## Ricette Con Forma {#shaped-recipes}

Per una ricetta con forma, dovrai definire la forma con una `String`, poi definire ciò che ogni `char` della `String` rappresenta.

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModRecipeProvider.java#datagen_recipes_shaped

::: tip

Ci sono tanti metodi ausiliari per la creazione di ricette tipiche. Controlla ciò che `RecipeProvider` ha da offrire! Usa <kbd>Alt</kbd>+<kbd>7</kbd> in IntelliJ per aprire la struttura di una classe, inclusa una lista dei metodi.

:::

## Altre Ricette {#other-recipes}

Altre ricette funzionano in maniera simile, ma richiedono alcuni parametri aggiuntivi. Per esempio, le ricette di fusioni devono includere la quantità di esperienza da assegnare.

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModRecipeProvider.java#datagen_recipes_smelting

L'affumicatura funziona un po' diversamente, poiché non usa lo stesso generatore di ricette degli altri blocchi da fusione.

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModRecipeProvider.java#datagen_recipes_smoking

## Risorse condizionali {#resource-conditions}

Per applicare una [condizione per risorse](../resource-conditions) a una ricetta generata, racchiudi l'output in `withConditions` e fornisci le condizioni desiderate. Questo permetterà di generare la ricetta e un progresso ai quali sono applicate le condizioni:

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModRecipeProvider.java#datagen_recipes_conditions
