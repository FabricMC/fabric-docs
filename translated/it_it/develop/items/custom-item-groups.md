---
title: Schede d'inventario Personalizzate
description: Impara come creare la tua scheda d'inventario in Creativa e come aggiungerci oggetti.
authors:
  - CelDaemon
  - IMB11
---

Le schede d'inventario, anche dette gruppi di oggetti, sono quelle dell'inventario in Creativa, in cui vengono memorizzati gli oggetti. Puoi creare la tua scheda personalizzata per memorizzare i tuoi oggetti a parte. Questo è piuttosto utile se la tua mod aggiunge molti oggetti e vuoi tenerli organizzati in una sola posizione per facilitarne l'accesso per i giocatori.

## Creare la scheda d'inventario {#creating-the-creative-tab}

Aggiungere una scheda all'inventario è abbastanza semplice. Basta creare un nuovo attributo `static final` nella classe dei tuoi oggetti per memorizzare la scheda e una chiave di registry per essa. Poi, potrai usare `FabricItemGroup.builder` per crearla e aggiungerci gli oggetti:

@[code transcludeWith=:::9](@/reference/latest/src/main/java/com/example/docs/item/ModItems.java)

@[code transcludeWith=:::\_12](@/reference/latest/src/main/java/com/example/docs/item/ModItems.java)

Dovresti notare che la nuova scheda è ora nel menu dell'inventario in Creativa. Tuttavia, è rimasto senza traduzione - devi aggiungere una chiave al tuo file di traduzioni - come quando hai tradotto il tuo primo oggetto.

![Scheda senza traduzione nell'inventario in Creativa](/assets/develop/items/itemgroups_0.png)

## Aggiungere una Chiave di Traduzione {#adding-a-translation-key}

Se avessi per caso usato `Component.translatable` per il metodo `title` del costruttore della scheda, dovrai aggiungere la traduzione al tuo file di lingua.

```json
{
  "itemGroup.example-mod": "Example Mod"
}
```

Ora, come puoi notare, la scheda dovrebbe avere il nome corretto:

![Scheda d'inventario completata, con traduzione e oggetti](/assets/develop/items/itemgroups_1.png)
