---
title: Java auf macOS installieren
description: Ein Schritt-für-Schritt-Leitfaden zur Installation von Java auf macOS.
authors:
  - dexman545
  - ezfe
next: false
---

Diese Anleitung führt Sie durch die Installation von Java 21 auf macOS.

Der Minecraft Launcher kommt bereits mit seiner eigenen Java Installation, diese Sektion ist also nur relevant, wenn du den auf der Fabric `.jar` basierenden Installer oder die Minecraft Server `.jar` verwenden möchtest.

## 1. Überprüfe, ob Java bereits installiert ist {#1-check-if-java-is-already-installed}

Gebe in das Terminal (zu finden unter `/Applications/Utilities/Terminal.app`) Folgendes ein und drücke die <kbd>Eingabetaste</kbd>:

```sh
$(/usr/libexec/java_home -v 21)/bin/java --version
```

Du solltest etwa Folgendes sehen:

```text:no-line-numbers
openjdk 21.0.9 2025-10-21 LTS
OpenJDK Runtime Environment Temurin-21.0.9+10 (build 21.0.9+10-LTS)
OpenJDK 64-Bit Server VM Temurin-21.0.9+10 (build 21.0.9+10-LTS, mixed mode, sharing)
```

Beachten die Versionsnummer: Im obigen Beispiel lautet sie `21.0.9`.

::: warning

Um Minecraft 1.21.11 zu verwenden, musst du mindestens Java 21 installiert haben.

Wenn dieser Befehl, eine Version kleiner als 21 anzeigt, musst du deine existierende Java Installation aktualisieren; lies diese Seite weiter.

:::

## 2. Herunterladen und Installieren von Java 21 {#2-downloading-and-installing-java}

Wir empfehlen die Verwendung von [Adoptiums Build von OpenJDK 21](https://adoptium.net/temurin/releases?version=21&os=mac&arch=any&mode=filter):

![Temurin Java Download Seite](/assets/players/installing-java/macos-download-java.png)

Stelle sicher die Version "21 – LTS" auszuwählen und das Installer Format `.PKG` auszuwählen.
Du solltest auch die richtige Architektur entsprechend dem Prozessor deines Systems auswählen:

- Wenn du einen Apple M-Serie Prozessor hast, wähle `aarch64` (der Standard)
- Wenn du einen Intel Prozessor hast, wähle `x64`
- Folge diesen [Anweisungen, um zu wissen, welcher Prozessor sich in deinem Mac befindet](https://support.apple.com/en-us/116943)

Nach dem Download des `.pkg` Installer, öffne diese und folge den Aufforderungen:

![Temurin Java Installer](/assets/players/installing-java/macos-installer.png)

Du wirst dein Administrator Passwort eingeben müssen, um die Installation abzuschließen:

![macOS Aufforderung zur Passwort Eingabe](/assets/players/installing-java/macos-password-prompt.png)

### Homebrew verwenden {#using-homebrew}

Wenn du bereits [Homebrew](https://brew.sh) installiert hast, kannst du stattdessen Java 21 mit `brew` installieren:

```sh
brew install --cask temurin@21
```

## 3. Verifiziere, dass Java 21 installiert ist {#3-verify-that-java-is-installed}

Sobald die Installation abgeschlossen ist, kannst du überprüfen, ob Java 21 aktiv ist, indem du das Terminal erneut öffnest und `$(/usr/libexec/java_home -v 21)/bin/java --version` eingibst.

Wenn der Befehl erfolgreich ist, solltest du etwa Folgendes sehen:

```text:no-line-numbers
openjdk 21.0.9 2025-10-21 LTS
OpenJDK Runtime Environment Temurin-21.0.9+10 (build 21.0.9+10-LTS)
OpenJDK 64-Bit Server VM Temurin-21.0.9+10 (build 21.0.9+10-LTS, mixed mode, sharing)
```

Wenn du Probleme hast, kannst du gerne im [Fabric Discord](https://discord.fabricmc.net/) im Kanal `#player-support` um Hilfe bitten.
