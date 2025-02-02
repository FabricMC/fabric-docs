---
title: Suoni Dinamici
description: Crea suoni più dinamici e interattivi
authors:
  - JR1811
---

# Suoni Dinamici {#create-dynamic-and-interactive-sounds}

:::info
Questa pagina è basata sulle pagine [Riprodurre Suoni](../sounds/using-sounds) e [Creare Suoni Personalizzati](../sounds/custom)!
:::

## Problemi con i `SoundEvents` {#problems-with-soundevents}

Come abbiamo imparato nella pagina [Usare i Suoni](../sounds/using-sounds), è preferibile usare i `SoundEvent` sul lato logico server, anche se è un po' controintuitivo. Dopo tutto, è il client che deve gestire il suono, trasmesso alle tue cuffie, no?

Questo modo di pensare è corretto. Tecnicamente è il lato client che dovrebbe gestire l'audio. Tuttavia, per la semplice riproduzione di un `SoundEvent`, il lato server ha preparato un passo enorme che potrebbe non essere ovvio a prima vista. Quali client dovrebbero poter sentire quel suono?

Usare il suono sul lato logico server risolverà il problema della trasmissione dei `SoundEvent`. Per farla semplice, a ogni client (`ClientPlayerEntity`) nel raggio tracciato viene inviato un pacchetto di rete per riprodurre un certo suono specifico. L'evento audio viene praticamente trasmesso dal lato logico server, a ogni client partecipante, senza che tu te ne debba preoccupare. Il suono è riprodotto una volta sola, con i valori di volume e tono specificati.

Ma, e se questo non bastasse? E se il suono dovesse essere riprodotto in loop? E se dovesse cambiare il volume e il tono in maniera dinamica durante la riproduzione? Tutto questo magari in base a valori provenienti da cose come `Entities` o `BlockEntities`?

La semplice strategia di usare i `SoundEvent` sul lato logico server non basta in questo caso.

## Preparare il File Audio {#preparing-the-audio-file}

Creeremo un nuovo audio che andrà in **loop** per un altro `SoundEvent`. Se riesci a trovare un file audio che già va in loop perfettamente, ti basta seguire i passaggi da [Creare Suoni Personalizzati](../sounds/custom). Se il suono non va ancora in loop perfettamente, dobbiamo prepararlo affinché faccia ciò.

Ripeto, la maggior parte delle DAW (Workstation Audio Digitali) moderne dovrebbero essere in grado di fare questo, ma preferisco usare [Reaper](https://www.reaper.fm/) se la modifica dell'audio è un po' più avanzata.

### Configurazione {#set-up}

Il [suono da cui partiamo](https://freesound.org/people/el-bee/sounds/644881/) proverrà da un motore.

<audio controls>
    <source src="/assets/develop/sounds/dynamic-sounds/step_0.wav" type="audio/wav">
    
    Il tuo browser non supporta l'elemento audio.
</audio>

Carichiamo il file nella DAW che abbiamo scelto.

![Reaper in cui si è caricato il file audio](/assets/develop/sounds/dynamic-sounds/step_0.png)

Possiamo sentire e notare che il motore viene avviato all'inizio e interrotto alla fine, il che non è ottimo per i suoni in loop. Tagliamo via quelle parti e regoliamo le maniglie della selezione del tempo perché corrispondano con la nuova lunghezza. Attiva anche la modalità `Toggle Repeat` così che l'audio vada in loop mentre lo regoliamo.

![File audio tagliato](/assets/develop/sounds/dynamic-sounds/step_1.png)

### Rimuovere Elementi Audio Dirompenti {#removing-disruptive-audio-elements}

Se ascoltiamo con attenzione, c'è un segnale acustico sullo sfondo, che potrebbe provenire dalla macchina. Penso che questo non suonerebbe bene nel gioco, per cui proviamo a toglierlo.

È un suono costante, la cui frequenza rimane al di sopra della lunghezza dell'audio. Per questo un semplice filtro EQ dovrebbe essere sufficiente per filtrarlo.

Reaper include già un filtro EQ chiamato "ReaEQ". Questo potrebbe essere localizzato in altri posti o avere nomi diversi in altre DAW, ma l'utilizzo dell'EQ è uno standard nella maggior parte delle DAW odierne.

Se sei sicuro che la tua DAW non disponga di un filtro EQ, cerca alternative VST gratuite online che tu possa installare sulla DAW di tua scelta.

In Reaper usa la Finestra Effetti per aggiungere l'effetto audio "ReaEQ", o altri EQ.

![Aggiungere un filtro EQ](/assets/develop/sounds/dynamic-sounds/step_2.png)

Riproducendo adesso l'audio, tenendo la finestra del filtro EQ aperta, il filtro EQ mostrerà l'audio in entrata sul display.
Lì si notano tante protuberanze.

![Identificazione del problema](/assets/develop/sounds/dynamic-sounds/step_3.png)

Se non sei un ingegnere audio allenato, questa parte è abbastanza sperimentazione, andando a tentativi. C'è una protuberanza piuttosto notevole tra i nodi 2 e 3. Muoviamo i nodi in modo che si abbassi la frequenza solo per quella parte.

![Frequenza problematica abbassata](/assets/develop/sounds/dynamic-sounds/step_4.png)

Inoltre altri effetti si possono ottenere con un semplice filtro EQ. Per esempio, tagliare le frequenze alte e/o basse può dare l'impressione di suoni trasmessi via audio.

Puoi anche sovrapporre più file audio, cambiare il tono, aggiungere del riverbero o usare effetti di suono più elaborati come "bit-crusher". La progettazione di suoni può essere divertente, soprattutto se trovi delle belle variazioni di suono dei tuoi audio per caso. La sperimentazione è la chiave, e magari alla fine il tuo suono sarà meglio di come è cominciato.

Andremo avanti con il filtro EQ soltanto, poiché l'abbiamo usato per togliere la frequenza problematica.

### Paragone {#comparison}

Confrontiamo il file originale con la versione ritoccata.

Nel suono originale puoi distinguere un segnale acustico proveniente forse da un elemento elettrico del motore.

<audio controls>
    <source src="/assets/develop/sounds/dynamic-sounds/step_5_first.ogg" type="audio/ogg">
    
    Il tuo browser non supporta l'elemento audio.
</audio>

Con il filtro EQ siamo riusciti a toglierlo quasi del tutto. Sicuramente è più piacevole da sentire.

<audio controls>
    <source src="/assets/develop/sounds/dynamic-sounds/step_5_second.ogg" type="audio/ogg">
    
    Il tuo browser non supporta l'elemento audio.
</audio>

### Fare in Modo che Vada in Loop {#making-it-loop}

Se lasciassimo che il suono si riproduca fino alla fine e ricominci dall'inizio, possiamo chiaramente sentire quando avviene la transizione. L'obiettivo è liberarci di questo applicando una transizione fluida.

Inizia tagliando via una parte dal fondo, grande quanto vuoi che sia lunga la transizione, e posizionala all'inizio di una nuova traccia audio.
In Reaper puoi dividere l'audio semplicemente muovendo il cursore dalla posizione del taglio e premendo <kbd>S</kbd>.

![Tagliare il fondo e muoverlo ad una nuova traccia](/assets/develop/sounds/dynamic-sounds/step_6.png)

Potresti dover copiare l'effetto audio EQ della prima traccia audio anche sulla seconda.

Ora lascia che la parte in fondo della nuova traccia si dissolva in chiusura, e che l'inizio della prima traccia audio si dissolva in apertura.

![Loop con effetti di dissolvenza sulle tracce audio](/assets/develop/sounds/dynamic-sounds/step_7.png)

### Esportazione {#exporting}

Esporta l'audio con le due tracce, ma con un solo canale audio (Mono) e crea un nuovo `SoundEvent` per quel file `.ogg` nella tua mod.
Se non sei certo di come farlo, controlla la pagina [Creare Suoni Personalizzati](../sounds/custom).

Ciò che abbiamo chiamato `ENGINE_LOOP` è il suono in loop del motore completato per il `SoundEvent`.

<audio controls>
    <source src="/assets/develop/sounds/dynamic-sounds/step_8.ogg" type="audio/ogg">
    
    Il tuo browser non supporta l'elemento audio.
</audio>

## Usare una `SoundInstance` {#using-a-soundinstance}

Per riprodurre suoni sul lato client, è necessaria una `SoundInstance`. Tuttavia usano comunque un `SoundEvent`.

Se vuoi solo riprodurre qualcosa come un clic su un elemento dell'interfaccia grafica, esiste già la classe `PositionedSoundInstance`.

Tieni a mente che questo sarà riprodotto solo sul client che ha eseguito questa parte del codice in particolare.

@[code lang=java transcludeWith=:::1](@/reference/latest/src/client/java/com/example/docs/FabricDocsDynamicSound.java)

:::warning
Si prega di notare che la classe `AbstractSoundInstance`, da cui le `SoundInstance` ereditano, ha l'annotazione `@Environment(EnvType.CLIENT)`.

Questo significa che questa classe (e tutte le sue sottoclassi) saranno disponibili solo lato client.

Se provassi ad usarla in un contesto logico lato server, potresti non notare inizialmente il problema in Giocatore Singolo, ma un server in ambiente Multiplayer crasherà, poiché non riuscirà a trovare quella parte del codice.

Se trovi difficoltà con queste questioni, si consiglia di creare la tua mod dal [Generatore di Mod Modello online](https://fabricmc.net/develop/template)
attivando l'opzione `Split client and common sources`.
:::

---

Una `SoundInstance` può essere molto più potente rispetto a una semplice riproduzione di un suono una volta.

Dai un'occhiata alla classe `AbstractSoundInstance` e a quali tipi di valori può tenere in considerazione.
A parte le solite variabili di volume e tono, memorizza anche le coordinate XYZ, e un'opzione perché si ripeta dopo aver finito il `SoundEvent`.

Poi, dando un'occhiata alla sua sottoclasse `MovingSoundInstance`, viene anche introdotta l'interfaccia `TickableSoundInstance`, che aggiunge funzionalità legate ai tick alla `SoundInstance`.

Per cui per sfruttare queste utilità, basta creare una nuova classe per la tua `SoundInstance` personalizzata ed estendere `MovingSoundInstance`.

@[code lang=java transcludeWith=:::1](@/reference/latest/src/client/java/com/example/docs/sound/instance/CustomSoundInstance.java)

Usare la tua `Entity` o il tuo `BlockEntity` personalizzati invece dell'istanza basilare `LivingEntity` può darti ancora più controllo, per esempio nel metodo `tick()` basato su metodi accessori, ma non devi per forza far riferimento ad una fonte di suono del genere. Invece di ciò puoi anche accedere alla `BlockPos` da altrove, o addirittura impostarla manualmente una volta sola nel costruttore soltanto.

Tieni solo a mente che tutti gli oggetti a cui fai riferimento nella `SoundInstance` sono le versioni lato client.
In certe circostanze specifiche, le proprietà di un'entità dal lato logico server possono differire dalla controparte lato client.
Se noti che i tuoi valori non si allineano, assicurati che siano sincronizzati o con i `TrackedData` dell'entità, o con pacchetti S2C (da server a client) di `BlockEntity` o con pacchetti di rete S2C completamente personalizzati.

Dopo aver finito di creare la tua `SoundInstance` personalizzata, è tutto pronto per usarla ovunque, a condizione che venga eseguita lato client con il gestore di suoni.
Allo stesso modo, puoi anche fermare la `SoundInstance` personalizzata manualmente, se necessario.

@[code lang=java transcludeWith=:::2](@/reference/latest/src/client/java/com/example/docs/FabricDocsDynamicSound.java)

Il loop di suono sarà adesso riprodotto solo sul client che ha eseguito quella `SoundInstance`. In questo caso, il suono seguirà la `ClientPlayerEntity` stessa.

Così abbiamo concluso la spiegazione di come creare e usare una semplice `SoundInstance` personalizzata.

## Istanze di Suoni Avanzate {#advanced-sound-instances}

:::warning
Il contenuto seguente tratta di un argomento avanzato.

Da questo punto in poi è necessaria una conoscenza solida di Java, programmazione a oggetti, tipi generici e sistemi di callback.

Conoscere anche le `Entities`, i `BlockEntities` e il networking personalizzato aiuterà anche molto nella comprensione di questi casi e delle applicazioni pratiche dei suoni avanzati.
:::

Per mostrare un esempio di come si possano creare sistemi di `SoundInstance` più elaborati, aggiungeremo funzionalità ulteriori, astrazioni e utilità per rendere il lavoro con suoni del genere in contesti più larghi più semplice, dinamico e flessibile.

### Teoria {#theory}

Ragioniamo un po' riguardo al nostro obiettivo con la `SoundInstance`.

- Il suono deve rimanere in loop fintanto che il `EngineBlockEntity` personalizzato a esso collegato è in esecuzione
- La `SoundInstance` dovrebbe muoversi, seguendo la posizione del `EngineBlockEntity` personalizzato _(La `BlockEntity` non si muoverà, per cui questo sarebbe più utile con le `Entities`)_
- Ci servono transizioni fluide. L'accensione e lo spegnimento non dovrebbero mai essere istantanei.
- Cambiare il volume e il tono in base a fattori esterni (per esempio della fonte del suono)

Insomma, dobbiamo tener traccia di un'istanza di un `BlockEntity` personalizzato, regolare i valori di volume e tono mentre la `SoundInstance` è in esecuzione in base a valori provenienti da quel `BlockEntity` personalizzato, e implementare "Stati di Transizione".

Se hai intenzione di creare più `SoundInstance` diverse, che si comportino in maniere diverse, consiglierei di creare una nuova classe astratta `AbstractDynamicSoundInstance`, che implementi il comportamento predefinito e lasciare che le classi `SoundInstance` la estendano.

Se invece ne userai solo una, puoi saltare la creazione della superclasse astratta, e implementare invece quella funzionalità direttamente nella tua classe `SoundInstance` personalizzata.

Sarebbe anche una buona idea avere una posizione centralizzata dove le `SoundInstance` vengono tracciate, riprodotte e interrotte. In altre parole deve gestire i segnali in ingresso, come quelli provenienti da pacchetti di rete S2C personalizzati, elencare tutte le istanze in esecuzione e gestire i casi particolari, per esempio quali suoni è permesso riprodurre allo stesso tempo e quali suoni disattiverebbero potenzialmente altri suoni se attivati.
Per fare ciò si può creare una nuova classe `DynamicSoundManager`, per interagire con questo sistema sonoro facilmente.

Tutto sommato il nostro sistema sonoro potrebbe avere questo aspetto, una volta completato.

![Struttura del Sistema Sonoro Personalizzato](/assets/develop/sounds/dynamic-sounds/custom-dynamic-sound-handling.jpg)

:::info
Tutti questi enum, interfacce e classi saranno create da zero. Adatta il sistema e le utilità alla tua situazione specifica.
Questo è solo un esempio su come ci si può approcciare ad argomenti del genere.
:::

### Interfaccia `DynamicSoundSource` {#dynamicsoundsource-interface}

Se scegli di creare una nuova classe `AbstractDynamicSoundInstance` personalizzata e più modulare, da usare come superclasse, potresti voler fare riferimento non solo ad un tipo singolo di `Entity` ma a più tipi diversi, o anche ad un `BlockEntity`.

In quel caso la chiave è sfruttare l'astrazione.
Invece di fare riferimento, per esempio, direttamente a un `BlockEntity` personalizzato, tener solo conto di un'interfaccia che fornisce i dati risolve quel problema.

D'ora in poi useremo un'interfaccia personalizzata chiamata `DynamicSoundSource`. È da implementare in tutte le classi che vogliono sfruttare quelle funzionalità di suoni dinamici, come il tuo `BlockEntity` personalizzato, le entità o addirittura, usando Mixin, su classi preesistenti come `ZombieEntity`. In pratica contiene solo i dati necessari della fonte di suono.

@[code lang=java transcludeWith=:::1](@/reference/latest/src/main/java/com/example/docs/sound/DynamicSoundSource.java)

Dopo aver creato questa interfaccia, assicurati di implementarla anche nelle classi ove necessario.

:::info
Questa è un'utilità che potrebbe essere usata sia lato client sia lato logico server.

Per cui questa interfaccia dovrebbe essere memorizzata nei package comuni, non in quelli esclusivi del client, se usi l'opzione "split sources".
:::

### Enum `TransitionState` {#transitionstate-enum}

Come detto prima, puoi interrompere la `SoundInstance` con il `SoundManager` del client, ma questo farà in modo che la `SoundInstance` si zittisca immediatamente.
Il nostro obiettivo non è, quando si riceve un segnale di stop, di interrompere il suono ma di eseguire una fase di chiusura del suo "Stato di Transizione". Solo dopo che la fase di chiusura è stata completata, si deve interrompere la `SoundInstance` personalizzata.

Uno `TransitionState` è un enum appena creato, che contiene tre valori. Essi verranno usati per tener traccia della fase in cui si trova il suono.

- Fase `STARTING`: iniziando dal silenzio, il volume del suono aumenta lentamente
- Fase `RUNNING`: il suono viene riprodotto normalmente
- Fase `ENDING`: iniziando dal volume originario esso diminuisce lentamente fino al silenzio

Tecnicamente basterebbe un semplice enum con le fasi.

```java
public enum TransitionState {
    STARTING, RUNNING, ENDING
}
```

Ma quando quei valori vengono inviati tramite rete, potresti voler definire un `Identifier` per esse o anche aggiungere altri valori personalizzati.

@[code lang=java transcludeWith=:::1](@/reference/latest/src/main/java/com/example/docs/sound/TransitionState.java)

:::info
Ripeto, se stai usando le "split sources" devi ragionare su dove si posiziona questo enum.
Tecnicamente, solo le `SoundInstance` personalizzate, che sono solo disponibili lato client, useranno quei valori dell'enum.

Ma se questo enum venisse usato altrove, per esempio in pacchetti di rete personalizzati, potresti anche dover mettere l'enum nei package comuni invece dei package esclusivi del client.
:::

### Interfaccia `SoundInstanceCallback` {#soundinstancecallback-interface}

Questa interfaccia viene usata come callback. Per ora ci basta un metodo `onFinished`, ma puoi anche aggiungere i tuoi metodi personalizzati se dovessi inviare anche altri segnali dall'oggetto `SoundInstance`.

@[code lang=java transcludeWith=:::1](@/reference/latest/src/client/java/com/example/docs/sound/instance/SoundInstanceCallback.java)

Implementa quest'interfaccia in qualsiasi classe che deve poter gestire segnali in ingresso, per esempio la `AbstractDynamicSoundInstance` che presto creeremo, e aggiungi le funzionalità nella `SoundInstance` personalizzata stessa.

### Classe `AbstractDynamicSoundInstance` {#abstractdynamicsoundinstance-class}

Finalmente cominciamo a lavorare sul cuore del sistema di `SoundInstance` dinamiche. `AbstractDynamicSoundInstance` è la classe `abstract` che dobbiamo creare.
Essa implementa le funzioni e utilità predefinite caratteristiche della nostra `SoundInstance` personalizzata, che da essa erediterà.

Possiamo considerare la `CustomSoundInstance` di [poco fa](#using-a-soundinstance) e migliorarla.
Invece della `LivingEntity` faremo ora riferimento alla `DynamicSoundSource`.
Inoltre definiremo altre proprietà.

- `TransitionState` per tener conto della fase attuale
- Durate in tick di quanto lunghe sono le fasi d'inizio e di fine
- Valori minimi e massimi per volume e tono
- Valore booleano per indicare se questa istanza è terminata e può essere ripulita
- Gestori dei tick per tener traccia del progresso corrente del suono
- Un callback che restituisce un segnale finale al `DynamicSoundManager` per la ripulizia, quando la `SoundInstance` è terminata

@[code lang=java transcludeWith=:::1](@/reference/latest/src/client/java/com/example/docs/sound/AbstractDynamicSoundInstance.java)

Poi configuriamo i valori iniziali predefiniti per la `SoundInstance` personalizzata nel costruttore della classe astratta.

@[code lang=java transcludeWith=:::2](@/reference/latest/src/client/java/com/example/docs/sound/AbstractDynamicSoundInstance.java)

Dopo aver finito il costruttore, devi permettere alla `SoundInstance` di essere riprodotta.

@[code lang=java transcludeWith=:::3](@/reference/latest/src/client/java/com/example/docs/sound/AbstractDynamicSoundInstance.java)

Ecco qui la parte fondamentale di questa `SoundInstance` dinamica. In base al tick corrente dell'istanza, può applicare vari valori e comportamenti.

@[code lang=java transcludeWith=:::4](@/reference/latest/src/client/java/com/example/docs/sound/AbstractDynamicSoundInstance.java)

Come puoi notare, non abbiamo ancora applicato alcuna modulazione a volume e tono qui. Applichiamo solo il comportamento condiviso.
Per cui, in questa classe `AbstractDynamicSoundInstance`, forniamo solo la struttura basilare e gli strumenti per le sottoclassi, e saranno queste a decidere il genere di modulazione che vogliono applicare.

Diamo un'occhiata ad alcuni esempi di strategie per quella modulazione del suono.

@[code lang=java transcludeWith=:::5](@/reference/latest/src/client/java/com/example/docs/sound/AbstractDynamicSoundInstance.java)

Come noti, valori normalizzati combinati con interpolazione lineare (lerp) permettodo di adattare i valori ai limiti sonori preferiti.
Tieni a mente che, aggiungendo metodi multipli che modificano lo stesso valore, devi fare attenzione e regolare il modo in cui lavorano insieme tra loro.

Ora dobbiamo solo aggiungere il resto dei metodi di utilità, e abbiamo finito la classe `AbstractDynamicSoundInstance`.

@[code lang=java transcludeWith=:::6](@/reference/latest/src/client/java/com/example/docs/sound/AbstractDynamicSoundInstance.java)

### Esempio d'Implementazione di `SoundInstance` {#example-soundinstance-implementation}

Dando un'occhiata all'effettiva classe `SoundInstance` personalizzata, che estende l'`AbstractDynamicSoundInstance` appena creata, dobbiamo solo pensare a quali condizioni porterebbero all'interruzione del suono e a quale modulazione vogliamo applicare al suono.

@[code lang=java transcludeWith=:::1](@/reference/latest/src/client/java/com/example/docs/sound/instance/EngineSoundInstance.java)

### Classe `DynamicSoundManager` {#dynamicsoundmanager-class}

Abbiamo discusso [poco fa](#using-a-soundinstance) come si riproduce e interrompe una `SoundInstance`. Per ripulire, centralizzare e gestire quelle interazioni, puoi creare un tuo gestore di `SoundInstance` personalizzato basato su di esso.

Questa nuova classe `DynamicSoundManager` gestirà le `SoundInstance` personalizzate, quindi anch'essa sarà solo disponibile lato client. Oltre a ciò, un client dovrebbe permettere l'esistenza solo di un'istanza di questa classe. Gestori multipli di suoni per un client solo non hanno molto senso, e complicano ulteriormente le interazioni.
Usiamo quindi il ["Modello di Progettazione Singleton"](https://refactoring.guru/design-patterns/singleton/java/example).

@[code lang=java transcludeWith=:::1](@/reference/latest/src/client/java/com/example/docs/sound/DynamicSoundManager.java)

Dopo aver creato la corretta struttura basilare, puoi aggiungere i metodi necessari per interagire con il sistema sonoro.

- Riprodurre suoni
- Interrompere suoni
- Controllare se un suono è attualmente in riproduzione

@[code lang=java transcludeWith=:::2](@/reference/latest/src/client/java/com/example/docs/sound/DynamicSoundManager.java)

Invece di avere solo una lista di tutte le `SoundInstance` attualmente in riproduzione, puoi anche tener traccia di quali fonti sonore stanno riproducendo quali suoni.
Per esempio, un motore che riproduce due suoni di un motore allo stesso tempo non ha senso, però più motori, ciascuno dei quali riproduce i propri suoni, è un caso valido. Per semplicità abbiamo solo creato una `List<AbstractDynamicSoundInstance>`, ma spesso una `HashMap` di una `DynamicSoundSource` e una `AbstractDynamicSoundInstance` potrebbe essere una scelta migliore.

### Usare il Sistema Sonoro Avanzato {#using-the-advanced-sound-system}

Per usare questo sistema sonoro, basta sfruttare i metodi di `DynamicSoundManager` o di `SoundInstance`. Usando `onStartedTrackingBy` e `onStoppedTrackingBy` delle entità, o anche solo networking S2C personalizzato, puoi ora avviare e interrompere le tue `SoundInstance` dinamiche personalizzate.

@[code lang=java transcludeWith=:::1](@/reference/latest/src/client/java/com/example/docs/network/ReceiveS2C.java)

Il prodotto finale può regolare il proprio volume in base alla fase di suono, per rendere fluide le transizioni, e cambiare il tono in base a un valore di stress che proviene dalla fonte sonora.

<VideoPlayer src="/assets/develop/sounds/dynamic-sounds/engine-block-sound.webm">BlockEntity Motore con cambiamenti dinamici del suono</VideoPlayer>

Potresti anche volendo aggiungere un altro valore alla tua fonte sonora, che tiene traccia di un valore "surriscaldamento", e fare in modo che una `SoundInstance` di un fischio/sibilo aumenti lentamente se il valore è sopra a 0; o ancora aggiungere una nuova interfaccia alla tua `SoundInstance` dinamica personalizzata, che assegni un valore di priorità ai tipi di suono, e permette di scegliere quale suono riprodurre se questi vanno in conflitto tra loro.

Con il sistema corrente, puoi facilmente gestire più `SoundInstance` insieme e progettare l'audio secondo i tuoi bisogni.
