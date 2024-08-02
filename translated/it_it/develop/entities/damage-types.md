---
title: Tipi di Danno
description: Impara come aggiungere tipi di danno personalizzati.
authors:
  - dicedpixels
  - hiisuuii
  - mattidragon
---

# Tipi di Danno {#damage-types}

I tipi di danno definiscono tipi di danno che le entità possono subire. A partire da Minecraft 1.19.4, la creazione di nuovi tipi di danno è basata sui dati, per cui essi sono creati tramite file JSON.

## Creare un Tipo di Danno {#creating-a-damage-type}

Creiamo un tipo di danno personalizzato chiamato _Tater_. Inizieremo creando un file JSON per il tuo danno personalizzato. Il file sarà posizionato nella cartella `data` della tua mod, in una sottocartella chiamata `damage_type`.

```:no-line-numbers
resources/data/fabric-docs-reference/damage_type/tater.json
```

Ha la struttura seguente:

@[code lang=json](@/reference/latest/src/main/generated/data/fabric-docs-reference/damage_type/tater.json)

Questo tipo di danno personalizzato causa un aumento di 0.1 nel livello di esaurimento ([exhaustion level](https://minecraft.wiki/w/Hunger#Exhaustion_level_increase)) ogni volta che il giocatore prende danno, quando il danno è causato da una fonte vivente che non sia un giocatore (per esempio un blocco). Inoltre, la quantità di danno subita cambierà a seconda della difficoltà del mondo

::: info

Affidati alla [Minecraft Wiki](https://minecraft.wiki/w/Damage_type#JSON_format) per tutte le possibili chiavi e valori.

:::

### Accedere ai Tipi di Danno Tramite Codice {#accessing-damage-types-through-code}

Quando abbiamo bisogno di accedere al nostro tipo di danno personalizzato tramite codice, useremo la sua `RegistryKey` per costruire un'istanza di `DamageSource`.

La `RegistryKey` può essere ottenuta nel modo seguente:

@[code lang=java transcludeWith=:::1](@/reference/latest/src/main/java/com/example/docs/damage/FabricDocsReferenceDamageTypes.java)

### Usare i Tipi di Danno {#using-damage-types}

Per mostrare l'utilizzo dei tipi di danno personalizzati, useremo un blocco personalizzato chiamato _Blocco di Tater_. Facciamo in modo che quando un'entità calpesta un _Blocco di Tater_, esso causa danno _Tater_.

Puoi fare override di `onSteppedOn` per infliggere questo danno.

Cominciamo creando una `DamageSource` del nostro tipo di danno personalizzato.

@[code lang=java transclude={21-24}](@/reference/latest/src/main/java/com/example/docs/damage/TaterBlock.java)

Poi, chiamiamo `entity.damage()` con la nostra `DamageSource` e con una quantità.

@[code lang=java transclude={25-25}](@/reference/latest/src/main/java/com/example/docs/damage/TaterBlock.java)

L'intera implementazione del blocco:

@[code lang=java transcludeWith=:::1](@/reference/latest/src/main/java/com/example/docs/damage/TaterBlock.java)

Ora quando un'entità vivente calpesta il nostro blocco personalizzato, subirà 5 di danno (2.5 cuori) usando il nostro tipo di danno personalizzato.

### Messaggio di Morte Personalizzato {#custom-death-message}

Puoi definire un messaggio di morte per il tipo di danno nel formato `death.attack.<message_id>` nel file `en_us.json` della nostra mod.

@[code lang=json transclude={4-4}](@/reference/latest/src/main/resources/assets/fabric-docs-reference/lang/en_us.json)

Al momento della morte dal nostro tipo di danno personalizzato, vedrete il messaggio di morte seguente:

![Effetto nell'inventario del giocatore](/assets/develop/tater-damage-death.png)

### Tag dei Tipi di Danno {#damage-type-tags}

Alcuni tipi di danno possono bypassare armatura, bypassare effetti di stato, o simili. I tag sono usati per controllare questo genere di proprietà dei tipi di danno.

Puoi trovare tipi di danno già esistenti in `data/minecraft/tags/damage_type`.

::: info

Affidati alla [Minecraft Wiki](https://minecraft.wiki/w/Tag#Damage_types) per una lista completa dei tag dei tipi di danno.

:::

Aggiungiamo il nostro tipo di danno Tater al tag `bypasses_armor` dei tipi di danno.

Per aggiungere il nostro tipo di danno a uno di questi tag, creeremo un file JSON nel namespace `minecraft`.

```:no-line-numbers
data/minecraft/tags/damage_type/bypasses_armor.json
```

Con il contenuto seguente:

@[code lang=json](@/reference/latest/src/main/generated/data/minecraft/tags/damage_type/bypasses_armor.json)

Assicurati che il tuo tag non sostituisca il tag esistente impostando la chiave `replace` a `false`.
