---
title: Generazione di Ricette
description: Una guida per configurare la generazione di ricette con datagen.
authors:
  - skycatminepokie
  - Spinoscythe
authors-nogithub:
  - mcrafterzz
  - jmanc3
---

:::info PREREQUISITI
Assicurati di aver prima completato il processo di [configurazione della datagen](./setup).
:::

## Configurazione {#setup}

Anzitutto, ci serve il nostro fornitore. Crea una classe che `extends FabricRecipeProvider`. Tutta la nostra generazione di ricette avverrà nel metodo `generate` del nostro fornitore.

@[code lang=java transcludeWith=:::datagen-recipes:provider](@/reference/1.21/src/client/java/com/example/docs/datagen/ExampleModRecipeProvider.java)

Per completare la configurazione, aggiungi questo fornitore alla tua `DataGeneratorEntrypoint` nel metodo `onInitializeDataGenerator`.

@[code lang=java transclude={30-30}](@/reference/1.21/src/client/java/com/example/docs/datagen/ExampleModDataGenerator.java)

## Ricette Senza Forma {#shapeless-recipes}

Le ricette senza forma non sono complesse. Basta aggiungerle al metodo `generate` nel tuo fornitore:

@[code lang=java transcludeWith=:::datagen-recipes:shapeless](@/reference/1.21/src/client/java/com/example/docs/datagen/ExampleModRecipeProvider.java)

## Ricette Con Forma {#shaped-recipes}

Per una ricetta con forma, dovrai definire la forma con una `String`, poi definire ciò che ogni `char` della `String` rappresenta.

@[code lang=java transcludeWith=:::datagen-recipes:shaped](@/reference/1.21/src/client/java/com/example/docs/datagen/ExampleModRecipeProvider.java)

:::tip
Ci sono tanti metodi ausiliari per la creazione di ricette tipiche. Controlla ciò che `RecipeProvider` ha da offrire! Usa `Alt + 7` in IntelliJ per aprire la struttura di una classe, inclusa una lista dei metodi.
:::

## Altre Ricette {#other-recipes}

Altre ricette funzionano in maniera simile, ma richiedono alcuni parametri aggiuntivi. Per esempio, le ricette di fusioni devono includere la quantità di esperienza da assegnare.

@[code lang=java transcludeWith=:::datagen-recipes:other](@/reference/1.21/src/client/java/com/example/docs/datagen/ExampleModRecipeProvider.java)

## Tipi di Ricette Personalizzati {#custom-recipe-types}
