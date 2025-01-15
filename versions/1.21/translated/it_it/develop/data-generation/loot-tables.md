---
title: Generazione di Loot Table
description: Una guida per configurare la generazione di loot table con datagen.
authors:
  - skycatminepokie
  - Spinoscythe
  - Alphagamer47
  - matthewperiut
  - JustinHuPrime
authors-nogithub:
  - mcrafterzz
  - jmanc3
---

# Generazione di Loot Table {#loot-table-generation}

:::info PREREQUISITI
Assicurati di aver prima completato il processo di [configurazione della datagen](./setup).
:::

Ti serviranno fornitori (classi) diversi per blocchi, bauli, ed entità. Ricorda di aggiungerli tutto al tuo pack nella tua `DataGeneratorEntrypoint` nel metodo `onInitializeDataGenerator`.

@[code lang=java transclude={32-33}](@/reference/1.21/src/client/java/com/example/docs/datagen/FabricDocsReferenceDataGenerator.java)

## Loot Table Spiegate {#loot-tables-explained}

Le **loot table** definiscono cosa si ottiene dalla rottura di un blocco (esclusi i contenuti, per esempio per i bauli), uccisione di un'entità, o apertura di un contenitore appena generato. Ogni loot table ha dei **pool** fra cui gli oggetti sono selezionati. Le loot table hanno anche **funzioni**, che modificano il loot risultante in qualche modo.

Le loot pool hanno **voci**, **condizioni**, funzioni, **roll** e **roll bonus**. Le voci sono gruppi, sequenze, o possibilità di oggetti, o solo oggetti. Le condizioni sono cose per cui si testa nel mondo, come incantesimi su uno strumento o una probabilità casuale. Il numero minimo di voci scelte da una pool è detto roll, e qualsiasi cosa sopra a ciò è detta una roll bonus.

## Blocchi {#blocks}

Perché i blocchi droppino oggetti - inclusi se stessi - dobbiamo creare una loot table. Crea una classe che `extends FabricBlockLootTableProvider`:

@[code lang=java transcludeWith=:::datagen-loot-tables:block-provider](@/reference/1.21/src/client/java/com/example/docs/datagen/FabricDocsReferenceBlockLootTableProvider.java)

Assicurati di aggiungere questo fornitore al tuo pack!

Ci sono parecchi metodi ausiliari per aiutarti a costruire le tue loot table. Non li analizzeremo, per cui assicurati di controllarli nel tuo IDE.

Aggiungiamo alcuni drop nel metodo `generate`:

@[code lang=java transcludeWith=:::datagen-loot-tables:block-drops](@/reference/1.21/src/client/java/com/example/docs/datagen/FabricDocsReferenceBlockLootTableProvider.java)

## Bauli {#chests}

Il loot dei bauli è un po' più complesso del loot dei blocchi. Crea una classe che `extends SimpleFabricLootTableProvider` come nell'esempio sotto **e aggiungila al tuo pack**.

@[code lang=java transcludeWith=:::datagen-loot-tables:chest-provider](@/reference/1.21/src/client/java/com/example/docs/datagen/FabricDocsReferenceChestLootTableProvider.java)

Ci servirà una `RegistryKey<LootTable>` per la nostra loot table. Mettiamola in una nuova classe chiamata `ModLootTables`. Assicurati che sia nel tuo insieme di sorgenti `main` se usi fonti suddivise.

@[code lang=java transcludeWith=:::datagen-loot-tables:mod-loot-tables](@/reference/1.21/src/main/java/com/example/docs/ModLootTables.java)

Poi possiamo generare una loot table nel metodo `generate` del tuo fornitore.

@[code lang=java transcludeWith=:::datagen-loot-tables:chest-loot](@/reference/1.21/src/client/java/com/example/docs/datagen/FabricDocsReferenceChestLootTableProvider.java)
