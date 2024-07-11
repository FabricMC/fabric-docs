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
---

# Effetti di Stato

Gli effetti di stato, anche noti come effetti, sono una condizione che interessa un'entità. Possono essere positivi, negativi o neutrali in natura. Il gioco base applica questi effetti in vari modi, come cibi, pozioni ecc.

Il comando `/effect` può essere usato per applicare effetti su un'entità.

## Effetti di Stato Personalizzati

In questo tutorial aggiungeremo un nuovo effetto personalizzato chiamato _Tater_ che ti darà un punto esperienza in ogni tick di gioco.

### Estendere `StatusEffect`

Creiamo una classe per il nostro effetto personalizzato estendendo `StatusEffect`, che è la classe base per tutti gli effetti.

@[code lang=java transcludeWith=:::1](@/reference/latest/src/main/java/com/example/docs/effect/TaterEffect.java)

### Registrare il tuo Effetto Personalizzato

Come nella registrazione di blocchi e oggetti, usiamo `Registry.register` per registrare i nostri effetti personalizzati nel registro `STATUS_EFFECT`. Questo può essere fatto nel nostro initializer.

@[code lang=java transcludeWith=:::1](@/reference/latest/src/main/java/com/example/docs/effect/FabricDocsReferenceEffects.java)

### **Texture**

L'icona dell'effetto è un PNG 18x18. Posiziona la tua icona personalizzata in:

```:no-line-numbers
resources/assets/fabric-docs-reference/textures/mob_effect/tater.png
```

<DownloadEntry type="Example Texture" visualURL="/assets/develop/tater-effect.png" downloadURL="/assets/develop/tater-effect-icon.png" />

### **Traduzioni**

Come ogni altra traduzione, puoi aggiungere una voce con formato ID `"effect.<mod-id>.<effect-identifier>": "Valore"` al file di lingua.

```json
{
  "effect.fabric-docs-reference.tater": "Tater"
}
```

### Fase di Test {#testing}

Usa il comando `/effect give @p fabric-docs-reference:tater` per dare al giocatore il nostro effetto Tater.
Usa `/effect clear @p fabric-docs-reference:tater` per rimuovere l'effetto.

:::info
::: info
Per creare una pozione che usa questo effetto, per favore vedi la guida [Pozioni](../items/potions).
:::
