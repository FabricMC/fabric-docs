---
title: Impostare un Ambiente di Sviluppo
description: Una guida passo per passo su come impostare un ambiente di sviluppo per creare mod utilizzando Fabric.
authors:
  - IMB11
  - andrew6rant
  - SolidBlock-cn
  - modmuss50
  - daomephsta
  - liach
  - telepathicgrunt
  - 2xsaiko
  - natanfudge
  - mkpoli
  - falseresync
  - asiekierka
---

<!-- No GitHub profiles for: siglong -->

# Impostare un Ambiente di Sviluppo

Per iniziare a sviluppare mod con Fabric, avrai bisogno di impostare un ambiente di sviluppo utilizzando IntelliJ IDEA.

## Installare JDK 17

Per sviluppare mod per Minecraft 1.20.4, avrai bisogno del JDK 17.

Se ti serve aiuto nell'installare Java, puoi fare riferimento alle varie guide per installazione di Java nella [sezione guide per il giocatore.](../../players/index.md)

## Installare IntelliJ IDEA

:::info
Ovviamente puoi utilizzare altri IDE, come Eclipse o Visual Studio Code, ma la maggior parte delle pagine su questo sito di documentazione supporrà che stai utilizzando IntelliJ IDEA - dovresti fare riferimento alla documentazione del tuo IDE se ne stai utilizzando un altro.
:::

Se non hai IntelliJ IDEA installato, puoi scaricarlo dal [sito ufficiale](https://www.jetbrains.com/idea/download/) - segui i passaggi per l'installazione sul tuo sistema operativo.

L'edizione Community di IntelliJ IDEA è gratuita e open source, ed è la versione consigliata per moddare con Fabric.

Potresti dover scorrere giù per trovare il link per scaricare l'edizione Community - ha il seguente aspetto:

![Prompt per Scaricare l'Edizione Communiti di IDEA](/assets/develop/getting-started/idea-community.png)

## Installare Plugin IDEA

Anche se questi plugin non sono strettamente necessari, essi rendono il modding con Fabric molto più semplice - dovresti installarli.

### Minecraft Development

Il plugin Minecraft Development fornisce supporto per il modding con Fabric, ed è il plugin più importante da installare.

Puoi installarlo aprendo IntelliJ IDEA, e poi navigando a `File > Impostazioni > Plugin > Scheda Marketplace` - cerca `Minecraft Development` nella barra di ricerca, e poi clicca il pulsante `Installa`.

In alternativa, puoi scaricarlo dalla [pagina del plugin (in inglese)](https://plugins.jetbrains.com/plugin/8327-minecraft-development) e poi installarlo navigando a `File > Impostazioni > Plugin > Installa Plugin Dal Disco`.
