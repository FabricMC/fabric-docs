---
title: Networking
description: Una guida generale sul networking con l'API di Fabric.
authors:
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

:::info
Un payload sono i dati che vengono inviati in un pacchetto.
:::

Questo può essere fatto creando un `Record` Java con un parametro `BlockPos` che implementi `CustomPayload`.

@[code lang=java transcludeWith=:::summon_Lightning_payload](@/reference/latest/src/main/java/com/example/docs/networking/basic/SummonLightningS2CPayload.java)

Allo stesso tempo, abbiamo definito:

- Un `Identifier` utilizzato per identificare il payload del nostro pacchetto. Per questo esempio, il nostro identificatore sarà `fabric-docs-reference:summon_lightning`.

@[code lang=java transclude={13-13}](@/reference/latest/src/main/java/com/example/docs/networking/basic/SummonLightningS2CPayload.java)

- Un'istanza pubblica statica di `CustomPayload.Id` per identificare in modo univoco questo payload personalizzato. Faremo riferimento a questo ID sia nel codice comune che in quello client.

@[code lang=java transclude={14-14}](@/reference/latest/src/main/java/com/example/docs/networking/basic/SummonLightningS2CPayload.java)

- Un'istanza pubblica statica di `PacketCodec` affinché il gioco sappia come serializzare/deserializzare i contenuti del pacchetto.

@[code lang=java transclude={15-15}](@/reference/latest/src/main/java/com/example/docs/networking/basic/SummonLightningS2CPayload.java)

Abbiamo anche fatto override di `getId` per restituire l'ID del nostro payload.

@[code lang=java transclude={17-20}](@/reference/latest/src/main/java/com/example/docs/networking/basic/SummonLightningS2CPayload.java)

### Registrare un Payload {#registering-a-payload}

Prima di inviare un pacchetto con il nostro payload personalizzato, dobbiamo registrarlo.

:::info
`S2C` e `C2S` sono due suffissi comunemente usati, e che significano rispettivamente _Server-to-Client_ e _Client-to-Server_.
:::

Questo si può fare nel nostro **initializer comune** usando `PayloadTypeRegistry.playS2C().register` che accetta un `CustomPayload.Id` e un `PacketCodec`.

@[code lang=java transclude={25-25}](@/reference/latest/src/main/java/com/example/docs/networking/basic/FabricDocsReferenceNetworkingBasic.java)

Esiste un metodo simile per registrare i payload da client a server: `PayloadTypeRegistry.playC2S().register`.

### Inviare un Pacchetto al Client {#sending-a-packet-to-the-client}

Per inviare un pacchetto con il nostro payload personalizzato, possiamo usare `ServerPlayNetworking.send` che accetta un `ServerPlayerEntity` e un `CustomPayload`.

Iniziamo creando il nostro oggetto Lightning Tater. Puoi fare override di `use` per attivare un'azione quando l'oggetto viene utilizzato.
In questo caso, inviamo pacchetti ai giocatori nel mondo del server.

@[code lang=java transcludeWith=:::lightning_tater_item](@/reference/latest/src/main/java/com/example/docs/networking/basic/LightningTaterItem.java)

Esaminiamo il codice sopra.

Inviamo pacchetti solo quando l'azione viene avviata sul server, uscendo anticipatamente con un controllo `isClient`:

@[code lang=java transclude={22-24}](@/reference/latest/src/main/java/com/example/docs/networking/basic/LightningTaterItem.java)

Creiamo un'istanza del payload con la posizione dell'utente:

@[code lang=java transclude={26-26}](@/reference/latest/src/main/java/com/example/docs/networking/basic/LightningTaterItem.java)

Infine, otteniamo i giocatori nel mondo del server tramite `PlayerLookup` e inviamo un pacchetto a ciascun giocatore.

@[code lang=java transclude={28-30}](@/reference/latest/src/main/java/com/example/docs/networking/basic/LightningTaterItem.java)

:::info
L'API di Fabric fornisce `PlayerLookup`, una serie di funzioni ausiliarie che andranno a cercare i giocatori in un server.

Un termine usato di frequente per descrivere la funzionalità di questi metodi è "_tracking_" (_tracciamento_). Ciò significa che un'entità o un chunk sul server sono noti al client di un giocatore (all'interno della sua distanza di visualizzazione), e che l'entità o l'entità-blocco dovrebbe notificare i client che la stanno tracciando di eventuali cambiamenti.

Il tracciamento è un concetto importante per un networking efficiente, così che solo i giocatori necessari vengano notificati dei cambiamenti tramite l'invio di pacchetti.
:::

### Ricevere un Pacchetto sul Client {#receiving-a-packet-on-the-client}

Per ricevere un pacchetto inviato da un server sul client, devi specificare come gestirai il pacchetto in arrivo.

Questo si può fare nell'**initializer del client**, chiamando `ClientPlayNetworking.registerGlobalReceiver` e passando un `CustomPayload.Id` e un `PlayPayloadHandler`, che è un'interfaccia funzionale.

In questo caso, definiremo l'azione da attivare all'interno dell'implementazione di `PlayPayloadHandler` (come espressione lambda).

@[code lang=java transcludeWith=:::client_global_receiver](@/reference/latest/src/client/java/com/example/docs/network/basic/FabricDocsReferenceNetworkingBasicClient.java)

Esaminiamo il codice sopra.

Possiamo accedere ai dati dal nostro payload chiamando i metodi getter del Record. In questo caso `payload.pos()`, che può poi essere usato per ottenere le coordinate `x`, `y` e `z`.

@[code lang=java transclude={32-32}](@/reference/latest/src/client/java/com/example/docs/network/basic/FabricDocsReferenceNetworkingBasicClient.java)

Infine, creiamo una `LightningEntity` e aggiungiamola al mondo.

@[code lang=java transclude={33-38}](@/reference/latest/src/client/java/com/example/docs/network/basic/FabricDocsReferenceNetworkingBasicClient.java)

Ora, aggiungendo questa mod a un server, e quando un giocatore usa il nostro oggetto Lightning Tater, ogni giocatore vedrà un fulmine colpire la posizione dell'utente.

<VideoPlayer src="/assets/develop/networking/summon-lightning.webm">Evocazione di un lampo con una Lightning Tater</VideoPlayer>

### Inviare un Pacchetto al Server {#sending-a-packet-to-the-server}

Come per l'invio di un pacchetto al client, iniziamo creando un payload personalizzato. Questa volta, quando un giocatore usa una Patata Velenosa su un'entità vivente, chiediamo al server di applicare l'effetto Luminescenza su di essa.

@[code lang=java transcludeWith=:::give_glowing_effect_payload](@/reference/latest/src/main/java/com/example/docs/networking/basic/GiveGlowingEffectC2SPayload.java)

Passiamo il codec appropriato insieme a un riferimento al metodo per ottenere il valore dal Record per costruire questo codec.

Poi registriamo il nostro payload nel nostro **initializer comune**. Ma, questa volta, come payload _Client-to-Server_, utilizzando `PayloadTypeRegistry.playC2S().register`.

@[code lang=java transclude={26-26}](@/reference/latest/src/main/java/com/example/docs/networking/basic/FabricDocsReferenceNetworkingBasic.java)

Per inviare un pacchetto, aggiungiamo un'azione per quando il giocatore usa una Patata Velenosa. Utilizzeremo l'evento `UseEntityCallback` per mantenere il codice conciso.

Registriamo l'evento nel nostro **initializer del client**, e usiamo `isClient()` per assicurarci che l'azione venga attivata solo sul client logico.

@[code lang=java transcludeWith=:::use_entity_callback](@/reference/latest/src/client/java/com/example/docs/network/basic/FabricDocsReferenceNetworkingBasicClient.java)

Creiamo un'istanza del nostro `GiveGlowingEffectC2SPayload` con gli argomenti necessari. In questo caso, l'ID di rete dell'entità bersaglio.

@[code lang=java transclude={51-51}](@/reference/latest/src/client/java/com/example/docs/network/basic/FabricDocsReferenceNetworkingBasicClient.java)

Infine, inviamo un pacchetto al server chiamando `ClientPlayNetworking.send` con l'istanza del nostro `GiveGlowingEffectC2SPayload`.

@[code lang=java transclude={52-52}](@/reference/latest/src/client/java/com/example/docs/network/basic/FabricDocsReferenceNetworkingBasicClient.java)

### Ricevere un Pacchetto sul Server {#receiving-a-packet-on-the-server}

Questo può essere fatto nell'**initializer comune**, chiamando `ServerPlayNetworking.registerGlobalReceiver` e passando un `CustomPayload.Id` e un `PlayPayloadHandler`.

@[code lang=java transcludeWith=:::server_global_receiver](@/reference/latest/src/main/java/com/example/docs/networking/basic/FabricDocsReferenceNetworkingBasic.java)

:::info
È importante che convalidare il contenuto del pacchetto lato server.

In questo caso, verifichiamo se l'entità esiste in base al suo ID di rete.

@[code lang=java transclude={30-30}](@/reference/latest/src/main/java/com/example/docs/networking/basic/FabricDocsReferenceNetworkingBasic.java)

Inoltre, l'entità-bersaglio deve essere un'entità vivente, e limitiamo la distanza tra il giocatore e l'entità-bersaglio a 5.

@[code lang=java transclude={32-32}](@/reference/latest/src/main/java/com/example/docs/networking/basic/FabricDocsReferenceNetworkingBasic.java)
:::

Ora, quando un giocatore cerca di usare una Patata Velenosa su un'entità vivente, l'effetto Luminescenza verrà applicato.

<VideoPlayer src="/assets/develop/networking/use-poisonous-potato.webm">Effetto Luminescenza applicato quando una Patata Velenosa viene usata su un'entità vivente</VideoPlayer>
