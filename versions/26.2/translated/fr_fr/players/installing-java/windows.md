---
title: Installer Java sur Windows
description: Guide étape par étape pour l'installation de Java sous Windows.
authors:
  - IMB11
  - skycatminepokie
next: false
---

Ce guide vous guidera dans l'installation de Java 25 sur Windows.

Le lanceur Minecraft intègre sa propre installation Java ; cette section ne vous concerne donc que si vous souhaitez utiliser le programme d'installation basé sur le fichier `.jar` de Fabric, ou si vous souhaitez utiliser le fichier `.jar` du serveur Minecraft.

## 1. Vérifier si Java est déjà installé {#1-check-if-java-is-already-installed}

Pour vérifier si Java est déjà installé, vous devez d'abord ouvrir l'invite de commande.

Pour ce faire, appuyez sur <kbd>Windows</kbd>+<kbd>R</kbd>, puis saisissez `cmd.exe` dans la fenêtre qui s'affiche.

![Windows Run Dialog with "cmd.exe" in the run bar](/assets/players/installing-java/windows-run-dialog.png)

Une fois l'invite de commande ouverte, tapez `java -version` puis appuyez sur <kbd>Entrée</kbd>.

Si la commande s'exécute correctement, vous verrez s'afficher un résultat similaire à celui-ci. Sinon, suivez la prochaine étape.

![Command prompt with "java -version" typed in](/assets/players/installing-java/windows-java-version.png)

::: warning

Pour utiliser Minecraft 26.1, vous devez disposer au minimum de Java 25.

Si cette commande affiche une version inférieure à 25, vous devrez mettre à jour votre installation Java actuelle ; continuez à lire cette page.

:::

## 2. Télécharger l'installateur de Java 25 {#2-download-the-java-installer}

Pour installer Java 25, vous devrez télécharger le programme d'installation depuis [Adoptium](https://adoptium.net/temurin/releases?version=25&os=windows&arch=any&mode=filter).

Nous vous recommandons de télécharger la version `Windows Installer (.msi)` :

![Adoptium download page with Windows Installer (.msi) highlighted](/assets/players/installing-java/windows-download-java.png)

Vous devez choisir « x86 » si vous disposez d'un système d'exploitation 32 bits, ou « x64 » si vous disposez d'un système d'exploitation 64 bits.

La plupart des ordinateurs modernes sont équipés d'un système d'exploitation 64-bit. En cas de doute, essayez de télécharger la version 64 bits.

## 3. Lancer l'installateur ! {#3-run-the-installer}

Suivez les étapes indiquées dans le programme d'installation pour installer Java 25. Lorsque vous accédez à cette page, vous devez configurer les fonctionnalités suivantes en sélectionnant « La fonctionnalité sera entièrement installée sur le disque dur local » :

- `Définir la variable d'environnement JAVA_HOME` - Elle sera ajoutée à votre PATH.
- `Clés de registre JavaSoft (Oracle)`

![Java 25 installer with "Set JAVA_HOME variable" and "JavaSoft (Oracle) registry keys" highlighted](/assets/players/installing-java/windows-wizard-screenshot.png)

Une fois cette étape terminée, vous pouvez cliquer sur « Suivant » et poursuivre l'installation.

::: warning

Windows n'indique pas toujours aux autres programmes que Java est installé tant que vous n'avez pas redémarré votre ordinateur.

**Veillez à redémarrer votre ordinateur avant de continuer !**

:::

## 4. Vérifier que Java 25 est bien installé {#4-verify-that-java-is-installed}

Une fois l'installation terminée, vous pouvez vérifier que Java 25 est bien installé en ouvrant à nouveau l'invite de commande et en tapant `java -version`.

Si la commande s'exécute correctement, vous verrez s'afficher un message similaire à celui présenté précédemment, dans lequel la version de Java est indiquée :

![Command prompt with "java -version" typed in](/assets/players/installing-java/windows-java-version.png)

Si vous rencontrez des difficultés, n'hésitez pas à demander de l'aide sur [Fabric Discord](https://discord.fabricmc.net/), dans le canal `#player-support`.
