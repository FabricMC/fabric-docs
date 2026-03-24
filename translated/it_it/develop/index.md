---
title: Guide per Sviluppatori
description: Le nostre guide scritte dalla community trattano di tutto, dalla creazione di una mod e la configurazione del tuo ambiente di sviluppo, fino ad argomenti avanzati come rendering, reti, generazione di dati, e altro.
authors:
  - IMB11
  - its-miroma
  - itsmiir
authors-nogithub:
  - basil4088
---

Fabric è una toolchain di modding leggera per Minecraft: Java Edition, pensata per essere semplice e facile da usare. Permette agli sviluppatori di applicare modificazioni ("mod") al gioco vanilla, aggiungendo nuove funzionalità o modificando meccaniche esistenti.

Questa documentazione ti guiderà nel mondo del modding con Fabric, partendo dal [creare la tua prima mod](./getting-started/creating-a-project) e [configurare il tuo ambiente di sviluppo](./getting-started/setting-up), per arrivare ad argomenti avanzati come il [rendering](./rendering/basic-concepts), la [gestione in rete](./networking), la [generazione di dati](./data-generation/setup), e molto altro ancora.

Controlla la barra laterale per una lista delle pagine disponibili.

::: tip

Nel caso ti servisse in qualunque momento, è disponibile una mod completamente funzionante con tutto il codice di questa documentazione nella [cartella `/reference` su GitHub](https://github.com/FabricMC/fabric-docs/tree/main/reference/latest).

:::

## Prerequisiti {#prerequisites}

Prima d'iniziare a far modding con Fabric, hai bisogno di avere una comprensione generale dello sviluppo con Java, e di conoscere la Programmazione Orientata agli Oggetti (OOP) a grandi linee.

Ecco qualche risorsa utile per imparare e familiarizzare con Java e OOP:

- [W3: Java Tutorials](https://www.w3schools.com/java/)
- [Codecademy: Learn Java](https://www.codecademy.com/learn/learn-java)
- [W3: Java OOP](https://www.w3schools.com/java/java_oop.asp)
- [Medium: Introduction to OOP](https://medium.com/@Adekola_Olawale/beginners-guide-to-object-oriented-programming-a94601ea2fbd)

## Cosa mi offre Fabric? {#what-does-fabric-offer}

Il Progetto Fabric è centrato attorno a tre componenti principali:

- **Fabric Loader**: un loader di mod flessibile, indipendente dalla piattaforma, progettato principalmente per Minecraft: Java Edition
- **Fabric API**: un insieme complementare di API e di strumenti che gli sviluppatori di mod possono usare
- **Fabric Loom**: un plugin [Gradle](https://gradle.org/), che permette agli sviluppatori di sviluppare ed effettuare debug delle mod facilmente

### Cosa mi offre Fabric API? {#what-does-fabric-api-offer}

Fabric API fornisce un'ampia gamma di API che si basano sulla funzionalità vanilla, per permettere sviluppo più semplice o più avanzato.

Per esempio, fornisce nuovi hook, eventi, utilità come i transitive access widener, accesso a registry interne tra cui la registry di oggetti compostabili, e altro.
