---
title: Gruppi di Oggetti Personalizzati
description: Impara come creare il tuo gruppo di oggetti e come aggiungerci oggetti.
authors:
  - IMB11

search: false---

# Gruppi di Oggetti Personalizzati {#custom-item-groups}

I gruppi di oggetti sono le schede nell'inventario in creativa che memorizzano oggetti. Puoi creare il tuo gruppo di oggetti personalizzato per memorizzare i tuoi oggetti in una scheda separata. Questo è piuttosto utile se la tua mod aggiunge molti oggetti e vuoi tenerli organizzati in una sola posizione per facilitarne l'accesso per i giocatori.

## Creare il Gruppo di Oggetti {#creating-the-item-group}

È sorprendentemente facile creare un gruppo di oggetti. Basta creare un nuovo attributo `static final` nella classe dei tuoi oggetti per memorizzare il gruppo di oggetti e una chiave di registry per esso, puoi quindi usare l'evento del gruppo di oggetti come quando hai aggiunti i tuoi oggetti ai gruppi vanilla:

@[code transcludeWith=:::9](@/reference/1.21/src/main/java/com/example/docs/item/ModItems.java)

@[code transcludeWith=:::_12](@/reference/1.21/src/main/java/com/example/docs/item/ModItems.java)

<hr />

Dovresti notare che il gruppo di oggetti è ora nel menu dell'inventario in creativa. Tuttavia, è rimasto senza traduzione - devi aggiungere una chiave al tuo file di traduzioni - come quando hai tradotto il tuo primo oggetto.

![Gruppo di oggetti senza traduzione nel menu creativo](/assets/develop/items/itemgroups_0.png)

## Aggiungere una Chiave di Traduzione {#adding-a-translation-key}

Se avessi per caso usato `Text.translatable` per il metodo `displayName` del costruttore del gruppo di oggetti, dovrai aggiungere la traduzione al tuo file di lingua.

```json
{
    "itemGroup.fabric_docs_reference": "Fabric Docs Reference"
}
```

Ora, come puoi notare, il gruppo di oggetti dovrebbe avere il nome corretto:

![Gruppo di oggetti completo con traduzione e oggetti](/assets/develop/items/itemgroups_1.png)
