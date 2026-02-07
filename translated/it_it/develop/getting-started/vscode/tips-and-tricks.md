---
title: Consigli e Trucchetti per VS Code
description: Informazioni utili e pratiche per renderti il lavoro più piacevole.
authors:
  - dicedpixels
prev:
  text: Generare le Sorgenti in VS Code
  link: ./generating-sources
next: false
---

È importante sapere come navigare le fonti generate, così da poter fare debugging e comprendere il funzionamento di Minecraft dietro le quinte. Qui presentiamo alcune prassi comuni nell'IDE.

## Cercare una classe di Minecraft {#searching-for-a-minecraft-class}

Quando avrai generato le sorgenti, dovresti poter cercare e visualizzare le classi di Minecraft.

### Visualizzare le definizioni delle classi {#viewing-class-definitions}

**Quick Open** (<kbd>Ctrl</kbd>+<kbd>P</kbd>): Scrivi `#` seguito dal nome della classe (es. `#Identifier`).

![Finestra Quick Open](/assets/develop/getting-started/vscode/quick-open.png)

**Go to Definition** (<kbd>F12</kbd>): Dal codice sorgente, naviga alla definizione di una classe facendo <kbd>Ctrl</kbd> + clic sul nome, o cliccando col pulsante destro e selezionando "Go to Definition".

![Go to Definition](/assets/develop/getting-started/vscode/go-to-definition.png)

### Trovare i riferimenti {#finding-references}

Puoi elencare gli utilizzi e i riferimenti a una classe cliccando con il pulsante destro sul nome di una classe e selezionando **Find All References**.

![Find All References](/assets/develop/getting-started/vscode/find-all-references.png)

::: info

Se le funzioni citate non funzionassero come dovrebbero, probabilmente le sorgenti non sono allegate correttamente. Di solito basta ripulire la cache dell'ambiente per risolvere il problema.

- Clicca il pulsante **Show Java Status Menu** nella barra di stato.

![Mostra lo stato di Java](/assets/develop/getting-started/vscode/java-ready.png)

- Nel menu appena aperto, seleziona **Clean Workspace Cache...** e conferma l'operazione.

![Ripulisci ambiente di lavoro](/assets/develop/getting-started/vscode/clear-workspace.png)

- Chiudi e riapri il progetto.

:::

## Mostrare il bytecode {#viewing-bytecode}

Visualizzare il bytecode è necessario se si scrivono mixin. Tuttavia, a Visual Studio Code manca il supporto per la visualizzazione di bytecode, e le poche estensioni che lo introducono potrebbero non funzionare.

In questo caso, potresti usare lo strumento `javap` incluso con Java per visualizzare il bytecode.

- **Trova il percorso del JAR di Minecraft:**

  Apri la vista Explorer, espandi la sezione **Java Projects**. Espandi il nodo **Reference Libraries** nell'albero del progetto e trova un JAR che abbia `minecraft-` nel nome. Clicca con il pulsante destro sul JAR e copiane il percorso completo.

  Potrebbe essere qualcosa del genere:

  ```:no-line-numbers
  C:/project/.gradle/loom-cache/minecraftMaven/net/minecraft/minecraft-merged-503b555a3d/1.21.8-net.fabricmc.yarn.1_21_8.1.21.8+build.1-v2/minecraft-merged-503b555a3d-1.21.8-net.fabricmc.yarn.1_21_8.1.21.8+build.1-v2.jar
  ```

![Copia il percorso](/assets/develop/getting-started/vscode/copy-path.png)

- **Esegui `javap`:**

  Puoi ora eseguire `javap` passandoci il percorso appena copiato come `cp` ("class path", percorso della classe) e il nome completo qualificato della classe come ultimo argomento.

  ```sh
  javap -cp C:/project/.gradle/loom-cache/minecraftMaven/net/minecraft/minecraft-merged-503b555a3d/1.21.8-net.fabricmc.yarn.1_21_8.1.21.8+build.1-v2/minecraft-merged-503b555a3d-1.21.8-net.fabricmc.yarn.1_21_8.1.21.8+build.1-v2.jar -c -private net.minecraft.util.Identifier
  ```

  Questo riporterà il bytecode nell'output del terminale.

![javap](/assets/develop/getting-started/vscode/javap.png)
