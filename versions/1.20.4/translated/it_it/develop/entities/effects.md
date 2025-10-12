---
title: Effetti di Stato
description: Impara come aggiungere effetti di stato personalizzati.
authors:
  - dicedpixels
  - YanisBft
  - FireBlast
  - Friendly-Banana
  - SattesKrokodil
authors-nogithub:
  - siglong
  - tao0lu

search: false
---

Gli effetti di stato, anche noti come effetti, sono una condizione che interessa un'entità. Possono essere positivi, negativi o neutrali in natura. Il gioco base applica questi effetti in vari modi, come cibi, pozioni ecc.

Il comando `/effect` può essere usato per applicare effetti su un'entità.

## Effetti di Stato Personalizzati

In questo tutorial aggiungeremo un nuovo effetto personalizzato chiamato _Tater_ che ti darà un punto esperienza in ogni tick di gioco.

### Estendere `StatusEffect`

Creiamo una classe per il nostro effetto personalizzato estendendo `StatusEffect`, che è la classe base per tutti gli effetti.

@[code lang=java transcludeWith=:::1](@/reference/latest/src/main/java/com/example/docs/effect/TaterEffect.java)

### Registrare il tuo Effetto Personalizzato

Come nella registrazione di blocchi e oggetti, usiamo `Registry.register` per registrare i nostri effetti personalizzati nel registro `STATUS_EFFECT`. Questo può essere fatto nel nostro initializer.

@[code lang=java transcludeWith=:::1](@/reference/latest/src/main/java/com/example/docs/effect/ExampleModEffects.java)

### Traduzioni e Texture

Puoi assegnare un nome al tuo effetto di stato e fornire un'icona che apparirà nello schermo dell'inventario del giocatore.

#### **Texture**

L'icona dell'effetto è un PNG 18x18. Posiziona la tua icona personalizzata in:

```:no-line-numbers
resources/assets/example-mod/textures/mob_effect/tater.png
```

![Effetto nell'inventario del giocatore](/assets/develop/tater-effect.png)

#### **Traduzioni**

Come ogni altra traduzione, puoi aggiungere una voce con formato ID `"effect.example-mod.<effect-identifier>": "Valore"` al file di lingua.

::: code-group

```json[assets/example-mod/lang/en_us.json]
{
  "effect.example-mod.tater": "Tater"
}
```

### Fase di Test

Usa il comando `/effect give @p example-mod:tater` per dare al giocatore il nostro effetto Tater.
Usa `/effect clear @p example-mod:tater` per rimuovere l'effetto.

::: info
Per creare una pozione che usa questo effetto, per favore vedi la guida [Pozioni](../items/potions).
:::
