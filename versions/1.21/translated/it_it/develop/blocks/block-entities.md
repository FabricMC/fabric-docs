---
title: Blocchi-Entità
description: Impara come creare blocchi-entità per i tuoi blocchi personalizzati.
authors:
  - natri0
---

I blocchi-entità sono un modo per memorizzare dati aggiuntivi per un blocco, che non siano parte dello stato del blocco: contenuti dell'inventario, nome personalizzato e così via.
Minecraft usa i blocchi-entità per blocchi come bauli, fornaci, e blocchi dei comandi.

Come esempio, creeremo un blocco che conta quante volte esso è stato cliccato con il tasto destro.

## Creare il Blocco-Entità {#creating-the-block-entity}

Per fare in modo che Minecraft riconosca e carichi i nuovi blocchi-entità, dobbiamo creare un tipo di blocco-entità. Questo si fa estendendo la classe `BlockEntity` e registrandola in una nuova classe `ModBlockEntities`.

@[code transcludeWith=:::1](@/reference/1.21/src/main/java/com/example/docs/block/entity/custom/CounterBlockEntity.java)

La registrazione di un `BlockEntity` produce un `BlockEntityType` come il `COUNTER_BLOCK_ENTITY` che abbiamo usato sopra:

@[code transcludeWith=:::1](@/reference/1.21/src/main/java/com/example/docs/block/entity/ModBlockEntities.java)

:::tip
Nota come il costruttore del `CounterBlockEntity` prenda due parametri, ma il costruttore del `BlockEntity` ne prenda tre: il `BlockEntityType`, la `BlockPos`, e lo `BlockState`.
Se non fissassimo nel codice il `BlockEntityType`, la classe `ModBlockEntities` non compilerebbe! Questo perché la `BlockEntityFactory`, che è un'interfaccia funzionale, descrive una funzione che prende solo due parametri, proprio come il nostro costruttore.
:::

## Creare il Blocco {#creating-the-block}

Dopo di che, per usare effettivamente il blocco-entità, ci serve un blocco che implementi `BlockEntityProvider`. Creiamone uno e chiamiamolo `CounterBlock`.

:::tip
Ci sono due modi per approcciarsi a questo:

- Creare un blocco che estenda `BlockWithEntity` e implementi il metodo `createBlockEntity` (_e_ il metodo `getRenderType`, poiché `BlockWithEntity` li rende invisibili in maniera predefinita)
- Creare un blocco che implementi `BlockEntityProvider` da solo e faccia override del metodo `createBlockEntity`

Useremo il primo approccio in questo esempio, poiché `BlockWithEntity` fornisce anche alcune utilità comode.
:::

@[code transcludeWith=:::1](@/reference/1.21/src/main/java/com/example/docs/block/custom/CounterBlock.java)

Usare `BlockWithEntity` come classe genitore significa che dobbiamo anche implementare il metodo `createCodec`, il che è piuttosto semplice.

A differenza dei blocchi, che sono dei singleton, viene creato un nuovo blocco-entità per ogni istanza del blocco. Questo viene fatto con il metodo `createBlockEntity`, che prende la posizione e il `BlockState`, e restituisce un `BlockEntity`, o `null` se non ce ne dovrebbe essere uno.

Non dimenticare di registrare il blocco nella classe `ModBlocks`, proprio come nella guida [Creare il Tuo Primo Blocco](../blocks/first-block):

@[code transcludeWith=:::5](@/reference/1.21/src/main/java/com/example/docs/block/ModBlocks.java)

## Usare il Blocco-Entità {#using-the-block-entity}

Ora che abbiamo un blocco-entità, possiamo usarlo per memorizzare il numero di clic con il tasto destro sul blocco. Faremo questo aggiungendo un attributo `clicks` alla classe `CounterBlockEntity`:

@[code transcludeWith=:::2](@/reference/1.21/src/main/java/com/example/docs/block/entity/custom/CounterBlockEntity.java)

Il metodo `markDirty`, usato in `incrementClicks`, informa il gioco che i dati dell'entità sono stati aggiornati; questo sarà utile quando aggiungeremo i metodi per serializzare il contatore e ricaricarlo dal file di salvataggio.

Il prossimo passaggio è incrementare questo attributo ogni volta che il blocco viene cliccato con il tasto destro. Questo si fa facendo override del metodo `onUse` nella classe `CounterBlock`:

@[code transcludeWith=:::2](@/reference/1.21/src/main/java/com/example/docs/block/custom/CounterBlock.java)

Poiché il `BlockEntity` non viene passato nel metodo, usiamo `world.getBlockEntity(pos)`, e se il `BlockEntity` non è valido, usciamo dal metodo.

![Messaggio "You've clicked the block for the 6th time" apparso sullo schermo dopo il clic con il tasto destro](/assets/develop/blocks/block_entities_1.png)

## Salvare e Caricare i Dati {#saving-loading}

Ora che abbiamo un blocco funzionante, dovremmo anche fare in modo che il contatore non si resetti dopo un riavvio del gioco. Questo si fa serializzandolo in NBT quando si salva il gioco, e deserializzandolo durante il caricamento.

La serializzazione avviene con il metodo `writeNbt`:

@[code transcludeWith=:::3](@/reference/1.21/src/main/java/com/example/docs/block/entity/custom/CounterBlockEntity.java)

Qui, aggiungiamo gli attributi che dovrebbero essere salvati al `NbtCompound` passato: nel caso del blocco contatore, l'attributo `clicks`.

La lettura è simile, ma invece di salvare nel `NbtCompound` si ottengono i valori salvati precendentemente, e salvarli negli attributi del `BlockEntity`:

@[code transcludeWith=:::4](@/reference/1.21/src/main/java/com/example/docs/block/entity/custom/CounterBlockEntity.java)

Ora, salvando e ricaricando il gioco, il blocco contatore dovrebbe riprendere da dove è stato salvato.

## Ticker {#tickers}

L'interfaccia `BlockEntityProvider` definisce anche un metodo chiamato `getTicker`, che può essere usato per eseguire del codice ogni tick per ogni istanza del blocco. Possiamo implementarlo creando un metodo statico che verrà usato come `BlockEntityTicker`:

Il metodo `getTicker` dovrebbe anche controllare che il `BlockEntityType` passato sia quello che stiamo usando; se lo è, restituirà la funzione da chiamare a ogni tick. Vi è una funzione di utilità che fa il controllo in `BlockWithEntity`:

@[code transcludeWith=:::3](@/reference/1.21/src/main/java/com/example/docs/block/custom/CounterBlock.java)

`CounterBlockEntity::tick` è un riferimento al metodo statico `tick` che dobbiamo creare nella classe `CounterBlockEntity`. Non è necessario seguire questa struttura, ma buona pratica per mantenere del codice pulito e organizzato.

Diciamo di voler fare in modo che il contatore possa essere incrementato soltanto ogni 10 tick (2 volte al secondo). Possiamo fare ciò aggiungendo un attributo `ticksSinceLast` alla classe `CounterBlockEntity`, e incrementandolo a ogni tick:

@[code transcludeWith=:::5](@/reference/1.21/src/main/java/com/example/docs/block/entity/custom/CounterBlockEntity.java)

Non dimenticare di serializzare e deserializzare questo attributo!

Ora possiamo usare `ticksSinceLast` per controllare se il contatore può essere incrementato in `incrementClicks`:

@[code transcludeWith=:::6](@/reference/1.21/src/main/java/com/example/docs/block/entity/custom/CounterBlockEntity.java)

:::tip
Se sembra che il blocco-entità non faccia tick, prova a controllare il codice della registrazione! Dovrebbe passare i blocchi validi per questa entità nel `BlockEntityType.Builder`, o altrimenti scriverà un avviso nella console:

```text
[13:27:55] [Server thread/WARN] (Minecraft) Block entity example-mod:counter @ BlockPos{x=-29, y=125, z=18} state Block{example-mod:counter_block} invalid for ticking:
```

:::
