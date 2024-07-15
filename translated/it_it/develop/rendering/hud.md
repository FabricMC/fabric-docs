---
title: Rendering nel Hud
description: Impara a usare l'evento HudRenderCallback per renderizzare sul hud.
authors:
  - IMB11
---

# Rendering nel Hud

Abbiamo già parlato brevemente di come renderizzare cose sulla Hud nelle pagine [Concetti di Rendering di Base](./basic-concepts) e [Usare il Drawing Context](./draw-context), per cui in questa pagina ci concentreremo sull'evento `HudRenderCallback` e sul parametro `deltaTick`.

## HudRenderCallback

L'evento `HudRenderCallback` - fornito dall'API di Fabric - viene chiamato ogni frame, e viene usato per renderizzare cose sul HUD.

Per registrarsi a questo evento, puoi semplicemente chiamare `HudRenderCallback.EVENT.register` e passare una lambda che prende come parametri un `DrawContext` e un `float` (deltaTick).

Il contesto di disegno può essere usato per accedere a varie utilità di rendering fornite dal gioco, e per accedere allo stack di matrici puro.

Dovresti dare un'occhiata alla pagina [Usare il Contesto di Disegno](./draw-context) per saperne di più riguardo al contesto di disegno.

### DeltaTick

Il `deltaTick` è il tempo trascorso dall'ultimo frame, in secondi. Questo può essere usato per fare animazioni e altri effetti basati sul tempo.

Per esempio, immagina di voler interpolare linearmente un colore nel tempo. Puoi usare il `deltaTickManager` per ottenere il deltaTick, e memorizzarlo nel tempo per interpolare il colore:

@[code lang=java transcludeWith=:::1](@/reference/latest/src/client/java/com/example/docs/rendering/HudRenderingEntrypoint.java)

![Interpolare un colore nel tempo](/assets/develop/rendering/hud-rendering-deltatick.webp)
