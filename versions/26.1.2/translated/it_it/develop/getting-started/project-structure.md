---
title: Struttura del Progetto
description: Una panoramica sulla struttura di un progetto per una mod Fabric.
authors:
  - IMB11
---

Questa pagina analizzerà la struttura di un progetto per una mod Fabric, e l'utilità di ogni file e cartella nel progetto.

## `fabric.mod.json` {#fabric-mod-json}

Il file [`fabric.mod.json`](../loader/fabric-mod-json) è il file principale che descrive la tua mod al Loader di Fabric. Contiene informazioni come l'ID della mod, la versione, e le dipendenze.

Gli attributi più importanti nel file `fabric.mod.json` sono:

- `id`: L'ID della mod, che dovrebbe essere unico.
- `name`: Il nome della mod.
- `environment`: L'ambiente in cui la tua mod viene eseguita, come `client`, `server`, o `*` per entrambi.
- `entrypoints`: Gli entrypoint che la tua mod fornisce, come `main` o `client`.
- `depends`: Le mod da cui la tua mod dipende.
- `mixins`: I mixin che la tua mod fornisce.

## Entrypoint {#entrypoints}

Come detto in precedenza, il file [`fabric.mod.json`](../loader/fabric-mod-json) contiene un attributo `entrypoints` - questo attributo è usato per specificare gli entrypoint che la tua mod fornisce.

Il generatore di mod modello crea sia un entrypoint `main` che `client` predefiniti:

- L'entrypoint `main` è usato per codice comune, ed è contenuto in una classe che implementi `ModInitializer`
- L'entrypoint `client` è usato per codice esclusivo del client, e la sua classe implementa `ClientModInitializer`

Questi entrypoint vengono chiamati rispettivamente quando il gioco viene avviato.

Ecco un esempio di un entrypoint `main` molto semplice che logga un messaggio alla console quando si avvia il gioco:

<<< @/reference/26.1.2/src/main/java/com/example/docs/ExampleMod.java#entrypoint

## `src/main/resources` {#src-main-resources}

Nella cartella `src/main/resources` si memorizzano le risorse che la tua mod usa, come texture, modelli, e suoni.

È anche la posizione di `fabric.mod.json` e di qualsiasi file di configurazione mixin che la tua mod usa.

Le risorse sono memorizzate in una struttura che rispecchia la struttura dei pacchetti risorse - per esempio, una texture per un blocco verrebbe memorizzata in `assets/example-mod/textures/block/block.png`.

## `src/client/resources` {#src-client-resources}

Nella cartella `src/client/resources` si memorizzano risorse client specifiche, come texture, modelli, e suoni che sono solo usati dal lato client.

## `src/main/java` {#src-main-java}

La cartella `src/main/java` viene usata per memorizzare il codice sorgente Java per la tua mod - esiste sia su ambienti client sia server.

## `src/client/java` {#src-client-java}

La cartella `src/client/java` viene usata per memorizzare codice sorgente Java client specifico, come codice per il rendering o logica del lato client - come provider per il colore dei blocchi.
