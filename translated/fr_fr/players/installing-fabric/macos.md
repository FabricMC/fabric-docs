---
title: Installation de Fabric sur macOS
description: Un guide étape par étape sur comment installer Fabric sur macOS.
authors:
  - Benonardo
  - ezfe
  - IMB11
  - modmuss50
next: false
---

<!---->

:::info PRÉREQUIS

Vous avez besoin d'[installer Java](../installing-java/macos) avant d'exécuter le `.jar`.

:::

<!-- #region common -->

## 1. Télécharger l'installateur de Fabric {#1-download-the-fabric-installer}

Télécharger le `.jar` de la version de l'installateur de Fabric depuis le [site internet de Fabric](https://fabricmc.net/use/), en cliquant sur `Download installer (Universal/.JAR)`.

## 2. Exécuter l'installeur de Fabric {#2-run-the-fabric-installer}

Fermer Minecraft et le lanceur de Minecraft avant d'exécuter l'installateur.

::: tip

Vous pourriez recevoir un avertissement indiquant qu'Apple ne peut pas vérifier le `.jar`. Pour le contourner, ouvrer Réglages Système > Confidentialité et sécurité, et cliquez sur `Ouvrir quand même`. Confirmer et entrer votre mot de passe administrateur si demandé.

![macos System Settings](/assets/players/installing-fabric/macos-settings.png)

:::

Une fois que vous avez ouvert l'installateur, vous devriez voir un écran comme ça:

![Installateur Fabric avec "Installer" en surveillance](/assets/players/installing-fabric/installer-screen.png)

<!-- #endregion common -->

Sélectionner votre version de Minecraft et cliquer sur `Installer`. Assurez-vous que `Créer un Profil` soit sélectionné.

### Installation via Homebrew {#installing-via-homebrew}

Si vous avec déjà [Homebrew](https://brew.sh) d'installer, vous pouvez installer l'installateur de Fabric en utilisant `brew` comme ceci:

```sh
brew install fabric-installer
```

## 3. Terminer la configuration {#3-finish-setup}

Une fois l'installation complète, ouvrez le lanceur Minecraft. Sélectionner le profil Fabric depuis le menu déroulant des versions et cliquer sur Jouer.

![Lanceur Minecraft avec le profil Fabric sélectionné](/assets/players/installing-fabric/launcher-screen.png)

Vous pouvez maintenant ajouter des mods à votre jeu. Voir le guide [Trouver des mods fiables](../finding-mods) pour plus d'information.

Si vous rencontrez des problèmes, n'hésitez pas à demander de l'aide sur le [discord de Fabric](https://discord.fabricmc.net/) sur le channel `#player-support`.
