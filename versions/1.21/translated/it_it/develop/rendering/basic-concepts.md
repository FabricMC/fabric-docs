---
title: Concetti Base del Rendering
description: Impara i concetti base del rendering usando il motore grafico di Minecraft.
authors:
  - IMB11
  - "0x3C50"
---

# Concetti Base del Rendering {#basic-rendering-concepts}

::: warning
Although Minecraft is built using OpenGL, as of version 1.17+ you cannot use legacy OpenGL methods to render your own things. Instead, you must use the new `BufferBuilder` system, which formats rendering data and uploads it to OpenGL to draw.

Per riassumere, devi usare il sistema di rendering di Minecraft, o crearne uno tuo che usa `GL.glDrawElements()`.
:::

Questa pagina tratterà le basi del rendering usando il nuovo sistema, presentando terminologia e concetti chiave.

Anche se molto del rendering in Minecraft viene astratto attraverso i vari metodi `DrawContext`, e probabilmente non ti servirà toccare nulla di quel che viene menzionato qui, è comunque importante capire le basi di come funziona il rendering.

## Il `Tessellator` {#the-tessellator}

Il `Tessellator` è la principale classe usata per renderizzare le cose in Minecraft. È un singleton, cioè solo un'istanza è presente in gioco. Puoi ottenere l'istanza usando `Tessellator.getInstance()`.

## Il `BufferBuilder` {#the-bufferbuilder}

Il `BufferBuilder` è la classe usata per formattare e caricare i dati di rendering su OpenGL. Viene usata per creare un buffer, che viene caricato su OpenGL per essere disegnato.

Il `Tessellator` viene usato per creare un `BufferBuilder`, che viene usato per formattare e caricare i dati di rendering su OpenGL.

### Inizializzare il `BufferBuilder` {#initializing-the-bufferbuilder}

Prima di poter scrivere al `BufferBuilder`, devi inizializzarlo. Questo viene fatto usando `Tessellator#begin(...)`, che prende un `VertexFormat` e una modalità di disegno e restituisce un `BufferBuilder`.

#### Formati dei Vertici {#vertex-formats}

Il `VertexFormat` definisce gli elementi che includiamo nel nostro buffer di dati e precisa come questi elementi debbano essere trasmessi a OpenGL.

I seguenti elementi `VertexFormat` sono disponibili:

| Elemento                                      | Formato                                                                                 |
| --------------------------------------------- | --------------------------------------------------------------------------------------- |
| `BLIT_SCREEN`                                 | `{ position (3 floats: x, y and z), uv (2 floats), color (4 ubytes) }`                  |
| `POSITION_COLOR_TEXTURE_LIGHT_NORMAL`         | `{ position, color, texture uv, texture light (2 shorts), texture normal (3 sbytes) }`  |
| `POSITION_COLOR_TEXTURE_OVERLAY_LIGHT_NORMAL` | `{ position, color, texture uv, overlay (2 shorts), texture light, normal (3 sbytes) }` |
| `POSITION_TEXTURE_COLOR_LIGHT`                | `{ position, texture uv, color, texture light }`                                        |
| `POSITION`                                    | `{ position }`                                                                          |
| `POSITION_COLOR`                              | `{ position, color }`                                                                   |
| `LINES`                                       | `{ position, color, normal }`                                                           |
| `POSITION_COLOR_LIGHT`                        | `{ position, color, light }`                                                            |
| `POSITION_TEXTURE`                            | `{ position, uv }`                                                                      |
| `POSITION_COLOR_TEXTURE`                      | `{ position, color, uv }`                                                               |
| `POSITION_TEXTURE_COLOR`                      | `{ position, uv, color }`                                                               |
| `POSITION_COLOR_TEXTURE_LIGHT`                | `{ position, color, uv, light }`                                                        |
| `POSITION_TEXTURE_LIGHT_COLOR`                | `{ position, uv, light, color }`                                                        |
| `POSITION_TEXTURE_COLOR_NORMAL`               | `{ position, uv, color, normal }`                                                       |

#### Modalità di Disegno {#draw-modes}

La modalità di disegno definisce come sono disegnati i dati. Sono disponibili le seguenti modalità di disegno:

| Modalità di Disegno         | Descrizione                                                                                                                                                         |
| --------------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| `DrawMode.LINES`            | Ogni elemento è fatto da 2 vertici ed è rappresentato come una linea singola.                                                                       |
| `DrawMode.LINE_STRIP`       | Il primo elemento richiede 2 vertici. Elementi addizionali vengono disegnati con un solo nuovo vertice, creando una linea continua. |
| `DrawMode.DEBUG_LINES`      | Simile a `DrawMode.LINES`, ma la linea è sempre esattamente larga un pixel sullo schermo.                                                           |
| `DrawMode.DEBUG_LINE_STRIP` | Come `DrawMode.LINE_STRIP`, ma le linee sono sempre larghe un pixel.                                                                                |
| `DrawMode.TRIANGLES`        | Ogni elemento è fatto da 3 vertici, formando un triangolo.                                                                                          |
| `DrawMode.TRIANGLE_STRIP`   | Inizia con 3 vertici per il primo triangolo. Ogni vertice aggiuntivo forma un nuovo triangolo con gli ultimi due vertici.           |
| `DrawMode.TRIANGLE_FAN`     | Inizia con 3 vertici per il primo triangolo. Ogni vertice aggiuntivo forma un triangolo con il primo e l'ultimo vertice.            |
| `DrawMode.QUADS`            | Ogni elemento è fatto da 4 vertici, formando un quadrilatero.                                                                                       |

### Scrivere al `BufferBuilder` {#writing-to-the-bufferbuilder}

Una volta che il `BufferBuilder` è inizializzato, puoi scriverci dei dati.

Il `BufferBuilder` permette di costruire il nostro buffer, un vertice dopo l'altro. Per aggiungere un vertice, usiamo il metodo `buffer.vertex(matrix, float, float, float)`. Il parametro `matrix` è la matrice di trasformazione, che discuteremo più dettagliatamente in seguito. I tre parametri float rappresentano le coordinate (x, y, z) della posizione del vertice.

Questo metodo restituisce un costruttore di vertice, che possiamo usare per specificare informazioni addizionali per il vertice. È cruciale seguire l'ordine del nostro `VertexFormat` definito quando aggiungiamo questa informazione. Se non lo facciamo, OpenGL potrebbe non interpretare i nostri dati correttamente. Dopo aver finito la costruzione di un vertice, se vuoi puoi continuare ad aggiungere altri vertici e dati al buffer.

Importante è anche capire il concetto di culling. Il culling è il processo con cui si rimuovono facce di una forma 3D che non sono visibili dalla prospettiva dell'osservatore. Se i vertici per una faccia sono specificati nell'ordine sbagliato, la faccia potrebbe non essere renderizzata correttamente a causa del culling.

#### Cos'è una Matrice di Trasformazione? {#what-is-a-transformation-matrix}

Una matrice di trasformazione è una matrice 4x4 che viene usata per trasformare un vettore. In Minecraft, la matrice di trasformazione sta solo trasformando le coordinate che diamo nella chiamata del vertice. Le trasformazioni possono scalare il nostro modello, muoverlo e ruotarlo.

A volte viene chiamata anche matrice di posizione, o matrice modello.

Solitamente è ottenuta dalla classe `MatrixStack`, che può essere ottenuta attraverso l'oggetto `DrawContext`:

```java
drawContext.getMatrices().peek().getPositionMatrix();
```

#### Renderizzare una Striscia di Triangoli {#rendering-a-triangle-strip}

Spiegare come scrivere al `BufferBuilder` è più semplice con un esempio pratico. Immaginiamo di voler renderizzare qualcosa usando la modalità di disegno `DrawMode.TRIANGLE_STRIP` e il formato vertice `POSITION_COLOR`.

Disegneremo vertici nelle seguenti posizioni sul HUD (in ordine):

```txt
(20, 20)
(5, 40)
(35, 40)
(20, 60)
```

Questo dovrebbe darci un diamante carino - siccome stiamo usando la modalità di disegno `TRIANGLE_STRIP`, il renderizzatore eseguirà i seguenti passaggi:

![Quattro passaggi che mostrano il posizionamento dei vertici sullo schermo per formare due triangoli](/assets/develop/rendering/concepts-practical-example-draw-process.png)

Siccome stiamo disegnando sulla HUD in questo esempio, useremo l'evento `HudRenderCallback`:

@[code lang=java transcludeWith=:::1](@/reference/latest/src/client/java/com/example/docs/rendering/RenderingConceptsEntrypoint.java)

Questo risulta nel seguente disegno sul HUD:

![Risultato Finale](/assets/develop/rendering/concepts-practical-example-final-result.png)

:::tip
Prova a giocare coi colori e le posizioni dei vertici per vedere che succede! Puoi anche provare a usare modalità di disegno e formati vertice differenti.
:::

## La `MatrixStack` {#the-matrixstack}

Dopo aver imparato come scrivere al `BufferBuilder`, ti starai chiedendo come trasformare il tuo modello - anche animarlo magari. Qui è dove entra in gioco la classe `MatrixStack`.

La classe `MatrixStack` ha i seguenti metodi:

- `push()` - Spinge una nuova matrice sullo stack.
- `pop()` - Elimina la matrice in cima allo stack.
- `peek()` - Restituisce la matrice in cima allo stack.
- `translate(x, y, z)` - Trasla la matrice in cima allo stack.
- `scale(x, y, z)` - Scala la matrice in cima allo stack.

Puoi anche moltiplicare la matrice in cima allo stack usando i quaternioni, che tratteremo nella prossima sezione.

Usando l'esempio di prima, possiamo ingrandire e rimpicciolire il nostro diamante usando la `MatrixStack` e il `tickDelta` - che è il tempo passato dall'ultimo frame.

::: warning
You must first push the matrix stack and then pop it after you're done with it. If you don't, you'll end up with a broken matrix stack, which will cause rendering issues.

Assicurati di spingere lo stack di matrici prima di prendere una matrice di trasformazione!
:::

@[code lang=java transcludeWith=:::2](@/reference/latest/src/client/java/com/example/docs/rendering/RenderingConceptsEntrypoint.java)

![Un video che mostra il diamante ingrandito e rimpicciolito](/assets/develop/rendering/concepts-matrix-stack.webp)

## Quaternioni (Cose che Ruotano) {#quaternions-rotating-things}

I quaternioni sono un modo di rappresentare rotazioni in uno spazio 3D. Vengono usate per ruotare la matrice in cima al `MatrixStack` usando il metodo `multiply(Quaternion, x, y, z)`.

Difficilmente dovrai usare una classe Quaternion direttamente, siccome Minecraft fornisce varie istanze Quaternion pre-costruite nella sua classe di utilità `RotationAxis`.

Immaginiamo di voler ruotare il nostro diamante attorno all'asse z. Possiamo farlo usando il `MatrixStack` e il metodo `multiply(Quaternion, x, y, z)`.

@[code lang=java transcludeWith=:::3](@/reference/latest/src/client/java/com/example/docs/rendering/RenderingConceptsEntrypoint.java)

Il risultato è il seguente:

![Un video che mostra il diamante che ruota attorno all'asse z](/assets/develop/rendering/concepts-quaternions.webp)
