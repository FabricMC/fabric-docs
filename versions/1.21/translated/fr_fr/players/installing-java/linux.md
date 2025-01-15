---
title: Installer Java sous Linux
description: Un guide étape par étape sur comment installer Java sous Linux.
authors:
  - IMB11
---

# Installer Java sous Linux

Ce guide vous accompagnera dans l'installation de Java 21 sous Linux.

## 1. Vérification de si Java est déjà installé {#1-check-if-java-is-already-installed}

Ouvrez un terminal, entrez `java -version`, et pressez <kbd>Entrer</kbd>.

![Terminal avec "java -version" entré dedans](/assets/players/installing-java/linux-java-version.png)

:::warning
Pour utiliser Minecraft 1.21, vous avez besoin d'avoir au moins Java 21 d'iinstallé. Si cette commande affiche une version inférieure à 21, vous aurez besoin de mettre à jour votre installation de Java.
:::

## 2. Téléchargement et installation de Java 21 {#2-downloading-and-installing-java}

Nous recommandons d'utiliser OpenJDK 21, qui est disponible sur la plupart des distributions Linux.

### Arch Linux {#arch-linux}

:::info
Pour plus d'information sur comment installer Java sous Arch Linux, consultez le [Wiki d'Arch Linux (Anglais)](https://wiki.archlinux.org/title/Java).
:::

Vous pouvez installer le dernier JRE depuis les dépots officiels :

```sh
sudo pacman -S jre-openjdk
```

Si vous faites tourner un serveur sans besoin d'interface graphique, vous pouvez installer la version sans affichage à la place :

```sh
sudo pacman -S jre-openjdk-headless
```

Si vous prévoyez de développer des mods, vous aurez plutôt besoin du JDK :

```sh
sudo pacman -S jdk-openjdk
```

### Debian/Ubuntu

Vous pouvez installer Java 21 en utilisant `apt` avec les commandes suivantes :

```sh
sudo apt update
sudo apt install openjdk-21-jdk
```

### Fedora

Vous pouvez installer Java 21 en utilisant `dnf` avec les commandes suivantes :

```sh
sudo dnf install java-21-openjdk
```

Si vous n'avez pas besoin d'interface graphique, vous pouvez installer la version sans affichage à la place :

```sh
sudo dnf install java-21-openjdk-headless
```

Si vous prévoyez de développer des mods, vous aurez plutôt besoin du JDK :

```sh
sudo dnf install java-21-openjdk-devel
```

### Autres distributions Linux

Si votre distribution n'est pas listée ci-dessus, vous pouvez télécharger le dernier JRE sur [Adoptium](https://adoptium.net/fr/temurin/)

Il est recommandé de se référer à un guide alternatif pour votre distribution si vous souhaitez développer des mods.

## 3. Vérifier que Java 21 est bien installé {#3-verify-that-java-is-installed}

Lorsque l'installation est terminée, vous pouvez vérifier que Java 21 est installé en ouvrant un terminal et en entrant `java -version`.

Si la commande se termine avec succès, vous verrez quelque chose de similaire à avant, où la version de java est affichée :

![Terminal avec "java -version" entré dedans](/assets/players/installing-java/linux-java-version.png)
