---
title: Caricare i Log
description: Come caricare i log per la risoluzione dei problemi.
authors:
  - IMB11
---

# Caricare i Log {#uploading-logs}

Quando stai tentando di risolvere problemi, spesso è necessario fornire i log per aiutare a identificare la causa del problema.

## Perché Dovrei Caricare i Log? {#why-should-i-upload-logs}

Caricare i log permette agli altri di aiutarti a risolvere i tuoi problemi molto più velocemente che non semplicemente incollare i log in una chat o in un post su un forum. Ti permette anche di condividere i tuoi log con altri senza doverli copiare e incollare.

Alcuni siti forniscono anche colorazione della sintassi per i log, che li rende più facili da leggere, e potrebbero censurare informazioni riservate, come il tuo nome utente, o informazioni sul sistema.

## Segnalazioni dei Crash {#crash-reports}

Le segnalazioni dei crash sono generate automaticamente quando il gioco subisce un crash. Contengono soltanto informazioni sul crash e non i log reali del gioco. Esse sono posizionate nella cartella `crash-reports` nella directory del gioco.

Per maggiori informazioni sulle segnalazioni dei crash, vedi [Segnalazioni dei Crash](./crash-reports).

## Localizzare i Log {#locating-logs}

Questa guida ricopre il Launcher di Minecraft ufficiale (anche comunemente noto come il "launcher vanilla") - per launcher di terze parti, dovresti consultare la loro documentazione.

I log sono posizionati nella cartella `logs` della directory del gioco, la directory del gioco può essere trovata nelle seguenti posizioni a seconda del tuo sistema operativo:

::: code-group

```:no-line-numbers [Windows]
%appdata%\.minecraft
```

```:no-line-numbers [macOS]
~/Library/Application Support/minecraft
```

```:no-line-numbers [Linux]
~/.minecraft
```

:::

Il log più recente si chiama `latest.log`, e log precedenti usano lo schema di denominazione `aaaa-mm-gg_numero.log.gz`.

## Caricare i Log Online {#uploading-logs-online}

I log possono essere caricati su vari servizi, come:

- [Pastebin](https://pastebin.com/)
- [Gist di GitHub](https://gist.github.com/)
- [mclo.gs](https://mclo.gs/)
