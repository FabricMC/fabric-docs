---
title: Eventi
description: Una guida all'uso degli eventi disponibili con Fabric API.
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
---

<!-- Couldn't find GitHub usernames for: stormyfabric -->

# Eventi

Fabric API mette a disposizione un sitema che permette alle mod di reagire ad azioni o accadiementi, definiti come _eventi_ che accadono in gioco.

Gli Eventi sono "agganci" che soddifano usi comuni e/o permettono una compatibilità e performance migliori fra mod diverse che si agganciano alle stesse aree del codice. L'uso degli eventi spesso sostituisce l'uso dei mixin.

Fabric API mette a disposizione eventi per aree importanti del codebase di Minecraft, alle quali molti modder potrebbero essere interessati.

Gli Eventi sono rappresentati da una instance di `net.fabricmc.fabric.api.event.Event` che contiene e chiama le _callbacks_. Spesso c'è una singola instance di un evento per una callback, che è conservato in un attributo statico `EVENT` dell'interfaccia della callback, ma ci sono anche altri casi. Per esempio, `ClientTickEvents` raggruppa vari eventi di tipi simili insieme.

## Callbacks

Le Callbacks sono una parte di codice che viene passata come argomento ad un evento. Quando l'evento viene innescato dal gioco, il pezzo di codice passato viene eseguito.

### Interfacce di Callback

Ogni evento ha un'interfaccia di callaback, convenzionalmente chiamata `<EventName>Callback`. Le Callback sono registrate chiamando il metodo `register()` in un'instance di un evento, con un'instance dell'interfaccia della callback passata come argomento.

Tutti le interfacce delle callback degli eventi fornite da Fabric API possono essere trovate nel pacchetto `net.fabricmc.fabric.api.event`.

## Ascoltare gli Eventi

### Un Semplice Esempio

Questo esempio registra un `AttackBlockCallback` per danneggiare il giocatore quando colpisce dei blocchi che non droppano un oggetto quando rotti a mani nude.

@[code lang=java transcludeWith=:::1](@/reference/latest/src/main/java/com/example/docs/event/FabricDocsReferenceEvents.java)

### Aggiungere Oggetti alle Loot Table esistenti

A volte potresti voler aggiungere oggetti alle loot tables. Ad esempio, aggiungere i tuoi drop ad un blocco o ad un'entità vanilla.

La soluzione più semplice, rimpiazzare il file delle loot table, può rompere altre mod. E se volessero cambiarli anche loro? Daremo un'occhiata a come puoi aggiungere oggetti alle loot tables senza sovrascriverle.

Aggiungeremo delle uova alla loot table del carbone.

#### Ascoltare il caricamento delle Loot Table

Fabric API ha un evento che viene attivato quando le loot tables sono caricate, `LootTableEvents.MODIFY`. Puoi registrare una callbak per quello nel tuo mod initializer. Controlliamo anche la loot table corrente sia quella quella del minerale di carbone.

@[code lang=java transclude={38-40}](@/reference/latest/src/main/java/com/example/docs/event/FabricDocsReferenceEvents.java)

#### Aggiungere Oggetti alla Loot Table

Nelle loot table, gli oggetti sono conservati in _loot pool entries_, e le voci sono conservate in _loot pools_. Per aggiungere un oggetto, dovremo aggiungere una pool con una voce oggetto alla loot table.

Possiamo creare una pool con `LootPool#builder`, e aggiungerla alla loot table.

La nostra pool non ha nemmeno un oggetto, quindi dovremo creare una voce oggetto usando `ItemEntry#builder` e aggiungerla alla pool.

@[code highlight={6-7} transcludeWith=:::2](@/reference/latest/src/main/java/com/example/docs/event/FabricDocsReferenceEvents.java)

## Eventi Custom

Alcune aree del gioco non hanno agganci forniti da Fabric API, quindi dovrai usare un mixin o creare il tuo evento.

Guardermo come creare un evento che viene innescato quando una pecora viene tosata. Il processo per la creazione di un evento è:

- Creare l'interfaccia di callback per l'evento
- Innescare l'evento da un mixin
- Creare un'implementazione di prova

### Creare l'interfaccia di Callback per l'Evento

L'interfaccia di callback descrive cosa deve essere implementata dagli event listener, che ascolteranno il tuo evento. L'interfaccia di callback descrive anche come l'evento verrà chiamato dal tuo mixin. È convezione posizionare un oggetto `Event` come agromento nell'interfaccia di callback, che identificherà un evento effettivo.

Per la nostra implementazione di `Event` sceglieremo di usare un evento basato sui vettori. Il vettore conterrà tutti gli event listners che stanno ascoltando l'evento.

La nostra implementazione chiamerà gli event listeners i ordine, fino a che uno non ritrona un `ActionResult.PASS`. Questo vuol dire che un listener può dire "_cancella questo_", "_approva questo_" o "_non m'interessa, lascialo al prossimo listener_".

Usare `ActionResult` come valore di return, è una maniera convenzionale per fare cooperare gestori di eventi in questa maniera.

Dovrai crare un'intefaccia che ha un'istanza `Event` e un metodo per l'implementazione della risposta. Un setup semplice per la nostra callback per la tosatura della pecora è:

@[code lang=java transcludeWith=:::](@/reference/latest/src/main/java/com/example/docs/event/SheepShearCallback.java)

Diamogli un'occhiata più precisa. Quando l'invocatore viene chiamato, iteriamo su tutti i listener:

@[code lang=java transclude={21-22}](@/reference/latest/src/main/java/com/example/docs/event/SheepShearCallback.java)

Poi chiamiamo il nostro metodo (in questo caso, `interact`) sul listener per ottenera la sua risposta:

@[code lang=java transclude={33-33}](@/reference/latest/src/main/java/com/example/docs/event/SheepShearCallback.java)

Se il listener dice che dobbiamo cancellare (`ActionResult.FAIL`), oppure finire completamente (`ActionResult.SUCCESS`), la callback restituisce il risultato e finische il loop. `ActionResult.PASS` si sposta sul prossimo listener, e nella maggior parte dei casi dovrebbe risultare un successo se non ci sono altri listener registrati:

@[code lang=java transclude={25-30}](@/reference/latest/src/main/java/com/example/docs/event/SheepShearCallback.java)

Possiamo aggiungere commenti Javadoc in cima alle classi di callback per documentare cosa ogni \`ActionResult fa. Nel nostro caso, potrebbe essere:

@[code lang=java transclude={9-16}](@/reference/latest/src/main/java/com/example/docs/event/SheepShearCallback.java)

### Innescare l'Evento da un Mixin

Ora abbiamo lo scheltro di base dell'evento, ma nessuno modo di innescarlo. Siccome vogliamo avere l'evento chiamato quando un giocatore prova a tosare una pecora, chiamiamo l'event `invoker` dentro `SheepEntity#interactMob` quando `sheared()` viene chiamato (ovvero quando la pecora può essere tosata, e il giocatore sta tendendo delle cesoie):

@[code lang=java transcludeWith=:::](@/reference/latest/src/main/java/com/example/docs/mixin/event/SheepEntityMixin.java)

### Creare un Implementazione di Prova

Ora dobbiamo testare il nostro evento. Puoi registrare un listener nel tuo metodo di inizalizzazione (o in un'altra area, se preferisci) e aggiungere la logica custom lì. Qui c'è un esempio che droppa un diamante anziché lana ai piedi della pecora:

@[code lang=java transcludeWith=:::3](@/reference/latest/src/main/java/com/example/docs/event/FabricDocsReferenceEvents.java)

Se entri nel gioco e tosi una percora, un diamante dovrebbe essere droppato anziché lana.
