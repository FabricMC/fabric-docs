---
title: Installation de Fabric sur linux
description: Guide étape par étape pour l'installation de Fabric sous Linux.
authors:
  - Benonardo
  - ezfe
  - IMB11
  - modmuss50
next: false
---

<!---->

:::info PREREQUIS

Vous aurez sûrement besoin d'[installer java](../installing-java/linux) avant d'ouvrir le `.jar`.

:::

<!-- #region common -->

## 1. Télécharger l'installateur de Fabric {#1-download-the-fabric-installer}

Téléchargez la version `.jar` du programme d'installation de Fabric depuis le [site Web de Fabric](https://fabricmc.net/use/), en cliquant sur `Télécharger le programme d'installation (Universal/.JAR)`.

## 2. Exécutez l'installateur de Fabric {#2-run-the-fabric-installer}

Fermez Minecraft et le lanceur Minecraft avant d'exécuter l'installateur.

Ouvrir un terminal et exécutez l'installateur en utilisant Java:

```sh
java -jar fabric-installer.jar
```

Une fois que vous avez ouvert l'installateur, vous devrez voir un écran comme ceci:

![Installateur Fabric avec "Install" surligné](/assets/players/installing-fabric/installer-screen.png)

<!-- #endregion common -->

Sélectionnez la version de Minecraft que vous souhaitez et cliquez sur `Install`. Assurez-vous que `Create Profile` est coché.

## 3. Terminer la configuration {#3-finish-setup}

Une fois l'installation terminée, ouvrez le lanceur Minecraft. Sélectionnez ensuite le profil Fabric dans le menu déroulant des versions, puis cliquez sur Jouer.

![Lanceur Minecraft avec le profile Fabric sélectionné](/assets/players/installing-fabric/launcher-screen.png)

Vous pouvez désormais ajouter des mods à votre jeu. Consultez le guide ["Trouver des mods fiables"](../finding-mods) pour plus d'informations.

Si vous rencontrez des difficultés, n'hésitez pas à demander de l'aide sur le [Discord Fabric](https://discord.fabricmc.net/) dans le salon `#player-support`.
