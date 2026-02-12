---
title: Creare il Tuo Primo Oggetto
description: Impara come registrare un semplice oggetto e come aggiungergli texture, modello e nome.
authors:
  - dicedpixels
  - Earthcomputer
  - IMB11
  - RaphProductions
---

Questa pagina ti presenterà alcuni concetti chiave legati agli oggetti, e come registrargli e aggiungere loro texture, modello e nome.

Se non ne sei al corrente, tutto in Minecraft è memorizzato in registry, e gli oggetti non fanno eccezione.

## Preparare la Tua Classe dei Oggetti {#preparing-your-items-class}

Per semplificare la registrazione degli elementi, è possibile creare un metodo che accetta un identificatore di stringa, alcune proprietà degli oggetti e una fabbrica per creare l'istanza `Item`.

Questo metodo creerà un oggetto con l'identificatore fornito e lo registrerà con il registro degli oggetti del gioco.

Puoi mettere questo metodo in una classe chiamata `Moditems` (o qualunque nome in cui tu voglia nominare la classe).

Anche Mojang fa lo stesso per i suoi oggetti! Prendi ispirazione dalla classe `Items`.

@[code transcludeWith=:::1](@/reference/latest/src/main/java/com/example/docs/item/ModItems.java)

Nota che stiamo usando un `GenericItem`, che ci permette di usare lo stesso metodo `register` per registrare qualsiasi tipo di oggetto che estenda `Item`. Stiamo anche usando un'interfaccia [`Function`](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/util/function/Function.html) per la fabbrica, che ci consentirà di specificare come vogliamo creare il nostro articolo date le proprietà.

## Registrare un Oggetto {#registering-an-item}

Ora puoi registrare un oggetto con il metodo.

Il metodo di registrazione accetta un'istanza della classe `Item.Properties` come parametro. Questa classe ti permette di configurare le proprietà dell'oggetto con vari metodi di costruttore.

::: tip

Se vuoi cambiare la dimensione di uno stack del tuo oggetto, puoi usare il metodo `stacksTo` dalla classe `Item.Properties`.

Questo non funzionerà se hai segnato un oggetto come danneggiabile, poiché la dimensione di uno stack è sempre 1 per oggetti danneggiabili per evitare duplicazioni.

:::

@[code transcludeWith=:::2](@/reference/latest/src/main/java/com/example/docs/item/ModItems.java)

`Item::new` dice alla funzione di registrazione di creare un'istanza `Item` con `Item.Properties` chiamando il costruttore `Item` (`new Item(...)`), che accetta `Item.Properties` come parametro.

Tuttavia, provando ora ad eseguire il client modificato, noterai che il nostro oggetto non esiste ancora nel gioco! Questo perché non hai inizializzato la classe staticamente.

Per fare questo puoi aggiungere un metodo `initialize` pubblico e statico alla tua classe e richiamarlo dall'[initializer della tua mod](../getting-started/project-structure#entrypoints). Per ora il metodo non deve contenere nulla.

@[code transcludeWith=:::3](@/reference/latest/src/main/java/com/example/docs/item/ModItems.java)

@[code transcludeWith=:::1](@/reference/latest/src/main/java/com/example/docs/item/ExampleModItems.java)

Chiamare un metodo su una classe la inizializza staticamente se non è mai stata caricata prima - questo significa che tutti gli attributi `static` vengono calcolati. Questo è il motivo di questo metodo `initialize` fasullo.

## Aggiungere l'Oggetto a una Scheda d'inventario {#adding-the-item-to-a-creative-tab}

::: info

Se volessi aggiungere l'oggetto a una `CreativeModeTab` personalizzata, consulta la pagina [Schede d'inventario personalizzate](./custom-item-groups) per maggiori informazioni.

:::

Per questo esempio, aggiungeremo questo oggetto alla `CreativeModeTab` degli ingredienti, dovrai usare gli eventi delle schede d'inventario dall'API di Fabric - in particolare `ItemGroupEvents.modifyEntriesEvent`

Questo si può fare nel metodo `initialize` della tua classe degli oggetti.

@[code transcludeWith=:::4](@/reference/latest/src/main/java/com/example/docs/item/ModItems.java)

Appena caricato il gioco, vedrai che il nostro oggetto è stato registrato, ed è nella scheda Ingredienti:

![Oggetto nel gruppo ingredienti](/assets/develop/items/first_item_0.png)

Tuttavia, gli manca il seguente:

- Modello dell'Oggetto
- Texture
- Traduzione (nome)

## Dare un Nome all'Oggetto {#naming-the-item}

L'oggetto per ora non ha una traduzione, per cui dovrai aggiungerne una. La chiave di traduzione è già stata fornita da Minecraft: `item.example-mod.suspicious_substance`.

Crea un nuovo file JSON presso: `src/main/resources/assets/example-mod/lang/en_us.json` e mettici la chiave di traduzione, e il suo valore:

```json
{
  "item.example-mod.suspicious_substance": "Suspicious Substance"
}
```

Puoi riavviare il gioco, o ricostruire la tua mod e premere <kbd>F3</kbd>+<kbd>T</kbd> per applicare le modifiche.

## Aggiungere un oggetto del client, una Texture e un Modello {#adding-a-client-item-texture-and-model}

Perché il tuo oggetto abbia l'aspetto desiderato, sono necessari:

- [Una texture per l'oggetto](https://minecraft.wiki/w/Textures#Items)
- [Un modello dell'oggetto](https://minecraft.wiki/w/Model#Item_models)
- [Un oggetto del client](https://minecraft.wiki/w/Items_model_definition)

### Aggiungere una Texture {#adding-a-texture}

::: info

Per maggiori informazioni, vedi la pagina sui [Modelli degli oggetti](./item-models).

:::

Per dare al tuo oggetto una texture e un modello, ti basta creare un'immagine 16x16 come texture per il tuo oggetto e salvarla nella cartella `assets/example-mod/textures/item`. Il nome del file è l'identificatore dell'oggetto, con estensione `.png`.

Per questo esempio, puoi usare questa texture di esempio per `suspicious_substance.png`

<DownloadEntry visualURL="/assets/develop/items/first_item_1.png" downloadURL="/assets/develop/items/first_item_1_small.png">Texture</DownloadEntry>

### Aggiungere un Modello {#adding-a-model}

Appena riavviato/ricaricato il gioco - dovresti vedere che l'oggetto ancora non ha texture, questo perché devi aggiungere un modello che usi questa texture.

Creeremo un semplice modello `item/generated`, che accetti come input solo una texture.

Crea il modello JSON nella cartella `assets/example-mod/models/item`, con lo stesso nome dell'oggetto; `suspicious_substance.json`

@[code](@/reference/latest/src/main/generated/assets/example-mod/models/item/suspicious_substance.json)

#### Comprendere il Modello in JSON {#breaking-down-the-model-json}

- `parent`: Questo è il modello genitore da cui questo modello erediterà. In questo caso è il modello `item/generated`.
- `textures`: Qui è dove definisci le texture per il modello. La chiave `layer0` è la texture che il modello userà.

La maggior parte degli oggetti usa il modello `item/generated` come genitore, perché è un modello semplice che mostra semplicemente la texture.

Ci sono alternative, tra cui `item/handheld` che si usa per oggetti che il giocatore "tiene in mano", come gli utensili.

### Creare l'oggetto del client {#creating-the-client-item}

Minecraft non sa in automatico dove i file dei modelli dei tuoi oggetti si trovino, dobbiamo fornire un oggetto del client.

Crea l'oggetto del client in `assets/example-mod/items`, e come nome del file l'identifier dell'oggetto: `suspicious_substance.json`.

@[code](@/reference/latest/src/main/generated/assets/example-mod/items/suspicious_substance.json)

#### Comprendere il JSON dell'oggetto del client {#breaking-down-the-client-item-json}

- `model`: Questa è la proprietà che contiene il riferimento al nostro modello.
  - `type`: Questo è il tipo del nostro modello. Per la maggior parte degli oggetti dovrebbe essere `minecraft:model`
  - `model`: Questo è l'identifier del modello. Dovrebbe seguire questo formato: `example-mod:item/item_name`

Il tuo oggetto dovrebbe ora avere questo aspetto nel gioco:

![Oggetto con il modello corretto](/assets/develop/items/first_item_2.png)

## Rendere l'Oggetto Compostabile o Combustibile {#making-the-item-compostable-or-a-fuel}

L'API di Fabric fornisce varie registry che si possono usare per aggiungere altre proprietà al tuo oggetto.

Per esempio, per rendere il tuo oggetto compostabile, puoi usare la `CompostingChanceRegistry`:

@[code transcludeWith=:::\_10](@/reference/latest/src/main/java/com/example/docs/item/ModItems.java)

In alternativa, se vuoi rendere il tuo oggetto combustibile, puoi usare l'evento `FuelRegistryEvents.BUILD`:

@[code transcludeWith=:::\_11](@/reference/latest/src/main/java/com/example/docs/item/ModItems.java)

## Aggiungere una Ricetta Basilare {#adding-a-basic-crafting-recipe}

<!-- In the future, an entire section on recipes and recipe types should be created. For now, this suffices. -->

Se vuoi aggiungere una ricetta per il tuo oggetto, devi posizione un file JSON della ricetta nella cartella `data/example-mod/recipe`.

Per maggiori informazioni sul formato delle ricette, consulta queste risorse:

- [Generatore di Ricette JSON](https://crafting.thedestruc7i0n.ca/)
- [Minecraft Wiki - Recipe JSON](https://minecraft.wiki/w/Recipe#JSON_Format)

## Tooltip Personalizzati {#custom-tooltips}

Se vuoi che il tuo oggetto abbia un tooltip personalizzato, dovrai creare una classe che estenda `Item` e faccia override del metodo `appendHoverText`.

::: info

Questo esempio usa la classe `LightningStick` creata nella pagina [Interazioni Personalizzate tra Oggetti](./custom-item-interactions).

:::

@[code lang=java transcludeWith=:::3](@/reference/latest/src/main/java/com/example/docs/item/custom/LightningStick.java)

Ogni chiamata ad `accept()` aggiungerà una linea al tooltip.

![Anteprima del Tooltip](/assets/develop/items/first_item_3.png)
