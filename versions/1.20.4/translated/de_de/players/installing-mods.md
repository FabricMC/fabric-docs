---
title: Mods installieren
description: Eine Schritt-für-Schritt-Anleitung zur Installation von Mods für Fabric.
authors:
  - IMB11

search: false
---

# Mods installieren

Diese Anleitung wird die Installation von Mods für Fabric detailliert für den Minecraft-Launcher erklären.

Für die Launcher von Drittanbietern solltest du deren Dokumentation verwenden.

## 1. Herunterladen der Mod

:::warning
Du solltest Mods nur aus Quellen herunterladen, denen du vertraust. Weitere Informationen zum Finden von Mods findest du iim Leitfaden [Vertrauenswürdige Mods finden](./finding-mods).
:::

Die meisten Mods benötigen auch die Fabric API, die von [Modrinth](https://modrinth.com/mod/fabric-api) oder [CurseForge](https://curseforge.com/minecraft/mc-mods/fabric-api) heruntergeladen werden kann.

Stelle beim Herunterladen von Mods sicher, dass:

- Sie auf der Version von Minecraft funktionieren, auf der du spielen möchtest. Eine Mod, die beispielsweise auf der Version 1.20 funktioniert, muss nicht auf der Version 1.20.2 funktionieren.
- Sie für Fabric sind und nicht für einen anderen Mod-Loader.
- Sie für die korrekte Edition von Minecraft sind (Java Edition).

## 2. Verschieben der Mod in das Verzeichnis `mods`

Das Mods-Verzeichnis kann, abhängig vom Betriebssystem, an den folgenden Stellen gefunden werden.

Normalerweise können die Pfade direkt in der Adressleiste des Dateiexplorers eingefügt werden, um schnell zum Verzeichnis zu navigieren.

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

Sobald du das `mods`-Verzeichnis gefunden hast, kannst du die `.jar`-Dateien der Mods dorthin verschieben.

![Installierte Mods im mods-Verzeichnis](/assets/players/installing-mods.png)

## 3. Du hast es geschafft!

Sobald du die Mods in das `mods`-Verzeichnis verschoben hast, kannst du den Minecraft-Launcher öffnen und das Fabric-Profil aus der Liste in der unteren linken Ecke auswählen und spielen!

![Minecraft-Launcher mit ausgewähltem Fabric-Profil](/assets/players/installing-fabric/launcher-screen.png)

## Problembehandlung

Fall dir beim Folgen dieser Anleitungen irgendwelche Fehler auftreten, kannst du nach Hilfe im [Fabric-Discord](https://discord.gg/v6v4pMv) im Kanal `#player-support` fragen.

Du kannst auch versuchen, das Problem mit diesen Fehlerbehebungs-Seiten selbst zu lösen:

- [Absturzberichte](./troubleshooting/crash-reports)
- [Logs hochladen](./troubleshooting/uploading-logs)
