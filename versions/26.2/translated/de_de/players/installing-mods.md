---
title: Mods installieren
description: Ein Schritt-für-Schritt-Leitfaden zur Installation von Mods für Fabric.
authors:
  - IMB11
---

Diese Anleitung wird die Installation von Mods für Fabric detailliert für den Minecraft-Launcher erklären.

Für die Launcher von Drittanbietern solltest du deren Dokumentation verwenden.

## 1. Herunterladen der Mod {#1-download-the-mod}

::: warning

Du solltest Mods nur aus Quellen herunterladen, denen du vertraust. Weitere Informationen zum Finden von Mods findest du iim Leitfaden [Vertrauenswürdige Mods finden](./finding-mods).

:::

Der Großteil der Mods benötigt ebenfalls die Fabric API, die von [Modrinth](https://modrinth.com/mod/fabric-api) oder [CurseForge](https://curseforge.com/minecraft/mc-mods/fabric-api) heruntergeladen werden kann.

Stelle beim Herunterladen von Mods sicher, dass:

- Sie auf der Version von Minecraft funktionieren, auf der du spielen möchtest. Ein Mod, der beispielsweise auf der Version 1.21.8 funktioniert hat, funktioniert möglicherweise auf der Version 1.21.11 nicht mehr.
- Sie für Fabric sind und nicht für einen anderen Mod-Loader.
- Sie für die korrekte Edition von Minecraft sind (Java Edition).

## 2. Verschieben der Mod in das Verzeichnis `mods` {#2-move-the-mod-to-the-mods-folder}

Das Mods-Verzeichnis kann, abhängig vom Betriebssystem, an den folgenden Stellen gefunden werden.

Normalerweise können die Pfade direkt in der Adressleiste des Dateiexplorers eingefügt werden, um schnell zum Verzeichnis zu navigieren.

::: code-group

```text:no-line-numbers [Windows]
%appdata%\.minecraft\mods
```

```text:no-line-numbers [macOS]
~/Library/Application Support/minecraft/mods
```

```text:no-line-numbers [Linux]
~/.minecraft/mods
```

:::

Sobald du das `mods`-Verzeichnis gefunden hast, kannst du die `.jar`-Dateien der Mods dorthin verschieben.

![Installierte Mods im mods-Verzeichnis](/assets/players/installing-mods.png)

## 3. Du hast es geschafft! {#3-you-re-done}

Sobald du die Mods in das `mods`-Verzeichnis verschoben hast, kannst du den Minecraft-Launcher öffnen und das Fabric-Profil aus der Liste in der linken unteren Ecke auswählen und spielen drücken!

![Minecraft Launcher mit dem Fabric Profil ausgewählt](/assets/players/installing-fabric/launcher-screen.png)

## Problembehandlung {#troubleshooting}

Wenn du beim Befolgen dieses Leitfadens auf Probleme stößt, kannst du im [Fabric Discord](https://discord.fabricmc.net/) im Kanal `#player-support` um Hilfe bitten.

Du kannst auch versuchen, das Problem mit diesen Fehlerbehebungs-Seiten selbst zu lösen:

- [Absturzberichte](./troubleshooting/crash-reports)
- [Logs hochladen](./troubleshooting/uploading-logs)
