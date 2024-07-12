---
title: Impostare un Ambiente di Sviluppo
description: Una guida passo per passo su come impostare un ambiente di sviluppo per creare mod usando Fabric.
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
authors-nogithub:
  - siglong
---

# Impostare un Ambiente di Sviluppo {#setting-up-a-development-environment}

Per iniziare a sviluppare mod con Fabric, avrai bisogno d'impostare un ambiente di sviluppo usando IntelliJ IDEA.

## Installare JDK 21 {#installing-jdk-21}

Per sviluppare mod per Minecraft 1.21, avrai bisogno del JDK 21.

Se ti serve aiuto nell'installare Java, puoi fare riferimento alle varie guide per installazione di Java nella [sezione guide per il giocatore](../../players/index).

## Installare IntelliJ IDEA {#installing-intellij-idea}

:::info
Ovviamente puoi usare altri IDE, come Eclipse o Visual Studio Code, ma la maggior parte delle pagine su questo sito di documentazione supporrà che stai usando IntelliJ IDEA - dovresti fare riferimento alla documentazione del tuo IDE se ne stai usando un altro.
:::

Se non hai IntelliJ IDEA installato, puoi scaricarlo dal [sito ufficiale](https://www.jetbrains.com/idea/download/) - segui i passaggi per l'installazione sul tuo sistema operativo.

L'edizione Community di IntelliJ IDEA è gratuita e open-source, ed è la versione consigliata per il modding con Fabric.

Potresti dover scorrere giù per trovare il link per scaricare l'edizione Community - ha il seguente aspetto:

![Prompt per Scaricare l'Edizione Community di IDEA](/assets/develop/getting-started/idea-community.png)

## Installare i Plugin IDEA {#installing-idea-plugins}

Anche se questi plugin non sono strettamente necessari, essi rendono il modding con Fabric molto più semplice - dovresti installarli.

### Minecraft Development {#minecraft-development}

Il plugin Minecraft Development fornisce supporto per il modding con Fabric, ed è il plugin più importante da installare.

Puoi installarlo aprendo IntelliJ IDEA, e poi navigando a `File > Impostazioni > Plugin > Scheda Marketplace` - cerca `Minecraft Development` nella barra di ricerca, e poi clicca il pulsante `Installa`.

In alternativa, puoi scaricarlo dalla [pagina del plugin](https://plugins.jetbrains.com/plugin/8327-minecraft-development) e poi installarlo navigando a `File > Impostazioni > Plugin > Installa Plugin Dal Disco`.
