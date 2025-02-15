---
title: Creare il Tuo Primo Oggetto
description: Impara come registrare un semplice oggetto e come aggiungergli texture, modello e nome.
authors:
  - IMB11
  - dicedpixels
  - RaphProductions
---

Questa pagina ti presenterà alcuni concetti chiave legati agli oggetti, e come registrargli e aggiungere loro texture, modello e nome.

Se non ne sei al corrente, tutto in Minecraft è memorizzato in registry, e gli oggetti non fanno eccezione.

## Preparare la Tua Classe dei Oggetti {#preparing-your-items-class}

Per semplificare la registrazione degli oggetti, puoi creare un metodo che accetta un'istanza di un oggetto e una stringa come identificatore.

Questo metodo creerà un oggetto con l'identificatore fornito e lo registrano nella registry degli oggetti del gioco.

Puoi mettere questo metodo in una classe chiamata `ModItems` (o qualsiasi altro nome).

Anche Mojang fa lo stesso per i suoi oggetti! Prendi ispirazione dalla classe `Items`.

@[code transcludeWith=:::1](@/reference/latest/src/main/java/com/example/docs/item/ModItems.java)

## Registrare un Oggetto {#registering-an-item}

Puoi ora registrare un oggetto con il metodo.

Il costruttore dell'oggetto prende come parametro un'istanza della classe `Items.Settings`. Questa classe ti permette di configurare le proprietà dell'oggetto con vari metodi costruttori.

::: tip
If you want to change your item's stack size, you can use the `maxCount` method in the `Items.Settings`/`FabricItemSettings` class.

Questo non funzionerà se hai segnato un oggetto come danneggiabile, poiché la dimensione di uno stack è sempre 1 per oggetti danneggiabili per evitare duplicazioni.
:::

@[code transcludeWith=:::2](@/reference/latest/src/main/java/com/example/docs/item/ModItems.java)

Tuttavia, provando ora ad eseguire il client modificato, noterai che il nostro oggetto non esiste ancora nel gioco! Questo perché non hai inizializzato la classe staticamente.

Per fare questo puoi aggiungere un metodo `initialize()` pubblico e statico alla tua classe e richiamarlo dall'[initializer della tua mod](./getting-started/project-structure#entrypoints). Per ora il metodo non deve contenere nulla.

@[code transcludeWith=:::3](@/reference/latest/src/main/java/com/example/docs/item/ModItems.java)

@[code transcludeWith=:::1](@/reference/latest/src/main/java/com/example/docs/item/FabricDocsReferenceItems.java)

Chiamare un metodo su una classe la inizializza staticamente se non è mai stata caricata prima - questo significa che tutti gli attributi `static` vengono calcolati. Questo è il motivo di questo metodo `initialize` fasullo.

## Aggiungere l'Oggetto ad un Gruppo di Oggetti {#adding-the-item-to-an-item-group}

:::info
Se volessi aggiungere l'oggetto a un `ItemGroup` personalizzato, consulta la pagina [Gruppi di Oggetti Personalizzati](./custom-item-groups) per maggiori informazioni.
:::

Per questo esempio, aggiungeremo questo oggetto all'`ItemGroup` ingredienti, dovrai usare gli eventi dei gruppi di oggetti dell'API di Fabric - in particolare `ItemGroupEvents.modifyEntriesEvent`

Questo si può fare nel metodo `initialize` della tua classe degli oggetti.

@[code transcludeWith=:::4](@/reference/latest/src/main/java/com/example/docs/item/ModItems.java)

Appena caricato il gioco, vedrai che il nostro oggetto è stato registrato, ed è nel gruppo di oggetti Ingredienti:

![Oggetto nel gruppo ingredienti](/assets/develop/items/first_item_0.png)

Tuttavia, gli manca il seguente:

- Modello dell'Oggetto
- Texture
- Traduzione (nome)

## Dare un Nome all'Oggetto {#naming-the-item}

L'oggetto per ora non ha una traduzione, per cui dovrai aggiungerne una. La chiave di traduzione è già stata fornita da Minecraft: `item.mod_id.suspicious_substance`.

Crea un nuovo file JSON presso: `src/main/resources/assets/mod-id/lang/en_us.json` e mettici la chiave di traduzione, e il suo valore:

```json
{
  "item.mod_id.suspicious_substance": "Suspicious Substance"
}
```

Puoi riavviare il gioco, o ricostruire la tua mod e premere <kbd>F3</kbd>+<kbd>T</kbd> per applicare le modifiche.

## Aggiungere Texture e Modello {#adding-a-texture-and-model}

Per dare al tuo oggetto una texture e un modello, ti basta creare un'immagine 16x16 come texture per il tuo oggetto e salvarla nella cartella `assets/mod-id/textures/item`. Il nome del file è l'identificatore dell'oggetto, con estensione `.png`.

Per questo esempio, puoi usare questa texture di esempio per `suspicious_substance.png`

<DownloadEntry visualURL="/assets/develop/items/first_item_1.png" downloadURL="/assets/develop/items/first_item_1_small.png">Texture</DownloadEntry>

Appena riavviato/ricaricato il gioco - dovresti vedere che l'oggetto ancora non ha texture, questo perché devi aggiungere un modello che usi questa texture.

Appena riavviato/ricaricato il gioco - dovresti vedere che l'oggetto ancora non ha texture, questo perché devi aggiungere un modello che usi questa texture.

Crea il modello JSON nella cartella `assets/mod-id/models/item`, con lo stesso nome dell'oggetto; `suspicious_substance.json`

@[code](@/reference/latest/src/main/generated/assets/fabric-docs-reference/models/item/suspicious_substance.json)

### Comprendere il Modello in JSON {#breaking-down-the-model-json}

- `parent`: Questo è il modello genitore da cui questo modello erediterà. In questo caso è il modello `item/generated`.
- `textures`: Qui è dove definisci le texture per il modello. La chiave `layer0` è la texture che il modello userà.

La maggior parte degli oggetti usa il modello `item/generated` come genitore, perché è un modello semplice che mostra semplicemente la texture.

Ci sono alternative, tra cui `item/handheld` che si usa per oggetti che il giocatore "tiene in mano", come gli utensili.

## Creare la Descrizione del Modello d'Oggetto {#creating-the-item-model-description}

Minecraft non sa in automatico dove i file dei modelli dei tuoi oggetti si trovino, dobbiamo fornire una descrizione del modello dell'oggetto.

Crea la descrizione JSON dell'oggetto in `assets/mod-id/items`, e come nome del file l'identifier dell'oggetto: `suspicious_substance.json`.

@[code](@/reference/latest/src/main/generated/assets/fabric-docs-reference/items/suspicious_substance.json)

### Comprendere il JSON della Descrizione del Modello d'Oggetto {#breaking-down-the-item-model-description-json}

- `model`: Questa è la proprietà che contiene il riferimento al nostro modello.
  - `type`: Questo è il tipo del nostro modello. Per la maggior parte degli oggetti dovrebbe essere `minecraft:model`
  - `model`: Questo è l'identifier del modello. Dovrebbe seguire questo formato: `mod-id:item/item_name`

Il tuo oggetto dovrebbe ora avere questo aspetto nel gioco:

![Oggetto con il modello corretto](/assets/develop/items/first_item_2.png)

## Rendere l'Oggetto Compostabile o Combustibile {#making-the-item-compostable-or-a-fuel}

L'API di Fabric fornisce varie registry che si possono usare per aggiungere altre proprietà al tuo oggetto.

Per esempio, per rendere il tuo oggetto compostabile, puoi usare la `CompostableItemRegistry`:

@[code transcludeWith=:::_10](@/reference/latest/src/main/java/com/example/docs/item/ModItems.java)

In alternativa, se vuoi rendere il tuo oggetto combustibile, puoi usare l'evento `FuelRegistryEvents.BUILD`:

@[code transcludeWith=:::_11](@/reference/latest/src/main/java/com/example/docs/item/ModItems.java)

## Aggiungere una Ricetta Basilare {#adding-a-basic-crafting-recipe}

<!-- In the future, an entire section on recipes and recipe types should be created. For now, this suffices. -->

Se vuoi aggiungere una ricetta per il tuo oggetto, devi posizione un file JSON della ricetta nella cartella `data/mod-id/recipe`.

Per maggiori informazioni sul formato delle ricette, consulta queste risorse:

- [Generatore di Ricette JSON](https://crafting.thedestruc7i0n.ca/)
- [Minecraft Wiki - Recipe JSON](https://minecraft.wiki/w/Recipe#JSON_Format)

## Tooltip Personalizzati {#custom-tooltips}

Se vuoi che il tuo oggetto abbia un tooltip personalizzato, dovrai creare una classe che estenda `Item` e faccia override del metodo `appendTooltip`.

:::info
Questo esempio usa la classe `LightningStick` creata nella pagina [Interazioni Personalizzate tra Oggetti](./custom-item-interactions).
:::

@[code lang=java transcludeWith=:::3](@/reference/latest/src/main/java/com/example/docs/item/custom/LightningStick.java)

Ogni chiamata di `add()` aggiungerà una linea al tooltip.

![Anteprima del Tooltip](/assets/develop/items/first_item_3.png)
