---
title: Avviare il Gioco
description: Impara come usare i vari profili d'avvio per avviare ed effettuare debug delle tue mod in un ambiente di gioco dal vivo.
authors:
  - IMB11

search: false
---

# Avviare il Gioco

Loom di Fabric fornisce una varietà di profili d'avvio che ti aiutano ad avviare ed effettuare debug delle tue mod in un ambiente di gioco live. Questa guida tratterà dei vari profili d'avvio e di come usarli per effettuare debug e per testare le tue mod nel gioco.

## Profili d'Avvio

Se stai usando IntelliJ IDEA, puoi trovare i profili d'avvio nell'angolo in alto a destra della finestra. Clicca sul menu a tendina per vedere i profili d'avvio disponibili.

Dovrebbe esserci un profilo client e uno server, con l'opzione di eseguire normalmente o in modalità debug:

![Profili d'Avvio](/assets/develop/getting-started/launch-profiles.png)

## Operazioni Gradle

Se stai usando la linea di comando, puoi usare i comandi Gradle seguenti per avviare il gioco:

- `./gradlew runClient` - Avvia il gioco in modalità client.
- `./gradlew runServer` - Avvia il gioco in modalità server.

L'unico problema con questo approccio è che non puoi facilmente effettuare il debug del tuo codice. Se vuoi effettuare debug del tuo codice, avrai bisogno di usare i profili d'avvio in IntelliJ IDEA o tramite l'integrazione Gradle del tuo IDE.

## Hotswapping delle Classi

Quando esegui il gioco in modalità debug, puoi fare hotswap ("scambio a caldo") delle tue classe senza riavviare il gioco. Questo è utile per testare cambiamenti al tuo codice velocemente.

Tuttavia ci sono alcune limitazioni:

- Non puoi aggiungere né rimuovere metodi
- Non puoi cambiare i parametri dei metodi
- Non puoi aggiungere né togliere attributi

## Hotswapping dei Mixin

Se stai usando i Mixin, puoi fare hotswap delle tue classi Mixin senza riavviare il gioco. Questo è utile per testare cambiamenti ai tuoi Mixin velocemente.

Avrai bisogno d'installare l'agent Java Mixin perché questo funzioni.

### 1. Trova il Jar della Libreria Mixin

In IntelliJ IDEA, puoi trovare il jar della libreria Mixin nella sezione "Librerie Esterne" della sezione "Progetto":

![Libreria Mixin](/assets/develop/getting-started/mixin-library.png)

Dovrai copiare il "Percorso Assoluto" del jar per il prossimo passaggio.

### 2. Aggiungi l'argomento VM `-javaagent`

Nella tua configurazione di avvio "Client Minecraft " e/o "Server Minecraft", aggiungi ciò che segue all'opzione Argomenti VM:

```:no-line-numbers
-javaagent:"percorso al jar della libreria mixin qui"
```

![Screenshot di Argomenti VM](/assets/develop/getting-started/vm-arguments.png)

Ora, dovresti poter modificare i contenuti dei tuoi metodi mixin durante il debugging e vedere gli effetti delle modifiche senza riavviare il gioco.
