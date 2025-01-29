---
title: Renderer dei Blocchi-Entità
description: Impara come vivacizzare il rendering con i renderer di blocchi-entità.
authors:
  - natri0
---

# Renderer dei Blocchi-Entità {#block-entity-renderers}

A volte non basta usare il formato del modello di Minecraft. Se devi aggiungere del rendering dinamico all'aspetto del tuo blocco, dovrai usare un `BlockEntityRenderer`.

Per esempio, facciamo in modo che il Blocco Contatore dell'[articolo sui Blocchi-Entità](../blocks/block-entities) mostri il numero di clic sulla sua faccia superiore.

## Creare un BlockEntityRenderer {#creating-a-blockentityrenderer}

Anzitutto, dobbiamo creare un `BlockEntityRenderer` per il nostro `CounterBlockEntity`.

Nel creare un `BlockEntityRenderer` per il `CounterBlockEntity`, è importante inserire la classe nell'insieme delle fonti corretto, come `src/client/`, se il tuo progetto divide gli insiemi delle fonti tra client e server. Accedere a classi legate al rendering direttamente nell'insieme delle fonti `src/main/` non è sicuro perché quelle classi potrebbero essere caricare su un server.

@[code transcludeWith=:::1](@/reference/latest/src/client/java/com/example/docs/rendering/blockentity/CounterBlockEntityRenderer.java)

La nuova classe ha un costruttore con un `BlockEntityRendererFactory.Context` come parametro. Il `Context` ha alcune utilità per il rendering, come l'`ItemRenderer` o il `TextRenderer`.
Inoltre, includendo un costruttore come questo, è possibile usare il costruttore come interfaccia funzionale per la `BlockEntityRendererFactory`:

@[code transcludeWith=:::1](@/reference/latest/src/client/java/com/example/docs/FabricDocsBlockEntityRenderer.java)

Dovresti registrare i renderer dei blocchi-entità nella tua classe `ClientModInitializer`.

`BlockEntityRendererFactories` è una registry che mappa ogni `BlockEntityType` con del codice di rendering personalizzato al rispettivo `BlockEntityRenderer`.

## Disegnare su Blocchi {#drawing-on-blocks}

Ora che abbiamo un renderer, possiamo disegnare. Il metodo `render` viene chiamato ad ogni frame, ed è qui che la magia del rendering avviene.

### Orientarsi {#moving-around}

Anzitutto, dobbiamo bilanciare e ruotare il testo in modo che sia sul lato superiore del blocco.

:::info
Come suggerisce il nome, il `MatrixStack` è uno _stack_, il che significa che puoi inserirci (push) ed estrarne (pop) le trasformazioni.
Una buona regola di base è inserirne uno nuovo all'inizio del metodo `render` ed estrarlo alla fine, in modo che il rendering di un blocco non influenzi gli altri.

Si possono trovare maggiori informazioni riguardo al `MatrixStack` nell'[articolo sui Concetti Base del Rendering](../rendering/basic-concepts).
:::

Per capire meglio le traslazioni e le rotazioni necessarie, visualizziamole. Nell'immagine, il blocco verde è dove il testo verrebbe disegnato, nel punto più in basso a sinistra del blocco in maniera predefinita:

![Posizione di rendering predefinita](/assets/develop/blocks/block_entity_renderer_1.png)

Quindi dobbiamo inizialmente spostare il testo a metà del blocco sugli assi X e Z, poi muoverlo in cima al blocco lungo l'asse Y:

![Blocco verde nel punto centrale in alto](/assets/develop/blocks/block_entity_renderer_2.png)

Questo si fa con una sola chiamata a `translate`:

```java
matrices.translate(0.5, 1, 0.5);
```

Ecco che abbiamo completato la _tranlazione_, mancano la _rotazione_ e la _scala_.

Per impostazione predefinita il testo viene disegnato sul piano XY, quindi dobbiamo ruotarlo di 90 gradi attorno all'asse X perché sia orientato verso l'alto sul piano XZ:

![Blocco verde nel punto centrale in alto, orientato verso l'alto](/assets/develop/blocks/block_entity_renderer_3.png)

Il `MatrixStack` non ha una funzione `rotate`, invece dobbiamo usare `multiply` e `RotationAxis.POSITIVE_X`:

```java
matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(90));
```

Ora il testo è nella posizione corretta, ma è troppo grande. Il `BlockEntityRenderer` mappa l'intero blocco ad un cubo `[-0.5, 0.5]`, mentre il `TextRenderer` usa come coordinate Y `[0, 9]`. Per questo dobbiamo rimpicciolirlo di un fattore di 18:

```java
matrices.scale(1/18f, 1/18f, 1/18f);
```

Ora la trasformazione completa ha questo aspetto:

@[code transcludeWith=:::2](@/reference/latest/src/client/java/com/example/docs/rendering/blockentity/CounterBlockEntityRenderer.java)

### Disegnare Testo {#drawing-text}

Come menzionato in precedenza, il `Context` passato al costruttore del nostro renderer ha un `TextRenderer` che possiamo usare per disegnare testo. Per questo esempio lo salveremo in un attributo.

Il `TextRenderer` ha metodi per misurare il testo (`getWidth`), il che è utile per centrarlo, e per disegnarlo (`draw`).

@[code transcludeWith=:::3](@/reference/latest/src/client/java/com/example/docs/rendering/blockentity/CounterBlockEntityRenderer.java)

Il metodo `draw` prende molti parametri, ma quelli più importanti sono:

- Il `Text` (o `String`) da disegnare;
- Le sue coordinate `x` e `y`;
- Il valore RGB di `color`;
- La `Matrix4f` che descrive come deve essere trasformato (peeeeer ottenerne una da un `MatrixStack`, possiamo usare `.peek().getPositionMatrix()` per ottenere la `Matrix4f` per la voce in cima).

Dopo tutto questo lavoro, eccone il risultato:

![Blocco Contatore con un numero in cima](/assets/develop/blocks/block_entity_renderer_4.png)
