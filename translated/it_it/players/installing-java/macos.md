---
title: Installare Java su macOS
description: Una guida passo per passo su come installare Java su macOS.
authors:
  - dexman545
  - ezfe
next: false
---

Questa guida ti spiegherà come installare Java 21 su macOS.

Il Launcher di Minecraft ha la sua versione di Java installata, quindi questa sezione è rilevante solo se vuoi usare l'installer `.jar` di Fabric, oppure se vuoi usare il `.jar` del Server di Minecraft.

## 1. Controlla Se Java È Già Installato {#1-check-if-java-is-already-installed}

Nel Terminale (che trovi in `/Applications/Utilities/Terminal.app`) scrivi ciò che segue, e premi <kbd>Invio</kbd>:

```sh
$(/usr/libexec/java_home -v 21)/bin/java --version
```

Dovresti vedere qualcosa del genere:

```text:no-line-numbers
openjdk 21.0.9 2025-10-21 LTS
OpenJDK Runtime Environment Temurin-21.0.9+10 (build 21.0.9+10-LTS)
OpenJDK 64-Bit Server VM Temurin-21.0.9+10 (build 21.0.9+10-LTS, mixed mode, sharing)
```

Prendi nota del numero della versione: nell'esempio sopra è `21.0.9`.

::: warning

Per usare Minecraft 1.21.11, ti servirà almeno Java 21 installato.

Se questo comando mostra una versione inferiore a 21, allora dovrai aggiornare la versione di Java già esistente; continua a seguire questa pagina.

:::

## 2. Scaricare e Installare Java 21 {#2-downloading-and-installing-java}

Consigliamo di usare la [build di OpenJDK 21 da Adoptium](https://adoptium.net/temurin/releases?version=21&os=mac&arch=any&mode=filter):

![Pagina da cui si scarica Temurin Java](/assets/players/installing-java/macos-download-java.png)

Assicurati di aver selezionato la versione "21 - LTS", e scegli il formato d'installazione `.PKG`.
Dovresti anche far attenzione all'architettura, a seconda del processore del tuo sistema:

- Se hai un processore Apple della serie M, scegli `aarch64` (predefinito)
- Se hai un processore Intel, scegli `x64`
- Puoi seguire queste [istruzioni per determinare il processore del tuo Mac](https://support.apple.com/en-us/116943)

Dopo aver scaricato l'installer `.pkg`, aprilo e segui le istruzioni:

![Installer di Temurin Java](/assets/players/installing-java/macos-installer.png)

Dovrai inserire la tua password di amministratore per completare l'installazione:

![Finestra di autenticazione di macOS](/assets/players/installing-java/macos-password-prompt.png)

### Usando Homebrew {#using-homebrew}

Se hai già installato [Homebrew](https://brew.sh), puoi alternativamente usare `brew` per installare Java 21:

```sh
brew install --cask temurin@21
```

## 3. Verifica che Java 21 Sia Installato {#3-verify-that-java-is-installed}

Quando l'installazione è stata completata, puoi verificare che Java 21 sia attivo aprendo di nuovo il Terminale e scrivendo `$(/usr/libexec/java_home -v 21)/bin/java --version`.

Se il comando funziona correttamente, dovresti vedere qualcosa del genere:

```text:no-line-numbers
openjdk 21.0.9 2025-10-21 LTS
OpenJDK Runtime Environment Temurin-21.0.9+10 (build 21.0.9+10-LTS)
OpenJDK 64-Bit Server VM Temurin-21.0.9+10 (build 21.0.9+10-LTS, mixed mode, sharing)
```

Se incontri dei problemi, sentiti libero di chiedere aiuto nel server [Fabric Discord](https://discord.fabricmc.net/) nel canale `#player-support`.
