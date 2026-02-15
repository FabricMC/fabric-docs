---
title: Generare le Sorgenti in IntelliJ IDEA
description: Una guida alla generazione delle risorse di Minecraft in IntelliJ IDEA.
authors:
  - dicedpixels
prev:
  text: Avviare il Gioco in IntelliJ IDEA
  link: ./launching-the-game
next:
  text: Consigli e Trucchetti per IntelliJ IDEA
  link: ./tips-and-tricks
---

La toolchain Fabric ti permette di accedere al codice sorgente di Minecraft generandolo localmente, e puoi usare IntelliJ IDEA per navigarci comodamente. Per generare le sorgenti, bisogna eseguire l'attività Gradle `genSources`.

Questo si può fare dal pannello Gradle, eseguendo l'attività `genSources` in **Tasks** > **`fabric`**:
![Attività genSources nel pannello Gradle](/assets/develop/getting-started/intellij/gradle-gensources.png)

Oppure puoi eseguire il comando dal terminale:

```sh:no-line-numbers
./gradlew genSources
```

![Attività genSources nel terminale](/assets/develop/getting-started/intellij/terminal-gensources.png)

## Allegare le sorgenti {#attaching-sources}

Per IntelliJ serve un passaggio ulteriore, ovvero allegare le sorgenti generate al progetto.

Per far ciò, apri qualsiasi classe di Minecraft. Puoi fare <kbd>Ctrl</kbd> + Clic per raggiungere la definizione, che mostrerà una classe, oppure usa "Search everywhere" per aprirne una.

Per esempio, apriamo `MinecraftServer.class`. Dovresti ora vedere un banner blu in cima, con un link "**Choose Sources...**".

![Pulsante Choose Sources](/assets/develop/getting-started/intellij/choose-sources.png)

Clicca su "**Choose Sources...**" per aprire una finestra di selezione file. La finestra si apre in automatico nella posizione dove sono generate le fonti.

Seleziona il file che finisce per **`-sources`** e premi **Open** per confermare la selezione.

![Finestra per scegliere le sorgenti](/assets/develop/getting-started/intellij/choose-sources-dialog.png)

Dovresti ora poter cercare i riferimenti. Se stai usando dei mapping che contengono Javadoc, per esempio [Parchment](https://parchmentmc.org/) (per i mapping di Mojang) o Yarn, dovresti anche vedere i Javadoc.

![Commenti Javadoc nelle sorgenti](/assets/develop/getting-started/intellij/javadoc.png)
