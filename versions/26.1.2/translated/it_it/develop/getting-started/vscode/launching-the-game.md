---
title: Avviare il Gioco in VS Code
description: Impara come avviare un'istanza di Minecraft da Visual Studio Code.
authors:
  - dicedpixels
prev:
  text: Aprire un progetto in VS Code
  link: ./opening-a-project
next:
  text: Generare le Sorgenti in VS Code
  link: ./generating-sources
---

La toolchain di Fabric si integra con Visual Studio Code per fornire un modo comodo di eseguire un'istanza del gioco su cui effettuare test e debugging della tua mod.

## Generare gli obiettivi di avvio {#generating-launch-targets}

Per eseguire il gioco attivando il debugging, dovrai generare gli obiettivi di avvio eseguendo l'attività Gradle `vscode`.

Questo si può fare dalla vista Gradle all'interno di Visual Studio Code: aprila e naviga all'attività `vscode` in **Tasks** > **`ide`**. Cliccaci due volte o usa il pulsante **Run Task** per eseguire l'attività.

![Attività vscode nella vista Gradle](/assets/develop/getting-started/vscode/gradle-vscode.png)

In alternativa si può usare direttamente il terminale: aprine uno da **Terminal** > **New Terminal** ed esegui:

```sh:no-line-numbers
./gradlew vscode
```

![Attività vscode nel terminale](/assets/develop/getting-started/vscode/terminal-vscode.png)

### Usare gli obiettivi di avvio {#using-launch-targets}

Una volta generati, puoi usare gli obiettivi di avvio aprendo la vista **Run and Debug**, selezionando l'obiettivo desiderato e premendo il pulsante **Start Debugging** (<kbd>F5</kbd>).

![Obiettivi di avvio](/assets/develop/getting-started/vscode/launch-targets.png)
