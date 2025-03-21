---
title: Stati dei Blocchi
description: Impara perché gli stati dei blocchi sono ottimi per aggiungere funzionalità visive ai tuoi blocchi.
authors:
  - IMB11
---

Uno stato di un blocco è un dato relativo a un singolo blocco nel mondo di Minecraft che contiene informazioni riguardanti il blocco sotto forma di proprietà - ecco alcuni esempi di proprietà che in vanilla sono memorizzate come stati:

- Rotazione: Usato principalmente per i tronchi e per altri blocchi naturali.
- Attivo: Fondamentale nei componenti della redstone, e blocchi come la fornace e l'affumicatore.
- Età: Usato per colture, piante, arboscelli, alghe...

Probabilmente hai capito perché sono così utili - per evitare di immagazzinare dati NBT in un blocco-entità, riducendo dunque le dimensioni del mondo e migliorando i TPS!

Le definizioni degli stati dei blocchi si trovano nella cartella `assets/mod-id/blockstates`.

## Esempio: Pilastro {#pillar-block}

<!-- Note: This example could be used for a custom recipe types guide, a condensor machine block with a custom "Condensing" recipe? -->

Minecraft ha già delle classi che permettono di creare velocemente alcuni tipi di blocco - questo esempio mostra la creazione di un blocco con la proprietà `asse`, con un blocco "Tronco di Quercia Condensato".

La classe vanilla `PillarBlock` permette di piazzare il blocco lungo gli assi X, Y o Z.

@[code transcludeWith=:::3](@/reference/latest/src/main/java/com/example/docs/block/ModBlocks.java)

I pilastri hanno due texture diverse, superiore e laterale - e usano il modello `block/cube_column`.

Ovviamente, come per tutte le altre texture dei blocchi, i file si trovano nella cartella `assets/mod-id/textures/blocks`

<DownloadEntry visualURL="/assets/develop/blocks/blockstates_0_large.png" downloadURL="/assets/develop/blocks/condensed_oak_log_textures.zip">le Texture</DownloadEntry>

Dato che un pilastro ha due posizioni, orizzontale e verticale, dobbiamo creare due file di modelli separati:

- 'condensed_oak_log_horizontal.json`che estende il modello`block/cube_column_horizontal\\\`.
- `condensed_oak_log.json` che estende il modello `block/cube_column`.

Un esempio di come deve essere il file `condensed_oak_log_horizontal.json`:

@[code](@/reference/latest/src/main/generated/assets/fabric-docs-reference/models/block/condensed_oak_log_horizontal.json)

::: info
Remember, blockstate files can be found in the `assets/mod-id/blockstates` folder, the name of the blockstate file should match the block ID used when registering your block in the `ModBlocks` class. For instance, if the block ID is `condensed_oak_log`, the file should be named `condensed_oak_log.json`.

Se vuoi vedere tutti i modificatori disponibili nel file degli stati, leggi la pagina [Minecraft Wiki - Models (Block States)](https://minecraft.wiki/w/Tutorials/Models#Block_states).
:::

Ora dobbiamo creare un file per lo stato. Il file dello stato è dove avviene la magia—i pilastri hanno tre assi e quindi useremo modelli specifici per i seguenti casi:

- `axis=x` - Quando il blocco è piazzato sull'asse X, ne ruoteremo il modello in modo che guardi verso la parte positiva delle X.
- `axis=y` - Quando il blocco è piazzato sull'asse Y, useremo il modello verticale normale.
- `axis=z` - Quando il blocco è piazzato sull'asse Z, ne ruoteremo il modello in modo che guardi verso la parte positiva delle X.

@[code](@/reference/latest/src/main/generated/assets/fabric-docs-reference/blockstates/condensed_oak_log.json)

Come sempre, dovrai creare una traduzione per il tuo blocco, oltre ad un modello dell'oggetto il quale deve essere figlio di uno dei due modelli.

![Foto del pilastro in gioco](/assets/develop/blocks/blockstates_1.png)

## Stati del Blocco Personalizzati {#custom-block-states}

Gli stati del blocco personalizazti sono ottimi se il tuo blocco ha proprietà uniche - a volte è anche possibile che il tuo blocco riusi proprietà vanilla.

Questo esempio creerà una proprietà booleana chiamata `activated` - quando un giocatore clicca con il tasto destro il blocco, il blocco passerà dall'essere `activated=false` ad `activated=true` - cambiando la texture in maniera appropriata.

### Creare la Proprietà {#creating-the-property}

Anzitutto, dovrai creare la proprietà in sé - poiché questo è un booleano, useremo il metodo `BooleanProperty.of`.

@[code transcludeWith=:::1](@/reference/latest/src/main/java/com/example/docs/block/custom/PrismarineLampBlock.java)

Dopo di che, dovremo aggiungere la proprietà al gestore degli stati del blocco nel metodo `appendProperties`. Dovrai fare override del metodo per accedere al costruttore:

@[code transcludeWith=:::2](@/reference/latest/src/main/java/com/example/docs/block/custom/PrismarineLampBlock.java)

Dovrai anche impostare un valore predefinito per la proprietà `activated` nel costruttore del tuo blocco personalizzato.

@[code transcludeWith=:::3](@/reference/latest/src/main/java/com/example/docs/block/custom/PrismarineLampBlock.java)

### Usare la Proprietà {#using-the-property}

Questo esempio invertirà la proprietà booleana `activated` quando il giocatore interagisce con il blocco. Possiamo fare override del metodo `onUse` per questo:

@[code transcludeWith=:::4](@/reference/latest/src/main/java/com/example/docs/block/custom/PrismarineLampBlock.java)

### Visualizzare la Proprietà {#visualizing-the-property}

Prima di creare il file degli stati del blocco, dovrai fornire texture per entrambi gli stati del blocco, sia attivo sia inattivo, e con quelle anche il modello del blocco.

<DownloadEntry visualURL="/assets/develop/blocks/blockstates_2_large.png" downloadURL="/assets/develop/blocks/prismarine_lamp_textures.zip">le Texture</DownloadEntry>

Sfrutta la tua conoscenza dei modelli dei blocchi per creare due modelli per il blocco: uno per lo stato attivo ed uno per quello inattivo. Quando avrai fatto ciò, puoi iniziare la creazione del file degli stati del blocco.

Poiché hai aggiunto una nuova proprietà, dovrai aggiornare il file degli stati di quel blocco per tenere quella proprietà in considerazione.

Se hai proprietà multiple su un blocco, dovrai tenere in conto tutte le possibili combinazioni. Per esempio, `activated` e `axis` porterebbero a 6 combinazioni (due valori possibili per `activated` e tre valori possibili per `axis`).

Poiché questo blocco ha solo due possibili varianti, dato che ha solo una proprietà (`activated`), il file JSON degli stati del blocco avrà il seguente aspetto:

@[code](@/reference/latest/src/main/resources/assets/fabric-docs-reference/blockstates/prismarine_lamp.json)

:::tip
Non dimenticare di aggiungere una [Descrizione del Modello d'Oggetto](../items/first-item#creating-the-item-model-description) per il blocco così che appaia nell'inventario!
:::

Poiché il blocco nell'esempio è una lampada, dovremo anche fargli emettere luce quando la proprietà `activated` è `true`. Questo si può ottenere tramite le impostazioni del blocco, passate al costruttore durante la registrazione del blocco.

Puoi usare il metodo `luminance` per impostare il livello di luce emessa dal blocco, possiamo creare un metodo statico nella classe `PrismarineLampBlock` per restituire il livello di luce in base alla proprietà `activated`, e passarlo come riferimento a un metodo nel metodo `luminance`:

@[code transcludeWith=:::5](@/reference/latest/src/main/java/com/example/docs/block/custom/PrismarineLampBlock.java)

@[code transcludeWith=:::4](@/reference/latest/src/main/java/com/example/docs/block/ModBlocks.java)

<!-- Note: This block can be a great starter for a redstone block interactivity page, maybe triggering the blockstate based on redstone input? -->

Quando avrai completato tutto, il risultato finale dovrebbe avere il seguente aspetto:

<VideoPlayer src="/assets/develop/blocks/blockstates_3.webm">Blocco di Lampada di Prismarino nel gioco</VideoPlayer>
