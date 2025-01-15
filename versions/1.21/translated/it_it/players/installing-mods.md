---
title: Installare le Mod
description: Una guida passo per passo su come installare mod per Fabric.
authors:
  - IMB11
---

# Installare le Mod {#installing-mods}

Questa guida ti aiuterà a installare mod per Fabric usando il Launcher di Minecraft ufficiale.

Per launcher di terze parti, dovresti consultare la loro documentazione.

## 1. Scarica la Mod {#1-download-the-mod}

:::warning
Dovresti solo scaricare mod da fonti di cui ti fidi. Per maggiori informazioni su come trovare le mod, vedi la guida [Trovare Mod Affidabili](./finding-mods).
:::

La maggior parte delle mod richiede anche l'API di Fabric, che può essere scaricata da [Modrinth](https://modrinth.com/mod/fabric-api) o da [CurseForge](https://curseforge.com/minecraft/mc-mods/fabric-api).

Quando scarichi delle mod, assicurati che:

- Funzionino nella versione di Minecraft su cui vuoi giocare. Una mod che funziona sulla versione 1.20, per esempio, potrebbe non funzionare sulla versione 1.20.2.
- Siano per Fabric e non per un altro loader di mod.
- Inoltre, che siano per la corretta edizione di Minecraft (Java Edition).

## 2. Sposta le Mod nella Cartella `mods` {#2-move-the-mod-to-the-mods-folder}

La cartella mods può essere trovata nelle posizioni seguenti per ciascun sistema operativo.

Puoi solitamente incollare questi percorsi nella barra degli indirizzi del tuo gestore di file per navigare velocemente alla cartella.

::: code-group

```:no-line-numbers [Windows]
%appdata%\.minecraft\mods
```

```:no-line-numbers [macOS]
~/Library/Application Support/minecraft/mods
```

```:no-line-numbers [Linux]
~/.minecraft/mods
```

:::

Una volta che hai trovato la cartella `mods`, puoi muovere i file `.jar` della mod in essa.

![Mod installate nella cartella mods](/assets/players/installing-mods.png)

## 3. Hai Finito! {#3-you-re-done}

Una volta che hai spostato le mod nella cartella `mods`, puoi aprire il Launcher di Minecraft e selezionare il profilo Fabric dal menu a tendina nell'angolo in basso a sinistra e premere gioca!

![Launcher di Minecraft con il profilo Fabric selezionato](/assets/players/installing-fabric/launcher-screen.png)

## Risoluzione dei Problemi {#troubleshooting}

Se incontri qualsiasi problema nel seguire questa guida, puoi chiedere aiuto nel [Discord di Fabric](https://discord.gg/v6v4pMv) nel canale `#player-support`.

Puoi anche tentare di risolvere il problema da solo leggendo le seguenti pagine di risoluzione dei problemi:

- [Segnalazioni dei Crash](./troubleshooting/crash-reports)
- [Caricare i Log](./troubleshooting/uploading-logs)
