---
title: Generazione di Loot Table
description: Una guida per configurare la generazione di loot table con datagen.
authors:
  - CelDaemon
  - JustinHuPrime
  - LunarTremor
  - MarcusElg
  - matthewperiut
  - skycatminepokie
  - Spinoscythe
authors-nogithub:
  - jmanc3
---

<!---->

:::info PREREQUISITI

Assicurati di aver prima completato il processo di [configurazione della datagen](./setup).

:::

Ti serviranno fornitori (classi) diversi per blocchi, bauli, ed entità. Ricorda di aggiungerli tutto al tuo pack nella tua `DataGeneratorEntrypoint` nel metodo `onInitializeDataGenerator`.

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModDataGenerator.java#datagen_loot_tables_register

## Loot Table Spiegate {#loot-tables-explained}

Le **loot table** definiscono cosa si ottiene dalla rottura di un blocco (esclusi i contenuti, per esempio per i bauli), uccisione di un'entità, o apertura di un contenitore appena generato. Ogni loot table ha dei **pool** fra cui gli oggetti sono selezionati. Le loot table hanno anche **funzioni**, che modificano il loot risultante in qualche modo.

Le loot pool hanno **voci**, **condizioni**, funzioni, **roll** e **roll bonus**. Le voci sono gruppi, sequenze, o possibilità di oggetti, o solo oggetti. Le condizioni sono cose per cui si testa nel mondo, come incantesimi su un'utensile o una probabilità casuale. Il numero minimo di voci scelte da una pool è detto roll, e qualsiasi cosa sopra a ciò è detta una roll bonus.

## Blocchi {#blocks}

Perché i blocchi droppino oggetti - inclusi se stessi - dobbiamo creare una loot table. Crea una classe che estenda `FabricBlockLootSubProvider`:

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModBlockLootTableProvider.java#datagen_loot_tables_block_provider

Assicurati di aggiungere questo fornitore al tuo pack!

Ci sono parecchi metodi ausiliari per aiutarti a costruire le tue loot table. Non li analizzeremo, per cui assicurati di controllarli nel tuo IDE.

Aggiungiamo alcuni drop nel metodo `generate`:

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModBlockLootTableProvider.java#datagen_loot_tables_block_drops

## Bauli {#chests}

Il loot dei bauli è un po' più complesso del loot dei blocchi. Crea una classe che estenda `SimpleFabricLootTableSubProvider` come nell'esempio sotto **e aggiungila al tuo pack**.

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModChestLootTableProvider.java#datagen_loot_tables_chest_provider

Ci servirà una `ResourceKey<LootTable>` per la nostra loot table. Mettiamola in una nuova classe chiamata `ModLootTables`. Assicurati che sia nel tuo insieme di sorgenti `main` se usi fonti suddivise.

<<< @/reference/latest/src/main/java/com/example/docs/ModLootTables.java#datagen_loot_tables_mod_loot_tables

Poi possiamo generare una loot table nel metodo `generate` del tuo fornitore.

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModChestLootTableProvider.java#datagen_loot_tables_chest_loot

## Risorse condizionali {#resource-conditions}

Per applicare una [condizione per risorse](../resource-conditions) a una loot table generata, chiama `withConditions` fornendo le condizioni desiderate, poi chiama un altro metodo del fornitore di loot table come `dropSelf`. Questo permetterà di generare la loot table alla quale sono applicate le condizioni:

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModBlockLootTableProvider.java#datagen_loot_tables_conditions
