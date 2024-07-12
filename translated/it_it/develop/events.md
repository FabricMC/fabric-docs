---
title: Eventi
description: Una guida all'uso degli eventi forniti dall'API di Fabric.
authors:
  - dicedpixels
  - mkpoli
  - daomephsta
  - solidblock
  - draylar
  - jamieswhiteshirt
  - PhoenixVX
  - Juuxel
  - YanisBft
  - liach
  - natanfudge
authors-nogithub:
  - stormyfabric
---

# Eventi {#events}

L'API di Fabric fornisce un sistema che permette alle mod di reagire ad azioni o circostanze, anche dette _eventi_ che accadono nel gioco.

Gli eventi sono agganci che soddisfano usi comuni e/o permettono compatibilità e prestazioni migliori tra mod diverse che si agganciano alle stesse aree del codice. L'uso degli eventi spesso sostituisce l'uso dei mixin.

L'API di Fabric fornisce eventi per aree importanti nel codice base di Minecraft ai quali molti modder potrebbero voler agganciarsi.

Gli eventi sono rappresentati da istanze di `net.fabricmc.fabric.api.event.Event` che memorizza e chiama i _callback_. Spesso c'è una singola istanza di un evento per un callback, che è conservata in un attributo statico `EVENT` dell'interfaccia callback, ma ci sono anche altre organizzazioni. Per esempio, `ClientTickEvents` raggruppa vari eventi legati insieme.

## Callback {#callbacks}

I callback sono una parte di codice che viene passata come argomento a un evento. Quando l'evento viene innescato dal gioco, il pezzo di codice passato viene eseguito.

### Interfacce di Callback {#callback-interfaces}

A ogni evento corrisponde un'interfaccia di callback, convenzionalmente chiamata `<EventName>Callback`. I callback sono registrati chiamando il metodo `register()` su un'istanza di un evento, con un'istanza dell'interfaccia callback come argomento.

Tutti le interfacce callback degli eventi fornite dall'API di Fabric possono essere trovate nel package `net.fabricmc.fabric.api.event`.

## Ascoltare gli Eventi {#listening-to-events}

Questo esempio registra un `AttackBlockCallback` per danneggiare il giocatore quando egli colpisce dei blocchi che non droppano un oggetto se rotti senza strumenti.

@[code lang=java transcludeWith=:::1](@/reference/latest/src/main/java/com/example/docs/event/FabricDocsReferenceEvents.java)

### Aggiungere Oggetti alle Loot Table Esistenti {#adding-items-to-existing-loot-tables}

A volte potresti voler aggiungere oggetti alle loot table. Per esempio, fare in modo che un blocco o un'entità vanilla droppi un tuo oggetto.

La soluzione più semplice, sostituire il file della loot table, può rompere altre mod. E se volessero cambiarli anche loro? Daremo un'occhiata a come puoi aggiungere oggetti alle loot table senza sovrascriverle.

Aggiungeremo le uova alla loot table del minerale di carbone.

#### Ascoltare il Caricamento delle Loot Table {#listening-to-loot-table-loading}

L'API di Fabric ha un evento che si attiva quando le loot table sono caricate, `LootTableEvents.MODIFY`. Puoi registrare un callback per quell'evento nell'initializer della tua mod. Controlliamo anche che la loot table corrente sia quella del minerale di carbone.

@[code lang=java transclude={38-40}](@/reference/latest/src/main/java/com/example/docs/event/FabricDocsReferenceEvents.java)

#### Aggiungere Oggetti alla Loot Table {#adding-items-to-the-loot-table}

Nelle loot table, gli oggetti sono memorizzati come _loot pool entries_, e le voci sono memorizzate in _loot pools_. Per aggiungere un oggetto, dovremo aggiungere una pool con una voce oggetto alla loot table.

Possiamo creare una pool con `LootPool#builder`, e aggiungerla alla loot table.

La nostra pool non ha nemmeno un oggetto, quindi dovremo creare una voce oggetto usando `ItemEntry#builder` e aggiungerla alla pool.

@[code highlight={6-7} transcludeWith=:::2](@/reference/latest/src/main/java/com/example/docs/event/FabricDocsReferenceEvents.java)

## Eventi Personalizzati {#custom-events}

Alcune aree del gioco non hanno agganci forniti dall'API di Fabric, quindi dovrai usare un mixin o creare il tuo evento personalizzato.

Vedremo come creare un evento che viene innescato quando una pecora viene tosata. Il processo per la creazione di un evento è:

- Creare l'interfaccia callback dell'evento
- Innescare l'evento da un mixin
- Creare un'implementazione di prova

### Creare l'Interfaccia Callback dell'Evento {#creating-the-event-callback-interface}

L'interfaccia callback descrive cosa deve essere implementato dai listener di eventi che ascolteranno il tuo evento. L'interfaccia callback descrive anche come l'evento verrà chiamato dal tuo mixin. È convenzione posizionare un oggetto `Event` come attributo nell'interfaccia callback, che identificherà effettivamente il nostro evento.

Per la nostra implementazione di `Event`, sceglieremo di usare un evento basato su un vettore. Il vettore conterrà tutti i listener agli eventi che stanno ascoltando l'evento.

La nostra implementazione chiamerà i listener di eventi in ordine finché uno di essi non restituisce `ActionResult.PASS`. Questo significa che con il valore restituito un listener può dire "_annulla questo_", "_approva questo_" o "_non m'interessa, lascialo al prossimo listener_".

Usare `ActionResult` come valore restituito è una convenzione per far cooperare i gestori di eventi in questa maniera.

Dovrai creare un'interfaccia che ha un'istanza `Event` e un metodo per implementare la risposta. Una semplice configurazione per il nostro callback di tosatura di una pecora è:

@[code lang=java transcludeWith=:::](@/reference/latest/src/main/java/com/example/docs/event/SheepShearCallback.java)

Diamogli un'occhiata più precisa. Quando l'invocatore viene chiamato, iteriamo su tutti i listener:

@[code lang=java transclude={21-22}](@/reference/latest/src/main/java/com/example/docs/event/SheepShearCallback.java)

Poi chiamiamo il nostro metodo (in questo caso, `interact`) sul listener per ottenere la sua risposta:

@[code lang=java transclude={33-33}](@/reference/latest/src/main/java/com/example/docs/event/SheepShearCallback.java)

Se il listener dice che dobbiamo annullare (`ActionResult.FAIL`), oppure finire completamente (`ActionResult.SUCCESS`), il callback restituisce il risultato e finisce il loop. `ActionResult.PASS` si sposta sul prossimo listener, e nella maggior parte dei casi dovrebbe risultare in un successo se non ci sono altri listener registrati:

@[code lang=java transclude={25-30}](@/reference/latest/src/main/java/com/example/docs/event/SheepShearCallback.java)

Possiamo aggiungere commenti Javadoc in cima alle classi di callback per documentare cosa fa ogni `ActionResult`. Nel nostro caso, potrebbe essere:

@[code lang=java transclude={9-16}](@/reference/latest/src/main/java/com/example/docs/event/SheepShearCallback.java)

### Innescare l'Evento da un Mixin {#triggering-the-event-from-a-mixin}

Ora abbiamo lo scheletro di base dell'evento, ma dobbiamo anche innescarlo. Siccome vogliamo che l'evento venga chiamato quando un giocatore prova a tosare una pecora, chiamiamo l'`invoker` dell'evento in `SheepEntity#interactMob` quando `sheared()` viene chiamata (ovvero quando la pecora può essere tosata, e il giocatore sta tenendo delle cesoie):

@[code lang=java transcludeWith=:::](@/reference/latest/src/main/java/com/example/docs/mixin/event/SheepEntityMixin.java)

### Creare un Implementazione di Prova {#creating-a-test-implementation}

Ora dobbiamo testare il nostro evento. Puoi registrare un listener nel tuo metodo d'inizializzazione (o in un'altra area, se preferisci) e aggiungere logica personalizzata lì. Qui c'è un esempio che droppa un diamante anziché lana ai piedi della pecora:

@[code lang=java transcludeWith=:::3](@/reference/latest/src/main/java/com/example/docs/event/FabricDocsReferenceEvents.java)

Se entri nel gioco e tosi una pecora, un diamante dovrebbe essere droppato anziché lana.
