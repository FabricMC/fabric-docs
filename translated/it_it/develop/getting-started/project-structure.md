---
title: Struttura del Progetto
description: Una panoramica sulla struttura di un progetto per una mod Fabric.
authors:
  - IMB11
---

# Struttura del Progetto {#project-structure}

Questa pagina analizzerà la struttura di un progetto per una mod Fabric, e l'utilità di ogni file e cartella nel progetto.

## `fabric.mod.json` {#fabric-mod-json}

Il file `fabric.mod.json` è il file principale che descrive la tua mod al Loader di Fabric. Contiene informazioni come l'ID della mod, la versione, e le dipendenze.

Gli attributi più importanti nel file `fabric.mod.json` sono:

- `id`: L'ID della mod, che dovrebbe essere unico.
- `name`: Il nome della mod.
- `environment`: L'ambiente in cui la tua mod viene eseguita, come `client`, `server`, o `*` per entrambi.
- `entrypoints`: Gli entrypoint che la tua mod fornisce, come `main` o `client`.
- `depends`: Le mod da cui la tua mod dipende.
- `mixins`: I mixin che la tua mod fornisce.

Puoi trovare un esempio del file `fabric.mod.json` sotto - questo è il file `fabric.mod.json` per il progetto di riferimento su cui è basato questo sito di documentazione.

:::details `fabric.mod.json` del Progetto di Riferimento
@[code lang=json](@/reference/latest/src/main/resources/fabric.mod.json)
:::

## Entrypoint {#entrypoints}

Come detto in precedenza, il file `fabric.mod.json` contiene un attributo `entrypoints` - questo attributo è usato per specificare gli entrypoint che la tua mod fornisce.

Il generatore di mod modello crea sia un entrypoint `main` che `client` predefiniti - l'entrypoint `main` è usato per codice comune, mentre l'entrypoint `client` è usato per codice client specifico. Questi entrypoint vengono chiamati rispettivamente quando il gioco viene avviato.

@[code lang=java transcludeWith=#entrypoint](@/reference/latest/src/main/java/com/example/docs/FabricDocsReference.java)

Quello sopra è un esempio di un semplice entrypoint `main` che logga un messaggio alla console quando il gioco si avvia.

## `src/main/resources` {#src-main-resources}

La cartella `src/main/resources` viene usata per memorizzare le risorse che la tua mod utilizza, come texture, modelli, e suoni.

È anche la posizione di `fabric.mod.json` e di qualsiasi file di configurazione mixin che la tua mod utilizza.

Le risorse sono memorizzate in una struttura che rispecchia la struttura dei pacchetti risorse - per esempio, una texture per un blocco verrebbe memorizzata in `assets/modid/textures/block/block.png`.

## `src/client/resources` {#src-client-resources}

La cartella `src/client/resources` viene usata per memorizzare risorse client specifiche, come texture, modelli, e suoni che sono solo utilizzati dal lato client.

## `src/main/java` {#src-main-java}

La cartella `src/main/java` viene usata per memorizzare il codice sorgente Java per la tua mod - esiste sia su ambienti client sia server.

## `src/client/java` {#src-client-java}

La cartella `src/client/java` viene usata per memorizzare codice sorgente Java client specifico, come codice per il rendering o logica del lato client - come provider per il colore dei blocchi.
