---
title: Installare Java su Windows
description: Una guida passo per passo su come installare Java su Windows.
authors:
  - IMB11

search: false
---

# Installare Java su Windows

Questa guida ti spiegherà come installare Java 17 su Windows.

Il Launcher di Minecraft ha la sua versione di Java installata, quindi questa sezione è rilevante solo se vuoi usare l'installer `.jar` di Fabric, oppure se vuoi usare il `.jar` del Server di Minecraft.

## 1. Controlla se Java è già installato

Per controllare se Java è già installato devi prima aprire il prompt dei comandi.

Puoi farlo premendo <kbd>Win</kbd> + <kbd>R</kbd> e scrivendo `cmd.exe` nel riquadro che appare.

![Dialogo Esegui su Windows che mostra "cmd.exe" scritto nella barra](/assets/players/installing-java/windows-run-dialog.png)

Una volta aperto il prompt dei comandi, scrivi `java -version` e premi <kbd>Invio</kbd>.

Se il comando funziona correttamente, vedrai qualcosa come questo. Se il comando ha fallito, procedi con il prossimo passaggio.

![Il prompt dei comandi con scritto "java -version"](/assets/players/installing-java/windows-java-version.png)

:::warning
Per usare la maggior parte delle versioni moderne di Minecraft, ti servirà almeno Java 17 installato. Se questo comando mostra una versione inferiore a 17, allora dovrai aggiornare la versione di Java già esistente.
:::

## 2. Scarica l'Installer per Java 17

Per installare Java 17, dovrai scaricare l'installer da [Adoptium](https://adoptium.net/en-GB/temurin/releases/?os=windows&package=jdk&version=17).

Dovrai scaricare la versione `Windows Installer (.msi)`:

![La pagina di download di Adoptium con Windows Installer (.msi) evidenziato](/assets/players/installing-java/windows-download-java.png)

Dovresti scegliere `x86` se il tuo sistema operativo è a 32-bit, oppure `x64` se tuo sistema operativo è a 64-bit.

La maggior parte dei computer moderni ha un sistema operativo a 64-bit. Se non sei sicuro, prova a usare il download 64-bit.

## 3. Esegui l'Installer!

Segui le istruzioni per installare Java 17. Quando arrivi a questa pagina, dovresti selezionare "L'intera funzionalità verrà installata sul disco rigido locale" per le seguenti funzionalità:

- `Imposta JAVA_HOME come variabile d'ambiente` - Questo verrà aggiunto al tuo PATH.
- `JavaSoft (Oracle) registry keys`

![Installer Java 17 con "Set JAVA_HOME variable" e "JavaSoft (Oracle) registry keys" evidenziati](/assets/players/installing-java/windows-wizard-screenshot.png)

Quando hai finito, puoi cliccare su `Avanti` e continuare con l'installazione.

## 4. Verifica che Java 17 Sia Installato

Quando l'installazione è stata completata, puoi verificare che Java 17 è installato aprendo il prompt dei comandi e scrivendo `java -version`.

Se il comando funziona correttamente, vedrai qualcosa simile a quello mostrato prima, dove è mostrata la versione di Java:

![Il prompt dei comandi con scritto "java -version"](/assets/players/installing-java/windows-java-version.png)

---

Se incontri dei problemi, sentiti libero di chiedere aiuto nel server [Fabric Discord](https://discord.gg/v6v4pMv) nel canale `#player-support`.
