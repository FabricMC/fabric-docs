---
title: Effetti d'Incantesimi Personalizzati
description: Impara come creare i tuoi effetti d'incantesimi.
authors:
  - krizh-p
---

A partire dalla versione 1.21, gli incantesimi personalizzati in Minecraft usano un approccio basato sui dati. Questo rende l'aggiunta d'incantesimi semplici, come aumentare il danno da attacco, più semplice, ma complica la creazione d'incantesimi più complessi. Il processo prevede la suddivisione degli incantesimi in _componenti degli effetti_.

Una componente di un effetto contiene il codice che definisce gli effetti speciali di un incantesimo. Minecraft supporta vari effetti predefiniti, come danno degli oggetti, contraccolpo, ed esperienza.

:::tip
Assicurati di controllare se gli effetti predefiniti di Minecraft soddisfano le tue necessità visitando [la pagina sulle Componenti degli Effetti d'Incantesimi della Wiki di Minecraft](https://minecraft.wiki/w/Enchantment_definition#Effect_components). Questa guida suppone che tu comprenda come si configurino incantesimi "semplici" basati su dati, e si focalizza sulla creazione di effetti d'incantesimi personalizzati che non sono supportati in maniera predefinita.
:::

## Effetti d'Incantesimi Personalizzati {#custom-enchantment-effects}

Inizia con la creazione di una cartella `enchantment`, e in essa crea una cartella `effect`. In questa creeremo il record `LightningEnchantmentEffect`.

Poi possiamo creare un costruttore e fare override dei metodi dell'interfaccia `EnchantmentEntityEffect`. Creeremo anche una variabile `CODEC` per codificare e decodificare il nostro effetto; puoi leggere di più [riguardo ai Codec qui](../codecs).

La maggior parte del nostro codice andrà nell'evento `apply()`, che viene chiamato quando i criteri perché il tuo incantesimo funzioni sono soddisfatti. Dopo di che configureremo questo `Effect` in modo che sia chiamato quando un'entità è colpita, ma per ora scriviamo codice semplice per colpire l'obiettivo con un fulmine.

@[code transcludeWith=#entrypoint](@/reference/latest/src/main/java/com/example/docs/enchantment/effect/LightningEnchantmentEffect.java)

Qui, la variabile `amount` indica un valore ridimensionato in base al livello dell'incantesimo. Possiamo usare questa per modificare l'efficacia dell'incantesimo in base al livello. Nel codice sopra, stiamo usando il livello dell'incantesimo per determinare quanti fulmini vengono generati.

## Registrare l'Effetto dell'Incantesimo {#registering-the-enchantment-effect}

Come ogni altra componente della tua mod, dovremo aggiungere questo `EnchantmentEffect` alla registry di Minecraft. Per fare ciò, aggiungi una classe `ModEnchantmentEffects` (o un qualsiasi nome che tu voglia darle) e un metodo ausiliare per registrare l'incantesimo. Assicurati di chiamare il `registerModEnchantmentEffects()` nella tua classe principale, che contiene il metodo `onInitialize()`.

@[code transcludeWith=#entrypoint](@/reference/latest/src/main/java/com/example/docs/enchantment/ModEnchantmentEffects.java)

## Creare l'Incantesimo {#creating-the-enchantment}

Ora abbiamo un effetto d'incantesimo! Il passaggio finale è creare un incantesimo che applica il nostro effetto personalizzato. Anche se questo si potrebbe fare creando un file JSON in maniera simile a quella dei datapack, questa guida ti mostrerà come generare il JSON dinamicamente usando gli strumenti di generazione di dati di Fabric. Per cominciare, crea una classe `EnchantmentGenerator`.

All'interno di questa classe registreremo anzitutto un nuovo incantesimo, e poi useremo il metodo `configure()` per creare il nostro JSON programmaticamente.

@[code transcludeWith=#entrypoint](@/reference/latest/src/client/java/com/example/docs/datagen/EnchantmentGenerator.java)

Prima di procedere dovresti assicurarti che il tuo progetto sia configurato per la generazione di dati; se non sei sicuro, [controlla la pagina corrispondente della documentazione](../data-generation/setup).

Infine, dobbiamo dire alla nostra mod di aggiungere il nostro `EnchantmentGenerator` alla lista di operazioni di generazione dati. Per fare questo, basta aggiungere il `EnchantmentGenerator` a questo all'interno del metodo `onInitializeDataGenerator`.

@[code transclude={24-24}](@/reference/latest/src/client/java/com/example/docs/datagen/FabricDocsReferenceDataGenerator.java)

Ora, eseguendo l'operazione di generazione dati della tua mod, i file JSON degli incantesimi verranno generati nella cartella `generated`. Ecco un esempio qua sotto:

@[code](@/reference/latest/src/main/generated/data/fabric-docs-reference/enchantment/thundering.json)

Dovresti anche aggiungere le traduzioni al tuo file `en_us.json` per dare al tuo incantesimo un nome leggibile:

```json
"enchantment.FabricDocsReference.thundering": "Thundering",
```

Dovresti ora avere un effetto d'incantesimo personalizzato funzionante! Testalo incantando un'arma con l'incantesimo e colpendo un mob. Ecco un esempio nel video seguente:

<VideoPlayer src="/assets/develop/enchantment-effects/thunder.webm">Usare l'Incantesimo Fulminazione</VideoPlayer>
