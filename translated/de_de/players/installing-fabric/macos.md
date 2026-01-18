---
title: Fabric auf macOS installieren
description: Ein Schritt-für-Schritt-Leitfaden zur Installation von Fabric auf macOS.
authors:
  - Benonardo
  - ezfe
  - IMB11
  - modmuss50
next: false
---

<!---->

:::info VORAUSSETZUNGEN

Du musst möglicherweise [Java installieren](../installing-java/macos), vor du die `.jar` ausführst.

:::

<!-- #region common -->

## 1. Den Fabric Installer herunterladen {#1-download-the-fabric-installer}

Lade die `.jar` Version des Fabric Installer von der [Fabric Website](https://fabricmc.net/use/) herunter, indem du auf `Download installer (Universal/.JAR)` klickst.

## 2. Den Fabric Installer ausführen {#2-run-the-fabric-installer}

Schließe vor dem Ausführen des Installers zuerst Minecraft und den Minecraft Launcher.

::: tip

Du erhältst möglicherweise eine Warnung, dass Apple die `.jar` nicht verifizieren konnte. Um dies zu umgehen, öffne Systemeinstellungen > Datenschutz und Sicherheit und klicke dann auf `Dennoch öffnen`. Bestätige und gebe, wenn aufgefordert, dein Administrator Passwort ein.

![macOS Systemeinstellungen](/assets/players/installing-fabric/macos-settings.png)

:::

Sobald du den Installer öffnest, solltest du eine Oberfläche wie diese sehen:

![Fabric Installer mit "Install" hervorgehoben](/assets/players/installing-fabric/installer-screen.png)

<!-- #endregion common -->

Wähle die gewünschte Minecraft-Version aus und klicke auf `Installieren`. Stelle sicher, dass die Option `Profil erstellen` aktiviert ist.

### Installation über Homebrew {#installing-via-homebrew}

Wenn du bereits [Homebrew](https://brew.sh) installiert hast, kannst du stattdessen den Fabric Installer mit `brew` installieren:

```sh
brew install fabric-installer
```

## 3. Einrichtung abschließen {#3-finish-setup}

Sobald die Installation abgeschlossen ist, öffne den Minecraft Launcher. Wähle dann das Fabric Profil aus dem Versions-Dropdown-Menü aus und klicke auf Spielen.

![Minecraft Launcher mit dem Fabric Profil ausgewählt](/assets/players/installing-fabric/launcher-screen.png)

Du kannst jetzt Mods zu deinem Spiel hinzufügen. Weitere Informationen findest du im Leitfaden [Vertrauenswürdige Mods finden](../finding-mods).

Wenn du Probleme hast, kannst du gerne im [Fabric Discord](https://discord.fabricmc.net/) im Kanal `#player-support` um Hilfe bitten.
