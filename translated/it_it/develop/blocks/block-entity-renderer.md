---
title: Renderer dei Blocchi-Entità
description: Impara come vivacizzare il rendering con i renderer di blocchi-entità.
authors:
  - natri0
resources:
  https://docs.neoforged.net/docs/blockentities/ber/: BlockEntityRenderer - NeoForge Docs
---

A volte non basta usare il formato del modello di Minecraft. Se devi aggiungere del rendering dinamico all'aspetto del tuo blocco, dovrai usare un `BlockEntityRenderer`.

Per esempio, facciamo in modo che il Blocco Contatore dell'[articolo sui Blocchi-Entità](../blocks/block-entities) mostri il numero di clic sulla sua faccia superiore.

## Creare un BlockEntityRenderer {#creating-a-blockentityrenderer}

Il rendering di blocchi-entità utilizza un sistema di presenta/renderizza dove prima presenti i dati necessari a renderizzare un oggetto a schermo, poi il gioco renderizza l'oggetto usando lo stato presentato.

Nel creare un `BlockEntityRenderer` per il `CounterBlockEntity`, è importante inserire la classe nell'insieme delle fonti corretto, come `src/client/`, se il tuo progetto divide gli insiemi delle fonti tra client e server. Accedere a classi legate al rendering direttamente nell'insieme delle fonti `src/main/` non è sicuro perché quelle classi potrebbero essere caricare su un server.

Prima di tutto, dobbiamo creare un `BlockEntityRenderState` per il nostro `CounterBlockEntity` per contenere i dati che verrano usati per il rendering. In questo caso, sarà necessario che i `clicks` siano disponibili durante il rendering

<<< @/reference/latest/src/client/java/com/example/docs/rendering/blockentity/CounterBlockEntityRenderState.java#render_state

Poi creiamo un `BlockEntityRenderer` per il nostro `CounterBlockEntity`.

<<< @/reference/latest/src/client/java/com/example/docs/rendering/blockentity/CounterBlockEntityRenderer.java#renderer_structure

La nuova classe ha un costruttore con un `BlockEntityRendererProvider.Context` come parametro. Il `Context` ha alcune utilità per il rendering, come l'`ItemRenderer` o il `Font`.
Inoltre, includendo un costruttore come questo, è possibile usare il costruttore come interfaccia funzionale per la `BlockEntityRendererProvider`:

<<< @/reference/latest/src/client/java/com/example/docs/ExampleModBlockEntityRenderer.java#register_block_entity_renderer

Sovrascriveremo alcuni metodi per impostare lo stato di rendering, oltre al metodo `submit` dove verrà impostata la logica di rendering.

`createRenderState` può essere usato per inizializzare lo stato di rendering.

<<< @/reference/latest/src/client/java/com/example/docs/rendering/blockentity/CounterBlockEntityRenderer.java#create_render_state

`extractRenderState` può essere usato per aggiornare lo stato di rendering con i dati dell'entità.

<<< @/reference/latest/src/client/java/com/example/docs/rendering/blockentity/CounterBlockEntityRenderer.java#extract_render_state

Dovresti registrare i renderer dei blocchi-entità nella tua classe `ClientModInitializer`.

`BlockEntityRenderers` è una registry che mappa ogni `BlockEntityType` con del codice di rendering personalizzato al rispettivo `BlockEntityRenderer`.

## Disegnare su Blocchi {#drawing-on-blocks}

Ora che abbiamo un renderer, possiamo disegnare. Il metodo `submit` viene chiamato ad ogni frame, ed è qui che la magia del rendering avviene.

### Orientarsi {#moving-around}

Anzitutto, dobbiamo bilanciare e ruotare il testo in modo che sia sul lato superiore del blocco.

::: info

Come suggerisce il nome, il `PoseStack` è uno _stack_, il che significa che puoi inserirci (push) ed estrarne (pop) le trasformazioni.
Una buona regola di base è inserirne uno nuovo all'inizio del metodo `submit` ed estrarlo alla fine, in modo che il rendering di un blocco non influenzi gli altri.

Si possono trovare maggiori informazioni riguardo al `PoseStack` nell'[articolo sui Concetti Base del Rendering](../rendering/basic-concepts).

:::

Per capire meglio le traslazioni e le rotazioni necessarie, visualizziamole. Nell'immagine, il blocco verde è dove il testo verrebbe disegnato, nel punto più in basso a sinistra del blocco in maniera predefinita:

![Posizione di rendering predefinita](/assets/develop/blocks/block_entity_renderer_1.png)

Quindi dobbiamo inizialmente spostare il testo a metà del blocco sugli assi X e Z, poi muoverlo in cima al blocco lungo l'asse Y:

![Blocco verde nel punto centrale in alto](/assets/develop/blocks/block_entity_renderer_2.png)

Questo si fa con una sola chiamata a `translate`:

<<< @/reference/latest/src/client/java/com/example/docs/rendering/blockentity/CounterBlockEntityRenderer.java#translate

Ecco che abbiamo completato la _tranlazione_, mancano la _rotazione_ e la _scala_.

Per impostazione predefinita il testo viene disegnato sul piano XY, quindi dobbiamo ruotarlo di 90 gradi attorno all'asse X perché sia orientato verso l'alto sul piano XZ:

![Blocco verde nel punto centrale in alto, orientato verso l'alto](/assets/develop/blocks/block_entity_renderer_3.png)

Il `PoseStack` non ha una funzione `rotate`, invece dobbiamo usare `mulPose` e `Axis.XP`:

<<< @/reference/latest/src/client/java/com/example/docs/rendering/blockentity/CounterBlockEntityRenderer.java#rotate

Ora il testo è nella posizione corretta, ma è troppo grande. Il `BlockEntityRenderer` mappa l'intero blocco ad un cubo `[-0.5, 0.5]`, mentre il `Font` usa come coordinate Y `[0, 9]`. Per questo dobbiamo rimpicciolirlo di un fattore di 18:

<<< @/reference/latest/src/client/java/com/example/docs/rendering/blockentity/CounterBlockEntityRenderer.java#scale

Ora la trasformazione completa ha questo aspetto:

<<< @/reference/latest/src/client/java/com/example/docs/rendering/blockentity/CounterBlockEntityRenderer.java#transformations

### Disegnare Testo {#drawing-text}

Come detto prima, il `Context` passato nel costruttore del nostro renderer ha un `Font` che possiamo usare per misurare il testo (`width`), che è utile per centrarelo.

Per disegnare il testo, dovremo fornire i dati necessari alla coda di renderizzazione. Siccome stiamo disegnando del testo, possiamo usare il metodo `submitText` fornito attraverso l'istanza di `SubmitNodeCollector` passata nel metodo `submit`.

<<< @/reference/latest/src/client/java/com/example/docs/rendering/blockentity/CounterBlockEntityRenderer.java#drawing_text

Il metodo `submitText` prende molti parametri, ma i più importanti sono:

- la `FormattedCharSequence` da disegnare;
- Le sue coordinate `x` e `y`;
- Il valore RGB di `color`;
- il `PoseStack` che descrive come deve essere trasformato.

Dopo tutto questo lavoro, eccone il risultato:

![Blocco Contatore con un numero in cima](/assets/develop/blocks/block_entity_renderer_4.png)
