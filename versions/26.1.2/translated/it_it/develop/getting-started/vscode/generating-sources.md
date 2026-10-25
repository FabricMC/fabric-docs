---
title: Generare le Sorgenti in VS Code
description: Una guida alla generazione delle risorse di Minecraft in Visual Studio Code.
authors:
  - dicedpixels
prev:
  text: Avviare il Gioco in VS Code
  link: ./launching-the-game
next:
  text: Consigli e Trucchetti per VS Code
  link: ./tips-and-tricks
---

La toolchain Fabric ti permette di accedere al codice sorgente di Minecraft generandolo localmente, e puoi usare Visual Studio Code per navigarci comodamente. Per generare le sorgenti, bisogna eseguire l'attività Gradle `genSources`.

Questo si può fare dalla vista Gradle, eseguendo l'attività `genSources` in **Tasks** > **`fabric`**:
![Attività genSources nella vista Gradle](/assets/develop/getting-started/vscode/gradle-gensources.png)

Oppure puoi eseguire il comando dal terminale:

```sh:no-line-numbers
./gradlew genSources
```

![Attività genSources nel terminale](/assets/develop/getting-started/vscode/terminal-gensources.png)
