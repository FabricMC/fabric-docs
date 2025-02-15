---
title: Utensili e Armi
description: Impara come creare il tuo utensile e configurarne le proprietà.
authors:
  - IMB11
---

Gli utensili sono essenziali per la sopravvivenza e l'avanzamento, poiché permettono ai giocatori di raccogliere risorse, costruire edifici, e difendere se stessi.

## Creare un Materiale dell'Utensile {#creating-a-tool-material}

Puoi creare un materiale degli utensili istanziando un nuovo oggetto `ToolMaterial` e memorizzandolo in un attributo da usare dopo per creare gli oggetti degli utensili che usano il materiale.

@[code transcludeWith=:::guidite_tool_material](@/reference/latest/src/main/java/com/example/docs/item/ModItems.java)

Il costruttore di `ToolMaterial` accetta i parametri seguenti, in questo ordine:

| Parametro                 | Descrizione                                                                                                                                                                                  |
| ------------------------- | -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| `incorrectBlocksForDrops` | Se un blocco è nel tag incorrectBlocksForDrops, significa che quando si usa un'utensile fatto di questo `ToolMaterial` su quel blocco, il blocco non dropperà alcun oggetto. |
| `durability`              | La durabilità di tutti gli utensili fatti di questo `ToolMaterial`.                                                                                                          |
| `speed`                   | La velocità con cui tutti gli utensili fatti di questo `ToolMaterial` rompono, per esempio, i blocchi.                                                                       |
| `attackDamageBonus`       | Il danno aggiuntivo in attacco che avranno gli utensili di questo `ToolMaterial`.                                                                                            |
| `enchantmentValue`        | L'"incantabilità" degli utensili fatti di questo `ToolMaterial`.                                                                                                             |
| `repairItems`             | Tutti gli oggetti in questo tag possono essere usati per riparare utensili di questo `ToolMaterial` in un'incudine.                                                          |

Se fai fatica a determinare valori adatti per ciascuno dei parametri numerici, considera le costanti dei materiali di utensili vanilla, tra cui `ToolMaterial.STONE` o `ToolMaterial.DIAMOND`.

## Creare Oggetti per gli Utensili {#creating-tool-items}

Con la stessa funzione di utilità della guida [Creare il Tuo Primo Oggetto](./first-item), puoi creare gli oggetti dei tuoi utensili:

@[code transcludeWith=:::7](@/reference/latest/src/main/java/com/example/docs/item/ModItems.java)

I due valori float (`1f, 1f`) fanno riferimento al danno in attacco dell'utensile e alla sua velocità d'attacco rispettivamente.

Ricorda di aggiungerli ad un gruppo di oggetti se vuoi accedere ad essi dall'inventario in creativa!

@[code transcludeWith=:::8](@/reference/latest/src/main/java/com/example/docs/item/ModItems.java)

Dovrai anche aggiungere una texture, una traduzione e un modello per l'oggetto. Tuttavia, per i modelli, dovrai usare il modello `item/handheld` come genitore, invece del solito `item/generated`.

Per questo esempio, useremo il modello e la texture seguenti per l'oggetto "Spada di Guidite":

@[code](@/reference/latest/src/main/generated/assets/fabric-docs-reference/models/item/guidite_sword.json)

<DownloadEntry visualURL="/assets/develop/items/tools_0.png" downloadURL="/assets/develop/items/tools_0_small.png">Texture</DownloadEntry>

Questo è praticamente tutto! Se passi al gioco dovresti vedere gli oggetti dei tuoi strumenti nella scheda utensili dell'inventario in creativa.

![Utensili nell'inventario](/assets/develop/items/tools_1.png)
