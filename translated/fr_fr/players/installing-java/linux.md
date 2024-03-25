---
title: Installer Java sous Linux
description: Un guide étape par étape sur comment installer Java sous Linux.
authors:
  - IMB11
---

# Installer Java sous Linux

Ce guide vous accompagnera dans l'installation de Java 17 sous Linux.

## 1. Vérifier si Java est déjà installé.

Ouvrez un terminal, entrez `java -version`, et pressez <kbd>Entrer</kbd>.

![Terminal avec "java -version" entré dedans.](/assets/players/installing-java/linux-java-version.png)

:::warning
Pour utiliser la majorité des versions modernes de Minecraft, vous aurez besoin d'avoir au moins Java 17 d'installé. Si cette commande affiche une version inférieure à 17, vous aurez besoin de mettre à jour vous installation Java existante.
:::

## 2. Télécharger et installer Java 17

Nous recommandons d'utiliser OpenJDK 17, qui est disponible sur la plupart des distributions Linux.

### Arch Linux

:::info
Pour plus d'information sur comment installer Java sous Arch Linux, consultez le [Wiki d'Arch Linux (Anglais)](https://wiki.archlinux.org/title/Java).
:::

Vous pouvez installer le dernier JRE depuis les dépots officiels :

```bash
sudo pacman -S jre-openjdk
```

Si vous voulez faire un serveur sans nécéssité d'interface graphique, vous pouvez installer la version sans affiche à la place :

```bash
sudo pacman -S jre-openjdk-headless
```

Si vous prévoyez de développer des mods, vous aurez plutôt besoin du JDK :

```bash
sudo pacman -S jdk-openjdk
```

### Debian/Ubuntu

Vous pouvez installer Java 17 en utilisant `apt` avec les commandes suivantes :

```bash
sudo apt update
sudo apt install openjdk-17-jdk
```

### Fedora

Vous pouvez installer Java 17 en utilisant `dnf` avec les commandes suivantes :

```bash
sudo dnf install java-17-openjdk
```

Si vous n'avez pas besoin d'interface graphique, vous pouvez installer la version sans affiche à la place :

```bash
sudo dnf install java-17-openjdk-headless
```

Si vous prévoyez de développer des mods, vous aurez plutôt besoin du JDK :

```bash
sudo dnf install java-17-openjdk-devel
```

### Autres distributions Linux

Si votre distribution n'est pas listée au dessus, vous pouvez télécharger le dernier JRE sur [AdoptOpenJDK/Adoptium](https://adoptium.net/fr/temurin/)

Il est recommandé de se référer à un guide alternatif pour votre distribution si vous souhaitez développer des mods.

## 3. Vérifier que Java 17 est bien installé.

Lorsque l'installation est complète, vous pouvez vérifier que Java 17 est installé en ouvrant un terminal et en entrant `java -version`.

Si la commande se termine avec succès, vous verrez quelque chose de similaire à avant, où la version de java est affichée :

![Terminal avec "java -version" entré dedans.](/assets/players/installing-java/linux-java-version.png)
