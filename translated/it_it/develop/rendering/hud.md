---
title: Rendering nel HUD
description: Impara a usare l'API Fabric Hud per renderizzare sul HUD.
authors:
  - IMB11
  - kevinthegreat1
---

Abbiamo già parlato brevemente di come renderizzare cose sulla HUD nelle pagine [Concetti di Rendering di Base](./basic-concepts) e [Usare il Drawing Context](./draw-context), per cui in questa pagina ci concentreremo sull'API Hud e sul parametro `HudRenderCallback`.

## `HudRenderCallback` {#hudrendercallback}

:::warning
Nel passato Fabric ha fornito `HudRenderCallback` per renderizzare al HUD. A causa di alcuni cambiamenti al rendering nel HUD, questo evento è diventato estremamente limitato, ed è deprecato dalla versione 0.116 dell'API di Fabric. Il suo uso è fortemente sconsigliato.
:::

## `HudLayerRegistrationCallback` {#hudlayerregistrationcallback}

Fabric fornisce l'API Hud per renderizzare e sovrapporre elementi nella HUD.

Per iniziare, dobbiamo registrare un listener a `HudLayerRegistrationCallback` che registri i tuoi strati. Ogni strato è un `IdentifiedLayer`, ovver un `LayeredDrawer.Layer` vanilla collegato a un `Identifier`. Un'istanza di `LayeredDrawer.Layer` è solitamente una lambda che accetta un `DrawContext` e un'istanza di `RenderTickCounter` come parametro. Leggi `HudLayerRegistrationCallback` e le Javadoc correlate per maggiori dettagli sull'uso dell'API.

Il contesto di disegno può essere usato per accedere a varie utilità di rendering fornite dal gioco, e per accedere allo stack di matrici puro. Dovresti dare un'occhiata alla pagina [Usare il Contesto di Disegno](./draw-context) per saperne di più riguardo al contesto di disegno.

### Contatore di Tick di Render {#render-tick-counter}

La classe `RenderTickCounter` ti permette di ottenere il valore corrente di `tickDelta`. `tickDelta` è l'"avanzamento" tra il tick del gioco precedente e quello successivo.

Per esempio, ipotizzando uno scenario a 200 FPS, il gioco esegue un nuovo tick più o meno ogni 10 frame. A ogni frame, `tickDelta` indica quanto siamo distanti tra un tick e l'altro. Nel corso di 11 frame, potresti ottenere:

| Frame | `tickDelta`                     |
| :---: | ------------------------------- |
|  `1`  | `1`: Nuovo tick |
|  `2`  | `1/10 = 0.1`                    |
|  `3`  | `2/10 = 0.2`                    |
|  `4`  | `3/10 = 0.3`                    |
|  `5`  | `4/10 = 0.4`                    |
|  `6`  | `5/10 = 0.5`                    |
|  `7`  | `6/10 = 0.6`                    |
|  `8`  | `7/10 = 0.7`                    |
|  `9`  | `8/10 = 0.8`                    |
|  `10` | `9/10 = 0.9`                    |
|  `11` | `1`: Nuovo tick |

In pratica, dovresti solo usare `tickDelta` quando le tue animazioni dipendono dai tick di Minecraft. Per animazioni basate sul tempo usa `Util.getMeasuringTimeMs()`, che misura il tempo del mondo reale.

Puoi ottenere `tickDelta` chiamando `renderTickCounter.getTickDelta(false)`, dove il parametro booleano è `ignoreFreeze`, che in sostanza ti permette semplicemente d'ignorare l'utilizzo del giocatore del comando `/tick freeze`.

In questo esempio, useremo `Util.getMeasuringTimeMs()` per interpolare linearmente il colore di un quadrato che viene renderizzato nel HUD.

@[code lang=java transcludeWith=:::1](@/reference/latest/src/client/java/com/example/docs/rendering/HudRenderingEntrypoint.java)

![Interpolare un colore nel tempo](/assets/develop/rendering/hud-rendering-deltatick.webp)

Perché non provi a usare `tickDelta` e a vedere cosa succede all'animazione quando esegui il comando `/tick freeze`? Dovresti vedere che l'animazione si congela appena `tickDelta` diventa una costante (supponendo di aver passato `false` come parametro di `RenderTickCounter#getTickDelta`)
