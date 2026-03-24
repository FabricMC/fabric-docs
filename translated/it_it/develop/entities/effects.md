---
title: Effetti dei mob
description: Impara come aggiungere effetti dei mob personalizzati.
authors:
  - dicedpixels
  - Friendly-Banana
  - Manchick0
  - SattesKrokodil
  - TheFireBlast
  - YanisBft
authors-nogithub:
  - siglong
  - tao0lu
---

Gli effetti dei mob, anche noti come effetti di stato, o semplicemente effetti, sono una condizione che interessa un'entità. Possono essere positivi, negativi o neutrali in natura. Il gioco base applica questi effetti in vari modi, come cibi, pozioni ecc.

Il comando `/effect` può essere usato per applicare effetti su un'entità.

## Effetti dei mob Personalizzati {#custom-mob-effects}

In questo tutorial aggiungeremo un nuovo effetto personalizzato chiamato _Tater_ che ti darà un punto esperienza in ogni tick di gioco.

### Estendere `MobEffect` {#extend-mobeffect}

Creiamo una classe per il nostro effetto personalizzato estendendo `MobEffect`, che è la classe base per tutti gli effetti.

@[code lang=java transcludeWith=:::1](@/reference/latest/src/main/java/com/example/docs/effect/TaterEffect.java)

### Registrare il tuo Effetto Personalizzato {#registering-your-custom-effect}

Come nella registrazione di blocchi e oggetti, usiamo `Registry.register` per registrare i nostri effetti personalizzati nella registry `MOB_EFFECT`. Questo può essere fatto nel nostro initializer.

@[code lang=java transcludeWith=:::1](@/reference/latest/src/main/java/com/example/docs/effect/ExampleModEffects.java)

### Texture {#texture}

L'icona dell'effetto è un PNG 18x18 che apparirà nella schermata dell'inventario del giocatore. Posiziona la tua icona personalizzata in:

```text:no-line-numbers
resources/assets/example-mod/textures/mob_effect/tater.png
```

<DownloadEntry visualURL="/assets/develop/tater-effect.png" downloadURL="/assets/develop/tater-effect-icon.png">Texture di Esempio</DownloadEntry>

### Traduzioni {#translations}

Come per qualsiasi altra traduzione, puoi aggiungere un'entrata con il formato ID `"effect.example-mod.effect-identifier": "Value"` al file di lingua.

```json
{
  "effect.example-mod.tater": "Tater"
}
```

### Applicare l'Effetto {#applying-the-effect}

Vale la pena di dare un'occhiata a come si aggiunge solitamente un effetto ad un'entità.

::: tip

Per un test rapido, potrebbe essere una buona idea usare il comando `/effect` citato prima:

```mcfunction
effect give @p example-mod:tater
```

:::

Per applicare un effetto internamente, vorrai usare il metodo `LivingEntity#addEffect`, che accetta una `MobEffectInstance`, e restituisce un booleano, che indica se l'effetto è stato applicato con successo.

@[code lang=java transcludeWith=:::1](@/reference/latest/src/main/java/com/example/docs/ReferenceMethods.java)

| Argomento   | Tipo                | Descrizione                                                                                                                                                                                                                                                                             |
| ----------- | ------------------- | --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| `effect`    | `Holder<MobEffect>` | Un holder che rappresenta l'effetto.                                                                                                                                                                                                                                    |
| `duration`  | `int`               | La durata dell'effetto **in tick**; **non** in secondi                                                                                                                                                                                                                                  |
| `amplifier` | `int`               | L'amplificatore al livello dell'effetto. Non corrisponde al **livello** dell'effetto, ma è invece aggiunto al di sopra. Per cui un `amplifier` di `4` => livello `5`                                                                                    |
| `ambient`   | `boolean`           | Questo è un po' complesso. In pratica indica che l'effetto è stato aggiunto dall'ambiente (per esempio un **Faro**) e non ha una causa diretta. Se `true`, l'icona dell'effetto nel HUD avrà un overlay color ciano. |
| `particles` | `boolean`           | Se si mostrano le particelle.                                                                                                                                                                                                                                           |
| `icon`      | `boolean`           | Se si mostra un'icona dell'effetto nel HUD. L'effetto sarà mostrato nell'inventario indipendentemente da questo valore.                                                                                                                                 |

::: info

Per creare una pozione che usa questo effetto, per favore vedi la guida [Pozioni](../items/potions).

:::

<!---->
