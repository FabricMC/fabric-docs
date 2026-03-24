---
title: Impostare VS Code
description: Una guida passo per passo su come impostare Visual Studio Code per creare mod usando Fabric.
authors:
  - dicedpixels
prev:
  text: Impostare il Tuo IDE
  link: ../setting-up
next:
  text: Aprire un progetto in VS Code
  link: ./opening-a-project
---

<!---->

:::warning IMPORTANTE

Anche se è tecnicamente possibile sviluppare mod in Visual Studio Code, lo sconsigliamo.
Dovresti usare [IntelliJ IDEA](../intellij-idea/setting-up), che fornisce tooling dedicato per Java, funzioni avanzate, e plugin utili creati dalla comunità, tra cui **Minecraft Development**.

:::

:::info PREREQUISITI

Assicurati di aver già [installato un JDK](../setting-up#install-jdk-21).

:::

## Installazione {#installation}

Puoi scaricare Visual Studio Code da [code.visualstudio.com](https://code.visualstudio.com/) o tramite il tuo gestore di pacchetti preferito.

![Pagina da cui scaricare Visual Studio Code](/assets/develop/getting-started/vscode/download.png)

## Prerequisiti {#prerequisites}

Visual Studio Code non include il supporto del linguaggio Java appena installato. Tuttavia, Microsoft redige un pacchetto di estensioni conveniente, perché contiene tutto il necessario per attivare il supporto di Java.

Puoi installare questo pacchetto dal [Marketplace di Visual Studio](https://marketplace.visualstudio.com/items?itemName=vscjava.vscode-java-pack).

![Pacchetto di estensioni per Java](/assets/develop/getting-started/vscode/extension.png)

Oppure, se sei in Visual Studio Code, dalla vista Extensions.

![Pacchetto di estensioni per Java, nella vista Extensions di VS Code](/assets/develop/getting-started/vscode/extension-view.png)

L'estensione **Language Support for Java** mostrerà all'avvio una schermata per configurare un JDK. Se non l'hai ancora fatto, configuralo.
