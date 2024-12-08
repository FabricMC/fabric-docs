---
title: Utensili e Armi
description: Impara come si crea il tuo strumento e configurarne le proprietà.
authors:
  - IMB11
---

# Strumenti {#tools}

Gli strumenti sono essenziali per la sopravvivenza e l'avanzamento, poiché permettono ai giocatori di raccogliere risorse, costruire edifici, e difendere sé stessi.

## Creare un Materiale dello Strumento {#creating-a-tool-material}

::: info
If you're creating multiple tool materials, consider using an `Enum` to store them. Vanilla does this in the `ToolMaterials` class, which stores all the tool materials that are used in the game.

Questa classe può anche essere usata per determinare le proprietà del materiale del tuo strumento, legate a quelle dei materiali di strumenti vanilla.
:::

Puoi creare un materiale dello strumento creando una nuova classe che lo eredita - in questo esempio, creeremo strumenti di "Guidite":

@[code transcludeWith=:::1](@/reference/latest/src/main/java/com/example/docs/item/tool/GuiditeMaterial.java)

Quando avrai creato il materiale del tuo strumento e l'avrai modificato a piacere, puoi creare un'istanza di esso da usare nei costruttori degli oggetti.

@[code transcludeWith=:::8](@/reference/latest/src/main/java/com/example/docs/item/tool/GuiditeMaterial.java)

Il materiale dello strumento informa il gioco sulle seguenti proprietà:

### Durabilità - `getDurability()` {#durability}

Quante volte si può usare lo strumento prima che si rompa:

@[code transcludeWith=:::2](@/reference/latest/src/main/java/com/example/docs/item/tool/GuiditeMaterial.java)

### Velocità di Rottura - `getMiningSpeedMultiplier()` {#mining-speed}

Se lo strumento viene usato per rompere blocchi, quanto velocemente deve fare ciò?

@[code transcludeWith=:::3](@/reference/latest/src/main/java/com/example/docs/item/tool/GuiditeMaterial.java)

Per darti un riferimento, il materiale diamante ha come velocità di rottura `8.0F`, mentre quello di pietra ha come velocità `4.0F`.

### Danno da Attacco - `getAttackDamage()` {#attack-damage}

Quanti punti di danno deve causare lo strumento quando lo si usa come arma contro un'altra entità?

@[code transcludeWith=:::4](@/reference/latest/src/main/java/com/example/docs/item/tool/GuiditeMaterial.java)

### Tag Invertito - `getMiningLevel()` {#inverse-tag}

Il tag invertito mostra ciò che l'oggetto _**non**_ può rompere. Per esempio, usare il tag `BlockTags.INCORRECT_FOR_WOODEN_TOOL` non permette allo strumento di rompere certi blocchi:

```json
{
  "values": [
    "#minecraft:needs_diamond_tool",
    "#minecraft:needs_iron_tool",
    "#minecraft:needs_stone_tool"
  ]
}
```

Questo significa che lo strumento non può rompere blocchi che richiedono strumenti di diamante, ferro o pietra.

Usiamo il tag degli strumenti di ferro. Questo non permetterà agli strumenti di Guidite di rompere blocchi che richiedono uno strumento più forte del ferro.

@[code transcludeWith=:::5](@/reference/latest/src/main/java/com/example/docs/item/tool/GuiditeMaterial.java)

Puoi usare `TagKey.of(...)` per creare una chiave di tag personalizzata se vuoi usare un tag personalizzato.

### Incantabilità - `getEnchantability()` {#enchantability}

Quanto facile è ottenere livelli maggiori e migliori degli incantesimi con questo oggetto? Per riferimento, l'oro ha incantabilità 22, mentre la netherite ha incantabilità 15.

@[code transcludeWith=:::6](@/reference/latest/src/main/java/com/example/docs/item/tool/GuiditeMaterial.java)

### Ingredienti di Riparo - `getRepairIngredient()` {#repair-ingredient}

Quale oggetto o oggetti si usano per riparare lo strumento?

@[code transcludeWith=:::7](@/reference/latest/src/main/java/com/example/docs/item/tool/GuiditeMaterial.java)

## Creare Oggetti per gli Strumenti {#creating-tool-items}

Con la stessa funzione di utilità della guida [Creare il Tuo Primo Oggetto](./first-item), puoi creare gli oggetti dei tuoi strumenti:

@[code transcludeWith=:::7](@/reference/latest/src/main/java/com/example/docs/item/ModItems.java)

Ricorda di aggiungerli ad un gruppo di oggetti se vuoi accedere ad essi dall'inventario in creativa!

@[code transcludeWith=:::8](@/reference/latest/src/main/java/com/example/docs/item/ModItems.java)

Dovrai anche aggiungere una texture, una traduzione e un modello per l'oggetto. Tuttavia, per i modelli, dovrai usare il modello `item/handheld` come genitore.

Per questo esempio, useremo il modello e la texture seguenti per l'oggetto "Spada di Guidite":

@[code](@/reference/latest/src/main/resources/assets/fabric-docs-reference/models/item/guidite_sword.json)

<DownloadEntry visualURL="/assets/develop/items/tools_0.png" downloadURL="/assets/develop/items/tools_0_small.png">Texture</DownloadEntry>

Questo è praticamente tutto! Se passi al gioco dovresti vedere gli oggetti dei tuoi strumenti nella scheda strumenti del menu inventario in creativa.

![Strumenti nell'inventario](/assets/develop/items/tools_1.png)
