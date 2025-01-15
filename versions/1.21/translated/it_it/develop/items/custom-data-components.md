---
title: Componenti di Dati Personalizzate
description: Impara come si aggiungono dati personalizzati ai tuoi oggetti usando il nuovo sistema di componenti di 1.20.5.
authors:
  - Romejanic
---

# Componenti di Dati Personalizzate {#custom-data-components}

Più i tuoi oggetti crescono in complessità, più troverai la necessità di memorizzare dati personalizzati associati con ogni oggetto. Il gioco permette di memorizzare dati persistenti in un `ItemStack`, e a partire da 1.20.5 il modo di fare ciò è usare le **Componenti di Dati**.

Le Componenti di Dati sostituiscono i dati NBT di versioni precedenti, con tipi di dati strutturati che possono essere applicati a un `ItemStack` per memorizzare dati su quello stack. Le componenti di dati sfruttano namespace, il che significa che possiamo implementare le nostre componenti di dati per memorizzare dati personalizzati su un `ItemStack` e accederci successivamente. Una lista completa delle componenti di dati vanilla si trova su questa [pagina della Minecraft Wiki](https://minecraft.wiki/w/Data_component_format#List_of_components).

Assieme alla registrazione delle componenti personalizzate, questa pagina copre l'utilizzo generale dell'API delle componenti, il che si applica anche alle componenti vanilla. Puoi vedere e accedere alle definizioni di tutte le componenti vanilla nella classe `DataComponentTypes`.

## Registrare una Componente {#registering-a-component}

Come per qualsiasi altra cosa nella tua mod dovrai registrare la tua componente personalizzata usando un `ComponentType`. Questo tipo di componente prende un parametro generico contenente il tipo del valore della tua componente. Ci concentreremo su questo più in basso quando tratteremo le componenti [basilari](#basic-data-components) e [avanzate](#advanced-data-components).

Scegli sensibilmente una classe in cui mettere ciò. Per questo esempio creeremo un nuovo package chiamato `component` e una classe che conterrà tutti i tipi delle nostre componenti chiamate `ModComponents`. Assicurati di richiamare `ModComponents.initialize()` nell'[initializer della tua mod](./getting-started/project-structure#entrypoints).

@[code transcludeWith=::1](@/reference/latest/src/main/java/com/example/docs/component/ModComponents.java)

Questo è il modello generico per registrare un tipo di componente:

```java
public static final ComponentType<?> MY_COMPONENT_TYPE = Registry.register(
    Registries.DATA_COMPONENT_TYPE,
    Identifier.of(FabricDocsReference.MOD_ID, "my_component"),
    ComponentType.<?>builder().codec(null).build()
);
```

Ci sono un paio di cose qui da far notare. Nelle linee prima e quarta, noti un `?`. Questo sarà sostituito con il tipo del valore della tua componente. Lo compileremo presto.

Secondo, devi fornire un `Identifier` che contenga l'ID voluto per la tua componente. Questo avrà il namespace con l'ID della tua mod.

Infine, abbiamo un `ComponentType.Builder` che crea l'istanza `ComponentType` effettiva da registrare. Questo contiene un altro dettaglio cruciale che dovremmo analizzare: il `Codec` della tua componente. Questo per ora è `null` ma presto dobbiamo scriverlo.

## Componenti di Dati Basilari {#basic-data-components}

Le componenti di dati basilari (come `minecraft:damage`) consiste di un valore di dati singolo, come un `int`, `float`, `boolean` o `String`.

Per questo esempio, creiamo un valore `Integer` che traccierà quante volte il giocatore ha cliccato con il tasto destro mente teneva il nostro oggetto. Aggiorniamo la registrazione della nostra componente alla seguente:

@[code transcludeWith=::2](@/reference/latest/src/main/java/com/example/docs/component/ModComponents.java)

Puoi ora notare che abbiamo passato `<Integer>` come nostro tipo generico, indicando che questa componente sarà memorizzata come un valore `int` singolo. Per il nostro codec, cusiamo il codec `Codec.INT` fornito. Possiamo cavarcela usando codec basilari per componenti semplici come questa, ma scenari più complessi potrebbero richiedere un codec personalizzato (questo sarà trattato tra poco).

Se avviassi il gioco, dovresti poter inserire un comando come questo:

![Comando /give che mostra la componente personalizzata](/assets/develop/items/custom_component_0.png)

Quando esegui il comando, dovresti ricevere l'oggetto contenente la componente. Tuttavia, non possiamo ancora sfruttare la componente per fare qualcosa di utile. Iniziamo leggendo il valore della componente in modo da poterlo vedere.

## Leggere il Valore della Componente {#reading-component-value}

Aggiungiamo un nuovo oggetto che aumenterà il contatore ogni volta che viene cliccato con il tasto destro. Dovresti leggere la pagina [Interazioni tra Oggetti Personalizzate](./custom-item-interactions) che tratterà delle tecniche utilizzate in questa guida.

@[code transcludeWith=::1](@/reference/latest/src/main/java/com/example/docs/item/custom/CounterItem.java)

Ricorda come sempre di registrare l'oggetto nella tua classe `ModItems`.

```java
public static final Item COUNTER = register(new CounterItem(
    new Item.Settings()
), "counter");
```

Aggiungeremo del codice del tooltip per mostrare il valore corrente del contatore di clic quando passiamo il mouse sopra al nostro oggetto nell'inventario. Possiamo usare il metodo `get()` sul nostro `ItemStack` per ottenere il valore della nostra componente, così:

```java
int clickCount = stack.get(ModComponents.CLICK_COUNT_COMPONENT);
```

Questo restituirà il valore corrente della componente nel tipo che abbiamo definito quando abbiamo registrato la nostra componente. Possiamousare questo valore per aggiungere una voce al tooltip. Aggiungi questa linea al metodo `appendTooltip` nella classe `CounterItem`:

```java
public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
    int count = stack.get(ModComponents.CLICK_COUNT_COMPONENT);
    tooltip.add(Text.translatable("item.fabric-docs-reference.counter.info", count).formatted(Formatting.GOLD));
}
```

Non dimenticare di aggiornare il tuo file di lingua (`/assets/<mod id>/lang/en_us.json`) e aggiungere queste due linee:

```json
{
  "item.fabric-docs-reference.counter": "Counter",
  "item.fabric-docs-reference.counter.info": "Used %1$s times"
}
```

Avvia il gioco e esegui questo comando per darti un nuovo oggetto Counter con un conto di 5.

```mcfunction
/give @p fabric-docs-reference:counter[fabric-docs-reference:click_count=5]
```

Quando passi il mouse sopra a questo oggetto nell'inventario, dovresti notare il conto nel tooltip!

![Tooltip che mostra "Used 5 times"](/assets/develop/items/custom_component_1.png)

Tuttavia, se ti dai un nuovo oggetto Counter _senza_ la componente personalizzata, il gioco crasherà quando passi il mouse sull'oggetto nel suo inventario. Dovresti vedere un errore come il seguente nel report di crash:

```log
java.lang.NullPointerException: Cannot invoke "java.lang.Integer.intValue()" because the return value of "net.minecraft.item.ItemStack.get(net.minecraft.component.ComponentType)" is null
        at com.example.docs.item.custom.CounterItem.appendTooltip(LightningStick.java:45)
        at net.minecraft.item.ItemStack.getTooltip(ItemStack.java:767)
```

Come ci aspettavamo, poiché l'`ItemStack` non contiene per ora un'istanza della nostra componente personalizzata, chiamare `stack.get()` con il tipo della nostra componente restituirà `null`.

Ci sono tre soluzioni a questo problema.

### Impostare un Valore Predefinito della Componente {#setting-default-value}

Quando registri il tuo oggetto e passi un'istanza di `Item.Settings` al suo costruttore, puoi anche fornire una lista di componenti predefinite applicate a tutti i nuovi oggetti. Tornando alla nostra classe `ModItems`, dove registriamo il `CounterItem`, possiamo aggiungere un valore predefinito alla nostra componente. Aggiungi questo così i nuovi oggetti mostreranno un conto di `0`.

@[code transcludeWith=::_13](@/reference/latest/src/main/java/com/example/docs/item/ModItems.java)

Quando un nuovo oggetto viene creato, applicherà automaticamente la nostra componente personalizzata con il valore dato.

:::warning
Usando i comandi, è possibile togliere la componente predefinita da un `ItemStack`. Dovresti affidarti alle prossime due sezioni per gestire lo scenario della mancanza della componente sul tuo oggetto correttamente.
:::

### Leggere con un Valore Predefinito {#reading-default-value}

Inoltre, leggendo il valore della componente, possiamo usare il metodo `getOrDefault()` sul nostro `ItemStack` per restituire un valore predefinito indicato se la componente non è presente nello stack. Questo ci tutelerà contro errori risultanti da una componente mancante. Possiamo modificare il codice del nostro tooltip così:

```java
int clickCount = stack.getOrDefault(ModComponents.CLICK_COUNT_COMPONENT, 0);
```

Come puoi notare, questo metodo prende due parametri: il tipo della nostra componente come prima, e un valore predefinito restituito se la componente non esiste.

### Controllare se una Componente Esiste {#checking-if-component-exists}

Puoi anche verificare se una componente specifica esiste in un `ItemStack` con il metodo `contains()`. Questo prende il tipo della componente come parametro e restituisce `true` o `false` se lo stack contiene o meno quella componente.

```java
boolean exists = stack.contains(ModComponents.CLICK_COUNT_COMPONENT);
```

### Risolvere l'Errore {#fixing-the-error}

Sceglieremo la terza opzione. Quindi, oltre ad aggiungere un valore predefinito alla componente, controlleremo anche se la componente esiste sullo stack, e mostreremo il tooltip solo se lo è.

@[code transcludeWith=::3](@/reference/latest/src/main/java/com/example/docs/item/custom/CounterItem.java)

Riavvia il gioco e passa il mouse sopra all'oggetto senza la componente, dovresti notare che mostra "Used 0 times" e non fa più crashare il gioco.

![Tooltip che mostra "Used 0 times"](/assets/develop/items/custom_component_2.png)

Prova a darti un Counter rimuovendo la nostra componente personalizzata. Puoi usare il comando per fare ciò:

```mcfunction
/give @p fabric-docs-reference:counter[!fabric-docs-reference:click_count]
```

Passando il mouse sopra all'oggetto, dovrebbe mancare il tooltip.

![Oggetto Counter senza tooltip](/assets/develop/items/custom_component_7.png)

## Aggiornare il Valore della Componente {#setting-component-value}

Ora proviamo ad aggiornare il valore della nostra componente. Aumenteremo il conto dei clic ogni volta che usiamo il nostro oggetto Counter. Per cambiare il valore di una componente su un `ItemStack` usiamo il metodo `set()` come segue:

```java
stack.set(ModComponents.CLICK_COUNT_COMPONENT, newValue);
```

Questo prende il tipo della nostra componente e il valore che le vogliamo assegnare. In questo caso sarà il nuovo conto dei clic. Questo metodo restituisce anche il vecchio valore della componente (se ne esiste uno), e questo potrebbe essere utile in alcune situazioni. Per esempio:

```java
int oldValue = stack.set(ModComponents.CLICK_COUNT_COMPONENT, newValue);
```

Configuriamo un nuovo metodo `use()` per leggere il vecchio conto dei clic, aumentarlo di uno, e impostare il conto dei clic aggiornato.

@[code transcludeWith=::2](@/reference/latest/src/main/java/com/example/docs/item/custom/CounterItem.java)

Ora prova ad avviare il gioco e a cliccare con il tasto destro l'oggetto Counter nella tua mano. Aprendo l'inventario dovresti notare che il numero di utilizzi dell'oggetto è aumentato di tante volte quante hai cliccato.

![Tooltip che mostra "Used 8 times"](/assets/develop/items/custom_component_3.png)

## Rimuovere il Valore della Componente {#removing-component-value}

Puoi anche rimuovere una componente dal tuo `ItemStack` se non serve più. Questo si fa usando il metodo `remove()`, che prende il tipo della componente.

```java
stack.remove(ModComponents.CLICK_COUNT_COMPONENT);
```

Questo metodo restituisce anche il valore della componente prima di essere rimossa, per cui puoi usarlo come segue:

```java
int oldCount = stack.remove(ModComponents.CLICK_COUNT_COMPONENT);
```

## Dati Avanzati delle Componenti {#advanced-data-components}

Potresti avere bisogno di memorizzare più attributi in una componente singola. Un esempio da vanilla è la componente `minecraft:food`, che memorizza più valori legati al cibo come `nutrition`, `saturation`, `eat_seconds` e altro ancora. In questa guida le chiameremo componenti "composite".

Per le componenti composite, devi creare una classe `record` per memorizzare i dati. Questo è il tipo che registreremo nel tipo della componente e che leggeremo e scriveremo interagendo con un `ItemStack`. Inizia creando una nuova classe record nel package `component` che abbiamo creato prima.

```java
public record MyCustomComponent() {
}
```

Nota che c'è un paio di parentesi dopo il nome della classe. Questo è dove definiremo la lista di proprietà che vogliamo dare alla nostra componente. Aggiungiamo un float e un booleano chiamati `temperature` e `burnt` rispettivamente.

@[code transcludeWith=::1](@/reference/latest/src/main/java/com/example/docs/component/MyCustomComponent.java)

Poiché stiamo definendo una struttura dai personalizzata, non ci sarà un `Codec` preesistente per il nostro caso come c'era per le [componenti basilari](#basic-data-components). Questo significa che dovremo costruire il nostro codec. Definiamone uno nella nostra classe record con un `RecordCodecBuilder` a cui potremo far riferimento quando registriamo la componente. Per maggiori dettagli sull'utilizzo di un `RecordCodecBuilder` fai riferimento a [questa sezione della pagina sui Codec](../codecs#merging-codecs-for-record-like-classes).

@[code transcludeWith=::2](@/reference/latest/src/main/java/com/example/docs/component/MyCustomComponent.java)

Puoi notare che stiamo definendo una lista di attributi personalizzati basata sui tipi di `Codec` primitivi. Tuttavia, stiamo anche indicando come si chiamano i nostri attributi con `fieldOf()`, e poi usando `forGetter()` per dire al gioco quale attributo del nostro record deve essere riempito.

Puoi anche definire attributi opzionali usando `optionalFielfOf()` e passando un valore predefinito come secondo parametro. Qualsiasi attributo non opzionale sarà richiesto quando si impostano le componenti con `/give`, quindi assicurati di segnare i parametri opzionali quando crei il tuo codec.

Infine, possiamo chiamare `apply()` e passare il costruttore del nostro record. Per maggiori dettagli su come costruire codec e su casi più avanzati, assicurati di leggere la pagina sui [Codec](../codecs).

Registrare una componente composita funziona come prima. Basta passare la nostra classe record come tipo generico, e il nostro `Codec` personalizzato al metodo `codec()`.

@[code transcludeWith=::3](@/reference/latest/src/main/java/com/example/docs/component/ModComponents.java)

Ora avvia il gioco. Usando il comando `/give`, prova ad applicare la componente. I valori delle componenti composite si passano come oggetto racchiuso da `{}`. Se lasci le graffe vuote, vedrai un errore che ti dice che la chiave `temperature` necessaria è mancante.

![Comando give che mostra la chiave "temperature" mancante](/assets/develop/items/custom_component_4.png)

Aggiungi un valore di temperatura all'oggetto con la sintassi `temperature:8.2`. Puoi anche passare opzionalmente un valore per `burnt` con la stessa sintassi ma con `true` o `false`. Dovresti ora notare che il comando è valido, e che può darti un oggetto che contiene la componente.

![Comando give valido che mostra entrambe le proprietà](/assets/develop/items/custom_component_5.png)

### Ottenere, Impostare e Rimuovere le Componenti Avanzate {#getting-setting-removing-advanced-comps}

Usare la componente nel codice funziona proprio come prima. Usare `stack.get()` restituirà un'istanza della tua classe `record`, che puoi quindi usare per leggere i valori. Poiché i record sono a sola lettura, dovrai creare una nuova istanza del tuo record per aggiornare i valori.

```java
// read values of component
MyCustomComponent comp = stack.get(ModComponents.MY_CUSTOM_COMPONENT);
float temp = comp.temperature();
boolean burnt = comp.burnt();

// set new component values
stack.set(ModComponents.MY_CUSTOM_COMPONENT, new MyCustomComponent(8.4f, true));

// check for component
if (stack.contains(ModComponents.MY_CUSTOM_COMPONENT)) {
    // do something
}

// remove component
stack.remove(ModComponents.MY_CUSTOM_COMPONENT);
```

Puoi anche impostare un valore predefinito per una componente composita passando un oggetto componente alle tue `Item.Settings`. Per esempio:

```java
public static final Item COUNTER = register(new CounterItem(
    new Item.Settings().component(ModComponents.MY_CUSTOM_COMPONENT, new MyCustomComponent(0.0f, false))
), "counter");
```

Ora puoi memorizzare dati personalizzati su un `ItemStack`. Usa in modo responsabile!

![Oggetto che mostra un tooltip per numero di clic, temperatura e bruciato](/assets/develop/items/custom_component_6.png)
