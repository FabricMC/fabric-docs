---
title: Installer des mods
description: Un guide pour installer des mods pour Fabric pas à pas.
authors:
  - IMB11

search: false
---

# Installer des mods

Ce guide explique en détail le processus d'installation de mods pour Fabric avec le launcher Minecraft officiel.

Pour les lanceurs tiers, vous devriez consulter leur documentation.

## 1. Télécharger le mod

:::warning
Ne téléchargez que des mods provenant de sources dignes de confiance. Pour plus d'informations sur comment trouver des mods, voir le guide [Trouver des mods fiables](./finding-mods).
:::

La majorité des mods requiert également Fabric API, qui peut être téléchargée depuis [Modrinth](https://modrinth.com/mod/fabric-api) ou [CurseForge](https://curseforge.com/minecraft/mc-mods/fabric-api).

Lorsque vous téléchargez des mods, assurez-vous :

- Qu'ils fonctionnent sur la version de Minecraft à laquelle vous souhaitez jouer. Par exemple, un mod qui fonctionne sur la version 1.20 peut ne pas fonctionner sur la version 1.20.2.
- Qu'ils sont pour Fabric et pas un autre mod loader.
- Et qu'ils sont pour la bonne version de Minecraft (Java Edition).

## 2. Déplacer le mod dans le dossier `mods`

Le dossier mods peut être trouvé aux emplacements suivants pour chaque système d'exploitation.

Vous pouvez généralement coller ces chemins dans la barre d'adresse de votre explorateur de fichiers pour naviguer rapidement jusqu'au dossier.

::: code-group

```:no-line-numbers [Windows]
%appdata%\.minecraft\mods
```

```:no-line-numbers [macOS]
~/Library/Application Support/minecraft/mods
```

```:no-line-numbers [Linux]
~/.minecraft/mods
```

:::

Une fois le dossier `mods` trouvé, vous pouvez y déplacer les fichiers `.jar` du mod.

![Mods installés dans le dossier mods](/assets/players/installing-mods.png)

## 3. Et voilà !

Après avoir déplacé les mods dans le dossier `mods`, vous pouvez ouvrir le launcher Minecraft et sélectionner le profil Fabric depuis le menu déroulant en bas à gauche, puis appuyer sur Jouer.

![Le lanceur Minecraft avec le profil Fabric sélectionné](/assets/players/installing-fabric/launcher-screen.png)

## Résolution de problèmes

Si vous rencontrez des problèmes en suivant ce guide, vous pouvez demander de l'aide dans le [Discord Fabric](https://discord.gg/v6v4pMv) dans le salon `#player-support`.

Vous pouvez également essayer de dépanner le problème vous-même en utilisant les guides de dépannage :

- [Rapports de plantage](./troubleshooting/crash-reports)
- [Mettre en ligne des logs](./troubleshooting/uploading-logs)
