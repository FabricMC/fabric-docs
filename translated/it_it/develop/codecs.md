---
title: Codec
description: Una guida esaustiva per la comprensione e l'uso del sistema di codec di Mojang per serializzare e deserializzare gli oggetti.
authors:
  - enjarai
  - Syst3ms
---

# Codec {#codecs}

Un codec è un sistema per serializzare facilmente oggetti Java, ed è incluso nella libreria DataFixerUpper (DFU) di Mojang, che è inclusa in Minecraft. Nel contesto del modding essi possono essere usati come un'alternativa a GSON e Jankson quando si leggono e si scrivono file json personalizzati, anche se hanno cominciato a diventare sempre più rilevanti, visto che Mojang sta riscrivendo molto suo codice in modo che usi i Codec.

I Codec vengono usati assieme a un'altra API da DFU, `DynamicOps`. Un codec definisce la struttura di un oggetto, mentre i dynamic ops vengono usati per definire un formato da cui e a cui essere serializzato, come json o NBT. Questo significa che qualsiasi codec può essere usato con qualsiasi dynamic ops, e viceversa, permettendo una grande flessibilità.

## Usare i Codec {#using-codecs}

### Serializzazione e Deserializzazione {#serializing-and-deserializing}

L'utilizzo basilare di un codec è serializzare e deserializzare oggetti da e a un formato specifico.

Poiché alcune classi vanilla hanno già dei codec definiti, possiamo usare quelli come un esempio. Mojang ci ha anche fornito due classi di dynamic ops predefinite, `JsonOps` e `NbtOps`, che tendono a coprire la maggior parte degli casi.

Ora, immaginiamo di voler serializzare un `BlockPos` a json e viceversa. Possiamo fare questo usando il codec memorizzato staticamente presso `BlockPos.CODEC` con i metodi `Codec#encodeStart` e `Codec#parse`, rispettivamente.

```java
BlockPos pos = new BlockPos(1, 2, 3);

// Serializza il BlockPos a un JsonElement
DataResult<JsonElement> result = BlockPos.CODEC.encodeStart(JsonOps.INSTANCE, pos);
```

Quando si usa un codec, i valori sono restituiti come un `DataResult`. Questo è un wrapper che può rappresentare un successo oppure un fallimento. Possiamo usare questo in diversi modi: Se vogliamo soltanto il nostro valore serializzato, `DataResult#result` restituirà semplicemente un `Optional` contenente il nostro valore, mentre `DataResult#resultOrPartial` ci permette anche di fornire una funzione per gestire qualsiasi errore che potrebbe essersi verificato. La seconda è specialmente utile per risorse di datapack personalizzati, dove vorremmo segnare gli errori nel log senza causare problemi altrove.

Quindi prendiamo il nostro valore serializzato e ritrasformiamolo nuovamente in un `BlockPos`:

```java
// Quando stai davvero scrivendo una mod, vorrai ovviamente gestire gli Optional vuoti propriamente
JsonElement json = result.resultOrPartial(LOGGER::error).orElseThrow();

// Qui abbiamo il nostro valore json, che dovrebbe corrispondere a `[1, 2, 3]`,
// poiché quello è il formato usato dal codec di BlockPos.
LOGGER.info("Serialized BlockPos: {}", json);

// Ora deserializzeremo nuovamente il JsonElement in un BlockPos
DataResult<BlockPos> result = BlockPos.CODEC.parse(JsonOps.INSTANCE, json);

// Ancora, prenderemo soltanto il nostro valore dal risultato
BlockPos pos = result.resultOrPartial(LOGGER::error).orElseThrow();

// E possiamo notare che abbiamo serializzato e deserializzato il nostro BlockPos con successo!
LOGGER.info("Deserialized BlockPos: {}", pos);
```

### Codec Predefiniti {#built-in-codecs}

Come menzionato in precedenza, Mojang ha già definito codec per tante classi Java vanilla e standard, incluse, ma non solo, `BlockPos`, `BlockState`, `ItemStack`, `Identifier`, `Text`, e `Pattern` regex. I Codec per le classi di Mojang si trovano solitamente come attributi static chiamati `CODEC` della classe stessa, mentre molte altre sono mantenute nella classe `Codecs`. Bisogna anche sottolineare che tutte le registry vanilla contengono un metodo `getCodec()`, per esempio, puoi usare `Registries.BLOCK.getCodec()` per ottenere un `Codec<Block>` che serializza all'id del blocco e viceversa.

L'API stessa dei Codec contiene anche alcuni codec per tipi primitivi, come `Codec.INT` e `Codec.STRING`. Queste sono disponibili come statici nella classe `Codec`, e sono solitamente usate come base per codec più complessi, come spiegato sotto.

## Costruire Codec {#building-codecs}

Ora che abbiamo visto come usare i codec, vediamo come possiamo costruircene di nostri. Supponiamo di avere la seguente classe, e di voler deserializzare le sue istanze da file json:

```java
public class CoolBeansClass {

    private final int beansAmount;
    private final Item beanType;
    private final List<BlockPos> beanPositions;

    public CoolBeansClass(int beansAmount, Item beanType, List<BlockPos> beanPositions) {...}

    public int getBeansAmount() { return this.beansAmount; }
    public Item getBeanType() { return this.beanType; }
    public List<BlockPos> getBeanPositions() { return this.beanPositions; }
}
```

Il corrispondente file json potrebbe avere il seguente aspetto:

```json
{
  "beans_amount": 5,
  "bean_type": "beanmod:mythical_beans",
  "bean_positions": [
    [1, 2, 3],
    [4, 5, 6]
  ]
}
```

Possiamo creare un codec per questa classe mettendo insieme tanti codec più piccoli per formarne uno più grande. In questo caso, ne avremo bisogno di uno per ogni attributo:

- un `Codec<Integer>`
- un `Codec<Item>`
- un `Codec<List<BlockPos>>`

Possiamo ottenere il primo dal codec primitivo nella classe `Codec` menzionato in precedenza, nello specifico `Codec.INT`. Mentre il secondo può essere ottenuto dalla registry `Registries.ITEM`, che ha un metodo `getCodec()` che restituisce un `Codec<Item>`. Non abbiamo un codec predefinito per `List<BlockPos>`, ma possiamo crearne uno a partire da `BlockPos.CODEC`.

### Liste {#lists}

`Codec#listOf` può essere usato per creare una versione lista di qualsiasi codec:

```java
Codec<List<BlockPos>> listCodec = BlockPos.CODEC.listOf();
```

Bisogna sottolineare che i codec creati così verranno sempre deserializzati a un'`ImmutableList`. Se invece ti servisse una lista mutabile, puoi usare [xmap](#tipi-convertibili-mutualmente-e-tu) per convertirla ad una durante la deserializzazione.

### Unire i Codec per Classi Simili ai Record {#merging-codecs-for-record-like-classes}

Ora che abbiamo codec separati per ciascun attributo, possiamo combinarli a formare un singolo codec per la nostra classe usando un `RecordCodecBuilder`. Questo suppone che la nostra classe abbia un costruttore che contiene ogni attributo che vogliamo serializzare, e che ogni attributo ha un metodo getter corrispondente. Questo lo rende perfetto per essere usato assieme ai record, ma può anche essere usato con classi regolari.

Diamo un'occhiata a come creare un codec per la nostra `CoolBeansClass`:

```java
public static final Codec<CoolBeansClass> CODEC = RecordCodecBuilder.create(instance -> instance.group(
    Codec.INT.fieldOf("beans_amount").forGetter(CoolBeansClass::getBeansAmount),
    Registries.ITEM.getCodec().fieldOf("bean_type").forGetter(CoolBeansClass::getBeanType),
    BlockPos.CODEC.listOf().fieldOf("bean_positions").forGetter(CoolBeansClass::getBeanPositions)
    // Un massimo di 16 attributi può essere dichiarato qui
).apply(instance, CoolBeansClass::new));
```

Ogni linea nel gruppo specifica un codec, il nome di un attributo, e un metodo getter. La chiamata a `Codec#fieldOf` è usata per convertire il codec a un [MapCodec](#mapcodec), e la chiamata a `forGetter` specifica il metodo getter usato per ottenere il valore dell'attributo da un'istanza della classe. Inoltre, la chiamata ad `apply` specifica il costruttore usato per creare nuove istanze. Nota che l'ordine degli attributi nel gruppo dovrebbe essere lo stesso di quello dei parametri nel costruttore.

Puoi anche usare `Codec#optionalFieldOf` in questo contesto per rendere un attributo opzionale, come spiegato nella sezione [Attributi Opzionali](#attributi-opzionali).

### MapCodec, Da Non Confondere Con Codec&amp;lt;Map&amp;gt; {#mapcodec}

La chiamata a `Codec#fieldOf` convertirà un `Codec<T>` in un `MapCodec<T>`, che è una variante, ma non una diretta implementazione di `Codec<T>`. I `MapCodec`, come suggerisce il loro nome garantiscono la serializzazione a una mappa chiave-valore, o al suo equivalente nella `DynamicOps` usata. Alcune funzioni ne potrebbero richiedere uno invece di un codec normale.

Questo modo particolare di creare un `MapCodec` racchiude sostanzialmente il valore del codec sorgente dentro una mappa, con il nome dell'attributo dato come chiave. Per esempio, un `Codec<BlockPos>` serializzato a json avrebbe il seguente aspetto:

```json
[1, 2, 3]
```

Ma quando viene convertito in un `MapCodec<BlockPos>` usando `BlockPos.CODEC.fieldOf("pos")`, esso ha il seguente aspetto:

```json
{
  "pos": [1, 2, 3]
}
```

Anche se i Map Codec vengono più frequentemente usati per essere uniti ad altri Map Codec per costruire un codec per l'intero insieme di attributi di una classe, come spiegato nella sezione [Unire i Codec per Classi simili ai Record](#unire-i-codec-per-classi-simili-ai-record) sopra, essi possono anche essere ritrasformati in codec normali usando `MapCodec#codec`, che darà lo stesso risultato di incapsulare il loro valore di input.

#### Attributi Opzionali {#optional-fields}

`Codec#optionalFieldOf` può essere usato per create una mappa codec opzionale. Esso, quando l'attributo indicato non è presente nel container durante la deserializzazione, verrà o deserializzato come un `Optional` vuoto oppure con un valore predefinito indicato.

```java
// Senza un valore predefinito
MapCodec<Optional<BlockPos>> optionalCodec = BlockPos.CODEC.optionalFieldOf("pos");

// Con un valore predefinito
MapCodec<BlockPos> optionalCodec = BlockPos.CODEC.optionalFieldOf("pos", BlockPos.ORIGIN);
```

Nota che gli attributi opzionali ignoreranno silenziosamente qualsiasi errore che possa verificarsi durante la deserializzazione. Questo significa che se l'attributo è presente, ma il valore non è valido, l'attributo verrà sempre deserializzato al valore predefinito.

**A partire da 1.20.2**, Minecraft stesso (non DFU!) fornisce `Codecs#createStrictOptionalFieldCodec`, che fallisce del tutto nel deserializzare se il valore dell'attributo non è valido.

### Costanti, Vincoli, e Composizione {#constants-constraints-composition}

#### Unità {#unit}

`Codec.unit` può essere usato per creare un codec che verrà sempre deserializzato a un valore costante, indipendentemente dall'input. Durante la serializzazione, non farà nulla.

```java
Codec<Integer> theMeaningOfCodec = Codec.unit(42);
```

#### Intervalli Numerici {#numeric-ranges}

`Codec.intRange` e compagnia, `Codec.floatRange` e `Codec.doubleRange` possono essere usati per creare un codec che accetta soltanto valori numerici all'interno di un intervallo **inclusivo** specificato. Questo si applica sia alla serializzazione sia alla deserializzazione.

```java
// Non può essere superiore a 2
Codec<Integer> amountOfFriendsYouHave = Codec.intRange(0, 2);
```

#### Coppia {#pair}

`Codec.pair` unisce due codec, `Codec<A>` e `Codec<B>`, in un `Codec<Pair<A, B>>`. Tieni a mente che funziona correttamente soltanto con codec che serializzano a un attributo specifico, come [MapCodec convertiti](#mapcodec) oppure [Codec di Record](#unire-i-codec-per-classi-simili-ai-record).
Il codec risultante serializzerà a una mappa contenente gli attributi di entrambi i codec usati.

Per esempio, eseguire questo codice:

```java
// Crea due codec incapsulati separati
Codec<Integer> firstCodec = Codec.INT.fieldOf("i_am_number").codec();
Codec<Boolean> secondCodec = Codec.BOOL.fieldOf("this_statement_is_false").codec();

// Uniscili in un codec coppia
Codec<Pair<Integer, Boolean>> pairCodec = Codec.pair(firstCodec, secondCodec);

// Usalo per serializzare i dati
DataResult<JsonElement> result = pairCodec.encodeStart(JsonOps.INSTANCE, Pair.of(23, true));
```

Restituirà il seguente json:

```json
{
  "i_am_number": 23,
  "this_statement_is_false": true
}
```

#### Either {#either}

`Codec.either` unisce due codec, `Codec<A>` e `Codec<B>`, in un `Codec<Either<A, B>>`. Il codec risultante tenterà, durante la deserializzazione, di usare il primo codec, e _solo se quello fallisce_, tenterà di usare il secondo.
Se anche il secondo fallisse, l'errore del _secondo_ codec verrà restituito.

#### Mappe {#maps}

Per gestire mappe con chiavi arbitrarie, come `HashMap`, `Codec.unboundedMap` può essere usato. Questo restituisce un `Codec<Map<K, V>>` per un dato `Codec<K>` e `Codec<V>`. Il codec risultante serializzerà a un oggetto json oppure a qualsiasi equivalente disponibile per il dynamic ops corrente.

Date le limitazioni di json e nbt, il codec chiave utilizzato _deve_ serializzare a una stringa. Questo include codec per tipo che non sono in sé stringhe, ma che serializzano a esse, come `Identifier.CODEC`. Vedi l'esempio sotto:

```java
// Crea un codec per una mappa da identifier a interi
Codec<Map<Identifier, Integer>> mapCodec = Codec.unboundedMap(Identifier.CODEC, Codec.INT);

// Usalo per serializzare i dati
DataResult<JsonElement> result = mapCodec.encodeStart(JsonOps.INSTANCE, Map.of(
    new Identifier("example", "number"), 23,
    new Identifier("example", "the_cooler_number"), 42
));
```

Questo restituirà il json seguente:

```json
{
  "example:number": 23,
  "example:the_cooler_number": 42
}
```

Come puoi vedere, questo funziona perché `Identifier.CODEC` serializza direttamente a un valore di tipo stringa. Un effetto simile può essere ottenuto per oggetti semplici che non serializzano a stringhe usando [xmap e compagnia](#tipi-convertibili-mutualmente-e-tu) per convertirli.

### Tipi Convertibili Mutualmente e Tu {#mutually-convertible-types}

#### `xmap` {#xmap}

Immagina di avere due classi che possono essere convertite l'una nell'altra e viceversa, ma che non hanno un legame gerarchico genitore-figlio. Per esempio, un `BlockPos` vanilla e un `Vec3d`. Se avessimo un codec per uno, possiamo usare `Codec#xmap` per creare un codec per l'altro specificando una funzione di conversione per ciascuna direzione.

`BlockPos` ha già un codec, ma facciamo finta che non ce l'abbia. Possiamo creargliene uno basandolo sul codec per `Vec3d` così:

```java
Codec<BlockPos> blockPosCodec = Vec3d.CODEC.xmap(
    // Converti Vec3d a BlockPos
    vec -> new BlockPos(vec.x, vec.y, vec.z),
    // Converti BlockPos a Vec3d
    pos -> new Vec3d(pos.getX(), pos.getY(), pos.getZ())
);

// Quando converti una classe esistente (per esempio `X`)
// alla tua classe personalizzata (`Y`) in questo modo,
// potrebbe essere comodo aggiungere i metodi `toX` e
// `fromX` statico ad `Y` e usare riferimenti ai metodi
// nella tua chiamata ad `xmap`.
```

#### flatComapMap, comapFlatMap, e flatXMap {#flatcomapmap-comapflatmap-flatxmap}

`Codec#flatComapMap`, `Codec#comapFlatMap` e `flatXMap` sono simili a xmap, ma permettono a una o a entrambe le funzioni di conversione di restituire un DataResult. Questo è utile nella pratica perché un'istanza specifica di un oggetto potrebbe non essere sempre valida per la conversione.

Prendi per esempio gli `Identifier` vanilla. Anche se tutti gli identifier possono essere trasformati in stringhe, non tutte le stringhe sono identifier validi, quindi usare xmap vorrebbe dire lanciare delle brutte eccezioni quando la conversione fallisce.
Per questo, il suo codec predefinito è in realtà una `comapFlatMap` su `Codec.STRING`, che illustra bene come usarla:

```java
public class Identifier {
    public static final Codec<Identifier> CODEC = Codec.STRING.comapFlatMap(
        Identifier::validate, Identifier::toString
    );

    // ...

    public static DataResult<Identifier> validate(String id) {
        try {
            return DataResult.success(new Identifier(id));
        } catch (InvalidIdentifierException e) {
            return DataResult.error("Posizione di risorsa non valida: " + id + " " + e.getMessage());
        }
    }

    // ...
}
```

Anche se questi metodi sono molto d'aiuto, i loro nomi possono confondere un po', quindi ecco una tabella per aiutarti a ricordare quale usare:

| Metodo                  | A -> B è sempre valido? | B -> A è sempre valido? |
| ----------------------- | ----------------------- | ----------------------- |
| `Codec<A>#xmap`         | Sì                      | Sì                      |
| `Codec<A>#comapFlatMap` | No                      | Sì                      |
| `Codec<A>#flatComapMap` | Sì                      | No                      |
| `Codec<A>#flatXMap`     | No                      | No                      |

### Dispatch della Registry {#registry-dispatch}

`Codec#dispatch` ci permette di definire una registry di codec e di fare dispatch ad uno di essi in base al valore di un attributo nei dati serializzati. Questo è molto utile durante la deserializzazione di oggetti che hanno attributi diversi a seconda del loro tipo, ma che rappresentano pur sempre la stessa cosa.

Per esempio, immaginiamo di avere un'interfaccia astratta `Bean` con due classi che la implementano: `StringyBean` e `CountingBean`. Per serializzare queste con un dispatch di registry, ci serviranno alcune cose:

- Codec separati per ogni tipo di fagiolo.
- Una classe o un record `BeanType<T extends Bean>` che rappresenta il tipo di fagiolo, e che può restituire il codec per esso.
- Una funzione in `Bean` per ottenere il suo `BeanType<?>`.
- Una mappa o una registry per mappare `Identifier` a `BeanType<?>`.
- Un `Codec<BeanType<?>>` basato su questa registry. Se usi una `net.minecraft.registry.Registry`, un codec può essere creato facilmente usando `Registry#getCodec`.

Con tutto questo, possiamo creare un codec di dispatch di registry per i fagioli:

@[code transcludeWith=:::](@/reference/latest/src/main/java/com/example/docs/codec/Bean.java)
@[code transcludeWith=:::](@/reference/latest/src/main/java/com/example/docs/codec/BeanType.java)
@[code transcludeWith=:::](@/reference/latest/src/main/java/com/example/docs/codec/StringyBean.java)
@[code transcludeWith=:::](@/reference/latest/src/main/java/com/example/docs/codec/CountingBean.java)
@[code transcludeWith=:::](@/reference/latest/src/main/java/com/example/docs/codec/BeanTypes.java)

```java
// Ora possiamo creare un codec per i tipi di fagioli
// in base alla registry creata in precedenza
Codec<BeanType<?>> beanTypeCodec = BeanType.REGISTRY.getCodec();

// E in base a quello, ecco il nostro codec di dispatch della registry per i fagioli!
// Il primo parametro e il nome dell'attributo per il tipo di fagiolo.
// Se lasciato vuoto, assumerà "type" come valore predefinito.
Codec<Bean> beanCodec = beanTypeCodec.dispatch("type", Bean::getType, BeanType::codec);
```

Il nostro nuovo codec serializzerà fagioli a json così, prendendo solo attributi che sono rilevanti al loro tipo specifico:

```json
{
  "type": "example:stringy_bean",
  "stringy_string": "This bean is stringy!"
}
```

```json
{
  "type": "example:counting_bean",
  "counting_number": 42
}
```

### Codec Ricorsivi {#recursive-codecs}

A volte è utile avere un codec che utilizza _sé stesso_ per decodificare attributi specifici, per esempio quando si gestiscono certe strutture dati ricorsive. Nel codice vanilla, questo è usato per gli oggetti `Text`, che potrebbero contenere altri `Text` come figli. Un codec del genere può essere costruito usando `Codec#recursive`.

Per esempio, proviamo a serializzare una lista concatenata singolarmente. Questo metodo di rappresentare le liste consiste di una serie di nodi che contengono sia un valore sia un riferimento al nodo successivo nella lista. La lista è poi rappresentata dal suo primo nodo, e per attraversare la lista si segue il prossimo nodo finché non ce ne sono più. Ecco una semplice implementazione di nodi che contengono interi.

```java
public record ListNode(int value, ListNode next) {}
```

Non possiamo costruire un codec per questo come si fa di solito: quale codec useremmo per l'attributo `next`? Avremmo bisogno di un `Codec<ListNode>`, che è ciò che stiamo costruendo proprio ora! `Codec#recursive` ci permette di fare ciò usando una lambda che sembra magia:

```java
Codec<ListNode> codec = Codec.recursive(
  "ListNode", // un nome per il codec
  selfCodec -> {
    // Qui, `selfCodec` rappresenta il `Codec<ListNode>`, come se fosse già costruito
    // Questa lambda dovrebbe restituire il codec che volevamo usare dall'inizio,
    // che punta a sé stesso attraverso `selfCodec`
    return RecordCodecBuilder.create(instance ->
      instance.group(
        Codec.INT.fieldOf("value").forGetter(ListNode::value),
         // l'attributo `next` sarà gestito ricorsivamente con il self-codec
        Codecs.createStrictOptionalFieldCodec(selfCodec, "next", null).forGetter(ListNode::next)
      ).apply(instance, ListNode::new)
    );
  }
);
```

Un `ListNode` serializzato potrebbe avere questo aspetto:

```json
{
  "value": 2,
  "next": {
    "value": 3,
    "next" : {
      "value": 5
    }
  }
}
```

## Riferimenti {#references}

- Una documentazione molto più dettagliata sui Codec e sulle relative API può essere trovata presso la [JavaDoc non Ufficiale di DFU](https://kvverti.github.io/Documented-DataFixerUpper/snapshot/com/mojang/serialization/Codec).
- La struttura generale di questa guida è fortemente ispirata dalla [pagina sui codec della Wiki della Community di Forge](https://forge.gemwire.uk/wiki/Codecs), una pagina più orientata verso Forge sullo stesso argomento.
