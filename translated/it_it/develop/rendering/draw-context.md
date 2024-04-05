---
title: Usare il Contesto di Disegno (Draw Context)
description: Impara ad usare la classe DrawContext per renderizzere varie forme, testi e texture.
authors:
  - IMB11
---

# Usare il Contesto di Disegno (Draw Context)

Questa pagina assume che tu abbia guardato la pagina [Concetti di Rendering di Base](./basic-concepts.md).

La classe `DrawContext` è la principale classe utilizzata per il rendering nel gioco. Viene usata per renderizzare forme, testi e texture, e come visto in precedente, usata per manipolare le`MatrixStack` ed i `BufferBuilder`.

## Disegnare Forme

La classe `DrawContext` può essere usata per disegnare facilmente **forme rettangolari**. Se vuoi disegnare triangoli, o altre forme non rettangolari, dovrai usare un `BufferBuilder`.

### Disegnare Rettangoli

Puoi usare il metodo DrawContext.fill(...)\\` per disegnare un rettango pieno.

@[code lang=java transcludeWith=:::1](@/reference/latest/src/client/java/com/example/docs/rendering/DrawContextExampleScreen.java)

![Un rettangolo.](/assets/develop/rendering/draw-context-rectangle.png)

### Disegnare Contorni/Bordi

Diciamo che vogliamo fare un contorno al rettangolo che abbiamo disegnato. Possimao usare il metodo `DrawContext.drawBorder(...)` per disegnare un contorno.

@[code lang=java transcludeWith=:::2](@/reference/latest/src/client/java/com/example/docs/rendering/DrawContextExampleScreen.java)

![Rettangolo con bordo.](/assets/develop/rendering/draw-context-rectangle-border.png)

### Disegnare Singole Linee

Possiamo usare i metodi `DrawContext.drawHorizontalLine(...)` e `DrawContext.drawVerticalLine(...)` per disegnare linee.

@[code lang=java transcludeWith=:::3](@/reference/latest/src/client/java/com/example/docs/rendering/DrawContextExampleScreen.java)

![Linee](/assets/develop/rendering/draw-context-lines.png)

## Il Gestore di Forbici (Scrissor Manager)

La classe `DrawContext` ha uno scrissor manager incluso. Questo ti permette di ritagliare il rendering e confinarlo ad un area specifica. Questo è utile per renderizzare cose come consigli, i altri elementi che non dovrebbero essere renderizzati al di fuori di un'area specifica.

### Usare lo Scrissor Manager

:::tip
Le regioni Scrissor posso assets annotate l'una dentro l'altra! Ma assicurati di disabilitare lo scrissor manager lo stesso numero di volte che lo abiliti.
:::

Per abilitare lo scrissor manager, semplicemente usa il metodo `DrawContext.enableScissor(...)`. Similarmente per disabilitarlo usa il metodo `DrawContext.disableScissor()`.

@[code lang=java transcludeWith=:::4](@/reference/latest/src/client/java/com/example/docs/rendering/DrawContextExampleScreen.java)

![Regione Scissor in azione.](/assets/develop/rendering/draw-context-scissor.png)

Come puoi vedere, anche se diciamo al gioco di renderizzare il gradiente attraverso tutto lo schermo, lo renderizza solo nella regione scrissor.

## Disegnare Texture

Non c'è un modo "corretto" per disegnare texture su uno schermo, siccome il metodo `drawTexture(...)` ha tanti diversi overload. Questa sezione coprirà gli usi più comuni.

### Disegnare una Texture Intera

Generalmente, è raccomandato usare l'overload che specifica i parametri `textureWidth` e `textureHeight`. Questo perché la classe `DrawContext` assumerà questi valori se non li specifici, che può essere sbagliato a volte.

@[code lang=java transcludeWith=:::5](@/reference/latest/src/client/java/com/example/docs/rendering/DrawContextExampleScreen.java)

![Esempio di tutta la texture disegnata.](/assets/develop/rendering/draw-context-whole-texture.png)

### Disegnare una Porzione di una Texture

Qui è dove `u` e `v` entrano in gico. Questi parametri specificano l'angolo in alto a sinistra della texture da diseganre, ed i parametri `regionWidth` e `regionHeight` specificano la dimensione della porzione della texture da disegnare.

Prendiamo questa texture come esempio.

![Texture del Libro di Ricette](/assets/develop/rendering/draw-context-recipe-book-background.png)

Se vogliamo solo diseganre una regione che contiene la lante, possiamo usare i seguenti valori per `u`, `v`, `regionWidth` e `regionHeight`:

@[code lang=java transcludeWith=:::6](@/reference/latest/src/client/java/com/example/docs/rendering/DrawContextExampleScreen.java)

![Texture Regione](/assets/develop/rendering/draw-context-region-texture.png)

## Disegnare Testo

La classe `DrawContext` ha vari metodoi auto-esplicatori per renderizzare del testo - per brevità, non verrano coperti qui.

Diciamoc he vogliamo disegnare "Hello World" sullo schermo. Possiamo usare il metodo `DrawContext.drawText(...)` per farlo.

@[code lang=java transcludeWith=:::7](@/reference/latest/src/client/java/com/example/docs/rendering/DrawContextExampleScreen.java)

![Disegnare testo](/assets/develop/rendering/draw-context-text.png)
