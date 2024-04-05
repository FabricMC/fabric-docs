---
title: Concetti di Rendering di Base
description: Impara i concetti di base del rendering usando il motore grafico di Minecraft.
authors:
  - IMB11
  - "0x3C50"
---

# Concetti di Rendering di Base

::: warning
Although Minecraft is built using OpenGL, as of version 1.17+ you cannot use legacy OpenGL methods to render your own things. Instead, you must use the new `BufferBuilder` system, which formats rendering data and uploads it to OpenGL to draw.

Per riassumere, devi usare il sistema di rendering di Minecraft, o crearne uno tuo che utilizza `GL.glDrawElements()`.
:::

Questa pagina coprirà le basi del rendering usando il nuovo sistema, andando ad usare terminologia e concetti chiave.

Anche se molto del rendering in Minecraft è astratto attraverso i vari metodi `DrawContext`, e probabilmente non ti servirà nulla di quel che viene menzionato qui, è comunque importante capire le basi di come funziona il rendering.

## Il `Tessellator`

Il `Tessellator` è la principale classe usata per renderizzare le cose in Minecraft. È un singleton, cioè solo un'istanza è presente in gioco. Puoi ottenere l'istanza usando `Tessellator.getInstance()`.

## Il `BufferBuilder`

Il `BufferBuilder` è la classe usata per formattare e caricare i dati di rendering su OpenGL. Viene usata per creare un buffer, che viene caricato su OpenGL per essere disegnato.

Il `Tessellator` viene usato per creare il `BufferBuilder`, che viene usato per formattare e caricare i dati di rendering su OpenGL. Puoi creare un `BufferBuilder` usando `Tessellator.getBuffer()`.

### Inizializzare il `BufferBuilder`

Prima di poter scrivere al `BufferBuilder`, devi inizializzarlo. Questo viene fatto usando `BufferBuilder.begin(...)`, che prende un `VertexFormat` ed una modalità di disegno.

#### Formati Vertex

Il `VertexFormat` definisce gli elementi che includiamo nel nostro buffer di dati e precisa come questi elementi debbano essere trasmessi a OpenGL.

I seguenti elementi `VertexFormat` sono disponibili:

| Elemento                                      | Formato                                                                                   |
| --------------------------------------------- | ----------------------------------------------------------------------------------------- |
| `BLIT_SCREEN`                                 | `{ posizione (3 floats: x, y e z), uv (2 floats), colore (4 ubytes) }`                    |
| `POSITION_COLOR_TEXTURE_LIGHT_NORMAL`         | `{ posizione, color, texture uv, luce texture (2 shorts), texture normale (3 sbytes) }`   |
| `POSITION_COLOR_TEXTURE_OVERLAY_LIGHT_NORMAL` | `{ posizione, colore, texture uv, overlay (2 shorts), luce texture, normale (3 sbytes) }` |
| `POSITION_TEXTURE_COLOR_LIGHT`                | `{ posizione, texture uv, colore, luce texture }`                                         |
| `POSITION`                                    | `{ posizione }`                                                                           |
| `POSITION_COLOR`                              | `{ posizione, colore }`                                                                   |
| `LINES`                                       | `{ posizione, colore, normale }`                                                          |
| `POSITION_COLOR_LIGHT`                        | `{ posizione, coloer, luce }`                                                             |
| `POSITION_TEXTURE`                            | `{ posizione, uv }`                                                                       |
| `POSITION_COLOR_TEXTURE`                      | `{ posizione colore, uv }`                                                                |
| `POSITION_TEXTURE_COLOR`                      | `{ posizione, uv, colore }`                                                               |
| `POSITION_COLOR_TEXTURE_LIGHT`                | `{ posizione, colore, uv, luce }`                                                         |
| `POSITION_TEXTURE_LIGHT_COLOR`                | `{ posizione, uv, luce, colore }`                                                         |
| `POSITION_TEXTURE_COLOR_NORMAL`               | `{ posizione, uv, colore, normale }`                                                      |

#### Modalità di Disegno

La modalità di disegno definisce come sono disegnati i dati. Sono disponibili le seguenti modalità di disegno:

| Draw Mode                   | Descrizione                                                                                                                                                        |
| --------------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------ |
| `DrawMode.LINES`            | Ogni elemento è fatto da 2 vertici ed è rappresentato da una singola linea.                                                                        |
| `DrawMode.LINE_STRIP`       | Il primo elemento richiede 2 vertici. Elementi addizionali vengono disegnati con solo un nuovo vertice creando una linea continua. |
| `DrawMode.DEBUG_LINES`      | Simile a `DrawMode.LINES`, ma la linea è sempre esattamente larga un pixel sullo schermo.                                                          |
| `DrawMode.DEBUG_LINE_STRIP` | Come `DrawMode.LINE_STRIP`, ma le linee sono sempre larghe un pixel.                                                                               |
| `DrawMode.TRIANGLES`        | Ogni elemento è farro da 3 vertici, formando un triangolo.                                                                                         |
| `DrawMode.TRIANGLE_STRIP`   | Inizia con 3 vertici per il primo triangolo. Ogni vertex addizionale forma un nuovo triangolo con gli ultimi due vertici.          |
| `DrawMode.TRIANGLE_FAN`     | Inizia con 3 vertici per il primo triangolo. Ogni vertex addizionale forma un triangolo con il primo e l'ultimo vertice.           |
| `DrawMode.QUADS`            | Ogni elemento è fatto da 4 vertice, formando un quadrilatero.                                                                                      |

### Scrivere al `BufferBuilder`

Una volta che il `BufferBuilder` è inizializzato, puoi scriverci dei dati.

Il `BufferBuilder` permette di costruire il nostro buffer, vertex per vertex. Per aggiungere un vertex, usiamo il metodo `buffer.vertex(matrix, float, float, float)`. Il parametro `matrix` è la matrice di trasformazione, della quale discuteremo più dettagliatamente in seguito. I tre parametri float rappresentano le coordinate (x, y, z) della poszione del vertex.

Questo metodo restituisce un vertex builder, che possiamo usare per specificare informazioni addizionali per il vertex. È cruciale seguire l'ordine del nostro `VertexFormat` definito quando aggiungiamo questa informazione. Se non lo facciamo, OpenGL potrebbe non interpretare i nostri dati correttamente. Dopo aver fintio la costruzione del vertex, chiamiamo il metodo `.next()`. Questo finalizza il vertex corrente e prepare il builder per il prossimo.

Importante è anche capire il concetto di culling. Il culling è il processo con cui si rimuovono facce di una forma 3D che non sono visibili dalla prospettiva dell'osservatore. Se i vertici per una facca sono specificate nell'ordine sbagliato, la faccia potrebbe non essere renderizzata correttamente a causa del culling.

#### Cosìè una Matrice di Trasformazione?

Una matrice di trasformazione è una matrice 4x4 che viene usata per trasformare un vettore. In Minecraft, la matrice di trasformazione sta solo trasformando le coordinate che diamo nella chiamata del vertex. Le trasformazione possono scalare il nostro modello, muoverlo in giro e ruotarlo.

A volte viene chiamata anche matrici di posizione (position matrix), o matrice modelle (model matrix).

Soltiamente è ottenuta dalla classe `MatrixStack`, che può essere ottenuto attraverso l'oggetto `DrawContext`:

```java
drawContext.getMatrices().peek().getPositionMatrix();
```

#### Un Esempio Pratico: Renderizzare una striscia di Triangoli

Spiegare come scrivere al `BufferBuilder` è più semplice con un esempio pratico. Diciamo che vogliamo renderizzare qualcosa usando la modalità di disegno `DrawMode.TRIANGLE_STRIP` e il formato vertex \\`POSITION_COLOR.

Disegneremo vertici ai seguenti punti nella HUD (in ordine):

```txt
(20, 20)
(5, 40)
(35, 40)
(20, 60)
```

Questo dovrebbe darci un diamante carino - siccome staimo usando la modalità di disegno `TRIANGLE_STRIP`, il renderizzatore farà i seguenti step:

![Quattro step che mostrano il piazzamento dei vertici sullo schermo per formare due triangoli.](/assets/develop/rendering/concepts-practical-example-draw-process.png)

Siccome stiamo disegnando sulla HUD in questo esempio, useremo l'evento `HudRenderCallback`:

@[code lang=java transcludeWith=:::1](@/reference/latest/src/client/java/com/example/docs/rendering/RenderingConceptsEntrypoint.java)

Questo risulta nel disegno della cosa seguente nella HUD:

![Risultato Finale](/assets/develop/rendering/concepts-practical-example-final-result.png)

:::tip
Prova a giocare coi colori e le posizione dei vertici per vedere che succede! Puoi anche provare ad usare modalità di disegno e formati vertex differenti.
:::

## La `MatrixStack`

Dopo aver imparato come scrivere al `BufferBuilder`, ti potresti chiedere come trasformare il tuo modello - anche animarlo magari. Qui è dove entra in gioco la classe `MatrixStack`.

La classe `MatrixStack` ha i seguenti metodi:

- `push()` - Spinge una nuova matrice sullo stack.
- `pop()` - Elimina la matrice in cima allo stack.
- `peek()` - Restutuisce la matrice in cima allo stack.
- `translate(x, y, z)` - Trasla la matrice in cima allo stack.
- `scale(x, y, z)` - Scala la matrice in cima allo stack.

Puoi anche moltiplicare la matrice in cima allo stack usando i quaternioni, che copriremo nella prossima sezione.

Usando l'esempio di prima, possiamo scalare il nostro diamante su e giù usando la `MatrixStack` e il `tickDelta` - che è il tempo passato dall'ultimo frame.

::: warning
You must push and pop the matrix stack when you're done with it. If you don't, you'll end up with a broken matrix stack, which will cause rendering issues.

Assicurati di pushare la stack di matrci prima di prendere una matrice di trasformazione!
:::

@[code lang=java transcludeWith=:::2](@/reference/latest/src/client/java/com/example/docs/rendering/RenderingConceptsEntrypoint.java)

![Un video che mostra il diamante scalato su e giù.](/assets/develop/rendering/concepts-matrix-stack.webp)

## Quaternioni (Cose che ruotano)

I quaternioni sono un modo di rappresentare rotazioni in uno spazio 3D. Vengono usato per ruotare la matrice in cima al `MatrixStack` usando il metodo `multiply(Quaternion, x, y, z)`.

Difficilmente dovrai usare una classe Quaternion direttamente, siccome Minecraft fornisce vaire istanze Quaternion pre-compilate nella sua classe di utility `RotationAxis`.

Diciamo che vogliamo ruotare il nostro diamante attorno all'asse z. Possiamo farlo usando il `MatrixStack` ed il metodo `multiply(Quaternion, x, y, z)`.

@[code lang=java transcludeWith=:::3](@/reference/latest/src/client/java/com/example/docs/rendering/RenderingConceptsEntrypoint.java)

Il risultato è il seguente:

![Un video che mostra il diamante ruotare attorno all'asse z.](/assets/develop/rendering/concepts-quaternions.webp)
