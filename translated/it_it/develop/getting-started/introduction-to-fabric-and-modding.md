---
title: Introduzione A Fabric E Al Modding
description: "Una breve introduzione a Fabric e al modding in Minecraft: Java Edition."
authors:
  - IMB11
  - itsmiir
---

<!-- No GitHub profile exists for "basil4088" -->

# Introduzione A Fabric E Al Modding

## Prerequisiti

Prima d'iniziare, dovresti avere una comprensione basilare dello sviluppo con Java, e una comprensione della Programmazione Orientata agli Oggetti (OOP).

Se questi concetti non ti sono familiari, potresti voler cercare qualche tutorial su Java e sulla OOP prima di cominciare a fare modding, ecco alcune risorse in inglese che puoi utilizzare per imparare Java e OOP:

- [W3: Java Tutorials](https://www.w3schools.com/java/)
- [Codecademy: Learn Java](https://www.codecademy.com/learn/learn-java)
- [W3: Java OOP](https://www.w3schools.com/java/java_oop.asp)
- [Medium: Introduction to OOP](https://medium.com/@Adekola_Olawale/beginners-guide-to-object-oriented-programming-a94601ea2fbd)

### Terminologia

Prima di cominciare, chiariamo alcuni termini che incontrerai nel moddare con Fabric:

- **Mod**: Una modifica al gioco, che aggiunge nuove funzionalità oppure ne modifica di esistenti.
- **Loader di Mod**: Uno strumento che carica le mod nel gioco, per esempio il Loader di Fabric.
- **Mixin**: Uno strumento per modificare il codice del gioco al runtime - vedi [Mixin Introduction](https://fabricmc.net/wiki/tutorial:mixin_introduction) per maggiori informazioni.
- **Gradle**: Uno strumento per l'automatizzazione del build utilizzato per costruire e compilare mod, utilizzato da Fabric per costruire le mod.
- **Mapping**: Un insieme di mapping che mappano codice offuscato a codice leggibile.
- **Offuscamento**: Il processo di rendere il codice difficile da comprendere, utilizzato da Mojang per proteggere il codice di Minecraft.
- **Remapping**: Il processo di rimappare codice offuscato a codice leggibile.

## Cos'è Fabric?

Fabric è una toolchain di modding leggera per Minecraft: Java Edition.

È progettato per essere una piattaforma per modding semplice e facile da utilizzare. Fabric è un progetto guidato dalla comunità, ed è open-source, per cui chiunque può contribuire al progetto.

Dovresti conoscere le quattro componenti principali di Fabric:

- **Loader di Fabric**: Un loader di mod flessibile indipendente dalla piattaforma progettato per Minecraft e per altri giochi e applicazioni.
- **Loom di Fabric**: Un plugin Gradle che permette agli sviluppatori di sviluppare ed effettuare debug delle mod facilmente.
- **API di Fabric**: Un insieme di API e di strumenti per sviluppatori di mod da utilizzare quando si creano mod.
- **Yarn**: Un insieme di mapping Minecraft aperti, gratuiti per tutti da utilizzare sotto la licenza Creative Commons Zero.

## Perché è necessario Fabric per moddare Minecraft?

> Il modding è il processo di modifica del gioco per cambiarne il comportamento o per aggiungere nuove funzionalità - nel caso di Minecraft, questo può essere qualsiasi cosa dall'aggiungere nuovi oggetti, blocchi, o entità, al cambiare le meccaniche del gioco o aggiungere nuove modalità di gioco.

Minecraft: Java Edition è offuscato da Mojang, il che rende la pura modifica difficile. Tuttavia, aiutandosi con strumenti di modding come Fabric, il modding diventa molto più facile. Ci sono vari sistemi di mapping che possono aiutare in questo processo.

Loom rimappa il codice offuscato a un formato leggibile utilizzando questi mapping, rendendo la comprensione e la modifica del codice del gioco più semplice per i modder. Yarn è una scelta popolare ed eccellente di mapping per questo, ma esistono anche altre opzioni. Ogni progetto di mapping potrebbe avere il suo punto forte o la sua focalizzazione.

Loom ti permette di sviluppare e compilare mod facilmente in riferimento a codice rimappato, e il Loader di Fabric ti permette di caricare queste mod nel gioco.

## Cosa fornisce l'API di Fabric, e perché è necessaria?

> L'API di Fabric è un insieme di API e di strumenti per sviluppatori di mod da utilizzare quando si creano mod.

L'API di Fabric fornisce un largo insieme di API che espandono le funzionalità esistenti di Minecraft - per esempio, fornendo nuovi hook ed eventi che i modder possono utilizzare, o fornendo nuove utilità e strumenti per rendere il modding più facile - come access widener transitivi e la possibilità di accedere a registry interne, come la registry di oggetti compostabili.

Anche se l'API di Fabric offre funzionalità potenti, alcune operazioni, come la registrazione basilare di blocchi, possono essere effettuate senza di essa utilizzando le API vanilla.
