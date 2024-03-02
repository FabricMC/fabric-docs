---
title: Rendering nella Hud
description: Impara ad usare l'evento HudRenderCallback per renderizzare sulla hud.
authors:
  - IMB11
---

# Rendering nella Hud

Abbiamo già parlato brevemente sul renderizzare cose sulla Hud nelle pagine [Concetti di Rendering di base](./basic-concepts.md) e [Usare il Drawing Context](./draw-context.md),.

## HudRenderCallback

L'evento `HudRenderCallback` - fornito da Fabric API - viene chiamato ogni frame, e viene usato per renderizzare cose sulla HUD.

Per registrare questi evento, puoi semplicemente chiamare `HudRenderCallback.EVENT.register` e passare una funzione lambda che prende come parametri `DrawContext` e `float` (deltaTick).

Il contesto di disegno (draw context) può essere usato per accedere a varie utilities per il rendering fornite dal gioco, ed accedere allo stack di matrici crude (raw matrix stack).

Dovresti dare un'occhiata alla pagina [Draw Context](./draw-context.md) per saperne di più riguardo al contesto di disegno.

### DeltaTick

Il parametro `deltaTick` è il tempo trascorso dall'ultimo frame, in secondi. Questo può essere usato per fare animazioni e altri effetti basati sul tempo.

#### Esempio: Interpolare un Colore nel tempo

Diciamo che vuoi interpolare linearmente un colore nel tempo. Puoi usare il parametro `deltaTick` per farlo.

@[code lang=java transcludeWith=:::1](@/reference/latest/src/client/java/com/example/docs/rendering/HudRenderingEntrypoint.java)

![](/assets/develop/rendering/hud-rendering-deltatick.webp)
