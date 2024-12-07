---
title: Armature Personalizzate
description: Impara come creare i tuoi set di armature personalizzati.
authors:
  - IMB11
---

# Armature Personalizzate {#custom-armor}

Un'armatura fornisce al giocatore una difesa migliore contro attacchi di mob e di altri giocatori.

## Creare una Classe per un Materiale delle Armature {#creating-an-armor-materials-class}

Proprio come gli oggetti e i blocchi, i materiali delle armature devono essere registrati. Per mettere ordine, creeremo una classe `ModArmorMaterials` in cui memorizzare il nostro materiale personalizzato.

Dovrai aggiungere un metodo statico `initialize()` a questa classe, e chiamarlo dall'[initializer della tua mod](./getting-started/project-structure#entrypoints) perché i materiali vengano registrati.

```java
// Within the ModArmorMaterials class
public static void initialize() {};
```

:::warning
Assicurati di chiamare questo metodo **prima** di registrare i tuoi oggetti, poiché sarà necessario che i materiali siano registrati prima che gli oggetti vengano creati.
:::

```java
@Override
public void onInitialize() {
  ModArmorMaterials.initialize();
}
```

---

All'interno di questa classe `ModArmorMaterials`, dovrai creare un metodo statico che registrerà il materiale dell'armatura. Questo metodo dovrebbe restituire una voce di registry per il materiale, perché questa voce verrà usata dal costruttore di ArmorItem per creare le componenti dell'armatura.

@[code transcludeWith=:::1](@/reference/latest/src/main/java/com/example/docs/item/armor/ModArmorMaterials.java)

## Proprietà dei Materiali delle Armature {#armor-material-properties}

:::tip
Se ti è difficile decidere un buon valore per queste proprietà, potresti prendere ispirazione ai materiali delle armature vanilla nella classe `ArmorMaterials`.
:::

Durante la creazione di un materiale delle armature, dovrai definire le seguenti proprietà:

### Punti Difesa {#defense-points}

:::warning
Assicurati di assegnare un valore a ogni pezzo di armatura che intendi creare e registrare come oggetto. Se creassi un oggetto per un pezzo di armatura senza impostare un valore per i punti di difesa, il gioco andrà in crash.
:::

La mappa `defensePoints` viene usata per definire il numero di punti difesa che ciascun pezzo dell'armatura fornirà. Più alto è il numero, maggiore la protezione fornita dal pezzo di armatura. La mappa dovrebbe contenere una voce per ciascun tipo di pezzo di armatura.

### Incantabilità {#enchantability}

La proprietà `enchantability` definisce la facilità con cui l'armatura può essere incantata. Più alto è il numero, maggiore la quantità di incantesimi che può ricevere l'armatura.

### Suono Appena Indossata {#equip-sound}

La proprietà `equipSound` è il suono che verrà riprodotto quando l'armatura viene indossata. Questo suono dovrebbe essere una voce di registry di un `SoundEvent`. Potresti voler dare un'occhiata alla pagina sui [SoundEvent personalizzati](../sounds/custom) se avessi in mente di creare suoni personalizzati invece di affidarti ai suoni vanilla dalla classe `SoundEvents`.

### Ingredienti per il Riparo {#repair-ingredient}

La proprietà `repairIngredientSupplier` fornisce un `Ingredient` che viene usato per riparare l'armatura. Questo ingrediente può essere quasi qualsiasi cosa, consigliamo di sceglierlo in modo che sia lo stesso materiale con cui si creano gli oggetti dell'armatura.

### Tenacità {#toughness}

La proprietà `toughness` definisce quanto danno l'armatura assorbirà. Più alto è il numero, maggiore il danno che l'armatura assorbirà.

### Resistenza al Contraccolpo {#knockback-resistance}

La proprietà `knockbackResistance` definisce quanto contraccolpo verrà riflesso dal giocatore quando viene colpito. Più alto è il numero, minore il contraccolpo che riceverà il giocatore.

### Tingibile {#dyeable}

La proprietà `dyeable` è un booleano che definisce se l'armatura può essere tinta. Se fosse `true`, l'armatura potrebbe essere tinta coi coloranti in un banco da lavoro.

Se scegliessi di rendere la tua armatura tingibile, lo strato della tua armatura e le texture degli oggetti devono essere **pensati per essere tinti**, poiché il colorante si sovrapporrà alla texture, non la sostituirà. Prendi per esempio l'armatura di cuoio vanilla: le texture sono in scala di grigi e il colorante viene applicato in sovrapposizione, il che cambia il colore dell'armatura.

## Registrare il Materiale dell'Armatura {#registering-the-armor-material}

Ora che hai creato un metodo di utilità che può essere usato per registrare materiali di armature, puoi registrare i tuoi materiali di armature personalizzati come attributo statico nella classe `ModArmorMaterials`.

Per questo esempio, creeremo l'armatura di Guidite, con le seguenti proprietà:

@[code transcludeWith=:::2](@/reference/latest/src/main/java/com/example/docs/item/armor/ModArmorMaterials.java)

## Creare gli Oggetti dell'Armatura {#creating-the-armor-items}

Ora che hai registrato il materiale, puoi creare gli oggetti dell'armatura nella tua classe `ModItems`:

Ovviamente, un set di armatura non deve per forza essere completo, puoi avere un set con solo stivali, o solo gambiere... - il carapace di tartaruga vanilla è un buon esempio di un set di armatura con elementi mancanti.

### Durabilità {#durability}

A differenza di `ToolMaterial`, `ArmorMaterial` non memorizza alcuna informazione riguardo alla durabilità degli oggetti.
Per questo motivo la durabilità deve essere aggiunta manualmente a `Item.Settings` degli oggetti dell'armatura quando li si registra.

Questo si fa con il metodo `maxDamage` nella classe `Item.Settings`.
Le varie parti dell'armatura hanno durabilità base diverse, solitamente ottenute come prodotto di un fattore condiviso a livello del materiale dell'armatura, ma si possono anche scegliere valori fissi.

Per l'armatura di Guidite useremo un fattore condiviso memorizzato assieme al materiale dell'armatura:

@[code transcludeWith=:::3](@/reference/latest/src/main/java/com/example/docs/item/armor/ModArmorMaterials.java)

Possiamo quindi creare gli oggetti dell'armatura con la costante di durabilità:

@[code transcludeWith=:::6](@/reference/latest/src/main/java/com/example/docs/item/ModItems.java)

Dovrai anche **aggiungere gli oggetti ad un gruppo** se vorrai che essi siano accessibili dall'inventario in creativa.

Come per tutti gli oggetti, dovresti creare chiavi di traduzione anche per questi.

## Texture e Modelli {#texturing-and-modelling}

Dovrai creare due insiemi di texture:

- Texture e modelli per gli oggetti stessi.
- Texture per l'armatura in sé, visibile quando un'entità la indossa.

### Texture e Modello dell'Oggetto {#item-textures-and-model}

Queste texture non differiscono da quelle di altri oggetti - devi creare le texture, e creare un modello di oggetto generico generato - tutto questo è coperto dalla guida [Creare il Tuo Primo Oggetto](./first-item#adding-a-texture-and-model).

Come esempio, puoi usare le seguenti texture e modelli JSON come riferimento.

<DownloadEntry visualURL="/assets/develop/items/armor_0.png" downloadURL="/assets/develop/items/example_armor_item_textures.zip">Texture degli Oggetti</DownloadEntry>

:::info
Ti serviranno modelli in file JSON per tutti gli oggetti, non solo l'elmo, stesso principio di altri modelli di oggetti.
:::

@[code](@/reference/latest/src/main/resources/assets/fabric-docs-reference/models/item/guidite_helmet.json)

Come puoi notare, gli oggetti dell'armatura avranno i modelli appropriati nel gioco:

![Modelli degli oggetti dell'armatura](/assets/develop/items/armor_1.png)

## Texture e Modello dell'Armatura {#armor-textures-and-model}

Quando un'entità indossa la tua armatura, per ora apparirà la texture mancante:

![Modello di armatura corrotto su un giocatore](/assets/develop/items/armor_2.png)

Ci sono due strati per le texture dell'armatura, entrambi devono essere presenti.

Poiché il nome del materiale dell'armatura è nel nostro caso `guidite`, i percorsi delle texture saranno:

- `assets/<mod-id>/textures/models/armor/guidite_layer_1.png`
- `assets/<mod-id>/textures/models/armor/guidite_layer_2.png`

<DownloadEntry downloadURL="/assets/develop/items/example_armor_layer_textures.zip">Texture dei Modelli delle Armature</DownloadEntry>

Il primo strato contiene texture per elmo e corazza, mentre il secondo strato contiene texture per gambiere e stivali.

Quando queste texture sono presenti, dovresti poter vedere la tua armatura sulle entità che la indossano:

![Modello di armatura funzionante su un giocatore](/assets/develop/items/armor_3.png)
