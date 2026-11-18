---
title: Installare Fabric su macOS
description: Una guida passo per passo su come installare Fabric su macOS.
authors:
  - Benonardo
  - ezfe
  - IMB11
  - modmuss50
next: false
---

<!---->

:::info PREREQUISITI

Potresti dover [installare Java](../installing-java/macos) prima di eseguire il `.jar`.

:::

<!-- #region common -->

## 1. Scarica l'Installer di Fabric {#1-download-the-fabric-installer}

Scarica la versione `.jar` dell'Installer di Fabric dal [sito di Fabric](https://fabricmc.net/use/), cliccando su `Download installer (Universal/.JAR)`.

## 2. Esegui l'Installer di Fabric {#2-run-the-fabric-installer}

Chiudi Minecraft e il Launcher di Minecraft prima di avviare l'installer.

::: tip

Potresti ricevere un avviso da Apple che il `.jar` non è verificato. Per raggirarlo, apri le Impostazioni di Sistema > Privacy e Sicurezza, poi clicca `Apri comunque`. Conferma inserendo la tua password di amministratore se richiesta.

![Impostazioni di Sistema macOS](/assets/players/installing-fabric/macos-settings.png)

:::

Una volta aperto l'installer, dovresti vedere una schermata come questa:

![Fabric Installer con "Installa" evidenziato](/assets/players/installing-fabric/installer-screen.png)

<!-- #endregion common -->

Scegli la versione di Minecraft desiderata e clicca `Installa`. Assicurati che `Crea Profilo` sia selezionato.

### Installa tramite Homebrew {#installing-via-homebrew}

Se hai già installato [Homebrew](https://brew.sh), puoi alternativamente usare `brew` per installare l'Installer di Fabric:

```sh
brew install fabric-installer
```

## 3. Completa la Configurazione {#3-finish-setup}

Appena l'installazione è completata, apri il Launcher di Minecraft. Poi seleziona il profilo Fabric dal menu a tendina delle versioni, e clicca Gioca.

![Launcher di Minecraft con il profilo Fabric selezionato](/assets/players/installing-fabric/launcher-screen.png)

Ora puoi aggiungere mod al gioco. Da' un occhiata alla guida [Trovare Mod Affidabili](./finding-mods) per maggiori informazioni.

Se incontri dei problemi, sentiti libero di chiedere aiuto nel server [Fabric Discord](https://discord.fabricmc.net/) nel canale `#player-support`.
