---
title: Installare Java su Linux
description: Una guida passo passo su come installare Java su Linux.
authors:
  - IMB11
---

# Installare Java su Linux

Questa guida ti spiegherà come installare Java 17 su Linux.

## 1. Controlla se Java sia già installato.

Apri un terminale, e scrivi `java -version` e premi <kbd>Invio</kbd>.

![Terminale con scritto "java -version"](/assets/players/installing-java/linux-java-version.png)

:::warning
Per usare la maggior parte delle versioni moderne di Minecraft, ti servirà almeno Java 17 installato. Se il comando di prima mostra una versione inferiore a 17, allora dovrai aggiornare la versione di Java già esistente.
:::

## 2. Scaricare ed Installare Java 17

Raccomandiamo l'utilizzo di OpenJDK 17, che è disponibile per la maggior parte delle distribuzioni Linux.

### Arch Linux

:::info
Per maggior informazione su come installare Java su Arch Linux, vedi [Arch Linux Wiki](https://wiki.archlinux.org/title/Java).
:::

Puoi installare la più recente JRE dalle repositories ufficiali:

```bash
sudo pacman -S jre-openjdk
```

Se stai avviando un server che non ha bisogno di un'interfaccia grafica, puoi anzi installare la versione headless:

```bash
sudo pacman -S jre-openjdk-headless
```

Se pensi di sviluppare delle mod, avari bisogno del JDK invece:

```bash
sudo pacman -S jdk-openjdk
```

### Debian/Ubuntu

Puoi installare Java 17 usando `apt` con i comandi seguenti:

```bash
sudo apt update
sudo apt install openjdk-17-jdk
```

### Fedora

Puoi installare Java 17 usando `dnf` con i comandi seguenti:

```bash
sudo dnf install java-17-openjdk
```

Se non hai bisogno di un'interfaccia grafica, puoi anzi installare la versione headless:

```bash
sudo dnf install java-17-openjdk-headless
```

Se pensi di sviluppare delle mod, avari bisogno del JDK invece:

```bash
sudo dnf install java-17-openjdk-devel
```

### Altre distribuzioni Linux

Se la tua distribuzione non è fra quelle elencate sopra, puoi scaricare il JRE più recente da [Adoptium](https://adoptium.net/temurin)

Dovresti fare riferimento a una guida alternativa per la tua distribuzione se vuoi sviluppare delle mod.

## 3. Verifica che Java 17 sia installato.

Quando l'installazione è stata completata, puoi verificare che Java 17 sia installato aprendo il terminale e scrivendo `java -version`.

Se il comando funziona correttamente, vedrai qualcosa simile a quello mostrato prima, dove è mostrata la versione di Java:

![Terminale con scritto "java -version"](/assets/players/installing-java/linux-java-version.png)
