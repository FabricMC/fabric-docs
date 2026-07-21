---
title: Codec
description: Una guida esaustiva per la comprensione e l'uso del sistema di codec di Mojang per serializzare e deserializzare gli oggetti.
authors:
  - CelDaemon
  - enjarai
  - Syst3ms
resources:
  https://docs.neoforged.net/docs/datastorage/codecs/: Codec - NeoForge Docs (EN)
  https://docs.neoforged.net/docs/networking/streamcodecs/: Stream Codec - NeoForge Docs (EN)
  https://kvverti.github.io/Documented-DataFixerUpper/snapshot/com/mojang/serialization/Codec: JavaDocs ufficiosi di DFU
  https://forge.gemwire.uk/wiki/Codecs: Codec - Wiki della community di Forge (EN)
---

Un codec è un sistema per serializzare facilmente oggetti Java, ed è incluso nella libreria DataFixerUpper (DFU) di Mojang, che è inclusa in Minecraft. Nel contesto del modding essi possono essere usati come un'alternativa a GSON e Jankson quando si leggono e si scrivono file JSON personalizzati, anche se hanno cominciato a diventare sempre più rilevanti, visto che Mojang sta riscrivendo molto suo codice in modo che usi i Codec.

I Codec vengono usati assieme a un'altra API da DFU, `DynamicOps`. Un codec definisce la struttura di un oggetto, mentre i dynamic ops vengono usati per definire un formato da cui e a cui essere serializzato, come JSON o NBT. Questo significa che qualsiasi codec può essere usato con qualsiasi dynamic ops, e viceversa, permettendo una grande flessibilità.

## Usare i Codec {#using-codecs}

### Serializzazione e Deserializzazione {#serializing-and-deserializing}

L'uso principale di un codec è serializzare e deserializzare oggetti da e a un formato specifico.

Poiché alcune classi vanilla hanno già dei codec definiti, possiamo usare quelli come un esempio. Mojang ci ha anche fornito due classi di dynamic ops predefinite, `JsonOps` e `NbtOps`, che tendono a coprire la maggior parte degli casi.

Ora, immaginiamo di voler serializzare un `BlockPos` a JSON e viceversa. Possiamo fare questo usando il codec memorizzato staticamente presso `BlockPos.CODEC` con i metodi `Codec#encodeStart` e `Codec#parse`, rispettivamente.

::: code-group

<<< @/reference/26.1.2/src/client/java/com/example/docs/datagen/ExampleModCodecExampleProvider.java#encode_blockpos[Java]

<<< @/reference/26.1.2/src/main/generated/reports/example-mod/codec_examples/serialize_blockpos.json[Output]

:::

Quando si usa un codec, i valori sono restituiti come un `DataResult`. Questo è un wrapper che può rappresentare un successo oppure un fallimento. Possiamo usare questo in diversi modi: Se vogliamo soltanto il nostro valore serializzato, `DataResult#result` restituirà semplicemente un `Optional` contenente il nostro valore, mentre `DataResult#resultOrPartial` ci permette anche di fornire una funzione per gestire qualsiasi errore che potrebbe essersi verificato. La seconda è specialmente utile per risorse di datapack personalizzati, in cui potremmo voler segnare gli errori nel log senza causare problemi altrove.

Quindi prendiamo il nostro valore serializzato e ritrasformiamolo nuovamente in un `BlockPos`:

<<< @/reference/26.1.2/src/client/java/com/example/docs/datagen/ExampleModCodecExampleProvider.java#parse_blockpos

### Codec Predefiniti {#built-in-codecs}

Come menzionato in precedenza, Mojang ha già definito codec per tante classi Java vanilla e standard, incluse, ma non solo, `BlockPos`, `BlockState`, `ItemStack`, `Identifier`, `Component`, e `Pattern` regex. I Codec per le classi di Mojang si trovano solitamente come attributi static chiamati `CODEC` della classe stessa, mentre molte altre sono mantenute nella classe `Codecs`. Bisogna anche sottolineare che tutte le registry vanilla contengono un metodo per ottenere un `Codec`, per esempio, puoi usare `BuiltInRegistries.BLOCK.byNameCodec()` per ottenere un `Codec<Block>` che serializza all'id del blocco e viceversa, e un `holderByNameCodec()` per ottenere un `Codec<Holder<Block>>`.

L'API stessa dei Codec contiene anche alcuni codec per tipi primitivi, come `Codec.INT` e `Codec.STRING`. Queste sono disponibili come statici nella classe `Codec`, e sono solitamente usate come base per codec più complessi, come spiegato sotto.

## Costruire Codec {#building-codecs}

Ora che abbiamo visto come usare i codec, vediamo come possiamo costruircene di nostri. Supponiamo di avere la seguente classe, e di voler deserializzare le sue istanze da file JSON:

<<< @/reference/26.1.2/src/main/java/com/example/docs/codec/CoolBeansClass.java#bean_class

Il corrispondente file JSON potrebbe avere il seguente aspetto:

<<< @/reference/26.1.2/src/main/generated/reports/example-mod/codec_examples/cool_beans.json

Possiamo creare un codec per questa classe mettendo insieme tanti codec più piccoli per formarne uno più grande. In questo caso, ne avremo bisogno di uno per ogni attributo:

- un `Codec<Integer>`
- un `Codec<Item>`
- un `Codec<List<BlockPos>>`

Possiamo ottenere il primo dal codec primitivo nella classe `Codec` menzionato in precedenza, nello specifico `Codec.INT`. Mentre il secondo può essere ottenuto dalla registry `BuiltInRegistries.ITEM`, che ha un metodo `byNameCodec()` che restituisce un `Codec<Item>`. Non abbiamo un codec predefinito per `List<BlockPos>`, ma possiamo crearne uno a partire da `BlockPos.CODEC`.

### Liste {#lists}

`Codec#listOf` può essere usato per creare una versione lista di qualsiasi codec:

::: code-group

<<< @/reference/26.1.2/src/client/java/com/example/docs/datagen/ExampleModCodecExampleProvider.java#list_codec[Codec]

<<< @/reference/26.1.2/src/client/java/com/example/docs/datagen/ExampleModCodecExampleProvider.java#list_codec_data[Input]

<<< @/reference/26.1.2/src/main/generated/reports/example-mod/codec_examples/list_codec.json[Output]

:::

Bisogna sottolineare che i codec creati così verranno sempre deserializzati a un'`ImmutableList`. Se invece ti servisse una lista mutabile, puoi usare [xmap](#tipi-convertibili-mutualmente-e-tu) per convertirla ad una durante la deserializzazione.

### Unire i Codec per Classi Simili ai Record {#merging-codecs-for-record-like-classes}

Ora che abbiamo codec separati per ciascun attributo, possiamo combinarli a formare un singolo codec per la nostra classe usando un `RecordCodecBuilder`. Questo suppone che la nostra classe abbia un costruttore che contiene ogni attributo che vogliamo serializzare, e che ogni attributo ha un metodo getter corrispondente. Questo lo rende perfetto per essere usato assieme ai record, ma può anche essere usato con classi regolari.

Diamo un'occhiata a come creare un codec per la nostra `CoolBeansClass`:

::: code-group

<<< @/reference/26.1.2/src/main/java/com/example/docs/codec/CoolBeansClass.java#bean_codec[Codec]

<<< @/reference/26.1.2/src/client/java/com/example/docs/datagen/ExampleModCodecExampleProvider.java#bean_codec_data[Input]

<<< @/reference/26.1.2/src/main/generated/reports/example-mod/codec_examples/cool_beans.json[Output]

:::

Ogni linea nel gruppo specifica un codec, il nome di un attributo, e un metodo getter. La chiamata a `Codec#fieldOf` è usata per convertire il codec a un [MapCodec](#mapcodec), e la chiamata a `forGetter` specifica il metodo getter usato per ottenere il valore dell'attributo da un'istanza della classe. Inoltre, la chiamata ad `apply` specifica il costruttore usato per creare nuove istanze. Nota che l'ordine degli attributi nel gruppo dovrebbe essere lo stesso di quello dei parametri nel costruttore.

Puoi anche usare `Codec#optionalFieldOf` in questo contesto per rendere un attributo opzionale, come spiegato nella sezione [Attributi Opzionali](#attributi-opzionali).

### MapCodec, Da Non Confondere Con Codec&lt;Map&gt; {#mapcodec}

La chiamata a `Codec#fieldOf` convertirà un `Codec<T>` in un `MapCodec<T>`, che è una variante, ma non una diretta implementazione di `Codec<T>`. I `MapCodec`, come suggerisce il loro nome garantiscono la serializzazione a una mappa chiave-valore, o al suo equivalente nella `DynamicOps` usata. Alcune funzioni ne potrebbero richiedere uno invece di un codec normale.

Questo modo particolare di creare un `MapCodec` racchiude sostanzialmente il valore del codec sorgente dentro una mappa, con il nome dell'attributo dato come chiave. Per esempio, un `Codec<BlockPos>` serializzato a JSON avrebbe il seguente aspetto:

<<< @/reference/26.1.2/src/main/generated/reports/example-mod/codec_examples/plain_codec.json

Ma quando viene convertito in un `MapCodec<BlockPos>` usando `BlockPos.CODEC.fieldOf("pos")`, esso ha il seguente aspetto:

<<< @/reference/26.1.2/src/main/generated/reports/example-mod/codec_examples/map_codec.json

Anche se i Map Codec vengono più frequentemente usati per essere uniti ad altri Map Codec per costruire un codec per l'intero insieme di attributi di una classe, come spiegato nella sezione [Unire i Codec per Classi simili ai Record](#unire-i-codec-per-classi-simili-ai-record) sopra, essi possono anche essere ritrasformati in codec normali usando `MapCodec#codec`, che darà lo stesso risultato di incapsulare il loro valore di input.

#### Attributi Opzionali {#optional-fields}

`Codec#optionalFieldOf` può essere usato per create una mappa codec opzionale. Esso, quando l'attributo indicato non è presente nel container durante la deserializzazione, verrà o deserializzato come un `Optional` vuoto oppure con un valore predefinito indicato.

::: code-group

<<< @/reference/26.1.2/src/client/java/com/example/docs/datagen/ExampleModCodecExampleProvider.java#optional_field[Codec]

<<< @/reference/26.1.2/src/client/java/com/example/docs/datagen/ExampleModCodecExampleProvider.java#optional_field_data[Input]

<<< @/reference/26.1.2/src/main/generated/reports/example-mod/codec_examples/optional_field.json[Output]

:::

Per aggiungere un valore predefinito, possiamo passarlo come il secondo parametro nel metodo `optionalFieldOf`.

::: code-group

<<< @/reference/26.1.2/src/client/java/com/example/docs/datagen/ExampleModCodecExampleProvider.java#default_field[Codec]

<<< @/reference/26.1.2/src/client/java/com/example/docs/datagen/ExampleModCodecExampleProvider.java#default_field_data[Input]

<<< @/reference/26.1.2/src/main/generated/reports/example-mod/codec_examples/default_field.json[Output]

:::

Nota che se l'attributo è presente, ma il valore non è valido, l'attributo non potrà essere deserializzato.

### Costanti, Vincoli, e Composizione {#constants-constraints-composition}

#### Unità {#unit}

`MapCodec.unitCodec` può essere usato per creare un codec che verrà sempre deserializzato a un valore costante, indipendentemente dall'input. Durante la serializzazione, non farà nulla.

::: code-group

<<< @/reference/26.1.2/src/client/java/com/example/docs/datagen/ExampleModCodecExampleProvider.java#unit_codec[Codec]

<<< @/reference/26.1.2/src/main/generated/reports/example-mod/codec_examples/unit.json[Output]

:::

#### Intervalli Numerici {#numeric-ranges}

`Codec.intRange` e compagnia, `Codec.floatRange` e `Codec.doubleRange` possono essere usati per creare un codec che accetta soltanto valori numerici all'interno di un intervallo **inclusivo** specificato. Questo si applica sia alla serializzazione sia alla deserializzazione.

::: code-group

<<< @/reference/26.1.2/src/client/java/com/example/docs/datagen/ExampleModCodecExampleProvider.java#numeric_range[Codec]

<<< @/reference/26.1.2/src/client/java/com/example/docs/datagen/ExampleModCodecExampleProvider.java#numeric_range_data[Input]

<<< @/reference/26.1.2/src/main/generated/reports/example-mod/codec_examples/numeric_range.json[Output]

:::

#### Coppia {#pair}

`Codec.pair` unisce due codec, `Codec<A>` e `Codec<B>`, in un `Codec<Pair<A, B>>`. Tieni a mente che funziona correttamente soltanto con codec che serializzano a un attributo specifico, come [`MapCodec` convertiti](#mapcodec) oppure [Codec di Record](#merging-codecs-for-record-like-classes).
Il codec risultante serializzerà a una mappa contenente gli attributi di entrambi i codec usati.

::: code-group

<<< @/reference/26.1.2/src/client/java/com/example/docs/datagen/ExampleModCodecExampleProvider.java#pair_codec[Codec]

<<< @/reference/26.1.2/src/client/java/com/example/docs/datagen/ExampleModCodecExampleProvider.java#pair_codec_data[Input]

<<< @/reference/26.1.2/src/main/generated/reports/example-mod/codec_examples/pair.json[Output]

:::

#### Either {#either}

`Codec.either` unisce due codec, `Codec<A>` e `Codec<B>`, in un `Codec<Either<A, B>>`. Il codec risultante tenterà, durante la deserializzazione, di usare il primo codec, e _solo se quello fallisce_, tenterà di usare il secondo.
Se anche il secondo fallisse, l'errore del _secondo_ codec verrà restituito.

#### Mappe {#maps}

Per gestire mappe con chiavi arbitrarie, come `HashMap`, `Codec.unboundedMap` può essere usato. Questo restituisce un `Codec<Map<K, V>>` per un dato `Codec<K>` e `Codec<V>`. Il codec risultante serializzerà a un oggetto JSON oppure a qualsiasi equivalente per il dynamic ops corrente.

Date le limitazioni di JSON e NBT, il codec chiave usato _deve_ serializzare a una stringa. Questo include codec per tipo che non sono in sé stringhe, ma che serializzano a esse, come `Identifier.CODEC`. Vedi l'esempio sotto:

::: code-group

<<< @/reference/26.1.2/src/client/java/com/example/docs/datagen/ExampleModCodecExampleProvider.java#map_codec[Codec]

<<< @/reference/26.1.2/src/client/java/com/example/docs/datagen/ExampleModCodecExampleProvider.java#map_codec_data[Input]

<<< @/reference/26.1.2/src/main/generated/reports/example-mod/codec_examples/map.json[Output]

:::

Come puoi vedere, questo funziona perché `Identifier.CODEC` serializza direttamente a un valore di tipo stringa. Un effetto simile può essere ottenuto per oggetti semplici che non serializzano a stringhe usando [xmap e compagnia](#mutually-convertible-types) per convertirli.

### Tipi Convertibili Mutualmente {#mutually-convertible-types}

#### `xmap` {#xmap}

Immagina di avere due classi che possono essere convertite l'una nell'altra e viceversa, ma che non hanno un legame gerarchico genitore-figlio. Per esempio, un `BlockPos` vanilla e un `Vec3d`. Se avessimo un codec per uno, possiamo usare `Codec#xmap` per creare un codec per l'altro specificando una funzione di conversione per ciascuna direzione.

`BlockPos` ha già un codec, ma facciamo finta che non ce l'abbia. Possiamo creargliene uno basandolo sul codec per `Vec3d` così:

::: code-group

<<< @/reference/26.1.2/src/client/java/com/example/docs/datagen/ExampleModCodecExampleProvider.java#convert_xmap[Codec]

<<< @/reference/26.1.2/src/client/java/com/example/docs/datagen/ExampleModCodecExampleProvider.java#convert_xmap_data[Input]

<<< @/reference/26.1.2/src/main/generated/reports/example-mod/codec_examples/xmap.json[Output]

:::

#### flatComapMap, comapFlatMap, e flatXMap {#flatcomapmap-comapflatmap-flatxmap}

`Codec#flatComapMap`, `Codec#comapFlatMap` e `flatXMap` sono simili a xmap, ma permettono a una o a entrambe le funzioni di conversione di restituire un DataResult. Questo è utile nella pratica perché un'istanza specifica di un oggetto potrebbe non essere sempre valida per la conversione.

Prendi per esempio gli `Identifier` vanilla. Anche se tutti gli identifier possono essere trasformati in stringhe, non tutte le stringhe sono identifier validi, quindi usare xmap vorrebbe dire lanciare delle brutte eccezioni quando la conversione fallisce.
Per questo, il suo codec predefinito è in realtà una `comapFlatMap` su `Codec.STRING`, che illustra bene come usarla:

<<< @/reference/26.1.2/src/main/java/com/example/docs/codec/Identifier.java#identifier_flatmap

Anche se questi metodi sono molto d'aiuto, i loro nomi possono confondere un po', quindi ecco una tabella per aiutarti a ricordare quale usare:

| Metodo         | Il decoding è sempre valido? | L'encoding è sempre valido? |
| -------------- | ---------------------------- | --------------------------- |
| `xmap`         | Sì                           | Sì                          |
| `comapFlatMap` | No                           | Sì                          |
| `flatComapMap` | Sì                           | No                          |
| `flatXMap`     | No                           | No                          |

### Dispatch della Registry {#registry-dispatch}

`Codec#dispatch` ci permette di definire una registry di codec e di fare dispatch ad uno di essi in base al valore di un attributo nei dati serializzati. Questo è molto utile durante la deserializzazione di oggetti che hanno attributi diversi a seconda del loro tipo, ma che rappresentano pur sempre la stessa cosa.

Per esempio, immaginiamo di avere un'interfaccia astratta `Bean` con due classi che la implementano: `StringyBean` e `CountingBean`. Per serializzare queste con un dispatch di registry, ci serviranno alcune cose:

- Codec separati per ogni tipo di fagiolo.
- Una classe o un record `BeanType<T extends Bean>` che rappresenta il tipo di fagiolo, e che può restituire il codec per esso.
- Una funzione in `Bean` per ottenere il suo `BeanType<?>`.
- Una mappa o una registry per mappare `Identifier` a `BeanType<?>`.
- Un `Codec<BeanType<?>>` basato su questa registry. Se usi una `net.minecraft.core.Registry`, un codec può essere creato facilmente usando `Registry#byNameCodec`.

Con tutto questo, possiamo creare un codec di dispatch di registry per i fagioli:

::: code-group

<<< @/reference/26.1.2/src/client/java/com/example/docs/datagen/ExampleModCodecExampleProvider.java#registry_dispatch[Codec]

<<< @/reference/26.1.2/src/main/java/com/example/docs/codec/Bean.java#bean_interface[Bean]

<<< @/reference/26.1.2/src/main/java/com/example/docs/codec/BeanType.java#bean_type_record[BeanType]

<<< @/reference/26.1.2/src/main/java/com/example/docs/codec/StringyBean.java#stringy_bean_class[StringyBean]

<<< @/reference/26.1.2/src/main/java/com/example/docs/codec/CountingBean.java#counting_bean_class[CountingBean]

<<< @/reference/26.1.2/src/main/java/com/example/docs/codec/BeanTypes.java#bean_types_class[BeanTypes]

:::

Il nostro nuovo codec serializzerà fagioli a JSON così, prendendo solo attributi che sono rilevanti al loro tipo specifico:

<<< @/reference/26.1.2/src/main/generated/reports/example-mod/codec_examples/stringy_bean.json

<<< @/reference/26.1.2/src/main/generated/reports/example-mod/codec_examples/counting_bean.json

### Codec Ricorsivi {#recursive-codecs}

A volte è utile avere un codec che usa _sé stesso_ per decodificare attributi specifici, per esempio quando si gestiscono certe strutture dati ricorsive. Nel codice vanilla, questo è usato per gli oggetti `Component`, che potrebbero contenere altri `Component` come figli. Un codec del genere può essere costruito usando `Codec#recursive`.

Per esempio, proviamo a serializzare una lista concatenata singolarmente. Questo metodo di rappresentare le liste consiste di una serie di nodi che contengono sia un valore sia un riferimento al nodo successivo nella lista. La lista è poi rappresentata dal suo primo nodo, e per attraversare la lista si segue il prossimo nodo finché non ce ne sono più. Ecco una semplice implementazione di nodi che contengono interi.

<<< @/reference/26.1.2/src/main/java/com/example/docs/codec/ListNode.java#node_record

Non possiamo costruire un codec per questo come si fa di solito: quale codec useremmo per l'attributo `next`? Avremmo bisogno di un `Codec<ListNode>`, che è ciò che stiamo costruendo proprio ora! `Codec#recursive` ci permette di fare ciò usando una lambda che sembra magia:

::: code-group

<<< @/reference/26.1.2/src/client/java/com/example/docs/datagen/ExampleModCodecExampleProvider.java#recursive_codec[Codec]

<<< @/reference/26.1.2/src/client/java/com/example/docs/datagen/ExampleModCodecExampleProvider.java#recursive_codec_data[Input]

<<< @/reference/26.1.2/src/main/generated/reports/example-mod/codec_examples/recursive.json[Output]

:::

<!---->
