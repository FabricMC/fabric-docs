---
title: Concetti Base del Rendering
description: Impara i concetti base del rendering usando il motore grafico di Minecraft.
authors:
  - "0x3C50"
  - IMB11
  - MildestToucan
---

<!---->

::: warning

Anche se Minecraft è costruito usando OpenGL, a partire da 1.17 non puoi usare metodi OpenGL legacy per fare rendering. Devi invece usare il nuovo sistema `BufferBuilder`, che formatta i dati del rendering e li carica su OpenGL perché vengano disegnati.

Per riassumere, devi usare il sistema di rendering di Minecraft, o crearne uno tuo che usa `GL.glDrawElements()`.

:::

:::warning AGGIORNAMENTO IMPORTANTE

A partire da 1.21.6 si stanno implementando grandi modifiche alle procedure del rendering, tra cui la migrazione verso `RenderType` e `RenderPipeline` e, soprattutto, `RenderState`, con l'obiettivo ultimo di riuscire a preparare il frame successivo mentre si disegna il frame corrente, in contemporanea. Nella fase di "preparazione", tutti i dati del gioco usati per il rendering sono estratti come `RenderState`, cosicché un altro thread possa lavorare per disegnare quel frame mentre si estrae il prossimo.

Per esempio, in 1.21.8, il rendering alla GUI ha adottato questo schema, e i metodi `GuiGraphics` aggiungono semplicemente al `RenderState`. Il caricamento effettivo a `BufferBuilder` avviene alla fine della fase di preparazione, dopo che tutti gli elementi sono stati aggiunti al `RenderState`. Vedi `GuiRenderer#prepare`.

Questo articolo tratta delle basi del rendering e, purché siano alquanto rilevanti, la maggior parte delle volte vi sono livelli superiori di astrazione per prestazioni e compatibilità migliori. Per maggiori informazioni, vedi la pagina [Rendering nel mondo](./world).

:::

Questa pagina tratterà le basi del rendering usando il nuovo sistema, presentando terminologia e concetti chiave.

Anche se molto del rendering in Minecraft viene astratto attraverso i vari metodi `GuiGraphics`, e probabilmente non ti servirà toccare nulla di quel che viene menzionato qui, è comunque importante capire le basi di come funziona il rendering.

## Il `Tesselator` {#the-tesselator}

Il `Tesselator` è la principale classe usata per renderizzare le cose in Minecraft. È un singleton, cioè solo un'istanza è presente in gioco. Puoi ottenere l'istanza usando `Tesselator.getInstance()`.

## Il `BufferBuilder` {#the-bufferbuilder}

Il `BufferBuilder` è la classe usata per formattare e caricare i dati di rendering su OpenGL. Viene usata per creare un buffer, che viene caricato su OpenGL per essere disegnato.

Il `Tesselator` viene usato per creare un `BufferBuilder`, che viene usato per formattare e caricare i dati di rendering su OpenGL.

### Inizializzare il `BufferBuilder` {#initializing-the-bufferbuilder}

Prima di poter scrivere al `BufferBuilder`, devi inizializzarlo. Questo viene fatto usando `Tesselator#begin(...)`, che accetta un `VertexFormat` e una modalità di disegno e restituisce un `BufferBuilder`.

#### Formati dei Vertici {#vertex-formats}

Il `VertexFormat` definisce gli elementi che includiamo nel nostro buffer di dati e precisa come questi elementi debbano essere trasmessi a OpenGL.

I seguenti elementi `VertexFormat` predefiniti sono disponibili in `DefaultVertexFormat`:

| Elemento                      | Formato                                                                                 |
| ----------------------------- | --------------------------------------------------------------------------------------- |
| `EMPTY`                       | `{ }`                                                                                   |
| `BLOCK`                       | `{ position, color, texture uv, texture light (2 shorts), texture normal (3 sbytes) }`  |
| `NEW_ENTITY`                  | `{ position, color, texture uv, overlay (2 shorts), texture light, normal (3 sbytes) }` |
| `PARTICLE`                    | `{ position, texture uv, color, texture light }`                                        |
| `POSITION`                    | `{ position }`                                                                          |
| `POSITION_COLOR`              | `{ position, color }`                                                                   |
| `POSITION_COLOR_NORMAL`       | `{ position, color, normal }`                                                           |
| `POSITION_COLOR_LIGHTMAP`     | `{ position, color, light }`                                                            |
| `POSITION_TEX`                | `{ position, uv }`                                                                      |
| `POSITION_TEX_COLOR`          | `{ position, uv, color }`                                                               |
| `POSITION_COLOR_TEX_LIGHTMAP` | `{ position, color, uv, light }`                                                        |
| `POSITION_TEX_LIGHTMAP_COLOR` | `{ position, uv, light, color }`                                                        |
| `POSITION_TEX_COLOR_NORMAL`   | `{ position, uv, color, normal }`                                                       |

#### Modalità di Disegno {#draw-modes}

La modalità di disegno definisce come sono disegnati i dati. Sono disponibili le seguenti modalità di disegno in `VertexFormat.Mode`:

| Modalità di Disegno | Descrizione                                                                                                                                                         |
| ------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| `LINES`             | Ogni elemento è fatto da 2 vertici ed è rappresentato come una linea singola.                                                                       |
| `LINE_STRIP`        | Il primo elemento richiede 2 vertici. Elementi addizionali vengono disegnati con un solo nuovo vertice, creando una linea continua. |
| `DEBUG_LINES`       | Simile a `Mode.LINES`, ma la linea è sempre esattamente larga un pixel sullo schermo.                                                               |
| `DEBUG_LINE_STRIP`  | Come `Mode.LINE_STRIP`, ma le linee sono sempre larghe un pixel.                                                                                    |
| `TRIANGLES`         | Ogni elemento è fatto da 3 vertici, formando un triangolo.                                                                                          |
| `TRIANGLE_STRIP`    | Inizia con 3 vertici per il primo triangolo. Ogni vertice aggiuntivo forma un nuovo triangolo con gli ultimi due vertici.           |
| `TRIANGLE_FAN`      | Inizia con 3 vertici per il primo triangolo. Ogni vertice aggiuntivo forma un triangolo con il primo e l'ultimo vertice.            |
| `QUADS`             | Ogni elemento è fatto da 4 vertici, formando un quadrilatero.                                                                                       |

### Scrivere al `BufferBuilder` {#writing-to-the-bufferbuilder}

Una volta che il `BufferBuilder` è inizializzato, puoi scriverci dei dati.

Il `BufferBuilder` permette di costruire il nostro buffer, un vertice dopo l'altro. Per aggiungere un vertice, usiamo il metodo `buffer.addVertex(Matrix4F, float, float, float)`. Il parametro `Matrix4f` è la matrice di trasformazione, che discuteremo più dettagliatamente in seguito. I tre parametri float rappresentano le coordinate (x, y, z) della posizione del vertice.

Questo metodo restituisce un costruttore di vertice, che possiamo usare per specificare informazioni addizionali per il vertice. È cruciale seguire l'ordine del nostro `VertexFormat` definito quando aggiungiamo questa informazione. Se non lo facciamo, OpenGL potrebbe non interpretare i nostri dati correttamente. Dopo aver finito la costruzione di un vertice, se vuoi puoi continuare ad aggiungere altri vertici e dati al buffer.

Importante è anche capire il concetto di culling. Il culling è il processo con cui si rimuovono facce di una forma 3D che non sono visibili dalla prospettiva dell'osservatore. Se i vertici per una faccia sono specificati nell'ordine sbagliato, la faccia potrebbe non essere renderizzata correttamente a causa del culling.

#### Cos'è una Matrice di Trasformazione? {#what-is-a-transformation-matrix}

Una matrice di trasformazione è una matrice 4x4 che viene usata per trasformare un vettore. In Minecraft, la matrice di trasformazione sta solo trasformando le coordinate che diamo nella chiamata ad `addVertex`. Le trasformazioni possono scalare il nostro modello, muoverlo e ruotarlo.

A volte viene chiamata anche matrice di posizione, o matrice modello.

Solitamente è ottenuta dalla classe `Matrix3x2fStack`, che può essere ottenuta attraverso l'oggetto `GuiGraphics` chiamando il metodo `GuiGraphics#pose()`.

#### Renderizzare una Striscia di Triangoli {#rendering-a-triangle-strip}

Spiegare come scrivere al `BufferBuilder` è più semplice con un esempio pratico. Immaginiamo di voler renderizzare qualcosa usando la modalità di disegno `VertexFormat.Mode.TRIANGLE_STRIP` e il formato vertice `POSITION_COLOR`.

Disegneremo vertici nelle seguenti posizioni sul HUD (in ordine):

```text:no-line-numbers
(20, 20)
(5, 40)
(35, 40)
(20, 60)
```

Questo dovrebbe darci un diamante carino - siccome stiamo usando la modalità di disegno `TRIANGLE_STRIP`, il renderizzatore eseguirà i seguenti passaggi:

![Quattro passaggi che mostrano il posizionamento dei vertici sullo schermo per formare due triangoli](/assets/develop/rendering/concepts-practical-example-draw-process.png)

Siccome stiamo disegnando sulla HUD in questo esempio, useremo la `HudElementRegistry`:

:::warning AGGIORNAMENTO IMPORTANTE

A partire da 1.21.8, il matrix stack usato per il rendering del HUD è cambiato da `PoseStack` a `Matrix3x2fStack`. La maggior parte dei metodi è leggermente diversa, e non prende più un parametro `z`, ma i concetti sono gli stessi.

Inoltre, il codice qua sotto non combacia perfettamente con la spiegazione di sopra: non si deve scrivere manualmente al `BufferBuilder`, perché i metodi di `GuiGraphics` scrivono automaticamente al `BufferBuilder` del HUD durante la preparazione.

Leggi l'aggiornamento importante sopra per maggiori informazioni.

:::

**Registrazione dell'elemento:**

@[code lang=java transcludeWith=:::registration](@/reference/latest/src/client/java/com/example/docs/rendering/RenderingConceptsEntrypoint.java)

**Implementazione di `hudLayer()`:**

@[code lang=java transcludeWith=:::hudLayer](@/reference/latest/src/client/java/com/example/docs/rendering/RenderingConceptsEntrypoint.java)

Questo risulta nel seguente disegno sul HUD:

![Risultato Finale](/assets/develop/rendering/concepts-practical-example-final-result.png)

::: tip

Prova a giocare coi colori e le posizioni dei vertici per vedere che succede! Puoi anche provare a usare modalità di disegno e formati vertice differenti.

:::

## Il `PoseStack` {#the-posestack}

::: warning

Il codice di questa sezione e il testo stanno spiegando cose diverse!

Il codice mostra `Matrix3x2fStack`, che viene usato per il rendering nel HUD a partire da 1.21.8, mentre il testo descrive `PoseStack`, che ha metodi leggermente diversi.

Leggi l'aggiornamento importante sopra per maggiori informazioni.

:::

Dopo aver imparato come scrivere al `BufferBuilder`, ti starai chiedendo come trasformare il tuo modello - anche animarlo magari. Qui è dove entra in gioco la classe `PoseStack`.

La classe `PoseStack` ha i seguenti metodi:

- `pushPose()` - Spinge una nuova matrice sullo stack.
- `popPose()` - Elimina la matrice in cima allo stack.
- `last()` - Restituisce la matrice in cima allo stack.
- `translate(x, y, z)` - Trasla la matrice in cima allo stack.
- `translate(vec3)`
- `scale(x, y, z)` - Scala la matrice in cima allo stack.

Puoi anche moltiplicare la matrice in cima allo stack usando i quaternioni, che tratteremo nella prossima sezione.

Usando l'esempio di prima, possiamo ingrandire e rimpicciolire il nostro diamante usando il `PoseStack` e il `tickDelta` - che è l'"avanzamento" tra il tick di gioco precedente e quello successivo. Chiariremo questo ulteriormente nella pagina [Rendering nel HUD](./hud#render-tick-counter).

::: warning

Assicurati di spingere lo stack di matrici, poi eliminalo dopo aver finito. Se così non facessi, ti rimarrebbe un matrix stack corrotto, che causerebbe problemi di rendering.

Assicurati di spingere lo stack di matrici prima di prendere una matrice di trasformazione!

:::

@[code lang=java transcludeWith=:::2](@/reference/latest/src/client/java/com/example/docs/rendering/RenderingConceptsEntrypoint.java)

![Un video che mostra il diamante ingrandirsi e rimpicciolirsi](/assets/develop/rendering/concepts-matrix-stack.webp)

## Quaternioni (Cose che Ruotano) {#quaternions-rotating-things}

::: warning

Il codice di questa sezione e il testo stanno spiegando cose diverse!

Il codice mostra il rendering nel HUD, mentre il testo descrive il rendering in uno spazio 3D del mondo.

Leggi l'aggiornamento importante sopra per maggiori informazioni.

:::

I quaternioni sono un modo di rappresentare rotazioni in uno spazio 3D. Vengono usate per ruotare la matrice in cima al `PoseStack` usando il metodo `rotateAround(quaternionfc, x, y, z)`.

Difficilmente dovrai usare una classe Quaternion direttamente, siccome Minecraft fornisce varie istanze Quaternion pre-costruite nella sua classe di utilità `Axis`.

Immaginiamo di voler ruotare il nostro quadrato attorno all'asse z. Possiamo farlo usando il `PoseStack` e il metodo `rotateAround(quaternionfc, x, y, z)`.

@[code lang=java transcludeWith=:::3](@/reference/latest/src/client/java/com/example/docs/rendering/RenderingConceptsEntrypoint.java)

Il risultato è il seguente:

![Un video che mostra il diamante che ruota attorno all'asse z](/assets/develop/rendering/concepts-quaternions.webp)
