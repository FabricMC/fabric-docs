---
title: Rendering nel Hud
description: Impara a usare l'evento HudRenderCallback per renderizzare sul hud.
authors:
  - IMB11
---

Abbiamo già parlato brevemente di come renderizzare cose sulla Hud nelle pagine [Concetti di Rendering di Base](./basic-concepts) e [Usare il Drawing Context](./draw-context), per cui in questa pagina ci concentreremo sull'evento `HudRenderCallback` e sul parametro `tickDelta`.

## HudRenderCallback {#hudrendercallback}

L'evento `HudRenderCallback` - fornito dall'API di Fabric - viene chiamato ogni frame, e viene usato per renderizzare cose sul HUD.

Per registrarsi a questo evento, puoi semplicemente chiamare `HudRenderCallback.EVENT.register` e passare una lambda che prende come parametri un `DrawContext` e un'istanza di `RenderTickCounter`.

Il contesto di disegno può essere usato per accedere a varie utilità di rendering fornite dal gioco, e per accedere allo stack di matrici puro.

Dovresti dare un'occhiata alla pagina [Usare il Contesto di Disegno](./draw-context) per saperne di più riguardo al contesto di disegno.

### Contatore di Tick di Render {#render-tick-counter}

La classe `RenderTickCounter` ti permette di ottenere il valore corrente di `tickDelta`.

`tickDelta` è l'"avanzamento" tra il tick del gioco precedente e quello successivo.

Per esempio, ipotizzando uno scenario a 200 FPS, il gioco esegue un nuovo tick più o meno ogni 10 frame. A ogni frame, `tickDelta` indica quanto siamo distanti tra un tick e l'altro. Nel corso di 10 frame, potresti ottenere:

| Frame | tickDelta                                                         |
| :---: | ----------------------------------------------------------------- |
|   1   | `1.0` (nuovo tick)                             |
|   2   | `0.11 (1÷9)` - Il prossimo tick sarà tra 9 frame. |
|   3   | `0.22 (2÷9)`                                                      |
|   4   | `0.33 (3÷9)`                                                      |
|   5   | `0.44 (4÷9)`                                                      |
|   6   | `0.55 (5÷9)`                                                      |
|   7   | `0.66 (6÷9)`                                                      |
|   8   | `0.77 (7÷9)`                                                      |
|   9   | `0.88 (8÷9)`                                                      |
|   10  | `1.0` (nuovo tick)                             |

In pratica, dovresti solo usare `tickDelta` quando le tue animazioni dipendono dai tick di Minecraft. Per animazioni basate sul tempo usa `Util.getMeasuringTimeMs()`, che misura il tempo del mondo reale.

Puoi ottenere `tickDelta` usando la funzione `renderTickCounter.getTickDelta(false);`, dove il parametro booleano è `ignoreFreeze`, che in sostanza ti permette semplicemente d'ignorare l'utilizzo del giocatore del comando `/tick freeze`.

In questo esempio, useremo `Util.getMeasuringTimeMs()` per interpolare linearmente il colore di un quadrato che viene renderizzato nel HUD.

@[code lang=java transcludeWith=:::1](@/reference/latest/src/client/java/com/example/docs/rendering/HudRenderingEntrypoint.java)

![Interpolare un colore nel tempo](/assets/develop/rendering/hud-rendering-deltatick.webp)

Perché non provi a usare `tickDelta` e a vedere cosa succede all'animazione quando esegui il comando `/tick freeze`? Dovresti vedere che l'animazione si congela appena `tickDelta` diventa una costante (supponendo di aver passato `false` come parametro di `RenderTickCounter#getTickDelta`)
