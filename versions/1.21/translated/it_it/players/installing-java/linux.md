---
title: Installare Java su Linux
description: Una guida passo per passo su come installare Java su Linux.
authors:
  - IMB11
---

# Installare Java su Linux {#installing-java-on-linux}

Questa guida ti spiegherà come installare Java 21 su Linux.

## 1. Controlla Se Java È Già Installato {#1-check-if-java-is-already-installed}

Apri un terminale, scrivi `java -version` e premi <kbd>Invio</kbd>.

![Terminale con scritto "java -version"](/assets/players/installing-java/linux-java-version.png)

:::warning
Per usare Minecraft 1.21, ti servirà almeno Java 21 installato. Se questo comando mostra una versione inferiore a 21, allora dovrai aggiornare la versione di Java già esistente.
:::

## 2. Scaricare e Installare Java 21 {#2-downloading-and-installing-java}

Raccomandiamo l'uso di OpenJDK 21, che è disponibile per la maggior parte delle distribuzioni Linux.

### Arch Linux {#arch-linux}

:::info
Per maggiori informazioni su come installare Java su Arch Linux, vedi [la Wiki di Arch Linux](https://wiki.archlinux.org/title/Java).
:::

Puoi installare la JRE più recente dalle repository ufficiali:

```sh
sudo pacman -S jre-openjdk
```

Se stai eseguendo un server che non ha bisogno di un'interfaccia grafica, puoi installare la versione headless:

```sh
sudo pacman -S jre-openjdk-headless
```

Se pensi di sviluppare delle mod, ti servirà invece il JDK:

```sh
sudo pacman -S jdk-openjdk
```

### Debian/Ubuntu {#debian-ubuntu}

Puoi installare Java 21 usando `apt` con i comandi seguenti:

```sh
sudo apt update
sudo apt install openjdk-21-jdk
```

### Fedora {#fedora}

Puoi installare Java 21 usando `dnf` con i comandi seguenti:

```sh
sudo dnf install java-21-openjdk
```

Se non hai bisogno di un'interfaccia grafica, puoi installare la versione headless:

```sh
sudo dnf install java-21-openjdk-headless
```

Se pensi di sviluppare delle mod, ti servirà invece il JDK:

```sh
sudo dnf install java-21-openjdk-devel
```

### Altre Distribuzioni Linux {#other-linux-distributions}

Se la tua distribuzione non è fra quelle elencate sopra, puoi scaricare il JRE più recente da [Adoptium](https://adoptium.net/temurin/)

Dovresti fare riferimento a una guida alternativa per la tua distribuzione se vuoi sviluppare delle mod.

## 3. Verifica che Java 21 Sia Installato {#3-verify-that-java-is-installed}

Quando l'installazione è stata completata, puoi verificare che Java 21 sia installato aprendo il terminale e scrivendo `java -version`.

Se il comando funziona correttamente, vedrai qualcosa simile a quello mostrato prima, dove è mostrata la versione di Java:

![Terminale con scritto "java -version"](/assets/players/installing-java/linux-java-version.png)
