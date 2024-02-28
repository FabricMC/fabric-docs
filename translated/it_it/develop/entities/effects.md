---
title: Effetti di Stato
description: Impara come aggiungere effetti di status custom.
authors:
  - dicedpixels
  - YanisBft
  - FireBlast
  - Friendly-Banana
  - SattesKrokodil
---

<!-- Couldn't find GitHub usernames for: siglong, tao0lu  -->

# Effetti di Stato

Effetti di stato, conosciuti anche anche come effetti, sone una condizione comune che interessa un'entità. Possono essere positivi, negativi o neutrali in natura. Il gioco base applica questi effetti in vari modi, come cibi, pozioni ecc.

Il comando `/effect` può essere usato per applicare effetti su un'entità.

## Effetti di stato Custom

In questo tutorial aggiungeremo un nuovo effetto custom chiamto _Tater_ che darà un punto esperienza ogni game tick.

### Estendere `StatusEffect`

Creiamo una classe per il nostro effetto custom estendedo `StatusEffect`, che è la classe base per tutt gli effetti.

@[code lang=java transcludeWith=:::1](@/reference/latest/src/main/java/com/example/docs/effect/TaterEffect.java)

### Registrare il tuo Effetto Custom

Similarmente a registrazioni di blocchi e oggetti, usiamo `Registry.register` per registrare i nostri effetti custom nel registro `STATUS_EFFECT`. Ciò può essere fatto nel nostro initializer.

@[code lang=java transcludeWith=:::1](@/reference/latest/src/main/java/com/example/docs/effect/FabricDocsReferenceEffects.java)

### Traduzione e Texture

Puoi assegnare un nome al tuo effetto di stato e fornire una texture per un icona che apparirà nello schermo dell'inventario del giocatore.

**Texture**

L'icona dell'effetto è un PNG 18x18. Posiziona la tua icona custom in:

```:no-line-numbers
resources/assets/fabric-docs-reference/textures/mob_effect/tater.png
```

![Effetto nell'inventario del giocatore](/assets/develop/tater-effect.png)

**Traduzioni**

Come ogni altra tarduzione, puoi aggiungere una voce con formato ID `"effect.<mod-id>.<effect-identifier>": "Valore"` ai file di lingua.

::: code-group

```json[assets/fabric-docs-reference/lang/en_us.json]
{
  "effect.fabric-docs-reference.tater": "Tater"
}
```

### Testing

Usa il comando `/effect give @p fabric-docs-reference:tater` per dare al giocatore il nostro effetto Tater. Usa `/effect clear` per rimuovere l'effetto.

::: info
Per creare una pozione che usa questo effetto, per favore vedi la guida [Pozioni](../items/potions.md).
:::
