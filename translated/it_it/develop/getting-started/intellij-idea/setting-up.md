---
title: Impostare IntelliJ IDEA
description: Una guida passo per passo su come impostare IntelliJ IDEA per creare mod usando Fabric.
authors:
  - 2xsaiko
  - Andrew6rant
  - asiekierka
  - Daomephsta
  - dicedpixels
  - falseresync
  - IMB11
  - liach
  - mkpoli
  - modmuss50
  - natanfudge
  - SolidBlock-cn
  - TelepathicGrunt
authors-nogithub:
  - siglong
prev:
  text: Impostare il Tuo IDE
  link: ../setting-up
next:
  text: Aprire un progetto in IntelliJ IDEA
  link: ./opening-a-project
---

<!---->

:::info PREREQUISITI

Assicurati di aver già [installato un JDK](../setting-up#install-jdk-21).

:::

## Installare IntelliJ IDEA {#installing-intellij-idea}

Se non hai IntelliJ IDEA installato, puoi scaricarlo dal [sito ufficiale](https://www.jetbrains.com/idea/download/) - segui i passaggi per l'installazione sul tuo sistema operativo.

![Prompt per scaricare IntelliJ IDEA](/assets/develop/getting-started/idea-download.png)

## Installare il plugin "Minecraft Development" {#installing-idea-plugins}

Il plugin Minecraft Development fornisce supporto per il modding con Fabric, ed è il plugin più importante da installare.

::: tip

Anche se questo plugin non è strettamente necessario, esso rende il modding con Fabric molto più semplice - dovresti installarlo.

:::

Puoi installarlo aprendo IntelliJ IDEA, e poi navigando a **File** > **Impostazioni** > **Plugin** > Scheda **Marketplace**, poi inserisci `Minecraft Development` nella barra di ricerca, e clicca il pulsante **Installa**.

In alternativa, puoi scaricarlo dalla [pagina del plugin](https://plugins.jetbrains.com/plugin/8327-minecraft-development) e poi installarlo navigando a **File** > **Impostazioni** > **Plugin** > **Installa Plugin dal Disco**.

### Riguardo alla creazione di un progetto {#about-creating-a-project}

È vero che quel plugin permette di creare un progetto, ma questo non è consigliato perché i modelli che usa sono spesso obsoleti. Consigliamo invece di seguire la pagina [Creare un progetto](../creating-a-project) per usare il generatore di mod modello di Fabric.
