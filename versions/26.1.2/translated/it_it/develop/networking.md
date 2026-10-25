---
title: Networking
description: Una guida generale sul networking con l'API di Fabric.
authors:
  - bluebear94
  - Daomephsta
  - dicedpixels
  - Earthcomputer
  - FlooferLand
  - FxMorin
  - i509VCB
  - modmuss50
  - natanfudge
  - NetUserGet
  - NShak
  - parzivail
  - skycatminepokie
  - SolidBlock-cn
  - Voleil
  - Wxffel
  - YTG123-Mods
  - zulrang
---

Il networking in Minecraft si usa affinché client e server possano comunicare tra loro. Il networking è un argomento ampio, per cui questa pagina è suddivisa in diverse categorie.

## Qual È l'Importanza del Networking? {#why-is-networking-important}

I pacchetti sono il concetto fondamentale del networking in Minecraft.
I pacchetti sono costituiti da dati arbitrari che possono essere inviati dal server al client o dal client al server.
Dai un'occhiata al diagramma qui sotto, che fornisce una rappresentazione visiva dell'architettura di rete in Fabric:

![Architettura Bilaterale](/assets/develop/networking/sides.png)

Nota come i pacchetti fungano da ponte tra il server e il client; questo perché quasi tutto ciò che fai nel gioco coinvolge il networking in qualche modo, che tu lo sappia o meno.
Ad esempio, quando invii un messaggio in chat, un pacchetto viene inviato al server con il contenuto.
Il server quindi invia un altro pacchetto a tutti gli altri client con il tuo messaggio.

Una cosa importante da tenere a mente è che c'è sempre un server in esecuzione, anche in singleplayer o LAN. I pacchetti vengono ancora utilizzati per la comunicazione tra client e server, anche quando nessun altro sta giocando con te. Quando si parla di lati nel networking, si utilizzano i termini "**client logico**" e "**server logico**". Il server integrato per singleplayer/LAN e il server dedicato sono entrambi server **logici**, ma solo il server dedicato può essere considerato un server **fisico**.

Quando lo stato non è sincronizzato tra client e server, possono verificarsi problemi in cui il server o altri client non concordano su ciò che un altro client sta facendo. Questo fenomeno è spesso noto come "desync". Quando scrivi la tua mod, potresti dover inviare un pacchetto di dati per mantenere sincronizzato lo stato del server e di tutti i client.

## Introduzione al Networking {#an-introduction-to-networking}

### Definire un Payload {#defining-a-payload}

::: info

Un payload sono i dati che vengono inviati in un pacchetto.

:::

Questo può essere fatto creando un `Record` Java con un parametro `BlockPos` che implementi `CustomPacketPayload`.

<<< @/reference/26.1.2/src/main/java/com/example/docs/networking/basic/ClientboundSummonLightningPayload.java#summon_lightning_payload

Allo stesso tempo, abbiamo definito:

- Un `Identifier` utilizzato per identificare il payload del nostro pacchetto. Per questo esempio, il nostro identificatore sarà `example-mod:summon_lightning`.

<<< @/reference/26.1.2/src/main/java/com/example/docs/networking/basic/ClientboundSummonLightningPayload.java#identifier

- Un'istanza pubblica statica di `CustomPayload.Type` per identificare in modo univoco questo payload personalizzato. Faremo riferimento a questo ID sia nel codice comune che in quello client.

<<< @/reference/26.1.2/src/main/java/com/example/docs/networking/basic/ClientboundSummonLightningPayload.java#payload_type

- Un'istanza pubblica statica di `StreamCodec` affinché il gioco sappia come serializzare/deserializzare i contenuti del pacchetto.

<<< @/reference/26.1.2/src/main/java/com/example/docs/networking/basic/ClientboundSummonLightningPayload.java#stream_codec

Abbiamo anche fatto override di `type` per restituire l'ID del nostro payload.

<<< @/reference/26.1.2/src/main/java/com/example/docs/networking/basic/ClientboundSummonLightningPayload.java#type

### Registrare un Payload {#registering-a-payload}

Prima di inviare un pacchetto con il nostro payload personalizzato, dobbiamo registrarlo su entrambi i lati fisici.

Questo si può fare nel nostro **initializer comune** usando `PayloadTypeRegistry.clientboundPlay().register` che accetta un `CustomPayload.Type` e un `StreamCodec`.

<<< @/reference/26.1.2/src/main/java/com/example/docs/networking/basic/ExampleModNetworkingBasic.java#clientbound

Esiste un metodo simile per registrare i payload da client a server: `PayloadTypeRegistry.serverboundPlay().register`.

### Inviare un Pacchetto al Client {#sending-a-packet-to-the-client}

Per inviare un pacchetto con il nostro payload personalizzato, possiamo usare `ServerPlayNetworking.send` che accetta un `ServerPlayer` e un `CustomPayload`.

Iniziamo creando il nostro oggetto Lightning Tater. Puoi fare override di `use` per attivare un'azione quando l'oggetto viene utilizzato.
In questo caso, inviamo pacchetti ai giocatori nel livello del server.

<<< @/reference/26.1.2/src/main/java/com/example/docs/networking/basic/LightningTaterItem.java#lightning_tater_item

Esaminiamo il codice sopra.

Inviamo pacchetti solo quando l'azione viene avviata sul server, uscendo anticipatamente con un controllo `isClientSide()`:

<<< @/reference/26.1.2/src/main/java/com/example/docs/networking/basic/LightningTaterItem.java#client_check

Creiamo un'istanza del payload con la posizione dell'utente:

<<< @/reference/26.1.2/src/main/java/com/example/docs/networking/basic/LightningTaterItem.java#payload_instance

Infine, otteniamo i giocatori nel livello del server tramite `PlayerLookup` e inviamo un pacchetto a ciascun giocatore.

<<< @/reference/26.1.2/src/main/java/com/example/docs/networking/basic/LightningTaterItem.java#lookup

::: info

L'API di Fabric fornisce `PlayerLookup`, una serie di funzioni ausiliarie che andranno a cercare i giocatori in un server.

Un termine usato di frequente per descrivere la funzionalità di questi metodi è "_tracking_" (_tracciamento_). Ciò significa che un'entità o un chunk sul server sono noti al client di un giocatore (all'interno della sua distanza di visualizzazione), e che l'entità o l'entità-blocco dovrebbe notificare i client che la stanno tracciando di eventuali cambiamenti.

Il tracciamento è un concetto importante per un networking efficiente, così che solo i giocatori necessari vengano notificati dei cambiamenti tramite l'invio di pacchetti.

:::

### Ricevere un Pacchetto sul Client {#receiving-a-packet-on-the-client}

Per ricevere un pacchetto inviato da un server sul client, devi specificare come gestirai il pacchetto in arrivo.

Questo si può fare nell'**initializer del client**, chiamando `ClientPlayNetworking.registerGlobalReceiver` e passando un `CustomPayload.Type` e un `PlayPayloadHandler`, che è un'interfaccia funzionale.

In questo caso, definiremo l'azione da attivare all'interno dell'implementazione di `PlayPayloadHandler` (come espressione lambda).

<<< @/reference/26.1.2/src/client/java/com/example/docs/network/basic/ExampleModNetworkingBasicClient.java#client_global_receiver

Esaminiamo il codice sopra.

Possiamo accedere ai dati dal nostro payload chiamando i metodi getter del Record. In questo caso `payload.pos()`. Che può poi essere usato per ottenere le coordinate `x`, `y` e `z`.

<<< @/reference/26.1.2/src/client/java/com/example/docs/network/basic/ExampleModNetworkingBasicClient.java#payload_pos

Infine, creiamo una `LightningBolt` e aggiungiamola al livello.

<<< @/reference/26.1.2/src/client/java/com/example/docs/network/basic/ExampleModNetworkingBasicClient.java#lightning_bolt

Ora, aggiungendo questa mod a un server, e quando un giocatore usa il nostro oggetto Lightning Tater, ogni giocatore vedrà un fulmine colpire la posizione dell'utente.

<VideoPlayer src="/assets/develop/networking/summon-lightning.webm">Evocazione di un lampo con una Lightning Tater</VideoPlayer>

### Inviare un Pacchetto al Server {#sending-a-packet-to-the-server}

Come per l'invio di un pacchetto al client, iniziamo creando un payload personalizzato. Questa volta, quando un giocatore usa una Patata Velenosa su un'entità vivente, chiediamo al server di applicare l'effetto Luminescenza su di essa.

<<< @/reference/26.1.2/src/main/java/com/example/docs/networking/basic/GiveGlowingEffectServerboundPayload.java#give_glowing_effect_payload

Passiamo il codec appropriato insieme a un riferimento al metodo per ottenere il valore dal Record per costruire questo codec.

Poi registriamo il nostro payload nel nostro **initializer comune**. Ma, questa volta, come payload _Client-to-Server_, utilizzando `PayloadTypeRegistry.serverboundPlay().register`.

<<< @/reference/26.1.2/src/main/java/com/example/docs/networking/basic/ExampleModNetworkingBasic.java#serverbound

Per inviare un pacchetto, aggiungiamo un'azione per quando il giocatore usa una Patata Velenosa. Utilizzeremo l'evento `UseEntityCallback` per mantenere il codice conciso.

Registriamo l'evento nel nostro **initializer del client**, e usiamo `isClientSide()` per assicurarci che l'azione venga attivata solo sul client logico.

<<< @/reference/26.1.2/src/client/java/com/example/docs/network/basic/ExampleModNetworkingBasicClient.java#use_entity_callback

Creiamo un'istanza del nostro `GiveGlowingEffectServerboundPayload` con gli argomenti necessari. In questo caso, l'ID di rete dell'entità bersaglio.

<<< @/reference/26.1.2/src/client/java/com/example/docs/network/basic/ExampleModNetworkingBasicClient.java#payload

Infine, inviamo un pacchetto al server chiamando `ClientPlayNetworking.send` con l'istanza del nostro `GiveGlowingEffectServerboundPayload`.

<<< @/reference/26.1.2/src/client/java/com/example/docs/network/basic/ExampleModNetworkingBasicClient.java#send

### Ricevere un Pacchetto sul Server {#receiving-a-packet-on-the-server}

Questo può essere fatto nell'**initializer comune**, chiamando `ServerPlayNetworking.registerGlobalReceiver` e passando un `CustomPayload.Type` e un `PlayPayloadHandler`.

<<< @/reference/26.1.2/src/main/java/com/example/docs/networking/basic/ExampleModNetworkingBasic.java#server_global_receiver

::: info

È importante che convalidare il contenuto del pacchetto lato server.

In questo caso, verifichiamo se l'entità esiste in base al suo ID di rete.

<<< @/reference/26.1.2/src/main/java/com/example/docs/networking/basic/ExampleModNetworkingBasic.java#validate_entity

Inoltre, l'entità-bersaglio deve essere un'entità vivente, e limitiamo la distanza tra il giocatore e l'entità-bersaglio a 5. Se le condizioni sono soddisfatte, applicheremo l'effetto:

<<< @/reference/26.1.2/src/main/java/com/example/docs/networking/basic/ExampleModNetworkingBasic.java#entity_checks

:::

Ora, quando un giocatore cerca di usare una Patata Velenosa su un'entità vivente, l'effetto Luminescenza verrà applicato.

<VideoPlayer src="/assets/develop/networking/use-poisonous-potato.webm">Effetto Luminescenza applicato quando una Patata Velenosa viene usata su un'entità vivente</VideoPlayer>
