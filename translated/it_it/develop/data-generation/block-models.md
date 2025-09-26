---
title: Generazione di Modelli di Blocchi
description: Una guida alla generazione di modelli e stati di blocchi tramite datagen.
authors:
  - Fellteros
  - IMB11
  - its-miroma
  - natri0
---

:::info PREREQUISITI
Assicurati di aver prima completato il processo di [configurazione della datagen](./setup).
:::

## Configurazione {#setup}

Anzitutto, avremo bisogno di creare il nostro ModelProvider. Crea una classe che `extends FabricModelProvider`. Implementa entrambi i metodi astratti: `generateBlockStateModels` e `generateItemModels`.
Infine, crea un costruttore che corrisponda a super.

@[code lang=java transcludeWith=:::datagen-model:provider](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModModelProvider.java)

Registra questa classe nella tua `DataGeneratorEntrypoint` all'interno del metodo `onInitializeDataGenerator`.

## Stati e Modelli dei Blocchi {#blockstates-and-block-models}

```java
@Override
public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
}
```

Per i modelli dei blocchi ci concentreremo soprattutto sul metodo `generateBlockStateModels`. Nota il parametro `BlockStateModelGenerator blockStateModelGenerator` - questo oggetto sarà responsabile della generazione di tutti i file JSON.
Ecco alcuni esempi utili che puoi usare per generare i tuoi modelli desiderati:

### Cubo Intero Semplice {#simple-cube-all}

@[code lang=java transcludeWith=:::datagen-model:cube-all](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModModelProvider.java)

Questa è la funzione usata più spesso. Essa genera un file JSON per un semplice modello `cube_all` di un blocco. Una texture viene usata per tutti e sei le facce, in questo caso useremo `steel_block`.

@[code](@/reference/latest/src/main/generated/assets/example-mod/models/block/steel_block.json)

Essa genera anche un file JSON dei stati del blocco. Poiché non abbiamo alcuna proprietà per gli stati del blocco (per esempio Asse, Orientazione, ...), ci basta una variante che verrà usata ogni volta che il blocco è piazzato.

@[code](@/reference/latest/src/main/generated/assets/example-mod/blockstates/steel_block.json)

<DownloadEntry visualURL="/assets/develop/data-generation/block-model/steel_block_big.png" downloadURL="/assets/develop/data-generation/block-model/steel_block.png">Blocco d'Acciaio</DownloadEntry>

### Singleton {#singletons}

Il metodo `registerSingleton` fornisce file JSON di modelli in base al `TexturedModel` che passi, e una singola variante di stato di blocco.

@[code lang=java transcludeWith=:::datagen-model:cube-top-for-ends](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModModelProvider.java)

Questo metodo genererà modelli per un cubo normale, che usa il file di texture `pipe_block` per i lati e `pipe_block_top` per le facce superiore e inferiore.

@[code](@/reference/latest/src/main/generated/assets/example-mod/models/block/pipe_block.json)

:::tip
Se non sai quale `TextureModel` dovresti scegliere, apri la classe `TexturedModel` e dai un'occhiata alle [`TextureMaps`](#using-texture-map)!
:::

<DownloadEntry visualURL="/assets/develop/data-generation/block-model/pipe_block_textures_big.png" downloadURL="/assets/develop/data-generation/block-model/pipe_block_textures.zip">Blocco di Tubatura</DownloadEntry>

### Pool di Texture dei Blocchi {#block-texture-pool}

@[code lang=java transcludeWith=:::datagen-model:block-texture-pool-normal](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModModelProvider.java)

Un altro metodo utile è `registerCubeAllModelTexturePool`: definisce le texture passandoci il "blocco di base", per poi aggiungerci i "figli", che avranno le stesse texture.
In questo caso, abbiamo passato il `RUBY_BLOCK`, quindi scalini, lastra e staccionata useranno la texture `RUBY_BLOCK`.

:::warning
Genererà anche un [modello JSON per un cubo intero semplice](#simple-cube-all) per il "blocco di base" per assicurarsi che il blocco abbia un modello.

Tieni conto di questo se vuoi cambiare il modello di quel blocco in particolare, poiché causerà un errore.
:::

Puoi anche aggiungere una `BlockFamily`, che genererà modelli per tutti i suoi "figli".

@[code lang=java transcludeWith=:::datagen-model:family-declaration](@/reference/latest/src/main/java/com/example/docs/block/ModBlocks.java)

@[code lang=java transcludeWith=:::datagen-model:block-texture-pool-family](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModModelProvider.java)

<DownloadEntry visualURL="/assets/develop/data-generation/block-model/ruby_block_big.png" downloadURL="/assets/develop/data-generation/block-model/ruby_block.png">Blocco di Rubino</DownloadEntry>

### Porte e Botole {#doors-and-trapdoors}

@[code lang=java transcludeWith=:::datagen-model:door-and-trapdoor](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModModelProvider.java)

Le porte e botole funzionano un po' diversamente. Qui, dovrai creare tre nuove texture - due per la porta, e una per la botola.

1. La porta:
  - Ha due parti - la metà superiore e quella inferiore. **Ciascuna necessita di una texture propria**: in questo caso `ruby_door_top` per la metà superiore e `ruby_door_bottom` per l'inferiore.
  - Il metodo `registerDoor()` creerà modelli per tutte le orientazioni della porta, sia aperta che chiusa.
  - **Servirà anche una texture per l'oggetto!** Mettila nella cartella `assets/mod-id/textures/item/`.
2. La botola:
  - Qui ti basta una texture sola, in questo caso chiamata `ruby_trapdoor`. Verrà usata per tutti i lati.
  - Poiché il `TrapdoorBlock` ha una proprietà `FACING`, puoi usare il metodo commentato per generare file di modello con texture ruotate = la botola sarà "orientabile". Altrimenti avrà lo stesso aspetto in tutte le direzioni.

<DownloadEntry visualURL="/assets/develop/data-generation/block-model/ruby_door_trapdoor_big.png" downloadURL="/assets/develop/data-generation/block-model/ruby_door_trapdoor_textures.zip">Porta e Botola di Rubino</DownloadEntry>

## Modelli del Blocco Personalizzati {#custom-block-models}

In questa sezione creeremo i modelli per una Lastra Verticale di Quercia, con texture di un Tronco di Quercia.

_I punti 2. - 6. sono dichiarati in una classe ausiliaria interna chiamata `CustomBlockStateModelGenerator`._

### Classe dei Blocchi Personalizzati {#custom-block-class}

Crea un blocco `VerticalSlab` con una proprietà `FACING` e una proprietà booleana `SINGLE`, come nel tutorial degli [Stati dei Blocchi](../blocks/blockstates). `SINGLE` indicherà se ci sono entrambe le lastre.
Poi dovresti fare override di `getOutlineShape` e `getCollisionShape`, cosicché il contorno sia renderizzato correttamente, e il blocco abbia la forma di collisione corretta.

@[code lang=java transcludeWith=:::datagen-model-custom:voxels](@/reference/latest/src/main/java/com/example/docs/block/custom/VerticalSlabBlock.java)

@[code lang=java transcludeWith=:::datagen-model-custom:collision](@/reference/latest/src/main/java/com/example/docs/block/custom/VerticalSlabBlock.java)

Fai anche override del metodo `canReplace()`, altrimenti non potresti rendere la lastra un blocco intero.

@[code lang=java transcludeWith=:::datagen-model-custom:replace](@/reference/latest/src/main/java/com/example/docs/block/custom/VerticalSlabBlock.java)

Tutto fatto! Ora puoi testare il blocco e piazzarlo nel gioco.

### Modello di Blocco Genitore {#parent-block-model}

Ora creiamo un modello di blocco genitore. Esso determinerà la dimensione, posizione nella mano o in altri slot e le coordinate `x` e `y` della texture.
Si consiglia, per questo, di usare un editor come [Blockbench](https://www.blockbench.net/) poiché crearlo manualmente è davvero faticoso. Dovrebbe avere un aspetto del genere:

@[code lang=json](@/reference/latest/src/main/resources/assets/example-mod/models/block/vertical_slab.json)

Controlla il [formato degli stati del blocco](https://minecraft.wiki/w/Blockstates_definition_format) per maggiori informazioni.
Nota le parole chiave `#bottom`, `#top`, `#side`. Queste saranno variabili impostabili in modelli che ereditino da questo genitore:

```json
{
  "parent": "minecraft:block/cube_bottom_top",
  "textures": {
    "bottom": "minecraft:block/sandstone_bottom",
    "side": "minecraft:block/sandstone",
    "top": "minecraft:block/sandstone_top"
  }
}
```

Il valore `bottom` sostituirà il segnaposto `#bottom` eccetera. **Mettilo nella cartella `resources/assets/mod-id/models/block/`.**

### Modello Personalizzato {#custom-model}

Un'altra cosa che ci serve è un'istanza della classe `Model`. Essa rappresenterà proprio il [modello di blocco genitore](#parent-block-model) nella nostra mod.

@[code lang=java transcludeWith=:::datagen-model-custom:model](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModModelProvider.java)

Il metodo `block()` crea un nuovo `Model`, puntando al file `vertical_slab.json` nella cartella `resources/assets/mod-id/models/block/`.
Le `TextureKey` rappresentano i "segnaposto" (`#bottom`, `#top`, ...) come oggetti.

### Usare le Mappe di Texture {#using-texture-map}

Cosa fa `TextureMap`? In effetti fornisce gli identificativi che puntano alle texture. Tecnicamente funziona proprio come una mappa normale - si associa una `TextureKey` (chiave) con un `Identifier` (valore).

Puoi usare quelle vanilla, come `TextureMap.all()`(che associa tute le `TextureKey` allo stesso `Identifier`), o crearne una nuova creandone una nuova istanza e usando `.put()` per associare valori alle chiavi.

:::tip
`TextureMap.all()` associa tutte le `TextureKey` allo stesso `Identifier`, indipendentemente dalla loro quantità!
:::

Poiché vogliamo usare le texture del Tronco di Quercia, ma abbiamo le `TextureKey` `BOTTOM`, `TOP` e `SIDE`, ne dovremo creare un'altra.

@[code lang=java transcludeWith=:::datagen-model-custom:texture-map](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModModelProvider.java)

Le facce `bottom` e `top` useranno `oak_log_top.png`, i lati useranno `oak_log.png`.

:::warning
Tutte le `TextureKey` nella `TextureMap` **devono** corrispondere a tutte le `TextureKey` nel tuo modello di blocco genitore!
:::

### Metodo `BlockStateSupplier` Personalizzato {#custom-supplier-method}

Il `BlockStateSupplier` contiene tutte le varianti degli stati del blocco, le rotazioni, e altre opzioni come uvlock.

@[code lang=java transcludeWith=:::datagen-model-custom:supplier](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModModelProvider.java)

Anzitutto creeremo un nuovo `VariantsBlockStateSupplier` tramite `VariantsBlockStateSupplier.create()`.
Poi creeremo una nuova `BlockStateVariantMap` che contiene parametri per tutte le varianti del blocco, in questo caso `FACING` e `SINGLE`, e la passeremo nel nostro `VariantsBlockStateSupplier`.
Puoi indicare il modello e le trasformazioni (uvlock, rotazione) da usare in `.register()`.
Per esempio:

- Sulla prima linea, il blocco è in direzione nord, ed è singolo => useremo il modello senza ruotarlo.
- Sulla quarta linea, il blocco è in direzione ovest, ed è singolo => ruoteremo il modello di 270° sull'asse Y.
- Sulla sesta linea, il blocco è in direzione est, ma non è singolo => sembra un normale tronco di quercia => non dobbiamo ruotarlo.

### Metodo di Generazione Dati Personalizzato {#custom-datagen-method}

L'ultimo passaggio - creare effettivamente un metodo che si possa chiamare e che generi i JSON.
Ma che parametri sono necessari?

1. `BlockStateModelGenerator generator`, quello che abbiamo passato in `generateBlockStateModels`.
2. `Block vertSlabBlock` è il blocco di cui genereremo i JSON.
3. `Block fullBlock` - il modello usato quando la proprietà `SINGLE` è falsa = il blocco di lastre sembra un blocco intero.
4. `TextureMap textures` definisce le texture che il modello usa effettivamente. Controlla il capitolo [Usare le Mappe di Texture](#using-texture-map).

@[code lang=java transcludeWith=:::datagen-model-custom:gen](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModModelProvider.java)

Anzitutto, otteniamo l'`Identifier` del modello di lastra singola con `VERTICAL_SLAB.upload()`. Poi otteniamo l'`Identifier` del modello di blocco intero con `ModelIds.getBlockModelId()`, e passiamo entrambi i modelli in `createVerticalSlabBlockStates`.
Il `BlockStateSupplier` viene passato nel `blockStateCollector`, di modo che i file JSON vengano effettivamente generati.
Inoltre, creiamo un modello per l'oggetto di lastra verticale con `BlockStateModelGenerator.registerParentedItemModel()`.

È tutto! Tutto ciò che rimane da fare è chiamare il metodo nel nostro `ModelProvider`:

@[code lang=java transcludeWith=:::datagen-model-custom:method-call](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModModelProvider.java)

## Fonti e Link {#sources-and-links}

Per maggiori informazioni, puoi trovare il test di esempio nell'[API di Fabric](https://github.com/FabricMC/fabric/blob/1.21.4/fabric-data-generation-api-v1/src/) e nella [Mod di Riferimento](https://github.com/FabricMC/fabric-docs/tree/main/reference) di questa documentazione.

Puoi anche trovare altri esempi dell'uso di metodi di datagen personalizzati navigando il codice sorgente aperto delle mod, per esempio di [Vanilla+ Blocks](https://github.com/Fellteros/vanillablocksplus) e [Vanilla+ Verticals](https://github.com/Fellteros/vanillavsplus) di Fellteros.
