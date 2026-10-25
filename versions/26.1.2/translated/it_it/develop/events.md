---
title: Eventi
description: Una guida all'uso degli eventi forniti dall'API di Fabric.
authors:
  - Daomephsta
  - dicedpixels
  - Draylar
  - JamiesWhiteShirt
  - Jimmy474
  - Juuxel
  - liach
  - mkpoli
  - natanfudge
  - PhoenixVX
  - SolidBlock-cn
  - YanisBft
authors-nogithub:
  - stormyfabric
---

L'API di Fabric fornisce un sistema che permette alle mod di reagire ad azioni o circostanze, anche dette _eventi_ che accadono nel gioco.

Gli eventi sono agganci che soddisfano usi comuni e/o permettono compatibilità e prestazioni migliori tra mod diverse che si agganciano alle stesse aree del codice. L'uso degli eventi spesso sostituisce l'uso dei mixin.

L'API di Fabric fornisce eventi per aree importanti nel codice base di Minecraft ai quali molti modder potrebbero voler agganciarsi.

Gli eventi sono rappresentati da istanze di `net.fabricmc.fabric.api.event.Event` che memorizza e chiama i _callback_. Spesso c'è una singola istanza di un evento per un callback, che è conservata in un attributo statico `EVENT` dell'interfaccia callback, ma ci sono anche altre organizzazioni. Per esempio, `ClientTickEvents` raggruppa vari eventi legati insieme.

## Callback {#callbacks}

I callback sono una parte di codice che viene passata come argomento a un evento. Quando l'evento viene innescato dal gioco, il pezzo di codice passato viene eseguito.

### Interfacce di Callback {#callback-interfaces}

A ogni evento corrisponde un'interfaccia di callback. I callback sono registrati chiamando il metodo `register()` su un'istanza di un evento, con un'istanza dell'interfaccia callback come argomento.

## Ascoltare gli Eventi {#listening-to-events}

Questo esempio registra un `AttackBlockCallback` per danneggiare il giocatore quando colpisce dei blocchi che non droppano un oggetto se rotti senza utensili.

<<< @/reference/26.1.2/src/main/java/com/example/docs/event/ExampleModEvents.java#attack_block_callback_event

### Aggiungere Oggetti alle Loot Table Esistenti {#adding-items-to-existing-loot-tables}

A volte potresti voler aggiungere oggetti alle loot table. Per esempio, fare in modo che un blocco o un'entità vanilla droppi un tuo oggetto.

La soluzione più semplice, sostituire il file della loot table, può rompere altre mod. E se volessero cambiarli anche loro? Daremo un'occhiata a come puoi aggiungere oggetti alle loot table senza sovrascriverle.

Aggiungeremo le uova alla loot table del minerale di carbone.

#### Ascoltare il Caricamento delle Loot Table {#listening-to-loot-table-loading}

L'API di Fabric ha un evento che si attiva quando le loot table sono caricate, `LootTableEvents.MODIFY`. Puoi registrare un callback per quell'evento nell'[initializer della tua mod](./getting-started/project-structure#entrypoints). Controlliamo anche che la loot table corrente sia quella del minerale di carbone:

<<< @/reference/26.1.2/src/main/java/com/example/docs/event/ExampleModEvents.java#loot_table_events

#### Aggiungere Oggetti alla Loot Table {#adding-items-to-the-loot-table}

Per aggiungere un oggetto, dovremo aggiungere una pool con una voce oggetto alla loot table.

Possiamo creare una pool con `LootPool#lootPool`, e aggiungerla alla loot table.

La nostra pool non ha ancora un oggetto, quindi dovremo creare una voce oggetto usando `LootItem#lootTableItem` e aggiungerla alla pool.

<<< @/reference/26.1.2/src/main/java/com/example/docs/event/ExampleModEvents.java#loot_pool_builder{5-7}

## Eventi Personalizzati {#custom-events}

Alcune aree del gioco non hanno agganci forniti dall'API di Fabric, quindi dovrai usare un mixin o creare il tuo evento personalizzato.

Vedremo come creare un evento che viene innescato quando una pecora viene tosata. Il processo per la creazione di un evento è:

- Creare l'interfaccia callback dell'evento
- Innescare l'evento da un mixin
- Creare un'implementazione di prova

### Creare l'Interfaccia Callback dell'Evento {#creating-the-event-callback-interface}

L'interfaccia callback descrive cosa deve essere implementato dai listener di eventi che ascolteranno il tuo evento. L'interfaccia callback descrive anche come l'evento verrà chiamato dal tuo mixin. È convenzione posizionare un oggetto `Event` come attributo nell'interfaccia callback, che identificherà effettivamente il nostro evento.

Per la nostra implementazione di `Event`, sceglieremo di usare un evento basato su un vettore. Il vettore conterrà tutti i listener agli eventi che stanno ascoltando l'evento.

La nostra implementazione chiamerà i listener di eventi in ordine finché uno di essi non restituisce `InteractionResult.PASS`. Questo significa che con il valore restituito un listener può dire "_annulla questo_", "_approva questo_", o "_non m'interessa, passalo al prossimo listener_".

Usare `InteractionResult` come valore restituito è una convenzione per far cooperare i gestori di eventi in questa maniera.

Dovrai creare un'interfaccia che ha un'istanza `Event` e un metodo per implementare la risposta. Una semplice configurazione per il nostro callback di tosatura di una pecora è:

<<< @/reference/26.1.2/src/main/java/com/example/docs/event/SheepShearCallback.java#sheep_shear_callback

Diamogli un'occhiata più precisa. Quando l'invocatore viene chiamato, iteriamo su tutti i listener:

<<< @/reference/26.1.2/src/main/java/com/example/docs/event/SheepShearCallback.java#listener_iterator

Per ogni listener, chiameremo il metodo `interact` per ottenere la sua risposta. Ecco la firma di `interact` dichiarata nella nostra interface:

<<< @/reference/26.1.2/src/main/java/com/example/docs/event/SheepShearCallback.java#interact_method

Se il listener dice che dobbiamo annullare (perché restituisce `FAIL`), oppure terminare completamente (`SUCCESS`), il callback restituisce il risultato e finisce il loop.

`InteractionResult.PASS` fa avanzare al prossimo listener, e questo finché tutti i listener saranno stati chiamati, anche finché l'ultimo restituisce `PASS`:

<<< @/reference/26.1.2/src/main/java/com/example/docs/event/SheepShearCallback.java#return_value

Possiamo aggiungere commenti Javadoc in cima alle classi di callback per documentare cosa fa ogni `InteractionResult`. Nel nostro caso, potrebbe essere:

<<< @/reference/26.1.2/src/main/java/com/example/docs/event/SheepShearCallback.java#javadoc_comment

### Innescare l'Evento da un Mixin {#triggering-the-event-from-a-mixin}

Ora abbiamo lo scheletro di base dell'evento, ma dobbiamo anche innescarlo. Siccome vogliamo che l'evento venga chiamato quando un giocatore prova a tosare una pecora, chiamiamo l'`invoker` dell'evento in `Sheep#mobInteract` quando `shear()` viene chiamato (ovvero quando la pecora può essere tosata, e il giocatore sta tenendo delle cesoie):

<<< @/reference/26.1.2/src/main/java/com/example/docs/mixin/event/SheepMixin.java#sheep_mixin

### Creare un Implementazione di Prova {#creating-a-test-implementation}

Ora dobbiamo testare il nostro evento. Puoi registrare un listener nel tuo metodo d'inizializzazione (o in un'altra area, se preferisci) e aggiungere logica personalizzata lì.

Qui c'è un esempio che droppa un diamante anziché lana ai piedi della pecora:

<<< @/reference/26.1.2/src/main/java/com/example/docs/event/ExampleModEvents.java#sheep_shear_callback_event

Se entri nel gioco e tosi una pecora, un diamante dovrebbe essere droppato anziché lana.

<!-- TODO: maybe adding a video of a sheep dropping diamonds? -->
