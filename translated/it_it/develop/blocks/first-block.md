---
title: Creare il Tuo Primo Blocco
description: Impara come creare il tuo primo blocco personalizzato in Minecraft.
authors:
  - Earthcomputer
  - IMB11
  - its-miroma
  - xEobardThawne
---

I blocchi sono i blocchi di costruzione di Minecraft (perdona il gioco di parole) - proprio come tutto il resto di Minecraft, sono memorizzati in registry.

## Preparare la Tua Classe dei Blocchi {#preparing-your-blocks-class}

Se hai già completato la pagina [Creare il Tuo Primo Oggetto](../items/first-item), questo processo ti sembrerà molto familiare - dovrai creare un metodo che registri il tuo blocco, e l'oggetto ad esso associato.

Dovresti mettere questo metodo in una classe chiamata `ModBlocks` (o qualsiasi altro nome).

Mojang fa qualcosa di simile con i suoi blocchi vanilla; informati riguardo alla classe `Blocks` per sapere come fanno loro.

@[code transcludeWith=:::1](@/reference/latest/src/main/java/com/example/docs/block/ModBlocks.java)

Proprio come per gli oggetti, dovrai assicurarti che la classe sia caricata, in modo che tutti gli attributi statici contenenti le istanze dei tuoi blocchi siano inizializzati.

Puoi fare questo creando un metodo fittizio `initialize`, che potrà essere richiamato nell'[initializer della tua mod](./getting-started/project-structure#entrypoints) per avviare l'inizializzazione statica.

:::info
Se non sai cos'è l'inizializzazione statica, essa è il processo di inizializzazione degli attributi statici in una classe. Questo viene fatto quando la classe viene caricata dalla JVM, ed è fatto prima che qualsiasi istanza della classe venga creata.
:::

```java
public class ModBlocks {
    // ...

    public static void initialize() {}
}
```

@[code transcludeWith=:::1](@/reference/latest/src/main/java/com/example/docs/block/FabricDocsReferenceBlocks.java)

## Creare e Registrare il Tuo Blocco {#creating-and-registering-your-block}

In maniera del tutto simile agli oggetti, i blocchi prendono la classe `AbstractBlock.Settings` nel costruttore. La classe indica proprietà specifiche del blocco, come i suoi effetti sonori e il livello di estrazione.

Non tratteremo tutte le opzioni qui—puoi vedere la classe da solo per capirne le varie opzioni, che dovrebbero essere chiaramente comprensibili.

Per questo esempio, creeremo un blocco semplice, con le proprietà della terra ma con un materiale diverso.

- Creiamo le nostre impostazioni del blocco in maniera simile a come abbiamo creato le impostazioni degli oggetti nel loro tutorial.
- Diciamo al metodo `register` di creare un'istanza di `Block` dalle impostazioni del blocco chiamando il costruttore di `Block`.

:::tip
Puoi anche usare `AbstractBlock.Settings.copy(AbstractBlock block)` per copiare le impostazioni di un blocco esistente, in questo caso avremmo potuto usare `Blocks.DIRT` per copiare le impostazioni della terra, ma per questo esempio useremo il costruttore.
:::

@[code transcludeWith=:::2](@/reference/latest/src/main/java/com/example/docs/block/ModBlocks.java)

Poiché il `BlockItem` viene creato e registrato in automatico, per aggiungerlo a un gruppo di oggetti devi usare il metodo `Block.asItem()` per ottenere l'istanza `BlockItem`.

### Aggiungere l'Oggetto del Tuo Blocco a un Gruppo di Oggetti {#adding-your-block-s-item-to-an-item-group}

Per creare l'oggetto del blocco in automatico, possiamo passare `true` al parametro `shouldRegisterItem` del metodo `register` che abbiamo creato nel passaggio precedente.

Per questo esempio, useremo un gruppo di oggetti personalizzato, che abbiamo creato nella pagina [Gruppi di Oggetti Personalizzati](../items/custom-item-groups).

@[code transcludeWith=:::6](@/reference/latest/src/main/java/com/example/docs/block/ModBlocks.java)

Dovresti mettere questo nella funzione `initialize()` della tua classe.

Dovresti ora notare che il tuo blocco è nell'inventario in creativa, e può essere posizionato nel mondo!

![Blocco nel mondo senza né modello né texture](/assets/develop/blocks/first_block_0.png)

Ci sono alcuni problemi tuttavia - il blocco non ha nome, non ha texture e non ha modello né per il blocco né per l'oggetto.

## Aggiungere Traduzioni del Blocco {#adding-block-translations}

Per aggiungere una traduzione, devi creare una chiave di traduzione nel tuo file di traduzioni - `assets/mod-id/lang/en_us.json`.

Minecraft userà questa traduzione nell'inventario in creativa e in altri posti in cui il nome del blocco viene mostrato, come nel feedback dei comandi.

```json
{
  "block.mod_id.condensed_dirt": "Condensed Dirt"
}
```

Per applicare le modifiche, puoi riavviare il gioco o costruire la tua mod e premere <kbd>F3</kbd>+<kbd>T</kbd> - e dovresti vedere che il blocco ha un nome nell'inventario in creative e in altri posti come nella schermata delle statistiche.

## Modelli e Texture {#models-and-textures}

Tutte le texture dei blocchi si trovano nella cartella `assets/mod-id/textures/block` - ti forniamo una texture di esempio del blocco di "Terra Condensata", che sei libero di usare.

<DownloadEntry visualURL="/assets/develop/blocks/first_block_1.png" downloadURL="/assets/develop/blocks/first_block_1_small.png">Texture</DownloadEntry>

Per fare in modo che la texture sia visibile nel gioco, devi creare un modello del blocco, presenti nel file `assets/mod-id/models/block/condensed_dirt.json` per quanto riguarda il blocco di "Terra Condensata". Per questo blocco useremo il tipo di modello `block/cube_all`.

@[code](@/reference/latest/src/main/generated/assets/fabric-docs-reference/models/block/condensed_dirt.json)

Perché il blocco sia mostrato nell'inventario, dovrai creare una [Descrizione del Modello dell'Oggetto](../items/first-item#creating-the-item-model-description) che punti al modello del tuo blocco. Per questo esempio la descrizione del modello dell'oggetto per il blocco "Terra Condensata" si può trovare in `assets/mod-id/items/condensed_dirt.json`.

@[code](@/reference/latest/src/main/generated/assets/fabric-docs-reference/items/condensed_dirt.json)

:::tip
Dovrai creare una descrizione del modello d'oggetto solo se hai registrato un `BlockItem` assieme al tuo blocco!
:::

Quando carichi il gioco, potresti notare che la texture è ancora mancante. Questo perché devi aggiungere la definizione degli stati del blocco.

## Creare la Definizione degli Stati del Blocco {#creating-the-block-state-definition}

La definizione degli stati del blocco è usata dal gioco per capire quale modello renderizzare in base allo stato corrente del blocco.

Per il blocco di esempio, che non ha stati complessi, basta una sola voce nella definizione.

Questo file si dovrebbe trovare nella cartella `assets/mod-id/blockstates`, e il suo nome dovrebbe corrispondere all'ID del blocco che hai usato quando l'hai registrato nella classe `ModBlocks`. Per esempio, se l'ID è `condensed_dirt`, il file dovrebbe chiamarsi `condensed_dirt.json`.

@[code](@/reference/latest/src/main/generated/assets/fabric-docs-reference/blockstates/condensed_dirt.json)

:::tip
Gli stati dei blocchi sono davvero complessi, ed è per questo che gli tratteremo successivamente in una [loro pagina a parte](./blockstates).
:::

Riavviando il gioco o ricaricando con <kbd>F3</kbd>+<kbd>T</kbd> per applicare le modifiche - dovresti poter vedere la texture del blocco nell'inventario e fisicamente nel mondo:

![Blocco nel mondo con texture e modello appropriati](/assets/develop/blocks/first_block_4.png)

## Aggiungere Drop al Blocco {#adding-block-drops}

Quando si rompe il blocco in sopravvivenza, potresti notare che il blocco non droppa - potresti volere questa funzionalità, ma per fare in modo che il blocco droppi come oggetto quando viene rotto devi implementarne la loot table - il file della loot table dovrebbe essere nella cartella `data/mod-id/loot_table/blocks/`.

:::info
Per comprendere le loot table nel profondo, fai riferimento alla pagina [Minecraft Wiki - Loot Tables](https://minecraft.wiki/w/Loot_table).
:::

@[code](@/reference/latest/src/main/resources/data/fabric-docs-reference/loot_tables/blocks/condensed_dirt.json)

Questa loot table fornisce un solo drop come oggetto del blocco quando viene rotto, o distrutto da un'esplosione.

## Consigliare un'Utensile per la Raccolta {#recommending-a-harvesting-tool}

Potresti anche volere che il tuo blocco sia ottenibile solo con un'utensile specifico - per esempio, più veloce da ottenere con una pala.

Tutti i tag degli utensili dovrebbero essere nella cartella `data/minecraft/tags/block/mineable/` - e il nome del file dipende dal tipo di utensile usato, uno tra i seguenti:

- `hoe.json`
- `axe.json`
- `pickaxe.json`
- `shovel.json`

I contenuti del file sono piuttosto semplici - è una lista di oggetti da aggiungere al tag.

Questo esempio aggiunge il blocco "Terra Condensata" al tag `shovel`.

@[code](@/reference/latest/src/main/resources/data/minecraft/tags/mineable/shovel.json)

Se desideri che un'utensile sia necessario per minare il blocco, dovrai aggiungere `.requiresTool()` alle impostazioni del tuo blocco, oltre che aggiungere il tag del livello di scavo appropriato.

## Livelli di Scavo {#mining-levels}

Similmente, il tag del livello di scavo si trova nella cartella `data/minecraft/tags/block/`, e segue il seguente formato:

- `needs_stone_tool.json` - Almeno utensili di pietra
- `needs_iron_tool.json` - Almeno utensili di ferro
- `needs_diamond_tool.json` - Almeno utensili di diamante

Il file ha lo stesso formato di quello per la raccolta - una lista di oggetti da aggiungere al tag.

## Note Aggiuntive {#extra-notes}

Se stai aggiungendo più blocchi alla tua mod, potresti voler usare la [Generazione di Dati](../data-generation/setup) per automatizzare il processo di creazione dei modelli di blocchi e oggetti, delle definizioni degli stati del blocco, e delle loot table.
