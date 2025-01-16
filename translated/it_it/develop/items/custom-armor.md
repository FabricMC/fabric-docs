---
title: Armature Personalizzate
description: Impara come creare i tuoi set di armature personalizzati.
authors:
  - IMB11
---

# Armature Personalizzate {#custom-armor}

Un'armatura fornisce al giocatore una difesa migliore contro attacchi di mob e di altri giocatori.

## Creare una Classe per un Materiale delle Armature {#creating-an-armor-materials-class}

Tecnicamente, non serve una classe apposita per il materiale della tua armatura, ma comunque è buona pratica data la quantità di attributi statici di cui avrai bisogno.

Per questo esempio, creeremo una classe `GuiditeArmorMaterial` che memorizzi i nostri attributi statici.

### Durabilità di Base {#base-durability}

Questa costante verrà usata nel metodo `Item.Settings#maxDamage(int damageValue)` quando si creano gli oggetti della nostra armatura, ed è anche necessaria come parametro nel costruttore `ArmorMaterial` quando creeremo successivamente il nostro oggetto `ArmorMaterial`.

@[code transcludeWith=:::base_durability](@/reference/latest/src/main/java/com/example/docs/item/armor/GuiditeArmorMaterial.java)

Se fai fatica a determinare una durabilità di base bilanciata, puoi far riferimento alle istanze dei materiali di armature vanilla che trovi nell'interfaccia `ArmorMaterials`.

### Chiave di Registry per gli Asset Indossati {#equipment-asset-registry-key}

Anche se non dobbiamo registrare il nostro `ArmorMaterial` in alcuna registry, è in genere buona pratica memorizzare le chiavi di registry come costanti, poiché il gioco userà queste per trovare le texture adatte alla nostra armatura.

@[code transcludeWith=:::material_key](@/reference/latest/src/main/java/com/example/docs/item/armor/GuiditeArmorMaterial.java)

Dopo passeremo questo al costruttore di `ArmorMaterial`.

### Istanza di `ArmorMaterial` {#armormaterial-instance}

Per creare il nostro materiale, dobbiamo creare una nuova istanza del record `ArmorMaterial`, in cui useremo le costanti di durabilità di base e chiave di registry del materiale.

@[code transcludeWith=:::guidite_armor_material](@/reference/latest/src/main/java/com/example/docs/item/armor/GuiditeArmorMaterial.java)

Il costruttore di `ArmorMaterial` accetta i parametri seguenti, in questo ordine:

| Parametro             | Descrizione                                                                                                                                                                                                                                                                                  |
| --------------------- | -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| `durability`          | La durabilità di base di tutti i pezzi d'armatura, questa viene usata nel calcolare la durabilità totale di ciascun pezzo individuale di armatura che usa questo materiale. Questa dovrebbe essere la costante di durabilità di base che hai creato pocanzi. |
| `defense`             | Una mappa da `EquipmentType` (un enum che rappresenti ogni casella d'armatura) a un valore intero, che indica il valore di difesa del materiale se usato nella casella d'armaturà corrispondente.                                                         |
| `enchantmentValue`    | L'"incantabilità" degli oggetti d'armatura che usano questo materiale.                                                                                                                                                                                                       |
| `equipSound`          | Una voce di registry di un evento sonoro da riprodurre appena indossato un pezzo d'armatura che usa questo materiale. Per maggiori informazioni sui suoni, dai un'occhiata alla pagina [Suoni Personalizzati](../sounds/custom).                             |
| `toughness`           | Un valore float che rappresenti l'attributo "tenacità" del materiale d'armatura - in altre parole quanto l'armatura assorba bene il danno.                                                                                                                                   |
| `knockbackResistance` | Un valore float che rappresenti la quantità di resistenza al contraccolpo che il materiale d'armatura offre a chi la indossa.                                                                                                                                                |
| `repairIngredient`    | Un tag di oggetti che rappresenta tutti gli oggetti che possono essere usati per riparare gli oggetti dell'armatura di questo materiale in un'incudine.                                                                                                                      |
| `assetId`             | Una chiave di registry `EquipmentAsset`, questa dovrebbe essere la costante di chiave di registry per l'asset indossato creata in precedenza.                                                                                                                                |

Se fai fatica a determinare valori adatti per ciascuno di questi parametri, puoi consultare le istanze dei `ArmorMaterial` vanilla che trovi nell'interfaccia `ArmorMaterials`.

## Creare gli Oggetti dell'Armatura {#creating-the-armor-items}

Ora che hai registrato il materiale, puoi creare gli oggetti dell'armatura nella tua classe `ModItems`:

Ovviamente, un set di armatura non deve per forza essere completo, puoi avere un set con solo stivali, o solo gambiere... - il carapace di tartaruga vanilla è un buon esempio di un set di armatura con elementi mancanti.

A differenza di `ToolMaterial`, `ArmorMaterial` non memorizza alcuna informazione riguardo alla durabilità degli oggetti. Per questo motivo la durabilità di base deve essere aggiunta manualmente a `Item.Settings` degli oggetti dell'armatura quando li si registra.

Questo si ottiene passando la costante `BASE_DURABILITY` che abbiamo creato sopra nel metodo `maxDamage` nella classe `Item.Settings`.

@[code transcludeWith=:::6](@/reference/latest/src/main/java/com/example/docs/item/ModItems.java)

Dovrai anche **aggiungere gli oggetti ad un gruppo** se vorrai che essi siano accessibili dall'inventario in creativa.

Come per tutti gli oggetti, dovresti creare chiavi di traduzione anche per questi.

## Texture e Modelli {#textures-and-models}

Dovremo creare un insieme di texture per gli oggetti, e un insieme di texture per l'armatura effettiva quando indossata da un'entità "umanoide" (giocatori, zombie, scheletri...).

### Texture e Modello dell'Oggetto {#item-textures-and-model}

Queste texture non differiscono da quelle di altri oggetti - devi creare le texture, e creare un modello di oggetto generico generato - tutto questo è coperto dalla guida [Creare il Tuo Primo Oggetto](./first-item#adding-a-texture-and-model).

Come esempio, puoi usare le seguenti texture e modelli JSON come riferimento.

<DownloadEntry visualURL="/assets/develop/items/armor_0.png" downloadURL="/assets/develop/items/example_armor_item_textures.zip">Texture degli Oggetti</DownloadEntry>

:::info
Ti serviranno modelli in file JSON per tutti gli oggetti, non solo l'elmo, stesso principio di altri modelli di oggetti.
:::

@[code](@/reference/latest/src/main/generated/assets/fabric-docs-reference/models/item/guidite_helmet.json)

Come puoi notare, gli oggetti dell'armatura avranno i modelli appropriati nel gioco:

![Modelli degli oggetti dell'armatura](/assets/develop/items/armor_1.png)

### Texture dell'Armatura {#armor-textures}

Quando un'entità indossa la tua armatura, non si vedrà nulla. Questo perché ti mancano le definizioni di texture e di modello indossato.

![Modello di armatura corrotto su un giocatore](/assets/develop/items/armor_2.png)

Ci sono due strati per le texture dell'armatura, entrambi devono essere presenti.

Abbiamo prima creato una costante `RegistryKey<EquipmentAsset>` chiamata `GUIDITE_ARMOR_MATERIAL_KEY`, che avevamo passato nel nostro costruttore `ArmorMaterial`. Si consiglia di nominare la texture similmente, per cui nel nostro caso sarà `guidite.png`

- `assets/<mod_id>/textures/entity/equipment/humanoid/guidite.png` - Contiene texture della parte superiore del corpo e degli stivali.
- `assets/<mod_id>/textures/entity/equipment/humanoid_leggings/guidite.png` - Contiene texture per il gambiere.

<DownloadEntry downloadURL="/assets/develop/items/example_armor_layer_textures.zip">Texture dei Modelli delle Armature in Guidite</DownloadEntry>

:::tip
Se stai passando a 1.21.4 da una versione meno recente del gioco, è nella cartella `humanoid` che si mette la tua texture d'armatura `layer0.png`, mentre la cartella `humanoid_leggings` contiene la tua texture `layer1.png`.
:::

Poi, dovrai creare una definizione del modello indossato ad essa associata. Queste vanno nella cartella `/assets/<mod_id>/equipment/`.

La costante `RegistryKey<EquipmentAsset>` creata più sopra determinerà il nome del file JSON. In questo caso sarà `guidite.json`.

Poiché vogliamo solo aggiungere i pezzi d'armatura "umanoidi" (elmo, corazza, gambiere, stivali...), la definizione del nostro modello indossato sarà come segue:

@[code](@/reference/latest/src/main/resources/assets/fabric-docs-reference/equipment/guidite.json)

Quando sia le texture sia le definizioni dei modelli indossati sono presenti, dovresti poter vedere la tua armatura sulle entità che la indossano:

![Modello di armatura funzionante su un giocatore](/assets/develop/items/armor_3.png)

<!-- TODO: A guide on creating equipment for dyeable armor could prove useful. -->
