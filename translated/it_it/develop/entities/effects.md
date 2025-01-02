---
title: Effetti di Stato
description: Impara come aggiungere effetti di stato personalizzati.
authors:
  - dicedpixels
  - YanisBft
  - FireBlast
  - Friendly-Banana
  - SattesKrokodil
  - Manchick0
authors-nogithub:
  - siglong
  - tao0lu
---

# Effetti di Stato {#status-effects}

Gli effetti di stato, anche noti come effetti, sono una condizione che interessa un'entità. Possono essere positivi, negativi o neutrali in natura. Il gioco base applica questi effetti in vari modi, come cibi, pozioni ecc.

Il comando `/effect` può essere usato per applicare effetti su un'entità.

## Effetti di Stato Personalizzati {#custom-status-effects}

In questo tutorial aggiungeremo un nuovo effetto personalizzato chiamato _Tater_ che ti darà un punto esperienza in ogni tick di gioco.

### Estendere `StatusEffect` {#extend-statuseffect}

Creiamo una classe per il nostro effetto personalizzato estendendo `StatusEffect`, che è la classe base per tutti gli effetti.

@[code lang=java transcludeWith=:::1](@/reference/latest/src/main/java/com/example/docs/effect/TaterEffect.java)

### Registrare il tuo Effetto Personalizzato {#registering-your-custom-effect}

Come nella registrazione di blocchi e oggetti, usiamo `Registry.register` per registrare i nostri effetti personalizzati nella registry `STATUS_EFFECT`. Questo può essere fatto nel nostro initializer.

@[code lang=java transcludeWith=:::1](@/reference/latest/src/main/java/com/example/docs/effect/FabricDocsReferenceEffects.java)

### Texture {#texture}

L'icona dell'effetto è un PNG 18x18. Posiziona la tua icona personalizzata in:

```:no-line-numbers
resources/assets/fabric-docs-reference/textures/mob_effect/tater.png
```

<DownloadEntry visualURL="/assets/develop/tater-effect.png" downloadURL="/assets/develop/tater-effect-icon.png">Texture di Esempio</DownloadEntry>

### Traduzioni {#translations}

Come ogni altra traduzione, puoi aggiungere una voce con formato ID `"effect.<mod-id>.<effect-identifier>": "Valore"` al file di lingua.

```json
{
  "effect.fabric-docs-reference.tater": "Tater"
}
```

### Applicare l'Effetto {#applying-the-effect}

Vale la pena di dare un'occhiata a come si aggiunge solitamente un effetto ad un'entità.

::: tip
For a quick test, it might be a better idea to use the previously mentioned `/effect` command:

```mcfunction
effect give @p fabric-docs-reference:tater
```

:::

Per applicare un effetto internamente, vorrai usare il metodo `LivingEntity#addStatusEffect`, che prende una `StatusEffectInstance`, e restituisce un booleano, che indica se l'effetto è stato applicato con successo.

@[code lang=java transcludeWith=:::1](@/reference/latest/src/main/java/com/example/docs/ReferenceMethods.java)

| Argomento   | Tipo                          | Descrizione                                                                                                                                                                                                                                                                             |
| ----------- | ----------------------------- | --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| `effect`    | `RegistryEntry<StatusEffect>` | Una voce di registry che rappresenta l'effetto.                                                                                                                                                                                                                         |
| `duration`  | `int`                         | La durata dell'effetto **in tick**; **non** in secondi                                                                                                                                                                                                                                  |
| `amplifier` | `int`                         | L'amplificatore al livello dell'effetto. Non corrisponde al **livello** dell'effetto, ma è invece aggiunto al di sopra. Per cui un `amplifier` di `4` => livello `5`                                                                                    |
| `ambient`   | `boolean`                     | Questo è un po' complesso. In pratica indica che l'effetto è stato aggiunto dall'ambiente (per esempio un **Faro**) e non ha una causa diretta. Se `true`, l'icona dell'effetto nel HUD avrà un overlay color ciano. |
| `particles` | `boolean`                     | Se si mostrano le particelle.                                                                                                                                                                                                                                           |
| `icon`      | `boolean`                     | Se si mostra un'icona dell'effetto nel HUD. L'effetto sarà mostrato nell'inventario indipendentemente da questo valore.                                                                                                                                 |

:::info
::: info
Per creare una pozione che usa questo effetto, per favore vedi la guida [Pozioni](../items/potions).
:::
