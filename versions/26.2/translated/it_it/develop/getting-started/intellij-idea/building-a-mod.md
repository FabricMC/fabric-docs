---
title: Costruire una mod in IntelliJ IDEA
description: Impara come usare IntelliJ IDEA per costruire una mod per Minecraft che possa essere condivisa o testata in un ambiente di produzione.
authors:
  - cassiancc
  - cputnam-a11y
  - gdude2002
  - Scotsguy
prev:
  text: Generare le Sorgenti in IntelliJ IDEA
  link: ./generating-sources
next:
  text: Consigli e Trucchetti per IntelliJ IDEA
  link: ./tips-and-tricks
---

In IntelliJ IDEA, apri la scheda Gradle sulla destra ed esegui l'attività `build` nell'elenco. I JAR dovrebbero apparire nella cartella `build/libs` nel tuo progetto. Fuori dall'ambiente di sviluppo usa il file JAR con il nome più breve.

![La barra laterale di IntelliJ IDEA che mostra l'attività build evidenziata](/assets/develop/getting-started/build-idea.png)

![La cartella build/libs con il file corretto evidenziato](/assets/develop/getting-started/build-libs.png)

## Installare e condividere {#installing-and-sharing}

Dopodiché la mod potrà essere [installata come il solito](../../../players/installing-mods), o caricata su siti di gestione di mod affidabili come [CurseForge](https://www.curseforge.com/minecraft) o [Modrinth](https://modrinth.com/discover/mods).
